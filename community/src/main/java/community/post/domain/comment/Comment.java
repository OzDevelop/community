package community.post.domain.comment;


import community.common.IntegerRelationCounter;
import community.post.domain.Post;
import community.post.domain.content.CommentContent;
import community.post.domain.content.Content;
import community.user.domain.User;

public class Comment {

    private final Long id;
    private final Post post;
    private final User author;
    private final Content content;
    private final IntegerRelationCounter likeCount;

    public Comment(Long id, Post post, User author, Content content, IntegerRelationCounter likeCount) {
        if (post == null) {
            throw new IllegalArgumentException("post should not be null");
        }
        if (author == null) {
            throw new IllegalArgumentException("author should not be null");
        }
        if (content == null) {
            throw new IllegalArgumentException("content should not be null or empty");
        }

        this.id = id;
        this.post = post;
        this.author = author;
        this.content = content;
        this.likeCount = likeCount;
    }

    public Comment(Long id, Post post, User author, String content) {
        this(id, post, author, new CommentContent(content), new IntegerRelationCounter());
    }

    public void like(User user) {
        if(author.equals(user)) {
            throw new IllegalArgumentException("author cannot like own comment");
        }
        likeCount.increase();
    }

    public void unlike() {
        likeCount.decrease();
    }

    public void updateComment(User user, String updatedContent) {
        if(!author.equals(user)) {
            throw new IllegalArgumentException("only author can update content");
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
