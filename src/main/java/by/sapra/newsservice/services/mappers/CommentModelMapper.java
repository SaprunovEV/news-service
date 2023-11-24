package by.sapra.newsservice.services.mappers;

import by.sapra.newsservice.services.models.Comment;
import by.sapra.newsservice.storages.models.CommentListModel;
import org.mapstruct.Mapper;

import java.util.List;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = "spring", unmappedTargetPolicy = IGNORE)
public interface CommentModelMapper {

    default List<Comment> commentListModelToCommentList(CommentListModel model) {
        return null;
    }
}
