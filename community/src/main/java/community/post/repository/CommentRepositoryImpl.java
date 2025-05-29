package community.post.repository;

import community.post.application.dto.GetCommentListResponseDto;
import community.post.application.interfaces.CommentRepository;
import community.post.domain.Post;
import community.post.domain.comment.Comment;
import community.post.repository.entity.comment.CommentEntity;
import community.post.repository.jpa.JpaCommentRepository;
import community.post.repository.jpa.JpaPostRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {

    private final JpaCommentRepository jpaCommentRepository;
    private final JpaPostRepository jpaPostRepository;

    @Override
    public Comment save(Comment comment) {
        CommentEntity commentEntity = new CommentEntity(comment);
        Post targetPost = comment.getPost();
        if (commentEntity.getId() != null) {
            commentEntity.update(comment.getContentText());

            jpaCommentRepository.save(commentEntity);

            return commentEntity.toComment();
        }
        CommentEntity savedCommentEntity = jpaCommentRepository.save(commentEntity);
        jpaPostRepository.increaseCommentCount(targetPost.getId());

        return savedCommentEntity.toComment();
    }

    @Override
    public Comment findById(Long id) {
        CommentEntity commentEntity = jpaCommentRepository.findById(id).orElseThrow();
        return commentEntity.toComment();
    }

    @Override
    public void deleteAllByPostId(Long postId) {
        jpaCommentRepository.deleteAllByPostId(postId);
    }

    @Override
    public List<GetCommentListResponseDto> getCommentList(Long postId) {
        return jpaCommentRepository.getCommentList(postId);
    }
}
