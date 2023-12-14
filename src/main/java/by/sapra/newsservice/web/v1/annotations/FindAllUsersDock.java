package by.sapra.newsservice.web.v1.annotations;

import by.sapra.newsservice.web.v1.models.PaginationErrorResponse;
import by.sapra.newsservice.web.v1.models.UserListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Operation(
        method = "GET",
        summary = "Get all users.",
        description = "Get all users. Return basic information about users.",
        parameters = {
                @Parameter(
                        name = "pageNumber",
                        schema = @Schema(implementation = Long.class),
                        examples = {@ExampleObject(value = "0")}),
                @Parameter(
                        name = "pageSize",
                        schema = @Schema(implementation = Long.class),
                        examples = {@ExampleObject(value = "3")})
        },
        tags = {"user", "V1"}
)
@ApiResponse(
        responseCode = "200",
        content = @Content(schema = @Schema(implementation = UserListResponse.class))
)
@ApiResponse(
        responseCode = "400",
        description = "Error of pagination.",
        content = @Content(
                schema = @Schema(implementation = PaginationErrorResponse.class),
                examples = {@ExampleObject(value = "{\n\"message\": \"Номер страницы должен быть заполнен!\"\n}")})
)
public @interface FindAllUsersDock {
}
