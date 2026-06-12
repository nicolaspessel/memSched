import java.util.Queue;

public interface SchedulingStrategy {
    public Kernel.ProcessControlBlock selectNext(Queue<Kernel.ProcessControlBlock> readyQueue);
}