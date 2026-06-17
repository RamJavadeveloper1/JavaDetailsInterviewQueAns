### **REST Security**
125. How to secure REST APIs?
126. What is JWT token?
127. OAuth 2.0 flow
128. What is CORS? How to enable it?

## ✅ How to Secure REST APIs?

REST APIs require multiple layers of security to protect against common threats like unauthorized access, data breaches, and injection attacks. Here's a comprehensive approach:

### 🔹 Authentication & Authorization

**Implement proper user identification and access control**

```java
// Spring Security configuration
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
            .httpBasic(); // or JWT, OAuth
    }
}
```

### 🔹 HTTPS/TLS Encryption

**Encrypt all data in transit**

```bash
# Enable HTTPS in Spring Boot
server.ssl.key-store=classpath:keystore.jks
server.ssl.key-store-password=password
server.ssl.keyAlias=tomcat
```

### 🔹 Input Validation & Sanitization

**Prevent injection attacks**

```java
@PostMapping("/users")
public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
    // @Valid triggers validation
}

public class User {
    @NotNull
    @Size(min = 2, max = 50)
    private String name;
    
    @Email
    private String email;
}
```
### 🔹 Rate Limiting
**Prevent abuse and DoS attacks**

```java
@Configuration
public class RateLimitConfig {
    @Bean
    public RateLimiter rateLimiter() {
        return RateLimiter.create(10.0); // 10 requests per second
    }
}
```

### 🔹 API Keys & Tokens

**Secure API access**

```java
// JWT Token validation
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        
        String token = extractToken(request);
        if (token != null && jwtUtil.validateToken(token)) {
            // Set authentication context
        }
        filterChain.doFilter(request, response);
    }
}
```
### 🔹 CORS Configuration

**Control cross-origin requests**

```java
@Configuration
public class CorsConfig {
    
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("https://trusted-domain.com")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
```

### 🔹 Security Headers

**Add security headers**

```java
@Configuration
public class SecurityHeadersConfig {
    
    @Bean
    public FilterRegistrationBean<HeaderFilter> headerFilter() {
        FilterRegistrationBean<HeaderFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new HeaderFilter());
        registrationBean.addUrlPatterns("/api/*");
        return registrationBean;
    } 
}

public class HeaderFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.se                                                          tHeader("X-Content-Type-Options", "nosniff");
        httpResponse.setHeader("X-Frame-Options", "DENY");
        httpResponse.setHeader("X-XSS-Protection", "1; mode=block");
        chain.doFilter(request, response);
    }
}
```

### 🔹 Logging & Monitoring

**Track API usage and security events**

```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDenied(AccessDeniedException e) {
        // Log security violation
        logger.warn("Access denied: " + e.getMessage());
        return ResponseEntity.status(403).body("Access Denied");
    }
}
```

### 🔹 API Versioning & Deprecation

**Manage API lifecycle securely**

```java
@RestController
@RequestMapping("/api/v1")
public class UserControllerV1 {
    // Version 1 implementation
}

@RestController  
@RequestMapping("/api/v2")
public class UserControllerV2 {
    // Version 2 with enhanced security
}
```

---

## ✅ What is JWT token?

**JWT (JSON Web Token)** is an open standard (RFC 7519) for securely transmitting information between parties as a JSON object. It's commonly used for authentication and authorization in web applications.

### 🔹 JWT Structure

**Three parts separated by dots: Header.Payload.Signature**

```javascript
// Example JWT Token
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c

// Decoded structure:
{
  "header": {
    "alg": "HS256",  // Algorithm
    "typ": "JWT"     // Type
  },
  "payload": {
    "sub": "1234567890",    // Subject (user ID)
    "name": "John Doe",     // User name
    "iat": 1516239022,      // Issued at time
    "exp": 1516242622       // Expiration time
  },
  "signature": "SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
}
```

### 🔹 How JWT Works

**Stateless authentication flow**

```javascript
// 1. User logs in with credentials
POST /api/auth/login
{
  "username": "john",
  "password": "secret"
}

// 2. Server validates credentials and creates JWT
const token = jwt.sign({
  sub: user.id,
  name: user.name,
  role: user.role,
  iat: Date.now(),
  exp: Date.now() + (24 * 60 * 60 * 1000) // 24 hours
}, secretKey);

// 3. Client stores token (localStorage, cookies)
localStorage.setItem('token', token);

// 4. Client sends token in Authorization header
GET /api/protected
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...

// 5. Server verifies token on each request
const decoded = jwt.verify(token, secretKey);
```

### 🔹 JWT Advantages

- **Stateless**: No server-side session storage needed
- **Self-contained**: All user info in the token
- **Cross-domain**: Works across different domains
- **Performance**: Fast validation without database calls

### 🔹 JWT Security Considerations

```javascript
// Use strong secret keys
const secretKey = process.env.JWT_SECRET; // 256-bit key recommended

// Set reasonable expiration times
const token = jwt.sign(payload, secretKey, { expiresIn: '1h' });

// Store tokens securely
// Use httpOnly cookies for web apps
// Use secure storage for mobile apps

// Implement token refresh mechanism
// Short-lived access tokens + long-lived refresh tokens
```

### 🔹 Common JWT Libraries

```java
// Java (JJWT)
String jwt = Jwts.builder()
    .setSubject("user123")
    .setExpiration(new Date(System.currentTimeMillis() + 86400000))
    .signWith(SignatureAlgorithm.HS256, secretKey)
    .compact();

// JavaScript (jsonwebtoken)
const jwt = require('jsonwebtoken');
const token = jwt.sign({ userId: 123 }, secretKey, { expiresIn: '1h' });
```

### 🔹 JWT vs Sessions

| Aspect          | JWT              | Sessions          |
|-----------------|------------------|-------------------|
| **Storage**     | Client-side      | Server-side       |
| **Scalability** | High (stateless) | Lower (stateful)  |
| **Security**    | Token theft risk | Session hijacking |
| **Performance** | Fast validation  | Database lookup   |
| **Logout**      | Complex          | Simple            |

---

## ✅ OAuth 2.0 Flow

**OAuth 2.0** is an authorization framework that enables applications to obtain limited access to user accounts on an HTTP service. It decouples authentication from authorization.

### 🔹 OAuth 2.0 Roles

- **Resource Owner**: The user who owns the data
- **Client**: The application requesting access
- **Authorization Server**: Issues access tokens
- **Resource Server**: Hosts the protected resources

### 🔹 Authorization Code Flow (Most Common)

**Secure flow for web applications**

```sequence
Client -> Authorization Server: GET /authorize?response_type=code&client_id=123&redirect_uri=https://client.com/callback&scope=read
Authorization Server -> Resource Owner: Redirect to login page
Resource Owner -> Authorization Server: User authenticates and consents
Authorization Server -> Client: Redirect to https://client.com/callback?code=abc123
Client -> Authorization Server: POST /token with code=abc123
Authorization Server -> Client: Returns access_token and refresh_token
Client -> Resource Server: GET /api/data with Authorization: Bearer <access_token>
Resource Server -> Client: Returns protected data
```

### 🔹 Detailed Code Flow

```javascript
// 1. Redirect user to authorization endpoint
const authUrl = `https://auth-server.com/authorize?
  response_type=code&
  client_id=${CLIENT_ID}&
  redirect_uri=${REDIRECT_URI}&
  scope=read+write&
  state=${randomState}`;

window.location.href = authUrl;

// 2. Handle authorization code callback
// URL: https://client.com/callback?code=abc123&state=xyz

// 3. Exchange code for tokens
const response = await fetch('https://auth-server.com/token', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/x-www-form-urlencoded',
    'Authorization': 'Basic ' + btoa(CLIENT_ID + ':' + CLIENT_SECRET)
  },
  body: new URLSearchParams({
    grant_type: 'authorization_code',
    code: 'abc123',
    redirect_uri: REDIRECT_URI
  })
});

const tokens = await response.json();
// { access_token: '...', refresh_token: '...', token_type: 'Bearer', expires_in: 3600 }
```

### 🔹 Other OAuth 2.0 Flows

### 🔹 Implicit Flow (SPA Applications)

**Simplified flow for client-side apps**

```javascript
// Direct token in redirect (less secure)
GET /authorize?response_type=token&client_id=123&redirect_uri=https://spa.com/callback

// Callback URL: https://spa.com/callback#access_token=abc123&token_type=Bearer
```

### 🔹 Client Credentials Flow (Machine-to-Machine)

**For service accounts**

```javascript
// No user interaction
POST /token
Authorization: Basic base64(client_id:client_secret)
Content-Type: application/x-www-form-urlencoded

grant_type=client_credentials&scope=read
```

### 🔹 Refresh Token Flow

**Renew expired access tokens**

```javascript
POST /token
Authorization: Basic base64(client_id:client_secret)
Content-Type: application/x-www-form-urlencoded

grant_type=refresh_token&refresh_token=refresh_abc123
```

### 🔹 OAuth 2.0 Security Best Practices

```javascript
// Use PKCE (Proof Key for Code Exchange) for public clients
const codeVerifier = generateRandomString(64);
const codeChallenge = base64URLEncode(sha256(codeVerifier));

// Include state parameter to prevent CSRF
const state = generateRandomString(32);

// Validate redirect URIs strictly
// Use short-lived access tokens (1 hour)
// Store refresh tokens securely
// Implement token rotation
```

### 🔹 OAuth 2.0 vs OAuth 1.0

| Feature             | OAuth 1.0 | OAuth 2.0 |
|---------------------|-----------|-----------|
| **Complexity**      | High      | Medium    |
| **Signatures**      | Required  | Optional  |
| **Flows**           | Limited   | Multiple  |
| **Mobile Support**  | Poor      | Excellent |
| **Security**        | High      | Good (with best practices) |

### 🔹 Popular OAuth 2.0 Providers

- **Google OAuth 2.0**
- **Facebook Login**
- **GitHub OAuth**
- **Microsoft Azure AD**
- **Auth0**

---

## ✅ What is CORS? How to Enable It?

**CORS (Cross-Origin Resource Sharing)** is a security feature implemented by web browsers that controls how web pages from one domain can request resources from another domain.

### 🔹 Same-Origin Policy

**Browser security mechanism**

```javascript
// Same origin: protocol + domain + port must match
// Examples of same origin:
https://example.com:443/app.js
https://example.com:443/api/data

// Different origins:
http://example.com (different protocol)
https://api.example.com (different domain)
https://example.com:8080 (different port)
```

### 🔹 CORS Headers

**Server response headers that control cross-origin access**

```http
// Simple request response
HTTP/1.1 200 OK
Access-Control-Allow-Origin: https://trusted-site.com
Access-Control-Allow-Credentials: true
Access-Control-Expose-Headers: X-Custom-Header

// Preflight request response
HTTP/1.1 200 OK
Access-Control-Allow-Origin: https://trusted-site.com
Access-Control-Allow-Methods: GET, POST, PUT, DELETE
Access-Control-Allow-Headers: Content-Type, Authorization
Access-Control-Max-Age: 86400
```

### 🔹 CORS Request Types

### 🔹 Simple Requests

**No preflight required**

```javascript
// Allowed methods: GET, POST, HEAD
// Allowed headers: Accept, Accept-Language, Content-Language
// Content-Type: application/x-www-form-urlencoded, multipart/form-data, text/plain

fetch('https://api.example.com/data')
  .then(response => response.json())
  .then(data => console.log(data));
```

### 🔹 Preflight Requests

**Browser sends OPTIONS request first**

```javascript
// For complex requests (PUT, DELETE, custom headers)
OPTIONS /api/data HTTP/1.1
Origin: https://client.com
Access-Control-Request-Method: PUT
Access-Control-Request-Headers: X-Custom-Header

// Server responds with allowed methods/headers
```

### 🔹 How to Enable CORS in Spring Boot

```java
@Configuration
public class CorsConfig {
    
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("https://localhost:3000", "https://myapp.com")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true)
                        .maxAge(3600);
            }
        };
    }
}
```
### 🔹 Global CORS Configuration

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().configurationSource(corsConfigurationSource());
    }
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("https://trusted.com"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);
        return source;
    }
}
```

### 🔹 Controller-Level CORS

```java
@RestController
@CrossOrigin(origins = "https://localhost:3000")
public class UserController {
    
    @GetMapping("/users")
    public List<User> getUsers() {
        // CORS enabled for this controller
    }
    
    @PostMapping("/users")
    @CrossOrigin(origins = "https://admin-panel.com") // Method-level override
    public User createUser(@RequestBody User user) {
        // Different CORS for this method
    }
}
```

### 🔹 CORS in Express.js (Node.js)

```javascript
const express = require('express');
const cors = require('cors');
const app = express();

// Enable CORS for all routes
app.use(cors({
  origin: ['https://localhost:3000', 'https://myapp.com'],
  methods: ['GET', 'POST', 'PUT', 'DELETE'],
  allowedHeaders: ['Content-Type', 'Authorization'],
  credentials: true
}));

// Or configure per route
app.get('/api/data', cors({
  origin: 'https://trusted.com'
}), (req, res) => {
  res.json({ data: 'Hello CORS!' });
});
```

### 🔹 CORS Security Considerations

```javascript
// Never use wildcard (*) in production for credentials
// Specify exact origins
// Validate origins server-side
// Use HTTPS for secure communication
// Implement proper authentication/authorization
// Monitor CORS requests for abuse
```

### 🔹 Common CORS Issues

```javascript
// Error: CORS policy: No 'Access-Control-Allow-Origin' header
// Solution: Add CORS headers to server response

// Error: CORS policy: Request header field not allowed
// Solution: Add header to Access-Control-Allow-Headers

// Error: CORS policy: Method not allowed
// Solution: Add method to Access-Control-Allow-Methods

// Error: CORS policy: Credentials flag is true but Access-Control-Allow-Origin is *
// Solution: Specify exact origin when using credentials
```

### 🔹 CORS vs JSONP

| Feature             | CORS             | JSONP             |
|---------------------|------------------|-------------------|
| **Security**        | Secure           | Vulnerable to XSS |
| **Methods**         | All HTTP methods | GET only          |
| **Headers**         | Full control     | Limited           |
| **Error Handling**  | Standard         | Callback-based    |
| **Browser Support** | Modern browsers  | All browsers      |

---

## 🎯 Interview One-Liners

> **REST Security**: Secure REST APIs using HTTPS, JWT/OAuth authentication, input validation, rate limiting, CORS configuration, and security headers to prevent common web vulnerabilities.

> **JWT Token**: JWT is a compact, self-contained token format for securely transmitting information as JSON objects, commonly used for stateless authentication with header.payload.signature structure.

> **OAuth 2.0 Flow**: OAuth 2.0 is an authorization framework where clients obtain access tokens through flows like Authorization Code (secure web apps), Implicit (SPAs), or Client Credentials (M2M), enabling delegated access without sharing credentials.

> **CORS**: CORS is a browser security mechanism that uses HTTP headers to control cross-origin requests, requiring servers to explicitly allow origins, methods, and headers to prevent unauthorized cross-domain access.

