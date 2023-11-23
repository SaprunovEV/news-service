package by.sapra.newsservice.services.mappers;

import by.sapra.newsservice.services.models.News;
import by.sapra.newsservice.storages.models.NewsListModel;

import java.util.List;

public interface NewsModelMapper {
    List<News> modelListToNewsList(NewsListModel model);
}
