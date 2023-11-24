package by.sapra.newsservice.storages.mappers;

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
                .news(content.stream().map(entity -> entityToModel(entity, countsMap.get(entity.getId()))).toList())
                .count(countsMap.values().stream().reduce(0L, Long::sum))
                .build();
    }

    @Mapping(source = "count", target = "commentSize")
    NewsModel entityToModel(NewsEntity entity, Long count);
}
