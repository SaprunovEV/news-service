package by.sapra.newsservice.web.v1.controllers;

import by.sapra.newsservice.services.models.News;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CategoryWithNews {
    private long id;
    private String name;
    private List<News> news;
}
