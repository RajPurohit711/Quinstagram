package com.quinstagram.postService.service.impl;

import com.quinstagram.postService.common.OrganizationPost;
import com.quinstagram.postService.entity.Post;
import com.quinstagram.postService.repository.PostRepository;
import com.quinstagram.postService.service.PostService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    KafkaTemplate kafkaTemplate;

    @Override
    public Post getPostById(Long id) {
        return postRepository.findById(id).get();
    }

    @Override
    public void deletePostById(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public Post save(Post post) {
       return postRepository.save(post);
    }

    @Override
    public List<Post> findPostsByUserId(String id) {
       return postRepository.findByUserId(id);
    }

    @Override
    public Long getPostCount(String id) {
        return postRepository.countByUserId(id);
    }

    @Override
    public void sendDataToCommon(OrganizationPost organizationPost) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("category",organizationPost.getCategoryId());
        jsonObject.put("organizationName",organizationPost.getOrganizationName());
        jsonObject.put("url",organizationPost.getUrl());
//        kafkaTemplate.send()
        jsonObject.put("appId","2");
        kafkaTemplate.send("kishan",jsonObject);
    }


}
