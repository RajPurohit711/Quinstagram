package com.quinstagram.storyService.service.impl;

import com.quinstagram.storyService.entity.Story;
import com.quinstagram.storyService.repository.StoryRepository;
import com.quinstagram.storyService.service.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StoryServiceImpl implements StoryService {

    @Autowired
    StoryRepository storyRepository;



    @Override
    public Story get(Long id) {
       return storyRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        storyRepository.deleteById(id);
    }

    @Override
    public Story save(Story story) {
        return storyRepository.save(story);
    }


    @Override
    public List<Story> getForUser(String id) {
        return storyRepository.
                findByUserId(id);
    }
}

