package by.sapra.newsservice.services.mappers;

import by.sapra.newsservice.services.models.News;
import by.sapra.newsservice.storages.models.NewsListModel;
import by.sapra.newsservice.storages.models.NewsModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = "spring", unmappedTargetPolicy = IGNORE, uses = {CommentModelMapper.class})
public interface NewsModelMapper {
    default List<News> modelListToNewsList(NewsListModel model) {
        return model.getNews().stream().map(this::newsModelToNews).toList();
    }

    @Mapping(source = "commentSize", target = "commentsCount")
    News newsModelToNews(NewsModel newsModel);

    @Mapping(source = "commentsCount", target = "commentSize")
    NewsModel newsToNewsModel(News news);
}
