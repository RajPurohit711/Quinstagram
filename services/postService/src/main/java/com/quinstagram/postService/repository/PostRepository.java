package com.quinstagram.postService.repository;

import com.quinstagram.postService.entity.Post;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends CrudRepository<Post,Long> {
    List<Post> findByUserId(String userId);

    @Query(value = "{'userId':?0}",count = true)
    Long countByUserId(String userId);
}
