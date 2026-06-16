## Decision Log

### 1. Critical Architectural Decisions

#### Decision 1: Process Lifecycle Encapsulation

**Context:** A raw process (Process) has static data, however, the Kernel needs to track dynamic execution data
related to the existing processes, such as remaining times and current state. Mixing these responsibilities would violate
the immutability of the Process record.

**Decision:** Kept the Process record isolated. To manage dynamic execution data, an inner class called ProcessControlBlock
was created within the Kernel.

**Reasoning:** To maintain the Process with raw and immutable data and separate the execution control.

---

#### Decision 2: Decoupling the Scheduling Algorithm

**Context:** Implement scheduling logic directly inside the Kernel would violate the Open/Close Principle (extend and not
change existing code). Whenever testing a new scheduler, the Kernel's core code would have to be modified.

**Decision:** Create the SchedulingStrategy interface. The Kernel does not need to know how the process selection is done;
it simply receives the strategy via its Constructor (Dependency Injection) and triggers when necessary.

**Reasoning:** Maintain high modularity and scalability, without modifying the previous written code.

---

#### Decision 3: Controlled Processor Mutability (final vs. references)

**Context**: Critical Kernel variables must be protected against accidental reassignment, yet the objects they hold need 
to change internally.

**Solution**: The readyQueue was marked as final (the variable reference always points to the same queue instance, but the queue's 
elements can grow or shrink). Conversely, the currentlyRunning variable (the CPU slot) was kept non-final and initialized as 
null, allowing process rotation.

**Reasoning**: Protection against memory leaks and compilation errors, ensuring that the CPU can stay idle (null) or 
transition states without compiler restrictions.

---

### 2. The Clock Loop Mechanism
The heart of the Kernel operates in a strictly sequential manner on every iteration triggered by the user (t). The order 
of operations within each cycle was designed to prevent race conditions:

**Consumption Phase**: If the CPU (currentlyRunning) is not empty, the remaining ticks of the active process are decremented by 1.

**Deallocation Phase**: If the active process's ticks hit zero, the Kernel performs a clean exit: prints the completion status, 
triggers the MemoryManager to release the RAM allocated to that process, and clears the CPU by setting it to null.

**Scheduling Phase**: If the CPU is empty (either because the process just finished or because the system was already idle) 
and there are processes waiting in the queue, the injected strategy is called to determine the next occupant of the CPU slot.