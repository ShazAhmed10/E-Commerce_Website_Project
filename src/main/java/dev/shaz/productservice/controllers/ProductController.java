package dev.shaz.productservice.controllers;

import dev.shaz.productservice.dtos.ExceptionDto;
import dev.shaz.productservice.dtos.GenericProductDto;
import dev.shaz.productservice.exceptions.NotFoundException;
import dev.shaz.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;

    public ProductController(@Qualifier("selfProductService") ProductService productService){
        this.productService = productService;
    }

    @GetMapping("{id}")
    public GenericProductDto getProductById(@PathVariable("id") String id) throws NotFoundException {
        return productService.getProductById(id);
    }

    @GetMapping()
    public List<GenericProductDto> getAllProducts(){
        return productService.getAllProducts();
    }

    @PostMapping()
    public GenericProductDto createProduct(@RequestBody GenericProductDto genericProductDto){
        return productService.createProduct(genericProductDto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<GenericProductDto> deleteProduct(@PathVariable("id") String id) throws NotFoundException{
        return new ResponseEntity<>(productService.deleteProduct(id), HttpStatus.OK);
    }

    @PutMapping("updateProduct/{id}")
    public ResponseEntity<GenericProductDto> updateProductById(@PathVariable("id") String id, @RequestBody GenericProductDto genericProductDto) throws NotFoundException{
        return new ResponseEntity<>(productService.updateProductById(id, genericProductDto), HttpStatus.OK);
    }

//    The below exception Handler is specific to only this controller
//    @ExceptionHandler(NotFoundException.class)
//    public ResponseEntity<ExceptionDto> handleNotFoundException(NotFoundException notFoundException){
//        return new ResponseEntity<>(new ExceptionDto(HttpStatus.NOT_FOUND, notFoundException.getMessage()), HttpStatus.NOT_FOUND);
//    }
}
