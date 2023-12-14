package by.sapra.newsservice.storages.models;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class StorageUserItem {
    private long id;
    private String name;
}
