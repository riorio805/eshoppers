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
    private final Map<String, Integer> indexMap = new HashMap<>();

    public Product create(Product product) {
        product.setProductId(UUID.randomUUID().toString());
        productData.add(product);
        indexMap.put(product.getProductId(), productData.size()-1);
        return product;
    }

    public Product get(String productId) {
        int productIndex = indexMap.get(productId);
        return productData.get(productIndex);
    }

    // change oldProduct to match newProduct
    public void edit(Product newProduct) {
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
        return productData.iterator();
    }
}
