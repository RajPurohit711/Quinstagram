package com.quinstagram.postService.config;

import net.minidev.json.JSONObject;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import java.util.HashMap;
import java.util.Map;
@EnableKafka
@Configuration
public class ProducerConfiguration {
    @Bean
    public ProducerFactory<String, JSONObject>
    producerFactory()
    {

        Map<String, Object> config
                = new HashMap<>();
        config.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "10.177.1.156:9092");
        config.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        config.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(config);
    }



    @Bean
    public KafkaTemplate<String, JSONObject>
    kafkaTemplate()
    {
        return new KafkaTemplate<>(
                producerFactory());
    }
}