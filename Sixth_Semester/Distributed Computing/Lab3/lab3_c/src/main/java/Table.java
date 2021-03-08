import java.util.concurrent.CountDownLatch;

public class Table {
    private PartType curPartType;

    public PartType getCurPartType() {
        return curPartType;
    }

    public void setCurPartType(PartType curPartType) {
        this.curPartType = curPartType;
    }
}
