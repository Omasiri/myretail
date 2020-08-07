package com.myretail.target.dto;

import com.myretail.target.model.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:application.properties")
public class DtoUtils {
//    @Value("${default.currencyCode}")
//    private String currencyCode;

    public ProductDto getProductDto(Product product,String title, String currencyCode){
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(title);
        CurrentPriceDto currentPriceDto = new CurrentPriceDto();
        currentPriceDto.setCurrency_code(currencyCode);
        currentPriceDto.setValue(product.getPrice());
        productDto.setCurrent_price(currentPriceDto);
        return productDto;
    }
}
