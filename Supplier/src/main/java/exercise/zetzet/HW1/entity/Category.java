package exercise.zetzet.HW1.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.LazyCollection;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "category")
    private List<Product> products;
}