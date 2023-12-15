package by.sapra.newsservice.web.v1.annotations;

import by.sapra.newsservice.models.errors.CategoryError;
import by.sapra.newsservice.web.v1.models.PaginationErrorResponse;
import by.sapra.newsservice.web.v1.models.UserListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Operation(
        method = "GET",
        summary = "Get user by ID.",
        description = "Get user by ID. Return basic information about user.",
        tags = {"user", "V1"}
)
@ApiResponse(
        responseCode = "200",
        content = @Content(schema = @Schema(implementation = UserListResponse.class))
)
@ApiResponse(
        responseCode = "404",
        description = "User id not found.",
        content = @Content(
                schema = @Schema(implementation = CategoryError.class),
                examples = {@ExampleObject(value = "{\n\"message\": \"Пользователь по ID 3 не найден!\"\n}")})
)
@ApiResponse(
        responseCode = "400",
        description = "User ID should be positive.",
        content = @Content(
                schema = @Schema(implementation = PaginationErrorResponse.class),
                examples = {@ExampleObject(value = "{\n\"message\": \"Параметр ID должен быть положительным!\"\n}")})
)
public @interface FindUserByIdDock {
}
