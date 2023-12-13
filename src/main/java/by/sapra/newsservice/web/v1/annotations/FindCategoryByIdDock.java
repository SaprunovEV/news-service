package by.sapra.newsservice.web.v1.annotations;

import by.sapra.newsservice.models.errors.CategoryError;
import by.sapra.newsservice.web.v1.models.CategoryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Operation(
        summary = "Get category by id.",
        description = "Get category by id. Return category with news.",
        method = "GET",
        tags = {"category", "V1"}
)
@ApiResponse(
        responseCode = "200",
        content = @Content(schema = @Schema(implementation = CategoryResponse.class))
)
@ApiResponse(
        responseCode = "404",
        description = "Category id not found.",
        content = @Content(
                schema = @Schema(implementation = CategoryError.class),
                examples = {@ExampleObject(value = "{\n\"message\": \"Категория по ID 3 не найдена!\"\n}")})
)
@ApiResponse(
        responseCode = "400",
        description = "Category id should positive.",
        content = @Content(
                schema = @Schema(implementation = CategoryError.class),
                examples = {@ExampleObject(value = "{\n\"message\": \"Параметр ID должен быть положителен!\"\n}")})
)
public @interface FindCategoryByIdDock {
}
