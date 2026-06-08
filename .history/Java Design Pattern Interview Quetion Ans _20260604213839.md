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
