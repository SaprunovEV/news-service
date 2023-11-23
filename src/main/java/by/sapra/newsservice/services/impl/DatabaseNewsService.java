package by.sapra.newsservice.services.impl;

import by.sapra.newsservice.services.NewsService;
import by.sapra.newsservice.services.mappers.NewsModelMapper;
import by.sapra.newsservice.services.models.News;
import by.sapra.newsservice.services.models.filters.NewsFilter;
import by.sapra.newsservice.storages.NewsStorage;
import by.sapra.newsservice.storages.models.NewsListModel;
import by.sapra.newsservice.storages.models.NewsModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DatabaseNewsService implements NewsService {
    private final NewsStorage newsStorage;
    private final NewsModelMapper mapper;
    @Override
    public List<News> findAll(NewsFilter filter) {
        NewsListModel listModel = newsStorage.findAll(filter);
        return listModel.getCount() == 0 ? List.of(News.builder().build()) : mapper.modelListToNewsList(listModel);
    }
}
