package exercise.zetzet.consumer.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Category {

    @Null(message = "Поле id для категорий не должно быть заполнено")
    private Long id;

    @NotBlank(message = "Название обязательно для категории")
    private String name;
}
