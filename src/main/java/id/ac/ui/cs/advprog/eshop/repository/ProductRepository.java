package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.Map;
import java.util.HashMap;

@Repository
public class ProductRepository {
    private final List<Product> productData = new ArrayList<>();
    private final Map<String, Integer> indexMap = new HashMap<>();

    public Product create(Product product) {
        if (product.getProductId() == null) {
            product.setProductId(UUID.randomUUID().toString());
        }
        productData.add(product);
        indexMap.put(product.getProductId(), productData.size()-1);
        return product;
    }

    public Product get(String productId) {
        int productIndex = indexMap.get(productId);
        return productData.get(productIndex);
    }

    public void update(Product newProduct) {
        String productId = newProduct.getProductId();
        int productIndex = indexMap.get(productId);
        Product oldProduct = productData.get(productIndex);

        oldProduct.setProductQuantity(newProduct.getProductQuantity());
        oldProduct.setProductName(newProduct.getProductName());
    }

    public void delete(Product product) {
        String productId = product.getProductId();
        int productIndex = indexMap.get(productId);
        productData.set(productIndex, null);
        indexMap.remove(productId);
    }

    public Iterator<Product> findAll() {
        List<Product> allProduct = new ArrayList<>();
        productData.forEach(
            e -> {
                if (e != null) { allProduct.add(e); }
            }
        );
        return allProduct.iterator();
    }
}
