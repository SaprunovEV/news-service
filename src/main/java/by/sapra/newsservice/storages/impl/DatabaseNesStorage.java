package by.sapra.newsservice.storages.impl;

import by.sapra.newsservice.models.NewsEntity;
import by.sapra.newsservice.services.models.filters.NewsFilter;
import by.sapra.newsservice.storages.NewsStorage;
import by.sapra.newsservice.storages.mappers.StorageNewsMapper;
import by.sapra.newsservice.storages.models.NewsListModel;
import by.sapra.newsservice.storages.reposytory.CommentRepository;
import by.sapra.newsservice.storages.reposytory.NewsRepository;
import by.sapra.newsservice.storages.reposytory.specifications.NewsSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DatabaseNesStorage implements NewsStorage {
    private final NewsRepository repository;
    private final CommentRepository commentRepository;
    private final StorageNewsMapper mapper;
    @Override
    public NewsListModel findAll(NewsFilter filter) {
        Page<NewsEntity> page = repository.findAll(
                NewsSpecification.withFilter(filter),
                PageRequest.of(filter.getPageNumber(), filter.getPageSize())
        );
        return page.isEmpty() ? getEmptyModel() : mapListModel(page);
    }

    private NewsListModel mapListModel(Page<NewsEntity> page) {
        HashMap<Long, Long> countsMap = new HashMap<>();
        List<NewsEntity> content = page.getContent();
        for (NewsEntity news : content) {
            countsMap.put(news.getId(), commentRepository.countByNews_Id(news.getId()));
        }
        return mapper.entitiesListToNewsListModel(content, countsMap);
    }

    private NewsListModel getEmptyModel() {
        return NewsListModel.builder().news(new ArrayList<>()).build();
    }
}
