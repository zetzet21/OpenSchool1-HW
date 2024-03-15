package exercise.zetzet.consumer.controllers;


import exercise.zetzet.consumer.models.Product;
import exercise.zetzet.consumer.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/v1/consumer/products")
@Validated
public class ProductController {

    private final ProductService productService;

    @GetMapping()
    public List<Product> getAllProducts() {
        var products = productService.getAllProducts();

        log.info("Всего продуктов: {}", products.toArray().length);


        return products;
    }

    @PostMapping()
    public Product createProduct(@Valid @RequestBody Product product) {
        return productService.createProduct(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@Valid @PathVariable Long id) {
        productService.deleteProduct(id);
    }


    private boolean filterByPrice(Product product, Double minPrice, Double maxPrice) {
        if (product.getPrice() == null) return false;
        if (minPrice == null && maxPrice == null) return true;
        if (minPrice == null) return product.getPrice() <= maxPrice;
        if (maxPrice == null) return product.getPrice() >= minPrice;
        return product.getPrice() >= minPrice && product.getPrice() <= maxPrice;
    }

    @GetMapping("/filtered")
    public ResponseEntity<Iterable<Product>> getFilteredProducts(
            @RequestParam(defaultValue = "0") Double minPrice,
            @RequestParam(defaultValue = "99999999") Double maxPrice,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "") String description,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        log.info("Request: minPrice={}, maxPrice={}, categoryId={}, nameContains={}, descriptionContains={}, page={}, size={}",
                minPrice, maxPrice, categoryId, name, description, page, size);

        List<Product> products = productService.getAllProducts();

        log.info("Количество продуктов: {}", products.toArray().length);

        List<Product> filteredProducts = products.stream()
                .filter(product -> filterByPrice(product, minPrice, maxPrice))
                .filter(product -> product.getName().startsWith(name))
                .filter(product -> product.getDescription().startsWith(description))
                .filter(product -> product.getCategoryId().equals(categoryId))
                .toList();

        log.info("Всего отфильтрованных продуктов: {}", filteredProducts.size());


        List<Product> pagedProducts = filteredProducts.stream()
                .skip((long) page * size)
                .limit(size)
                .toList();

        return ResponseEntity.ok().body(pagedProducts);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(
                error -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                });
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Map<String, String> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put(ex.getName(), "Проверьте корректность заполнения");
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Map<String, String> handleNotReadableMessage(HttpMessageNotReadableException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("message", "Проверьте, что правильно указали параметр в теле");
        return errors;
    }

    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler(ResourceAccessException.class)
    public Map<String, String> handleResourceAccessException(ResourceAccessException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("message", "Сервер недоступен. Попробуйте позже.");
        return errors;
    }

    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler(RestClientException.class)
    public Map<String, String> handleRestClientException(RestClientException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("message", "Сервер не исправен. Попробуйте позже");
        return errors;
    }
}