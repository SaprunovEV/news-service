package by.sapra.newsservice.storages.reposytory;

import by.sapra.newsservice.models.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    long countByNews_Id(Long newsId);
}
