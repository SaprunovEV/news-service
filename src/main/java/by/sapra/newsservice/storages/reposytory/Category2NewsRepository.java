package by.sapra.newsservice.storages.reposytory;

import by.sapra.newsservice.models.Category2News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Category2NewsRepository extends JpaRepository<Category2News, Long> {
    long countByCategory_Id(long id);
}
