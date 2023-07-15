package br.com.compra;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CompraApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testHealth() {
        assertThat(restTemplate.getForObject("http://localhost:8080/health",
                String.class)).contains("Estou funcionando!");
    }

}
