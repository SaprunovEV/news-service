package by.sapra.newsservice.web.v1.controllers;

import by.sapra.newsservice.services.NewsService;
import by.sapra.newsservice.services.models.filters.NewsFilter;
import by.sapra.newsservice.web.v1.mappers.NewsMapper;
import by.sapra.newsservice.web.v1.models.NewsListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/news")
@RequiredArgsConstructor
public class NewsController {
    private final NewsService service;
    private final NewsMapper mapper;
    @GetMapping
    public ResponseEntity<NewsListResponse> findAll(NewsFilter filter) {
        return ResponseEntity.ok(
                mapper.newsListToNewsListResponse(service.findAll(filter))
        );
    }
}
