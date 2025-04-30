package community.post.domain;

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
    private final PostContent content;
    private PostPublicationState state;

    public Post(Long id, User user, PostContent content) {
        this.id = id;
        this.author = user;
        this.content = content;
        this.state = PostPublicationState.PUBLIC;
    }

    public static Post createPost(Long postId, User author, String content, PostPublicationState state) {
        return new Post(postId, author, new PostContent(content), state);
    }



    // 좋아요
    public void like(User user) {
        if(this.author.equals(user)) {
            throw new IllegalArgumentException();
        }
        //TODO - UserRelationCOunter를 Common으로 Refactoring 후 작성

    }

    // 싫어요
    public void unlike() {
        //TODO - UserRelationCOunter를 Common으로 Refactoring 후 작성
    }

    // Post 업데이트
    public void updatePost(User user, String updateContent, PostPublicationState state) {
        if(!this.author.equals(user)) {
            throw new IllegalArgumentException();
        }
        this.state = state;
        this.content.updateContent(updateContent);

    }
}
