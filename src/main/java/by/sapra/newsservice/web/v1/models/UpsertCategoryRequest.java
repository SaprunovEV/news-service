package by.sapra.newsservice.web.v1.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpsertCategoryRequest {
    @NotBlank(message = "Категория не должна быть пуста!")
    @Size(min = 5, max = 50, message = "Имя категории должно быть между {min} и {max} символами")
    private String name;
}
