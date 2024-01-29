package dev.shaz.productservice.dtos;

import dev.shaz.productservice.models.Category;
import dev.shaz.productservice.models.Price;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class GenericProductDto {
    private Long id;
    private String title;
    private String description;
    private String image;
    private String category;
    private Double price;
    private int inventoryCount;
}
