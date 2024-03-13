package exercise.zetzet.consumer.models;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    @Null(message = "Поле id для продукта не должно быть заполнено")
    private Long id;

    @NotBlank(message = "Имя является обязательным полем")
    private String name;

    @NotBlank(message = "Описание является обязательным полем")
    private String description;

    @NotNull
    @DecimalMin(value = "0.0", message = "Цена не может быть равна нулю и должна быть больше")
    @DecimalMax(value = "1000000.0", message = "Цена не может превыщать 1000000")
    private Double price;

    @Valid
    private Long categoryId;
}
