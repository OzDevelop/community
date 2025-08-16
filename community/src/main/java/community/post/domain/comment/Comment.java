package community.post.domain.comment;


import community.common.IntegerRelationCounter;
import community.common.domain.exception.postException.PostAuthorRequiredException;
import community.common.domain.exception.postException.PostContentRequiredException;
import community.common.domain.exception.postException.PostNotExistException;
import community.common.domain.exception.postException.SelfLikeNotAllowedException;
import community.common.domain.exception.postException.UnauthorizedPostUpdateException;
import community.post.domain.Post;
import community.post.domain.content.CommentContent;
import community.post.domain.content.Content;
import community.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Comment {
    private final Long id;
    private final Post post;
    private final User author;
    private final Content content;
    private final IntegerRelationCounter likeCount;
    private final Comment parent;

    public Comment(Long id, Post post, User author, Content content, IntegerRelationCounter likeCount, Comment parent) {
        if (post == null) {
            throw new PostNotExistException();
        }
        if (author == null) {
            throw new PostAuthorRequiredException();
        }
        if (content == null) {
            throw new PostContentRequiredException();
        }

        this.id = id;
        this.post = post;
        this.author = author;
        this.content = content;
        this.likeCount = likeCount;
        this.parent = parent;
    }

    public Comment(Long id, Post post, User author, String content, Comment comment) {
        this(id, post, author, new CommentContent(content), new IntegerRelationCounter(), comment);
    }

    public static Comment createReply(Post post, User author, String content, Comment parent) {
        return new Comment(null, post, author, new CommentContent(content), new IntegerRelationCounter(), parent);
    }


    public void like(User user) {
        if(author.equals(user)) {
            throw new SelfLikeNotAllowedException();
        }
        likeCount.increase();
    }

    public void unlike(User user) {
        if(author.equals(user)) {
            throw new SelfLikeNotAllowedException();
        }

        likeCount.decrease();
    }

    public void updateComment(User user, String updatedContent) {
        if(!author.equals(user)) {
            throw new UnauthorizedPostUpdateException();
        }
        content.updateContent(updatedContent);
    }

    /// -- get --
    public int getLikeCount() {
        return likeCount.getCount();
    }

    public String getContentText() {
        return content.getContentText();
    }
}
