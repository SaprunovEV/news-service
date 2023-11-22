package by.sapra.newsservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "comment")
@Getter
@Setter
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @CreationTimestamp
    private Instant createAt;
    @UpdateTimestamp
    private Instant updateAt;

    private String body;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    @ToString.Exclude
    private CommentEntity parent;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "news_id")
    @ToString.Exclude
    private NewsEntity news;

    @OneToMany(mappedBy = "parent", cascade = ALL)
    @ToString.Exclude
    private List<CommentEntity> comments;
}
