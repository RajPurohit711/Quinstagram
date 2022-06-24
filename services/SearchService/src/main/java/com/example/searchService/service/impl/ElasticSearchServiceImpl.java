package com.example.searchService.service.impl;

import com.example.searchService.dto.ProductModelDto;
import com.example.searchService.repository.ProductRepository;
import com.example.searchService.service.ElasticSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElasticSearchServiceImpl implements ElasticSearchService {


    @Autowired
    ProductRepository productRepository;

    @Override
    public void save(List<ProductModelDto> productModelDtos) {
        productRepository.save(productModelDtos);
    }

    @Override
    public void saveOne(ProductModelDto productModelDto) {
        productRepository.save(productModelDto);
    }

    @Override
    public  Iterable<ProductModelDto> findAll() {
       return productRepository.findAll();
    }
}
