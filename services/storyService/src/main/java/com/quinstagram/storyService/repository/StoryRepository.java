package com.quinstagram.storyService.repository;

import com.quinstagram.storyService.entity.Story;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoryRepository extends CrudRepository<Story,Long> {

    List<Story> findByUserId(String userId);
}
