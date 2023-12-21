package by.sapra.newsservice.storages.reposytory;

import by.sapra.newsservice.models.NewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface NewsRepository extends JpaRepository<NewsEntity, Long>, JpaSpecificationExecutor<NewsEntity> {
}
