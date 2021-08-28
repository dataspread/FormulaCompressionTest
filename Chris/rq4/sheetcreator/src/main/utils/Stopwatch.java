package utils;

import java.time.LocalDateTime;
import java.time.Duration;

public class Stopwatch {
    
    private LocalDateTime startTime;

    public Stopwatch () {}

    public void start () {
        this.startTime = LocalDateTime.now();
    }

    public void printDuration () {
        long seconds = Duration.between(this.startTime, LocalDateTime.now()).getSeconds();
        System.out.println("Time taken (HH:MM:SS): " + 
            String.format("%02d:%02d:%02d"
                , (seconds / 3600)
                , (seconds % 3600) / 60
                , (seconds % 60)
            )
        );
    }

}
