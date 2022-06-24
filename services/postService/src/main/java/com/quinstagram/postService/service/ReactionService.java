package com.quinstagram.postService.service;


import com.quinstagram.postService.common.DislikeDto;
import com.quinstagram.postService.dto.ReactionDto;
import com.quinstagram.postService.entity.Reaction;

import java.util.List;

public interface ReactionService {
    void add(Reaction reaction);
    void deleteById(String id);
    Long getNoOfLikes(boolean flag, Long postId);
    List<Reaction> findAll();
    void deleteAll();
    Reaction findByPostIdAndReactionBy(Long postId,String reactionBy);
    void sendAnalyticsData(ReactionDto org);

    void sendDislike(ReactionDto reactionDto);

    void sendNotificationForReaction(ReactionDto reactionDto);

    }
