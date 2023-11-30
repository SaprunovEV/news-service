package by.sapra.newsservice.web.v1.controllers;

import by.sapra.newsservice.models.errors.CategoryNotFound;
import by.sapra.newsservice.services.CategoryService;
import by.sapra.newsservice.services.models.ApplicationModel;
import by.sapra.newsservice.services.models.CategoryFilter;
import by.sapra.newsservice.web.v1.mappers.CategoryMapper;
import by.sapra.newsservice.web.v1.models.CategoryListResponse;
import by.sapra.newsservice.web.v1.models.CategoryResponse;
import by.sapra.newsservice.web.v1.models.PaginationErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Tag(name = "Category V1", description = "Category API version V1")
public class CategoryController {
    private final CategoryService service;
    private final CategoryMapper mapper;

    @GetMapping
    @Operation(
            summary = "Get all categories.",
            description = "Get all categories. Return list of categories without news.",
            method = "GET",
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
            tags = {"category", "V1"}
    )
    @ApiResponse(
            responseCode = "200",
            content = @Content(schema = @Schema(implementation = CategoryListResponse.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Error of pagination.",
            content = @Content(
                    schema = @Schema(implementation = PaginationErrorResponse.class),
                    examples = {@ExampleObject(value = "{\n\"message\": \"Номер страницы должен быть заполнен!\"\n}")})
    )
    public ResponseEntity<?> handleFindAll(@Valid CategoryFilter filter) {
        return ResponseEntity.ok(mapper.categoryItemListToCategoryListResponse(service.findAll(filter)));
    }

    @GetMapping("/{id}")
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
                    schema = @Schema(implementation = CategoryNotFound.class),
                    examples = {@ExampleObject(value = "{\n\"message\": \"Категория по ID 3 не найдена!\"\n}")})
    )
    public ResponseEntity<?> handleFindById(@PathVariable(name = "id") Long id) {
        ApplicationModel<CategoryWithNews, CategoryNotFound> model = service.findById(id);

        if (model.hasError()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(model.getError());
        }
        return ResponseEntity.ok(mapper.categoryToCategoryResponse(model.getData()));
    }
}
