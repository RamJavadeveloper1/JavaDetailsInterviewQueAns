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

throw
Used to explicitly throw an exception.

throws
Used in method signature to declare exception.

------------------------------------------------

7. What is try block?

The try block contains code that may throw an exception.

------------------------------------------------

8. What is catch block?

The catch block handles the exception thrown in the try block.

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
