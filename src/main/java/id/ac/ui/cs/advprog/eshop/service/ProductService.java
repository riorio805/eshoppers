package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import java.util.List;

public interface ProductService {
    Product create(Product product);
    Product get(String productId);
    void update(Product newProduct);
    void delete(Product product);
    List<Product> findAll();
}
