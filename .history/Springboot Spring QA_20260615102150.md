
## **2. SPRING / SPRING BOOT**

### **Spring Core**

## ✅ 51. What is Spring Framework? Benefits?

**Spring Framework is a comprehensive Java framework that provides infrastructure support for developing Java applications, focusing on dependency injection, aspect-oriented programming, and enterprise application features.**

### 🔹 What is Spring Framework?
## **2. SPRING / SPRING BOOT**

### **Spring Core**

## ✅ 51. What is Spring Framework? Benefits?

**Spring Framework is a comprehensive Java framework that provides infrastructure support for developing Java applications, focusing on dependency injection, aspect-oriented programming, and enterprise application features.**

### 🔹 What is Spring Framework?

**Spring is an open-source application framework and inversion of control container for the Java platform.**

```java
// Traditional Java application
public class Application {
    private DatabaseService dbService = new DatabaseService();
    private EmailService emailService = new EmailService();
    
    public void process() {
        // Tight coupling - hard to test and maintain
        dbService.save();
        emailService.send();
    }
}

// Spring-managed application
@Component
public class SpringApplication {
    @Autowired
    private DatabaseService dbService;
    
    @Autowired
    private EmailService emailService;
    
    public void process() {
        // Loose coupling - easy to test and maintain
        dbService.save();
        emailService.send();
    }
}
```

**Key characteristics:**
- **Lightweight**: Minimal overhead, POJO-based
- **Modular**: Can use individual modules as needed
- **Non-invasive**: Doesn't force inheritance from framework classes
- **Comprehensive**: Covers all layers of enterprise applications

### 🔹 Core Modules

```java
// Spring Core Container
- Core: Core utilities, IoC container
- Beans: Bean factory, dependency injection
- Context: Application context, internationalization
- Expression Language (SpEL)

// Data Access/Integration
- JDBC: Database connectivity
- ORM: Object-relational mapping (Hibernate, JPA)
- OXM: Object-XML mapping
- JMS: Java Messaging Service
- Transactions: Transaction management

// Web Layer
- Web: Web utilities
- Web-MVC: Model-View-Controller
- Web-Socket: WebSocket support
- Web-Portlet: Portlet environment

// AOP and Instrumentation
- AOP: Aspect-oriented programming
- Aspects: AOP alliance compliant
- Instrumentation: JVM agent, classloader

// Test
- Test: Testing utilities and mock objects
```

### 🔹 Key Benefits

### 🔹 1. Dependency Injection (DI)

**Loose coupling between components**

```java
// Without DI - tight coupling
public class UserService {
    private UserRepository repository = new UserRepositoryImpl();
    
    public User getUser(int id) {
        return repository.findById(id);
    }
}

// With DI - loose coupling
@Service
public class UserService {
    private final UserRepository repository;
    
    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }
    
    public User getUser(int id) {
        return repository.findById(id);
    }
}
```

### 🔹 2. Aspect-Oriented Programming (AOP)

**Separation of cross-cutting concerns**

```java
@Aspect
@Component
public class LoggingAspect {
    @Before("execution(* com.example.service.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("Executing: " + joinPoint.getSignature());
    }
}

// No logging code in business logic
@Service
public class UserService {
    public User getUser(int id) {
        // Business logic only
        return repository.findById(id);
    }
}
```

### 🔹 3. Transaction Management

**Declarative transaction support**

```java
@Service
public class UserService {
    @Transactional
    public void transferMoney(Account from, Account to, BigDecimal amount) {
        from.debit(amount);
        to.credit(amount);
        // If exception occurs, transaction rolls back automatically
    }
}
```

### 🔹 4. Data Access

**Simplified database operations**

```java
@Repository
public class UserRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public User findById(int id) {
        return jdbcTemplate.queryForObject(
            "SELECT * FROM users WHERE id = ?",
            new Object[]{id},
            (rs, rowNum) -> new User(rs.getInt("id"), rs.getString("name"))
        );
    }
}
```

### 🔹 5. MVC Framework

**Clean web application architecture**

```java
@Controller
public class UserController {
    @Autowired
    private UserService userService;
    
    @GetMapping("/users/{id}")
    public String getUser(@PathVariable int id, Model model) {
        User user = userService.getUser(id);
        model.addAttribute("user", user);
        return "user-view";
    }
}
```

### 🔹 6. Testing Support

**Easy unit and integration testing**

```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;
    
    @MockBean
    private UserRepository userRepository;
    
    @Test
    public void testGetUser() {
        // Easy mocking and testing
        when(userRepository.findById(1)).thenReturn(new User(1, "John"));
        User user = userService.getUser(1);
        assertEquals("John", user.getName());
    }
}
```

### 🔹 7. Enterprise Features

```java
// Caching
@Cacheable("users")
public User getUser(int id) {
    return repository.findById(id);
}

// Scheduling
@Service
public class ScheduledService {
    @Scheduled(fixedRate = 5000)
    public void doSomething() {
        // Runs every 5 seconds
    }
}

// Security
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // Security configuration
}
```

### 🔹 Advantages Over Other Frameworks

| Feature | Spring | EJB 2.x | Plain Java |
|---------|--------|---------|------------|
| **Complexity** | Low | High | Medium |
| **Testability** | High | Low | Medium |
| **Performance** | High | Medium | High |
| **Learning Curve** | Medium | High | Low |
| **Flexibility** | High | Low | High |

### 🔹 When to Use Spring

```java
// Use Spring for:
- Enterprise applications
- Web applications (Spring MVC)
- Microservices
- Batch processing
- Integration with other frameworks
- Applications requiring DI and AOP

// Consider alternatives for:
- Simple command-line applications
- High-performance requirements (may have slight overhead)
- Applications where you want full control
```

### 🔹 Best Practices

```java
// Use constructor injection over field injection
@Service
public class UserService {
    private final UserRepository repository;
    
    public UserService(UserRepository repository) {  // Preferred
        this.repository = repository;
    }
}

// Use @Configuration for bean definitions
@Configuration
public class AppConfig {
    @Bean
    public DataSource dataSource() {
        return new HikariDataSource();
    }
}

// Follow naming conventions
// Use meaningful package structure
// Leverage Spring Boot for new applications
```

---

## 🎯 Interview One-Liner

> Spring Framework provides DI, AOP, transaction management, MVC; lightweight, modular, POJO-based; simplifies enterprise Java development with comprehensive features and testing support.

---

## ✅ 52. What is Dependency Injection?

**Dependency Injection is a design pattern where objects receive their dependencies from external sources rather than creating them internally, promoting loose coupling and easier testing.**

### 🔹 Traditional Approach (Tight Coupling)

**Objects create their own dependencies**

```java
public class UserService {
    private UserRepository repository;
    
    public UserService() {
        this.repository = new UserRepositoryImpl();  // Tight coupling
    }
    
    public User getUser(int id) {
        return repository.findById(id);
    }
}

// Problems:
// - Hard to test (can't mock UserRepositoryImpl)
// - Hard to change implementation
// - Creates unnecessary dependencies
// - Violates Single Responsibility Principle
```

### 🔹 Dependency Injection Approach

**Dependencies are injected from outside**

```java
public class UserService {
    private final UserRepository repository;
    
    // Constructor injection
    public UserService(UserRepository repository) {
        this.repository = repository;
    }
    
    public User getUser(int id) {
        return repository.findById(id);
    }
}

// Usage with Spring
@Configuration
public class AppConfig {
    @Bean
    public UserRepository userRepository() {
        return new UserRepositoryImpl();
    }
    
    @Bean
    public UserService userService(UserRepository repository) {
        return new UserService(repository);
    }
}
```

### 🔹 Types of Dependency Injection

### 🔹 1. Constructor Injection

**Dependencies provided through constructor**

```java
@Service
public class OrderService {
    private final UserService userService;
    private final PaymentService paymentService;
    
    @Autowired  // Optional in Spring 4.3+
    public OrderService(UserService userService, PaymentService paymentService) {
        this.userService = userService;
        this.paymentService = paymentService;
    }
}

// Advantages:
// - Immutable dependencies
// - Clear contract (required dependencies)
// - Easy testing
// - Prevents circular dependencies
```

### 🔹 2. Setter Injection

**Dependencies provided through setter methods**

```java
@Service
public class EmailService {
    private EmailProvider provider;
    
    @Autowired
    public void setEmailProvider(EmailProvider provider) {
        this.provider = provider;
    }
    
    public void sendEmail(String to, String subject, String body) {
        provider.send(to, subject, body);
    }
}

// Advantages:
// - Optional dependencies
// - Can change dependencies at runtime
// - Resolves circular dependencies
```

### 🔹 3. Field Injection

**Dependencies injected directly into fields**

```java
@Service
public class NotificationService {
    @Autowired
    private EmailService emailService;
    
    @Autowired
    private SmsService smsService;
    
    public void sendNotification(String message) {
        emailService.send(message);
        smsService.send(message);
    }
}

// Advantages:
// - Less boilerplate code
// - Easy to use

// Disadvantages:
// - Harder to test
// - Hidden dependencies
// - Cannot make fields final
```

### 🔹 Benefits of Dependency Injection

### 🔹 1. Loose Coupling

```java
// Easy to switch implementations
@Configuration
public class TestConfig {
    @Bean
    public UserRepository userRepository() {
        return new MockUserRepository();  // For testing
    }
}

@Configuration
public class ProdConfig {
    @Bean
    public UserRepository userRepository() {
        return new JpaUserRepository();  // For production
    }
}
```

### 🔹 2. Easier Testing

```java
@RunWith(SpringRunner.class)
public class UserServiceTest {
    private UserService userService;
    
    @Before
    public void setUp() {
        UserRepository mockRepo = mock(UserRepository.class);
        userService = new UserService(mockRepo);  // Easy mocking
    }
    
    @Test
    public void testGetUser() {
        when(mockRepo.findById(1)).thenReturn(new User(1, "John"));
        User user = userService.getUser(1);
        assertEquals("John", user.getName());
    }
}
```

### 🔹 3. Better Maintainability

```java
// Changes are localized
public class ImprovedUserService {
    private final UserRepository repository;
    private final CacheService cache;        // New dependency
    private final AuditService audit;        // New dependency
    
    public ImprovedUserService(UserRepository repository, 
                              CacheService cache, 
                              AuditService audit) {
        this.repository = repository;
        this.cache = cache;
        this.audit = audit;
    }
}
```

### 🔹 4. Inversion of Control

**Framework controls object creation and wiring**

```java
// Spring IoC container manages dependencies
ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

// Get fully configured bean
UserService service = context.getBean(UserService.class);
// All dependencies automatically injected
```

### 🔹 DI vs Service Locator

```java
// Service Locator (anti-pattern)
public class ServiceLocator {
    public static UserRepository getUserRepository() {
        return new UserRepositoryImpl();
    }
}

// Still tight coupling
public class BadUserService {
    private UserRepository repository = ServiceLocator.getUserRepository();
}

// Dependency Injection (better)
public class GoodUserService {
    private final UserRepository repository;
    
    public GoodUserService(UserRepository repository) {
        this.repository = repository;
    }
}
```

### 🔹 Common Pitfalls

```java
// Circular dependencies
@Service
public class A {
    @Autowired
    private B b;  // A depends on B
}

@Service
public class B {
    @Autowired
    private A a;  // B depends on A - circular!
}

// Solution: Use setter injection or redesign
@Service
public class A {
    private B b;
    
    @Autowired
    public void setB(B b) {
        this.b = b;
    }
}
```

### 🔹 Best Practices

```java
// Prefer constructor injection
// Use field injection sparingly
// Make injected fields final when possible
// Avoid circular dependencies
// Use interfaces for dependencies
// Leverage Spring profiles for different environments
// Document dependencies clearly
```

---

## 🎯 Interview One-Liner

> DI injects dependencies from outside instead of creating them; promotes loose coupling, testability; types: constructor (preferred), setter, field; Spring IoC container manages injection automatically.

---

## ✅ 53. What is IoC (Inversion of Control)?

**Inversion of Control is a design principle where the control of object creation and dependency management is transferred from the application code to an external container or framework.**

### 🔹 Traditional Control Flow

**Application controls object lifecycle**

```java
public class TraditionalApp {
    public static void main(String[] args) {
        // Application creates and manages objects
        DatabaseConnection db = new DatabaseConnection("localhost", 3306);
        UserRepository repo = new UserRepository(db);
        UserService service = new UserService(repo);
        UserController controller = new UserController(service);
        
        // Application manages lifecycle
        controller.handleRequest();
        
        // Application cleans up
        db.close();
    }
}

// Problems:
// - Tight coupling
// - Hard to test
// - Boilerplate code
// - Manual dependency management
```

### 🔹 IoC with Spring

**Framework controls object lifecycle**

```java
@SpringBootApplication
public class SpringApp {
    public static void main(String[] args) {
        // Spring creates and manages all objects
        ApplicationContext context = SpringApplication.run(SpringApp.class, args);
        
        // Get fully configured bean
        UserController controller = context.getBean(UserController.class);
        controller.handleRequest();
        
        // Spring manages lifecycle automatically
    }
}

// Spring configuration
@Configuration
public class AppConfig {
    @Bean
    public DatabaseConnection databaseConnection() {
        return new DatabaseConnection("localhost", 3306);
    }
    
    @Bean
    public UserRepository userRepository(DatabaseConnection db) {
        return new UserRepository(db);
    }
    
    @Bean
    public UserService userService(UserRepository repo) {
        return new UserService(repo);
    }
    
    @Bean
    public UserController userController(UserService service) {
        return new UserController(service);
    }
}
```

### 🔹 IoC Container

**Central registry for managing objects**

```java
// ApplicationContext is the IoC container
ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

// Container manages:
// - Object creation
// - Dependency injection
// - Lifecycle management
// - Configuration

// Get beans by type
UserService service = context.getBean(UserService.class);

// Get beans by name
UserService service = (UserService) context.getBean("userService");

// Check if bean exists
boolean exists = context.containsBean("userService");
```

### 🔹 Types of IoC

### 🔹 1. Dependency Injection (DI)

**Most common form of IoC**

```java
@Service
public class OrderService {
    private final PaymentService paymentService;
    
    // IoC container injects dependency
    @Autowired
    public OrderService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
}
```

### 🔹 2. Service Locator

**Alternative to DI (less preferred)**

```java
public class ServiceLocator {
    private static ApplicationContext context;
    
    public static void setContext(ApplicationContext context) {
        ServiceLocator.context = context;
    }
    
    public static <T> T getService(Class<T> clazz) {
        return context.getBean(clazz);
    }
}

@Service
public class OrderService {
    public void processOrder() {
        // Lookup service (still coupled to locator)
        PaymentService payment = ServiceLocator.getService(PaymentService.class);
        payment.process();
    }
}
```

### 🔹 3. Template Method Pattern

**Framework controls flow, application provides details**

```java
@Repository
public class UserRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public User findById(int id) {
        // IoC: JdbcTemplate controls how, we provide what
        return jdbcTemplate.queryForObject(
            "SELECT * FROM users WHERE id = ?",
            new Object[]{id},
            (rs, rowNum) -> new User(rs.getInt("id"), rs.getString("name"))
        );
    }
}
```

### 🔹 4. Factory Pattern with IoC

```java
@Configuration
public class FactoryConfig {
    @Bean
    public PaymentFactory paymentFactory() {
        return new PaymentFactory();
    }
    
    @Bean
    @Scope("prototype")
    public PaymentService paymentService(PaymentFactory factory, String type) {
        return factory.createPaymentService(type);  // IoC through factory
    }
}
```

### 🔹 Benefits of IoC

### 🔹 1. Loose Coupling

```java
// Easy to swap implementations
@Configuration
public class TestConfig {
    @Bean
    public PaymentService paymentService() {
        return new MockPaymentService();  // Test implementation
    }
}

@Configuration
public class ProdConfig {
    @Bean
    public PaymentService paymentService() {
        return new StripePaymentService();  // Production implementation
    }
}
```

### 🔹 2. Testability

```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {
    @Autowired
    private OrderService orderService;
    
    @MockBean
    private PaymentService paymentService;  // IoC enables easy mocking
    
    @Test
    public void testOrderProcessing() {
        when(paymentService.process(any())).thenReturn(true);
        boolean result = orderService.processOrder(new Order());
        assertTrue(result);
    }
}
```

### 🔹 3. Separation of Concerns

```java
// Business logic focuses on business rules
@Service
public class OrderService {
    public void processOrder(Order order) {
        // Business logic only
        validateOrder(order);
        calculateTotal(order);
        saveOrder(order);
        // IoC container handles infrastructure concerns
    }
}
```

### 🔹 4. Lifecycle Management

```java
@Component
public class DatabaseConnection implements InitializingBean, DisposableBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        // IoC container calls this after bean creation
        connectToDatabase();
    }
    
    @Override
    public void destroy() throws Exception {
        // IoC container calls this before bean destruction
        closeConnection();
    }
}
```

### 🔹 IoC vs Traditional Programming

| Aspect | Traditional | IoC |
|--------|-------------|-----|
| **Control** | Application controls flow | Framework controls flow |
| **Dependencies** | Objects create dependencies | Dependencies injected |
| **Testing** | Hard to mock dependencies | Easy with DI |
| **Flexibility** | Rigid, hard to change | Flexible, configurable |
| **Boilerplate** | Lots of setup code | Minimal setup code |

### 🔹 Common IoC Containers

```java
// Spring ApplicationContext
ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

// Google Guice
Injector injector = Guice.createInjector(new AppModule());
UserService service = injector.getInstance(UserService.class);

// CDI (Java EE)
@ApplicationScoped
public class UserService {
    @Inject
    private UserRepository repository;
}
```

### 🔹 Best Practices

```java
// Use constructor injection for required dependencies
// Use setter injection for optional dependencies
// Avoid field injection in production code
// Leverage Spring profiles for different environments
// Use @Qualifier for multiple beans of same type
// Document bean dependencies
// Test with Spring test support
```

---

## 🎯 Interview One-Liner

> IoC transfers control of object creation/management from application to framework; Spring IoC container manages beans, dependencies, lifecycle; enables loose coupling, testability, separation of concerns.

---

## ✅ 54. Types of Dependency Injection

**Spring supports three types of dependency injection: Constructor Injection (recommended), Setter Injection, and Field Injection, each with different use cases and trade-offs.**

### 🔹 Constructor Injection

**Dependencies provided through constructor parameters**

```java
@Service
public class OrderService {
    private final UserService userService;
    private final PaymentService paymentService;
    private final EmailService emailService;
    
    // All dependencies injected at creation time
    @Autowired
    public OrderService(UserService userService, 
                       PaymentService paymentService,
                       EmailService emailService) {
        this.userService = userService;
        this.paymentService = paymentService;
        this.emailService = emailService;
    }
    
    public void processOrder(Order order) {
        User user = userService.getUser(order.getUserId());
        boolean paymentSuccess = paymentService.process(order);
        if (paymentSuccess) {
            emailService.sendConfirmation(user.getEmail());
        }
    }
}
```

**Advantages:**
- **Immutability**: Dependencies cannot be changed after construction
- **Clear contract**: Required dependencies are obvious
- **Testability**: Easy to create test instances
- **Fail-fast**: Application fails to start if dependencies missing
- **Thread-safety**: No synchronization needed for dependencies

**Disadvantages:**
- **Circular dependencies**: Cannot resolve circular references
- **Verbose**: Many parameters for classes with many dependencies

### 🔹 Setter Injection

**Dependencies provided through setter methods**

```java
@Service
public class NotificationService {
    private EmailService emailService;
    private SmsService smsService;
    
    // Optional dependency
    @Autowired(required = false)
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }
    
    // Required dependency
    @Autowired
    public void setSmsService(SmsService smsService) {
        this.smsService = smsService;
    }
    
    public void sendNotification(String message, NotificationType type) {
        switch (type) {
            case EMAIL:
                if (emailService != null) {
                    emailService.send(message);
                }
                break;
            case SMS:
                smsService.send(message);
                break;
            case BOTH:
                if (emailService != null) {
                    emailService.send(message);
                }
                smsService.send(message);
                break;
        }
    }
}
```

**Advantages:**
- **Optional dependencies**: Can work without some dependencies
- **Circular dependency resolution**: Can resolve circular references
- **Runtime reconfiguration**: Dependencies can be changed
- **Inheritance friendly**: Subclasses can override setters

**Disadvantages:**
- **Null checks needed**: Must check for null dependencies
- **Incomplete initialization**: Object may be in inconsistent state
- **Thread-safety concerns**: May need synchronization

### 🔹 Field Injection

**Dependencies injected directly into fields**

```java
@Service
public class ReportService {
    @Autowired
    private DataService dataService;
    
    @Autowired
    private PdfGenerator pdfGenerator;
    
    @Autowired
    private EmailService emailService;
    
    public void generateAndSendReport(String userId) {
        List<Data> data = dataService.getUserData(userId);
        byte[] pdf = pdfGenerator.generatePdf(data);
        emailService.sendAttachment(userId, "Report", pdf);
    }
}
```

**Advantages:**
- **Less code**: No constructor or setter boilerplate
- **Clean**: No injection methods cluttering the class
- **Easy**: Just add @Autowired annotation

**Disadvantages:**
- **Hidden dependencies**: Not clear from constructor what is needed
- **Hard to test**: Cannot create instances without Spring
- **Final fields impossible**: Cannot make dependencies final
- **Tight coupling**: Depends on Spring's reflection-based injection

### 🔹 Method Injection

**Dependencies injected through arbitrary methods**

```java
@Service
public class ComplexService {
    private DependencyA depA;
    private DependencyB depB;
    
    @Autowired
    public void injectDependencies(DependencyA depA, DependencyB depB) {
        this.depA = depA;
        this.depB = depB;
    }
    
    // Alternative: multiple methods
    @Autowired
    public void setDependencyA(DependencyA depA) {
        this.depA = depA;
    }
    
    @Autowired
    public void setDependencyB(DependencyB depB) {
        this.depB = depB;
    }
}
```

### 🔹 Comparison Table

| Type | Constructor | Setter | Field |
|------|-------------|--------|-------|
| **Immutability** | ✅ | ❌ | ❌ |
| **Optional deps** | ❌ | ✅ | ✅ |
| **Circular deps** | ❌ | ✅ | ✅ |
| **Testability** | ✅ | ⚠️ | ❌ |
| **Thread-safety** | ✅ | ⚠️ | ⚠️ |
| **Code clarity** | ✅ | ✅ | ❌ |
| **Spring version** | All | All | 2.5+ |

### 🔹 When to Use Each Type

```java
// Use Constructor Injection for:
// - Required dependencies
// - Immutable objects
// - Better testability
// - Clear component contracts

public class PaymentProcessor {
    private final PaymentGateway gateway;
    private final TransactionLogger logger;
    
    public PaymentProcessor(PaymentGateway gateway, TransactionLogger logger) {
        this.gateway = gateway;
        this.logger = logger;
    }
}

// Use Setter Injection for:
// - Optional dependencies
// - Circular dependencies
// - Runtime reconfiguration needs

public class CacheManager {
    private CacheProvider cache;
    
    @Autowired(required = false)
    public void setCache(CacheProvider cache) {
        this.cache = cache;
    }
    
    public void put(String key, Object value) {
        if (cache != null) {
            cache.put(key, value);
        }
    }
}

// Use Field Injection for:
// - Simple cases
// - Rapid prototyping
// - When constructor would be too verbose

@Component
public class SimpleService {
    @Autowired
    private SimpleRepository repository;
    
    public void doWork() {
        repository.save(new Entity());
    }
}
```

### 🔹 Spring Boot Improvements

```java
// Spring 4.3+: @Autowired optional for single constructor
@Service
public class UserService {
    private final UserRepository repository;
    
    public UserService(UserRepository repository) {  // @Autowired not needed
        this.repository = repository;
    }
}

// @Required for mandatory setters (deprecated in 5.1)
@Service
public class LegacyService {
    private DataSource dataSource;
    
    @Required  // Throws exception if not set
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
```

### 🔹 Best Practices

```java
// Prefer constructor injection for required dependencies
// Use setter/field injection only when necessary
// Make injected fields final when possible
// Use @Autowired(required = false) for optional dependencies
// Avoid field injection in production code
// Document why you're using a particular injection type
// Consider using Lombok's @RequiredArgsConstructor for constructor injection
```

---

## 🎯 Interview One-Liner

> Constructor injection (preferred): immutable, required deps, clear contract; Setter injection: optional deps, circular deps; Field injection: simple but hides dependencies; choose based on requirements.

---

## ✅ 55. @Component vs @Service vs @Repository

**@Component, @Service, and @Repository are Spring stereotypes that mark classes as Spring-managed beans, with @Service and @Repository providing additional semantic meaning and features.**

### 🔹 @Component

**Generic stereotype for any Spring-managed component**

```java
@Component
public class UserValidator {
    public boolean isValid(User user) {
        return user != null && user.getEmail() != null;
    }
}

@Component
public class PasswordEncoder {
    public String encode(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}

// Usage
@Controller
public class UserController {
    @Autowired
    private UserValidator validator;
    
    @Autowired
    private PasswordEncoder encoder;
    
    @PostMapping("/register")
    public String register(User user) {
        if (!validator.isValid(user)) {
            return "error";
        }
        user.setPassword(encoder.encode(user.getPassword()));
        // Save user
        return "success";
    }
}
```

**Characteristics:**
- **Generic**: Can be used for any component
- **Basic**: No additional features
- **Scanning**: Included in component scanning
- **Use case**: Utility classes, validators, processors

### 🔹 @Service

**Specialized stereotype for service layer classes**

```java
@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private EmailService emailService;
    
    public User registerUser(User user) {
        // Business logic
        User savedUser = userRepository.save(user);
        emailService.sendWelcomeEmail(savedUser);
        return savedUser;
    }
    
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
    
    @Transactional
    public void updateUser(User user) {
        // Transactional business logic
        userRepository.save(user);
    }
}
```

**Characteristics:**
- **Semantic**: Indicates service layer component
- **Transactional**: Often used with @Transactional
- **Business logic**: Contains business rules and workflows
- **Use case**: Business services, application services

### 🔹 @Repository

**Specialized stereotype for data access layer**

```java
@Repository
public class UserRepositoryImpl implements UserRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public User save(User user) {
        // Data access logic
        String sql = "INSERT INTO users (name, email) VALUES (?, ?)";
        jdbcTemplate.update(sql, user.getName(), user.getEmail());
        return user;
    }
    
    @Override
    public Optional<User> findById(Long id) {
        try {
            String sql = "SELECT * FROM users WHERE id = ?";
            User user = jdbcTemplate.queryForObject(sql, new Object[]{id}, 
                (rs, rowNum) -> new User(rs.getInt("id"), rs.getString("name"), rs.getString("email")));
            return Optional.of(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
    
    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, (rs, rowNum) -> 
            new User(rs.getInt("id"), rs.getString("name"), rs.getString("email")));
    }
}
```

**Characteristics:**
- **Semantic**: Indicates data access component
- **Exception translation**: Automatic translation of SQLExceptions to DataAccessExceptions
- **Use case**: DAO classes, repository implementations

### 🔹 Key Differences

| Annotation | Purpose | Features | Example |
|------------|---------|----------|---------|
| **@Component** | Generic component | Basic bean | Validators, utilities |
| **@Service** | Business service | Transaction support | UserService, OrderService |
| **@Repository** | Data access | Exception translation | UserRepository, ProductDao |

### 🔹 Additional Stereotypes

```java
// @Controller - Web MVC controller
@Controller
public class UserController {
    // Handles HTTP requests
}

// @RestController - REST controller (@Controller + @ResponseBody)
@RestController
public class ApiController {
    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.getAllUsers();
    }
}

// @Configuration - Configuration class
@Configuration
public class AppConfig {
    @Bean
    public DataSource dataSource() {
        return new HikariDataSource();
    }
}
```

### 🔹 Component Scanning

```java
// Enable component scanning
@Configuration
@ComponentScan(basePackages = "com.example")
public class AppConfig {
    // Scans for @Component, @Service, @Repository, @Controller, @RestController
}

// Custom component scanning
@Configuration
@ComponentScan(
    basePackages = "com.example",
    includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Service.class),
    excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Controller.class)
)
public class CustomScanConfig {
    // Only scans @Service, excludes @Controller
}
```

### 🔹 When to Use Each

```java
// Use @Component for:
// - Utility classes
// - Helper classes
// - Custom processors
// - Classes that don't fit other stereotypes

@Component
public class DateFormatter {
    public String format(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }
}

// Use @Service for:
// - Business logic classes
// - Service layer components
// - Classes with transactional methods
// - Workflow orchestration

@Service
public class OrderProcessingService {
    @Transactional
    public void processOrder(Order order) {
        validateOrder(order);
        calculateTotal(order);
        saveOrder(order);
        sendConfirmation(order);
    }
}

// Use @Repository for:
// - Data access objects
// - Repository implementations
// - Classes that interact with databases
// - Classes needing exception translation

@Repository
public class JpaUserRepository implements UserRepository {
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public User save(User user) {
        entityManager.persist(user);
        return user;
    }
}
```

### 🔹 Best Practices

```java
// Use appropriate stereotype for semantic clarity
// Prefer @Service for business logic
// Use @Repository for data access
// Use @Component for everything else
// Combine with other annotations as needed
// Follow naming conventions (UserService, UserRepository)
// Document the purpose of each component
```

---

## 🎯 Interview One-Liner

> @Component: generic bean; @Service: business logic, often transactional; @Repository: data access, exception translation; use appropriate stereotype for semantic meaning and features.

---

## ✅ 56. @Autowired vs @Inject vs @Resource

**@Autowired (Spring), @Inject (JSR-330), and @Resource (JSR-250) are dependency injection annotations with different precedence rules and compatibility.**

### 🔹 @Autowired (Spring-specific)

**Spring's annotation for dependency injection**

```java
@Service
public class OrderService {
    // Field injection
    @Autowired
    private PaymentService paymentService;
    
    // Constructor injection
    private final UserService userService;
    
    @Autowired
    public OrderService(UserService userService) {
        this.userService = userService;
    }
    
    // Setter injection
    private EmailService emailService;
    
    @Autowired
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }
    
    // Method injection
    @Autowired
    public void configureServices(PaymentService payment, AuditService audit) {
        // Inject multiple dependencies
    }
}
```

**Characteristics:**
- **Spring-specific**: Only works with Spring framework
- **Flexible**: Field, constructor, setter, method injection
- **Required by default**: Throws exception if dependency not found
- **Type-based**: Matches by type, then by name if qualifiers used

### 🔹 @Inject (JSR-330)

**Standard Java dependency injection annotation**

```java
import javax.inject.Inject;
import javax.inject.Named;

@Service
public class ProductService {
    // Field injection
    @Inject
    private ProductRepository productRepository;
    
    // Constructor injection
    private final CategoryService categoryService;
    
    @Inject
    public ProductService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    
    // Qualifier for multiple beans
    @Inject
    @Named("primaryInventory")
    private InventoryService inventoryService;
}
```

**Characteristics:**
- **Standard**: Part of JSR-330, works with Spring, Guice, etc.
- **Similar to @Autowired**: Same injection points
- **Always required**: No 'required' attribute
- **Qualifier needed**: Use @Named for disambiguation

### 🔹 @Resource (JSR-250)

**Java EE annotation for resource injection**

```java
import javax.annotation.Resource;

@Service
public class ReportService {
    // Field injection by name
    @Resource(name = "reportRepository")
    private ReportRepository reportRepository;
    
    // Field injection by type
    @Resource
    private PdfGenerator pdfGenerator;
    
    // Setter injection
    private EmailService emailService;
    
    @Resource
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }
}
```

**Characteristics:**
- **JSR-250**: Part of Java EE, supported by Spring
- **Name-based**: Prefers name over type matching
- **Resource-oriented**: Designed for resources (JDBC, JMS, etc.)
- **Always required**: No optional injection

### 🔹 Injection Precedence

**Order when multiple candidates exist**

```java
// Multiple PaymentService implementations
@Service
public class CreditCardPayment implements PaymentService {}

@Service
public class PayPalPayment implements PaymentService {}

// @Autowired - by type, fails if multiple
@Service
public class OrderService {
    @Autowired
    private PaymentService paymentService;  // ERROR: multiple beans
}

// @Autowired with @Qualifier
@Service
public class OrderService {
    @Autowired
    @Qualifier("creditCardPayment")
    private PaymentService paymentService;  // OK
}

// @Inject with @Named
@Service
public class OrderService {
    @Inject
    @Named("creditCardPayment")
    private PaymentService paymentService;  // OK
}

// @Resource - by name
@Service
public class OrderService {
    @Resource(name = "creditCardPayment")
    private PaymentService paymentService;  // OK
}
```

### 🔹 Detailed Comparison

| Feature | @Autowired | @Inject | @Resource |
|---------|------------|---------|-----------|
| **Source** | Spring | JSR-330 | JSR-250 |
| **Package** | org.springframework | javax.inject | javax.annotation |
| **Required** | Configurable | Always | Always |
| **Injection** | Type > Name | Type > Name | Name > Type |
| **Qualifier** | @Qualifier | @Named | name attribute |
| **Compatibility** | Spring only | Multiple DI frameworks | Java EE |

### 🔹 Practical Examples

```java
// @Autowired examples
@Configuration
public class AutowiredConfig {
    @Bean
    public PaymentService creditCardPayment() {
        return new CreditCardPayment();
    }
    
    @Bean
    public PaymentService paypalPayment() {
        return new PayPalPayment();
    }
}

@Service
public class PaymentProcessor {
    @Autowired
    @Qualifier("creditCardPayment")  // Specify which bean
    private PaymentService primaryPayment;
    
    @Autowired
    private List<PaymentService> allPayments;  // Inject all implementations
}

// @Inject examples
@Service
public class InventoryService {
    @Inject
    @Named("primaryInventory")
    private InventoryRepository repository;
    
    @Inject
    private Provider<InventoryRepository> repoProvider;  // Lazy injection
}

// @Resource examples
@Service
public class DataService {
    @Resource(name = "dataSource")  // JNDI lookup
    private DataSource dataSource;
    
    @Resource
    private JdbcTemplate jdbcTemplate;  // By type
}
```

### 🔹 When to Use Each

```java
// Use @Autowired for:
// - Spring-specific applications
// - Flexible injection requirements
// - Optional dependencies (@Autowired(required = false))
// - Collection injection (List, Map, etc.)

@Autowired
public void setOptionalService(@Autowired(required = false) OptionalService service) {
    this.service = service;
}

// Use @Inject for:
// - Framework-agnostic code
// - JSR-330 compliance
// - Working with multiple DI containers
// - Provider injection for lazy loading

@Inject
private Provider<ExpensiveService> expensiveServiceProvider;

// Use @Resource for:
// - Java EE applications
// - Resource injection (DataSource, JMS, etc.)
// - Name-based injection preference
// - Legacy code migration

@Resource(name = "jdbc/myDataSource")
private DataSource dataSource;
```

### 🔹 Best Practices

```java
// Prefer constructor injection over field injection
// Use @Autowired for Spring applications
// Use @Inject for framework-agnostic code
// Use @Resource for resource injection
// Use qualifiers to resolve ambiguity
// Avoid field injection in production code
// Document injection requirements
```

---

## 🎯 Interview One-Liner

> @Autowired (Spring): flexible, type-based; @Inject (JSR-330): standard, similar to @Autowired; @Resource (JSR-250): name-based, for resources; choose based on framework requirements and injection style.

---

## ✅ 57. What is Bean in Spring?

**A Bean is an object that is instantiated, assembled, and managed by the Spring IoC container, representing a component in the application.**

### 🔹 Bean Definition

**Spring-managed object with metadata**

```java
// Bean defined via annotation
@Service
public class UserService {
    // Bean class
}

// Bean defined via XML (legacy)
<bean id="userService" class="com.example.UserService">
    <property name="userRepository" ref="userRepository"/>
</bean>

// Bean defined via Java config
@Configuration
public class AppConfig {
    @Bean
    public UserService userService() {
        return new UserService();
    }
}
```

### 🔹 Bean Characteristics

```java
// Every bean has:
- Class: The actual Java class
- Name/ID: Unique identifier
- Scope: Lifecycle management
- Dependencies: Other beans it needs
- Configuration: How it should be created/configured

@Service("userManager")  // Custom name
public class UserService implements InitializingBean, DisposableBean {
    
    @Autowired
    private UserRepository repository;
    
    @PostConstruct  // Lifecycle callback
    public void init() {
        System.out.println("Bean initialized");
    }
    
    @PreDestroy  // Lifecycle callback
    public void destroy() {
        System.out.println("Bean destroyed");
    }
}
```

### 🔹 Bean Instantiation

**How Spring creates bean instances**

```java
// 1. Constructor instantiation (default)
@Service
public class DefaultService {
    public DefaultService() {
        // Default constructor
    }
}

// 2. Static factory method
@Configuration
public class FactoryConfig {
    @Bean
    public DataSource dataSource() {
        return DataSourceFactory.createDataSource();  // Static factory
    }
}

// 3. Instance factory method
@Configuration
public class InstanceFactoryConfig {
    @Bean
    public FactoryBean factoryBean() {
        return new MyFactoryBean();
    }
    
    @Bean
    public Product product(FactoryBean factory) {
        return factory.createProduct();  // Instance factory
    }
}
```

### 🔹 Bean Naming

**How beans are identified**

```java
// 1. Default naming (class name with first letter lowercase)
@Service
public class UserService {}  // Bean name: "userService"

// 2. Explicit naming
@Service("userManager")
public class UserService {}  // Bean name: "userManager"

// 3. @Bean naming
@Configuration
public class Config {
    @Bean(name = "myService")
    public UserService userService() {
        return new UserService();
    }
    
    @Bean({"service", "userService"})  // Multiple names
    public UserService anotherService() {
        return new UserService();
    }
}
```

### 🔹 Bean Retrieval

**Accessing beans from container**

```java
ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

// By type
UserService service = context.getBean(UserService.class);

// By name
UserService service = (UserService) context.getBean("userService");

// By name and type
UserService service = context.getBean("userService", UserService.class);

// Get all beans of type
Map<String, UserService> services = context.getBeansOfType(UserService.class);

// Check if bean exists
boolean exists = context.containsBean("userService");
```

### 🔹 Bean Dependencies

**How beans reference each other**

```java
@Service
public class OrderService {
    @Autowired
    private UserService userService;  // Direct dependency
    
    @Autowired
    private List<PaymentService> paymentServices;  // Collection dependency
    
    @Autowired
    private Map<String, NotificationService> notificationServices;  // Map dependency
}

// Circular dependency (avoid)
@Service
public class A {
    @Autowired
    private B b;
}

@Service
public class B {
    @Autowired
    private A a;  // Circular reference
}
```

### 🔹 Bean Configuration

**Different ways to configure beans**

```java
// 1. Annotation-based (modern)
@Service
public class UserService {
    @Value("${app.user.default-role}")
    private String defaultRole;
}

// 2. Java-based configuration
@Configuration
public class AppConfig {
    @Bean
    @Scope("prototype")
    public UserService userService() {
        UserService service = new UserService();
        service.setDefaultRole("USER");
        return service;
    }
}

// 3. XML-based configuration (legacy)
<bean id="userService" class="com.example.UserService" scope="prototype">
    <property name="defaultRole" value="USER"/>
</bean>
```

### 🔹 Bean Lifecycle

**Bean creation and destruction phases**

```java
// 1. Instantiation
// 2. Population of properties
// 3. BeanNameAware.setBeanName()
// 4. BeanFactoryAware.setBeanFactory()
// 5. ApplicationContextAware.setApplicationContext()
// 6. InitializingBean.afterPropertiesSet()
// 7. Custom init method
// 8. Bean is ready to use
// 9. DisposableBean.destroy()
// 10. Custom destroy method

@Component
public class LifecycleBean implements 
    BeanNameAware, BeanFactoryAware, ApplicationContextAware, 
    InitializingBean, DisposableBean {
    
    @Override
    public void setBeanName(String name) {
        System.out.println("Bean name: " + name);
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Bean initialized");
    }
    
    @Override
    public void destroy() throws Exception {
        System.out.println("Bean destroyed");
    }
}
```

### 🔹 Special Bean Types

```java
// FactoryBean - creates other beans
@Component
public class ConnectionFactoryBean implements FactoryBean<Connection> {
    @Override
    public Connection getObject() throws Exception {
        return createConnection();
    }
    
    @Override
    public Class<?> getObjectType() {
        return Connection.class;
    }
}

// BeanPostProcessor - modifies beans after creation
@Component
public class CustomBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        // Modify bean after initialization
        return bean;
    }
}
```

### 🔹 Bean Scopes

**Bean lifecycle management**

```java
// Singleton (default) - one instance per container
@Service
@Scope("singleton")
public class SingletonService {}

// Prototype - new instance each time
@Service
@Scope("prototype")
public class PrototypeService {}

// Request - one instance per HTTP request
@Service
@Scope("request")
public class RequestService {}

// Session - one instance per HTTP session
@Service
@Scope("session")
public class SessionService {}

// Application - one instance per ServletContext
@Service
@Scope("application")
public class ApplicationService {}
```

### 🔹 Best Practices

```java
// Use meaningful bean names
// Prefer annotation-based configuration
// Use appropriate scopes
// Avoid circular dependencies
// Implement lifecycle interfaces when needed
// Use @PostConstruct/@PreDestroy for custom initialization
// Document bean purposes and dependencies
```

---

## 🎯 Interview One-Liner

> Bean is Spring-managed object with lifecycle, dependencies, configuration; created by IoC container; has name, scope, dependencies; supports various instantiation methods and lifecycle callbacks.

---

## ✅ 58. Bean scopes in Spring

**Bean scopes define the lifecycle and visibility of Spring beans, determining how long a bean instance lives and how it's shared between requests.**

### 🔹 Singleton Scope (Default)

**One instance per Spring container**

```java
@Service
@Scope("singleton")  // Default scope
public class UserService {
    private int counter = 0;
    
    public void increment() {
        counter++;
        System.out.println("Counter: " + counter);
    }
}

// Usage
UserService service1 = context.getBean(UserService.class);
UserService service2 = context.getBean(UserService.class);

service1.increment();  // Counter: 1
service2.increment();  // Counter: 2 (same instance)

System.out.println(service1 == service2);  // true
```

**Characteristics:**
- **One instance**: Shared across entire application
- **Thread-safety**: Must be thread-safe if stateful
- **Performance**: Best performance, minimal memory
- **Use case**: Stateless services, utilities

### 🔹 Prototype Scope

**New instance for each request**

```java
@Service
@Scope("prototype")
public class OrderProcessor {
    private String orderId;
    
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    
    public void process() {
        System.out.println("Processing order: " + orderId);
    }
}

// Usage
OrderProcessor processor1 = context.getBean(OrderProcessor.class);
OrderProcessor processor2 = context.getBean(OrderProcessor.class);

processor1.setOrderId("ORD-001");
processor2.setOrderId("ORD-002");

processor1.process();  // Processing order: ORD-001
processor2.process();  // Processing order: ORD-002

System.out.println(processor1 == processor2);  // false
```

**Characteristics:**
- **New instance**: Each injection/request gets new instance
- **State isolation**: Safe for stateful beans
- **Memory usage**: Higher memory consumption
- **Cleanup**: Container doesn't manage destruction

### 🔹 Request Scope

**One instance per HTTP request**

```java
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RequestContext {
    private String userId;
    private Map<String, Object> attributes = new HashMap<>();
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public void setAttribute(String key, Object value) {
        attributes.put(key, value);
    }
    
    public Object getAttribute(String key) {
        return attributes.get(key);
    }
}

// Usage in controller
@Controller
public class UserController {
    @Autowired
    private RequestContext requestContext;
    
    @GetMapping("/user/profile")
    public String profile(HttpServletRequest request) {
        // Extract user from request/session
        String userId = extractUserId(request);
        requestContext.setUserId(userId);
        
        // Use request-scoped data
        return "profile";
    }
}
```

**Characteristics:**
- **Request-bound**: Instance lives for single HTTP request
- **Thread-safety**: Safe in multi-threaded web apps
- **Proxy required**: Use proxyMode for injection into singletons
- **Use case**: Request-specific data, user context

### 🔹 Session Scope

**One instance per HTTP session**

```java
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserSession {
    private String username;
    private List<String> permissions;
    private LocalDateTime loginTime;
    
    public void initialize(String username, List<String> permissions) {
        this.username = username;
        this.permissions = permissions;
        this.loginTime = LocalDateTime.now();
    }
    
    public boolean hasPermission(String permission) {
        return permissions.contains(permission);
    }
    
    public String getUsername() {
        return username;
    }
}

// Usage
@Controller
public class SecureController {
    @Autowired
    private UserSession userSession;
    
    @GetMapping("/admin")
    public String adminPage() {
        if (!userSession.hasPermission("ADMIN")) {
            return "access-denied";
        }
        return "admin";
    }
}
```

**Characteristics:**
- **Session-bound**: Instance lives for user session
- **User-specific**: Different instance per user
- **Persistence**: Survives multiple requests
- **Use case**: User preferences, shopping cart, login state

### 🔹 Application Scope

**One instance per ServletContext**

```java
@Component
@Scope(value = WebApplicationContext.SCOPE_APPLICATION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ApplicationCache {
    private final Map<String, Object> cache = new ConcurrentHashMap<>();
    
    public void put(String key, Object value) {
        cache.put(key, value);
    }
    
    public Object get(String key) {
        return cache.get(key);
    }
    
    public void clear() {
        cache.clear();
    }
    
    public int size() {
        return cache.size();
    }
}

// Usage
@Service
public class ProductService {
    @Autowired
    private ApplicationCache cache;
    
    public Product getProduct(String id) {
        Product product = (Product) cache.get("product:" + id);
        if (product == null) {
            product = repository.findById(id);
            cache.put("product:" + id, product);
        }
        return product;
    }
}
```

**Characteristics:**
- **Application-wide**: Shared across all users/sessions
- **Global state**: Visible to entire application
- **Thread-safety**: Must be thread-safe
- **Use case**: Application configuration, global cache

### 🔹 Custom Scopes

**Define your own scopes**

```java
// Custom scope implementation
public class ThreadScope implements Scope {
    private final ThreadLocal<Map<String, Object>> threadLocal = 
        ThreadLocal.withInitial(HashMap::new);
    
    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        Map<String, Object> scope = threadLocal.get();
        Object object = scope.get(name);
        if (object == null) {
            object = objectFactory.getObject();
            scope.put(name, object);
        }
        return object;
    }
    
    @Override
    public Object remove(String name) {
        return threadLocal.get().remove(name);
    }
    
    @Override
    public void registerDestructionCallback(String name, Runnable callback) {
        // Handle cleanup
    }
    
    @Override
    public Object resolveContextualObject(String key) {
        return null;
    }
    
    @Override
    public String getConversationId() {
        return Thread.currentThread().getName();
    }
}

// Register custom scope
@Configuration
public class ScopeConfig {
    @Bean
    public static CustomScopeConfigurer customScopeConfigurer() {
        CustomScopeConfigurer configurer = new CustomScopeConfigurer();
        configurer.addScope("thread", new ThreadScope());
        return configurer;
    }
}

// Use custom scope
@Component
@Scope("thread")
public class ThreadScopedBean {
    // One instance per thread
}
```

### 🔹 Scope Comparison

| Scope | Lifecycle | Thread-safe | Use Case |
|-------|-----------|-------------|----------|
| **Singleton** | Container | Must ensure | Shared services |
| **Prototype** | Request | Yes | Stateful objects |
| **Request** | HTTP request | Yes | Request data |
| **Session** | HTTP session | Yes | User data |
| **Application** | App lifetime | Must ensure | Global data |

### 🔹 Scoped Proxy

**Proxy for injecting scoped beans into singletons**

```java
// Without proxy - fails
@Service
public class SingletonService {
    @Autowired
    private RequestScopedBean requestBean;  // ERROR at startup
}

// With proxy - works
@Service
public class SingletonService {
    @Autowired
    private RequestScopedBean requestBean;  // Proxy injected
}

// Proxy delegates to actual scoped instance at runtime
@Configuration
public class ProxyConfig {
    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public RequestScopedBean requestScopedBean() {
        return new RequestScopedBean();
    }
}
```

### 🔹 Best Practices

```java
// Use singleton for stateless services
// Use prototype for stateful objects
// Use request/session scope in web applications
// Ensure thread-safety for shared scopes
// Use scoped proxies when injecting scoped beans
// Consider memory implications of scopes
// Document scope requirements
```

---

## 🎯 Interview One-Liner

> Singleton: one instance per container (default); Prototype: new instance per request; Request: per HTTP request; Session: per user session; Application: per web app; choose based on sharing requirements.

---

## ✅ 59. Bean lifecycle in Spring

**Bean lifecycle consists of instantiation, initialization, usage, and destruction phases, with multiple extension points for customization.**

### 🔹 Lifecycle Phases

**Complete bean lifecycle**

```java
// 1. Instantiation - Bean created
// 2. Population - Dependencies injected
// 3. Initialization - Bean configured and ready
// 4. Usage - Bean used by application
// 5. Destruction - Bean cleaned up

@Component
public class LifecycleBean {
    public LifecycleBean() {
        System.out.println("1. Constructor called");
    }
    
    @Autowired
    public void setDependency(Dependency dep) {
        System.out.println("2. Dependencies injected");
    }
    
    @PostConstruct
    public void init() {
        System.out.println("3. @PostConstruct called");
    }
    
    public void use() {
        System.out.println("4. Bean in use");
    }
    
    @PreDestroy
    public void destroy() {
        System.out.println("5. @PreDestroy called");
    }
}
```

### 🔹 Detailed Lifecycle

### 🔹 Phase 1: Instantiation

**Bean object is created**

```java
// Ways to instantiate:
1. Constructor (default)
2. Static factory method
3. Instance factory method

// Constructor instantiation
@Service
public class UserService {
    public UserService() {
        // Constructor logic
        System.out.println("Bean instantiated");
    }
}

// Factory method instantiation
@Configuration
public class FactoryConfig {
    @Bean
    public DataSource dataSource() {
        // Factory creates and returns bean
        return DataSourceFactory.create();
    }
}
```

### 🔹 Phase 2: Population of Properties

**Dependencies and configuration injected**

```java
@Service
public class OrderService {
    @Autowired
    private UserService userService;  // Dependency injection
    
    @Value("${app.order.timeout}")
    private int timeout;  // Property injection
    
    // Called after dependencies injected
    @Autowired
    public void initialize(OrderService self) {
        System.out.println("Dependencies populated");
    }
}
```

### 🔹 Phase 3: Initialization

**Bean is configured and prepared for use**

```java
// Multiple ways to hook into initialization:

// 1. @PostConstruct (recommended)
@Component
public class InitBean1 {
    @PostConstruct
    public void init() {
        // Initialization logic
        validateConfiguration();
        establishConnections();
    }
}

// 2. InitializingBean interface
@Component
public class InitBean2 implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        // Initialization logic
    }
}

// 3. Custom init method
@Configuration
public class Config {
    @Bean(initMethod = "customInit")
    public MyBean myBean() {
        return new MyBean();
    }
}

public class MyBean {
    public void customInit() {
        // Custom initialization
    }
}

// 4. BeanPostProcessor (global)
@Component
public class CustomBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        // Modify bean after initialization
        return bean;
    }
}
```

### 🔹 Phase 4: Usage

**Bean is used by the application**

```java
@Service
public class ApplicationService {
    @Autowired
    private UserService userService;
    
    public void processRequest() {
        // Bean is actively used
        User user = userService.getUser(1);
        // Process user data
    }
}
```

### 🔹 Phase 5: Destruction

**Bean is cleaned up before removal**

```java
// Multiple ways to hook into destruction:

// 1. @PreDestroy (recommended)
@Component
public class DestroyBean1 {
    @PreDestroy
    public void cleanup() {
        // Cleanup logic
        closeConnections();
        releaseResources();
    }
}

// 2. DisposableBean interface
@Component
public class DestroyBean2 implements DisposableBean {
    @Override
    public void destroy() throws Exception {
        // Cleanup logic
    }
}

// 3. Custom destroy method
@Configuration
public class Config {
    @Bean(destroyMethod = "customDestroy")
    public MyBean myBean() {
        return new MyBean();
    }
}

public class MyBean {
    public void customDestroy() {
        // Custom cleanup
    }
}
```

### 🔹 Aware Interfaces

**Beans can be aware of container**

```java
@Component
public class AwareBean implements 
    BeanNameAware, BeanFactoryAware, ApplicationContextAware, 
    EnvironmentAware, EmbeddedValueResolverAware {
    
    @Override
    public void setBeanName(String name) {
        System.out.println("Bean name: " + name);
    }
    
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("BeanFactory injected");
    }
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("ApplicationContext injected");
    }
    
    @Override
    public void setEnvironment(Environment environment) {
        System.out.println("Environment injected");
    }
    
    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        System.out.println("ValueResolver injected");
    }
}
```

### 🔹 BeanPostProcessor

**Modify beans during lifecycle**

```java
@Component
public class CustomBeanPostProcessor implements BeanPostProcessor {
    
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // Called before initialization
        if (bean instanceof Validatable) {
            ((Validatable) bean).validate();
        }
        return bean;
    }
    
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // Called after initialization
        if (bean instanceof Proxyable) {
            return createProxy(bean);
        }
        return bean;
    }
}
```

### 🔹 Lifecycle in Different Scopes

```java
// Singleton scope - created once
@Service
@Scope("singleton")
public class SingletonBean {
    // Lifecycle: constructor → init → (usage) → destroy
}

// Prototype scope - created per request
@Service
@Scope("prototype")
public class PrototypeBean {
    // Lifecycle: constructor → init → usage
    // No destroy phase - container doesn't manage
}

// Request scope - per HTTP request
@Component
@Scope("request")
public class RequestBean {
    // Lifecycle: constructor → init → usage → destroy (end of request)
}
```

### 🔹 @DependsOn

**Control bean initialization order**

```java
@Configuration
public class DependencyConfig {
    @Bean
    @DependsOn("dataSource")
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
    
    @Bean
    public DataSource dataSource() {
        return new HikariDataSource();
    }
}
```

### 🔹 @Lazy

**Defer bean initialization**

```java
@Service
@Lazy
public class LazyService {
    // Created only when first accessed
}

@Configuration
public class Config {
    @Bean
    @Lazy
    public ExpensiveBean expensiveBean() {
        // Created only when needed
        return new ExpensiveBean();
    }
}
```

### 🔹 Best Practices

```java
// Use @PostConstruct/@PreDestroy for lifecycle hooks
// Prefer constructor injection over setters
// Implement validation in initialization phase
// Clean up resources in destruction phase
// Use @DependsOn for explicit dependencies
// Use @Lazy for expensive beans
// Avoid complex logic in lifecycle methods
// Document lifecycle requirements
```

---

## 🎯 Interview One-Liner

> Bean lifecycle: instantiation → property population → initialization (@PostConstruct) → usage → destruction (@PreDestroy); aware interfaces for container access; BeanPostProcessor for modification.

---

## ✅ 60. What is ApplicationContext?

**ApplicationContext is the central interface for Spring's IoC container, providing advanced features like internationalization, event publishing, and resource loading beyond basic BeanFactory functionality.**

### 🔹 ApplicationContext Overview

**Spring's advanced IoC container**

```java
// ApplicationContext interface hierarchy
public interface ApplicationContext extends 
    EnvironmentCapable, ListableBeanFactory, HierarchicalBeanFactory,
    MessageSource, ApplicationEventPublisher, ResourcePatternResolver {
    // Advanced container features
}

// Common implementations
ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
ApplicationContext context = new FileSystemXmlApplicationContext("c:/app/config.xml");
```

### 🔹 Key Features

### 🔹 1. Bean Management

**Advanced bean factory capabilities**

```java
ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

// Get beans
UserService service = context.getBean(UserService.class);
UserService service = context.getBean("userService", UserService.class);

// Get all beans of type
Map<String, UserService> services = context.getBeansOfType(UserService.class);

// Check bean existence
boolean exists = context.containsBean("userService");
boolean singleton = context.isSingleton("userService");
boolean prototype = context.isPrototype("userService");

// Get bean definition
BeanDefinition definition = context.getBeanDefinition("userService");
```

### 🔹 2. Internationalization (i18n)

**Message source for localization**

```java
// Configure message source
@Configuration
public class I18nConfig {
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasename("messages");
        source.setDefaultEncoding("UTF-8");
        return source;
    }
}

// Usage
@Service
public class GreetingService {
    @Autowired
    private ApplicationContext context;
    
    public String getGreeting(String name, Locale locale) {
        String template = context.getMessage("greeting.template", null, locale);
        return String.format(template, name);
    }
}

// messages.properties
greeting.template=Hello, %s!

// messages_fr.properties  
greeting.template=Bonjour, %s!
```

### 🔹 3. Event Publishing

**Publish and listen to application events**

```java
// Custom event
public class UserRegisteredEvent extends ApplicationEvent {
    private final User user;
    
    public UserRegisteredEvent(Object source, User user) {
        super(source);
        this.user = user;
    }
    
    public User getUser() {
        return user;
    }
}

// Event listener
@Component
public class UserEventListener {
    @EventListener
    public void handleUserRegistration(UserRegisteredEvent event) {
        System.out.println("User registered: " + event.getUser().getName());
        // Send welcome email, etc.
    }
}

// Event publisher
@Service
public class UserService {
    @Autowired
    private ApplicationContext context;
    
    public void registerUser(User user) {
        // Save user
        repository.save(user);
        
        // Publish event
        context.publishEvent(new UserRegisteredEvent(this, user));
    }
}
```

### 🔹 4. Resource Loading

**Load resources from various locations**

```java
@Service
public class ResourceService {
    @Autowired
    private ApplicationContext context;
    
    public void loadResources() throws IOException {
        // Load from classpath
        Resource resource1 = context.getResource("classpath:config.properties");
        
        // Load from file system
        Resource resource2 = context.getResource("file:/etc/app/config.properties");
        
        // Load from URL
        Resource resource3 = context.getResource("https://example.com/config.properties");
        
        // Load multiple resources
        Resource[] resources = context.getResources("classpath*:config/*.properties");
        
        // Read content
        try (InputStream is = resource1.getInputStream()) {
            // Process resource
        }
    }
}
```

### 🔹 5. Environment and Profiles

**Access environment properties and profiles**

```java
@Service
public class EnvironmentService {
    @Autowired
    private ApplicationContext context;
    
    public void checkEnvironment() {
        Environment env = context.getEnvironment();
        
        // Get active profiles
        String[] profiles = env.getActiveProfiles();
        
        // Get property
        String dbUrl = env.getProperty("spring.datasource.url");
        
        // Check if profile is active
        boolean isProd = env.acceptsProfiles(Profiles.of("prod"));
        
        // Get system properties
        String javaVersion = env.getProperty("java.version");
    }
}
```

### 🔹 6. BeanFactory vs ApplicationContext

**ApplicationContext extends BeanFactory**

```java
// BeanFactory (basic)
BeanFactory factory = new XmlBeanFactory(new ClassPathResource("beans.xml"));
UserService service = (UserService) factory.getBean("userService");

// ApplicationContext (advanced)
ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
UserService service = context.getBean(UserService.class);

// ApplicationContext features over BeanFactory:
// - Internationalization (MessageSource)
// - Event publishing (ApplicationEventPublisher)  
// - Resource loading (ResourcePatternResolver)
// - Environment access
// - Automatic BeanPostProcessor registration
// - Automatic BeanFactoryPostProcessor execution
```

### 🔹 7. Hierarchical Contexts

**Parent-child context relationships**

```java
// Root context
ApplicationContext rootContext = new AnnotationConfigApplicationContext(RootConfig.class);

// Child context
ApplicationContext childContext = new AnnotationConfigApplicationContext();
childContext.setParent(rootContext);

// Child can access parent's beans
// Parent cannot access child's beans
UserService service = childContext.getBean(UserService.class);  // From parent or child
```

### 🔹 8. Lifecycle Management

**Manage application lifecycle**

```java
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        
        // Application is running
        
        // Graceful shutdown
        context.close();
    }
}

// Lifecycle callbacks
@Component
public class LifecycleManager implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("Application context refreshed");
        // Initialize application resources
    }
}
```

### 🔹 Common Implementations

```java
// Annotation-based (modern)
ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

// XML-based (legacy)
ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

// Web application
ApplicationContext context = new XmlWebApplicationContext();

// Spring Boot
ApplicationContext context = SpringApplication.run(App.class, args);
```

### 🔹 Best Practices

```java
// Use ApplicationContext over BeanFactory for features
// Prefer annotation-based configuration
// Leverage event publishing for loose coupling
// Use internationalization for multi-language support
// Access environment properties through context
// Implement lifecycle listeners for initialization
// Use hierarchical contexts for modular applications
```

---

## 🎯 Interview One-Liner

> ApplicationContext is advanced IoC container extending BeanFactory; provides i18n, events, resource loading, environment access; supports hierarchical contexts and lifecycle management.

---

### **Spring Boot**
## ✅ 61. What is Spring Boot? Advantages over Spring?

**Spring Boot is a framework built on top of Spring Framework that simplifies the creation of production-ready, stand-alone Spring applications with minimal configuration and setup.**

### 🔹 What is Spring Boot?

**Convention-over-configuration framework for Spring**

```java
// Traditional Spring application (lots of XML/config)
@Configuration
@ComponentScan
@EnableWebMvc
public class AppConfig {
    // Multiple configurations needed
}

// Spring Boot application (minimal setup)
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

### 🔹 Key Features

### 🔹 1. Auto-Configuration

**Automatically configures beans based on classpath**

```java
// No need to configure DataSource manually
// Spring Boot detects H2/MySQL and configures automatically
@SpringBootApplication
public class Application {
    // DataSource bean auto-configured
}
```

### 🔹 2. Standalone Applications

**Creates JAR with embedded server**

```java
// Traditional Spring: WAR deployment
// Spring Boot: JAR with embedded Tomcat
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

### 🔹 3. Opinionated Defaults

**Sensible defaults for common configurations**

```java
// Default port: 8080
// Default context path: /
// Default logging: Logback
// Default database: H2 (if on classpath)
```

### 🔹 4. Production Ready

**Built-in features for production**

```java
// Health checks, metrics, monitoring
// Embedded servers (Tomcat, Jetty, Undertow)
// Externalized configuration
// Security auto-configuration
```

### 🔹 Advantages over Spring Framework

| Aspect | Spring Framework | Spring Boot |
|--------|------------------|-------------|
| **Configuration** | XML/Java config required | Auto-configuration |
| **Dependencies** | Manual management | Starters manage dependencies |
| **Deployment** | WAR files | JAR files with embedded server |
| **Setup Time** | Days/weeks | Minutes |
| **Learning Curve** | Steep | Gentle |
| **Microservices** | Complex setup | Easy with starters |

### 🔹 Spring Boot vs Spring MVC

```java
// Spring MVC (traditional)
@Configuration
@EnableWebMvc
@ComponentScan
public class WebConfig extends WebMvcConfigurerAdapter {
    // Lots of configuration
}

// Spring Boot (simplified)
@SpringBootApplication
public class Application {
    // Everything auto-configured
}
```

### 🔹 When to Use Spring Boot

```java
// ✅ Microservices
// ✅ REST APIs
// ✅ Batch applications
// ✅ Cloud-native applications
// ✅ Rapid prototyping
// ✅ Production applications

// ❌ Legacy applications requiring custom configurations
// ❌ Applications needing fine-grained control
```

### 🔹 Best Practices

```java
// Use @SpringBootApplication for main class
// Leverage auto-configuration when possible
// Override only when necessary
// Use starters for dependency management
// Externalize configuration
// Use profiles for different environments
```

---

## 🎯 Interview One-Liner

> Spring Boot simplifies Spring development with auto-configuration, embedded servers, and opinionated defaults; reduces setup from weeks to minutes compared to traditional Spring.

---

## ✅ 62. What is auto-configuration in Spring Boot?

**Auto-configuration is Spring Boot's mechanism that automatically configures Spring application based on the dependencies present on the classpath, eliminating the need for manual bean configuration.**

### 🔹 How Auto-Configuration Works

**Convention-over-configuration approach**

```java
// Spring Boot scans classpath for libraries
// Automatically configures beans based on presence of:
// - spring-boot-starter-web → Tomcat + Spring MVC
// - spring-boot-starter-data-jpa → EntityManager + DataSource
// - spring-boot-starter-security → Security configuration

@SpringBootApplication  // Enables auto-configuration
public class Application {
    // No manual configuration needed
}
```

### 🔹 Auto-Configuration Classes

**Conditional bean registration**

```java
@Configuration
@ConditionalOnClass(DataSource.class)  // Only if DataSource on classpath
@ConditionalOnMissingBean(DataSource.class)  // Only if no manual bean
@EnableConfigurationProperties(DataSourceProperties.class)
public class DataSourceAutoConfiguration {
    
    @Bean
    @ConditionalOnMissingBean
    public DataSource dataSource(DataSourceProperties properties) {
        return DataSourceBuilder.create()
            .url(properties.getUrl())
            .username(properties.getUsername())
            .password(properties.getPassword())
            .build();
    }
}
```

### 🔹 Conditional Annotations

**Control when auto-configuration applies**

```java
@ConditionalOnClass          // If class is present
@ConditionalOnMissingClass   // If class is absent
@ConditionalOnBean           // If bean exists
@ConditionalOnMissingBean    // If bean doesn't exist
@ConditionalOnProperty       // If property has value
@ConditionalOnResource       // If resource exists
@ConditionalOnWebApplication // If web application
@ConditionalOnNotWebApplication // If not web application
```

### 🔹 Excluding Auto-Configuration

**Disable specific auto-configurations**

```java
@SpringBootApplication(exclude = {
    DataSourceAutoConfiguration.class,
    HibernateJpaAutoConfiguration.class
})
public class Application {
    // Custom configuration instead
}
```

### 🔹 Custom Auto-Configuration

**Create your own auto-configuration**

```java
@Configuration
@ConditionalOnClass(MyService.class)
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
public class MyServiceAutoConfiguration {
    
    @Bean
    @ConditionalOnMissingBean
    public MyService myService() {
        return new MyServiceImpl();
    }
}
```

### 🔹 Auto-Configuration Order

**@AutoConfigureBefore/@AutoConfigureAfter**

```java
@Configuration
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
public class MyRepositoryAutoConfiguration {
    // Configured after DataSource
}
```

### 🔹 Debugging Auto-Configuration

**See what auto-configurations are applied**

```properties
# application.properties
debug=true  # Shows auto-configuration report
```

### 🔹 Common Auto-Configurations

```java
// Web applications
- ServerPropertiesAutoConfiguration
- WebMvcAutoConfiguration
- HttpEncodingAutoConfiguration

// Data
- DataSourceAutoConfiguration
- JpaRepositoriesAutoConfiguration
- MongoAutoConfiguration

// Security
- SecurityAutoConfiguration
- UserDetailsServiceAutoConfiguration

// Actuator
- EndpointAutoConfiguration
- HealthIndicatorAutoConfiguration
```

### 🔹 Best Practices

```java
// Let Spring Boot auto-configure when possible
// Exclude only when you need custom configuration
// Use @ConditionalOnMissingBean for custom beans
// Debug with debug=true property
// Understand conditional annotations
// Create custom auto-configurations for libraries
```

---

## 🎯 Interview One-Liner

> Auto-configuration automatically configures Spring beans based on classpath dependencies using conditional annotations; eliminates manual XML/Java config for common scenarios.

---

## ✅ 63. Explain @SpringBootApplication annotation

**@SpringBootApplication is a convenience annotation that combines @Configuration, @EnableAutoConfiguration, and @ComponentScan, enabling Spring Boot's auto-configuration and component scanning in a single annotation.**

### 🔹 Annotation Composition

**Combines three essential annotations**

```java
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

// Equivalent to:
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application {
    // Same functionality
}
```

### 🔹 @Configuration

**Marks class as configuration source**

```java
@Configuration
public class AppConfig {
    @Bean
    public UserService userService() {
        return new UserServiceImpl();
    }
}
```

### 🔹 @EnableAutoConfiguration

**Enables auto-configuration**

```java
@EnableAutoConfiguration
public class Application {
    // Spring Boot scans classpath
    // Configures beans automatically
}
```

### 🔹 @ComponentScan

**Scans for Spring components**

```java
@ComponentScan(basePackages = "com.example")
public class Application {
    // Scans @Component, @Service, @Repository, @Controller
}
```

### 🔹 Customization Options

**Configure scanning and auto-configuration**

```java
@SpringBootApplication(
    scanBasePackages = "com.example",  // Component scan packages
    exclude = {DataSourceAutoConfiguration.class},  // Exclude auto-config
    excludeName = {"com.example.config"}  // Exclude by name
)
public class Application {
    // Customized scanning
}
```

### 🔹 Main Method Convention

**Standard Spring Boot application structure**

```java
@SpringBootApplication
public class Application {
    
    public static void main(String[] args) {
        // Bootstrap the application
        SpringApplication.run(Application.class, args);
    }
}
```

### 🔹 SpringApplication.run()

**What happens during startup**

```java
// 1. Creates ApplicationContext
// 2. Registers command line arguments
// 3. Loads configuration
// 4. Starts auto-configuration
// 5. Starts embedded server (if web app)
// 6. Triggers application ready event
```

### 🔹 Alternative Configurations

**Different ways to configure**

```java
// Separate annotations
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application { }

// Custom component scan
@SpringBootApplication
@PropertySource("classpath:custom.properties")
public class Application { }

// Exclude specific auto-configurations
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class Application { }
```

### 🔹 Best Practices

```java
// Place @SpringBootApplication on main class
// Keep main class in root package
// Use default scanning when possible
// Exclude auto-configuration only when necessary
// Use SpringApplication.run() for startup
// Customize scanBasePackages for multi-module projects
```

---

## 🎯 Interview One-Liner

> @SpringBootApplication combines @Configuration, @EnableAutoConfiguration, and @ComponentScan; enables auto-config and component scanning with single annotation.

---

## ✅ 64. What are Spring Boot Starters?

**Spring Boot Starters are curated sets of dependencies that simplify dependency management by providing a single dependency to include all necessary libraries for a specific functionality.**

### 🔹 What are Starters?

**Pre-configured dependency bundles**

```xml
<!-- Without starter (manual dependencies) -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-web</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-webmvc</artifactId>
</dependency>
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
</dependency>

<!-- With starter (single dependency) -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

### 🔹 Common Starters

### 🔹 1. Core Starters

```xml
<!-- Web applications -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<!-- REST APIs -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webflux</artifactId>
</dependency>

<!-- Data JPA -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<!-- Security -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

### 🔹 2. Database Starters

```xml
<!-- JDBC -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>

<!-- MongoDB -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-mongodb</artifactId>
</dependency>

<!-- Redis -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

### 🔹 3. Testing Starters

```xml
<!-- Unit and integration testing -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
</dependency>
```

### 🔹 4. Production Starters

```xml
<!-- Monitoring and management -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>

<!-- Logging -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-logging</artifactId>
</dependency>
```

### 🔹 Custom Starters

**Create your own starter**

```xml
<!-- Custom starter POM -->
<dependency>
    <groupId>com.example</groupId>
    <artifactId>my-service-starter</artifactId>
    <version>1.0.0</version>
</dependency>

<!-- Includes auto-configuration and dependencies -->
```

### 🔹 Starter Structure

**Typical starter components**

```java
// 1. Auto-configuration class
@Configuration
@ConditionalOnClass(MyService.class)
public class MyServiceAutoConfiguration {
    @Bean
    public MyService myService() {
        return new MyServiceImpl();
    }
}

// 2. Properties class
@ConfigurationProperties("my.service")
public class MyServiceProperties {
    private String url = "localhost";
    private int port = 8080;
}

// 3. spring.factories file
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
com.example.MyServiceAutoConfiguration
```

### 🔹 Benefits

```java
// ✅ Simplified dependency management
// ✅ Version compatibility guaranteed
// ✅ Auto-configuration included
// ✅ Convention over configuration
// ✅ Faster project setup
// ✅ Consistent dependency versions
```

### 🔹 Best Practices

```java
// Use official Spring Boot starters when possible
// Check starter contents with mvn dependency:tree
// Create custom starters for reusable components
// Include auto-configuration in custom starters
// Use spring-boot-starter-parent for version management
// Override versions only when necessary
```

---

## 🎯 Interview One-Liner

> Spring Boot Starters are dependency bundles providing single dependency for functionality; include auto-config, reduce manual dependency management, ensure version compatibility.

---

## ✅ 65. application.properties vs application.yml

**Both application.properties and application.yml are used for externalized configuration in Spring Boot, with properties using key=value format and YAML using hierarchical structure, but YAML is more readable for complex configurations.**

### 🔹 application.properties

**Traditional properties file format**

```properties
# application.properties
server.port=8080
server.servlet.context-path=/api
spring.datasource.url=jdbc:mysql://localhost:3306/mydb
spring.datasource.username=root
spring.datasource.password=secret
logging.level.com.example=DEBUG
app.name=My Application
app.version=1.0.0
```

### 🔹 application.yml

**YAML format for configuration**

```yaml
# application.yml
server:
  port: 8080
  servlet:
    context-path: /api

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/mydb
    username: root
    password: secret

logging:
  level:
    com.example: DEBUG

app:
  name: "My Application"
  version: 1.0.0
```

### 🔹 Comparison

| Aspect              | Properties      | YAML             |
|---------------------|-----------------|------------------|
| **Readability**     | Flat structure  | Hierarchical     |
| **Complex Config**  | Verbose         | Clean            |
| **Lists**           | Comma-separated | Native support   |
| **Comments**        | # comments      | # comments       |
| **Multi-line**      | Not supported   | Supported        |
| **Validation**      | Basic           | Better structure |

### 🔹 Lists and Complex Structures

```properties
# Properties - comma separated
app.servers=server1,server2,server3
app.config.items[0].name=item1
app.config.items[0].value=value1
```

```yaml
# YAML - native lists and objects
app:
  servers:
    - server1
    - server2
    - server3
  config:
    items:
      - name: item1
        value: value1
      - name: item2
        value: value2
```

### 🔹 Profiles

```properties
# application-dev.properties
spring.profiles.active=dev
database.url=jdbc:h2:mem:dev

# application-prod.properties  
spring.profiles.active=prod
database.url=jdbc:mysql://prod:3306/app
```

```yaml
# application.yml
spring:
  profiles:
    active: dev
---
spring:
  profiles: dev
  datasource:
    url: jdbc:h2:mem:dev
---
spring:
  profiles: prod
  datasource:
    url: jdbc:mysql://prod:3306/app
```

### 🔹 Environment Variables

```properties
# Properties
app.database.url=${DATABASE_URL:jdbc:h2:mem:default}
app.database.user=${DB_USER:admin}
```

```yaml
# YAML
app:
  database:
    url: ${DATABASE_URL:jdbc:h2:mem:default}
    user: ${DB_USER:admin}
```

### 🔹 When to Use Which

```yaml
# Use YAML for:
# - Complex hierarchical configurations
# - Lists and nested objects
# - Better readability
# - Microservices configurations

# Use Properties for:
# - Simple key-value pairs
# - Legacy applications
# - Tools that don't support YAML
# - Environment variables
```

### 🔹 Loading Order

**Configuration loading precedence**

```yaml
# 1. Command line arguments
# 2. JNDI attributes
# 3. System properties
# 4. Environment variables
# 5. application-{profile}.yml/properties
# 6. application.yml/properties
# 7. @PropertySource
# 8. Default properties
```

### 🔹 Best Practices

```yaml
# Use YAML for new applications
# Keep sensitive data in environment variables
# Use profiles for different environments
# Validate configuration with @ConfigurationProperties
# Document configuration properties
# Use consistent naming conventions
```

---

## 🎯 Interview One-Liner

> application.properties uses flat key=value format; application.yml uses hierarchical YAML structure; YAML preferred for complex configs, properties for simple key-value pairs.

---

## ✅ 66. What are Spring Profiles? How to use them?

**Spring Profiles provide a way to segregate application configuration and beans for different environments (dev, test, prod) or use cases, allowing conditional activation based on active profiles.**

### 🔹 What are Profiles?

**Environment-specific configurations**

```java
@Configuration
public class AppConfig {
    
    @Bean
    @Profile("dev")
    public DataSource devDataSource() {
        return new EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .build();
    }
    
    @Bean
    @Profile("prod")
    public DataSource prodDataSource() {
        return DataSourceBuilder.create()
            .url("jdbc:mysql://prod:3306/app")
            .build();
    }
}
```

### 🔹 Activating Profiles

**Multiple ways to activate profiles**

```java
// 1. Command line
java -jar app.jar --spring.profiles.active=prod

// 2. Environment variable
export SPRING_PROFILES_ACTIVE=prod

// 3. application.properties/yml
spring.profiles.active=prod

// 4. Programmatically
SpringApplication app = new SpringApplication(MyApplication.class);
app.setAdditionalProfiles("prod");
app.run(args);
```

### 🔹 Profile-Specific Configuration

**Separate configuration files**

```properties
# application.properties (common)
app.name=MyApp

# application-dev.properties
spring.profiles.active=dev
server.port=8080
logging.level.root=DEBUG

# application-prod.properties
spring.profiles.active=prod
server.port=8443
logging.level.root=INFO
```

```yaml
# application.yml
spring:
  profiles:
    active: dev
---
spring:
  profiles: dev
server:
  port: 8080
logging:
  level:
    root: DEBUG
---
spring:
  profiles: prod
server:
  port: 8443
logging:
  level:
    root: INFO
```

### 🔹 Profile Annotations

**Conditional bean registration**

```java
@Service
@Profile("dev")
public class DevService implements MyService {
    // Only created when 'dev' profile active
}

@Service
@Profile("prod")
public class ProdService implements MyService {
    // Only created when 'prod' profile active
}

@Service
@Profile("!test")  // Not in test profile
public class NonTestService implements MyService {
    // Created in all profiles except 'test'
}
```

### 🔹 Multiple Profiles

**Activate multiple profiles**

```java
// Activate multiple profiles
spring.profiles.active=dev,metrics

// Bean with multiple profiles
@Profile({"dev", "staging"})
public class DevStagingService {
    // Active in dev OR staging
}
```

### 🔹 Default Profile

**Default profile when none specified**

```java
@Profile("default")
public class DefaultService {
    // Active when no profile specified
}
```

### 🔹 Programmatic Profile Detection

**Check active profiles in code**

```java
@Service
public class ProfileAwareService {
    
    @Autowired
    private Environment environment;
    
    public void someMethod() {
        if (environment.acceptsProfiles("dev")) {
            // Dev-specific logic
        }
        
        String[] activeProfiles = environment.getActiveProfiles();
        // Check active profiles
    }
}
```

### 🔹 Profile Groups (Spring Boot 2.4+)

**Group related profiles**

```yaml
# application.yml
spring:
  profiles:
    group:
      dev: dev-db, dev-log
      prod: prod-db, prod-log, prod-monitoring
```

### 🔹 Testing with Profiles

**Profile-specific tests**

```java
@SpringBootTest
@ActiveProfiles("test")
public class MyServiceTest {
    
    @Test
    public void testWithTestProfile() {
        // Test with test profile active
    }
}
```

### 🔹 Best Practices

```java
// Use profiles for environment-specific config
// Keep common config in application.properties/yml
// Use profile-specific files for overrides
// Avoid complex profile logic in code
// Document profile usage
// Test with different profiles
// Use @Profile on configuration classes
```

---

## 🎯 Interview One-Liner

> Spring Profiles enable environment-specific configurations; activate via spring.profiles.active property; use @Profile annotation for conditional bean registration.

---

## ✅ 67. How to externalize configuration?

**Externalizing configuration in Spring Boot involves moving application settings outside the codebase to properties files, environment variables, command-line arguments, or external systems for better maintainability and security.**

### 🔹 Configuration Sources

**Multiple configuration sources with precedence**

```java
// Loading order (highest to lowest precedence):
// 1. Command line arguments
// 2. JNDI attributes (java:comp/env)
// 3. System properties (-D)
// 4. Environment variables
// 5. Profile-specific application properties
// 6. Application properties
// 7. @PropertySource annotated classes
// 8. Default properties
```

### 🔹 Properties Files

**application.properties/yml**

```properties
# application.properties
app.name=MyApp
app.version=1.0.0
database.url=jdbc:mysql://localhost:3306/mydb
database.username=${DB_USER:admin}
database.password=${DB_PASSWORD:secret}
```

```yaml
# application.yml
app:
  name: MyApp
  version: 1.0.0
database:
  url: jdbc:mysql://localhost:3306/mydb
  username: ${DB_USER:admin}
  password: ${DB_PASSWORD:secret}
```

### 🔹 Environment Variables

**System environment variables**

```bash
export SPRING_DATASOURCE_URL=jdbc:mysql://prod:3306/app
export SPRING_DATASOURCE_USERNAME=produser
export SPRING_DATASOURCE_PASSWORD=prodpass
export SERVER_PORT=8443
```

### 🔹 Command Line Arguments

**Override at runtime**

```bash
java -jar app.jar --server.port=9090 --spring.profiles.active=prod
```

### 🔹 External Configuration Files

**Load from external locations**

```bash
# Specify config location
java -jar app.jar --spring.config.location=file:/etc/myapp/config.properties

# Multiple locations
java -jar app.jar --spring.config.location=classpath:/config/,file:/etc/myapp/
```

### 🔹 @ConfigurationProperties

**Type-safe configuration binding**

```java
@ConfigurationProperties(prefix = "app.database")
public class DatabaseProperties {
    
    private String url;
    private String username;
    private String password;
    private int maxConnections = 10;
    
    // getters and setters
}

@Configuration
public class DatabaseConfig {
    
    @Bean
    @ConfigurationProperties(prefix = "app.database")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }
}
```

### 🔹 @Value Annotation

**Inject individual properties**

```java
@Service
public class MyService {
    
    @Value("${app.name}")
    private String appName;
    
    @Value("${app.timeout:30}")
    private int timeout;  // Default value
    
    @Value("${app.features:}")
    private String[] features;  // Array from comma-separated
}
```

### 🔹 Profile-Specific External Config

**Environment-specific external files**

```bash
# Load dev-specific config
java -jar app.jar --spring.config.location=file:/config/dev/

# With profile
java -jar app.jar --spring.profiles.active=prod --spring.config.location=file:/config/prod/
```

### 🔹 Cloud Configuration

**Spring Cloud Config**

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-config</artifactId>
</dependency>
```

```properties
# application.properties
spring.cloud.config.uri=http://config-server:8888
spring.cloud.config.name=myapp
```

### 🔹 JNDI Configuration

**Container-provided configuration**

```java
@Bean
public DataSource dataSource() throws NamingException {
    JndiTemplate jndi = new JndiTemplate();
    return jndi.lookup("java:comp/env/jdbc/myDataSource", DataSource.class);
}
```

### 🔹 Best Practices

```java
// Use environment variables for sensitive data
// Provide sensible defaults
// Document all configuration properties
// Use @ConfigurationProperties for type safety
// Validate configuration values
// Use profiles for different environments
// Externalize config for containerized deployments
```

---

## 🎯 Interview One-Liner

> Externalize config via properties files, environment variables, command-line args; use @ConfigurationProperties for type-safe binding; order: command line > env vars > properties files.

---

## ✅ 68. What is @ConfigurationProperties?

**@ConfigurationProperties is a Spring Boot annotation that binds external configuration properties to Java objects, providing type-safe access to configuration values with validation and documentation support.**

### 🔹 Basic Usage

**Bind properties to POJO**

```java
@ConfigurationProperties(prefix = "app.database")
public class DatabaseProperties {
    
    private String url;
    private String username;
    private String password;
    private int maxPoolSize = 10;
    
    // getters and setters
}

// Usage in configuration
@Configuration
public class DatabaseConfig {
    
    @Bean
    public DataSource dataSource(DatabaseProperties props) {
        return DataSourceBuilder.create()
            .url(props.getUrl())
            .username(props.getUsername())
            .password(props.getPassword())
            .build();
    }
}
```

### 🔹 Properties File

**Corresponding configuration**

```yaml
# application.yml
app:
  database:
    url: jdbc:mysql://localhost:3306/mydb
    username: admin
    password: secret
    max-pool-size: 20
```

### 🔹 Validation

**Validate configuration values**

```java
@ConfigurationProperties(prefix = "app.database")
@Validated
public class DatabaseProperties {
    
    @NotBlank
    private String url;
    
    @NotBlank
    private String username;
    
    @NotBlank
    private String password;
    
    @Min(1)
    @Max(100)
    private int maxPoolSize = 10;
    
    // getters and setters
}
```

### 🔹 Nested Properties

**Complex nested configurations**

```java
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    
    private Database database = new Database();
    private Security security = new Security();
    
    public static class Database {
        private String url;
        private String username;
        // getters and setters
    }
    
    public static class Security {
        private String jwtSecret;
        private int tokenValidity = 3600;
        // getters and setters
    }
    
    // getters and setters
}
```

```yaml
app:
  database:
    url: jdbc:mysql://localhost:3306/app
    username: admin
  security:
    jwt-secret: mySecretKey
    token-validity: 7200
```

### 🔹 Lists and Maps

**Bind collections**

```java
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    
    private List<String> servers = new ArrayList<>();
    private Map<String, String> config = new HashMap<>();
    
    // getters and setters
}
```

```yaml
app:
  servers:
    - server1.example.com
    - server2.example.com
  config:
    key1: value1
    key2: value2
```

### 🔹 Duration and DataSize

**Type-safe values**

```java
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    
    private Duration timeout = Duration.ofSeconds(30);
    private DataSize maxFileSize = DataSize.ofMegabytes(10);
    
    // getters and setters
}
```

```yaml
app:
  timeout: 45s
  max-file-size: 20MB
```

### 🔹 @ConfigurationPropertiesScan

**Enable scanning for properties classes**

```java
@SpringBootApplication
@ConfigurationPropertiesScan("com.example.config")
public class Application {
    // Scans for @ConfigurationProperties classes
}
```

### 🔹 Constructor Binding

**Immutable properties with constructor**

```java
@ConfigurationProperties(prefix = "app.database")
public class DatabaseProperties {
    
    private final String url;
    private final String username;
    private final String password;
    
    public DatabaseProperties(
        @DefaultValue("jdbc:h2:mem:test") String url,
        String username,
        String password) {
        
        this.url = url;
        this.username = username;
        this.password = password;
    }
    
    // getters only
}
```

### 🔹 Best Practices

```java
// Use @ConfigurationProperties for complex configs
// Enable validation with @Validated
// Provide sensible defaults
// Use constructor binding for immutability
// Document properties with @ConfigurationProperties
// Group related properties with prefixes
// Use type-safe values (Duration, DataSize)
```

---

## 🎯 Interview One-Liner

> @ConfigurationProperties binds external properties to Java objects with type safety; supports validation, nested objects, lists/maps; prefix-based property grouping.

---

## ✅ 69. What is Spring Boot Actuator?

**Spring Boot Actuator provides production-ready features like health checks, metrics, monitoring, and management endpoints to help monitor and manage Spring Boot applications in production environments.**

### 🔹 What is Actuator?

**Production monitoring and management**

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

```yaml
# application.yml
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics
  endpoint:
    health:
      show-details: when-authorized
```

### 🔹 Built-in Endpoints

**Common actuator endpoints**

```bash
# Health check
GET /actuator/health
# Application info
GET /actuator/info
# Application metrics
GET /actuator/metrics
# Environment properties
GET /actuator/env
# Bean information
GET /actuator/beans
# HTTP request mappings
GET /actuator/mappings
# Thread dump
GET /actuator/threaddump
# Heap dump
GET /actuator/heapdump
```

### 🔹 Health Indicators

**Health check components**

```java
@Component
public class DatabaseHealthIndicator implements HealthIndicator {
    
    @Override
    public Health health() {
        try {
            // Check database connection
            return Health.up().build();
        } catch (Exception e) {
            return Health.down()
                .withDetail("error", e.getMessage())
                .build();
        }
    }
}
```

### 🔹 Custom Endpoints

**Create custom management endpoints**

```java
@Component
@Endpoint(id = "custom")
public class CustomEndpoint {
    
    @ReadOperation
    public Map<String, Object> customInfo() {
        Map<String, Object> info = new HashMap<>();
        info.put("customProperty", "value");
        info.put("timestamp", System.currentTimeMillis());
        return info;
    }
    
    @WriteOperation
    public void updateCustom(@Selector String key, String value) {
        // Update operation
    }
}
```

### 🔹 Security

**Secure actuator endpoints**

```yaml
management:
  endpoints:
    web:
      exposure:
        include: health,info
  endpoint:
    health:
      show-details: when-authorized
  security:
    enabled: true
```

### 🔹 Metrics

**Application metrics collection**

```java
@Service
public class MyService {
    
    private final MeterRegistry registry;
    
    public MyService(MeterRegistry registry) {
        this.registry = registry;
    }
    
    public void processData() {
        Counter.builder("data.processed")
            .tag("service", "myService")
            .register(registry)
            .increment();
    }
}
```

### 🔹 Info Endpoint

**Application information**

```yaml
# application.yml
info:
  app:
    name: My Application
    version: 1.0.0
  build:
    artifact: ${project.artifactId}
    version: ${project.version}
```

### 🔹 JMX Endpoints

**JMX management**

```yaml
management:
  endpoints:
    jmx:
      exposure:
        include: "*"
```

### 🔹 Best Practices

```java
// Expose only necessary endpoints in production
// Secure sensitive endpoints
// Implement custom health indicators
// Use metrics for monitoring
// Configure info endpoint with build details
// Use JMX for local management
// Monitor actuator endpoints regularly
```

---

## 🎯 Interview One-Liner

> Spring Boot Actuator provides production monitoring endpoints (health, metrics, info); enables health checks, metrics collection, and application management for production deployments.

---

## ✅ 70. How to create custom starter in Spring Boot?

**Creating a custom Spring Boot starter involves creating an auto-configuration module that provides opinionated defaults and dependencies for a specific functionality, following Spring Boot's starter conventions.**

### 🔹 Starter Structure

**Typical starter project structure**

```
my-library-starter/
├── src/main/java/
│   ├── com/example/
│   │   ├── autoconfigure/
│   │   │   ├── MyLibraryAutoConfiguration.java
│   │   │   └── MyLibraryProperties.java
│   │   └── MyLibraryService.java
│   └── META-INF/
│       └── spring.factories
├── src/main/resources/
│   └── META-INF/
│       └── additional-spring-configuration-metadata.json
└── pom.xml
```

### 🔹 Auto-Configuration Class

**Main auto-configuration logic**

```java
@Configuration
@ConditionalOnClass(MyLibrary.class)
@ConditionalOnMissingBean(MyLibrary.class)
@EnableConfigurationProperties(MyLibraryProperties.class)
public class MyLibraryAutoConfiguration {
    
    @Autowired
    private MyLibraryProperties properties;
    
    @Bean
    @ConditionalOnMissingBean
    public MyLibrary myLibrary() {
        return new MyLibrary(properties.getUrl(), properties.getTimeout());
    }
    
    @Bean
    @ConditionalOnMissingBean
    public MyLibraryService myLibraryService(MyLibrary myLibrary) {
        return new MyLibraryService(myLibrary);
    }
}
```

### 🔹 Properties Class

**Configuration properties**

```java
@ConfigurationProperties(prefix = "my.library")
public class MyLibraryProperties {
    
    private String url = "http://localhost:8080";
    private int timeout = 5000;
    private boolean enabled = true;
    
    // getters and setters
}
```

### 🔹 spring.factories

**Register auto-configuration**

```properties
# src/main/resources/META-INF/spring.factories
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
com.example.autoconfigure.MyLibraryAutoConfiguration
```

### 🔹 POM Configuration

**Starter dependencies**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0">
    <modelVersion>4.0.0</modelVersion>
    
    <groupId>com.example</groupId>
    <artifactId>my-library-starter</artifactId>
    <version>1.0.0</version>
    
    <properties>
        <java.version>17</java.version>
    </properties>
    
    <dependencies>
        <!-- Core Spring Boot -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-autoconfigure</artifactId>
        </dependency>
        
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-configuration-processor</artifactId>
            <optional>true</optional>
        </dependency>
        
        <!-- Your library dependencies -->
        <dependency>
            <groupId>com.example</groupId>
            <artifactId>my-library</artifactId>
            <version>1.0.0</version>
        </dependency>
    </dependencies>
</project>
```

### 🔹 Configuration Metadata

**Property documentation**

```json
// src/main/resources/META-INF/additional-spring-configuration-metadata.json
{
  "properties": [
    {
      "name": "my.library.url",
      "type": "java.lang.String",
      "description": "URL of the MyLibrary service.",
      "defaultValue": "http://localhost:8080"
    },
    {
      "name": "my.library.timeout",
      "type": "java.lang.Integer",
      "description": "Timeout for MyLibrary operations in milliseconds.",
      "defaultValue": 5000
    }
  ]
}
```

### 🔹 Conditional Annotations

**Control when starter activates**

```java
@Configuration
@ConditionalOnClass(MyLibrary.class)              // Library must be on classpath
@ConditionalOnMissingBean(MyLibrary.class)        // No manual bean defined
@ConditionalOnProperty(
    name = "my.library.enabled", 
    havingValue = "true", 
    matchIfMissing = true)                        // Property enabled or default
@AutoConfigureAfter(DataSourceAutoConfiguration.class)  // Configure after datasource
public class MyLibraryAutoConfiguration {
    // Auto-configuration logic
}
```

### 🔹 Testing the Starter

**Test auto-configuration**

```java
@SpringBootTest
@ImportAutoConfiguration(MyLibraryAutoConfiguration.class)
public class MyLibraryAutoConfigurationTest {
    
    @Autowired(required = false)
    private MyLibrary myLibrary;
    
    @Test
    public void shouldConfigureMyLibrary() {
        assertThat(myLibrary).isNotNull();
    }
}
```

### 🔹 Using the Starter

**In application**

```xml
<dependency>
    <groupId>com.example</groupId>
    <artifactId>my-library-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```

```yaml
# application.yml
my:
  library:
    url: http://api.example.com
    timeout: 10000
```

### 🔹 Best Practices

```java
// Follow naming convention: *-starter
// Include auto-configuration and properties
// Use conditional annotations appropriately
// Provide sensible defaults
// Document configuration properties
// Test auto-configuration thoroughly
// Use @AutoConfigureAfter for ordering
// Make dependencies optional where possible
```

---

## 🎯 Interview One-Liner

> Custom starter creates auto-configuration module with @Configuration, properties class, spring.factories registration; provides opinionated defaults and dependency management for specific functionality.


**Spring is an open-source application framework and inversion of control container for the Java platform.**

```java
// Traditional Java application
public class Application {
    private DatabaseService dbService = new DatabaseService();
    private EmailService emailService = new EmailService();
    
    public void process() {
        // Tight coupling - hard to test and maintain
        dbService.save();
        emailService.send();
    }
}

// Spring-managed application
@Component
public class SpringApplication {
    @Autowired
    private DatabaseService dbService;
    
    @Autowired
    private EmailService emailService;
    
    public void process() {
        // Loose coupling - easy to test and maintain
        dbService.save();
        emailService.send();
    }
}
```

**Key characteristics:**
- **Lightweight**: Minimal overhead, POJO-based
- **Modular**: Can use individual modules as needed
- **Non-invasive**: Doesn't force inheritance from framework classes
- **Comprehensive**: Covers all layers of enterprise applications

### 🔹 Core Modules

```java
// Spring Core Container
- Core: Core utilities, IoC container
- Beans: Bean factory, dependency injection
- Context: Application context, internationalization
- Expression Language (SpEL)

// Data Access/Integration
- JDBC: Database connectivity
- ORM: Object-relational mapping (Hibernate, JPA)
- OXM: Object-XML mapping
- JMS: Java Messaging Service
- Transactions: Transaction management

// Web Layer
- Web: Web utilities
- Web-MVC: Model-View-Controller
- Web-Socket: WebSocket support
- Web-Portlet: Portlet environment

// AOP and Instrumentation
- AOP: Aspect-oriented programming
- Aspects: AOP alliance compliant
- Instrumentation: JVM agent, classloader

// Test
- Test: Testing utilities and mock objects
```

### 🔹 Key Benefits

### 🔹 1. Dependency Injection (DI)

**Loose coupling between components**

```java
// Without DI - tight coupling
public class UserService {
    private UserRepository repository = new UserRepositoryImpl();
    
    public User getUser(int id) {
        return repository.findById(id);
    }
}

// With DI - loose coupling
@Service
public class UserService {
    private final UserRepository repository;
    
    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }
    
    public User getUser(int id) {
        return repository.findById(id);
    }
}
```

### 🔹 2. Aspect-Oriented Programming (AOP)

**Separation of cross-cutting concerns**

```java
@Aspect
@Component
public class LoggingAspect {
    @Before("execution(* com.example.service.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("Executing: " + joinPoint.getSignature());
    }
}

// No logging code in business logic
@Service
public class UserService {
    public User getUser(int id) {
        // Business logic only
        return repository.findById(id);
    }
}
```

### 🔹 3. Transaction Management

**Declarative transaction support**

```java
@Service
public class UserService {
    @Transactional
    public void transferMoney(Account from, Account to, BigDecimal amount) {
        from.debit(amount);
        to.credit(amount);
        // If exception occurs, transaction rolls back automatically
    }
}
```

### 🔹 4. Data Access

**Simplified database operations**

```java
@Repository
public class UserRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public User findById(int id) {
        return jdbcTemplate.queryForObject(
            "SELECT * FROM users WHERE id = ?",
            new Object[]{id},
            (rs, rowNum) -> new User(rs.getInt("id"), rs.getString("name"))
        );
    }
}
```

### 🔹 5. MVC Framework

**Clean web application architecture**

```java
@Controller
public class UserController {
    @Autowired
    private UserService userService;
    
    @GetMapping("/users/{id}")
    public String getUser(@PathVariable int id, Model model) {
        User user = userService.getUser(id);
        model.addAttribute("user", user);
        return "user-view";
    }
}
```

### 🔹 6. Testing Support

**Easy unit and integration testing**

```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;
    
    @MockBean
    private UserRepository userRepository;
    
    @Test
    public void testGetUser() {
        // Easy mocking and testing
        when(userRepository.findById(1)).thenReturn(new User(1, "John"));
        User user = userService.getUser(1);
        assertEquals("John", user.getName());
    }
}
```

### 🔹 7. Enterprise Features

```java
// Caching
@Cacheable("users")
public User getUser(int id) {
    return repository.findById(id);
}

// Scheduling
@Service
public class ScheduledService {
    @Scheduled(fixedRate = 5000)
    public void doSomething() {
        // Runs every 5 seconds
    }
}

// Security
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // Security configuration
}
```

### 🔹 Advantages Over Other Frameworks

| Feature | Spring | EJB 2.x | Plain Java |
|---------|--------|---------|------------|
| **Complexity** | Low | High | Medium |
| **Testability** | High | Low | Medium |
| **Performance** | High | Medium | High |
| **Learning Curve** | Medium | High | Low |
| **Flexibility** | High | Low | High |

### 🔹 When to Use Spring

```java
// Use Spring for:
- Enterprise applications
- Web applications (Spring MVC)
- Microservices
- Batch processing
- Integration with other frameworks
- Applications requiring DI and AOP

// Consider alternatives for:
- Simple command-line applications
- High-performance requirements (may have slight overhead)
- Applications where you want full control
```

### 🔹 Best Practices

```java
// Use constructor injection over field injection
@Service
public class UserService {
    private final UserRepository repository;
    
    public UserService(UserRepository repository) {  // Preferred
        this.repository = repository;
    }
}

// Use @Configuration for bean definitions
@Configuration
public class AppConfig {
    @Bean
    public DataSource dataSource() {
        return new HikariDataSource();
    }
}

// Follow naming conventions
// Use meaningful package structure
// Leverage Spring Boot for new applications
```

---

## 🎯 Interview One-Liner

> Spring Framework provides DI, AOP, transaction management, MVC; lightweight, modular, POJO-based; simplifies enterprise Java development with comprehensive features and testing support.

---

## ✅ 52. What is Dependency Injection?

**Dependency Injection is a design pattern where objects receive their dependencies from external sources rather than creating them internally, promoting loose coupling and easier testing.**

### 🔹 Traditional Approach (Tight Coupling)

**Objects create their own dependencies**

```java
public class UserService {
    private UserRepository repository;
    
    public UserService() {
        this.repository = new UserRepositoryImpl();  // Tight coupling
    }
    
    public User getUser(int id) {
        return repository.findById(id);
    }
}

// Problems:
// - Hard to test (can't mock UserRepositoryImpl)
// - Hard to change implementation
// - Creates unnecessary dependencies
// - Violates Single Responsibility Principle
```

### 🔹 Dependency Injection Approach

**Dependencies are injected from outside**

```java
public class UserService {
    private final UserRepository repository;
    
    // Constructor injection
    public UserService(UserRepository repository) {
        this.repository = repository;
    }
    
    public User getUser(int id) {
        return repository.findById(id);
    }
}

// Usage with Spring
@Configuration
public class AppConfig {
    @Bean
    public UserRepository userRepository() {
        return new UserRepositoryImpl();
    }
    
    @Bean
    public UserService userService(UserRepository repository) {
        return new UserService(repository);
    }
}
```

### 🔹 Types of Dependency Injection

### 🔹 1. Constructor Injection

**Dependencies provided through constructor**

```java
@Service
public class OrderService {
    private final UserService userService;
    private final PaymentService paymentService;
    
    @Autowired  // Optional in Spring 4.3+
    public OrderService(UserService userService, PaymentService paymentService) {
        this.userService = userService;
        this.paymentService = paymentService;
    }
}

// Advantages:
// - Immutable dependencies
// - Clear contract (required dependencies)
// - Easy testing
// - Prevents circular dependencies
```

### 🔹 2. Setter Injection

**Dependencies provided through setter methods**

```java
@Service
public class EmailService {
    private EmailProvider provider;
    
    @Autowired
    public void setEmailProvider(EmailProvider provider) {
        this.provider = provider;
    }
    
    public void sendEmail(String to, String subject, String body) {
        provider.send(to, subject, body);
    }
}

// Advantages:
// - Optional dependencies
// - Can change dependencies at runtime
// - Resolves circular dependencies
```

### 🔹 3. Field Injection

**Dependencies injected directly into fields**

```java
@Service
public class NotificationService {
    @Autowired
    private EmailService emailService;
    
    @Autowired
    private SmsService smsService;
    
    public void sendNotification(String message) {
        emailService.send(message);
        smsService.send(message);
    }
}

// Advantages:
// - Less boilerplate code
// - Easy to use

// Disadvantages:
// - Harder to test
// - Hidden dependencies
// - Cannot make fields final
```

### 🔹 Benefits of Dependency Injection

### 🔹 1. Loose Coupling

```java
// Easy to switch implementations
@Configuration
public class TestConfig {
    @Bean
    public UserRepository userRepository() {
        return new MockUserRepository();  // For testing
    }
}

@Configuration
public class ProdConfig {
    @Bean
    public UserRepository userRepository() {
        return new JpaUserRepository();  // For production
    }
}
```

### 🔹 2. Easier Testing

```java
@RunWith(SpringRunner.class)
public class UserServiceTest {
    private UserService userService;
    
    @Before
    public void setUp() {
        UserRepository mockRepo = mock(UserRepository.class);
        userService = new UserService(mockRepo);  // Easy mocking
    }
    
    @Test
    public void testGetUser() {
        when(mockRepo.findById(1)).thenReturn(new User(1, "John"));
        User user = userService.getUser(1);
        assertEquals("John", user.getName());
    }
}
```

### 🔹 3. Better Maintainability

```java
// Changes are localized
public class ImprovedUserService {
    private final UserRepository repository;
    private final CacheService cache;        // New dependency
    private final AuditService audit;        // New dependency
    
    public ImprovedUserService(UserRepository repository, 
                              CacheService cache, 
                              AuditService audit) {
        this.repository = repository;
        this.cache = cache;
        this.audit = audit;
    }
}
```

### 🔹 4. Inversion of Control

**Framework controls object creation and wiring**

```java
// Spring IoC container manages dependencies
ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

// Get fully configured bean
UserService service = context.getBean(UserService.class);
// All dependencies automatically injected
```

### 🔹 DI vs Service Locator

```java
// Service Locator (anti-pattern)
public class ServiceLocator {
    public static UserRepository getUserRepository() {
        return new UserRepositoryImpl();
    }
}

// Still tight coupling
public class BadUserService {
    private UserRepository repository = ServiceLocator.getUserRepository();
}

// Dependency Injection (better)
public class GoodUserService {
    private final UserRepository repository;
    
    public GoodUserService(UserRepository repository) {
        this.repository = repository;
    }
}
```

### 🔹 Common Pitfalls

```java
// Circular dependencies
@Service
public class A {
    @Autowired
    private B b;  // A depends on B
}

@Service
public class B {
    @Autowired
    private A a;  // B depends on A - circular!
}

// Solution: Use setter injection or redesign
@Service
public class A {
    private B b;
    
    @Autowired
    public void setB(B b) {
        this.b = b;
    }
}
```

### 🔹 Best Practices

```java
// Prefer constructor injection
// Use field injection sparingly
// Make injected fields final when possible
// Avoid circular dependencies
// Use interfaces for dependencies
// Leverage Spring profiles for different environments
// Document dependencies clearly
```

---

## 🎯 Interview One-Liner

> DI injects dependencies from outside instead of creating them; promotes loose coupling, testability; types: constructor (preferred), setter, field; Spring IoC container manages injection automatically.

---

## ✅ 53. What is IoC (Inversion of Control)?

**Inversion of Control is a design principle where the control of object creation and dependency management is transferred from the application code to an external container or framework.**

### 🔹 Traditional Control Flow

**Application controls object lifecycle**

```java
public class TraditionalApp {
    public static void main(String[] args) {
        // Application creates and manages objects
        DatabaseConnection db = new DatabaseConnection("localhost", 3306);
        UserRepository repo = new UserRepository(db);
        UserService service = new UserService(repo);
        UserController controller = new UserController(service);
        
        // Application manages lifecycle
        controller.handleRequest();
        
        // Application cleans up
        db.close();
    }
}

// Problems:
// - Tight coupling
// - Hard to test
// - Boilerplate code
// - Manual dependency management
```

### 🔹 IoC with Spring

**Framework controls object lifecycle**

```java
@SpringBootApplication
public class SpringApp {
    public static void main(String[] args) {
        // Spring creates and manages all objects
        ApplicationContext context = SpringApplication.run(SpringApp.class, args);
        
        // Get fully configured bean
        UserController controller = context.getBean(UserController.class);
        controller.handleRequest();
        
        // Spring manages lifecycle automatically
    }
}

// Spring configuration
@Configuration
public class AppConfig {
    @Bean
    public DatabaseConnection databaseConnection() {
        return new DatabaseConnection("localhost", 3306);
    }
    
    @Bean
    public UserRepository userRepository(DatabaseConnection db) {
        return new UserRepository(db);
    }
    
    @Bean
    public UserService userService(UserRepository repo) {
        return new UserService(repo);
    }
    
    @Bean
    public UserController userController(UserService service) {
        return new UserController(service);
    }
}
```

### 🔹 IoC Container

**Central registry for managing objects**

```java
// ApplicationContext is the IoC container
ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

// Container manages:
// - Object creation
// - Dependency injection
// - Lifecycle management
// - Configuration

// Get beans by type
UserService service = context.getBean(UserService.class);

// Get beans by name
UserService service = (UserService) context.getBean("userService");

// Check if bean exists
boolean exists = context.containsBean("userService");
```

### 🔹 Types of IoC

### 🔹 1. Dependency Injection (DI)

**Most common form of IoC**

```java
@Service
public class OrderService {
    private final PaymentService paymentService;
    
    // IoC container injects dependency
    @Autowired
    public OrderService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
}
```

### 🔹 2. Service Locator

**Alternative to DI (less preferred)**

```java
public class ServiceLocator {
    private static ApplicationContext context;
    
    public static void setContext(ApplicationContext context) {
        ServiceLocator.context = context;
    }
    
    public static <T> T getService(Class<T> clazz) {
        return context.getBean(clazz);
    }
}

@Service
public class OrderService {
    public void processOrder() {
        // Lookup service (still coupled to locator)
        PaymentService payment = ServiceLocator.getService(PaymentService.class);
        payment.process();
    }
}
```

### 🔹 3. Template Method Pattern

**Framework controls flow, application provides details**

```java
@Repository
public class UserRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public User findById(int id) {
        // IoC: JdbcTemplate controls how, we provide what
        return jdbcTemplate.queryForObject(
            "SELECT * FROM users WHERE id = ?",
            new Object[]{id},
            (rs, rowNum) -> new User(rs.getInt("id"), rs.getString("name"))
        );
    }
}
```

### 🔹 4. Factory Pattern with IoC

```java
@Configuration
public class FactoryConfig {
    @Bean
    public PaymentFactory paymentFactory() {
        return new PaymentFactory();
    }
    
    @Bean
    @Scope("prototype")
    public PaymentService paymentService(PaymentFactory factory, String type) {
        return factory.createPaymentService(type);  // IoC through factory
    }
}
```

### 🔹 Benefits of IoC

### 🔹 1. Loose Coupling

```java
// Easy to swap implementations
@Configuration
public class TestConfig {
    @Bean
    public PaymentService paymentService() {
        return new MockPaymentService();  // Test implementation
    }
}

@Configuration
public class ProdConfig {
    @Bean
    public PaymentService paymentService() {
        return new StripePaymentService();  // Production implementation
    }
}
```

### 🔹 2. Testability

```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {
    @Autowired
    private OrderService orderService;
    
    @MockBean
    private PaymentService paymentService;  // IoC enables easy mocking
    
    @Test
    public void testOrderProcessing() {
        when(paymentService.process(any())).thenReturn(true);
        boolean result = orderService.processOrder(new Order());
        assertTrue(result);
    }
}
```

### 🔹 3. Separation of Concerns

```java
// Business logic focuses on business rules
@Service
public class OrderService {
    public void processOrder(Order order) {
        // Business logic only
        validateOrder(order);
        calculateTotal(order);
        saveOrder(order);
        // IoC container handles infrastructure concerns
    }
}
```

### 🔹 4. Lifecycle Management

```java
@Component
public class DatabaseConnection implements InitializingBean, DisposableBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        // IoC container calls this after bean creation
        connectToDatabase();
    }
    
    @Override
    public void destroy() throws Exception {
        // IoC container calls this before bean destruction
        closeConnection();
    }
}
```

### 🔹 IoC vs Traditional Programming

| Aspect | Traditional | IoC |
|--------|-------------|-----|
| **Control** | Application controls flow | Framework controls flow |
| **Dependencies** | Objects create dependencies | Dependencies injected |
| **Testing** | Hard to mock dependencies | Easy with DI |
| **Flexibility** | Rigid, hard to change | Flexible, configurable |
| **Boilerplate** | Lots of setup code | Minimal setup code |

### 🔹 Common IoC Containers

```java
// Spring ApplicationContext
ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

// Google Guice
Injector injector = Guice.createInjector(new AppModule());
UserService service = injector.getInstance(UserService.class);

// CDI (Java EE)
@ApplicationScoped
public class UserService {
    @Inject
    private UserRepository repository;
}
```

### 🔹 Best Practices

```java
// Use constructor injection for required dependencies
// Use setter injection for optional dependencies
// Avoid field injection in production code
// Leverage Spring profiles for different environments
// Use @Qualifier for multiple beans of same type
// Document bean dependencies
// Test with Spring test support
```

---

## 🎯 Interview One-Liner

> IoC transfers control of object creation/management from application to framework; Spring IoC container manages beans, dependencies, lifecycle; enables loose coupling, testability, separation of concerns.

---

## ✅ 54. Types of Dependency Injection

**Spring supports three types of dependency injection: Constructor Injection (recommended), Setter Injection, and Field Injection, each with different use cases and trade-offs.**

### 🔹 Constructor Injection

**Dependencies provided through constructor parameters**

```java
@Service
public class OrderService {
    private final UserService userService;
    private final PaymentService paymentService;
    private final EmailService emailService;
    
    // All dependencies injected at creation time
    @Autowired
    public OrderService(UserService userService, 
                       PaymentService paymentService,
                       EmailService emailService) {
        this.userService = userService;
        this.paymentService = paymentService;
        this.emailService = emailService;
    }
    
    public void processOrder(Order order) {
        User user = userService.getUser(order.getUserId());
        boolean paymentSuccess = paymentService.process(order);
        if (paymentSuccess) {
            emailService.sendConfirmation(user.getEmail());
        }
    }
}
```

**Advantages:**
- **Immutability**: Dependencies cannot be changed after construction
- **Clear contract**: Required dependencies are obvious
- **Testability**: Easy to create test instances
- **Fail-fast**: Application fails to start if dependencies missing
- **Thread-safety**: No synchronization needed for dependencies

**Disadvantages:**
- **Circular dependencies**: Cannot resolve circular references
- **Verbose**: Many parameters for classes with many dependencies

### 🔹 Setter Injection

**Dependencies provided through setter methods**

```java
@Service
public class NotificationService {
    private EmailService emailService;
    private SmsService smsService;
    
    // Optional dependency
    @Autowired(required = false)
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }
    
    // Required dependency
    @Autowired
    public void setSmsService(SmsService smsService) {
        this.smsService = smsService;
    }
    
    public void sendNotification(String message, NotificationType type) {
        switch (type) {
            case EMAIL:
                if (emailService != null) {
                    emailService.send(message);
                }
                break;
            case SMS:
                smsService.send(message);
                break;
            case BOTH:
                if (emailService != null) {
                    emailService.send(message);
                }
                smsService.send(message);
                break;
        }
    }
}
```

**Advantages:**
- **Optional dependencies**: Can work without some dependencies
- **Circular dependency resolution**: Can resolve circular references
- **Runtime reconfiguration**: Dependencies can be changed
- **Inheritance friendly**: Subclasses can override setters

**Disadvantages:**
- **Null checks needed**: Must check for null dependencies
- **Incomplete initialization**: Object may be in inconsistent state
- **Thread-safety concerns**: May need synchronization

### 🔹 Field Injection

**Dependencies injected directly into fields**

```java
@Service
public class ReportService {
    @Autowired
    private DataService dataService;
    
    @Autowired
    private PdfGenerator pdfGenerator;
    
    @Autowired
    private EmailService emailService;
    
    public void generateAndSendReport(String userId) {
        List<Data> data = dataService.getUserData(userId);
        byte[] pdf = pdfGenerator.generatePdf(data);
        emailService.sendAttachment(userId, "Report", pdf);
    }
}
```

**Advantages:**
- **Less code**: No constructor or setter boilerplate
- **Clean**: No injection methods cluttering the class
- **Easy**: Just add @Autowired annotation

**Disadvantages:**
- **Hidden dependencies**: Not clear from constructor what is needed
- **Hard to test**: Cannot create instances without Spring
- **Final fields impossible**: Cannot make dependencies final
- **Tight coupling**: Depends on Spring's reflection-based injection

### 🔹 Method Injection

**Dependencies injected through arbitrary methods**

```java
@Service
public class ComplexService {
    private DependencyA depA;
    private DependencyB depB;
    
    @Autowired
    public void injectDependencies(DependencyA depA, DependencyB depB) {
        this.depA = depA;
        this.depB = depB;
    }
    
    // Alternative: multiple methods
    @Autowired
    public void setDependencyA(DependencyA depA) {
        this.depA = depA;
    }
    
    @Autowired
    public void setDependencyB(DependencyB depB) {
        this.depB = depB;
    }
}
```

### 🔹 Comparison Table

| Type | Constructor | Setter | Field |
|------|-------------|--------|-------|
| **Immutability** | ✅ | ❌ | ❌ |
| **Optional deps** | ❌ | ✅ | ✅ |
| **Circular deps** | ❌ | ✅ | ✅ |
| **Testability** | ✅ | ⚠️ | ❌ |
| **Thread-safety** | ✅ | ⚠️ | ⚠️ |
| **Code clarity** | ✅ | ✅ | ❌ |
| **Spring version** | All | All | 2.5+ |

### 🔹 When to Use Each Type

```java
// Use Constructor Injection for:
// - Required dependencies
// - Immutable objects
// - Better testability
// - Clear component contracts

public class PaymentProcessor {
    private final PaymentGateway gateway;
    private final TransactionLogger logger;
    
    public PaymentProcessor(PaymentGateway gateway, TransactionLogger logger) {
        this.gateway = gateway;
        this.logger = logger;
    }
}

// Use Setter Injection for:
// - Optional dependencies
// - Circular dependencies
// - Runtime reconfiguration needs

public class CacheManager {
    private CacheProvider cache;
    
    @Autowired(required = false)
    public void setCache(CacheProvider cache) {
        this.cache = cache;
    }
    
    public void put(String key, Object value) {
        if (cache != null) {
            cache.put(key, value);
        }
    }
}

// Use Field Injection for:
// - Simple cases
// - Rapid prototyping
// - When constructor would be too verbose

@Component
public class SimpleService {
    @Autowired
    private SimpleRepository repository;
    
    public void doWork() {
        repository.save(new Entity());
    }
}
```

### 🔹 Spring Boot Improvements

```java
// Spring 4.3+: @Autowired optional for single constructor
@Service
public class UserService {
    private final UserRepository repository;
    
    public UserService(UserRepository repository) {  // @Autowired not needed
        this.repository = repository;
    }
}

// @Required for mandatory setters (deprecated in 5.1)
@Service
public class LegacyService {
    private DataSource dataSource;
    
    @Required  // Throws exception if not set
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
```

### 🔹 Best Practices

```java
// Prefer constructor injection for required dependencies
// Use setter/field injection only when necessary
// Make injected fields final when possible
// Use @Autowired(required = false) for optional dependencies
// Avoid field injection in production code
// Document why you're using a particular injection type
// Consider using Lombok's @RequiredArgsConstructor for constructor injection
```

---

## 🎯 Interview One-Liner

> Constructor injection (preferred): immutable, required deps, clear contract; Setter injection: optional deps, circular deps; Field injection: simple but hides dependencies; choose based on requirements.

---

## ✅ 55. @Component vs @Service vs @Repository

**@Component, @Service, and @Repository are Spring stereotypes that mark classes as Spring-managed beans, with @Service and @Repository providing additional semantic meaning and features.**

### 🔹 @Component

**Generic stereotype for any Spring-managed component**

```java
@Component
public class UserValidator {
    public boolean isValid(User user) {
        return user != null && user.getEmail() != null;
    }
}

@Component
public class PasswordEncoder {
    public String encode(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}

// Usage
@Controller
public class UserController {
    @Autowired
    private UserValidator validator;
    
    @Autowired
    private PasswordEncoder encoder;
    
    @PostMapping("/register")
    public String register(User user) {
        if (!validator.isValid(user)) {
            return "error";
        }
        user.setPassword(encoder.encode(user.getPassword()));
        // Save user
        return "success";
    }
}
```

**Characteristics:**
- **Generic**: Can be used for any component
- **Basic**: No additional features
- **Scanning**: Included in component scanning
- **Use case**: Utility classes, validators, processors

### 🔹 @Service

**Specialized stereotype for service layer classes**

```java
@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private EmailService emailService;
    
    public User registerUser(User user) {
        // Business logic
        User savedUser = userRepository.save(user);
        emailService.sendWelcomeEmail(savedUser);
        return savedUser;
    }
    
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
    
    @Transactional
    public void updateUser(User user) {
        // Transactional business logic
        userRepository.save(user);
    }
}
```

**Characteristics:**
- **Semantic**: Indicates service layer component
- **Transactional**: Often used with @Transactional
- **Business logic**: Contains business rules and workflows
- **Use case**: Business services, application services

### 🔹 @Repository

**Specialized stereotype for data access layer**

```java
@Repository
public class UserRepositoryImpl implements UserRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public User save(User user) {
        // Data access logic
        String sql = "INSERT INTO users (name, email) VALUES (?, ?)";
        jdbcTemplate.update(sql, user.getName(), user.getEmail());
        return user;
    }
    
    @Override
    public Optional<User> findById(Long id) {
        try {
            String sql = "SELECT * FROM users WHERE id = ?";
            User user = jdbcTemplate.queryForObject(sql, new Object[]{id}, 
                (rs, rowNum) -> new User(rs.getInt("id"), rs.getString("name"), rs.getString("email")));
            return Optional.of(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
    
    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, (rs, rowNum) -> 
            new User(rs.getInt("id"), rs.getString("name"), rs.getString("email")));
    }
}
```

**Characteristics:**
- **Semantic**: Indicates data access component
- **Exception translation**: Automatic translation of SQLExceptions to DataAccessExceptions
- **Use case**: DAO classes, repository implementations

### 🔹 Key Differences

| Annotation | Purpose | Features | Example |
|------------|---------|----------|---------|
| **@Component** | Generic component | Basic bean | Validators, utilities |
| **@Service** | Business service | Transaction support | UserService, OrderService |
| **@Repository** | Data access | Exception translation | UserRepository, ProductDao |

### 🔹 Additional Stereotypes

```java
// @Controller - Web MVC controller
@Controller
public class UserController {
    // Handles HTTP requests
}

// @RestController - REST controller (@Controller + @ResponseBody)
@RestController
public class ApiController {
    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.getAllUsers();
    }
}

// @Configuration - Configuration class
@Configuration
public class AppConfig {
    @Bean
    public DataSource dataSource() {
        return new HikariDataSource();
    }
}
```

### 🔹 Component Scanning

```java
// Enable component scanning
@Configuration
@ComponentScan(basePackages = "com.example")
public class AppConfig {
    // Scans for @Component, @Service, @Repository, @Controller, @RestController
}

// Custom component scanning
@Configuration
@ComponentScan(
    basePackages = "com.example",
    includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Service.class),
    excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Controller.class)
)
public class CustomScanConfig {
    // Only scans @Service, excludes @Controller
}
```

### 🔹 When to Use Each

```java
// Use @Component for:
// - Utility classes
// - Helper classes
// - Custom processors
// - Classes that don't fit other stereotypes

@Component
public class DateFormatter {
    public String format(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }
}

// Use @Service for:
// - Business logic classes
// - Service layer components
// - Classes with transactional methods
// - Workflow orchestration

@Service
public class OrderProcessingService {
    @Transactional
    public void processOrder(Order order) {
        validateOrder(order);
        calculateTotal(order);
        saveOrder(order);
        sendConfirmation(order);
    }
}

// Use @Repository for:
// - Data access objects
// - Repository implementations
// - Classes that interact with databases
// - Classes needing exception translation

@Repository
public class JpaUserRepository implements UserRepository {
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public User save(User user) {
        entityManager.persist(user);
        return user;
    }
}
```

### 🔹 Best Practices

```java
// Use appropriate stereotype for semantic clarity
// Prefer @Service for business logic
// Use @Repository for data access
// Use @Component for everything else
// Combine with other annotations as needed
// Follow naming conventions (UserService, UserRepository)
// Document the purpose of each component
```

---

## 🎯 Interview One-Liner

> @Component: generic bean; @Service: business logic, often transactional; @Repository: data access, exception translation; use appropriate stereotype for semantic meaning and features.

---

## ✅ 56. @Autowired vs @Inject vs @Resource

**@Autowired (Spring), @Inject (JSR-330), and @Resource (JSR-250) are dependency injection annotations with different precedence rules and compatibility.**

### 🔹 @Autowired (Spring-specific)

**Spring's annotation for dependency injection**

```java
@Service
public class OrderService {
    // Field injection
    @Autowired
    private PaymentService paymentService;
    
    // Constructor injection
    private final UserService userService;
    
    @Autowired
    public OrderService(UserService userService) {
        this.userService = userService;
    }
    
    // Setter injection
    private EmailService emailService;
    
    @Autowired
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }
    
    // Method injection
    @Autowired
    public void configureServices(PaymentService payment, AuditService audit) {
        // Inject multiple dependencies
    }
}
```

**Characteristics:**
- **Spring-specific**: Only works with Spring framework
- **Flexible**: Field, constructor, setter, method injection
- **Required by default**: Throws exception if dependency not found
- **Type-based**: Matches by type, then by name if qualifiers used

### 🔹 @Inject (JSR-330)

**Standard Java dependency injection annotation**

```java
import javax.inject.Inject;
import javax.inject.Named;

@Service
public class ProductService {
    // Field injection
    @Inject
    private ProductRepository productRepository;
    
    // Constructor injection
    private final CategoryService categoryService;
    
    @Inject
    public ProductService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    
    // Qualifier for multiple beans
    @Inject
    @Named("primaryInventory")
    private InventoryService inventoryService;
}
```

**Characteristics:**
- **Standard**: Part of JSR-330, works with Spring, Guice, etc.
- **Similar to @Autowired**: Same injection points
- **Always required**: No 'required' attribute
- **Qualifier needed**: Use @Named for disambiguation

### 🔹 @Resource (JSR-250)

**Java EE annotation for resource injection**

```java
import javax.annotation.Resource;

@Service
public class ReportService {
    // Field injection by name
    @Resource(name = "reportRepository")
    private ReportRepository reportRepository;
    
    // Field injection by type
    @Resource
    private PdfGenerator pdfGenerator;
    
    // Setter injection
    private EmailService emailService;
    
    @Resource
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }
}
```

**Characteristics:**
- **JSR-250**: Part of Java EE, supported by Spring
- **Name-based**: Prefers name over type matching
- **Resource-oriented**: Designed for resources (JDBC, JMS, etc.)
- **Always required**: No optional injection

### 🔹 Injection Precedence

**Order when multiple candidates exist**

```java
// Multiple PaymentService implementations
@Service
public class CreditCardPayment implements PaymentService {}

@Service
public class PayPalPayment implements PaymentService {}

// @Autowired - by type, fails if multiple
@Service
public class OrderService {
    @Autowired
    private PaymentService paymentService;  // ERROR: multiple beans
}

// @Autowired with @Qualifier
@Service
public class OrderService {
    @Autowired
    @Qualifier("creditCardPayment")
    private PaymentService paymentService;  // OK
}

// @Inject with @Named
@Service
public class OrderService {
    @Inject
    @Named("creditCardPayment")
    private PaymentService paymentService;  // OK
}

// @Resource - by name
@Service
public class OrderService {
    @Resource(name = "creditCardPayment")
    private PaymentService paymentService;  // OK
}
```

### 🔹 Detailed Comparison

| Feature | @Autowired | @Inject | @Resource |
|---------|------------|---------|-----------|
| **Source** | Spring | JSR-330 | JSR-250 |
| **Package** | org.springframework | javax.inject | javax.annotation |
| **Required** | Configurable | Always | Always |
| **Injection** | Type > Name | Type > Name | Name > Type |
| **Qualifier** | @Qualifier | @Named | name attribute |
| **Compatibility** | Spring only | Multiple DI frameworks | Java EE |

### 🔹 Practical Examples

```java
// @Autowired examples
@Configuration
public class AutowiredConfig {
    @Bean
    public PaymentService creditCardPayment() {
        return new CreditCardPayment();
    }
    
    @Bean
    public PaymentService paypalPayment() {
        return new PayPalPayment();
    }
}

@Service
public class PaymentProcessor {
    @Autowired
    @Qualifier("creditCardPayment")  // Specify which bean
    private PaymentService primaryPayment;
    
    @Autowired
    private List<PaymentService> allPayments;  // Inject all implementations
}

// @Inject examples
@Service
public class InventoryService {
    @Inject
    @Named("primaryInventory")
    private InventoryRepository repository;
    
    @Inject
    private Provider<InventoryRepository> repoProvider;  // Lazy injection
}

// @Resource examples
@Service
public class DataService {
    @Resource(name = "dataSource")  // JNDI lookup
    private DataSource dataSource;
    
    @Resource
    private JdbcTemplate jdbcTemplate;  // By type
}
```

### 🔹 When to Use Each

```java
// Use @Autowired for:
// - Spring-specific applications
// - Flexible injection requirements
// - Optional dependencies (@Autowired(required = false))
// - Collection injection (List, Map, etc.)

@Autowired
public void setOptionalService(@Autowired(required = false) OptionalService service) {
    this.service = service;
}

// Use @Inject for:
// - Framework-agnostic code
// - JSR-330 compliance
// - Working with multiple DI containers
// - Provider injection for lazy loading

@Inject
private Provider<ExpensiveService> expensiveServiceProvider;

// Use @Resource for:
// - Java EE applications
// - Resource injection (DataSource, JMS, etc.)
// - Name-based injection preference
// - Legacy code migration

@Resource(name = "jdbc/myDataSource")
private DataSource dataSource;
```

### 🔹 Best Practices

```java
// Prefer constructor injection over field injection
// Use @Autowired for Spring applications
// Use @Inject for framework-agnostic code
// Use @Resource for resource injection
// Use qualifiers to resolve ambiguity
// Avoid field injection in production code
// Document injection requirements
```

---

## 🎯 Interview One-Liner

> @Autowired (Spring): flexible, type-based; @Inject (JSR-330): standard, similar to @Autowired; @Resource (JSR-250): name-based, for resources; choose based on framework requirements and injection style.

---

## ✅ 57. What is Bean in Spring?

**A Bean is an object that is instantiated, assembled, and managed by the Spring IoC container, representing a component in the application.**

### 🔹 Bean Definition

**Spring-managed object with metadata**

```java
// Bean defined via annotation
@Service
public class UserService {
    // Bean class
}

// Bean defined via XML (legacy)
<bean id="userService" class="com.example.UserService">
    <property name="userRepository" ref="userRepository"/>
</bean>

// Bean defined via Java config
@Configuration
public class AppConfig {
    @Bean
    public UserService userService() {
        return new UserService();
    }
}
```

### 🔹 Bean Characteristics

```java
// Every bean has:
- Class: The actual Java class
- Name/ID: Unique identifier
- Scope: Lifecycle management
- Dependencies: Other beans it needs
- Configuration: How it should be created/configured

@Service("userManager")  // Custom name
public class UserService implements InitializingBean, DisposableBean {
    
    @Autowired
    private UserRepository repository;
    
    @PostConstruct  // Lifecycle callback
    public void init() {
        System.out.println("Bean initialized");
    }
    
    @PreDestroy  // Lifecycle callback
    public void destroy() {
        System.out.println("Bean destroyed");
    }
}
```

### 🔹 Bean Instantiation

**How Spring creates bean instances**

```java
// 1. Constructor instantiation (default)
@Service
public class DefaultService {
    public DefaultService() {
        // Default constructor
    }
}

// 2. Static factory method
@Configuration
public class FactoryConfig {
    @Bean
    public DataSource dataSource() {
        return DataSourceFactory.createDataSource();  // Static factory
    }
}

// 3. Instance factory method
@Configuration
public class InstanceFactoryConfig {
    @Bean
    public FactoryBean factoryBean() {
        return new MyFactoryBean();
    }
    
    @Bean
    public Product product(FactoryBean factory) {
        return factory.createProduct();  // Instance factory
    }
}
```

### 🔹 Bean Naming

**How beans are identified**

```java
// 1. Default naming (class name with first letter lowercase)
@Service
public class UserService {}  // Bean name: "userService"

// 2. Explicit naming
@Service("userManager")
public class UserService {}  // Bean name: "userManager"

// 3. @Bean naming
@Configuration
public class Config {
    @Bean(name = "myService")
    public UserService userService() {
        return new UserService();
    }
    
    @Bean({"service", "userService"})  // Multiple names
    public UserService anotherService() {
        return new UserService();
    }
}
```

### 🔹 Bean Retrieval

**Accessing beans from container**

```java
ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

// By type
UserService service = context.getBean(UserService.class);

// By name
UserService service = (UserService) context.getBean("userService");

// By name and type
UserService service = context.getBean("userService", UserService.class);

// Get all beans of type
Map<String, UserService> services = context.getBeansOfType(UserService.class);

// Check if bean exists
boolean exists = context.containsBean("userService");
```

### 🔹 Bean Dependencies

**How beans reference each other**

```java
@Service
public class OrderService {
    @Autowired
    private UserService userService;  // Direct dependency
    
    @Autowired
    private List<PaymentService> paymentServices;  // Collection dependency
    
    @Autowired
    private Map<String, NotificationService> notificationServices;  // Map dependency
}

// Circular dependency (avoid)
@Service
public class A {
    @Autowired
    private B b;
}

@Service
public class B {
    @Autowired
    private A a;  // Circular reference
}
```

### 🔹 Bean Configuration

**Different ways to configure beans**

```java
// 1. Annotation-based (modern)
@Service
public class UserService {
    @Value("${app.user.default-role}")
    private String defaultRole;
}

// 2. Java-based configuration
@Configuration
public class AppConfig {
    @Bean
    @Scope("prototype")
    public UserService userService() {
        UserService service = new UserService();
        service.setDefaultRole("USER");
        return service;
    }
}

// 3. XML-based configuration (legacy)
<bean id="userService" class="com.example.UserService" scope="prototype">
    <property name="defaultRole" value="USER"/>
</bean>
```

### 🔹 Bean Lifecycle

**Bean creation and destruction phases**

```java
// 1. Instantiation
// 2. Population of properties
// 3. BeanNameAware.setBeanName()
// 4. BeanFactoryAware.setBeanFactory()
// 5. ApplicationContextAware.setApplicationContext()
// 6. InitializingBean.afterPropertiesSet()
// 7. Custom init method
// 8. Bean is ready to use
// 9. DisposableBean.destroy()
// 10. Custom destroy method

@Component
public class LifecycleBean implements 
    BeanNameAware, BeanFactoryAware, ApplicationContextAware, 
    InitializingBean, DisposableBean {
    
    @Override
    public void setBeanName(String name) {
        System.out.println("Bean name: " + name);
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Bean initialized");
    }
    
    @Override
    public void destroy() throws Exception {
        System.out.println("Bean destroyed");
    }
}
```

### 🔹 Special Bean Types

```java
// FactoryBean - creates other beans
@Component
public class ConnectionFactoryBean implements FactoryBean<Connection> {
    @Override
    public Connection getObject() throws Exception {
        return createConnection();
    }
    
    @Override
    public Class<?> getObjectType() {
        return Connection.class;
    }
}

// BeanPostProcessor - modifies beans after creation
@Component
public class CustomBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        // Modify bean after initialization
        return bean;
    }
}
```

### 🔹 Bean Scopes

**Bean lifecycle management**

```java
// Singleton (default) - one instance per container
@Service
@Scope("singleton")
public class SingletonService {}

// Prototype - new instance each time
@Service
@Scope("prototype")
public class PrototypeService {}

// Request - one instance per HTTP request
@Service
@Scope("request")
public class RequestService {}

// Session - one instance per HTTP session
@Service
@Scope("session")
public class SessionService {}

// Application - one instance per ServletContext
@Service
@Scope("application")
public class ApplicationService {}
```

### 🔹 Best Practices

```java
// Use meaningful bean names
// Prefer annotation-based configuration
// Use appropriate scopes
// Avoid circular dependencies
// Implement lifecycle interfaces when needed
// Use @PostConstruct/@PreDestroy for custom initialization
// Document bean purposes and dependencies
```

---

## 🎯 Interview One-Liner

> Bean is Spring-managed object with lifecycle, dependencies, configuration; created by IoC container; has name, scope, dependencies; supports various instantiation methods and lifecycle callbacks.

---

## ✅ 58. Bean scopes in Spring

**Bean scopes define the lifecycle and visibility of Spring beans, determining how long a bean instance lives and how it's shared between requests.**

### 🔹 Singleton Scope (Default)

**One instance per Spring container**

```java
@Service
@Scope("singleton")  // Default scope
public class UserService {
    private int counter = 0;
    
    public void increment() {
        counter++;
        System.out.println("Counter: " + counter);
    }
}

// Usage
UserService service1 = context.getBean(UserService.class);
UserService service2 = context.getBean(UserService.class);

service1.increment();  // Counter: 1
service2.increment();  // Counter: 2 (same instance)

System.out.println(service1 == service2);  // true
```

**Characteristics:**
- **One instance**: Shared across entire application
- **Thread-safety**: Must be thread-safe if stateful
- **Performance**: Best performance, minimal memory
- **Use case**: Stateless services, utilities

### 🔹 Prototype Scope

**New instance for each request**

```java
@Service
@Scope("prototype")
public class OrderProcessor {
    private String orderId;
    
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    
    public void process() {
        System.out.println("Processing order: " + orderId);
    }
}

// Usage
OrderProcessor processor1 = context.getBean(OrderProcessor.class);
OrderProcessor processor2 = context.getBean(OrderProcessor.class);

processor1.setOrderId("ORD-001");
processor2.setOrderId("ORD-002");

processor1.process();  // Processing order: ORD-001
processor2.process();  // Processing order: ORD-002

System.out.println(processor1 == processor2);  // false
```

**Characteristics:**
- **New instance**: Each injection/request gets new instance
- **State isolation**: Safe for stateful beans
- **Memory usage**: Higher memory consumption
- **Cleanup**: Container doesn't manage destruction

### 🔹 Request Scope

**One instance per HTTP request**

```java
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RequestContext {
    private String userId;
    private Map<String, Object> attributes = new HashMap<>();
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public void setAttribute(String key, Object value) {
        attributes.put(key, value);
    }
    
    public Object getAttribute(String key) {
        return attributes.get(key);
    }
}

// Usage in controller
@Controller
public class UserController {
    @Autowired
    private RequestContext requestContext;
    
    @GetMapping("/user/profile")
    public String profile(HttpServletRequest request) {
        // Extract user from request/session
        String userId = extractUserId(request);
        requestContext.setUserId(userId);
        
        // Use request-scoped data
        return "profile";
    }
}
```

**Characteristics:**
- **Request-bound**: Instance lives for single HTTP request
- **Thread-safety**: Safe in multi-threaded web apps
- **Proxy required**: Use proxyMode for injection into singletons
- **Use case**: Request-specific data, user context

### 🔹 Session Scope

**One instance per HTTP session**

```java
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserSession {
    private String username;
    private List<String> permissions;
    private LocalDateTime loginTime;
    
    public void initialize(String username, List<String> permissions) {
        this.username = username;
        this.permissions = permissions;
        this.loginTime = LocalDateTime.now();
    }
    
    public boolean hasPermission(String permission) {
        return permissions.contains(permission);
    }
    
    public String getUsername() {
        return username;
    }
}

// Usage
@Controller
public class SecureController {
    @Autowired
    private UserSession userSession;
    
    @GetMapping("/admin")
    public String adminPage() {
        if (!userSession.hasPermission("ADMIN")) {
            return "access-denied";
        }
        return "admin";
    }
}
```

**Characteristics:**
- **Session-bound**: Instance lives for user session
- **User-specific**: Different instance per user
- **Persistence**: Survives multiple requests
- **Use case**: User preferences, shopping cart, login state

### 🔹 Application Scope

**One instance per ServletContext**

```java
@Component
@Scope(value = WebApplicationContext.SCOPE_APPLICATION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ApplicationCache {
    private final Map<String, Object> cache = new ConcurrentHashMap<>();
    
    public void put(String key, Object value) {
        cache.put(key, value);
    }
    
    public Object get(String key) {
        return cache.get(key);
    }
    
    public void clear() {
        cache.clear();
    }
    
    public int size() {
        return cache.size();
    }
}

// Usage
@Service
public class ProductService {
    @Autowired
    private ApplicationCache cache;
    
    public Product getProduct(String id) {
        Product product = (Product) cache.get("product:" + id);
        if (product == null) {
            product = repository.findById(id);
            cache.put("product:" + id, product);
        }
        return product;
    }
}
```

**Characteristics:**
- **Application-wide**: Shared across all users/sessions
- **Global state**: Visible to entire application
- **Thread-safety**: Must be thread-safe
- **Use case**: Application configuration, global cache

### 🔹 Custom Scopes

**Define your own scopes**

```java
// Custom scope implementation
public class ThreadScope implements Scope {
    private final ThreadLocal<Map<String, Object>> threadLocal = 
        ThreadLocal.withInitial(HashMap::new);
    
    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        Map<String, Object> scope = threadLocal.get();
        Object object = scope.get(name);
        if (object == null) {
            object = objectFactory.getObject();
            scope.put(name, object);
        }
        return object;
    }
    
    @Override
    public Object remove(String name) {
        return threadLocal.get().remove(name);
    }
    
    @Override
    public void registerDestructionCallback(String name, Runnable callback) {
        // Handle cleanup
    }
    
    @Override
    public Object resolveContextualObject(String key) {
        return null;
    }
    
    @Override
    public String getConversationId() {
        return Thread.currentThread().getName();
    }
}

// Register custom scope
@Configuration
public class ScopeConfig {
    @Bean
    public static CustomScopeConfigurer customScopeConfigurer() {
        CustomScopeConfigurer configurer = new CustomScopeConfigurer();
        configurer.addScope("thread", new ThreadScope());
        return configurer;
    }
}

// Use custom scope
@Component
@Scope("thread")
public class ThreadScopedBean {
    // One instance per thread
}
```

### 🔹 Scope Comparison

| Scope | Lifecycle | Thread-safe | Use Case |
|-------|-----------|-------------|----------|
| **Singleton** | Container | Must ensure | Shared services |
| **Prototype** | Request | Yes | Stateful objects |
| **Request** | HTTP request | Yes | Request data |
| **Session** | HTTP session | Yes | User data |
| **Application** | App lifetime | Must ensure | Global data |

### 🔹 Scoped Proxy

**Proxy for injecting scoped beans into singletons**

```java
// Without proxy - fails
@Service
public class SingletonService {
    @Autowired
    private RequestScopedBean requestBean;  // ERROR at startup
}

// With proxy - works
@Service
public class SingletonService {
    @Autowired
    private RequestScopedBean requestBean;  // Proxy injected
}

// Proxy delegates to actual scoped instance at runtime
@Configuration
public class ProxyConfig {
    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public RequestScopedBean requestScopedBean() {
        return new RequestScopedBean();
    }
}
```

### 🔹 Best Practices

```java
// Use singleton for stateless services
// Use prototype for stateful objects
// Use request/session scope in web applications
// Ensure thread-safety for shared scopes
// Use scoped proxies when injecting scoped beans
// Consider memory implications of scopes
// Document scope requirements
```

---

## 🎯 Interview One-Liner

> Singleton: one instance per container (default); Prototype: new instance per request; Request: per HTTP request; Session: per user session; Application: per web app; choose based on sharing requirements.

---

## ✅ 59. Bean lifecycle in Spring

**Bean lifecycle consists of instantiation, initialization, usage, and destruction phases, with multiple extension points for customization.**

### 🔹 Lifecycle Phases

**Complete bean lifecycle**

```java
// 1. Instantiation - Bean created
// 2. Population - Dependencies injected
// 3. Initialization - Bean configured and ready
// 4. Usage - Bean used by application
// 5. Destruction - Bean cleaned up

@Component
public class LifecycleBean {
    public LifecycleBean() {
        System.out.println("1. Constructor called");
    }
    
    @Autowired
    public void setDependency(Dependency dep) {
        System.out.println("2. Dependencies injected");
    }
    
    @PostConstruct
    public void init() {
        System.out.println("3. @PostConstruct called");
    }
    
    public void use() {
        System.out.println("4. Bean in use");
    }
    
    @PreDestroy
    public void destroy() {
        System.out.println("5. @PreDestroy called");
    }
}
```

### 🔹 Detailed Lifecycle

### 🔹 Phase 1: Instantiation

**Bean object is created**

```java
// Ways to instantiate:
1. Constructor (default)
2. Static factory method
3. Instance factory method

// Constructor instantiation
@Service
public class UserService {
    public UserService() {
        // Constructor logic
        System.out.println("Bean instantiated");
    }
}

// Factory method instantiation
@Configuration
public class FactoryConfig {
    @Bean
    public DataSource dataSource() {
        // Factory creates and returns bean
        return DataSourceFactory.create();
    }
}
```

### 🔹 Phase 2: Population of Properties

**Dependencies and configuration injected**

```java
@Service
public class OrderService {
    @Autowired
    private UserService userService;  // Dependency injection
    
    @Value("${app.order.timeout}")
    private int timeout;  // Property injection
    
    // Called after dependencies injected
    @Autowired
    public void initialize(OrderService self) {
        System.out.println("Dependencies populated");
    }
}
```

### 🔹 Phase 3: Initialization

**Bean is configured and prepared for use**

```java
// Multiple ways to hook into initialization:

// 1. @PostConstruct (recommended)
@Component
public class InitBean1 {
    @PostConstruct
    public void init() {
        // Initialization logic
        validateConfiguration();
        establishConnections();
    }
}

// 2. InitializingBean interface
@Component
public class InitBean2 implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        // Initialization logic
    }
}

// 3. Custom init method
@Configuration
public class Config {
    @Bean(initMethod = "customInit")
    public MyBean myBean() {
        return new MyBean();
    }
}

public class MyBean {
    public void customInit() {
        // Custom initialization
    }
}

// 4. BeanPostProcessor (global)
@Component
public class CustomBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        // Modify bean after initialization
        return bean;
    }
}
```

### 🔹 Phase 4: Usage

**Bean is used by the application**

```java
@Service
public class ApplicationService {
    @Autowired
    private UserService userService;
    
    public void processRequest() {
        // Bean is actively used
        User user = userService.getUser(1);
        // Process user data
    }
}
```

### 🔹 Phase 5: Destruction

**Bean is cleaned up before removal**

```java
// Multiple ways to hook into destruction:

// 1. @PreDestroy (recommended)
@Component
public class DestroyBean1 {
    @PreDestroy
    public void cleanup() {
        // Cleanup logic
        closeConnections();
        releaseResources();
    }
}

// 2. DisposableBean interface
@Component
public class DestroyBean2 implements DisposableBean {
    @Override
    public void destroy() throws Exception {
        // Cleanup logic
    }
}

// 3. Custom destroy method
@Configuration
public class Config {
    @Bean(destroyMethod = "customDestroy")
    public MyBean myBean() {
        return new MyBean();
    }
}

public class MyBean {
    public void customDestroy() {
        // Custom cleanup
    }
}
```

### 🔹 Aware Interfaces

**Beans can be aware of container**

```java
@Component
public class AwareBean implements 
    BeanNameAware, BeanFactoryAware, ApplicationContextAware, 
    EnvironmentAware, EmbeddedValueResolverAware {
    
    @Override
    public void setBeanName(String name) {
        System.out.println("Bean name: " + name);
    }
    
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("BeanFactory injected");
    }
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("ApplicationContext injected");
    }
    
    @Override
    public void setEnvironment(Environment environment) {
        System.out.println("Environment injected");
    }
    
    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        System.out.println("ValueResolver injected");
    }
}
```

### 🔹 BeanPostProcessor

**Modify beans during lifecycle**

```java
@Component
public class CustomBeanPostProcessor implements BeanPostProcessor {
    
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // Called before initialization
        if (bean instanceof Validatable) {
            ((Validatable) bean).validate();
        }
        return bean;
    }
    
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // Called after initialization
        if (bean instanceof Proxyable) {
            return createProxy(bean);
        }
        return bean;
    }
}
```

### 🔹 Lifecycle in Different Scopes

```java
// Singleton scope - created once
@Service
@Scope("singleton")
public class SingletonBean {
    // Lifecycle: constructor → init → (usage) → destroy
}

// Prototype scope - created per request
@Service
@Scope("prototype")
public class PrototypeBean {
    // Lifecycle: constructor → init → usage
    // No destroy phase - container doesn't manage
}

// Request scope - per HTTP request
@Component
@Scope("request")
public class RequestBean {
    // Lifecycle: constructor → init → usage → destroy (end of request)
}
```

### 🔹 @DependsOn

**Control bean initialization order**

```java
@Configuration
public class DependencyConfig {
    @Bean
    @DependsOn("dataSource")
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
    
    @Bean
    public DataSource dataSource() {
        return new HikariDataSource();
    }
}
```

### 🔹 @Lazy

**Defer bean initialization**

```java
@Service
@Lazy
public class LazyService {
    // Created only when first accessed
}

@Configuration
public class Config {
    @Bean
    @Lazy
    public ExpensiveBean expensiveBean() {
        // Created only when needed
        return new ExpensiveBean();
    }
}
```

### 🔹 Best Practices

```java
// Use @PostConstruct/@PreDestroy for lifecycle hooks
// Prefer constructor injection over setters
// Implement validation in initialization phase
// Clean up resources in destruction phase
// Use @DependsOn for explicit dependencies
// Use @Lazy for expensive beans
// Avoid complex logic in lifecycle methods
// Document lifecycle requirements
```

---

## 🎯 Interview One-Liner

> Bean lifecycle: instantiation → property population → initialization (@PostConstruct) → usage → destruction (@PreDestroy); aware interfaces for container access; BeanPostProcessor for modification.

---

## ✅ 60. What is ApplicationContext?

**ApplicationContext is the central interface for Spring's IoC container, providing advanced features like internationalization, event publishing, and resource loading beyond basic BeanFactory functionality.**

### 🔹 ApplicationContext Overview

**Spring's advanced IoC container**

```java
// ApplicationContext interface hierarchy
public interface ApplicationContext extends 
    EnvironmentCapable, ListableBeanFactory, HierarchicalBeanFactory,
    MessageSource, ApplicationEventPublisher, ResourcePatternResolver {
    // Advanced container features
}

// Common implementations
ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
ApplicationContext context = new FileSystemXmlApplicationContext("c:/app/config.xml");
```

### 🔹 Key Features

### 🔹 1. Bean Management

**Advanced bean factory capabilities**

```java
ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

// Get beans
UserService service = context.getBean(UserService.class);
UserService service = context.getBean("userService", UserService.class);

// Get all beans of type
Map<String, UserService> services = context.getBeansOfType(UserService.class);

// Check bean existence
boolean exists = context.containsBean("userService");
boolean singleton = context.isSingleton("userService");
boolean prototype = context.isPrototype("userService");

// Get bean definition
BeanDefinition definition = context.getBeanDefinition("userService");
```

### 🔹 2. Internationalization (i18n)

**Message source for localization**

```java
// Configure message source
@Configuration
public class I18nConfig {
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasename("messages");
        source.setDefaultEncoding("UTF-8");
        return source;
    }
}

// Usage
@Service
public class GreetingService {
    @Autowired
    private ApplicationContext context;
    
    public String getGreeting(String name, Locale locale) {
        String template = context.getMessage("greeting.template", null, locale);
        return String.format(template, name);
    }
}

// messages.properties
greeting.template=Hello, %s!

// messages_fr.properties  
greeting.template=Bonjour, %s!
```

### 🔹 3. Event Publishing

**Publish and listen to application events**

```java
// Custom event
public class UserRegisteredEvent extends ApplicationEvent {
    private final User user;
    
    public UserRegisteredEvent(Object source, User user) {
        super(source);
        this.user = user;
    }
    
    public User getUser() {
        return user;
    }
}

// Event listener
@Component
public class UserEventListener {
    @EventListener
    public void handleUserRegistration(UserRegisteredEvent event) {
        System.out.println("User registered: " + event.getUser().getName());
        // Send welcome email, etc.
    }
}

// Event publisher
@Service
public class UserService {
    @Autowired
    private ApplicationContext context;
    
    public void registerUser(User user) {
        // Save user
        repository.save(user);
        
        // Publish event
        context.publishEvent(new UserRegisteredEvent(this, user));
    }
}
```

### 🔹 4. Resource Loading

**Load resources from various locations**

```java
@Service
public class ResourceService {
    @Autowired
    private ApplicationContext context;
    
    public void loadResources() throws IOException {
        // Load from classpath
        Resource resource1 = context.getResource("classpath:config.properties");
        
        // Load from file system
        Resource resource2 = context.getResource("file:/etc/app/config.properties");
        
        // Load from URL
        Resource resource3 = context.getResource("https://example.com/config.properties");
        
        // Load multiple resources
        Resource[] resources = context.getResources("classpath*:config/*.properties");
        
        // Read content
        try (InputStream is = resource1.getInputStream()) {
            // Process resource
        }
    }
}
```

### 🔹 5. Environment and Profiles

**Access environment properties and profiles**

```java
@Service
public class EnvironmentService {
    @Autowired
    private ApplicationContext context;
    
    public void checkEnvironment() {
        Environment env = context.getEnvironment();
        
        // Get active profiles
        String[] profiles = env.getActiveProfiles();
        
        // Get property
        String dbUrl = env.getProperty("spring.datasource.url");
        
        // Check if profile is active
        boolean isProd = env.acceptsProfiles(Profiles.of("prod"));
        
        // Get system properties
        String javaVersion = env.getProperty("java.version");
    }
}
```

### 🔹 6. BeanFactory vs ApplicationContext

**ApplicationContext extends BeanFactory**

```java
// BeanFactory (basic)
BeanFactory factory = new XmlBeanFactory(new ClassPathResource("beans.xml"));
UserService service = (UserService) factory.getBean("userService");

// ApplicationContext (advanced)
ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
UserService service = context.getBean(UserService.class);

// ApplicationContext features over BeanFactory:
// - Internationalization (MessageSource)
// - Event publishing (ApplicationEventPublisher)  
// - Resource loading (ResourcePatternResolver)
// - Environment access
// - Automatic BeanPostProcessor registration
// - Automatic BeanFactoryPostProcessor execution
```

### 🔹 7. Hierarchical Contexts

**Parent-child context relationships**

```java
// Root context
ApplicationContext rootContext = new AnnotationConfigApplicationContext(RootConfig.class);

// Child context
ApplicationContext childContext = new AnnotationConfigApplicationContext();
childContext.setParent(rootContext);

// Child can access parent's beans
// Parent cannot access child's beans
UserService service = childContext.getBean(UserService.class);  // From parent or child
```

### 🔹 8. Lifecycle Management

**Manage application lifecycle**

```java
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        
        // Application is running
        
        // Graceful shutdown
        context.close();
    }
}

// Lifecycle callbacks
@Component
public class LifecycleManager implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("Application context refreshed");
        // Initialize application resources
    }
}
```

### 🔹 Common Implementations

```java
// Annotation-based (modern)
ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

// XML-based (legacy)
ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

// Web application
ApplicationContext context = new XmlWebApplicationContext();

// Spring Boot
ApplicationContext context = SpringApplication.run(App.class, args);
```

### 🔹 Best Practices

```java
// Use ApplicationContext over BeanFactory for features
// Prefer annotation-based configuration
// Leverage event publishing for loose coupling
// Use internationalization for multi-language support
// Access environment properties through context
// Implement lifecycle listeners for initialization
// Use hierarchical contexts for modular applications
```

---

## 🎯 Interview One-Liner

> ApplicationContext is advanced IoC container extending BeanFactory; provides i18n, events, resource loading, environment access; supports hierarchical contexts and lifecycle management.

---

### **Spring Boot**
## ✅ 61. What is Spring Boot? Advantages over Spring?

**Spring Boot is a framework built on top of Spring Framework that simplifies the creation of production-ready, stand-alone Spring applications with minimal configuration and setup.**

### 🔹 What is Spring Boot?

**Convention-over-configuration framework for Spring**

```java
// Traditional Spring application (lots of XML/config)
@Configuration
@ComponentScan
@EnableWebMvc
public class AppConfig {
    // Multiple configurations needed
}

// Spring Boot application (minimal setup)
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

### 🔹 Key Features

### 🔹 1. Auto-Configuration

**Automatically configures beans based on classpath**

```java
// No need to configure DataSource manually
// Spring Boot detects H2/MySQL and configures automatically
@SpringBootApplication
public class Application {
    // DataSource bean auto-configured
}
```

### 🔹 2. Standalone Applications

**Creates JAR with embedded server**

```java
// Traditional Spring: WAR deployment
// Spring Boot: JAR with embedded Tomcat
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

### 🔹 3. Opinionated Defaults

**Sensible defaults for common configurations**

```java
// Default port: 8080
// Default context path: /
// Default logging: Logback
// Default database: H2 (if on classpath)
```

### 🔹 4. Production Ready

**Built-in features for production**

```java
// Health checks, metrics, monitoring
// Embedded servers (Tomcat, Jetty, Undertow)
// Externalized configuration
// Security auto-configuration
```

### 🔹 Advantages over Spring Framework

| Aspect | Spring Framework | Spring Boot |
|--------|------------------|-------------|
| **Configuration** | XML/Java config required | Auto-configuration |
| **Dependencies** | Manual management | Starters manage dependencies |
| **Deployment** | WAR files | JAR files with embedded server |
| **Setup Time** | Days/weeks | Minutes |
| **Learning Curve** | Steep | Gentle |
| **Microservices** | Complex setup | Easy with starters |

### 🔹 Spring Boot vs Spring MVC

```java
// Spring MVC (traditional)
@Configuration
@EnableWebMvc
@ComponentScan
public class WebConfig extends WebMvcConfigurerAdapter {
    // Lots of configuration
}

// Spring Boot (simplified)
@SpringBootApplication
public class Application {
    // Everything auto-configured
}
```

### 🔹 When to Use Spring Boot

```java
// ✅ Microservices
// ✅ REST APIs
// ✅ Batch applications
// ✅ Cloud-native applications
// ✅ Rapid prototyping
// ✅ Production applications

// ❌ Legacy applications requiring custom configurations
// ❌ Applications needing fine-grained control
```

### 🔹 Best Practices

```java
// Use @SpringBootApplication for main class
// Leverage auto-configuration when possible
// Override only when necessary
// Use starters for dependency management
// Externalize configuration
// Use profiles for different environments
```

---

## 🎯 Interview One-Liner

> Spring Boot simplifies Spring development with auto-configuration, embedded servers, and opinionated defaults; reduces setup from weeks to minutes compared to traditional Spring.

---

## ✅ 62. What is auto-configuration in Spring Boot?

**Auto-configuration is Spring Boot's mechanism that automatically configures Spring application based on the dependencies present on the classpath, eliminating the need for manual bean configuration.**

### 🔹 How Auto-Configuration Works

**Convention-over-configuration approach**

```java
// Spring Boot scans classpath for libraries
// Automatically configures beans based on presence of:
// - spring-boot-starter-web → Tomcat + Spring MVC
// - spring-boot-starter-data-jpa → EntityManager + DataSource
// - spring-boot-starter-security → Security configuration

@SpringBootApplication  // Enables auto-configuration
public class Application {
    // No manual configuration needed
}
```

### 🔹 Auto-Configuration Classes

**Conditional bean registration**

```java
@Configuration
@ConditionalOnClass(DataSource.class)  // Only if DataSource on classpath
@ConditionalOnMissingBean(DataSource.class)  // Only if no manual bean
@EnableConfigurationProperties(DataSourceProperties.class)
public class DataSourceAutoConfiguration {
    
    @Bean
    @ConditionalOnMissingBean
    public DataSource dataSource(DataSourceProperties properties) {
        return DataSourceBuilder.create()
            .url(properties.getUrl())
            .username(properties.getUsername())
            .password(properties.getPassword())
            .build();
    }
}
```

### 🔹 Conditional Annotations

**Control when auto-configuration applies**

```java
@ConditionalOnClass          // If class is present
@ConditionalOnMissingClass   // If class is absent
@ConditionalOnBean           // If bean exists
@ConditionalOnMissingBean    // If bean doesn't exist
@ConditionalOnProperty       // If property has value
@ConditionalOnResource       // If resource exists
@ConditionalOnWebApplication // If web application
@ConditionalOnNotWebApplication // If not web application
```

### 🔹 Excluding Auto-Configuration

**Disable specific auto-configurations**

```java
@SpringBootApplication(exclude = {
    DataSourceAutoConfiguration.class,
    HibernateJpaAutoConfiguration.class
})
public class Application {
    // Custom configuration instead
}
```

### 🔹 Custom Auto-Configuration

**Create your own auto-configuration**

```java
@Configuration
@ConditionalOnClass(MyService.class)
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
public class MyServiceAutoConfiguration {
    
    @Bean
    @ConditionalOnMissingBean
    public MyService myService() {
        return new MyServiceImpl();
    }
}
```

### 🔹 Auto-Configuration Order

**@AutoConfigureBefore/@AutoConfigureAfter**

```java
@Configuration
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
public class MyRepositoryAutoConfiguration {
    // Configured after DataSource
}
```

### 🔹 Debugging Auto-Configuration

**See what auto-configurations are applied**

```properties
# application.properties
debug=true  # Shows auto-configuration report
```

### 🔹 Common Auto-Configurations

```java
// Web applications
- ServerPropertiesAutoConfiguration
- WebMvcAutoConfiguration
- HttpEncodingAutoConfiguration

// Data
- DataSourceAutoConfiguration
- JpaRepositoriesAutoConfiguration
- MongoAutoConfiguration

// Security
- SecurityAutoConfiguration
- UserDetailsServiceAutoConfiguration

// Actuator
- EndpointAutoConfiguration
- HealthIndicatorAutoConfiguration
```

### 🔹 Best Practices

```java
// Let Spring Boot auto-configure when possible
// Exclude only when you need custom configuration
// Use @ConditionalOnMissingBean for custom beans
// Debug with debug=true property
// Understand conditional annotations
// Create custom auto-configurations for libraries
```

---

## 🎯 Interview One-Liner

> Auto-configuration automatically configures Spring beans based on classpath dependencies using conditional annotations; eliminates manual XML/Java config for common scenarios.

---

## ✅ 63. Explain @SpringBootApplication annotation

**@SpringBootApplication is a convenience annotation that combines @Configuration, @EnableAutoConfiguration, and @ComponentScan, enabling Spring Boot's auto-configuration and component scanning in a single annotation.**

### 🔹 Annotation Composition

**Combines three essential annotations**

```java
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

// Equivalent to:
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application {
    // Same functionality
}
```

### 🔹 @Configuration

**Marks class as configuration source**

```java
@Configuration
public class AppConfig {
    @Bean
    public UserService userService() {
        return new UserServiceImpl();
    }
}
```

### 🔹 @EnableAutoConfiguration

**Enables auto-configuration**

```java
@EnableAutoConfiguration
public class Application {
    // Spring Boot scans classpath
    // Configures beans automatically
}
```

### 🔹 @ComponentScan

**Scans for Spring components**

```java
@ComponentScan(basePackages = "com.example")
public class Application {
    // Scans @Component, @Service, @Repository, @Controller
}
```

### 🔹 Customization Options

**Configure scanning and auto-configuration**

```java
@SpringBootApplication(
    scanBasePackages = "com.example",  // Component scan packages
    exclude = {DataSourceAutoConfiguration.class},  // Exclude auto-config
    excludeName = {"com.example.config"}  // Exclude by name
)
public class Application {
    // Customized scanning
}
```

### 🔹 Main Method Convention

**Standard Spring Boot application structure**

```java
@SpringBootApplication
public class Application {
    
    public static void main(String[] args) {
        // Bootstrap the application
        SpringApplication.run(Application.class, args);
    }
}
```

### 🔹 SpringApplication.run()

**What happens during startup**

```java
// 1. Creates ApplicationContext
// 2. Registers command line arguments
// 3. Loads configuration
// 4. Starts auto-configuration
// 5. Starts embedded server (if web app)
// 6. Triggers application ready event
```

### 🔹 Alternative Configurations

**Different ways to configure**

```java
// Separate annotations
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application { }

// Custom component scan
@SpringBootApplication
@PropertySource("classpath:custom.properties")
public class Application { }

// Exclude specific auto-configurations
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class Application { }
```

### 🔹 Best Practices

```java
// Place @SpringBootApplication on main class
// Keep main class in root package
// Use default scanning when possible
// Exclude auto-configuration only when necessary
// Use SpringApplication.run() for startup
// Customize scanBasePackages for multi-module projects
```

---

## 🎯 Interview One-Liner

> @SpringBootApplication combines @Configuration, @EnableAutoConfiguration, and @ComponentScan; enables auto-config and component scanning with single annotation.

---

## ✅ 64. What are Spring Boot Starters?

**Spring Boot Starters are curated sets of dependencies that simplify dependency management by providing a single dependency to include all necessary libraries for a specific functionality.**

### 🔹 What are Starters?

**Pre-configured dependency bundles**

```xml
<!-- Without starter (manual dependencies) -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-web</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-webmvc</artifactId>
</dependency>
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
</dependency>

<!-- With starter (single dependency) -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

### 🔹 Common Starters

### 🔹 1. Core Starters

```xml
<!-- Web applications -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<!-- REST APIs -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webflux</artifactId>
</dependency>

<!-- Data JPA -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<!-- Security -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

### 🔹 2. Database Starters

```xml
<!-- JDBC -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>

<!-- MongoDB -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-mongodb</artifactId>
</dependency>

<!-- Redis -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

### 🔹 3. Testing Starters

```xml
<!-- Unit and integration testing -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
</dependency>
```

### 🔹 4. Production Starters

```xml
<!-- Monitoring and management -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>

<!-- Logging -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-logging</artifactId>
</dependency>
```

### 🔹 Custom Starters

**Create your own starter**

```xml
<!-- Custom starter POM -->
<dependency>
    <groupId>com.example</groupId>
    <artifactId>my-service-starter</artifactId>
    <version>1.0.0</version>
</dependency>

<!-- Includes auto-configuration and dependencies -->
```

### 🔹 Starter Structure

**Typical starter components**

```java
// 1. Auto-configuration class
@Configuration
@ConditionalOnClass(MyService.class)
public class MyServiceAutoConfiguration {
    @Bean
    public MyService myService() {
        return new MyServiceImpl();
    }
}

// 2. Properties class
@ConfigurationProperties("my.service")
public class MyServiceProperties {
    private String url = "localhost";
    private int port = 8080;
}

// 3. spring.factories file
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
com.example.MyServiceAutoConfiguration
```

### 🔹 Benefits

```java
// ✅ Simplified dependency management
// ✅ Version compatibility guaranteed
// ✅ Auto-configuration included
// ✅ Convention over configuration
// ✅ Faster project setup
// ✅ Consistent dependency versions
```

### 🔹 Best Practices

```java
// Use official Spring Boot starters when possible
// Check starter contents with mvn dependency:tree
// Create custom starters for reusable components
// Include auto-configuration in custom starters
// Use spring-boot-starter-parent for version management
// Override versions only when necessary
```

---

## 🎯 Interview One-Liner

> Spring Boot Starters are dependency bundles providing single dependency for functionality; include auto-config, reduce manual dependency management, ensure version compatibility.

---

## ✅ 65. application.properties vs application.yml

**Both application.properties and application.yml are used for externalized configuration in Spring Boot, with properties using key=value format and YAML using hierarchical structure, but YAML is more readable for complex configurations.**

### 🔹 application.properties

**Traditional properties file format**

```properties
# application.properties
server.port=8080
server.servlet.context-path=/api
spring.datasource.url=jdbc:mysql://localhost:3306/mydb
spring.datasource.username=root
spring.datasource.password=secret
logging.level.com.example=DEBUG
app.name=My Application
app.version=1.0.0
```

### 🔹 application.yml

**YAML format for configuration**

```yaml
# application.yml
server:
  port: 8080
  servlet:
    context-path: /api

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/mydb
    username: root
    password: secret

logging:
  level:
    com.example: DEBUG

app:
  name: "My Application"
  version: 1.0.0
```

### 🔹 Comparison

| Aspect              | Properties      | YAML             |
|---------------------|-----------------|------------------|
| **Readability**     | Flat structure  | Hierarchical     |
| **Complex Config**  | Verbose         | Clean            |
| **Lists**           | Comma-separated | Native support   |
| **Comments**        | # comments      | # comments       |
| **Multi-line**      | Not supported   | Supported        |
| **Validation**      | Basic           | Better structure |

### 🔹 Lists and Complex Structures

```properties
# Properties - comma separated
app.servers=server1,server2,server3
app.config.items[0].name=item1
app.config.items[0].value=value1
```

```yaml
# YAML - native lists and objects
app:
  servers:
    - server1
    - server2
    - server3
  config:
    items:
      - name: item1
        value: value1
      - name: item2
        value: value2
```

### 🔹 Profiles

```properties
# application-dev.properties
spring.profiles.active=dev
database.url=jdbc:h2:mem:dev

# application-prod.properties  
spring.profiles.active=prod
database.url=jdbc:mysql://prod:3306/app
```

```yaml
# application.yml
spring:
  profiles:
    active: dev
---
spring:
  profiles: dev
  datasource:
    url: jdbc:h2:mem:dev
---
spring:
  profiles: prod
  datasource:
    url: jdbc:mysql://prod:3306/app
```

### 🔹 Environment Variables

```properties
# Properties
app.database.url=${DATABASE_URL:jdbc:h2:mem:default}
app.database.user=${DB_USER:admin}
```

```yaml
# YAML
app:
  database:
    url: ${DATABASE_URL:jdbc:h2:mem:default}
    user: ${DB_USER:admin}
```

### 🔹 When to Use Which

```yaml
# Use YAML for:
# - Complex hierarchical configurations
# - Lists and nested objects
# - Better readability
# - Microservices configurations

# Use Properties for:
# - Simple key-value pairs
# - Legacy applications
# - Tools that don't support YAML
# - Environment variables
```

### 🔹 Loading Order

**Configuration loading precedence**

```yaml
# 1. Command line arguments
# 2. JNDI attributes
# 3. System properties
# 4. Environment variables
# 5. application-{profile}.yml/properties
# 6. application.yml/properties
# 7. @PropertySource
# 8. Default properties
```

### 🔹 Best Practices

```yaml
# Use YAML for new applications
# Keep sensitive data in environment variables
# Use profiles for different environments
# Validate configuration with @ConfigurationProperties
# Document configuration properties
# Use consistent naming conventions
```

---

## 🎯 Interview One-Liner

> application.properties uses flat key=value format; application.yml uses hierarchical YAML structure; YAML preferred for complex configs, properties for simple key-value pairs.

---

## ✅ 66. What are Spring Profiles? How to use them?

**Spring Profiles provide a way to segregate application configuration and beans for different environments (dev, test, prod) or use cases, allowing conditional activation based on active profiles.**

### 🔹 What are Profiles?

**Environment-specific configurations**

```java
@Configuration
public class AppConfig {
    
    @Bean
    @Profile("dev")
    public DataSource devDataSource() {
        return new EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .build();
    }
    
    @Bean
    @Profile("prod")
    public DataSource prodDataSource() {
        return DataSourceBuilder.create()
            .url("jdbc:mysql://prod:3306/app")
            .build();
    }
}
```

### 🔹 Activating Profiles

**Multiple ways to activate profiles**

```java
// 1. Command line
java -jar app.jar --spring.profiles.active=prod

// 2. Environment variable
export SPRING_PROFILES_ACTIVE=prod

// 3. application.properties/yml
spring.profiles.active=prod

// 4. Programmatically
SpringApplication app = new SpringApplication(MyApplication.class);
app.setAdditionalProfiles("prod");
app.run(args);
```

### 🔹 Profile-Specific Configuration

**Separate configuration files**

```properties
# application.properties (common)
app.name=MyApp

# application-dev.properties
spring.profiles.active=dev
server.port=8080
logging.level.root=DEBUG

# application-prod.properties
spring.profiles.active=prod
server.port=8443
logging.level.root=INFO
```

```yaml
# application.yml
spring:
  profiles:
    active: dev
---
spring:
  profiles: dev
server:
  port: 8080
logging:
  level:
    root: DEBUG
---
spring:
  profiles: prod
server:
  port: 8443
logging:
  level:
    root: INFO
```

### 🔹 Profile Annotations

**Conditional bean registration**

```java
@Service
@Profile("dev")
public class DevService implements MyService {
    // Only created when 'dev' profile active
}

@Service
@Profile("prod")
public class ProdService implements MyService {
    // Only created when 'prod' profile active
}

@Service
@Profile("!test")  // Not in test profile
public class NonTestService implements MyService {
    // Created in all profiles except 'test'
}
```

### 🔹 Multiple Profiles

**Activate multiple profiles**

```java
// Activate multiple profiles
spring.profiles.active=dev,metrics

// Bean with multiple profiles
@Profile({"dev", "staging"})
public class DevStagingService {
    // Active in dev OR staging
}
```

### 🔹 Default Profile

**Default profile when none specified**

```java
@Profile("default")
public class DefaultService {
    // Active when no profile specified
}
```

### 🔹 Programmatic Profile Detection

**Check active profiles in code**

```java
@Service
public class ProfileAwareService {
    
    @Autowired
    private Environment environment;
    
    public void someMethod() {
        if (environment.acceptsProfiles("dev")) {
            // Dev-specific logic
        }
        
        String[] activeProfiles = environment.getActiveProfiles();
        // Check active profiles
    }
}
```

### 🔹 Profile Groups (Spring Boot 2.4+)

**Group related profiles**

```yaml
# application.yml
spring:
  profiles:
    group:
      dev: dev-db, dev-log
      prod: prod-db, prod-log, prod-monitoring
```

### 🔹 Testing with Profiles

**Profile-specific tests**

```java
@SpringBootTest
@ActiveProfiles("test")
public class MyServiceTest {
    
    @Test
    public void testWithTestProfile() {
        // Test with test profile active
    }
}
```

### 🔹 Best Practices

```java
// Use profiles for environment-specific config
// Keep common config in application.properties/yml
// Use profile-specific files for overrides
// Avoid complex profile logic in code
// Document profile usage
// Test with different profiles
// Use @Profile on configuration classes
```

---

## 🎯 Interview One-Liner

> Spring Profiles enable environment-specific configurations; activate via spring.profiles.active property; use @Profile annotation for conditional bean registration.

---

## ✅ 67. How to externalize configuration?

**Externalizing configuration in Spring Boot involves moving application settings outside the codebase to properties files, environment variables, command-line arguments, or external systems for better maintainability and security.**

### 🔹 Configuration Sources

**Multiple configuration sources with precedence**

```java
// Loading order (highest to lowest precedence):
// 1. Command line arguments
// 2. JNDI attributes (java:comp/env)
// 3. System properties (-D)
// 4. Environment variables
// 5. Profile-specific application properties
// 6. Application properties
// 7. @PropertySource annotated classes
// 8. Default properties
```

### 🔹 Properties Files

**application.properties/yml**

```properties
# application.properties
app.name=MyApp
app.version=1.0.0
database.url=jdbc:mysql://localhost:3306/mydb
database.username=${DB_USER:admin}
database.password=${DB_PASSWORD:secret}
```

```yaml
# application.yml
app:
  name: MyApp
  version: 1.0.0
database:
  url: jdbc:mysql://localhost:3306/mydb
  username: ${DB_USER:admin}
  password: ${DB_PASSWORD:secret}
```

### 🔹 Environment Variables

**System environment variables**

```bash
export SPRING_DATASOURCE_URL=jdbc:mysql://prod:3306/app
export SPRING_DATASOURCE_USERNAME=produser
export SPRING_DATASOURCE_PASSWORD=prodpass
export SERVER_PORT=8443
```

### 🔹 Command Line Arguments

**Override at runtime**

```bash
java -jar app.jar --server.port=9090 --spring.profiles.active=prod
```

### 🔹 External Configuration Files

**Load from external locations**

```bash
# Specify config location
java -jar app.jar --spring.config.location=file:/etc/myapp/config.properties

# Multiple locations
java -jar app.jar --spring.config.location=classpath:/config/,file:/etc/myapp/
```

### 🔹 @ConfigurationProperties

**Type-safe configuration binding**

```java
@ConfigurationProperties(prefix = "app.database")
public class DatabaseProperties {
    
    private String url;
    private String username;
    private String password;
    private int maxConnections = 10;
    
    // getters and setters
}

@Configuration
public class DatabaseConfig {
    
    @Bean
    @ConfigurationProperties(prefix = "app.database")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }
}
```

### 🔹 @Value Annotation

**Inject individual properties**

```java
@Service
public class MyService {
    
    @Value("${app.name}")
    private String appName;
    
    @Value("${app.timeout:30}")
    private int timeout;  // Default value
    
    @Value("${app.features:}")
    private String[] features;  // Array from comma-separated
}
```

### 🔹 Profile-Specific External Config

**Environment-specific external files**

```bash
# Load dev-specific config
java -jar app.jar --spring.config.location=file:/config/dev/

# With profile
java -jar app.jar --spring.profiles.active=prod --spring.config.location=file:/config/prod/
```

### 🔹 Cloud Configuration

**Spring Cloud Config**

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-config</artifactId>
</dependency>
```

```properties
# application.properties
spring.cloud.config.uri=http://config-server:8888
spring.cloud.config.name=myapp
```

### 🔹 JNDI Configuration

**Container-provided configuration**

```java
@Bean
public DataSource dataSource() throws NamingException {
    JndiTemplate jndi = new JndiTemplate();
    return jndi.lookup("java:comp/env/jdbc/myDataSource", DataSource.class);
}
```

### 🔹 Best Practices

```java
// Use environment variables for sensitive data
// Provide sensible defaults
// Document all configuration properties
// Use @ConfigurationProperties for type safety
// Validate configuration values
// Use profiles for different environments
// Externalize config for containerized deployments
```

---

## 🎯 Interview One-Liner

> Externalize config via properties files, environment variables, command-line args; use @ConfigurationProperties for type-safe binding; order: command line > env vars > properties files.

---

## ✅ 68. What is @ConfigurationProperties?

**@ConfigurationProperties is a Spring Boot annotation that binds external configuration properties to Java objects, providing type-safe access to configuration values with validation and documentation support.**

### 🔹 Basic Usage

**Bind properties to POJO**

```java
@ConfigurationProperties(prefix = "app.database")
public class DatabaseProperties {
    
    private String url;
    private String username;
    private String password;
    private int maxPoolSize = 10;
    
    // getters and setters
}

// Usage in configuration
@Configuration
public class DatabaseConfig {
    
    @Bean
    public DataSource dataSource(DatabaseProperties props) {
        return DataSourceBuilder.create()
            .url(props.getUrl())
            .username(props.getUsername())
            .password(props.getPassword())
            .build();
    }
}
```

### 🔹 Properties File

**Corresponding configuration**

```yaml
# application.yml
app:
  database:
    url: jdbc:mysql://localhost:3306/mydb
    username: admin
    password: secret
    max-pool-size: 20
```

### 🔹 Validation

**Validate configuration values**

```java
@ConfigurationProperties(prefix = "app.database")
@Validated
public class DatabaseProperties {
    
    @NotBlank
    private String url;
    
    @NotBlank
    private String username;
    
    @NotBlank
    private String password;
    
    @Min(1)
    @Max(100)
    private int maxPoolSize = 10;
    
    // getters and setters
}
```

### 🔹 Nested Properties

**Complex nested configurations**

```java
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    
    private Database database = new Database();
    private Security security = new Security();
    
    public static class Database {
        private String url;
        private String username;
        // getters and setters
    }
    
    public static class Security {
        private String jwtSecret;
        private int tokenValidity = 3600;
        // getters and setters
    }
    
    // getters and setters
}
```

```yaml
app:
  database:
    url: jdbc:mysql://localhost:3306/app
    username: admin
  security:
    jwt-secret: mySecretKey
    token-validity: 7200
```

### 🔹 Lists and Maps

**Bind collections**

```java
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    
    private List<String> servers = new ArrayList<>();
    private Map<String, String> config = new HashMap<>();
    
    // getters and setters
}
```

```yaml
app:
  servers:
    - server1.example.com
    - server2.example.com
  config:
    key1: value1
    key2: value2
```

### 🔹 Duration and DataSize

**Type-safe values**

```java
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    
    private Duration timeout = Duration.ofSeconds(30);
    private DataSize maxFileSize = DataSize.ofMegabytes(10);
    
    // getters and setters
}
```

```yaml
app:
  timeout: 45s
  max-file-size: 20MB
```

### 🔹 @ConfigurationPropertiesScan

**Enable scanning for properties classes**

```java
@SpringBootApplication
@ConfigurationPropertiesScan("com.example.config")
public class Application {
    // Scans for @ConfigurationProperties classes
}
```

### 🔹 Constructor Binding

**Immutable properties with constructor**

```java
@ConfigurationProperties(prefix = "app.database")
public class DatabaseProperties {
    
    private final String url;
    private final String username;
    private final String password;
    
    public DatabaseProperties(
        @DefaultValue("jdbc:h2:mem:test") String url,
        String username,
        String password) {
        
        this.url = url;
        this.username = username;
        this.password = password;
    }
    
    // getters only
}
```

### 🔹 Best Practices

```java
// Use @ConfigurationProperties for complex configs
// Enable validation with @Validated
// Provide sensible defaults
// Use constructor binding for immutability
// Document properties with @ConfigurationProperties
// Group related properties with prefixes
// Use type-safe values (Duration, DataSize)
```

---

## 🎯 Interview One-Liner

> @ConfigurationProperties binds external properties to Java objects with type safety; supports validation, nested objects, lists/maps; prefix-based property grouping.

---

## ✅ 69. What is Spring Boot Actuator?

**Spring Boot Actuator provides production-ready features like health checks, metrics, monitoring, and management endpoints to help monitor and manage Spring Boot applications in production environments.**

### 🔹 What is Actuator?

**Production monitoring and management**

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

```yaml
# application.yml
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics
  endpoint:
    health:
      show-details: when-authorized
```

### 🔹 Built-in Endpoints

**Common actuator endpoints**

```bash
# Health check
GET /actuator/health
# Application info
GET /actuator/info
# Application metrics
GET /actuator/metrics
# Environment properties
GET /actuator/env
# Bean information
GET /actuator/beans
# HTTP request mappings
GET /actuator/mappings
# Thread dump
GET /actuator/threaddump
# Heap dump
GET /actuator/heapdump
```

### 🔹 Health Indicators

**Health check components**

```java
@Component
public class DatabaseHealthIndicator implements HealthIndicator {
    
    @Override
    public Health health() {
        try {
            // Check database connection
            return Health.up().build();
        } catch (Exception e) {
            return Health.down()
                .withDetail("error", e.getMessage())
                .build();
        }
    }
}
```

### 🔹 Custom Endpoints

**Create custom management endpoints**

```java
@Component
@Endpoint(id = "custom")
public class CustomEndpoint {
    
    @ReadOperation
    public Map<String, Object> customInfo() {
        Map<String, Object> info = new HashMap<>();
        info.put("customProperty", "value");
        info.put("timestamp", System.currentTimeMillis());
        return info;
    }
    
    @WriteOperation
    public void updateCustom(@Selector String key, String value) {
        // Update operation
    }
}
```

### 🔹 Security

**Secure actuator endpoints**

```yaml
management:
  endpoints:
    web:
      exposure:
        include: health,info
  endpoint:
    health:
      show-details: when-authorized
  security:
    enabled: true
```

### 🔹 Metrics

**Application metrics collection**

```java
@Service
public class MyService {
    
    private final MeterRegistry registry;
    
    public MyService(MeterRegistry registry) {
        this.registry = registry;
    }
    
    public void processData() {
        Counter.builder("data.processed")
            .tag("service", "myService")
            .register(registry)
            .increment();
    }
}
```

### 🔹 Info Endpoint

**Application information**

```yaml
# application.yml
info:
  app:
    name: My Application
    version: 1.0.0
  build:
    artifact: ${project.artifactId}
    version: ${project.version}
```

### 🔹 JMX Endpoints

**JMX management**

```yaml
management:
  endpoints:
    jmx:
      exposure:
        include: "*"
```

### 🔹 Best Practices

```java
// Expose only necessary endpoints in production
// Secure sensitive endpoints
// Implement custom health indicators
// Use metrics for monitoring
// Configure info endpoint with build details
// Use JMX for local management
// Monitor actuator endpoints regularly
```

---

## 🎯 Interview One-Liner

> Spring Boot Actuator provides production monitoring endpoints (health, metrics, info); enables health checks, metrics collection, and application management for production deployments.

---

## ✅ 70. How to create custom starter in Spring Boot?

**Creating a custom Spring Boot starter involves creating an auto-configuration module that provides opinionated defaults and dependencies for a specific functionality, following Spring Boot's starter conventions.**

### 🔹 Starter Structure

**Typical starter project structure**

```
my-library-starter/
├── src/main/java/
│   ├── com/example/
│   │   ├── autoconfigure/
│   │   │   ├── MyLibraryAutoConfiguration.java
│   │   │   └── MyLibraryProperties.java
│   │   └── MyLibraryService.java
│   └── META-INF/
│       └── spring.factories
├── src/main/resources/
│   └── META-INF/
│       └── additional-spring-configuration-metadata.json
└── pom.xml
```

### 🔹 Auto-Configuration Class

**Main auto-configuration logic**

```java
@Configuration
@ConditionalOnClass(MyLibrary.class)
@ConditionalOnMissingBean(MyLibrary.class)
@EnableConfigurationProperties(MyLibraryProperties.class)
public class MyLibraryAutoConfiguration {
    
    @Autowired
    private MyLibraryProperties properties;
    
    @Bean
    @ConditionalOnMissingBean
    public MyLibrary myLibrary() {
        return new MyLibrary(properties.getUrl(), properties.getTimeout());
    }
    
    @Bean
    @ConditionalOnMissingBean
    public MyLibraryService myLibraryService(MyLibrary myLibrary) {
        return new MyLibraryService(myLibrary);
    }
}
```

### 🔹 Properties Class

**Configuration properties**

```java
@ConfigurationProperties(prefix = "my.library")
public class MyLibraryProperties {
    
    private String url = "http://localhost:8080";
    private int timeout = 5000;
    private boolean enabled = true;
    
    // getters and setters
}
```

### 🔹 spring.factories

**Register auto-configuration**

```properties
# src/main/resources/META-INF/spring.factories
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
com.example.autoconfigure.MyLibraryAutoConfiguration
```

### 🔹 POM Configuration

**Starter dependencies**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0">
    <modelVersion>4.0.0</modelVersion>
    
    <groupId>com.example</groupId>
    <artifactId>my-library-starter</artifactId>
    <version>1.0.0</version>
    
    <properties>
        <java.version>17</java.version>
    </properties>
    
    <dependencies>
        <!-- Core Spring Boot -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-autoconfigure</artifactId>
        </dependency>
        
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-configuration-processor</artifactId>
            <optional>true</optional>
        </dependency>
        
        <!-- Your library dependencies -->
        <dependency>
            <groupId>com.example</groupId>
            <artifactId>my-library</artifactId>
            <version>1.0.0</version>
        </dependency>
    </dependencies>
</project>
```

### 🔹 Configuration Metadata

**Property documentation**

```json
// src/main/resources/META-INF/additional-spring-configuration-metadata.json
{
  "properties": [
    {
      "name": "my.library.url",
      "type": "java.lang.String",
      "description": "URL of the MyLibrary service.",
      "defaultValue": "http://localhost:8080"
    },
    {
      "name": "my.library.timeout",
      "type": "java.lang.Integer",
      "description": "Timeout for MyLibrary operations in milliseconds.",
      "defaultValue": 5000
    }
  ]
}
```

### 🔹 Conditional Annotations

**Control when starter activates**

```java
@Configuration
@ConditionalOnClass(MyLibrary.class)              // Library must be on classpath
@ConditionalOnMissingBean(MyLibrary.class)        // No manual bean defined
@ConditionalOnProperty(
    name = "my.library.enabled", 
    havingValue = "true", 
    matchIfMissing = true)                        // Property enabled or default
@AutoConfigureAfter(DataSourceAutoConfiguration.class)  // Configure after datasource
public class MyLibraryAutoConfiguration {
    // Auto-configuration logic
}
```

### 🔹 Testing the Starter

**Test auto-configuration**

```java
@SpringBootTest
@ImportAutoConfiguration(MyLibraryAutoConfiguration.class)
public class MyLibraryAutoConfigurationTest {
    
    @Autowired(required = false)
    private MyLibrary myLibrary;
    
    @Test
    public void shouldConfigureMyLibrary() {
        assertThat(myLibrary).isNotNull();
    }
}
```

### 🔹 Using the Starter

**In application**

```xml
<dependency>
    <groupId>com.example</groupId>
    <artifactId>my-library-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```

```yaml
# application.yml
my:
  library:
    url: http://api.example.com
    timeout: 10000
```

### 🔹 Best Practices

```java
// Follow naming convention: *-starter
// Include auto-configuration and properties
// Use conditional annotations appropriately
// Provide sensible defaults
// Document configuration properties
// Test auto-configuration thoroughly
// Use @AutoConfigureAfter for ordering
// Make dependencies optional where possible
```

---

## 🎯 Interview One-Liner

> Custom starter creates auto-configuration module with @Configuration, properties class, spring.factories registration; provides opinionated defaults and dependency management for specific functionality.
