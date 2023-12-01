package by.sapra.newsservice.web.v1.controllers;

import by.sapra.newsservice.models.errors.CategoryError;
import by.sapra.newsservice.services.CategoryService;
import by.sapra.newsservice.services.models.ApplicationModel;
import by.sapra.newsservice.services.models.CategoryFilter;
import by.sapra.newsservice.web.v1.mappers.CategoryMapper;
import by.sapra.newsservice.web.v1.models.*;
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
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

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
    public ResponseEntity<?> handleFindById(@Valid CategoryId id) {
        ApplicationModel<CategoryWithNews, CategoryError> model = service.findById(id.getId());

        if (model.hasError()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(model.getError());
        }
        return ResponseEntity.ok(mapper.categoryToCategoryResponse(model.getData()));
    }

    @PostMapping
    @Operation(
            summary = "Saved new category.",
            description = "Saved new category. Return saved category.",
            method = "POST",
            tags = {"category", "V1"}
    )
    @ApiResponse(
            responseCode = "200",
            content = @Content(schema = @Schema(implementation = CategoryResponse.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Category already exist.",
            content = @Content(
                    schema = @Schema(implementation = CategoryError.class),
                    examples = {@ExampleObject(value = "{\n\"message\": \"Категория с именем бизнес уже существует!\"\n}")})
    )
    public ResponseEntity<?> handleSaveCategory(@RequestBody UpsertCategoryRequest request) {
        ApplicationModel<CategoryWithNews, CategoryError> model =
                service.saveCategory(mapper.requestToCategoryWithNews(request));

        if (model.hasError())
            return ResponseEntity.badRequest().body(model.getError());

        return ResponseEntity.status(CREATED).body(mapper.categoryToCategoryResponse(model.getData()));
    }
}
