import java.util.ArrayList;
import java.util.List;

public class MemoryManager {
    int maxRamCapacity;
    int currentRamUsage;
    List<Process> activeProcesses = new ArrayList<>();
    List<Process> diskSwap = new ArrayList<>();
}
