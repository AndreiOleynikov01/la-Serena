import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
public class Main {
    public static void main(String[] args) {
        ScheduledExecutorService myPool = Executors.newScheduledThreadPool(1);
        myPool.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello World!");
            }
        }, 5, TimeUnit.SECONDS);
        myPool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("It's me, Mario!");
            }
        }, 0, 5, TimeUnit.SECONDS);
    }
}