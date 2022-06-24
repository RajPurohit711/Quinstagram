package com.quinstagram.storyService.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    private static final String ROUTING_STORY_PROFILE ="routing.StoryProfile";
    @Bean
    Queue queueStoryProfile(){
        return new Queue("queue.StoryProfile",false);
    }

    @Bean
    Binding bindingStoryProfile(Queue queueStoryProfile, DirectExchange exchangeStoryProfile){
        return BindingBuilder.bind(queueStoryProfile).to(exchangeStoryProfile).with(ROUTING_STORY_PROFILE);
    }
    @Bean
    DirectExchange exchangeStoryProfile(){
        return new DirectExchange("direct.exchangeStoryProfile");
    }

    @Bean
    MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory factory){
        RabbitTemplate rabbitTemplate=new RabbitTemplate(factory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}
