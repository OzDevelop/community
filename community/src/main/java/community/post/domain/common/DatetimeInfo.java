package community.post.domain.common;

import java.time.LocalDateTime;

public class DatetimeInfo {

    private LocalDateTime datetime;

    public DatetimeInfo() {
        this.datetime = LocalDateTime.now();
    }

    public void updateEditDateTime() {

        this.datetime = LocalDateTime.now();

    }
}
