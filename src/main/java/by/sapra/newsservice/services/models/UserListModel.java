package by.sapra.newsservice.services.models;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserListModel {
    private List<UserItemModel> users;
}
