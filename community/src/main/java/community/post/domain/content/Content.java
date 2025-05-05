package community.post.domain.content;

import community.post.domain.common.DatetimeInfo;

public abstract class Content {
    protected String contentText;
    protected final DatetimeInfo datetimeInfo;

    public Content(String content) {
        checkText(content);
        this.contentText = content;
        this.datetimeInfo = new DatetimeInfo();
    }

    public void updateContent(String updateContent) {
        checkText(updateContent);
        this.contentText = updateContent;
        this.datetimeInfo.updateEditDateTime();
    }

    protected abstract void checkText(String contentText);

    public String getContentText() {
        return contentText;
    }
}
