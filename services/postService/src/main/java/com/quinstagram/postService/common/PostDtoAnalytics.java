package com.quinstagram.postService.common;

import java.io.Serializable;
import java.sql.Timestamp;

public class PostDtoAnalytics implements Serializable {

    private String postId;
    private  String userId;
    private String postName;
    private String postType;
    private String reactionType;
    private String contentType;
    private Timestamp reactionTime;

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public String getReactionType() {
        return reactionType;
    }

    public void setReactionType(String reactionType) {
        this.reactionType = reactionType;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Timestamp getReactionTime() {
        return reactionTime;
    }

    public void setReactionTime(Timestamp reactionTime) {
        this.reactionTime = reactionTime;
    }
}
