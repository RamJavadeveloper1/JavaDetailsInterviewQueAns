### **Microservices Challenges**

## ✅ 104. How to handle distributed transactions in microservices?

**Distributed transactions in microservices are challenging because each service manages its own database, making traditional ACID transactions impossible. Solutions include sagas, event sourcing, and compensating transactions.**

### 🔹 The Problem

**Microservices break monolithic transactions**

```javascript
// Monolithic: Single transaction across tables
BEGIN TRANSACTION;
  UPDATE orders SET status = 'paid' WHERE id = 123;
  UPDATE inventory SET quantity = quantity - 1 WHERE product_id = 456;
COMMIT;

// Microservices: Separate databases
// Service A: Order DB
// Service B: Inventory DB
// No distributed transaction coordinator
```

### 🔹 Saga Pattern

**Sequence of local transactions with compensating actions**

```javascript
// Choreography Saga (event-driven)
Order Service: Create Order → Publish "OrderCreated"
Payment Service: Listen → Process Payment → Publish "PaymentProcessed"  
Inventory Service: Listen → Reserve Items → Publish "InventoryReserved"

// If payment fails: compensating transaction
Payment Service: Publish "PaymentFailed"
Order Service: Listen → Cancel Order
Inventory Service: Listen → Release Items
```

```java
// Orchestration Saga (central coordinator)
@Service
public class OrderSagaOrchestrator {
    
    @Autowired
    private OrderService orderService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private InventoryService inventoryService;
    
    public void processOrder(OrderRequest request) {
        try {
            // Step 1: Create order
            Order order = orderService.createOrder(request);
            
            // Step 2: Process payment
            paymentService.processPayment(order.getId(), request.getPayment());
            
            // Step 3: Reserve inventory
            inventoryService.reserveInventory(order.getItems());
            
            // Step 4: Complete order
            orderService.completeOrder(order.getId());
            
        } catch (PaymentFailedException e) {
            // Compensating transactions
            inventoryService.releaseInventory(order.getItems());
            orderService.cancelOrder(order.getId());
            throw e;
        }
    }
}
```

### 🔹 Event Sourcing

**Store state changes as events**

```java
// Instead of updating state, append events
public class OrderAggregate {
    
    private List<OrderEvent> events = new ArrayList<>();
    
    public void createOrder(CreateOrderCommand command) {
        OrderCreated event = new OrderCreated(
            command.getOrderId(),
            command.getItems(),
            command.getTotal()
        );
        events.add(event);
        // Apply event to current state
        apply(event);
    }
    
    private void apply(OrderCreated event) {
        this.orderId = event.getOrderId();
        this.items = event.getItems();
        this.status = OrderStatus.CREATED;
    }
}

// Event handlers in other services
@Service
public class PaymentEventHandler {
    
    @EventListener
    public void handleOrderCreated(OrderCreated event) {
        // Process payment asynchronously
        paymentService.processPayment(event.getOrderId(), event.getTotal());
    }
}
```

### 🔹 Two-Phase Commit (2PC)

**Distributed transaction coordinator**

```java
// Coordinator manages transaction across services
public class TransactionCoordinator {
    
    public void executeDistributedTransaction(List<Participant> participants) {
        // Phase 1: Prepare
        for (Participant p : participants) {
            if (!p.prepare()) {
                // Abort all
                for (Participant p2 : participants) {
                    p2.rollback();
                }
                return;
            }
        }
        
        // Phase 2: Commit
        for (Participant p : participants) {
            p.commit();
        }
    }
}

// Drawbacks: Blocking, single point of failure
```

### 🔹 Best Practices

```javascript
// Prefer Saga over 2PC
// Use idempotent operations
// Implement timeout mechanisms
// Monitor transaction states
// Use circuit breakers for resilience
// Consider eventual consistency
```

---

## 🎯 Interview One-Liner

> Handle distributed transactions with Saga pattern (choreography/orchestration), event sourcing for audit trails, or 2PC for strong consistency; prefer eventual consistency over tight coupling.

---

## ✅ 105. How to maintain data consistency across microservices?

**Data consistency in microservices requires balancing between strong consistency (difficult) and eventual consistency (practical), using patterns like sagas, CQRS, and event-driven architecture.**

### 🔹 Consistency Types

**CAP Theorem trade-offs**

```javascript
// CAP Theorem: Choose 2 out of 3
// Consistency: All nodes see same data
// Availability: System remains operational
// Partition Tolerance: Works despite network failures

// Microservices: Prioritize AP (Availability + Partition Tolerance)
// Accept eventual consistency
```

### 🔹 Eventual Consistency

**Data becomes consistent over time**

```java
// Order Service
@Service
public class OrderService {
    
    @Transactional
    public void createOrder(CreateOrderRequest request) {
        Order order = orderRepository.save(new Order(request));
        // Publish event asynchronously
        eventPublisher.publish(new OrderCreatedEvent(order));
    }
}

// Inventory Service (eventual consistency)
@Service
public class InventoryEventHandler {
    
    @EventListener
    @Transactional
    public void handleOrderCreated(OrderCreatedEvent event) {
        for (OrderItem item : event.getItems()) {
            inventoryRepository.decreaseStock(item.getProductId(), item.getQuantity());
        }
    }
}
```

### 🔹 CQRS Pattern

**Separate read and write models**
```java
// Write Model (Command)
public class OrderCommandService {
    
    public void createOrder(CreateOrderCommand command) {
        // Validate business rules
        if (inventoryService.checkAvailability(command.getItems())) {
            Order order = new Order(command);
            orderRepository.save(order);
            eventPublisher.publish(new OrderCreatedEvent(order));
        }
    }
}

// Read Model (Query) - Materialized View
public class OrderQueryService {
    
    public OrderDto getOrder(String orderId) {
        // Read from optimized read database
        return orderReadRepository.findById(orderId);
    }
}
```

### 🔹 Saga Pattern for Consistency

**Maintain consistency through compensating actions**

```java
// Forward steps
1. Reserve inventory
2. Process payment  
3. Ship order

// If payment fails: compensating steps
3. Cancel shipment
2. Refund payment
1. Release inventory
```

### 🔹 Distributed Locks

**Prevent concurrent modifications**

```java
// Redis distributed lock
@Service
public class InventoryService {
    
    @Autowired
    private RedisTemplate<String, String> redis;
    
    public boolean reserveStock(String productId, int quantity) {
        String lockKey = "lock:inventory:" + productId;
        
        // Try to acquire lock
        Boolean locked = redis.opsForValue().setIfAbsent(lockKey, "locked", 
            Duration.ofSeconds(30));
            
        if (locked) {
            try {
                // Check and update stock
                int currentStock = getCurrentStock(productId);
                if (currentStock >= quantity) {
                    updateStock(productId, currentStock - quantity);
                    return true;
                }
            } finally {
                redis.delete(lockKey);
            }
        }
        return false;
    }
}
```

### 🔹 Monitoring Consistency

**Detect and resolve inconsistencies**

```java
// Reconciliation job
@Scheduled(fixedRate = 300000) // Every 5 minutes
public void reconcileInventory() {
    List<Order> recentOrders = orderRepository.findRecentOrders();
    
    for (Order order : recentOrders) {
        int expectedStock = calculateExpectedStock(order);
        int actualStock = inventoryService.getActualStock(order.getProductId());
        
        if (expectedStock != actualStock) {
            // Log inconsistency and alert
            alertService.sendAlert("Stock inconsistency for product: " + order.getProductId());
        }
    }
}
```

### 🔹 Best Practices

```javascript
// Embrace eventual consistency
// Use domain events for communication
// Implement idempotent operations
// Monitor for inconsistencies
// Use circuit breakers
// Design for failure scenarios
```

---

## 🎯 Interview One-Liner

> Maintain consistency with eventual consistency via events, CQRS for separate read/write models, sagas for complex transactions, and monitoring/reconciliation for detecting issues.

---

## ✅ 106. How to handle authentication in microservices?

**Authentication in microservices requires centralized auth management, token-based approaches, and secure inter-service communication using JWT, OAuth2, or API gateways.**

### 🔹 Centralized Authentication

**API Gateway handles authentication**

```java
// API Gateway
@Component
public class AuthenticationFilter implements GatewayFilter {
    
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = extractToken(exchange.getRequest());
        
        if (token == null || !jwtService.validateToken(token)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        
        // Add user info to headers
        exchange.getRequest().mutate()
            .header("X-User-Id", jwtService.getUserId(token))
            .header("X-User-Roles", jwtService.getRoles(token))
            .build();
            
        return chain.filter(exchange);
    }
}
```

### 🔹 JWT Tokens

**Stateless authentication tokens**

```java
// Generate JWT
@Service
public class JwtService {
    
    @Value("${jwt.secret}")
    private String secret;
    
    public String generateToken(User user) {
        return Jwts.builder()
            .setSubject(user.getId())
            .claim("roles", user.getRoles())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 24h
            .signWith(SignatureAlgorithm.HS256, secret)
            .compact();
    }
    
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
```

### 🔹 OAuth2 Authorization Server

**Centralized token management**

```java
// Authorization Server Configuration
@Configuration
@EnableAuthorizationServer
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {
    
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
            .withClient("client-id")
            .secret("client-secret")
            .authorizedGrantTypes("authorization_code", "refresh_token")
            .scopes("read", "write")
            .redirectUris("http://localhost:8080/callback");
    }
}
```

### 🔹 Service-to-Service Authentication

**Mutual TLS or API keys**

```java
// Mutual TLS
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .anyRequest().authenticated()
            .and()
            .x509()  // Client certificate authentication
            .subjectPrincipalRegex("CN=(.*?)(?:,|$)")
            .userDetailsService(userDetailsService());
    }
}

// API Key authentication
@Component
public class ApiKeyAuthFilter extends OncePerRequestFilter {
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
            HttpServletResponse response, FilterChain filterChain) {
        
        String apiKey = request.getHeader("X-API-Key");
        if (apiKey == null || !apiKeyService.isValid(apiKey)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        
        filterChain.doFilter(request, response);
    }
}
```

### 🔹 Session Management

**Distributed sessions with Redis**

```java
@Configuration
public class SessionConfig {
    
    @Bean
    public RedisConnectionFactory connectionFactory() {
        return new JedisConnectionFactory();
    }
    
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory());
        return template;
    }
    
    @Bean
    public HttpSessionStrategy httpSessionStrategy() {
        return new HeaderHttpSessionStrategy();
    }
}
```

### 🔹 Security Best Practices

```javascript
// Use HTTPS everywhere
// Rotate tokens regularly
// Implement token revocation
// Use short-lived access tokens with refresh tokens
// Validate tokens on every request
// Log authentication failures
// Implement rate limiting
```

---

## 🎯 Interview One-Liner

> Handle authentication with API Gateway for central auth, JWT for stateless tokens, OAuth2 for authorization, mutual TLS for service-to-service, and distributed sessions for state management.

---

## ✅ 107. What is API versioning strategy in microservices?

**API versioning ensures backward compatibility when evolving microservices, allowing clients to migrate gradually using URI versioning, headers, or content negotiation.**

### 🔹 URI Versioning

**Version in URL path**

```java
// Version 1
@RestController
@RequestMapping("/api/v1/users")
public class UserControllerV1 {
    
    @GetMapping
    public List<User> getUsers() {
        return userService.getAllUsers();
    }
}

// Version 2
@RestController
@RequestMapping("/api/v2/users")
public class UserControllerV2 {
    
    @GetMapping
    public List<UserDto> getUsers() {
        return userService.getAllUsers()
            .stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }
}
```

### 🔹 Query Parameter Versioning

**Version as query parameter**

```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @GetMapping
    public ResponseEntity<?> getUsers(@RequestParam(required = false) String version) {
        if ("v2".equals(version)) {
            return ResponseEntity.ok(userService.getUsersV2());
        }
        return ResponseEntity.ok(userService.getUsersV1());
    }
}

// Usage: GET /api/users?version=v2
```

### 🔹 Header Versioning

**Version in custom header**

```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @GetMapping
    public ResponseEntity<?> getUsers(@RequestHeader(value = "X-API-Version", required = false) String version) {
        if ("v2".equals(version)) {
            return ResponseEntity.ok(userService.getUsersV2());
        }
        return ResponseEntity.ok(userService.getUsersV1());
    }
}

// Usage: GET /api/users
// Header: X-API-Version: v2
```

### 🔹 Accept Header Versioning

**Version in Accept header**

```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @GetMapping
    public ResponseEntity<?> getUsers(@RequestHeader("Accept") String accept) {
        if (accept.contains("vnd.example.v2+json")) {
            return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/vnd.example.v2+json"))
                .body(userService.getUsersV2());
        }
        return ResponseEntity.ok(userService.getUsersV1());
    }
}

// Usage: GET /api/users
// Accept: application/vnd.example.v2+json
```

### 🔹 Semantic Versioning

**Version numbering convention**

```java
// Major.Minor.Patch
// v1.0.0 - Initial release
// v1.1.0 - Backward compatible additions
// v2.0.0 - Breaking changes
// v2.1.0 - New features
// v2.1.1 - Bug fixes

// API Evolution
// PATCH: Bug fixes, no API changes
// MINOR: New features, backward compatible
// MAJOR: Breaking changes, new API version
```

### 🔹 Deprecation Strategy

**Graceful version transitions**

```java
@RestController
@RequestMapping("/api/v1/users")
@Deprecated
public class UserControllerV1 {
    
    @GetMapping
    @Deprecated
    public List<User> getUsers() {
        // Add deprecation warning
        return userService.getAllUsers();
    }
}

// Response headers for deprecation
@GetMapping
public ResponseEntity<List<User>> getUsers() {
    return ResponseEntity.ok()
        .header("Deprecation", "true")
        .header("Link", "</api/v2/users>; rel=\"successor-version\"")
        .body(userService.getAllUsers());
}
```

### 🔹 Version Routing

**Dynamic version routing**

```java
@Component
public class ApiVersionRequestMappingHandlerMapping extends RequestMappingHandlerMapping {
    
    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        RequestMappingInfo info = super.getMappingForMethod(method, handlerType);
        if (info != null) {
            // Add version condition
            info = info.combine(RequestMappingInfo
                .paths("/api/{version}/users")
                .methods(RequestMethod.GET)
                .build());
        }
        return info;
    }
}
```

### 🔹 Best Practices

```javascript
// Use semantic versioning
// Document version changes clearly
// Support multiple versions simultaneously
// Set deprecation timelines
// Monitor version usage
// Plan migration strategies
// Use feature flags for gradual rollout
```

---

## 🎯 Interview One-Liner

> API versioning strategies: URI paths (/api/v1/users), custom headers (X-API-Version), query params (?version=v2), or Accept headers; use semantic versioning and deprecation headers for smooth transitions.

---

## ✅ 108. How to test microservices?

**Testing microservices requires unit tests, integration tests, contract tests, and end-to-end tests, using tools like Spring Boot Test, WireMock, and Pact for comprehensive coverage.**

### 🔹 Testing Pyramid

**Different levels of testing**

```javascript
// Unit Tests (Bottom layer - most tests)
// Test individual components in isolation
// Fast, reliable, cheap to maintain

// Integration Tests (Middle layer)
// Test interactions between components
// Slower, more complex

// End-to-End Tests (Top layer - fewest tests)
// Test complete user journeys
// Slowest, most brittle, most expensive
```

### 🔹 Unit Testing

**Test individual services**

```java
@SpringBootTest
class UserServiceTest {
    
    @MockBean
    private UserRepository userRepository;
    
    @Autowired
    private UserService userService;
    
    @Test
    void createUser_ShouldReturnSavedUser() {
        // Arrange
        User user = new User("John", "john@example.com");
        when(userRepository.save(user)).thenReturn(user);
        
        // Act
        User result = userService.createUser(user);
        
        // Assert
        assertEquals("John", result.getName());
        verify(userRepository).save(user);
    }
}
```

### 🔹 Integration Testing

**Test service with dependencies**

```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerIntegrationTest {
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    void getUser_ShouldReturnUser() {
        // Test against real database and HTTP layer
        ResponseEntity<User> response = restTemplate.getForEntity("/api/users/1", User.class);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}
```

### 🔹 Contract Testing

**Verify API contracts between services**

```java
// Consumer side (Pact)
@ExtendWith(PactConsumerTestExt.class)
class UserServiceContractTest {
    
    @Pact(consumer = "user-service", provider = "notification-service")
    public RequestResponsePact sendNotification(PactDslWithProvider builder) {
        return builder
            .given("user exists")
            .uponReceiving("a request to send notification")
            .path("/api/notifications")
            .method("POST")
            .body("{\"userId\": \"123\", \"message\": \"Welcome\"}")
            .willRespondWith()
            .status(200)
            .toPact();
    }
    
    @Test
    @PactTestFor(pactMethod = "sendNotification")
    void testSendNotification() {
        // Test against mock provider
    }
}
```

### 🔹 Component Testing

**Test service with mocked dependencies**

```java
@SpringBootTest
@AutoConfigureWireMock(port = 0)
class PaymentServiceTest {
    
    @Autowired
    private PaymentService paymentService;
    
    @Test
    void processPayment_ShouldCallExternalAPI() {
        // Mock external payment API
        stubFor(post(urlEqualTo("/api/payments"))
            .willReturn(aResponse()
                .withStatus(200)
                .withBody("{\"status\": \"success\"}")));
        
        PaymentResult result = paymentService.processPayment(new PaymentRequest());
        
        assertEquals(PaymentStatus.SUCCESS, result.getStatus());
    }
}
```

### 🔹 End-to-End Testing

**Test complete system**

```java
@SpringBootTest
@AutoConfigureWireMock(port = 0)
class E2ETest {
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    void completeOrderFlow_ShouldWorkEndToEnd() {
        // Mock external services
        stubFor(post("/api/payments").willReturn(ok()));
        stubFor(post("/api/inventory").willReturn(ok()));
        
        // Create order
        OrderRequest request = new OrderRequest(/*...*/);
        ResponseEntity<Order> response = restTemplate.postForEntity("/api/orders", request, Order.class);
        
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        // Verify order was created and events were published
    }
}
```

### 🔹 Testing Tools

**Frameworks and libraries**

```java
// Unit Testing
JUnit 5, Mockito, AssertJ

// Integration Testing  
Spring Boot Test, TestContainers

// Contract Testing
Pact, Spring Cloud Contract

// API Testing
REST Assured, WireMock

// Performance Testing
JMeter, Gatling

// Chaos Testing
Chaos Monkey, Gremlin
```

### 🔹 Best Practices

```javascript
// Test in isolation first
// Use test doubles for external dependencies
// Test failure scenarios
// Automate testing in CI/CD
// Monitor test coverage
// Use consumer-driven contracts
// Test asynchronous operations
// Include performance tests
```

---

## 🎯 Interview One-Liner
> Test microservices with unit tests (isolated components), integration tests (service interactions), contract tests (API compatibility), component tests (with mocks), and E2E tests (full system); use Spring Boot Test, WireMock, Pact.
