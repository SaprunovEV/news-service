package by.sapra.newsservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "news")
@Getter
@Setter
public class NewsEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String title;
    @Column(name = "abstract", length = 100)
    private String newsAbstract;
    @Column(name = "body", nullable = false)
    private String body;

    @CreationTimestamp
    private Instant createAt;
    @UpdateTimestamp
    private Instant updateAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private UserEntity user;

    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Category2News> category2News;
}
