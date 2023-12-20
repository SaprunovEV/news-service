package by.sapra.newsservice.storages.mappers;

import by.sapra.newsservice.models.Category2News;
import by.sapra.newsservice.models.CategoryEntity;
import by.sapra.newsservice.models.NewsEntity;
import by.sapra.newsservice.storages.models.NewsListModel;
import by.sapra.newsservice.storages.models.NewsModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Map;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = "spring", unmappedTargetPolicy = IGNORE, uses = {StorageCommentMapper.class})
public interface StorageNewsMapper {
    default NewsListModel entitiesListToNewsListModel(List<NewsEntity> content, Map<Long, Long> countsMap) {
        return NewsListModel.builder()
                .news(content.stream().map(entity -> entityToModelWithCategoryList(entity, countsMap.get(entity.getId()))).toList())
                .count(content.size())
                .build();
    }

    default List<Long> categoryListToCategoryIdList(List<Category2News> category2News) {
        return category2News.stream().map(Category2News::getCategory).map(CategoryEntity::getId).toList();
    }
    @Mapping(source = "count", target = "commentSize")
    NewsModel entityToModel(NewsEntity entity, Long count);

    default NewsModel entityToModelWithCategoryList(NewsEntity entity, Long count) {
        NewsModel newsModel = entityToModel(entity, count);

        newsModel.setCategoryIds(categoryListToCategoryIdList(entity.getCategory2News()));

        return newsModel;
    }
}
