package exercise.zetzet.consumer.services;

import exercise.zetzet.consumer.models.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final RestTemplate restTemplate;
    private final String SUPPLIER_SERVICE_URL = "http://localhost:8080/api/v1/category";

    public List<Category> getAllCategories() {
        return Arrays.asList(Objects.requireNonNull(restTemplate.getForObject(SUPPLIER_SERVICE_URL, Category[].class)));
    }

    public Category getCategoryById(Long id) {
        return restTemplate.getForObject(SUPPLIER_SERVICE_URL + "/" + id, Category.class);
    }

    public Category createCategory(Category category) {
        return restTemplate.postForObject(SUPPLIER_SERVICE_URL, category, Category.class);
    }

    public void updateCategory(Long id, Category category) {
        restTemplate.put(SUPPLIER_SERVICE_URL + "/" + id, category);
    }

    public void deleteCategory(Long id) {
        restTemplate.delete(SUPPLIER_SERVICE_URL + "/" + id);
    }

}
