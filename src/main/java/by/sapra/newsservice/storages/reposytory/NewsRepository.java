package by.sapra.newsservice.storages.reposytory;

import by.sapra.newsservice.models.NewsEntity;
import by.sapra.newsservice.services.models.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<NewsEntity, Long> {
}
