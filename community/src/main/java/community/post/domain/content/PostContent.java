package community.post.domain.content;

public class PostContent extends Content {


    private static final int MAX_LENGTH = 500;
    private static final int MIN_LENGTH = 5;

    public PostContent(String content) {
        super(content);
    }

    public String getContentText() {
        return contentText;
    }

    public void updateContent(String updateContent) {
        checkText(updateContent);
        this.contentText = updateContent;
        this.datetimeInfo.updateEditDateTime();
    }

    //🦊 contextText 검증
    protected void checkText(String contentText) {
        if (contentText == null || contentText.isEmpty())
            throw new IllegalArgumentException();
        if (contentText.length() > MAX_LENGTH)
            throw new IllegalArgumentException();
        if (contentText.length() <MIN_LENGTH)
            throw new IllegalArgumentException();
    }
}
