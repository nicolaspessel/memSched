### Decision Log

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

#### Decision 3: Clock Loop Mechanism

**Context:** Implement scheduling logic directly inside the Kernel would violate the Open/Close Principle (extend and not
change existing code). Whenever testing a new scheduler, the Kernel's core code would have to be modified.

**Decision:** Create the SchedulingStrategy interface. The Kernel does not need to know how the process selection is done;
it simply receives the strategy via its Constructor (Dependency Injection) and triggers when necessary.

**Reasoning:** Maintain high modularity and scalability, without modifying the previous written code.

---

#### Decision 4: Clock Loop Mechanism

**Context:** Implement scheduling logic directly inside the Kernel would violate the Open/Close Principle (extend and not
change existing code). Whenever testing a new scheduler, the Kernel's core code would have to be modified.

**Decision:** Create the SchedulingStrategy interface. The Kernel does not need to know how the process selection is done;
it simply receives the strategy via its Constructor (Dependency Injection) and triggers when necessary.

**Reasoning:** Maintain high modularity and scalability, without modifying the previous written code.
