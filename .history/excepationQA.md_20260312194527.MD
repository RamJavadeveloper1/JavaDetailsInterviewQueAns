# 📌 Java Exception Handling Topics for Interviews

## 1️⃣ Basics of Exceptions

* What is an Exception?
* Difference between Error and Exception
* Checked vs Unchecked Exceptions

Example:

| Type                | Example                                   |
| ------------------- | ----------------------------------------- |
| Checked Exception   | IOException, SQLException                 |
| Unchecked Exception | NullPointerException, ArithmeticException |

---

# 2️⃣ Exception Hierarchy

Interviewers often ask to draw or explain the hierarchy.

```
Throwable
 ├── Error
 │     ├── OutOfMemoryError
 │     └── StackOverflowError
 │
 └── Exception
       ├── Checked Exceptions
       │      ├── IOException
       │      └── SQLException
       │
       └── RuntimeException
              ├── NullPointerException
              ├── ArithmeticException
              └── IllegalArgumentException
```

---

# 3️⃣ try-catch-finally

Questions:

* What is try block?
* What is catch block?
* What is finally block?
* Will finally always execute?

Example:

```java
try {
    int a = 10 / 0;
} catch (ArithmeticException e) {
    System.out.println("Exception occurred");
} finally {
    System.out.println("Cleanup code");
}
```

---

# 4️⃣ throw vs throws

| throw                              | throws                    |
| ---------------------------------- | ------------------------- |
| Used to explicitly throw exception | Used to declare exception |
| Used inside method                 | Used in method signature  |

Example:

```java
throw new IllegalArgumentException("Invalid input");
```

```java
public void readFile() throws IOException
```

---

# 5️⃣ Checked vs Unchecked Exceptions

### Checked Exceptions

* Checked at compile time
* Must handle or declare

Example:

```
IOException
SQLException
FileNotFoundException
```

### Unchecked Exceptions

* Occur at runtime
* Not mandatory to handle

Example:

```
NullPointerException
ArrayIndexOutOfBoundsException
ArithmeticException
```

---

# 6️⃣ Custom Exceptions

Interviewers often ask this.

Example:

```java
class InvalidAgeException extends Exception {
    public InvalidAgeException(String message) {
        super(message);
    }
}
```

Usage:

```java
if(age < 18){
    throw new InvalidAgeException("Age must be 18");
}
```

---

# 7️⃣ Multiple catch blocks

```java
try {
   // code
} catch (IOException e) {
} catch (SQLException e) {
}
```

Java executes the **first matching catch block**.

---

# 8️⃣ try-with-resources

Important for Java 7+

Used to automatically close resources.

```java
try (BufferedReader br = new BufferedReader(new FileReader("file.txt"))) {
    System.out.println(br.readLine());
}
```

---

# 9️⃣ Exception Propagation

If a method does not handle an exception, it propagates to the calling method.

Example:

```
method1 → method2 → method3
```

Exception flows upward.

---

# 🔟 finally block behaviour

Questions interviewers ask:

* Will finally run if exception occurs?
* Will finally run if return statement exists?
* Will finally run if System.exit() is called?

---

# 1️⃣1️⃣ Best Practices

Interviewers sometimes ask this.

Best practices:

* Catch specific exceptions
* Avoid catching Exception or Throwable
* Use meaningful custom exceptions
* Log exceptions properly

---

# 📌 Most Asked Exception Questions (Important)

Focus on these:

1️⃣ Difference between **checked and unchecked exception**
2️⃣ Difference between **throw and throws**
3️⃣ Will **finally always execute?**
4️⃣ What is **try-with-resources?**
5️⃣ What is **custom exception?**
6️⃣ What is **exception propagation?**

---

# 🎯 Example Interview Answer

**What is Exception?**

> An exception is an event that occurs during program execution which disrupts the normal flow of the program. In Java, exceptions are objects that represent error conditions and can be handled using try, catch, and finally blocks.

---

If you want, I can also give you the **Top 25 Java Exception Interview Questions (with answers)** that are commonly asked in **7+ year Java backend interviews**.


JAVA EXCEPTION INTERVIEW QUESTIONS (25)

1. What is an Exception in Java?

An exception is an event that occurs during program execution that disrupts the normal flow of the program.

**Detailed Explanation:**

**Definition:**
- An object that represents an error or abnormal condition
- Disrupts normal execution flow of a program
- Objects created from the Exception class and its subclasses
- Thrown when something goes wrong during execution

**How Exceptions Work:**
- Code execution stops at the point where exception occurs
- Exception object created with error information
- Control transfers to exception handler (catch block)
- If not handled, JVM terminates the program

**Key Characteristics:**
- Exceptions are objects (instances of Exception class)
- Contain information about what went wrong
- Include error message and stack trace
- Can be caught and handled gracefully
- Allow recovery from abnormal conditions

**Exception vs Normal Error:**
```
Without Exception Handling:
Program crashes abruptly → User sees "Application crashed"

With Exception Handling:
Exception caught → Error logged → Program continues or graceful shutdown
```

**When Exceptions Occur:**
- Dividing by zero: `int result = 10 / 0;` → ArithmeticException
- Accessing null object: `String s = null; s.length();` → NullPointerException
- Invalid array index: `arr[100]` → ArrayIndexOutOfBoundsException
- File not found: `new FileReader("nonexistent.txt")` → FileNotFoundException
- Casting mismatch: `(String) intObject` → ClassCastException

**Handling Mechanism:**
```java
try {
    // Code that may throw exception
    int x = 10 / 0;
} catch (ArithmeticException e) {
    // Exception caught and handled here
    System.out.println("Cannot divide by zero");
} finally {
    // Cleanup code
    System.out.println("Operation completed");
}
```

**Advantages of Exception Handling:**
- Program doesn't crash unexpectedly
- Error can be logged for debugging
- Recovery mechanisms can be implemented
- User gets meaningful error messages
- Resource cleanup is guaranteed (finally block)

**⚠️ Keywords for Follow-up Questions:**
- **Try-catch-finally blocks** - How does control flow work?
- **Exception hierarchy** - What's the parent class of all exceptions?
- **Checked exceptions** - What must be declared/handled?
- **Unchecked exceptions** - What doesn't need handling?
- **Exception propagation** - How does it flow up the call stack?
- **Try-with-resources** - How to auto-close resources?
- **Custom exceptions** - How to create user-defined exceptions?

------------------------------------------------

2. What is the difference between Error and Exception?

**Error:**
- Serious, critical problem that cannot be recovered
- Caused by JVM or system-level issues
- Programmer cannot handle meaningfully
- Should NOT be caught or handled
- Program execution typically terminates
- Examples: OutOfMemoryError, StackOverflowError, InternalError

**Exception:**
- Can be caught and handled by programmer
- Caused by application-level logic errors
- Can be recovered from gracefully
- Should be handled with try-catch blocks
- Program can continue after handling
- Examples: IOException, SQLException, NullPointerException

**Detailed Comparison Table:**

| Feature | Error | Exception |
|---------|-------|----------|
| Severity | Critical, unrecoverable | Can be recovered |
| Cause | JVM/System level | Application logic |
| Handling | Should NOT catch | Should catch & handle |
| Recovery | Not possible | Possible |
| Extends | Throwable | Exception |
| Parent Class | N/A | Throwable |
| Examples | OutOfMemoryError, StackOverflowError | IOException, NullPointerException |

**Examples:**

**Error Example - OutOfMemoryError:**
```java
// Creating huge array consuming all memory
byte[] memory = new byte[Integer.MAX_VALUE];  // OutOfMemoryError
// Cannot be recovered - program will crash
```

**Error Example - StackOverflowError:**
```java
public void recursiveMethod() {
    recursiveMethod();  // Infinite recursion
    // Eventually causes StackOverflowError when stack exhausted
}
```

**Exception Example - NullPointerException (Recoverable):**
```java
try {
    String str = null;
    int len = str.length();  // NullPointerException thrown
} catch (NullPointerException e) {
    System.out.println("String is null, providing default value");
    int len = 0;  // Program continues
}
```

**Exception Example - IOException (Recoverable):**
```java
try {
    FileReader reader = new FileReader("nonexistent.txt");
    // File not found - IOException thrown
} catch (IOException e) {
    System.out.println("File not found, using default config");
    // Program continues with alternative
}
```

**Exception Hierarchy:**
```
Throwable
  ├── Error (never catch these)
  │     ├── OutOfMemoryError
  │     ├── StackOverflowError
  │     └── InternalError
  │
  └── Exception (catch and handle these)
        ├── Checked Exceptions
        │    ├── IOException
        │    ├── SQLException
        │    └── FileNotFoundException
        │
        └── RuntimeException (Unchecked)
             ├── NullPointerException
             ├── ArithmeticException
             └── IllegalArgumentException
```

**When to Worry:**
- Errors: Almost never (system's responsibility)
- Exceptions: Almost always (your responsibility)

**DON'T do this:**
```java
// WRONG - Catching Error is not recommended
try {
    // code
} catch (Error e) {  // ❌ Bad practice
    // This defeats purpose of errors
}
```

**DO this:**
```java
// RIGHT - Catch specific exceptions
try {
    // code
} catch (IOException e) {  // ✅ Good practice
    // Handle IO errors gracefully
}
```

**⚠️ Keywords for Follow-up Questions:**
- **Checked vs Unchecked exceptions** - How are they different?
- **Exception hierarchy** - How does inheritance work?
- **Throwable class** - Why is it the root of both?
- **OutOfMemoryError** - How to prevent heap exhaustion?
- **StackOverflowError** - How to avoid infinite recursion?
- **Try-catch-finally** - Can we catch Errors?
- **Exception propagation** - How do Errors propagate?

------------------------------------------------

3. What is Exception Hierarchy?

**Complete Exception Hierarchy in Java:**

The hierarchy represents the inheritance chain for all throwable objects:

**Root Class: Throwable**
- Parent of all exceptions and errors in Java
- Part of java.lang package
- provides methods like getMessage(), printStackTrace(), getStackTrace()

**Two Main Branches:**

**Branch 1: Error (System-level)**
- Serious problems from JVM/runtime
- Should NOT be caught
- Examples:
  - OutOfMemoryError: JVM ran out of heap memory
  - StackOverflowError: Stack memory exhausted
  - VirtualMachineError: JVM internal error

**Branch 2: Exception (Application-level)**
- Problems from application code
- Should be caught and handled
- Two sub-types:
  - **Checked Exceptions**: Must be handled/declared
  - **Unchecked Exceptions (RuntimeException)**: Optional handling

**Complete Hierarchy Tree:**
```
Throwable
  ├── Error
  │     ├── OutOfMemoryError
  │     ├── StackOverflowError
  │     ├── VirtualMachineError
  │     ├── InternalError
  │     └── AWTError
  │
  └── Exception
        │
        ├── Checked Exceptions (must handle)
        │     ├── IOException
        │     │    ├── FileNotFoundException
        │     │    ├── EOFException
        │     │    └── SocketException
        │     │
        │     ├── SQLException
        │     ├── ClassNotFoundException
        │     ├── InterruptedException
        │     ├── ReflectiveOperationException
        │     └── Others compiled-time exceptions
        │
        └── RuntimeException (Unchecked - extends Exception)
              ├── NullPointerException
              ├── ArithmeticException
              ├── ArrayIndexOutOfBoundsException
              ├── ClassCastException
              ├── IllegalArgumentException
              ├── NumberFormatException
              ├── StringIndexOutOfBoundsException
              └── UnsupportedOperationException
```

**Key Points:**

**Checked Exceptions:**
- Compiler checks at compile-time
- Must be handled with try-catch or declared with throws
- Compiler gives error if not handled
- Represents recoverable conditions
```java
// Compiler ERROR if not handled
public void readFile() {  // ❌ Missing IOException handling
    FileReader reader = new FileReader("file.txt");
}

// Option 1: Handle with try-catch
public void readFile() {
    try {
        FileReader reader = new FileReader("file.txt");
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }
}

// Option 2: Declare with throws
public void readFile() throws IOException {
    FileReader reader = new FileReader("file.txt");
}
```

**Unchecked Exceptions (RuntimeException):**
- Not checked at compile-time
- Occur at runtime
- Don't need to be caught or declared
- Represents programming errors
```java
// No compiler error - allowed
public void divide() {
    int result = 10 / 0;  // ArithmeticException at runtime
    // Runtime error, compiler doesn't complain
}

// Handling is optional but recommended
public void divide() {
    try {
        int result = 10 / 0;
    } catch (ArithmeticException e) {
        System.out.println("Cannot divide by zero");
    }
}
```

**Comparison Table:**

| Aspect | Checked Exception | Unchecked Exception | Error |
|--------|---|---|---|
| Parent Class | Exception | RuntimeException | Error |
| Compiler Check | Yes (mandatory) | No (optional) | No |
| Must Handle | Yes | No | No (shouldn't) |
| When Detected | Compile-time | Runtime | Runtime |
| Recovery Possible | Yes | Rarely | No |
| Examples | IOException, SQLException | NullPointerException, ArithmeticException | OutOfMemoryError |

**⚠️ Keywords for Follow-up Questions:**
- **Checked exceptions** - Why are they required to be handled?
- **Unchecked exceptions** - Why are they optional to handle?
- **Throwable class** - What methods does it provide?
- **RuntimeException** - How is it different from Exception?
- **Error vs Exception** - Why both at top level?
- **getMessage()** - How to get error information?
- **printStackTrace()** - What information does it provide?
- **Custom exceptions** - How to extend Exception or RuntimeException?

------------------------------------------------

4. What is a Checked Exception?

**Definition:**
Checked exceptions are exceptions that the Java compiler forces you to catch or declare in method signature.

**Key Characteristics:**
- **Compile-time checking**: Compiler enforces handling
- **Must be handled**: Using try-catch or throws keyword
- **Recoverable conditions**: Represent situations program can recover from
- **Extends Exception class**: (Not RuntimeException)
- **Declared in method signature**: Using throws keyword if not caught

**Examples of Checked Exceptions:**

**IOException Family:**
```
IOException (parent)
  ├── FileNotFoundException
  ├── EOFException
  ├── SocketException
  └── BindException
```

**Database Exception:**
```
SQLException
  └── Thrown during database operations
```

**Other Common Checked Exceptions:**
```
ClassNotFoundException     - When class not found by reflection
InterruptedException      - When thread is interrupted
ParseException           - When parsing fails
MalformedURLException    - Invalid URL format
NoSuchMethodException    - Method not found by reflection
```

**Why Checked Exceptions Exist:**

**Problem they solve:**
- Force programmers to think about error recovery
- Ensure critical errors aren't accidentally ignored
- Improve code reliability

**Example scenario:**
```java
// Without forced handling - programmer might forget to handle
FileReader reader = new FileReader("data.txt");  // What if file missing?

// With checked exception - compiler forces handling
FileReader reader = new FileReader("data.txt");  // ❌ COMPILER ERROR!

// Must handle or declare
// Option 1: Handle with try-catch
try {
    FileReader reader = new FileReader("data.txt");
} catch (FileNotFoundException e) {
    System.out.println("File not found: " + e.getMessage());
}

// Option 2: Declare with throws
public void loadData() throws FileNotFoundException {
    FileReader reader = new FileReader("data.txt");
}
```

**Handling Methods:**

**Method 1: Try-Catch Block**
```java
try {
    FileInputStream fis = new FileInputStream("data.txt");
    // Read file
} catch (FileNotFoundException e) {
    System.out.println("File not found: " + e.getMessage());
    // Recovery: use default file
} catch (IOException e) {
    System.out.println("IO error: " + e.getMessage());
    // Recovery: log and retry
}
```

**Method 2: Throws Declaration**
```java
public void readFile() throws IOException, SQLException {
    // Not handled here, propagated to caller
    FileReader reader = new FileReader("file.txt");
    // ... rest of code
}

public static void main(String[] args) throws IOException, SQLException {
    readFile();  // Must handle in main or declare
}
```

**Complete IOException Handling Example:**
```java
import java.io.*;

public class FileProcessor {
    
    // Method 1: Handling with try-catch
    public String readFileMethod1(String filename) {
        try {
            BufferedReader br = new BufferedReader(
                new FileReader(filename)
            );
            String line = br.readLine();
            br.close();
            return line;
        } catch (FileNotFoundException e) {
            System.out.println("File " + filename + " not found");
            return null;  // Graceful recovery
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return null;  // Graceful recovery
        }
    }
    
    // Method 2: Propagating with throws
    public String readFileMethod2(String filename) throws IOException {
        BufferedReader br = new BufferedReader(
            new FileReader(filename)
        );
        String line = br.readLine();
        br.close();
        return line;
    }
    
    public static void main(String[] args) {
        FileProcessor processor = new FileProcessor();
        
        // Method 1: Local handling
        String content1 = processor.readFileMethod1("test.txt");
        
        // Method 2: Must handle since it throws
        try {
            String content2 = processor.readFileMethod2("test.txt");
        } catch (IOException e) {
            System.out.println("Failed to read file");
        }
    }
}
```

**Java 7+ Try-with-Resources (Auto-closing resources):**
```java
// Automatically closes FileReader even if exception occurs
try (BufferedReader br = new BufferedReader(
         new FileReader("file.txt"))) {
    String line = br.readLine();
} catch (IOException e) {
    System.out.println("IO Error: " + e.getMessage());
}
// FileReader and BufferedReader auto-closed here
```

**Compiler Enforcement Example:**

```java
// This WON'T compile:
public void process() {  // ❌ COMPILER ERROR
    FileReader reader = new FileReader("file.txt");
}

// Error message:
// "Unhandled exception type FileNotFoundException"

// Must do one of these:
// 1. Add try-catch
public void process() {  // ✅ Compiles
    try {
        FileReader reader = new FileReader("file.txt");
    } catch (FileNotFoundException e) {}
}

// 2. Add throws
public void process() throws FileNotFoundException {  // ✅ Compiles
    FileReader reader = new FileReader("file.txt");
}
```

**Inheritance Hierarchy:**
```
Throwable
  └── Exception
       └── (All checked exceptions)
            ├── IOException
            ├── SQLException  
            ├── ClassNotFoundException
            └── ... (many others)

       └── RuntimeException
            └── (Unchecked - not shown above)
```

**Common Checked Exceptions Reference:**

| Exception | Scenario | Recovery |
|-----------|----------|----------|
| FileNotFoundException | File doesn't exist | Use backup file |
| IOException | General I/O failure | Retry or use different resource |
| SQLException | Database operation fails | Retry transaction |
| ClassNotFoundException | Class not available | Load from different location |
| InterruptedException | Thread interrupted | Stop gracefully |

**⚠️ Keywords for Follow-up Questions:**
- **IOException** - What sub-types exist?
- **Try-catch block** - How to handle multiple exceptions?
- **Throws keyword** - How exception propagation works?
- **Try-with-resources** - How auto-closing works?
- **Exception handling best practices** - Which approach is better?
- **SQLException** - How to handle database errors?
- **Custom checked exceptions** - How to create them?
- **Exception recovery** - How to design recovery strategies?

------------------------------------------------

5. What is an Unchecked Exception?

**Definition:**
Unchecked exceptions are runtime exceptions that the Java compiler does NOT force you to catch or declare.

**Key Characteristics:**
- **Runtime checking**: Detected only at runtime, not by compiler
- **Optional handling**: Not mandatory to catch or declare
- **Extends RuntimeException**: (Which extends Exception)
- **Not declared in method signature**: Can use without throws
- **Represent programming errors**: Logic mistakes, bad assumptions
- **Preventable through code**: Can be avoided with proper checks

**Unchecked Exception Hierarchy:**
```
Throwable
  └── Exception
       └── RuntimeException (Unchecked - does NOT need to be caught)
            ├── NullPointerException
            ├── ArithmeticException
            ├── ArrayIndexOutOfBoundsException
            ├── ClassCastException
            ├── IllegalArgumentException
            ├── NumberFormatException
            ├── StringIndexOutOfBoundsException
            ├── IndexOutOfBoundsException
            ├── UnsupportedOperationException
            ├── ConcurrentModificationException
            └── (many others extending RuntimeException)
```

**Common Unchecked Exceptions:**

**1. NullPointerException - Most Common**
```java
// Accessing null object
String str = null;
str.length();  // ❌ NullPointerException

// Prevention:
if (str != null) {
    str.length();  // ✅ Safe
}

// Or Java 8+:
Optional<String> optStr = Optional.ofNullable(str);
optStr.ifPresent(s -> System.out.println(s.length()));
```

**2. ArithmeticException**
```java
// Division by zero
int result = 10 / 0;  // ❌ ArithmeticException

// Prevention:
if (divisor != 0) {
    int result = 10 / divisor;  // ✅ Safe
}
```

**3. ArrayIndexOutOfBoundsException**
```java
int[] arr = {1, 2, 3};
int value = arr[10];  // ❌ ArrayIndexOutOfBoundsException

// Prevention:
if (index >= 0 && index < arr.length) {
    int value = arr[index];  // ✅ Safe
}
```

**4. ClassCastException**
```java
Object obj = "String";
Integer num = (Integer) obj;  // ❌ ClassCastException

// Prevention:
if (obj instanceof Integer) {
    Integer num = (Integer) obj;  // ✅ Safe
}
```

**5. IllegalArgumentException**
```java
// Invalid method argument
public void setAge(int age) {
    if (age < 0) {
        throw new IllegalArgumentException("Age cannot be negative");
    }
}

setAge(-5);  // ❌ IllegalArgumentException
```

**6. NumberFormatException**
```java
int num = Integer.parseInt("abc");  // ❌ NumberFormatException

// Prevention:
try {
    int num = Integer.parseInt("123");  // ✅ Safe
} catch (NumberFormatException e) {
    System.out.println("Invalid number format");
}
```

**Why Handle Unchecked Exceptions?**

**Reason 1: Application Stability**
```java
// Without handling - program crashes
public void processData(List<String> data) {
    for (String item : data) {
        System.out.println(item.toUpperCase());  // NPE if item is null
    }
}

// With handling - program continues
public void processData(List<String> data) {
    for (String item : data) {
        if (item != null) {
            System.out.println(item.toUpperCase());
        } else {
            System.out.println("NULL item skipped");
        }
    }
}
```

**Reason 2: User Experience**
```java
// Without handling
String input = getUserInput();
int number = Integer.parseInt(input);  // May crash if invalid

// With handling
String input = getUserInput();
try {
    int number = Integer.parseInt(input);
} catch (NumberFormatException e) {
    System.out.println("Please enter a valid number");
    // Ask user to retry
}
```

**Unchecked vs Checked Exceptions:**

| Aspect | Unchecked | Checked |
|--------|-----------|----------|
| Parent Class | RuntimeException | Exception (not RuntimeException) |
| Compiler Check | No | Yes |
| Must Handle | No | Yes |
| Must Declare | No | Yes |
| When Occurs | Runtime | Can be compile or runtime |
| Examples | NullPointerException | IOException, SQLException |
| Represents | Programming errors | Recoverable conditions |
| Prevention | Code checks | Exception handling |

**Handling Unchecked Exceptions - Two Approaches:**

**Approach 1: Defensive Programming (Recommended)**
```java
public String processString(String input) {
    // Check conditions BEFORE they cause exceptions
    if (input == null || input.isEmpty()) {
        return "DEFAULT";  // No exception thrown
    }
    return input.toUpperCase();
}
```

**Approach 2: Exception Handling (When necessary)**
```java
public String processString(String input) {
    try {
        return input.toUpperCase();  // Might throw NPE
    } catch (NullPointerException e) {
        return "DEFAULT";  // Fallback
    }
}
```

**Real-World Example - User Input Processing:**
```java
public class UserValidator {
    
    // Good: Defensive programming with unchecked exceptions
    public void validateUser(String name, int age) {
        // Check conditions before processing
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (age < 18 || age > 120) {
            throw new IllegalArgumentException("Age must be between 18 and 120");
        }
        // Only get here if input is valid
        System.out.println("User " + name + " is " + age + " years old");
    }
    
    // Usage
    public static void main(String[] args) {
        try {
            validateUser("John", 25);        // ✅ OK
            validateUser(null, 30);          // ❌ IllegalArgumentException
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input: " + e.getMessage());
        }
    }
}
```

**Best Practices for Unchecked Exceptions:**

**DO:**
```java
// ✅ Check for null before using
if (object != null) {
    object.method();
}

// ✅ Validate array bounds
if (index >= 0 && index < array.length) {
    value = array[index];
}

// ✅ Use defensive programming
if (divisor != 0) {
    result = numerator / divisor;
}

// ✅ Prevent invalid arguments
if (age < 0) {
    throw new IllegalArgumentException("Age cannot be negative");
}
```

**DON'T:**
```java
// ❌ Catch all exceptions
try {
    // code
} catch (Exception e) {  // Too broad
    e.printStackTrace();
}

// ❌ Catch RuntimeException
try {
    // code
} catch (RuntimeException e) {  // Masks bugs
    // silent ignore
}

// ❌ Empty catch block
try {
    // code
} catch (NullPointerException e) {
    // Ignore
}
```

**⚠️ Keywords for Follow-up Questions:**
- **NullPointerException** - How to prevent and handle?
- **ArithmeticException** - What operations cause it?
- **ArrayIndexOutOfBoundsException** - How to avoid?
- **ClassCastException** - When does it occur?
- **IllegalArgumentException** - When to throw it?
- **Defensive programming** - Vs exception handling?
- **NumberFormatException** - How to handle string conversions?
- **Try-catch for unchecked exceptions** - When necessary?
- **Custom unchecked exceptions** - How to create?
- **Optional class** - As alternative to null checks?

------------------------------------------------

6. Difference between throw and throws?

**throw keyword:**

**Purpose:** Used to explicitly throw an exception in code

**Location:** Inside method body

**Syntax:** 
```java
throw new ExceptionClass("error message");
```

**When to use:**
- When you detect an error condition
- When you want to create custom exception
- When existing exception implementation is inadequate

**Example:**
```java
public void setAge(int age) {
    if (age < 0) {
        throw new IllegalArgumentException("Age cannot be negative");
    }
    this.age = age;
}

public void authenticate(String password) {
    if (password == null || password.isEmpty()) {
        throw new NullPointerException("Password cannot be empty");
    }
    // authenticate logic
}

public void divide(int a, int b) {
    if (b == 0) {
        throw new ArithmeticException("Divisor cannot be zero");
    }
    System.out.println(a / b);
}
```

**Details:**
- Only one exception can be thrown per statement
- Exception object must be created first
- Control immediately transfers to catch block
- No code after throw executes in same method

```java
public void processOrder(Order order) {
    if (order == null) {
        throw new NullPointerException("Order cannot be null");
    }
    System.out.println("Processing order...");  // Only executes if order != null
}
```

**---**

**throws keyword:**

**Purpose:** Used to declare that method might throw an exception

**Location:** In method signature (after parenthesis)

**Syntax:**
```java
public void methodName() throws ExceptionClass { }
```

**When to use:**
- For checked exceptions in method
- When method cannot handle exception
- To propagate exception to caller
- For API documentation

**Example:**
```java
public void readFile(String filename) throws IOException {
    FileReader reader = new FileReader(filename);
    // If file not found, IOException will be thrown
    reader.read();
}

public void queryDatabase(String query) throws SQLException {
    Connection conn = DriverManager.getConnection(url);
    Statement stmt = conn.createStatement();
    stmt.execute(query);
}

public void executeCommand(String cmd) throws IOException, InterruptedException {
    Process process = Runtime.getRuntime().exec(cmd);
    process.waitFor();
}
```

**Details:**
- Multiple exceptions can be declared (comma-separated)
- Responsibility shifts to calling method
- Caller must handle or re-declare exception
- Method doesn't handle exception here
- Used only for checked exceptions (compiler requirement)

```java
// Multiple checked exceptions
public void complexOperation() throws IOException, SQLException, ClassNotFoundException {
    // Any of these exceptions may be thrown
}

// Caller must handle
public static void main(String[] args) {
    try {
        complexOperation();
    } catch (IOException e) {
        // Handle
    } catch (SQLException e) {
        // Handle
    } catch (ClassNotFoundException e) {
        // Handle
    }
}
```

**Complete Comparison Table:**

| Feature | throw | throws |
|---------|-------|--------|
| Purpose | Explicitly throw exception | Declare method throws exception |
| Location | Inside method body | Method signature |
| Syntax | `throw new Exception();` | `method() throws Exception { }` |
| When Used | When error detected | When cannot handle exception |
| Keyword Count | Single exception per statement | Multiple exceptions (,) |
| Exception Type | Any (checked/unchecked) | Usually checked exceptions |
| Control Flow | Immediate jump to catch/caller | Deferred to caller |
| Compiler Check | No (for unchecked) | Yes (for checked) |
| Documentation | Implicit propagation | Explicit in signature |
| Responsibility | Current method | Caller's method |

**Real-World Example Combining Both:**

```java
public class BankAccount {
    private double balance;
    
    // Method 1: Uses throw
    public void withdraw(double amount) throws IllegalArgumentException {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        if (amount > balance) {
            throw new IllegalArgumentException("Insufficient balance");
        }
        balance -= amount;
    }
    
    // Method 2: Uses throws
    public void saveToFile(String filename) throws IOException {
        FileWriter writer = new FileWriter(filename);
        writer.write("Balance: " + balance);
        writer.close();
    }
    
    // Method 3: Uses both throw and throws
    public void transfer(BankAccount recipient, double amount) 
            throws IllegalArgumentException, IOException {
        
        // throw: Validate local condition
        if (recipient == null) {
            throw new IllegalArgumentException("Recipient cannot be null");
        }
        
        // throws: Propagate checked exception
        this.withdraw(amount);
        recipient.deposit(amount);
        saveToFile("transaction.log");
    }
}

// Usage
public class Main {
    public static void main(String[] args) {
        BankAccount acc1 = new BankAccount(1000);
        BankAccount acc2 = new BankAccount(500);
        
        try {
            // Handles both types of exceptions thrown by transfer
            acc1.transfer(acc2, 250);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid argument: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("File error: " + e.getMessage());
        }
    }
}
```

**Flow Comparison:**

**Using throw:**
```java
method() {
    if (condition) {
        throw new Exception();  → Immediately goes to catch block
    }
    // Rest of code not executed
}
```

**Using throws:**
```java
method() throws Exception {
    // Any thrown exception here...
    throw new Exception();  → Propagates to caller
}

caller() {
    try {
        method();  → Exception caught here
    } catch (Exception e) {
        // Handle
    }
}
```

**Unchecked vs Checked - throw/throws Behavior:**

```java
// Unchecked - throw doesn't need throws declaration
public void validatePositive(int num) {  // No throws required
    if (num < 0) {
        throw new IllegalArgumentException("Must be positive");  // ✅ OK
    }
}

// Checked - throw needs throws declaration
public void readFile() throws IOException {  // Required
    throw new IOException("Cannot read file");  // ✅ OK
}

// This won't compile:
public void readFile() {  // ❌ Missing throws
    throw new IOException("Cannot read file");  // ❌ Compiler error
}
```

**Best Practices:**

```java
// ✅ Good: Throw specific exception
public void setEmail(String email) {
    if (email == null || !email.contains("@")) {
        throw new IllegalArgumentException("Invalid email format");
    }
}

// ✅ Good: Declare all possible exceptions
public void connectDatabase() throws SQLException, ClassNotFoundException {
    // Connection code
}

// ❌ Bad: Throwing generic Exception
public void setEmail(String email) throws Exception {
    if (email == null) {
        throw new Exception("Invalid";  // Too generic
    }
}

// ❌ Bad: Declaring Exception instead of specific types
public void connectDatabase() throws Exception {  // Too broad
    // Connection code
}
```

**⚠️ Keywords for Follow-up Questions:**
- **Exception propagation** - How does it work up the call stack?
- **Checked exceptions** - When to use throws?
- **Unchecked exceptions** - When to use throw?
- **Try-catch-finally** - How does control flow work?
- **Custom exceptions** - How to throw them?
- **Exception hierarchy** - Which exceptions to declare?
- **Exception chaining** - How to preserve stack trace?
- **Multiple catch blocks** - How to handle different types?
- **Try-with-resources** - Automatic exception handling?
- **Throws with inheritance** - Covariant return types?

------------------------------------------------

7. What is try block?

**Definition:**
The try block contains code that may potentially throw an exception during execution.

**Key Characteristics:**
- Wraps code that might throw exception
- Must be followed by catch block or finally block (or both)
- Cannot exist alone
- Executed sequentially from top to bottom
- If exception occurs, execution stops and control transfers to catch block
- If no exception, all code executes normally

**Syntax:**
```java
try {
    // Code that may throw exception
    // These are called risky operations
}
```

**What Inside try Block?**

```java
try {
    // File operations (may throw IOException)
    FileReader reader = new FileReader("file.txt");
    
    // Database operations (may throw SQLException)
    Connection conn = DriverManager.getConnection(url);
    
    // Array access (may throw ArrayIndexOutOfBoundsException)
    int value = array[10];
    
    // Object method calls (may throw NullPointerException)
    String str = null;
    str.length();
    
    // Type casting (may throw ClassCastException)
    Object obj = "String";
    Integer num = (Integer) obj;
    
    // Arithmetic (may throw ArithmeticException)
    int result = 10 / 0;
}
```

**Execution Flow - No Exception:**
```java
try {
    System.out.println("Statement 1");  // Executes
    System.out.println("Statement 2");  // Executes
    System.out.println("Statement 3");  // Executes
} catch (Exception e) {
    System.out.println("Exception");    // SKIPPED
}
System.out.println("After");           // Executes

Output:
Statement 1
Statement 2
Statement 3
After
```

**Execution Flow - With Exception:**
```java
try {
    System.out.println("Statement 1");      // Executes
    int x = 10 / 0;                         // Exception thrown here
    System.out.println("Statement 2");      // SKIPPED
    System.out.println("Statement 3");      // SKIPPED
} catch (ArithmeticException e) {
    System.out.println("Exception caught"); // Executes
}
System.out.println("After");               // Executes

Output:
Statement 1
Exception caught
After
```

**try Block with catch:**
```java
try {
    int[] numbers = {1, 2, 3};
    System.out.println(numbers[5]);  // Exception here
    System.out.println("Never reached");
} catch (ArrayIndexOutOfBoundsException e) {
    System.out.println("Array index out of bounds");
}
```

**try Block with finally:**
```java
try {
    System.out.println("Try block");
    int x = 10 / 0;  // Exception
} finally {
    System.out.println("Finally block");  // Always executes
}

Output:
Try block
Finally block
Exception thrown: ArithmeticException
```

**try Block with Multiple Statements:**
```java
try {
    // Declaration
    FileReader reader = new FileReader("data.txt");
    BufferedReader br = new BufferedReader(reader);
    
    // Reading
    String line = br.readLine();
    System.out.println("Read: " + line);
    
    // Processing
    int count = Integer.parseInt(line);
    
    // Using
    System.out.println("Count: " + count);
} catch (FileNotFoundException e) {
    System.out.println("File not found");
} catch (IOException e) {
    System.out.println("IO error");
} catch (NumberFormatException e) {
    System.out.println("Invalid number");
}
```

**Important Rules:**

**Rule 1: try must have catch or finally**
```java
// ❌ Invalid - nothing after try
try {
    // code
}

// ✅ Valid - has catch
try {
    // code
} catch (Exception e) {
}

// ✅ Valid - has finally
try {
    // code
} finally {
}

// ✅ Valid - has both
try {
    // code
} catch (Exception e) {
} finally {
}
```

**Rule 2: Code after exception stops**
```java
try {
    System.out.println("A");  // Executes
    throw new Exception();
    System.out.println("B");  // Never reaches here
} catch (Exception e) {
    System.out.println("C");  // Executes
}

Output: A, C
```

**Rule 3: Single exception stops block**
```java
try {
    int[] arr = {1, 2};
    System.out.println(arr[5]);  // First exception here
    System.out.println(arr[10]); // Never executed
} catch (ArrayIndexOutOfBoundsException e) {
    System.out.println("Index error");
}
```

**Real-World Example - Resource Processing:**
```java
import java.io.*;

public class FileProcessor {
    public void processFile(String filename) {
        try {
            // Resource creation (may throw IOException)
            FileInputStream fis = new FileInputStream(filename);
            BufferedInputStream bis = new BufferedInputStream(fis);
            
            // Risky operations (may throw IOException)
            int data;
            while ((data = bis.read()) != -1) {
                System.out.print((char) data);
            }
            
            // Resource cleanup
            bis.close();
            fis.close();
            
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}
```

**Java 7+ try-with-resources (Better approach):**
```java
public void processFile(String filename) {
    try (BufferedInputStream bis = new BufferedInputStream(
             new FileInputStream(filename))) {
        // Risky code in try block
        int data;
        while ((data = bis.read()) != -1) {
            System.out.print((char) data);
        }
    } catch (IOException e) {
        System.out.println("Error: " + e.getMessage());
    }
    // Resources auto-closed here
}
```

**Best Practices for try Block:**

```java
// ✅ Good: Minimal scope - only risky code
try {
    int result = 10 / divisor;  // Only risky operation
    System.out.println(result);
} catch (ArithmeticException e) {
    System.out.println("Division by zero");
}

// ❌ Bad: Too much code - mixes risky and safe code
try {
    System.out.println("Starting...");
    int result = 10 / divisor;
    System.out.println("Result: " + result);
    // Too much unrelated code
} catch (ArithmeticException e) {
    System.out.println("Error");
}

// ❌ Bad: Initialization outside try
FileReader reader;  // ❌ Resource leak risk
try {
    reader = new FileReader("file.txt");
    reader.read();
} catch (IOException e) {
}

// ✅ Good: Initialization inside try
try {
    FileReader reader = new FileReader("file.txt");  // ✅ Better scope
    reader.read();
} catch (IOException e) {
}
```

**⚠️ Keywords for Follow-up Questions:**
- **Catch block** - How to handle exceptions?
- **Finally block** - When does it execute?
- **Control flow** - What happens when exception occurs?
- **Exception propagation** - How does it continue?
- **Try-with-resources** - How to auto-close resources?
- **Multiple catch blocks** - Order of exception handling?
- **Nested try blocks** - How to handle cascading?
- **Exception variable scope** - When is 'e' accessible?
- **Return in try** - Does finally still execute?

------------------------------------------------

8. What is catch block?

**Definition:**
The catch block handles exceptions that are thrown within the associated try block.

**Key Characteristics:**
- Follows try block (mandatory)
- Catches specific exception types
- Executes only if matching exception occurs
- Can have multiple catch blocks
- Receives exception object as parameter
- Allows recovery from error conditions
- Execution of remaining catch blocks stops after one matches

**Syntax:**
```java
try {
    // risky code
} catch (ExceptionType variable) {
    // handling code
}
```

**How catch Works:**

```java
try {
    int result = 10 / 0;  // Throws ArithmeticException
} catch (ArithmeticException e) {  // Catches it
    System.out.println("Error: " + e.getMessage());
}
```

**Exception Parameter:**
```java
catch (IOException e) {
    // e is the exception object
    e.getMessage();      // Get error message
    e.printStackTrace(); // Print stack trace
    e.getCause();        // Get underlying cause
    e.toString();        // Get string representation
}
```

**Accessing Exception Information:**

```java
try {
    FileReader reader = new FileReader("file.txt");
} catch (FileNotFoundException e) {
    // Exception information methods:
    String msg = e.getMessage();        // "file.txt (No such file or directory)"
    
    e.printStackTrace();  // Prints:
                         // java.io.FileNotFoundException: file.txt
                         // at java.io.FileInputStream.open()
                         // at java.io.FileReader.<init>()
    
    StackTraceElement[] trace = e.getStackTrace();
    // Array of method calls leading to exception
    
    Throwable cause = e.getCause();
    // Original exception if this was wrapped
    
    String fullInfo = e.toString();
    // "java.io.FileNotFoundException: file.txt"
}
```

**Multiple catch Blocks:**

```java
try {
    // Multiple operations may throw different exceptions
    int[] arr = {1, 2, 3};
    FileReader reader = new FileReader("file.txt");
    Object obj = "String";
    Integer num = (Integer) obj;  // Throws ClassCastException
} catch (FileNotFoundException e) {
    // Handles file not found
    System.out.println("File missing");
} catch (ClassCastException e) {
    // Handles casting error
    System.out.println("Type mismatch");
} catch (ArrayIndexOutOfBoundsException e) {
    // Handles array index error
    System.out.println("Array index invalid");
} catch (IOException e) {
    // Handles any other IO error
    System.out.println("IO error");
}
```

**Order Matters - Most Specific First:**

```java
// ✅ CORRECT - Specific to General
try {
    // code
} catch (FileNotFoundException e) {      // Most specific
    System.out.println("File not found");
} catch (IOException e) {                 // More general
    System.out.println("IO error");
} catch (Exception e) {                   // Most general
    System.out.println("Any error");
}

// ❌ WRONG - General to Specific (won't compile)
try {
    // code
} catch (Exception e) {
    // Catches everything, making others unreachable
} catch (IOException e) {
    // Never reached - IOException already caught above
} catch (FileNotFoundException e) {
    // Never reached
}
```

**Why Order Matters:**
```
FileNotFoundException IS-A IOException IS-A Exception

If catch(Exception) comes first:
- FileNotFoundException matches Exception
- IOException matches Exception
- More specific catches become unreachable (dead code)
```

**Catch Block Execution Flow:**

```java
try {
    String str = null;
    System.out.println(str.length());  // NullPointerException here
    System.out.println("After length");  // SKIPPED
} catch (StringIndexOutOfBoundsException e) {
    System.out.println("String error");  // SKIPPED - doesn't match
} catch (NullPointerException e) {
    System.out.println("Null error");    // EXECUTES - matches
    System.out.println("Recovery: " + e.getMessage());
} catch (Exception e) {
    System.out.println("General error"); // SKIPPED - already matched above
}
System.out.println("After catch");      // Executes

Output:
Null error
Recovery: Cannot invoke method on null object
After catch
```

**Java 7+ Multi-catch (Catching multiple exceptions in one block):**

```java
// Multiple exceptions in single catch
try {
    // code that may throw IOException or SQLException
} catch (IOException | SQLException e) {
    System.out.println("Database or file error: " + e.getMessage());
}

// Multiple, unrelated exceptions
try {
    // risky code
} catch (NullPointerException | ArrayIndexOutOfBoundsException | ArithmeticException e) {
    System.out.println("Runtime error: " + e.getMessage());
}

// Limitations:
// catch (IOException | FileNotFoundException e)  // ❌ Invalid - redundant
// FileNotFoundException is subclass of IOException
```

**Example: Real-World Error Handling:**

```java
public class BankService {
    
    public void transferMoney(Account from, Account to, double amount) {
        try {
            // Validation
            if (from == null || to == null) {
                throw new IllegalArgumentException("Accounts cannot be null");
            }
            
            // Business logic that may fail
            from.debit(amount);
            to.credit(amount);
            
            // Database persistence that may fail
            saveTransaction("TRANSFER", from, to, amount);
            
        } catch (IllegalArgumentException e) {
            System.out.println("Validation error: " + e.getMessage());
            // Don't process transaction
            
        } catch (InsufficientFundsException e) {
            System.out.println("Error: " + e.getMessage());
            // Reverse debit if credit fails
            
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            // Log error, alert admin
            logError(e);
            
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
            // Generic fallback
            e.printStackTrace();
        }
    }
}
```

**Exception Handling Strategies:**

**Strategy 1: Log and Rethrow**
```java
try {
    riskyOperation();
} catch (IOException e) {
    logger.error("Operation failed: " + e.getMessage());
    throw e;  // Pass to caller
}
```

**Strategy 2: Log and Continue**
```java
try {
    riskyOperation();
} catch (IOException e) {
    logger.warn("Operation failed: " + e.getMessage());
    // Continue processing
}
```

**Strategy 3: Log and Use Default**
```java
try {
    config = loadConfig();
} catch (IOException e) {
    logger.warn("Config file missing, using defaults");
    config = getDefaultConfig();
}
```

**Strategy 4: Log and Retry**
```java
int retries = 3;
while (retries > 0) {
    try {
        connectToDatabase();
        break;  // Success
    } catch (SQLException e) {
        retries--;
        if (retries == 0) {
            throw e;
        }
        logger.warn("Connection failed, retrying...");
        Thread.sleep(1000);
    }
}
```

**Best Practices:**

```java
// ✅ Good: Specific exception catch
try {
    readFile();
} catch (FileNotFoundException e) {
    System.out.println("File missing");
}

// ❌ Bad: Catching too generic Exception
try {
    readFile();
} catch (Exception e) {  // Too broad
    System.out.println("Error");
}

// ✅ Good: Use exception variable
catch (IOException e) {
    System.out.println("Error: " + e.getMessage());  // Informative
}

// ❌ Bad: Ignore exception variable
catch (IOException e) {
    System.out.println("Error occurred");  // Not informative
}

// ✅ Good: Log for debugging
catch (SQLException e) {
    logger.error("Database error", e);
}

// ❌ Bad: Silent failure
catch (SQLException e) {
    // empty catch - bad practice
}
```

**⚠️ Keywords for Follow-up Questions:**
- **Try block** - How to wrap risky code?
- **Finally block** - When to use for cleanup?
- **Exception parameter** - How to access error info?
- **Multiple catch blocks** - What's the order?
- **Exception hierarchy** - Why does order matter?
- **Multi-catch (Java 7)** - How to catch multiple in one block?
- **Exception chaining** - How to wrap exceptions?
- **Logging exceptions** - Best logging practices?
- **Exception recovery** - Different strategies?
- **Nested try-catch** - When to use?

------------------------------------------------

9. What is finally block?

The finally block always executes whether exception occurs or not.

Used for cleanup operations.

------------------------------------------------

10. Will finally always execute?

Yes, except in cases like:
System.exit()
JVM crash

------------------------------------------------

11. What happens if an exception occurs and no catch block is present?

The exception propagates to the calling method. If not handled anywhere, JVM terminates the program.

------------------------------------------------

12. What is Exception Propagation?

Exception propagation means passing the exception from one method to its caller.

------------------------------------------------

13. What is a Custom Exception?

A user-defined exception created by extending Exception or RuntimeException.

Example:
class MyException extends Exception {}

------------------------------------------------

14. What is try-with-resources?

Introduced in Java 7 to automatically close resources.

Example:
try (BufferedReader br = new BufferedReader(new FileReader("file.txt"))) {}

------------------------------------------------

15. What is the difference between final, finally, and finalize?

final
Used for variables, methods, and classes.

finally
Block used in exception handling.

finalize
Method used by garbage collector (deprecated).

------------------------------------------------

16. Can we have multiple catch blocks?

Yes. Multiple catch blocks can handle different types of exceptions.

------------------------------------------------

17. Can we write try without catch?

Yes, if there is a finally block.

Example:
try {
   // code
} finally {
}

------------------------------------------------

18. Can we write try without finally?

Yes, but must have at least one catch block.

------------------------------------------------

19. What happens if exception occurs in finally block?

The exception from finally block overrides previous exception.

------------------------------------------------

20. Can we have multiple finally blocks?

No. Only one finally block is allowed.

------------------------------------------------

21. Can we catch multiple exceptions in one catch block?

Yes (Java 7+)

Example:
catch(IOException | SQLException e)

------------------------------------------------

22. What is Stack Trace?

Stack trace shows method call sequence where the exception occurred.

------------------------------------------------

23. What is the root class of all exceptions?

Throwable

------------------------------------------------

24. What is the difference between RuntimeException and Checked Exception?

RuntimeException
Occurs during runtime and not checked by compiler.

Checked Exception
Checked at compile time and must be handled.

------------------------------------------------

25. What is the best practice for exception handling?

- Catch specific exceptions
- Avoid catching Exception class
- Log exceptions properly
- Use custom exceptions when needed
