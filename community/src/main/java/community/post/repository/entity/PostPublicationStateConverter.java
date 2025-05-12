package community.post.repository.entity;

import community.post.domain.content.PostPublicationState;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class PostPublicationStateConverter implements AttributeConverter<PostPublicationState, String> {

    @Override
    public String convertToDatabaseColumn(PostPublicationState postPublicationState) {
        return postPublicationState.name();
    }

    @Override
    public PostPublicationState convertToEntityAttribute(String s) {
        if (s == null) return PostPublicationState.PUBLIC; // 기본값 설정
        return PostPublicationState.valueOf(s);    }
}