package exercise.zetzet.HW1.repositories;

import exercise.zetzet.HW1.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByPriceBetweenAndNameNotNull(long min, long max);

    List<Product> findAllByPriceLessThan(long price, Pageable pageable);
}