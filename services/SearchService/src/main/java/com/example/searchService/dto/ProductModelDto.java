package com.example.searchService.dto;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Comparator;
import java.util.List;

@Document(indexName = "products", type = "products", shards = 1)
public class ProductModelDto {


        @Id
        private String id;
        private String name;

        private boolean allowed;
        private String description;
        private String category;

        private List<MerchantProductDto> merchantProducts;

        public void  addMerchantProduct(MerchantProductDto merchantProductDto){
            merchantProducts.add(merchantProductDto);
        }

        public void sortMerchantProduct(){
            merchantProducts.sort(Comparator.comparing(MerchantProductDto::getPrice));
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isAllowed() {
            return allowed;
        }

        public void setAllowed(boolean allowed) {
            this.allowed = allowed;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }


        public List<MerchantProductDto> getMerchantProducts() {
            return merchantProducts;
        }

        public void setMerchantProducts(List<MerchantProductDto> merchantProducts) {
            this.merchantProducts = merchantProducts;
        }

    public ProductModelDto( ) {
    }

    public ProductModelDto(String name, boolean allowed, String description, String category, List<MerchantProductDto> merchantProducts) {
        this.name = name;
        this.allowed = allowed;
        this.description = description;
        this.category = category;
        this.merchantProducts = merchantProducts;
    }
}


