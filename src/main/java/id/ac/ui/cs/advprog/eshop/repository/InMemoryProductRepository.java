package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryProductRepository implements ProductRepository {
    private final List<Product> productData = new ArrayList<>();

    @Override
    public Product create(Product product) {
        productData.add(product);
        return product;
    }

    @Override
    public List<Product> findAll() {
        return productData;
    }

    @Override
    public Optional<Product> findById(String id) {
        return productData.stream().filter(product -> product.getId().equals(id)).findFirst();
    }

    @Override
    public void update(Product updatedProduct) {
        Product product = findById(updatedProduct.getId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + updatedProduct.getId()));
        product.setName(updatedProduct.getName());
        product.setQuantity(updatedProduct.getQuantity());
    }

    @Override
    public void deleteById(String id) {
        productData.removeIf(product -> product.getId().equals(id));
    }
}