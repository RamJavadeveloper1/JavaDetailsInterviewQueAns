# **SCENARIO-BASED QUESTIONS**

## ✅ 248. How would you handle a million records insertion in database?

**Handling bulk data insertion of a million records requires optimization techniques like batch processing, connection pooling, asynchronous processing, and database-specific optimizations to ensure performance and reliability.**

### 🔹 Batch Processing

**Using JDBC batch inserts for efficiency**

```java
@Service
public class BulkInsertService {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public void insertMillionRecords(List<User> users) {
        String sql = "INSERT INTO users (name, email, created_date) VALUES (?, ?, ?)";
        
        jdbcTemplate.batchUpdate(sql, users, 1000, (ps, user) -> {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setTimestamp(3, Timestamp.valueOf(user.getCreatedDate()));
        });
    }
}
```

### 🔹 Spring Data JPA Batch Insert

**Using saveAll with batch configuration**

```java
@Configuration
public class JpaConfig {
    
    @Bean
    public JpaProperties jpaProperties() {
        JpaProperties properties = new JpaProperties();
        properties.getProperties().put("hibernate.jdbc.batch_size", "50");
        properties.getProperties().put("hibernate.order_inserts", "true");
        properties.getProperties().put("hibernate.order_updates", "true");
        return properties;
    }
}

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Transactional
    public void bulkInsert(List<User> users) {
        userRepository.saveAll(users);
    }
}
```

### 🔹 Asynchronous Processing

**Using @Async for parallel processing**

```java
@Service
public class AsyncBulkInsertService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Async
    @Transactional
    public CompletableFuture<Void> insertBatch(List<User> batch) {
        userRepository.saveAll(batch);
        return CompletableFuture.completedFuture(null);
    }
    
    public void insertMillionRecords(List<User> users) {
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        List<List<User>> batches = partition(users, 1000);
        
        for (List<User> batch : batches) {
            futures.add(insertBatch(batch));
        }
        
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }
}
```

### 🔹 Database-Specific Optimizations

**MySQL bulk insert optimizations**

```sql
-- Disable indexes during bulk insert
ALTER TABLE users DISABLE KEYS;

-- Bulk insert
LOAD DATA INFILE '/path/to/data.csv'
INTO TABLE users
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n'
(name, email, created_date);

-- Re-enable indexes
ALTER TABLE users ENABLE KEYS;
```

### 🔹 Connection Pooling

**HikariCP configuration for bulk operations**

```yaml
spring:
  datasource:
    hikari:
      maximum-pool-size: 20
      minimum-idle: 5
      connection-timeout: 30000
      idle-timeout: 600000
      max-lifetime: 1800000
```

### 🔹 Monitoring and Error Handling

**Progress tracking and error recovery**

```java
@Service
public class BulkInsertWithMonitoring {
    
    @Autowired
    private UserRepository userRepository;
    
    public BulkInsertResult insertWithMonitoring(List<User> users) {
        BulkInsertResult result = new BulkInsertResult();
        int batchSize = 1000;
        
        for (int i = 0; i < users.size(); i += batchSize) {
            List<User> batch = users.subList(i, Math.min(i + batchSize, users.size()));
            
            try {
                userRepository.saveAll(batch);
                result.incrementSuccess(batch.size());
                logProgress(i + batch.size(), users.size());
            } catch (Exception e) {
                result.addError(e.getMessage());
                // Implement retry logic or save failed records
            }
        }
        
        return result;
    }
}
```

### 🔹 Best Practices

```java
// Use batch processing with appropriate batch sizes (100-1000)
// Disable constraints/indexes during bulk insert if possible
// Use connection pooling with sufficient pool size
// Implement progress monitoring and error handling
// Consider partitioning large datasets
// Use database-specific bulk insert features
// Monitor memory usage during large operations
// Implement rollback strategies for failed operations
```

### 🔹 Performance Considerations

**Memory and resource management**

```java
@Configuration
public class BulkInsertConfig {
    
    @Bean
    public TaskExecutor bulkInsertExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(8);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("bulk-insert-");
        return executor;
    }
}
```

---

## 🎯 Interview One-Liner

> Million records insertion: use batch processing (JDBC/Spring Data), disable indexes during insert, async processing, connection pooling; monitor progress and handle errors.

---

## ✅ 249. How to improve performance of slow API?

**API performance can be improved through caching, database optimization, asynchronous processing, pagination, compression, and monitoring, with caching and database tuning being the most impactful optimizations.**

### 🔹 Caching Implementation

**Multi-level caching strategy**

```java
@Configuration
@EnableCaching
public class CacheConfig {
    
    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("users", "products");
    }
}

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Cacheable(value = "users", key = "#id")
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    
    @CachePut(value = "users", key = "#user.id")
    public User updateUser(User user) {
        return userRepository.save(user);
    }
    
    @CacheEvict(value = "users", key = "#id")
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
```

### 🔹 Database Optimization

**Query optimization and indexing**

```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    @Query("SELECT u FROM User u WHERE u.email = :email")
    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Optional<User> findByEmailReadOnly(@Param("email") String email);
    
    @Query(value = "SELECT * FROM users WHERE created_date >= :startDate", 
           nativeQuery = true)
    List<User> findRecentUsers(@Param("startDate") LocalDateTime startDate);
}

// Database indexes
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_created_date ON users(created_date);
```

### 🔹 Pagination and Limiting

**Efficient data retrieval**

```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping
    public Page<UserDTO> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id") String sort) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return userService.getUsers(pageable);
    }
}
```

### 🔹 Asynchronous Processing

**Non-blocking operations**

```java
@RestController
@RequestMapping("/api/reports")
public class ReportController {
    
    @Autowired
    private ReportService reportService;
    
    @PostMapping("/generate")
    public CompletableFuture<ResponseEntity<String>> generateReport(@RequestBody ReportRequest request) {
        return reportService.generateReportAsync(request)
                .thenApply(report -> ResponseEntity.ok("Report generated: " + report.getId()));
    }
}

@Service
public class ReportService {
    
    @Async
    public CompletableFuture<Report> generateReportAsync(ReportRequest request) {
        // Heavy processing logic
        Report report = generateHeavyReport(request);
        return CompletableFuture.completedFuture(report);
    }
}
```

### 🔹 Response Compression

**GZIP compression for responses**

```java
@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MappingJackson2HttpMessageConverter());
    }
    
    @Bean
    public GzipFilter gzipFilter() {
        return new GzipFilter();
    }
}
```

### 🔹 Connection Pooling

**Database connection optimization**

```yaml
spring:
  datasource:
    hikari:
      maximum-pool-size: 20
      minimum-idle: 5
      connection-timeout: 20000
      leak-detection-threshold: 60000
```

### 🔹 Monitoring and Profiling

**Performance monitoring**

```java
@Configuration
public class MonitoringConfig {
    
    @Bean
    public MeterRegistry meterRegistry() {
        return new SimpleMeterRegistry();
    }
}

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private MeterRegistry meterRegistry;
    
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        Timer.Sample sample = Timer.start(meterRegistry);
        
        try {
            UserDTO user = userService.getUser(id);
            return ResponseEntity.ok(user);
        } finally {
            sample.stop(Timer.builder("user.get").register(meterRegistry));
        }
    }
}
```

### 🔹 CDN and Static Content

**Offloading static content**

```java
@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/")
                .setCacheControl(CacheControl.maxAge(1, TimeUnit.DAYS));
    }
}
```

### 🔹 Best Practices

```java
// Implement multi-level caching (application, database, CDN)
// Optimize database queries with proper indexing
// Use pagination for large result sets
// Implement asynchronous processing for heavy operations
// Enable response compression
// Monitor API performance metrics
// Use connection pooling
// Implement rate limiting
// Profile and optimize slow queries
```

### 🔹 Performance Testing

**Load testing APIs**

```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PerformanceTest {
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    public void testApiPerformance() {
        long startTime = System.currentTimeMillis();
        
        for (int i = 0; i < 100; i++) {
            ResponseEntity<UserDTO> response = restTemplate.getForEntity("/api/users/1", UserDTO.class);
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        }
        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        System.out.println("Average response time: " + (duration / 100) + "ms");
    }
}
```

---

## 🎯 Interview One-Liner

> Slow API performance: implement caching, optimize DB queries/indexes, use pagination, async processing, compression; monitor with metrics and profile bottlenecks.

---

## ✅ 250. How to handle file upload in Spring Boot?

**File upload in Spring Boot can be handled using MultipartFile, with proper validation, storage strategies, and error handling to ensure security and performance.**

### 🔹 Basic File Upload

**Single file upload with validation**

```java
@RestController
@RequestMapping("/api/files")
public class FileUploadController {
    
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }
        
        // Validate file type
        String contentType = file.getContentType();
        if (!isValidContentType(contentType)) {
            return ResponseEntity.badRequest().body("Invalid file type");
        }
        
        // Validate file size
        if (file.getSize() > MAX_FILE_SIZE) {
            return ResponseEntity.badRequest().body("File too large");
        }
        
        try {
            String fileName = saveFile(file);
            return ResponseEntity.ok("File uploaded successfully: " + fileName);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to upload file");
        }
    }
    
    private boolean isValidContentType(String contentType) {
        return contentType.equals("image/jpeg") || 
               contentType.equals("image/png") || 
               contentType.equals("application/pdf");
    }
}
```

### 🔹 Multiple File Upload

**Handling multiple files simultaneously**

```java
@RestController
@RequestMapping("/api/files")
public class MultiFileUploadController {
    
    @PostMapping("/upload-multiple")
    public ResponseEntity<List<String>> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        
        List<String> uploadedFiles = new ArrayList<>();
        
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                try {
                    String fileName = saveFile(file);
                    uploadedFiles.add(fileName);
                } catch (IOException e) {
                    // Handle individual file errors
                }
            }
        }
        
        return ResponseEntity.ok(uploadedFiles);
    }
}
```

### 🔹 File Storage Service

**Centralized file storage logic**

```java
@Service
public class FileStorageService {
    
    private final Path rootLocation = Paths.get("uploads");
    
    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage", e);
        }
    }
    
    public String store(MultipartFile file) throws IOException {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        
        if (file.isEmpty()) {
            throw new IOException("Failed to store empty file " + filename);
        }
        
        if (filename.contains("..")) {
            throw new IOException("Cannot store file with relative path outside current directory " + filename);
        }
        
        try (InputStream inputStream = file.getInputStream()) {
            Path targetLocation = this.rootLocation.resolve(filename);
            Files.copy(inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return filename;
        }
    }
}
```

### 🔹 Cloud Storage Integration

**AWS S3 file upload**

```java
@Service
public class S3FileStorageService {
    
    @Autowired
    private AmazonS3 s3Client;
    
    private final String bucketName = "my-bucket";
    
    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = generateUniqueFileName(file.getOriginalFilename());
        
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        
        s3Client.putObject(bucketName, fileName, file.getInputStream(), metadata);
        
        return s3Client.getUrl(bucketName, fileName).toString();
    }
    
    private String generateUniqueFileName(String originalFilename) {
        return UUID.randomUUID().toString() + "_" + originalFilename;
    }
}
```

### 🔹 File Upload Configuration

**Spring Boot configuration for uploads**

```java
@Configuration
public class FileUploadConfig {
    
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.ofMegabytes(10)); // 10MB
        factory.setMaxRequestSize(DataSize.ofMegabytes(50)); // 50MB
        factory.setFileSizeThreshold(DataSize.ofKilobytes(512)); // 512KB
        return factory.createMultipartConfig();
    }
}

# application.properties
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=50MB
spring.servlet.multipart.file-size-threshold=512KB
```

### 🔹 Progress Tracking

**Upload progress monitoring**

```java
@RestController
@RequestMapping("/api/files")
public class ProgressFileUploadController {
    
    @PostMapping("/upload-progress")
    public ResponseEntity<String> uploadWithProgress(
            @RequestParam("file") MultipartFile file,
            HttpServletRequest request) throws IOException {
        
        ProgressListener progressListener = new ProgressListener() {
            @Override
            public void update(long bytesRead, long contentLength, int items) {
                // Update progress in session or database
                request.getSession().setAttribute("uploadProgress", 
                    (bytesRead * 100) / contentLength);
            }
        };
        
        // Use Apache Commons FileUpload for progress tracking
        // Implementation details...
        
        return ResponseEntity.ok("Upload completed");
    }
}
```

### 🔹 Security Considerations

**File upload security**

```java
@Component
public class FileValidator {
    
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList(
        "jpg", "jpeg", "png", "pdf", "doc", "docx"
    );
    
    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB
    
    public void validateFile(MultipartFile file) throws ValidationException {
        // Check file size
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new ValidationException("File size exceeds maximum allowed size");
        }
        
        // Check file extension
        String filename = file.getOriginalFilename();
        if (filename != null) {
            String extension = getFileExtension(filename);
            if (!ALLOWED_EXTENSIONS.contains(extension.toLowerCase())) {
                throw new ValidationException("File type not allowed");
            }
        }
        
        // Check content type
        String contentType = file.getContentType();
        if (contentType == null || !isValidContentType(contentType)) {
            throw new ValidationException("Invalid content type");
        }
        
        // Additional security checks (virus scanning, etc.)
    }
    
    private String getFileExtension(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1);
    }
}
```

### 🔹 Error Handling

**Comprehensive error handling**

```java
@RestControllerAdvice
public class FileUploadExceptionHandler {
    
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<String> handleMaxSizeException(MaxUploadSizeExceededException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("File size exceeds maximum allowed size");
    }
    
    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(IOException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Failed to process file upload");
    }
}
```

### 🔹 Best Practices

```java
// Validate file type, size, and content before processing
// Use unique filenames to prevent conflicts
// Store files in secure locations or cloud storage
// Implement progress tracking for large uploads
// Handle errors gracefully with proper HTTP status codes
// Clean up temporary files
// Implement rate limiting for upload endpoints
// Scan files for viruses/malware
// Use HTTPS for file uploads
```

### 🔹 Testing File Upload

**Unit testing file upload functionality**

```java
@SpringBootTest
@RunWith(SpringRunner.class)
public class FileUploadTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    public void testFileUpload() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
            "file", "test.jpg", "image/jpeg", "test content".getBytes()
        );
        
        mockMvc.perform(multipart("/api/files/upload").file(file))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("uploaded successfully")));
    }
}
```

---

## 🎯 Interview One-Liner

> File upload in Spring Boot: use MultipartFile, validate type/size, store securely (local/cloud), handle errors; configure limits in application.properties.

---

## ✅ 251. Design a URL shortener service

**A URL shortener service requires generating short unique codes, storing URL mappings, handling redirects, and providing analytics, with scalability and reliability considerations.**

### 🔹 Service Architecture

**High-level design**

```java
@RestController
@RequestMapping("/api")
public class UrlShortenerController {
    
    @Autowired
    private UrlShortenerService urlShortenerService;
    
    @PostMapping("/shorten")
    public ResponseEntity<ShortenResponse> shortenUrl(@RequestBody ShortenRequest request) {
        String shortCode = urlShortenerService.shortenUrl(request.getUrl());
        String shortUrl = "https://short.ly/" + shortCode;
        return ResponseEntity.ok(new ShortenResponse(shortUrl, shortCode));
    }
    
    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) {
        String originalUrl = urlShortenerService.getOriginalUrl(shortCode);
        if (originalUrl == null) {
            return ResponseEntity.notFound().build();
        }
        
        // Track click analytics
        urlShortenerService.trackClick(shortCode);
        
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, originalUrl)
                .build();
    }
}
```

### 🔹 URL Shortening Algorithm

**Base62 encoding for short codes**

```java
@Service
public class UrlShortenerService {
    
    private static final String BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int CODE_LENGTH = 7;
    
    @Autowired
    private UrlRepository urlRepository;
    
    public String shortenUrl(String originalUrl) {
        // Check if URL already exists
        UrlEntity existing = urlRepository.findByOriginalUrl(originalUrl);
        if (existing != null) {
            return existing.getShortCode();
        }
        
        // Generate unique short code
        String shortCode;
        do {
            shortCode = generateShortCode();
        } while (urlRepository.existsByShortCode(shortCode));
        
        // Save to database
        UrlEntity urlEntity = new UrlEntity();
        urlEntity.setOriginalUrl(originalUrl);
        urlEntity.setShortCode(shortCode);
        urlEntity.setCreatedDate(LocalDateTime.now());
        urlRepository.save(urlEntity);
        
        return shortCode;
    }
    
    private String generateShortCode() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < CODE_LENGTH; i++) {
            sb.append(BASE62.charAt(random.nextInt(BASE62.length())));
        }
        return sb.toString();
    }
    
    public String getOriginalUrl(String shortCode) {
        UrlEntity urlEntity = urlRepository.findByShortCode(shortCode);
        return urlEntity != null ? urlEntity.getOriginalUrl() : null;
    }
}
```

### 🔹 Database Design

**URL entity with analytics**

```java
@Entity
@Table(name = "urls")
public class UrlEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String shortCode;
    
    @Column(nullable = false, length = 2048)
    private String originalUrl;
    
    @Column(nullable = false)
    private LocalDateTime createdDate;
    
    private LocalDateTime expiryDate;
    
    @Column(nullable = false)
    private boolean active = true;
    
    private Long clickCount = 0L;
    
    // Getters and setters
}

@Repository
public interface UrlRepository extends JpaRepository<UrlEntity, Long> {
    
    UrlEntity findByShortCode(String shortCode);
    UrlEntity findByOriginalUrl(String originalUrl);
    boolean existsByShortCode(String shortCode);
    
    @Query("SELECT u FROM UrlEntity u WHERE u.expiryDate < :now AND u.active = true")
    List<UrlEntity> findExpiredUrls(@Param("now") LocalDateTime now);
}
```

### 🔹 Analytics and Tracking

**Click tracking and analytics**

```java
@Entity
@Table(name = "url_clicks")
public class UrlClick {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String shortCode;
    
    @Column(nullable = false)
    private LocalDateTime clickTime;
    
    private String userAgent;
    private String ipAddress;
    private String referrer;
    
    // Getters and setters
}

@Service
public class AnalyticsService {
    
    @Autowired
    private UrlClickRepository clickRepository;
    
    @Async
    public void trackClick(String shortCode, HttpServletRequest request) {
        UrlClick click = new UrlClick();
        click.setShortCode(shortCode);
        click.setClickTime(LocalDateTime.now());
        click.setUserAgent(request.getHeader("User-Agent"));
        click.setIpAddress(getClientIpAddress(request));
        click.setReferrer(request.getHeader("Referer"));
        
        clickRepository.save(click);
        
        // Update click count in URL entity
        urlRepository.incrementClickCount(shortCode);
    }
    
    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}
```

### 🔹 Custom Short Codes

**User-defined short codes**

```java
@RestController
@RequestMapping("/api")
public class CustomUrlController {
    
    @PostMapping("/shorten-custom")
    public ResponseEntity<?> shortenWithCustomCode(@RequestBody CustomShortenRequest request) {
        
        // Validate custom code
        if (!isValidCustomCode(request.getCustomCode())) {
            return ResponseEntity.badRequest().body("Invalid custom code");
        }
        
        // Check if custom code is available
        if (urlRepository.existsByShortCode(request.getCustomCode())) {
            return ResponseEntity.badRequest().body("Custom code already taken");
        }
        
        // Create URL with custom code
        UrlEntity urlEntity = new UrlEntity();
        urlEntity.setOriginalUrl(request.getUrl());
        urlEntity.setShortCode(request.getCustomCode());
        urlEntity.setCreatedDate(LocalDateTime.now());
        urlRepository.save(urlEntity);
        
        return ResponseEntity.ok(new ShortenResponse(
            "https://short.ly/" + request.getCustomCode(), 
            request.getCustomCode()
        ));
    }
    
    private boolean isValidCustomCode(String code) {
        return code != null && code.length() >= 3 && code.length() <= 20 && 
               code.matches("^[a-zA-Z0-9_-]+$");
    }
}
```

### 🔹 Expiration and Cleanup

**URL expiration handling**

```java
@Service
@EnableScheduling
public class UrlCleanupService {
    
    @Autowired
    private UrlRepository urlRepository;
    
    @Scheduled(fixedRate = 3600000) // Run every hour
    public void cleanupExpiredUrls() {
        List<UrlEntity> expiredUrls = urlRepository.findExpiredUrls(LocalDateTime.now());
        
        for (UrlEntity url : expiredUrls) {
            url.setActive(false);
            urlRepository.save(url);
        }
    }
}
```

### 🔹 Caching for Performance

**Redis caching for frequently accessed URLs**

```java
@Configuration
public class CacheConfig {
    
    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }
}

@Service
public class CachedUrlShortenerService {
    
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    
    @Autowired
    private UrlRepository urlRepository;
    
    private static final String URL_CACHE_PREFIX = "url:";
    private static final Duration CACHE_TTL = Duration.ofHours(24);
    
    public String getOriginalUrl(String shortCode) {
        String cacheKey = URL_CACHE_PREFIX + shortCode;
        
        // Try cache first
        String cachedUrl = redisTemplate.opsForValue().get(cacheKey);
        if (cachedUrl != null) {
            return cachedUrl;
        }
        
        // Fetch from database
        UrlEntity urlEntity = urlRepository.findByShortCode(shortCode);
        if (urlEntity != null && urlEntity.isActive()) {
            // Cache the result
            redisTemplate.opsForValue().set(cacheKey, urlEntity.getOriginalUrl(), CACHE_TTL);
            return urlEntity.getOriginalUrl();
        }
        
        return null;
    }
}
```

### 🔹 Rate Limiting

**Prevent abuse with rate limiting**

```java
@Configuration
public class RateLimitConfig {
    
    @Bean
    public KeyResolver userKeyResolver() {
        return exchange -> Mono.just(
            exchange.getRequest().getRemoteAddress().getAddress().getHostAddress()
        );
    }
}

@Configuration
public class GatewayConfig {
    
    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("shorten_route", r -> r
                        .path("/api/shorten")
                        .filters(f -> f
                                .requestRateLimiter(config -> config
                                        .setKeyResolver(userKeyResolver())
                                        .setRateLimiter(redisRateLimiter())
                                )
                        )
                        .uri("lb://url-shortener-service")
                )
                .build();
    }
}
```

### 🔹 Best Practices

```java
// Use Base62 encoding for short codes (7-8 characters)
// Implement collision detection and retry logic
// Add URL validation and sanitization
// Implement caching for frequently accessed URLs
// Track analytics (clicks, referrers, user agents)
// Support custom short codes for branded URLs
// Implement URL expiration and cleanup
// Use rate limiting to prevent abuse
// Ensure high availability with database replication
// Implement monitoring and alerting
```

### 🔹 Scalability Considerations

**Distributed architecture**

```yaml
# Kubernetes deployment for scalability
apiVersion: apps/v1
kind: Deployment
metadata:
  name: url-shortener
spec:
  replicas: 3
  selector:
    matchLabels:
      app: url-shortener
  template:
    metadata:
      labels:
        app: url-shortener
    spec:
      containers:
      - name: url-shortener
        image: url-shortener:latest
        ports:
        - containerPort: 8080
        env:
        - name: SPRING_PROFILES_ACTIVE
          value: "prod"
        - name: REDIS_HOST
          value: "redis-service"
        - name: DB_HOST
          value: "postgres-service"
```

### 🔹 Testing the Service

**Comprehensive testing**

```java
@SpringBootTest
@RunWith(SpringRunner.class)
public class UrlShortenerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private UrlRepository urlRepository;
    
    @Test
    public void testUrlShortening() throws Exception {
        String originalUrl = "https://www.example.com/very/long/url";
        
        mockMvc.perform(post("/api/shorten")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"url\":\"" + originalUrl + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shortUrl").exists());
    }
    
    @Test
    public void testUrlRedirect() throws Exception {
        // Create a short URL first
        UrlEntity urlEntity = new UrlEntity();
        urlEntity.setShortCode("abc123");
        urlEntity.setOriginalUrl("https://www.example.com");
        urlRepository.save(urlEntity);
        
        mockMvc.perform(get("/abc123"))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "https://www.example.com"));
    }
}
```

---

## 🎯 Interview One-Liner

> URL shortener: generate unique Base62 codes, store URL mappings in DB, handle redirects with analytics; use caching, rate limiting, and scalable architecture.

---

## ✅ 252. How to implement caching in microservices?

**Caching in microservices can be implemented at multiple levels (application, database, CDN) using distributed caches like Redis, with cache-aside, write-through, and write-behind patterns for consistency.**

### 🔹 Cache-Aside Pattern

**Application-level caching**

```java
@Service
public class CacheAsideUserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RedisTemplate<String, User> redisTemplate;
    
    private static final String USER_CACHE_PREFIX = "user:";
    
    public User getUserById(Long id) {
        String cacheKey = USER_CACHE_PREFIX + id;
        
        // Try cache first
        User cachedUser = redisTemplate.opsForValue().get(cacheKey);
        if (cachedUser != null) {
            return cachedUser;
        }
        
        // Cache miss - fetch from database
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            redisTemplate.opsForValue().set(cacheKey, user, Duration.ofMinutes(30));
        }
        
        return user;
    }
    
    public User updateUser(User user) {
        // Update database first
        User savedUser = userRepository.save(user);
        
        // Invalidate cache
        String cacheKey = USER_CACHE_PREFIX + user.getId();
        redisTemplate.delete(cacheKey);
        
        return savedUser;
    }
}
```

### 🔹 Write-Through Caching

**Synchronous cache updates**

```java
@Service
public class WriteThroughUserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RedisTemplate<String, User> redisTemplate;
    
    private static final String USER_CACHE_PREFIX = "user:";
    
    @Transactional
    public User saveUser(User user) {
        // Save to database
        User savedUser = userRepository.save(user);
        
        // Update cache synchronously
        String cacheKey = USER_CACHE_PREFIX + savedUser.getId();
        redisTemplate.opsForValue().set(cacheKey, savedUser, Duration.ofHours(1));
        
        return savedUser;
    }
    
    public User getUserById(Long id) {
        String cacheKey = USER_CACHE_PREFIX + id;
        
        User cachedUser = redisTemplate.opsForValue().get(cacheKey);
        if (cachedUser != null) {
            return cachedUser;
        }
        
        // Cache miss - this shouldn't happen in write-through
        return userRepository.findById(id).orElse(null);
    }
}
```

### 🔹 Write-Behind Caching

**Asynchronous cache updates**

```java
@Service
public class WriteBehindUserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RedisTemplate<String, User> redisTemplate;
    
    @Autowired
    private KafkaTemplate<String, UserEvent> kafkaTemplate;
    
    private static final String USER_CACHE_PREFIX = "user:";
    
    @Transactional
    public User saveUser(User user) {
        // Save to database
        User savedUser = userRepository.save(user);
        
        // Update cache immediately
        String cacheKey = USER_CACHE_PREFIX + savedUser.getId();
        redisTemplate.opsForValue().set(cacheKey, savedUser, Duration.ofHours(1));
        
        // Send event for async processing
        UserEvent event = new UserEvent("USER_UPDATED", savedUser);
        kafkaTemplate.send("user-events", event);
        
        return savedUser;
    }
    
    @KafkaListener(topics = "user-events")
    public void handleUserEvent(UserEvent event) {
        // Async processing - update search index, send notifications, etc.
        if ("USER_UPDATED".equals(event.getType())) {
            // Additional processing
        }
    }
}
```

### 🔹 Distributed Caching with Redis

**Redis configuration and usage**

```java
@Configuration
public class RedisConfig {
    
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        
        // JSON serialization
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        template.setDefaultSerializer(serializer);
        
        return template;
    }
    
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(30))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new Jackson2JsonRedisSerializer<>(Object.class)));
        
        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(config)
                .build();
    }
}
```

### 🔹 Spring Cache Abstraction

**Declarative caching**

```java
@Configuration
@EnableCaching
public class CacheConfig {
    
    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("users", "products", "orders");
    }
}

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Cacheable(value = "users", key = "#id", unless = "#result == null")
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    
    @Cacheable(value = "users", key = "#email")
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    @CachePut(value = "users", key = "#user.id")
    public User updateUser(User user) {
        return userRepository.save(user);
    }
    
    @CacheEvict(value = "users", key = "#id")
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    
    @CacheEvict(value = "users", allEntries = true)
    public void clearCache() {
        // Clear all user cache
    }
}
```

### 🔹 Cache Invalidation Strategies

**TTL and manual invalidation**

```java
@Service
public class CacheInvalidationService {
    
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    
    // Pattern-based invalidation
    public void invalidateUserRelatedCache(Long userId) {
        String pattern = "user:" + userId + "*";
        Set<String> keys = redisTemplate.keys(pattern);
        if (!keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
    }
    
    // Time-based expiration
    public void setWithTTL(String key, Object value, Duration ttl) {
        redisTemplate.opsForValue().set(key, value, ttl);
    }
    
    // Conditional invalidation
    @CacheEvict(value = "users", key = "#user.id", condition = "#user.status == 'INACTIVE'")
    public void deactivateUser(User user) {
        user.setStatus("INACTIVE");
        userRepository.save(user);
    }
}
```

### 🔹 Multi-Level Caching

**L1 (local) and L2 (distributed) caching**

```java
@Configuration
public class MultiLevelCacheConfig {
    
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        // L2 cache - Redis
        RedisCacheConfiguration redisConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(1));
        
        // L1 cache - Caffeine
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCaffeine(Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(Duration.ofMinutes(10)));
        
        // Composite cache manager
        CompositeCacheManager compositeCacheManager = new CompositeCacheManager(
            caffeineCacheManager,
            RedisCacheManager.builder(redisConnectionFactory)
                    .cacheDefaults(redisConfig)
                    .build()
        );
        
        return compositeCacheManager;
    }
}
```

### 🔹 Cache Synchronization

**Cache consistency across services**

```java
@Service
public class CacheSynchronizationService {
    
    @Autowired
    private KafkaTemplate<String, CacheEvent> kafkaTemplate;
    
    public void publishCacheInvalidation(String cacheName, String key) {
        CacheEvent event = new CacheEvent("INVALIDATE", cacheName, key);
        kafkaTemplate.send("cache-events", event);
    }
}

@Component
public class CacheEventListener {
    
    @Autowired
    private CacheManager cacheManager;
    
    @KafkaListener(topics = "cache-events")
    public void handleCacheEvent(CacheEvent event) {
        if ("INVALIDATE".equals(event.getType())) {
            Cache cache = cacheManager.getCache(event.getCacheName());
            if (cache != null) {
                cache.evict(event.getKey());
            }
        }
    }
}
```

### 🔹 Cache Monitoring

**Metrics and monitoring**

```java
@Configuration
public class CacheMonitoringConfig {
    
    @Bean
    public MeterRegistry meterRegistry() {
        return new SimpleMeterRegistry();
    }
}

@Service
public class CacheMetricsService {
    
    @Autowired
    private MeterRegistry meterRegistry;
    
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    
    public void recordCacheHit(String cacheName) {
        Counter.builder("cache.hits")
                .tag("cache", cacheName)
                .register(meterRegistry)
                .increment();
    }
    
    public void recordCacheMiss(String cacheName) {
        Counter.builder("cache.misses")
                .tag("cache", cacheName)
                .register(meterRegistry)
                .increment();
    }
    
    public Map<String, Object> getCacheStats() {
        // Redis info command for cache statistics
        Properties info = redisTemplate.getConnectionFactory()
                .getConnection()
                .info();
        
        Map<String, Object> stats = new HashMap<>();
        stats.put("connected_clients", info.getProperty("connected_clients"));
        stats.put("used_memory", info.getProperty("used_memory"));
        stats.put("total_connections_received", info.getProperty("total_connections_received"));
        
        return stats;
    }
}
```

### 🔹 Best Practices

```java
// Use appropriate cache patterns (cache-aside, write-through, write-behind)
// Implement proper cache invalidation strategies
// Set appropriate TTL values based on data freshness requirements
// Monitor cache hit/miss ratios and adjust accordingly
// Use distributed caches like Redis for microservices
// Implement cache synchronization across services
// Handle cache failures gracefully (circuit breaker pattern)
// Use cache warming for frequently accessed data
// Implement cache versioning to handle data structure changes
// Monitor memory usage and set size limits
```

### 🔹 Cache Testing

**Testing caching behavior**

```java
@SpringBootTest
@RunWith(SpringRunner.class)
public class CacheTest {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private CacheManager cacheManager;
    
    @Test
    public void testCacheHit() {
        // First call should miss cache
        User user1 = userService.getUserById(1L);
        assertNotNull(user1);
        
        // Second call should hit cache
        User user2 = userService.getUserById(1L);
        assertEquals(user1, user2);
        
        // Verify cache contains the data
        Cache cache = cacheManager.getCache("users");
        assertNotNull(cache.get(1L));
    }
    
    @Test
    public void testCacheEviction() {
        User user = userService.getUserById(1L);
        assertNotNull(user);
        
        userService.deleteUser(1L);
        
        // Cache should be evicted
        Cache cache = cacheManager.getCache("users");
        assertNull(cache.get(1L));
    }
}
```

---

## 🎯 Interview One-Liner

> Microservices caching: cache-aside (lazy loading), write-through (synchronous), write-behind (async); use Redis, implement invalidation, monitor hit ratios.

---

## ✅ 253. How would you migrate from monolith to microservices?

**Migrating from monolith to microservices involves incremental decomposition using the Strangler Fig pattern, establishing service boundaries, implementing communication patterns, and ensuring data consistency during the transition.**

### 🔹 Migration Strategy

**Strangler Fig pattern for gradual migration**

```java
// Monolith application structure
@RestController
@RequestMapping("/orders")
public class OrderController {
    
    @Autowired
    private OrderService orderService;
    
    @PostMapping
    public Order createOrder(@RequestBody OrderRequest request) {
        // Create order logic
        return orderService.createOrder(request);
    }
    
    @GetMapping("/{id}")
    public Order getOrder(@PathVariable Long id) {
        // Get order logic
        return orderService.getOrder(id);
    }
    
    // Other order-related endpoints
}

// Step 1: Extract Order service as separate microservice
@Service
public class OrderProxyService {
    
    @Autowired
    private RestTemplate restTemplate;
    
    private static final String ORDER_SERVICE_URL = "http://order-service";
    
    public Order createOrder(OrderRequest request) {
        // Check feature flag
        if (isOrderServiceEnabled()) {
            // Route to new microservice
            return restTemplate.postForObject(ORDER_SERVICE_URL + "/orders", request, Order.class);
        } else {
            // Use existing monolith logic
            return orderService.createOrder(request);
        }
    }
}
```

### 🔹 Feature Flags

**Controlling migration rollout**

```java
@Component
public class FeatureFlagService {
    
    @Autowired
    private ConfigService configService;
    
    public boolean isOrderServiceEnabled() {
        return configService.getBooleanProperty("features.order-service.enabled", false);
    }
    
    public boolean isPaymentServiceEnabled() {
        return configService.getBooleanProperty("features.payment-service.enabled", false);
    }
    
    public boolean isInventoryServiceEnabled() {
        return configService.getBooleanProperty("features.inventory-service.enabled", false);
    }
}

// Configuration properties
features:
  order-service:
    enabled: true
  payment-service:
    enabled: false
  inventory-service:
    enabled: true
```

### 🔹 Service Extraction Process

**Step-by-step decomposition**

```java
// Step 2: Create separate Order microservice
@SpringBootApplication
@EnableEurekaClient
public class OrderServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }
}

@RestController
@RequestMapping("/orders")
public class OrderController {
    
    @Autowired
    private OrderService orderService;
    
    @PostMapping
    public Order createOrder(@RequestBody OrderRequest request) {
        return orderService.createOrder(request);
    }
    
    @GetMapping("/{id}")
    public Order getOrder(@PathVariable Long id) {
        return orderService.getOrder(id);
    }
}

// Step 3: Implement service communication
@Configuration
public class ServiceCommunicationConfig {
    
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
    
    @Autowired
    private InventoryService inventoryService;
    
    @Transactional
    public Order createOrder(OrderRequest request) {
        // Validate inventory
        boolean inventoryAvailable = inventoryService.checkInventory(request.getItems());
        if (!inventoryAvailable) {
            throw new InsufficientInventoryException();
        }
        
        // Create order
        Order order = new Order(request);
        orderRepository.save(order);
        
        // Update inventory
        inventoryService.updateInventory(request.getItems());
        
        return order;
    }
}
```

### 🔹 Data Migration

**Handling shared database during migration**

```java
// Step 4: Database migration strategy
@Configuration
public class DataMigrationConfig {
    
    @Bean
    public FlywayMigrationStrategy migrationStrategy() {
        return flyway -> {
            // Run migrations for shared schema
            flyway.migrate();
            
            // Additional migration logic for service-specific tables
            createServiceSpecificTables();
        };
    }
}

@Service
public class DataMigrationService {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public void migrateOrderData() {
        // Migrate existing order data to new service
        jdbcTemplate.execute("""
            INSERT INTO order_service.orders (id, customer_id, total_amount, status, created_date)
            SELECT id, customer_id, total_amount, status, created_date 
            FROM monolithic.orders 
            WHERE migrated = false
        """);
        
        // Mark as migrated
        jdbcTemplate.execute("""
            UPDATE monolithic.orders SET migrated = true WHERE migrated = false
        """);
    }
}
```

### 🔹 API Gateway Implementation

**Routing and composition**

```java
@Configuration
public class GatewayConfig {
    
    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("order_route", r -> r
                        .path("/api/orders/**")
                        .filters(f -> f
                                .rewritePath("/api/orders/(?<segment>.*)", "/orders/${segment}")
                                .circuitBreaker(config -> config
                                        .setName("orderCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/orders"))
                        )
                        .uri("lb://order-service")
                )
                .route("inventory_route", r -> r
                        .path("/api/inventory/**")
                        .filters(f -> f
                                .rewritePath("/api/inventory/(?<segment>.*)", "/inventory/${segment}")
                        )
                        .uri("lb://inventory-service")
                )
                .build();
    }
}
```

### 🔹 Event-Driven Communication

**Implementing event sourcing for loose coupling**

```java
// Step 5: Event-driven architecture
@Service
public class OrderService {
    
    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;
    
    @Transactional
    public Order createOrder(OrderRequest request) {
        Order order = new Order(request);
        orderRepository.save(order);
        
        // Publish order created event
        OrderCreatedEvent event = new OrderCreatedEvent(order.getId(), order.getItems());
        kafkaTemplate.send("order-events", event);
        
        return order;
    }
}

@Component
public class InventoryEventHandler {
    
    @Autowired
    private InventoryService inventoryService;
    
    @KafkaListener(topics = "order-events")
    public void handleOrderCreated(OrderCreatedEvent event) {
        // Update inventory based on order
        inventoryService.reserveInventory(event.getOrderId(), event.getItems());
    }
}
```

### 🔹 Testing Migration

**Parallel testing of old and new systems**

```java
@SpringBootTest
@RunWith(SpringRunner.class)
public class MigrationTest {
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Test
    public void testParallelExecution() {
        OrderRequest request = createTestOrderRequest();
        
        // Test old monolith logic
        Order oldOrder = orderService.createOrderOld(request);
        
        // Test new microservice
        Order newOrder = restTemplate.postForObject(
            "http://order-service/orders", request, Order.class);
        
        // Compare results
        assertEquals(oldOrder.getTotalAmount(), newOrder.getTotalAmount());
        assertEquals(oldOrder.getItems().size(), newOrder.getItems().size());
    }
    
    @Test
    public void testFeatureFlag() {
        // Test with feature flag disabled
        System.setProperty("features.order-service.enabled", "false");
        assertFalse(featureFlagService.isOrderServiceEnabled());
        
        // Test with feature flag enabled
        System.setProperty("features.order-service.enabled", "true");
        assertTrue(featureFlagService.isOrderServiceEnabled());
    }
}
```

### 🔹 Monitoring and Rollback

**Migration monitoring and rollback strategy**

```java
@Service
public class MigrationMonitoringService {
    
    @Autowired
    private MeterRegistry meterRegistry;
    
    public void recordMigrationProgress(String serviceName, int percentage) {
        Gauge.builder("migration.progress", () -> percentage)
                .tag("service", serviceName)
                .register(meterRegistry);
    }
    
    public void recordServiceHealth(String serviceName, boolean healthy) {
        Gauge.builder("service.health", () -> healthy ? 1 : 0)
                .tag("service", serviceName)
                .register(meterRegistry);
    }
}

@Service
public class RollbackService {
    
    public void rollbackToMonolith(String serviceName) {
        // Disable feature flag
        configService.setProperty("features." + serviceName + ".enabled", "false");
        
        // Redirect traffic back to monolith
        updateGatewayRoutes(serviceName, "monolith");
        
        // Log rollback event
        logger.warn("Rolled back service: {} to monolith", serviceName);
    }
}
```

### 🔹 Best Practices

```java
// Start with less critical services for migration
// Use feature flags to control rollout
// Implement comprehensive monitoring and alerting
// Test thoroughly in staging environment
// Have rollback plan ready
// Migrate data incrementally
// Implement proper logging and tracing
// Use API gateway for routing
// Implement circuit breakers for resilience
// Document service boundaries and contracts
// Plan for eventual monolith decommissioning
```

### 🔹 Migration Checklist

**Comprehensive migration checklist**

```java
public class MigrationChecklist {
    
    // Pre-migration
    private boolean domainAnalysisComplete = false;
    private boolean serviceBoundariesDefined = false;
    private boolean dataMigrationPlanReady = false;
    private boolean testingStrategyDefined = false;
    
    // During migration
    private boolean featureFlagsImplemented = false;
    private boolean apiGatewayConfigured = false;
    private boolean monitoringSetup = false;
    private boolean rollbackPlanReady = false;
    
    // Post-migration
    private boolean performanceValidated = false;
    private boolean monolithDecommissioned = false;
    private boolean documentationUpdated = false;
    
    // Validation methods
    public boolean isReadyForMigration() {
        return domainAnalysisComplete && serviceBoundariesDefined && 
               dataMigrationPlanReady && testingStrategyDefined;
    }
    
    public boolean isMigrationComplete() {
        return performanceValidated && monolithDecommissioned && documentationUpdated;
    }
}
```

---

## 🎯 Interview One-Liner

> Monolith to microservices migration: Strangler Fig pattern, feature flags, incremental extraction, API gateway routing, event-driven communication, comprehensive testing.

---

## ✅ 254. Explain your current project architecture

**This is a behavioral question requiring explanation of current project architecture, focusing on design patterns, technologies used, scalability considerations, and lessons learned.**

### 🔹 Project Architecture Overview

**High-level system architecture**

```java
// E-commerce platform architecture
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableCircuitBreaker
public class EcommerceApplication {
    public static void main(String[] args) {
        SpringApplication.run(EcommerceApplication.class, args);
    }
}

// Microservices architecture
- API Gateway (Spring Cloud Gateway)
- User Service (authentication, profiles)
- Product Service (catalog, inventory)
- Order Service (order management)
- Payment Service (payment processing)
- Notification Service (emails, SMS)
- Recommendation Service (AI-based recommendations)
```

### 🔹 Service Architecture Pattern

**Hexagonal architecture implementation**

```java
// Domain layer
public class Order {
    private Long id;
    private List<OrderItem> items;
    private OrderStatus status;
    
    public void addItem(Product product, int quantity) {
        // Business logic
    }
    
    public BigDecimal calculateTotal() {
        // Calculation logic
    }
}

// Application layer
@Service
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private PaymentService paymentService;
    
    @Transactional
    public Order createOrder(CreateOrderCommand command) {
        // Application logic
        Order order = new Order(command);
        orderRepository.save(order);
        
        // Integrate with payment service
        paymentService.processPayment(order);
        
        return order;
    }
}

// Infrastructure layer
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerId(Long customerId);
}

@RestController
@RequestMapping("/orders")
public class OrderController {
    
    @Autowired
    private OrderService orderService;
    
    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody CreateOrderRequest request) {
        Order order = orderService.createOrder(request.toCommand());
        return ResponseEntity.ok(OrderDTO.from(order));
    }
}
```

### 🔹 Technology Stack

**Technology choices and rationale**

```yaml
# Technology stack
spring:
  profiles:
    active: prod
  cloud:
    config:
      uri: http://config-server:8888
      
# Database configuration
spring:
  datasource:
    url: jdbc:postgresql://postgres:5432/ecommerce
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
  jpa:
    hibernate:
      ddl-auto: validate
  redis:
    host: redis-service
    
# Message queue
spring:
  kafka:
    bootstrap-servers: kafka:9092
    consumer:
      group-id: ecommerce-group
```

### 🔹 Scalability Considerations

**Horizontal scaling and load balancing**

```java
@Configuration
public class LoadBalancingConfig {
    
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    
    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}

// Kubernetes deployment for scalability
apiVersion: apps/v1
kind: Deployment
metadata:
  name: order-service
spec:
  replicas: 3
  selector:
    matchLabels:
      app: order-service
  template:
    metadata:
      labels:
        app: order-service
    spec:
      containers:
      - name: order-service
        image: ecommerce/order-service:latest
        ports:
        - containerPort: 8080
        env:
        - name: SPRING_PROFILES_ACTIVE
          value: "k8s"
        resources:
          requests:
            memory: "512Mi"
            cpu: "250m"
          limits:
            memory: "1Gi"
            cpu: "500m"
```

### 🔹 Security Implementation

**Authentication and authorization**

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/api/public/**").permitAll()
                .antMatchers("/api/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            .and()
            .oauth2ResourceServer()
                .jwt();
    }
}

@RestController
@RequestMapping("/api/user")
public class UserController {
    
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/profile")
    public UserProfile getUserProfile(@AuthenticationPrincipal User user) {
        return userService.getUserProfile(user.getId());
    }
}
```

### 🔹 Monitoring and Observability

**Comprehensive monitoring setup**

```java
@Configuration
public class MonitoringConfig {
    
    @Bean
    public MeterRegistry meterRegistry() {
        return new SimpleMeterRegistry();
    }
    
    @Bean
    public TimedAspect timedAspect(MeterRegistry registry) {
        return new TimedAspect(registry);
    }
}

@RestController
@RequestMapping("/actuator")
public class CustomMetricsController {
    
    @Autowired
    private MeterRegistry meterRegistry;
    
    @GetMapping("/metrics/orders")
    public Map<String, Object> getOrderMetrics() {
        Map<String, Object> metrics = new HashMap<>();
        metrics.put("total_orders", meterRegistry.counter("orders.created").count());
        metrics.put("active_users", meterRegistry.gauge("users.active", Gauge.builder()));
        return metrics;
    }
}
```

### 🔹 Database Design

**Normalized database schema**

```sql
-- User service database
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    created_date TIMESTAMP NOT NULL,
    last_login TIMESTAMP
);

-- Product service database
CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    category_id BIGINT REFERENCES categories(id),
    inventory_count INTEGER NOT NULL DEFAULT 0,
    created_date TIMESTAMP NOT NULL
);

-- Order service database
CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_date TIMESTAMP NOT NULL,
    updated_date TIMESTAMP
);

CREATE TABLE order_items (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT REFERENCES orders(id),
    product_id BIGINT NOT NULL,
    quantity INTEGER NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL
);
```

### 🔹 API Design

**RESTful API design principles**

```java
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    
    @Autowired
    private ProductService productService;
    
    @GetMapping
    public ResponseEntity<Page<ProductDTO>> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "name") String sort) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        Page<Product> products = productService.getProducts(category, pageable);
        
        return ResponseEntity.ok(products.map(ProductDTO::from));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id) {
        Product product = productService.getProduct(id);
        return ResponseEntity.ok(ProductDTO.from(product));
    }
    
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody CreateProductRequest request) {
        Product product = productService.createProduct(request);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(product.getId())
                .toUri();
        
        return ResponseEntity.created(location).body(ProductDTO.from(product));
    }
}
```

### 🔹 Best Practices Implemented

```java
// Domain-driven design principles
// Microservices decomposition by business capability
// Event-driven architecture for loose coupling
// CQRS pattern for read/write optimization
// Circuit breaker pattern for resilience
// Comprehensive logging and monitoring
// Automated testing (unit, integration, E2E)
// CI/CD pipeline with automated deployment
// Containerization with Docker
// Orchestration with Kubernetes
// Security best practices (OAuth2, JWT, HTTPS)
// Performance optimization (caching, indexing, pagination)
```

### 🔹 Challenges Faced

**Technical challenges and solutions**

```java
// Challenge: Data consistency across services
// Solution: Saga pattern with event sourcing

// Challenge: Service discovery and communication
// Solution: Eureka server with Feign clients

// Challenge: Distributed transactions
// Solution: Eventual consistency with compensating actions

// Challenge: Debugging distributed systems
// Solution: Centralized logging with ELK stack and distributed tracing with Sleuth/Zipkin

// Challenge: Performance bottlenecks
// Solution: Caching with Redis, database optimization, horizontal scaling
```

### 🔹 Lessons Learned

**Key takeaways from the project**

```java
// Importance of proper domain modeling
// Value of automated testing and CI/CD
// Need for comprehensive monitoring
// Benefits of microservices when done right
// Complexity of distributed systems
// Importance of team communication and documentation
// Value of evolutionary architecture
// Need for proper DevOps practices
```

---

## 🎯 Interview One-Liner

> Current project: microservices e-commerce platform with Spring Cloud, Kubernetes, event-driven architecture, CQRS, comprehensive monitoring, and DevOps practices.

---

## ✅ 255. How do you handle production issues?

**Production issue handling involves systematic debugging, monitoring, incident response, and preventive measures to minimize downtime and ensure quick resolution.**

### 🔹 Incident Response Process

**Structured approach to production issues**

```java
@Service
public class IncidentResponseService {
    
    @Autowired
    private AlertService alertService;
    
    @Autowired
    private MonitoringService monitoringService;
    
    public void handleProductionIssue(String issueId, String description, Severity severity) {
        // 1. Acknowledge the issue
        Incident incident = createIncident(issueId, description, severity);
        
        // 2. Assess impact
        ImpactAssessment assessment = assessImpact(incident);
        
        // 3. Notify stakeholders
        notifyStakeholders(incident, assessment);
        
        // 4. Start investigation
        InvestigationResult result = investigateIssue(incident);
        
        // 5. Implement fix
        Fix fix = implementFix(result);
        
        // 6. Test and deploy
        deployFix(fix);
        
        // 7. Post-mortem
        conductPostMortem(incident, fix);
    }
}
```

### 🔹 Monitoring and Alerting

**Comprehensive monitoring setup**

```java
@Configuration
public class MonitoringConfig {
    
    @Bean
    public MeterRegistry meterRegistry() {
        return new SimpleMeterRegistry();
    }
}

@Service
public class AlertService {
    
    @Autowired
    private MeterRegistry meterRegistry;
    
    @Autowired
    private NotificationService notificationService;
    
    public void checkSystemHealth() {
        // CPU usage alert
        Gauge cpuGauge = meterRegistry.gauge("system.cpu.usage", Gauge.builder());
        if (cpuGauge.value() > 90.0) {
            notificationService.sendAlert("High CPU usage detected", Severity.CRITICAL);
        }
        
        // Memory usage alert
        Gauge memoryGauge = meterRegistry.gauge("system.memory.usage", Gauge.builder());
        if (memoryGauge.value() > 85.0) {
            notificationService.sendAlert("High memory usage detected", Severity.WARNING);
        }
        
        // Error rate alert
        Counter errorCounter = meterRegistry.counter("http.requests.errors");
        if (errorCounter.count() > 100) {
            notificationService.sendAlert("High error rate detected", Severity.CRITICAL);
        }
    }
}
```

### 🔹 Debugging Tools

**Debugging production issues**

```java
@RestController
@RequestMapping("/debug")
public class DebugController {
    
    @Autowired
    private HealthCheckService healthCheckService;
    
    @Autowired
    private MetricsService metricsService;
    
    @GetMapping("/health")
    public Health health() {
        return healthCheckService.checkHealth();
    }
    
    @GetMapping("/metrics")
    public Map<String, Object> getMetrics() {
        return metricsService.getCurrentMetrics();
    }
    
    @GetMapping("/thread-dump")
    public String getThreadDump() {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(true, true);
        
        StringBuilder sb = new StringBuilder();
        for (ThreadInfo threadInfo : threadInfos) {
            sb.append(threadInfo.toString()).append("\n");
        }
        
        return sb.toString();
    }
    
    @GetMapping("/heap-dump")
    public ResponseEntity<String> triggerHeapDump() throws IOException {
        String fileName = "heap-dump-" + System.currentTimeMillis() + ".hprof";
        HotSpotDiagnosticMXBean diagnosticMXBean = ManagementFactory.getPlatformMXBean(HotSpotDiagnosticMXBean.class);
        diagnosticMXBean.dumpHeap(fileName, true);
        
        return ResponseEntity.ok("Heap dump created: " + fileName);
    }
}
```

### 🔹 Log Analysis

**Centralized logging and analysis**

```java
@Configuration
public class LoggingConfig {
    
    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(10000);
        filter.setIncludeHeaders(false);
        filter.setAfterMessagePrefix("REQUEST DATA: ");
        return filter;
    }
}

@Service
public class LogAnalysisService {
    
    public List<LogEntry> analyzeLogs(String serviceName, LocalDateTime startTime, LocalDateTime endTime) {
        // Query logs from ELK stack or cloud logging service
        // Analyze for patterns, errors, performance issues
        return logRepository.findLogsByServiceAndTimeRange(serviceName, startTime, endTime);
    }
    
    public List<String> findErrorPatterns(List<LogEntry> logs) {
        return logs.stream()
                .filter(log -> log.getLevel().equals("ERROR"))
                .map(LogEntry::getMessage)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .filter(entry -> entry.getValue() > 5)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
```

### 🔹 Rollback Strategy

**Safe rollback procedures**

```java
@Service
public class RollbackService {
    
    @Autowired
    private DeploymentService deploymentService;
    
    @Autowired
    private DatabaseBackupService backupService;
    
    public void rollbackDeployment(String serviceName, String version) {
        // 1. Stop current deployment
        deploymentService.stopService(serviceName);
        
        // 2. Restore previous version
        deploymentService.deployVersion(serviceName, getPreviousVersion(version));
        
        // 3. Restore database if needed
        if (isDatabaseChange(version)) {
            backupService.restoreBackup(getLastStableBackup());
        }
        
        // 4. Verify rollback
        Health health = healthCheckService.checkServiceHealth(serviceName);
        if (!health.getStatus().equals(Status.UP)) {
            // Escalation needed
            alertService.sendAlert("Rollback failed for " + serviceName, Severity.CRITICAL);
        }
    }
    
    private String getPreviousVersion(String currentVersion) {
        // Logic to determine previous stable version
        return versionRepository.findPreviousStableVersion(currentVersion);
    }
}
```

### 🔹 Root Cause Analysis

**Post-mortem process**

```java
@Service
public class PostMortemService {
    
    public PostMortemReport conductPostMortem(Incident incident, Fix fix) {
        PostMortemReport report = new PostMortemReport();
        
        // Timeline of events
        report.setTimeline(createTimeline(incident));
        
        // Root cause
        report.setRootCause(analyzeRootCause(incident));
        
        // Impact assessment
        report.setImpact(assessImpact(incident));
        
        // Resolution steps
        report.setResolutionSteps(fix.getSteps());
        
        // Preventive measures
        report.setPreventiveMeasures(suggestImprovements(incident));
        
        // Lessons learned
        report.setLessonsLearned(extractLessons(incident));
        
        return report;
    }
    
    private String analyzeRootCause(Incident incident) {
        // Use 5-whys technique or fishbone diagram
        // Analyze logs, metrics, code changes
        // Interview team members
        return "Root cause analysis result";
    }
}
```

### 🔹 Preventive Measures

**Proactive issue prevention**

```java
@Service
public class PreventiveMaintenanceService {
    
    @Scheduled(fixedRate = 3600000) // Run every hour
    public void performHealthChecks() {
        // Database connection checks
        checkDatabaseConnectivity();
        
        // External service dependencies
        checkExternalServices();
        
        // Disk space monitoring
        checkDiskSpace();
        
        // Certificate expiration
        checkCertificateExpiration();
    }
    
    @Scheduled(cron = "0 0 2 * * ?") // Daily at 2 AM
    public void performMaintenanceTasks() {
        // Database optimization
        optimizeDatabase();
        
        // Log rotation
        rotateLogs();
        
        // Cache cleanup
        cleanupCache();
        
        // Update dependencies
        updateDependencies();
    }
}
```

### 🔹 Communication

**Stakeholder communication**

```java
@Service
public class CommunicationService {
    
    @Autowired
    private EmailService emailService;
    
    @Autowired
    private SlackService slackService;
    
    public void notifyIncident(Incident incident, List<String> stakeholders) {
        String message = createIncidentMessage(incident);
        
        // Email notification
        emailService.sendEmail(stakeholders, "Production Incident", message);
        
        // Slack notification
        slackService.sendMessage("#incidents", message);
        
        // Status page update
        statusPageService.updateIncident(incident);
    }
    
    public void sendStatusUpdate(Incident incident, String update) {
        String message = createStatusUpdateMessage(incident, update);
        
        // Notify subscribers
        notificationService.sendToSubscribers(message);
    }
}
```

### 🔹 Best Practices

```java
// Implement comprehensive monitoring and alerting
// Have clear incident response procedures
// Maintain detailed logs and audit trails
// Implement gradual rollouts and canary deployments
// Have rollback strategies ready
// Conduct regular post-mortems
// Implement automated testing and chaos engineering
// Maintain runbooks for common issues
// Foster blameless culture
// Invest in observability tools
```

### 🔹 Tools and Technologies

**Production issue handling tools**

```yaml
# Monitoring stack
monitoring:
  - Prometheus (metrics collection)
  - Grafana (visualization)
  - AlertManager (alerting)
  
# Logging
logging:
  - ELK Stack (Elasticsearch, Logstash, Kibana)
  - Fluentd (log aggregation)
  
# Debugging
debugging:
  - JVM tools (jstack, jmap, jstat)
  - APM tools (New Relic, Dynatrace)
  - Distributed tracing (Jaeger, Zipkin)
  
# Incident management
incident_management:
  - PagerDuty (alerting)
  - Jira Service Desk (ticketing)
  - Statuspage (communication)
```

---

## 🎯 Interview One-Liner

> Production issues: structured incident response, monitoring/alerting, log analysis, root cause analysis, rollback strategies, post-mortems, preventive maintenance.

---

## ✅ 256. Tell me about a challenging bug you fixed

**This is a behavioral question requiring STAR (Situation-Task-Action-Result) format to describe a challenging debugging experience, technical problem-solving skills, and lessons learned.**

### 🔹 STAR Format Response

**Situation-Task-Action-Result structure**

```java
// Example: Memory leak in production e-commerce application
public class BugFixExample {
    
    // Situation: Describe the context
    public void describeSituation() {
        System.out.println("In our e-commerce platform, we experienced intermittent OutOfMemoryError");
        System.out.println("during peak traffic hours, causing service degradation and customer complaints.");
        System.out.println("The application was a Spring Boot microservice handling order processing.");
    }
    
    // Task: Explain your responsibility
    public void describeTask() {
        System.out.println("As the lead backend developer, I was responsible for investigating");
        System.out.println("the root cause, implementing a fix, and ensuring it didn't recur.");
        System.out.println("The challenge was that the issue only occurred under high load conditions.");
    }
    
    // Action: Detail the steps taken
    public void describeAction() {
        System.out.println("1. Analyzed heap dumps using Eclipse MAT to identify memory leak patterns");
        System.out.println("2. Reviewed code for potential memory leaks (unclosed streams, static collections)");
        System.out.println("3. Implemented thread dump analysis during peak load");
        System.out.println("4. Added custom metrics and monitoring for memory usage");
        System.out.println("5. Identified the root cause: improper connection pooling in database layer");
        System.out.println("6. Implemented connection pool monitoring and leak detection");
        System.out.println("7. Added circuit breaker pattern for database calls");
        System.out.println("8. Conducted thorough testing under load conditions");
    }
    
    // Result: Explain the outcome
    public void describeResult() {
        System.out.println("Successfully eliminated the OutOfMemoryError occurrences.");
        System.out.println("Improved application stability during peak loads by 95%.");
        System.out.println("Reduced incident response time through better monitoring.");
        System.out.println("Implemented preventive measures for similar issues.");
    }
}
```

### 🔹 Technical Debugging Process

**Systematic debugging approach**

```java
@Service
public class DebuggingProcess {
    
    public void debugMemoryLeak() {
        // 1. Reproduce the issue
        reproduceIssue();
        
        // 2. Gather diagnostic information
        HeapDump heapDump = captureHeapDump();
        ThreadDump threadDump = captureThreadDump();
        GCLogs gcLogs = analyzeGCLogs();
        
        // 3. Analyze root cause
        MemoryLeakAnalysis analysis = analyzeMemoryLeak(heapDump);
        
        // 4. Implement fix
        Fix fix = implementFix(analysis);
        
        // 5. Test fix
        testFix(fix);
        
        // 6. Deploy and monitor
        deployAndMonitor(fix);
    }
    
    private void reproduceIssue() {
        // Use JMeter or similar tool to simulate load
        LoadTest loadTest = new LoadTest();
        loadTest.simulatePeakLoad();
    }
    
    private HeapDump captureHeapDump() {
        // jmap -dump:format=b,file=heap.hprof <pid>
        return HeapDumpAnalyzer.capture();
    }
    
    private MemoryLeakAnalysis analyzeMemoryLeak(HeapDump heapDump) {
        // Use Eclipse MAT or similar tools
        return MemoryAnalyzer.analyze(heapDump);
    }
}
```

### 🔹 Common Bug Patterns

**Frequent bug categories and fixes**

```java
// 1. Memory leaks
public class MemoryLeakPatterns {
    
    // Anti-pattern: Static collections growing indefinitely
    public static class MemoryLeakAntiPattern {
        private static List<Object> cache = new ArrayList<>();
        
        public static void addToCache(Object obj) {
            cache.add(obj); // Objects never removed
        }
    }
    
    // Fixed pattern: Bounded cache with cleanup
    public static class MemoryLeakFix {
        private static final int MAX_CACHE_SIZE = 1000;
        private static List<Object> cache = Collections.synchronizedList(new ArrayList<>());
        
        public static void addToCache(Object obj) {
            synchronized(cache) {
                if (cache.size() >= MAX_CACHE_SIZE) {
                    cache.remove(0); // Remove oldest
                }
                cache.add(obj);
            }
        }
    }
}

// 2. Race conditions
public class RaceConditionPatterns {
    
    // Anti-pattern: Non-thread-safe counter
    public static class RaceConditionAntiPattern {
        private static int counter = 0;
        
        public static void increment() {
            counter++; // Not atomic
        }
    }
    
    // Fixed pattern: Atomic operations
    public static class RaceConditionFix {
        private static AtomicInteger counter = new AtomicInteger(0);
        
        public static void increment() {
            counter.incrementAndGet(); // Thread-safe
        }
    }
}

// 3. Connection leaks
public class ConnectionLeakPatterns {
    
    // Anti-pattern: Unclosed connections
    public static class ConnectionLeakAntiPattern {
        public void processData() throws SQLException {
            Connection conn = dataSource.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");
            // Forgot to close resources
        }
    }
    
    // Fixed pattern: Try-with-resources
    public static class ConnectionLeakFix {
        public void processData() throws SQLException {
            try (Connection conn = dataSource.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM users")) {
                // Process results
            } // Resources automatically closed
        }
    }
}
```

### 🔹 Debugging Tools and Techniques

**Essential debugging toolkit**

```java
public class DebuggingToolkit {
    
    // JVM diagnostic tools
    public void useJVMTools() {
        // jps - List JVM processes
        // jstack - Thread dump
        // jmap - Heap dump
        // jstat - JVM statistics
        // jcmd - JVM diagnostic commands
    }
    
    // Application monitoring
    public void useMonitoringTools() {
        // Application Metrics
        Counter requestCounter = meterRegistry.counter("requests.total");
        Timer requestTimer = meterRegistry.timer("requests.duration");
        
        // Custom health checks
        @Component
        public class CustomHealthIndicator implements HealthIndicator {
            @Override
            public Health health() {
                // Check database connectivity
                // Check external service health
                // Check disk space
                return Health.up().build();
            }
        }
    }
    
    // Logging strategies
    public void implementLogging() {
        // Structured logging
        logger.info("Processing order", Map.of(
            "orderId", orderId,
            "userId", userId,
            "amount", amount
        ));
        
        // Correlation IDs for distributed tracing
        MDC.put("correlationId", correlationId);
        logger.info("Starting order processing");
        // ... processing logic
        logger.info("Order processing completed");
        MDC.clear();
    }
}
```

### 🔹 Testing After Bug Fix

**Comprehensive testing strategies**

```java
@SpringBootTest
@RunWith(SpringRunner.class)
public class BugFixVerificationTest {
    
    @Test
    public void testMemoryLeakFix() {
        // Test under load
        for (int i = 0; i < 10000; i++) {
            service.processRequest(new Request());
        }
        
        // Verify memory usage
        Runtime runtime = Runtime.getRuntime();
        long usedMemory = runtime.totalMemory() - runtime.freeMemory();
        long maxMemory = runtime.maxMemory();
        
        double memoryUsagePercent = (double) usedMemory / maxMemory * 100;
        assertTrue(memoryUsagePercent < 80, "Memory usage should be under 80%");
    }
    
    @Test
    public void testRaceConditionFix() {
        // Concurrent access test
        ExecutorService executor = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(100);
        
        for (int i = 0; i < 100; i++) {
            executor.submit(() -> {
                service.incrementCounter();
                latch.countDown();
            });
        }
        
        latch.await();
        assertEquals(100, service.getCounter());
    }
    
    @Test
    public void testConnectionLeakFix() {
        // Test connection pool usage
        for (int i = 0; i < 100; i++) {
            service.executeQuery();
        }
        
        // Verify connections are returned to pool
        assertTrue(dataSource.getActiveConnections() < 10, "Active connections should be minimal");
    }
}
```

### 🔹 Lessons Learned

**Key takeaways from debugging experiences**

```java
public class LessonsLearned {
    
    // Prevention is better than cure
    public void implementPreventiveMeasures() {
        // Code reviews
        // Automated testing
        // Static code analysis
        // Performance monitoring
        // Chaos engineering
    }
    
    // Documentation and knowledge sharing
    public void documentAndShare() {
        // Update runbooks
        // Share post-mortems
        // Create troubleshooting guides
        // Train team members
    }
    
    // Tooling and automation
    public void investInTooling() {
        // Monitoring dashboards
        // Alerting systems
        // Automated rollback
        // Performance regression tests
    }
    
    // Mindset and culture
    public void fosterGoodPractices() {
        // Blameless post-mortems
        // Continuous learning
        // Psychological safety
        // Knowledge sharing culture
    }
}
```

### 🔹 Best Practices

```java
// Follow systematic debugging process
// Use appropriate tools for different types of issues
// Implement comprehensive logging
// Write testable code
// Conduct thorough testing after fixes
// Document lessons learned
// Share knowledge with team
// Implement preventive measures
// Monitor for regressions
// Foster collaborative debugging culture
```

---

## 🎯 Interview One-Liner

> Challenging bug fix: memory leak in production e-commerce app; used heap dumps, thread analysis, identified connection pool issue; implemented monitoring, fixed with circuit breaker; improved stability 95%.

---

## ✅ 257. How do you ensure code quality?

**Code quality assurance involves multiple practices including testing, code reviews, static analysis, continuous integration, and automated tools to maintain high standards.**

### 🔹 Testing Strategy

**Comprehensive testing pyramid**

```java
@SpringBootTest
@RunWith(SpringRunner.class)
public class UnitTest {
    
    @Test
    public void testBusinessLogic() {
        // Unit tests for individual components
        UserService userService = new UserService(mockRepository);
        User user = userService.createUser("john@example.com");
        assertNotNull(user);
        assertEquals("john@example.com", user.getEmail());
    }
}

@SpringBootTest
@RunWith(SpringRunner.class)
public class IntegrationTest {
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    public void testUserAPI() {
        // Integration tests for API endpoints
        ResponseEntity<UserDTO> response = restTemplate.postForEntity(
            "/api/users", createUserRequest, UserDTO.class);
        
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody().getId());
    }
}

@SpringBootTest
@RunWith(SpringRunner.class)
public class EndToEndTest {
    
    @Autowired
    private WebDriver webDriver;
    
    @Test
    public void testUserRegistrationFlow() {
        // E2E tests for complete user journeys
        webDriver.get("http://localhost:8080/register");
        // ... complete registration flow
        assertTrue(webDriver.getPageSource().contains("Registration successful"));
    }
}
```

### 🔹 Code Review Process

**Structured code review workflow**

```java
@Service
public class CodeReviewService {
    
    public CodeReviewResult reviewPullRequest(PullRequest pr) {
        CodeReviewResult result = new CodeReviewResult();
        
        // Automated checks
        result.addIssues(runStaticAnalysis(pr));
        result.addIssues(checkTestCoverage(pr));
        result.addIssues(validateCodingStandards(pr));
        
        // Manual review checklist
        result.addChecklistItem("Security vulnerabilities checked");
        result.addChecklistItem("Performance implications reviewed");
        result.addChecklistItem("Error handling implemented");
        result.addChecklistItem("Documentation updated");
        
        return result;
    }
    
    private List<Issue> runStaticAnalysis(PullRequest pr) {
        // Run SonarQube, PMD, FindBugs, etc.
        return staticAnalysisTool.analyze(pr.getFiles());
    }
    
    private List<Issue> checkTestCoverage(PullRequest pr) {
        // Check test coverage with JaCoCo
        CoverageReport report = jacocoAnalyzer.analyze(pr);
        return report.getCoverage() < 80 ? 
            List.of(new Issue("Test coverage below 80%")) : List.of();
    }
}
```

### 🔹 Static Code Analysis

**Automated code quality checks**

```xml
<!-- pom.xml for Maven project -->
<build>
    <plugins>
        <!-- SonarQube for comprehensive analysis -->
        <plugin>
            <groupId>org.sonarsource.scanner.maven</groupId>
            <artifactId>sonar-maven-plugin</artifactId>
            <version>3.9.1.2184</version>
        </plugin>
        
        <!-- PMD for code rule violations -->
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-pmd-plugin</artifactId>
            <version>3.15.0</version>
            <configuration>
                <rulesets>
                    <ruleset>rulesets/java/basic.xml</ruleset>
                    <ruleset>rulesets/java/imports.xml</ruleset>
                    <ruleset>rulesets/java/unusedcode.xml</ruleset>
                </rulesets>
            </configuration>
        </plugin>
        
        <!-- Checkstyle for coding standards -->
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-checkstyle-plugin</artifactId>
            <version>3.1.2</version>
            <configuration>
                <configLocation>checkstyle.xml</configLocation>
            </configuration>
        </plugin>
    </plugins>
</build>
```

### 🔹 Continuous Integration

**CI/CD pipeline for quality gates**

```yaml
# GitHub Actions CI pipeline
name: CI Pipeline

on: [push, pull_request]

jobs:
  test:
    runs-on: ubuntu-latest
    
    steps:
    - uses: actions/checkout@v2
    
    - name: Set up JDK
      uses: actions/setup-java@v2
      with:
        java-version: '11'
        distribution: 'adopt'
    
    - name: Cache Maven packages
      uses: actions/cache@v2
      with:
        path: ~/.m2
        key: ${{ runner.os }}-m2-${{ hashFiles('**/pom.xml') }}
        restore-keys: ${{ runner.os }}-m2
    
    - name: Run tests
      run: mvn test
    
    - name: Generate coverage report
      run: mvn jacoco:report
    
    - name: Run SonarQube analysis
      run: mvn sonar:sonar
      env:
        SONAR_TOKEN: ${{ secrets.SONAR_TOKEN }}
    
    - name: Check code quality gates
      run: |
        if [ $(cat target/site/jacoco/jacoco.xml | grep -oP '(?<=line-covered>)[^<]+' | awk '{sum+=$1} END {print sum}') -lt 80 ]; then
          echo "Test coverage below 80%"
          exit 1
        fi
```

### 🔹 Code Quality Metrics

**Measuring and tracking quality**

```java
@Service
public class CodeQualityMetricsService {
    
    @Autowired
    private SonarQubeClient sonarClient;
    
    @Autowired
    private JaCoCoClient jacocoClient;
    
    public CodeQualityReport generateReport(String projectKey) {
        CodeQualityReport report = new CodeQualityReport();
        
        // SonarQube metrics
        SonarMetrics sonarMetrics = sonarClient.getMetrics(projectKey);
        report.setTechnicalDebt(sonarMetrics.getTechnicalDebt());
        report.setCodeSmells(sonarMetrics.getCodeSmells());
        report.setBugs(sonarMetrics.getBugs());
        report.setVulnerabilities(sonarMetrics.getVulnerabilities());
        
        // Test coverage
        CoverageMetrics coverage = jacocoClient.getCoverage(projectKey);
        report.setLineCoverage(coverage.getLineCoverage());
        report.setBranchCoverage(coverage.getBranchCoverage());
        
        // Complexity metrics
        report.setCyclomaticComplexity(sonarMetrics.getComplexity());
        
        return report;
    }
    
    @Scheduled(fixedRate = 86400000) // Daily
    public void trackQualityTrends() {
        CodeQualityReport report = generateReport("my-project");
        qualityMetricsRepository.save(report);
        
        // Alert if quality degrades
        if (report.getCodeSmells() > previousReport.getCodeSmells() * 1.1) {
            alertService.sendAlert("Code smell count increased significantly");
        }
    }
}
```

### 🔹 Coding Standards

**Enforced coding conventions**

```xml
<!-- checkstyle.xml -->
<module name="Checker">
    <property name="charset" value="UTF-8"/>
    
    <module name="TreeWalker">
        <!-- Naming conventions -->
        <module name="ConstantName"/>
        <module name="LocalVariableName"/>
        <module name="MemberName"/>
        <module name="MethodName"/>
        <module name="PackageName"/>
        
        <!-- Code style -->
        <module name="AvoidInlineConditionals"/>
        <module name="EmptyBlock"/>
        <module name="EmptyCatchBlock"/>
        <module name="HiddenField"/>
        <module name="IllegalInstantiation"/>
        <module name="MagicNumber"/>
        <module name="MissingSwitchDefault"/>
        
        <!-- Size violations -->
        <module name="CyclomaticComplexity">
            <property name="max" value="10"/>
        </module>
        <module name="MethodLength">
            <property name="max" value="50"/>
        </module>
        <module name="ParameterNumber">
            <property name="max" value="7"/>
        </module>
    </module>
</module>
```

### 🔹 Documentation

**Code documentation practices**

```java
/**
 * Service class for managing user operations.
 * This class provides methods for creating, updating, and retrieving user information.
 * 
 * @author Your Name
 * @version 1.0
 * @since 2023-01-01
 */
@Service
public class UserService {
    
    /**
     * Creates a new user with the provided information.
     * 
     * @param email the user's email address (must be unique)
     * @param name the user's full name
     * @return the created User object
     * @throws UserAlreadyExistsException if a user with the email already exists
     * @throws InvalidEmailException if the email format is invalid
     */
    public User createUser(String email, String name) {
        // Implementation
    }
    
    /**
     * Retrieves a user by their ID.
     * 
     * @param id the unique identifier of the user
     * @return the User object if found
     * @throws UserNotFoundException if no user exists with the given ID
     */
    public User getUserById(Long id) {
        // Implementation
    }
}
```

### 🔹 Peer Review Guidelines

**Code review checklist**

```java
public class CodeReviewChecklist {
    
    // Functionality
    private boolean requirementsMet = false;
    private boolean edgeCasesHandled = false;
    private boolean errorHandlingImplemented = false;
    
    // Code Quality
    private boolean readableCode = false;
    private boolean properNaming = false;
    private boolean noCodeDuplication = false;
    
    // Testing
    private boolean unitTestsWritten = false;
    private boolean integrationTestsIncluded = false;
    private boolean adequateTestCoverage = false;
    
    // Security
    private boolean inputValidation = false;
    private boolean sqlInjectionPrevented = false;
    private boolean xssProtectionImplemented = false;
    
    // Performance
    private boolean efficientAlgorithms = false;
    private boolean noMemoryLeaks = false;
    private boolean properResourceManagement = false;
    
    // Documentation
    private boolean codeDocumented = false;
    private boolean apiDocumented = false;
    private boolean changesLogged = false;
    
    public boolean isApproved() {
        return requirementsMet && readableCode && unitTestsWritten && 
               inputValidation && efficientAlgorithms && codeDocumented;
    }
}
```

### 🔹 Best Practices

```java
// Implement comprehensive testing strategy
// Use static code analysis tools
// Enforce coding standards
// Conduct thorough code reviews
// Maintain high test coverage (>80%)
// Document code and APIs
// Use CI/CD with quality gates
// Track quality metrics over time
// Foster collaborative culture
// Continuous learning and improvement
```

### 🔹 Tools and Technologies

**Code quality toolchain**

```yaml
# Testing frameworks
testing:
  - JUnit 5 (unit testing)
  - Mockito (mocking)
  - Testcontainers (integration testing)
  - Selenium (E2E testing)
  
# Static analysis
static_analysis:
  - SonarQube (comprehensive analysis)
  - PMD (rule violations)
  - Checkstyle (coding standards)
  - SpotBugs (bug detection)
  
# Coverage
coverage:
  - JaCoCo (coverage reports)
  - Coveralls (coverage tracking)
  
# CI/CD
ci_cd:
  - Jenkins (pipeline automation)
  - GitHub Actions (cloud CI)
  - GitLab CI (integrated CI)
  
# Documentation
documentation:
  - Swagger/OpenAPI (API docs)
  - JavaDoc (code docs)
  - Confluence (knowledge base)
```

---

## 🎯 Interview One-Liner

> Code quality: comprehensive testing (unit/integration/E2E), static analysis (SonarQube), code reviews, CI/CD quality gates, documentation, high test coverage, coding standards.