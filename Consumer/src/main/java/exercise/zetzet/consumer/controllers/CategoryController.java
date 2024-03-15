package exercise.zetzet.consumer.controllers;

import exercise.zetzet.consumer.models.Category;
import exercise.zetzet.consumer.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/consumer/category")
@Slf4j
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping()
    public List<Category> getAllCategories() {
        var categories = categoryService.getAllCategories();
        log.info("Всего категорий: {}", categories.size());
        return categories;
    }

    @PostMapping()
    public Category createCategory(@Valid @RequestBody Category category) {
        return categoryService.createCategory(category);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@Valid @PathVariable Long id) {
        categoryService.deleteCategory(id);
    }

    @ExceptionHandler
    public ResponseEntity<String> handle(Exception exception) {
        if(exception instanceof SQLException){
            return ResponseEntity.status(HttpStatusCode.valueOf(401)).body("  ");
        }
        return ResponseEntity.ok().build();
    }
}
