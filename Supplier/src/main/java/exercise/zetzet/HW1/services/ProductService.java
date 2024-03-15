package exercise.zetzet.HW1.services;

import exercise.zetzet.HW1.entity.Product;
import exercise.zetzet.HW1.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public Product getById(Long id){
        return productRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException(String.format("Продукта с id %d не существует", id)));
    }

    public List<Product> getAll(){
        return productRepository.findAll();
    }
    public Product save(Product productForm){

        var product = new Product();
        var category = categoryService.getById(productForm.getCategory().getId());
        product.setName(productForm.getName());
        product.setDescription(productForm.getDescription());
        product.setPrice(productForm.getPrice());
        product.setCategory(category);
        return productRepository.save(product);
    }

    public Product update(Long id, Product productForm){
        var product  = getById(id);
        var category = categoryService.getById(productForm.getCategory().getId());
        product.setName(productForm.getName());
        product.setDescription(productForm.getDescription());
        product.setPrice(productForm.getPrice());
        product.setCategory(category);
        return productRepository.save(product);
    }

    public void deleteById(Long id){
        productRepository.deleteById(id);
    }
}
