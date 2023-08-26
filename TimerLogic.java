package timeclock;

public class TimerLogic implements Runnable {
    private int seconds;
    private TimerApp app;

    public TimerLogic(int seconds, TimerApp app) {
        this.seconds = seconds;
        this.app = app;
    }

    @Override
    public void run() {
        try {
            while (seconds > 0) {
                Thread.sleep(1000);
                seconds--;
                app.updateTime(seconds);
            }
            app.timerFinished();
        } catch (InterruptedException e) {
            app.timerInterrupted();
        }
    }

    public void stopTimer() {
        seconds = 0;
    }
}

