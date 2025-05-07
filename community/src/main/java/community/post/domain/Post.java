package community.post.domain;

import community.common.IntegerRelationCounter;
import community.post.domain.content.Content;
import community.post.domain.content.PostContent;
import community.post.domain.content.PostPublicationState;
import community.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class Post {
    private final Long id;
    private final User author;
    private final Content content;
    private final IntegerRelationCounter likeCount;
    private PostPublicationState state;


    public Post(Long id, User user, PostContent content) {
        this(id, user, content, PostPublicationState.PUBLIC);
    }

    public Post(Long id, User author, Content content) {
        this(id, author, content, PostPublicationState.PUBLIC);
    }

    public Post(Long id, User author, Content content, PostPublicationState state) {
        if (author == null) {
            throw  new IllegalArgumentException();
        }

        this.id = id;
        this.author = author;
        this.content = content;
        this.likeCount = new IntegerRelationCounter();
        this.state = state;
    }

    public static Post createPost(Long postId, User author, String content, PostPublicationState state) {
        return new Post(postId, author, new PostContent(content), state);
    }



    // 좋아요
    public void like(User user) {
        if(this.author.equals(user)) {
            throw new IllegalArgumentException();
        }
        likeCount.increase();

    }

    // 싫어요
    public void unlike() {
        likeCount.decrease();
    }

    // Post 업데이트
    public void updatePost(User user, String updateContent, PostPublicationState state) {
        if(!this.author.equals(user)) {
            throw new IllegalArgumentException();
        }
        this.state = state;
        this.content.updateContent(updateContent);

    }
    public int getLikeCount() {
        return likeCount.getCount();
    }

}
