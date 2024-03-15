package exercise.zetzet.HW1.controllers;

import exercise.zetzet.HW1.domain.CategoryDto;
import exercise.zetzet.HW1.entity.Category;
import exercise.zetzet.HW1.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping()
    public ResponseEntity<Category> save(@RequestBody Category category){
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(categoryService.save(category));
    }

    @GetMapping()
    public ResponseEntity<List<Category>> getAll(){
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(categoryService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getById(@PathVariable Long id){
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(categoryService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody CategoryDto categoryDto) {
        categoryService.update(id, categoryDto);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body("Категория " + categoryService.getById(id).getName() + " успешно обновлена");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        var name = categoryService.getById(id).getName();
        categoryService.deleteById(id);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body("Категория " + name + " успешно удалена");
    }
}
