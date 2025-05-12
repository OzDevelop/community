package community.common;

import lombok.Getter;

@Getter
public class IntegerRelationCounter {
    private int count;

    public IntegerRelationCounter() {
        this.count = 0;
    }

    public IntegerRelationCounter(int count) {
        this.count = count;
    }

    public void increase() {
        this.count++;
    }

    public void decrease() {
        if (count <= 0)
            return;
        this.count--;
    }
}
