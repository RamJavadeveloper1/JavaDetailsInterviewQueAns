### **Spring AOP**
## ✅ 71. What is AOP? Key terminologies?

**Aspect-Oriented Programming (AOP) is a programming paradigm that enables modularization of cross-cutting concerns (like logging, security, transactions) by separating them from business logic, allowing for cleaner and more maintainable code.**

### 🔹 What is AOP?

**Programming paradigm for cross-cutting concerns**

```java
// Traditional approach - scattering concerns
@Service
public class UserService {
    
    public void createUser(User user) {
        // Logging scattered throughout
        logger.info("Creating user: " + user.getName());
        
        // Security check scattered
        securityService.checkPermission("CREATE_USER");
        
        // Transaction scattered
        transactionManager.begin();
        try {
            userRepository.save(user);
            transactionManager.commit();
        } catch (Exception e) {
            transactionManager.rollback();
            throw e;
        }
        
        // Audit scattered
        auditService.log("User created: " + user.getId());
    }
}

// AOP approach - concerns separated
@Service
public class UserService {
    
    public void createUser(User user) {
        userRepository.save(user);  // Clean business logic
    }
}

// Cross-cutting concerns handled by aspects
@Aspect
@Component
public class LoggingAspect {
    @Before("execution(* com.example.UserService.*(..))")
    public void logMethodCall() {
        // Centralized logging
    }
}
```

### 🔹 Key Terminologies

### 🔹 1. Aspect

**Module containing cross-cutting logic**

```java
@Aspect
@Component
public class SecurityAspect {
    // Contains security-related cross-cutting logic
}
```

### 🔹 2. Join Point

**Point in program execution where aspect can be applied**

```java
// Join points in Spring AOP:
- Method execution
- Constructor execution
- Field access/modification
- Exception handling
- Class initialization

// Example join points
public class UserService {
    public void createUser(User user) {}     // Method execution join point
    public User getUser(Long id) {}          // Method execution join point
    private void validateUser(User user) {}  // Method execution join point
}
```

### 🔹 3. Pointcut

**Expression that matches join points**

```java
@Aspect
@Component
public class LoggingAspect {
    
    // Pointcut expressions
    @Pointcut("execution(* com.example.service.*.*(..))")
    public void serviceMethods() {}
    
    @Pointcut("execution(* com.example.service.UserService.*(..))")
    public void userServiceMethods() {}
    
    @Pointcut("@annotation(com.example.annotation.Auditable)")
    public void auditableMethods() {}
    
    @Pointcut("within(com.example.controller.*)")
    public void controllerClasses() {}
}
```

### 🔹 4. Advice

**Action taken at join point**

```java
@Aspect
@Component
public class TransactionAspect {
    
    @Around("serviceMethods()")
    public Object manageTransaction(ProceedingJoinPoint joinPoint) {
        // Advice implementation
    }
}
```

### 🔹 5. Weaving

**Process of applying aspects to target object**

```java
// Types of weaving:
1. Compile-time weaving (AspectJ compiler)
2. Load-time weaving (AspectJ agent)
3. Runtime weaving (Spring AOP - proxy-based)

// Spring AOP uses runtime weaving with proxies
UserService proxy = (UserService) Proxy.newProxyInstance(
    UserService.class.getClassLoader(),
    new Class[]{UserService.class},
    new LoggingInvocationHandler(targetService)
);
```

### 🔹 6. Target Object

**Object being advised**

```java
@Service
public class UserService {  // Target object
    public void createUser(User user) {
        // This method is the target of advice
    }
}
```

### 🔹 7. Proxy

**Object created by AOP framework**

```java
// JDK Dynamic Proxy (for interfaces)
UserService proxy = (UserService) Proxy.newProxyInstance(
    UserService.class.getClassLoader(),
    new Class[]{UserService.class},
    invocationHandler
);

// CGLIB Proxy (for classes)
UserService proxy = (UserService) Enhancer.create(
    UserService.class,
    callback
);
```

### 🔹 Pointcut Expressions
**Syntax for matching join points**

```java
// Method signature patterns
execution(modifiers-pattern? ret-type-pattern declaring-type-pattern?name-pattern(param-pattern)
          throws-pattern?)

// Examples:
execution(* com.example.service.*.*(..))           // All methods in service package
execution(public * com.example.UserService.*(..))  // Public methods in UserService
execution(* com.example.*.save*(..))               // Methods starting with save
execution(* *(..) throws Exception)                // Methods throwing Exception

// Within patterns
within(com.example.service.*)                      // Classes in service package
within(com.example..*Controller)                   // Classes ending with Controller

// Annotation patterns
@annotation(com.example.Auditable)                 // Methods with @Auditable
@within(com.example.Service)                       // Classes with @Service
```

### 🔹 AOP Proxies in Spring

**How Spring implements AOP**

```java
@Configuration
@EnableAspectJAutoProxy
public class AopConfig {
    // Enables AOP proxy creation
}

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Transactional  // Uses AOP proxy
    public void createUser(User user) {
        userRepository.save(user);
    }
}

// Spring creates proxy:
// UserService proxy = new UserServiceProxy(targetService);
// proxy implements same interface, delegates to target, applies advice
```

### 🔹 Best Practices

```java
// Use AOP for cross-cutting concerns only
// Keep aspects focused on single responsibility
// Use meaningful pointcut names
// Prefer execution pointcuts over within
// Avoid circular dependencies
// Test aspects thoroughly
// Document aspect behavior
// Use @EnableAspectJAutoProxy(proxyTargetClass = true) for CGLIB
```

---

## 🎯 Interview One-Liner

> AOP modularizes cross-cutting concerns (logging, security, transactions); key terms: Aspect (logic), Join Point (execution point), Pointcut (matching expression), Advice (action), Weaving (application).

---

## ✅ 72. Types of advice in Spring AOP

**Spring AOP provides five types of advice that define when and how cross-cutting logic is applied to join points: Before, After, AfterReturning, AfterThrowing, and Around advice.**

### 🔹 Before Advice

**Executes before target method**

```java
@Aspect
@Component
public class LoggingAspect {
    
    @Before("execution(* com.example.service.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        
        logger.info("Before executing: {} with args: {}", methodName, args);
    }
}

// Usage
@Service
public class UserService {
    public void createUser(User user) {
        // Advice executes BEFORE this method
    }
}
```

### 🔹 After Advice

**Executes after target method (always)**

```java
@Aspect
@Component
public class CleanupAspect {
    
    @After("execution(* com.example.service.*.*(..))")
    public void cleanup(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        
        logger.info("After executing: {} (always executed)", methodName);
        // Cleanup resources, close connections, etc.
    }
}

// Usage
@Service
public class FileService {
    public void processFile(String filePath) {
        // Advice executes AFTER this method, even if exception thrown
    }
}
```

### 🔹 AfterReturning Advice

**Executes after successful method completion**

```java
@Aspect
@Component
public class AuditAspect {
    
    @AfterReturning(
        pointcut = "execution(* com.example.service.*.*(..))",
        returning = "result"
    )
    public void auditSuccess(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        
        logger.info("Method {} completed successfully, returned: {}", 
                   methodName, result);
        
        // Log successful operation
        auditService.logSuccess(methodName, result);
    }
}

// Usage
@Service
public class PaymentService {
    public PaymentResult processPayment(Payment payment) {
        // Advice executes only if method returns normally
        return new PaymentResult(true);
    }
}
```

### 🔹 AfterThrowing Advice

**Executes when method throws exception**

```java
@Aspect
@Component
public class ErrorHandlingAspect {
    
    @AfterThrowing(
        pointcut = "execution(* com.example.service.*.*(..))",
        throwing = "exception"
    )
    public void handleException(JoinPoint joinPoint, Exception exception) {
        String methodName = joinPoint.getSignature().getName();
        
        logger.error("Method {} threw exception: {}", 
                    methodName, exception.getMessage());
        
        // Send alert, log error details, cleanup
        errorService.handleException(methodName, exception);
    }
}

// Usage
@Service
public class OrderService {
    public void placeOrder(Order order) {
        // If exception thrown, advice executes
        if (order.getItems().isEmpty()) {
            throw new IllegalArgumentException("Order must have items");
        }
    }
}
```

### 🔹 Around Advice

**Most powerful advice - controls method execution**

```java
@Aspect
@Component
public class PerformanceAspect {
    
    @Around("execution(* com.example.service.*.*(..))")
    public Object measurePerformance(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        long startTime = System.currentTimeMillis();
        
        try {
            logger.info("Starting execution of: {}", methodName);
            
            // Execute the target method
            Object result = joinPoint.proceed();
            
            long endTime = System.currentTimeMillis();
            logger.info("Completed {} in {} ms", methodName, (endTime - startTime));
            
            return result;
            
        } catch (Exception e) {
            long endTime = System.currentTimeMillis();
            logger.error("Failed {} in {} ms with error: {}", 
                        methodName, (endTime - startTime), e.getMessage());
            throw e;
        }
    }
}

// Usage
@Service
public class ReportService {
    public Report generateReport(ReportRequest request) {
        // Around advice can:
        // - Skip method execution
        // - Modify parameters
        // - Modify return value
        // - Handle exceptions
        // - Retry operations
        return new Report();
    }
}
```

### 🔹 Advice Execution Order

**Order when multiple advices apply**

```java
@Aspect
@Component
public class MultipleAspects {
    
    @Around("serviceMethods()")
    @Order(1)
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) {
        // Executes first (lowest order)
        return joinPoint.proceed();
    }
    
    @Before("serviceMethods()")
    @Order(2)
    public void beforeAdvice() {
        // Executes second
    }
    
    @After("serviceMethods()")
    @Order(3)
    public void afterAdvice() {
        // Executes third
    }
}
```

### 🔹 Advice Parameters

**Accessing join point information**

```java
@Aspect
@Component
public class ParameterAspect {
    
    @Before("execution(* *(..)) && args(user, amount)")
    public void validatePayment(User user, BigDecimal amount) {
        // Access method parameters directly
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
    }
    
    @Around("execution(* *(..))")
    public Object logExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        // Access join point details
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        Object target = joinPoint.getTarget();
        
        return joinPoint.proceed();
    }
}
```

### 🔹 Best Practices

```java
// Use @Before for validation and logging
// Use @After for cleanup operations
// Use @AfterReturning for success auditing
// Use @AfterThrowing for error handling
// Use @Around for performance monitoring and caching
// Keep advice methods simple and focused
// Use @Order for controlling advice execution order
// Avoid complex logic in advice methods
```

---

## 🎯 Interview One-Liner

> Spring AOP advices: @Before (pre-execution), @After (post-execution always), @AfterReturning (on success), @AfterThrowing (on exception), @Around (full control of execution).

---

## ✅ 73. @Before vs @After vs @Around

**@Before executes before method, @After executes after method completion (always), and @Around provides full control over method execution with ability to modify parameters, return values, and handle exceptions.**

### 🔹 @Before Advice

**Pre-method execution logic**

```java
@Aspect
@Component
public class BeforeAspect {
    
    @Before("execution(* com.example.service.*.*(..))")
    public void validateInput(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        
        logger.info("Validating input for: {}", methodName);
        
        // Validate method parameters
        for (Object arg : args) {
            if (arg == null) {
                throw new IllegalArgumentException("Null argument not allowed");
            }
        }
    }
}

// Characteristics:
// ✅ Executes before target method
// ✅ Cannot prevent method execution
// ✅ Cannot modify return value
// ✅ Cannot access return value
// ✅ Cannot handle exceptions from target method
// ✅ Good for: validation, logging, security checks
```

### 🔹 @After Advice

**Post-method execution logic (always)**

```java
@Aspect
@Component
public class AfterAspect {
    
    @After("execution(* com.example.service.*.*(..))")
    public void cleanup(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        
        logger.info("Cleaning up after: {}", methodName);
        
        // Cleanup resources
        ThreadLocalContext.remove();
        
        // Close connections
        ConnectionManager.closeConnections();
    }
}

// Characteristics:
// ✅ Executes after target method (always)
// ✅ Executes even if target method throws exception
// ✅ Cannot access return value
// ✅ Cannot modify return value
// ✅ Cannot prevent method execution
// ✅ Good for: cleanup, resource management
```

### 🔹 @Around Advice

**Complete method execution control**

```java
@Aspect
@Component
public class AroundAspect {
    
    @Around("execution(* com.example.service.*.*(..))")
    public Object cacheAndExecute(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        
        // Check cache first
        String cacheKey = generateCacheKey(methodName, args);
        Object cachedResult = cache.get(cacheKey);
        if (cachedResult != null) {
            logger.info("Returning cached result for: {}", methodName);
            return cachedResult;
        }
        
        // Measure performance
        long startTime = System.currentTimeMillis();
        
        try {
            logger.info("Executing: {}", methodName);
            
            // Execute target method
            Object result = joinPoint.proceed();
            
            // Cache result
            cache.put(cacheKey, result);
            
            long endTime = System.currentTimeMillis();
            logger.info("Completed {} in {} ms", methodName, (endTime - startTime));
            
            return result;
            
        } catch (Exception e) {
            long endTime = System.currentTimeMillis();
            logger.error("Failed {} in {} ms: {}", 
                        methodName, (endTime - startTime), e.getMessage());
            
            // Retry logic
            if (isRetryable(e)) {
                return retry(joinPoint);
            }
            
            throw e;
        }
    }
}

// Characteristics:
// ✅ Full control over method execution
// ✅ Can prevent method execution
// ✅ Can modify method parameters
// ✅ Can modify return value
// ✅ Can handle exceptions
// ✅ Can retry operations
// ✅ Most powerful but complex
```

### 🔹 Comparison Table

| Feature                   | @Before | @After | @Around           |
|---------                  |---------|--------|-------------------|
| **Execution Timing**  Before method | After method|Around method |
| **Access to Return Value**| ❌      | ❌  | ✅                   |
| **Modify Return Value**   | ❌      | ❌  | ✅                   |
| **Access to Exception**   | ❌      | ❌  | ✅                   |
| **Modify Parameters**     | ❌      | ❌  | ✅                   |
| **Prevent Execution**     | ❌      | ❌  | ✅                   |
| **Exception Handling**    | ❌      | ❌  | ✅                   |
| **Performance Impact**    | Low     | Low | Medium-High          |
| **Complexity**            | Low     | Low | High                 |

### 🔹 When to Use Each

```java
// Use @Before for:
// - Input validation
// - Security checks
// - Logging method entry
// - Setting up context

// Use @After for:
// - Resource cleanup
// - Connection closing
// - Context cleanup
// - Always-needed operations

// Use @Around for:
// - Caching
// - Performance monitoring
// - Transaction management
// - Retry logic
// - Dynamic behavior modification
```

### 🔹 Common Patterns

```java
// Validation with @Before
@Before("execution(* *.*(..)) && args(user)")
public void validateUser(User user) {
    if (user == null || user.getId() == null) {
        throw new ValidationException("Invalid user");
    }
}

// Cleanup with @After
@After("execution(* com.example.FileService.*(..))")
public void closeFileHandles() {
    FileHandleManager.closeAll();
}

// Caching with @Around
@Around("execution(* com.example.CacheableService.*(..))")
public Object cacheResult(ProceedingJoinPoint joinPoint) throws Throwable {
    String key = generateKey(joinPoint);
    Object cached = cache.get(key);
    return cached != null ? cached : cache.put(key, joinPoint.proceed());
}
```

### 🔹 Best Practices

```java
// Choose advice type based on requirements
// Use @Before for simple pre-processing
// Use @After for cleanup operations
// Use @Around only when you need full control
// Keep advice methods simple
// Test advice behavior thoroughly
// Document advice behavior and side effects
```

---

## 🎯 Interview One-Liner

> @Before: pre-execution logic; @After: post-execution cleanup (always); @Around: full control - can modify params/results, handle exceptions, prevent execution.

---

## ✅ 74. When to use AOP?

**Use AOP for cross-cutting concerns that span multiple application layers, such as logging, security, transactions, caching, and performance monitoring, when you want to keep business logic clean and maintainable.**

### 🔹 Cross-Cutting Concerns

**Concerns that affect multiple components**

```java
// ✅ Good candidates for AOP:

// 1. Logging
@Aspect
@Component
public class LoggingAspect {
    @Around("execution(* com.example.service.*.*(..))")
    public Object logExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Executing: {}", joinPoint.getSignature().getName());
        return joinPoint.proceed();
    }
}

// 2. Security
@Aspect
@Component
public class SecurityAspect {
    @Before("execution(* com.example.service.*.*(..))")
    public void checkSecurity() {
        if (!securityContext.isAuthenticated()) {
            throw new SecurityException("Not authenticated");
        }
    }
}

// 3. Transaction Management
@Aspect
@Component
public class TransactionAspect {
    @Around("@annotation(org.springframework.transaction.annotation.Transactional)")
    public Object manageTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        TransactionStatus status = transactionManager.getTransaction(definition);
        try {
            Object result = joinPoint.proceed();
            transactionManager.commit(status);
            return result;
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw e;
        }
    }
}
```

### 🔹 When to Use AOP

### 🔹 1. Logging

**Centralized logging across application**

```java
@Aspect
@Component
public class LoggingAspect {
    
    @Before("execution(* com.example.service.*.*(..))")
    public void logMethodEntry(JoinPoint joinPoint) {
        logger.info("Entering: {} with args: {}", 
                   joinPoint.getSignature().getName(),
                   joinPoint.getArgs());
    }
    
    @AfterReturning("execution(* com.example.service.*.*(..))")
    public void logMethodExit(JoinPoint joinPoint, Object result) {
        logger.info("Exiting: {} with result: {}", 
                   joinPoint.getSignature().getName(),
                   result);
    }
}
```

### 🔹 2. Security

**Authorization and authentication**

```java
@Aspect
@Component
public class SecurityAspect {
    
    @Before("@annotation(com.example.Secured)")
    public void checkPermission(JoinPoint joinPoint) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Secured annotation = method.getAnnotation(Secured.class);
        
        if (!permissionService.hasPermission(annotation.role())) {
            throw new AccessDeniedException("Insufficient permissions");
        }
    }
}
```

### 🔹 3. Performance Monitoring

**Measure method execution time**

```java
@Aspect
@Component
public class PerformanceAspect {
    
    @Around("execution(* com.example.service.*.*(..))")
    public Object monitorPerformance(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            long executionTime = System.currentTimeMillis() - start;
            
            if (executionTime > 1000) { // Log slow methods
                logger.warn("Slow method: {} took {} ms", 
                           joinPoint.getSignature().getName(), executionTime);
            }
            
            return result;
        } catch (Exception e) {
            long executionTime = System.currentTimeMillis() - start;
            logger.error("Method {} failed after {} ms", 
                        joinPoint.getSignature().getName(), executionTime);
            throw e;
        }
    }
}
```

### 🔹 4. Caching

**Cache method results**

```java
@Aspect
@Component
public class CachingAspect {
    
    @Around("@annotation(com.example.Cacheable)")
    public Object cacheResult(ProceedingJoinPoint joinPoint) throws Throwable {
        String key = generateKey(joinPoint);
        
        Object cached = cache.get(key);
        if (cached != null) {
            return cached;
        }
        
        Object result = joinPoint.proceed();
        cache.put(key, result);
        return result;
    }
}
```

### 🔹 5. Exception Handling

**Centralized exception handling**

```java
@Aspect
@Component
public class ExceptionHandlingAspect {
    
    @AfterThrowing("execution(* com.example.service.*.*(..))")
    public void handleException(JoinPoint joinPoint, Exception exception) {
        logger.error("Exception in {}: {}", 
                    joinPoint.getSignature().getName(),
                    exception.getMessage());
        
        // Send notification, log to monitoring system
        monitoringService.reportException(exception);
    }
}
```

### 🔹 6. Audit Trail

**Track data changes**

```java
@Aspect
@Component
public class AuditAspect {
    
    @AfterReturning("execution(* com.example.service.*.save*(..))")
    public void auditSave(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        
        auditService.log("Data saved via " + methodName, args, result);
    }
}
```

### 🔹 When NOT to Use AOP

```java
// ❌ Don't use AOP for:

// Business logic
@Service
public class OrderService {
    // Don't put business rules in aspects
    public void processOrder(Order order) {
        // Business logic belongs here
    }
}

// Data validation (use Bean Validation)
public void createUser(@Valid User user) {
    // Validation belongs here
}

// Configuration
@Configuration
public class AppConfig {
    // Configuration belongs here
}
```

### 🔹 Best Practices

```java
// Use AOP for true cross-cutting concerns
// Keep aspects focused on single responsibility
// Make aspects configurable
// Test aspects thoroughly
// Document aspect behavior
// Avoid overusing @Around advice
// Use annotations to control aspect application
// Monitor performance impact
```
### 🔹 Alternatives to AOP

```java
// For simple cases, consider:

// 1. Utility classes
public class LoggingUtils {
    public static void logMethodEntry(String methodName) {
        logger.info("Entering: {}", methodName);
    }
}

// 2. Decorators
public class LoggingUserService implements UserService {
    private final UserService delegate;
    
    public LoggingUserService(UserService delegate) {
        this.delegate = delegate;
    }
    
    public void createUser(User user) {
        logger.info("Creating user");
        delegate.createUser(user);
    }
}

// 3. Spring's @Transactional
@Service
public class UserService {
    @Transactional
    public void createUser(User user) {
        // Transaction managed by AOP
    }
}
```

---

## 🎯 Interview One-Liner

> Use AOP for cross-cutting concerns: logging, security, transactions, caching, performance monitoring; keep business logic clean; avoid for core business rules or simple utilities.
