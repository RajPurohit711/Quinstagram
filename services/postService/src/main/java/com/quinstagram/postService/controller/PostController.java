package com.quinstagram.postService.controller;
import com.quinstagram.postService.common.OrganizationPost;
import com.quinstagram.postService.dto.PostDto;
import com.quinstagram.postService.dto.PostProfileDto;
import com.quinstagram.postService.common.ResponseDto;
import com.quinstagram.postService.entity.Post;
import com.quinstagram.postService.service.PostService;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private DirectExchange exchangePostProfile;

    @Autowired
    private DirectExchange getExchangePostProfileDelete;

    @GetMapping("/{id}")
    public Post getPostById(@PathVariable Long id){
        return postService.getPostById(id);
    }

    @RequestMapping(method={RequestMethod.PUT,RequestMethod.POST})
    void save(@RequestBody PostDto postDto){


        Post post = new Post();
        BeanUtils.copyProperties(postDto,post);
        post.setNumberOfLikes(0);
        post.setNumberOfDisLikes(0);
        postService.save(post);

        PostProfileDto postProfileDto=new PostProfileDto();
        postProfileDto.setPostId(post.getId());
        postProfileDto.setUserEmail(post.getUserId());

//        Todo send signal to profile service {postId,UserId,TimeStamp}
        rabbitTemplate.convertAndSend(exchangePostProfile.getName(),"routing.PostProfile",postProfileDto);
        String url = "http://localhost:9000/organisation/checkOrg/" + postDto.getUserId();
        ResponseDto responseDto = new RestTemplate().getForObject(url,ResponseDto.class );
        assert responseDto != null;
        if(responseDto.getType())
        {
            OrganizationPost organizationPost=new OrganizationPost();
            organizationPost.setUrl(postDto.getUrl());
            if(postDto.getCategory().equals("sport"))
            {
                organizationPost.setCategoryId("1");
            }

            if(postDto.getCategory().equals("lifestyle")){
                organizationPost.setCategoryId("2");

            }

            if(postDto.getCategory().equals("e-commercial")){
                organizationPost.setCategoryId("3");

            }
            if(postDto.getCategory().equals("education")){
                organizationPost.setCategoryId("4");

            }
            if(postDto.getCategory().equals("Cinematic")){
                organizationPost.setCategoryId("5");
            }
            else
                organizationPost.setCategoryId("2");


            organizationPost.setCategoryId(postDto.getCategory());
            organizationPost.setOrganizationName(responseDto.getName());
            postService.sendDataToCommon(organizationPost);
        }

    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){

        Post post = postService.getPostById(id);
        if(post!=null) {
            PostProfileDto postProfileDto = new PostProfileDto();
            postProfileDto.setPostId(id);
            postProfileDto.setUserEmail(post.getUserId());
            rabbitTemplate.convertAndSend(getExchangePostProfileDelete.getName(),"routing.PostProfileDelete",postProfileDto);
            postService.deletePostById(id);
        }
    }

    @GetMapping("/getPostsByUserId/{id}")
    public List<PostDto> getPostsForUser(@PathVariable String id){
        List<Post>  posts = postService.findPostsByUserId(id);
        List<PostDto> postDtos = new ArrayList<>();

        for(Post post : posts){
            PostDto postDto = new PostDto();
            BeanUtils.copyProperties(post,postDto);
            postDtos.add(postDto);
        }
        Collections.reverse(postDtos);
        return postDtos;
    }

    @GetMapping("/getNumberOfPosts/{id}")
    public Long getNoOfPosts(@PathVariable String id){
        return postService.getPostCount(id);
    }
}