import java.util.concurrent.atomic.AtomicInteger;

class Counter implements AutoCloseable {
    private AtomicInteger count = new AtomicInteger(0);

    public int addAndGet() {
        return count.incrementAndGet();
    }

    public int getCount() {
        return count.get();
    }

    @Override
    public void close() {
        if (count.get() != 0) {
            throw new IllegalStateException("Ошибка обработки");
        }
    }
}
