package community.post.repository;

import community.post.application.interfaces.CommentRepository;
import community.post.domain.comment.Comment;
import community.post.repository.entity.comment.CommentEntity;
import community.post.repository.jpa.JpaCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {

    private final JpaCommentRepository jpaCommentRepository;

    @Override
    public Comment save(Comment comment) {
        CommentEntity commentEntity = new CommentEntity(comment);
        if (commentEntity.getId() != null) {
            commentEntity.update(comment.getContentText());

            jpaCommentRepository.save(commentEntity);

            return commentEntity.toComment();
        }
        CommentEntity savedCommentEntity = jpaCommentRepository.save(commentEntity);
        return savedCommentEntity.toComment();
    }

    @Override
    public Comment findById(Long id) {
        CommentEntity commentEntity = jpaCommentRepository.findById(id).orElseThrow();
        return commentEntity.toComment();
    }
}
