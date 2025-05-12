package community.post.repository.entity.like;

import community.post.domain.Post;
import community.post.domain.comment.Comment;
import community.user.domain.User;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "community_like")
@Getter
@NoArgsConstructor
public class LikeEntity {

    @EmbeddedId
    private LikeIdEntity id;


    public LikeEntity(Post post, User likeedUser) {
        this.id = new LikeIdEntity(post.getId(), likeedUser.getId(), LikeTarget.POST.name());
    }

    public LikeEntity(Comment comment, User likeedUser) {
        this.id = new LikeIdEntity(comment.getId(), likeedUser.getId(), LikeTarget.COMMENT.name());
    }
}
