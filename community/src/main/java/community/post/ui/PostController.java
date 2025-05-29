package community.post.ui;


import community.common.ui.Response;
import community.post.application.PostService;
import community.post.application.dto.CreatePostRequestDto;
import community.post.application.dto.LikeRequestDto;
import community.post.application.dto.UpdatePostRequestDto;
import community.post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    // 동작 확인 O
    @PostMapping
    public Response<Long> createPost(@RequestBody CreatePostRequestDto dto) {
        Post post = postService.createPost(dto);
        return Response.ok(post.getId());
    }

    //TODO - update, delete, like, unlike

    // update
    // 동작 확인 O
    @PatchMapping("/{postId}")
    public Response<Long> updatePost(@PathVariable(name = "postId") Long postId,
                                     @RequestBody UpdatePostRequestDto dto) {
        Post post = postService.updatePost(postId, dto);
        return Response.ok(post.getId());
    }


    @DeleteMapping("/{postId}")
    public Response<Long> deletePost(@PathVariable(name = "postId") Long postId) {
        postService.deletePost(postId);
        return Response.ok(postId);

    }


    // like
    // 동작 확인 O
    @PostMapping("/like")
    public Response<Void> likePost(@RequestBody LikeRequestDto dto) {
        postService.likePost(dto);
        return Response.ok(null);
    }

    // unlike
    // 동작 확인 O
    @PostMapping("/unlike")
    public Response<Void> unlikePost(@RequestBody LikeRequestDto dto) {
        postService.unlikePost(dto);
        return Response.ok(null);
    }
}
