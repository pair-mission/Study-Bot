package schedule;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ReminderScheduler {

    private final ScheduledExecutorService executor;
    private final Job job;

    public ReminderScheduler(Job job) {
        executor = Executors.newSingleThreadScheduledExecutor();
        this.job = job;
    }

    public void startDailyAt(int hour, int minute) {
        long initialDelay = calculateInitialDelay(hour, minute);
        long period = TimeUnit.DAYS.toMillis(1);

        executor.scheduleAtFixedRate(
                job::run,
                initialDelay,
                period,
                TimeUnit.MILLISECONDS
        );
    }

    private long calculateInitialDelay(int hour, int minute) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime todayTarget = now.toLocalDate().atTime(hour, minute);

        if (now.isAfter(todayTarget)) {
            todayTarget = todayTarget.plusDays(1);
        }

        return Duration.between(now, todayTarget).toMillis();
    }

    public void stop() {
        executor.shutdown();
    }

}
