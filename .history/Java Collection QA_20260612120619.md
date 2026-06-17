
### **Collections Framework**

## ✅ 31. Explain Collection hierarchy

**The Java Collections Framework provides a unified architecture for storing and manipulating groups of objects. It consists of interfaces, implementations, and algorithms that work together.**

### 🔹 Core Interfaces

**The foundation of the framework**

```java
// Collection (root interface)
interface Collection<E> {
    boolean add(E e);
    boolean remove(Object o);
    int size();
    boolean isEmpty();
    Iterator<E> iterator();
    // ... more methods
}

// List - ordered collection, allows duplicates
interface List<E> extends Collection<E> {
    E get(int index);
    E set(int index, E element);
    void add(int index, E element);
    E remove(int index);
}

// Set - no duplicates, unordered (except TreeSet)
interface Set<E> extends Collection<E> {
    // No additional methods, just contract
}

// Queue - FIFO data structure
interface Queue<E> extends Collection<E> {
    boolean offer(E e);    // Add element
    E poll();              // Remove and return head
    E peek();              // Return head without removing
}

// Map - key-value pairs
interface Map<K,V> {
    V put(K key, V value);
    V get(Object key);
    V remove(Object key);
    boolean containsKey(Object key);
    Set<K> keySet();
    Collection<V> values();
    Set<Map.Entry<K,V>> entrySet();
}
```

### 🔹 Collection Hierarchy

```
Iterable (root)
├── Collection
│   ├── List
│   │   ├── ArrayList
│   │   ├── LinkedList
│   │   ├── Vector
│   │   └── Stack
│   ├── Set
│   │   ├── HashSet
│   │   ├── LinkedHashSet
│   │   ├── TreeSet
│   │   └── EnumSet
│   └── Queue
│       ├── PriorityQueue
│       ├── ArrayDeque
│       └── LinkedList (also implements Queue)
└── Map (doesn't extend Collection)
    ├── HashMap
    ├── LinkedHashMap
    ├── TreeMap
    ├── Hashtable
    └── ConcurrentHashMap
```

### 🔹 Key Characteristics

| Interface | Ordered | Allows Duplicates | Allows Null | Thread-Safe |
|-----------|---------|-------------------|-------------|-------------|
| **List** | ✅ | ✅ | ✅ | ❌ (except Vector) |
| **Set** | ❌ (except TreeSet) | ❌ | ✅ (except TreeSet) | ❌ |
| **Queue** | ✅ (FIFO) | ✅ | ✅ | ❌ |
| **Map** | ❌ (except TreeMap) | Keys: ❌, Values: ✅ | Keys: ✅, Values: ✅ | ❌ (except Hashtable) |

### 🔹 Common Implementations

```java
// Lists
List<String> arrayList = new ArrayList<>();     // Fast random access
List<String> linkedList = new LinkedList<>();   // Fast insertions/deletions
List<String> vector = new Vector<>();           // Synchronized ArrayList

// Sets
Set<String> hashSet = new HashSet<>();          // Fast lookup, no order
Set<String> linkedHashSet = new LinkedHashSet<>(); // Insertion order
Set<String> treeSet = new TreeSet<>();          // Sorted order

// Maps
Map<String, Integer> hashMap = new HashMap<>(); // Fast lookup
Map<String, Integer> linkedHashMap = new LinkedHashMap<>(); // Insertion order
Map<String, Integer> treeMap = new TreeMap<>(); // Sorted keys
```

### 🔹 Utility Classes

```java
// Collections utility class
Collections.sort(list);
Collections.reverse(list);
Collections.shuffle(list);
Collections.unmodifiableList(list);

// Arrays utility class
String[] array = {"a", "b", "c"};
List<String> list = Arrays.asList(array);
```

---

## 🎯 Interview One-Liner

> Java Collections hierarchy: Collection (List, Set, Queue) and Map interfaces; implementations like ArrayList (fast access), LinkedList (fast modifications), HashMap (fast lookup), TreeSet (sorted); choose based on ordering, duplicates, and performance needs.

---

## ✅ 32. ArrayList vs LinkedList - when to use which?

**ArrayList and LinkedList are both List implementations but with different performance characteristics. ArrayList uses dynamic arrays while LinkedList uses doubly-linked nodes.**

### 🔹 ArrayList Internal Structure

**Dynamic array that grows automatically**

```java
public class ArrayList<E> {
    private Object[] elementData;  // Array to store elements
    private int size;              // Current number of elements
    
    // Constructor
    public ArrayList() {
        elementData = new Object[10]; // Default capacity
    }
    
    // Add element
    public boolean add(E e) {
        ensureCapacity(size + 1);  // Grow array if needed
        elementData[size++] = e;
        return true;
    }
    
    // Get element - O(1)
    public E get(int index) {
        return (E) elementData[index];
    }
}
```

**Performance:**
- **get(int index)**: O(1) - Direct array access
- **add(E element)**: O(1) amortized - Usually fast, occasional array copy
- **add(int index, E element)**: O(n) - Shift elements
- **remove(int index)**: O(n) - Shift elements

### 🔹 LinkedList Internal Structure

**Doubly-linked list of nodes**

```java
public class LinkedList<E> {
    private Node<E> first;  // First node
    private Node<E> last;   // Last node
    private int size;
    
    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;
        
        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
    
    // Add to end - O(1)
    public boolean add(E e) {
        final Node<E> l = last;
        final Node<E> newNode = new Node<>(l, e, null);
        last = newNode;
        if (l == null)
            first = newNode;
        else
            l.next = newNode;
        size++;
        return true;
    }
}
```

**Performance:**
- **get(int index)**: O(n) - Traverse from start/end
- **add(E element)**: O(1) - Add to end
- **add(int index, E element)**: O(n) - Find position, then O(1) insertion
- **remove(int index)**: O(n) - Find position, then O(1) removal

### 🔹 When to Use ArrayList

```java
// Best for:
// - Frequent random access by index
// - Few insertions/deletions in middle
// - Most common use case

List<String> names = new ArrayList<>();
names.add("John");     // Fast
names.add("Jane");     // Fast
String first = names.get(0);  // Very fast - O(1)
```

### 🔹 When to Use LinkedList

```java
// Best for:
// - Frequent insertions/deletions anywhere
// - Queue/Deque operations
// - Don't need random access

LinkedList<String> queue = new LinkedList<>();
queue.addFirst("First");    // Fast - O(1)
queue.addLast("Last");      // Fast - O(1)
queue.removeFirst();        // Fast - O(1)
```

### 🔹 Memory Usage

```java
// ArrayList: Stores elements only
// Memory = element references + array overhead

// LinkedList: Stores elements + node references
// Memory = element references + 2 node references per element + node overhead
// ~2-3x more memory than ArrayList
```

### 🔹 Performance Comparison

| Operation | ArrayList | LinkedList |
|-----------|-----------|------------|
| **get(index)** | O(1) | O(n) |
| **add(element)** | O(1) amortized | O(1) |
| **add(index, element)** | O(n) | O(n) |
| **remove(index)** | O(n) | O(n) |
| **Memory** | Lower | Higher |

### 🔹 Real-World Usage

```java
// Use ArrayList for:
List<User> users = new ArrayList<>();  // Database results
for (User user : users) {              // Iterate through
    process(user);
}

// Use LinkedList for:
LinkedList<Task> taskQueue = new LinkedList<>();
taskQueue.addFirst(urgentTask);        // Priority tasks
taskQueue.removeLast();                // Process completed
```

---

## 🎯 Interview One-Liner

> ArrayList for fast random access (O(1) get) and memory efficiency; LinkedList for frequent insertions/deletions (O(1) add/remove) and queue operations; choose based on access patterns and memory constraints.

---

## ✅ 33. HashMap internal working (Java 8+)

**HashMap stores key-value pairs using hashing for fast lookup. Java 8+ introduced performance improvements with tree structures for handling hash collisions.**

### 🔹 Basic Structure

**Array of buckets containing linked lists (or trees)**

```java
public class HashMap<K,V> {
    static final int DEFAULT_INITIAL_CAPACITY = 16;  // Default size
    static final float DEFAULT_LOAD_FACTOR = 0.75f;  // When to resize
    
    transient Node<K,V>[] table;  // Array of buckets
    transient int size;           // Number of key-value pairs
    
    // Node structure (linked list)
    static class Node<K,V> {
        final int hash;
        final K key;
        V value;
        Node<K,V> next;  // Next node in bucket
        
        Node(int hash, K key, V value, Node<K,V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
}
```

### 🔹 Hashing Process

**Convert key to bucket index**

```java
// Step 1: Calculate hash code
int hash = key.hashCode();

// Step 2: Apply secondary hashing (spread bits)
int h = hash ^ (hash >>> 16);  // In Java 8+

// Step 3: Calculate bucket index
int index = (table.length - 1) & h;  // Modulo operation using AND

// Example:
key = "John"
hashCode() = 2314534
h = 2314534 ^ (2314534 >>> 16) = 2314534 ^ 35 = 2314569
table.length = 16
index = 15 & 2314569 = 9  // Bucket 9
```

### 🔹 Put Operation

```java
public V put(K key, V value) {
    // Calculate hash and index
    int hash = hash(key);
    int index = (table.length - 1) & hash;
    
    // Check if bucket exists
    if (table[index] == null) {
        // Create new node
        table[index] = new Node<>(hash, key, value, null);
    } else {
        // Handle collision - traverse linked list/tree
        Node<K,V> node = table[index];
        while (node != null) {
            if (node.hash == hash && 
                (node.key == key || key.equals(node.key))) {
                // Key exists - update value
                V oldValue = node.value;
                node.value = value;
                return oldValue;
            }
            node = node.next;
        }
        // Key doesn't exist - add to list/tree
        // (actual implementation more complex)
    }
    
    // Check if resize needed
    if (++size > threshold) {
        resize();
    }
    
    return null;
}
```

### 🔹 Collision Handling

**Java 8+ improvement: Tree instead of long linked lists**

```java
// Before Java 8: Linked list for all collisions
bucket: Node1 -> Node2 -> Node3 -> Node4 -> Node5

// Java 8+: Tree when list gets long (TREEIFY_THRESHOLD = 8)
if (binCount >= TREEIFY_THRESHOLD) {
    treeifyBin(table, hash);
}

// Tree structure for fast lookup
TreeNode structure maintains sorted order
```

### 🔹 Get Operation

```java
public V get(Object key) {
    int hash = hash(key);
    int index = (table.length - 1) & hash;
    
    Node<K,V> node = table[index];
    while (node != null) {
        if (node.hash == hash && 
            (node.key == key || key.equals(node.key))) {
            return node.value;
        }
        node = node.next;
    }
    return null;
}
```

### 🔹 Resize Operation

**Grow table when load factor exceeded**

```java
// Load factor = 0.75 (size / capacity)
// When size > 16 * 0.75 = 12, resize to 32

void resize() {
    Node<K,V>[] oldTable = table;
    int oldCapacity = oldTable.length;
    int newCapacity = oldCapacity << 1;  // Double size
    
    Node<K,V>[] newTable = new Node[newCapacity];
    
    // Rehash all entries
    for (Node<K,V> node : oldTable) {
        while (node != null) {
            // Recalculate index for new table
            int newIndex = (newCapacity - 1) & node.hash;
            // Move to new table
            node = node.next;
        }
    }
    
    table = newTable;
    threshold = (int)(newCapacity * loadFactor);
}
```

### 🔹 Key Points

```java
// HashMap is not thread-safe
// null keys/values allowed (stored in bucket 0)
// Initial capacity: 16, load factor: 0.75
// Resize: capacity doubles, all entries rehashed
// Java 8: Tree bins for collision chains > 8 nodes
// Performance: O(1) average, O(log n) worst case
```

### 🔹 Common Interview Questions

```java
// Why initial capacity power of 2?
// - Faster modulo with AND operation
// - (length-1) creates mask: 15 (1111), 31 (11111)

// Why load factor 0.75?
// - Balance between space and time
// - Higher: more collisions, slower
// - Lower: more space, faster but wasteful

// Why tree when 8 nodes?
// - Empirical testing showed tree faster than long linked list
// - Untree when 6 nodes (UNTREEIFY_THRESHOLD)
```

---

## 🎯 Interview One-Liner

> HashMap uses array of buckets with linked lists/trees; hash(key) → index via (length-1) & hash; Java 8+ trees for collisions > 8; O(1) average lookup; resizes at 75% load factor.

---

## ✅ 34. ConcurrentHashMap vs Hashtable vs Collections.synchronizedMap()

**All provide thread-safe Map operations but with different approaches, performance characteristics, and use cases.**

### 🔹 Hashtable

**Legacy synchronized Map**

```java
public class Hashtable<K,V> extends Dictionary<K,V> implements Map<K,V> {
    // All methods synchronized
    public synchronized V put(K key, V value) {
        // Entire table locked during operation
        // Only one thread can access at a time
    }
    
    public synchronized V get(Object key) {
        // Entire table locked during read
    }
}
```

**Characteristics:**
- All methods synchronized
- Single lock for entire table
- Thread-safe but poor performance
- Doesn't allow null keys/values
- Legacy class from Java 1.0

### 🔹 Collections.synchronizedMap()

**Wrapper that synchronizes existing Map**

```java
public static <K,V> Map<K,V> synchronizedMap(Map<K,V> m) {
    return new SynchronizedMap<>(m);
}

private static class SynchronizedMap<K,V> implements Map<K,V> {
    private final Map<K,V> m;
    
    public synchronized V put(K key, V value) {
        return m.put(key, value);
    }
    
    public synchronized V get(Object key) {
        return m.get(key);
    }
}
```

**Characteristics:**
- Single lock for entire Map
- All operations serialized
- Better than Hashtable (can use any Map implementation)
- Still poor concurrent performance

### 🔹 ConcurrentHashMap

**Segmented locking for better concurrency**

```java
public class ConcurrentHashMap<K,V> extends AbstractMap<K,V> implements ConcurrentMap<K,V> {
    
    // Java 8+: Node array with CAS operations
    transient volatile Node<K,V>[] table;
    
    // Put operation - only locks affected segment
    public V put(K key, V value) {
        int hash = spread(key.hashCode());
        int binCount = 0;
        
        for (Node<K,V>[] tab = table;;) {
            // CAS operations for thread-safety
            // Only locks specific bucket, not entire map
        }
    }
    
    // Get operation - no locking needed
    public V get(Object key) {
        Node<K,V>[] tab; 
        int hash = spread(key.hashCode());
        
        // Volatile reads - thread-safe without locks
        // Multiple threads can read simultaneously
    }
}
```

### 🔹 Performance Comparison

| Operation | Hashtable | synchronizedMap() | ConcurrentHashMap |
|-----------|-----------|-------------------|-------------------|
| **Read** | Slow (locked) | Slow (locked) | Fast (no locks) |
| **Write** | Slow (locked) | Slow (locked) | Fast (segmented locks) |
| **Iteration** | Fail-fast | Fail-fast | Weakly consistent |
| **Scalability** | Poor | Poor | Excellent |

### 🔹 Thread Safety Mechanisms

```java
// Hashtable: synchronized methods
public synchronized V get(Object key)

// synchronizedMap: synchronized wrapper
synchronized(map) { map.get(key); }

// ConcurrentHashMap: CAS + volatile
volatile Node<K,V>[] table;  // Volatile for visibility
CAS operations for atomic updates
```

### 🔹 When to Use Each

```java
// Use Hashtable: Legacy code, simple cases
Hashtable<String, String> legacyMap = new Hashtable<>();

// Use synchronizedMap: Need thread-safe wrapper for existing Map
Map<String, String> syncMap = Collections.synchronizedMap(new HashMap<>());  

// Use ConcurrentHashMap: High concurrency, frequent reads
ConcurrentHashMap<String, String> concurrentMap = new ConcurrentHashMap<>();
```

### 🔹 ConcurrentHashMap Features

```java
// Atomic operations
concurrentMap.putIfAbsent(key, value);
concurrentMap.computeIfAbsent(key, k -> computeValue(k));

// Bulk operations
concurrentMap.forEach((k,v) -> process(k,v));  // Parallel processing

// Size calculation
int size = concurrentMap.size();  // Approximate, no locking
long mappingCount = concurrentMap.mappingCount();  // Java 8+
```

### 🔹 Best Practices

```java
// Prefer ConcurrentHashMap for new code
// Use synchronizedMap only if you need specific Map implementation
// Avoid Hashtable in new code
// Consider ConcurrentSkipListMap for sorted concurrent Map
```

---

## 🎯 Interview One-Liner

> Hashtable: synchronized methods, poor performance; synchronizedMap: wrapper with single lock; ConcurrentHashMap: segmented locking, excellent concurrency; prefer ConcurrentHashMap for high-performance thread-safe Maps.

---

## ✅ 35. What is fail-fast and fail-safe iterators?

**Fail-fast iterators throw ConcurrentModificationException when collection is modified during iteration, while fail-safe iterators work on a copy/clone and don't throw exceptions.**

### 🔹 Fail-Fast Iterator

**Throws exception on concurrent modification**

```java
List<String> list = new ArrayList<>();
list.add("A");
list.add("B");
list.add("C");

Iterator<String> iterator = list.iterator();
while (iterator.hasNext()) {
    String element = iterator.next();
    System.out.println(element);
    
    // This will throw ConcurrentModificationException
    if ("B".equals(element)) {
        list.add("D");  // Modification during iteration
    }
}

// Exception: java.util.ConcurrentModificationException
```

**How it works:**
```java
// ArrayList iterator checks for modifications
private class Itr implements Iterator<E> {
    int expectedModCount = modCount;  // Snapshot of modification count
    
    public E next() {
        checkForComodification();  // Check before each operation
        // ...
    }
    
    final void checkForComodification() {
        if (modCount != expectedModCount)
            throw new ConcurrentModificationException();
    }
}
```

**Fail-fast collections:**
- ArrayList, LinkedList, HashSet, HashMap, etc.
- Most standard collections

### 🔹 Fail-Safe Iterator

**Works on copy, ignores modifications**

```java
Map<String, String> map = new ConcurrentHashMap<>();
map.put("A", "1");
map.put("B", "2");
map.put("C", "3");

Iterator<String> iterator = map.keySet().iterator();
while (iterator.hasNext()) {
    String key = iterator.next();
    System.out.println(key + " = " + map.get(key));
    
    // This is safe - no exception
    if ("B".equals(key)) {
        map.put("D", "4");  // Modification during iteration
    }
}

// Output: A=1, B=2, C=3, D=4 (may or may not show D)
```

**How it works:**
```java
// ConcurrentHashMap iterator works on snapshot/copy
public Iterator<K> iterator() {
    return new KeyIterator();
}

private class KeyIterator implements Iterator<K> {
    // Works on a copy or uses weak consistency
    // Modifications don't affect iteration
}
```

**Fail-safe collections:**
- ConcurrentHashMap, ConcurrentSkipListMap
- CopyOnWriteArrayList, CopyOnWriteArraySet

### 🔹 Key Differences

| Aspect | Fail-Fast | Fail-Safe |
|--------|-----------|-----------|
| **Exception** | ConcurrentModificationException | No exception |
| **Performance** | Fast | Slower (copy overhead) |
| **Memory** | Low | Higher (copy/clone) |
| **Consistency** | Strong | Weak/eventual |
| **Use Case** | Single-threaded | Multi-threaded |

### 🔹 When to Use

```java
// Fail-fast: Single-threaded environments
List<String> list = new ArrayList<>();
for (String item : list) {
    // Safe as long as no other thread modifies
}

// Fail-safe: Multi-threaded environments  
ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
for (String key : map.keySet()) {
    // Safe even if other threads modify
}
```

### 🔹 Avoiding Fail-Fast Exceptions

```java
// Method 1: Use iterator's remove method
Iterator<String> it = list.iterator();
while (it.hasNext()) {
    String element = it.next();
    if (shouldRemove(element)) {
        it.remove();  // Safe removal
    }
}

// Method 2: Collect modifications, apply later
List<String> toRemove = new ArrayList<>();
for (String element : list) {
    if (shouldRemove(element)) {
        toRemove.add(element);
    }
}
list.removeAll(toRemove);

// Method 3: Use fail-safe collection
CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
```

### 🔹 Real-World Scenarios

```java
// Fail-fast: Good for detecting bugs in single-threaded code
// Fail-safe: Good for concurrent environments where you want to continue iteration despite modifications

// Example: Cache cleanup
for (CacheEntry entry : cache.values()) {  // Fail-fast
    if (entry.isExpired()) {
        cache.remove(entry.getKey());  // Will throw exception
    }
}

// Better: Use iterator remove
Iterator<CacheEntry> it = cache.values().iterator();
while (it.hasNext()) {
    CacheEntry entry = it.next();
    if (entry.isExpired()) {
        it.remove();  // Safe
    }
}
```

---

## 🎯 Interview One-Liner

> Fail-fast iterators (ArrayList) throw ConcurrentModificationException on modification during iteration; fail-safe iterators (ConcurrentHashMap) work on copy/clone and allow modifications; choose based on threading and consistency needs.

---

## ✅ 36. Comparable vs Comparator

**Comparable and Comparator both enable sorting, but Comparable is implemented by the class itself for natural ordering, while Comparator is external for custom ordering.**

### 🔹 Comparable Interface

**Natural ordering within the class**

```java
public class Employee implements Comparable<Employee> {
    private String name;
    private int salary;
    
    @Override
    public int compareTo(Employee other) {
        // Natural order: by salary ascending
        return Integer.compare(this.salary, other.salary);
    }
}

// Usage
List<Employee> employees = Arrays.asList(
    new Employee("John", 50000),
    new Employee("Jane", 60000),
    new Employee("Bob", 40000)
);

Collections.sort(employees);  // Uses compareTo()
// Result: Bob(40000), John(50000), Jane(60000)
```

**Characteristics:**
- Single natural ordering
- Implemented by the class itself
- Method: `compareTo(T other)`
- Returns: negative (less), 0 (equal), positive (greater)

### 🔹 Comparator Interface

**External comparison logic**

```java
public class Employee {
    private String name;
    private int salary;
    
    // Getters...
}

// Comparator for salary (ascending)
Comparator<Employee> salaryComparator = new Comparator<Employee>() {
    @Override
    public int compare(Employee e1, Employee e2) {
        return Integer.compare(e1.getSalary(), e2.getSalary());
    }
};

// Comparator for name (alphabetical)
Comparator<Employee> nameComparator = new Comparator<Employee>() {
    @Override
    public int compare(Employee e1, Employee e2) {
        return e1.getName().compareTo(e2.getName());
    }
};

// Usage
Collections.sort(employees, salaryComparator);  // By salary
Collections.sort(employees, nameComparator);    // By name
```

**Characteristics:**
- Multiple comparison strategies
- External to the class
- Method: `compare(T o1, T o2)`
- Functional interface (Java 8+ lambdas)

### 🔹 Java 8+ Comparator Features

```java
// Lambda expressions
Comparator<Employee> bySalary = (e1, e2) -> Integer.compare(e1.getSalary(), e2.getSalary());

// Method references
Comparator<Employee> byName = Comparator.comparing(Employee::getName);

// Chaining comparators
Comparator<Employee> bySalaryThenName = 
    Comparator.comparing(Employee::getSalary)
             .thenComparing(Employee::getName);

// Reverse order
Comparator<Employee> bySalaryDesc = bySalary.reversed();

// Null handling
Comparator<Employee> nullsFirst = Comparator.nullsFirst(bySalary);
Comparator<Employee> nullsLast = Comparator.nullsLast(bySalary);
```

### 🔹 Key Differences

| Aspect | Comparable | Comparator |
|--------|------------|------------|
| **Package** | java.lang | java.util |
| **Method** | compareTo(T) | compare(T,T) |
| **Implementation** | In class | External class |
| **Ordering** | Natural | Custom |
| **Count** | One per class | Multiple |
| **Collections.sort()** | sort(list) | sort(list, comparator) |

### 🔹 TreeSet and TreeMap Usage

```java
// TreeSet with Comparable (natural order)
Set<Employee> naturalOrder = new TreeSet<>(employees);  // Uses compareTo()

// TreeSet with Comparator (custom order)
Set<Employee> salaryOrder = new TreeSet<>(salaryComparator);
Set<Employee> nameOrder = new TreeSet<>(nameComparator);

// TreeMap keys sorted
Map<Employee, String> employeeMap = new TreeMap<>(salaryComparator);
```

### 🔹 Best Practices

```java
// Implement Comparable for natural ordering
public class Person implements Comparable<Person> {
    @Override
    public int compareTo(Person other) {
        return this.name.compareTo(other.name);  // Natural: by name
    }
}

// Use Comparator for multiple sort options
List<Person> people = getPeople();
people.sort(Comparator.comparing(Person::getAge));      // By age
people.sort(Comparator.comparing(Person::getName));     // By name
people.sort(Comparator.comparing(Person::getAge)
                   .thenComparing(Person::getName));     // Age then name
```

### 🔹 Common Pitfalls

```java
// Inconsistent with equals()
class BadComparable implements Comparable<BadComparable> {
    @Override
    public int compareTo(BadComparable other) {
        // compareTo says equal
        return 0;
    }
    
    @Override
    public boolean equals(Object obj) {
        // equals says not equal
        return false;
    }
}
// This violates the contract and causes issues in sorted collections

// Solution: Make compareTo and equals consistent
// If compareTo returns 0, equals should return true
```

---

## 🎯 Interview One-Liner

> Comparable provides natural ordering (compareTo in class); Comparator enables custom/external sorting (compare method); use Comparable for single natural order, Comparator for multiple/custom orders; Java 8+ offers fluent Comparator API.

---

## ✅ 37. How to make a collection read-only?

**Collections can be made read-only using unmodifiable wrappers from Collections utility class, preventing modifications while allowing read operations.**

### 🔹 Collections.unmodifiable* Methods

```java
// Create modifiable collections first
List<String> modifiableList = new ArrayList<>();
modifiableList.add("A");
modifiableList.add("B");

// Make them read-only
List<String> readOnlyList = Collections.unmodifiableList(modifiableList);
Set<String> readOnlySet = Collections.unmodifiableSet(new HashSet<>(modifiableList));
Map<String, String> readOnlyMap = Collections.unmodifiableMap(new HashMap<>());

// Attempting to modify throws UnsupportedOperationException
readOnlyList.add("C");        // Throws exception
readOnlySet.add("C");         // Throws exception
readOnlyMap.put("key", "value");  // Throws exception
```

### 🔹 Available Methods

```java
// For all collection types
Collections.unmodifiableCollection(Collection c)
Collections.unmodifiableList(List list)
Collections.unmodifiableSet(Set set)
Collections.unmodifiableSortedSet(SortedSet set)
Collections.unmodifiableMap(Map map)
Collections.unmodifiableSortedMap(SortedMap map)
```

### 🔹 How It Works

**Wrapper that throws exceptions on modification**

```java
public static <T> List<T> unmodifiableList(List<? extends T> list) {
    return new UnmodifiableList<>(list);
}

static class UnmodifiableList<E> extends UnmodifiableCollection<E> implements List<E> {
    // All modification methods throw UnsupportedOperationException
    public boolean add(E e) {
        throw new UnsupportedOperationException();
    }
    
    public E set(int index, E element) {
        throw new UnsupportedOperationException();
    }
    
    // Read operations delegate to wrapped list
    public E get(int index) {
        return list.get(index);
    }
}
```

### 🔹 Important Notes

```java
// The wrapper prevents modification of the collection structure
// But doesn't prevent modification of the elements themselves

List<StringBuilder> list = new ArrayList<>();
list.add(new StringBuilder("Hello"));
List<StringBuilder> readOnly = Collections.unmodifiableList(list);

// This throws exception
readOnly.add(new StringBuilder("World"));

// This is allowed - modifies the element, not the collection
readOnly.get(0).append(" World");  // "Hello World"
```

### 🔹 Deep Immutability

**For truly immutable collections**

```java
// Option 1: Copy and make unmodifiable
List<String> original = Arrays.asList("A", "B", "C");
List<String> immutable = Collections.unmodifiableList(new ArrayList<>(original));

// Option 2: Use immutable collections (Java 9+)
List<String> immutableList = List.of("A", "B", "C");
Set<String> immutableSet = Set.of("A", "B", "C");
Map<String, String> immutableMap = Map.of("key", "value");

// Option 3: Third-party libraries
// Guava: ImmutableList, ImmutableSet, ImmutableMap
// Immutables library
```

### 🔹 Java 9+ Immutable Collections

```java
// List.of() - immutable list
List<String> list = List.of("A", "B", "C");
list.add("D");  // UnsupportedOperationException

// Set.of() - immutable set  
Set<String> set = Set.of("A", "B", "C");

// Map.of() - immutable map
Map<String, String> map = Map.of(
    "key1", "value1",
    "key2", "value2"
);

// Map.ofEntries() for larger maps
Map<String, String> bigMap = Map.ofEntries(
    Map.entry("key1", "value1"),
    Map.entry("key2", "value2")
);
```

### 🔹 Use Cases

```java
// Method return values - prevent external modification
public List<String> getNames() {
    List<String> names = new ArrayList<>();
    // populate names
    return Collections.unmodifiableList(names);
}

// Constants
public static final List<String> DAYS = 
    Collections.unmodifiableList(Arrays.asList("Mon", "Tue", "Wed"));

// API responses - prevent client modification
@RestController
public class ApiController {
    @GetMapping("/users")
    public List<UserDto> getUsers() {
        List<UserDto> users = userService.getAllUsers();
        return Collections.unmodifiableList(users);
    }
}
```

### 🔹 Performance Considerations

```java
// Unmodifiable wrappers have minimal overhead
// Just delegate read operations
// Throw exceptions on write operations

// For frequently accessed collections, consider copying
List<String> frequentlyAccessed = new ArrayList<>(originalList);
frequentlyAccessed = Collections.unmodifiableList(frequentlyAccessed);
```

---

## 🎯 Interview One-Liner

> Make collections read-only with Collections.unmodifiableList/Set/Map() wrappers that throw UnsupportedOperationException on modifications; Java 9+ offers List.of(), Set.of(), Map.of() for immutable collections; prevents structural changes but not element modifications.

---

## ✅ 38. What is PriorityQueue?

**PriorityQueue is a queue implementation that orders elements based on priority, where the highest priority element is always at the head of the queue.**

### 🔹 Basic Usage

```java
// Natural ordering (Comparable)
PriorityQueue<Integer> pq = new PriorityQueue<>();
pq.add(3);
pq.add(1);
pq.add(4);
pq.add(2);

System.out.println(pq.poll());  // 1 (smallest)
System.out.println(pq.poll());  // 2
System.out.println(pq.poll());  // 3
System.out.println(pq.poll());  // 4
```

### 🔹 Custom Ordering

```java
// Custom comparator for reverse order
PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);
maxHeap.add(3);
maxHeap.add(1);
maxHeap.add(4);

System.out.println(maxHeap.poll());  // 4 (largest)
System.out.println(maxHeap.poll());  // 3
System.out.println(maxHeap.poll());  // 1
```

### 🔹 Internal Structure

**Binary heap implementation**

```java
public class PriorityQueue<E> extends AbstractQueue<E> implements Serializable {
    private Object[] queue;     // Array to store heap
    private int size = 0;
    private Comparator<? super E> comparator;
    
    // Constructor
    public PriorityQueue() {
        this(DEFAULT_INITIAL_CAPACITY, null);
    }
    
    public PriorityQueue(Comparator<? super E> comparator) {
        this(DEFAULT_INITIAL_CAPACITY, comparator);
    }
}
```

**Heap operations:**
- **add/offer**: Insert element, maintain heap property (O(log n))
- **poll**: Remove and return head, maintain heap property (O(log n))
- **peek**: Return head without removing (O(1))

### 🔹 Key Characteristics

| Feature | PriorityQueue |
|---------|---------------|
| **Ordering** | Priority-based (min-heap by default) |
| **Duplicates** | Allowed |
| **Null elements** | Not allowed |
| **Thread-safety** | Not thread-safe |
| **Implementation** | Binary heap |
| **Access** | Head only (no random access) |

### 🔹 Common Operations

```java
PriorityQueue<Task> tasks = new PriorityQueue<>((t1, t2) -> t1.getPriority() - t2.getPriority());

// Add elements
tasks.offer(new Task("Low", 3));
tasks.offer(new Task("High", 1));
tasks.offer(new Task("Medium", 2));

// Peek at highest priority (doesn't remove)
Task next = tasks.peek();  // High priority task

// Remove and return highest priority
Task process = tasks.poll();  // High priority task

// Check if empty
boolean empty = tasks.isEmpty();

// Size
int count = tasks.size();
```

### 🔹 Use Cases

```java
// Task scheduling
PriorityQueue<Task> taskQueue = new PriorityQueue<>((t1, t2) -> t1.getPriority() - t2.getPriority());

// Dijkstra's algorithm
PriorityQueue<Node> pq = new PriorityQueue<>((n1, n2) -> n1.distance - n2.distance);

// Event simulation
PriorityQueue<Event> events = new PriorityQueue<>((e1, e2) -> e1.time - e2.time);

// Merge k sorted arrays
PriorityQueue<ListNode> pq = new PriorityQueue<>((a, b) -> a.val - b.val);
```

### 🔹 Performance

| Operation | Time Complexity |
|-----------|-----------------|
| **add/offer** | O(log n) |
| **poll** | O(log n) |
| **peek** | O(1) |
| **remove** | O(n) |
| **contains** | O(n) |

### 🔹 Limitations

```java
// No random access - can't get element at index
// Not suitable for frequent contains() checks
// Iterator doesn't guarantee order
// Not thread-safe (use PriorityBlockingQueue for concurrent)

// Iterator behavior
PriorityQueue<Integer> pq = new PriorityQueue<>();
pq.add(3); pq.add(1); pq.add(2);
for (Integer num : pq) {
    System.out.println(num);  // May print 1, 3, 2 (not guaranteed order)
}
```

### 🔹 Comparison with Other Queues

```java
// Regular Queue (FIFO)
Queue<String> queue = new LinkedList<>();
queue.add("First"); queue.add("Second");
queue.poll();  // "First"

// PriorityQueue (priority-based)
PriorityQueue<Task> pq = new PriorityQueue<>((t1, t2) -> t1.priority - t2.priority);

// Deque (double-ended)
Deque<String> deque = new ArrayDeque<>();
deque.addFirst("Front"); deque.addLast("Back");
```

---

## 🎯 Interview One-Liner

> PriorityQueue is a heap-based queue that orders elements by priority (min-heap default); add/offer O(log n), poll/peek O(log n)/O(1); use for task scheduling, algorithms like Dijkstra; not for random access or frequent searches.

---

## ✅ 39. Difference between Set, List, and Map

**Set, List, and Map are the three main collection interfaces with different contracts for storing and accessing elements.**

### 🔹 List Interface

**Ordered collection allowing duplicates**

```java
// Characteristics
List<String> list = new ArrayList<>();
list.add("A");        // Allows duplicates
list.add("A");        // Duplicate added
list.add("B");
System.out.println(list);  // [A, A, B]

// Access by index
String first = list.get(0);    // "A"
String second = list.get(1);   // "A"

// Ordered - maintains insertion order
for (String item : list) {
    System.out.println(item);  // A, A, B
}
```

**Key features:**
- Ordered (maintains insertion order)
- Allows duplicates
- Access by index
- Examples: ArrayList, LinkedList

### 🔹 Set Interface

**Unordered collection with no duplicates**

```java
// Characteristics
Set<String> set = new HashSet<>();
set.add("A");        // Added
set.add("A");        // Duplicate ignored
set.add("B");        // Added
System.out.println(set);  // [A, B] or [B, A] (order not guaranteed)

// No index access
// set.get(0);  // No such method

// Unordered - no guaranteed order
for (String item : set) {
    System.out.println(item);  // A, B or B, A
}
```

**Key features:**
- No duplicates (uniqueness)
- Unordered (except LinkedHashSet, TreeSet)
- No index access
- Examples: HashSet, TreeSet, LinkedHashSet

### 🔹 Map Interface

**Key-value pairs**

```java
// Characteristics
Map<String, Integer> map = new HashMap<>();
map.put("A", 1);     // Key-value pair
map.put("A", 2);     // Overwrites value for key "A"
map.put("B", 3);     // New pair
System.out.println(map);  // {A=2, B=3}

// Access by key
Integer valueA = map.get("A");    // 2
Integer valueC = map.get("C");    // null

// Keys are unique, values can duplicate
map.put("C", 2);     // Different key, same value OK
```

**Key features:**
- Key-value associations
- Keys are unique, values can duplicate
- Access by key, not index
- Examples: HashMap, TreeMap, LinkedHashMap

### 🔹 Comparison Table

| Feature | List | Set | Map |
|---------|------|-----|-----|
| **Duplicates** | ✅ | ❌ | Keys: ❌, Values: ✅ |
| **Order** | ✅ (insertion) | ❌ (except TreeSet) | ❌ (except TreeMap) |
| **Access** | By index | By value | By key |
| **Methods** | get(index), add(element) | add(element), contains() | get(key), put(key,value) |
| **Use Case** | Sequence of items | Unique items | Key-value lookup |

### 🔹 Common Implementations

```java
// Lists
List<String> arrayList = new ArrayList<>();     // Fast access
List<String> linkedList = new LinkedList<>();   // Fast modifications

// Sets
Set<String> hashSet = new HashSet<>();          // Fast lookup
Set<String> treeSet = new TreeSet<>();          // Sorted
Set<String> linkedHashSet = new LinkedHashSet<>(); // Insertion order

// Maps
Map<String, Object> hashMap = new HashMap<>();  // Fast lookup
Map<String, Object> treeMap = new TreeMap<>();  // Sorted keys
Map<String, Object> linkedHashMap = new LinkedHashMap<>(); // Insertion order
```

### 🔹 When to Use Each

```java
// Use List when you need:
List<Task> tasks = new ArrayList<>();  // Ordered sequence
tasks.add(task1); tasks.add(task2);    // With possible duplicates
Task first = tasks.get(0);             // Access by position

// Use Set when you need:
Set<String> uniqueNames = new HashSet<>();  // Unique elements only
uniqueNames.add("John"); uniqueNames.add("John"); // Second ignored
boolean exists = uniqueNames.contains("John");    // Fast lookup

// Use Map when you need:
Map<String, User> users = new HashMap<>();  // Key-value associations
users.put("john@example.com", user1);       // Fast lookup by email
User user = users.get("john@example.com");  // Retrieve by key
```

### 🔹 Performance Considerations

```java
// List operations
list.get(index)        // O(1) for ArrayList, O(n) for LinkedList
list.add(element)      // O(1) amortized for ArrayList, O(1) for LinkedList
list.contains(element) // O(n) for both

// Set operations  
set.add(element)       // O(1) average for HashSet
set.contains(element)  // O(1) average for HashSet
set.remove(element)    // O(1) average for HashSet

// Map operations
map.get(key)           // O(1) average for HashMap
map.put(key, value)    // O(1) average for HashMap
map.containsKey(key)   // O(1) average for HashMap
```

---

## 🎯 Interview One-Liner

> List: ordered, allows duplicates, index access; Set: unordered, no duplicates, value-based; Map: key-value pairs, unique keys; choose based on data relationships and access patterns.
