package com.quinstagram.postService.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Reaction {

    @Id
    private String id;
    private Long postId;
    private boolean reactionType; // True Positive False Negative
    private String reactionBy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public boolean isReactionType() {
        return reactionType;
    }

    public void setReactionType(boolean reactionType) {
        this.reactionType = reactionType;
    }

    public String getReactionBy() {
        return reactionBy;
    }

    public void setReactionBy(String reactionBy) {
        this.reactionBy = reactionBy;
    }
}
