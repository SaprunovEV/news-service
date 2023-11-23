package by.sapra.newsservice.storages.impl;

import by.sapra.newsservice.services.models.filters.NewsFilter;
import by.sapra.newsservice.storages.NewsStorage;
import by.sapra.newsservice.storages.models.NewsListModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class DatabaseNesStorage implements NewsStorage {
    @Override
    public NewsListModel findAll(NewsFilter filter) {
        return NewsListModel.builder().news(new ArrayList<>()).build();
    }
}
