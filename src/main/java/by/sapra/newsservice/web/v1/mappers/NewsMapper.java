package by.sapra.newsservice.web.v1.mappers;

import by.sapra.newsservice.services.models.News;
import by.sapra.newsservice.web.v1.models.NewsListResponse;

import java.util.List;

public interface NewsMapper {
    NewsListResponse newsListToNewsListResponse(List<News> newsList);
}
