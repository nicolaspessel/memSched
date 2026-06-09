import java.util.Queue;

public interface SchedulingStrategy {
    public Process selectNext(Queue<Process> readyQueue);
}