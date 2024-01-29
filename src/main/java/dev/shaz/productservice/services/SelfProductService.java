package dev.shaz.productservice.services;

import dev.shaz.productservice.dtos.GenericProductDto;
import dev.shaz.productservice.exceptions.NotFoundException;
import dev.shaz.productservice.models.Category;
import dev.shaz.productservice.models.Price;
import dev.shaz.productservice.models.Product;
import dev.shaz.productservice.repositories.CategoryRepository;
import dev.shaz.productservice.repositories.PriceRepository;
import dev.shaz.productservice.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service("selfProductService")
public class SelfProductService implements ProductService{
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private PriceRepository priceRepository;

    public SelfProductService(ProductRepository productRepository,
                              CategoryRepository categoryRepository,
                              PriceRepository priceRepository){
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.priceRepository = priceRepository;
    }

    public static GenericProductDto convertProductToGenericProductDto(Product product){
        GenericProductDto genericProductDto = new GenericProductDto();

        genericProductDto.setId(Math.abs(product.getUuid().getMostSignificantBits()));
        genericProductDto.setTitle(product.getTitle());
        genericProductDto.setCategory(product.getCategory().getName());
        genericProductDto.setDescription(product.getDescription());
        genericProductDto.setPrice(product.getPrice().getPrice());
        genericProductDto.setImage(product.getImage());

        return genericProductDto;
    }

    @Override
    public GenericProductDto getProductById(String id) throws NotFoundException {
        Optional<Product> optionalProduct = productRepository.findById(UUID.fromString(id));

        if(optionalProduct.isEmpty()){
            throw new NotFoundException("The product required is not present");
        }

        Product product = optionalProduct.get();

        return SelfProductService.convertProductToGenericProductDto(product);
    }

    @Override
    public List<GenericProductDto> getAllProducts() {
        List<Product> productList = productRepository.findAllProduct();

        List<GenericProductDto> genericProductDtoList = new ArrayList<>();

        for(Product product : productList){
            genericProductDtoList.add(SelfProductService.convertProductToGenericProductDto(product));
        }

        return genericProductDtoList;
    }

    @Override
    public GenericProductDto createProduct(GenericProductDto genericProductDto) {
        Category category = new Category();
        category.setName(genericProductDto.getCategory());
        categoryRepository.save(category);

        Price price = new Price();
        price.setPrice(genericProductDto.getPrice());
        priceRepository.save(price);

        Product product = new Product();
        product.setUuid(UUID.randomUUID());
        product.setTitle(genericProductDto.getTitle());
        product.setDescription(genericProductDto.getDescription());
        product.setImage(genericProductDto.getImage());
        product.setCategory(category);
        product.setPrice(price);
        product.setInventoryCount(1);
        productRepository.save(product);

        return genericProductDto;
    }

    @Override
    public GenericProductDto deleteProduct(String id) throws NotFoundException{
        Optional<Product> optionalProduct = productRepository.findById(UUID.fromString(id));

        if(optionalProduct.isEmpty()){
            throw new NotFoundException("The product required for delete is not present");
        }

        productRepository.deleteById(UUID.fromString(id));

        Product product = optionalProduct.get();

        return SelfProductService.convertProductToGenericProductDto(product);
    }

    @Override
    public GenericProductDto updateProductById(String id, GenericProductDto genericProductDto) throws NotFoundException {
        Optional<Product> optionalProduct = productRepository.findById(UUID.fromString(id));

        if(optionalProduct.isEmpty()){
            throw new NotFoundException("The product required for update is not present");
        }

        Product product = optionalProduct.get();

        Category category = product.getCategory();
        if(genericProductDto.getCategory() != null){
            category.setName(genericProductDto.getCategory());
        }
        categoryRepository.save(category);

        Price price = product.getPrice();
        if(genericProductDto.getPrice() != null){
            price.setPrice(genericProductDto.getPrice());
        }
        priceRepository.save(price);

        if(genericProductDto.getTitle() != null){
            product.setTitle(genericProductDto.getTitle());
        }
        if(genericProductDto.getDescription() != null){
            product.setDescription(genericProductDto.getDescription());
        }
        if(genericProductDto.getImage() != null){
            product.setImage(genericProductDto.getImage());
        }
        if(genericProductDto.getCategory() != null){
            product.setCategory(category);
        }
        if(genericProductDto.getPrice() != null){
            product.setPrice(price);
        }
        if(genericProductDto.getInventoryCount() != 0){
            product.setInventoryCount(genericProductDto.getInventoryCount());
        }
        productRepository.save(product);

        return genericProductDto;
    }
}
