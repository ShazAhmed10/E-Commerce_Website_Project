package dev.shaz.productservice.controllers;

import dev.shaz.productservice.dtos.ProductListRequestDto;
import dev.shaz.productservice.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("category")
public class CategoryController {
    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @GetMapping("{uuid}")
    public void getCategory(@PathVariable("uuid") String uuid){
        categoryService.getCategory(uuid);
    }

    @GetMapping
    public void getProductList(@RequestBody ProductListRequestDto request){
        categoryService.getProductList(request.getUuids());
    }
}
