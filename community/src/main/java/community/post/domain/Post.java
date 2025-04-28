package community.post.domain;

import community.post.domain.content.PostContent;
import community.post.domain.content.PostPublicationState;
import community.user.domain.User;


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

    // 좋아요
    public void like(User user) {
        if(this.author.equals(user)) {
            throw new IllegalArgumentException();
        }

    }

    // 싫어요
    public void unlike(User user) {

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
