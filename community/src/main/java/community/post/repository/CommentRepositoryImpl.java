package community.post.repository;

import community.post.application.interfaces.CommentRepository;
import community.post.domain.comment.Comment;
import community.post.repository.jpa.JpaCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {

    private final JpaCommentRepository jpaCommentRepository;

    @Override
    public Comment save(Comment comment) {
        return null;
    }

    @Override
    public Comment findById(Long id) {
        return null;
    }
}
