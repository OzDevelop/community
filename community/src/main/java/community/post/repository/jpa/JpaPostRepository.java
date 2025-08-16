package community.post.repository.jpa;

import community.post.application.dto.GetPostContentResponseDto;
import community.post.repository.entity.post.PostEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaPostRepository extends JpaRepository<PostEntity, Long> {

    @Modifying
    @Query(value = "UPDATE PostEntity p "
            + "SET p.commentCount = p.commentCount + 1 "
            + "WHERE p.id = :postId")
    void increaseCommentCount(Long postId);

    @Query("SELECT p.id "
            + "FROM PostEntity p "
            + "WHERE p.author.id = :authorId")
    List<Long> findAllPostIdsByAuthor(Long authorId);

    @Query("SELECT p FROM PostEntity p WHERE p.author.id = :authorId")
    List<PostEntity> findAllPostsByAuthorId(Long authorId);

    @Query("""
    select new community.post.application.dto.GetPostContentResponseDto(
        p.id,
        p.content,
        p.author.id,
        p.author.name,
        p.author.profileImage,
        p.regDt,
        p.updDt,
        0,
        false,
        0
    )
    from PostEntity p
    where p.author.id = :userId
    order by p.updDt desc
""")
    List<GetPostContentResponseDto> findAllPostsByUserId(@Param("userId") Long userId);

}
