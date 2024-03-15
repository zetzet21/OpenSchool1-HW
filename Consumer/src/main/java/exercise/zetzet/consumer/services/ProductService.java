package exercise.zetzet.consumer.services;

import exercise.zetzet.consumer.models.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    @Autowired
    private RestTemplate restTemplate;
    private final String SUPPLIER_SERVICE_URL = "http://localhost:8080/api/v1/products";

    public Product createProduct(Product product) {
        return restTemplate.postForObject(SUPPLIER_SERVICE_URL, product, Product.class);
    }

    public List<Product> getAllProducts() {
        return Arrays.asList(restTemplate.getForObject(SUPPLIER_SERVICE_URL, Product.class));
    }

    public void deleteProduct(Long id) {
        restTemplate.delete(SUPPLIER_SERVICE_URL + "/" + id);
    }


}
