package by.sapra.newsservice.web.v1.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpsertUserRequest {
    @NotBlank(message = "Имя пользователя не должно быть пустым!")
    @Size(max = 50, message = "Имя пользователя должно быть менее {max} символами")
    private String name;
}
