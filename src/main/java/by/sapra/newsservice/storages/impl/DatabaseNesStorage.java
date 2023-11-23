package by.sapra.newsservice.storages.impl;

import by.sapra.newsservice.models.NewsEntity;
import by.sapra.newsservice.services.models.filters.NewsFilter;
import by.sapra.newsservice.storages.NewsStorage;
import by.sapra.newsservice.storages.models.NewsListModel;
import by.sapra.newsservice.storages.models.NewsModel;
import by.sapra.newsservice.storages.reposytory.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class DatabaseNesStorage implements NewsStorage {
    private final NewsRepository repository;
    @Override
    public NewsListModel findAll(NewsFilter filter) {
        Page<NewsEntity> page = repository.findAll(PageRequest.of(filter.getPageNumber(), filter.getPageSize()));
        return page.isEmpty() ? getEmptyModel() : mapListModel(page);
    }

    private NewsListModel mapListModel(Page<NewsEntity> page) {
        return NewsListModel.builder()
                .news(page.getContent().stream().map(this::mapNewsModel).toList())
                .count(page.getSize())
                .build();
    }

    private NewsModel mapNewsModel(NewsEntity newsEntity) {
        return NewsModel.builder().build();
    }

    private NewsListModel getEmptyModel() {
        return NewsListModel.builder().news(new ArrayList<>()).build();
    }
}
