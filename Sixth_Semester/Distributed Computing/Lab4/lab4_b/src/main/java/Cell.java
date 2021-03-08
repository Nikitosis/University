import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Cell {
    private CellStatus cellStatus;
    private ReentrantReadWriteLock lock;

    public Cell() {
        cellStatus = CellStatus.OK;
        lock = new ReentrantReadWriteLock();
    }

    public CellStatus getCellStatus() {
        lock.readLock().lock();
        CellStatus cellStatus =  this.cellStatus;
        lock.readLock().unlock();
        return cellStatus;
    }

    public void setCellStatus(CellStatus cellStatus) {
        lock.writeLock().lock();
        this.cellStatus = cellStatus;
        lock.writeLock().unlock();
    }
}
