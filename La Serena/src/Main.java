import java.util.concurrent.*;
public class Main {
    public static void main(String[] args) {
        ScheduledExecutorService myPool = Executors.newScheduledThreadPool(1);
        myPool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello World!");
            }
        }, 0, 5, TimeUnit.SECONDS);
        myPool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("It's me, Mario!");
            }
        }, 0, 5, TimeUnit.SECONDS);
    }
}