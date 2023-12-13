package by.sapra.newsservice.web.v1.annotations;

import by.sapra.newsservice.models.errors.CategoryError;
import by.sapra.newsservice.web.v1.models.CategoryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = "Saved new category.",
        description = "Saved new category. Return saved category.",
        method = "POST",
        tags = {"category", "V1"}
)
@ApiResponse(
        responseCode = "201",
        content = @Content(schema = @Schema(implementation = CategoryResponse.class))
)
@ApiResponse(
        responseCode = "400",
        description = "Category already exist.",
        content = @Content(
                schema = @Schema(implementation = CategoryError.class),
                examples = {@ExampleObject(value = "{\n\"message\": \"Категория с именем бизнес уже существует!\"\n}")})
)
@ApiResponse(
        responseCode = "406",
        description = "Category validation error.",
        content = @Content(
                schema = @Schema(implementation = CategoryError.class),
                examples = {@ExampleObject(value = "{\n\"message\": \"Имя категории должно быть между 5 и 50 символами\"\n}")})
)
public @interface CreateNewCategoryDock {
}
