package by.sapra.newsservice.storages;

import by.sapra.newsservice.models.UserEntity;
import by.sapra.newsservice.services.models.filters.UserFilter;
import by.sapra.newsservice.storages.mappers.StorageUserMapper;
import by.sapra.newsservice.storages.models.StorageUserItem;
import by.sapra.newsservice.storages.models.StorageUserList;
import by.sapra.newsservice.storages.reposytory.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DatabaseUserStorage implements UserStorage {
    private final UserRepository repository;
    private final StorageUserMapper mapper;

    @Override
    public StorageUserList findAll(UserFilter filter) {
        Page<UserEntity> pageUser = repository.findAll(PageRequest.of(filter.getPageNumber(), filter.getPageSize()));
        return mapper.entityListToStorageUserList(pageUser.getContent());
    }

    @Override
    public Optional<StorageUserItem> findById(long id) {
        return null;
    }
}
