package by.sapra.newsservice.storages.mappers;

import by.sapra.newsservice.models.Category2News;
import by.sapra.newsservice.storages.models.NewsModel;
import org.mapstruct.Mapper;

import java.util.List;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = "spring", unmappedTargetPolicy = IGNORE)
public interface StorageCategory2NewsMapper {

    default List<NewsModel> linksToNewsEntityList(List<Category2News> category2News) {

        return category2News.stream().map(this::lincToNewsEntity).toList();
    }

    NewsModel lincToNewsEntity(Category2News category2News);
}
