package dev.shaz.productservice.repositories;

import dev.shaz.productservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    Product findByTitleAndPrice_currency(String title, String currency);

    @Query(value = "select * from product where title = :title", nativeQuery = true)
    List<Product> findByTitleSQL(String title);

    @Query(value = "select Product from Product where Product.title = :title", nativeQuery = false)
    List<Product> findByTitleHQL(String title);
}
