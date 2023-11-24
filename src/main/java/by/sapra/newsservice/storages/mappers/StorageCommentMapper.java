package by.sapra.newsservice.storages.mappers;

import by.sapra.newsservice.models.CommentEntity;
import by.sapra.newsservice.storages.models.CommentListModel;
import org.mapstruct.Mapper;

import java.util.List;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = "spring", unmappedTargetPolicy = IGNORE)
public interface StorageCommentMapper {
    default CommentListModel entitiesToCommentListModel(List<CommentEntity> entities) {
        return null;
    }
}
