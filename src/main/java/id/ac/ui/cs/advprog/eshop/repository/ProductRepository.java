package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Product create(Product product);

    List<Product> findAll();

    Optional<Product> findById(String id);

    void update(Product product);

    void deleteById(String id);
}