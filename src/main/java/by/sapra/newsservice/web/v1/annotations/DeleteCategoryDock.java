package by.sapra.newsservice.web.v1.annotations;

import by.sapra.newsservice.models.errors.CategoryError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = "Delete the category.",
        description = "Delete the category by id.",
        method = "DELETE",
        tags = {"category", "V1"}
)
@ApiResponse(
        responseCode = "204"
)
@ApiResponse(
        responseCode = "406",
        description = "ID validation error.",
        content = @Content(
                schema = @Schema(implementation = CategoryError.class),
                examples = {@ExampleObject(value = "{\n\"message\": \"ID должен быть > 0\"\n}")})
)
public @interface DeleteCategoryDock {
}
