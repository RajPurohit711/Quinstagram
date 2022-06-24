package com.quinstagram.postService.repository;

import com.quinstagram.postService.entity.Reaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactionRepository extends MongoRepository<Reaction,String> {

    @Query(value = "{'reactionType' : ?0, 'postId': ?1 }",count = true)
    Long getNoOfLikes(boolean flag, Long postId);

    Reaction findByPostIdAndReactionBy(Long postId,String reactionBy);

}
