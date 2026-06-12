public record Process(int pid, int requiredMemory, int priority, int burstTime) {
    public int calculateSwapScore(int memoryWeight, int priorityWeight) {
        return (memoryWeight * this.requiredMemory) - (priorityWeight * this.priority);
    }
}