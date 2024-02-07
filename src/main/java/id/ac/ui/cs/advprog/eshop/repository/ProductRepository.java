package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

@Repository
public class ProductRepository {
    private final List<Product> productData = new ArrayList<>();
    private final Map<String, Product> productMap = new HashMap<>();

    public Product create(Product product) {
        product.setProductId(UUID.randomUUID().toString());
        productData.add(product);
        productMap.put(product.getProductId(), product);
        return product;
    }

    public Product get(String productId) {
        return productMap.get(productId);
    }

    // change oldProduct to match newProduct
    public void edit(Product newProduct) {
        Product oldProduct = productMap.get(newProduct.getProductId());
        oldProduct.setProductQuantity(newProduct.getProductQuantity());
        oldProduct.setProductName(newProduct.getProductName());
    }

    public Iterator<Product> findAll() {
        return productData.iterator();
    }
}
