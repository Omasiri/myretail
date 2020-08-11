package com.myretail.target.controller;

import com.myretail.target.data.ProductRepository;
import com.myretail.target.dto.CurrentPriceDto;
import com.myretail.target.dto.DtoUtils;
import com.myretail.target.dto.ProductDto;
import com.myretail.target.exceptions.ResourceNotFoundException;
import com.myretail.target.model.Product;
import com.myretail.target.services.SequenceGeneratorService;
import com.myretail.target.services.TargetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@PropertySource("classpath:application.properties")
public class ProductController {
    final DtoUtils utils = new DtoUtils();
    @Value("${default.currencyCode}")
    private String currencyCode;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    TargetService targetService;

    @GetMapping("/products")
    public List<ProductDto> index(){
        List<Product> products = productRepository.findAll();

        List<ProductDto> productDtos = new ArrayList<>();
        for(Product product:products){
            String title = targetService.getTitle(product.getId());
            // when a product doesn't return a title, it probably doesn't exist at the endpoint so we ignore it
            if(title != null){
                ProductDto productDto = utils.getProductDto(product,title,currencyCode);
                productDtos.add(productDto);
            }

        }

        return productDtos;
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDto> show(@PathVariable(value = "id") Long productId) throws ResourceNotFoundException {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Resource not found for id "+ productId));
        String title = targetService.getTitle(productId);
        ProductDto productDto = utils.getProductDto(product, title,currencyCode);

        return ResponseEntity.ok().body(productDto);
    }

    @PostMapping("/products")
    public Product save(@RequestBody Product product){
        return productRepository.save(product);
    }


    @PutMapping("/products/{id}")
    public ResponseEntity < Product > update(@PathVariable(value = "id") Long productId,
                                                      @RequestBody Product product) throws Exception {
        Product product1 = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found for this id :: " + productId));

        product1.setPrice(product.getPrice());
        final Product updatedProduct = productRepository.save(product1);
        return ResponseEntity.ok(updatedProduct);
    }


    @DeleteMapping("/products/{id}")
    public Map<String,Boolean> delete(@PathVariable(value = "id") Long productId,
                                      @RequestBody Product product) throws Exception {
        Product product1 = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found for this id :: " + productId));

        productRepository.delete(product1);
        Map < String, Boolean > response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}
