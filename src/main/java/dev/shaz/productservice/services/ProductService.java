package dev.shaz.productservice.services;

import dev.shaz.productservice.dtos.GenericProductDto;
import dev.shaz.productservice.exceptions.NotFoundException;

import java.util.List;

public interface ProductService {

    public GenericProductDto getProductById(Long Id) throws NotFoundException;
    public GenericProductDto createProduct(GenericProductDto genericProductDto);
    public List<GenericProductDto> getAllProducts();
    public GenericProductDto deleteProduct(Long id);
}
