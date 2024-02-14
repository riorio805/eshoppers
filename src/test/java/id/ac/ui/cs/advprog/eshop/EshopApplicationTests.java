package id.ac.ui.cs.advprog.eshop;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EshopApplicationTests {
    // Test added ONLY to cover main() invocation not covered by application tests.
    @Test
    void contextLoads() {
        EshopApplication.main(new String[] {});
    }
}
