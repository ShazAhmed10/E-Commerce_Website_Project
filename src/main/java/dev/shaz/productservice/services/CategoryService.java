package dev.shaz.productservice.services;

import dev.shaz.productservice.models.Category;
import dev.shaz.productservice.models.Product;
import dev.shaz.productservice.repositories.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public void getCategory(String uuid){
        Optional<Category> optionalCategory = categoryRepository.findById(UUID.fromString(uuid));

        Category category = optionalCategory.get();
        System.out.println(category);

        List<Product> products = category.getProduct();
        System.out.println(products);
    }

    public void getProductList(List<String> uuids){
        List<UUID> uuidList = new ArrayList<>();
        for(String id : uuids){
            uuidList.add(UUID.fromString(id));
        }

        List<Category> categories = categoryRepository.findAllById(uuidList);
        for(Category category : categories){
            for(Product product : category.getProduct()){
                System.out.println(product.getTitle());
            }
        }
    }
}
