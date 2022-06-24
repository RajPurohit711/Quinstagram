package com.quinstagram.storyService.controller;

import com.quinstagram.storyService.dto.StoryDto;
import com.quinstagram.storyService.dto.StoryProfileDto;
import com.quinstagram.storyService.entity.Story;
import com.quinstagram.storyService.service.StoryService;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/story")
public class StoryController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private DirectExchange exchangeStoryProfile;

    @Autowired
    private StoryService storyService;

    @GetMapping("/{id}")
    StoryDto getById(@PathVariable Long id){
        // todo : check if the story is not expired before returning !!
        Story story = storyService.get(id);
//        Story story1 = storyService.get(id);
        StoryDto storyDto = new StoryDto();
        // todo : develop another method on repository to return story object, if it is not expired
        System.out.println(new Date().getTime());
        System.out.println(story.getExpiryTime());
//        if(storyDto.getExpiryTime() < new Date().getTime()){
        BeanUtils.copyProperties(story,storyDto);
        return storyDto;
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id){
        storyService.delete(id);
    }

    @PostMapping()
    void save (@RequestBody StoryDto storyDto){
        System.out.println(storyDto.getUserId());
        Story story = new Story();

        BeanUtils.copyProperties(storyDto,story);
        Long unixTime = System.currentTimeMillis()/1000L;
        Long plusTime = unixTime + (60 * 10);
        System.out.println("\n"+unixTime);
        System.out.println("\n"+plusTime);
        story.setExpiryTime(plusTime);
        Story story1 = storyService.save(story);


        StoryProfileDto storyProfileDto = new StoryProfileDto();
        storyProfileDto.setStoryId(story1.getId());
        storyProfileDto.setUserEmail(story1.getUserId());

        rabbitTemplate.convertAndSend(exchangeStoryProfile.getName(),"routing.StoryProfile",storyProfileDto);

    }

    @GetMapping("user/{id}")
    List<Story> getStories(@PathVariable String id){
        return storyService.getForUser(id);
    }
}


