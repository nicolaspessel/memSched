import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Kernel {
    private final MemoryManager memoryManager;
    private final SchedulingStrategy schedulingStrategy; // Composition

    private final Queue<ProcessControlBlock> readyQueue = new LinkedList<>(); // Better O(n) complexity
    private ProcessControlBlock currentlyRunning = null;
    private int clock = 0;

    // Constructor
    public Kernel(MemoryManager memoryManager, SchedulingStrategy schedulingStrategy) {
        this.memoryManager = memoryManager;
        this.schedulingStrategy = schedulingStrategy;
    }

    // Inner class
    public static class ProcessControlBlock {
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

    public void executeCpuClock() {
        if(currentlyRunning != null) {
            currentlyRunning.remainingTicks--;

            if(currentlyRunning.remainingTicks == 0) {
                System.out.println("\n[KERNEL] Process " + currentlyRunning.process.name() + " " +
                        currentlyRunning.process.pid() + "finished execution.");
                memoryManager.removeProcess(currentlyRunning.process);
                currentlyRunning = null;
            }
        }
        else if(!readyQueue.isEmpty()) {
            currentlyRunning = schedulingStrategy.selectNext(readyQueue);
            currentlyRunning.currentState = ProcessState.RUNNING;
        }
    }

    private void printSystemState() {
        // Códigos ANSI para cores
        String RESET = "\u001B[0m";
        String GREEN = "\u001B[32m";
        String YELLOW = "\u001B[33m";
        String CYAN = "\u001B[36m";
        String BLUE = "\u001B[34m";

        System.out.println(CYAN + "\n╔══════════════════════════════════════════════════════════════╗" + RESET);
        System.out.println(CYAN + "║" + RESET + " 🖥️  " + YELLOW + "KERNEL DASHBOARD" + RESET + "                              CLOCK: " + String.format("%04d", clock) + CYAN + " ║" + RESET);
        System.out.println(CYAN + "╠══════════════════════════════════════════════════════════════╣" + RESET);

        // --- PAINEL DA CPU ---
        System.out.print(CYAN + "║" + RESET + " CPU: ");
        if (currentlyRunning != null) {
            String procName = currentlyRunning.process.name();
            int pid = currentlyRunning.process.pid();
            int remTicks = currentlyRunning.remainingTicks;
            System.out.printf(GREEN + "▶ RUNNING" + RESET + " | [%04d] %-12s | Ticks Restantes: %02d   " + CYAN + "║\n" + RESET, pid, procName, remTicks);
        } else {
            System.out.println(YELLOW + "⏸ IDLE (Ociosa)" + RESET + "                                          " + CYAN + "║\n" + RESET);
        }

        // --- PAINEL DE MEMÓRIA (Barra de Progresso) ---
        // Supondo que você crie os métodos getCurrentRamUsage() e getMaxRamCapacity() no MemoryManager
        // int ramUsage = memoryManager.getCurrentRamUsage();
        // int maxRam = memoryManager.getMaxRamCapacity();
        int ramUsage = 3000000; // Valores fixos apenas para teste visual
        int maxRam = 8000000;

        int percent = (int) (((double) ramUsage / maxRam) * 100);
        int barLength = 40;
        int filled = (int) (((double) ramUsage / maxRam) * barLength);

        StringBuilder bar = new StringBuilder("[");
        for (int i = 0; i < barLength; i++) {
            if (i < filled) bar.append("█");
            else bar.append("░");
        }
        bar.append("]");

        System.out.println(CYAN + "╠══════════════════════════════════════════════════════════════╣" + RESET);
        System.out.println(CYAN + "║" + RESET + " RAM: " + BLUE + bar.toString() + RESET + " " + percent + "% " + CYAN + "║" + RESET);
        System.out.println(CYAN + "╠══════════════════════════════════════════════════════════════╣" + RESET);

        // --- PAINEL DA FILA DE PRONTOS ---
        System.out.println(CYAN + "║" + RESET + " READY QUEUE:                                                 " + CYAN + "║" + RESET);
        if (readyQueue.isEmpty()) {
            System.out.println(CYAN + "║" + RESET + "   (Fila vazia)                                               " + CYAN + "║" + RESET);
        } else {
            for (ProcessControlBlock pcb : readyQueue) {
                System.out.printf(CYAN + "║" + RESET + "   -> [%04d] %-12s | Burst: %02d | Prioridade: %d       " + CYAN + "║\n" + RESET,
                        pcb.process.pid(), pcb.process.name(), pcb.remainingTicks, pcb.process.priority());
            }
        }
        System.out.println(CYAN + "╚══════════════════════════════════════════════════════════════╝" + RESET);
    }

    public void startSimulation() {
        Scanner sc = new Scanner(System.in);
        String ctrl = "";

        System.out.println("***** MEMORY AND PROCESS SCHEDULER SIMULATOR ******");
        while(!ctrl.contentEquals("q")) {
            System.out.println("""
                    Press one of the following keys:\s
                    (p) add process
                    (t) wall-clock time
                    (q) quit""");

            ctrl = sc.next();
            if(!ctrl.contentEquals("p") && !ctrl.contentEquals("t") && !ctrl.contentEquals("q")) {
                System.out.println("Error! Choose a valid option.");
                continue; // Restarts the loop
            }

            switch(ctrl) {
                case "p":
                    System.out.println("\n--- NEW PROCESS ---");
                    System.out.print("\nPID Number: ");
                    int pid = sc.nextInt();
                    sc.nextLine(); // Cleans the buffer
                    System.out.print("\nName: ");
                    String name = sc.nextLine();
                    System.out.print("\nRequired Memory: ");
                    int requiredMemory = sc.nextInt();
                    System.out.print("\nPriority (1-5): ");
                    int priority = sc.nextInt();
                    System.out.print("\nBurst Time (ticks): ");
                    int burstTime = sc.nextInt();

                    Process process = new Process(pid, name, requiredMemory, priority, burstTime);
                    addNewProcess(process);

                    if(currentlyRunning == null && !readyQueue.isEmpty()) {
                        currentlyRunning = schedulingStrategy.selectNext(readyQueue);
                        currentlyRunning.currentState = ProcessState.RUNNING;
                    }
                    break;

                case "t":
                    clock++;
                    executeCpuClock();
                    break;

                case "q":
                    System.out.println("Shutting down simulator...");
                    break;
            }
            printSystemState();
        }
    }
}
