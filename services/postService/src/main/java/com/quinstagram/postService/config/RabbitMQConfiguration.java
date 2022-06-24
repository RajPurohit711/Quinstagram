package com.quinstagram.postService.config;

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
import org.springframework.context.annotation.Primary;

@Configuration
public class RabbitMQConfiguration {

    private static final String ROUTING_POST_PROFILE ="routing.PostProfile";

    private static final String ROUTING_POST_PROFILE_DELETE ="routing.PostProfileDelete";
    @Bean
    Queue queuePostProfile(){
        return new Queue("queue.PostProfile",false);
    }

    @Bean
    Queue queuePostProfileDelete(){
        return new Queue("queue.PostProfileDelete",false);
    }

    @Bean
    @Primary
    DirectExchange exchangePostProfile(){
        return new DirectExchange("direct.exchangePostProfile");
    }
    @Bean
    DirectExchange exchangePostProfileDelete(){
        return new DirectExchange("direct.exchangePostProfileDelete");
    }

    @Bean
    Binding bindingPostProfile(Queue queuePostProfile, DirectExchange exchangePostProfile){
        return BindingBuilder.bind(queuePostProfile).to(exchangePostProfile).with(ROUTING_POST_PROFILE);
    }

    @Bean
    Binding bindingPostProfileDelete(Queue queuePostProfileDelete, DirectExchange exchangePostProfileDelete){
        return BindingBuilder.bind(queuePostProfileDelete).to(exchangePostProfileDelete).with(ROUTING_POST_PROFILE_DELETE);
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
