package by.sapra.newsservice.web.v1.mappers;

import by.sapra.newsservice.services.models.News;
import by.sapra.newsservice.web.v1.models.NewsItem;
import by.sapra.newsservice.web.v1.models.NewsListResponse;
import org.mapstruct.Mapper;

import java.util.List;

import static org.mapstruct.ReportingPolicy.IGNORE;


@Mapper(componentModel = "spring", unmappedTargetPolicy = IGNORE)
public interface NewsMapper {
    default NewsListResponse newsListToNewsListResponse(List<News> newsList) {
        return NewsListResponse.builder()
                .news(newsList.stream().map(this::newsListToNesItemList).toList())
                .build();
    }

    NewsItem newsListToNesItemList(News news);
}
