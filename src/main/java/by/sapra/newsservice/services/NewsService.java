package by.sapra.newsservice.services;

import by.sapra.newsservice.services.models.News;
import by.sapra.newsservice.services.models.filters.NewsFilter;
import by.sapra.newsservice.web.v1.models.NewsListResponse;

import java.util.List;

public interface NewsService {
    List<News> findAll(NewsFilter filter);
}
