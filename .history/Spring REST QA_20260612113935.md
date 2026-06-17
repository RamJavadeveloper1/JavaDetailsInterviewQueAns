### **Spring REST**
115. @RestController vs @Controller
116. @RequestMapping vs @GetMapping, @PostMapping
117. @PathVariable vs @RequestParam
118. @RequestBody vs @ResponseBody
119. How to handle exceptions in REST API? (@ControllerAdvice)
120. How to validate request body? (@Valid, @NotNull)
121. How to implement pagination in REST API?
122. How to implement sorting and filtering?
123. What is Content Negotiation?
124. How to version REST APIs?

## ✅ @RestController vs @Controller

**@RestController** is a specialized version of @Controller designed specifically for RESTful web services. It combines @Controller and @ResponseBody annotations.

### 🔹 @Controller

**Traditional Spring MVC controller for web applications**

```java
@Controller
public class UserController {
    
    @GetMapping("/users")
    public String getUsers(Model model) {
        // Returns view name
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users"; // Returns users.jsp or users.html
    }
    
    @PostMapping("/users")
    @ResponseBody
    public User createUser(@RequestBody User user) {
        // Need @ResponseBody for JSON response
        return userService.createUser(user);
    }
}
```

### 🔹 @RestController

**REST API controller that automatically serializes responses**

```java
@RestController
public class UserController {
    
    @GetMapping("/users")
    public List<User> getUsers() {
        // Automatically converts to JSON/XML
        return userService.getAllUsers();
    }
    
    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        // No need for @ResponseBody
        return userService.createUser(user);
    }
}
```

### 🔹 Key Differences

| Feature | @Controller | @RestController |
|---------|-------------|-----------------|
| **Purpose** | Web MVC applications | REST APIs |
| **Response** | View names | Data (JSON/XML) |
| **@ResponseBody** | Required for data | Automatic |
| **Use Case** | Server-side rendering | API endpoints |

### 🔹 When to Use Which

```java
// Use @Controller for:
@Controller
public class WebController {
    // Traditional web apps with JSP/Thymeleaf
    // Hybrid apps (MVC + REST)
}

// Use @RestController for:
@RestController
public class ApiController {
    // Pure REST APIs
    // Microservices
    // Mobile app backends
}
```

---

## 🎯 Interview One-Liner

> @RestController combines @Controller and @ResponseBody for REST APIs, automatically serializing responses to JSON/XML, while @Controller returns view names for web applications.

---

## ✅ @RequestMapping vs @GetMapping, @PostMapping

**@RequestMapping** is the general-purpose annotation for mapping HTTP requests, while @GetMapping, @PostMapping, etc., are specialized shortcuts introduced in Spring 4.3.

### 🔹 @RequestMapping

**Flexible mapping with method specification**

```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @RequestMapping(method = RequestMethod.GET)
    public List<User> getUsers() {
        return userService.getAllUsers();
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
```

### 🔹 Specialized Mappings

**Cleaner, more readable code**

```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @GetMapping
    public List<User> getUsers() {
        return userService.getAllUsers();
    }
    
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }
    
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }
    
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }
    
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
```

### 🔹 Available Specialized Annotations

```java
@GetMapping     // GET requests
@PostMapping    // POST requests
@PutMapping     // PUT requests
@DeleteMapping  // DELETE requests
@PatchMapping   // PATCH requests
```

### 🔹 Advanced @RequestMapping Features

```java
@RequestMapping(
    value = "/users",
    method = {RequestMethod.GET, RequestMethod.POST},
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE,
    headers = "X-API-Version=1",
    params = "version=1"
)
public ResponseEntity<List<User>> getUsers() {
    // Advanced mapping with constraints
}
```

### 🔹 Comparison

| Feature | @RequestMapping | @GetMapping, etc. |
|---------|-----------------|-------------------|
| **Flexibility** | High | Limited to specific method |
| **Readability** | Verbose | Clean and clear |
| **Method Support** | All HTTP methods | Single method |
| **Spring Version** | All versions | Spring 4.3+ |

---

## 🎯 Interview One-Liner

> @RequestMapping maps any HTTP method with flexible configuration, while @GetMapping, @PostMapping, etc., are concise shortcuts for specific HTTP methods introduced in Spring 4.3.

---

## ✅ @PathVariable vs @RequestParam

**@PathVariable** extracts values from the URI path, while @RequestParam extracts values from query parameters or form data.

### 🔹 @PathVariable

**Extracts values from URL path segments**

```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }
    
    @GetMapping("/{userId}/posts/{postId}")
    public Post getUserPost(
        @PathVariable Long userId,
        @PathVariable Long postId
    ) {
        return postService.getUserPost(userId, postId);
    }
    
    // With custom path variable name
    @GetMapping("/profile/{userId}")
    public User getUserProfile(@PathVariable("userId") Long id) {
        return userService.getUser(id);
    }
}
```

### 🔹 @RequestParam

**Extracts values from query parameters**

```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @GetMapping
    public List<User> getUsers(
        @RequestParam(required = false) String name,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        return userService.getUsers(name, page, size);
    }
    
    @PostMapping
    public User createUser(@RequestParam String name, @RequestParam String email) {
        // For form data (not JSON)
        User user = new User(name, email);
        return userService.createUser(user);
    }
    
    // Optional parameters
    @GetMapping("/search")
    public List<User> searchUsers(@RequestParam(required = false) String query) {
        if (query != null) {
            return userService.searchUsers(query);
        }
        return userService.getAllUsers();
    }
}
```

### 🔹 URL Examples

```http
// @PathVariable examples:
GET /api/users/123                    // id = 123
GET /api/users/123/posts/456          // userId = 123, postId = 456

// @RequestParam examples:
GET /api/users?name=John&page=0&size=20
GET /api/users/search?query=john
POST /api/users (form data: name=John&email=john@example.com)
```

### 🔹 Key Differences

| Feature | @PathVariable                  | @RequestParam |
|---------|--------------------------------|---------------|
| **Location**      | URL path      | Query parameters / Form data |
| **Required**      | Usually required     | Can be optional |
| **Use Case**   | Resource identification | Filtering, pagination |
| **URL Structure** | /users/{id}          | /users?id=123 |
| **Caching**       | Better for caching   | Less cache-friendly |

### 🔹 Best Practices

```java
// Use @PathVariable for resource IDs
@GetMapping("/users/{id}")
public User getUser(@PathVariable Long id)

// Use @RequestParam for optional filters
@GetMapping("/users")
public List<User> getUsers(@RequestParam(required = false) String status)

// Combine both
@GetMapping("/users/{userId}/posts")
public List<Post> getUserPosts(
    @PathVariable Long userId,
    @RequestParam(defaultValue = "0") int page
)
```

---

## 🎯 Interview One-Liner

> @PathVariable extracts values from URL path segments (e.g., /users/{id}), while @RequestParam extracts from query parameters (e.g., ?name=value), with PathVariable for resource IDs and RequestParam for optional filters.

---

## ✅ @RequestBody vs @ResponseBody

**@RequestBody** deserializes HTTP request body into Java objects, while @ResponseBody serializes Java objects into HTTP response body.

### 🔹 @RequestBody

**Converts JSON/XML request body to Java object**

```java
@RestController
public class UserController {
    
    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        // JSON: {"name":"John", "email":"john@example.com"}
        // Automatically converted to User object
        return userService.createUser(user);
    }
    
    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        // Partial update or full update
        return userService.updateUser(id, user);
    }
    
    // With validation
    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody User user) {
        User savedUser = userService.register(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }
}
```

### 🔹 @ResponseBody

**Converts Java object to JSON/XML response**

```java
@Controller
public class UserController {
    
    @GetMapping("/users")
    @ResponseBody
    public List<User> getUsers() {
        // Returns JSON array of users
        return userService.getAllUsers();
    }
    
    @GetMapping("/users/{id}")
    @ResponseBody
    public User getUser(@PathVariable Long id) {
        // Returns JSON user object
        return userService.getUser(id);
    }
}
```

### 🔹 With @RestController

**@ResponseBody is implicit**

```java
@RestController  // @Controller + @ResponseBody
public class UserController {
    
    @GetMapping("/users")
    public List<User> getUsers() {
        // No need for @ResponseBody
        return userService.getAllUsers();
    }
    
    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        // @RequestBody still needed for input
        return userService.createUser(user);
    }
}
```

### 🔹 Content Types

```java
// JSON (default)
@PostMapping("/users")
public User createUser(@RequestBody User user) {
    // Content-Type: application/json
}

// Form data
@PostMapping("/users")
public User createUser(@ModelAttribute User user) {
    // Content-Type: application/x-www-form-urlencoded
}

// Custom deserializer
@PostMapping("/users")
public User createUser(@RequestBody String json) {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.readValue(json, User.class);
}
```

### 🔹 Error Handling

```java
@PostMapping("/users")
public ResponseEntity<User> createUser(@RequestBody User user) {
    try {
        User saved = userService.createUser(user);
        return ResponseEntity.ok(saved);
    } catch (Exception e) {
        return ResponseEntity.badRequest().build();
    }
}
```

### 🔹 Key Differences

| Feature      | @RequestBody | @ResponseBody |
|--------------|---------------------|----------------|
| **Direction**| Input (request)     | Output (response) |
| **Purpose**  | Deserialize JSON/XML to Java | Serialize Java to JSON/XML |
| **Required** | Explicit annotation | Implicit in @RestController|
| **Use Case** | POST/PUT requests   | All responses in REST APIs |

---

## 🎯 Interview One-Liner

> @RequestBody deserializes HTTP request body (JSON/XML) into Java objects, while @ResponseBody serializes Java objects into HTTP response body; @RestController makes @ResponseBody implicit.
---

## ✅ How to Handle Exceptions in REST API? (@ControllerAdvice)

**@ControllerAdvice** provides global exception handling for Spring MVC controllers, allowing centralized error handling and consistent error responses.

### 🔹 Basic @ControllerAdvice

**Global exception handler**

```java
@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Internal Server Error",
            ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            "Resource Not Found",
            ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
```

### 🔹 Custom Exception Classes

**Define specific exceptions**

```java
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}

public class ValidationException extends RuntimeException {
    private Map<String, String> errors;
    
    public ValidationException(Map<String, String> errors) {
        this.errors = errors;
    }
    
    public Map<String, String> getErrors() {
        return errors;
    }
}
```

### 🔹 Specific Exception Handlers

**Handle different exception types**

```java
@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationExceptions(
        MethodArgumentNotValidException ex) {
        
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> 
            errors.put(error.getField(), error.getDefaultMessage()));
        
        ValidationErrorResponse errorResponse = new ValidationErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            "Validation Failed",
            errors
        );
        return ResponseEntity.badRequest().body(errorResponse);
    }
    
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleJsonParseException(
        HttpMessageNotReadableException ex) {
        
        ErrorResponse error = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            "Invalid JSON",
            "Request body contains invalid JSON"
        );
        return ResponseEntity.badRequest().body(error);
    }
    
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDatabaseExceptions(
        DataIntegrityViolationException ex) {
        
        ErrorResponse error = new ErrorResponse(
            HttpStatus.CONFLICT.value(),
            "Data Integrity Error",
            "Database constraint violation"
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
}
```

### 🔹 Error Response Classes

**Structured error responses**

```java
public class ErrorResponse {
    private int status;
    private String error;
    private String message;
    private LocalDateTime timestamp;
    
    // Constructor, getters, setters
}

public class ValidationErrorResponse extends ErrorResponse {
    private Map<String, String> fieldErrors;
    
    // Constructor, getters, setters
}
```

### 🔹 Controller-Specific Advice

**Limit scope to specific controllers**

```java
@ControllerAdvice(assignableTypes = UserController.class)
public class UserExceptionHandler {
    // Only handles exceptions from UserController
}

@ControllerAdvice("com.example.api")
public class ApiExceptionHandler {
    // Only handles exceptions from controllers in com.example.api package
}
```

### 🔹 Response Status Annotations

**Automatic HTTP status codes**

```java
@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(ResourceNotFoundException ex) {
        return new ErrorResponse("Resource not found: " + ex.getMessage());
    }
    
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleUnauthorized(UnauthorizedException ex) {
        return new ErrorResponse("Unauthorized access");
    }
}
```

### 🔹 Logging and Monitoring

**Add logging for debugging**

```java
@ControllerAdvice
public class GlobalExceptionHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex, WebRequest request) {
        logger.error("Exception occurred: ", ex);
        
        ErrorResponse error = new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Internal Server Error",
            "An unexpected error occurred"
        );
        
        // In production, don't expose internal details
        if (isDevelopment()) {
            error.setDetails(ex.getMessage());
        }
        
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
```

---

## 🎯 Interview One-Liner

> @ControllerAdvice enables global exception handling in Spring REST APIs, allowing centralized error responses with @ExceptionHandler methods for different exception types like validation, not found, and server errors.
---

## ✅ How to Validate Request Body? (@Valid, @NotNull)

**Spring Boot uses Bean Validation (JSR-303/JSR-349) with @Valid annotation to validate request bodies, ensuring data integrity and providing meaningful error messages.**

### 🔹 Basic Validation

**Using @Valid and validation annotations**

```java
public class User {
    @NotNull(message = "Name cannot be null")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;
    
    @NotNull(message = "Email cannot be null")
    @Email(message = "Email should be valid")
    private String email;
    
    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 120, message = "Age must be less than 120")
    private Integer age;
    
    // getters and setters
}
```

```java
@RestController
public class UserController {
    
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }
}
```

### 🔹 Common Validation Annotations

**Built-in validation constraints**

```java
public class Product {
    @NotNull
    @NotEmpty(message = "Name cannot be empty")
    @NotBlank(message = "Name cannot be blank")
    private String name;
    
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @Digits(integer = 5, fraction = 2, message = "Price format invalid")
    private BigDecimal price;
    
    @Past(message = "Manufacture date must be in the past")
    private LocalDate manufactureDate;
    
    @Future(message = "Expiry date must be in the future")
    private LocalDate expiryDate;
    
    @Pattern(regexp = "^[A-Z]{2}\\d{10}$", message = "Invalid product code format")
    private String productCode;
    
    @Size(min = 1, max = 10, message = "Tags must have 1-10 items")
    private List<String> tags;
}
```

### 🔹 Custom Validation

**Create custom validation annotations**

```java
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneValidator.class)
public @interface ValidPhone {
    String message() default "Invalid phone number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

public class PhoneValidator implements ConstraintValidator<ValidPhone, String> {
    @Override
    public void initialize(ValidPhone constraintAnnotation) {
        // Initialization logic
    }
    
    @Override
    public boolean isValid(String phone, ConstraintValidatorContext context) {
        if (phone == null) return true;
        return phone.matches("^\\+?[1-9]\\d{1,14}$");
    }
}
```

```java
public class User {
    @ValidPhone
    private String phoneNumber;
}
```

### 🔹 Nested Validation

**Validate nested objects**

```java
public class Order {
    @NotNull
    @Valid  // Validates the nested Address object
    private Address shippingAddress;
    
    @NotEmpty
    @Valid  // Validates each Item in the list
    private List<OrderItem> items;
}

public class Address {
    @NotBlank
    private String street;
    
    @NotBlank
    private String city;
    
    @Pattern(regexp = "\\d{5}", message = "Invalid zip code")
    private String zipCode;
}
```

### 🔹 Group Validation

**Different validation rules for different scenarios**

```java
public interface Create {}
public interface Update {}

public class User {
    @NotNull(groups = {Create.class, Update.class})
    private String name;
    
    @NotNull(groups = Create.class)  // Required only for creation
    private String password;
    
    @NotNull(groups = Update.class)  // Required only for updates
    private Long id;
}
```

```java
@PostMapping("/users")
public User createUser(@Validated(Create.class) @RequestBody User user) {
    return userService.createUser(user);
}

@PutMapping("/users/{id}")
public User updateUser(@PathVariable Long id, @Validated(Update.class) @RequestBody User user) {
    user.setId(id);
    return userService.updateUser(user);
}
```

### 🔹 Validation Configuration

**Enable validation in Spring Boot**

```java
@Configuration
public class ValidationConfig {
    // Validation is automatically enabled in Spring Boot
    // Add custom validators or configuration here
}
```

### 🔹 Error Handling

**Handle validation errors**

```java
@ControllerAdvice
public class ValidationExceptionHandler {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
        MethodArgumentNotValidException ex) {
        
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage()));
        
        return ResponseEntity.badRequest().body(errors);
    }
}
```

---

## 🎯 Interview One-Liner

> Use @Valid annotation with JSR-303 validation annotations (@NotNull, @Size, @Email, etc.) on request body objects to validate input data, with @ControllerAdvice handling validation errors for consistent API responses.

---

## ✅ How to Implement Pagination in REST API?

**Pagination divides large result sets into smaller chunks, improving performance and user experience by returning data in pages.**

### 🔹 Basic Pagination

**Page and size parameters**

```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @GetMapping
    public ResponseEntity<Page<User>> getUsers(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "asc") String sortDir
    ) {
        Pageable pageable = PageRequest.of(page, size, 
            Sort.by(Sort.Direction.fromString(sortDir), sortBy));
        
        Page<User> users = userService.getUsers(pageable);
        return ResponseEntity.ok(users);
    }
}
```

### 🔹 Spring Data Page

**Using Page interface**

```java
public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findAll(Pageable pageable);
}

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    public Page<User> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}
```

### 🔹 Custom Pagination Response

**Structured pagination metadata**

```java
public class PaginationResponse<T> {
    private List<T> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean first;
    private boolean last;
    
    // Constructor and getters
}
```

```java
@RestController
public class UserController {
    
    @GetMapping("/users")
    public ResponseEntity<PaginationResponse<User>> getUsers(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Page<User> userPage = userService.getUsers(PageRequest.of(page, size));
        
        PaginationResponse<User> response = new PaginationResponse<>(
            userPage.getContent(),
            userPage.getNumber(),
            userPage.getSize(),
            userPage.getTotalElements(),
            userPage.getTotalPages(),
            userPage.isFirst(),
            userPage.isLast()
        );
        
        return ResponseEntity.ok(response);
    }
}
```

### 🔹 Cursor-Based Pagination

**For large datasets and real-time data**

```java
@RestController
public class FeedController {
    
    @GetMapping("/feed")
    public ResponseEntity<CursorPage<Post>> getFeed(
        @RequestParam(required = false) String cursor,
        @RequestParam(defaultValue = "10") int limit
    ) {
        CursorPage<Post> posts = postService.getFeed(cursor, limit);
        return ResponseEntity.ok(posts);
    }
}
```

### 🔹 Offset vs Cursor Pagination

| Feature | Offset Pagination | Cursor Pagination |
|---------|-------------------|-------------------|
| **Performance** | Degrades with large offsets | Consistent performance |
| **Real-time** | Shows outdated data | Shows current data |
| **Duplicates** | Possible with insertions | No duplicates |
| **Implementation** | Simple | Complex |
| **Use Case** | Small datasets | Large, dynamic datasets |

### 🔹 Pagination Links

**HATEOAS-style pagination**

```java
public class PaginationLinks {
    private String first;
    private String prev;
    private String self;
    private String next;
    private String last;
    
    // Constructor and getters
}
```

```java
@RestController
public class UserController {
    
    @GetMapping("/users")
    public ResponseEntity<PagedModel<User>> getUsers(Pageable pageable, PagedResourcesAssembler assembler) {
        Page<User> users = userService.getUsers(pageable);
        PagedModel<User> pagedModel = assembler.toModel(users);
        return ResponseEntity.ok(pagedModel);
    }
}
```

### 🔹 Best Practices

```java
// Reasonable defaults
@RequestParam(defaultValue = "0") int page,
@RequestParam(defaultValue = "20") int size,  // Not too large
@RequestParam(defaultValue = "id") String sortBy

// Size limits
if (size > 100) {
    size = 100;  // Maximum page size
}

// Validate sort fields
Set<String> allowedSortFields = Set.of("id", "name", "email", "createdDate");
if (!allowedSortFields.contains(sortBy)) {
    sortBy = "id";
}
```

---

## 🎯 Interview One-Liner

> Implement pagination using Spring Data's Pageable with @RequestParam for page/size, returning Page<T> or custom PaginationResponse with metadata like totalElements, totalPages, and navigation links.

---

## ✅ How to Implement Sorting and Filtering?

**Sorting and filtering allow clients to customize data retrieval, improving API usability and performance.**

### 🔹 Basic Sorting

**Sort by single field**

```java
@RestController
@RequestMapping("/api/products")
public class ProductController {
    
    @GetMapping
    public ResponseEntity<Page<Product>> getProducts(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "asc") String sortDir
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<Product> products = productService.getProducts(pageable);
        return ResponseEntity.ok(products);
    }
}
```

### 🔹 Multiple Field Sorting
**Sort by multiple fields**

```java
@GetMapping("/products")
public ResponseEntity<Page<Product>> getProducts(
    @RequestParam(defaultValue = "name") String[] sort
) {
    List<Order> orders = new ArrayList<>();
    for (String sortParam : sort) {
        String[] parts = sortParam.split(",");
        String property = parts[0];
        Sort.Direction direction = parts.length > 1 && "desc".equalsIgnoreCase(parts[1]) 
            ? Sort.Direction.DESC : Sort.Direction.ASC;
        orders.add(new Order(direction, property));
    }
    
    Pageable pageable = PageRequest.of(0, 10, Sort.by(orders));
    Page<Product> products = productService.getProducts(pageable);
    return ResponseEntity.ok(products);
}

// Usage: /products?sort=name,asc&sort=price,desc
```

### 🔹 Dynamic Filtering

**Filter by multiple criteria**

```java
@RestController
public class ProductController {
    
    @GetMapping("/products")
    public ResponseEntity<Page<Product>> getProducts(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) BigDecimal minPrice,
        @RequestParam(required = false) BigDecimal maxPrice,
        @RequestParam(required = false) String category,
        Pageable pageable
    ) {
        Specification<Product> spec = Specification.where(null);
        
        if (name != null) {
            spec = spec.and((root, query, cb) -> 
                cb.like(cb.lower(root.get("name")), " b%" + name.toLowerCase() + "%"));
        }
        
        if (minPrice != null) {
            spec = spec.and((root, query, cb) -> 
                cb.greaterThanOrEqualTo(root.get("price"), minPrice));
        }
        
        if (maxPrice != null) {
            spec = spec.and((root, query, cb) -> 
                cb.lessThanOrEqualTo(root.get("price"), maxPrice));
        }
        
        if (category != null) {
            spec = spec.and((root, query, cb) -> 
                cb.equal(root.get("category"), category));
        }
        
        Page<Product> products = productRepository.findAll(spec, pageable);
        return ResponseEntity.ok(products);
    }
}
```
### 🔹 JPA Specifications
**Reusable filter specifications**

```java
public class ProductSpecifications {
    
    public static Specification<Product> hasName(String name) {
        return (root, query, cb) -> 
            cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }
    
    public static Specification<Product> priceBetween(BigDecimal min, BigDecimal max) {
        return (root, query, cb) -> 
            cb.between(root.get("price"), min, max);
    }
    
    public static Specification<Product> hasCategory(String category) {
        return (root, query, cb) -> 
            cb.equal(root.get("category"), category);
    }
}
```

```java
@GetMapping("/products")
public ResponseEntity<Page<Product>> getProducts(ProductFilter filter, Pageable pageable) {
    Specification<Product> spec = Specification.where(null);
    
    if (filter.getName() != null) {
        spec = spec.and(ProductSpecifications.hasName(filter.getName()));
    }
    
    if (filter.getMinPrice() != null || filter.getMaxPrice() != null) {
        spec = spec.and(ProductSpecifications.priceBetween(
            filter.getMinPrice() != null ? filter.getMinPrice() : BigDecimal.ZERO,
            filter.getMaxPrice() != null ? filter.getMaxPrice() : BigDecimal.valueOf(Long.MAX_VALUE)
        ));
    }
    
    Page<Product> products = productRepository.findAll(spec, pageable);
    return ResponseEntity.ok(products);
}
```

### 🔹 QueryDSL Integration

**Type-safe queries**

```java
<dependency>
    <groupId>com.querydsl</groupId>
    <artifactId>querydsl-jpa</artifactId>
</dependency>
```

```java
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    @Override
    public Page<Product> findProducts(ProductSearchCriteria criteria, Pageable pageable) {
        QProduct product = QProduct.product;
        
        BooleanBuilder builder = new BooleanBuilder();
        
        if (criteria.getName() != null) {
            builder.and(product.name.containsIgnoreCase(criteria.getName()));
        }
        
        if (criteria.getMinPrice() != null) {
            builder.and(product.price.goe(criteria.getMinPrice()));
        }
        
        if (criteria.getCategory() != null) {
            builder.and(product.category.eq(criteria.getCategory()));
        }
        
        return new JPAQuery<Product>(entityManager)
            .from(product)
            .where(builder)
            .orderBy(product.name.asc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetchResults();
    }
}
```

### 🔹 Filter DTO

**Structured filter object**

```java
public class ProductFilter {
    private String name;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private String category;
    private LocalDate createdAfter;
    private LocalDate createdBefore;
    
    // getters and setters
}
```

### 🔹 Validation and Security

**Validate filter parameters**

```java
@GetMapping("/products")
public ResponseEntity<Page<Product>> getProducts(
    @Valid ProductFilter filter,
    Pageable pageable
) {
    // Validate and sanitize filter parameters
    if (filter.getMaxPrice() != null && filter.getMaxPrice().compareTo(BigDecimal.valueOf(1000000)) > 0) {
        throw new IllegalArgumentException("Price too high");
    }
    
    // Apply filters...
 }
```

---

## 🎯 Interview One-Liner

> Implement sorting with Spring Data's Sort and Pageable, filtering using JPA Specifications or QueryDSL for dynamic queries, allowing clients to filter by multiple criteria with request parameters.

---

## ✅ What is Content Negotiation?

**Content Negotiation is the process of selecting the best representation of a resource based on client preferences and server capabilities, typically for format (JSON/XML) and language.**

### 🔹 Accept Header

**Client specifies preferred content type**

```http
GET /api/users/123 HTTP/1.1
Accept: application/json
Host: api.example.com

GET /api/users/123 HTTP/1.1  
Accept: application/xml
Host: api.example.com
```

### 🔹 Spring Content Negotiation

**Automatic format selection**

```java
@Configuration
public                          class WebConfig implements WebMvcConfigurer {
    
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer
            .favorParameter(true)  // Allow format parameter: /users?format=json
            .parameterName("format")
            .ignoreAcceptHeader(false)
            .defaultContentType(MediaType.APPLICATION_JSON)
            .mediaType("json", MediaType.APPLICATION_JSON)
            .mediaType("xml", MediaType.APPLICATION_XML);
    }
}
```

### 🔹 Controller Methods

**Same method returns different formats**

```java
@RestController
public class UserController {
    
    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Long id) {
        // Returns JSON by default
        // Returns XML if Accept: application/xml
        return userService.getUser(id);
    }
    
    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getUsersJson() {
        return userService.getAllUsers();
    }
    
    @GetMapping(value = "/users", produces = MediaType.APPLICATION_XML_VALUE)
    public Users getUsersXml() {
        List<User> users = userService.getAllUsers();
        return new Users(users);  // Custom XML wrapper
    }
}
```

### 🔹 Custom Message Converters

**Support custom formats**

```java
@Configuration;
public class WebConfig implements WebMvcConfigurer {
    
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new CsvHttpMessageConverter());  // Custom CSV converter
    }
}
```
### 🔹 Content-Type Negotiation
**Request body format**

```java
@PostMapping("/users")
public User createUser(@RequestBody User user) {
    // Accepts JSON: Content-Type: application/json
    // Accepts XML: Content-Type: application/xml
    return userService.createUser(user);
}
```

### 🔹 Language Negotiation
**Internationalization support**

```java
@Configuration
public class LocaleConfig implements WebMvcConfigurer {
    
    @Bean
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();
        resolver.setDefaultLocale(Locale.US);
        return resolver;
    }
}
```

```http
GET /api/users HTTP/1.1
Accept-Language: fr-FR,fr;q=0.9,en;q=0.8
```

### 🔹 Custom Content Negotiation Strategy

**Advanced negotiation logic**

```java
@Component
public class CustomContentNegotiationStrategy implements ContentNegotiationStrategy {
    
    @Override
    public List<MediaType> resolveMediaTypes(NativeWebRequest request) {
        String format = request.getParameter("format");
        if ("csv".equals(format)) {
            return Collections.singletonList(MediaType.parseMediaType("text/csv"));
        }
        return Collections.singletonList(MediaType.APPLICATION_JSON);
    }
}
```
### 🔹 ResponseEntity with Content Type

**Explicit content type control**

```java
@GetMapping("/report")
public ResponseEntity<byte[]> getReport(
    @RequestParam(defaultValue = "pdf") String format
) {
    byte[] report;
    MediaType contentType;
    
    if ("pdf".equals(format)) {
        report = reportService.generatePdf();
        contentType = MediaType.APPLICATION_PDF;
    } else {
        report = reportService.generateCsv();
        contentType = MediaType.TEXT_PLAIN;
    }
    
    return ResponseEntity.ok()
        .contentType(contentType)
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report." + format)
        .body(report);
}
```

### 🔹 Best Practices

```java
// Default to JSON
@Configuration
public class WebConfig {
    // Configure content negotiation
}

// Support common formats
@GetMapping(value = "/data", 
    produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public Data getData() {
    return dataService.getData();
}

// Use appropriate HTTP status codes
@PostMapping("/users")
public ResponseEntity<User> createUser(@RequestBody User user) {
    User saved = userService.createUser(user);
    return ResponseEntity.created(URI.create("/users/" + saved.getId()))
        .contentType(MediaType.APPLICATION_JSON)
        .body(saved);
}
```

---

## 🎯 Interview One-Liner

> Content Negotiation selects the best resource representation (JSON/XML) based on client Accept headers and server capabilities, with Spring automatically handling format conversion using HttpMessageConverters.

---

## ✅ How to Version REST APIs?

**API versioning ensures backward compatibility when evolving APIs, allowing clients to use different versions simultaneously.**

### 🔹 URI Versioning

**Version in URL path**

```java
@RestController
@RequestMapping("/api/v1/users")
public class UserControllerV1 {
    
    @GetMapping
    public List<UserV1> getUsers() {
        return userService.getUsersV1();
    }
}

@RestController
@RequestMapping("/api/v2/users") 
public class UserControllerV2 {
    
    @GetMapping
    public List<UserV2> getUsers() {
        return userService.getUsersV2();
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

// Usage: /api/users?version=v2
```
### 🔹 Header Versioning
**Version in custom header**

```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @GetMapping
    public ResponseEntity<?> getUsers(@RequestHeader(value = "X-API-Version", defaultValue = "v1") String version) {
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
    
    @GetMapping(produces = "application/vnd.example.v1+json")
    public List<UserV1> getUsersV1() {
        return userService.getUsersV1();
    }
    
    @GetMapping(produces = "application/vnd.example.v2+json")
    public List<UserV2> getUsersV2() {
        return userService.getUsersV2();
    }
}

// Usage: GET /api/users
// Accept: application/vnd.example.v2+json
```

### 🔹 Content Negotiation with Versioning

**Combining format and version**

```java
@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer
            .favorParameter(true)
            .parameterName("version")
            .mediaType("v1", MediaType.parseMediaType("application/vnd.example.v1+json"))
            .mediaType("v2", MediaType.parseMediaType("application/vnd.example.v2+json"));
    }
}
```

### 🔹 Version Strategy Comparison

| Strategy | Pros | Cons |
|----------|------|------|
| **URI** | Explicit, cacheable | Breaks REST principles |
| **Query Param** | Flexible | Clutters URLs |
| **Header** | Clean URLs | Less visible |
| **Accept Header** | RESTful | Complex |

### 🔹 Semantic Versioning

**Version numbering convention**

```java
// Major.Minor.Patch
// v1.0.0 - Initial release
// v1.1.0 - Backward compatible additions
// v2.0.0 - Breaking changes
// v2.1.0 - New features
// v2.1.1 - Bug fixes
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
    public ResponseEntity<List<UserV1>> getUsers() {
        // Add deprecation warning header
        return ResponseEntity.ok()
            .header("X-Deprecation-Notice", "This API version is deprecated. Use /api/v2/users instead.")
            .body(userService.getUsersV1());
    }
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
        
        ApiVersion apiVersion = method.getAnnotation(ApiVersion.class);
        if (apiVersion != null) {
            // Add version to mapping
            info = RequestMappingInfo.paths("/api/" + apiVersion.value())
                .build().combine(info);
        }
        
        return info;
    }
}
```
### 🔹 Best Practices

```java
// Use semantic versioning
// Document version changes clearly
// Support multiple versions simultaneously
// Set deprecation timelines
// Monitor version usage
// Plan migration strategies

@RestController
@RequestMapping("/api")
public class ApiController {
    
    @GetMapping("/info")
    public ApiInfo getApiInfo() {
        return new ApiInfo(
            "User API",
            Arrays.asList("v1", "v2"),
            "v2"  // Current version
        );
    }
}
```
---
## 🎯 Interview One-Liner

> API versioning can be implemented via URI paths (/api/v1/users), custom headers (X-API-Version), query parameters (?version=v2), or Accept headers, with URI versioning being most explicit but least RESTful.

### **REST Basics**

## ✅ 109. What is REST? REST principles?

**REST (Representational State Transfer)** is an architectural style for designing networked applications, introduced by Roy Fielding in his 2000 doctoral dissertation. It treats resources as the key abstraction, where each resource is identified by a URI and can be manipulated using standard HTTP methods.

### 🔹 REST Principles

**Six fundamental constraints that define REST architecture:**

### 🔹 1. Client-Server Architecture

**Separation of concerns between client and server**

```javascript
// Client handles user interface and user state
// Server handles data storage and business logic
// They can evolve independently

Client (Browser/SPA) <---HTTP---> Server (API)
    UI Logic                     Business Logic
    State Management             Data Storage
```

### 🔹 2. Stateless

**Each request contains all information needed**

```javascript
// Server doesn't store client state between requests
// All state is managed by the client

// Bad: Server remembers user session
GET /api/users/123  // Server looks up session

// Good: Client sends authentication with each request
GET /api/users/123
Authorization: Bearer <token>
```

### 🔹 3. Cacheable

**Responses must be cacheable when possible**

```http
// Cache-Control headers enable caching
HTTP/1.1 200 OK
Cache-Control: max-age=3600
ETag: "abc123"

GET /api/products  // Can be cached for 1 hour
```

### 🔹 4. Uniform Interface

**Consistent interface between components**

- **Resource Identification**: Resources identified by URIs
- **Resource Manipulation through Representations**: Use representations (JSON/XML)
- **Self-descriptive Messages**: Messages contain enough info for processing
- **Hypermedia as the Engine of Application State (HATEOAS)**: Navigate via links

### 🔹 5. Layered System

**Hierarchical layers with no knowledge of inner layers**

```javascript
Client <-> Load Balancer <-> API Gateway <-> Application Server <-> Database
```

### 🔹 6. Code on Demand (Optional)

**Server can send executable code to client**

```javascript
// JavaScript sent from server to client
// Enables dynamic behavior
```

### 🔹 REST vs SOAP

| Feature | REST | SOAP |
|---------|------|------|
| **Protocol** | HTTP | Any protocol |
| **Format** | JSON/XML | XML only |
| **Operations** | CRUD operations | Custom operations |
| **State** | Stateless | Can be stateful |
| **Caching** | Built-in | Limited |
| **Complexity** | Simple | Complex |

### 🔹 REST Resource Examples

```javascript
// Resources are nouns, not verbs
GET    /users        // Get all users
GET    /users/123    // Get user with ID 123
POST   /users        // Create new user
PUT    /users/123    // Update user 123
DELETE /users/123    // Delete user 123
```

---

## 🎯 Interview One-Liner

> REST is an architectural style using HTTP for resource manipulation with principles: Client-Server, Stateless, Cacheable, Uniform Interface, Layered System, and optional Code-on-Demand.

---

## ✅ 110. What are HTTP methods? (GET, POST, PUT, PATCH, DELETE)

**HTTP methods (verbs) define the action to be performed on a resource identified by a URI. REST APIs use these methods to implement CRUD operations.**

### 🔹 GET

**Retrieve resource representation**

```http
GET /api/users HTTP/1.1
Host: api.example.com
Accept: application/json

// Response
HTTP/1.1 200 OK
Content-Type: application/json

[
  {"id": 1, "name": "John"},
  {"id": 2, "name": "Jane"}
]
```

**Characteristics:**
- Safe (no side effects)
- Idempotent
- Cacheable
- Can have query parameters

### 🔹 POST

**Create new resource**

```http
POST /api/users HTTP/1.1
Host: api.example.com
Content-Type: application/json

{"name": "Bob", "email": "bob@example.com"}

// Response
HTTP/1.1 201 Created
Location: /api/users/3
Content-Type: application/json

{"id": 3, "name": "Bob", "email": "bob@example.com"}
```

**Characteristics:**
- Not safe (creates/modifies)
- Not idempotent
- Not cacheable
- Sends data in request body

### 🔹 PUT

**Update entire resource (full replacement)**

```http
PUT /api/users/123 HTTP/1.1
Host: api.example.com
Content-Type: application/json

{"id": 123, "name": "John Updated", "email": "john.updated@example.com"}

// Response
HTTP/1.1 200 OK
Content-Type: application/json

{"id": 123, "name": "John Updated", "email": "john.updated@example.com"}
```

**Characteristics:**
- Not safe
- Idempotent (multiple calls = same result)
- Not cacheable
- Requires full resource representation

### 🔹 PATCH

**Partial resource update**

```http
PATCH /api/users/123 HTTP/1.1
Host: api.example.com
Content-Type: application/json

{"email": "john.new@example.com"}

// Response
HTTP/1.1 200 OK
Content-Type: application/json

{"id": 123, "name": "John", "email": "john.new@example.com"}
```

**Characteristics:**
- Not safe
- Not necessarily idempotent
- Not cacheable
- Sends only changed fields

### 🔹 DELETE

**Remove resource**

```http
DELETE /api/users/123 HTTP/1.1
Host: api.example.com

// Response
HTTP/1.1 204 No Content
```

**Characteristics:**
- Not safe
- Idempotent (deleting non-existent = same result)
- Not cacheable

### 🔹 Other HTTP Methods

```http
HEAD    /api/users/123    // Same as GET but no body
OPTIONS /api/users       // Get allowed methods
TRACE   /api/users       // Echo request for debugging
CONNECT /api/users       // Establish tunnel
```

### 🔹 Method Comparison

| Method | Safe | Idempotent | Cacheable | Use Case |
|--------|------|------------|-----------|----------|
| **GET** | ✅ | ✅ | ✅ | Read data |
| **POST** | ❌ | ❌ | ❌ | Create resource |
| **PUT** | ❌ | ✅ | ❌ | Full update |
| **PATCH** | ❌ | ❌ | ❌ | Partial update |
| **DELETE** | ❌ | ✅ | ❌ | Delete resource |

### 🔹 Best Practices

```javascript
// Use nouns for resources
GET /users     // Good
GET /getUsers  // Bad

// Use plural nouns
GET /users/123 // Good
GET /user/123  // Bad

// Use sub-resources for relationships
GET /users/123/posts // User's posts
GET /posts?userId=123 // Alternative
```

---

## 🎯 Interview One-Liner

> HTTP methods: GET (read), POST (create), PUT (full update), PATCH (partial update), DELETE (remove); GET/HEAD are safe and idempotent, PUT/DELETE are idempotent but not safe, POST/PATCH are neither.
---

## ✅ 111. What is idempotency? Which methods are idempotent?

**Idempotency means that multiple identical requests have the same effect as a single request. It's crucial for building reliable distributed systems and handling network failures.**

### 🔹 Definition

**Property where f(f(x)) = f(x)**

```javascript
// Mathematical example
multiplyByZero(5) = 0
multiplyByZero(multiplyByZero(5)) = 0  // Same result

// HTTP example
DELETE /users/123  // User deleted
DELETE /users/123  // Same result (user still deleted)
```

### 🔹 Why Idempotency Matters

**Reliability in distributed systems**

```javascript
// Network failure scenario
Client -> Server: DELETE /users/123
[Network timeout - client doesn't know if request succeeded]

Client -> Server: DELETE /users/123 (retry)
// Without idempotency: might delete different user
// With idempotency: safe to retry
```

### 🔹 Idempotent HTTP Methods

### 🔹 GET

**Safe and idempotent - multiple reads return same data**

```http
GET /api/users/123
GET /api/users/123
GET /api/users/123
// All return the same user data
```

### 🔹 PUT

**Idempotent - full replacement of resource**

```http
PUT /api/users/123
{"name": "John", "email": "john@example.com"}

PUT /api/users/123  
{"name": "John", "email": "john@example.com"}
// Resource ends up in same state
```

### 🔹 DELETE

**Idempotent - deleting non-existent resource is same as deleting existing**

```http
DELETE /api/users/123  // User deleted
DELETE /api/users/123  // 404 or 204, but user still gone
```

### 🔹 HEAD

**Idempotent - same as GET without body**

```http
HEAD /api/users/123
HEAD /api/users/123
// Same headers returned
```

### 🔹 OPTIONS

**Idempotent - returns allowed methods**

```http
OPTIONS /api/users
OPTIONS /api/users
// Same CORS headers returned
```

### 🔹 Non-Idempotent Methods

### 🔹 POST

**Not idempotent - creates new resource each time**

```http
POST /api/users
{"name": "John"}

POST /api/users
{"name": "John"}
// Creates two different users with different IDs
```

### 🔹 PATCH
**Not necessarily idempotent - depends on implementation**

```http
PATCH /api/users/123
{"counter": 1}  // Increment counter

PATCH /api/users/123
{"counter": 1}  // Counter becomes 2, not 1
```
### 🔹 Implementing Idempotency for POST

**Use idempotency keys**

```http
POST /api/payments
Idempotency-Key: abc-123-def
Content-Type: application/json

{"amount": 100, "currency": "USD"}

POST /api/payments
Idempotency-Key: abc-123-def  // Same key
Content-Type: application/json

{"amount": 100, "currency": "USD"}
// Server returns cached result for same key
```

### 🔹 Database Transactions

**ACID properties ensure idempotency**

```sql
-- Atomic: All or nothing
-- Consistent: Valid state transitions  
-- Isolated: Concurrent operations don't interfere
-- Durable: Changes persist

BEGIN TRANSACTION;
UPDATE accounts SET balance = balance - 100 WHERE id = 1;
UPDATE accounts SET balance = balance + 100 WHERE id = 2;
COMMIT;
```
### 🔹 Idempotency in Microservices

**Saga pattern for distributed transactions**

```javascript
// Compensating actions for failures
try {
  debitAccount(accountId, amount);
  creditAccount(merchantId, amount);
} catch (error) {
  // Compensate
  creditAccount(accountId, amount);
  debitAccount(merchantId, amount);
}
```

### 🔹 Testing Idempotency

```javascript
// Test idempotent operations
function testIdempotency(operation, times = 3) {
  let results = [];
  for (let i = 0; i < times; i++) {
    results.push(operation());
  }
  // All results should be equivalent
  assert(results[0] === results[1] === results[2]);
}
```
---

## 🎯 Interview One-Liner

> Idempotency means multiple identical requests produce same result; GET, PUT, DELETE are idempotent (safe retry), POST/PATCH are not; crucial for reliable distributed systems and handling network failures.
---

## ✅ 112. What are HTTP status codes? Explain 2xx, 4xx, 5xx

**HTTP status codes are three-digit numbers returned by servers to indicate the result of a client's request. They are grouped into classes (1xx-5xx) with specific meanings.**

### 🔹 Status Code Classes

### 🔹 1xx: Informational

**Request received, continuing process**

```http
100 Continue
    // Client can continue sending request body

101 Switching Protocols  
    // Server switching protocols (e.g., HTTP to WebSocket)

102 Processing
    // Server processing request (WebDAV)
```

### 🔹 2xx: Successful

**Request successfully received, understood, and accepted**

```http
200 OK
    // Standard success response
    GET /api/users → Returns user list

201 Created
    // Resource created successfully
    POST /api/users → Returns new user with Location header

202 Accepted
    // Request accepted for processing (async)
    POST /api/reports → Report queued for generation

203 Non-Authoritative Information
    // Response from cached/proxy source

204 No Content
    // Success but no content to return
    DELETE /api/users/123 → User deleted

205 Reset Content
    // Reset form/view state

206 Partial Content
    // Partial response (range requests)
    GET /api/file.mp4 Range: bytes=0-1023
```

### 🔹 3xx: Redirection

**Further action needed to complete request**

```http
301 Moved Permanently
    // Resource moved permanently
    GET /api/users → Location: /api/v2/users

302 Found (Moved Temporarily)
    // Resource temporarily moved

303 See Other
    // Redirect to different URI for result

304 Not Modified
    // Resource not changed (caching)
    GET /api/users If-Modified-Since: ...

307 Temporary Redirect
    // Same as 302 but preserve method

308 Permanent Redirect
    // Same as 301 but preserve method
```

### 🔹 4xx: Client Error

**Request contains bad syntax or cannot be fulfilled**

```http
400 Bad Request
    // Malformed request syntax
    POST /api/users with invalid JSON

401 Unauthorized
    // Authentication required
    GET /api/users without valid token

402 Payment Required
    // Reserved for future use

403 Forbidden
    // Authentication succeeded but access denied
    GET /api/admin/users as regular user

404 Not Found
    // Resource doesn't exist
    GET /api/users/999

405 Method Not Allowed
    // HTTP method not supported
    PATCH /api/users (only GET/POST allowed)

406 Not Acceptable
    // Content type not acceptable
    GET /api/users Accept: text/html (only JSON)

407 Proxy Authentication Required
    // Proxy requires authentication

408 Request Timeout
    // Server timed out waiting for request

409 Conflict
    // Request conflicts with current state
    POST /api/users/ with existing email

410 Gone
    // Resource permanently removed

411 Length Required
    // Content-Length header required

412 Precondition Failed
    // Preconditions in headers not met

413 Payload Too Large
    // Request body too large

414 URI Too Long
    // URI too long

415 Unsupported Media Type
    // Content-Type not supported

416 Range Not Satisfiable
    // Invalid range request

417 Expectation Failed
    // Expect header not met

418 I'm a teapot
    // April Fools joke (RFC 2324)

421 Misdirected Request
    // Request sent to wrong server

422 Unprocessable Entity
    // Semantic errors in request
    POST /api/users with validation errors

423 Locked
    // Resource locked (WebDAV)

424 Failed Dependency
    // Request failed due to prior request

425 Too Early
    // Server unwilling to risk replay

426 Upgrade Required
    // Protocol upgrade required

428 Precondition Required
    // Conditional request required

429 Too Many Requests
    // Rate limit exceeded

431 Request Header Fields Too Large
    // Headers too large

451 Unavailable For Legal Reasons
    // Legal restrictions
```

### 🔹 5xx: Server Error

**Server failed to fulfill valid request**

```http
500 Internal Server Error
    // Unexpected server error
    NullPointerException in code

501 Not Implemented
    // Server doesn't support functionality
    Custom HTTP method

502 Bad Gateway
    // Invalid response from upstream server
    API gateway → broken microservice

503 Service Unavailable
    // Server temporarily unavailable
    Maintenance mode, overload

504 Gateway Timeout
    // Upstream server timeout
    Database connection timeout

505 HTTP Version Not Supported
    // HTTP version not supported

506 Variant Also Negotiates
    // Content negotiation error

507 Insufficient Storage
    // Server storage full

508 Loop Detected
    // Infinite loop in request

510 Not Extended
    // Further extensions required

511 Network Authentication Required
    // Network requires authentication
```

### 🔹 Common Status Code Usage

```javascript
// Successful operations
POST /api/users     → 201 Created
GET  /api/users/123 → 200 OK
PUT  /api/users/123 → 200 OK or 204 No Content
DELETE /api/users/123 → 204 No Content

// Client errors
GET /api/users/999  → 404 Not Found
POST /api/users with invalid data → 400 Bad Request
GET /api/admin without auth → 401 Unauthorized
GET /api/admin as user → 403 Forbidden

// Server errors
Database down → 503 Service Unavailable
Code bug → 500 Internal Server Error
Timeout → 504 Gateway Timeout
```

### 🔹 Best Practices

```javascript
// Use appropriate codes
// Include error details in response body for 4xx/5xx
// Use consistent error format
// Log server errors for debugging
// Don't expose internal details in 5xx responses
```

---

## 🎯 Interview One-Liner

> HTTP status codes: 2xx (success: 200 OK, 201 Created), 4xx (client errors: 400 Bad Request, 401 Unauthorized, 404 Not Found), 5xx (server errors: 500 Internal Server Error, 503 Service Unavailable).

---

## ✅ 113. What is HATEOAS?

**HATEOAS (Hypermedia As The Engine Of Application State)** is a REST constraint that allows clients to navigate and interact with an API dynamically through hyperlinks, making APIs more discoverable and self-documenting.**

### 🔹 The Concept

**Hypermedia drives application state**

```javascript
// Traditional API
GET /api/orders/123
{
  "id": 123,
  "status": "pending",
  "total": 99.99
}

// HATEOAS API
GET /api/orders/123
{
  "id": 123,
  "status": "pending", 
  "total": 99.99,
  "_links": {
    "self": {"href": "/api/orders/123"},
    "cancel": {"href": "/api/orders/123/cancel"},
    "pay": {"href": "/api/orders/123/payment"}
  }
}
```

### 🔹 Link Relations

**Standardized link types**

```javascript
{
  "_links": {
    "self": {"href": "/api/orders/123"},
    "collection": {"href": "/api/orders"},
    "next": {"href": "/api/orders?page=2"},
    "prev": {"href": "/api/orders?page=0"},
    "edit": {"href": "/api/orders/123", "method": "PUT"},
    "delete": {"href": "/api/orders/123", "method": "DELETE"}
  }
}
```

### 🔹 Spring HATEOAS

**Implementation in Spring Boot**

```java
@RestController
public class OrderController {
    
    @GetMapping("/orders/{id}")
    public EntityModel<Order> getOrder(@PathVariable Long id) {
        Order order = orderService.getOrder(id);
        
        EntityModel<Order> model = EntityModel.of(order);
        model.add(linkTo(methodOn(OrderController.class).getOrder(id)).withSelfRel());
        model.add(linkTo(methodOn(OrderController.class).getAllOrders()).withRel("collection"));
        
        if (order.getStatus() == OrderStatus.PENDING) {
            model.add(linkTo(methodOn(OrderController.class).cancelOrder(id)).withRel("cancel"));
            model.add(linkTo(methodOn(OrderController.class).payOrder(id)).withRel("pay"));
        }
        
        return model;
    }
    
    @GetMapping("/orders")
    public CollectionModel<EntityModel<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        
        List<EntityModel<Order>> orderModels = orders.stream()
            .map(order -> EntityModel.of(order,
                linkTo(methodOn(OrderController.class).getOrder(order.getId())).withSelfRel()))
            .collect(Collectors.toList());
        
        CollectionModel<EntityModel<Order>> collectionModel = CollectionModel.of(orderModels);
        collectionModel.add(linkTo(methodOn(OrderController.class).getAllOrders()).withSelfRel());
        
        return collectionModel;
    }
}
```

### 🔹 HAL Format

**Hypertext Application Language**

```javascript
{
  "_links": {
    "self": {"href": "/api/orders/123"},
    "next": {"href": "/api/orders/124"},
    "find": {"href": "/api/orders{?id}", "templated": true}
  },
  "id": 123,
  "total": 99.99,
  "_embedded": {
    "customer": {
      "_links": {"self": {"href": "/api/customers/456"}},
      "name": "John Doe"
    }
  }
}
```

### 🔹 Benefits

**Why use HATEOAS?**

```javascript 
// API Evolution
// Clients don't hardcode URLs
// Server can change URL structure
// Backward compatibility maintained

// Discoverability  
// Clients can explore API dynamically
// Self-documenting APIs
// Reduced coupling between client/server

// State Management
// Hypermedia indicates available actions
// Clients know what to do next
// Workflow guidance
```

### 🔹 Richardson Maturity Model

**Level 3: Hypermedia Controls**

```javascript
// Level 0: RPC over HTTP
POST /api/placeOrder
{
  "productId": 123,
  "quantity": 2
}

// Level 1: Resources
POST /api/orders
{
  "productId": 123,
  "quantity": 2
}

// Level 2: HTTP Verbs
POST /api/orders
PUT  /api/orders/456
GET  /api/orders/456

// Level 3: HATEOAS
GET /api/orders/456
{
  "_links": {
    "pay": {"href": "/api/orders/456/payment"},
    "cancel": {"href": "/api/orders/456/cancel"}
  }
}
```

### 🔹 Challenges

**Implementation complexity**

```javascript
// More complex responses
// Increased payload size
// Client complexity
// Tooling support limited
// Learning curve
```

### 🔹 When to Use HATEOAS

```javascript
// Public APIs
// Long-lived APIs
// Complex workflows
// API-first development

// When NOT to use
// Internal APIs
// Simple CRUD APIs
// Mobile apps (thick clients)
```

---

## 🎯 Interview One-Liner

> HATEOAS enables clients to navigate REST APIs through hyperlinks in responses, making APIs self-discoverable and allowing dynamic exploration of available actions and resources.

---

## ✅ 114. Richardson Maturity Model

**The Richardson Maturity Model is a way to grade REST APIs based on their adherence to REST principles, with four levels from 0 (RPC) to 3 (full REST with HATEOAS).**

### 🔹 Level 0: The Swamp of POX

**RPC over HTTP - no REST principles**

```javascript
// Single endpoint for all operations
// Uses HTTP as transport only
// No resources, no HTTP methods

POST /api/service
Content-Type: application/xml

<request>
  <method>placeOrder</method>
  <params>
    <productId>123</productId>
    <quantity>2</quantity>
  </params>
</request>

// Response
<response>
  <result>Order placed successfully</result>
</response>
```

**Characteristics:**
- Single URI endpoint
- Single HTTP method (usually POST)
- Remote procedure calls
- No use of HTTP features

### 🔹 Level 1: Resources

**Multiple resources but still RPC-style**

```javascript
// Different endpoints for different resources
// Still uses POST for everything

POST /api/orders
{
  "action": "create",
  "productId": 123,
  "quantity": 2
}

POST /api/orders
{
  "action": "get",
  "orderId": 456
}

POST /api/orders  
{
  "action": "update",
  "orderId": 456,
  "status": "shipped"
}
```

**Characteristics:**
- Multiple URIs (resources)
- Still single HTTP method
- Actions specified in request body
- Better organization but not RESTful

### 🔹 Level 2: HTTP Verbs

**Proper use of HTTP methods**

```javascript
// Uses appropriate HTTP verbs
// Proper status codes
// Still no hypermedia

GET    /api/orders       // List orders
GET    /api/orders/123   // Get specific order
POST   /api/orders       // Create order
PUT    /api/orders/123   // Update order
DELETE /api/orders/123   // Delete order

// Response with proper status codes
POST /api/orders
// 201 Created
// Location: /api/orders/456
```

**Characteristics:**
- Proper HTTP methods
- Meaningful status codes
- Multiple resources
- Still tightly coupled clients

### 🔹 Level 3: Hypermedia Controls (HATEOAS)

**Full REST with hypermedia**

```javascript
// Responses include links for next actions
// Clients discover API dynamically

GET /api/orders/123
{
  "id": 123,
  "status": "pending",
  "total": 99.99,
  "_links": {
    "self": {"href": "/api/orders/123"},
    "cancel": {"href": "/api/orders/123/cancel", "method": "PUT"},
    "pay": {"href": "/api/orders/123/payment", "method": "POST"},
    "customer": {"href": "/api/customers/456"}
  }
}
// Client follows links instead of hardcoding URLs

```

**Characteristics:**
- Hypermedia in responses
- Self-discoverable API
- Loose coupling
- Evolvable APIs

### 🔹 Level Comparison

| Level | Characteristics | Example |
|-------|------------------|---------|
| **0** | RPC over HTTP | SOAP-like |
| **1** | Resources | Different URIs |
| **2** | HTTP Verbs | GET/POST/PUT/DELETE |
| **3** | HATEOAS | Links in responses |

### 🔹 Real-World Examples

```javascript
// Twitter API (Level 2)
GET /1.1/statuses/user_timeline.json?screen_name=twitterapi

// GitHub API (Level 3)
GET /repos/octocat/Hello-World
{
  "name": "Hello-World",
  "_links": {
    "self": {"href": "/repos/octocat/Hello-World"},
    "git_refs": {"href": "/repos/octocat/Hello-World/git/refs{/sha}"},
    "git_tags": {"href": "/repos/octocat/Hello-World/git/tags{/sha}"}
  }
}
```

### 🔹 Benefits of Higher Levels

```javascript
// Level 2 vs Level 3
// Level 2: Clients hardcode URLs
// Level 3: Clients follow links

// API Evolution
// Level 2: Breaking changes when URLs change
// Level 3: Backward compatible URL changes

// Coupling
// Level 2: Tight coupling
// Level 3: Loose coupling
```

### 🔹 Implementation Challenges

```javascript
// Level 3 is hardest to implement
// Requires more complex responses
// Client libraries needed
// Tooling support limited
// Learning curve steep

// Many APIs stop at Level 2
// Good balance of REST principles
// Easier to implement and consume
```

### 🔹 Assessment Questions

```javascript
// To determine API maturity level:

// Level 0: Does it use HTTP as transport only?
// Level 1: Does it have multiple URIs?
// Level 2: Does it use HTTP methods properly?
// Level 3: Do responses include hypermedia links?
```
---
## 🎯 Interview One-Liner

> Richardson Maturity Model grades REST APIs: Level 0 (RPC over HTTP), Level 1 (resources), Level 2 (HTTP verbs), Level 3 (HATEOAS with hypermedia links for full REST compliance).
