package by.sapra.newsservice.storages.mappers;

import by.sapra.newsservice.models.Category2News;
import by.sapra.newsservice.storages.models.NewsModel;
import by.sapra.newsservice.storages.reposytory.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class DelegateStorageCategory2NewsMapper implements StorageCategory2NewsMapper {
    @Autowired
    private StorageNewsMapper delegate;
    @Autowired
    private CommentRepository repository;

    @Override
    public NewsModel lincToNewsModel(Category2News category2News) {
        return delegate.entityToModel(category2News.getNews(), repository.countByNews_Id(category2News.getNews().getId()));
    }
}
