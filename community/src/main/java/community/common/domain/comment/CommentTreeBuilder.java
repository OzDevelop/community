package community.common.domain.comment;

import community.post.application.dto.GetCommentListResponseDto;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentTreeBuilder {
    public static List<GetCommentListResponseDto> commentBuilddTree(List<GetCommentListResponseDto> flatList) {
        Map<Long, GetCommentListResponseDto> map = new HashMap<>();
        for (GetCommentListResponseDto dto : flatList) {
            map.put(dto.commentId(), dto);
        }

        List<GetCommentListResponseDto> result = new ArrayList<>();
        for (GetCommentListResponseDto dto : flatList) {
            if (dto.parentCommentId() == null) {
                result.add(dto);
            } else {
                GetCommentListResponseDto parent = map.get(dto.parentCommentId());
                if (parent != null) {
                    parent.getChildren().add(dto);
                }
            }
        }
        return result;
    }
}

