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

# 📝 Summary for Quick Revision

A brief recap of all the key interview questions and their answers:

1. **What is an Exception?**
   - An event disrupting normal program flow; Errors are serious, Exceptions are usually recoverable.
2. **Checked vs Unchecked:**
   - Checked: compile-time, must handle/declare (e.g., `IOException`).
   - Unchecked: runtime, optional handling (e.g., `NullPointerException`).
3. **Exception Hierarchy:**
   - `Throwable` → `Error`/`Exception` → `RuntimeException` etc. Draw the tree.
4. **try-catch-finally:**
   - `try` holds risky code, `catch` handles specific exceptions, `finally` always executes (even after return, unless JVM exits).
5. **throw vs throws:**
   - `throw` to raise an exception explicitly; `throws` declares potential exceptions in method signature.
6. **Custom Exceptions:**
   - Extend `Exception` or `RuntimeException`; use to signal domain-specific issues.
7. **Multiple catch blocks:**
   - Order matters; first matching block executes.
8. **try-with-resources:**
   - Auto-closes `AutoCloseable` resources, introduced in Java 7.
9. **Exception Propagation:**
   - Unhandled exceptions bubble up the call stack to the caller.
10. **finally behavior:**
   - Executes even with return or exceptions; not executed if `System.exit()` is called.
11. **Best Practices:**
   - Catch specific exceptions, prefer custom messages, avoid generic `Exception`/`Throwable`, log appropriately.

---

Use this section to review before interviews or as a cheat-sheet for quick memory refresh.

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

**Definition:**
The finally block contains code that ALWAYS executes regardless of whether an exception occurs or not.

**Key Characteristics:**
- Executes whether exception occurs or not
- Executes even if try block has return statement
- Used for cleanup operations
- Optional but important for resource management
- Always executes last in try-catch-finally block
- Guaranteed execution in most scenarios
- Cannot exist without try block

**Syntax:**
```java
try {
    // risky code
} catch (Exception e) {
    // handle exception
} finally {
    // always executes code
}
```

**Execution Guarantee Scenarios:**

**Scenario 1: No exception**
```java
try {
    System.out.println("Try");
} catch (Exception e) {
    System.out.println("Catch");
} finally {
    System.out.println("Finally");
}

Output:
Try
Finally
```

**Scenario 2: Exception thrown and caught**
```java
try {
    System.out.println("Try");
    throw new Exception();
    System.out.println("Try after throw");  // SKIPPED
} catch (Exception e) {
    System.out.println("Catch");
} finally {
    System.out.println("Finally");
}

Output:
Try
Catch
Finally
```

**Scenario 3: With return statement in try**
```java
public void testFinally() {
    try {
        System.out.println("Try");
        return;  // Still goes to finally
    } finally {
        System.out.println("Finally");
    }
}

Output:
Try
Finally
```

**Scenario 4: With return statement in catch**
```java
public void testFinally() {
    try {
        System.out.println("Try");
        throw new Exception();
    } catch (Exception e) {
        System.out.println("Catch");
        return;  // Still goes to finally
    } finally {
        System.out.println("Finally");
    }
}

Output:
Try
Catch
Finally
```

**Common Uses:**

**Use Case 1: Closing Resources**
```java
FileReader reader = null;
try {
    reader = new FileReader("file.txt");
    reader.read();  // May throw IOException
} catch (IOException e) {
    System.out.println("Error reading file");
} finally {
    // Always close resource, regardless of exception
    if (reader != null) {
        try {
            reader.close();  // May throw IOException
        } catch (IOException e) {
            System.out.println("Error closing file");
        }
    }
}
```

**Better with try-with-resources (Java 7+):**
```java
try (FileReader reader = new FileReader("file.txt")) {
    reader.read();
} catch (IOException e) {
    System.out.println("Error");
}
// Resource auto-closed in finally equivalent
```

**Use Case 2: Database Connection Cleanup**
```java
Connection conn = null;
try {
    conn = DriverManager.getConnection(url, user, password);
    Statement stmt = conn.createStatement();
    ResultSet rs = stmt.executeQuery(sql);
    // Process results
} catch (SQLException e) {
    System.out.println("Database error");
} finally {
    // Always close connection
    if (conn != null) {
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error closing connection");
        }
    }
}
```

**Use Case 3: Logging and Cleanup**
```java
long startTime = System.currentTimeMillis();
try {
    processLargeFile();
} catch (IOException e) {
    logger.error("Error processing file", e);
} finally {
    // Always log execution time
    long duration = System.currentTimeMillis() - startTime;
    logger.info("Execution took " + duration + " ms");
    
    // Always clean up resources
    cleanupTempFiles();
}
```

**Use Case 4: Releasing Locks**
```java
lock.lock();
try {
    criticalSection();
} catch (Exception e) {
    handleError(e);
} finally {
    // Always release lock
    lock.unlock();
}
```

**WHEN finally DOES NOT Execute:**

**Exception 1: System.exit()**
```java
try {
    System.out.println("Try");
    System.exit(0);  // JVM terminates immediately
} finally {
    System.out.println("Finally");  // NEVER EXECUTES
}

Output:
Try
```

**Exception 2: JVM Crash**
```java
try {
    // Code
} finally {
    // NEVER executes if JVM crashes
}
```

**Exception 3: Infinite Loop**
```java
try {
    while (true) {  // Infinite loop
        // code
    }
} finally {
    System.out.println("Finally");  // Never reached
}
```

**Exception 4: Death of Thread**
```java
try {
    // code
} finally {
    // May not execute if thread dies abruptly
}
```

**finally with Return Values:**

**Scenario A: return in try**
```java
public int getValue() {
    try {
        return 10;  // Return value
    } finally {
        System.out.println("Finally");  // Executes before return
    }
}

Output:
Finally
Returned: 10
```

**Scenario B: return in finally overrides return in try**
```java
public int getValue() {
    try {
        return 10;
    } finally {
        return 20;  // Overrides try's return
    }
}

Result: 20  // finally's return wins
```

**Scenario C: Exception in finally**
```java
try {
    throw new Exception("Original");
} finally {
    throw new RuntimeException("In finally");
}

Result: RuntimeException thrown (original exception suppressed)
```

**Best Practice - Avoid returning from finally:**
```java
// ❌ Bad: Returning from finally
public int tricky() {
    try {
        throw new Exception();
    } catch (Exception e) {
        return 1;
    } finally {
        return 2;  // Confusing - suppresses exception
    }
}

// ✅ Good: Only cleanup in finally
public int clear() {
    try {
        // code
        return 1;
    } catch (Exception e) {
        return 2;
    } finally {
        cleanup();  // Just cleanup, no return
    }
}
```

**finally Block Order:**

```java
try {
    System.out.println("1. Try");
    return 5;
} catch (Exception e) {
    System.out.println("2. Catch");  // Not executed
} finally {
    System.out.println("3. Finally");  // Executes before return
}

Output:
1. Try
3. Finally
Returned: 5
```

**Multiple try-finally:**

```java
try {
    try {
        System.out.println("Inner try");
    } finally {
        System.out.println("Inner finally");
    }
} finally {
    System.out.println("Outer finally");
}

Output:
Inner try
Inner finally
Outer finally
```

**⚠️ Keywords for Follow-up Questions:**
- **try-with-resources** - How to auto-close resources?
- **Control flow** - What happens before finally executes?
- **Return statements** - Does finally delay return?
- **Resource management** - How to ensure cleanup?
- **Exception suppression** - How finally affects exceptions?
- **Lock releasing** - Pattern for synchronized blocks?
- **Nested try-finally** - Order of execution?
- **System.exit()** - When finally doesn't execute?

------------------------------------------------

10. Will finally always execute?

**Short Answer:** 
Yes, finally ALMOST always executes. Exceptions exist where it doesn't.

**Detailed Explanation:**

finally executes in ALL normal cases, but NOT in these scenarios:

**Case 1: System.exit()**
Calls JVM termination
```java
try {
    System.out.println("Try");       // Executes
    System.exit(0);                  // JVM terminates
} finally {
    System.out.println("Finally");   // NEVER executes
}

Output:
Try
```

**Case 2: JVM Crashes/Shutdown**
```java
try {
    // Code
    throw new StackOverflowError();  // JVM may crash
} finally {
    // May not execute in JVM crash
}
```

**Case 3: Infinite Loop**
```java
try {
    while(true) {  // Never returns
        // Infinite loop
    }
} finally {
    System.out.println("Finally");  // Never reached
}
```

**Case 4: Exception Escapes (rare)**
```java
try {
    // Code
} finally {
    throw new Exception();  // If finally throws, it escapes
}
```

**Case 5: Thread Death**
```java
try {
    // Code
} finally {
    // May not execute if thread is forcefully killed
}
```

**Normal Cases - finally WILL Execute:**

**✅ With normal completion:**
```java
try {
    System.out.println("Try");
} finally {
    System.out.println("Finally");
}
Output: Try, Finally
```

**✅ With exception in try:**
```java
try {
    throw new Exception();
} catch (Exception e) {
    System.out.println("Caught");
} finally {
    System.out.println("Finally");  // Executes
}
Output: Caught, Finally
```

**✅ With exception not caught:**
```java
try {
    throw new RuntimeException();
} finally {
    System.out.println("Finally");  // Executes first
}
Output: Finally, then RuntimeException
```

**✅ With return in try:**
```java
public void test() {
    try {
        return;
    } finally {
        System.out.println("Finally");  // Executes even with return
    }
}
Output: Finally
```

**✅ With return in catch:**
```java
public void test() {
    try {
        throw new Exception();
    } catch (Exception e) {
        return;
    } finally {
        System.out.println("Finally");  // Executes even with return
    }
}
Output: Finally
```

**✅ With break/continue:**
```java
for (int i = 0; i < 5; i++) {
    try {
        System.out.println(i);
        if (i == 2) break;
    } finally {
        System.out.println("Finally " + i);  // Executes before break
    }
}
Output: 0, Finally 0, 1, Finally 1, 2, Finally 2
```

**Practical Interview Answers:**

**Q: Will finally execute if there's a return in try?**
```java
public int getValue() {
    try {
        System.out.println("Try");
        return 10;
    } finally {
        System.out.println("Finally");
    }
}

getValue();
Output:
Try
Finally
Returned: 10

Answer: YES, finally executes before return
```

**Q: Will finally execute if System.exit() is called?**
```java
public void test() {
    try {
        System.out.println("Try");
        System.exit(0);
    } finally {
        System.out.println("Finally");
    }
}

test();
Output:
Try
(Program terminates, Finally never prints)

Answer: NO, JVM terminates before finally can execute
```

**Q: Will finally execute if exception occurs but no catch?**
```java
public void test() {
    try {
        throw new IOException();
    } finally {
        System.out.println("Finally");
    }
}

test();
Output:
Finally
IOException thrown

Answer: YES, finally executes BEFORE exception propagates
```

**Complete Execution Flow Chart:**

```
TRY BLOCK
    └ No exception
         └ finally executes ✓
    └ Exception occurs
         └ catch matches
              └ finally executes ✓
         └ catch doesn't match
              └ finally executes ✓ (then propagates)
         └ no catch block
              └ finally executes ✓ (then propagates)

RETURN/BREAK/CONTINUE
    └ In try/catch
         └ finally executes ✓
    └ In finally
         └ Completes as stated

SPECIAL CASES
    └ System.exit()
         └ finally does NOT execute ✗
    └ Thread death
         └ finally may NOT execute ✗
    └ JVM crash
         └ finally does NOT execute ✗
    └ Infinite loop
         └ finally never reached ✗
```

**Best Practice Example:**

```java
public class ResourceManagement {
    public void processFile(String filename) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            // ALWAYS cleanup, even on early return or exception
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.out.println("Error closing: " + e.getMessage());
                }
            }
            System.out.println("Finally: Resource cleanup complete");
        }
    }
}
```

**Java 7+ Better Pattern (try-with-resources):**

```java
public void processFile(String filename) {
    try (BufferedReader reader = new BufferedReader(
             new FileReader(filename))) {
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    } catch (IOException e) {
        System.out.println("Error: " + e.getMessage());
    }
    // Resource automatically closed (equivalent to finally)
}
```

**⚠️ Keywords for Follow-up Questions:**
- **System.exit()** - When does finally not execute?
- **Try-with-resources** - Better than finally for cleanup?
- **Control flow** - How does finally delay return?
- **Exception propagation** - Does finally delay propagation?
- **Return value** - Can finally override return?
- **Resource cleanup** - Best practices for finally?
- **Thread termination** - Can finally fail?
- **JVM crashes** - How to handle them?

------------------------------------------------

11. What happens if an exception occurs and no catch block is present?

**Explanation:**

When an exception is thrown in a try block but there's no matching catch block, the exception **propagates up** to the calling method.

**Execution Flow:**

```
method1() {
    method2();     ← Exception propagates here
}

method2() {
    method3();     ← Exception propagates here
}

method3() {
    throw new Exception();  ← Exception thrown here
}

Result: Exception reaches JVM, program terminates
```

**Example 1: No catch block**

```java
public void riskyOperations() {
    try {
        int result = 10 / 0;  // ArithmeticException thrown
    } finally {
        System.out.println("Finally");
    }
    // No catch block
}

public static void main(String[] args) {
    riskyOperations();  // Exception reaches here
    System.out.println("Never reached");  // Program terminates before this
}

Output:
Finally
Exception in thread "main" java.lang.ArithmeticException: / by zero
    at Example.riskyOperations()
    at Example.main()
```

**Example 2: Unmatched exception type**

```java
public void process() {
    try {
        throw new IOException("File error");
    } catch (NullPointerException e) {  // Does not match IOException
        System.out.println("Caught NPE");
    }
    // IOException not caught, propagates up
}

public static void main(String[] args) {
    process();  // IOException reaches here
    System.out.println("Never reached");  // Program terminates
}

Console:
Exception in thread "main" java.io.IOException: File error
    at Example.process()
    at Example.main()
```

**Example 3: With finally block**

```java
public void operation() {
    try {
        System.out.println("Try");
        throw new RuntimeException("Error");
    } finally {
        System.out.println("Finally");  // Executes
    }
    // Always executes even without catch
}

public static void main(String[] args) {
    operation();  // Exception propagates after finally
}

Output:
Try
Finally
Exception in thread "main" java.lang.RuntimeException: Error
```

**Exception Propagation Up the Call Stack:**

```java
public void methodA() {
    System.out.println("A: Start");
    methodB();  ← Exception caught here
    System.out.println("A: End");  // SKIPPED
}

public void methodB() {
    System.out.println("B: Start");
    methodC();  ← Exception propagates here
    System.out.println("B: End");  // SKIPPED
}

public void methodC() {
    System.out.println("C: Start");
    throw new IllegalArgumentException("Invalid");  // Exception thrown
}

public static void main(String[] args) {
    try {
        methodA();
    } catch (IllegalArgumentException e) {
        System.out.println("Caught in main: " + e.getMessage());
    }
}

Output:
A: Start
B: Start
C: Start
Caught in main: Invalid
```

**Stack Trace Explanation:**

```
Exception in thread "main" java.io.FileNotFoundException: file.txt
    at java.io.FileInputStream.open(FileInputStream.java:195)
    at java.io.FileInputStream.<init>(FileInputStream.java:138)
    at Example.readFile(Example.java:25)
    at Example.main(Example.java:45)
```

Reading stack trace:
- Line 195 in FileInputStream.open() threw the exception
- Called from line 138 in FileInputStream constructor
- Called from line 25 in readFile() method
- Called from line 45 in main() method

**Throwing Checked Exception Without Handling:**

```java
// ❌ This won't compile
public void readFile() {  // No throws declaration
    FileReader reader = new FileReader("file.txt");
    // Compiler ERROR: Unhandled IOException
}

// ✅ Must either handle or declare
public void readFile() throws IOException {  // Declare
    FileReader reader = new FileReader("file.txt");
}

// OR
public void readFile() {  // Handle
    try {
        FileReader reader = new FileReader("file.txt");
    } catch (IOException e) {
        System.out.println("Error: " + e.getMessage());
    }
}
```

**When Exception Propagates Without catch:**

```
1. Exception thrown in try block
2. No matching catch block found
3. finally block executes (if present)
4. Exception propagates to calling method
5. Process repeats in calling method
6. If no handler found anywhere: JVM terminates
```

**Complete Example: Propagation Chain**

```java
public class ExceptionPropagation {
    
    public static void main(String[] args) {
        System.out.println("1. Main start");
        try {
            level1();  ← Can catch here
        } catch (Exception e) {
            System.out.println("10. Main caught: " + e.getMessage());
        }
        System.out.println("11. Main end");
    }
    
    public static void level1() throws Exception {
        System.out.println("2. Level1 start");
        level2();  ← Propagates here
        System.out.println("9. Level1 end");  // SKIPPED
    }
    
    public static void level2() throws Exception {
        System.out.println("3. Level2 start");
        level3();  ← Propagates here
        System.out.println("8. Level2 end");  // SKIPPED
    }
    
    public static void level3() throws Exception {
        System.out.println("4. Level3 start");
        try {
            riskyOperation();
            System.out.println("6. Level3 continue");
        } finally {
            System.out.println("7. Level3 finally");  // Executes
        }
        System.out.println("8. Level3 end");  // SKIPPED
    }
    
    public static void riskyOperation() throws Exception {
        System.out.println("5. Risky operation");
        throw new Exception("Something went wrong");
    }
}

Output:
1. Main start
2. Level1 start
3. Level2 start
4. Level3 start
5. Risky operation
7. Level3 finally
10. Main caught: Something went wrong
11. Main end
```

**What NOT to Do:**

```java
// ❌ Bad: Catching Exception and ignoring
try {
    riskyOperation();
} catch (Exception e) {
    // Empty catch - exception is hidden
}
System.out.println("Continuing");  // Proceeds without knowing about error

// ✅ Better: At least log
try {
    riskyOperation();
} catch (Exception e) {
    logger.error("Operation failed: " + e.getMessage());
}

// ✅ Best: Re-throw if cannot handle
try {
    riskyOperation();
} catch (Exception e) {
    logger.error("Operation failed", e);
    throw e;  // Propagate to caller
}
```

**⚠️ Keywords for Follow-up Questions:**
- **Exception propagation** - How does it work up call stack?
- **Throws keyword** - How to propagate exceptions?
- **Stack trace** - How to read and interpret it?
- **Finally block** - Does it execute before propagation?
- **Checked exceptions** - Why must they be handled?
- **Unchecked exceptions** - Can they propagate freely?
- **Call stack** - How exceptions trace back?
- **Re-throwing exceptions** - How to propagate after handling?

------------------------------------------------

12. What is Exception Propagation?

**Definition:**
Exception propagation is the process where an exception thrown in a method is passed (propagated) to its calling method if it's not caught locally.

**Key Concept:**
- Exception travels upward through the method call stack
- Each method can catch or propagate further
- If not caught anywhere: JVM terminates
- Only first matching catch block handles it
- finally blocks always execute during propagation

**Simple Example:**

```java
// Exception happens here
public void methodC() {
    throw new RuntimeException("Error");
}

// Propagates here
public void methodB() {
    methodC();  // Exception not caught, propagates
}

// Propagates here
public void methodA() {
    methodB();  // Exception not caught, propagates
}

// Caught here
public static void main(String[] args) {
    try {
        methodA();  // Exception caught here
    } catch (RuntimeException e) {
        System.out.println("Handled: " + e.getMessage());
    }
}

Flow: methodC → methodB → methodA → main (caught)
```

**How Propagation Works:**

**Step 1: Exception thrown**
```java
throw new ApplicationException("Data invalid");
```

**Step 2: Search for catch in current method**
```java
public void process() {
    try {
        // Exception thrown here
        validateData();  ← Does current method handle it?
    } catch (ApplicationException e) {
        // If yes, caught here
    }
    // If no, propagate to caller
}
```

**Step 3: Move to calling method**
```java
public void caller() {
    process();  ← Exception propagates here
    // Does this method handle it?
}
```

**Step 4: Continue up call stack**
```java
public static void main(String[] args) {
    caller();  ← Exception reaches here
    // Does main handle it?
}
```

**Step 5: No handler found - JVM terminates**
```
Exception in thread "main" ...
Program crashes
```

**Visual Call Stack Propagation:**

```
Main Thread Call Stack

Top (most recent)
  __________
 | methodD()  |
 |____________|
            |
            | Exception thrown in D
            | No catch in D
            | PROPAGATE UP
            |↓
  __________
 | methodC()  |
 |____________|
            |
            | Exception received from D
            | try-catch exists
            | Catch type matches?
            | NO - doesn't match
            | PROPAGATE UP
            |↓
  __________
 | methodB()  |
 |____________|
            |
            | Exception received from C
            | No catch block
            | PROPAGATE UP
            |↓
  __________
 | methodA()  |
 |____________|
            |
            | Exception received from B
            | try-catch exists
            | Catch type matches?
            | YES - CAUGHT HERE!
            | Exception HANDLED
            |↓
Bottom (initial call)
```

**Practical Example:**

```java
public class ExceptionPropagationDemo {
    
    // Level 3: Throws exception
    public static void divide() {
        System.out.println("3. divide() - throwing exception");
        int result = 10 / 0;  // ArithmeticException
    }
    
    // Level 2: Does not catch, propagates
    public static void calculate() {
        System.out.println("2. calculate() - no catch");
        divide();  // Exception propagates from here
        System.out.println("Never printed in calculate");
    }
    
    // Level 1: Catches exception
    public static void process() {
        System.out.println("1. process() - has try-catch");
        try {
            calculate();  // Exception caught here
        } catch (ArithmeticException e) {
            System.out.println("4. Caught in process: " + e.getMessage());
        }
        System.out.println("5. process() - after catch");
    }
    
    public static void main(String[] args) {
        process();
        System.out.println("6. Program ends normally");
    }
}

Output:
1. process() - has try-catch
2. calculate() - no catch
3. divide() - throwing exception
4. Caught in process: / by zero
5. process() - after catch
6. Program ends normally
```

**Exception Propagation with finally:**

```java
public void methodA() {
    try {
        System.out.println("A: Try");
        methodB();  // Exception propagates after B executes
        System.out.println("A: After B");  // SKIPPED
    } finally {
        System.out.println("A: Finally");  // Executes
    }
}

public void methodB() {
    try {
        System.out.println("B: Try");
        throw new Exception();
    } finally {
        System.out.println("B: Finally");  // Executes
    }
}

public static void main(String[] args) {
    try {
        methodA();
    } catch (Exception e) {
        System.out.println("Main: Caught");
    }
}

Output:
A: Try
B: Try
B: Finally
A: Finally
Main: Caught
```

**Throws Keyword for Propagation:**

```java
// Method declares it may throw exception
public void readFile(String filename) throws IOException {
    FileReader reader = new FileReader(filename);
    // If exception occurs, it propagates to caller
}

// Caller must handle or propagate further
public void processFiles() throws IOException {
    readFile("file1.txt");  // IOException propagates from here
    // If not caught, throws IOException to its caller
}

// Final handler
public static void main(String[] args) {
    try {
        processFiles();
    } catch (IOException e) {
        System.out.println("Error: " + e.getMessage());
    }
}
```

**Exception Propagation Patterns:**

**Pattern 1: Catch and Handle (Stop propagation)**
```java
try {
    riskyOperation();
} catch (Exception e) {
    // Handle completely, no propagation
    System.out.println("Handled: " + e.getMessage());
}
```

**Pattern 2: Catch and Rethrow (Continue propagation)**
```java
try {
    riskyOperation();
} catch (Exception e) {
    System.out.println("Logged: " + e.getMessage());
    throw e;  // Propagate to caller
}
```

**Pattern 3: Catch and Wrap (Transform exception)**
```java
try {
    databaseOperation();
} catch (SQLException e) {
    throw new ApplicationException("DB error", e);  // Wrap and propagate
}
```

**Pattern 4: Catch and Ignore (Suppress propagation)**
```java
try {
    nonCriticalOperation();
} catch (Exception e) {
    logger.warn("Non-critical failure");  // Just log, don't propagate
}
```

**When Propagation Stops:**

```
1. Exception is caught by matching catch block
2. Catch block completes successfully
3. Control moves to finally (if exists)
4. Execution continues after try-catch-finally
5. Propagation STOPS
```

**⚠️ Keywords for Follow-up Questions:**
- **Throws keyword** - How to declare propagated exceptions?
- **Finally block** - Executes during propagation?
- **Call stack** - How to read exception stack trace?
- **Exception chaining** - How to wrap and propagate?
- **Checked exceptions** - Must be propagated or caught?
- **Unchecked exceptions** - Can be propagated anywhere?
- **Re-throwing** - How to propagate in catch block?
- **Exception handling strategies** - Different approaches?
- **Stack trace** - What information does it provide?

------------------------------------------------

13. What is a Custom Exception?

**Definition:**
A custom exception is a user-defined exception class created by extending the Exception class or RuntimeException class to handle application-specific error conditions.

**Why Create Custom Exceptions?**
- More meaningful error messages
- Application-specific error handling
- Differentiate from standard exceptions
- Better code documentation
- Cleaner API design

**Creating Custom Checked Exception:**

**Simple Custom Exception:**
```java
// Extending Exception (Checked exception)
public class InvalidAgeException extends Exception {
    // Constructor
    public InvalidAgeException(String message) {
        super(message);
    }
}

// Using custom exception
public class User {
    public void setAge(int age) throws InvalidAgeException {
        if (age < 0 || age > 150) {
            throw new InvalidAgeException("Age must be between 0 and 150");
        }
    }
}

// Handling custom exception
public static void main(String[] args) {
    try {
        User user = new User();
        user.setAge(-5);
    } catch (InvalidAgeException e) {
        System.out.println("Error: " + e.getMessage());
    }
}
```

**Custom Exception with Multiple Constructors:**

```java
public class InsufficientFundsException extends Exception {
    private double requiredAmount;
    private double availableAmount;
    
    // Constructor 1: Message only
    public InsufficientFundsException(String message) {
        super(message);
    }
    
    // Constructor 2: With amounts
    public InsufficientFundsException(String message, 
                                      double required,
                                      double available) {
        super(message);
        this.requiredAmount = required;
        this.availableAmount = available;
    }
    
    // Constructor 3: With cause (exception chaining)
    public InsufficientFundsException(String message, Throwable cause) {
        super(message, cause);
    }
    
    // Getters
    public double getRequiredAmount() {
        return requiredAmount;
    }
    
    public double getAvailableAmount() {
        return availableAmount;
    }
    
    public double getShortfall() {
        return requiredAmount - availableAmount;
    }
}

// Usage
public class BankAccount {
    private double balance = 100.0;
    
    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount > balance) {
            throw new InsufficientFundsException(
                "Cannot withdraw " + amount,
                amount,
                balance
            );
        }
        balance -= amount;
    }
}

// Handling with custom methods
public static void main(String[] args) {
    BankAccount account = new BankAccount();
    try {
        account.withdraw(150);
    } catch (InsufficientFundsException e) {
        System.out.println("Error: " + e.getMessage());
        System.out.println("Shortfall: " + e.getShortfall());
    }
}
```

**Creating Custom Unchecked Exception (RuntimeException):**

```java
// Extending RuntimeException (Unchecked exception)
public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException(String message) {
        super(message);
    }
    
    public InvalidEmailException(String message, Throwable cause) {
        super(message, cause);
    }
}

// Usage (no throws declaration needed)
public class UserValidator {
    public void validateEmail(String email) {  // No throws
        if (email == null || !email.contains("@")) {
            throw new InvalidEmailException("Invalid email format: " + email);
        }
    }
}

// Handling
public static void main(String[] args) {
    try {
        UserValidator validator = new UserValidator();
        validator.validateEmail("invalid");
    } catch (InvalidEmailException e) {
        System.out.println("Validation failed: " + e.getMessage());
    }
}
```

**Custom Exception Hierarchy:**

```java
// Base custom exception
public class ApplicationException extends Exception {
    public ApplicationException(String message) {
        super(message);
    }
}

// Database exceptions
public class DatabaseException extends ApplicationException {
    public DatabaseException(String message) {
        super(message);
    }
}

public class ConnectionException extends DatabaseException {
    public ConnectionException(String message) {
        super(message);
    }
}

public class QueryException extends DatabaseException {
    public QueryException(String message) {
        super(message);
    }
}

// Business exceptions
public class BusinessException extends ApplicationException {
    public BusinessException(String message) {
        super(message);
    }
}

public class ValidationException extends BusinessException {
    public ValidationException(String message) {
        super(message);
    }
}

// Exception hierarchy
exception - hierarchy:
Exception
  └── ApplicationException
       ├── DatabaseException
       │    ├── ConnectionException
       │    └── QueryException
       │
       └── BusinessException
            └── ValidationException
```

**Exception Chaining (Wrapping):**

```java
public class DataProcessingException extends Exception {
    public DataProcessingException(String message, Throwable cause) {
        super(message, cause);  // Cause is wrapped
    }
}

// Usage: Wrap low-level exception with custom exception
public void processData() throws DataProcessingException {
    try {
        // Low-level operation throwing SQLException
        queryDatabase();
    } catch (SQLException e) {
        // Wrap with custom exception
        throw new DataProcessingException("Failed to process data", e);
    }
}

// Access wrapped cause
public static void main(String[] args) {
    try {
        processData();
    } catch (DataProcessingException e) {
        System.out.println("Error: " + e.getMessage());
        Throwable cause = e.getCause();
        System.out.println("Root cause: " + cause.getMessage());
        cause.printStackTrace();
    }
}
```

**Custom Exception with Additional Context:**

```java
public class OrderException extends Exception {
    private String orderId;
    private int errorCode;
    private long timestamp;
    
    public OrderException(String orderId, String message, int errorCode) {
        super(message);
        this.orderId = orderId;
        this.errorCode = errorCode;
        this.timestamp = System.currentTimeMillis();
    }
    
    // Getters
    public String getOrderId() {
        return orderId;
    }
    
    public int getErrorCode() {
        return errorCode;
    }
    
    public long getTimestamp() {
        return timestamp;
    }
    
    @Override
    public String toString() {
        return String.format(
            "OrderException [orderId=%s, errorCode=%d, message=%s, timestamp=%d]",
            orderId, errorCode, getMessage(), timestamp
        );
    }
}

// Usage
public void cancelOrder(String orderId) throws OrderException {
    boolean orderExists = checkOrder(orderId);
    if (!orderExists) {
        throw new OrderException(orderId, "Order not found", 404);
    }
}

// Handling
public static void main(String[] args) {
    try {
        cancelOrder("ORD123");
    } catch (OrderException e) {
        System.out.println("Error details: " + e.toString());
        System.out.println("Order ID: " + e.getOrderId());
        System.out.println("Error Code: " + e.getErrorCode());
    }
}
```

**Best Practices for Custom Exceptions:**

**✅ Good: Specific, descriptive exception**
```java
public class InvalidConfigurationException extends Exception {
    public InvalidConfigurationException(String message) {
        super(message);
    }
}
```

**✅ Good: Extends appropriate base (checked vs unchecked)**
```java
// For recoverable errors
public class PaymentException extends Exception { }

// For programming errors
public class InvalidParameterException extends RuntimeException { }
```

**✅ Good: Provides useful context**
```java
public class FileProcessingException extends Exception {
    private String filename;
    private int lineNumber;
    
    public FileProcessingException(String message, 
                                   String filename,
                                   int lineNumber) {
        super(message);
        this.filename = filename;
        this.lineNumber = lineNumber;
    }
}
```

**❌ Bad: Too generic**
```java
public class MyException extends Exception { }  // What type of error?
```

**❌ Bad: Doesn't capture context**
```java
public class ErrorException extends Exception { }  // Which error?
```

**Exception Naming Conventions:**

```
✓ Classes to avoid     →   Use instead
  - MyException          →   InvalidArgumentException
  - ErrorException       →   DatabaseException
  - FailureException     →   PaymentProcessingException
  - Exception1           →   DuplicateUserException
```

**Complete Real-World Example:**

```java
public class OrderService {
    
    // Custom exceptions
    public static class InsufficientInventoryException extends Exception {
        public InsufficientInventoryException(String message) {
            super(message);
        }
    }
    
    public static class InvalidOrderException extends Exception {
        private String orderId;
        
        public InvalidOrderException(String orderId, String message) {
            super(message);
            this.orderId = orderId;
        }
    }
    
    // Method using custom exceptions
    public void processOrder(Order order) throws InvalidOrderException, 
                                              InsufficientInventoryException {
        // Validation
        if (order == null || order.getId() == null) {
            throw new InvalidOrderException(null, "Order cannot be null");
        }
        
        // Inventory check
        for (Item item : order.getItems()) {
            if (!hasInventory(item.getId(), item.getQuantity())) {
                throw new InsufficientInventoryException(
                    "Item " + item.getId() + " out of stock"
                );
            }
        }
        
        // Process order
        saveOrder(order);
    }
}

// Usage
public static void main(String[] args) {
    try {
        OrderService service = new OrderService();
        service.processOrder(myOrder);
    } catch (OrderService.InvalidOrderException e) {
        System.out.println("Order validation failed: " + e.getMessage());
    } catch (OrderService.InsufficientInventoryException e) {
        System.out.println("Inventory error: " + e.getMessage());
    }
}
```

**⚠️ Keywords for Follow-up Questions:**
- **Extends Exception vs RuntimeException** - When to use each?
- **Exception chaining** - How to wrap lower-level exceptions?
- **Checked exceptions** - When required to declare?
- **Unchecked exceptions** - When optional to handle?
- **Exception hierarchy** - How to design exception classes?
- **Constructor patterns** - What parameters to include?
- **getMessage()** - How to provide useful messages?
- **getCause()** - How to access wrapped exceptions?
- **Exception naming** - Conventions and patterns?
- **Best practices** - How to design good exceptions?

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

**Short Answer:** YES

**Detailed Explanation:**

Yes, you can have multiple catch blocks to handle different types of exceptions separately.

**Syntax:**
```java
try {
    // Code that may throw different exceptions
} catch (ExceptionType1 e) {
    // Handle ExceptionType1
} catch (ExceptionType2 e) {
    // Handle ExceptionType2
} catch (ExceptionType3 e) {
    // Handle ExceptionType3
}
```

**Simple Example:**
```java
try {
    int[] arr = {1, 2, 3};
    String str = null;
    int result = 10 / 0;
    // Throws ArithmeticException
} catch (ArrayIndexOutOfBoundsException e) {
    System.out.println("Array error: " + e.getMessage());
} catch (NullPointerException e) {
    System.out.println("Null error: " + e.getMessage());
} catch (ArithmeticException e) {
    System.out.println("Arithmetic error: " + e.getMessage());
}
```

**Important Rule: Catch Order Matters**

**Most Specific to Most General (Correct Order):**
```java
// ✅ CORRECT ORDER
try {
    // code
} catch (FileNotFoundException e) {     // Most specific
    System.out.println("File not found");
} catch (IOException e) {                // More general
    System.out.println("IO error");
} catch (Exception e) {                  // Most general
    System.out.println("Any error");
}
```

**How Exception Matching Works:**
```
Exception Hierarchy (Inheritance):
FileNotFoundException extends IOException extends Exception

If you catch IOException FIRST:
- FileNotFoundException ALSO matches IOException
- Specific catch block becomes unreachable (dead code)
- Compiler error
```

**⚠️ Keywords for Follow-up Questions:**
- **Exception hierarchy** - Why does order matter?
- **Catch order** - Most specific to general?
- **Multi-catch (Java 7)** - Syntax and rules?
- **Exception matching** - How does it work?
- **Finally block** - Executes with multiple catch?

------------------------------------------------

17. Can we write try without catch?

**Short Answer:** YES, if you have finally block

**Valid Syntax:**
```java
try {
    // Code that may throw exception
} finally {
    // Cleanup code
}
// catch block NOT required if finally exists
```

**Why try-finally is useful:**
- Ensures cleanup code always runs
- Allows exception to propagate to caller
- Exception is eventually handled by caller or JVM
- Used when current method cannot handle exception

**Example 1: try-finally (No catch)**
```java
public void readFile(String filename) throws IOException {
    BufferedReader reader = null;
    try {
        reader = new BufferedReader(new FileReader(filename));
        String line = reader.readLine();
        System.out.println(line);
    } finally {
        // Cleanup always executes
        if (reader != null) {
            reader.close();
        }
    }
    // If IOException occurs, it propagates AFTER finally
}
```

**Example 2: try-finally with return**
```java
public int getValue() {
    try {
        return 10;
    } finally {
        System.out.println("Finally");  // Executes before return
    }
}

Output:
Finally
Returned: 10
```

**Invalid Syntax: try without catch or finally**

```java
// ❌ INVALID - Compiler ERROR
try {
    // code
}
System.out.println("After");  // Missing catch or finally
// Compiler error: try statement must have catch or finally
```

**Valid Combinations:**
```java
// ✅ Valid: try-catch (with or without finally)
// ✅ Valid: try-finally (without catch)
// ✅ Valid: try-catch-finally (all three)

// ❌ Invalid: try alone
```

**Comparison with try-catch:**

```java
// try-catch: Handles exception, program continues
try {
    int x = 10 / 0;  // ArithmeticException
} catch (ArithmeticException e) {
    System.out.println("Caught");
}
System.out.println("Continues");  // Executes - exception handled

// try-finally: Doesn't handle exception
try {
    int x = 10 / 0;  // ArithmeticException
} finally {
    System.out.println("Finally");
}
System.out.println("Continues");  // SKIPPED - exception propagates
```

**Modern Alternative: try-with-resources (Java 7+)**

```java
// Old way: try-finally
BufferedReader reader = null;
try {
    reader = new BufferedReader(new FileReader("file.txt"));
    // Use
} finally {
    if (reader != null) {
        reader.close();
    }
}

// Modern way: try-with-resources
try (BufferedReader reader = new BufferedReader(
         new FileReader("file.txt"))) {
    // Use
}
// Auto-closed, cleaner than finally
```

**⚠️ Keywords for Follow-up Questions:**
- **Finally block** - What guarantees does it provide?
- **Try-with-resources** - Better than try-finally?
- **Exception propagation** - How does finally affect it?
- **Resource cleanup** - Best practices?

------------------------------------------------

18. Can we write try without finally?

**Short Answer:** YES, as long as you have at least one catch block

**Valid Syntax:**
```java
try {
    // Code that may throw exception
} catch (ExceptionType e) {
    // Handle exception
}
// finally NOT required if catch exists
```

**Why try-catch without finally works:**
- Exception is caught and handled
- No resource cleanup needed
- Program can continue normally
- Most common pattern

**Example 1: try-catch (No finally)**
```java
try {
    int result = 10 / 0;  // ArithmeticException
} catch (ArithmeticException e) {
    System.out.println("Caught: Cannot divide by zero");
}
System.out.println("Program continues");  // Executes normally
```

**Example 2: Multiple catch, no finally**
```java
try {
    FileReader reader = new FileReader("file.txt");
    int data = reader.read();
} catch (FileNotFoundException e) {
    System.out.println("File not found");
} catch (IOException e) {
    System.out.println("IO error");
}
// No finally needed - exception handled
```

**Example 3: try-catch with return (No finally)**
```java
public int safeDivide(int a, int b) {
    try {
        return a / b;  // Returns if no exception
    } catch (ArithmeticException e) {
        System.out.println("Invalid division");
        return 0;  // Returns if exception caught
    }
    // No finally needed
}
```

**When finally IS needed:**
```java
// Resources that must be closed
try {
    BufferedReader reader = new BufferedReader(new FileReader("file.txt"));
    String line = reader.readLine();
} catch (IOException e) {
    System.out.println("Error reading file");
} finally {
    // ✅ Should close reader here (or use try-with-resources)
}
```

**Modern Pattern: try-with-resources (Java 7+)**

```java
// If using try-with-resources, finally not needed
try (BufferedReader reader = new BufferedReader(new FileReader("file.txt"))) {
    String line = reader.readLine();
} catch (IOException e) {
    System.out.println("Error");
}
// Resource auto-closed
```

**Invalid Combinations:**

```java
// ❌ INVALID: try without catch or finally
try {
    // code
}
// Compile error

// ✅ VALID: try-catch (no finally)
try {
    // code
} catch (Exception e) {
    // handle
}

// ✅ VALID: try-finally (no catch)
try {
    // code
} finally {
    // cleanup
}

// ✅ VALID: try-catch-finally (all three)
try {
    // code
} catch (Exception e) {
    // handle
} finally {
    // cleanup
}
```

**⚠️ Keywords for Follow-up Questions:**
- **Catch block** - What does it do?
- **Finally block** - When is it required?
- **Try-with-resources** - Automatic resource management?
- **Resource cleanup** - Best practices?

------------------------------------------------

19. What happens if exception occurs in finally block?

**Key Point:**
If an exception occurs in the finally block, it will **override** or **suppress** any previous exception from the try or catch block.

**Exception Override Scenario:**

```java
try {
    throw new IOException("Original error");
} finally {
    throw new RuntimeException("Error in finally");
}

// Result: RuntimeException is thrown (original IOException is lost)
```

**Example 1: Exception in finally overrides try exception**

```java
public void demo() {
    try {
        System.out.println("1. Try block");
        throw new IOException("Try exception");
    } finally {
        System.out.println("2. Finally block");
        throw new RuntimeException("Finally exception");  // Overrides
    }
}

// Result: RuntimeException is thrown, IOException is lost/suppressed
```

**Best Practices:**

```java
// ❌ Bad: Throw new exception in finally
try {
    riskyOperation();
} finally {
    throw new Exception();  // Loses original exception
}

// ✅ Good: Only cleanup in finally
try {
    riskyOperation();
} finally {
    cleanupResources();  // Don't throw
}

// ✅ Best: Use try-with-resources (Java 7+)
try (Resource res = new Resource()) {
    riskyOperation();
} catch (Exception e) {
    // Both exceptions properly handled
}
```

**⚠️ Keywords for Follow-up Questions:**
- **Exception chaining** - How to preserve original exception?
- **Suppressed exceptions** - Java 7 feature?
- **Try-with-resources** - Better exception handling?
- **Finally cleanup** - Safe patterns?

------------------------------------------------

20. Can we have multiple finally blocks?

**Short Answer:** NO

**Explanation:**
You can have only ONE finally block per try statement. This is a strict Java rule.

**Invalid Example:**

```java
// ❌ NOT ALLOWED - Compiler Error
try {
    // Code
} catch (Exception e) {
} finally {
    // Cleanup 1
}
finally {  // ❌ Compile error: Only one finally allowed
    // Cleanup 2
}
```

**Why Only One finally?**
- Execution order would be ambiguous with multiple finally blocks
- Defeats purpose of guaranteed cleanup
- Keeps control flow clear and predictable
- Simplifies exception handling model

**If You Need Multiple Cleanup Operations:**

```java
// ❌ WRONG: Multiple finally blocks
try {
    // code
} finally {
    close1();
} finally {
    close2();
}

// ✅ RIGHT: One finally with multiple statements
try {
    // code
} finally {
    close1();
    close2();
    close3();
}

// ✅ ALSO RIGHT: Nested try-finally
try {
    try {
        // code
    } finally {
        close1();
    }
} finally {
    close2();
}
```

**Nested Finally Blocks:**

```java
public void nestedFinally() {
    try {
        System.out.println("Outer try");
        try {
            System.out.println("Inner try");
            throw new Exception();
        } finally {
            System.out.println("Inner finally");  // Executes first
        }
    } finally {
        System.out.println("Outer finally");  // Executes second
    }
}

Output:
Outer try
Inner try
Inner finally
Outer finally
```

**Best Practice: Single finally with all cleanup**

```java
public void cleanupExample() {
    Resource1 r1 = null;
    Resource2 r2 = null;
    try {
        r1 = new Resource1();
        r2 = new Resource2();
        // Use resources
    } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
    } finally {
        // Single finally with all cleanup
        if (r2 != null) r2.close();  // Close in reverse order
        if (r1 != null) r1.close();
    }
}
```

**Modern Solution: try-with-resources (Java 7+)**

```java
// Much cleaner - no multiple finally blocks needed
try (Resource1 r1 = new Resource1();
     Resource2 r2 = new Resource2()) {
    // Use resources
} catch (Exception e) {
    System.out.println("Error: " + e.getMessage());
}
// Both resources auto-closed in reverse order
```

**⚠️ Keywords for Follow-up Questions:**
- **Finally block** - Why only one allowed?
- **Nested try-finally** - How to use multiple finally?
- **Try-with-resources** - Better than finally?
- **Cleanup order** - How to manage multiple resources?

------------------------------------------------

21. Can we catch multiple exceptions in one catch block?

**Short Answer:** YES (Java 7+)

**Syntax:**
```java
catch (ExceptionType1 | ExceptionType2 | ExceptionType3 e) {
    // Handle all three exception types
}
```

**Example 1: Multiple unrelated exceptions**

```java
try {
    // Code
} catch (IOException | SQLException | ClassNotFoundException e) {
    System.out.println("Error: " + e.getMessage());
    logger.error("Operation failed", e);
}
```

**Example 2: Multiple runtime exceptions**

```java
try {
    int[] arr = new int[5];
    String str = null;
    Integer num = (Integer) "string";
} catch (ArrayIndexOutOfBoundsException | NullPointerException | 
         ClassCastException e) {
    System.out.println("Data access error: " + e.getMessage());
}
```

**Rules for Multi-catch:**

**✅ VALID: Unrelated exception types**
```java
catch (IOException | SQLException e) {
    // Both are independent - OK
}
```

**❌ INVALID: Redundant exceptions (one is subclass of other)**
```java
catch (IOException | FileNotFoundException e) {
    // FileNotFoundException IS-A IOException
    // Compile error - cannot have subclass and parent
}

// Correct version:
catch (IOException e) {
    // Catches both IOException and FileNotFoundException
}
```

**❌ INVALID: Duplicate exception types**
```java
catch (IOException | IOException e) {
    // Duplicate type - compile error
}
```

**Benefits of Multi-catch:**

```java
// Before Java 7: Verbose and repetitive
try {
    // code
} catch (IOException e) {
    System.out.println("Error: " + e.getMessage());
    logger.error("Failed", e);
} catch (SQLException e) {
    System.out.println("Error: " + e.getMessage());
    logger.error("Failed", e);
}

// Java 7+: Concise with multi-catch
try {
    // code
} catch (IOException | SQLException e) {
    System.out.println("Error: " + e.getMessage());
    logger.error("Failed", e);
}
```

**Exception Type Checking:**

```java
try {
    // code
} catch (IOException | SQLException e) {
    if (e instanceof IOException) {
        System.out.println("File operation failed");
    } else if (e instanceof SQLException) {
        System.out.println("Database operation failed");
    }
}
```

**Single Exception Variable:**

```java
catch (IOException | SQLException e) {
    // e variable is effectively final
    // e can be either IOException or SQLException
    System.out.println(e.getMessage());
    e.printStackTrace();
    
    // Cannot reassign e
    // e = new Exception();  // ❌ Compile error
}
```

**When to Use Multi-catch:**

```java
// ✅ Use when: Same handling for different exceptions
try {
    processData();
} catch (FileNotFoundException | IOException e) {
    logger.error("File operation failed: " + e.getMessage());
}

// ❌ Don't use when: Different handling needed
try {
    processData();
} catch (FileNotFoundException e) {
    // Different recovery
    useDefaultFile();
} catch (IOException e) {
    // Different recovery
    retryOperation();
}
```

**⚠️ Keywords for Follow-up Questions:**
- **Exception type hierarchy** - Why does order of parents/children matter?
- **Multi-catch syntax** - Valid combinations?
- **Exception matching** - How does instanceof check work?
- **Multiple separate catch blocks** - Difference from multi-catch?

------------------------------------------------

22. What is Stack Trace?

**Definition:**
A stack trace is a list of method calls (call stack) showing the sequence of methods that were executed before an exception occurred. It helps trace where the error originated.

**What It Shows:**
- Which method threw the exception
- All method calls that led to that point
- File name and line number of each call
- Order of method invocations (top = most recent)

**Simple Example:**

```java
public class StackTraceExample {
    public static void main(String[] args) {
        methodA();
    }
    
    public static void methodA() {
        methodB();
    }
    
    public static void methodB() {
        methodC();
    }
    
    public static void methodC() {
        int x = 10 / 0;  // Exception here
    }
}

Output:
Exception in thread "main" java.lang.ArithmeticException: / by zero
    at StackTraceExample.methodC(StackTraceExample.java:20)
    at StackTraceExample.methodB(StackTraceExample.java:16)
    at StackTraceExample.methodA(StackTraceExample.java:12)
    at StackTraceExample.main(StackTraceExample.java:8)
```

**Reading Stack Trace:**

```
Exception in thread "main" java.lang.ArithmeticException: / by zero
^exception in main thread          ^exception type            ^error message

    at StackTraceExample.methodC(StackTraceExample.java:20)
    ^method where error occurred        ^file name        ^line number

    at StackTraceExample.methodB(StackTraceExample.java:16)
    at StackTraceExample.methodA(StackTraceExample.java:12)
    at StackTraceExample.main(StackTraceExample.java:8)
    
Reading order (bottom to top):
1. Execution started in main() line 8
2. Called methodA() at line 12
3. Which called methodB() at line 16
4. Which called methodC() at line 20
5. Error occurred at methodC() line 20
```

**Getting Stack Trace Programmatically:**

```java
try {
    int result = 10 / 0;
} catch (ArithmeticException e) {
    // Method 1: Print to console
    e.printStackTrace();
    
    // Method 2: Get stack trace array
    StackTraceElement[] stackTrace = e.getStackTrace();
    for (StackTraceElement element : stackTrace) {
        System.out.println(element.getClassName() + "." + 
                         element.getMethodName() + "(" + 
                         element.getFileName() + ":" + 
                         element.getLineNumber() + ")");
    }
    
    // Method 3: Get as string
    String message = e.getMessage();
    System.out.println(message);
}
```

**Exception Logging:**

```java
import java.util.logging.Logger;

public class ExceptionLogging {
    private static final Logger logger = Logger.getLogger(
        ExceptionLogging.class.getName());
    
    public void processData() {
        try {
            readFile();
        } catch (IOException e) {
            // Log with stack trace
            logger.log(Level.SEVERE, "File reading failed", e);
            // Output includes full stack trace
        }
    }
}
```

**Custom Stack Trace:**

```java
public class CustomStackTrace {
    public static void printCustomStackTrace(Exception e) {
        System.out.println("Exception: " + e.getClass().getName());
        System.out.println("Message: " + e.getMessage());
        System.out.println("\nStack Trace:");
        
        for (StackTraceElement element : e.getStackTrace()) {
            System.out.printf("  %s.%s() at %s:%d%n",
                element.getClassName(),
                element.getMethodName(),
                element.getFileName(),
                element.getLineNumber());
        }
    }
}
```

**Real-World Stack Trace Example:**

```
Exception in thread "main" java.sql.SQLException: Column 'user_id' not found
    at com.mysql.jdbc.SQLError.createSQLException(SQLError.java:1073)
    at com.mysql.jdbc.SQLError.createSQLException(SQLError.java:987)
    at com.mysql.jdbc.SQLError.createSQLException(SQLError.java:973)
    at com.mysql.jdbc.MysqlIO.checkErrorPacket(MysqlIO.java:3593)
    at com.mysql.jdbc.MysqlIO.checkErrorPacket(MysqlIO.java:3525)
    at com.mysql.jdbc.MysqlIO.sendCommand(MysqlIO.java:1959)
    at com.app.UserRepository.getUser(UserRepository.java:45)
    at com.app.UserService.findUserById(UserService.java:23)
    at com.app.UserController.getUserDetails(UserController.java:15)
    at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
    at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:57)
    at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
    at java.lang.reflect.Method.invoke(Method.java:498)
    at com.sun.jersey.spi.container.JavaMethodInvoker.invoke(JavaMethodInvoker.java:75)

Reading this trace:
- Error: SQLException - Column 'user_id' not found
- Root cause: MySQL JDBC (getUser at UserRepository.java:45)
- Then called by UserService at line 23
- Then called by UserController at line 15
- Then invoked through reflection
```

**⚠️ Keywords for Follow-up Questions:**
- **printStackTrace()** - How to print stack trace?
- **getStackTrace()** - How to access programmatically?
- **Caused by** - Nested exception stack traces?
- **Suppressed** - Java 7+ feature?
- **Line numbers** - How does Java track them?

------------------------------------------------

23. What is the root class of all exceptions?

**Answer:** `Throwable`

**Throwable Hierarchy:**

```
Throwable (java.lang.Throwable)
├── Error
│   ├── OutOfMemoryError
│   ├── StackOverflowError
│   ├── VirtualMachineError
│   └── InternalError
│
└── Exception
    ├── Checked Exceptions
    │   ├── IOException
    │   ├── SQLException
    │   ├── ClassNotFoundException
    │   └── ... (others)
    │
    └── RuntimeException (Unchecked)
        ├── NullPointerException
        ├── ArithmeticException
        ├── ArrayIndexOutOfBoundsException
        └── ... (others)
```

**Why Throwable?**
- All throwable objects in Java inherit from Throwable
- Both Error and Exception extend Throwable
- You cannot throw anything except Throwable subclasses

**Key Characteristics of Throwable:**

```java
public class Throwable implements Serializable {
    // Methods
    public String getMessage();           // Error message
    public void printStackTrace();        // Print stack trace
    public StackTraceElement[] getStackTrace();  // Get call stack
    public synchronized Throwable getCause();    // Root cause
    public synchronized Throwable initCause(Throwable cause);
    public Throwable fillInStackTrace(); // Record stack trace
    public String toString();            // String representation
}
```

**Cannot Throw Non-Throwable:**

```java
// ❌ INVALID - Compiler error
throw new String("Error");  // String doesn't extend Throwable

// ✅ VALID - Extends Throwable
throw new Exception("Error");

// ✅ VALID - Extends RuntimeException (which extends Exception)
throw new RuntimeException("Error");

// ✅ VALID - Custom exception extending Exception
throw new MyException("Error");
```

**Example: Using Throwable Methods**

```java
try {
    int x = 10 / 0;
} catch (Throwable t) {
    // All Throwable methods available
    System.out.println("Type: " + t.getClass().getName());
    System.out.println("Message: " + t.getMessage());
    System.out.println("Cause: " + t.getCause());
    
    StackTraceElement[] trace = t.getStackTrace();
    for (StackTraceElement e : trace) {
        System.out.println("  at " + e);
    }
    
    // Print formatted stack trace
    t.printStackTrace();
}
```

**Why Not Catch Throwable?**

```java
// ❌ BAD: Catching Throwable
try {
    // code
} catch (Throwable t) {  // Catches Error too
    // Includes OutOfMemoryError, StackOverflowError, etc.
    // Dangerous - system errors shouldn't be caught
}

// ✅ GOOD: Catch Exception or specific type
try {
    // code
} catch (Exception e) {  // Only application exceptions
    // Errors are not caught
}
```

**Throwable vs Exception vs Error:**

```java
// Throwable: Everything
Throwable t = new OutOfMemoryError();
Throwable t = new Exception();
Throwable t = new RuntimeException();

// Exception: Application errors only
Exception e = new IOException();      // ✅
Exception e = new OutOfMemoryError(); // ❌ Error, not Exception

// Error: System-level 
Error err = new OutOfMemoryError();   // ✅
Error err = new IOException();        // ❌ Exception, not Error
```

**⚠️ Keywords for Follow-up Questions:**
- **Error vs Exception** - When should we catch each?
- **Custom exceptions** - How to extend Throwable hierarchy?
- **getMessage()** - How to get error information?
- **printStackTrace()** - What does it show?
- **getCause()** - Exception chaining?

------------------------------------------------

24. What is the difference between RuntimeException and Checked Exception?

**Key Differences:**

| Feature | RuntimeException | Checked Exception |
|---------|---|---|
| **Parent Class** | Extends Exception (but via RuntimeException) | Extends Exception directly |
| **Compiler Check** | NO - Optional to handle | YES - Mandatory to handle |
| **Declaration** | No throws needed | Throws declaration required |
| **Handling** | Optional but recommended | Required (compile-time enforced) |
| **When Detected** | Runtime only | Compile-time and Runtime |
| **Represents** | Programming errors | Recoverable conditions |
| **Prevention** | Defensive programming (null checks, etc) | Exception handling (try-catch) |

**Detailed Explanation:**

**RuntimeException (Unchecked):**
```java
// Extends RuntimeException (which extends Exception)
public class ArithmeticException extends RuntimeException { }

// Usage: No handling required
public void divide(int a, int b) {  // No throws declaration
    int result = a / b;  // May throw - but compiler doesn't complain
}

// Call: No try-catch required
divide(10, 0);  // Compiles fine, exception at runtime

// Can handle optionally
try {
    divide(10, 0);
} catch (ArithmeticException e) {  // Optional
    System.out.println("Error");
}
```

**Checked Exception:**
```java
// Extends Exception (not RuntimeException)
class FileNotFoundException extends IOException { }

// Usage: Must handle or declare
public void readFile(String filename) throws IOException {  // Must declare
    FileReader reader = new FileReader(filename);  // Throws checked
}

// Call: Must handle
try {
    readFile("file.txt");  // Must wrap in try-catch
} catch (IOException e) {  // Mandatory
    System.out.println("Error: " + e.getMessage());
}
```

**Common RuntimeExceptions:**
```java
NullPointerException          // Accessing null object
ArithmeticException           // Division by zero
ArrayIndexOutOfBoundsException // Invalid array index
ClassCastException            // Invalid type casting
IllegalArgumentException      // Invalid method argument
NumberFormatException         // Invalid string to number
IndexOutOfBoundsException     // Collection bounds
ConcurrentModificationException // Concurrent modification
UnsupportedOperationException // Unsupported operation
```

**Common Checked Exceptions:**
```java
IOException              // Input/Output operation failed
SQLException           // Database operation failed
ClassNotFoundException // Class not found
FileNotFoundException  // File not found
ParseException        // Parsing failed
InterruptedException  // Thread interrupted
NoSuchMethodException // Method not found
MalformedURLException // Invalid URL
```

**When to Use Each:**

**RuntimeException: Programming Errors (Preventable)**
```java
// ❌ Bad: Throwing checked exception for programming error
public void setValue(int val) throws IllegalArgumentException {
    if (val < 0) {
        throw new IllegalArgumentException("Value < 0");
    }
}

// ✅ Good: Throw RuntimeException for programming error
public void setValue(int val) {
    if (val < 0) {
        throw new IllegalArgumentException("Value < 0");
    }
}

// Can even prevent with check:
public void setValue(int val) {  // No exception needed
    if (val < 0) {
        val = 0;  // Use default
    }
}
```

**Checked Exception: Unpreventable Conditions**
```java
// Unpreventable - file may be deleted between check and use
public void readFile(String filename) throws IOException {
    // Even if (new File(filename).exists()) is true,
    // file can be deleted before FileReader opens it
    FileReader reader = new FileReader(filename);
}

// Must declare - caller must handle/propagate
```

**Design Philosophy:**

```
RuntimeException (Unchecked):
- Errors that could be prevented by code
- Examples: null checks, bounds checks, type checks
- Philosophy: Fix the bug, don't just handle it
- Compiler: "You should have checked first"

Checked Exception:
- Conditions beyond program control
- Examples: file deletion, network failure
- Philosophy: Anticipated problem needing recovery
- Compiler: "This WILL happen, you must handle it"
```

**Real-World Examples:**

```java
public class DataProcessor {
    
    // Checked exception - file I/O is risky
    public void processFile(String filename) throws IOException {
        try {
            FileReader reader = new FileReader(filename);
            // process
        } catch (FileNotFoundException e) {  // Handle or propagate
            logger.error("File not found", e);
            throw e;
        }
    }
    
    // RuntimeException - programming error
    public void processData(List<String> data) {
        for (String item : data) {  // No throws needed
            if (item == null) {  // Check before using
                throw new IllegalArgumentException("Null item");
            }
            processItem(item);
        }
    }
}
```

**Creating Custom Exceptions:**

```java
// Checked - for recoverable conditions
public class PaymentException extends Exception {
    public PaymentException(String message) {
        super(message);
    }
}

// Unchecked - for programming errors
public class InvalidProductException extends RuntimeException {
    public InvalidProductException(String message) {
        super(message);
    }
}

// Usage
public void processPayment(double amount) throws PaymentException {
    if (amount < 0) {
        throw new InvalidProductException("Amount cannot be negative");
    }
    // Attempt payment (may fail)
}
```

**⚠️ Keywords for Follow-up Questions:**
- **throws declaration** - When required for checked?
- **try-catch handling** - Required for unchecked?
- **Exception hierarchy** - When to extend each?
- **Custom exceptions** - Checked vs Unchecked?
- **Best practices** - Which to use when?

------------------------------------------------

25. What is the best practice for exception handling?

**Best Practices Summary:**

## 1. Catch Specific Exceptions (Not Generic)

```java
// ❌ BAD: Catching too generic
try {
    riskyOperation();
} catch (Exception e) {  // Catches everything
    System.out.println("Error");
}

// ✅ GOOD: Catch specific exceptions
try {
    fileOperation();
} catch (FileNotFoundException e) {
    System.out.println("File not found");
} catch (IOException e) {
    System.out.println("IO error");
}
```

**Why?**
- Allows different handling for different errors
- Makes debugging easier
- Prevents masking unexpected exceptions
- More maintainable code

## 2. Avoid Catching Exception or Throwable

```java
// ❌ VERY BAD
try {
    // code
} catch (Exception e) {  // Catches checked AND unchecked
    // Dangerous - hides all errors
}

// ❌ TERRIBLE
try {
    // code
} catch (Throwable t) {  // Catches Error too!
    // Can't even handle this properly
}

// ✅ GOOD
try {
    // code
} catch (IOException e) {
    // Handle specific error type
}
```

## 3. Log Exceptions Properly

```java
// ❌ BAD: No logging
try {
    operation();
} catch (Exception e) {
    // Silent fail - impossible to debug
}

// ✅ GOOD: Log with stack trace
try {
    operation();
} catch (IOException e) {
    logger.error("Operation failed", e);  // Includes stack trace
}

// ✅ ALSO GOOD: Multiple levels
try {
    operation();
} catch (IOException e) {
    logger.warn("Retry needed", e);
} catch (SQLException e) {
    logger.error("Database error", e);
}
```

## 4. Never Silently Ignore Exceptions

```java
// ❌ BAD: Empty catch block
try {
    closeResource();
} catch (IOException e) {
    // It's gone forever, no way to know
}

// ✅ GOOD: At least log it
try {
    closeResource();
} catch (IOException e) {
    logger.warn("Failed to close resource: " + e.getMessage());
}

// ✅ BETTER: Handle appropriately
try {
    closeResource();
} catch (IOException e) {
    logger.error("Cleanup failed", e);
    // Maybe alert admin?
}
```

## 5. Use try-with-resources (Java 7+)

```java
// ❌ OLD: Manual cleanup with finally
BufferedReader reader = null;
try {
    reader = new BufferedReader(new FileReader("file.txt"));
    String line = reader.readLine();
} catch (IOException e) {
    logger.error("Error reading file", e);
} finally {
    if (reader != null) {
        try {
            reader.close();  // Nested try-catch
        } catch (IOException e) {
            logger.error("Error closing", e);
        }
    }
}

// ✅ NEW: Auto resource management
try (BufferedReader reader = new BufferedReader(
         new FileReader("file.txt"))) {
    String line = reader.readLine();
} catch (IOException e) {
    logger.error("Error reading file", e);
}
// Resource auto-closed
```

## 6. Provide Meaningful Error Messages

```java
// ❌ Bad: Vague message
throw new Exception("Error");

// ✅ Good: Descriptive message
throw new IOException("Failed to read configuration from: " + 
                     filename + " at line " + lineNumber);

// ✅ ALSO GOOD: Include context
throw new IllegalArgumentException("User ID must be positive, got: " + userId);
```

## 7. Use Custom Exceptions

```java
// ❌ BAD: Using generic exceptions
public void validateAge(int age) {
    if (age < 18) {
        throw new IllegalArgumentException("Age too low");
    }
}

// ✅ GOOD: Custom exception makes code clearer
public void validateAge(int age) throws InvalidAgeException {
    if (age < 18) {
        throw new InvalidAgeException("User must be 18 or older");
    }
}

// Custom exception class
public class InvalidAgeException extends Exception {
    public InvalidAgeException(String message) {
        super(message);
    }
}
```

## 8. Preserve Exception Chain

```java
// ❌ BAD: Losing original exception
try {
    databaseOperation();
} catch (SQLException e) {
    throw new ApplicationException("DB failed");  // Lost original
}

// ✅ GOOD: Preserve cause
try {
    databaseOperation();
} catch (SQLException e) {
    throw new ApplicationException("DB failed: " + e.getMessage(), e);
}

// ✅ ALSO GOOD: Use cause constructor
try {
    databaseOperation();
} catch (SQLException e) {
    throw new ApplicationException("DB failed", e);  // e is cause
}
```

## 9. Clean Up Resources

```java
// ❌ BAD: Resource leak if exception occurs
FileReader reader = new FileReader("file.txt");
reader.read();
reader.close();

// ❌ ALSO BAD: Not catching close exception
FileReader reader = null;
try {
    reader = new FileReader("file.txt");
    reader.read();
} catch (IOException e) {
    logger.error("Error", e);
} finally {
    try {
        if (reader != null) reader.close();
    } catch (IOException e) {
        // Ignored!
    }
}

// ✅ BEST: Try-with-resources
try (FileReader reader = new FileReader("file.txt")) {
    reader.read();
} catch (IOException e) {
    logger.error("Error reading", e);
}
```

## 10. Don't Catch Exceptions for Control Flow

```java
// ❌ BAD: Using exception for normal flow
int index;
try {
    while (true) {
        array[index++].process();
    }
} catch (ArrayIndexOutOfBoundsException e) {
    // Stop iteration
}

// ✅ GOOD: Normal control flow
for (int i = 0; i < array.length; i++) {
    array[i].process();
}
```

## Complete Example: Following Best Practices

```java
public class DataProcessor {
    private static final Logger logger = 
        Logger.getLogger(DataProcessor.class.getName());
    
    // Custom exception
    public static class DataProcessingException extends Exception {
        public DataProcessingException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    
    // Method following best practices
    public void processFile(String filename) throws DataProcessingException {
        try (BufferedReader reader = new BufferedReader(
                 new FileReader(filename))) {
            // Specific resource handling
            String line;
            int lineNumber = 0;
            
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                try {
                    processLine(line, lineNumber);
                } catch (InvalidDataException e) {
                    // Specific error type
                    logger.warn("Invalid data at line " + lineNumber, e);
                    // Continue processing other lines
                }
            }
            
        } catch (FileNotFoundException e) {
            // Specific handling for file not found
            logger.error("Configuration file not found: " + filename);
            throw new DataProcessingException(
                "File not found: " + filename, e);
                
        } catch (IOException e) {
            // Specific handling for IO errors
            logger.error("Error reading file: " + filename, e);
            throw new DataProcessingException(
                "Error reading file: " + filename, e);
        }
    }
    
    private void processLine(String line, int lineNumber) 
            throws InvalidDataException {
        if (line == null || line.trim().isEmpty()) {
            throw new InvalidDataException(
                "Empty line at " + lineNumber);
        }
        // Process line
    }
}
```

## Summary Checklist

- ✅ Catch specific exception types
- ✅ Avoid catching Exception or Throwable
- ✅ Always log exceptions with stack trace
- ✅ Never have empty catch blocks
- ✅ Use try-with-resources for resources
- ✅ Provide meaningful error messages
- ✅ Create custom exceptions for business logic
- ✅ Preserve exception chains
- ✅ Ensure proper resource cleanup
- ✅ Don't use exceptions for control flow

**⚠️ Keywords for Follow-up Questions:**
- **Custom exceptions** - When to create them?
- **Exception logging** - Best frameworks?
- **Try-with-resources** - How does it work?
- **Exception chaining** - How to preserve cause?
- **Resource cleanup** - Guaranteed approaches?
- **Error messages** - What to include?
