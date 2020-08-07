package com.myretail.target.controller;

import com.myretail.target.data.ProductRepository;
import com.myretail.target.model.Product;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    ProductRepository productRepository;

    @Test
    public void indexShouldReturnProducts() throws Exception {
        String resp = this.restTemplate.getForObject("http://localhost:" + port + "/api/v1/products", String.class);
        assertThat(resp.contains("13860428"));
        assertThat(resp.contains("54456119"));
        assertThat(resp.contains("13264003"));
        assertThat(resp.contains("12954218"));

    }

    @Test
    public void updateShouldUpdateProduct() throws Exception{
        Product product = new Product(12345,200.0);
        productRepository.save(product);

        String url = "http://localhost:" + port + "/api/v1/products";

        double newPrice = new Random().nextDouble() * 10;

        product.setPrice(newPrice);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Product> entity = new HttpEntity<>(product, headers);

        ResponseEntity<Product> response = this.restTemplate.exchange(url, HttpMethod.PUT, entity, Product.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            Product updatedProduct = productRepository.findById(product.getId()).get();
            if(updatedProduct == null){
                Assertions.fail("Updated product was not found in data store");
            }
            else{
                assertThat(updatedProduct.getPrice() == newPrice);
            }
        }

        // delete test product
        productRepository.delete(product);

    }
}
