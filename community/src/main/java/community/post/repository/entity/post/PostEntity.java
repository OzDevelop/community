package community.post.repository.entity.post;

import community.common.IntegerRelationCounter;
import community.post.domain.Post;
import community.post.domain.content.PostContent;
import community.post.domain.content.PostPublicationState;
import community.user.repository.entity.UserEntity;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "community_post")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostEntity {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="author_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private UserEntity author;

    private String content;

    @Convert(converter = PostPublicationStateConverter.class)
    private PostPublicationState state;
    private Integer likeCount;

    @ColumnDefault("0")
    private int commentCount;

    public PostEntity(Post post) {
        this.id = post.getId();

        this.content = post.getContent();
        this.state = post.getState();
        this.likeCount = post.getLikeCount();
    }

    public Post toPost() {
        return Post.builder()
                .id(id)

                .content(new PostContent(content))
                .state(state)
                .likeCount(new IntegerRelationCounter(likeCount))
                .build();

    }
}
