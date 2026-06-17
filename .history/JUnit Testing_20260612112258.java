## **5. JUNIT & INTEGRATION TESTING**

### **JUnit 5**
What is JUnit? 
1. JUnit 5 (Test Framework)
✅ What it is
JUnit is the core testing framework used to write and run tests.

✅ When to use
Writing unit tests
Testing pure Java logic
No Spring context needed
✅ Common Annotations
@Test → marks test method
@BeforeEach → runs before each test
@AfterEach → runs after each test
@DisplayName → custom test name
✅ Example
import org.junit.jupiter.api.*;

class CalculatorTest {

    @BeforeEach
    void setup() {
        System.out.println("Before each test");
    }

    @Test
    void testAddition() {
        int result = 2 + 3;
        Assertions.assertEquals(5, result);
    }

    @AfterEach
    void tearDown() {
        System.out.println("After each test");
    }
}

👉 Use case:
Testing simple logic like calculations, utility methods.

🔹 2. Mockito (Mocking Framework)
✅ What it is

Mockito is used to mock dependencies (fake objects).

✅ When to use
When your class depends on another class (like Repository)
You want to isolate logic
Avoid database/API calls

✅ Common Annotations
@Mock → creates mock object
@InjectMocks → inject mocks into class
@ExtendWith(MockitoExtension.class) → enable Mockito in JUnit 5

✅ Example
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    void testGetUser() {
        when(userRepository.findById(1)).thenReturn("John");

        String result = userService.getUser(1);

        assertEquals("John", result);
    }
}

👉 Use case:
Service layer testing without hitting DB.

🔹 3. Spring Boot Test (Integration Testing)
✅ What it is
Spring Boot Test loads Spring context and tests real beans.

✅ When to use
Testing full application flow
Controller → Service → Repository
Testing dependency injection
API testing

✅ Common Annotations
@SpringBootTest → loads full context
@WebMvcTest → test only controllers
@MockBean → mock Spring beans
@Autowired → inject real beans

✅ Example
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceSpringTest {

    @Autowired
    UserService userService;

    @MockBean
    UserRepository userRepository;

    @Test
    void testGetUser() {
        when(userRepository.findById(1)).thenReturn("John");

        String result = userService.getUser(1);

        assertEquals("John", result);
    }
}

👉 Use case:
Testing real Spring behavior (DI, configuration, beans)

🔥 Key Differences (Interview Answer)
Feature	    JUnit 5	     Mockito	        Spring Boot Test
Purpose	   Run tests	 Mock dependencies	 Test Spring context
Scope	   Unit testing	 Unit testing(with mocks)Integration
testing Spring needed?	
            ❌ No	       ❌ No	             ✅ Yes
Speed	      ⚡ Fast	 ⚡ Fast	           🐢 Slower


2. # What is JUnit? JUnit 4 vs JUnit 5?

JUnit 5 is a modern, modular testing framework with better extension support, improved annotations, and advanced features like nested and dynamic tests, whereas JUnit 4 is older and more limited.

JUnit 4
Single module
Less flexible

JUnit 5
Modular:
JUnit Platform
JUnit Jupiter (new API)
JUnit Vintage (runs JUnit 4 tests)

different 
jUnit4
@RunWith(MockitoJUnitRunner.class)

junit5
@ExtendWith(MockitoExtension.class)


| JUnit 4        | JUnit 5       |
| -------------- | ------------- |
| `@Test`        | `@Test`       |
| `@Before`      | `@BeforeEach` |
| `@After`       | `@AfterEach`  |
| `@BeforeClass` | `@BeforeAll`  |
| `@AfterClass`  | `@AfterAll`   |
| `@Ignore`      | `@Disabled`   |

3.@Test, @BeforeEach, @AfterEach annotations

@Test
✅ What it does

Marks a method as a test case.

👉 JUnit will execute this method as a test.

✅ Use case
To verify business logic
To check expected vs actual output

@BeforeEach
✅ What it does

Runs before every test method.

👉 Used for setup (initialization)

@BeforeEach
void setup() {
    System.out.println("Setup before each test");
}


✅ Use case
Initialize objects
Set common test data
Open resources (DB connection, mocks)


@AfterEach
✅ What it does

Runs after every test method.

👉 Used for cleanup

✅ Example
@AfterEach
void tearDown() {
    System.out.println("Cleanup after each test");
    }
    
✅ Use case    
Close resources
Reset variables
Clean test data

import org.junit.jupiter.api.*;

class ExampleTest {

    @BeforeEach
    void setup() {
        System.out.println("Before each test");
    }

    @Test
    void testOne() {
        System.out.println("Test 1 running");
    }

    @Test
    void testTwo() {
        System.out.println("Test 2 running");
    }

    @AfterEach
    void tearDown() {
        System.out.println("After each test");
    }
}

flow
@BeforeEach → @Test → @AfterEach

@Test → defines test
@BeforeEach → setup before every test
@AfterEach → cleanup after every test


3. # @BeforeAll vs @AfterAll

@BeforeAll runs once before all tests for setup, and @AfterAll runs once after all tests for cleanup, unlike @BeforeEach and @AfterEach which run for every test.


import org.junit.jupiter.api.*;

class LifecycleTest {

    @BeforeAll
    static void init() {
        System.out.println("Before all tests");
    }

    @Test
    void test1() {
        System.out.println("Test 1");
    }

    @Test
    void test2() {
        System.out.println("Test 2");
    }

    @AfterAll
    static void cleanup() {
        System.out.println("After all tests");
    }
}

4. # @DisplayName, @Disabled annotations

@DisplayName → improves readability of test names
@Disabled → skips test execution with reason

import org.junit.jupiter.api.*;

class ExampleTest {

    @Test
    @DisplayName("Valid login test")
    void testLogin() {
        System.out.println("Login successful");
    }

    @Test
    @Disabled("Pending bug fix")
    @DisplayName("Invalid login test")
    void testInvalidLogin() {
        System.out.println("This won't run");
    }
}

5. # What is assertion? Types of assertions
An assertion is a statement used to verify that actual output matches expected output.

👉 If the assertion fails → test fails
👉 If it passes → test passes

import static org.junit.jupiter.api.Assertions.*;
@Test
void testAddition() {
    int result = 2 + 3;
    assertEquals(5, result); // assertion
}

1. assertNotEquals(5, 2 + 2);
2. assertNull(null);  Use case: Object validation
3. assertNotNull(new Object());
4. assertTrue(5 > 3);👉 Use case: Boolean conditions
5. assertFalse(5 < 3);
6. assertThrows()  Use case: Exception testing

   assertThrows(IllegalArgumentException.class, () -> {
    throw new IllegalArgumentException();
});

   Runs multiple assertions together
7. assertAll(
    () -> assertEquals(5, 2 + 3),
    () -> assertTrue(10 > 5)
); Even if one fails, all are executed

8. assertTimeout() Use case: Performance testing
9. assertSame()
String a = "test";
String b = a;

assertSame(a, b);
Use case: Reference comparison (same object)

| Assertion      | Purpose           |
| -------------- | ----------------- |
| `assertEquals` | Value comparison  |
| `assertTrue`   | Boolean check     |
| `assertNull`   | Null check        |
| `assertThrows` | Exception testing |
| `assertAll`    | Group assertions  |
| `assertSame`   | Reference check   |


Assertions are used to validate expected vs actual results in a test; if the assertion fails, the test fails.

5. # @ParameterizedTest

@ParameterizedTest allows you to run the same test multiple times with different inputs.

👉 Instead of writing multiple test methods, you write one test + multiple data sets
Why use it?
Avoid duplicate test code
Improve readability
Test multiple scenarios easily

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

class ParameterizedTestExample {

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4})
    void testNumbers(int number) {
        assertTrue(number > 0);
    }
}

1. @ValueSource 
2. @CsvSource multipal input 
    @ParameterizedTest
    @CsvSource({
        "2, 3, 5",
        "4, 5, 9"
    })
    void testAddition(int a, int b, int expected) {
    assertEquals(expected, a + b);
}
3.@NullSource, @EmptySource
   @ParameterizedTest
   @NullSource
   @EmptySource
   void testNullOrEmpty(String input) {
       assertTrue(input == null || input.isEmpty());
   }
4.@MethodSource
👉 Custom data provider
import java.util.stream.Stream;
@ParameterizedTest
   @MethodSource("provideNumbers")
   void testWithMethodSource(int num) {
       assertTrue(num > 0);
   }
   
   static Stream<Integer> provideNumbers() {
       return Stream.of(1, 2, 3);
   }

@ParameterizedTest allows running the same test multiple times with different inputs, improving test coverage and reducing duplicate code.

### 6. @RepeatedTest
@RepeatedTest allows you to run the same test multiple times (fixed number of times).

👉 Unlike parameterized tests → no different inputs
👉 It just repeats the same test

import org.junit.jupiter.api.RepeatedTest;
import static org.junit.jupiter.api.Assertions.*;

class RepeatedTestExample {

    @RepeatedTest(3)
    void testRepeat() {
        System.out.println("Running test");
        assertTrue(5 > 3);
    }
}


@RepeatedTest(value = 3, name = "Run {currentRepetition} of {totalRepetitions}")
void testRepeat() {
    System.out.println("Repeated test");
}

import org.junit.jupiter.api.RepetitionInfo;

@RepeatedTest(3)
void testWithInfo(RepetitionInfo info) {
    System.out.println("Current: " + info.getCurrentRepetition());
    System.out.println("Total: " + info.getTotalRepetitions());
}

🔥 When to Use
Testing unstable/flaky code
Checking retry logic
Verifying idempotent operations
Performance consistency checks

🔥 Difference from @ParameterizedTest

| Feature      | `@RepeatedTest` | `@ParameterizedTest` |
| ------------ | --------------- | -------------------- |
| Input values | Same input      | Different inputs     |
| Purpose      | Repeat test     | Test multiple data   |
| Example      | Run 5 times     | Run with 5 inputs    |


@RepeatedTest runs the same test multiple times, useful for verifying stability or repeated execution scenarios.    



# 6.How to test exceptions?
👉 We use:

## ✅ `assertThrows()`

---

# 🔥 1. Basic Example

```java id="ex1"
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ExceptionTest {

    @Test
    void testException() {
        assertThrows(IllegalArgumentException.class, () -> {
            throw new IllegalArgumentException("Invalid input");
        });
    }
}
```

👉 Test passes only if exception is thrown

---

# 🔹 2. Real Example (Service Method)

```java id="ex2"
class UserService {

    public int calculate(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Invalid amount");
        }
        return amount * 2;
    }
}
```

### ✅ Test

```java id="ex3"
@Test
void testCalculateException() {
    UserService service = new UserService();

    assertThrows(IllegalArgumentException.class, () -> {
        service.calculate(0);
    });
}
```

---

# 🔹 3. Verify Exception Message (Very Important)

```java id="ex4"
@Test
void testExceptionMessage() {
    UserService service = new UserService();

    Exception ex = assertThrows(IllegalArgumentException.class, () -> {
        service.calculate(0);
    });

    assertEquals("Invalid amount", ex.getMessage());
}
```

---

# 🔹 4. Using Mockito (Mock Exception)

```java id="ex5"
when(userRepository.findById(1))
    .thenThrow(new RuntimeException("DB error"));

assertThrows(RuntimeException.class, () -> {
    userService.getUser(1);
});
```

👉 Use case: Simulate failures (DB/API)

```

❌ Not recommended now
👉 Use JUnit 5 instead

---

# 🔥 Types of Exception Testing

| Type               | How                   |
| ------------------ | --------------------- |
| Exception thrown   | `assertThrows()`      |
| Message validation | `getMessage()`        |
| Mock exception     | Mockito `thenThrow()` |

---

# 🎯 Interview One-Liner

👉

> In JUnit 5, we test exceptions using `assertThrows()`, which verifies that a specific exception is thrown during execution.

---

# ⚠️ Common Mistakes (Interview Trap)

❌ Not wrapping code in lambda
❌ Not checking exception type
❌ Ignoring message validation

---

# 💡 Bonus Tip

👉 You can also use:

```java id="ex7"
assertDoesNotThrow(() -> {
    service.calculate(10);
});
```

---

### **Mockito**
137. What is Mockito? Why use it?
Great—this is a **core interview question** 👇

---

# 🔹 What is Mockito?

**Mockito** is a **mocking framework for Java** used in unit testing.

👉 It allows you to create **fake (mock) objects** for dependencies.

---

## ✅ Simple Definition (Interview)

> Mockito is used to mock dependencies so that we can test a class in isolation.

---

# 🔹 Why Do We Use Mockito?

In real applications, classes depend on other classes.

👉 Example:

```java
UserService → UserRepository → Database
```

### ❌ Problem

* Calling real DB → slow
* External systems → unreliable
* Hard to control data

---

### ✅ Solution → Mockito

👉 Replace real dependency with **mock (fake object)**

---

# 🔥 Example Without Mockito

```java id="m1"
UserService service = new UserService(new UserRepository());
service.getUser(1); // calls real DB ❌
```

---

# 🔥 Example With Mockito

```java id="m2"
@Mock
UserRepository userRepository;

@InjectMocks
UserService userService;

@Test
void testGetUser() {
    when(userRepository.findById(1)).thenReturn("John");

    String result = userService.getUser(1);

    assertEquals("John", result);
}
```

👉 No DB call ✔
👉 Fully controlled test ✔

---

# 🔹 What Mockito Helps With

### ✅ 1. Isolation

Test only the class logic (not dependencies)

---

### ✅ 2. Control Behavior

```java id="m3"
when(repo.findById(1)).thenReturn("John");
```

---

### ✅ 3. Simulate Failures

```java id="m4"
when(repo.findById(1)).thenThrow(new RuntimeException());
```

---

### ✅ 4. Verify Interactions

```java id="m5"
verify(userRepository).findById(1);
```

---

# 🔥 Key Mockito Annotations

| Annotation                            | Purpose                 |
| ------------------------------------- | ----------------------- |
| `@Mock`                               | Create mock object      |
| `@InjectMocks`                        | Inject mocks into class |
| `@Spy`                                | Partial mocking         |
| `@ExtendWith(MockitoExtension.class)` | Enable Mockito          |

---

# 138 🔹 When to Use Mockito
* Testing service layer
* Dependency exists (DB, API, etc.)
* You want fast unit tests

---

### ❌ Do NOT use Mockito when:

* Testing simple logic
* Doing full integration testing (use SpringBootTest)

---

# 🎯 Interview One-Liner

👉

> Mockito is a mocking framework used to create fake dependencies, allowing us to test a class in isolation without relying on external systems like databases or APIs.

---

# 💡 Real Interview Tip

👉 If interviewer asks:
**“Why not use real objects?”**

Answer:

> Because real dependencies make tests slow, unreliable, and harder to control. Mockito ensures fast and isolated unit tests.

---

If you want next, I can explain **@Mock vs @InjectMocks vs @Spy (very important tricky question)** 👍

138. @Mock vs @InjectMocks

@Mock → creates fake dependency  
@InjectMocks → creates real class + injects mock


Perfect—this is a **very common and slightly tricky interview question** 👇

---

# 🔹 `@Mock` vs `@InjectMocks` (Mockito)

---

## 🔹 `@Mock`

## ✅ What it does

Creates a **fake (mock) object** of a class.

👉 It does NOT contain real logic
👉 You control its behavior using `when()`

---

## ✅ Example

```java id="mm1"
@Mock
UserRepository userRepository;
```

👉 This creates a **dummy repository**

---

## 🔹 `@InjectMocks`

## ✅ What it does

Creates the **actual object (real class)** and **injects mocks into it**.

👉 Dependencies are replaced with mocks automatically

---

## ✅ Example

```java id="mm2"
@InjectMocks
UserService userService;
```

👉 This creates:

```id="k1l9v7"
UserService(userRepository)  // mock injected
```

---

# 🔥 Combined Example (Most Important)

```java id="mm3"
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    void testGetUser() {
        when(userRepository.findById(1)).thenReturn("John");

        String result = userService.getUser(1);

        assertEquals("John", result);
    }
}
```

---

# 🔄 How It Works Internally

```id="flow3"
@Mock → creates fake dependency  
@InjectMocks → creates real class + injects mock
```

---

# 🔥 Key Differences

| Feature              | `@Mock`            | `@InjectMocks`     |
| -------------------- | ------------------ | ------------------ |
| Purpose              | Create fake object | Create real object |
| Logic                | ❌ No real logic    | ✅ Real logic       |
| Dependency injection | ❌ No               | ✅ Yes              |
| Used for             | Dependencies       | Class under test   |

---

# 🎯 Simple Way to Remember

👉

* `@Mock` → **fake dependency**
* `@InjectMocks` → **real class under test**

---

# 🔹 Real Scenario

```java id="2f8zbm"
UserService → UserRepository
```

👉 You write:

```java id="mm4"
@Mock
UserRepository repo;        // fake

@InjectMocks
UserService service;        // real
```

---

# ⚠️ Common Interview Mistakes

❌ Using `@Mock` for class under test
❌ Forgetting `@ExtendWith(MockitoExtension.class)`
❌ Not defining behavior with `when()`

---

# 🎯 Interview One-Liner

👉

> `@Mock` creates fake dependencies, while `@InjectMocks` creates the actual class and injects those mocks into it.

---

# 139. when().thenReturn() vs doReturn().when()

Excellent—this is a **tricky Mockito interview question** 👇

---

# 🔹 `when().thenReturn()` vs `doReturn().when()`

Both are used to **define mock behavior**, but they differ in **how and when the method is executed**.

---

# 🔥 1. `when().thenReturn()`

## ✅ Syntax

```java
when(mock.method()).thenReturn(value);
```

## ✅ Example

```java id="w1"
when(userRepository.findById(1)).thenReturn("John");
```

## ⚙️ Behavior

👉 It **calls the actual method (if not mocked properly)** before stubbing.

---

## ⚠️ Problem

* Can throw **NullPointerException**
* Dangerous with **spies**
* Executes real method unintentionally

---

# 🔥 2. `doReturn().when()`

## ✅ Syntax

```java
doReturn(value).when(mock).method();
```

## ✅ Example

```java id="w2"
doReturn("John").when(userRepository).findById(1);
```

## ⚙️ Behavior

👉 It **does NOT call the real method**

---

# 🔥 Key Difference

| Feature            | `when().thenReturn()` | `doReturn().when()` |
| ------------------ | --------------------- | ------------------- |
| Calls real method? | ⚠️ Yes (sometimes)    | ❌ No                |
| Safe for spies?    | ❌ Risky               | ✅ Safe              |
| Usage              | Normal mocks          | Spies / edge cases  |

---

# 🔹 Example with Spy (Very Important)

```java id="w3"
List<String> list = new ArrayList<>();
List<String> spyList = spy(list);

// ❌ This will call real method → IndexOutOfBoundsException
when(spyList.get(0)).thenReturn("Hello");

// ✅ Safe
doReturn("Hello").when(spyList).get(0);
```

---

# 🔹 When to Use What

### ✅ Use `when().thenReturn()`

* Normal mocks
* Standard cases

---

### ✅ Use `doReturn().when()`

* With **@Spy (partial mocking)**
* To avoid real method execution
* When method has side effects

---

# 🎯 Interview One-Liner

👉

> `when().thenReturn()` may call the real method, whereas `doReturn().when()` avoids calling the real method and is safer for spies.

---

# ⚠️ Common Interview Trap

👉 Question:
**"Why does when() fail with spy?"**

Answer:

> Because it calls the real method first, which may throw exceptions.

---
# 140. verify() method usage

Great—this is a **very important Mockito concept** (interaction testing) 👇

---

# 🔹 What is `verify()` in Mockito?

## ✅ Definition

`verify()` is used to **check whether a method was called on a mock object**.

👉 It verifies **interaction**, not result

---

# 🔥 Why use `verify()`?

* Ensure method was called
* Check number of calls
* Validate behavior (not just output)

---

# 🔹 Basic Example

```java id="v1"
@Mock
UserRepository userRepository;

@InjectMocks
UserService userService;

@Test
void testVerify() {
    when(userRepository.findById(1)).thenReturn("John");

    userService.getUser(1);

    verify(userRepository).findById(1); // ✅ verified
}
```

---

# 🔹 What it checks

```java id="flow4"
userService.getUser(1)
        ↓
userRepository.findById(1)  ✅ verified
```

---

# 🔥 Common `verify()` Variations

---

## ✅ 1. Verify method called once (default)

```java id="v2"
verify(userRepository).findById(1);
```

---

## ✅ 2. Verify number of times

```java id="v3"
verify(userRepository, times(2)).findById(1);
```

---

## ✅ 3. Verify never called

```java id="v4"
verify(userRepository, never()).deleteById(1);
```

---

## ✅ 4. Verify at least / at most

```java id="v5"
verify(userRepository, atLeastOnce()).findById(1);
verify(userRepository, atMost(2)).findById(1);
```

---

## ✅ 5. Verify no interactions

```java id="v6"
verifyNoInteractions(userRepository);
```

---

## ✅ 6. Verify order (advanced)

```java id="v7"
InOrder inOrder = inOrder(userRepository);

inOrder.verify(userRepository).findById(1);
inOrder.verify(userRepository).save("John");
```

---

# 🔥 Real Example

```java id="v8"
class UserService {

    private UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public void processUser(int id) {
        repo.findById(id);
        repo.save("Processed");
    }
}
```

### ✅ Test

```java id="v9"
@Test
void testProcessUser() {
    userService.processUser(1);

    verify(userRepository).findById(1);
    verify(userRepository).save("Processed");
}
```

---

# 🎯 When to Use `verify()`

* When method has **no return value (void)**
* When checking **behavior/interactions**
* Ensuring **dependency was called correctly**

---

# 🎯 Interview One-Liner

👉

> `verify()` is used to confirm that specific methods were called on mock objects with expected arguments and number of times.

---

# ⚠️ Common Mistakes

❌ Using verify without calling method
❌ Verifying wrong arguments
❌ Overusing verify (only use when needed)
---
# 141. ArgumentCaptor usage

Excellent—this is a **high-level Mockito concept** (very good for interviews) 👇

---

# 🔹 What is `ArgumentCaptor`?

## ✅ Definition

`ArgumentCaptor` is used to **capture arguments passed to a mock method** so you can **verify or assert them**.

👉 Instead of just checking *method called*, you check **what value was passed**

---

# 🔥 Why use it?

Sometimes you don’t know the exact argument beforehand, or it’s **created inside the method**.

👉 Example:

```java
repo.save(new User("John"));
```

You can’t directly access that object → use `ArgumentCaptor`

---

# 🔹 Basic Example

```java id="ac1"
import org.mockito.ArgumentCaptor;

@Mock
UserRepository userRepository;

@InjectMocks
UserService userService;

@Test
void testCaptureArgument() {
    userService.saveUser("John");

    ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

    verify(userRepository).save(captor.capture());

    assertEquals("John", captor.getValue());
}
```

---

# 🔹 Real Scenario (Object Capture)

## ✅ Service

```java id="ac2"
class UserService {

    private UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public void createUser(String name) {
        User user = new User(name);
        repo.save(user);
    }
}
```

---

## ✅ Test

```java id="ac3"
@Test
void testCreateUser() {
    userService.createUser("John");

    ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

    verify(userRepository).save(captor.capture());

    User savedUser = captor.getValue();

    assertEquals("John", savedUser.getName());
}
```

---

# 🔥 Multiple Captures

```java id="ac4"
verify(userRepository, times(2)).save(captor.capture());

List<User> users = captor.getAllValues();
```

---

# 🔹 Key Methods

| Method           | Purpose                 |
| ---------------- | ----------------------- |
| `capture()`      | Capture argument        |
| `getValue()`     | Get last captured value |
| `getAllValues()` | Get all captured values |

---

# 🔥 When to Use

* Argument created inside method
* Complex objects
* Need to verify fields inside object
* Debugging interactions

---

# 🔥 Without vs With ArgumentCaptor

### ❌ Without

```java id="ac5"
verify(repo).save(new User("John")); // fails (different object)
```

---

### ✅ With

```java id="ac6"
verify(repo).save(captor.capture());
assertEquals("John", captor.getValue().getName());
```

---

# 🎯 Interview One-Liner

👉

> `ArgumentCaptor` is used to capture and assert the arguments passed to a mock method, especially when the argument is created inside the method under test.

---

# ⚠️ Common Mistakes

❌ Forgetting `captor.capture()` inside verify
❌ Using wrong class type
❌ Using it when simple `verify()` is enough
---
142. @Spy annotation

Great—this is a **tricky and important Mockito concept** 👇

---

# 🔹 What is `@Spy`?

## ✅ Definition

`@Spy` is used to create a **partial mock**.

👉 It uses the **real object**, but allows you to **mock specific methods**

---

## ✅ Simple Explanation (Interview)

> `@Spy` creates a real object where some methods can be mocked while others use actual logic.

---

# 🔥 Difference from `@Mock`

| Feature      | `@Mock`      | `@Spy`          |
| ------------ | ------------ | --------------- |
| Object type  | Fake         | Real            |
| Method calls | All mocked   | Real by default |
| Use case     | Full mocking | Partial mocking |

---

# 🔹 Basic Example

```java id="spy1"
@Spy
List<String> list = new ArrayList<>();

@Test
void testSpy() {
    list.add("Hello"); // real method

    assertEquals(1, list.size());

    doReturn(100).when(list).size(); // override behavior

    assertEquals(100, list.size());
}
```

---

# 🔥 Real Example (Service)

## ✅ Class

```java id="spy2"
class UserService {

    public String getUser() {
        return "Real User";
    }

    public String processUser() {
        return getUser() + " processed";
    }
}
```

---

## ✅ Test

```java id="spy3"
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Spy
    UserService userService;

    @Test
    void testSpy() {
        doReturn("Mock User").when(userService).getUser();

        String result = userService.processUser();

        assertEquals("Mock User processed", result);
    }
}
```

👉 Here:

* `processUser()` → real method
* `getUser()` → mocked

---

# 🔥 Important Rule (Interview Trap)

❌ Don’t use:

```java
when(spy.method()).thenReturn(value);
```

👉 It may call real method → error

---

✅ Use:

```java
doReturn(value).when(spy).method();
```

---

# 🔹 When to Use `@Spy`

* When you want **partial mocking**
* When class has **complex logic**
* When only **some methods need mocking**

---

# 🔹 When NOT to Use

* Prefer `@Mock` for clean unit tests
* Avoid if full mocking is enough

---

# 🎯 Interview One-Liner

👉

> `@Spy` creates a partial mock where real methods are called by default, but specific methods can be stubbed.

---

# ⚠️ Common Mistakes

❌ Using `when()` with spy
❌ Overusing spy instead of mock
❌ Not understanding real method execution
---

### **Spring Boot Testing**
143. @SpringBootTest annotation

Great—this is a **very important Spring Boot testing annotation** 👇

---

# 🔹 What is `@SpringBootTest`?

## ✅ Definition

`@SpringBootTest` is used to **load the full Spring application context** for testing.

👉 It starts your **entire Spring Boot application**

---

## ✅ Simple Interview Definition

> `@SpringBootTest` is used for integration testing by loading the complete Spring context and testing real beans.

---

# 🔥 What it does internally

```text
✔ Loads all beans  
✔ Enables dependency injection  
✔ Reads properties (application.properties)  
✔ Starts embedded server (optional)
```

---

# 🔹 Basic Example

```java id="sb1"
@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void testService() {
        int result = userService.calculateFinalAmount(100);
        assertEquals(90, result);
    }
}
```

👉 Real service bean is used (no mocking)

---

# 🔹 With MockBean (Very Common)

```java id="sb2"
@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    void testService() {
        when(userRepository.findById(1)).thenReturn("John");

        String result = userService.getUser(1);

        assertEquals("John", result);
    }
}
```

👉 Real service + mocked dependency

---

# 🔹 Web Environment (API Testing)

```java id="sb3"
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testApi() {
        String result = restTemplate.getForObject("/users", String.class);
        assertNotNull(result);
    }
}
```

---

# 🔥 When to Use `@SpringBootTest`

### ✅ Use when:

* Testing **full application flow**
* Need **Spring context**
* Testing **integration (Controller + Service + Repo)**

---

### ❌ Avoid when:

* Unit testing (use Mockito instead)
* You want fast tests

---

# 🔥 Key Features

| Feature              | Description            |
| -------------------- | ---------------------- |
| Full context         | Loads entire app       |
| Dependency Injection | Works like real app    |
| Property support     | Reads config           |
| Slower               | Compared to unit tests |

---

# 🔥 Important Variants

```java id="sb4"
@SpringBootTest
@SpringBootTest(webEnvironment = RANDOM_PORT)
@SpringBootTest(classes = MyConfig.class)
```

---

# 🔥 Comparison

| Annotation        | Scope            |
| ----------------- | ---------------- |
| `@SpringBootTest` | Full application |
| `@WebMvcTest`     | Only controller  |
| `@DataJpaTest`    | Only JPA layer   |

---

# 🎯 Interview One-Liner

👉

> `@SpringBootTest` loads the full Spring application context and is used for integration testing of real beans and configurations.

---

# ⚠️ Common Interview Mistakes

❌ Using it for unit tests
❌ Not using `@MockBean` when needed
❌ Ignoring performance impact

---

If you want next, I can explain **@WebMvcTest vs @SpringBootTest (very common comparison question)** 👍


# 144. @WebMvcTest vs @DataJpaTest

    Great—this is a **very popular comparison question in Spring Boot interviews** 👇
(*I’ll correct the typo: it’s `@SpringBootTest`*)

---

# 🔥 `@WebMvcTest` vs `@DataJpaTest` vs `@SpringBootTest`

---

# 🔹 1. `@WebMvcTest`

## ✅ What it does

Loads **only the web layer (controllers)**

👉 Does NOT load:

* Service
* Repository
* Full context

---

## ✅ Example

```java id="wm1"
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void testController() throws Exception {
        when(userService.getUser(1)).thenReturn("John");

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk());
    }
}
```

---

## ✅ Use case

* Test **REST APIs / Controllers**
* Validate request/response

---

# 🔹 2. `@DataJpaTest`

## ✅ What it does

Loads **only JPA components**

👉 Includes:

* Repository
* Entity
* Hibernate
* In-memory DB (H2)

👉 Does NOT include:

* Controller
* Service

---

## ✅ Example

```java id="dj1"
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveUser() {
        User user = new User("John");
        userRepository.save(user);

        assertNotNull(user.getId());
    }
}
```

---

## ✅ Use case

* Test **database layer**
* Validate queries

---

# 🔹 3. `@SpringBootTest`

## ✅ What it does

Loads **full application context**

👉 Includes everything:

* Controller
* Service
* Repository
* Config

---

## ✅ Example

```java id="sb5"
@SpringBootTest
class ApplicationTest {

    @Autowired
    private UserService userService;

    @Test
    void testFullFlow() {
        assertNotNull(userService);
    }
}
```

---

## ✅ Use case

* Integration testing
* End-to-end flow

---

# 🔥 Key Differences (Most Important)

| Feature        | `@WebMvcTest` | `@DataJpaTest` | `@SpringBootTest` |
| -------------- | ------------- | -------------- | ----------------- |
| Scope          | Controller    | Repository     | Full app          |
| Loads Service? | ❌ No          | ❌ No           | ✅ Yes             |
| Loads DB?      | ❌ No          | ✅ Yes          | ✅ Yes             |
| Speed          | ⚡ Fast        | ⚡ Fast         | 🐢 Slow           |
| Use case       | API testing   | DB testing     | Integration       |

---

# 🔄 Visual Understanding

```text id="viz1"
@WebMvcTest     → Controller only  
@DataJpaTest    → Repository only  
@SpringBootTest → Everything
```

---

# 🎯 When to Use What (Interview Gold)

### ✅ Use `@WebMvcTest`

👉 When testing:

* REST endpoints
* Request/response

---

### ✅ Use `@DataJpaTest`

👉 When testing:

* Repository
* DB queries

---

### ✅ Use `@SpringBootTest`

👉 When testing:

* Full flow
* Integration

---

# 🎯 Interview One-Liner

👉

> `@WebMvcTest` tests only the controller layer, `@DataJpaTest` tests the repository layer, and `@SpringBootTest` loads the full application for integration testing.

---

# ⚠️ Common Interview Mistakes

❌ Using `@SpringBootTest` for everything
❌ Forgetting `@MockBean` in `@WebMvcTest`
❌ Expecting service in `@DataJpaTest`
--- 👍

Great—**`MockMvc` is one of the most important Spring testing tools** (especially for controller testing) 👇

---

# 🔹 What is `MockMvc`?

## ✅ Definition

`MockMvc` is used to **test Spring MVC controllers without starting the server**.

👉 It simulates HTTP requests like:

* GET
* POST
* PUT
* DELETE

---

## ✅ Interview One-Liner

> `MockMvc` is used to perform HTTP requests and test controller endpoints without running a real server.

---

# 🔥 Why use `MockMvc`?

* No need to start server
* Fast testing
* Full control over request/response
* Works well with `@WebMvcTest`

---

# 🔹 Basic Setup

```java id="mvc1"
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
}
```

---

# 🔹 Example 1: GET API

```java id="mvc2"
@Test
void testGetUser() throws Exception {

    when(userService.getUser(1)).thenReturn("John");

    mockMvc.perform(get("/users/1"))
            .andExpect(status().isOk())
            .andExpect(content().string("John"));
}
```

---

# 🔹 Example 2: POST API

```java id="mvc3"
@Test
void testCreateUser() throws Exception {

    String json = "{\"name\":\"John\"}";

    mockMvc.perform(post("/users")
            .contentType("application/json")
            .content(json))
            .andExpect(status().isOk());
}
```

---

# 🔹 Common Methods

| Method        | Purpose             |
| ------------- | ------------------- |
| `perform()`   | Execute request     |
| `get()`       | GET request         |
| `post()`      | POST request        |
| `andExpect()` | Validate response   |
| `status()`    | Check HTTP status   |
| `content()`   | Check response body |

---

# 🔥 Response Validations

```java id="mvc4"
.andExpect(status().isOk())
.andExpect(content().string("John"))
.andExpect(jsonPath("$.name").value("John"))
```

---

# 🔹 JSON Validation (Important)

```java id="mvc5"
.andExpect(jsonPath("$.id").value(1))
.andExpect(jsonPath("$.name").value("John"))
```

---

# 🔹 With Request Params

```java id="mvc6"
mockMvc.perform(get("/users")
        .param("id", "1"))
```

---

# 🔹 With Headers

```java id="mvc7"
mockMvc.perform(get("/users")
        .header("Authorization", "Bearer token"))
```

---

# 🔥 When to Use MockMvc

### ✅ Use when:

* Testing controllers
* Validating REST APIs
* No need for full SpringBootTest

---

### ❌ Avoid when:

* Testing full integration → use `@SpringBootTest`

---

# 🔄 Flow

```text id="flowmvc"
MockMvc → Fake HTTP Request → Controller → Response → Assert
```

---

# 🎯 Interview Tips

👉 If asked:
**“Why MockMvc instead of RestTemplate?”**

Answer:

> MockMvc does not start a server and is faster, while RestTemplate requires a running application.

---

# ⚠️ Common Mistakes

❌ Forgetting `.contentType("application/json")`
❌ Not mocking service (`@MockBean`)
❌ Wrong URL mapping

---

# 🎯 Final One-Liner

👉
> MockMvc is used to test Spring MVC controllers by simulating HTTP requests and validating responses without starting the server.
---
# 146. Mock vs MockBean

Great—this is a **very important Spring + Mockito interview question** 👇

---

# 🔹 `@Mock` vs `@MockBean`

---

## 🔹 `@Mock` (Mockito)

## ✅ What it does

Creates a **mock object** using Mockito.

👉 Works **outside Spring context**

---

## ✅ Example

```java id="mb1"
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;
}
```

---

## ✅ Key Points

* Pure **unit testing**
* No Spring involved
* Fast execution
* Used with `@InjectMocks`

---

# 🔹 `@MockBean` (Spring Boot)

## ✅ What it does

Creates a **mock and adds it to Spring Application Context**

👉 Replaces real bean with mock

---

## ✅ Example

```java id="mb2"
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService; // replaces real bean

    @Test
    void testController() throws Exception {
        when(userService.getUser(1)).thenReturn("John");

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk());
    }
}
```

---

## ✅ Key Points

* Used in **Spring tests**
* Replaces actual Spring bean
* Works with:

  * `@SpringBootTest`
  * `@WebMvcTest`

---

# 🔥 Key Differences

| Feature   | `@Mock`                 | `@MockBean`                   |
| --------- | ----------------------- | ----------------------------- |
| Framework | Mockito                 | Spring Boot                   |
| Context   | ❌ No Spring             | ✅ Spring context              |
| Injection | Manual (`@InjectMocks`) | Auto (Spring DI)              |
| Use case  | Unit test               | Integration / controller test |
| Speed     | ⚡ Fast                  | 🐢 Slower                     |

---

# 🔄 Visual Understanding

```text id="viz2"
@Mock      → Fake object (no Spring)  
@MockBean  → Fake bean inside Spring context
```

---

# 🔹 Real Scenario

### 👉 Service Test (Unit Test)

```java id="mb3"
@Mock
UserRepository repo;

@InjectMocks
UserService service;
```

---

### 👉 Controller Test (Spring Test)

```java id="mb4"
@MockBean
UserService service;
```

---

# 🎯 When to Use What

### ✅ Use `@Mock`

* Unit testing
* No Spring context
* Fast tests

---

### ✅ Use `@MockBean`

* Spring Boot tests
* Controller testing
* Replace real bean

---

# 🎯 Interview One-Liner
👉
> `@Mock` creates a mock object for unit testing, while `@MockBean` creates and injects a mock into the Spring application context, replacing the real bean.

---

# ⚠️ Common Interview Mistakes

❌ Using `@Mock` inside `@SpringBootTest`
❌ Forgetting `@MockBean` in controller tests
❌ Mixing both incorrectly
---
# 147. How to test REST controllers?

Great—this is a **must-know practical interview question** 👇

---

# 🔹 How to Test REST Controllers in Spring Boot?

## ✅ Main Approach

We use:

* `@WebMvcTest` → load only controller
* `MockMvc` → simulate HTTP requests
* `@MockBean` → mock service layer

---

# 🔥 Step-by-Step Example

---

# 🔹 1. Controller

```java id="rc1"
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public String getUser(@PathVariable int id) {
        return userService.getUser(id);
    }
}
```

---

# 🔹 2. Test Class

```java id="rc2"
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
}
```

---

# 🔹 3. Test GET API

```java id="rc3"
@Test
void testGetUser() throws Exception {

    when(userService.getUser(1)).thenReturn("John");

    mockMvc.perform(get("/users/1"))
            .andExpect(status().isOk())
            .andExpect(content().string("John"));
}
```

---

# 🔹 4. Test POST API

```java id="rc4"
@Test
void testCreateUser() throws Exception {

    String json = "{\"name\":\"John\"}";

    mockMvc.perform(post("/users")
            .contentType("application/json")
            .content(json))
            .andExpect(status().isOk());
}
```

---

# 🔥 Key Components

| Component     | Purpose              |
| ------------- | -------------------- |
| `@WebMvcTest` | Load controller only |
| `MockMvc`     | Perform HTTP calls   |
| `@MockBean`   | Mock dependencies    |
| `when()`      | Define behavior      |
| `andExpect()` | Validate response    |

---

# 🔄 Flow

```text id="flowrc"
MockMvc → Fake HTTP Request → Controller → Mock Service → Response → Assert
```

---

# 🔹 JSON Validation (Important)

```java id="rc5"
.andExpect(jsonPath("$.name").value("John"))
.andExpect(jsonPath("$.id").value(1))
```

---

# 🔹 Alternative (Full Integration)

👉 If you want full flow:

```java id="rc6"
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testApi() {
        String result = restTemplate.getForObject("/users/1", String.class);
        assertNotNull(result);
    }
}
```

---

# 🔥 When to Use What

### ✅ Use `@WebMvcTest`

* Controller testing
* Fast
* Mock dependencies

---

### ✅ Use `@SpringBootTest`

* Full integration
* Real DB / full flow

---

# 🎯 Interview One-Liner

👉

> REST controllers are tested using `@WebMvcTest` with MockMvc to simulate HTTP requests and validate responses while mocking service dependencies.

---

# ⚠️ Common Mistakes

❌ Not using `@MockBean`
❌ Wrong URL mapping
❌ Missing `contentType` for POST
❌ Using `@SpringBootTest` unnecessarily
---

# 148. How to test service layer

Alright—this is a **very important and frequently asked interview question** 👇
(I’ll keep it clean + practical so you can answer confidently)

---

# 🔹 How to Test Service Layer?

## ✅ Main Idea

We test the **service layer using JUnit + Mockito**
👉 No Spring context needed (fast unit tests)

---

# 🔥 What we test in Service?

* Business logic ✅
* Conditions / validations ✅
* Interaction with repository ✅
* Success + failure scenarios ✅

---

# 🔹 Example Scenario

```text
UserService → UserRepository
```

---

# 🔹 1. Service Class

```java id="s1"
class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String getUser(int id) {
        String user = userRepository.findById(id);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        return user;
    }
}
```

---

# 🔹 2. Service Test (JUnit + Mockito)

```java id="s2"
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    void testGetUser_success() {
        when(userRepository.findById(1)).thenReturn("John");

        String result = userService.getUser(1);

        assertEquals("John", result);
        verify(userRepository).findById(1);
    }

    @Test
    void testGetUser_failure() {
        when(userRepository.findById(1)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> {
            userService.getUser(1);
        });
    }
}
```

---

# 🔥 Key Points

### ✅ Use:

* `@Mock` → mock repository
* `@InjectMocks` → real service
* `when()` → define behavior
* `verify()` → check interaction

---

# 🔄 Flow

```text
Test → Service → Mock Repository → Return fake data → Assert
```

---

# 🔹 What NOT to Use

❌ `@SpringBootTest`
❌ Real database
❌ Full application context

👉 Because:

* Slower
* Not unit testing

---

# 🔥 When to Use SpringBootTest for Service?

👉 Only when:

* You need real Spring features
* Integration testing

---

# 🎯 Interview Answer (Perfect)

👉

> Service layer is tested using JUnit and Mockito by mocking dependencies like repositories, verifying business logic, and testing both success and failure scenarios without loading the Spring context.

---

# ⚠️ Common Mistakes

❌ Calling real DB
❌ Not testing exception cases
❌ Not using `verify()`
❌ Using SpringBootTest unnecessarily

---

# 💡 Bonus Tip

👉 Always write:

* 1 success test
* 1 failure test
---
# 149. How to test repository layer?

Great—this is another **important interview question** 👇

---

# 🔹 How to Test Repository Layer?

## ✅ Main Idea

We test the **repository layer using `@DataJpaTest`**

👉 It loads only:

* JPA components
* Entity
* Repository
* In-memory DB (H2)

---

# 🔥 Why not Mockito?

👉 Repository interacts with DB
👉 We need to **test real queries**, not mock them

---

# 🔹 1. Entity Example

```java id="r1"
import jakarta.persistence.*;

@Entity
class User {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    // getters & setters
}
```

---

# 🔹 2. Repository

```java id="r2"
import org.springframework.data.jpa.repository.JpaRepository;

interface UserRepository extends JpaRepository<User, Long> {
    User findByName(String name);
}
```

---

# 🔹 3. Repository Test

```java id="r3"
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveUser() {
        User user = new User();
        user.setName("John");

        User saved = userRepository.save(user);

        assertNotNull(saved.getId());
    }

    @Test
    void testFindByName() {
        User user = new User();
        user.setName("John");
        userRepository.save(user);

        User found = userRepository.findByName("John");

        assertEquals("John", found.getName());
    }
}
```

---

# 🔥 What Happens Internally

```text id="q1t08r"
@DataJpaTest → loads JPA + H2 DB → runs queries → rollback after test
```

---

# 🔹 Key Features of `@DataJpaTest`

| Feature       | Description           |
| ------------- | --------------------- |
| In-memory DB  | Uses H2               |
| Transactional | Auto rollback         |
| Fast          | No full context       |
| JPA only      | No controller/service |

---

# 🔹 Optional: Use Real DB (Advanced)

```java id="r4"
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
```

👉 Use actual DB instead of H2

---

# 🔥 What to Test in Repository

* Save operation
* Find queries (`findBy...`)
* Custom queries (`@Query`)
* Delete operations

---

# 🎯 Interview One-Liner

👉

> Repository layer is tested using `@DataJpaTest`, which loads only JPA components and uses an in-memory database to verify real database operations.

---

# ⚠️ Common Mistakes

❌ Using Mockito for repository
❌ Using `@SpringBootTest` unnecessarily
❌ Not testing custom queries

---

# 🔄 Summary

```text id="summary1"
Service → Mockito  
Controller → MockMvc  
Repository → DataJpaTest
```
---
# 150. What is TestContainers?

Great—this is a **modern and very high-value interview topic** 👇

---

# 🔹 What is Testcontainers?

## ✅ Definition

**Testcontainers** is a Java library that allows you to run **real services (like databases) inside Docker containers during tests**.

👉 Instead of using H2 (fake DB), you use **real DB (MySQL, PostgreSQL, etc.)**

---

## ✅ Interview One-Liner

> Testcontainers is used to run real dependencies like databases in Docker containers for reliable and production-like integration testing.

---

# 🔥 Why Use Testcontainers?

### ❌ Problem with H2 / Fake DB

* Behavior differs from real DB
* Queries may pass in H2 but fail in production

---

### ✅ Solution with Testcontainers

* Real database ✔
* Real environment ✔
* More reliable tests ✔

---

# 🔹 Example Scenario

```text id="tcflow"
Test → Service → Repository → PostgreSQL (Docker container)
```

---

# 🔹 Basic Example

```java id="tc1"
import org.testcontainers.containers.PostgreSQLContainer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserRepositoryTest {

    static PostgreSQLContainer<?> postgres =
        new PostgreSQLContainer<>("postgres:15");

    static {
        postgres.start();
    }

    @Test
    void testDatabaseConnection() {
        System.out.println(postgres.getJdbcUrl());
    }
}
```

---

# 🔹 With Spring Boot (Best Practice)

```java id="tc2"
@Testcontainers
@SpringBootTest
class UserRepositoryTest {

    @Container
    static PostgreSQLContainer<?> postgres =
        new PostgreSQLContainer<>("postgres:15");

    @Test
    void test() {
        // test with real DB
    }
}
```

---

# 🔥 Key Annotations

| Annotation            | Purpose               |
| --------------------- | --------------------- |
| `@Testcontainers`     | Enable Testcontainers |
| `@Container`          | Define container      |
| `PostgreSQLContainer` | DB container          |

---

# 🔹 Supported Containers

* MySQL
* PostgreSQL
* MongoDB
* Kafka
* Redis

---

# 🔥 When to Use

### ✅ Use when:

* Testing repository with real DB
* Integration testing
* Avoid H2 limitations

---

### ❌ Avoid when:

* Simple unit tests
* No Docker available

---

# 🔥 Advantages

* Real environment
* No manual setup
* Automatic start/stop
* Better reliability

---

# 🔥 Disadvantages

* Requires Docker
* Slower than unit tests
* More setup

---

# 🔄 Comparison

| Feature  | H2 DB  | Testcontainers |
| -------- | ------ | -------------- |
| Real DB  | ❌ No   | ✅ Yes          |
| Speed    | ⚡ Fast | 🐢 Slower      |
| Accuracy | ❌ Less | ✅ High         |

---

# 🎯 Interview Answer (Perfect)

👉
> Testcontainers is used to run real services like databases inside Docker containers during testing, ensuring production-like behavior and more reliable integration tests compared to in-memory databases.

---

# ⚠️ Common Interview Mistakes

❌ Saying it replaces Mockito
❌ Using it for unit tests
❌ Not mentioning Docker
---

### **Testing Best Practices**
151. What is TDD (Test-Driven Development)?
Great—this is a **very important conceptual interview question** 👇

---

# 🔹 What is TDD (Test-Driven Development)?

## ✅ Definition

**TDD (Test-Driven Development)** is a development approach where you:

👉 **Write tests first → then write code → then refactor**

---

## ✅ Interview One-Liner

> TDD is a development practice where tests are written before the actual code to ensure correctness and better design.

---

# 🔥 TDD Cycle (Very Important)

👉 Known as **Red → Green → Refactor**

---

## 🔴 1. Red (Write Failing Test)

* Write a test for functionality
* Test will fail (because code doesn’t exist yet)

```java id="tdd1"
@Test
void testAddition() {
    assertEquals(5, calculator.add(2, 3)); // fails
}
```

---

## 🟢 2. Green (Write Code)

* Write minimal code to pass the test

```java id="tdd2"
int add(int a, int b) {
    return a + b;
}
```

---

## 🔵 3. Refactor

* Improve code quality
* Keep tests passing

---

# 🔄 Cycle

```text id="tddflow"
Write Test → Fail → Write Code → Pass → Refactor → Repeat
```

---

# 🔥 Why Use TDD?

### ✅ Benefits

* Better code quality
* Fewer bugs
* High test coverage
* Cleaner design
* Confidence in changes

---

# 🔹 Real Example Flow

```text id="realflow"
Step 1 → Write test for service  
Step 2 → Run → FAIL  
Step 3 → Implement service  
Step 4 → Run → PASS  
Step 5 → Refactor
```

---

# 🔥 When to Use TDD

* New feature development
* Critical business logic
* Agile development

---

# 🔥 When NOT Ideal

* Quick prototypes
* UI-heavy work
* Legacy code (hard to start)

---

# 🔥 TDD vs Traditional Testing

| Feature     | TDD         | Traditional |
| ----------- | ----------- | ----------- |
| Test timing | Before code | After code  |
| Approach    | Test-first  | Code-first  |
| Quality     | High        | Medium      |

---

# 🎯 Interview One-Liner

👉

> TDD follows a Red-Green-Refactor cycle where tests are written before code to ensure correctness and maintainability.

---

# ⚠️ Common Interview Mistakes

❌ Saying “testing after coding”
❌ Ignoring refactoring step
❌ Not mentioning Red-Green cycle

---

If you want next, I can give you a **real TDD coding example (step-by-step like interview)** 👍


# 152. What is BDD (Behavior-Driven Development)?Great—this is a **natural follow-up to TDD and very common in interviews** 👇

---

# 🔹 What is BDD (Behavior-Driven Development)?

## ✅ Definition

**BDD (Behavior-Driven Development)** is an extension of TDD that focuses on:

👉 **application behavior from the user/business perspective**

---

## ✅ Interview One-Liner

> BDD is a development approach that focuses on describing application behavior in a human-readable format using scenarios like Given-When-Then.

---

# 🔥 Key Idea

👉 Instead of testing *code*, BDD tests **behavior**

---

# 🔹 BDD Format (Very Important)

BDD uses:

```text
Given → When → Then
```

---

## ✅ Example (Readable)

```text
Given user has balance 100  
When user withdraws 50  
Then balance should be 50
```

---

# 🔹 Same Example in Code (JUnit + Mockito)

```java id="bdd1"
@Test
void testWithdraw() {
    // Given
    int balance = 100;

    // When
    int result = balance - 50;

    // Then
    assertEquals(50, result);
}
```

---

# 🔹 With Mockito (BDD Style)

Mockito also supports BDD style:

```java id="bdd2"
import static org.mockito.BDDMockito.*;

given(userRepository.findById(1)).willReturn("John");

String result = userService.getUser(1);

then(userRepository).should().findById(1);
```

---

# 🔥 Popular BDD Tools

* **Cucumber** (most popular)
* JBehave
* SpecFlow

---

# 🔹 Cucumber Example

```gherkin id="bdd3"
Feature: User Login

Scenario: Successful login
  Given user is on login page
  When user enters valid credentials
  Then user should see dashboard
```

---

# 🔥 TDD vs BDD (Very Important)

| Feature  | TDD          | BDD                 |
| -------- | ------------ | ------------------- |
| Focus    | Code         | Behavior            |
| Language | Technical    | Business-readable   |
| Format   | Test methods | Given-When-Then     |
| Audience | Developers   | Dev + QA + Business |

---

# 🔥 When to Use BDD

### ✅ Use when:

* Working with business stakeholders
* Need clear requirements
* Writing acceptance tests

---

### ❌ Avoid when:

* Small/simple logic
* No business interaction needed

---

# 🎯 Interview One-Liner

👉

> BDD focuses on testing application behavior using human-readable scenarios like Given-When-Then, making it easier for developers and business stakeholders to collaborate.

---

# ⚠️ Common Interview Mistakes

❌ Saying BDD = TDD (it’s an extension)
❌ Not mentioning Given-When-Then
❌ Ignoring business readability

---

# 💡 Simple Difference

```text
TDD → "Does the code work?"  
BDD → "Does the behavior match requirements?"
```
---



# 153. Unit test vs Integration test
Great—this is a **very common and important interview comparison** 👇

---

# 🔥 Unit Test vs Integration Test

---

# 🔹 1. Unit Test

## ✅ Definition

A **unit test** tests a **single class or method in isolation**.

👉 Dependencies are **mocked using Mockito**

---

## ✅ Example

```java id="ut1"
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository repo;

    @InjectMocks
    UserService service;

    @Test
    void testGetUser() {
        when(repo.findById(1)).thenReturn("John");

        String result = service.getUser(1);

        assertEquals("John", result);
    }
}
```

---

## ✅ Characteristics

* Tests **one unit only**
* Uses **mock dependencies**
* Very fast ⚡
* No Spring context

---

## ✅ Use case

* Business logic testing
* Service layer

---

# 🔹 2. Integration Test

## ✅ Definition

An **integration test** tests **multiple components together**

👉 Example:
Controller → Service → Repository → DB

---

## ✅ Example

```java id="it1"
@SpringBootTest
class UserIntegrationTest {

    @Autowired
    UserService userService;

    @Test
    void testFullFlow() {
        String result = userService.getUser(1);
        assertNotNull(result);
    }
}
```

---

## ✅ Characteristics

* Tests **real components together**
* Uses **real DB or Testcontainers**
* Slower 🐢
* Loads Spring context

---

## ✅ Use case

* End-to-end flow
* API testing
* DB interaction

---

# 🔥 Key Differences (Very Important)

| Feature        | Unit Test    | Integration Test |
| -------------- | ------------ | ---------------- |
| Scope          | Single class | Multiple layers  |
| Dependencies   | Mocked       | Real             |
| Speed          | ⚡ Fast       | 🐢 Slow          |
| Spring context | ❌ No         | ✅ Yes            |
| Example        | Service test | Full app test    |

---

# 🔄 Visual Understanding

```text id="viz3"
Unit Test        → Service (mock repo)  
Integration Test → Controller → Service → Repo → DB
```

---

# 🎯 When to Use What

### ✅ Use Unit Test

* Test logic
* Fast feedback
* Most of your tests

---

### ✅ Use Integration Test

* Test full flow
* Validate DB/API
* Ensure components work together

---

# 🎯 Interview One-Liner

👉

> Unit tests verify individual components in isolation using mocks, while integration tests validate multiple components working together with real dependencies.

---

# ⚠️ Common Interview Mistakes

❌ Saying both are same
❌ Using integration test for everything
❌ Not mentioning mocks in unit test

---

# 💡 Best Practice

```text id="best1"
70% Unit Tests  
20% Integration Tests  
10% End-to-End Tests
```
---

# 154. What is test coverage? Target coverage?
Great—this is a **very common QA + interview concept** 👇

---

# 🔹 What is Test Coverage?

## ✅ Definition

**Test coverage** measures **how much of your code is executed by your tests**.

👉 Usually expressed as a **percentage (%)**

---

## ✅ Example

```java id="tcov1"
if (x > 10) {
    System.out.println("Greater");
} else {
    System.out.println("Smaller");
}
```

👉 If your test only checks `x > 10`
→ Coverage = **50%** (else block not tested)

---

# 🔥 Types of Test Coverage

---

## 🔹 1. Line Coverage

👉 How many lines are executed

---

## 🔹 2. Branch Coverage (Very Important)

👉 Checks all conditions (if/else)

```java id="tcov2"
if (x > 10) { } else { }
```

✔ Both paths must be tested

---

## 🔹 3. Method Coverage

👉 How many methods are tested

---

## 🔹 4. Class Coverage

👉 How many classes are tested

---

# 🔹 Example

```java id="tcov3"
@Test
void testGreater() {
    check(20); // only one path tested
}
```

👉 Missing:

* else case ❌
* full coverage ❌

---

# 🔥 What is Target Coverage?

## ✅ Definition

**Target coverage** is the **desired percentage of test coverage** set by a team or organization.

---

## ✅ Common Targets

| Level            | Coverage |
| ---------------- | -------- |
| Minimum          | 60%      |
| Good             | 70–80%   |
| High             | 80–90%   |
| Critical systems | 90%+     |

---

# 🔥 Important Point (Interview Gold)

👉
**100% coverage ≠ bug-free code**

---

## ❗ Why?

* Tests may be weak
* Logic may not be validated properly
* Edge cases may be missing

---

# 🔹 Tools for Coverage

* JaCoCo (most popular)
* SonarQube
* IntelliJ coverage

---

# 🔄 Example Output

```text id="covout"
Line Coverage: 80%  
Branch Coverage: 70%
```

---

# 🎯 Interview One-Liner

👉

> Test coverage measures how much of the code is executed during testing, while target coverage is the desired percentage set by the team.

---

# ⚠️ Common Interview Mistakes

❌ Saying 100% coverage means perfect code
❌ Ignoring branch coverage
❌ Focusing only on numbers, not quality

---

# 💡 Best Practice

👉

* Aim for **70–80%+ coverage**
* Focus on **critical logic**
* Write **meaningful tests**, not just increase %

---
# 155. Mocking vs Stubbing

Great—this is a **very important conceptual Mockito question** 👇
(*Many people confuse these two in interviews!*)

---

# 🔹 Mocking vs Stubbing

---

# 🔹 What is Stubbing?

## ✅ Definition

**Stubbing** means **defining what a method should return when called**.

👉 You control the **output**

---

## ✅ Example

```java id="stub1"
when(userRepository.findById(1)).thenReturn("John");
```

👉 Here:

* You are telling mock → *“if called, return John”*

---

## ✅ Focus

✔ Return values
✔ Behavior setup

---

# 🔹 What is Mocking?

## ✅ Definition

**Mocking** means **creating fake objects and verifying interactions with them**.

👉 You check **whether something was called**

---

## ✅ Example

```java id="mock1"
verify(userRepository).findById(1);
```

👉 Here:

* You are checking → *“was this method called?”*

---

## ✅ Focus

✔ Interaction
✔ Behavior verification

---

# 🔥 Key Difference

| Feature | Stubbing              | Mocking            |
| ------- | --------------------- | ------------------ |
| Purpose | Define return value   | Verify interaction |
| Focus   | Output                | Behavior           |
| Example | `when().thenReturn()` | `verify()`         |

---

# 🔄 Combined Example (Very Important)

```java id="both1"
@Test
void testService() {

    // Stubbing
    when(userRepository.findById(1)).thenReturn("John");

    // Call method
    String result = userService.getUser(1);

    // Assertion
    assertEquals("John", result);

    // Mocking (verification)
    verify(userRepository).findById(1);
}
```

---

# 🔥 Simple Way to Remember

```text id="mem1"
Stubbing → "What to return?"  
Mocking  → "Was it called?"
```

---

# 🔹 Real-Life Analogy

👉 Think of a **phone call**

* **Stubbing** → You decide what the person will say
* **Mocking** → You check if the call happened

---

# 🎯 Interview One-Liner

👉

> Stubbing defines the behavior of a mock (what it should return), while mocking verifies interactions (whether a method was called).

---

# ⚠️ Common Interview Mistakes

❌ Saying both are same
❌ Using only stubbing without verification
❌ Confusing `when()` with `verify()`

---

# 💡 Pro Tip

👉 In real tests:

* Use **stubbing** to control behavior
* Use **verify** only when interaction matters

---
