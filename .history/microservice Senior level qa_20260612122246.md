## **3. MICROSERVICES**

## ✅ 81. What are Microservices? Advantages and disadvantages?

**Microservices are an architectural style where applications are built as a collection of small, independent services that communicate over well-defined APIs, each running in its own process and often deployed independently.**

### 🔹 Definition of Microservices

**Small, autonomous services**

Microservices architecture breaks down large applications into smaller, loosely coupled services. Each service:

- Is responsible for a specific business capability
- Can be developed, deployed, and scaled independently
- Communicates with other services via APIs
- Has its own database (database per service pattern)

```java
// Example: User Service (Spring Boot microservice)
@SpringBootApplication
@RestController
public class UserServiceApplication {
    
    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Long id) {
        return userRepository.findById(id).orElseThrow();
    }
    
    // Other user-related endpoints
}
```

### 🔹 Key Characteristics

**Independent deployment and scaling**

- **Single Responsibility**: Each service handles one business domain
- **Independent Deployment**: Services can be updated without affecting others
- **Technology Diversity**: Different services can use different technologies
- **Resilient**: Failure in one service doesn't bring down the entire system
- **Scalable**: Individual services can be scaled based on demand

### 🔹 Advantages

**Benefits of microservices architecture**

1. **Scalability**: Scale individual services based on load
2. **Technology Flexibility**: Choose best technology for each service
3. **Fault Isolation**: Issues in one service don't affect others
4. **Faster Deployment**: Smaller codebases deploy quicker
5. **Team Autonomy**: Teams can work independently on different services
6. **Easier Maintenance**: Smaller, focused codebases are easier to maintain

### 🔹 Disadvantages

**Challenges and complexities**

1. **Increased Complexity**: Distributed systems are inherently complex
2. **Network Latency**: Inter-service communication adds overhead
3. **Data Consistency**: Maintaining consistency across services is challenging
4. **Testing Difficulty**: End-to-end testing becomes more complex
5. **Operational Overhead**: More services mean more deployment and monitoring
6. **Debugging Challenges**: Tracing issues across multiple services is harder

### 🔹 When to Use Microservices

**Appropriate use cases**

- Large, complex applications with multiple business domains
- Teams that need to work independently
- Applications requiring high scalability
- Systems with diverse technology requirements
- Organizations practicing DevOps and continuous deployment

### 🔹 Best Practices

```java
// Use API versioning
// Implement proper logging and monitoring
// Use circuit breakers for resilience
// Implement service discovery
// Use centralized configuration
// Design for failure
// Use event-driven communication where appropriate
```

---

## 🎯 Interview One-Liner

> Microservices: small, independent services communicating via APIs; advantages: scalability, flexibility, fault isolation; disadvantages: complexity, distributed challenges.

---

## ✅ 82. Monolithic vs Microservices architecture

**Monolithic architecture builds applications as a single, unified unit, while microservices architecture decomposes applications into small, independent services that communicate via APIs.**

### 🔹 Monolithic Architecture

**Single deployable unit**

Traditional approach where all components are tightly coupled:

```java
// Monolithic Spring Boot Application
@SpringBootApplication
public class EcommerceApplication {
    
    // All functionality in one application
    @RestController
    public class UserController { /* user endpoints */ }
    
    @RestController  
    public class ProductController { /* product endpoints */ }
    
    @RestController
    public class OrderController { /* order endpoints */ }
    
    // All services, repositories in same codebase
}
```

**Characteristics:**
- Single codebase and deployment unit
- Shared database
- Tight coupling between components
- All-or-nothing scaling
- Simple development initially

### 🔹 Microservices Architecture

**Distributed, independent services**

Application broken into small, focused services:

```java
// User Service
@SpringBootApplication
@RestController
public class UserServiceApplication {
    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Long id) { /* ... */ }
}

// Product Service  
@SpringBootApplication
@RestController
public class ProductServiceApplication {
    @GetMapping("/products/{id}")
    public Product getProduct(@PathVariable Long id) { /* ... */ }
}

// Order Service
@SpringBootApplication
@RestController
public class OrderServiceApplication {
    @PostMapping("/orders")
    public Order createOrder(@RequestBody OrderRequest request) { /* ... */ }
}
```

**Characteristics:**
- Independent services with separate databases
- Loose coupling via APIs
- Independent deployment and scaling
- Complex inter-service communication

### 🔹 Key Differences

| Aspect | Monolithic | Microservices |
|--------|------------|---------------|
| **Deployment** | Single unit | Multiple independent units |
| **Scaling** | Scale entire application | Scale individual services |
| **Technology** | Single tech stack | Polyglot (multiple technologies) |
| **Database** | Shared database | Database per service |
| **Coupling** | Tight coupling | Loose coupling |
| **Development** | Single team | Multiple teams |
| **Fault Tolerance** | Single point of failure | Isolated failures |
| **Testing** | Simple unit/integration | Complex distributed testing |
| **Maintenance** | Harder for large apps | Easier for focused services |

### 🔹 Migration Strategy

**Moving from monolithic to microservices**

1. **Strangler Pattern**: Gradually replace monolithic parts with microservices
2. **Identify Boundaries**: Use domain-driven design to find service boundaries
3. **Start Small**: Begin with less critical functionality
4. **API Gateway**: Implement gateway for unified access
5. **Service Discovery**: Enable services to find each other
6. **Monitoring**: Implement comprehensive monitoring from day one

### 🔹 When to Choose Each

**Decision criteria**

- **Monolithic**: Small applications, simple requirements, small teams
- **Microservices**: Large applications, complex domains, large teams, high scalability needs

### 🔹 Best Practices

```java
// Start with monolithic if unsure
// Use domain-driven design for boundaries
// Implement proper API contracts
// Use event-driven communication
// Implement circuit breakers and retries
// Monitor everything
// Use containers (Docker) for deployment
```

---

## 🎯 Interview One-Liner

> Monolithic: single unit, tight coupling, shared DB; Microservices: independent services, loose coupling, separate DBs; choose based on scale and complexity.

---

## ✅ 83. What is Domain-Driven Design (DDD)?

**Domain-Driven Design (DDD) is an approach to software development that focuses on modeling software around the business domain, using ubiquitous language, bounded contexts, and domain entities to create maintainable and scalable applications.**

### 🔹 Core Concepts of DDD

**Business-focused development**

DDD emphasizes understanding and modeling the business domain:

- **Ubiquitous Language**: Common language used by developers and domain experts
- **Bounded Contexts**: Boundaries within which a particular domain model applies
- **Domain Model**: Representation of business concepts and rules
- **Entities**: Objects with identity that change over time
- **Value Objects**: Immutable objects without identity
- **Aggregates**: Clusters of domain objects treated as a unit

### 🔹 Strategic Design

**High-level domain modeling**

Strategic patterns for large systems:

```java
// Bounded Context Example
public class OrderContext {
    // Order-related entities, value objects, aggregates
    public class Order { /* Order entity */ }
    public class OrderItem { /* Value object */ }
    public class Customer { /* Reference to customer context */ }
}

public class CustomerContext {
    // Customer-related domain objects
    public class Customer { /* Customer entity */ }
    public class Address { /* Value object */ }
}
```

### 🔹 Tactical Design

**Implementation patterns**

Building blocks for domain models:

```java
// Entity with identity
@Entity
public class Order {
    @Id
    private Long id;
    private OrderStatus status;
    
    public void confirm() {
        if (status != OrderStatus.PENDING) {
            throw new IllegalStateException("Can only confirm pending orders");
        }
        this.status = OrderStatus.CONFIRMED;
    }
}

// Value Object (immutable)
@Embeddable
public class Money {
    private BigDecimal amount;
    private Currency currency;
    
    public Money add(Money other) {
        // Business logic for money addition
        return new Money(this.amount.add(other.amount), this.currency);
    }
}

// Aggregate Root
@Entity
public class ShoppingCart {
    @Id
    private Long id;
    
    @OneToMany(cascade = CascadeType.ALL)
    private List<CartItem> items = new ArrayList<>();
    
    public void addItem(Product product, int quantity) {
        // Business logic for adding items
        items.add(new CartItem(product, quantity));
    }
}
```

### 🔹 Domain Services

**Business logic that doesn't belong to entities**

```java
@Service
public class OrderService {
    
    public Order createOrder(Customer customer, ShoppingCart cart) {
        // Complex business logic for order creation
        validateCustomer(customer);
        validateCart(cart);
        applyDiscounts(cart);
        
        Order order = new Order(customer, cart.getItems());
        return orderRepository.save(order);
    }
}
```

### 🔹 Repository Pattern

**Abstracting data access**

```java
public interface OrderRepository {
    Order findById(Long id);
    List<Order> findByCustomer(Customer customer);
    Order save(Order order);
}

// Implementation
@Repository
public class JpaOrderRepository implements OrderRepository {
    // JPA implementation
}
```

### 🔹 Application Services

**Orchestrating domain operations**

```java
@Service
@Transactional
public class OrderApplicationService {
    
    public OrderDto createOrder(CreateOrderCommand command) {
        Customer customer = customerRepository.findById(command.getCustomerId());
        ShoppingCart cart = cartRepository.findById(command.getCartId());
        
        Order order = orderService.createOrder(customer, cart);
        return orderMapper.toDto(order);
    }
}
```

### 🔹 Benefits of DDD

**Why use DDD**

- Better alignment with business requirements
- More maintainable code
- Easier to evolve with changing business needs
- Better communication between technical and business teams
- Scalable architecture patterns

### 🔹 When to Use DDD

**Appropriate scenarios**

- Complex business domains
- Large applications with multiple teams
- Systems requiring high maintainability
- Projects with significant business logic
- Applications expected to evolve over time

### 🔹 Best Practices

```java
// Collaborate with domain experts
// Use ubiquitous language in code and discussions
// Keep bounded contexts clear and separate
// Focus on business value, not technical concerns
// Use aggregates to maintain consistency
// Implement proper validation in domain objects
// Test domain logic thoroughly
```

---

## 🎯 Interview One-Liner

> DDD: model software around business domain using ubiquitous language, bounded contexts, entities, value objects, aggregates for maintainable, scalable applications.

---

## ✅ 84. What is bounded context?

**Bounded Context is a central concept in Domain-Driven Design (DDD) that defines the boundaries within which a particular domain model is applicable, ensuring that terms and concepts have consistent meaning within those boundaries.**

### 🔹 Definition

**Contextual boundaries for domain models**

A bounded context represents:

- A boundary within which a domain model is defined and applicable
- A linguistic boundary where terms have specific meanings
- A boundary for team responsibility and code ownership
- A unit of deployment and scaling

### 🔹 Why Bounded Contexts Matter

**Managing complexity in large systems**

Without bounded contexts, large systems suffer from:

- **Ubiquitous Language Pollution**: Same terms mean different things in different parts
- **Model Conflicts**: Inconsistent domain models across the system
- **Tight Coupling**: Changes in one area affect others unexpectedly
- **Team Conflicts**: Different teams interpret requirements differently

### 🔹 Example: E-commerce System

**Different contexts, different meanings**

```java
// Sales Context (Order Processing)
public class Order {
    private Customer customer;
    private List<OrderItem> items;
    private Payment payment;
    
    public void place() { /* sales logic */ }
}

// Shipping Context (Delivery)
public class Order {
    private String trackingNumber;
    private Address deliveryAddress;
    private ShippingMethod method;
    
    public void ship() { /* shipping logic */ }
}

// Customer Service Context (Support)
public class Order {
    private String orderNumber;
    private CustomerIssue issue;
    private SupportTicket ticket;
    
    public void escalate() { /* support logic */ }
}
```

### 🔹 Context Mapping

**Relationships between bounded contexts**

Common patterns:

- **Shared Kernel**: Shared subset of domain model
- **Customer-Supplier**: Upstream-downstream relationship
- **Conformist**: Downstream conforms to upstream model
- **Anti-Corruption Layer**: Protects from external model changes
- **Separate Ways**: Completely independent contexts

### 🔹 Implementing Bounded Contexts

**Technical boundaries**

```java
// Separate packages/namespaces
package com.ecommerce.sales;
package com.ecommerce.shipping;
package com.ecommerce.customerservice;

// Separate databases
@Configuration
public class SalesDataSourceConfig {
    // Sales database configuration
}

@Configuration  
public class ShippingDataSourceConfig {
    // Shipping database configuration
}

// Separate services
@Service
public class SalesOrderService {
    // Sales-specific business logic
}

@Service
public class ShippingOrderService {
    // Shipping-specific business logic
}
```

### 🔹 Context Map Visualization

**Mapping relationships**

```
[Sales Context] ----(Customer-Supplier)----> [Inventory Context]
    |                                           |
    | (Shared Kernel)                           |
    v                                           v
[Customer Context] <---(Anti-Corruption Layer)--- [Legacy System]
```

### 🔹 Communication Between Contexts

**Inter-context integration**

```java
// Anti-Corruption Layer
@Service
public class LegacyOrderAdapter {
    
    public Order adaptLegacyOrder(LegacyOrder legacy) {
        return new Order(
            legacy.getCustomerId(),
            mapLegacyItems(legacy.getItems()),
            legacy.getTotal()
        );
    }
}

// Event-driven communication
@Service
public class OrderEventPublisher {
    
    public void publishOrderPlaced(Order order) {
        OrderPlacedEvent event = new OrderPlacedEvent(order);
        eventPublisher.publish(event);
    }
}
```

### 🔹 Identifying Bounded Contexts

**Discovery strategies**

1. **Business Capabilities**: Natural business boundaries
2. **Team Organization**: Existing team structures
3. **Existing Systems**: Legacy system boundaries
4. **Change Frequency**: Areas that change together
5. **Security Requirements**: Different security domains

### 🔹 Benefits

**Advantages of bounded contexts**

- Clear responsibility boundaries
- Consistent domain models within contexts
- Independent evolution of different parts
- Better team autonomy
- Reduced coupling between domains
- Easier testing and maintenance

### 🔹 Best Practices

```java
// Define clear context boundaries
// Use ubiquitous language within each context
// Implement context mapping patterns
// Use events for inter-context communication
// Keep contexts as independent as possible
// Document context relationships
// Regularly review and refine boundaries
```

---

## 🎯 Interview One-Liner

> Bounded Context: DDD boundary where domain model applies consistently; prevents language pollution, enables independent evolution of business domains.

---

## ✅ 85. How to decompose a monolith into microservices?

**Decomposing a monolithic application into microservices involves identifying business domains, establishing bounded contexts, and gradually extracting services while maintaining system functionality and data consistency.**

### 🔹 Step-by-Step Decomposition Process

**Systematic approach to microservices migration**

### 🔹 1. Domain Analysis

**Understand the business domain**

```java
// Identify business capabilities
// User Management, Product Catalog, Order Processing, Payment, Shipping, Inventory

// Analyze current monolithic structure
@SpringBootApplication
public class EcommerceMonolith {
    // All functionality mixed together
    @RestController public class UserController {}
    @RestController public class ProductController {}
    @RestController public class OrderController {}
    @RestController public class PaymentController {}
}
```

### 🔹 2. Identify Bounded Contexts

**Define domain boundaries**

Using Domain-Driven Design principles:

```java
// Bounded Context: User Management
public class UserContext {
    // User-related entities, services, repositories
}

// Bounded Context: Product Catalog  
public class ProductContext {
    // Product-related domain objects
}

// Bounded Context: Order Processing
public class OrderContext {
    // Order-related business logic
}
```

### 🔹 3. Define Service Boundaries

**Determine service responsibilities**

```java
// User Service API
@RestController
@RequestMapping("/users")
public class UserController {
    @PostMapping public User createUser(@RequestBody UserRequest request) {}
    @GetMapping("/{id}") public User getUser(@PathVariable Long id) {}
}

// Product Service API
@RestController
@RequestMapping("/products") 
public class ProductController {
    @GetMapping("/{id}") public Product getProduct(@PathVariable Long id) {}
    @PutMapping("/{id}/stock") public void updateStock(@PathVariable Long id, @RequestParam int stock) {}
}
```

### 🔹 4. Database Decomposition

**Separate databases per service**

```java
// Before: Shared database
@Configuration
public class MonolithDataSourceConfig {
    // Single database for everything
}

// After: Database per service
@Configuration
public class UserDataSourceConfig {
    @Bean
    public DataSource userDataSource() {
        return DataSourceBuilder.create()
            .url("jdbc:mysql://localhost:3306/userdb")
            .build();
    }
}

@Configuration
public class ProductDataSourceConfig {
    @Bean  
    public DataSource productDataSource() {
        return DataSourceBuilder.create()
            .url("jdbc:mysql://localhost:3306/productdb")
            .build();
    }
}
```

### 🔹 5. Implement API Gateway

**Unified entry point**

```java
// Spring Cloud Gateway configuration
@Configuration
public class GatewayConfig {
    
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("user-service", r -> r.path("/users/**")
                .uri("lb://user-service"))
            .route("product-service", r -> r.path("/products/**") 
                .uri("lb://product-service"))
            .build();
    }
}
```

### 🔹 6. Service Communication

**Inter-service communication patterns**

```java
// Synchronous communication with Feign
@FeignClient("user-service")
public interface UserServiceClient {
    @GetMapping("/users/{id}")
    User getUser(@PathVariable Long id);
}

// Usage in Order Service
@Service
public class OrderService {
    
    @Autowired
    private UserServiceClient userClient;
    
    public Order createOrder(Long userId, List<OrderItem> items) {
        User user = userClient.getUser(userId);
        // Create order logic
    }
}
```

### 🔹 7. Handle Data Consistency

**Managing distributed data**

```java
// Event-driven approach
@Service
public class OrderEventPublisher {
    
    @Autowired
    private ApplicationEventPublisher publisher;
    
    @Transactional
    public void placeOrder(Order order) {
        orderRepository.save(order);
        publisher.publishEvent(new OrderPlacedEvent(order));
    }
}

// Event handler in Inventory Service
@Component
public class OrderPlacedEventHandler {
    
    @EventListener
    public void handleOrderPlaced(OrderPlacedEvent event) {
        // Update inventory
        inventoryService.reserveItems(event.getOrder().getItems());
    }
}
```

### 🔹 8. Migration Strategies

**Safe transition approaches**

### 🔹 Strangler Pattern

**Gradually replace functionality**

```java
// Phase 1: Proxy to monolith
@RestController
public class ProductProxyController {
    
    @GetMapping("/products/{id}")
    public Product getProduct(@PathVariable Long id) {
        // Forward to monolithic service
        return restTemplate.getForObject(
            "http://monolith/products/" + id, Product.class);
    }
}

// Phase 2: Implement new service
@SpringBootApplication
public class ProductServiceApplication {
    // New microservice implementation
}
```

### 🔹 Parallel Run

**Run both systems simultaneously**

```java
// Dual write strategy
@Service
public class OrderService {
    
    @Transactional
    public Order createOrder(OrderRequest request) {
        // Write to new microservice database
        Order order = orderRepository.save(request.toOrder());
        
        // Also write to monolithic database for rollback
        legacyOrderService.createLegacyOrder(request);
        
        return order;
    }
}
```

### 🔹 9. Testing Strategy

**Comprehensive testing approach**

```java
// Contract testing with Spring Cloud Contract
@AutoConfigureStubRunner
public class OrderServiceContractTest {
    
    @Autowired
    private OrderService orderService;
    
    @Test
    public void shouldCreateOrder() {
        // Test against contract stubs
    }
}

// Integration testing
@SpringBootTest
public class OrderServiceIntegrationTest {
    
    @Autowired
    private OrderService orderService;
    
    @Test
    public void shouldCreateOrderWithUserService() {
        // Test with real service dependencies
    }
}
```

### 🔹 10. Monitoring and Observability

**Essential for distributed systems**

```java
// Distributed tracing with Sleuth
@SpringBootApplication
public class OrderServiceApplication {
    // Sleuth automatically adds trace IDs
}

// Centralized logging
@Configuration
public class LoggingConfig {
    
    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        return filter;
    }
}
```

### 🔹 Common Challenges

**Issues to anticipate**

- **Data Consistency**: Eventual consistency vs strong consistency
- **Service Discovery**: How services find each other
- **Network Failures**: Handling timeouts and retries
- **Debugging**: Tracing requests across services
- **Deployment Complexity**: Managing multiple services

### 🔹 Success Metrics

**Measuring decomposition success**

- Service independence (deployment frequency)
- Team autonomy (feature delivery speed)
- System resilience (downtime reduction)
- Scalability (resource utilization)
- Maintainability (bug fix speed)

### 🔹 Best Practices

```java
// Start with domain analysis
// Use DDD for boundary identification
// Begin with less critical services
// Implement proper monitoring from start
// Use feature flags for gradual rollout
// Maintain backward compatibility
// Document service contracts clearly
// Plan for failure scenarios
```

---

## 🎯 Interview One-Liner

> Decompose monolith by identifying bounded contexts, extracting services gradually using strangler pattern, implementing API gateway, handling data consistency with events.

---

## ✅ 86. Explain API Gateway pattern

**API Gateway is a service that acts as a single entry point for all client requests to a microservices architecture, providing routing, composition, and cross-cutting concerns like authentication, rate limiting, and monitoring.**

### 🔹 What is API Gateway?

**Single point of entry**

The API Gateway:

- Routes requests to appropriate microservices
- Aggregates responses from multiple services
- Handles cross-cutting concerns (auth, logging, rate limiting)
- Provides unified API to clients
- Can transform requests/responses

```java
// Spring Cloud Gateway example
@Configuration
public class GatewayConfig {
    
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("user-service", r -> r.path("/api/users/**")
                .filters(f -> f.stripPrefix(1))
                .uri("lb://user-service"))
            .route("product-service", r -> r.path("/api/products/**")
                .filters(f -> f.stripPrefix(1))
                .uri("lb://product-service"))
            .build();
    }
}
```

### 🔹 Key Responsibilities

**Gateway functions**

1. **Request Routing**: Direct requests to correct services
2. **Load Balancing**: Distribute load across service instances
3. **Authentication/Authorization**: Validate requests
4. **Rate Limiting**: Control request frequency
5. **Caching**: Cache responses for performance
6. **Request/Response Transformation**: Modify payloads
7. **Logging/Monitoring**: Track requests and responses
8. **Circuit Breaking**: Handle service failures

### 🔹 Request Routing

**Intelligent routing logic**

```java
@Configuration
public class RoutingConfig {
    
    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
            // Path-based routing
            .route("api-route", r -> r.path("/api/**")
                .uri("lb://api-service"))
                
            // Header-based routing
            .route("mobile-route", r -> r.header("X-Client-Type", "mobile")
                .uri("lb://mobile-service"))
                
            // Method-based routing
            .route("read-route", r -> r.method("GET")
                .uri("lb://read-service"))
                
            .route("write-route", r -> r.method("POST", "PUT", "DELETE")
                .uri("lb://write-service"))
            .build();
    }
}
```

### 🔹 Cross-Cutting Concerns

**Common functionality**

### 🔹 Authentication Filter

```java
@Component
public class AuthenticationFilter implements GlobalFilter, Ordered {
    
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
        
        if (!isValidToken(token)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        
        return chain.filter(exchange);
    }
}
```

### 🔹 Rate Limiting

```java
@Configuration
public class RateLimitConfig {
    
    @Bean
    public RouteLocator rateLimitedRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("rate-limited", r -> r.path("/api/**")
                .filters(f -> f.requestRateLimiter(c -> c.setRateLimiter(redisRateLimiter())))
                .uri("lb://api-service"))
            .build();
    }
    
    @Bean
    public RedisRateLimiter redisRateLimiter() {
        return new RedisRateLimiter(10, 20); // replenish rate, burst capacity
    }
}
```

### 🔹 Request Aggregation

**Combining multiple service calls**

```java
@RestController
public class ApiGatewayController {
    
    @Autowired
    private UserServiceClient userClient;
    
    @Autowired
    private OrderServiceClient orderClient;
    
    @GetMapping("/dashboard/{userId}")
    public DashboardData getDashboard(@PathVariable Long userId) {
        // Parallel calls to multiple services
        Mono<User> user = userClient.getUser(userId);
        Mono<List<Order>> orders = orderClient.getUserOrders(userId);
        
        return Mono.zip(user, orders)
            .map(tuple -> new DashboardData(tuple.getT1(), tuple.getT2()))
            .block();
    }
}
```

### 🔹 Circuit Breaker Integration

**Failure handling**

```java
@Configuration
public class CircuitBreakerConfig {
    
    @Bean
    public RouteLocator circuitBreakerRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("circuit-breaker", r -> r.path("/api/**")
                .filters(f -> f.circuitBreaker(c -> c.setName("api-circuit-breaker")
                    .setFallbackUri("forward:/fallback")))
                .uri("lb://api-service"))
            .build();
    }
}
```

### 🔹 Caching

**Performance optimization**

```java
@Configuration
public class CacheConfig {
    
    @Bean
    public RouteLocator cachedRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("cached", r -> r.path("/api/products/**")
                .filters(f -> f.cacheRequestParameters())
                .uri("lb://product-service"))
            .build();
    }
}
```

### 🔹 API Gateway vs Service Mesh

**Different approaches**

- **API Gateway**: Application-level concerns, request routing, composition
- **Service Mesh**: Infrastructure-level concerns, service-to-service communication

### 🔹 Popular Implementations

**Gateway solutions**

- **Spring Cloud Gateway**: Java-based, reactive
- **Netflix Zuul**: Java-based, blocking (legacy)
- **Kong**: Lua-based, highly extensible
- **AWS API Gateway**: Managed cloud service
- **Apigee**: Enterprise API management

### 🔹 Best Practices

```java
// Keep gateway lightweight
// Use reactive programming for performance
// Implement proper error handling
// Use circuit breakers for resilience
// Implement comprehensive logging
// Use distributed tracing
// Cache appropriately
// Version your APIs
// Document gateway endpoints
```

### 🔹 Common Patterns

**Gateway usage patterns**

- **Edge Gateway**: External client requests
- **Internal Gateway**: Inter-service communication
- **Mobile Gateway**: Mobile-specific adaptations
- **Partner Gateway**: External partner access

### 🔹 Security Considerations

**Protecting the gateway**

```java
// Use HTTPS everywhere
// Implement proper authentication
// Validate input thoroughly
// Use rate limiting to prevent abuse
// Implement CORS properly
// Use API keys for identification
// Implement proper session management
```

---

## 🎯 Interview One-Liner

> API Gateway: single entry point for microservices; handles routing, authentication, rate limiting, request aggregation, and cross-cutting concerns.

---

## ✅ 87. What is Service Discovery? (Eureka)

**Service Discovery is a mechanism that allows microservices to automatically find and communicate with each other without hardcoding network locations, using a service registry to maintain up-to-date service instances.**

### 🔹 What is Service Discovery?

**Dynamic service location**

Service Discovery enables:

- Automatic registration of service instances
- Dynamic lookup of service locations
- Load balancing across instances
- Health checking and instance removal
- No hardcoded IP addresses or ports

### 🔹 How Service Discovery Works

**Registration and discovery process**

```java
// Service Registration
@SpringBootApplication
@EnableEurekaClient  // For Eureka client
public class UserServiceApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}

// Application.yml configuration
eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/
  instance:
    hostname: user-service
    prefer-ip-address: true
```

### 🔹 Eureka Server Setup

**Service registry**

```java
// Eureka Server Application
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}

// Application.yml
server:
  port: 8761

eureka:
  client:
    register-with-eureka: false
    fetch-registry: false
```

### 🔹 Service Registration

**How services register themselves**

```java
// Automatic registration with Spring Cloud
@Configuration
public class EurekaConfig {
    // No additional configuration needed
    // Services auto-register on startup
}

// Manual registration (rarely needed)
@Service
public class ServiceRegistrar {
    
    @Autowired
    private EurekaClient eurekaClient;
    
    public void registerService() {
        InstanceInfo instanceInfo = InstanceInfo.Builder.newBuilder()
            .setAppName("my-service")
            .setHostName("my-service-host")
            .setPort(8080)
            .build();
        
        eurekaClient.register(instanceInfo);
    }
}
```

### 🔹 Service Discovery

**Finding service instances**

```java
// Using Eureka Client directly
@Service
public class UserService {
    
    @Autowired
    private EurekaClient eurekaClient;
    
    public String getProductServiceUrl() {
        InstanceInfo instance = eurekaClient
            .getNextServerFromEureka("product-service", false);
        return instance.getHomePageUrl();
    }
}

// Using Ribbon (client-side load balancing)
@Service
public class OrderService {
    
    @Autowired
    private RestTemplate restTemplate;
    
    public Product getProduct(Long productId) {
        // Ribbon automatically resolves service name
        return restTemplate.getForObject(
            "http://product-service/products/" + productId, 
            Product.class);
    }
}
```

### 🔹 Load Balancing

**Distributing requests**

```java
// Ribbon configuration
@Configuration
public class RibbonConfig {
    
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

// Usage with load balancing
@Service
public class OrderService {
    
    @Autowired
    private RestTemplate restTemplate;
    
    public List<Order> getUserOrders(Long userId) {
        // Requests automatically load balanced
        return restTemplate.exchange(
            "http://user-service/users/" + userId + "/orders",
            HttpMethod.GET, null, 
            new ParameterizedTypeReference<List<Order>>() {})
            .getBody();
    }
}
```

### 🔹 Health Checks

**Instance health monitoring**

```java
// Application.yml health check configuration
eureka:
  instance:
    health-check-url-path: /actuator/health
    status-page-url-path: /actuator/info
    lease-renewal-interval-in-seconds: 30
    lease-expiration-duration-in-seconds: 90

management:
  endpoints:
    web:
      exposure:
        include: health,info
  endpoint:
    health:
      show-details: always
```

### 🔹 Service Instance Metadata

**Additional instance information**

```java
// Custom metadata
eureka:
  instance:
    metadata-map:
      version: "1.0.0"
      environment: "production"
      zone: "us-east-1"

// Accessing metadata
@Service
public class ServiceDiscoveryHelper {
    
    @Autowired
    private EurekaClient eurekaClient;
    
    public String getServiceVersion(String serviceName) {
        InstanceInfo instance = eurekaClient
            .getNextServerFromEureka(serviceName, false);
        return instance.getMetadata().get("version");
    }
}
```

### 🔹 High Availability

**Eureka cluster setup**

```java
// Multiple Eureka servers
eureka:
  client:
    serviceUrl:
      defaultZone: http://eureka-server-1:8761/eureka/,http://eureka-server-2:8761/eureka/
  server:
    enable-self-preservation: false
```

### 🔹 Alternatives to Eureka

**Other service discovery solutions**

- **Consul**: HashiCorp's service discovery and configuration tool
- **Zookeeper**: Apache's coordination service
- **Kubernetes Service Discovery**: Built-in with K8s
- **etcd**: Distributed key-value store

### 🔹 Best Practices

```java
// Use client-side load balancing (Ribbon)
// Configure proper health checks
// Set appropriate lease intervals
// Use service names instead of IP addresses
// Implement circuit breakers with discovered services
// Monitor service registry health
// Use multiple Eureka servers for HA
// Configure proper timeouts
```

### 🔹 Common Issues

**Troubleshooting service discovery**

- **Service not found**: Check registration status
- **Load balancing not working**: Verify Ribbon configuration
- **Health checks failing**: Check actuator endpoints
- **Network issues**: Verify Eureka server connectivity

---

## 🎯 Interview One-Liner

> Service Discovery: automatic registration/lookup of microservices using registry (Eureka); enables dynamic service location, load balancing without hardcoded addresses.

---

## ✅ 88. What is Circuit Breaker pattern? (Resilience4j/Hystrix)

**Circuit Breaker is a design pattern that prevents cascading failures in distributed systems by monitoring service calls and temporarily stopping requests to a failing service, allowing it time to recover.**

### 🔹 What is Circuit Breaker?

**Failure prevention mechanism**

The Circuit Breaker pattern:

- Monitors calls to external services
- Tracks failure rates
- Opens circuit when failure threshold exceeded
- Allows limited requests through when half-open
- Closes circuit when service recovers

```java
// Resilience4j Circuit Breaker
@Configuration
public class CircuitBreakerConfig {
    
    @Bean
    public CircuitBreakerRegistry circuitBreakerRegistry() {
        return CircuitBreakerRegistry.ofDefaults();
    }
    
    @Bean
    public CircuitBreaker userServiceCircuitBreaker(CircuitBreakerRegistry registry) {
        return registry.circuitBreaker("user-service");
    }
}
```

### 🔹 Circuit Breaker States

**Three operational states**

### 🔹 Closed State (Normal Operation)

```java
@Service
public class OrderService {
    
    @Autowired
    private CircuitBreaker circuitBreaker;
    
    public User getUser(Long userId) {
        return circuitBreaker.executeSupplier(() -> 
            restTemplate.getForObject("http://user-service/users/" + userId, User.class));
    }
}
```

### 🔹 Open State (Failure Protection)

```java
// When failure rate exceeds threshold
@Configuration
public class CircuitBreakerConfig {
    
    @Bean
    public CircuitBreakerConfigCustomizer circuitBreakerConfigCustomizer() {
        return CircuitBreakerConfigCustomizer.of("user-service", builder -> builder
            .failureRateThreshold(50)  // Open if >50% failures
            .waitDurationInOpenState(Duration.ofMillis(10000))  // Wait 10s before half-open
            .slidingWindowSize(10)  // Check last 10 calls
        );
    }
}
```

### 🔹 Half-Open State (Recovery Testing)

```java
// Limited requests to test recovery
circuitBreaker.executeSupplier(() -> {
    // This call goes through even when circuit is half-open
    return userService.getUser(userId);
});
```

### 🔹 Integration with Spring Boot

**Spring Boot integration**

```java
// Using @CircuitBreaker annotation
@Service
public class PaymentService {
    
    @CircuitBreaker(name = "payment-service", fallbackMethod = "paymentFallback")
    public Payment processPayment(PaymentRequest request) {
        return restTemplate.postForObject(
            "http://payment-service/process", request, Payment.class);
    }
    
    public Payment paymentFallback(PaymentRequest request, Exception ex) {
        // Fallback logic
        return new Payment("fallback-id", PaymentStatus.PENDING);
    }
}
```

### 🔹 Hystrix Implementation (Legacy)

**Netflix Hystrix example**

```java
// Hystrix Command
public class UserServiceCommand extends HystrixCommand<User> {
    
    private final Long userId;
    private final RestTemplate restTemplate;
    
    public UserServiceCommand(Long userId, RestTemplate restTemplate) {
        super(HystrixCommandGroupKey.Factory.asKey("UserService"));
        this.userId = userId;
        this.restTemplate = restTemplate;
    }
    
    @Override
    protected User run() throws Exception {
        return restTemplate.getForObject(
            "http://user-service/users/" + userId, User.class);
    }
    
    @Override
    protected User getFallback() {
        return new User("fallback-user");
    }
}
```

### 🔹 Configuration Options

**Tuning circuit breaker behavior**

```java
@Configuration
public class Resilience4jConfig {
    
    @Bean
    public CircuitBreakerConfigCustomizer circuitBreakerConfigCustomizer() {
        return CircuitBreakerConfigCustomizer.of("backend-service", builder -> builder
            .failureRateThreshold(60.0f)  // Open at 60% failure rate
            .slowCallRateThreshold(60.0f)  // Open at 60% slow calls
            .slowCallDurationThreshold(Duration.ofSeconds(2))  // Calls >2s are slow
            .waitDurationInOpenState(Duration.ofSeconds(30))  // Wait 30s in open state
            .slidingWindowType(COUNT_BASED)  // Count-based sliding window
            .slidingWindowSize(20)  // Check last 20 calls
            .minimumNumberOfCalls(5)  // Need at least 5 calls to evaluate
            .permittedNumberOfCallsInHalfOpenState(3)  // 3 test calls in half-open
        );
    }
}
```

### 🔹 Metrics and Monitoring

**Observing circuit breaker state**

```java
@RestController
public class CircuitBreakerController {
    
    @Autowired
    private CircuitBreakerRegistry registry;
    
    @GetMapping("/circuit-breakers")
    public Map<String, CircuitBreaker> getCircuitBreakers() {
        return registry.getAllCircuitBreakers();
    }
    
    @GetMapping("/circuit-breakers/{name}/metrics")
    public CircuitBreaker.Metrics getMetrics(@PathVariable String name) {
        CircuitBreaker circuitBreaker = registry.circuitBreaker(name);
        return circuitBreaker.getMetrics();
    }
}
```

### 🔹 Bulkhead Pattern Integration

**Combining with bulkhead**

```java
@Configuration
public class ResilienceConfig {
    
    @Bean
    public ThreadPoolBulkheadConfig bulkheadConfig() {
        return ThreadPoolBulkheadConfig.custom()
            .maxThreadPoolSize(10)
            .coreThreadPoolSize(2)
            .queueCapacity(20)
            .build();
    }
    
    @Bean
    public CircuitBreakerConfig circuitBreakerConfig() {
        return CircuitBreakerConfig.custom()
            .failureRateThreshold(50)
            .waitDurationInOpenState(Duration.ofMillis(5000))
            .build();
    }
}
```

### 🔹 Best Practices

```java
// Configure appropriate thresholds
// Implement meaningful fallbacks
// Monitor circuit breaker metrics
// Use exponential backoff for retries
// Combine with timeouts
// Test failure scenarios
// Document fallback behavior
// Use different configurations for different services
```

### 🔹 Common Use Cases

**When to use circuit breakers**

- External service calls
- Database connections
- Third-party API integrations
- Inter-microservice communication
- Legacy system integrations

### 🔹 Testing Circuit Breakers

**Testing failure scenarios**

```java
@SpringBootTest
public class CircuitBreakerTest {
    
    @Autowired
    private CircuitBreakerRegistry registry;
    
    @Test
    public void shouldOpenCircuitOnFailures() {
        CircuitBreaker circuitBreaker = registry.circuitBreaker("test-service");
        
        // Force failures to open circuit
        for (int i = 0; i < 10; i++) {
            try {
                circuitBreaker.executeSupplier(() -> { throw new RuntimeException(); });
            } catch (Exception e) {
                // Expected
            }
        }
        
        assertEquals(CircuitBreaker.State.OPEN, circuitBreaker.getState());
    }
}
```

---

## 🎯 Interview One-Liner

> Circuit Breaker: prevents cascading failures by monitoring calls, opening when failures exceed threshold, allowing recovery time; states: closed, open, half-open.

---

## ✅ 89. What is Saga pattern?

**Saga pattern is a design pattern for managing distributed transactions in microservices, where a long-running business process is broken into a series of local transactions that can be rolled back individually if any step fails.**

### 🔹 What is Saga Pattern?

**Distributed transaction management**

Saga pattern addresses the challenge of:

- Maintaining data consistency across multiple services
- Handling failures in distributed transactions
- Avoiding distributed locks and two-phase commit complexity
- Implementing compensating actions for rollbacks

### 🔹 Saga Types

**Two implementation approaches**

### 🔹 Choreography Saga

**Event-driven coordination**

```java
// Order Service - Initiates saga
@Service
public class OrderService {
    
    @Autowired
    private ApplicationEventPublisher publisher;
    
    @Transactional
    public Order createOrder(OrderRequest request) {
        Order order = orderRepository.save(request.toOrder());
        publisher.publishEvent(new OrderCreatedEvent(order));
        return order;
    }
}

// Payment Service - Handles payment
@Component
public class OrderCreatedEventHandler {
    
    @EventListener
    @Transactional
    public void handleOrderCreated(OrderCreatedEvent event) {
        try {
            paymentService.processPayment(event.getOrder());
            publisher.publishEvent(new PaymentProcessedEvent(event.getOrder()));
        } catch (Exception e) {
            publisher.publishEvent(new PaymentFailedEvent(event.getOrder()));
        }
    }
}

// Inventory Service - Reserves inventory
@Component
public class PaymentProcessedEventHandler {
    
    @EventListener
    @Transactional
    public void handlePaymentProcessed(PaymentProcessedEvent event) {
        try {
            inventoryService.reserveItems(event.getOrder().getItems());
            publisher.publishEvent(new InventoryReservedEvent(event.getOrder()));
        } catch (Exception e) {
            // Compensating action: refund payment
            paymentService.refundPayment(event.getOrder());
            publisher.publishEvent(new SagaFailedEvent(event.getOrder()));
        }
    }
}
```

### 🔹 Orchestration Saga

**Central coordinator**

```java
// Saga Orchestrator
@Service
public class OrderSagaOrchestrator {
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private PaymentService paymentService;
    
    @Autowired
    private InventoryService inventoryService;
    
    @Autowired
    private ShippingService shippingService;
    
    public void executeOrderSaga(OrderRequest request) {
        OrderSaga saga = new OrderSaga();
        
        try {
            // Step 1: Create order
            Order order = orderService.createOrder(request);
            saga.setOrder(order);
            
            // Step 2: Process payment
            Payment payment = paymentService.processPayment(order);
            saga.setPayment(payment);
            
            // Step 3: Reserve inventory
            inventoryService.reserveInventory(order);
            
            // Step 4: Arrange shipping
            shippingService.arrangeShipping(order);
            
            saga.setStatus(SagaStatus.COMPLETED);
            
        } catch (Exception e) {
            // Compensate in reverse order
            compensateSaga(saga);
            saga.setStatus(SagaStatus.FAILED);
        }
    }
    
    private void compensateSaga(OrderSaga saga) {
        if (saga.getShipping() != null) {
            shippingService.cancelShipping(saga.getOrder());
        }
        if (saga.getInventoryReserved()) {
            inventoryService.releaseInventory(saga.getOrder());
        }
        if (saga.getPayment() != null) {
            paymentService.refundPayment(saga.getPayment());
        }
        if (saga.getOrder() != null) {
            orderService.cancelOrder(saga.getOrder());
        }
    }
}
```

### 🔹 Saga Implementation with Axon Framework

**Framework-based saga**

```java
// Saga definition
public class OrderSaga extends AbstractSaga {
    
    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderCreatedEvent event) {
        // Start saga
        associateWith("paymentId", event.getPaymentId());
        paymentService.processPayment(event.getOrder());
    }
    
    @SagaEventHandler(associationProperty = "paymentId")
    public void handle(PaymentProcessedEvent event) {
        associateWith("inventoryId", event.getInventoryId());
        inventoryService.reserveInventory(event.getOrder());
    }
    
    @SagaEventHandler(associationProperty = "inventoryId")
    public void handle(InventoryReservedEvent event) {
        shippingService.arrangeShipping(event.getOrder());
        end(); // Saga completed
    }
    
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderFailedEvent event) {
        // Compensating actions
        paymentService.refundPayment(getPaymentId());
        inventoryService.releaseInventory(getInventoryId());
        end();
    }
}
```

### 🔹 Compensating Transactions

**Rollback actions**

```java
// Compensating actions for each step
public class SagaCompensations {
    
    public static class PaymentCompensation {
        public void compensate(Payment payment) {
            paymentService.refundPayment(payment.getId());
        }
    }
    
    public static class InventoryCompensation {
        public void compensate(Order order) {
            inventoryService.releaseInventory(order.getId());
        }
    }
    
    public static class ShippingCompensation {
        public void compensate(Order order) {
            shippingService.cancelShipping(order.getId());
        }
    }
}
```

### 🔹 Saga Persistence

**Storing saga state**

```java
@Entity
public class OrderSaga {
    
    @Id
    private String sagaId;
    
    private String orderId;
    private String paymentId;
    private String inventoryId;
    private String shippingId;
    
    private SagaStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Saga steps completed
    private boolean orderCreated;
    private boolean paymentProcessed;
    private boolean inventoryReserved;
    private boolean shippingArranged;
}
```

### 🔹 Error Handling

**Handling failures**

```java
@Service
public class SagaErrorHandler {
    
    @Autowired
    private SagaRepository sagaRepository;
    
    public void handleSagaError(String sagaId, Exception error) {
        OrderSaga saga = sagaRepository.findById(sagaId);
        
        // Log error
        log.error("Saga {} failed: {}", sagaId, error.getMessage());
        
        // Execute compensating transactions
        sagaOrchestrator.compensate(saga);
        
        // Update saga status
        saga.setStatus(SagaStatus.FAILED);
        saga.setErrorMessage(error.getMessage());
        sagaRepository.save(saga);
        
        // Notify stakeholders
        notificationService.sendFailureNotification(saga);
    }
}
```

### 🔹 Monitoring Sagas

**Observability**

```java
@RestController
public class SagaMonitoringController {
    
    @Autowired
    private SagaRepository sagaRepository;
    
    @GetMapping("/sagas")
    public List<OrderSaga> getAllSagas() {
        return sagaRepository.findAll();
    }
    
    @GetMapping("/sagas/{id}")
    public OrderSaga getSaga(@PathVariable String id) {
        return sagaRepository.findById(id).orElseThrow();
    }
    
    @GetMapping("/sagas/metrics")
    public SagaMetrics getSagaMetrics() {
        List<OrderSaga> sagas = sagaRepository.findAll();
        return new SagaMetrics(
            sagas.stream().filter(s -> s.getStatus() == SagaStatus.COMPLETED).count(),
            sagas.stream().filter(s -> s.getStatus() == SagaStatus.FAILED).count(),
            sagas.stream().filter(s -> s.getStatus() == SagaStatus.PENDING).count()
        );
    }
}
```

### 🔹 Best Practices

```java
// Design compensating actions during forward design
// Keep sagas simple and focused
// Use timeouts for saga steps
// Implement proper monitoring
// Test compensating actions thoroughly
// Use idempotent operations
// Document saga flows clearly
// Consider saga timeouts and cleanup
```

### 🔹 When to Use Sagas

**Appropriate use cases**

- Long-running business processes
- Cross-service data consistency requirements
- Systems where 2PC is not feasible
- Complex business workflows
- Systems requiring eventual consistency

### 🔹 Alternatives

**Other distributed transaction approaches**

- **Two-Phase Commit (2PC)**: Strong consistency, blocking
- **Eventual Consistency**: No explicit coordination
- **TCC (Try-Confirm-Cancel)**: Three-phase approach
- **SAGA vs 2PC**: Saga for long-running, 2PC for short transactions

---

## 🎯 Interview One-Liner

> Saga pattern: manages distributed transactions by breaking into local transactions with compensating actions; choreography (event-driven) vs orchestration (central coordinator).

---

## ✅ 90. What is CQRS pattern?

**CQRS (Command Query Responsibility Segregation) is a design pattern that separates read and write operations into different models, allowing for optimized data structures and scaling strategies for each concern.**

### 🔹 What is CQRS?

**Separation of concerns for reads and writes**

CQRS pattern separates:

- **Commands**: Operations that change state (Create, Update, Delete)
- **Queries**: Operations that read state (Read operations)
- **Different models**: Optimized for each responsibility
- **Independent scaling**: Scale reads and writes separately

### 🔹 Traditional vs CQRS Architecture

**Comparison**

```java
// Traditional approach - Single model
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User save(User user);           // Write
    User findById(Long id);         // Read
    List<User> findAll();           // Read
    void deleteById(Long id);       // Write
}

// CQRS approach - Separate models
// Write Model (Commands)
@Service
public class UserCommandService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Transactional
    public User createUser(CreateUserCommand command) {
        User user = new User(command.getName(), command.getEmail());
        return userRepository.save(user);
    }
    
    @Transactional
    public User updateUser(UpdateUserCommand command) {
        User user = userRepository.findById(command.getId()).orElseThrow();
        user.update(command.getName(), command.getEmail());
        return userRepository.save(user);
    }
}

// Read Model (Queries)
@Service
public class UserQueryService {
    
    @Autowired
    private UserReadRepository userReadRepository;
    
    public UserView getUser(Long id) {
        return userReadRepository.findById(id).orElseThrow();
    }
    
    public List<UserSummaryView> getAllUsers() {
        return userReadRepository.findAllUsers();
    }
}
```

### 🔹 Command Side

**Write operations**

```java
// Command objects
public class CreateUserCommand {
    private String name;
    private String email;
    
    // Constructor, getters
}

public class UpdateUserCommand {
    private Long id;
    private String name;
    private String email;
    
    // Constructor, getters
}

// Command Handler
@Component
public class UserCommandHandler {
    
    @Autowired
    private UserCommandService commandService;
    
    @CommandHandler
    public User handle(CreateUserCommand command) {
        return commandService.createUser(command);
    }
    
    @CommandHandler
    public User handle(UpdateUserCommand command) {
        return commandService.updateUser(command);
    }
}
```

### 🔹 Query Side

**Read operations**

```java
// Query objects
public class GetUserQuery {
    private Long userId;
    
    // Constructor, getters
}

public class GetAllUsersQuery {
    private int page;
    private int size;
    
    // Constructor, getters
}

// Query Handler
@Component
public class UserQueryHandler {
    
    @Autowired
    private UserQueryService queryService;
    
    @QueryHandler
    public UserView handle(GetUserQuery query) {
        return queryService.getUser(query.getUserId());
    }
    
    @QueryHandler
    public List<UserSummaryView> handle(GetAllUsersQuery query) {
        return queryService.getAllUsers(query.getPage(), query.getSize());
    }
}
```

### 🔹 Separate Data Stores

**Different storage for reads and writes**

```java
// Write Database (Normalized)
@Entity
@Table(name = "users")
public class User {
    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Business logic methods
}

// Read Database (Denormalized)
public class UserView {
    private Long id;
    private String fullName;  // Computed field
    private String email;
    private String status;
    private LocalDateTime lastLogin;
    
    // Optimized for reading
}
```

### 🔹 Event Sourcing Integration

**Using events to synchronize models**

```java
// Events
public class UserCreatedEvent {
    private Long userId;
    private String name;
    private String email;
    
    // Constructor, getters
}

public class UserUpdatedEvent {
    private Long userId;
    private String name;
    private String email;
    
    // Constructor, getters
}

// Event Handler for Read Model
@Component
public class UserReadModelUpdater {
    
    @Autowired
    private UserReadRepository readRepository;
    
    @EventListener
    public void on(UserCreatedEvent event) {
        UserView userView = new UserView(
            event.getUserId(),
            event.getName(),
            event.getEmail(),
            "ACTIVE"
        );
        readRepository.save(userView);
    }
    
    @EventListener
    public void on(UserUpdatedEvent event) {
        UserView userView = readRepository.findById(event.getUserId()).orElseThrow();
        userView.update(event.getName(), event.getEmail());
        readRepository.save(userView);
    }
}
```

### 🔹 Benefits of CQRS

**Advantages**

1. **Independent Scaling**: Scale reads and writes separately
2. **Optimized Models**: Different schemas for different purposes
3. **Performance**: Better read performance with denormalized data
4. **Security**: Different security models for commands and queries
5. **Complexity Management**: Separate concerns for complex domains
6. **Event Sourcing**: Natural fit with event-driven architectures

### 🔹 When to Use CQRS

**Appropriate scenarios**

- High read/write ratio systems
- Complex domains with different read/write requirements
- Systems requiring high scalability
- Event-sourced systems
- Systems with complex business logic
- Applications with multiple user interfaces

### 🔹 CQRS with Event Sourcing

**Complete pattern combination**

```java
// Aggregate (Write Model)
public class UserAggregate {
    
    private Long id;
    private String name;
    private String email;
    private List<DomainEvent> uncommittedEvents = new ArrayList<>();
    
    public UserAggregate(Long id, String name, String email) {
        applyChange(new UserCreatedEvent(id, name, email));
    }
    
    public void updateName(String newName) {
        applyChange(new UserNameUpdatedEvent(id, newName));
    }
    
    private void applyChange(DomainEvent event) {
        // Apply to aggregate state
        // Add to uncommitted events
    }
}

// Event Store
@Repository
public class EventStore {
    
    public void saveEvents(Long aggregateId, List<DomainEvent> events, long expectedVersion) {
        // Save events with optimistic locking
    }
    
    public List<DomainEvent> getEventsForAggregate(Long aggregateId) {
        // Load events to rebuild aggregate
    }
}
```

### 🔹 Challenges

**Potential issues**

- **Complexity**: More complex architecture
- **Eventual Consistency**: Read model may lag behind write model
- **Duplication**: Code duplication between models
- **Testing**: More complex testing scenarios
- **Learning Curve**: Steeper learning curve for developers

### 🔹 Best Practices

```java
// Start simple, evolve to CQRS when needed
// Use event sourcing for synchronization
// Implement proper monitoring
// Handle eventual consistency appropriately
// Use appropriate technologies for each model
// Implement comprehensive testing
// Document the architecture clearly
// Consider team skills and complexity
```

### 🔹 Tools and Frameworks

**CQRS implementations**

- **Axon Framework**: Full CQRS and Event Sourcing support
- **Eventuate**: Platform for event-driven microservices
- **Lagom**: Microservices framework with CQRS support
- **Akka**: Actor-based CQRS implementations

---

## 🎯 Interview One-Liner

> CQRS: separates read (queries) and write (commands) operations into different models; enables independent scaling, optimized schemas, better performance.

---

## ✅ 91. What is Event Sourcing?

**Event Sourcing is a design pattern where instead of storing the current state of an entity, all changes (events) that led to the current state are stored, allowing reconstruction of state and providing an audit trail.**

### 🔹 What is Event Sourcing?

**Event-centric persistence**

Event Sourcing stores:

- **Events**: Immutable records of state changes
- **Event Store**: Append-only storage of events
- **Aggregate Reconstruction**: Current state built from events
- **Audit Trail**: Complete history of changes
- **Temporal Queries**: State at any point in time

### 🔹 Traditional vs Event Sourcing

**Different persistence approaches**

```java
// Traditional approach - Store current state
@Entity
@Table(name = "bank_accounts")
public class BankAccount {
    @Id
    private Long id;
    private String accountNumber;
    private BigDecimal balance;  // Current balance
    
    public void deposit(BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }
    
    public void withdraw(BigDecimal amount) {
        this.balance = this.balance.subtract(amount);
    }
}

// Event Sourcing approach - Store events
public class AccountOpenedEvent {
    private Long accountId;
    private String accountNumber;
    private LocalDateTime openedAt;
}

public class MoneyDepositedEvent {
    private Long accountId;
    private BigDecimal amount;
    private LocalDateTime depositedAt;
}

public class MoneyWithdrawnEvent {
    private Long accountId;
    private BigDecimal amount;
    private LocalDateTime withdrawnAt;
}
```

### 🔹 Event Store

**Storing events**

```java
@Repository
public class EventStore {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public void saveEvents(Long aggregateId, List<DomainEvent> events, long expectedVersion) {
        for (DomainEvent event : events) {
            jdbcTemplate.update(
                "INSERT INTO events (aggregate_id, event_type, event_data, version) VALUES (?, ?, ?, ?)",
                aggregateId,
                event.getClass().getSimpleName(),
                serialize(event),
                expectedVersion++
            );
        }
    }
    
    public List<DomainEvent> getEventsForAggregate(Long aggregateId) {
        return jdbcTemplate.query(
            "SELECT * FROM events WHERE aggregate_id = ? ORDER BY version",
            (rs, rowNum) -> deserialize(rs.getString("event_data"))
        );
    }
}
```

### 🔹 Aggregate Reconstruction

**Building current state from events**

```java
public class BankAccount {
    
    private Long id;
    private String accountNumber;
    private BigDecimal balance;
    private long version;
    
    // Private constructor for reconstruction
    private BankAccount() {}
    
    // Factory method to create from events
    public static BankAccount reconstruct(List<DomainEvent> events) {
        BankAccount account = new BankAccount();
        
        for (DomainEvent event : events) {
            account.apply(event, false);  // Don't add to uncommitted events
        }
        
        return account;
    }
    
    // Apply event to change state
    private void apply(DomainEvent event, boolean isNew) {
        if (event instanceof AccountOpenedEvent) {
            apply((AccountOpenedEvent) event);
        } else if (event instanceof MoneyDepositedEvent) {
            apply((MoneyDepositedEvent) event);
        } else if (event instanceof MoneyWithdrawnEvent) {
            apply((MoneyWithdrawnEvent) event);
        }
        
        if (isNew) {
            version++;
            // Add to uncommitted events
        }
    }
    
    private void apply(AccountOpenedEvent event) {
        this.id = event.getAccountId();
        this.accountNumber = event.getAccountNumber();
        this.balance = BigDecimal.ZERO;
    }
    
    private void apply(MoneyDepositedEvent event) {
        this.balance = this.balance.add(event.getAmount());
    }
    
    private void apply(MoneyWithdrawnEvent event) {
        this.balance = this.balance.subtract(event.getAmount());
    }
}
```

### 🔹 Command Handling

**Processing commands**

```java
@Service
public class BankAccountCommandHandler {
    
    @Autowired
    private EventStore eventStore;
    
    public void handle(OpenAccountCommand command) {
        // Create new aggregate
        BankAccount account = new BankAccount();
        account.openAccount(command.getAccountNumber());
        
        // Save events
        eventStore.saveEvents(
            account.getId(), 
            account.getUncommittedEvents(), 
            0
        );
    }
    
    public void handle(DepositMoneyCommand command) {
        // Load aggregate from events
        List<DomainEvent> events = eventStore.getEventsForAggregate(command.getAccountId());
        BankAccount account = BankAccount.reconstruct(events);
        
        // Apply command
        account.deposit(command.getAmount());
        
        // Save new events
        eventStore.saveEvents(
            account.getId(), 
            account.getUncommittedEvents(), 
            account.getVersion()
        );
    }
}
```

### 🔹 Event Publishing

**Publishing events for integration**

```java
@Service
public class EventPublisher {
    
    @Autowired
    private ApplicationEventPublisher publisher;
    
    public void publishEvents(List<DomainEvent> events) {
        for (DomainEvent event : events) {
            publisher.publishEvent(event);
        }
    }
}

// Event listeners for projections
@Component
public class AccountProjectionUpdater {
    
    @Autowired
    private AccountProjectionRepository projectionRepo;
    
    @EventListener
    public void on(AccountOpenedEvent event) {
        AccountProjection projection = new AccountProjection(
            event.getAccountId(),
            event.getAccountNumber(),
            BigDecimal.ZERO
        );
        projectionRepo.save(projection);
    }
    
    @EventListener
    public void on(MoneyDepositedEvent event) {
        AccountProjection projection = projectionRepo.findById(event.getAccountId()).orElseThrow();
        projection.setBalance(projection.getBalance().add(event.getAmount()));
        projectionRepo.save(projection);
    }
}
```

### 🔹 Snapshots

**Performance optimization**

```java
@Service
public class SnapshotService {
    
    private static final int SNAPSHOT_FREQUENCY = 100;
    
    @Autowired
    private EventStore eventStore;
    
    @Autowired
    private SnapshotRepository snapshotRepo;
    
    public BankAccount loadAggregate(Long aggregateId) {
        // Try to load from snapshot
        Snapshot snapshot = snapshotRepo.findTopByAggregateIdOrderByVersionDesc(aggregateId);
        
        List<DomainEvent> events;
        long startVersion = 0;
        
        if (snapshot != null) {
            events = eventStore.getEventsForAggregateFromVersion(
                aggregateId, snapshot.getVersion() + 1);
            startVersion = snapshot.getVersion();
        } else {
            events = eventStore.getEventsForAggregate(aggregateId);
        }
        
        BankAccount account = BankAccount.reconstruct(events);
        
        // Create snapshot if needed
        if (account.getVersion() % SNAPSHOT_FREQUENCY == 0) {
            createSnapshot(account);
        }
        
        return account;
    }
    
    private void createSnapshot(BankAccount account) {
        Snapshot snapshot = new Snapshot(
            account.getId(),
            account.getVersion(),
            serialize(account)
        );
        snapshotRepo.save(snapshot);
    }
}
```

### 🔹 Benefits

**Advantages of Event Sourcing**

1. **Audit Trail**: Complete history of changes
2. **Temporal Queries**: State at any point in time
3. **Event-Driven**: Natural fit for event-driven architectures
4. **Debugging**: Easy to understand what happened
5. **Performance**: Optimized for writes
6. **Scalability**: Easy to add new projections

### 🔹 Challenges

**Potential issues**

- **Complexity**: More complex than CRUD
- **Learning Curve**: Different way of thinking
- **Event Schema Evolution**: Handling event changes
- **Storage**: Events accumulate over time
- **Read Performance**: May need projections for complex queries
- **Testing**: More complex testing scenarios

### 🔹 Best Practices

```java
// Use domain events, not technical events
// Keep events immutable and serializable
// Implement snapshotting for performance
// Use event versioning for schema evolution
// Test event replay thoroughly
// Implement proper monitoring
// Use CQRS with Event Sourcing
// Document event schemas
```

### 🔹 Tools and Frameworks

**Event Sourcing implementations**

- **Axon Framework**: Comprehensive Event Sourcing support
- **EventStore**: Dedicated event store database
- **Eventuate**: Platform for event-driven systems
- **Akka Persistence**: Actor-based Event Sourcing

---

## 🎯 Interview One-Liner

> Event Sourcing: stores sequence of events instead of current state; enables audit trails, temporal queries, event-driven architectures; reconstructs state by replaying events.

---

## ✅ 92. What is Bulkhead pattern?

**Bulkhead pattern is a design pattern that isolates different parts of a system (like services or resources) to prevent failure in one part from cascading to others, similar to watertight compartments in a ship.**

### 🔹 What is Bulkhead Pattern?

**Failure isolation mechanism**

The Bulkhead pattern:

- **Isolates failures**: Prevents cascading failures
- **Resource segregation**: Separates resource pools
- **Thread isolation**: Uses separate thread pools
- **Service isolation**: Isolates service dependencies
- **Graceful degradation**: Allows partial system operation

### 🔹 Thread Pool Bulkhead

**Isolating threads**

```java
// Resilience4j Thread Pool Bulkhead
@Configuration
public class BulkheadConfig {
    
    @Bean
    public ThreadPoolBulkheadRegistry threadPoolBulkheadRegistry() {
        return ThreadPoolBulkheadRegistry.ofDefaults();
    }
    
    @Bean
    public ThreadPoolBulkhead userServiceBulkhead(ThreadPoolBulkheadRegistry registry) {
        return registry.bulkhead("user-service");
    }
}

// Usage
@Service
public class OrderService {
    
    @Autowired
    private ThreadPoolBulkhead bulkhead;
    
    public User getUser(Long userId) {
        return bulkhead.executeSupplier(() -> 
            restTemplate.getForObject("http://user-service/users/" + userId, User.class));
    }
}
```

### 🔹 Semaphore Bulkhead

**Resource limiting**

```java
@Configuration
public class SemaphoreBulkheadConfig {
    
    @Bean
    public BulkheadRegistry bulkheadRegistry() {
        return BulkheadRegistry.ofDefaults();
    }
    
    @Bean
    public Bulkhead paymentServiceBulkhead(BulkheadRegistry registry) {
        return registry.bulkhead("payment-service");
    }
}

// Usage with annotation
@Service
public class PaymentService {
    
    @Bulkhead(name = "payment-service", fallbackMethod = "paymentFallback")
    public Payment processPayment(PaymentRequest request) {
        return restTemplate.postForObject(
            "http://payment-service/process", request, Payment.class);
    }
    
    public Payment paymentFallback(PaymentRequest request, Exception ex) {
        return new Payment("fallback-id", PaymentStatus.PENDING);
    }
}
```

### 🔹 Service Isolation

**Isolating service dependencies**

```java
// Separate bulkheads for different services
@Configuration
public class ServiceBulkheadConfig {
    
    @Bean
    public BulkheadRegistry bulkheadRegistry() {
        return BulkheadRegistry.custom()
            .withBulkheadConfig(BulkheadConfig.custom()
                .maxConcurrentCalls(10)
                .maxWaitDuration(Duration.ofMillis(500))
                .build())
            .build();
    }
    
    @Bean
    public Bulkhead userBulkhead(BulkheadRegistry registry) {
        return registry.bulkhead("user-service", 
            BulkheadConfig.from(registry.getDefaultConfig())
                .maxConcurrentCalls(5)  // User service gets fewer resources
                .build());
    }
    
    @Bean
    public Bulkhead inventoryBulkhead(BulkheadRegistry registry) {
        return registry.bulkhead("inventory-service",
            BulkheadConfig.from(registry.getDefaultConfig())
                .maxConcurrentCalls(20)  // Inventory service gets more resources
                .build());
    }
}
```

### 🔹 Database Connection Pool Isolation

**Isolating database resources**

```java
@Configuration
public class DataSourceConfig {
    
    // Separate connection pools for different services
    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.user")
    public DataSource userDataSource() {
        return DataSourceBuilder.create()
            .type(HikariDataSource.class)
            .build();
    }
    
    @Bean
    @ConfigurationProperties("spring.datasource.order")
    public DataSource orderDataSource() {
        return DataSourceBuilder.create()
            .type(HikariDataSource.class)
            .build();
    }
    
    @Bean
    @ConfigurationProperties("spring.datasource.inventory")
    public DataSource inventoryDataSource() {
        return DataSourceBuilder.create()
            .type(HikariDataSource.class)
            .build();
    }
}
```

### 🔹 Circuit Breaker Integration

**Combining bulkhead with circuit breaker**

```java
@Configuration
public class ResilienceConfig {
    
    @Bean
    public CircuitBreakerRegistry circuitBreakerRegistry() {
        return CircuitBreakerRegistry.ofDefaults();
    }
    
    @Bean
    public BulkheadRegistry bulkheadRegistry() {
        return BulkheadRegistry.ofDefaults();
    }
    
    @Bean
    public CircuitBreaker paymentCircuitBreaker(CircuitBreakerRegistry registry) {
        return registry.circuitBreaker("payment-service");
    }
    
    @Bean
    public Bulkhead paymentBulkhead(BulkheadRegistry registry) {
        return registry.bulkhead("payment-service");
    }
}

// Combined usage
@Service
public class PaymentService {
    
    @Autowired
    private CircuitBreaker circuitBreaker;
    
    @Autowired
    private Bulkhead bulkhead;
    
    public Payment processPayment(PaymentRequest request) {
        return circuitBreaker.executeSupplier(() ->
            bulkhead.executeSupplier(() ->
                restTemplate.postForObject("http://payment-service/process", request, Payment.class)
            )
        );
    }
}
```

### 🔹 Monitoring Bulkheads

**Observing bulkhead metrics**

```java
@RestController
public class BulkheadMonitoringController {
    
    @Autowired
    private BulkheadRegistry registry;
    
    @GetMapping("/bulkheads")
    public Map<String, Bulkhead> getBulkheads() {
        return registry.getAllBulkheads();
    }
    
    @GetMapping("/bulkheads/{name}/metrics")
    public Bulkhead.Metrics getMetrics(@PathVariable String name) {
        Bulkhead bulkhead = registry.bulkhead(name);
        return bulkhead.getMetrics();
    }
}
```

### 🔹 Benefits

**Advantages of bulkhead pattern**

1. **Failure Isolation**: Prevents cascading failures
2. **Resource Protection**: Limits resource usage per service
3. **Performance Stability**: Maintains performance under load
4. **Graceful Degradation**: Allows partial system operation
5. **Predictable Behavior**: Controlled resource allocation
6. **Better Monitoring**: Isolated metrics per service

### 🔹 When to Use

**Appropriate scenarios**

- Systems with multiple external dependencies
- High-throughput applications
- Systems requiring high availability
- Applications with mixed criticality services
- Systems with unpredictable load patterns
- Microservices architectures

### 🔹 Configuration Best Practices

**Tuning bulkhead settings**

```java
// Configure based on service characteristics
// User-facing services: Lower limits, faster timeouts
// Background services: Higher limits, longer timeouts
// Critical services: Dedicated resources
// Non-critical services: Shared resources with lower priority

@Bean
public BulkheadConfigCustomizer bulkheadConfigCustomizer() {
    return BulkheadConfigCustomizer.of("critical-service", builder -> builder
        .maxConcurrentCalls(50)      // High concurrency for critical service
        .maxWaitDuration(Duration.ofMillis(100))  // Short wait
    );
}
```

### 🔹 Testing Bulkheads

**Testing isolation**

```java
@SpringBootTest
public class BulkheadTest {
    
    @Autowired
    private BulkheadRegistry registry;
    
    @Test
    public void shouldIsolateFailures() {
        Bulkhead bulkhead1 = registry.bulkhead("service1");
        Bulkhead bulkhead2 = registry.bulkhead("service2");
        
        // Exhaust bulkhead1
        for (int i = 0; i < 10; i++) {
            bulkhead1.executeSupplier(() -> {
                Thread.sleep(1000); // Simulate slow operation
                return "result";
            });
        }
        
        // bulkhead2 should still work
        String result = bulkhead2.executeSupplier(() -> "service2-result");
        assertEquals("service2-result", result);
    }
}
```

### 🔹 Common Patterns

**Bulkhead usage patterns**

- **Thread Pool Isolation**: Separate thread pools per service
- **Connection Pool Isolation**: Separate DB connections per service
- **Memory Isolation**: Separate memory pools
- **CPU Isolation**: CPU quotas per service
- **Network Isolation**: Separate network resources

### 🔹 Best Practices

```java
// Configure appropriate limits based on capacity
// Monitor bulkhead metrics regularly
// Use different configurations for different service types
// Implement proper fallback mechanisms
// Test bulkhead behavior under load
// Combine with circuit breakers for comprehensive resilience
// Document bulkhead configurations
// Use bulkheads at service boundaries
```

---

## 🎯 Interview One-Liner

> Bulkhead pattern: isolates system parts to prevent failure cascading; uses separate resource pools (threads, connections) to contain failures and maintain stability.

---

## ✅ 93. Explain Rate Limiting

**Rate Limiting is a technique to control the rate of requests sent to or received by a system, preventing abuse, ensuring fair usage, and protecting against overload by limiting the number of requests per time window.**

### 🔹 What is Rate Limiting?

**Request rate control**

Rate limiting controls:

- **Request Frequency**: Number of requests per time period
- **Resource Protection**: Prevents system overload
- **Fair Usage**: Ensures equitable resource distribution
- **Abuse Prevention**: Blocks malicious or excessive usage
- **Cost Control**: Manages resource consumption costs

### 🔹 Rate Limiting Algorithms

**Different implementation approaches**

### 🔹 Token Bucket Algorithm

**Token-based rate limiting**

```java
public class TokenBucket {
    private final long capacity;
    private final double refillRate; // tokens per second
    private double tokens;
    private long lastRefillTime;
    
    public TokenBucket(long capacity, double refillRate) {
        this.capacity = capacity;
        this.refillRate = refillRate;
        this.tokens = capacity;
        this.lastRefillTime = System.currentTimeMillis();
    }
    
    public synchronized boolean allowRequest() {
        refill();
        if (tokens >= 1) {
            tokens--;
            return true;
        }
        return false;
    }
    
    private void refill() {
        long now = System.currentTimeMillis();
        double elapsedSeconds = (now - lastRefillTime) / 1000.0;
        long tokensToAdd = (long) (elapsedSeconds * refillRate);
        
        if (tokensToAdd > 0) {
            tokens = Math.min(capacity, tokens + tokensToAdd);
            lastRefillTime = now;
        }
    }
}
```

### 🔹 Leaky Bucket Algorithm

**Queue-based rate limiting**

```java
public class LeakyBucket {
    private final long capacity;
    private final long leakRate; // requests per second
    private long waterLevel;
    private long lastLeakTime;
    
    public LeakyBucket(long capacity, long leakRate) {
        this.capacity = capacity;
        this.leakRate = leakRate;
        this.waterLevel = 0;
        this.lastLeakTime = System.currentTimeMillis();
    }
    
    public synchronized boolean allowRequest() {
        leak();
        if (waterLevel < capacity) {
            waterLevel++;
            return true;
        }
        return false;
    }
    
    private void leak() {
        long now = System.currentTimeMillis();
        long elapsedMillis = now - lastLeakTime;
        long leaks = (elapsedMillis * leakRate) / 1000;
        
        if (leaks > 0) {
            waterLevel = Math.max(0, waterLevel - leaks);
            lastLeakTime = now;
        }
    }
}
```

### 🔹 Fixed Window Algorithm

**Time window-based limiting**

```java
public class FixedWindowRateLimiter {
    private final Map<Long, Integer> windowRequests = new ConcurrentHashMap<>();
    private final int maxRequests;
    private final long windowSizeMillis;
    
    public FixedWindowRateLimiter(int maxRequests, long windowSizeMillis) {
        this.maxRequests = maxRequests;
        this.windowSizeMillis = windowSizeMillis;
    }
    
    public boolean allowRequest(String key) {
        long currentWindow = System.currentTimeMillis() / windowSizeMillis;
        int requests = windowRequests.getOrDefault(currentWindow, 0);
        
        if (requests < maxRequests) {
            windowRequests.put(currentWindow, requests + 1);
            return true;
        }
        return false;
    }
}
```

### 🔹 Sliding Window Algorithm

**Rolling time window**

```java
public class SlidingWindowRateLimiter {
    private final Map<String, LinkedList<Long>> userRequests = new ConcurrentHashMap<>();
    private final int maxRequests;
    private final long windowSizeMillis;
    
    public SlidingWindowRateLimiter(int maxRequests, long windowSizeMillis) {
        this.maxRequests = maxRequests;
        this.windowSizeMillis = windowSizeMillis;
    }
    
    public boolean allowRequest(String userId) {
        long now = System.currentTimeMillis();
        LinkedList<Long> requests = userRequests.computeIfAbsent(userId, k -> new LinkedList<>());
        
        // Remove old requests outside the window
        while (!requests.isEmpty() && (now - requests.peekFirst()) > windowSizeMillis) {
            requests.pollFirst();
        }
        
        if (requests.size() < maxRequests) {
            requests.addLast(now);
            return true;
        }
        return false;
    }
}
```

### 🔹 Spring Cloud Gateway Rate Limiting

**Gateway-level rate limiting**

```java
@Configuration
public class GatewayConfig {
    
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("api-route", r -> r.path("/api/**")
                .filters(f -> f.requestRateLimiter(c -> c.setRateLimiter(
                    redisRateLimiter()).setKeyResolver(userKeyResolver())))
                .uri("lb://api-service"))
            .build();
    }
    
    @Bean
    public RedisRateLimiter redisRateLimiter() {
        return new RedisRateLimiter(10, 20, 1); // replenish rate, burst capacity, requested tokens
    }
    
    @Bean
    public KeyResolver userKeyResolver() {
        return exchange -> Mono.just(
            exchange.getRequest().getHeaders().getFirst("X-User-Id") != null ?
            exchange.getRequest().getHeaders().getFirst("X-User-Id") :
            "anonymous"
        );
    }
}
```

### 🔹 Distributed Rate Limiting

**Cluster-wide rate limiting**

```java
@Service
public class DistributedRateLimiter {
    
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    
    public boolean allowRequest(String key, int maxRequests, long windowSeconds) {
        String redisKey = "rate_limit:" + key;
        long currentTime = System.currentTimeMillis() / 1000;
        
        // Use Redis sorted set to track requests
        redisTemplate.opsForZSet().add(redisKey, String.valueOf(currentTime), currentTime);
        
        // Remove old entries outside the window
        redisTemplate.opsForZSet().removeRangeByScore(redisKey, 0, currentTime - windowSeconds);
        
        Long requestCount = redisTemplate.opsForZSet().zCard(redisKey);
        
        return requestCount != null && requestCount <= maxRequests;
    }
}
```

### 🔹 Rate Limiting Strategies

**Different approaches**

### 🔹 User-Based Limiting

```java
@Component
public class UserRateLimitFilter implements GlobalFilter {
    
    @Autowired
    private RateLimiterService rateLimiter;
    
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String userId = exchange.getRequest().getHeaders().getFirst("X-User-Id");
        
        if (userId != null && !rateLimiter.allowRequest("user:" + userId, 100, 60)) {
            exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
            return exchange.getResponse().setComplete();
        }
        
        return chain.filter(exchange);
    }
}
```

### 🔹 API-Based Limiting

```java
@RestController
@RequestMapping("/api")
@RateLimit(maxRequests = 50, windowSeconds = 60)
public class ApiController {
    
    @GetMapping("/data")
    public List<Data> getData() {
        // API endpoint with rate limiting
        return dataService.getData();
    }
}

// Custom annotation
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {
    int maxRequests() default 100;
    int windowSeconds() default 60;
}
```

### 🔹 Response Headers

**Communicating limits to clients**

```java
@Component
public class RateLimitResponseFilter implements GlobalFilter {
    
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return chain.filter(exchange)
            .then(Mono.fromRunnable(() -> {
                ServerHttpResponse response = exchange.getResponse();
                
                // Add rate limit headers
                response.getHeaders().add("X-Rate-Limit-Limit", "100");
                response.getHeaders().add("X-Rate-Limit-Remaining", "95");
                response.getHeaders().add("X-Rate-Limit-Reset", "1640995200");
            }));
    }
}
```

### 🔹 Best Practices

```java
// Use distributed rate limiting for microservices
// Implement different limits for different user tiers
// Use Redis for distributed counting
// Set appropriate burst allowances
// Monitor rate limiting metrics
// Implement gradual backoff for clients
// Use multiple rate limiters for different resources
// Document rate limits clearly
```

### 🔹 Common Use Cases

**When to implement rate limiting**

- **API Protection**: Prevent API abuse
- **Resource Management**: Control resource usage
- **Cost Control**: Manage cloud resource costs
- **Fair Usage**: Ensure equitable access
- **Security**: Prevent DoS attacks
- **Performance**: Maintain system responsiveness

### 🔹 Testing Rate Limits

**Testing rate limiting behavior**

```java
@SpringBootTest
public class RateLimitTest {
    
    @Autowired
    private WebTestClient webClient;
    
    @Test
    public void shouldEnforceRateLimit() {
        // Send requests up to the limit
        for (int i = 0; i < 10; i++) {
            webClient.get().uri("/api/data")
                .header("X-User-Id", "test-user")
                .exchange()
                .expectStatus().isOk();
        }
        
        // Next request should be rate limited
        webClient.get().uri("/api/data")
            .header("X-User-Id", "test-user")
            .exchange()
            .expectStatus().isEqualTo(HttpStatus.TOO_MANY_REQUESTS);
    }
}
```

---

## 🎯 Interview One-Liner

> Rate Limiting: controls request frequency to prevent abuse/overload; algorithms: token bucket (smooth), leaky bucket (bursty), fixed/sliding window; protects resources and ensures fair usage.

---

## ✅ 94. Synchronous vs Asynchronous communication

**Synchronous communication requires the sender to wait for a response before continuing, while asynchronous communication allows the sender to continue processing without waiting, using message queues or events for decoupling.**

### 🔹 Synchronous Communication

**Blocking communication**

Synchronous communication:

- **Request-Response**: Sender waits for receiver response
- **Blocking**: Execution pauses until response received
- **Tight Coupling**: Direct dependency between services
- **Immediate Response**: Real-time interaction
- **Simple Error Handling**: Direct exception propagation

```java
// Synchronous REST call
@Service
public class OrderService {
    
    @Autowired
    private RestTemplate restTemplate;
    
    public Order createOrder(OrderRequest request) {
        // Synchronous call - blocks until response
        User user = restTemplate.getForObject(
            "http://user-service/users/" + request.getUserId(), 
            User.class);
        
        // Process order after getting user
        Order order = new Order(user, request.getItems());
        return orderRepository.save(order);
    }
}

// Synchronous Feign client
@FeignClient("user-service")
public interface UserServiceClient {
    
    @GetMapping("/users/{id}")
    User getUser(@PathVariable Long id);  // Blocks until response
}
```

### 🔹 Asynchronous Communication

**Non-blocking communication**

Asynchronous communication:

- **Fire and Forget**: Sender continues without waiting
- **Event-Driven**: Message-based interaction
- **Loose Coupling**: Services communicate via messages
- **Scalability**: Better resource utilization
- **Resilience**: Better fault tolerance

```java
// Asynchronous with @Async
@Service
public class OrderService {
    
    @Autowired
    private ApplicationEventPublisher publisher;
    
    @Transactional
    public void createOrderAsync(OrderRequest request) {
        Order order = orderRepository.save(new Order(request));
        
        // Publish event asynchronously
        publisher.publishEvent(new OrderCreatedEvent(order));
    }
}

// Event listener
@Component
public class OrderCreatedEventHandler {
    
    @Async
    @EventListener
    public void handleOrderCreated(OrderCreatedEvent event) {
        // Process order asynchronously
        inventoryService.reserveItems(event.getOrder().getItems());
        shippingService.arrangeShipping(event.getOrder());
    }
}
```

### 🔹 Message Queues

**Asynchronous messaging**

```java
// RabbitMQ producer
@Service
public class OrderMessageProducer {
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    public void sendOrderCreatedMessage(Order order) {
        rabbitTemplate.convertAndSend(
            "order-exchange", 
            "order.created", 
            order);
    }
}

// RabbitMQ consumer
@Component
public class OrderMessageConsumer {
    
    @RabbitListener(queues = "order-queue")
    public void handleOrderCreated(Order order) {
        // Process order asynchronously
        inventoryService.reserveItems(order.getItems());
        notificationService.sendOrderConfirmation(order);
    }
}
```

### 🔹 Key Differences

| Aspect | Synchronous | Asynchronous |
|--------|-------------|--------------|
| **Coupling** | Tight coupling | Loose coupling |
| **Performance** | May block threads | Non-blocking |
| **Scalability** | Limited by response time | Better scalability |
| **Error Handling** | Direct exceptions | Message retries/dlq |
| **Complexity** | Simpler | More complex |
| **Consistency** | Immediate consistency | Eventual consistency |
| **Monitoring** | Easier | More complex |

### 🔹 When to Use Synchronous

**Appropriate for sync communication**

- **Real-time Requirements**: Immediate responses needed
- **Simple Interactions**: Straightforward request-response
- **Strong Consistency**: Data must be consistent immediately
- **Simple Error Handling**: Direct error propagation
- **Low Latency**: Minimal delay requirements
- **Simple Orchestration**: Linear process flows

### 🔹 When to Use Asynchronous

**Appropriate for async communication**

- **High Throughput**: Handle many concurrent requests
- **Decoupled Services**: Independent service evolution
- **Eventual Consistency**: Acceptable data lag
- **Complex Workflows**: Saga patterns, complex orchestrations
- **Background Processing**: Non-critical background tasks
- **Scalability Requirements**: Handle variable loads

### 🔹 Hybrid Approach

**Combining both patterns**

```java
@Service
public class OrderService {
    
    public Order createOrder(OrderRequest request) {
        // Synchronous validation
        User user = userService.getUser(request.getUserId());
        validateUser(user);
        
        // Asynchronous processing
        CompletableFuture.runAsync(() -> {
            processOrderAsync(request);
        });
        
        // Return immediately
        return new Order(user, request.getItems(), OrderStatus.PENDING);
    }
    
    @Async
    private void processOrderAsync(OrderRequest request) {
        // Heavy processing asynchronously
        inventoryService.reserveItems(request.getItems());
        paymentService.processPayment(request.getPayment());
        shippingService.arrangeShipping(request);
    }
}
```

### 🔹 Communication Patterns

**Different async patterns**

### 🔹 Publish-Subscribe

```java
// Publisher
@Service
public class ProductService {
    
    @Autowired
    private ApplicationEventPublisher publisher;
    
    public Product updateProduct(ProductUpdate update) {
        Product product = productRepository.save(update.toProduct());
        publisher.publishEvent(new ProductUpdatedEvent(product));
        return product;
    }
}

// Multiple subscribers
@Component
public class InventoryUpdater {
    @EventListener
    public void updateInventory(ProductUpdatedEvent event) {
        inventoryService.updateStock(event.getProduct());
    }
}

@Component
public class SearchIndexUpdater {
    @EventListener  
    public void updateSearchIndex(ProductUpdatedEvent event) {
        searchService.updateIndex(event.getProduct());
    }
}
```

### 🔹 Request-Reply Async

```java
// Async request with reply
@Service
public class PaymentService {
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    public CompletableFuture<PaymentResult> processPaymentAsync(PaymentRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            // Send request and wait for reply
            PaymentResult result = (PaymentResult) rabbitTemplate.convertSendAndReceive(
                "payment-exchange",
                "payment.process",
                request);
            return result;
        });
    }
}
```

### 🔹 Best Practices

```java
// Use sync for real-time, user-facing operations
// Use async for background processing and decoupling
// Implement proper error handling for async operations
// Use correlation IDs for request tracing
// Monitor message queues and processing times
// Implement dead letter queues for failed messages
// Use timeouts for async operations
// Document communication patterns
```

### 🔹 Testing Considerations

**Testing sync vs async**

```java
// Synchronous testing
@Test
public void shouldCreateOrderSynchronously() {
    User user = new User("John");
    when(userService.getUser(1L)).thenReturn(user);
    
    Order order = orderService.createOrder(request);
    assertNotNull(order);
}

// Asynchronous testing
@Test
public void shouldProcessOrderAsynchronously() {
    orderService.createOrderAsync(request);
    
    // Wait for async processing
    await().atMost(5, SECONDS).until(() -> 
        orderRepository.findById(orderId).getStatus() == OrderStatus.PROCESSED);
}
```

---

## 🎯 Interview One-Liner

> Synchronous: blocking request-response, tight coupling, immediate consistency; Asynchronous: non-blocking, event-driven, loose coupling, eventual consistency, better scalability.

---

## ✅ 95. REST vs gRPC vs GraphQL

**REST, gRPC, and GraphQL are different API communication protocols: REST uses HTTP with resources and verbs, gRPC uses HTTP/2 with protocol buffers for efficient RPC, and GraphQL uses a query language for flexible data fetching.**

### 🔹 REST (Representational State Transfer)

**Resource-based HTTP APIs**

REST characteristics:

- **HTTP Methods**: GET, POST, PUT, DELETE
- **Resources**: URI-based resource identification
- **Stateless**: No server-side session state
- **Uniform Interface**: Standard HTTP conventions
- **JSON/XML**: Common data formats

```java
// REST Controller
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(user);
    }
    
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User created = userService.create(user);
        return ResponseEntity.created(
            ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri())
            .body(created);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        User updated = userService.update(id, user);
        return ResponseEntity.ok(updated);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
```

### 🔹 gRPC (Google Remote Procedure Call)

**High-performance RPC framework**

gRPC features:

- **Protocol Buffers**: Efficient binary serialization
- **HTTP/2**: Multiplexing, streaming, headers compression
- **Strong Typing**: Generated client/server code
- **Bidirectional Streaming**: Real-time communication
- **Built-in Features**: Authentication, load balancing, retries

```java
// Protocol Buffer definition
syntax = "proto3";

service UserService {
    rpc GetUser (GetUserRequest) returns (UserResponse);
    rpc CreateUser (CreateUserRequest) returns (UserResponse);
    rpc UpdateUser (UpdateUserRequest) returns (UserResponse);
    rpc DeleteUser (DeleteUserRequest) returns (Empty);
    rpc GetUsers (GetUsersRequest) returns (stream UserResponse); // Server streaming
}

message GetUserRequest {
    int64 id = 1;
}

message UserResponse {
    int64 id = 1;
    string name = 2;
    string email = 3;
}

// Generated Java service
public class UserServiceImpl extends UserServiceGrpc.UserServiceImplBase {
    
    @Override
    public void getUser(GetUserRequest request, StreamObserver<UserResponse> responseObserver) {
        User user = userService.findById(request.getId());
        UserResponse response = UserResponse.newBuilder()
            .setId(user.getId())
            .setName(user.getName())
            .setEmail(user.getEmail())
            .build();
        
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
```

### 🔹 GraphQL

**Query language for APIs**

GraphQL characteristics:

- **Single Endpoint**: POST to /graphql
- **Flexible Queries**: Client specifies needed data
- **Schema-Driven**: Strongly typed schema
- **Real-time**: Subscriptions for live updates
- **Introspective**: Self-documenting APIs

```java
// GraphQL Schema
type Query {
    user(id: ID!): User
    users(limit: Int): [User!]!
}

type Mutation {
    createUser(input: CreateUserInput!): User!
    updateUser(id: ID!, input: UpdateUserInput!): User!
    deleteUser(id: ID!): Boolean!
}

type User {
    id: ID!
    name: String!
    email: String!
    orders: [Order!]!
}

input CreateUserInput {
    name: String!
    email: String!
}

// GraphQL Resolver
@Component
public class UserResolver implements GraphQLQueryResolver, GraphQLMutationResolver {
    
    public User user(Long id) {
        return userService.findById(id);
    }
    
    public List<User> users(Integer limit) {
        return userService.findAll(PageRequest.of(0, limit != null ? limit : 10));
    }
    
    public User createUser(CreateUserInput input) {
        User user = new User(input.getName(), input.getEmail());
        return userService.create(user);
    }
    
    public User updateUser(Long id, UpdateUserInput input) {
        User user = userService.findById(id);
        user.setName(input.getName());
        user.setEmail(input.getEmail());
        return userService.update(user);
    }
    
    public Boolean deleteUser(Long id) {
        userService.delete(id);
        return true;
    }
}
```

### 🔹 Key Comparisons

| Aspect | REST | gRPC | GraphQL |
|--------|------|------|---------|
| **Protocol** | HTTP/1.1 | HTTP/2 | HTTP/1.1/2 |
| **Data Format** | JSON/XML | Protocol Buffers | JSON |
| **Typing** | Weak | Strong | Strong |
| **Performance** | Good | Excellent | Good |
| **Flexibility** | Limited | Fixed contracts | High |
| **Caching** | HTTP caching | Limited | Application-level |
| **Browser Support** | Excellent | Limited | Good |
| **Learning Curve** | Low | Medium | Medium |
| **Ecosystem** | Mature | Growing | Growing |

### 🔹 Use Cases

**When to choose each**

- **REST**: Traditional web APIs, public APIs, simple CRUD operations
- **gRPC**: Internal microservices, high-performance requirements, real-time streaming
- **GraphQL**: Complex data requirements, mobile apps, reducing over/under-fetching

### 🔹 Performance Comparison

**Efficiency metrics**

```java
// REST payload (JSON)
{
    "id": 1,
    "name": "John Doe",
    "email": "john@example.com",
    "orders": [
        {
            "id": 100,
            "total": 99.99,
            "items": [...]
        }
    ]
}

// gRPC payload (Protocol Buffers) - More compact
// Serialized binary format, typically 30-60% smaller

// GraphQL query - Client specifies exactly what they need
query GetUser($id: ID!) {
    user(id: $id) {
        name
        email
        orders {
            total
        }
    }
}
```

### 🔹 Error Handling

**Different error approaches**

```java
// REST error response
{
    "error": {
        "code": "USER_NOT_FOUND",
        "message": "User with id 123 not found",
        "status": 404
    }
}

// gRPC error
StatusRuntimeException: NOT_FOUND: User with id 123 not found

// GraphQL errors
{
    "data": null,
    "errors": [
        {
            "message": "User with id 123 not found",
            "locations": [{"line": 3, "column": 5}],
            "path": ["user"]
        }
    ]
}
```

### 🔹 Security Considerations

**Authentication and authorization**

```java
// REST - JWT in Authorization header
@GetMapping("/users/{id}")
public User getUser(@PathVariable Long id, 
                   @RequestHeader("Authorization") String token) {
    // Validate JWT token
}

// gRPC - Metadata/interceptors
public void getUser(GetUserRequest request, 
                   StreamObserver<UserResponse> responseObserver) {
    // Access metadata from context
    String token = Constants.METADATA_AUTHORIZATION_KEY.get();
}

// GraphQL - Custom directives/context
public User user(Long id, DataFetchingEnvironment env) {
    // Access context for authentication
    GraphQLContext context = env.getContext();
    User currentUser = (User) context.get("currentUser");
}
```

### 🔹 Best Practices

```java
// Choose based on use case requirements
// REST for public APIs and simplicity
// gRPC for internal services and performance
// GraphQL for complex data requirements
// Consider team expertise and ecosystem
// Implement proper versioning for all
// Use appropriate authentication mechanisms
// Monitor performance and usage patterns
```

---

## 🎯 Interview One-Liner

> REST: resource-based HTTP APIs with JSON; gRPC: high-performance RPC with Protocol Buffers; GraphQL: flexible query language for precise data fetching.

---

## ✅ 96. What is Message Queue? (RabbitMQ/Kafka)

**Message Queue is an asynchronous communication mechanism where messages are sent to a queue for processing by consumers, decoupling producers and consumers, enabling scalability, reliability, and fault tolerance.**

### 🔹 What is Message Queue?

**Asynchronous messaging system**

Message queues provide:

- **Decoupling**: Producers and consumers work independently
- **Buffering**: Handle load spikes and temporary failures
- **Reliability**: Messages persist until processed
- **Scalability**: Multiple consumers can process messages
- **Fault Tolerance**: System continues working during failures

### 🔹 RabbitMQ

**Advanced message broker**

RabbitMQ features:

- **AMQP Protocol**: Industry standard messaging protocol
- **Exchange Types**: Direct, Topic, Headers, Fanout
- **Queues**: Durable, temporary, exclusive queues
- **Bindings**: Connect exchanges to queues
- **Clustering**: High availability and scalability

```java
@Configuration
public class RabbitMQConfig {
    
    @Bean
    public Queue orderQueue() {
        return new Queue("order.queue", true); // Durable queue
    }
    
    @Bean
    public TopicExchange orderExchange() {
        return new TopicExchange("order.exchange");
    }
    
    @Bean
    public Binding orderBinding(Queue orderQueue, TopicExchange orderExchange) {
        return BindingBuilder.bind(orderQueue).to(orderExchange).with("order.created");
    }
}

// Producer
@Service
public class OrderMessageProducer {
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    public void sendOrderCreatedMessage(Order order) {
        rabbitTemplate.convertAndSend("order.exchange", "order.created", order);
    }
}

// Consumer
@Component
public class OrderMessageConsumer {
    
    @RabbitListener(queues = "order.queue")
    public void handleOrderCreated(Order order) {
        // Process order
        inventoryService.reserveItems(order.getItems());
        notificationService.sendOrderConfirmation(order);
    }
}
```

### 🔹 Apache Kafka

**Distributed streaming platform**

Kafka characteristics:

- **Topics**: Categories for organizing messages
- **Partitions**: Parallel processing within topics
- **Consumer Groups**: Scalable message consumption
- **Retention**: Configurable message retention
- **Streaming**: Real-time data processing capabilities

```java
@Configuration
public class KafkaConfig {
    
    @Bean
    public ProducerFactory<String, Order> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }
    
    @Bean
    public KafkaTemplate<String, Order> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
    
    @Bean
    public ConsumerFactory<String, Order> consumerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "order-group");
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(configProps);
    }
    
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Order> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Order> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}

// Producer
@Service
public class OrderEventProducer {
    
    @Autowired
    private KafkaTemplate<String, Order> kafkaTemplate;
    
    public void sendOrderCreatedEvent(Order order) {
        kafkaTemplate.send("order-events", order.getId().toString(), order);
    }
}

// Consumer
@Component
public class OrderEventConsumer {
    
    @KafkaListener(topics = "order-events", groupId = "order-processing")
    public void handleOrderCreated(Order order) {
        // Process order event
        inventoryService.updateInventory(order);
        shippingService.scheduleDelivery(order);
    }
}
```

### 🔹 Message Patterns

**Different messaging patterns**

### 🔹 Point-to-Point

**One producer, one consumer**

```java
// Queue-based messaging
@Configuration
public class QueueConfig {
    
    @Bean
    public Queue taskQueue() {
        return new Queue("task.queue");
    }
}

// Producer sends task
@Service
public class TaskProducer {
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    public void sendTask(Task task) {
        rabbitTemplate.convertAndSend("task.queue", task);
    }
}

// Single consumer processes task
@Component
public class TaskConsumer {
    
    @RabbitListener(queues = "task.queue")
    public void processTask(Task task) {
        // Process task
        taskService.execute(task);
    }
}
```

### 🔹 Publish-Subscribe

**One producer, multiple consumers**

```java
// Topic-based messaging
@Configuration
public class TopicConfig {
    
    @Bean
    public TopicExchange eventExchange() {
        return new TopicExchange("event.exchange");
    }
    
    @Bean
    public Queue userEventQueue() {
        return new Queue("user.event.queue");
    }
    
    @Bean
    public Queue auditEventQueue() {
        return new Queue("audit.event.queue");
    }
    
    @Bean
    public Binding userBinding(Queue userEventQueue, TopicExchange eventExchange) {
        return BindingBuilder.bind(userEventQueue).to(eventExchange).with("user.*");
    }
    
    @Bean
    public Binding auditBinding(Queue auditEventQueue, TopicExchange eventExchange) {
        return BindingBuilder.bind(auditEventQueue).to(eventExchange).with("*.audit");
    }
}

// Publisher
@Service
public class EventPublisher {
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    public void publishUserCreatedEvent(User user) {
        rabbitTemplate.convertAndSend("event.exchange", "user.created", user);
    }
    
    public void publishUserAuditEvent(AuditEvent event) {
        rabbitTemplate.convertAndSend("event.exchange", "user.audit", event);
    }
}
```

### 🔹 Message Reliability

**Ensuring message delivery**

```java
// Message acknowledgment
@Component
public class ReliableConsumer {
    
    @RabbitListener(queues = "order.queue", ackMode = "MANUAL")
    public void handleOrder(Order order, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        try {
            // Process order
            orderService.process(order);
            
            // Acknowledge successful processing
            channel.basicAck(tag, false);
            
        } catch (Exception e) {
            // Reject and requeue or send to dead letter queue
            channel.basicNack(tag, false, false);
        }
    }
}

// Dead letter queue configuration
@Configuration
public class DLQConfig {
    
    @Bean
    public Queue orderQueue() {
        return QueueBuilder.durable("order.queue")
            .withArgument("x-dead-letter-exchange", "dlx.exchange")
            .withArgument("x-dead-letter-routing-key", "order.failed")
            .build();
    }
    
    @Bean
    public Queue deadLetterQueue() {
        return new Queue("order.dlq");
    }
}
```

### 🔹 Monitoring and Observability

**Message queue monitoring**

```java
@RestController
public class MessageQueueMetricsController {
    
    @Autowired
    private RabbitAdmin rabbitAdmin;
    
    @GetMapping("/queue/metrics")
    public Map<String, Object> getQueueMetrics() {
        Properties queueProperties = rabbitAdmin.getQueueProperties("order.queue");
        Map<String, Object> metrics = new HashMap<>();
        
        metrics.put("messageCount", queueProperties.get("QUEUE_MESSAGE_COUNT"));
        metrics.put("consumerCount", queueProperties.get("QUEUE_CONSUMER_COUNT"));
        
        return metrics;
    }
}
```

### 🔹 Best Practices

```java
// Use appropriate message broker for use case
// Implement proper error handling and retries
// Use dead letter queues for failed messages
// Monitor queue depths and processing rates
// Implement message versioning
// Use correlation IDs for request tracing
// Configure appropriate TTL for messages
// Implement circuit breakers for message processing
```

### 🔹 Common Challenges

**Message queue issues**

- **Message Ordering**: Ensure order when required
- **Duplicate Messages**: Handle idempotent processing
- **Message Loss**: Implement proper acknowledgment
- **Performance**: Monitor throughput and latency
- **Scalability**: Handle increasing message volumes
- **Monitoring**: Track message flow and errors

---

## 🎯 Interview One-Liner

> Message Queue: asynchronous communication buffer; RabbitMQ: flexible routing with exchanges/queues; Kafka: high-throughput distributed streaming with topics/partitions.

---

## ✅ 97. What is Service Mesh? (Istio)

**Service Mesh is an infrastructure layer that manages service-to-service communication in microservices architectures, providing features like traffic management, security, observability, and resilience without requiring code changes.**

### 🔹 What is Service Mesh?

**Service communication infrastructure**

Service mesh provides:

- **Traffic Management**: Load balancing, routing, circuit breaking
- **Security**: mTLS, authentication, authorization
- **Observability**: Metrics, tracing, logging
- **Resilience**: Retries, timeouts, fault injection
- **Service Discovery**: Automatic service location
- **Policy Enforcement**: Traffic policies and rules

### 🔹 Istio Architecture

**Istio components**

```yaml
# Istio components overview
apiVersion: v1
kind: Service
metadata:
  name: istio-pilot
spec:
  # Pilot: Service discovery, configuration, certificates

---
apiVersion: v1
kind: Service  
metadata:
  name: istio-mixer
spec:
  # Mixer: Policy enforcement, telemetry

---
apiVersion: v1
kind: Service
metadata:
  name: istio-citadel
spec:
  # Citadel: Certificate management, security

---
apiVersion: v1
kind: Service
metadata:
  name: istio-galley
spec:
  # Galley: Configuration validation, distribution
```

### 🔹 Data Plane vs Control Plane

**Istio architecture layers**

- **Data Plane**: Envoy proxies handling traffic
- **Control Plane**: Istio components managing proxies

```yaml
# Envoy proxy configuration (simplified)
static_resources:
  listeners:
  - address:
      socket_address:
        address: 0.0.0.0
        port_value: 8080
    filter_chains:
    - filters:
      - name: envoy.http_connection_manager
        config:
          route_config:
            routes:
            - match:
                prefix: "/api"
              route:
                cluster: user-service
          http_filters:
          - name: envoy.router

  clusters:
  - name: user-service
    connect_timeout: 0.25s
    type: STRICT_DNS
    lb_policy: ROUND_ROBIN
    hosts:
    - socket_address:
        address: user-service
        port_value: 8080
```

### 🔹 Traffic Management

**Routing and load balancing**

```yaml
# Virtual Service for routing
apiVersion: networking.istio.io/v1beta1
kind: VirtualService
metadata:
  name: user-service-routing
spec:
  hosts:
  - user-service
  http:
  - match:
    - headers:
        x-user-type:
          exact: premium
    route:
    - destination:
        host: user-service
        subset: premium
  - route:  # Default route
    - destination:
        host: user-service
        subset: standard

---
# Destination Rule for subsets
apiVersion: networking.istio.io/v1beta1
kind: DestinationRule
metadata:
  name: user-service-subsets
spec:
  host: user-service
  subsets:
  - name: premium
    labels:
      version: premium
  - name: standard
    labels:
      version: standard
```

### 🔹 Load Balancing

**Traffic distribution**

```yaml
# Load balancing configuration
apiVersion: networking.istio.io/v1beta1
kind: DestinationRule
metadata:
  name: load-balancing-config
spec:
  host: payment-service
  trafficPolicy:
    loadBalancer:
      simple: LEAST_REQUEST  # Options: ROUND_ROBIN, LEAST_CONN, RANDOM, PASSTHROUGH
    connectionPool:
      tcp:
        maxConnections: 100
      http:
        http2MaxRequests: 1000
        maxRequestsPerConnection: 10
```

### 🔹 Circuit Breaking

**Failure protection**

```yaml
# Circuit breaker configuration
apiVersion: networking.istio.io/v1beta1
kind: DestinationRule
metadata:
  name: circuit-breaker
spec:
  host: inventory-service
  trafficPolicy:
    connectionPool:
      tcp:
        maxConnections: 100
      http:
        http1MaxPendingRequests: 10
        http2MaxRequests: 100
        maxRequestsPerConnection: 10
        maxRetries: 3
    outlierDetection:
      consecutive5xxErrors: 3
      interval: 10s
      baseEjectionTime: 30s
      maxEjectionPercent: 50
```

### 🔹 Security

**mTLS and authentication**

```yaml
# Peer authentication (mTLS)
apiVersion: security.istio.io/v1beta1
kind: PeerAuthentication
metadata:
  name: default
  namespace: istio-system
spec:
  mtls:
    mode: STRICT  # Options: PERMISSIVE, STRICT

---
# Request authentication
apiVersion: security.istio.io/v1beta1
kind: RequestAuthentication
metadata:
  name: jwt-auth
spec:
  selector:
    matchLabels:
      app: api-gateway
  jwtRules:
  - issuer: "https://accounts.google.com"
    jwksUri: "https://www.googleapis.com/oauth2/v3/certs"

---
# Authorization policy
apiVersion: security.istio.io/v1beta1
kind: AuthorizationPolicy
metadata:
  name: api-access
spec:
  selector:
    matchLabels:
      app: user-service
  rules:
  - from:
    - source:
        principals: ["cluster.local/ns/default/sa/api-gateway"]
    to:
    - operation:
        methods: ["GET", "POST"]
```

### 🔹 Observability

**Metrics and tracing**

```yaml
# Telemetry configuration
apiVersion: telemetry.istio.io/v1alpha1
kind: Telemetry
metadata:
  name: mesh-default
  namespace: istio-system
spec:
  metrics:
  - providers:
    - name: prometheus
    overrides:
    - match:
        metric: REQUEST_COUNT
      mode: CLIENT_AND_SERVER
  tracing:
  - providers:
    - name: jaeger
    randomSamplingPercentage: 100.0
```

### 🔹 Fault Injection

**Testing resilience**

```yaml
# Fault injection for testing
apiVersion: networking.istio.io/v1beta1
kind: VirtualService
metadata:
  name: fault-injection
spec:
  hosts:
  - payment-service
  http:
  - fault:
      delay:
        percentage:
          value: 50.0
        fixedDelay: 5s
      abort:
        percentage:
          value: 10.0
        httpStatus: 500
    route:
    - destination:
        host: payment-service
```

### 🔹 Service Mesh vs API Gateway

**Different responsibilities**

- **API Gateway**: External client requests, protocol translation
- **Service Mesh**: Internal service-to-service communication

### 🔹 Alternatives to Istio

**Other service mesh options**

- **Linkerd**: Lightweight, Rust-based
- **Consul Connect**: HashiCorp's service mesh
- **AWS App Mesh**: AWS managed service mesh
- **Kuma**: Universal service mesh

### 🔹 Best Practices

```java
// Start with minimal configuration
// Use namespaces for environment isolation
// Implement proper monitoring and alerting
// Test fault injection scenarios regularly
// Use mTLS in production
// Implement proper authorization policies
// Monitor service mesh performance
// Keep Istio version up to date
```

### 🔹 Common Challenges

**Service mesh complexity**

- **Resource Overhead**: Proxy resource consumption
- **Configuration Complexity**: YAML configuration management
- **Debugging Difficulty**: Additional network layer
- **Learning Curve**: New concepts and tools
- **Performance Impact**: Slight latency increase

---

## 🎯 Interview One-Liner

> Service Mesh: infrastructure layer for microservice communication; Istio provides traffic management, security, observability via Envoy proxies managed by control plane.

---

## ✅ 98. What is Spring Cloud?

**Spring Cloud is a framework that provides tools for building distributed systems and microservices, offering patterns for configuration management, service discovery, circuit breakers, and distributed tracing.**

### 🔹 Spring Cloud Overview

**Microservices development framework**

Spring Cloud provides:

- **Service Discovery**: Eureka, Consul, Zookeeper
- **Configuration Management**: Spring Cloud Config
- **Circuit Breakers**: Hystrix, Resilience4j
- **API Gateway**: Spring Cloud Gateway, Zuul
- **Load Balancing**: Ribbon
- **Distributed Tracing**: Sleuth, Zipkin
- **Message Bus**: Spring Cloud Stream

### 🔹 Spring Cloud Components

**Key modules and features**

```xml
<!-- Spring Cloud BOM for dependency management -->
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>2021.0.5</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>

<!-- Common dependencies -->
<dependencies>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-config</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-gateway</artifactId>
    </dependency>
</dependencies>
```

### 🔹 Service Discovery with Eureka

**Automatic service registration**

```java
// Eureka Server
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}

// Eureka Client
@SpringBootApplication
@EnableEurekaClient
public class UserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}
```

### 🔹 Configuration Management

**Centralized configuration**

```java
// Config Server
@SpringBootApplication
@EnableConfigServer
public class ConfigServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }
}

// Client configuration
@Configuration
public class ConfigClientConfig {
    
    @Value("${database.url}")
    private String databaseUrl;
    
    @Value("${service.timeout:5000}")
    private int timeout;
}
```

### 🔹 Circuit Breaker with Hystrix

**Fault tolerance**

```java
@Service
public class PaymentService {
    
    @HystrixCommand(fallbackMethod = "paymentFallback",
                   commandProperties = {
                       @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000"),
                       @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10")
                   })
    public Payment processPayment(PaymentRequest request) {
        return restTemplate.postForObject("http://payment-service/process", request, Payment.class);
    }
    
    public Payment paymentFallback(PaymentRequest request) {
        return new Payment("fallback-id", PaymentStatus.PENDING);
    }
}
```

### 🔹 API Gateway

**Request routing and filtering**

```java
@SpringBootApplication
@EnableZuulProxy
public class ApiGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
}

// Gateway configuration
zuul:
  routes:
    user-service:
      path: /users/**
      serviceId: user-service
    product-service:
      path: /products/**
      serviceId: product-service
```

### 🔹 Load Balancing with Ribbon

**Client-side load balancing**

```java
@Configuration
@RibbonClient(name = "user-service")
public class RibbonConfig {
    
    @Bean
    public IRule loadBalancingRule() {
        return new WeightedResponseTimeRule(); // Load balancing strategy
    }
}

@Service
public class UserServiceClient {
    
    @Autowired
    private RestTemplate restTemplate;
    
    public User getUser(Long id) {
        // Ribbon automatically load balances
        return restTemplate.getForObject("http://user-service/users/" + id, User.class);
    }
}
```

### 🔹 Distributed Tracing

**Request tracing across services**

```java
@SpringBootApplication
public class TracingApplication {
    // Sleuth automatically adds trace IDs
}

@RestController
public class UserController {
    
    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Long id) {
        logger.info("Getting user with id: {}", id);
        // Trace ID automatically included in logs
        return userService.findById(id);
    }
}
```

### 🔹 Spring Cloud Stream

**Event-driven microservices**

```java
@SpringBootApplication
@EnableBinding({Processor.class})
public class MessageProcessingApplication {
    
    @StreamListener(Processor.INPUT)
    @SendTo(Processor.OUTPUT)
    public String processMessage(String message) {
        return message.toUpperCase();
    }
}
```

### 🔹 Spring Cloud vs Spring Boot

**Relationship and differences**

- **Spring Boot**: Framework for building individual applications
- **Spring Cloud**: Tools for building distributed systems with Spring Boot

### 🔹 Migration to Spring Cloud

**Adopting Spring Cloud incrementally**

```java
// Phase 1: Add service discovery
@EnableEurekaClient
@SpringBootApplication
public class Application { }

// Phase 2: Add configuration management
@EnableConfigClient
@SpringBootApplication  
public class Application { }

// Phase 3: Add API gateway
@EnableZuulProxy
@SpringBootApplication
public class Application { }
```

### 🔹 Best Practices

```java
// Start with Spring Boot, then add Spring Cloud
// Use appropriate starters for dependencies
// Configure timeouts and circuit breakers
// Implement proper logging and monitoring
// Use centralized configuration
// Implement service discovery
// Use API gateway for external access
// Monitor distributed tracing
```

### 🔹 Common Patterns

**Spring Cloud usage patterns**

- **Microservices Template**: Standard microservice setup
- **API Gateway Pattern**: Single entry point
- **Config Server Pattern**: Centralized configuration
- **Service Registry Pattern**: Service discovery
- **Circuit Breaker Pattern**: Fault tolerance

---

## 🎯 Interview One-Liner

> Spring Cloud: framework for microservices with service discovery (Eureka), config management, circuit breakers (Hystrix), API gateway (Zuul), load balancing (Ribbon).

---

## ✅ 99. Explain Spring Cloud Config

**Spring Cloud Config provides server and client-side support for externalized configuration in a distributed system, allowing applications to pull configuration from a central repository.**

### 🔹 Config Server Setup

**Centralized configuration server**

```java
@SpringBootApplication
@EnableConfigServer
public class ConfigServerApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }
}

// Application.yml
server:
  port: 8888

spring:
  cloud:
    config:
      server:
        git:
          uri: https://github.com/myorg/config-repo
          search-paths: '{application}'
```

### 🔹 Configuration Repository

**Git-based configuration storage**

```
config-repo/
├── application.yml          # Common configuration
├── application-dev.yml      # Development overrides
├── application-prod.yml     # Production overrides
├── user-service.yml         # Service-specific config
├── user-service-dev.yml     # Service dev overrides
└── user-service-prod.yml    # Service prod overrides
```

```yaml
# application.yml (common)
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    hibernate:
      ddl-auto: validate

logging:
  level:
    com.example: INFO

# user-service.yml (service-specific)
server:
  port: 8081

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/userdb
    username: ${DB_USER:user}
    password: ${DB_PASSWORD:password}

user-service:
  features:
    email-verification: true
    two-factor-auth: false
```

### 🔹 Config Client

**Consuming configuration**

```java
@SpringBootApplication
public class UserServiceApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}

// Bootstrap.yml (loaded before application.yml)
spring:
  application:
    name: user-service
  profiles:
    active: dev
  cloud:
    config:
      uri: http://localhost:8888
      fail-fast: true
      retry:
        max-attempts: 10
```

### 🔹 Configuration Properties

**Accessing configuration values**

```java
@Component
@ConfigurationProperties(prefix = "user-service")
public class UserServiceProperties {
    
    private boolean emailVerification;
    private boolean twoFactorAuth;
    private int sessionTimeout = 30;
    
    // getters and setters
}

@Service
public class UserService {
    
    @Autowired
    private UserServiceProperties properties;
    
    @Value("${spring.datasource.url}")
    private String databaseUrl;
    
    public void createUser(User user) {
        if (properties.isEmailVerification()) {
            // Send verification email
            emailService.sendVerificationEmail(user);
        }
    }
}
```

### 🔹 Profile-Specific Configuration

**Environment-specific settings**

```yaml
# application-dev.yml
spring:
  datasource:
    url: jdbc:h2:mem:testdb
  jpa:
    hibernate:
      ddl-auto: create-drop

logging:
  level:
    com.example: DEBUG

# application-prod.yml  
spring:
  datasource:
    url: jdbc:mysql://prod-db:3306/proddb
    username: ${DB_USER}
    password: ${DB_PASSWORD}
  jpa:
    hibernate:
      ddl-auto: validate

logging:
  level:
    com.example: WARN
```

### 🔹 Encrypted Properties

**Securing sensitive configuration**

```yaml
# Config server configuration for encryption
encrypt:
  key: my-encryption-key

# Encrypted properties in config files
database:
  password: '{cipher}FKSAJDFGYOS8F7GLHAKERGFHLSAJ'
```

### 🔹 Refresh Configuration

**Dynamic configuration updates**

```java
@RestController
@RefreshScope
public class ConfigController {
    
    @Value("${user-service.session-timeout}")
    private int sessionTimeout;
    
    @GetMapping("/config")
    public Map<String, Object> getConfig() {
        return Map.of("sessionTimeout", sessionTimeout);
    }
}

// Manual refresh endpoint
// POST /actuator/refresh
```

### 🔹 Configuration Sources

**Different configuration backends**

```yaml
# Git repository (default)
spring:
  cloud:
    config:
      server:
        git:
          uri: https://github.com/myorg/config-repo
          username: ${GIT_USER}
          password: ${GIT_PASSWORD}

# File system
spring:
  cloud:
    config:
      server:
        native:
          search-locations: file:///path/to/config

# Database
spring:
  cloud:
    config:
      server:
        jdbc:
          sql: SELECT key, value FROM config_properties WHERE application=? AND profile=?
```

### 🔹 High Availability

**Config server clustering**

```yaml
# Multiple config servers
spring:
  cloud:
    config:
      uri: http://config-server-1:8888,http://config-server-2:8888
      fail-fast: true
```

### 🔹 Monitoring and Auditing

**Configuration monitoring**

```java
@RestController
public class ConfigMonitoringController {
    
    @Autowired
    private Environment environment;
    
    @GetMapping("/config/status")
    public Map<String, Object> getConfigStatus() {
        Map<String, Object> status = new HashMap<>();
        status.put("activeProfiles", environment.getActiveProfiles());
        status.put("propertySources", environment.getPropertySources().size());
        
        // Check if config server is reachable
        status.put("configServerReachable", checkConfigServerHealth());
        
        return status;
    }
}
```

### 🔹 Best Practices

```java
// Use Git for configuration versioning
// Separate common and service-specific configs
// Use profiles for environment-specific settings
// Encrypt sensitive properties
// Implement proper access control
// Use @RefreshScope for dynamic updates
// Monitor configuration loading
// Test configuration changes
```

### 🔹 Common Issues

**Configuration problems**

- **Config not loading**: Check bootstrap.yml configuration
- **Properties not refreshing**: Use @RefreshScope annotation
- **Git authentication**: Verify credentials
- **Profile precedence**: Understand property override order
- **Encryption issues**: Check encryption key configuration

---

## 🎯 Interview One-Liner

> Spring Cloud Config: centralized configuration management; server pulls from Git/repo, clients fetch config at startup; supports profiles, encryption, dynamic refresh.

---

## ✅ 100. What is Ribbon (Client-side load balancing)?

**Ribbon is a client-side load balancer from Netflix that provides load balancing, fault tolerance, and service discovery capabilities for inter-service communication in microservices architectures.**

### 🔹 Ribbon Overview

**Client-side load balancing**

Ribbon provides:

- **Load Balancing**: Distribute requests across service instances
- **Service Discovery**: Integration with Eureka
- **Fault Tolerance**: Automatic failover
- **Multiple Algorithms**: Round-robin, random, weighted
- **Custom Rules**: Pluggable load balancing strategies

### 🔹 Basic Configuration

**Enabling Ribbon**

```xml
<!-- Maven dependency -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
</dependency>
```

```java
@Configuration
@RibbonClient(name = "user-service")
public class RibbonConfig {
    // Ribbon configuration for user-service
}

@Service
public class UserServiceClient {
    
    @Autowired
    private RestTemplate restTemplate;
    
    public User getUser(Long id) {
        // Ribbon automatically resolves and load balances
        return restTemplate.getForObject("http://user-service/users/" + id, User.class);
    }
}
```

### 🔹 Load Balancing Rules

**Different load balancing algorithms**

```java
@Configuration
public class LoadBalancingConfig {
    
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    
    // Round Robin (default)
    @Bean
    public IRule roundRobinRule() {
        return new RoundRobinRule();
    }
    
    // Random
    @Bean
    public IRule randomRule() {
        return new RandomRule();
    }
    
    // Weighted Response Time
    @Bean
    public IRule weightedResponseTimeRule() {
        return new WeightedResponseTimeRule();
    }
    
    // Availability Filtering
    @Bean
    public IRule availabilityFilteringRule() {
        return new AvailabilityFilteringRule();
    }
    
    // Zone Avoidance
    @Bean
    public IRule zoneAvoidanceRule() {
        return new ZoneAvoidanceRule();
    }
}
```

### 🔹 Service Discovery Integration

**Working with Eureka**

```java
@SpringBootApplication
@EnableEurekaClient
@RibbonClient(name = "user-service")
public class OrderServiceApplication {
    
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

@Service
public class OrderService {
    
    @Autowired
    private RestTemplate restTemplate;
    
    public Order createOrder(CreateOrderRequest request) {
        // Ribbon + Eureka: resolves service name to instances
        User user = restTemplate.getForObject(
            "http://user-service/users/" + request.getUserId(), User.class);
        
        Product product = restTemplate.getForObject(
            "http://product-service/products/" + request.getProductId(), Product.class);
        
        return orderRepository.save(new Order(user, product, request.getQuantity()));
    }
}
```

### 🔹 Custom Load Balancing

**Implementing custom rules**

```java
public class CustomLoadBalancingRule extends AbstractLoadBalancerRule {
    
    @Override
    public Server choose(Object key) {
        return choose(getLoadBalancer(), key);
    }
    
    private Server choose(ILoadBalancer lb, Object key) {
        List<Server> reachableServers = lb.getReachableServers();
        List<Server> allServers = lb.getAllServers();
        
        // Custom logic: prefer servers with lower load
        return reachableServers.stream()
            .min((s1, s2) -> compareServerLoad(s1, s2))
            .orElse(null);
    }
    
    private int compareServerLoad(Server s1, Server s2) {
        // Implement load comparison logic
        return 0;
    }
}
```

### 🔹 Configuration Properties

**Ribbon configuration options**

```yaml
# Ribbon configuration
user-service:
  ribbon:
    # Load balancing rule
    NFLoadBalancerRuleClassName: com.netflix.loadbalancer.WeightedResponseTimeRule
    
    # Connection timeouts
    ConnectTimeout: 3000
    ReadTimeout: 5000
    
    # Retry configuration
    MaxAutoRetries: 1
    MaxAutoRetriesNextServer: 1
    OkToRetryOnAllOperations: true
    
    # Server list refresh interval
    ServerListRefreshInterval: 15000
    
    # Ping configuration
    NFLoadBalancerPingClassName: com.netflix.loadbalancer.PingUrl
    NFLoadBalancerPingInterval: 30
    
    # Circuit breaker
    EnablePrimeConnections: true
```

### 🔹 Ping Strategy

**Health checking**

```java
public class CustomPing extends IPing {
    
    @Override
    public boolean isAlive(Server server) {
        // Custom health check logic
        try {
            URL url = new URL("http://" + server.getHostPort() + "/health");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(1000);
            connection.setReadTimeout(1000);
            int responseCode = connection.getResponseCode();
            return responseCode == 200;
        } catch (Exception e) {
            return false;
        }
    }
}
```

### 🔹 Server List Management

**Dynamic server discovery**

```java
@Configuration
public class ServerListConfig {
    
    @Bean
    public ServerList<Server> ribbonServerList(IClientConfig config) {
        // Custom server list implementation
        return new CustomServerList(config);
    }
}

public class CustomServerList extends AbstractServerList<Server> {
    
    @Override
    public List<Server> getInitialListOfServers() {
        // Return initial server list
        return Arrays.asList(
            new Server("user-service-1", 8080),
            new Server("user-service-2", 8080)
        );
    }
    
    @Override
    public List<Server> getUpdatedListOfServers() {
        // Return updated server list (e.g., from database)
        return getServersFromDatabase();
    }
}
```

### 🔹 Monitoring and Metrics

**Ribbon metrics**

```java
@RestController
public class RibbonMetricsController {
    
    @Autowired
    private LoadBalancerContext loadBalancerContext;
    
    @GetMapping("/ribbon/metrics")
    public Map<String, Object> getRibbonMetrics() {
        Map<String, Object> metrics = new HashMap<>();
        
        // Get load balancer stats
        LoadBalancerStats stats = loadBalancerContext.getLoadBalancerStats();
        
        for (Server server : stats.getServerStats()) {
            Map<String, Object> serverMetrics = new HashMap<>();
            serverMetrics.put("activeRequests", server.getActiveRequestsCount());
            serverMetrics.put("totalRequests", server.getTotalRequestsCount());
            serverMetrics.put("successRate", server.getSuccessRate());
            
            metrics.put(server.getHostPort(), serverMetrics);
        }
        
        return metrics;
    }
}
```

### 🔹 Best Practices

```java
// Use @LoadBalanced RestTemplate
// Configure appropriate timeouts
// Implement proper retry logic
// Use health checks
// Monitor load balancer metrics
// Choose appropriate load balancing algorithm
// Configure circuit breaker settings
// Use service discovery integration
```

### 🔹 Limitations and Alternatives

**Ribbon considerations**

- **Maintenance**: Netflix OSS maintenance mode
- **Spring Cloud LoadBalancer**: Spring's alternative
- **Kubernetes**: Built-in service load balancing

---

## 🎯 Interview One-Liner

> Ribbon: Netflix client-side load balancer; distributes requests across service instances using rules (round-robin, weighted); integrates with Eureka for service discovery.

---

## ✅ 101. Zuul vs Spring Cloud Gateway

**Zuul is Netflix's API gateway (now in maintenance mode), while Spring Cloud Gateway is Spring's modern, reactive API gateway built on Spring WebFlux and Project Reactor.**

### 🔹 Zuul Overview

**Traditional blocking API gateway**

```java
@SpringBootApplication
@EnableZuulProxy
public class ZuulGatewayApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(ZuulGatewayApplication.class, args);
    }
}

// Application.yml
zuul:
  routes:
    user-service:
      path: /api/users/**
      serviceId: user-service
      stripPrefix: false
    product-service:
      path: /api/products/**
      serviceId: product-service
```

### 🔹 Spring Cloud Gateway

**Reactive API gateway**

```java
@SpringBootApplication
public class GatewayApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}

@Configuration
public class GatewayConfig {
    
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("user-service", r -> r.path("/api/users/**")
                .filters(f -> f.stripPrefix(1))
                .uri("lb://user-service"))
            .route("product-service", r -> r.path("/api/products/**")
                .filters(f -> f.stripPrefix(1))
                .uri("lb://product-service"))
            .build();
    }
}
```

### 🔹 Key Differences

| Aspect | Zuul | Spring Cloud Gateway |
|--------|------|---------------------|
| **Architecture** | Blocking (Servlet) | Reactive (WebFlux) |
| **Performance** | Good | Better for high concurrency |
| **Programming Model** | Synchronous | Reactive streams |
| **Configuration** | Properties-based | Java DSL + Properties |
| **Filters** | Limited built-in | Rich filter ecosystem |
| **Maintenance** | Netflix maintenance mode | Actively maintained |
| **Spring Integration** | Good | Excellent |
| **Async Support** | Limited | Full reactive support |

### 🔹 Routing Configuration

**Route definitions**

```yaml
# Zuul routing
zuul:
  routes:
    user-service:
      path: /api/users/**
      serviceId: user-service
    product-service:
      path: /api/products/**
      serviceId: product-service
      custom-sensitive-headers: true

# Spring Cloud Gateway routing
spring:
  cloud:
    gateway:
      routes:
      - id: user-service
        uri: lb://user-service
        predicates:
        - Path=/api/users/**
        filters:
        - StripPrefix=1
      - id: product-service
        uri: lb://product-service
        predicates:
        - Path=/api/products/**
        filters:
        - StripPrefix=1
```

### 🔹 Filter Implementation

**Custom filters**

```java
// Zuul filter
@Component
public class CustomZuulFilter extends ZuulFilter {
    
    @Override
    public String filterType() {
        return "pre";
    }
    
    @Override
    public int filterOrder() {
        return 1;
    }
    
    @Override
    public boolean shouldFilter() {
        return true;
    }
    
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        
        // Add custom header
        ctx.addZuulRequestHeader("X-Gateway", "Zuul");
        
        return null;
    }
}

// Spring Cloud Gateway filter
@Component
public class CustomGatewayFilter implements GlobalFilter, Ordered {
    
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // Add custom header
        exchange.getRequest().mutate()
            .header("X-Gateway", "Spring-Cloud-Gateway")
            .build();
        
        return chain.filter(exchange);
    }
    
    @Override
    public int getOrder() {
        return 1;
    }
}
```

### 🔹 Rate Limiting

**Traffic control**

```java
// Zuul rate limiting (requires additional library)
@Configuration
public class ZuulRateLimitConfig {
    // Custom implementation needed
}

// Spring Cloud Gateway rate limiting
@Configuration
public class GatewayRateLimitConfig {
    
    @Bean
    public RouteLocator rateLimitedRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("rate-limited", r -> r.path("/api/**")
                .filters(f -> f.requestRateLimiter(c -> c.setRateLimiter(
                    redisRateLimiter()).setKeyResolver(userKeyResolver())))
                .uri("lb://api-service"))
            .build();
    }
    
    @Bean
    public RedisRateLimiter redisRateLimiter() {
        return new RedisRateLimiter(10, 20);
    }
    
    @Bean
    public KeyResolver userKeyResolver() {
        return exchange -> Mono.just(
            exchange.getRequest().getHeaders().getFirst("X-User-Id") != null ?
            exchange.getRequest().getHeaders().getFirst("X-User-Id") :
            "anonymous"
        );
    }
}
```

### 🔹 Circuit Breaker Integration

**Fault tolerance**

```java
// Zuul with Hystrix
@Configuration
public class ZuulHystrixConfig {
    // Hystrix integration
}

// Spring Cloud Gateway with Resilience4j
@Configuration
public class GatewayResilienceConfig {
    
    @Bean
    public RouteLocator resilientRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("resilient", r -> r.path("/api/**")
                .filters(f -> f.circuitBreaker(c -> c.setName("api-circuit-breaker")
                    .setFallbackUri("forward:/fallback")))
                .uri("lb://api-service"))
            .build();
    }
}
```

### 🔹 Authentication and Security

**Security integration**

```java
// Zuul security
@Configuration
public class ZuulSecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/api/users/**").hasRole("USER")
            .antMatchers("/api/admin/**").hasRole("ADMIN");
    }
}

// Spring Cloud Gateway security
@Configuration
public class GatewaySecurityConfig {
    
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange()
            .pathMatchers("/api/users/**").hasRole("USER")
            .pathMatchers("/api/admin/**").hasRole("ADMIN");
        
        return http.build();
    }
}
```

### 🔹 Monitoring and Observability

**Metrics and tracing**

```java
// Zuul monitoring (limited built-in)
@RestController
public class ZuulMetricsController {
    // Custom metrics implementation
}

// Spring Cloud Gateway monitoring
@Configuration
public class GatewayMetricsConfig {
    
    @Bean
    public MeterRegistry meterRegistry() {
        return new SimpleMeterRegistry();
    }
}

// Built-in metrics available at /actuator/metrics
```

### 🔹 Migration from Zuul to Spring Cloud Gateway

**Migration strategy**

```java
// Phase 1: Add Spring Cloud Gateway alongside Zuul
@SpringBootApplication
@EnableZuulProxy
public class HybridGatewayApplication {
    // Both Zuul and Gateway running
}

// Phase 2: Gradually migrate routes
@Configuration
public class MigrationConfig {
    
    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        // Migrate critical routes to Gateway first
        return builder.routes()
            .route("user-service-gateway", r -> r.path("/api/users/**")
                .uri("lb://user-service"))
            .build();
    }
}

// Phase 3: Remove Zuul
@SpringBootApplication
public class GatewayOnlyApplication {
    // Zuul removed, only Gateway
}
```

### 🔹 Best Practices

```java
// Prefer Spring Cloud Gateway for new projects
// Use reactive programming model
// Implement proper error handling
// Configure timeouts and retries
// Use circuit breakers
// Implement rate limiting
// Monitor gateway performance
// Use appropriate filters
```

### 🔹 When to Choose Each

**Selection criteria**

- **Zuul**: Legacy systems, blocking requirements, simpler deployments
- **Spring Cloud Gateway**: New projects, high concurrency, reactive systems, better Spring integration

---

## 🎯 Interview One-Liner

> Zuul: Netflix blocking API gateway (maintenance mode); Spring Cloud Gateway: Spring reactive gateway with better performance, WebFlux integration, modern features.

---

## ✅ 102. What is Feign Client?

**Feign Client is a declarative web service client from Spring Cloud that simplifies HTTP API calls by allowing developers to write web service interfaces as Java interfaces with annotations.**

### 🔹 Feign Client Basics

**Declarative REST client**

```xml
<!-- Maven dependency -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```

```java
@SpringBootApplication
@EnableFeignClients
public class OrderServiceApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }
}

@FeignClient(name = "user-service")
public interface UserServiceClient {
    
    @GetMapping("/users/{id}")
    User getUser(@PathVariable Long id);
    
    @PostMapping("/users")
    User createUser(@RequestBody CreateUserRequest request);
    
    @PutMapping("/users/{id}")
    User updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest request);
    
    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable Long id);
}
```

### 🔹 Service Integration

**Using Feign client**

```java
@Service
public class OrderService {
    
    @Autowired
    private UserServiceClient userClient;
    
    @Autowired
    private ProductServiceClient productClient;
    
    @Transactional
    public Order createOrder(CreateOrderRequest request) {
        // Declarative calls - no RestTemplate needed
        User user = userClient.getUser(request.getUserId());
        Product product = productClient.getProduct(request.getProductId());
        
        Order order = new Order(user, product, request.getQuantity());
        return orderRepository.save(order);
    }
}
```

### 🔹 Configuration Options

**Feign client configuration**

```java
@FeignClient(name = "user-service", 
             url = "http://localhost:8081",  // Direct URL (bypass service discovery)
             configuration = UserClientConfig.class,
             fallback = UserClientFallback.class)
public interface UserServiceClient {
    // Interface methods
}

// Configuration class
@Configuration
public class UserClientConfig {
    
    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            // Add custom headers
            requestTemplate.header("X-API-Key", "secret-key");
            requestTemplate.header("X-Client", "order-service");
        };
    }
    
    @Bean
    public Retryer retryer() {
        // Custom retry logic
        return new Retryer.Default(100, 1000, 5);
    }
}
```

### 🔹 Error Handling

**Fallback and error handling**

```java
@Component
public class UserClientFallback implements UserServiceClient {
    
    @Override
    public User getUser(Long id) {
        // Fallback logic when service is unavailable
        return new User(id, "Unknown User", "unknown@example.com");
    }
    
    @Override
    public User createUser(CreateUserRequest request) {
        throw new ServiceUnavailableException("User service is currently unavailable");
    }
    
    @Override
    public User updateUser(Long id, UpdateUserRequest request) {
        throw new ServiceUnavailableException("User service is currently unavailable");
    }
    
    @Override
    public void deleteUser(Long id) {
        // Log the failure, but don't throw exception for delete
        logger.warn("Failed to delete user {} - service unavailable", id);
    }
}
```

### 🔹 Circuit Breaker Integration

**Fault tolerance with Hystrix/Resilience4j**

```java
@FeignClient(name = "user-service", 
             fallback = UserClientFallback.class)
public interface UserServiceClient {
    
    @GetMapping("/users/{id}")
    User getUser(@PathVariable Long id);
}

// With Hystrix properties
@Configuration
public class FeignHystrixConfig {
    
    @Bean
    public HystrixCommand.Setter userServiceCommandSetter() {
        return HystrixCommand.Setter
            .withGroupKey(HystrixCommandGroupKey.Factory.asKey("user-service"))
            .andCommandKey(HystrixCommandKey.Factory.asKey("getUser"));
    }
}
```

### 🔹 Request/Response Interceptors

**Customizing requests and responses**

```java
@Configuration
public class FeignConfig {
    
    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            // Add authentication header
            String token = getAuthToken();
            requestTemplate.header("Authorization", "Bearer " + token);
            
            // Add correlation ID for tracing
            String correlationId = MDC.get("correlationId");
            if (correlationId != null) {
                requestTemplate.header("X-Correlation-ID", correlationId);
            }
            
            // Log outgoing request
            logger.info("Feign Request: {} {}", 
                requestTemplate.method(), 
                requestTemplate.url());
        };
    }
    
    @Bean
    public ResponseInterceptor responseInterceptor() {
        return (requestTemplate, response) -> {
            // Log response
            logger.info("Feign Response: {} - {}", 
                response.status(), 
                response.reason());
        };
    }
    
    @Bean
    public ErrorDecoder errorDecoder() {
        return (methodKey, response) -> {
            // Custom error handling based on status code
            if (response.status() == 404) {
                return new ResourceNotFoundException("Resource not found");
            }
            return new RuntimeException("Feign client error");
        };
    }
}
```

### 🔹 Encoder/Decoder Customization

**Custom serialization**

```java
@Configuration
public class FeignCustomConfig {
    
    @Bean
    public Encoder feignEncoder() {
        return new JacksonEncoder(customObjectMapper());
    }
    
    @Bean
    public Decoder feignDecoder() {
        return new JacksonDecoder(customObjectMapper());
    }
    
    private ObjectMapper customObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper;
    }
}
```

### 🔹 Load Balancing Integration

**With Ribbon**

```java
// Feign automatically integrates with Ribbon for load balancing
@FeignClient(name = "user-service")  // Service name resolves via Ribbon
public interface UserServiceClient {
    @GetMapping("/users/{id}")
    User getUser(@PathVariable Long id);
}

// Ribbon configuration applies automatically
user-service:
  ribbon:
    NFLoadBalancerRuleClassName: com.netflix.loadbalancer.RoundRobinRule
    ConnectTimeout: 3000
    ReadTimeout: 5000
```

### 🔹 Testing Feign Clients

**Unit and integration testing**

```java
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceClientTest {
    
    @Autowired
    private UserServiceClient userClient;
    
    @Test
    public void shouldGetUser() {
        User user = userClient.getUser(1L);
        assertNotNull(user);
        assertEquals("John Doe", user.getName());
    }
}

// Mock testing
@RunWith(MockitoJUnitRunner.class)
public class UserServiceClientMockTest {
    
    @Mock
    private UserServiceClient userClient;
    
    @Test
    public void shouldHandleFallback() {
        when(userClient.getUser(1L)).thenThrow(new RuntimeException());
        
        // Test fallback behavior
        User fallbackUser = userClient.getUser(1L);
        assertEquals("Unknown User", fallbackUser.getName());
    }
}
```

### 🔹 Best Practices

```java
// Use interface-based declarations
// Implement proper fallbacks
// Configure timeouts appropriately
// Use request/response interceptors for cross-cutting concerns
// Implement proper error handling
// Use circuit breakers for resilience
// Test both success and failure scenarios
// Monitor Feign client metrics
```

### 🔹 Common Issues

**Feign troubleshooting**

- **Service not found**: Check @EnableFeignClients and service discovery
- **Timeout errors**: Configure appropriate timeouts
- **Serialization issues**: Check encoder/decoder configuration
- **Fallback not working**: Verify fallback class implementation
- **Headers not propagated**: Check interceptor configuration

---

## 🎯 Interview One-Liner

> Feign Client: Spring Cloud declarative HTTP client; write interface with annotations instead of RestTemplate; supports load balancing, circuit breakers, fallbacks.

---

## ✅ 103. Distributed tracing with Sleuth and Zipkin

**Spring Cloud Sleuth provides distributed tracing capabilities by adding trace and span IDs to requests, while Zipkin collects and visualizes these traces for debugging and monitoring distributed systems.**

### 🔹 Spring Cloud Sleuth

**Automatic tracing instrumentation**

```xml
<!-- Maven dependencies -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-sleuth</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-zipkin</artifactId>
</dependency>
```

```java
@SpringBootApplication
public class TracingApplication {
    // Sleuth automatically instruments Spring components
}

@RestController
public class UserController {
    
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Long id) {
        logger.info("Getting user with id: {}", id);
        // Trace ID automatically included in logs
        
        User user = userService.findById(id);
        logger.info("Found user: {}", user.getName());
        
        return user;
    }
}
```

### 🔹 Trace and Span Concepts

**Tracing fundamentals**

- **Trace**: Complete journey of a request across services
- **Span**: Individual operation within a trace
- **Trace ID**: Unique identifier for entire request flow
- **Span ID**: Unique identifier for individual operation
- **Parent Span ID**: Links spans in hierarchy

### 🔹 Zipkin Server Setup

**Trace collection and visualization**

```java
@SpringBootApplication
@EnableZipkinServer
public class ZipkinServerApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(ZipkinServerApplication.class, args);
    }
}

// Application.yml
server:
  port: 9411

zipkin:
  storage:
    type: mem  # In-memory storage (use Elasticsearch or MySQL for production)
```

### 🔹 Client Configuration

**Sending traces to Zipkin**

```yaml
# Application.yml
spring:
  application:
    name: user-service
  sleuth:
    sampler:
      probability: 1.0  # Sample 100% of requests (adjust for production)
  zipkin:
    base-url: http://localhost:9411  # Zipkin server URL
    sender:
      type: web  # HTTP sender (web, kafka, rabbit)
```

### 🔹 Custom Tracing

**Manual span creation**

```java
@Service
public class UserService {
    
    @Autowired
    private Tracer tracer;
    
    public User findById(Long id) {
        // Create custom span
        Span customSpan = tracer.nextSpan().name("findUser").start();
        
        try (Tracer.SpanInScope ws = tracer.withSpanInScope(customSpan)) {
            logger.info("Starting user lookup");
            
            // Add tags to span
            customSpan.tag("user.id", id.toString());
            customSpan.tag("operation", "findById");
            
            User user = userRepository.findById(id).orElseThrow();
            
            // Add more tags
            customSpan.tag("user.found", "true");
            customSpan.tag("user.name", user.getName());
            
            return user;
            
        } catch (Exception e) {
            // Tag error
            customSpan.tag("error", "true");
            customSpan.tag("error.message", e.getMessage());
            throw e;
        } finally {
            customSpan.end();
        }
    }
}
```

### 🔹 Inter-Service Tracing

**Tracing across service calls**

```java
@Service
public class OrderService {
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private UserServiceClient userClient;
    
    public Order createOrder(CreateOrderRequest request) {
        // Trace automatically propagated via HTTP headers
        User user = userClient.getUser(request.getUserId());
        Product product = restTemplate.getForObject(
            "http://product-service/products/" + request.getProductId(), 
            Product.class);
        
        Order order = new Order(user, product, request.getQuantity());
        return orderRepository.save(order);
    }
}
```

### 🔹 Baggage Propagation

**Custom context propagation**

```java
@Configuration
public class TracingConfig {
    
    @Bean
    public BaggageField userIdField() {
        return BaggageField.create("userId");
    }
    
    @Bean
    public BaggageField sessionIdField() {
        return BaggageField.create("sessionId");
    }
}

@RestController
public class UserController {
    
    @Autowired
    private BaggageField userIdField;
    
    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Long id) {
        // Set baggage that propagates across service calls
        userIdField.updateValue(id.toString());
        
        return userService.findById(id);
    }
}
```

### 🔹 Log Correlation

**Correlating logs with traces**

```java
@Component
public class LoggingAspect {
    
    @Autowired
    private Tracer tracer;
    
    @Around("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        Span currentSpan = tracer.currentSpan();
        
        if (currentSpan != null) {
            logger.info("Executing method {} with traceId: {}, spanId: {}", 
                joinPoint.getSignature().getName(),
                currentSpan.context().traceId(),
                currentSpan.context().spanId());
        }
        
        return joinPoint.proceed();
    }
}
```

### 🔹 Sampling Strategies

**Controlling trace volume**

```yaml
# Sampling configuration
spring:
  sleuth:
    sampler:
      # Sample 10% of requests
      probability: 0.1
      
      # Sample based on custom logic
      # implementation: com.example.CustomSampler
```

### 🔹 Zipkin UI Features

**Trace visualization and analysis**

- **Dependency Diagram**: Service communication visualization
- **Trace Timeline**: Request flow visualization
- **Span Details**: Individual operation details
- **Search and Filtering**: Find specific traces
- **Performance Metrics**: Response time analysis

### 🔹 Integration with Monitoring

**Combining with metrics**

```java
@Configuration
public class MonitoringConfig {
    
    @Bean
    public MeterRegistry meterRegistry() {
        return new SimpleMeterRegistry();
    }
}

@Service
public class MetricsService {
    
    @Autowired
    private MeterRegistry registry;
    
    @Autowired
    private Tracer tracer;
    
    public void recordOperationMetrics(String operation, long duration) {
        Span currentSpan = tracer.currentSpan();
        
        // Record metrics with trace context
        Timer.Sample sample = Timer.start(registry);
        sample.stop(Timer.builder("operation.duration")
            .tag("operation", operation)
            .tag("traceId", currentSpan != null ? currentSpan.context().traceId() : "unknown")
            .register(registry));
    }
}
```

### 🔹 Best Practices

```java
// Use meaningful span names
// Add relevant tags to spans
// Implement proper sampling for production
// Use baggage for custom context propagation
// Integrate with logging frameworks
// Monitor trace collection performance
// Use correlation IDs for manual tracing
// Clean up old traces regularly
```

### 🔹 Troubleshooting with Traces

**Debugging distributed issues**

```java
@RestController
public class DebugController {
    
    @Autowired
    private Tracer tracer;
    
    @GetMapping("/debug/trace")
    public Map<String, String> getCurrentTraceInfo() {
        Span currentSpan = tracer.currentSpan();
        
        if (currentSpan != null) {
            return Map.of(
                "traceId", currentSpan.context().traceId(),
                "spanId", currentSpan.context().spanId(),
                "parentSpanId", currentSpan.context().parentId() != null ? 
                    currentSpan.context().parentId() : "none"
            );
        }
        
        return Map.of("message", "No active span");
    }
}
```

---

## 🎯 Interview One-Liner

> Sleuth: adds trace/span IDs to Spring apps for distributed tracing; Zipkin: collects and visualizes traces; enables debugging complex service interactions.

---

## ✅ 104. How to handle distributed transactions?

**Distributed transactions in microservices can be handled using patterns like Saga, Two-Phase Commit (2PC), or eventual consistency, with Saga being the most common approach for long-running transactions.**

### 🔹 Saga Pattern

**Orchestration-based saga**

```java
@Service
public class OrderSagaOrchestrator {
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private PaymentService paymentService;
    
    @Autowired
    private InventoryService inventoryService;
    
    @Autowired
    private ShippingService shippingService;
    
    public void executeOrderSaga(CreateOrderRequest request) {
        OrderSaga saga = new OrderSaga();
        
        try {
            // Step 1: Create order
            Order order = orderService.createOrder(request);
            saga.setOrderId(order.getId());
            
            // Step 2: Process payment
            Payment payment = paymentService.processPayment(order);
            saga.setPaymentId(payment.getId());
            
            // Step 3: Reserve inventory
            inventoryService.reserveInventory(order);
            
            // Step 4: Arrange shipping
            shippingService.arrangeShipping(order);
            
            saga.setStatus(SagaStatus.COMPLETED);
            
        } catch (Exception e) {
            // Compensate in reverse order
            compensateSaga(saga);
            saga.setStatus(SagaStatus.FAILED);
        }
    }
    
    private void compensateSaga(OrderSaga saga) {
        try { shippingService.cancelShipping(saga.getOrderId()); } catch (Exception e) { /* log */ }
        try { inventoryService.releaseInventory(saga.getOrderId()); } catch (Exception e) { /* log */ }
        try { paymentService.refundPayment(saga.getPaymentId()); } catch (Exception e) { /* log */ }
        try { orderService.cancelOrder(saga.getOrderId()); } catch (Exception e) { /* log */ }
    }
}
```

### 🔹 Choreography Saga

**Event-driven saga**

```java
@Service
public class OrderService {
    
    @Autowired
    private ApplicationEventPublisher publisher;
    
    @Transactional
    public Order createOrder(CreateOrderRequest request) {
        Order order = orderRepository.save(request.toOrder());
        publisher.publishEvent(new OrderCreatedEvent(order));
        return order;
    }
}

// Payment Service
@Component
public class OrderCreatedEventHandler {
    
    @EventListener
    @Transactional
    public void handleOrderCreated(OrderCreatedEvent event) {
        try {
            paymentService.processPayment(event.getOrder());
            publisher.publishEvent(new PaymentProcessedEvent(event.getOrder()));
        } catch (Exception e) {
            publisher.publishEvent(new PaymentFailedEvent(event.getOrder()));
        }
    }
}

// Compensation handler
@Component
public class PaymentFailedEventHandler {
    
    @EventListener
    @Transactional
    public void handlePaymentFailed(PaymentFailedEvent event) {
        // Compensate: cancel order
        orderService.cancelOrder(event.getOrder().getId());
    }
}
```

### 🔹 Two-Phase Commit (2PC)

**Distributed transaction coordinator**

```java
@Service
public class DistributedTransactionManager {
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private PaymentService paymentService;
    
    @Autowired
    private InventoryService inventoryService;
    
    @Transactional
    public void processOrderWith2PC(CreateOrderRequest request) {
        // Phase 1: Prepare
        Order order = orderService.prepareOrder(request);
        Payment payment = paymentService.preparePayment(order);
        inventoryService.prepareInventoryReservation(order);
        
        try {
            // Phase 2: Commit
            orderService.commitOrder(order);
            paymentService.commitPayment(payment);
            inventoryService.commitInventoryReservation(order);
            
        } catch (Exception e) {
            // Phase 2: Rollback
            orderService.rollbackOrder(order);
            paymentService.rollbackPayment(payment);
            inventoryService.rollbackInventoryReservation(order);
            throw e;
        }
    }
}
```

### 🔹 Eventual Consistency

**BASE principle approach**

```java
@Service
public class OrderService {
    
    @Autowired
    private ApplicationEventPublisher publisher;
    
    @Transactional
    public Order createOrder(CreateOrderRequest request) {
        // Create order
        Order order = orderRepository.save(request.toOrder());
        
        // Publish event for eventual processing
        publisher.publishEvent(new OrderCreatedEvent(order));
        
        // Return immediately - consistency achieved eventually
        return order;
    }
}

// Async event handlers
@Component
public class OrderEventHandlers {
    
    @Async
    @EventListener
    public void handlePayment(OrderCreatedEvent event) {
        paymentService.processPaymentAsync(event.getOrder());
    }
    
    @Async
    @EventListener
    public void handleInventory(OrderCreatedEvent event) {
        inventoryService.reserveInventoryAsync(event.getOrder());
    }
}
```

### 🔹 TCC (Try-Confirm-Cancel)

**Three-phase approach**

```java
@Service
public class OrderTccService {
    
    @Transactional
    public Order tryCreateOrder(CreateOrderRequest request) {
        // Try phase: Reserve resources
        Order order = orderService.tryCreate(request);
        paymentService.tryReservePayment(order);
        inventoryService.tryReserveInventory(order);
        
        return order;
    }
    
    @Transactional
    public void confirmCreateOrder(Order order) {
        // Confirm phase: Commit resources
        orderService.confirmCreate(order);
        paymentService.confirmPayment(order);
        inventoryService.confirmInventory(order);
    }
    
    @Transactional
    public void cancelCreateOrder(Order order) {
        // Cancel phase: Release resources
        orderService.cancelCreate(order);
        paymentService.cancelPayment(order);
        inventoryService.cancelInventory(order);
    }
}
```

### 🔹 Outbox Pattern

**Reliable event publishing**

```java
@Entity
@Table(name = "orders")
public class Order {
    
    @Id
    private Long id;
    
    // Order fields...
    
    @OneToMany(cascade = CascadeType.ALL)
    private List<OutboxEvent> outboxEvents = new ArrayList<>();
    
    public void addOutboxEvent(String eventType, String payload) {
        outboxEvents.add(new OutboxEvent(eventType, payload));
    }
}

@Service
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Transactional
    public Order createOrder(CreateOrderRequest request) {
        Order order = new Order(request);
        
        // Add outbox event
        order.addOutboxEvent("OrderCreated", 
            objectMapper.writeValueAsString(order));
        
        return orderRepository.save(order);
    }
}

// Background job processes outbox
@Component
public class OutboxProcessor {
    
    @Scheduled(fixedDelay = 5000)
    public void processOutbox() {
        List<Order> ordersWithEvents = orderRepository.findOrdersWithOutboxEvents();
        
        for (Order order : ordersWithEvents) {
            for (OutboxEvent event : order.getOutboxEvents()) {
                // Publish event
                publisher.publishEvent(deserializeEvent(event));
                
                // Mark as processed
                event.setProcessed(true);
            }
        }
        
        orderRepository.saveAll(ordersWithEvents);
    }
}
```

### 🔹 Best Practices

```java
// Use Saga for long-running transactions
// Implement compensating actions
// Use eventual consistency where possible
// Implement proper monitoring
// Use correlation IDs for tracking
// Implement timeout mechanisms
// Test failure scenarios thoroughly
// Document transaction boundaries
```

### 🔹 Monitoring Distributed Transactions

**Observability**

```java
@Service
public class TransactionMonitor {
    
    @Autowired
    private SagaRepository sagaRepository;
    
    public List<SagaStatus> getSagaStatuses() {
        return sagaRepository.findAll().stream()
            .map(Saga::getStatus)
            .collect(Collectors.toList());
    }
    
    public Map<String, Long> getSagaMetrics() {
        List<Saga> sagas = sagaRepository.findAll();
        
        return Map.of(
            "total", (long) sagas.size(),
            "completed", sagas.stream().filter(s -> s.getStatus() == SagaStatus.COMPLETED).count(),
            "failed", sagas.stream().filter(s -> s.getStatus() == SagaStatus.FAILED).count(),
            "pending", sagas.stream().filter(s -> s.getStatus() == SagaStatus.PENDING).count()
        );
    }
}
```

---

## 🎯 Interview One-Liner

> Distributed transactions: Saga (compensating actions), 2PC (coordinator), eventual consistency; Saga preferred for microservices due to loose coupling and fault tolerance.

---

## ✅ 105. How to maintain data consistency across services?

**Data consistency in microservices can be maintained through eventual consistency, Saga patterns, event-driven architecture, and distributed transactions, accepting that strong consistency may not always be achievable or desirable.**

### 🔹 Eventual Consistency

**Accepting temporary inconsistency**

```java
@Service
public class OrderService {
    
    @Autowired
    private ApplicationEventPublisher publisher;
    
    @Transactional
    public Order createOrder(CreateOrderRequest request) {
        // Create order immediately
        Order order = orderRepository.save(request.toOrder());
        
        // Publish event for other services to react
        publisher.publishEvent(new OrderCreatedEvent(order));
        
        // Return order - data will be consistent eventually
        return order;
    }
}

// Inventory service reacts to event
@Component
public class OrderCreatedEventHandler {
    
    @Async
    @EventListener
    public void updateInventory(OrderCreatedEvent event) {
        // Update inventory asynchronously
        inventoryService.reserveItems(event.getOrder().getItems());
    }
}
```

### 🔹 Saga Pattern for Consistency

**Coordinated consistency**

```java
@Service
public class OrderProcessingSaga {
    
    public void processOrder(Order order) {
        try {
            // Step 1: Reserve inventory
            inventoryService.reserveInventory(order);
            
            // Step 2: Process payment
            paymentService.processPayment(order);
            
            // Step 3: Confirm order
            orderService.confirmOrder(order);
            
        } catch (Exception e) {
            // Compensate on failure
            inventoryService.releaseInventory(order);
            paymentService.cancelPayment(order);
            orderService.cancelOrder(order);
        }
    }
}
```

### 🔹 CQRS for Read/Write Consistency

**Separate read and write models**

```java
// Write Model - Strongly consistent
@Service
public class OrderCommandService {
    
    @Transactional
    public Order createOrder(CreateOrderCommand command) {
        Order order = new Order(command);
        orderRepository.save(order);
        
        // Publish event to update read model
        publisher.publishEvent(new OrderCreatedEvent(order));
        
        return order;
    }
}

// Read Model - Eventually consistent
@Component
public class OrderReadModelUpdater {
    
    @EventListener
    public void handleOrderCreated(OrderCreatedEvent event) {
        // Update read model asynchronously
        OrderSummary summary = new OrderSummary(event.getOrder());
        readRepository.save(summary);
    }
}
```

### 🔹 Distributed Locks

**Pessimistic locking approach**

```java
@Service
public class InventoryService {
    
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    
    public boolean reserveInventory(Order order) {
        String lockKey = "inventory:" + order.getProductId();
        
        // Try to acquire distributed lock
        Boolean locked = redisTemplate.opsForValue()
            .setIfAbsent(lockKey, "locked", Duration.ofSeconds(30));
        
        if (Boolean.TRUE.equals(locked)) {
            try {
                // Check and reserve inventory
                int availableStock = getAvailableStock(order.getProductId());
                
                if (availableStock >= order.getQuantity()) {
                    updateStock(order.getProductId(), availableStock - order.getQuantity());
                    return true;
                }
                
                return false;
                
            } finally {
                // Release lock
                redisTemplate.delete(lockKey);
            }
        }
        
        return false; // Could not acquire lock
    }
}
```

### 🔹 Idempotent Operations

**Safe retry operations**

```java
@Service
public class PaymentService {
    
    @Transactional
    public Payment processPayment(Order order, String idempotencyKey) {
        // Check if payment already processed
        Payment existingPayment = paymentRepository
            .findByOrderIdAndIdempotencyKey(order.getId(), idempotencyKey);
        
        if (existingPayment != null) {
            return existingPayment; // Return existing payment
        }
        
        // Process new payment
        Payment payment = new Payment(order, idempotencyKey);
        paymentRepository.save(payment);
        
        // Process payment with external service
        externalPaymentService.charge(payment);
        
        return payment;
    }
}
```

### 🔹 Event Sourcing

**Complete audit trail**

```java
@Entity
public class Order {
    
    @Id
    private Long id;
    
    private OrderStatus status;
    private List<OrderEvent> events = new ArrayList<>();
    
    public void apply(OrderEvent event) {
        // Apply event to current state
        if (event instanceof OrderCreatedEvent) {
            this.status = OrderStatus.CREATED;
        } else if (event instanceof OrderPaidEvent) {
            this.status = OrderStatus.PAID;
        }
        
        events.add(event);
    }
    
    // Reconstruct state from events
    public static Order reconstruct(List<OrderEvent> events) {
        Order order = new Order();
        events.forEach(order::apply);
        return order;
    }
}
```

### 🔹 Consistency Patterns

**Different consistency approaches**

### 🔹 Strong Consistency

**Immediate consistency**

```java
@Service
public class BankingService {
    
    @Transactional
    public void transferMoney(TransferRequest request) {
        // Both operations in same transaction
        Account fromAccount = accountRepository.findById(request.getFromAccountId());
        Account toAccount = accountRepository.findById(request.getToAccountId());
        
        fromAccount.debit(request.getAmount());
        toAccount.credit(request.getAmount());
        
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }
}
```

### 🔹 Weak Consistency

**Accept some inconsistency**

```java
@Service
public class SocialMediaService {
    
    public void likePost(LikeRequest request) {
        // Update like count asynchronously
        likeRepository.save(new Like(request));
        
        // Schedule background job to update count
        taskScheduler.schedule(() -> 
            postRepository.incrementLikeCount(request.getPostId()), 
            Instant.now().plusSeconds(5));
    }
}
```

### 🔹 Monitoring Consistency

**Tracking consistency issues**

```java
@Service
public class ConsistencyMonitor {
    
    public void checkOrderConsistency() {
        List<Order> orders = orderRepository.findAll();
        
        for (Order order : orders) {
            // Check if payment exists for paid orders
            if (order.getStatus() == OrderStatus.PAID) {
                Payment payment = paymentRepository.findByOrderId(order.getId());
                if (payment == null) {
                    alertService.sendAlert("Missing payment for order: " + order.getId());
                }
            }
            
            // Check inventory consistency
            boolean inventoryConsistent = inventoryService
                .checkInventoryConsistency(order);
            if (!inventoryConsistent) {
                alertService.sendAlert("Inventory inconsistency for order: " + order.getId());
            }
        }
    }
}
```

### 🔹 Best Practices

```java
// Design for eventual consistency
// Use Saga pattern for complex operations
// Implement compensating actions
// Use idempotent operations
// Monitor consistency with automated checks
// Implement proper error handling
// Use correlation IDs for tracking
// Document consistency guarantees
```

### 🔹 Testing Consistency

**Testing distributed consistency**

```java
@SpringBootTest
public class ConsistencyTest {
    
    @Test
    public void shouldAchieveEventualConsistency() {
        // Create order
        Order order = orderService.createOrder(request);
        
        // Wait for eventual consistency
        await().atMost(10, SECONDS).until(() -> 
            inventoryService.getReservedQuantity(order.getProductId()) == order.getQuantity()
            && paymentService.getPaymentStatus(order.getId()) == PaymentStatus.COMPLETED);
    }
}
```

---

## 🎯 Interview One-Liner

> Data consistency in microservices: eventual consistency with events/Saga; accept temporary inconsistency; use compensating actions, idempotency, monitoring for reconciliation.

---

## ✅ 106. How to handle authentication in microservices?

**Authentication in microservices can be handled through JWT tokens, OAuth2/OIDC, API gateways, or service mesh, with tokens propagated between services for secure inter-service communication.**

### 🔹 JWT Token-Based Authentication

**Stateless token authentication**

```java
@Service
public class JwtService {
    
    private static final String SECRET = "mySecretKey";
    private static final long EXPIRATION_TIME = 86400000; // 24 hours
    
    public String generateToken(User user) {
        return Jwts.builder()
            .setSubject(user.getUsername())
            .claim("userId", user.getId())
            .claim("roles", user.getRoles())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(SignatureAlgorithm.HS512, SECRET)
            .compact();
    }
    
    public Claims validateToken(String token) {
        return Jwts.parser()
            .setSigningKey(SECRET)
            .parseClaimsJws(token)
            .getBody();
    }
}

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private JwtService jwtService;
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        // Validate credentials
        User user = userService.authenticate(request.getUsername(), request.getPassword());
        
        // Generate token
        String token = jwtService.generateToken(user);
        
        return ResponseEntity.ok(new AuthResponse(token, user));
    }
}
```

### 🔹 Token Propagation

**Passing tokens between services**

```java
@Configuration
public class FeignConfig {
    
    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            // Get token from current request context
            ServletRequestAttributes attrs = (ServletRequestAttributes) 
                RequestContextHolder.getRequestAttributes();
            
            if (attrs != null) {
                HttpServletRequest request = attrs.getRequest();
                String token = request.getHeader("Authorization");
                
                if (token != null) {
                    requestTemplate.header("Authorization", token);
                }
            }
        };
    }
}

@Service
public class OrderService {
    
    @Autowired
    private UserServiceClient userClient;
    
    public Order getUserOrders(String token) {
        // Token automatically propagated via Feign interceptor
        User user = userClient.getCurrentUser();
        return orderRepository.findByUser(user);
    }
}
```

### 🔹 OAuth2 Authorization Server

**Centralized authentication**

```java
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
            .withClient("client-id")
            .secret(passwordEncoder.encode("client-secret"))
            .authorizedGrantTypes("authorization_code", "refresh_token")
            .scopes("read", "write")
            .redirectUris("http://localhost:8080/callback");
    }
    
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager);
    }
}

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/api/public/**").permitAll()
            .antMatchers("/api/user/**").hasRole("USER")
            .antMatchers("/api/admin/**").hasRole("ADMIN")
            .anyRequest().authenticated();
    }
}
```

### 🔹 API Gateway Authentication

**Centralized auth at gateway**

```java
@Configuration
public class GatewaySecurityConfig {
    
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange()
            .pathMatchers("/auth/**").permitAll()
            .pathMatchers("/api/user/**").hasRole("USER")
            .pathMatchers("/api/admin/**").hasRole("ADMIN")
            .anyExchange().authenticated()
            .and()
            .oauth2ResourceServer()
            .jwt();
        
        return http.build();
    }
}

@Configuration
public class GatewayRoutesConfig {
    
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("user-service", r -> r.path("/api/user/**")
                .filters(f -> f.stripPrefix(1))
                .uri("lb://user-service"))
            .route("auth-service", r -> r.path("/auth/**")
                .uri("lb://auth-service"))
            .build();
    }
}
```

### 🔹 Service Mesh Security

**Istio mTLS authentication**

```yaml
# Peer Authentication Policy
apiVersion: security.istio.io/v1beta1
kind: PeerAuthentication
metadata:
  name: default
  namespace: istio-system
spec:
  mtls:
    mode: STRICT

---
# Request Authentication
apiVersion: security.istio.io/v1beta1
kind: RequestAuthentication
metadata:
  name: jwt-auth
spec:
  selector:
    matchLabels:
      app: api-gateway
  jwtRules:
  - issuer: "https://accounts.google.com"
    jwksUri: "https://www.googleapis.com/oauth2/v3/certs"

---
# Authorization Policy
apiVersion: security.istio.io/v1beta1
kind: AuthorizationPolicy
metadata:
  name: api-access
spec:
  selector:
    matchLabels:
      app: user-service
  rules:
  - from:
    - source:
      principals: ["cluster.local/ns/default/sa/api-gateway"]
    to:
    - operation:
      methods: ["GET", "POST"]
```

### 🔹 Role-Based Access Control

**RBAC implementation**

```java
@Service
public class AuthorizationService {
    
    public boolean hasPermission(User user, String resource, String action) {
        // Check user roles against permissions
        return user.getRoles().stream()
            .anyMatch(role -> role.hasPermission(resource, action));
    }
}

@RestController
public class OrderController {
    
    @Autowired
    private AuthorizationService authService;
    
    @GetMapping("/orders/{id}")
    public Order getOrder(@PathVariable Long id, @AuthenticationPrincipal User user) {
        Order order = orderRepository.findById(id);
        
        // Check if user owns the order or has admin role
        if (!order.getUserId().equals(user.getId()) && 
            !authService.hasPermission(user, "orders", "read_all")) {
            throw new AccessDeniedException("Access denied");
        }
        
        return order;
    }
}
```

### 🔹 Token Refresh

**Handling token expiration**

```java
@Service
public class TokenRefreshService {
    
    @Autowired
    private JwtService jwtService;
    
    public AuthResponse refreshToken(String refreshToken) {
        // Validate refresh token
        Claims claims = jwtService.validateToken(refreshToken);
        
        // Generate new access token
        User user = userService.findByUsername(claims.getSubject());
        String newAccessToken = jwtService.generateToken(user);
        
        return new AuthResponse(newAccessToken, user);
    }
}

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        AuthResponse response = tokenRefreshService.refreshToken(request.getRefreshToken());
        return ResponseEntity.ok(response);
    }
}
```

### 🔹 Best Practices

```java
// Use JWT for stateless authentication
// Implement proper token expiration
// Use HTTPS everywhere
// Validate tokens on each request
// Implement proper logout/token revocation
// Use OAuth2 for third-party integrations
// Implement rate limiting for auth endpoints
// Log authentication events
```

### 🔹 Testing Authentication

**Testing auth mechanisms**

```java
@SpringBootTest
public class AuthenticationTest {
    
    @Autowired
    private JwtService jwtService;
    
    @Test
    public void shouldGenerateValidToken() {
        User user = new User("testuser", "password");
        String token = jwtService.generateToken(user);
        
        Claims claims = jwtService.validateToken(token);
        assertEquals("testuser", claims.getSubject());
        assertNotNull(claims.get("userId"));
    }
    
    @Test
    public void shouldRejectExpiredToken() {
        // Create expired token
        String expiredToken = createExpiredToken();
        
        assertThrows(Exception.class, () -> 
            jwtService.validateToken(expiredToken));
    }
}
```

---

## 🎯 Interview One-Liner

> Microservices authentication: JWT tokens propagated via headers, OAuth2/OIDC, API gateway validation, service mesh mTLS; centralize auth logic, use stateless tokens.

---

## ✅ 107. What is API versioning strategy?

**API versioning in microservices ensures backward compatibility while allowing evolution, using strategies like URI versioning, header versioning, content negotiation, or query parameters to manage different API versions.**

### 🔹 URI Versioning

**Version in URL path**

```java
@RestController
@RequestMapping("/api/v1/users")
public class UserControllerV1 {
    
    @GetMapping("/{id}")
    public User getUserV1(@PathVariable Long id) {
        return userService.getUser(id);
    }
    
    @PostMapping
    public User createUserV1(@RequestBody CreateUserRequest request) {
        return userService.createUser(request);
    }
}

// Version 2 with different response format
@RestController
@RequestMapping("/api/v2/users")
public class UserControllerV2 {
    
    @GetMapping("/{id}")
    public UserResponseV2 getUserV2(@PathVariable Long id) {
        User user = userService.getUser(id);
        return UserResponseV2.from(user); // Enhanced response
    }
    
    @PostMapping
    public UserResponseV2 createUserV2(@RequestBody CreateUserRequestV2 request) {
        User user = userService.createUser(request.toUser());
        return UserResponseV2.from(user);
    }
}
```

### 🔹 Header Versioning

**Version in request headers**

```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id, 
                                   @RequestHeader(value = "X-API-Version", defaultValue = "1") String version) {
        
        User user = userService.getUser(id);
        
        switch (version) {
            case "1":
                return ResponseEntity.ok(UserResponseV1.from(user));
            case "2":
                return ResponseEntity.ok(UserResponseV2.from(user));
            default:
                return ResponseEntity.badRequest().body("Unsupported API version");
        }
    }
    
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody String requestBody,
                                      @RequestHeader(value = "X-API-Version", defaultValue = "1") String version,
                                      @RequestHeader("Content-Type") String contentType) {
        
        switch (version) {
            case "1":
                CreateUserRequestV1 requestV1 = parseV1Request(requestBody);
                User user = userService.createUser(requestV1.toUser());
                return ResponseEntity.ok(UserResponseV1.from(user));
                
            case "2":
                CreateUserRequestV2 requestV2 = parseV2Request(requestBody);
                User user2 = userService.createUser(requestV2.toUser());
                return ResponseEntity.ok(UserResponseV2.from(user2));
                
            default:
                return ResponseEntity.badRequest().body("Unsupported API version");
        }
    }
}
```

### 🔹 Content Negotiation

**Version based on Accept header**

```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @GetMapping(value = "/{id}", produces = {
        "application/vnd.myapp.v1+json",
        "application/vnd.myapp.v2+json"
    })
    public ResponseEntity<?> getUser(@PathVariable Long id, 
                                   @RequestHeader("Accept") String acceptHeader) {
        
        User user = userService.getUser(id);
        
        if (acceptHeader.contains("v2")) {
            return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/vnd.myapp.v2+json"))
                .body(UserResponseV2.from(user));
        } else {
            return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/vnd.myapp.v1+json"))
                .body(UserResponseV1.from(user));
        }
    }
}
```

### 🔹 Query Parameter Versioning

**Version as query parameter**

```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id,
                                   @RequestParam(value = "version", defaultValue = "1") String version) {
        
        User user = userService.getUser(id);
        
        switch (version) {
            case "1":
                return ResponseEntity.ok(UserResponseV1.from(user));
            case "2":
                return ResponseEntity.ok(UserResponseV2.from(user));
            default:
                return ResponseEntity.badRequest().body("Unsupported version");
        }
    }
}
```

### 🔹 Semantic Versioning

**Version numbering strategy**

```java
public class ApiVersion {
    public static final String V1_0 = "1.0";
    public static final String V1_1 = "1.1";  // Backward compatible
    public static final String V2_0 = "2.0";  // Breaking changes
    
    // Version comparison
    public static boolean isCompatible(String clientVersion, String apiVersion) {
        // Implement semantic version comparison
        return compareVersions(clientVersion, apiVersion) >= 0;
    }
}
```

### 🔹 Version Negotiation Strategy

**Client-driven version selection**

```java
@Service
public class ApiVersionNegotiator {
    
    public String negotiateVersion(HttpServletRequest request) {
        // Check X-API-Version header
        String headerVersion = request.getHeader("X-API-Version");
        if (headerVersion != null && isValidVersion(headerVersion)) {
            return headerVersion;
        }
        
        // Check Accept header for content negotiation
        String acceptHeader = request.getHeader("Accept");
        if (acceptHeader != null) {
            return extractVersionFromAccept(acceptHeader);
        }
        
        // Check query parameter
        String queryVersion = request.getParameter("version");
        if (queryVersion != null && isValidVersion(queryVersion)) {
            return queryVersion;
        }
        
        // Default to latest version
        return getDefaultVersion();
    }
    
    private boolean isValidVersion(String version) {
        return Arrays.asList("1", "2", "1.0", "1.1", "2.0").contains(version);
    }
}
```

### 🔹 Backward Compatibility

**Ensuring compatibility**

```java
// Version 1 response
public class UserResponseV1 {
    private Long id;
    private String name;
    private String email;
    
    public static UserResponseV1 from(User user) {
        return new UserResponseV1(user.getId(), user.getName(), user.getEmail());
    }
}

// Version 2 response (adds new fields)
public class UserResponseV2 {
    private Long id;
    private String name;
    private String email;
    private LocalDateTime createdAt;  // New field
    private List<String> roles;       // New field
    
    public static UserResponseV2 from(User user) {
        return new UserResponseV2(
            user.getId(), 
            user.getName(), 
            user.getEmail(),
            user.getCreatedAt(),
            user.getRoles()
        );
    }
}
```

### 🔹 Deprecation Strategy

**Managing version lifecycle**

```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id,
                                   @RequestHeader(value = "X-API-Version", defaultValue = "2") String version,
                                   HttpServletResponse response) {
        
        if ("1".equals(version)) {
            // Add deprecation warning
            response.addHeader("X-API-Deprecated", "true");
            response.addHeader("X-API-Sunset", "2024-12-31");
            response.addHeader("X-API-Successor", "2");
        }
        
        User user = userService.getUser(id);
        return ResponseEntity.ok(getResponseForVersion(user, version));
    }
}
```

### 🔹 API Gateway Versioning

**Centralized version handling**

```java
@Configuration
public class GatewayVersioningConfig {
    
    @Bean
    public RouteLocator versionedRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
            // Route based on version header
            .route("user-service-v1", r -> r.path("/api/v1/users/**")
                .filters(f -> f.stripPrefix(1))
                .uri("lb://user-service"))
                
            .route("user-service-v2", r -> r.path("/api/v2/users/**")
                .filters(f -> f.stripPrefix(1))
                .uri("lb://user-service"))
                
            // Dynamic routing based on header
            .route("user-service-dynamic", r -> r.path("/api/users/**")
                .and().header("X-API-Version", "1")
                .filters(f -> f.stripPrefix(1))
                .uri("lb://user-service"))
            .build();
    }
}
```

### 🔹 Best Practices

```java
// Use URI versioning for clear API evolution
// Maintain backward compatibility
// Document version differences clearly
// Use semantic versioning
// Deprecate old versions gradually
// Test all supported versions
// Monitor version usage
// Plan version lifecycle
```

### 🔹 Testing Versioning

**Testing different versions**

```java
@SpringBootTest
public class ApiVersioningTest {
    
    @Autowired
    private WebTestClient webClient;
    
    @Test
    public void shouldSupportV1Response() {
        webClient.get().uri("/api/users/1")
            .header("X-API-Version", "1")
            .exchange()
            .expectStatus().isOk()
            .expectBody(UserResponseV1.class);
    }
    
    @Test
    public void shouldSupportV2Response() {
        webClient.get().uri("/api/users/1")
            .header("X-API-Version", "2")
            .exchange()
            .expectStatus().isOk()
            .expectBody(UserResponseV2.class);
    }
    
    @Test
    public void shouldDefaultToLatestVersion() {
        webClient.get().uri("/api/users/1")
            .exchange()
            .expectStatus().isOk()
            .expectBody(UserResponseV2.class); // Latest version
    }
}
```

---

## 🎯 Interview One-Liner

> API versioning: URI paths (/v1/), headers (X-API-Version), content negotiation (Accept), query params; ensures backward compatibility during API evolution.

---

## ✅ 108. How to test microservices?

**Microservices testing requires a comprehensive approach including unit tests, integration tests, contract tests, component tests, and end-to-end tests, with each layer addressing different concerns and dependencies.**

### 🔹 Unit Testing

**Testing individual components**

```java
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private UserService userService;
    
    @Test
    public void shouldCreateUser() {
        // Arrange
        CreateUserRequest request = new CreateUserRequest("John", "john@example.com");
        User savedUser = new User(1L, "John", "john@example.com");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        
        // Act
        User result = userService.createUser(request);
        
        // Assert
        assertNotNull(result);
        assertEquals("John", result.getName());
        verify(userRepository).save(any(User.class));
    }
    
    @Test
    public void shouldThrowExceptionForInvalidEmail() {
        CreateUserRequest request = new CreateUserRequest("John", "invalid-email");
        
        assertThrows(InvalidEmailException.class, () -> 
            userService.createUser(request));
    }
}
```

### 🔹 Integration Testing

**Testing service interactions**

```java
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceIntegrationTest {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    public void shouldCreateAndRetrieveUser() {
        // Test through REST API
        CreateUserRequest request = new CreateUserRequest("John", "john@example.com");
        
        ResponseEntity<User> createResponse = restTemplate.postForEntity(
            "/api/users", request, User.class);
        
        assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());
        User createdUser = createResponse.getBody();
        
        // Verify in database
        User dbUser = userRepository.findById(createdUser.getId()).orElse(null);
        assertNotNull(dbUser);
        assertEquals("John", dbUser.getName());
    }
}
```

### 🔹 Contract Testing

**Testing API contracts**

```java
// Spring Cloud Contract
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMessageVerifier
public class UserServiceContractTest {
    
    @Autowired
    private UserService userService;
    
    @Test
    public void validate_shouldReturnUser() throws Exception {
        // Contract test - validates against contract definition
    }
}

// Contract definition (in test/resources/contracts)
Contract.make {
    request {
        method 'GET'
        url '/api/users/1'
    }
    response {
        status 200
        body([
            id: 1,
            name: "John Doe",
            email: "john@example.com"
        ])
    }
}
```

### 🔹 Component Testing

**Testing service with dependencies mocked**

```java
@RunWith(SpringRunner.class)
@SpringBootTest
@MockBean(UserRepository.class)
@MockBean(NotificationService.class)
public class UserServiceComponentTest {
    
    @Autowired
    private UserService userService;
    
    @MockBean
    private UserRepository userRepository;
    
    @Test
    public void shouldCreateUserAndSendNotification() {
        // Arrange
        CreateUserRequest request = new CreateUserRequest("John", "john@example.com");
        User savedUser = new User(1L, "John", "john@example.com");
        
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        
        // Act
        User result = userService.createUser(request);
        
        // Assert
        assertNotNull(result);
        verify(notificationService).sendWelcomeEmail(savedUser);
    }
}
```

### 🔹 End-to-End Testing

**Testing complete user journeys**

```java
@SpringBootTest
@RunWith(SpringRunner.class)
public class E2ETest {
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    public void shouldCompleteOrderJourney() {
        // 1. Create user
        CreateUserRequest userRequest = new CreateUserRequest("John", "john@example.com");
        ResponseEntity<User> userResponse = restTemplate.postForEntity(
            "/api/users", userRequest, User.class);
        User user = userResponse.getBody();
        
        // 2. Create product
        CreateProductRequest productRequest = new CreateProductRequest("Laptop", 999.99);
        ResponseEntity<Product> productResponse = restTemplate.postForEntity(
            "/api/products", productRequest, Product.class);
        Product product = productResponse.getBody();
        
        // 3. Create order
        CreateOrderRequest orderRequest = new CreateOrderRequest(user.getId(), product.getId(), 1);
        ResponseEntity<Order> orderResponse = restTemplate.postForEntity(
            "/api/orders", orderRequest, Order.class);
        
        // 4. Verify order created
        assertEquals(HttpStatus.CREATED, orderResponse.getStatusCode());
        Order order = orderResponse.getBody();
        assertNotNull(order);
        assertEquals(user.getId(), order.getUserId());
    }
}
```

### 🔹 Consumer-Driven Contract Testing

**Testing from consumer perspective**

```java
// Pact consumer test
@RunWith(SpringRunner.class)
public class UserServiceConsumerTest {
    
    @Rule
    public PactProviderRuleMk2 mockProvider = new PactProviderRuleMk2(
        "user-service", "localhost", 8080, this);
    
    @Pact(consumer = "order-service")
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        return builder
            .given("user exists")
            .uponReceiving("a request to get user")
            .path("/api/users/1")
            .method("GET")
            .willRespondWith()
            .status(200)
            .body(newJsonBody((root) -> {
                root.numberType("id", 1);
                root.stringType("name", "John Doe");
                root.stringType("email", "john@example.com");
            }).build());
    }
    
    @Test
    @PactVerification("user-service")
    public void shouldGetUserFromProvider() {
        // Test against mock provider
        User user = userServiceClient.getUser(1L);
        assertEquals("John Doe", user.getName());
    }
}
```

### 🔹 Chaos Engineering

**Testing failure scenarios**

```java
@SpringBootTest
public class ChaosTest {
    
    @Autowired
    private OrderService orderService;
    
    @Test
    public void shouldHandleServiceFailure() {
        // Simulate database failure
        // Use tools like Chaos Monkey or custom test utilities
        
        // Attempt operation during failure
        assertThrows(ServiceUnavailableException.class, () -> 
            orderService.createOrder(orderRequest));
        
        // Verify fallback behavior
        // Check monitoring alerts
        // Validate error handling
    }
}
```

### 🔹 Performance Testing

**Testing under load**

```java
@RunWith(JUnit4.class)
public class PerformanceTest {
    
    @Test
    public void shouldHandleConcurrentRequests() {
        // Use JMeter or custom load testing
        int concurrentUsers = 100;
        int requestsPerUser = 10;
        
        // Execute load test
        LoadTestResult result = loadTester.runTest(concurrentUsers, requestsPerUser);
        
        // Assert performance metrics
        assertTrue(result.getAverageResponseTime() < 500); // ms
        assertEquals(0, result.getErrorCount());
        assertTrue(result.getThroughput() > 50); // requests/second
    }
}
```

### 🔹 Testing in Pipeline

**CI/CD testing strategy**

```yaml
# GitHub Actions example
name: Microservices Test Pipeline

on: [push]

jobs:
  test:
    runs-on: ubuntu-latest
    services:
      postgres:
        image: postgres:13
        env:
          POSTGRES_PASSWORD: postgres
      rabbitmq:
        image: rabbitmq:3-management
        ports:
          - 5672:5672
          - 15672:15672
    
    steps:
    - uses: actions/checkout@v2
    - name: Set up JDK
      uses: actions/setup-java@v2
      with:
        java-version: '11'
        distribution: 'adopt'
    
    - name: Run Unit Tests
      run: mvn test -Dtest="*UnitTest"
    
    - name: Run Integration Tests
      run: mvn test -Dtest="*IntegrationTest"
    
    - name: Run Contract Tests
      run: mvn test -Dtest="*ContractTest"
    
    - name: Build Docker Image
      run: docker build -t my-service .
    
    - name: Run Component Tests
      run: docker-compose -f docker-compose.test.yml up --abort-on-container-exit
```

### 🔹 Test Data Management

**Managing test data**

```java
@SpringBootTest
@Testcontainers
public class DatabaseIntegrationTest {
    
    @Container
    private static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:13")
        .withDatabaseName("testdb")
        .withUsername("test")
        .withPassword("test");
    
    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }
    
    @Autowired
    private UserRepository userRepository;
    
    @Test
    @Sql("/test-data.sql")  // Load test data
    public void shouldFindUserByEmail() {
        User user = userRepository.findByEmail("john@example.com");
        assertNotNull(user);
        assertEquals("John Doe", user.getName());
    }
}
```

### 🔹 Best Practices

```java
// Use test pyramid approach
// Isolate tests from external dependencies
// Use appropriate test doubles (mocks, stubs)
// Test both success and failure scenarios
// Implement comprehensive contract testing
// Use consumer-driven contracts
// Test asynchronous operations
// Implement chaos testing
// Automate testing in CI/CD pipeline
```

### 🔹 Testing Tools

**Testing frameworks and tools**

- **JUnit/Mockito**: Unit testing
- **Spring Boot Test**: Integration testing
- **Spring Cloud Contract**: Contract testing
- **Pact**: Consumer-driven contracts
- **Testcontainers**: Database testing
- **WireMock**: API mocking
- **Hoverfly**: Service virtualization
- **Chaos Monkey**: Chaos testing

---

## 🎯 Interview One-Liner

> Microservices testing: unit (isolated components), integration (service interactions), contract (API agreements), component (with mocks), E2E (full journeys); use test pyramid.
