package by.sapra.newsservice.web.v1.controllers;

import by.sapra.newsservice.services.NewsService;
import by.sapra.newsservice.services.models.filters.NewsFilter;
import by.sapra.newsservice.web.v1.mappers.NewsMapper;
import by.sapra.newsservice.web.v1.models.NewsListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/news")
@RequiredArgsConstructor
@Tag(name = "news_v1", description = "News API version V1")
public class NewsController {
    private final NewsService service;
    private final NewsMapper mapper;
    @GetMapping
    @Operation(
            summary = "Get all news.",
            description = "Get all news. Return list of news without comments.",
            tags = {"news"}
    )
    @ApiResponse(
            responseCode = "200",
            content = @Content(schema = @Schema(implementation = NewsListResponse.class))
    )
    public ResponseEntity<NewsListResponse> findAll(NewsFilter filter) {
        return ResponseEntity.ok(
                mapper.newsListToNewsListResponse(service.findAll(filter))
        );
    }
}
