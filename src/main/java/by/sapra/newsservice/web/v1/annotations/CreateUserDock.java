package by.sapra.newsservice.web.v1.annotations;

import by.sapra.newsservice.models.errors.CategoryError;
import by.sapra.newsservice.web.v1.models.UserItemResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Operation(
        method = "POST",
        summary = "Create new user.",
        description = "Create new user. Return created user.",
        tags = {"user", "V1"}
)
@ApiResponse(
        responseCode = "201",
        content = @Content(schema = @Schema(implementation = UserItemResponse.class))
)
@ApiResponse(
        responseCode = "400",
        description = "Username already exist.",
        content = @Content(
                schema = @Schema(implementation = CategoryError.class),
                examples = {@ExampleObject(value = "{\n\"message\": \"Пользователь с именем бизнес уже существует!\"\n}")})
)
@ApiResponse(
        responseCode = "406",
        description = "User validation error.",
        content = @Content(
                schema = @Schema(implementation = CategoryError.class),
                examples = {@ExampleObject(value = "{\n\"message\": \"Имя пользователя должно быть менее 50 символами\"\n}")})
)
public @interface CreateUserDock {
}
