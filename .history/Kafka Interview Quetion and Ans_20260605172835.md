Perfect! Let's start **Kafka Interview Questions & Answers** from Beginner → Intermediate → Experienced level.

---

# Kafka Interview Question 1

## What is Kafka?

### Beginner Understanding

Suppose you have:

* Order Service
* Payment Service
* Notification Service

When an order is placed:

1. Order Service creates order.
2. Payment Service processes payment.
3. Notification Service sends email/SMS.

Without Kafka, services call each other directly.

```text
Order Service
     |
     v
Payment Service
     |
     v
Notification Service
```

This creates tight coupling.

With Kafka:

```text
Order Service --> Kafka --> Payment Service
                      |
                      ---> Notification Service
```

Services become independent.

---

### Professional Interview Answer

Kafka is a distributed event-streaming platform used for building real-time data pipelines and event-driven applications. It enables producers to publish messages and consumers to process them asynchronously with high throughput, fault tolerance, and scalability.

---

## Follow-Up Question

### Why do we use Kafka?

### Answer

Kafka is used for:

* Asynchronous communication
* Event-driven architecture
* High throughput messaging
* Decoupling microservices
* Real-time data processing
* Log aggregation
* Stream processing

---

## Real Project Example

Imagine a Banking Application.

When money is transferred:

```text
Transaction Service
        |
        v
      Kafka
      / | \
     /  |  \
Audit Fraud Notification
```

One event is consumed by multiple services independently.

---

## Interview Short Answer (30 Seconds)

> Apache Kafka is a distributed messaging and event-streaming platform used to exchange data between applications asynchronously. It provides high throughput, scalability, fault tolerance, and is commonly used in microservices architectures for event-driven communication.

---

# Kafka Interview Question 2

## What is a Topic in Kafka?

### Beginner Understanding

A Topic is like a category or channel where messages are stored.

Example:

```text
order-created
payment-completed
user-registered
```

Each type of event can have its own topic.

---

### Example

Order Created Event:

```json
{
  "orderId": 101,
  "customer": "Ram"
}
```

Published into:

```text
order-created
```

topic.

---

### Professional Interview Answer

A Topic is a logical channel in Kafka used to organize and store messages. Producers publish messages to topics, and consumers subscribe to topics to read those messages.

---

## Real Project Example

In an E-Commerce Application:

| Topic               | Purpose          |
| ------------------- | ---------------- |
| order-created       | New order events |
| payment-success     | Payment events   |
| inventory-updated   | Stock updates    |
| notification-events | Email/SMS events |

---

## Interview Follow-Up

### Does Kafka create topics automatically?

Answer:

Yes, Kafka can create topics automatically if configured, but in production environments topics are usually created explicitly to control partitions and replication.

---

# Kafka Interview Question 3

## What is a Producer?

### Beginner Understanding

Producer = Message Sender

Example:

```text
Spring Boot Application
         |
         v
       Kafka
```

Producer sends data to Kafka.

---

### Example

Order Service:

```java
kafkaTemplate.send(
   "order-created",
   orderObject
);
```

This service is Producer.

---

### Professional Interview Answer

A Producer is a Kafka client that publishes messages to Kafka topics. Producers can send messages synchronously or asynchronously and can choose the partition where messages are stored.

---

## Real Project Answer

> In my Spring Boot project, Order Service acted as a Kafka Producer and published order events to the `order-created` topic. Other services consumed those events independently.

---

# Kafka Interview Question 4

## What is a Consumer?

### Beginner Understanding

Consumer = Message Reader

```text
Kafka Topic
      |
      v
 Notification Service
```

Consumer reads messages from topic.

---

### Spring Boot Example

```java
@KafkaListener(
   topics = "order-created"
)
public void consume(OrderEvent event) {

    System.out.println(event);
}
```

---

### Professional Interview Answer

A Consumer is a Kafka client that subscribes to one or more topics and processes messages published by producers.

---

## Real Project Example

```text
Order Service
      |
      v
Kafka Topic
      |
      +----> Inventory Service
      |
      +----> Notification Service
      |
      +----> Analytics Service
```

All are Consumers.

---

# Interview Trap Question

## Can Kafka work without Consumers?

Answer:

Yes.

Kafka stores messages in topics even if no consumer is currently reading them. Consumers can read the messages later based on retention policies.

---

# Quick Revision

### Kafka

Platform for event streaming and messaging.

### Topic

Logical channel where messages are stored.

### Producer

Sends messages to Kafka.

### Consumer

Reads messages from Kafka.

---

Next important questions are:

**Q5. What is Partition?** ⭐⭐⭐⭐⭐
**Q6. What is Offset?** ⭐⭐⭐⭐⭐
**Q7. Consumer Group?** ⭐⭐⭐⭐⭐
**Q8. Replication?** ⭐⭐⭐⭐⭐

These are asked in almost every Kafka interview for 5+ years experience.


--------------------------------------------------------------------------------------------------------------------------------------------

These 4 questions are the heart of Kafka. If you understand them properly, you can answer most Kafka interview questions confidently.

# Q5. What is a Partition?

## Beginner Understanding

A Topic is divided into multiple Partitions.

Example:

Topic = `order-created`

```text
order-created

Partition-0
Partition-1
Partition-2
```

Messages are stored inside partitions.

```text
Partition-0
------------
Order-1
Order-2
Order-3

Partition-1
------------
Order-4
Order-5

Partition-2
------------
Order-6
Order-7
```

---

## Why Partition?

If Kafka stored everything in one partition:

```text
Producer --> Topic --> Consumer
```

Only one consumer could process data efficiently.

With partitions:

```text
Producer
   |
   v

Topic
 |   |   |
 P0  P1  P2

 |   |   |
 C1  C2  C3
```

Multiple consumers can process data in parallel.

---

## Professional Interview Answer

A Partition is a physical subdivision of a Kafka topic that allows data to be distributed across multiple brokers and processed in parallel. Partitions enable scalability, load balancing, and high throughput.

---

## Interview Follow-Up

### Can ordering be guaranteed?

Answer:

Kafka guarantees ordering only within a partition, not across multiple partitions.

Example:

```text
Partition-0

Order-1
Order-2
Order-3
```

Order is preserved.

---

## Real Project Answer

> We used 12 partitions for high-volume order events so that multiple consumer instances could process messages concurrently and improve throughput.

---

# Q6. What is Offset?

## Beginner Understanding

Every message inside a partition gets a unique number.

That number is called Offset.

Example:

```text
Partition-0

Offset 0 -> Order-1
Offset 1 -> Order-2
Offset 2 -> Order-3
Offset 3 -> Order-4
```

Kafka identifies messages using offsets.

---

## Example

Consumer reads:

```text
Offset 0
Offset 1
Offset 2
```

If service crashes...

Kafka remembers:

```text
Last Read Offset = 2
```

After restart:

```text
Start From Offset = 3
```

No data loss.

---

## Professional Interview Answer

Offset is a unique sequential identifier assigned to each message within a partition. Kafka uses offsets to track message consumption and allow consumers to resume processing from a specific position.

---

## Interview Follow-Up

### Is Offset unique across Topic?

No.

Offsets are unique only within a partition.

Example:

```text
Partition-0
Offset 0

Partition-1
Offset 0
```

Both are valid.

---

## Real Project Answer

> Kafka stores consumer offsets so that in case of application restart, message consumption can resume from the last committed position.

---

# Q7. What is Consumer Group?

## Beginner Understanding

Multiple consumers can work together as a team.

That team is called a Consumer Group.

Example:

```text
Consumer Group

Consumer-1
Consumer-2
Consumer-3
```

---

## Topic Example

```text
Topic

Partition-0
Partition-1
Partition-2
```

Assignment:

```text
Consumer-1 -> Partition-0

Consumer-2 -> Partition-1

Consumer-3 -> Partition-2
```

Each consumer processes different partitions.

---

## Why Consumer Group?

Without Consumer Group:

```text
Consumer-1 reads all data

Consumer-2 reads all data

Consumer-3 reads all data
```

Duplicate processing.

With Consumer Group:

```text
Consumer-1 reads P0

Consumer-2 reads P1

Consumer-3 reads P2
```

Load distributed.

---

## Professional Interview Answer

A Consumer Group is a collection of consumers that work together to consume messages from a topic. Kafka ensures that each partition is consumed by only one consumer within the same group.

---

## Interview Trap Question

### Can two consumers read same partition?

Answer:

Inside the same Consumer Group → NO

Different Consumer Groups → YES

Example:

```text
Topic

Partition-0

Group-A Consumer-1

Group-B Consumer-1
```

Both groups can consume the same messages independently.

---

## Real Project Example

```text
Order Topic

Group-A -> Inventory Service

Group-B -> Notification Service

Group-C -> Analytics Service
```

Each group receives the same events.

---

# Q8. What is Replication?

## Beginner Understanding

Replication means keeping copies of partitions on multiple Kafka brokers.

Example:

```text
Broker-1
Partition-0 (Leader)

Broker-2
Partition-0 (Replica)

Broker-3
Partition-0 (Replica)
```

---

## Why Replication?

Suppose:

```text
Broker-1 crashes
```

Without replication:

```text
Data Lost
```

With replication:

```text
Broker-2 becomes Leader
```

System continues running.

---

## Professional Interview Answer

Replication is Kafka's fault-tolerance mechanism in which partition data is copied across multiple brokers. If the leader broker fails, one of the replica brokers becomes the new leader and continues serving requests.

---

## Important Terms

### Leader

Handles:

* Reads
* Writes

```text
Producer --> Leader
Consumer --> Leader
```

---

### Follower

Maintains a copy of data.

```text
Leader --> Follower
```

---

## Example

Replication Factor = 3

```text
Partition-0

Leader  -> Broker-1

Replica -> Broker-2

Replica -> Broker-3
```

Three copies exist.

---

## Interview Follow-Up

### What happens if Leader Broker goes down?

Answer:

Kafka automatically elects one of the in-sync replicas (ISR) as the new leader, ensuring continued availability.

---

## Real Project Answer

> In production we configured replication factor 3. This ensured fault tolerance because each partition existed on three brokers, allowing Kafka to survive broker failures without losing data.

# 30-Second Interview Revision

### Partition

A topic is divided into partitions for parallel processing and scalability.

### Offset

A unique sequence number identifying messages within a partition.

### Consumer Group

A group of consumers sharing the workload of consuming topic partitions.

### Replication

Maintaining multiple copies of partitions across brokers for fault tolerance and high availability.

---

The next most commonly asked Kafka interview questions are:

**Q9. What happens when a message is produced to Kafka?** ⭐⭐⭐⭐⭐
**Q10. What is ISR (In-Sync Replica)?** ⭐⭐⭐⭐⭐
**Q11. What is Rebalancing?** ⭐⭐⭐⭐⭐
**Q12. At-most-once, At-least-once, Exactly-once delivery?** ⭐⭐⭐⭐⭐

These are very common for Senior Java Developer interviews and often lead into real project discussions.
--------------------------------------------------------------------------------------------------------------------------------------------

Excellent! These questions are frequently asked when the interviewer wants to check whether you've actually worked with Kafka in projects.

# Q9. What Happens When a Message is Produced to Kafka?

## Beginner Understanding

Suppose Order Service sends an event:

```json
{
  "orderId": 101,
  "amount": 500
}
```

Producer sends it to Kafka.

### Step-by-Step Flow

```text
Producer
   |
   v
Topic
   |
Partition
   |
Kafka Broker
```

### Detailed Flow

1. Producer sends message.
2. Kafka determines which partition to use.
3. Message is written to the partition.
4. Kafka assigns an offset.
5. Leader broker stores the message.
6. Followers replicate the message.
7. Kafka sends acknowledgment (ACK) to producer.

---

## Professional Interview Answer

When a producer publishes a message, Kafka determines the target partition, writes the message to the leader partition, assigns an offset, replicates it to follower replicas according to the replication configuration, and returns an acknowledgment to the producer.

---

## Real Project Answer

> In our order-processing system, Order Service publishes events to Kafka. Kafka stores the event in the appropriate partition, replicates it across brokers, assigns an offset, and then consumers such as Inventory and Notification services process the event asynchronously.

---

# Q10. What is ISR (In-Sync Replica)?

## Beginner Understanding

Suppose:

```text
Partition-0

Broker-1 (Leader)

Broker-2 (Follower)

Broker-3 (Follower)
```

All followers are copying data properly.

```text
Leader ✔
Follower ✔
Follower ✔
```

These replicas are called ISR.

---

## What If One Replica Falls Behind?

```text
Broker-1 Leader

Broker-2 Follower ✔

Broker-3 Follower ❌
```

Broker-3 is not synchronized.

Kafka removes it from ISR.

```text
ISR

Broker-1
Broker-2
```

---

## Why ISR Matters?

Leader election happens only from ISR members.

This prevents data loss.

---

## Professional Interview Answer

ISR (In-Sync Replica) is the set of replicas that are fully synchronized with the leader partition. Kafka elects a new leader only from the ISR set to ensure data consistency and durability.

---

## Interview Follow-Up

### Can an out-of-sync replica become leader?

Answer:

No. Only replicas present in the ISR are eligible for leader election.

---

# Q11. What is Rebalancing?

## Beginner Understanding

Suppose:

Topic has:

```text
Partition-0
Partition-1
Partition-2
```

Consumer Group:

```text
Consumer-1
Consumer-2
```

Assignment:

```text
Consumer-1 -> P0,P1

Consumer-2 -> P2
```

---

### New Consumer Joins

```text
Consumer-1

Consumer-2

Consumer-3
```

Kafka redistributes partitions.

```text
Consumer-1 -> P0

Consumer-2 -> P1

Consumer-3 -> P2
```

This process is called Rebalancing.

---

### Another Example

Consumer-2 crashes.

Kafka again redistributes partitions among remaining consumers.

---

## Professional Interview Answer

Rebalancing is the process by which Kafka redistributes partition ownership among consumers in a consumer group when consumers join, leave, or fail.

---

## Why Is Rebalancing Important?

* Load balancing
* High availability
* Fault tolerance

---

## Interview Follow-Up

### Is rebalancing expensive?

Answer:

Yes. During rebalancing, consumers temporarily stop processing messages, which may increase latency.

---

## Real Project Answer

> We observed temporary message-processing pauses during consumer group rebalancing. To minimize impact, we used cooperative rebalancing and avoided unnecessary consumer restarts.

---

# Q12. What Are At-Most-Once, At-Least-Once, and Exactly-Once Delivery?

This is one of the most important Kafka interview questions.

---

# 1. At-Most-Once

## Meaning

Message may be lost.

No duplicate messages.

```text
Message Sent

Consumer Crashes

Message Lost
```

---

### Example

```text
Read Message

Commit Offset

Process Message
```

If processing fails after offset commit:

```text
Message Lost
```

---

### Interview Answer

At-most-once delivery guarantees that a message is delivered zero or one time. Duplicate messages do not occur, but message loss is possible.

---

# 2. At-Least-Once

## Meaning

Message is never lost.

Duplicates may occur.

```text
Message Processed

Crash Before Offset Commit
```

Kafka thinks message wasn't processed.

After restart:

```text
Read Same Message Again
```

Duplicate processing occurs.

---

### Example

```text
Process Message

Commit Offset
```

This is the most common approach.

---

### Interview Answer

At-least-once delivery guarantees that messages are not lost, but they may be delivered more than once, requiring consumers to handle duplicates.

---

## Real Project Example

Payment Event:

```text
Payment Success
```

Consumer should be idempotent:

```text
If payment already processed
Ignore duplicate
```

---

# 3. Exactly-Once

## Meaning

Message processed exactly one time.

No duplicates.

No data loss.

```text
1 Message

1 Processing

1 Result
```

---

## How Kafka Achieves It?

Using:

* Idempotent Producer
* Transactions
* Transactional Consumers

---

## Professional Interview Answer

Exactly-once semantics guarantee that a message is processed only once despite retries, failures, or network issues. Kafka achieves this using idempotent producers and transactional processing.

---

## Comparison Table

| Delivery Type | Data Loss | Duplicate |
| ------------- | --------- | --------- |
| At-Most-Once  | Possible  | No        |
| At-Least-Once | No        | Possible  |
| Exactly-Once  | No        | No        |

---

# Senior-Level Interview Question

### Which delivery guarantee is most commonly used in microservices?

Answer:

**At-Least-Once Delivery**

Reason:

* Easier implementation
* Better performance
* Reliable
* Consumer can handle duplicates using idempotency

---

# Real Project Answer (Very Strong)

> In our Spring Boot microservices architecture, we used Kafka with at-least-once delivery semantics. Consumers committed offsets only after successful processing, and business operations were designed to be idempotent to safely handle duplicate events. Replication factor was set to 3 for fault tolerance, and consumer groups were used for horizontal scaling.

These 4 questions (Q9–Q12) are among the most frequently asked Kafka topics for Java developers with 5+ years of experience. Next, interviewers often move to:

* Kafka vs RabbitMQ
* Kafka Message Ordering
* Retention Policy
* Dead Letter Topic (DLT)
* Retry Mechanism
* Spring Boot Kafka Integration
* Real project troubleshooting scenarios.
------------------------------------------------------------------------------------------------------------------------------------------

These are **very important Senior Java Developer Kafka interview questions**. Let's cover them one by one.

# Q13. Kafka vs RabbitMQ

## Simple Understanding

### Kafka

Think of Kafka as a huge event storage system.

```text
Producer
   |
   v
 Kafka
   |
   +--> Consumer 1
   |
   +--> Consumer 2
```

Messages remain stored even after consumption.

---

### RabbitMQ

Think of RabbitMQ as a post office.

```text
Producer
   |
 RabbitMQ
   |
 Consumer
```

Message is typically removed after successful consumption.

---

## Comparison Table

| Feature         | Kafka           | RabbitMQ       |
| --------------- | --------------- | -------------- |
| Architecture    | Distributed Log | Message Broker |
| Throughput      | Very High       | Medium         |
| Data Retention  | Yes             | Usually No     |
| Replay Messages | Yes             | Difficult      |
| Scalability     | Excellent       | Good           |
| Ordering        | Per Partition   | Queue Based    |
| Best For        | Event Streaming | Task Queues    |

---

## Interview Answer

> Kafka is designed for high-throughput event streaming and long-term message retention, whereas RabbitMQ is designed for reliable message delivery and complex routing scenarios.

---

# Q14. Kafka Message Ordering

## Beginner Understanding

Kafka guarantees ordering only inside a partition.

Example:

```text
Partition-0

Order-1
Order-2
Order-3
Order-4
```

Order is preserved.

---

## Multiple Partitions

```text
Partition-0
Order-1
Order-3

Partition-1
Order-2
Order-4
```

Global ordering is not guaranteed.

---

## How To Maintain Ordering?

Use a key.

Example:

```java
kafkaTemplate.send(
   "orders",
   customerId,
   orderEvent
);
```

Same key always goes to same partition.

---

## Interview Answer

> Kafka guarantees message ordering within a partition. To preserve ordering for related events, the same key should be used so messages are routed to the same partition.

---

# Q15. What is Retention Policy?

## Beginner Understanding

Kafka keeps messages even after consumers read them.

Example:

```text
Topic

Message-1
Message-2
Message-3
```

Consumers read them.

Messages still remain.

---

## Why?

New consumers may need old data.

---

## Retention Types

### Time Based

```properties
retention.ms=604800000
```

7 Days

---

### Size Based

```properties
retention.bytes=1073741824
```

1 GB

---

## Interview Answer

> Retention policy determines how long Kafka retains messages in a topic. Retention can be configured based on time or storage size regardless of whether messages have been consumed.

---

# Q16. What is Dead Letter Topic (DLT)?

## Beginner Understanding

Suppose consumer receives:

```json
{
  "userId": null
}
```

Application throws exception.

---

Without DLT:

```text
Consumer
   |
Exception
   |
Retry Forever
```

System gets stuck.

---

With DLT:

```text
Consumer
   |
Failure
   |
Dead Letter Topic
```

Bad messages move to DLT.

---

## Example

```text
order-topic

failed-order-topic
```

---

## Interview Answer

> A Dead Letter Topic stores messages that cannot be processed successfully after configured retry attempts. It prevents problematic messages from blocking normal processing.

---

## Real Project Answer

> We configured DLT for order-processing events. After three failed retries, messages were redirected to a dead-letter topic for investigation and reprocessing.

---

# Q17. What is Retry Mechanism?

## Problem

Consumer receives message.

```text
Order Event
```

Database is temporarily down.

Consumer fails.

---

## Solution

Retry.

```text
Try 1 -> Failed

Try 2 -> Failed

Try 3 -> Success
```

---

## Spring Kafka Example

```java
@RetryableTopic(
    attempts = "3"
)
@KafkaListener(
    topics = "orders"
)
public void consume(OrderEvent event) {

}
```

---

## Retry Flow

```text
Main Topic

     |
     v

Retry Topic

     |
     v

DLT
```

---

## Interview Answer

> Retry mechanisms allow transient failures such as network issues or temporary database outages to be handled automatically before moving messages to a dead-letter topic.

---

# Q18. Spring Boot Kafka Integration

## Dependency

```xml
<dependency>
   <groupId>
      org.springframework.kafka
   </groupId>
   <artifactId>
      spring-kafka
   </artifactId>
</dependency>
```

---

## Producer Configuration

```properties
spring.kafka.bootstrap-servers=localhost:9092
```

---

## Producer

```java
@Service
public class OrderProducer {

    @Autowired
    private KafkaTemplate<String,Object> kafkaTemplate;

    public void send(OrderEvent event){

        kafkaTemplate.send(
            "order-topic",
            event
        );
    }
}
```

---

## Consumer

```java
@KafkaListener(
    topics="order-topic",
    groupId="inventory-group"
)
public void consume(OrderEvent event){

    System.out.println(event);
}
```

---

## Interview Answer

> Spring Boot integrates with Kafka using Spring Kafka. KafkaTemplate is used for producing messages, while @KafkaListener is used for consuming messages from topics.

---

# Real Project Troubleshooting Scenarios

These questions are frequently asked to 5+ year developers.

---

# Scenario 1

## Consumer Is Not Receiving Messages

### Possible Reasons

1. Wrong topic name
2. Wrong bootstrap server
3. Consumer group issue
4. Deserialization error
5. Network issue

### Interview Answer

> I would verify topic existence, consumer group assignment, offsets, listener configuration, deserializer settings, and broker connectivity.

---

# Scenario 2

## Duplicate Messages Are Being Processed

### Reason

```text
Message Processed

Consumer Crashed

Offset Not Committed
```

Kafka sends message again.

---

### Solution

Implement idempotency.

Example:

```text
OrderId=1001

Already Processed?
```

Ignore duplicate.

---

## Interview Answer

> Duplicate messages are expected with at-least-once delivery. We handle them using idempotent processing and unique business keys.

---

# Scenario 3

## Consumer Lag Increasing

### Meaning

Producer is faster than Consumer.

```text
Producer ---> 1000 msg/sec

Consumer ---> 100 msg/sec
```

Lag increases.

---

### Solutions

* Increase partitions
* Increase consumer instances
* Optimize processing
* Batch processing

---

## Interview Answer

> I would monitor consumer lag, scale consumers horizontally, increase partition count if necessary, and optimize processing logic.

---

# Scenario 4

## Broker Failure

### Situation

```text
Broker-1 Down
```

---

### Solution

Replication.

```text
Broker-2

Becomes Leader
```

---

## Interview Answer

> Kafka automatically elects a new leader from the ISR set, ensuring service continuity without data loss when replication is configured correctly.

---

# Scenario 5

## Messages Arrive Out of Order

### Reason

Multiple Partitions

```text
Partition-0

Partition-1
```

---

### Solution

Use Key-Based Partitioning.

```java
kafkaTemplate.send(
   "orders",
   customerId,
   event
);
```

---

## Interview Answer

> Kafka guarantees ordering only within a partition. To preserve ordering, related events should use the same partition key.

---

# Senior-Level Project Answer (Memorize)

> In my Spring Boot microservices project, Kafka was used for asynchronous communication between services. Producers published events such as order creation and payment completion. Consumers processed these events using consumer groups for scalability. We configured a replication factor of 3 for fault tolerance, implemented retry topics and dead-letter topics for error handling, monitored consumer lag, and used idempotent consumers to safely handle duplicate messages under at-least-once delivery semantics.

This is the level of answer that impresses interviewers for a **6+ years Java Developer** role.

------------------------------------------------------------------------------------------------------------------------------------------
Great! Interviewers often ask **Kafka tricky questions** to check whether you really understand Kafka or have only memorized definitions.

# 1. Can Multiple Consumers Read the Same Partition?

### Answer

**Yes and No.**

✅ Different Consumer Groups → YES

❌ Same Consumer Group → NO

Example:

```text
Topic
 |
Partition-0

Group-A Consumer-1
Group-B Consumer-1
```

Both can read.

But:

```text
Group-A

Consumer-1
Consumer-2
```

Only one consumer gets Partition-0.

---

# 2. Can Number of Consumers Be Greater Than Number of Partitions?

### Answer

Yes.

Example:

```text
Partitions = 3

Consumers = 5
```

```text
Consumer-1 -> P0
Consumer-2 -> P1
Consumer-3 -> P2
Consumer-4 -> Idle
Consumer-5 -> Idle
```

Extra consumers remain idle.

---

# 3. Can Number of Partitions Be Greater Than Consumers?

### Answer

Yes.

```text
Partitions = 10

Consumers = 2
```

```text
Consumer-1 -> P0,P1,P2,P3,P4

Consumer-2 -> P5,P6,P7,P8,P9
```

Very common in production.

---

# 4. If Consumer Reads Message, Is Message Deleted?

### Answer

No.

Kafka keeps messages according to retention policy.

This is one of the biggest differences from RabbitMQ.

---

# 5. Can Kafka Lose Messages?

### Answer

Yes, if configured incorrectly.

Examples:

* Replication factor = 1
* Broker crashes before replication
* Producer ACK configuration is weak

Production usually uses:

```properties
acks=all
replication.factor=3
```

---

# 6. Why Do We Need Offset If Kafka Already Stores Messages?

### Answer

Offset tracks where the consumer has reached.

Example:

```text
Offset-0
Offset-1
Offset-2
Offset-3
```

Consumer processed till:

```text
Offset-2
```

After restart:

```text
Start from Offset-3
```

---

# 7. Can Offset Be Changed Manually?

### Answer

Yes.

Consumer can:

* Reset offset
* Move backward
* Reprocess old data

Example:

```bash
kafka-consumer-groups \
--reset-offsets
```

---

# 8. What Happens If Consumer Crashes?

### Answer

Kafka triggers Rebalancing.

Example:

Before:

```text
Consumer-1 -> P0

Consumer-2 -> P1
```

Consumer-2 crashes.

After:

```text
Consumer-1 -> P0,P1
```

---

# 9. What Happens If Broker Crashes?

### Answer

Replica becomes Leader.

Example:

```text
Broker-1 Leader

Broker-2 Replica
Broker-3 Replica
```

Broker-1 down:

```text
Broker-2 becomes Leader
```

---

# 10. Does Kafka Guarantee Exactly Once?

### Answer

Not by default.

Default:

```text
At-Least-Once
```

For Exactly Once:

* Idempotent Producer
* Transactions

must be enabled.

---

# 11. Is Ordering Guaranteed in Kafka?

### Answer

Only inside a partition.

Not across partitions.

Interviewers love this question.

---

# 12. How Do You Maintain Ordering?

### Answer

Use the same key.

Example:

```java
kafkaTemplate.send(
   "orders",
   customerId,
   event
);
```

Same key → Same partition → Order maintained.

---

# 13. Why Not Use Database Instead of Kafka?

### Answer

Database:

```text
Store Data
```

Kafka:

```text
Stream Events
```

Kafka handles:

* Millions of events
* Real-time processing
* Multiple consumers

efficiently.

---

# 14. Why Not Use REST API Instead of Kafka?

### REST

```text
Order Service
      |
      v
Payment Service
```

If Payment Service is down:

```text
Request Fails
```

### Kafka

```text
Order Service
      |
      v
Kafka
      |
Payment Service
```

Payment Service can consume later.

---

# 15. What Is Consumer Lag?

### Answer

Difference between:

```text
Latest Offset
-
Consumed Offset
```

Example:

```text
Latest = 1000

Consumed = 900
```

Lag:

```text
100
```

---

# 16. Can We Increase Partitions Later?

### Answer

Yes.

But be careful.

Example:

```text
Before = 3 partitions

After = 6 partitions
```

Message distribution changes.

Ordering may be affected.

---

# 17. Can We Reduce Partitions?

### Answer

No.

Kafka allows increasing partitions.

Reducing partitions is not supported directly.

This is a common tricky question.

---

# 18. What Happens If Retention Expires Before Consumer Reads Data?

### Answer

Messages are deleted.

Consumer cannot read them anymore.

---

# 19. What Is the Difference Between Leader and ISR?

### Leader

Handles:

```text
Read
Write
```

### ISR

Replicas synchronized with Leader.

```text
Leader
Replica-1
Replica-2
```

All synced = ISR.

---

# 20. Most Asked Senior-Level Kafka Question

### Why Is Kafka Faster Than Traditional Messaging Systems?

### Answer

Because Kafka uses:

* Sequential Disk Writes
* Partitioning
* Batch Processing
* Zero Copy Transfer
* Pull-Based Consumption

which provide extremely high throughput.

---

## One-Liner Answers to Memorize

**Can two consumers consume the same partition in same group?**

→ No.

**Can two consumer groups consume same topic?**

→ Yes.

**Can Kafka guarantee ordering?**

→ Only within a partition.

**Can partitions be decreased?**

→ No.

**Can messages be replayed?**

→ Yes.

**Can Kafka work without consumers?**

→ Yes.

**What causes duplicate messages?**

→ At-least-once delivery.

**What causes rebalancing?**

→ Consumer join, leave, or crash.

**What is consumer lag?**

→ Difference between latest offset and consumed offset.

These are exactly the kind of Kafka questions that often appear in 5–8 years Java/Spring Boot interviews.
