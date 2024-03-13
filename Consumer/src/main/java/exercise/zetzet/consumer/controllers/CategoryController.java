package exercise.zetzet.consumer.controllers;

import exercise.zetzet.consumer.models.Category;
import exercise.zetzet.consumer.services.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

        log.info("Всего категорий: {}", categories.toArray().length);


        return categories;
    }

    @PostMapping()
    public Category createCategory(@RequestBody Category category) {
        return categoryService.createCategory(category);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }


}
