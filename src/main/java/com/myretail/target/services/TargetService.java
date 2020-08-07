package com.myretail.target.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@PropertySource("classpath:application.properties")
public class TargetService {
    @Value("${default.targetURL}")
    private String targetURL;

    @Value("${default.targetParams}")
    private String targetParams;


    private final RestTemplate restTemplate;

    public TargetService(RestTemplateBuilder restTemplateBuilder){
        this.restTemplate = restTemplateBuilder.build();
    }

    /**
     * Retrieves a JSON string representation of a product from target endpoint
     * @param productId
     * @return
     */
    public String getProductJSON(long productId) {
        String url = targetURL+productId+"?"+targetParams;
        return this.restTemplate.getForObject(url, String.class);
    }

    /**
     * Get a product title given the product ID
     * @param productId
     * @return
     */
    public String getTitle(long productId){
        try{
            String json = getProductJSON(productId);
            JsonParser springParser = JsonParserFactory.getJsonParser();
            Map<String, Object> map = springParser.parseMap(json);

            Map<String, Object>  prod = (Map)map.get("product");
            Map<String, Object> item = (Map)prod.get("item");
            Map<String,Object> description = (Map)item.get("product_description");
            String title = (String)description.get("title");
            return title;
        }
        catch(Exception ex){
            return null;
        }

    }
}
