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
import org.springframework.transaction.annotation.Transactional;

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
        Optional<UserEntity> optional = repository.findById(id);
        return optional.isEmpty() ? Optional.empty() : Optional.of(mapper.entityToStorageUserItem(optional.get()));
    }

    @Override
    @Transactional
    public Optional<StorageUserItem> createNewUser(StorageUserItem userToSave) {
        if (repository.findByName(userToSave.getName()).isPresent()) return Optional.empty();
        UserEntity savedUser = repository.save(mapper.storageUserEntityToEntity(userToSave));
        return Optional.ofNullable(mapper.entityToStorageUserItem(savedUser));
    }

    @Override
    public Optional<StorageUserItem> updateUser(StorageUserItem mapperResponse) {
        return Optional.empty();
    }
}
