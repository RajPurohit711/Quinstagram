package com.quinstagram.postService.service.impl;

import com.quinstagram.postService.common.DislikeDto;
import com.quinstagram.postService.common.OrganizationPost;
import com.quinstagram.postService.common.PostDtoAnalytics;
import com.quinstagram.postService.dto.PostDto;
import com.quinstagram.postService.dto.ReactionDto;
import com.quinstagram.postService.entity.Post;
import com.quinstagram.postService.entity.Reaction;
import com.quinstagram.postService.repository.PostRepository;
import com.quinstagram.postService.repository.ReactionRepository;
import com.quinstagram.postService.service.ReactionService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReactionServiceImpl implements ReactionService {

    @Autowired
    private ReactionRepository reactionRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    KafkaTemplate<String,JSONObject> kafkaTemplate;

    @Override
    public void add(Reaction reaction) {
        reactionRepository.save(reaction);

    }
    @Override
    public void deleteById(String id) {
        reactionRepository.deleteById(id);

    }

    @Override
    public Long getNoOfLikes(boolean flag, Long postId) {
        return reactionRepository.getNoOfLikes(flag,postId);
    }

    @Override
    public List<Reaction> findAll() {
        return reactionRepository.findAll();
    }

    @Override
    public void deleteAll() {
        reactionRepository.deleteAll();
    }

    @Override
    public Reaction findByPostIdAndReactionBy(Long postId,String reactionBy){
        return reactionRepository.findByPostIdAndReactionBy(postId,reactionBy);
    }

    @Override
    public void sendAnalyticsData(ReactionDto reactionDto) {

        Post post = postRepository.findById(reactionDto.getPostId()).get();
        PostDtoAnalytics postDtoAnalytics = new PostDtoAnalytics();
        final DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        final long unixTime = new Date().getTime()/1000L;
        final String formattedDtm = Instant.ofEpochSecond(unixTime)
                .atZone(ZoneId.of("GMT-4"))
                .format(formatter);

        if(post.getType())
        postDtoAnalytics.setContentType("Image");
        else
            postDtoAnalytics.setContentType("Video");

        postDtoAnalytics.setPostId(String.valueOf(reactionDto.getPostId()));

        postDtoAnalytics.setPostName(post.getDescription());
        postDtoAnalytics.setUserId(reactionDto.getReactionBy());

        if(reactionDto.isReactionType())
        postDtoAnalytics.setReactionType("Like");
        else
            postDtoAnalytics.setReactionType("Dislike");
        postDtoAnalytics.setReactionTime(new Timestamp(Instant.now().getEpochSecond()*1000));

        postDtoAnalytics.setPostType("Instagram");

        String res = returnCatType(post.getCategory());
        JSONObject data=new JSONObject();
        System.out.println();
        data.put("postId",postDtoAnalytics.getPostId());
        data.put("postName",postDtoAnalytics.getPostName());
        data.put("postType",postDtoAnalytics.getPostType());
        data.put("userId",postDtoAnalytics.getUserId());
        data.put("reactionTime",postDtoAnalytics.getReactionTime());
        data.put("reactionType",postDtoAnalytics.getReactionType());
        data.put("contentType",postDtoAnalytics.getContentType());
        data.put("category",res);

        System.out.println(postDtoAnalytics.getUserId());
        System.out.println(postDtoAnalytics.getContentType());
        System.out.println(postDtoAnalytics.getPostType());
        System.out.println(postDtoAnalytics.getUserId());
        System.out.println(postDtoAnalytics.getPostName());
        System.out.println(postDtoAnalytics.getReactionTime());
        System.out.println(postDtoAnalytics.getReactionType());

        kafkaTemplate.send("postTopicAnalysis", data);
    }

    @Override
    public void sendDislike(ReactionDto reactionDto) {

        Post post = postRepository.findById(reactionDto.getPostId()).get();
        JSONObject jsonObject = new JSONObject();

        String res = returnCatType(post.getCategory());
        jsonObject.put("categoryId",res);
        jsonObject.put("postUrl",post.getUrl());
        jsonObject.put("appId","2");
        jsonObject.put("userEmail",reactionDto.getReactionBy());
        System.out.println("kdlnf____________");
        kafkaTemplate.send("crmm",jsonObject);
        System.out.println("ldksjf");

    }


    String  returnCatType(String category){
        if(category.equals("sport"))
        {
            return "1";
        }

        if(category.equals("lifestyle")){
           return "2";

        }

        if(category.equals("e-commercial")){
            return "3";
        }
        if(category.equals("education")){
            return "4";

        }
        if(category.equals("Cinematic")){
            return "5";
        }
        else
            return "2";
    }

    @Override
    public void sendNotificationForReaction(ReactionDto reactionDto) {
        JSONObject jsonObject = new JSONObject();


        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        final long unixTime = new Date().getTime()/1000L;
        final String formattedDtm = Instant.ofEpochSecond(unixTime)
                .atZone(ZoneId.of("Asia/Kolkata"))
                .format(formatter);
        String message;

        Post post = postRepository.findById(reactionDto.getPostId()).get();
        if(reactionDto.isReactionType())
            message = reactionDto.getReactionBy()+" has liked your post at "  + formattedDtm;
        else
            message = reactionDto.getReactionBy()+" has disliked your post at "  + formattedDtm;

        jsonObject.put("title","Pinstagram");
        jsonObject.put("appId","2");
        List<String> list = new ArrayList<>();
        list.add(post.getUserId());
        jsonObject.put("userEmails",list);
        jsonObject.put("message",message);

        kafkaTemplate.send("pinstagramrequest",jsonObject);

    }
}
