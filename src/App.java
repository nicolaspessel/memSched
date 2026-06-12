import java.util.Queue;

public class App {

    public static class Scheduler implements SchedulingStrategy {

        @Override
        public Kernel.ProcessControlBlock selectNext(Queue<Kernel.ProcessControlBlock> readyQueue) {
            if(!readyQueue.isEmpty()) {
                return readyQueue.remove();
            }
            return null;
        }
    }

    public static void main(String[] args) {
        MemoryManager memoryManager = new MemoryManager(8000000, 2, 3);
        Scheduler scheduler = new Scheduler();

        Kernel kernel = new Kernel(memoryManager, scheduler);
    }
}
