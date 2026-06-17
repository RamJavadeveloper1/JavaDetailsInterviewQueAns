### **Multithreading & Concurrency**

## ✅ 40. What is Thread? Difference between Thread and Runnable?

**A Thread is the smallest unit of execution in a process, allowing concurrent execution of code. Runnable is an interface that defines a task to be executed by a thread.**

### 🔹 What is a Thread?

**A thread is a lightweight process that shares memory space with other threads in the same process.**

```java
// Creating a thread by extending Thread class
public class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("Thread is running");
    }
}

// Usage
MyThread thread = new MyThread();
thread.start();  // Starts the thread
```

**Key characteristics:**
- **Lightweight**: Less resource-intensive than processes
- **Shared memory**: Threads share heap space, static variables
- **Concurrent execution**: Multiple threads run simultaneously
- **Managed by JVM**: JVM schedules thread execution

### 🔹 What is Runnable?

**Runnable is a functional interface that represents a task to be executed.**

```java
// Implementing Runnable interface
public class MyTask implements Runnable {
    @Override
    public void run() {
        System.out.println("Task is executing");
    }
}

// Usage
Runnable task = new MyTask();
Thread thread = new Thread(task);
thread.start();
```

**Key characteristics:**
- **Functional interface**: Single abstract method `run()`
- **Task representation**: Defines what to execute, not how
- **Reusable**: Can be executed by multiple threads
- **Java 8+**: Can use lambda expressions

### 🔹 Thread vs Runnable Comparison

| Aspect | Thread | Runnable |
|--------|--------|----------|
| **Inheritance** | Extends Thread class | Implements Runnable interface |
| **Reusability** | Cannot extend other classes | Can extend other classes |
| **Memory** | Each thread has its own stack | Shares thread's stack |
| **Flexibility** | Limited to single inheritance | Multiple interfaces possible |
| **Best Practice** | Avoid extending Thread | Prefer implementing Runnable |

### 🔹 Creating Threads

```java
// Method 1: Extend Thread class (not recommended)
class MyThread extends Thread {
    public void run() {
        // Thread logic
    }
}
new MyThread().start();

// Method 2: Implement Runnable (recommended)
class MyTask implements Runnable {
    public void run() {
        // Task logic
    }
}
new Thread(new MyTask()).start();

// Method 3: Anonymous class
new Thread(new Runnable() {
    public void run() {
        // Task logic
    }
}).start();

// Method 4: Lambda expression (Java 8+)
new Thread(() -> {
    // Task logic
}).start();
```

### 🔹 Thread States

```java
// Thread lifecycle
NEW → RUNNABLE → RUNNING → BLOCKED/WAITING/TIMED_WAITING → TERMINATED

Thread thread = new Thread(() -> {
    // RUNNABLE state
    try {
        Thread.sleep(1000);  // TIMED_WAITING
        synchronized(this) {
            wait();  // WAITING
        }
    } catch (InterruptedException e) {
        // Handle interruption
    }
});

thread.start();  // Moves to RUNNABLE
// Eventually TERMINATED
```

### 🔹 Best Practices

```java
// Prefer Runnable over extending Thread
Runnable task = () -> System.out.println("Hello from thread");
new Thread(task).start();

// Use thread pools instead of creating threads directly
ExecutorService executor = Executors.newFixedThreadPool(10);
executor.submit(task);

// Handle interruptions properly
public void run() {
    while (!Thread.currentThread().isInterrupted()) {
        // Do work
    }
}
```

### 🔹 Common Pitfalls

```java
// Don't call run() directly - it executes in current thread
Thread thread = new Thread(() -> System.out.println("Hello"));
thread.run();  // Wrong! Executes in main thread
thread.start();  // Correct! Starts new thread

// Don't share mutable state without synchronization
class Counter {
    private int count = 0;
    
    public void increment() {
        count++;  // Race condition!
    }
}
```

---

## 🎯 Interview One-Liner

> Thread is execution unit with its own stack; Runnable is task interface; prefer Runnable for flexibility and reusability; use thread pools over direct thread creation.

---

## ✅ 41. Thread lifecycle in Java

**Thread lifecycle consists of 6 states: NEW, RUNNABLE, RUNNING, BLOCKED, WAITING, TIMED_WAITING, and TERMINATED, managed by the JVM thread scheduler.**

### 🔹 Thread States

**The complete lifecycle of a thread**

```java
// 1. NEW - Thread created but not started
Thread thread = new Thread(() -> System.out.println("Hello"));
System.out.println(thread.getState());  // NEW

// 2. RUNNABLE - Ready to run, waiting for CPU
thread.start();
System.out.println(thread.getState());  // RUNNABLE

// 3. RUNNING - Currently executing
// (Thread is executing the run() method)

// 4. BLOCKED - Waiting for monitor lock
synchronized (lock) {
    // Thread holds lock
}

// 5. WAITING - Waiting indefinitely for another thread
synchronized (lock) {
    lock.wait();  // WAITING state
}

// 6. TIMED_WAITING - Waiting for specified time
Thread.sleep(1000);  // TIMED_WAITING

// 7. TERMINATED - Thread completed execution
// Thread finishes run() method
```

### 🔹 State Transitions

```java
NEW ────── start() ──────► RUNNABLE
    ◄─────────────────────     │
         Thread terminates      │
                               ▼
TERMINATED ◄──────────────── RUNNING
    ▲                          │
    │                          ▼
    └───────── timeout ─── TIMED_WAITING
               notify()        │
               signal          ▼
                          BLOCKED ◄─── acquire lock ───┘
                               │
                               ▼
                          WAITING ◄─── wait() ───┘
```

### 🔹 Detailed State Descriptions

### 🔹 NEW State

**Thread created but start() not called yet**

```java
Thread thread = new Thread(() -> {
    // Code to execute
});

// Thread is in NEW state
// No system resources allocated yet
// Calling methods like sleep() throws IllegalThreadStateException
```

### 🔹 RUNNABLE State

**Thread ready to run, waiting for CPU time**

```java
thread.start();  // Moves to RUNNABLE

// Thread is in runnable pool
// JVM scheduler will pick it for execution
// May move between RUNNABLE and RUNNING based on scheduler
```

### 🔹 RUNNING State

**Thread currently executing**

```java
public void run() {
    // Thread is RUNNING
    System.out.println("Executing...");
    
    // Can move to other states from here
    try {
        Thread.sleep(1000);  // → TIMED_WAITING
    } catch (InterruptedException e) {
        // Handle interruption
    }
}
```

### 🔹 BLOCKED State

**Waiting for monitor lock**

```java
synchronized (lock) {
    // Thread holds the lock - RUNNING
    
    // Another thread tries to enter
    // synchronized block → BLOCKED
}

// BLOCKED threads are not consuming CPU
// Waiting for lock to be released
```

### 🔹 WAITING State

**Waiting indefinitely for notification**

```java
synchronized (lock) {
    lock.wait();  // → WAITING
    // Thread releases lock and waits
}

// To wake up: another thread calls lock.notify() or lock.notifyAll()
```

### 🔹 TIMED_WAITING State

**Waiting for specified time period**

```java
// Common methods that cause TIMED_WAITING:
Thread.sleep(1000);              // Sleep for 1 second
thread.join(5000);               // Wait for thread to finish (max 5 sec)
lock.wait(3000);                 // Wait with timeout
condition.awaitNanos(1000000);   // Wait with nanosecond timeout
```

### 🔹 TERMINATED State

**Thread completed execution**

```java
public void run() {
    System.out.println("Work done");
    // Thread automatically moves to TERMINATED
}

// Cannot be restarted
// Resources are cleaned up by JVM
```

### 🔹 Thread State Methods

```java
// Check thread state
Thread.State state = thread.getState();

// Common state checks
if (thread.getState() == Thread.State.NEW) {
    thread.start();
}

if (thread.isAlive()) {  // Equivalent to !TERMINATED
    // Thread is still running
}

// Wait for thread to complete
thread.join();  // Main thread waits for this thread
thread.join(5000);  // Wait max 5 seconds
```

### 🔹 State Monitoring

```java
// Monitor thread states (for debugging)
Thread thread = new Thread(() -> {
    try {
        System.out.println("State: " + Thread.currentThread().getState());
        Thread.sleep(1000);
        System.out.println("State after sleep: " + Thread.currentThread().getState());
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
});

thread.start();

// Main thread can monitor
while (thread.isAlive()) {
    System.out.println("Thread state: " + thread.getState());
    Thread.sleep(100);
}
```

### 🔹 Best Practices

```java
// Handle InterruptedException properly
public void run() {
    while (!Thread.currentThread().isInterrupted()) {
        try {
            // Do work
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();  // Restore interrupt status
            break;
        }
    }
}

// Use join() to wait for completion
Thread worker = new Thread(task);
worker.start();
worker.join();  // Wait for worker to finish

// Check isAlive() before operations
if (thread.isAlive()) {
    thread.interrupt();
}
```

---

## 🎯 Interview One-Liner

> Thread states: NEW→RUNNABLE→RUNNING→BLOCKED/WAITING/TIMED_WAITING→TERMINATED; managed by JVM scheduler; use getState() to check current state; handle interruptions properly.

---

## ✅ 42. What is synchronized keyword?

**The synchronized keyword ensures that only one thread can execute a synchronized block or method at a time, preventing race conditions and ensuring thread safety.**

### 🔹 Synchronization Basics

**Mutual exclusion for shared resources**

```java
// Synchronized method
public synchronized void increment() {
    counter++;  // Thread-safe
}

// Synchronized block
public void increment() {
    synchronized (this) {
        counter++;  // Thread-safe
    }
}
```

**How it works:**
- **Monitor lock**: Each object has an associated monitor
- **Lock acquisition**: Thread must acquire lock before entering synchronized block
- **Mutual exclusion**: Only one thread can hold the lock at a time
- **Lock release**: Lock automatically released when block/method exits

### 🔹 Synchronized Methods

```java
public class Counter {
    private int count = 0;
    
    // Instance method - locks on 'this'
    public synchronized void increment() {
        count++;
    }
    
    // Static method - locks on Class object
    public static synchronized void staticMethod() {
        // Locks on Counter.class
    }
    
    // Equivalent synchronized block
    public void increment() {
        synchronized (this) {
            count++;
        }
    }
}
```

### 🔹 Synchronized Blocks

**More granular control**

```java
public class BankAccount {
    private double balance = 0;
    private final Object lock = new Object();  // Dedicated lock
    
    public void deposit(double amount) {
        synchronized (lock) {  // Lock on specific object
            balance += amount;
        }
    }
    
    public void withdraw(double amount) {
        synchronized (lock) {
            if (balance >= amount) {
                balance -= amount;
            }
        }
    }
}
```

### 🔹 Types of Locks

```java
// 1. Instance lock
synchronized (this) { }  // Locks current instance

// 2. Class lock  
synchronized (MyClass.class) { }  // Locks class object

// 3. Static method lock
public static synchronized void method() { }  // Same as synchronized (MyClass.class)

// 4. Dedicated lock
private final Object lock = new Object();
synchronized (lock) { }  // Fine-grained control
```

### 🔹 Reentrant Locks

**Same thread can acquire the same lock multiple times**

```java
public synchronized void method1() {
    System.out.println("Method 1");
    method2();  // Same thread can call synchronized method
}

public synchronized void method2() {
    System.out.println("Method 2");
}
```

### 🔹 Synchronization Issues

```java
// Race condition (without synchronization)
public class UnsafeCounter {
    private int count = 0;
    
    public void increment() {
        int temp = count;    // Read
        temp = temp + 1;     // Modify
        count = temp;        // Write
        // Another thread can interfere here
    }
}

// Thread-safe version
public class SafeCounter {
    private int count = 0;
    
    public synchronized void increment() {
        count++;  // Atomic operation
    }
}
```

### 🔹 Performance Considerations

```java
// Synchronization has overhead
// Use synchronized blocks instead of methods for better performance
public void method() {
    // Non-critical code (no synchronization needed)
    synchronized (this) {
        // Only critical section synchronized
    }
    // More non-critical code
}

// Prefer concurrent utilities for complex scenarios
private final AtomicInteger counter = new AtomicInteger(0);
public void increment() {
    counter.incrementAndGet();  // Lock-free
}
```

### 🔹 Common Patterns

```java
// Double-checked locking (Singleton)
public class Singleton {
    private static volatile Singleton instance;
    
    public static Singleton getInstance() {
        if (instance == null) {  // First check
            synchronized (Singleton.class) {
                if (instance == null) {  // Second check
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}

// Synchronized collections
List<String> syncList = Collections.synchronizedList(new ArrayList<>());
syncList.add("item");  // Thread-safe
```

### 🔹 Best Practices

```java
// Keep synchronized blocks small
// Use private final lock objects
// Avoid nested locks (deadlock risk)
// Consider volatile for simple cases
// Use concurrent collections when possible
// Document thread-safety guarantees
```

---

## 🎯 Interview One-Liner

> synchronized ensures mutual exclusion; locks methods/blocks; instance methods lock 'this', static methods lock Class; prevents race conditions but has performance overhead; prefer concurrent utilities for complex scenarios.

---

## ✅ 43. Difference between wait() and sleep()

**wait() and sleep() both pause thread execution, but wait() releases the lock and requires notify() to wake up, while sleep() holds the lock and wakes up automatically after the specified time.**

### 🔹 wait() Method

**Releases lock and waits for notification**

```java
public class ProducerConsumer {
    private final Object lock = new Object();
    private boolean itemAvailable = false;
    
    public void produce() throws InterruptedException {
        synchronized (lock) {
            while (itemAvailable) {
                lock.wait();  // Releases lock, waits for notification
            }
            // Produce item
            itemAvailable = true;
            lock.notify();  // Notify waiting consumer
        }
    }
    
    public void consume() throws InterruptedException {
        synchronized (lock) {
            while (!itemAvailable) {
                lock.wait();  // Releases lock, waits for notification
            }
            // Consume item
            itemAvailable = false;
            lock.notify();  // Notify waiting producer
        }
    }
}
```

**Key characteristics:**
- **Releases lock**: Other threads can enter synchronized block
- **Requires notification**: Must be woken by notify() or notifyAll()
- **Used for coordination**: Between producer/consumer threads
- **Can be interrupted**: Throws InterruptedException

### 🔹 sleep() Method

**Holds lock and sleeps for specified time**

```java
public class Worker {
    public void work() throws InterruptedException {
        synchronized (this) {
            System.out.println("Working...");
            Thread.sleep(1000);  // Holds lock for 1 second
            System.out.println("Work done");
        }
    }
}

// Usage
Worker worker = new Worker();
worker.work();  // Main thread sleeps, holding lock
```

**Key characteristics:**
- **Holds lock**: Other threads cannot enter synchronized block
- **Automatic wakeup**: Wakes up after specified time
- **Used for timing**: Delays, periodic tasks
- **Can be interrupted**: Throws InterruptedException

### 🔹 Key Differences

| Aspect | wait() | sleep() |
|--------|--------|---------|
| **Lock behavior** | Releases lock | Holds lock |
| **Wake up** | notify()/notifyAll() | Automatic after time |
| **Where called** | Only in synchronized block | Anywhere |
| **Exception** | InterruptedException | InterruptedException |
| **Use case** | Thread coordination | Timing delays |
| **Method of** | Object class | Thread class |

### 🔹 wait() Variants

```java
synchronized (lock) {
    lock.wait();          // Wait indefinitely
    lock.wait(5000);      // Wait max 5 seconds
    lock.wait(1000, 500); // Wait 1 second + 500 nanoseconds
}
```

### 🔹 sleep() Variants

```java
Thread.sleep(1000);           // Sleep 1 second
Thread.sleep(1000, 500000);   // Sleep 1 second + 500 microseconds
TimeUnit.SECONDS.sleep(1);     // Sleep 1 second (Java 5+)
```

### 🔹 Common Usage Patterns

```java
// Producer-Consumer with wait/notify
class BlockingQueue<T> {
    private final Queue<T> queue = new LinkedList<>();
    private final int capacity;
    
    public BlockingQueue(int capacity) {
        this.capacity = capacity;
    }
    
    public synchronized void put(T item) throws InterruptedException {
        while (queue.size() == capacity) {
            wait();  // Wait for space
        }
        queue.add(item);
        notifyAll();  // Notify waiting consumers
    }
    
    public synchronized T take() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();  // Wait for item
        }
        T item = queue.remove();
        notifyAll();  // Notify waiting producers
        return item;
    }
}

// Timing with sleep
public void periodicTask() {
    while (!Thread.currentThread().isInterrupted()) {
        try {
            performTask();
            Thread.sleep(5000);  // Wait 5 seconds before next execution
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
```

### 🔹 Best Practices

```java
// Always call wait() in a loop
synchronized (lock) {
    while (conditionNotMet) {
        lock.wait();  // Not if (!condition) lock.wait()
    }
}

// Handle InterruptedException properly
try {
    Thread.sleep(1000);
} catch (InterruptedException e) {
    Thread.currentThread().interrupt();  // Restore interrupt status
}

// Use notifyAll() instead of notify() for safety
// notify() wakes one thread, notifyAll() wakes all
```

---

## 🎯 Interview One-Liner

> wait() releases lock and waits for notify(); sleep() holds lock and wakes automatically; wait() for coordination, sleep() for timing; both throw InterruptedException and must be in try-catch.

---

## ✅ 44. What is ThreadPool? Executor Framework?

**ThreadPool manages a pool of worker threads to execute tasks asynchronously, improving performance by reusing threads. Executor Framework provides high-level APIs for thread management.**

### 🔹 What is ThreadPool?

**Pool of pre-created threads waiting for tasks**

```java
// Manual thread pool implementation
public class SimpleThreadPool {
    private final BlockingQueue<Runnable> taskQueue;
    private final List<Worker> workers;
    private volatile boolean isShutdown;
    
    public SimpleThreadPool(int poolSize) {
        taskQueue = new LinkedBlockingQueue<>();
        workers = new ArrayList<>();
        
        for (int i = 0; i < poolSize; i++) {
            Worker worker = new Worker();
            workers.add(worker);
            worker.thread.start();
        }
    }
    
    public void submit(Runnable task) {
        if (!isShutdown) {
            taskQueue.put(task);
        }
    }
    
    private class Worker implements Runnable {
        private final Thread thread;
        
        Worker() {
            thread = new Thread(this);
        }
        
        public void run() {
            while (!isShutdown) {
                try {
                    Runnable task = taskQueue.take();
                    task.run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
```

### 🔹 Executor Framework

**High-level concurrency API**

```java
// Executor interface
public interface Executor {
    void execute(Runnable command);
}

// ExecutorService extends Executor
public interface ExecutorService extends Executor {
    <T> Future<T> submit(Callable<T> task);
    <T> Future<T> submit(Runnable task, T result);
    Future<?> submit(Runnable task);
    void shutdown();
    List<Runnable> shutdownNow();
    boolean isShutdown();
    boolean isTerminated();
    // ... more methods
}
```

### 🔹 Executor Types

```java
// 1. Fixed Thread Pool
ExecutorService fixedPool = Executors.newFixedThreadPool(5);
fixedPool.execute(() -> System.out.println("Task executed"));

// 2. Cached Thread Pool
ExecutorService cachedPool = Executors.newCachedThreadPool();
// Creates threads as needed, reuses idle threads

// 3. Single Thread Executor
ExecutorService singleThread = Executors.newSingleThreadExecutor();
// Single worker thread, tasks executed sequentially

// 4. Scheduled Thread Pool
ScheduledExecutorService scheduled = Executors.newScheduledThreadPool(2);
scheduled.schedule(() -> System.out.println("Delayed"), 1, TimeUnit.SECONDS);
scheduled.scheduleAtFixedRate(() -> System.out.println("Periodic"), 0, 1, TimeUnit.SECONDS);
```

### 🔹 ThreadPoolExecutor

**Configurable thread pool implementation**

```java
ThreadPoolExecutor executor = new ThreadPoolExecutor(
    2,                          // Core pool size
    4,                          // Maximum pool size
    60, TimeUnit.SECONDS,       // Keep alive time
    new LinkedBlockingQueue<>(10) // Work queue
);

// Custom rejection policy
executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

// Submit tasks
executor.execute(() -> {
    // Task logic
});

// Shutdown
executor.shutdown();
executor.awaitTermination(5, TimeUnit.SECONDS);
```

### 🔹 Thread Pool Parameters

```java
// Core Pool Size: Minimum threads to keep alive
// Maximum Pool Size: Maximum threads allowed
// Keep Alive Time: How long idle threads wait before terminating
// Work Queue: Queue to hold tasks when all threads are busy
// Rejection Policy: What to do when queue is full and max threads reached

// Rejection Policies:
new ThreadPoolExecutor.AbortPolicy()        // Throws RejectedExecutionException
new ThreadPoolExecutor.CallerRunsPolicy()   // Execute in caller's thread
new ThreadPoolExecutor.DiscardPolicy()      // Silently discard task
new ThreadPoolExecutor.DiscardOldestPolicy() // Discard oldest task
```

### 🔹 Benefits of Thread Pools

```java
// Performance: Reuse threads, avoid creation overhead
// Resource management: Control number of concurrent threads
// Task queuing: Handle more tasks than available threads
// Lifecycle management: Proper shutdown and cleanup
// Monitoring: Track pool status and metrics
```

### 🔹 Best Practices

```java
// Choose appropriate pool size
// Core = CPU cores for CPU-bound tasks
// Core = CPU cores * (1 + wait/compute ratio) for I/O-bound tasks

// Always shutdown pools
executor.shutdown();
try {
    if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
        executor.shutdownNow();
    }
} catch (InterruptedException e) {
    executor.shutdownNow();
}

// Handle exceptions in tasks
executor.submit(() -> {
    try {
        riskyOperation();
    } catch (Exception e) {
        // Handle exception
    }
});

// Monitor pool status
int activeCount = executor.getActiveCount();
int poolSize = executor.getPoolSize();
long completedTasks = executor.getCompletedTaskCount();
```

### 🔹 Common Patterns

```java
// Producer-Consumer with Executor
class TaskProcessor {
    private final ExecutorService executor = Executors.newFixedThreadPool(10);
    private final BlockingQueue<Task> taskQueue = new LinkedBlockingQueue<>();
    
    public void start() {
        executor.submit(this::processTasks);
    }
    
    private void processTasks() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Task task = taskQueue.take();
                processTask(task);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    public void submitTask(Task task) {
        taskQueue.put(task);
    }
}
```

---

## 🎯 Interview One-Liner

> ThreadPool reuses threads for better performance; Executor Framework provides Executor/ExecutorService interfaces; use Executors factory methods; choose pool size based on task type; always shutdown properly.

---

## ✅ 45. Explain Callable vs Runnable

**Callable and Runnable both represent tasks to execute, but Callable returns a result and can throw checked exceptions, while Runnable returns void and cannot throw checked exceptions.**

### 🔹 Runnable Interface

**Task that doesn't return a result**

```java
// Runnable interface
@FunctionalInterface
public interface Runnable {
    void run();
}

// Implementation
public class PrintTask implements Runnable {
    @Override
    public void run() {
        System.out.println("Task executed");
    }
}

// Usage with Thread
Thread thread = new Thread(new PrintTask());
thread.start();

// Usage with Executor
ExecutorService executor = Executors.newFixedThreadPool(2);
executor.execute(new PrintTask());
```

**Characteristics:**
- **Return type**: void
- **Exceptions**: Cannot throw checked exceptions
- **Usage**: Thread constructor, Executor.execute()
- **Java 8+**: Lambda support

### 🔹 Callable Interface

**Task that returns a result**

```java
// Callable interface
@FunctionalInterface
public interface Callable<V> {
    V call() throws Exception;
}

// Implementation
public class CalculationTask implements Callable<Integer> {
    private final int number;
    
    public CalculationTask(int number) {
        this.number = number;
    }
    
    @Override
    public Integer call() throws Exception {
        return number * number;  // Return result
    }
}

// Usage with ExecutorService
ExecutorService executor = Executors.newFixedThreadPool(2);
Future<Integer> future = executor.submit(new CalculationTask(5));

try {
    Integer result = future.get();  // Blocks until result available
    System.out.println("Result: " + result);  // Result: 25
} catch (InterruptedException | ExecutionException e) {
    // Handle exceptions
}
```

**Characteristics:**
- **Return type**: Generic type V
- **Exceptions**: Can throw checked exceptions
- **Usage**: ExecutorService.submit()
- **Result**: Accessed via Future

### 🔹 Key Differences

| Aspect | Runnable | Callable |
|--------|----------|----------|
| **Return type** | void | V (generic) |
| **Exceptions** | Unchecked only | Checked allowed |
| **Method name** | run() | call() |
| **Execution** | execute() | submit() |
| **Result access** | None | Future.get() |
| **Thread creation** | new Thread(runnable) | Not directly |

### 🔹 Future Interface

**Represents asynchronous computation result**

```java
// Future methods
public interface Future<V> {
    boolean cancel(boolean mayInterruptIfRunning);
    boolean isCancelled();
    boolean isDone();
    V get() throws InterruptedException, ExecutionException;
    V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException;
}

// Usage examples
Future<Integer> future = executor.submit(callableTask);

// Check if done
if (future.isDone()) {
    Integer result = future.get();
}

// Wait with timeout
try {
    Integer result = future.get(5, TimeUnit.SECONDS);
} catch (TimeoutException e) {
    // Handle timeout
}

// Cancel task
future.cancel(true);  // May interrupt if running
```

### 🔹 Practical Examples

```java
// Runnable for fire-and-forget tasks
executor.execute(() -> {
    sendEmail();
    logActivity();
});

// Callable for computational tasks
Callable<Double> calculation = () -> {
    return performComplexCalculation();
};

Future<Double> future = executor.submit(calculation);
Double result = future.get();

// Callable for I/O operations
Callable<String> httpCall = () -> {
    return restTemplate.getForObject("http://api.example.com/data", String.class);
};

Future<String> response = executor.submit(httpCall);
String data = response.get();
```

### 🔹 Exception Handling

```java
// Runnable - exceptions must be handled internally
Runnable task = () -> {
    try {
        riskyOperation();
    } catch (IOException e) {
        // Handle or log exception
        e.printStackTrace();
    }
};

// Callable - exceptions propagated through Future
Callable<String> task = () -> {
    return riskyOperation();  // Can throw checked exception
};

Future<String> future = executor.submit(task);
try {
    String result = future.get();  // ExecutionException wraps original exception
} catch (ExecutionException e) {
    Throwable cause = e.getCause();  // Get original exception
    // Handle cause
}
```

### 🔹 When to Use Each

```java
// Use Runnable for:
- Fire-and-forget operations
- Tasks that don't need to return results
- Simple background processing
- Implementing Thread by extending Thread class

// Use Callable for:
- Computational tasks that return results
- I/O operations (HTTP calls, file operations)
- Tasks that may throw checked exceptions
- Parallel processing with result aggregation
```

### 🔹 Best Practices

```java
// Prefer Callable for new code when results are needed
// Handle exceptions properly in both cases
// Use appropriate timeouts with Future.get()
// Consider CompletableFuture for complex async operations
// Always shutdown executor services
```

---

## 🎯 Interview One-Liner

> Runnable: void run(), no result, unchecked exceptions; Callable: V call(), returns result via Future, can throw checked exceptions; use Runnable for simple tasks, Callable for results.

---

## ✅ 46. What is Future and CompletableFuture?

**Future represents the result of an asynchronous computation that may not be available yet. CompletableFuture is an enhanced Future with functional programming capabilities and better composition.**

### 🔹 Future Interface

**Basic asynchronous result container**

```java
// Future represents pending result
ExecutorService executor = Executors.newFixedThreadPool(2);

Future<Integer> future = executor.submit(() -> {
    Thread.sleep(1000);  // Simulate work
    return 42;
});

// Check if computation is done
if (future.isDone()) {
    Integer result = future.get();  // Blocks if not done
}

// Wait with timeout
try {
    Integer result = future.get(5, TimeUnit.SECONDS);
} catch (TimeoutException e) {
    // Handle timeout
}

// Cancel computation
future.cancel(true);  // May interrupt if running
```

**Limitations:**
- **Blocking**: get() blocks current thread
- **No composition**: Cannot combine multiple futures easily
- **No callbacks**: Cannot attach completion handlers
- **Limited exception handling**: Only through ExecutionException

### 🔹 CompletableFuture

**Enhanced Future with functional programming**

```java
// Create CompletableFuture
CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
    // Asynchronous computation
    return 42;
});

// Non-blocking result handling
future.thenAccept(result -> {
    System.out.println("Result: " + result);
});

// Chain operations
CompletableFuture<String> result = future
    .thenApply(n -> n * 2)              // Transform result
    .thenApply(n -> "Result: " + n)     // Another transformation
    .whenComplete((s, throwable) -> {   // Handle completion
        if (throwable != null) {
            System.err.println("Error: " + throwable);
        } else {
            System.out.println(s);
        }
    });
```

### 🔹 Creating CompletableFutures

```java
// 1. Completed future
CompletableFuture<String> completed = CompletableFuture.completedFuture("Done");

// 2. Run async task (no result)
CompletableFuture<Void> voidFuture = CompletableFuture.runAsync(() -> {
    // Task without result
});

// 3. Supply async value
CompletableFuture<Integer> supplyFuture = CompletableFuture.supplyAsync(() -> {
    return calculateValue();
});

// 4. Manual completion
CompletableFuture<String> manualFuture = new CompletableFuture<>();
// Later...
manualFuture.complete("Result");
// or
manualFuture.completeExceptionally(new RuntimeException("Error"));
```

### 🔹 Composition Methods

```java
// thenApply - Transform result
CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 10);
CompletableFuture<String> result = future.thenApply(n -> "Value: " + n);

// thenAccept - Consume result (no return)
future.thenAccept(n -> System.out.println("Result: " + n));

// thenRun - Execute after completion (no access to result)
future.thenRun(() -> System.out.println("Done"));

// thenCompose - Chain dependent futures
CompletableFuture<User> userFuture = getUserById(id);
CompletableFuture<Order> orderFuture = userFuture.thenCompose(user -> 
    getOrdersByUser(user));

// thenCombine - Combine two independent futures
CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> 10);
CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> 20);
CompletableFuture<Integer> combined = future1.thenCombine(future2, (a, b) -> a + b);
```

### 🔹 Exception Handling

```java
CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
    if (Math.random() > 0.5) {
        throw new RuntimeException("Random failure");
    }
    return 42;
});

// Handle exceptions
future.exceptionally(throwable -> {
    System.err.println("Error: " + throwable.getMessage());
    return 0;  // Default value
});

// Handle both success and failure
future.whenComplete((result, throwable) -> {
    if (throwable != null) {
        System.err.println("Completed with error: " + throwable);
    } else {
        System.out.println("Completed with result: " + result);
    }
});

// Recover from exceptions
CompletableFuture<Integer> recovered = future
    .exceptionally(throwable -> 0)
    .thenApply(n -> n * 2);
```

### 🔹 Advanced Patterns

```java
// Wait for any of multiple futures
CompletableFuture<Object> anyOf = CompletableFuture.anyOf(future1, future2, future3);

// Wait for all futures to complete
CompletableFuture<Void> allOf = CompletableFuture.allOf(future1, future2, future3);
CompletableFuture<List<Object>> results = allOf.thenApply(v -> 
    Stream.of(future1, future2, future3)
        .map(CompletableFuture::join)
        .collect(Collectors.toList()));

// Timeout handling
CompletableFuture<String> withTimeout = future
    .completeOnTimeout("Default", 5, TimeUnit.SECONDS);

// Manual completion
CompletableFuture<String> manual = new CompletableFuture<>();
// In another thread or callback
manual.complete("Result");
```

### 🔹 Real-World Example

```java
// Parallel API calls
CompletableFuture<User> userFuture = CompletableFuture.supplyAsync(() -> 
    restTemplate.getForObject("/api/user/" + userId, User.class));

CompletableFuture<List<Order>> ordersFuture = CompletableFuture.supplyAsync(() -> 
    restTemplate.getForObject("/api/orders?userId=" + userId, List.class));

// Combine results
CompletableFuture<UserDetails> userDetailsFuture = userFuture.thenCombine(ordersFuture, 
    (user, orders) -> new UserDetails(user, orders));

// Handle result
userDetailsFuture.thenAccept(userDetails -> {
    // Process combined data
    sendEmail(userDetails);
});
```

### 🔹 Best Practices

```java
// Use supplyAsync for computational tasks
// Use runAsync for side effects
// Chain operations instead of blocking
// Handle exceptions at appropriate levels
// Use timeouts to prevent hanging
// Consider thread pools for supplyAsync/runAsync
```

---

## 🎯 Interview One-Liner

> Future: blocking get() for async results; CompletableFuture: non-blocking, functional composition with thenApply/thenAccept, exception handling with exceptionally, combine with thenCombine/allOf.

---

## ✅ 47. What is deadlock? How to prevent it?

**Deadlock occurs when two or more threads are blocked forever, each waiting for the other to release a resource. Prevention requires careful resource ordering and timeout mechanisms.**

### 🔹 What is Deadlock?

**Circular waiting condition**

```java
// Example: Two threads, two locks
public class DeadlockExample {
    private final Object lock1 = new Object();
    private final Object lock2 = new Object();
    
    public void method1() {
        synchronized (lock1) {
            System.out.println("Got lock1 in method1");
            
            // Simulate some work
            try { Thread.sleep(100); } catch (InterruptedException e) {}
            
            synchronized (lock2) {
                System.out.println("Got lock2 in method1");
            }
        }
    }
    
    public void method2() {
        synchronized (lock2) {
            System.out.println("Got lock2 in method2");
            
            // Simulate some work
            try { Thread.sleep(100); } catch (InterruptedException e) {}
            
            synchronized (lock1) {
                System.out.println("Got lock1 in method2");
            }
        }
    }
}

// Usage - will cause deadlock
new Thread(() -> example.method1()).start();
new Thread(() -> example.method2()).start();
```

**Deadlock conditions (Coffman conditions):**
1. **Mutual Exclusion**: Resources cannot be shared
2. **Hold and Wait**: Thread holds one resource while waiting for another
3. **No Preemption**: Resources cannot be forcibly taken
4. **Circular Wait**: Circular chain of threads waiting for resources

### 🔹 Deadlock Prevention Strategies

### 🔹 1. Lock Ordering

**Acquire locks in consistent order**

```java
public class SafeExample {
    private final Object lock1 = new Object();
    private final Object lock2 = new Object();
    
    public void method1() {
        synchronized (lock1) {  // Always acquire lock1 first
            System.out.println("Got lock1 in method1");
            
            synchronized (lock2) {  // Then lock2
                System.out.println("Got lock2 in method1");
            }
        }
    }
    
    public void method2() {
        synchronized (lock1) {  // Always acquire lock1 first
            System.out.println("Got lock1 in method2");
            
            synchronized (lock2) {  // Then lock2
                System.out.println("Got lock2 in method2");
            }
        }
    }
}
```

### 🔹 2. Lock Timeout

**Use tryLock with timeout**

```java
public class TimeoutExample {
    private final Lock lock1 = new ReentrantLock();
    private final Lock lock2 = new ReentrantLock();
    
    public void method1() throws InterruptedException {
        if (lock1.tryLock(1, TimeUnit.SECONDS)) {
            try {
                System.out.println("Got lock1 in method1");
                
                if (lock2.tryLock(1, TimeUnit.SECONDS)) {
                    try {
                        System.out.println("Got lock2 in method1");
                        // Do work
                    } finally {
                        lock2.unlock();
                    }
                } else {
                    System.out.println("Could not get lock2 in method1");
                }
            } finally {
                lock1.unlock();
            }
        }
    }
}
```

### 🔹 3. Deadlock Detection

**Monitor for deadlocks**

```java
// ThreadMXBean for deadlock detection
public class DeadlockDetector {
    private final ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
    
    public void detectDeadlocks() {
        long[] deadlockedThreads = threadMXBean.findDeadlockedThreads();
        if (deadlockedThreads != null) {
            ThreadInfo[] threadInfos = threadMXBean.getThreadInfo(deadlockedThreads);
            for (ThreadInfo threadInfo : threadInfos) {
                System.out.println("Deadlocked thread: " + threadInfo.getThreadName());
            }
        }
    }
    
    // Run periodically
    public void startDetection() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(this::detectDeadlocks, 0, 10, TimeUnit.SECONDS);
    }
}
```

### 🔹 4. Resource Hierarchy

**Order resources by hierarchy**

```java
// Assign each resource a unique number
// Always acquire lower-numbered resources first
public class ResourceHierarchy {
    private static final int RESOURCE_A = 1;
    private static final int RESOURCE_B = 2;
    
    public void useResources() {
        // Always acquire in order: 1, then 2
        acquireResource(RESOURCE_A);
        try {
            acquireResource(RESOURCE_B);
            try {
                // Use both resources
            } finally {
                releaseResource(RESOURCE_B);
            }
        } finally {
            releaseResource(RESOURCE_A);
        }
    }
}
```

### 🔹 5. Avoid Nested Locks

**Minimize lock nesting**

```java
// Bad: Nested synchronized blocks
public synchronized void method1() {
    // Do some work
    synchronized (lock2) {
        // More work - potential deadlock
    }
}

// Better: Reduce lock scope
public void method1() {
    synchronized (this) {
        // Minimal work while holding lock
    }
    
    // Non-critical work outside synchronized block
    synchronized (lock2) {
        // Work with lock2
    }
}
```

### 🔹 6. Use Higher-Level Concurrency

**Atomic operations and concurrent collections**

```java
// Instead of synchronized blocks
private final AtomicInteger counter = new AtomicInteger(0);

public void increment() {
    counter.incrementAndGet();  // No locks, no deadlock
}

// Concurrent collections
private final ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
public void put(String key, String value) {
    map.put(key, value);  // Thread-safe, no external locking
}
```

### 🔹 Best Practices

```java
// Keep synchronized blocks small
// Acquire locks in consistent order
// Use tryLock with timeouts
// Avoid holding locks while calling alien methods
// Document lock ordering requirements
// Test for deadlocks in load testing
// Use deadlock detection in production
```

### 🔹 Common Deadlock Scenarios

```java
// Database deadlocks
// Connection pooling issues
// Synchronized method calling synchronized method
// Lock ordering violations
// Resource leaks holding locks
```

---

## 🎯 Interview One-Liner

> Deadlock: threads waiting forever for resources held by each other; prevent by lock ordering, timeouts, avoiding nested locks; detect with ThreadMXBean; use concurrent utilities to minimize locks.

---

## ✅ 48. Explain volatile keyword

**The volatile keyword ensures that changes to a variable are immediately visible to all threads and prevents compiler optimizations that could cache the variable value.**

### 🔹 Memory Visibility Problem

**Without volatile, threads may see stale values**

```java
public class VisibilityProblem {
    private boolean flag = false;
    
    public void writer() {
        flag = true;  // Writer thread sets flag
    }
    
    public void reader() {
        while (!flag) {  // Reader thread may never see the update
            // Busy wait - infinite loop!
        }
    }
}

// Problem: Compiler may cache 'flag' in register
// Reader thread never sees writer's update
```

### 🔹 Volatile Solution

**Guarantees visibility of changes**

```java
public class VolatileSolution {
    private volatile boolean flag = false;
    
    public void writer() {
        flag = true;  // All threads see this change immediately
    }
    
    public void reader() {
        while (!flag) {  // Will eventually see the update
            // Loop will terminate
        }
    }
}
```

### 🔹 What Volatile Guarantees

```java
// 1. Visibility: Changes visible to all threads immediately
// 2. Prevents reordering: Volatile operations not reordered with each other
// 3. No caching: Variable always read from/written to main memory

volatile int counter = 0;

// Thread 1
counter = 1;

// Thread 2
int local = counter;  // Sees 1, not stale value
```

### 🔹 Volatile vs Synchronized

```java
// Volatile for single variable visibility
private volatile boolean ready = false;

// Synchronized for compound operations
private int counter = 0;
public synchronized void increment() {
    counter++;  // Atomic read-modify-write
}

// Volatile doesn't make compound operations atomic
public void badIncrement() {
    counter++;  // Not atomic - race condition possible
}
```

### 🔹 Use Cases for Volatile

```java
// 1. Status flags
private volatile boolean shutdown = false;

public void shutdown() {
    shutdown = true;
}

public void worker() {
    while (!shutdown) {
        // Do work
    }
}

// 2. Lazy initialization (double-checked locking)
private volatile Singleton instance;

public Singleton getInstance() {
    if (instance == null) {  // First check
        synchronized (Singleton.class) {
            if (instance == null) {  // Second check
                instance = new Singleton();
            }
        }
    }
    return instance;
}

// 3. Progress indicators
private volatile int progress = 0;

public void updateProgress(int newProgress) {
    progress = newProgress;  // Immediately visible to monitoring threads
}
```

### 🔹 Limitations

```java
// Volatile doesn't provide atomicity for compound operations
volatile int counter = 0;

public void increment() {
    counter = counter + 1;  // Not atomic - race condition
    // Equivalent to: int temp = counter; temp = temp + 1; counter = temp;
}

// Solution: Use AtomicInteger
private final AtomicInteger counter = new AtomicInteger(0);

public void increment() {
    counter.incrementAndGet();  // Atomic
}
```

### 🔹 Volatile and Happens-Before

**Memory visibility guarantee**

```java
// Volatile write happens-before all subsequent volatile reads
volatile boolean flag = false;
int data = 0;

// Thread 1
data = 42;
flag = true;  // Volatile write

// Thread 2  
if (flag) {   // Volatile read
    // Guaranteed to see data = 42
    System.out.println(data);
}
```

### 🔹 Performance Considerations

```java
// Volatile has performance cost
// - No caching in registers
// - Memory barrier instructions
// - Prevents some compiler optimizations

// Use only when necessary
// Prefer Atomic* classes for counters
// Consider synchronized for complex operations
```

### 🔹 Best Practices

```java
// Use volatile for:
// - Status flags (shutdown, ready, etc.)
// - One-time initialization flags
// - Variables written by one thread, read by many
// - Progress/status indicators

// Don't use volatile for:
// - Counters (use AtomicInteger)
// - Collections (use concurrent collections)
// - Multiple related variables (use synchronized)
```

---

## 🎯 Interview One-Liner

> volatile ensures visibility of variable changes across threads, prevents caching/reordering; use for flags/status, not for atomic compound operations; Atomic* classes for counters.

---

## ✅ 49. What is AtomicInteger?

**AtomicInteger provides thread-safe operations on an int value without external synchronization, using low-level atomic operations instead of locks.**

### 🔹 Basic Usage

**Thread-safe integer operations**

```java
import java.util.concurrent.atomic.AtomicInteger;

public class Counter {
    private final AtomicInteger counter = new AtomicInteger(0);
    
    public void increment() {
        counter.incrementAndGet();  // Thread-safe increment
    }
    
    public int getValue() {
        return counter.get();  // Thread-safe read
    }
    
    public void reset() {
        counter.set(0);  // Thread-safe write
    }
}
```

### 🔹 Key Methods

```java
AtomicInteger atomic = new AtomicInteger(10);

// Get current value
int value = atomic.get();  // 10

// Set new value
atomic.set(20);

// Atomically set and get old value
int oldValue = atomic.getAndSet(30);  // Returns 20, sets to 30

// Increment and get new value
int newValue = atomic.incrementAndGet();  // Returns 31

// Get and increment
int current = atomic.getAndIncrement();  // Returns 30, then increments to 31

// Decrement operations
atomic.decrementAndGet();  // Decrement and return new value
atomic.getAndDecrement();  // Return current, then decrement

// Add value
atomic.addAndGet(5);  // Add 5 and return new value (36)
atomic.getAndAdd(5);  // Return current (31), then add 5
```

### 🔹 Compare-and-Set Operations

**Conditional updates**

```java
AtomicInteger atomic = new AtomicInteger(10);

// compareAndSet(expected, newValue)
boolean success = atomic.compareAndSet(10, 20);  // true, value becomes 20
success = atomic.compareAndSet(10, 30);          // false, value still 20

// Weak version (may fail spuriously)
atomic.weakCompareAndSet(20, 30);  // May or may not succeed
```

### 🔹 How It Works

**Uses CAS (Compare-And-Swap) operations**

```java
// AtomicInteger uses sun.misc.Unsafe or VarHandle (Java 9+)
// CAS operation: compare memory value with expected, swap if equal

// Pseudocode for incrementAndGet():
public int incrementAndGet() {
    for (;;) {
        int current = get();           // Read current value
        int next = current + 1;        // Calculate new value
        
        if (compareAndSet(current, next)) {  // Atomic CAS
            return next;               // Success
        }
        // CAS failed (another thread changed value), retry
    }
}
```

### 🔹 Performance Benefits

```java
// AtomicInteger vs synchronized
private int syncCounter = 0;
private AtomicInteger atomicCounter = new AtomicInteger(0);

// Synchronized version (locks)
public synchronized void syncIncrement() {
    syncCounter++;
}

// Atomic version (lock-free)
public void atomicIncrement() {
    atomicCounter.incrementAndGet();
}

// Atomic is faster for high contention
// No context switching, no lock inflation
```

### 🔹 Other Atomic Classes

```java
// AtomicBoolean
AtomicBoolean flag = new AtomicBoolean(false);
flag.compareAndSet(false, true);

// AtomicLong
AtomicLong timestamp = new AtomicLong(System.currentTimeMillis());
long current = timestamp.getAndUpdate(prev -> Math.max(prev, System.currentTimeMillis()));

// AtomicReference
AtomicReference<String> reference = new AtomicReference<>("initial");
reference.compareAndSet("initial", "updated");

// AtomicIntegerArray, AtomicLongArray, AtomicReferenceArray
AtomicIntegerArray array = new AtomicIntegerArray(10);
array.incrementAndGet(5);  // Atomic update at index 5
```

### 🔹 Use Cases

```java
// Counters
private final AtomicInteger requestCount = new AtomicInteger(0);

public void handleRequest() {
    int count = requestCount.incrementAndGet();
    // Process request
}

// ID generators
private final AtomicInteger idGenerator = new AtomicInteger(1);

public int generateId() {
    return idGenerator.getAndIncrement();
}

// Flags
private final AtomicBoolean initialized = new AtomicBoolean(false);

public void initialize() {
    if (initialized.compareAndSet(false, true)) {
        // Initialize only once
        doInitialization();
    }
}
```

### 🔹 Limitations

```java
// Atomic operations are per variable
// Cannot make multiple variables atomic together
private AtomicInteger x = new AtomicInteger(0);
private AtomicInteger y = new AtomicInteger(0);

// Not atomic together
public void updateBoth() {
    x.incrementAndGet();
    y.incrementAndGet();
    // Another thread might see inconsistent state
}

// Solution: Use synchronized or AtomicReference with custom class
private final AtomicReference<Point> point = new AtomicReference<>(new Point(0, 0));

public void updatePoint(int newX, int newY) {
    point.getAndUpdate(p -> new Point(newX, newY));
}
```

### 🔹 Best Practices

```java
// Use Atomic* classes instead of synchronized for simple operations
// Prefer AtomicInteger over volatile int for counters
// Use compareAndSet for conditional updates
// Consider AtomicReference for complex state
// Profile performance - atomics may not always be faster than locks
```

---

## 🎯 Interview One-Liner

> AtomicInteger provides lock-free thread-safe int operations using CAS; methods like incrementAndGet(), compareAndSet(); faster than synchronized for high contention; use for counters, IDs, flags.

---

## ✅ 50. CountDownLatch vs CyclicBarrier

**CountDownLatch allows threads to wait until a count reaches zero, used for one-time coordination. CyclicBarrier allows threads to wait for each other at a barrier point, reusable for multiple cycles.**

### 🔹 CountDownLatch

**One-time countdown coordination**

```java
import java.util.concurrent.CountDownLatch;

public class CountDownLatchExample {
    private static final int THREAD_COUNT = 3;
    
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
        
        // Start worker threads
        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread(() -> {
                try {
                    System.out.println("Worker thread doing work");
                    Thread.sleep(1000);  // Simulate work
                    latch.countDown();   // Decrement count
                    System.out.println("Worker thread finished");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }
        
        // Main thread waits for all workers to finish
        latch.await();  // Blocks until count reaches 0
        System.out.println("All workers finished, main thread continues");
    }
}
```

**Characteristics:**
- **One-time use**: Cannot be reset
- **Count down**: Threads call countDown() to decrement
- **Wait**: One or more threads call await() to wait
- **Use case**: Start signal, completion coordination

### 🔹 CyclicBarrier

**Reusable barrier synchronization**

```java
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierExample {
    private static final int THREAD_COUNT = 3;
    
    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(THREAD_COUNT, () -> {
            System.out.println("All threads reached barrier, proceeding to next phase");
        });
        
        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread(() -> {
                try {
                    System.out.println("Phase 1 work");
                    Thread.sleep(1000);
                    barrier.await();  // Wait for all threads
                    
                    System.out.println("Phase 2 work");
                    Thread.sleep(1000);
                    barrier.await();  // Wait again
                    
                    System.out.println("Phase 3 work");
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }
    }
}
```

**Characteristics:**
- **Reusable**: Can be used multiple times
- **Barrier**: All threads must reach await() before any can proceed
- **Optional action**: Runnable executed when all threads reach barrier
- **Use case**: Multi-phase algorithms, parallel computations

### 🔹 Key Differences

| Aspect | CountDownLatch | CyclicBarrier |
|--------|----------------|---------------|
| **Reusability** | One-time use | Reusable |
| **Count direction** | Count down to 0 | Fixed count, threads wait |
| **Waiting threads** | Any number can wait | Exactly the barrier count |
| **Use case** | Start/completion sync | Multi-phase coordination |
| **Reset capability** | No | Yes (implicitly) |

### 🔹 CountDownLatch Methods

```java
CountDownLatch latch = new CountDownLatch(3);

// Decrement count
latch.countDown();

// Wait for count to reach zero
latch.await();  // Wait indefinitely
latch.await(5, TimeUnit.SECONDS);  // Wait with timeout

// Check current count
long count = latch.getCount();
```

### 🔹 CyclicBarrier Methods

```java
CyclicBarrier barrier = new CyclicBarrier(3);

// Wait at barrier
barrier.await();  // Wait indefinitely
barrier.await(5, TimeUnit.SECONDS);  // Wait with timeout

// Get number of parties waiting
int waiting = barrier.getNumberWaiting();

// Get total parties
int parties = barrier.getParties();

// Reset barrier (not commonly used)
barrier.reset();
```

### 🔹 Use Cases

```java
// CountDownLatch examples

// 1. Service initialization
CountDownLatch initLatch = new CountDownLatch(3);
startDatabase(initLatch::countDown);
startCache(initLatch::countDown);
startWorkers(initLatch::countDown);
initLatch.await();  // Wait for all services to start

// 2. Test coordination
CountDownLatch testLatch = new CountDownLatch(1);
startTest(testLatch);
testLatch.await();  // Wait for test to complete

// CyclicBarrier examples

// 1. Parallel computation phases
CyclicBarrier phaseBarrier = new CyclicBarrier(4);
for (int i = 0; i < 4; i++) {
    new Thread(() -> {
        computePhase1();
        phaseBarrier.await();
        computePhase2();
        phaseBarrier.await();
        computePhase3();
    }).start();
}

// 2. Multi-threaded testing
CyclicBarrier testBarrier = new CyclicBarrier(5, () -> 
    System.out.println("All threads ready, starting test"));
```

### 🔹 Exception Handling

```java
// Both can throw InterruptedException and BrokenBarrierException

try {
    latch.await();
} catch (InterruptedException e) {
    Thread.currentThread().interrupt();
    // Handle interruption
}

try {
    barrier.await();
} catch (InterruptedException e) {
    Thread.currentThread().interrupt();
} catch (BrokenBarrierException e) {
    // Barrier was broken (thread reset or interrupted)
    // Handle barrier break
}
```

### 🔹 Performance Considerations

```java
// CountDownLatch: Lightweight, good for one-time events
// CyclicBarrier: More overhead due to reusability and barrier action

// Choose based on needs:
// - One-time coordination → CountDownLatch
// - Repeated synchronization → CyclicBarrier
```

### 🔹 Best Practices

```java
// Use CountDownLatch for:
// - Service startup coordination
// - Test synchronization
// - One-time completion waiting

// Use CyclicBarrier for:
// - Multi-phase algorithms
// - Parallel processing steps
// - Coordinating multiple threads at specific points

// Always handle exceptions properly
// Consider timeouts to prevent hanging
```

---

## 🎯 Interview One-Liner

> CountDownLatch: one-time countdown, threads wait for count to reach zero; CyclicBarrier: reusable barrier, all threads wait for each other; CountDownLatch for start/completion, CyclicBarrier for phases.