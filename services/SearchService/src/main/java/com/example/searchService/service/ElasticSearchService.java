package com.example.searchService.service;

import com.example.searchService.dto.ProductModelDto;

import java.util.List;

public interface ElasticSearchService {
    void save(List<ProductModelDto> productModelDtos);
    void saveOne(ProductModelDto productModelDto);
    Iterable<ProductModelDto> findAll();
}
