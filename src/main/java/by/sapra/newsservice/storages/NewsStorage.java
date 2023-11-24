package by.sapra.newsservice.storages;

import by.sapra.newsservice.services.models.filters.NewsFilter;
import by.sapra.newsservice.storages.models.NewsListModel;

public interface NewsStorage {
    NewsListModel findAll(NewsFilter filter);
}
