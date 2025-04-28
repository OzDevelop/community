package community.post.domain.content;

public class PostContent {
    protected String contentText;
    protected final DatetimeInfo datetimeInfo;

    private static final int MAX_LENGTH = 500;
    private static final int MIN_LENGTH = 5;

    protected PostContent(String content) {
        checkText(content);
        this.contentText = content;
        this.datetimeInfo = new DatetimeInfo();
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
    private void checkText(String contentText) {
        if (contentText == null || contentText.isEmpty())
            throw new IllegalArgumentException();
        if (contentText.length() > MAX_LENGTH)
            throw new IllegalArgumentException();
        if (contentText.length() <MIN_LENGTH)
            throw new IllegalArgumentException();
    }
}
