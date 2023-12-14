package by.sapra.newsservice.storages.reposytory;

import by.sapra.newsservice.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
