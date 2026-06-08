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
