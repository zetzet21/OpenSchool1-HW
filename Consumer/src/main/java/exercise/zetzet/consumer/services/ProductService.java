package exercise.zetzet.consumer.services;

import exercise.zetzet.consumer.models.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final RestTemplate restTemplate;
    private final String SUPPLIER_SERVICE_URL = "http://localhost:8080/api/v1/products";

    public Product createProduct(Product product) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<Product> request = new HttpEntity<>(product, headers);
        return restTemplate.postForObject(SUPPLIER_SERVICE_URL, product, Product.class);
    }
    public List<Product> getAllProducts() {

        return Arrays.asList(restTemplate.getForObject(SUPPLIER_SERVICE_URL, Product.class));
    }

//    public ProductDto getProductById(Long id) {
//        return restTemplate.getForObject(SUPPLIER_SERVICE_URL + "/" + id, ProductDto.class);
//    }
//
//
//    public void updateProduct(Long id, ProductDto productDto) {
//        restTemplate.put(SUPPLIER_SERVICE_URL + "/" + id, productDto);
//    }

    public void deleteProduct(Long id) {
        restTemplate.delete(SUPPLIER_SERVICE_URL + "/" + id);
    }


}
