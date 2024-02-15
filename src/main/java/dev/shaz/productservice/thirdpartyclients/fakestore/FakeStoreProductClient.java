package dev.shaz.productservice.thirdpartyclients.fakestore;

import dev.shaz.productservice.thirdpartyclients.fakestore.dtos.FakeStoreProductDto;
import dev.shaz.productservice.dtos.GenericProductDto;
import dev.shaz.productservice.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class FakeStoreProductClient {
    @Value("${fakestore.api.baseurl}")
    private String fakeStoreApiBaseUrl;
    @Value("${fakestore.api.product}")
    private String fakeStoreApiProductPath;
    private final String productPath = "/products";
    private String productUrl = "https://fakestoreapi.com/products/{id}";
    private String productRequestUrl = "https://fakestoreapi.com/products/";
    RestTemplateBuilder restTemplateBuilder;

    @Autowired
    public FakeStoreProductClient(RestTemplateBuilder restTemplateBuilder, @Value("fakestore.api.baseurl") String fakeStoreApiProductPath, @Value("fakestore.api.baseurl") String fakeStoreApiBaseUrl){
        this.restTemplateBuilder = restTemplateBuilder;
        this.productUrl = fakeStoreApiBaseUrl + productPath + "/{id}";
        this.productRequestUrl = fakeStoreApiBaseUrl + fakeStoreApiProductPath;
    }

    public FakeStoreProductDto getProductById(Long id) throws NotFoundException {
        RestTemplate restTemplate = restTemplateBuilder.build();

        ResponseEntity<FakeStoreProductDto> response = restTemplate.getForEntity(productUrl, FakeStoreProductDto.class, id);

        FakeStoreProductDto fakeStoreProductDto = response.getBody();

        if(fakeStoreProductDto == null) {
            throw new NotFoundException("Product with id: "+ id +" not found");
        }

        return fakeStoreProductDto;
    }

    public FakeStoreProductDto createProduct(GenericProductDto product) {
        RestTemplate restTemplate = restTemplateBuilder.build();

        ResponseEntity<FakeStoreProductDto> response = restTemplate.postForEntity(productRequestUrl, product, FakeStoreProductDto.class);

        FakeStoreProductDto fakeStoreProductDto = response.getBody();

        return fakeStoreProductDto;
    }

    public List<FakeStoreProductDto> getAllProducts() {
        RestTemplate restTemplate = restTemplateBuilder.build();

        ResponseEntity<FakeStoreProductDto[]> response = restTemplate.getForEntity(productRequestUrl, FakeStoreProductDto[].class);

        FakeStoreProductDto[] fakeStoreProductDtos = response.getBody();

        return Arrays.asList(fakeStoreProductDtos);
    }

    public FakeStoreProductDto deleteProduct(Long id) {
        RestTemplate restTemplate = restTemplateBuilder.build();

        ResponseEntity<FakeStoreProductDto> response = restTemplate.exchange(productUrl, HttpMethod.DELETE, null, FakeStoreProductDto.class, id);

        FakeStoreProductDto fakeStoreProductDto = response.getBody();

        return fakeStoreProductDto;
    }
}
