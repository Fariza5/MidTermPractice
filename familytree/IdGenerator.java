package familytree;

import java.util.concurrent.atomic.AtomicInteger;

public final class IdGenerator {
    private static final AtomicInteger COUNTER = new AtomicInteger(1);

    private IdGenerator() {}

    public static String nextId() {
        int n = COUNTER.getAndIncrement();
        return String.format("P%03d", n);
    }
}
