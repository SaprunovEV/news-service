package by.sapra.newsservice.storages.mappers;

import by.sapra.newsservice.models.NewsEntity;
import by.sapra.newsservice.storages.models.NewsListModel;

import java.util.HashMap;
import java.util.List;

public interface StorageNewsMapper {
    NewsListModel entitiesListToNewsListModel(List<NewsEntity> content, HashMap<Long, Long> countsMap);
}
