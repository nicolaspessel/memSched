import java.util.ArrayList;
import java.util.List;

public class MemoryManager {
    private final int maxRamCapacity;
    private int currentRamUsage;
    private final List<Process> activeProcesses = new ArrayList<>();
    private final List<Process> diskSwap = new ArrayList<>();
    private final int memoryWeight;
    private final int priorityWeight;

    // Getters
    public int getMaxRamCapacity() {
        return maxRamCapacity;
    }

    public int getCurrentRamUsage() {
        return currentRamUsage;
    }

    // Constructor
    public MemoryManager(int maxRamCapacity, int memoryWeight, int priorityWeight) {
        this.maxRamCapacity = maxRamCapacity;
        this.memoryWeight = memoryWeight;
        this.priorityWeight = priorityWeight;
    }

    // Methods
    public void addNewProcess(Process newProcess) {
        if(newProcess.requiredMemory() > maxRamCapacity) {
            throw new IllegalArgumentException("Error: the process needs more memory than the CPU can handle!");
        }

        while(currentRamUsage + newProcess.requiredMemory() > maxRamCapacity) {
            moveToSwap();
        }
        activeProcesses.add(newProcess);
        currentRamUsage += newProcess.requiredMemory();
    }

    private void moveToSwap() {
        Process swapProc = activeProcesses.stream().max((p1, p2) -> Integer.compare(
                p1.calculateSwapScore(memoryWeight, priorityWeight),
                p2.calculateSwapScore(memoryWeight, priorityWeight)))
                .orElseThrow(); // Exception blocked by previous if-clause
        activeProcesses.remove(swapProc);
        currentRamUsage -= swapProc.requiredMemory();
        diskSwap.add(swapProc);
    }

    private void calculateRamUsage() {
        currentRamUsage = activeProcesses.stream().mapToInt(Process::requiredMemory).sum();
    }
}
