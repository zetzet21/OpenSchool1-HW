package exercise.zetzet.HW1.services;

import exercise.zetzet.HW1.domain.CategoryDto;
import exercise.zetzet.HW1.entity.Category;
import exercise.zetzet.HW1.repositories.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Category getById(Long id){
        return categoryRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException(String.format("Категории с id %d не существует", id)));
    }

    public List<Category> getAll(){
        return categoryRepository.findAll();
    }
    public Category save(Category categoryForm){
        var category = new Category();
        category.setName(categoryForm.getName());
        return categoryRepository.save(category);
    }

    public void update(Long id, CategoryDto categoryDto){
        var category  = getById(id);
        category.setName(categoryDto.name());
        categoryRepository.save(category);
    }

    public void deleteById(Long id){
        categoryRepository.deleteById(id);
    }
}
