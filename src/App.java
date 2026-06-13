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

        System.out.println("[BOOT] Loading initializing processes...");

        Process p1 = new Process(1, "Terminal", 1000000, 3, 5);
        Process p2 = new Process(2, "EditorTexto", 500000, 2, 3);

        kernel.addNewProcess(p1);
        kernel.addNewProcess(p2);

        kernel.startSimulation();
    }
}
