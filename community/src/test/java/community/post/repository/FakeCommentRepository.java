package community.post.repository;

import community.post.application.dto.GetCommentListResponseDto;
import community.post.application.interfaces.CommentRepository;
import community.post.domain.comment.Comment;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FakeCommentRepository implements CommentRepository {

    private final Map<Long, Comment> store = new HashMap<>();

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() != null) {
            store.put(comment.getId(), comment);
            return comment;
        }

        long id = store.size() + 1;
        Comment newComment = new Comment(id, comment.getPost(), comment.getAuthor(), comment.getContentText());
        store.put(id, newComment);
        return newComment;
    }

    @Override
    public void deleteAllByPostId(Long postId) {

    }

    @Override
    public List<GetCommentListResponseDto> getCommentList(Long postId) {
        return List.of();
    }

    @Override
    public Comment findById(Long id) {
        return store.get(id);
    }

    @Override
    public void delete(Comment comment) {

    }
}