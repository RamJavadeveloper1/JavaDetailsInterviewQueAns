For a **Senior Java Developer (5–8 years experience)**, Design Patterns are among the most frequently asked interview topics.

Interviewers usually expect:

1. Real-world understanding
2. When to use the pattern
3. Why it is needed
4. Java code example
5. Pros and Cons

---

# 1. What is Design Pattern?

### Beginner Explanation

Design Pattern is a proven solution for a commonly occurring software design problem.

Just like:

* We use a blueprint before building a house.
* We use design patterns before building software.

It helps make code:

* Reusable
* Maintainable
* Flexible
* Scalable

### Interview Answer

> Design Patterns are reusable object-oriented design solutions to recurring software development problems. They provide best practices for creating maintainable, extensible, and loosely coupled applications.

---

# 2. What are the Types of Design Patterns?

### Interview Answer

There are 3 categories:

### Creational Patterns

Object Creation

* Singleton
* Factory
* Abstract Factory
* Builder
* Prototype

### Structural Patterns

Object Structure

* Adapter
* Decorator
* Facade
* Proxy

### Behavioral Patterns

Object Communication

* Strategy
* Observer
* Command
* Template Method

---

# 3. Singleton Pattern (Most Asked)

## What Problem Does It Solve?

Only one object should exist.

Examples:

* Logger
* Configuration
* Cache
* Database Connection Manager

---

## Code

```java
public class Singleton {

    private static Singleton instance;

    private Singleton() {}

    public static Singleton getInstance() {

        if(instance == null) {
            instance = new Singleton();
        }

        return instance;
    }
}
```

Usage:

```java
Singleton s1 = Singleton.getInstance();
Singleton s2 = Singleton.getInstance();

System.out.println(s1 == s2);
```

Output:

```java
true
```

---

## Thread Safe Singleton

```java
public class Singleton {

    private static volatile Singleton instance;

    private Singleton(){}

    public static Singleton getInstance() {

        if(instance == null) {

            synchronized (Singleton.class) {

                if(instance == null) {
                    instance = new Singleton();
                }
            }
        }

        return instance;
    }
}
```

---

### Interview Answer

> Singleton ensures only one instance of a class exists throughout the application lifecycle and provides a global access point to it.

---

# 4. Factory Pattern (Very Common)

## Problem

Avoid object creation logic scattered everywhere.

Without Factory:

```java
if(type.equals("CAR"))
    vehicle = new Car();
else
    vehicle = new Bike();
```

---

## Factory Solution

### Interface

```java
public interface Vehicle {
    void drive();
}
```

### Implementations

```java
public class Car implements Vehicle {
    public void drive() {
        System.out.println("Driving Car");
    }
}
```

```java
public class Bike implements Vehicle {
    public void drive() {
        System.out.println("Driving Bike");
    }
}
```

### Factory

```java
public class VehicleFactory {

    public static Vehicle getVehicle(String type) {

        if(type.equals("CAR"))
            return new Car();

        if(type.equals("BIKE"))
            return new Bike();

        return null;
    }
}
```

Usage

```java
Vehicle vehicle = VehicleFactory.getVehicle("CAR");
vehicle.drive();
```

---

### Interview Answer

> Factory Pattern encapsulates object creation logic and allows clients to create objects without knowing their concrete implementation classes.

---

# 5. Builder Pattern (Spring Boot Projects)

### Problem

Object has many fields.

```java
User user = new User(
name,
email,
phone,
address,
city,
country,
zipCode
);
```

Messy and difficult to read.

---

## Builder Solution

```java
public class User {

    private String name;
    private String email;
    private String phone;

    private User(Builder builder) {
        this.name = builder.name;
        this.email = builder.email;
        this.phone = builder.phone;
    }

    public static class Builder {

        private String name;
        private String email;
        private String phone;

        public Builder name(String name){
            this.name = name;
            return this;
        }

        public Builder email(String email){
            this.email = email;
            return this;
        }

        public Builder phone(String phone){
            this.phone = phone;
            return this;
        }

        public User build(){
            return new User(this);
        }
    }
}
```

Usage:

```java
User user = new User.Builder()
                .name("Ram")
                .email("ram@gmail.com")
                .phone("9999999999")
                .build();
```

---

### Interview Answer

> Builder Pattern is used to construct complex objects step by step and improves readability by avoiding telescoping constructors.

---

# 6. Strategy Pattern (VERY IMPORTANT)

Asked in almost every senior interview.

---

## Problem

Multiple algorithms.

Payment:

* Credit Card
* UPI
* PayPal

---

### Interface

```java
public interface PaymentStrategy {
    void pay(int amount);
}
```

### UPI

```java
public class UpiPayment implements PaymentStrategy {

    public void pay(int amount) {
        System.out.println("Paid via UPI");
    }
}
```

### Card

```java
public class CardPayment implements PaymentStrategy {

    public void pay(int amount) {
        System.out.println("Paid via Card");
    }
}
```

### Context

```java
public class PaymentService {

    private PaymentStrategy strategy;

    public PaymentService(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    public void makePayment(int amount) {
        strategy.pay(amount);
    }
}
```

Usage

```java
PaymentService service =
        new PaymentService(new UpiPayment());

service.makePayment(1000);
```

---

### Interview Answer

> Strategy Pattern defines a family of algorithms, encapsulates them into separate classes, and allows changing behavior at runtime.

---

# 7. Observer Pattern

Very popular.

### Real Example

YouTube Channel

* Creator uploads video
* Subscribers get notification

---

### Observer

```java
public interface Observer {

    void update(String message);

}
```

### Subscriber

```java
public class Subscriber implements Observer {

    private String name;

    public Subscriber(String name){
        this.name = name;
    }

    public void update(String message){
        System.out.println(name + " got " + message);
    }
}
```

### Subject

```java
public class Channel {

    List<Observer> observers = new ArrayList<>();

    public void subscribe(Observer observer){
        observers.add(observer);
    }

    public void notifyUsers(String message){

        for(Observer observer : observers){
            observer.update(message);
        }
    }
}
```

---

### Interview Answer

> Observer Pattern establishes a one-to-many dependency so that when one object changes state, all dependent objects are notified automatically.

---

# 8. Decorator Pattern

### Problem

Need to add behavior without modifying code.

Coffee:

* Basic Coffee
* Coffee + Milk
* Coffee + Milk + Sugar

---

### Interview Answer

> Decorator Pattern dynamically adds responsibilities to objects without modifying existing code.

---

# 9. Adapter Pattern

### Real Example

Mobile Charger Adapter

Indian Socket → Mobile Charger

---

### Interview Answer

> Adapter Pattern allows incompatible interfaces to work together by acting as a bridge between them.

---

# 10. Prototype Pattern

### Problem

Object creation expensive.

Clone existing object.

```java
Employee emp2 = emp1.clone();
```

---

### Interview Answer

> Prototype Pattern creates new objects by cloning existing instances rather than creating them from scratch.

---

# Spring Boot Design Patterns (Frequently Asked)

## Q. Which Design Patterns are used in Spring?

### Answer

| Pattern   | Example                 |
| --------- | ----------------------- |
| Singleton | Spring Beans            |
| Factory   | BeanFactory             |
| Proxy     | AOP                     |
| Template  | JdbcTemplate            |
| Observer  | EventListener           |
| Strategy  | Security Authentication |
| Builder   | RestTemplateBuilder     |

---

# Most Asked Rapid-Fire Questions

### Why Singleton?

> To ensure only one object exists and save memory/resources.

---

### Difference between Factory and Builder?

Factory:

* Creates object in one step

Builder:

* Creates object step by step

---
### Strategy vs Factory?

Factory:

* Creates object

Strategy:

* Changes behavior

---
### Why Strategy over if-else?

Because it follows:

* Open Closed Principle
* Easy extension
* Less coupling

---
### Which Design Pattern have you used?

Strong senior answer:

> In Spring Boot projects I frequently use Singleton (Spring Beans), Builder (Lombok @Builder), Strategy (payment/authentication modules), Factory (service creation), Observer (event-driven processing), and Proxy Pattern through Spring AOP.

---

# Top 10 Design Pattern Questions Asked in Senior Java Interviews

1. What is Singleton Pattern? How do you make it thread-safe?
2. Difference between Singleton and Spring Singleton Bean?
3. What is Factory Pattern?
4. Factory vs Abstract Factory?
5. Builder Pattern and why Lombok @Builder is useful?
6. What is Strategy Pattern? Real project example?
7. Explain Observer Pattern with Spring Events.
8. What Design Patterns are used internally by Spring Framework?
9. Difference between Decorator and Proxy Pattern?
10. Which Design Pattern did you use in your current project and why?

These 10 questions cover roughly **80–90% of design-pattern questions** asked in Java/Spring Boot interviews for 5–8 years experienced developers.

--------------------------------------------------------------------------------------------------------------------------------------------
# 1. What is Singleton Pattern? How do you make it thread-safe?

## Beginner Explanation

Singleton means:

> Only one object of a class should be created in the entire application.

Example:

* Logger
* Configuration Manager
* Cache Manager

If 100 classes need configuration, all should use the same object.

---

## Thread Safety Problem

Without thread safety:

```java
if(instance == null){
    instance = new Singleton();
}
```

Two threads can enter simultaneously and create two objects.

---

## Thread Safe Singleton (Double Checked Locking)

```java
public class Singleton {

    private static volatile Singleton instance;

    private Singleton(){}

    public static Singleton getInstance() {

        if(instance == null) {

            synchronized (Singleton.class) {

                if(instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
```

---

## Interview Answer

> Singleton Pattern ensures only one instance of a class exists throughout the application lifecycle and provides a global access point to it. To make it thread-safe, I use Double Checked Locking with a volatile instance variable.

---

# 2. Difference Between Singleton and Spring Singleton Bean

| Singleton Pattern            | Spring Singleton Bean           |
| ---------------------------- | ------------------------------- |
| Created by JVM               | Managed by Spring Container     |
| One instance per ClassLoader | One instance per Spring Context |
| Developer creates object     | Spring creates object           |
| Manual implementation        | Automatic                       |

Example:

```java
@Service
public class UserService {
}
```

Spring creates only one bean.

---

## Interview Answer

> A Singleton Pattern is implemented manually in Java to ensure a single instance. Spring Singleton Scope means Spring IoC Container manages a single bean instance per application context.

---

# 3. What is Factory Pattern?

## Problem

Object creation logic is spread everywhere.

```java
if(type.equals("UPI"))
   payment = new UpiPayment();

if(type.equals("CARD"))
   payment = new CardPayment();
```

Not maintainable.

---

## Factory Solution

```java
public interface Payment {
    void pay();
}
```

```java
public class UpiPayment implements Payment {
    public void pay() {
        System.out.println("UPI Payment");
    }
}
```

```java
public class CardPayment implements Payment {
    public void pay() {
        System.out.println("Card Payment");
    }
}
```

```java
public class PaymentFactory {

    public static Payment getPayment(String type) {

        switch(type){

            case "UPI":
                return new UpiPayment();

            case "CARD":
                return new CardPayment();

            default:
                throw new RuntimeException("Invalid Type");
        }
    }
}
```

---

## Interview Answer

> Factory Pattern encapsulates object creation logic and allows clients to create objects without knowing their concrete implementation classes.

---

# 4. Factory vs Abstract Factory

## Factory Pattern

Creates one type of product.

Example:

```java
VehicleFactory
```

Creates:

* Car
* Bike

---

## Abstract Factory

Creates families of related objects.

Example:

Windows UI Factory:

Creates:

* Windows Button
* Windows Checkbox

Mac UI Factory:

Creates:

* Mac Button
* Mac Checkbox

---

## Interview Answer

| Factory             | Abstract Factory         |
| ------------------- | ------------------------ |
| Creates one product | Creates related products |
| Simpler             | More complex             |
| One factory         | Multiple factories       |

> Factory creates individual objects, whereas Abstract Factory creates families of related objects.

---

# 5. Builder Pattern and Why Lombok @Builder is Useful?

## Problem

Large Constructor

```java
User user = new User(
"name",
"email",
"phone",
"city",
"country"
);
```

Hard to read.

---

## Builder Solution

```java
User user = User.builder()
        .name("Ram")
        .email("ram@gmail.com")
        .phone("999999999")
        .build();
```

---

## Lombok

```java
@Builder
@Getter
public class User {

    private String name;
    private String email;
    private String phone;
}
```

Lombok automatically generates:

* Builder Class
* Setter Methods
* build() method

---

## Interview Answer

> Builder Pattern is used to create complex objects step by step. Lombok @Builder eliminates boilerplate code and improves readability and maintainability.

---

# 6. What is Strategy Pattern? Real Project Example?

## Beginner Explanation

When multiple algorithms exist for the same task.

Example:

Payment Methods

* UPI
* Credit Card
* Net Banking

Each has different implementation.

---

## Strategy Interface

```java
public interface PaymentStrategy {

    void pay(double amount);

}
```

---

## Implementations

```java
public class UpiPaymentStrategy
        implements PaymentStrategy {

    @Override
    public void pay(double amount) {
        System.out.println("UPI Payment");
    }
}
```

```java
public class CardPaymentStrategy
        implements PaymentStrategy {

    @Override
    public void pay(double amount) {
        System.out.println("Card Payment");
    }
}
```

---

## Context

```java
public class PaymentService {

    private PaymentStrategy strategy;

    public PaymentService(
            PaymentStrategy strategy) {

        this.strategy = strategy;
    }

    public void pay(double amount){
        strategy.pay(amount);
    }
}
```

---

## Real Project Example

Since you worked on Spring Boot and Lambda migration:

> We had different notification channels such as Email, SMS, and Push Notifications. Each channel had a different implementation. We used Strategy Pattern to dynamically select the notification provider at runtime instead of writing large if-else blocks.

---

## Interview Answer

> Strategy Pattern defines a family of algorithms, encapsulates them into separate classes, and allows behavior to be changed at runtime. In my project, it can be used for notification processing or payment processing modules.

---

# 7. Explain Observer Pattern with Spring Events

## Beginner Explanation

One object changes state.

All interested objects get notified automatically.

Example:

YouTube Channel

Upload Video → Subscribers notified

---

## Spring Example

Event

```java
public class UserCreatedEvent {

    private String email;

    public UserCreatedEvent(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
```

---

Publisher

```java
@Autowired
private ApplicationEventPublisher publisher;

publisher.publishEvent(
    new UserCreatedEvent(email)
);
```

---

Listener

```java
@Component
public class WelcomeEmailListener {

    @EventListener
    public void sendWelcomeEmail(
            UserCreatedEvent event) {

        System.out.println(
            "Email sent to "
            + event.getEmail());
    }
}
```

---

## Interview Answer

> Observer Pattern establishes a one-to-many relationship where multiple listeners are notified when an event occurs. Spring implements this through ApplicationEventPublisher and @EventListener.

---

# 8. What Design Patterns Are Used Internally by Spring Framework?

This is a favorite senior-level question.

| Pattern   | Spring Example              |
| --------- | --------------------------- |
| Singleton | Default Bean Scope          |
| Factory   | BeanFactory                 |
| Proxy     | AOP, Transaction Management |
| Observer  | EventListener               |
| Template  | JdbcTemplate                |
| Strategy  | AuthenticationProvider      |
| Adapter   | HandlerAdapter              |
| Decorator | BeanWrapper                 |

---

## Interview Answer

> Spring heavily uses Singleton, Factory, Proxy, Observer, Strategy, Template Method, and Adapter Patterns. For example, BeanFactory uses Factory Pattern, AOP uses Proxy Pattern, and Spring Events use Observer Pattern.

---

# 9. Difference Between Decorator and Proxy Pattern

| Decorator                    | Proxy                    |
| ---------------------------- | ------------------------ |
| Adds new behavior            | Controls access          |
| Enhances functionality       | Acts as a representative |
| Focus on extension           | Focus on access control  |
| Multiple decorators possible | Usually one proxy        |

---

## Decorator Example

```java
Coffee coffee =
      new MilkDecorator(
      new SugarDecorator(
      new BasicCoffee()));
```

Adds functionality.

---

## Proxy Example

```java
proxy.downloadFile();
```

Checks:

* Authentication
* Logging
* Permission

Then calls actual object.

---

## Interview Answer

> Decorator Pattern adds additional functionality to an object dynamically, whereas Proxy Pattern controls access to an object and may add security, caching, or lazy loading.

---

# 10. Which Design Pattern Did You Use in Your Current Project and Why?

Since you are a **Senior Java Developer working on Spring Boot applications**, a strong answer is:

### Singleton Pattern

> Spring beans such as Service, Repository, and Controller are Singleton by default. This reduces object creation overhead and improves memory utilization.

### Builder Pattern

> I use Lombok @Builder for creating DTOs, API responses, and request objects with multiple fields. It improves readability and avoids telescoping constructors.

### Factory Pattern

> We use Factory Pattern to select different implementations based on business requirements. For example, choosing notification providers or file processors dynamically.

### Observer Pattern

> We use Spring Events with ApplicationEventPublisher and @EventListener to decouple modules. For example, after user registration we publish an event, and separate listeners send emails, audit logs, or notifications.

---

# 30-Second Interview Answer

> In my current Spring Boot project, I primarily use Singleton, Builder, Factory, and Observer patterns. Singleton is used through Spring-managed beans, Builder through Lombok @Builder for DTO creation, Factory for selecting implementations dynamically, and Observer through Spring Events using ApplicationEventPublisher and @EventListener. These patterns improve maintainability, loose coupling, and scalability of the application.

------------------------------------------------------------------------------------------------------------------------------------------

Yes. The patterns we covered are the ones most commonly asked in Java/Spring Boot interviews, but several important patterns are still left.

For a **Senior Java Developer (6+ years)**, these are also worth knowing:

---

# 1. Prototype Pattern

## Purpose

Create a new object by copying an existing object.

Instead of:

```java
Employee emp2 = new Employee();
```

Use:

```java
Employee emp2 = emp1.clone();
```

---

## Example

```java
public class Employee implements Cloneable {

    private String name;

    public Employee(String name) {
        this.name = name;
    }

    @Override
    public Employee clone() {
        try {
            return (Employee) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException();
        }
    }
}
```

Usage

```java
Employee emp1 = new Employee("Ram");

Employee emp2 = emp1.clone();
```

---

## Interview Answer

> Prototype Pattern creates new objects by cloning existing objects instead of creating them from scratch.

---

# 2. Adapter Pattern

## Real Life Example

Mobile charger adapter.

Indian Socket → Adapter → Mobile Charger

Different interfaces become compatible.

---

## Example

Old Service

```java
public class LegacyPayment {

    public void makePayment() {
        System.out.println("Legacy Payment");
    }
}
```

---

Adapter

```java
public class PaymentAdapter
        implements PaymentService {

    private LegacyPayment legacyPayment;

    public PaymentAdapter(
            LegacyPayment legacyPayment) {
        this.legacyPayment = legacyPayment;
    }

    @Override
    public void pay() {
        legacyPayment.makePayment();
    }
}
```

---

## Spring Example

Spring MVC uses:

```java
HandlerAdapter
```

---

## Interview Answer

> Adapter Pattern allows incompatible interfaces to work together without modifying existing code.

---

# 3. Facade Pattern

## Purpose

Hide system complexity behind one simple interface.

---

Without Facade

```java
paymentService.pay();
inventoryService.update();
notificationService.send();
```

---

With Facade

```java
orderFacade.placeOrder();
```

Internally:

```java
public class OrderFacade {

    public void placeOrder() {

        paymentService.pay();

        inventoryService.update();

        notificationService.send();
    }
}
```

---

## Interview Answer

> Facade Pattern provides a simplified interface to a complex subsystem.

---

# 4. Proxy Pattern

Very important because Spring AOP uses it.

---

## Purpose

Control access to an object.

---

Real Example

ATM Card

You don't directly access bank server.

Card acts as proxy.

---

## Example

```java
public interface FileService {

    void download();
}
```

Real Object

```java
public class RealFileService
        implements FileService {

    public void download() {
        System.out.println("Downloading...");
    }
}
```

Proxy

```java
public class FileProxy
        implements FileService {

    private RealFileService service;

    public void download() {

        if(checkPermission()) {

            service = new RealFileService();

            service.download();
        }
    }
}
```

---

## Spring Example

```java
@Transactional
```

Spring creates Proxy Objects.

---

## Interview Answer

> Proxy Pattern provides a placeholder object that controls access to the real object and may add security, logging, caching, or lazy loading.

---

# 5. Template Method Pattern

Frequently asked with Spring.

---

## Purpose

Define algorithm structure in parent class.

Allow subclasses to customize steps.

---

Example

```java
public abstract class ReportGenerator {

    public final void generateReport() {

        fetchData();

        processData();

        exportReport();
    }

    abstract void fetchData();

    abstract void processData();

    abstract void exportReport();
}
```

---

Child

```java
public class PdfReportGenerator
        extends ReportGenerator {

    void fetchData() {}

    void processData() {}

    void exportReport() {
        System.out.println("PDF Export");
    }
}
```

---

## Spring Example

* JdbcTemplate
* RestTemplate

---

## Interview Answer

> Template Method Pattern defines the skeleton of an algorithm and allows subclasses to override specific steps without changing the overall workflow.

---

# 6. Command Pattern

## Purpose

Convert request into an object.

---

Example

```java
public interface Command {

    void execute();
}
```

Command

```java
public class LightOnCommand
        implements Command {

    public void execute() {
        System.out.println("Light ON");
    }
}
```

Invoker

```java
public class RemoteControl {

    private Command command;

    public RemoteControl(Command command) {
        this.command = command;
    }

    public void pressButton() {
        command.execute();
    }
}
```

---

## Interview Answer

> Command Pattern encapsulates a request as an object, allowing parameterization, queuing, logging, and undo operations.

---

# 7. Chain of Responsibility Pattern

Very Important in Spring Security.

---

Example

Request goes through:

```text
Authentication Filter
       ↓
JWT Filter
       ↓
Authorization Filter
       ↓
Controller
```

Each handler decides:

```java
handle(request)
```

or pass to next.

---

## Interview Answer

> Chain of Responsibility passes a request through multiple handlers, where each handler processes or forwards the request to the next handler.

---

# 8. State Pattern

## Purpose

Object behavior changes based on current state.

---

Example

Order

```text
NEW
 ↓
PAID
 ↓
SHIPPED
 ↓
DELIVERED
```

Each state behaves differently.

---

## Interview Answer

> State Pattern allows an object to change its behavior dynamically when its internal state changes.

---

# 9. MVC Pattern (Spring MVC)

Interviewers sometimes ask this as well.

---

Components:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

---

## Spring Example

```java
@RestController
public class UserController {
}
```

```java
@Service
public class UserService {
}
```

```java
@Repository
public class UserRepository {
}
```

---

# Most Important Patterns for Spring Boot Interviews

Focus in this order:

### Must Know (Asked Very Frequently)

1. Singleton
2. Factory
3. Builder
4. Strategy
5. Observer
6. Proxy
7. Template Method

### Frequently Asked

8. Adapter
9. Facade
10. Chain of Responsibility

### Nice to Have

11. Prototype
12. Command
13. State

For your experience level (6+ years Spring Boot), if you can confidently explain **Singleton, Factory, Builder, Strategy, Observer, Proxy, Template Method, and Chain of Responsibility with Spring examples**, you'll be well prepared for most design-pattern interview rounds.
------------------------------------------------------------------------------------------------------------------------------------------


For a **Senior Java Developer (6+ years)**, interviewers often ask tricky design-pattern questions to check whether you truly understand the patterns or have just memorized definitions.

# 1. Can Singleton Be Broken?

### Expected Answer

Yes.

Singleton can be broken using:

* Reflection
* Serialization/Deserialization
* Cloning
* Multiple Class Loaders

Example:

```java
Constructor<Singleton> c =
        Singleton.class.getDeclaredConstructor();

c.setAccessible(true);

Singleton s2 = c.newInstance();
```

Now two objects exist.

### Best Solution

Use Enum Singleton:

```java
public enum Singleton {
    INSTANCE;
}
```

---

# 2. Why Not Use Singleton Everywhere?

### Expected Answer

Singleton introduces:

* Global state
* Tight coupling
* Difficult unit testing
* Thread safety concerns

### Interview Answer

> Singleton should be used only when exactly one instance is required. Overusing Singleton creates hidden dependencies and reduces testability.

---

# 3. Factory vs Strategy (Very Common)

Interviewer:

> Both remove if-else. Why do we need both?

### Answer

Factory:

```java
Payment payment =
    factory.create("UPI");
```

Creates objects.

Strategy:

```java
payment.pay();
```

Executes behavior.

### One-Liner

> Factory decides WHICH object to create, Strategy decides HOW an operation is performed.

---

# 4. Why Builder Instead of Setters?

### Setter Approach

```java
User user = new User();

user.setName("Ram");
user.setEmail("ram@gmail.com");
```

Problems:

* Mutable object
* Object may be incomplete

### Builder

```java
User user = User.builder()
                .name("Ram")
                .email("ram@gmail.com")
                .build();
```

Advantages:

* Immutable
* Readable
* Safer

---

# 5. Factory Pattern Violates Open Closed Principle?

### Basic Factory

```java
switch(type) {
   case "A":
   case "B":
}
```

Needs modification for every new type.

### Better Answer

> Simple Factory may violate OCP because new types require factory modification. Abstract Factory or Spring Dependency Injection can reduce this issue.

---

# 6. Why Spring Doesn't Use Singleton Pattern Directly?

### Trick Question

Many candidates say:

> Spring uses Singleton Pattern.

Partially correct.

### Correct Answer

Spring uses:

```java
@Scope("singleton")
```

which means:

> One bean per Spring Container.

Not JVM-wide Singleton.

---

# 7. What Design Pattern Is @Autowired?

### Expected Answer

Not a Design Pattern itself.

Internally Spring uses:

* Dependency Injection
* Factory Pattern
* Singleton Pattern

---

# 8. Is Dependency Injection a Design Pattern?

### Answer

No.

It is a design principle / technique.

Spring implements it using:

* Factory Pattern
* Inversion of Control

---

# 9. Why Is Strategy Better Than if-else?

### Bad

```java
if(type.equals("UPI")) {

}
else if(type.equals("CARD")) {

}
else if(type.equals("NETBANKING")) {

}
```

Every new payment method requires modification.

### Strategy

```java
Map<String, PaymentStrategy>
```

New strategy:

```java
@Component
class WalletStrategy
```

No code modification.

### Interview Answer

> Strategy follows Open Closed Principle and improves maintainability by removing large conditional blocks.

---

# 10. How Is Spring Security Using Chain of Responsibility?

### Answer

Request passes through:

```text
Filter 1
   ↓
Filter 2
   ↓
JWT Filter
   ↓
Authorization Filter
   ↓
Controller
```

Each filter:

* Processes request
* Passes to next filter

Classic Chain of Responsibility.

---

# 11. Why Is AOP Proxy and Not Decorator?

### Trick Question

Many candidates confuse them.

### Proxy

Purpose:

```text
Access Control
Security
Logging
Transaction
```

Decorator:

```text
Add Functionality
```

Spring AOP mainly controls behavior around method execution.

Therefore:

> AOP primarily uses Proxy Pattern.

---

# 12. Why Is JdbcTemplate Called Template Pattern?

### Answer

Workflow is fixed:

```text
Open Connection
Execute Query
Handle Exception
Close Connection
```

Developer customizes only:

```java
RowMapper
```

This is Template Method Pattern.

---

# 13. Which Pattern Is Used Most in Your Project?

Since you work on Spring Boot:

### Strong Answer

> In my current Spring Boot project, I frequently use Builder Pattern through Lombok @Builder for DTO creation, Factory Pattern for selecting implementation classes, Observer Pattern using Spring Events, and Singleton Pattern through Spring-managed beans. Spring Security internally uses Chain of Responsibility, while transaction management uses Proxy Pattern.

---

# 14. Can You Combine Factory and Strategy?

### Answer

Yes.

Very common.

Factory creates strategy.

```java
PaymentStrategy strategy =
        factory.getStrategy("UPI");
```

Then:

```java
strategy.pay();
```

Factory + Strategy together.

---

# 15. Which SOLID Principle Is Most Related to Strategy Pattern?

### Answer

Open Closed Principle

Because:

```text
Open for extension
Closed for modification
```

New strategy added without changing existing code.

---

# 16. Which Design Pattern Would You Use for Notification Service?

### Strong Senior Answer

* Strategy → Email/SMS/Push implementations
* Factory → Select notification provider
* Observer → Trigger notifications after events
* Builder → Build notification request

This answer impresses interviewers because it shows patterns working together.

---

## Top 10 Tricky Questions Asked Most Often

1. Can Singleton be broken?
2. Singleton vs Spring Singleton?
3. Factory vs Strategy?
4. Strategy vs if-else?
5. Builder vs Setters?
6. Why use Builder if Lombok exists?
7. How does Spring Security use Chain of Responsibility?
8. Why is AOP Proxy and not Decorator?
9. Which design patterns are used inside Spring Framework?
10. Which design patterns have you used in your current project and why?

These are the kinds of follow-up questions that usually appear after the interviewer asks the basic definitions. A candidate who answers these confidently is often viewed as having practical experience rather than just theoretical knowledge.

-----------------------------------------------------------------------------------------------------------------------------------------

In a real **Spring Boot project**, you usually use **8–12 design patterns**, even if you don't realize it because Spring implements many of them internally.

For your experience level (**6+ years Java/Spring Boot**), this is a strong interview answer.

# Design Patterns Used in Spring Boot Projects

| Design Pattern          | Where Used                            | Example                         |
| ----------------------- | ------------------------------------- | ------------------------------- |
| Singleton               | Service, Repository, Controller Beans | `@Service`, `@Repository`       |
| Builder                 | DTOs, Request/Response Objects        | Lombok `@Builder`               |
| Factory                 | Bean Creation                         | `BeanFactory`, custom factories |
| Strategy                | Multiple business implementations     | Payment, Notification           |
| Observer                | Event-driven processing               | `ApplicationEventPublisher`     |
| Proxy                   | AOP, Transactions                     | `@Transactional`                |
| Template Method         | Database/API operations               | `JdbcTemplate`, `RestTemplate`  |
| Adapter                 | Framework integration                 | `HandlerAdapter`                |
| Facade                  | Simplify complex workflows            | OrderFacade                     |
| Chain of Responsibility | Request processing                    | Spring Security Filters         |

---

# 1. Singleton Pattern

### Where?

```java
@Service
public class UserService {
}
```

Spring creates one object.

### Use Case

* UserService
* OrderService
* ProductService
* Repository

### Why?

* Saves memory
* Improves performance

### Interview Answer

> Spring beans are Singleton by default. We use Singleton for stateless services and repositories to avoid unnecessary object creation.

---

# 2. Builder Pattern

### Where?

DTO creation.

Without Builder

```java
UserDto dto =
 new UserDto(name,email,phone,address);
```

With Builder

```java
UserDto dto = UserDto.builder()
        .name("Ram")
        .email("ram@gmail.com")
        .build();
```

### Use Case

* Request DTO
* Response DTO
* Entity Mapping

### In Your Project

For Gramin Connect:

```java
ProductResponse.builder()
      .id(product.getId())
      .name(product.getName())
      .price(product.getPrice())
      .build();
```

---

# 3. Factory Pattern

### Use Case

Multiple implementations.

Example:

```java
NotificationService
```

Implementations:

```java
EmailNotificationService
SmsNotificationService
PushNotificationService
```

Factory

```java
public NotificationService getService(
        String type) {

   if(type.equals("EMAIL"))
       return emailService;

   return smsService;
}
```

### Interview Answer

> Factory Pattern is useful when object creation depends on runtime conditions.

---

# 4. Strategy Pattern

Most common business pattern.

### Example

Payment Processing

```java
UPI
CARD
NETBANKING
```

Each has different logic.

Interface

```java
public interface PaymentStrategy {
    void pay();
}
```

Implementations

```java
UpiPayment
CardPayment
```

### Use Case

* Payment
* Notification
* Pricing Logic
* Discount Calculation

### In Your Project

Tender pricing calculation could be Strategy Pattern.

---

# 5. Observer Pattern

### Example

User Registration

After user registration:

```java
publisher.publishEvent(
      new UserCreatedEvent(user));
```

Listeners:

```java
SendEmailListener
AuditListener
NotificationListener
```

### Use Case

* Notifications
* Audit Logs
* Async Processing

### Interview Answer

> Observer Pattern helps decouple modules by notifying interested listeners when events occur.

---

# 6. Proxy Pattern

### Example

```java
@Transactional
public void saveOrder() {
}
```

Spring creates proxy.

Actual flow:

```text
Client
 ↓
Proxy
 ↓
Transaction Start
 ↓
Method Execution
 ↓
Commit/Rollback
```

### Use Case

* Transactions
* Logging
* Security
* Caching

---

# 7. Template Method Pattern

### Example

```java
JdbcTemplate
```

You write:

```java
jdbcTemplate.query(...)
```

Spring handles:

```text
Connection Open
Execute Query
Handle Exception
Close Connection
```

### Use Case

* Database access
* REST calls

Examples:

```java
JdbcTemplate
RestTemplate
RedisTemplate
KafkaTemplate
```

---

# 8. Adapter Pattern

### Example

Spring MVC

Controller signatures vary:

```java
@GetMapping
@PostMapping
```

Spring uses:

```java
HandlerAdapter
```

to adapt controllers to a common execution model.

### Use Case

* Third-party integration
* Legacy system integration

---

# 9. Facade Pattern

### Example

Order Processing

Without Facade

```java
paymentService.pay();
inventoryService.update();
shippingService.ship();
notificationService.send();
```

With Facade

```java
orderFacade.placeOrder();
```

### Use Case

Complex workflows.

---

# 10. Chain of Responsibility

### Example

Spring Security

```text
Request
 ↓
JWT Filter
 ↓
Authentication Filter
 ↓
Authorization Filter
 ↓
Controller
```

Each filter:

```java
doFilter()
```

Processes request and forwards it.

### Use Case

* Security
* Validation
* Request processing

---

# Strong Answer for Interview

If an interviewer asks:

**"Which design patterns are used in your current Spring Boot project?"**

You can answer:

> In my Spring Boot projects, I commonly use Singleton through Spring-managed beans, Builder through Lombok @Builder, Factory and Strategy patterns for selecting implementations dynamically, Observer through Spring Events, Proxy through @Transactional and AOP, Template Method via JdbcTemplate and RestTemplate, Facade for orchestration services, and Chain of Responsibility in Spring Security filters. These patterns help improve maintainability, loose coupling, scalability, and testability.

This answer is typically strong enough for **Senior Java Developer interviews in product-based companies**, because it connects each pattern to a real Spring Boot use case rather than only giving textbook definitions.

