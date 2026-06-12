import java.util.LinkedList;
import java.util.Queue;

public class Kernel {
    private final MemoryManager memoryManager;
    private final SchedulingStrategy schedulingStrategy; // Composition

    private final Queue<ProcessControlBlock> readyQueue = new LinkedList<>(); // Better O(n) complexity
    private final ProcessControlBlock currentlyRunning = null;
    private int clock = 0;

    // Constructor
    public Kernel(MemoryManager memoryManager, SchedulingStrategy schedulingStrategy) {
        this.memoryManager = memoryManager;
        this.schedulingStrategy = schedulingStrategy;
    }

    // Inner class
    private static class ProcessControlBlock {
        private Process process;
        private int remainingTicks;
        private ProcessState currentState;

        // Constructor
        public ProcessControlBlock(Process process) {
            this.process = process;
            this.remainingTicks = process.burstTime();
            this.currentState = ProcessState.READY;
        }
    }

    // Methods
    public void addNewProcess(Process process) {
        memoryManager.addNewProcess(process);
        ProcessControlBlock pcb = new ProcessControlBlock(process);
        readyQueue.add(pcb);
    }
}
