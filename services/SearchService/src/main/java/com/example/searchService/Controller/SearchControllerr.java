package com.example.searchService.Controller;

import com.example.searchService.builder.SearchQueryBuilder;
import com.example.searchService.dto.ProductModelDto;
import com.example.searchService.service.ElasticSearchService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/search")
@Component
public class SearchControllerr {



    @Autowired
    private SearchQueryBuilder searchQueryBuilder;

    @Autowired
    ElasticSearchService elasticSearchService;


    // todo : phani : move this listner code to a different service
    @RabbitListener(queues = "queue.ProductSearch")
    public void receiveLoginEmail(ProductModelDto productModelDto){
        elasticSearchService.saveOne(productModelDto);
    }

    @GetMapping(value = "/manual/{text}")
    public List<ProductModelDto> get(@PathVariable final String text){

        return searchQueryBuilder.get(text);
    }


    @GetMapping()
    public List<ProductModelDto> get(){

        Iterable<ProductModelDto> iterable = elasticSearchService.findAll();

        List<ProductModelDto> productModelDtos = new ArrayList<>();
        iterable.forEach(productModelDtos::add);

        return productModelDtos;
    }

    @GetMapping("/load")
    public void load(){
            String url = "http://localhost:9000/product/";

        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();
        List<ProductModelDto> productModelDtos;
        try
        {
            String productMerchantDtoListString = restTemplate.exchange(url,HttpMethod.GET, null,String.class).getBody();
            productModelDtos = Arrays.asList(objectMapper.readValue(productMerchantDtoListString,ProductModelDto[].class));
            elasticSearchService.save(productModelDtos);
        }
        catch (IOException exc)
        {
            System.out.println("Something bad happened");
        }
    }

}
