package com.quinstagram.postService.controller;
import com.quinstagram.postService.dto.ReactionDto;
import com.quinstagram.postService.entity.Post;
import com.quinstagram.postService.entity.Reaction;
import com.quinstagram.postService.service.PostService;
import com.quinstagram.postService.service.ReactionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping(value = "/reaction")
@RestController
public class ReactionController {

    @Autowired
    private ReactionService reactionService;

    // todo : autowire post service to update like and dislike counts
    @Autowired
    private PostService postService;

    @Autowired
    KafkaTemplate kafkaTemplate;

    @RequestMapping(method={RequestMethod.POST,RequestMethod.PUT})
    void save(@RequestBody ReactionDto reactionDto)
    {
        System.out.println("called");

        Post post = postService.getPostById(reactionDto.getPostId());
        if(post.getNumberOfLikes()==null){
            post.setNumberOfLikes(0);
        }
        if(post.getNumberOfDisLikes()==null){
            post.setNumberOfDisLikes(0);
        }
        Reaction reaction = reactionService.findByPostIdAndReactionBy(reactionDto.getPostId(),reactionDto.getReactionBy());
        if(reaction!=null){
            if(reaction.isReactionType()!=reactionDto.isReactionType()){


                if(reaction.isReactionType()){
                    reaction.setReactionType(false);
                    post.setNumberOfDisLikes(post.getNumberOfDisLikes()+1);
                    post.setNumberOfLikes(post.getNumberOfLikes()-1);
                }
                else {
                    reaction.setReactionType(true);
                    post.setNumberOfDisLikes(post.getNumberOfDisLikes()-1);
                    post.setNumberOfLikes(post.getNumberOfLikes()+1);
                }
            }
        }
        else {
            reaction=new Reaction();
            BeanUtils.copyProperties(reactionDto,reaction);
            if(reaction.isReactionType()){
                post.setNumberOfLikes(post.getNumberOfLikes()+1);
            }
            else {
                post.setNumberOfDisLikes(post.getNumberOfDisLikes()+1);
            }
        }
        reactionService.add(reaction);
        postService.save(post);
        reactionService.sendAnalyticsData(reactionDto);


        if(!reactionDto.isReactionType())
        {
            reactionService.sendDislike(reactionDto);
        }

//        reactionService.sendNotificationForReaction(reactionDto);
    }


    @GetMapping("fb/{pid}/{rby}")
    Reaction findBy(@PathVariable("pid") Long postId,@PathVariable("rby") String reactionBy){
        return reactionService.findByPostIdAndReactionBy(postId,reactionBy);
    }

    @DeleteMapping(value = "/delete/{id}")
    void delete(@PathVariable("id")String id){ reactionService.deleteById(id);
    }

    @DeleteMapping(value = "/deleteAll")
    void deleteAll(){
        reactionService.deleteAll();
    }

    @GetMapping(value = "/{id}/{flag}")
    public Long test(@PathVariable Long id ,@PathVariable boolean flag){
        return reactionService.getNoOfLikes(flag,id);
    }

    @GetMapping("/getAll")
    List<ReactionDto> getAllReaction() {
        List<Reaction> reactions = reactionService.findAll();
        List<ReactionDto> reactionDtos = new ArrayList<>();
        for (Reaction reaction : reactions) {
            ReactionDto reactionDto = new ReactionDto();
            BeanUtils.copyProperties(reaction, reactionDto);
            reactionDtos.add(reactionDto);}
        return reactionDtos;
    }

}
