package com.quinstagram.storyService.service;

import com.quinstagram.storyService.entity.Story;

import java.util.List;
import java.util.Optional;

public interface StoryService {
    Story get(Long id);
    void delete(Long id);
    Story save (Story story);

    List<Story> getForUser(String id);
}
