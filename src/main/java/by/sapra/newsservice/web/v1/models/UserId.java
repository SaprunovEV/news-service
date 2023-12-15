package by.sapra.newsservice.web.v1.models;

import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserId {
    @Positive(message = "Параметр ID должен быть положителен!")
    private long id;
}
