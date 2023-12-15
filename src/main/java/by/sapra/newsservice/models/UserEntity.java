package by.sapra.newsservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "users")
@Getter
@Setter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(nullable = false, length = 50, unique = true)
    private String name;

    @OneToMany(mappedBy = "user", cascade = ALL)
    @ToString.Exclude
    private List<NewsEntity> news;

    @OneToMany(mappedBy = "user", cascade = ALL)
    @ToString.Exclude
    private List<CommentEntity> comments;
}
