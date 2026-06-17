### **Spring Data JPA**
## ✅ 75. What is JPA? Difference between JPA and Hibernate?

**JPA (Java Persistence API) is a Java specification for object-relational mapping that defines a standard way to map Java objects to database tables, while Hibernate is the most popular implementation of JPA that provides additional features beyond the specification.**

### 🔹 What is JPA?

**Java specification for ORM (Object-Relational Mapping)**

```java
// JPA defines standard annotations and interfaces
@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "first_name")
    private String firstName;
    
    @Column(name = "last_name")
    private String lastName;
    
    // getters and setters
}

// JPA interfaces
public interface UserRepository extends JpaRepository<User, Long> {
    // JPA provides standard CRUD operations
    User save(User user);
    Optional<User> findById(Long id);
    List<User> findAll();
    void deleteById(Long id);
}
```

### 🔹 JPA Architecture

**Standard interfaces and lifecycle**

```java
// Core JPA interfaces:
1. EntityManager - manages entity lifecycle
2. EntityManagerFactory - creates EntityManager instances
3. Persistence - bootstrap class
4. Query - represents database queries
5. TypedQuery - type-safe queries

// Entity lifecycle states:
1. New/Transient - not associated with persistence context
2. Managed/Persistent - associated with persistence context
3. Detached - was managed but no longer is
4. Removed - scheduled for deletion
```

### 🔹 What is Hibernate?

**Most popular JPA implementation**

```java
// Hibernate provides JPA implementation plus extras
@Configuration
public class HibernateConfig {
    
    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("com.example.entity");
        
        Properties hibernateProperties = new Properties();
        hibernateProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        hibernateProperties.put("hibernate.show_sql", "true");
        hibernateProperties.put("hibernate.hbm2ddl.auto", "update");
        
        sessionFactory.setHibernateProperties(hibernateProperties);
        return sessionFactory;
    }
}
```

### 🔹 JPA vs Hibernate Comparison

| Aspect      | JPA             | Hibernate |
|-------------|-----------------|-----------|
| **Type**    | Specification/Interface | Implementation |
| **Vendor**  | Oracle (Java EE) | Red Hat/JBoss |
| **Features**| Standard ORM features | JPA + Hibernate-specific features |
| **Annotations** | javax.persistence.* | javax.persistence.* + org.hibernate.* |
| **Query Language** | JPQL | JPQL + HQL + Native SQL |
| **Caching** | Basic L2 cache API | Advanced caching (Ehcache, Infinispan) |
| **Connection Pooling** | No built-in | C3P0, HikariCP integration |
| **Validation** | Bean Validation integration | Advanced validation |
| **Performance** | Standard | Optimized with extra features |

### 🔹 JPA Implementations

**Popular JPA providers**

```java
// 1. Hibernate (most popular)
<dependency>
    <groupId>org.hibernate</groupId>
    <artifactId>hibernate-core</artifactId>
</dependency>

// 2. EclipseLink
<dependency>
    <groupId>org.eclipse.persistence</groupId>
    <artifactId>eclipselink</artifactId>
</dependency>

// 3. OpenJPA
<dependency>
    <groupId>org.apache.openjpa</groupId>
    <artifactId>openjpa</artifactId>
</dependency>

// 4. DataNucleus
<dependency>
    <groupId>org.datanucleus</groupId>
    <artifactId>datanucleus-core</artifactId>
</dependency>
```

### 🔹 Hibernate-Specific Features

**Beyond JPA specification**

```java
// Hibernate-specific annotations
@Entity
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.hibernate.annotations.DynamicUpdate
public class User {
    
    @Id
    @GeneratedValue
    private Long id;
    
    @org.hibernate.annotations.Formula("(select count(*) from orders o where o.user_id = id)")
    private int orderCount;
    
    // Hibernate-specific mappings
    @org.hibernate.annotations.Type(type = "json")
    private Map<String, Object> preferences;
}

// Hibernate Query Language (HQL) - more powerful than JPQL
public interface UserRepository extends JpaRepository<User, Long> {
    
    @Query("from User u where u.createdDate > :date")
    List<User> findUsersCreatedAfter(@Param("date") LocalDate date);
    
    // Hibernate-specific features
    @Query(nativeQuery = true, value = "SELECT * FROM users WHERE age > ?1")
    List<User> findUsersOlderThan(int age);
}
```

### 🔹 When to Use JPA vs Hibernate

```java
// Use JPA when:
// - You want to be implementation-agnostic
// - Working with multiple JPA providers
// - Need standard Java EE compliance
// - Prefer specification over implementation details

// Use Hibernate when:
// - Need advanced ORM features
// - Require high performance optimizations
// - Need extensive caching capabilities
// - Want to use Hibernate-specific annotations
// - Need complex query capabilities
```

### 🔹 Spring Data JPA

**Spring's JPA abstraction layer**

```java
// Spring Data JPA provides repository abstraction
public interface UserRepository extends JpaRepository<User, Long> {
    
    // Automatic query generation
    List<User> findByLastName(String lastName);
    List<User> findByAgeGreaterThan(int age);
    
    // Custom queries
    @Query("SELECT u FROM User u WHERE u.email = ?1")
    User findByEmail(String email);
    
    // Pagination and sorting
    Page<User> findAll(Pageable pageable);
}

// Spring Boot auto-configuration
@Configuration
@EnableJpaRepositories(basePackages = "com.example.repository")
public class JpaConfig {
    // Spring Boot handles EntityManager setup
}
```

### 🔹 Best Practices

```java
// Use JPA interfaces for portability
// Leverage Hibernate for advanced features when needed
// Use Spring Data JPA for repository abstraction
// Prefer JPQL over native SQL when possible
// Implement proper caching strategies
// Use connection pooling
// Monitor query performance
// Handle transactions appropriately
```

---

## 🎯 Interview One-Liner

> JPA is ORM specification defining standard for mapping Java objects to database; Hibernate is most popular JPA implementation with additional features beyond spec.

---

## ✅ 76. What is @Entity and @Table?

**@Entity marks a Java class as a JPA entity that can be persisted to a database table, while @Table specifies the database table name and properties for the entity mapping.**

### 🔹 @Entity Annotation

**Marks class as JPA entity**

```java
@Entity
public class User {
    
    @Id
    @GeneratedValue
    private Long id;
    
    private String firstName;
    private String lastName;
    private String email;
    
    // getters and setters
}

// Requirements for @Entity:
// 1. Must have no-arg constructor (can be private)
// 2. Must not be final
// 3. Must have @Id field/method
// 4. Can be abstract
// 5. Can extend other entities
```

### 🔹 @Entity Properties

**Configuration options**

```java
@Entity(name = "UserEntity")  // JPA name (different from class name)
@Table(name = "users")        // Database table name
public class User {
    
    @Id
    private Long id;
    
    // Entity fields
}

// Entity naming strategies:
// 1. Default: Class name
// 2. @Entity(name = "..."): Custom JPA name
// 3. Table name via @Table
```

### 🔹 @Table Annotation

**Database table mapping**

```java
@Entity
@Table(
    name = "users",                    // Table name
    schema = "public",                 // Schema name
    catalog = "myapp",                 // Catalog name
    uniqueConstraints = {              // Unique constraints
        @UniqueConstraint(columnNames = {"email"}),
        @UniqueConstraint(columnNames = {"phone"})
    },
    indexes = {                        // Table indexes
        @Index(name = "idx_user_email", columnList = "email"),
        @Index(name = "idx_user_name", columnList = "first_name, last_name")
    }
)
public class User {
    
    @Id
    private Long id;
    
    @Column(unique = true)
    private String email;
    
    private String phone;
    private String firstName;
    private String lastName;
}
```

### 🔹 @Table Properties

**Detailed table configuration**

```java
// Schema and catalog
@Table(schema = "sales", catalog = "ecommerce")
public class Order { }

// Unique constraints
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"tenant_id", "order_number"}))
public class Order { }

// Indexes
@Table(indexes = {
    @Index(columnList = "status"),
    @Index(columnList = "created_date"),
    @Index(name = "idx_order_customer", columnList = "customer_id")
})
public class Order { }
```

### 🔹 Entity vs Table Naming

**Naming conventions**

```java
// Default naming (no @Table)
@Entity
public class UserProfile { }  // Maps to "userprofile" table

// Explicit table name
@Entity
@Table(name = "user_profiles")
public class UserProfile { }  // Maps to "user_profiles" table

// Schema specification
@Entity
@Table(name = "users", schema = "auth")
public class User { }  // Maps to "auth.users" table
```

### 🔹 Entity Inheritance

**Table mapping strategies**

```java
// 1. Single Table Strategy (default)
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type")
public abstract class User { }

@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends User { }

@Entity
@DiscriminatorValue("CUSTOMER")
public class Customer extends User { }

// Creates single "user" table with discriminator column

// 2. Table Per Class Strategy
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User { }

@Entity
public class Admin extends User { }  // Creates "admin" table

@Entity
public class Customer extends User { }  // Creates "customer" table

// 3. Joined Strategy
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User { }

@Entity
public class Admin extends User { }  // Creates "admin" table with FK to "user"
```

### 🔹 Entity Relationships

**Mapping associations**

```java
@Entity
@Table(name = "orders")
public class Order {
    
    @Id
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    
    @OneToMany(mappedBy = "order")
    private List<OrderItem> items;
    
    // getters and setters
}

@Entity
@Table(name = "customers")
public class Customer {
    
    @Id
    private Long id;
    
    @OneToMany(mappedBy = "customer")
    private List<Order> orders;
}
```

### 🔹 Best Practices

```java
// Use meaningful table names
// Specify schema when using multiple schemas
// Define unique constraints at table level
// Create appropriate indexes
// Use consistent naming conventions
// Consider inheritance strategies carefully
// Document entity relationships
// Use @Column for field-level constraints
```

---

## 🎯 Interview One-Liner

> @Entity marks Java class as JPA entity for persistence; @Table specifies database table name, schema, constraints, and indexes for entity mapping.

---

## ✅ 77. @Id vs @GeneratedValue

**@Id marks a field as the primary key of an entity, while @GeneratedValue specifies how the primary key value should be automatically generated by the database or JPA provider.**

### 🔹 @Id Annotation

**Primary key declaration**

```java
@Entity
public class User {
    
    @Id
    private Long id;  // Primary key field
    
    private String name;
    private String email;
    
    // getters and setters
}

// @Id can be placed on:
// - Field (field access)
// - Getter method (property access)
@Entity
public class Product {
    
    private Long id;
    private String name;
    
    @Id
    public Long getId() { return id; }  // Property access
}
```

### 🔹 @GeneratedValue Annotation

**Automatic ID generation strategies**

```java
@Entity
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Other fields
}

// Generation strategies:
// 1. AUTO - Let JPA choose (default)
// 2. IDENTITY - Database identity column
// 3. SEQUENCE - Database sequence
// 4. TABLE - Table-based ID generation
```

### 🔹 Generation Strategies

### 🔹 1. IDENTITY Strategy

**Auto-increment columns**

```java
@Entity
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // MySQL: AUTO_INCREMENT, PostgreSQL: SERIAL
    
    // Works with: MySQL, SQL Server, PostgreSQL, SQLite
    // Pros: Simple, database-managed
    // Cons: Cannot batch inserts, ID not available before flush
}
```

### 🔹 2. SEQUENCE Strategy

**Database sequences**

```java
@Entity
public class User {
    
    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "user_seq"
    )
    @SequenceGenerator(
        name = "user_seq",
        sequenceName = "user_sequence",
        allocationSize = 50  // Performance optimization
    )
    private Long id;
    
    // Works with: Oracle, PostgreSQL, H2
    // Pros: Can batch inserts, ID available before flush
    // Cons: Requires sequence creation
}
```

### 🔹 3. TABLE Strategy

**Table-based ID generation**

```java
@Entity
public class User {
    
    @Id
    @GeneratedValue(
        strategy = GenerationType.TABLE,
        generator = "id_generator"
    )
    @TableGenerator(
        name = "id_generator",
        table = "id_sequences",
        pkColumnName = "sequence_name",
        valueColumnName = "sequence_value",
        pkColumnValue = "user_id",
        allocationSize = 100
    )
    private Long id;
    
    // Works with: All databases
    // Pros: Portable across databases
    // Cons: Requires additional table, slower performance
}
```

### 🔹 4. AUTO Strategy

**JPA provider chooses**

```java
@Entity
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)  // Default
    private Long id;
    
    // Hibernate chooses based on database dialect:
    // MySQL/SQL Server: IDENTITY
    // Oracle: SEQUENCE
    // Others: TABLE
}
```

### 🔹 Composite Primary Keys

**Multiple ID fields**

```java
// Method 1: @IdClass
@Entity
@IdClass(UserId.class)
public class User {
    
    @Id
    private String tenantId;
    
    @Id
    private String userId;
    
    private String name;
}

public class UserId implements Serializable {
    private String tenantId;
    private String userId;
    // equals() and hashCode()
}

// Method 2: @EmbeddedId
@Entity
public class User {
    
    @EmbeddedId
    private UserId id;
    
    private String name;
}

@Embeddable
public class UserId implements Serializable {
    private String tenantId;
    private String userId;
    // equals() and hashCode()
}
```

### 🔹 Natural vs Surrogate Keys

**Key types**

```java
// Surrogate key (recommended)
@Entity
public class User {
    
    @Id
    @GeneratedValue
    private Long id;  // Auto-generated
    
    @Column(unique = true)
    private String email;  // Natural key as unique constraint
}

// Natural key (use with caution)
@Entity
public class Country {
    
    @Id
    @Column(length = 2)
    private String code;  // ISO country code
    
    private String name;
}
```

### 🔹 Best Practices

```java
// Use surrogate keys (Long) for most entities
// Prefer IDENTITY for simple applications
// Use SEQUENCE for batch operations
// Avoid TABLE strategy unless necessary
// Implement proper equals()/hashCode() for entities
// Use @Column(unique = true) for natural keys
// Consider UUID for distributed systems
// Document ID generation strategy
```

---

## 🎯 Interview One-Liner

> @Id marks primary key field; @GeneratedValue specifies auto-generation strategy: IDENTITY (auto-increment), SEQUENCE (database sequences), TABLE (portable), AUTO (JPA chooses).

---

## ✅ 78. findById() vs getById()

**findById() returns an Optional<T> that may or may not contain the entity, allowing for null-safe operations, while getById() throws an exception if the entity is not found, requiring explicit null checking.**

### 🔹 findById() Method

**Safe entity retrieval with Optional**

```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Inherits findById(Long id) from JpaRepository
}

// Usage
@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    public User findUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new UserNotFoundException("User not found with id: " + id);
        }
    }
    
    // Modern approach with Optional
    public User findUserByIdModern(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }
    
    // With default value
    public User findUserByIdWithDefault(Long id) {
        return userRepository.findById(id)
            .orElse(new User("Default", "User"));
    }
}
```

### 🔹 getById() Method (Deprecated)

**Direct entity retrieval (throws exception if not found)**

```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // getById() was available in older Spring Data versions
}

// Usage (if available)
@Service
public class UserService {
    
    public User findUserById(Long id) {
        try {
            User user = userRepository.getById(id);
            return user;
        } catch (EntityNotFoundException e) {
            throw new UserNotFoundException("User not found with id: " + id);
        }
    }
}
```

### 🔹 Key Differences

| Aspect | findById() | getById() |
|--------|------------|-----------|
| **Return Type** | Optional<T> | T (direct) |
| **Null Safety** | ✅ Safe | ❌ Unsafe |
| **Exception Handling** | No exception | EntityNotFoundException |
| **Availability** | Always available | Deprecated/Removed |
| **Null Checking** | Explicit required | Automatic exception |
| **Modern Code** | ✅ Preferred | ❌ Avoid |

### 🔹 EntityManager Methods

**Underlying JPA operations**

```java
@Service
public class UserService {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    // find() - similar to findById()
    public User findUser(Long id) {
        return entityManager.find(User.class, id);  // Returns null if not found
    }
    
    // getReference() - lazy loading
    public User getUserReference(Long id) {
        return entityManager.getReference(User.class, id);  // Returns proxy, throws exception if not found
    }
}
```

### 🔹 Repository Method Patterns

**Different retrieval methods**

```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // Returns Optional<T>
    Optional<User> findById(Long id);
    
    // Returns T or null
    User findUserById(Long id);
    
    // Throws exception if not found
    @Query("SELECT u FROM User u WHERE u.id = ?1")
    User getUserById(Long id);
    
    // Custom method with Optional
    Optional<User> findByEmail(String email);
}
```

### 🔹 Error Handling Patterns

**Best practices for entity retrieval**

```java
@Service
public class UserService {
    
    public User getUser(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
    
    public User getUserWithCustomMessage(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(
                String.format("User with id %d not found", id)));
    }
    
    public Optional<User> findUserOptional(Long id) {
        return userRepository.findById(id);
    }
    
    public User getUserOrDefault(Long id) {
        return userRepository.findById(id)
            .orElseGet(() -> createDefaultUser());
    }
}
```

### 🔹 Performance Considerations

**When entities are loaded**

```java
@Service
public class UserService {
    
    // findById() - hits database immediately
    public User getUserEager(Long id) {
        Optional<User> user = userRepository.findById(id);
        // Database query executed here
        return user.orElse(null);
    }
    
    // getReference() - lazy loading
    public User getUserLazy(Long id) {
        User user = entityManager.getReference(User.class, id);
        // No database query yet - proxy created
        // Query executes when accessing non-id fields
        return user;
    }
}
```

### 🔹 Testing Considerations

**Testing with Optional**

```java
@SpringBootTest
public class UserServiceTest {
    
    @Test
    public void shouldReturnUserWhenFound() {
        // Given
        User user = new User("John", "Doe");
        userRepository.save(user);
        
        // When
        Optional<User> result = userRepository.findById(user.getId());
        
        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getFirstName()).isEqualTo("John");
    }
    
    @Test
    public void shouldReturnEmptyWhenNotFound() {
        // When
        Optional<User> result = userRepository.findById(999L);
        
        // Then
        assertThat(result).isEmpty();
    }
}
```

### 🔹 Best Practices

```java
// Use findById() for safe retrieval
// Handle Optional properly with orElse/orElseThrow
// Use custom exceptions for better error messages
// Consider lazy loading for performance
// Test both found and not found scenarios
// Use @Transactional for data consistency
// Document expected behavior in API contracts
```

---

## 🎯 Interview One-Liner

> findById() returns Optional<T> (safe, modern); getById() throws exception if not found (deprecated, unsafe); prefer findById() with proper Optional handling.

---

## ✅ 79. What are derived query methods?

**Derived query methods are Spring Data JPA repository methods that automatically generate SQL queries based on method names following naming conventions, eliminating the need to write explicit JPQL or SQL queries.**

### 🔹 How Derived Queries Work

**Method name to SQL conversion**

```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // Basic queries
    List<User> findByLastName(String lastName);
    List<User> findByAgeGreaterThan(int age);
    List<User> findByActiveTrue();
    
    // Complex queries
    List<User> findByFirstNameAndLastName(String firstName, String lastName);
    List<User> findByAgeBetween(int minAge, int maxAge);
    List<User> findByEmailContaining(String emailPart);
    
    // With sorting
    List<User> findByDepartmentOrderBySalaryDesc(String department);
    
    // With pagination
    Page<User> findByDepartment(String department, Pageable pageable);
}
```

### 🔹 Query Keywords

**Supported keywords in method names**

```java
// Comparison operators
findByAgeGreaterThan(int age)           // age > ?
findByAgeLessThan(int age)              // age < ?
findByAgeGreaterThanEqual(int age)      // age >= ?
findByAgeLessThanEqual(int age)         // age <= ?
findByAgeBetween(int start, int end)    // age BETWEEN ? AND ?

// Pattern matching
findByNameLike(String name)             // name LIKE ?
findByNameNotLike(String name)          // name NOT LIKE ?
findByNameStartingWith(String prefix)   // name LIKE ?%
findByNameEndingWith(String suffix)     // name LIKE %?
findByNameContaining(String infix)      // name LIKE %?%
findByNameNotContaining(String infix)   // name NOT LIKE %?%

// Null checks
findByEmailIsNull()                    // email IS NULL
findByEmailIsNotNull()                 // email IS NOT NULL

// Boolean values
findByActiveTrue()                     // active = true
findByActiveFalse()                    // active = false

// Collection operations
findByRolesIn(List<String> roles)      // roles IN (?)
findByRolesNotIn(List<String> roles)   // roles NOT IN (?)
findByTagsContaining(String tag)       // tags CONTAINS ?

// Date/Time operations
findByCreatedDateAfter(Date date)      // createdDate > ?
findByCreatedDateBefore(Date date)     // createdDate < ?
findByCreatedDateBetween(Date start, Date end)  // createdDate BETWEEN ? AND ?
```

### 🔹 Logical Operators

**Combining conditions**

```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // AND (implicit)
    List<User> findByFirstNameAndLastName(String firstName, String lastName);
    
    // OR
    List<User> findByFirstNameOrLastName(String firstName, String lastName);
    
    // Complex combinations
    List<User> findByAgeGreaterThanAndDepartment(int age, String dept);
    List<User> findByStatusAndCreatedDateAfter(UserStatus status, Date date);
    
    // Negation
    List<User> findByStatusNot(UserStatus status);
    List<User> findByNameNotLike(String pattern);
}
```

### 🔹 Ordering and Limiting

**Sorting and result limiting**

```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // Ordering
    List<User> findByDepartmentOrderBySalaryAsc(String dept);
    List<User> findByDepartmentOrderBySalaryDesc(String dept);
    List<User> findByDepartmentOrderBySalaryDescLastNameAsc(String dept);
    
    // Limiting results
    User findFirstByDepartment(String dept);
    User findTopByDepartment(String dept);
    List<User> findFirst10ByDepartment(String dept);
    List<User> findTop5ByDepartmentOrderBySalaryDesc(String dept);
    
    // Distinct results
    List<User> findDistinctByDepartment(String dept);
}
```

### 🔹 Nested Properties

**Querying related entities**

```java
@Entity
public class User {
    @Id private Long id;
    private String name;
    
    @ManyToOne
    private Department department;
    
    @OneToMany
    private List<Address> addresses;
}

@Entity
public class Department {
    @Id private Long id;
    private String name;
}

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // Nested property queries
    List<User> findByDepartmentName(String deptName);
    List<User> findByAddressesCity(String city);
    List<User> findByDepartmentNameAndAddressesCity(String dept, String city);
}
```

### 🔹 Custom Repository Methods

**Combining derived and custom queries**

```java
@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    
    // Derived queries
    List<User> findByDepartmentAndActive(String dept, boolean active);
    
    // Custom implementation
    List<User> findUsersWithCustomLogic(String criteria);
}

public interface UserRepositoryCustom {
    List<User> findUsersWithCustomLogic(String criteria);
}

public class UserRepositoryImpl implements UserRepositoryCustom {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public List<User> findUsersWithCustomLogic(String criteria) {
        // Custom implementation
        return entityManager.createQuery("SELECT u FROM User u WHERE ...", User.class)
            .getResultList();
    }
}
```

### 🔹 Limitations and Workarounds

**When to use @Query instead**

```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // Complex queries - use @Query
    @Query("SELECT u FROM User u JOIN u.department d WHERE d.name = :dept AND u.salary > :minSalary")
    List<User> findHighEarnersInDepartment(@Param("dept") String dept, @Param("minSalary") BigDecimal salary);
    
    // Subqueries - use @Query
    @Query("SELECT u FROM User u WHERE u.salary > (SELECT AVG(u2.salary) FROM User u2)")
    List<User> findAboveAverageSalaryUsers();
    
    // Native SQL when needed
    @Query(nativeQuery = true, value = "SELECT * FROM users WHERE created_date > DATE_SUB(NOW(), INTERVAL 30 DAY)")
    List<User> findRecentlyCreatedUsers();
}
```

### 🔹 Best Practices

```java
// Use descriptive method names
// Follow Spring Data naming conventions
// Test generated queries
// Use @Query for complex queries
// Combine with custom repositories when needed
// Consider performance implications
// Document query behavior
// Use pagination for large result sets
```

---

## 🎯 Interview One-Liner

> Derived query methods auto-generate SQL from method names using keywords like findBy, And, Or, OrderBy; eliminates boilerplate queries for simple operations.

---

## ✅ 80. @Query annotation - when to use?

**@Query annotation is used in Spring Data JPA when you need to write custom JPQL, native SQL, or complex queries that cannot be expressed using derived query methods, providing full control over the generated SQL.**

### 🔹 @Query for JPQL

**Java Persistence Query Language**

```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    @Query("SELECT u FROM User u WHERE u.department.name = ?1 AND u.salary > ?2")
    List<User> findUsersByDepartmentAndMinSalary(String departmentName, BigDecimal minSalary);
    
    @Query("SELECT u FROM User u WHERE u.createdDate BETWEEN ?1 AND ?2 ORDER BY u.createdDate DESC")
    List<User> findUsersCreatedBetween(Date startDate, Date endDate);
    
    // Named parameters (recommended)
    @Query("SELECT u FROM User u WHERE u.firstName = :firstName AND u.lastName = :lastName")
    User findByFullName(@Param("firstName") String firstName, @Param("lastName") String lastName);
}
```

### 🔹 @Query for Native SQL

**Database-specific SQL queries**

```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    @Query(nativeQuery = true, value = "SELECT * FROM users WHERE created_date > DATE_SUB(NOW(), INTERVAL 30 DAY)")
    List<User> findRecentlyCreatedUsers();
    
    @Query(nativeQuery = true, value = "SELECT u.*, COUNT(o.id) as order_count " +
                                      "FROM users u LEFT JOIN orders o ON u.id = o.user_id " +
                                      "GROUP BY u.id " +
                                      "HAVING COUNT(o.id) > ?1")
    List<User> findUsersWithMinOrders(int minOrders);
    
    // With named parameters
    @Query(nativeQuery = true, value = "SELECT * FROM users WHERE department_id = :deptId AND salary > :minSalary")
    List<User> findUsersByDepartmentNative(@Param("deptId") Long deptId, @Param("minSalary") BigDecimal salary);
}
```

### 🔹 When to Use @Query

### 🔹 1. Complex Joins

**Queries involving multiple tables**

```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    @Query("SELECT u FROM User u " +
           "JOIN u.department d " +
           "JOIN u.addresses a " +
           "WHERE d.name = :deptName AND a.city = :city")
    List<User> findUsersInDepartmentAndCity(@Param("deptName") String deptName, @Param("city") String city);
    
    @Query("SELECT DISTINCT u FROM User u " +
           "LEFT JOIN FETCH u.roles r " +
           "WHERE u.active = true")
    List<User> findActiveUsersWithRoles();
}
```

### 🔹 2. Aggregations and Grouping

**Statistical queries**

```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    @Query("SELECT d.name, COUNT(u) FROM User u JOIN u.department d GROUP BY d.name")
    List<Object[]> getUserCountByDepartment();
    
    @Query("SELECT AVG(u.salary), MIN(u.salary), MAX(u.salary) FROM User u WHERE u.department.name = ?1")
    Object[] getSalaryStatistics(String departmentName);
    
    @Query("SELECT u.department.name, AVG(u.salary) FROM User u " +
           "GROUP BY u.department.name " +
           "HAVING AVG(u.salary) > ?1")
    List<Object[]> findDepartmentsWithHighAverageSalary(BigDecimal threshold);
}
```

### 🔹 3. Subqueries

**Nested queries**

```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    @Query("SELECT u FROM User u WHERE u.salary > " +
           "(SELECT AVG(u2.salary) FROM User u2 WHERE u2.department = u.department)")
    List<User> findUsersAboveDepartmentAverage();
    
    @Query("SELECT u FROM User u WHERE u.id IN " +
           "(SELECT DISTINCT o.user.id FROM Order o WHERE o.total > ?1)")
    List<User> findUsersWithHighValueOrders(BigDecimal minOrderTotal);
}
```

### 🔹 4. Updates and Deletes

**Modifying queries**

```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.salary = u.salary * ?2 WHERE u.department.name = ?1")
    int increaseSalaryByDepartment(String departmentName, BigDecimal percentage);
    
    @Modifying
    @Transactional
    @Query("DELETE FROM User u WHERE u.lastLoginDate < ?1")
    int deleteInactiveUsers(Date cutoffDate);
    
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.status = :status WHERE u.id = :id")
    int updateUserStatus(@Param("id") Long id, @Param("status") UserStatus status);
}
```

### 🔹 5. Custom Result Projections

**DTO projections**

```java
// DTO class
public class UserSummary {
    private String name;
    private String department;
    private BigDecimal salary;
    // constructor, getters
}

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    @Query("SELECT new com.example.UserSummary(u.firstName || ' ' || u.lastName, d.name, u.salary) " +
           "FROM User u JOIN u.department d WHERE u.active = true")
    List<UserSummary> findUserSummaries();
    
    @Query("SELECT u.firstName as firstName, u.lastName as lastName, d.name as department " +
           "FROM User u JOIN u.department d")
    List<UserProjection> findUserProjections();
}
```

### 🔹 6. Dynamic Queries

**Programmatically built queries**

```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // For complex dynamic queries, use Specifications or custom repository
}

public class UserSpecifications {
    
    public static Specification<User> hasNameLike(String name) {
        return (root, query, cb) -> 
            cb.like(cb.lower(root.get("firstName")), "%" + name.toLowerCase() + "%");
    }
    
    public static Specification<User> hasDepartment(String dept) {
        return (root, query, cb) -> cb.equal(root.get("department").get("name"), dept);
    }
}

// Usage
List<User> users = userRepository.findAll(
    where(hasNameLike("john")).and(hasDepartment("IT"))
);
```

### 🔹 @Query vs Derived Methods

**When to choose which approach**

```java
// Use derived methods for:
// - Simple single-table queries
// - Standard CRUD operations
// - Queries following naming conventions
// - Quick prototyping

// Use @Query for:
// - Complex joins and aggregations
// - Native SQL requirements
// - Subqueries and advanced SQL
// - Custom result projections
// - Bulk updates/deletes
// - Performance-critical queries
// - Database-specific features
```

### 🔹 Best Practices

```java
// Use named parameters (@Param) for clarity
// Prefer JPQL over native SQL when possible
// Use @Modifying for update/delete queries
// Add @Transactional to modifying queries
// Test queries thoroughly
// Consider performance implications
// Use projections for read-only operations
// Document complex queries
```

---

## 🎯 Interview One-Liner

> Use @Query for complex JPQL/native SQL queries, joins, aggregations, subqueries, bulk operations; when derived methods are insufficient for custom query requirements.
