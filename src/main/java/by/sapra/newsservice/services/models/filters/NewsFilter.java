package by.sapra.newsservice.services.models.filters;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class NewsFilter {
    @NotNull(message = "Размер страницы должен быть заполнен!")
    @Positive(message = "Размер страницы должен быть положителен!")
    private Integer pageSize;
    @NotNull(message = "Номер страницы должен быть заполнен!")
    @PositiveOrZero(message = "Номер страницы должен быть положителен!")
    private Integer pageNumber;
}
