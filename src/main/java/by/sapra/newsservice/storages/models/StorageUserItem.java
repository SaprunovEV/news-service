package by.sapra.newsservice.storages.models;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class StorageUserItem {
    private Long id;
    private String name;
}
