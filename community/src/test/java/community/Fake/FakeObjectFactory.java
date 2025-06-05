package community.Fake;

import community.post.application.CommentService;
import community.post.application.PostService;
import community.post.application.interfaces.CommentRepository;
import community.post.application.interfaces.LikeRepository;
import community.post.application.interfaces.PostRepository;
import community.post.repository.FakeCommentRepository;
import community.post.repository.FakeLikeRepository;
import community.post.repository.FakePostRepository;
import community.user.application.interfaces.UserRelationRepository;
import community.user.application.interfaces.UserRepository;
import community.user.application.repository.FakeUserRelationRepository;
import community.user.application.repository.FakeUserRepository;
import community.user.application.service.UserRelationService;
import community.user.application.service.UserService;

public class FakeObjectFactory {

    private static final UserRepository fakeUserRepository = new FakeUserRepository();
    private static final UserRelationRepository fakeUserRelationRepository = new FakeUserRelationRepository();
    private static final PostRepository fakePostRepository = new FakePostRepository();
    private static final CommentRepository fakeCommentRepository = new FakeCommentRepository();
    private static final LikeRepository fakeLikeRepository = new FakeLikeRepository();

    private static final UserService userService = new UserService(fakeUserRepository);
    private static final UserRelationService userRelationRepository = new UserRelationService(fakeUserRelationRepository, userService);
    private static final PostService postService = new PostService(userService, fakePostRepository, fakeLikeRepository, fakeCommentRepository);
    private static final CommentService commentService = new CommentService(userService, postService, fakeCommentRepository, fakeLikeRepository);

    private FakeObjectFactory() {}

    public static UserService getUserService() {
        return userService;
    }

    public static UserRelationService getUserRelationService() {
        return userRelationRepository;
    }

    public static PostService getPostService() {
        return postService;
    }

    public static CommentService getCommentService() {
        return commentService;
    }
}
