package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
public class ProductControllerTest {
    @Autowired
    private MockMvc mvc;

    Product testProduct1, testProduct2;

    @BeforeEach
    void setUp() {
        this.testProduct1 = new Product();
        this.testProduct1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        this.testProduct1.setProductName("Sampo Cap Bambang");
        this.testProduct1.setProductQuantity(100);

        this.testProduct2 = new Product();
        this.testProduct2.setProductId("602c3686-9b97-45b4-9349-f8450a6f1d46");
        this.testProduct2.setProductName("STelle Dobi's pre-built PC");
        this.testProduct2.setProductQuantity(10);
    }

    @Test
    void testCreateProductPage() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/product/create"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("CreateProduct"))
            .andExpect(MockMvcResultMatchers.model().attributeExists("product"));
    }

    @Test
    void testCreateProductPost() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.post("/product/create")
                    .flashAttr("product", testProduct1)
            )
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
            .andExpect(MockMvcResultMatchers.redirectedUrl("list"));
    }

    @Test
    void testGetProductList() throws Exception {
        mvc.perform(
            MockMvcRequestBuilders.post("/product/create")
                    .flashAttr("product", testProduct1)
        );

        mvc.perform(
                MockMvcRequestBuilders.get("/product/list")
            )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("ProductList"))
            .andExpect(MockMvcResultMatchers.model().attributeExists("products"))
            .andExpect(MockMvcResultMatchers.model().attribute("products", not(empty())));
    }

    @Test
    void testEditProductPage() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.post("/product/create")
                        .flashAttr("product", testProduct1)
        );

        // ensure no change
        mvc.perform(
                MockMvcRequestBuilders.get("/product/edit")
                        .param("id", testProduct1.getProductId())
            )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("EditProduct"))
            .andExpect(MockMvcResultMatchers.model().attributeExists("product"))
            .andExpect(MockMvcResultMatchers.model().attribute("product",
                    hasProperty("productId", equalTo(testProduct1.getProductId()))))
            .andExpect(MockMvcResultMatchers.model().attribute("product",
                    hasProperty("productName", equalTo(testProduct1.getProductName()))))
            ;
    }

    @Test
    void testEditProductPost() throws Exception {
        Product savedProduct = new Product();
        savedProduct.setProductId(testProduct1.getProductId());
        savedProduct.setProductName(testProduct1.getProductName());
        savedProduct.setProductQuantity(testProduct1.getProductQuantity());

        mvc.perform(
                MockMvcRequestBuilders.post("/product/create")
                        .flashAttr("product", testProduct1)
        );

        Product changedProduct = new Product();
        changedProduct.setProductId(testProduct1.getProductId());
        changedProduct.setProductName("Whatever as long as it's different");
        changedProduct.setProductQuantity(727);

        mvc.perform(
                MockMvcRequestBuilders.post("/product/edit")
                        .flashAttr("product", changedProduct)
            )
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
            .andExpect(MockMvcResultMatchers.redirectedUrl("list"));

        mvc.perform(
                MockMvcRequestBuilders.get("/product/list")
            )
            .andExpect(MockMvcResultMatchers.model().attributeExists("products"))
            .andExpect(MockMvcResultMatchers.model().attribute("products",
                    everyItem(hasProperty("productName",
                    not(equalTo(savedProduct.getProductName()))))))
            .andExpect(MockMvcResultMatchers.model().attribute("products",
                    everyItem(hasProperty("productQuantity",
                    not(equalTo(savedProduct.getProductQuantity()))))));
    }

    @Test
    void testDeleteProduct() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.post("/product/create")
                        .flashAttr("product", testProduct1)
            );
        mvc.perform(
                MockMvcRequestBuilders.post("/product/create")
                        .flashAttr("product", testProduct2)
            );


        mvc.perform(
                MockMvcRequestBuilders.get("/product/delete")
                        .param("id", testProduct1.getProductId())
        );

        mvc.perform(
                MockMvcRequestBuilders.get("/product/list")
            )
            .andExpect(MockMvcResultMatchers.model().attributeExists("products"))
            .andExpect(MockMvcResultMatchers.model().attribute("products",
                    everyItem(hasProperty("productName",
                    not(equalTo(testProduct1.getProductName()))))))
            .andExpect(MockMvcResultMatchers.model().attribute("products",
                    everyItem(hasProperty("productQuantity",
                    not(equalTo(testProduct1.getProductQuantity()))))));
    }
}
