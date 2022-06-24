package com.quinstagram.postService.service;

import com.quinstagram.postService.common.OrganizationPost;
import com.quinstagram.postService.entity.Post;

import java.util.List;

public interface PostService {
    Post getPostById(Long id);
    void deletePostById(Long id);
    Post save(Post post);
    List<Post> findPostsByUserId(String id);
    Long getPostCount(String id);

    void sendDataToCommon(OrganizationPost organizationPost);

}
