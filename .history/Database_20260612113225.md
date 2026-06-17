## **6. RELATIONAL DATABASE (SQL/Oracle)**

### **SQL Basics**
156. What is RDBMS? ACID properties?
157. What is Primary Key vs Foreign Key?
158. Types of joins (INNER, LEFT, RIGHT, FULL OUTER)
159. What is Self Join? Example?
160. What is Cross Join?
161. Difference between WHERE and HAVING
162. GROUP BY clause usage
163. Aggregate functions (COUNT, SUM, AVG, MAX, MIN)
164. What is subquery? Correlated vs Non-correlated
165. UNION vs UNION ALL

---

# 156. What is RDBMS? ACID properties?

## ✅ What is RDBMS?

**Relational Database Management System (RDBMS)** is a software system that:
- Stores data in **tables** (rows and columns)
- Uses **SQL** for querying
- Maintains **relationships** between tables
- Examples: MySQL, PostgreSQL, Oracle, SQL Server

---

## ✅ ACID Properties (Very Important)

ACID ensures **data integrity and reliability** in transactions.

### 🔹 A - Atomicity

**"All or Nothing"**

👉 A transaction either **completely succeeds or completely fails**

Example:
```sql
BEGIN TRANSACTION;
  UPDATE Account1 SET balance = balance - 100;
  UPDATE Account2 SET balance = balance + 100;
COMMIT; -- Both succeed or both fail
```

✔ Cannot have partial updates

---

### 🔹 C - Consistency

**"Valid to Valid State"**

👉 Database moves from one **valid state to another valid state**

Example:
```sql
-- Before: Account1 = 1000, Account2 = 500
-- After: Account1 = 900, Account2 = 600
-- Total remains = 1500 (consistent)
```

✔ All rules and constraints maintained

---

### 🔹 I - Isolation

**"Independent Transactions"**

👉 Concurrent transactions **don't interfere** with each other

Example:
```sql
Transaction 1: SELECT balance FROM Account WHERE id = 1;
Transaction 2: UPDATE Account SET balance = 500 WHERE id = 1;
-- Transaction 1 sees original or final value, not in-between
```

✔ No dirty reads, phantom reads

---

### 🔹 D - Durability

**"Permanent After Commit"**

👉 Once committed, data **survives system failures**

Example:
```sql
COMMIT; -- Even if system crashes, data persists
```

✔ Data stored on disk (not just memory)

---

## 🔄 Example (All Together)

```sql
BEGIN TRANSACTION;
  -- Atomicity: All updates together
  -- Consistency: Valid balance maintained
  -- Isolation: No interference from other transactions
  UPDATE Account1 SET balance = balance - 100;
  UPDATE Account2 SET balance = balance + 100;
COMMIT; -- Durability: Permanently saved
```

---

## 🎯 Interview One-Liner

> RDBMS stores data in structured tables using SQL, and ACID properties ensure transactions are Atomic (all-or-nothing), Consistent (valid states), Isolated (independent), and Durable (permanent).

---

# 157. What is Primary Key vs Foreign Key?

## ✅ Primary Key

**Uniquely identifies each row in a table**

### Characteristics:
- ✔ Unique (no duplicates)
- ✔ NOT NULL (must have value)
- ✔ One per table
- ✔ No NULL values

Example:
```sql
CREATE TABLE Users (
    user_id INT PRIMARY KEY,  -- Primary Key
    name VARCHAR(50),
    email VARCHAR(100)
);
```

---

## ✅ Foreign Key

**Links to Primary Key in another table**

### Characteristics:
- ✔ References Primary Key from another table
- ✔ Can be NULL
- ✔ Multiple per table
- ✔ Maintains **referential integrity**

Example:
```sql
CREATE TABLE Orders (
    order_id INT PRIMARY KEY,
    user_id INT,
    amount DECIMAL(10, 2),
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);
```

---

## 🔄 Visual Relationship

```
Users Table                    Orders Table
┌─────────────┐               ┌─────────────┐
│ user_id(PK) │──────────────→│ user_id(FK) │
│ name        │               │ order_id(PK)│
│ email       │               │ amount      │
└─────────────┘               └─────────────┘
```

---

## 🔥 Key Differences

| Feature        | Primary Key | Foreign Key      |
| -------------- | ----------- | ---------------- |
| Purpose        | Unique ID   | Reference to PK  |
| Uniqueness     | ✔ Yes       | ❌ No            |
| NULL values    | ❌ Not NULL  | ✔ Can be NULL   |
| Count per table| 1           | Many             |
| Referential    | N/A         | ✔ Yes            |

---

## 🎯 Interview One-Liner

> Primary Key uniquely identifies each row and cannot be NULL, while Foreign Key references a Primary Key from another table to maintain referential integrity.

---

# 158. Types of Joins (INNER, LEFT, RIGHT, FULL OUTER)

## ✅ Table Setup

```sql
-- Users Table
user_id | name
--------|--------
1       | John
2       | Sarah
3       | Mike

-- Orders Table
order_id | user_id | amount
---------|---------|--------
101      | 1       | 500
102      | 1       | 300
103      | 2       | 800
```

---

## 🔹 1. INNER JOIN

**Returns only matching rows from both tables**

```sql
SELECT U.user_id, U.name, O.order_id, O.amount
FROM Users U
INNER JOIN Orders O ON U.user_id = O.user_id;
```

Result:
```
user_id | name  | order_id | amount
--------|-------|----------|--------
1       | John  | 101      | 500
1       | John  | 102      | 300
2       | Sarah | 103      | 800
```

✔ Mike (no orders) is excluded

---

## 🔹 2. LEFT JOIN (LEFT OUTER JOIN)

**Returns all rows from LEFT table + matching from RIGHT**

```sql
SELECT U.user_id, U.name, O.order_id, O.amount
FROM Users U
LEFT JOIN Orders O ON U.user_id = O.user_id;
```

Result:
```
user_id | name  | order_id | amount
--------|-------|----------|--------
1       | John  | 101      | 500
1       | John  | 102      | 300
2       | Sarah | 103      | 800
3       | Mike  | NULL     | NULL
```

✔ Mike included with NULL values

---

## 🔹 3. RIGHT JOIN (RIGHT OUTER JOIN)

**Returns all rows from RIGHT table + matching from LEFT**

```sql
SELECT U.user_id, U.name, O.order_id, O.amount
FROM Users U
RIGHT JOIN Orders O ON U.user_id = O.user_id;
```

Result:
```
user_id | name  | order_id | amount
--------|-------|----------|--------
1       | John  | 101      | 500
1       | John  | 102      | 300
2       | Sarah | 103      | 800
```

---

## 🔹 4. FULL OUTER JOIN

**Returns all rows from both tables**

```sql
SELECT U.user_id, U.name, O.order_id, O.amount
FROM Users U
FULL OUTER JOIN Orders O ON U.user_id = O.user_id;
```

Result:
```
user_id | name  | order_id | amount
--------|-------|----------|--------
1       | John  | 101      | 500
1       | John  | 102      | 300
2       | Sarah | 103      | 800
3       | Mike  | NULL     | NULL
```

---

## 🔥 Visual Representation

```
INNER JOIN:   Matching only
LEFT JOIN:    All left + matching right
RIGHT JOIN:   All right + matching left
FULL OUTER:   All from both
```

---

## 🎯 Interview One-Liner

> INNER JOIN returns matching rows, LEFT JOIN returns all left rows with matching right, RIGHT JOIN is opposite, and FULL OUTER returns all rows from both tables.

---

# 159. What is Self Join? Example?

## ✅ Definition

**Self Join** - joining a table to itself

👉 Used to compare rows in the **same table**

---

## ✅ Example: Employee Manager Relationship

```sql
CREATE TABLE Employees (
    emp_id INT PRIMARY KEY,
    name VARCHAR(50),
    manager_id INT,
    FOREIGN KEY (manager_id) REFERENCES Employees(emp_id)
);
```

Data:
```
emp_id | name   | manager_id
--------|--------|------------
1       | John   | NULL
2       | Sarah  | 1
3       | Mike   | 1
4       | Lisa   | 2
```

---

## 🔹 Query: Find all employees and their managers

```sql
SELECT 
    E.emp_id AS employee_id,
    E.name AS employee_name,
    M.emp_id AS manager_id,
    M.name AS manager_name
FROM Employees E
LEFT JOIN Employees M ON E.manager_id = M.emp_id;
```

Result:
```
employee_id | employee_name | manager_id | manager_name
------------|---------------|------------|---------------
1           | John          | NULL       | NULL
2           | Sarah         | 1          | John
3           | Mike          | 1          | John
4           | Lisa          | 2          | Sarah
```

---

## 🔹 Another Example: Find employees earning more than their manager

```sql
SELECT 
    E.name AS employee,
    E.salary AS emp_salary,
    M.name AS manager,
    M.salary AS manager_salary
FROM Employees E
INNER JOIN Employees M ON E.manager_id = M.emp_id
WHERE E.salary > M.salary;
```

---

## 🎯 Interview One-Liner

> A Self Join is joining a table to itself using aliases to compare rows within the same table, commonly used for hierarchical relationships like employees and managers.

---

# 160. What is Cross Join?

## ✅ Definition

**Cross Join** produces **Cartesian product** of two tables

👉 Combines **every row from table1 with every row from table2**

---

## ✅ Example

Colors Table:
```
color_id | color
---------|-------
1        | Red
2        | Blue
```

Sizes Table:
```
size_id | size
--------|------
1       | Small
2       | Large
3       | XL
```

---

## 🔹 Cross Join Query

```sql
SELECT C.color, S.size
FROM Colors C
CROSS JOIN Sizes S;
```

Result (2 × 3 = 6 combinations):
```
color | size
------|-------
Red   | Small
Red   | Large
Red   | XL
Blue  | Small
Blue  | Large
Blue  | XL
```

---

## ✅ Syntax Variations

```sql
-- Method 1: CROSS JOIN
SELECT * FROM Colors CROSS JOIN Sizes;

-- Method 2: Comma separation (old style)
SELECT * FROM Colors, Sizes;
```

---

## ✅ Real-World Use Case

**Generate all possible combinations** (e.g., product variants)

```sql
SELECT 
    P.product_name,
    C.color,
    S.size,
    (CAST(P.base_price AS DECIMAL) + C.price_adjustment + S.price_adjustment) AS final_price
FROM Products P
CROSS JOIN Colors C
CROSS JOIN Sizes S;
```

---

## 🎯 Interview One-Liner

> A Cross Join produces a Cartesian product, combining every row from one table with every row from another table, resulting in m × n rows.

---

# 161. Difference between WHERE and HAVING

## ✅ WHERE Clause

**Filters rows BEFORE grouping**

- ✔ Works on individual rows
- ✔ Applied before GROUP BY
- ✔ Cannot use aggregate functions
- ✔ Faster (filters early)

Example:
```sql
SELECT department, COUNT(*) AS emp_count
FROM Employees
WHERE salary > 50000  -- Filter BEFORE grouping
GROUP BY department;
```

---

## ✅ HAVING Clause

**Filters groups AFTER grouping**

- ✔ Works on grouped data
- ✔ Applied after GROUP BY
- ✔ Can use aggregate functions
- ✔ Filters final results

Example:
```sql
SELECT department, COUNT(*) AS emp_count
FROM Employees
GROUP BY department
HAVING COUNT(*) > 5;  -- Filter AFTER grouping
```

---

## 🔥 Key Differences

| Feature           | WHERE      | HAVING       |
| ----------------- | ---------- | ------------ |
| When applied      | Before     | After GROUP  |
| Works on          | Rows       | Groups       |
| Aggregate support | ❌ No      | ✔ Yes        |
| Example           | salary>50k | COUNT(*)>5   |

---

## 🔹 Combined Example

```sql
SELECT department, AVG(salary) AS avg_sal, COUNT(*) AS emp_count
FROM Employees
WHERE hire_date > '2020-01-01'  -- Filter employees first
GROUP BY department
HAVING AVG(salary) > 60000;     -- Filter departments
```

Flow:
```
1. WHERE → Filter individual rows (hire_date > 2020)
2. GROUP BY → Group by department
3. HAVING → Filter groups (AVG salary > 60000)
```

---

## 🎯 Interview One-Liner

> WHERE filters rows before grouping and cannot use aggregates, while HAVING filters groups after GROUP BY and can use aggregate functions.

---

# 162. GROUP BY clause usage

## ✅ Definition

**GROUP BY** groups rows based on column(s) and applies aggregate functions

---

## ✅ Basic Example

```sql
Employees Table:
emp_id | name   | department | salary
--------|--------|------------|--------
1       | John   | Sales      | 50000
2       | Sarah  | Sales      | 60000
3       | Mike   | IT         | 70000
4       | Lisa   | IT         | 75000
```

Query:
```sql
SELECT department, COUNT(*) AS emp_count, AVG(salary) AS avg_salary
FROM Employees
GROUP BY department;
```

Result:
```
department | emp_count | avg_salary
-----------|-----------|------------
Sales      | 2         | 55000
IT         | 2         | 72500
```

---

## 🔹 Rules for GROUP BY

✔ All non-aggregated columns **must be in GROUP BY**

❌ Invalid:
```sql
SELECT department, name, AVG(salary)  -- name is not grouped!
FROM Employees
GROUP BY department;
```

✔ Valid:
```sql
SELECT department, MIN(salary) AS min_sal
FROM Employees
GROUP BY department;
```

---

## 🔹 GROUP BY Multiple Columns

```sql
SELECT department, job_title, COUNT(*) AS count
FROM Employees
GROUP BY department, job_title;
```

---

## 🔹 GROUP BY with ORDER BY

```sql
SELECT department, AVG(salary) AS avg_sal
FROM Employees
GROUP BY department
ORDER BY avg_sal DESC;
```

---

## 🔹 GROUP BY with WHERE

```sql
SELECT department, COUNT(*) AS count
FROM Employees
WHERE salary > 50000  -- Filter before grouping
GROUP BY department;
```

---

## 🎯 Real-World Examples

**Example 1: Sales by Region**
```sql
SELECT region, SUM(sales_amount) AS total_sales
FROM Sales
GROUP BY region;
```

**Example 2: Orders by User**
```sql
SELECT user_id, COUNT(*) AS order_count, AVG(amount) AS avg_order
FROM Orders
GROUP BY user_id;
```

---

## 🎯 Interview One-Liner

> GROUP BY groups rows by specified columns and applies aggregate functions to each group. All non-aggregated columns must be in the GROUP BY clause.

---

# 163. Aggregate functions (COUNT, SUM, AVG, MAX, MIN)

## ✅ Overview

Aggregate functions perform calculations on groups of rows.

---

## 🔹 1. COUNT()

**Counts number of rows**

```sql
SELECT COUNT(*) AS total_employees FROM Employees;
-- Result: 100

SELECT COUNT(DISTINCT department) AS dept_count FROM Employees;
-- Result: 5 (counts unique departments)

SELECT dept, COUNT(*) FROM Employees GROUP BY dept;
-- Result: department-wise counts
```

---

## 🔹 2. SUM()

**Sums numeric values**

```sql
SELECT SUM(salary) AS total_payroll FROM Employees;
-- Result: 5000000

SELECT department, SUM(salary) FROM Employees GROUP BY department;
```

⚠️ Ignores NULL values

---

## 🔹 3. AVG()

**Calculates average**

```sql
SELECT AVG(salary) AS avg_salary FROM Employees;
-- Result: 50000

SELECT department, AVG(salary) FROM Employees GROUP BY department;
```

⚠️ Ignores NULL values

---

## 🔹 4. MAX()

**Returns maximum value**

```sql
SELECT MAX(salary) AS highest_salary FROM Employees;
-- Result: 150000

SELECT department, MAX(salary) FROM Employees GROUP BY department;
```

---

## 🔹 5. MIN()

**Returns minimum value**

```sql
SELECT MIN(salary) AS lowest_salary FROM Employees;
-- Result: 25000

SELECT department, MIN(salary) FROM Employees GROUP BY department;
```

---

## 🔹 Combined Example

```sql
SELECT 
    department,
    COUNT(*) AS emp_count,
    SUM(salary) AS total_salary,
    AVG(salary) AS avg_salary,
    MAX(salary) AS max_salary,
    MIN(salary) AS min_salary
FROM Employees
GROUP BY department;
```

Result:
```
department | emp_count | total_salary | avg_salary | max_salary | min_salary
-----------|-----------|--------------|------------|------------|------------
Sales      | 10        | 500000       | 50000      | 80000      | 30000
IT         | 15        | 900000       | 60000      | 120000     | 40000
```

---

## 🎯 Interview One-Liner

> Aggregate functions (COUNT, SUM, AVG, MAX, MIN) perform calculations on groups of rows and are often used with GROUP BY.

---

# 164. What is subquery? Correlated vs Non-correlated

## ✅ What is Subquery?

**A query within another query** (also called inner query)

---

## 🔹 1. Non-Correlated Subquery

**Independent subquery** - executes once

```sql
SELECT name FROM Employees
WHERE salary > (SELECT AVG(salary) FROM Employees);
```

Flow:
```
1. Execute inner: AVG(salary) = 50000
2. Execute outer: SELECT employees with salary > 50000
```

✔ Executes once, then used in outer query

---

## ✅ Example: Find employees earning more than average

```sql
SELECT name, salary FROM Employees
WHERE salary > (SELECT AVG(salary) FROM Employees);
```

---

## 🔹 2. Correlated Subquery

**Dependent subquery** - executes for each row

```sql
SELECT E.name FROM Employees E
WHERE salary > (SELECT AVG(salary) FROM Employees E2 WHERE E2.department = E.department);
```

Flow:
```
For each employee E:
  - Get average salary of E's department
  - Compare E's salary to that average
```

⚠️ Executes for **every row** (slower)

---

## 🔹 Correlated Example: Find employees with above-average salary in their dept

```sql
SELECT name, department, salary
FROM Employees E1
WHERE salary > (
    SELECT AVG(salary)
    FROM Employees E2
    WHERE E2.department = E1.department
);
```

---

## 🔥 Key Differences

| Feature       | Non-Correlated   | Correlated         |
| ------------- | ---------------- | ------------------ |
| Dependency    | Independent      | Dependent on outer |
| Executions    | Once             | Per row            |
| Performance   | ⚡ Fast          | 🐢 Slower          |
| Complexity    | Simple           | Complex            |

---

## 🔹 More Examples

**Non-Correlated: Find orders above average value**
```sql
SELECT * FROM Orders
WHERE amount > (SELECT AVG(amount) FROM Orders);
```

**Correlated: Find users with more orders than average**
```sql
SELECT user_id FROM Users U
WHERE (SELECT COUNT(*) FROM Orders WHERE user_id = U.user_id) > (SELECT AVG(order_count) FROM (...));
```

---

## 🎯 Interview One-Liner

> A subquery is a query within another query. Non-correlated executes once independently, while correlated executes for each row and references the outer query.

---

# 165. UNION vs UNION ALL

## ✅ UNION

**Combines results and REMOVES duplicates**

```sql
SELECT name FROM Users
UNION
SELECT name FROM Employees;
```

✔ Duplicates are removed
🐢 Slower (needs to sort/deduplicate)

---

## ✅ UNION ALL

**Combines results and KEEPS all rows**

```sql
SELECT name FROM Users
UNION ALL
SELECT name FROM Employees;
```

✔ Keeps all rows/duplicates
⚡ Faster (no deduplication)

---

## 🔹 Example

Users Table:
```
name
------
John
Sarah
```

Employees Table:
```
name
------
Sarah
Mike
```

UNION Query:
```sql
SELECT name FROM Users
UNION
SELECT name FROM Employees;
```

Result (3 rows - Sarah appears once):
```
name
------
John
Sarah
Mike
```

UNION ALL Query:
```sql
SELECT name FROM Users
UNION ALL
SELECT name FROM Employees;
```

Result (4 rows - Sarah appears twice):
```
name
------
John
Sarah
Sarah
Mike
```

---

## 🔥 Key Differences

| Feature     | UNION        | UNION ALL     |
| ----------- | ------------ | ------------- |
| Duplicates  | ❌ Removed   | ✔ Kept        |
| Performance | 🐢 Slower    | ⚡ Faster     |
| Use case    | Distinct     | All records   |
| Sorting     | ✔ Yes        | ❌ No         |

---

## 🔹 Requirements for UNION

✔ Both SELECT statements must have:
- Same number of columns
- Same data types (in same order)
- Same column names (optional, uses first SELECT)

❌ Invalid:
```sql
SELECT name FROM Users          -- 1 column
UNION
SELECT name, age FROM Employees -- 2 columns
```

---

## 🎯 Interview One-Liner

> UNION combines query results and removes duplicates (slower), while UNION ALL keeps all rows including duplicates (faster).

---

# 🔥 SQL Summary Table

| Concept      | When to use                                    |
| ------------ | ---------------------------------------------- |
| INNER JOIN   | Only matching rows                             |
| LEFT JOIN    | All left + matching right                      |
| SELF JOIN    | Hierarchical data (employees-managers)         |
| CROSS JOIN   | All combinations                               |
| SUBQUERY     | Nested SELECT queries                          |
| GROUP BY     | Aggregate calculations per group               |
| UNION        | Combine + remove duplicates                    |
| WHERE        | Filter individual rows before grouping         |
| HAVING       | Filter groups after GROUP BY                   |

---

# 🎯 Interview Tips

✅ **Practice writing queries for:**
- Employee-Manager relationships (SELF JOIN)
- Department statistics (GROUP BY + aggregates)
- Filtering before/after grouping (WHERE vs HAVING)
- Combining multiple result sets (UNION)

❌ **Common mistakes:**
- Using UNION instead of UNION ALL (performance)
- Forgetting columns in GROUP BY
- Using aggregate in WHERE instead of HAVING
- Wrong JOIN type leading to incorrect results



### **Advanced SQL**
166. What are Window functions? (ROW_NUMBER, RANK, DENSE_RANK)
167. What is CTE (Common Table Expression)?
168. Recursive CTE example
169. What are Views? Materialized View?
170. What is Index? Types of indexes?
171. Clustered vs Non-clustered index
172. When to use indexes? When not to?
173. What is Query Optimization?
174. EXPLAIN PLAN usage
175. What are Stored Procedures?
176. What are Triggers? Types of triggers?
177. What are Constraints? (NOT NULL, UNIQUE, CHECK, DEFAULT)

---

# 166. What are Window functions? (ROW_NUMBER, RANK, DENSE_RANK)

## ✅ What are Window Functions?

**Window functions** perform calculations across a set of rows related to the current row.

👉 Unlike aggregate functions, they **don't collapse rows** - they return a value for each row

---

## ✅ Key Features

- ✔ Work on a **window** (subset) of rows
- ✔ Use **OVER()** clause
- ✔ Can use **PARTITION BY** and **ORDER BY**
- ✔ Don't reduce number of rows

---

## 🔹 1. ROW_NUMBER()

**Assigns unique sequential numbers**

```sql
SELECT name, salary, 
       ROW_NUMBER() OVER (ORDER BY salary DESC) AS row_num
FROM Employees;
```

Result:
```
name   | salary | row_num
-------|--------|--------
John   | 80000  | 1
Sarah  | 70000  | 2
Mike   | 60000  | 3
Lisa   | 50000  | 4
```

---

## 🔹 2. RANK()

**Assigns rank, skips duplicates**

```sql
SELECT name, salary, 
       RANK() OVER (ORDER BY salary DESC) AS rank
FROM Employees;
```

Data with ties:
```
name   | salary | rank
-------|--------|-----
John   | 80000  | 1
Sarah  | 70000  | 2
Mike   | 70000  | 2  -- Same rank
Lisa   | 50000  | 4  -- Skips 3
```

---

## 🔹 3. DENSE_RANK()

**Assigns rank, no gaps for duplicates**

```sql
SELECT name, salary, 
       DENSE_RANK() OVER (ORDER BY salary DESC) AS dense_rank
FROM Employees;
```

Result:
```
name   | salary | dense_rank
-------|--------|-----------
John   | 80000  | 1
Sarah  | 70000  | 2
Mike   | 70000  | 2  -- Same rank
Lisa   | 50000  | 3  -- No gap
```

---

## 🔹 PARTITION BY Example

**Rank within each department**

```sql
SELECT name, department, salary,
       RANK() OVER (PARTITION BY department ORDER BY salary DESC) AS dept_rank
FROM Employees;
```

Result:
```
name   | department | salary | dept_rank
-------|------------|--------|----------
John   | Sales      | 80000  | 1
Sarah  | Sales      | 70000  | 2
Mike   | IT         | 75000  | 1
Lisa   | IT         | 65000  | 2
```

---

## 🔥 Key Differences

| Function   | Behavior with Ties | Example (salary 70k, 70k, 50k) |
| ---------- | ------------------ | ------------------------------ |
| ROW_NUMBER | Always unique      | 1, 2, 3                       |
| RANK       | Skips ranks        | 1, 1, 3                       |
| DENSE_RANK | No gaps            | 1, 1, 2                       |

---

## 🎯 Interview One-Liner

> Window functions perform calculations across row windows using OVER() clause. ROW_NUMBER assigns unique numbers, RANK skips duplicates, DENSE_RANK doesn't skip.

---

# 167. What is CTE (Common Table Expression)?

## ✅ What is CTE?

**Common Table Expression (CTE)** is a temporary named result set that exists within a SQL statement.

👉 Like a **temporary view** that you can reference multiple times

---

## ✅ Syntax

```sql
WITH cte_name AS (
    SELECT column1, column2
    FROM table_name
    WHERE condition
)
SELECT * FROM cte_name;
```

---

## ✅ Example: Department Statistics

```sql
WITH DeptStats AS (
    SELECT department, 
           COUNT(*) AS emp_count,
           AVG(salary) AS avg_salary
    FROM Employees
    GROUP BY department
)
SELECT department, emp_count, avg_salary
FROM DeptStats
WHERE emp_count > 5;
```

---

## ✅ Multiple CTEs

```sql
WITH HighEarners AS (
    SELECT * FROM Employees WHERE salary > 50000
),
DeptCount AS (
    SELECT department, COUNT(*) AS count FROM HighEarners GROUP BY department
)
SELECT * FROM DeptCount;
```

---

## ✅ Recursive CTE

**CTE that references itself**

```sql
WITH RECURSIVE EmployeeHierarchy AS (
    -- Base case: Top-level managers
    SELECT emp_id, name, manager_id, 0 AS level
    FROM Employees
    WHERE manager_id IS NULL
    
    UNION ALL
    
    -- Recursive case: Employees under managers
    SELECT e.emp_id, e.name, e.manager_id, eh.level + 1
    FROM Employees e
    JOIN EmployeeHierarchy eh ON e.manager_id = eh.emp_id
)
SELECT * FROM EmployeeHierarchy;
```

---

## 🔥 CTE vs Subquery

| Feature | CTE                  | Subquery              |
| ------- | -------------------- | --------------------- |
| Readability | ✅ Better            | ❌ Worse              |
| Reusability | ✅ Can reference multiple times | ❌ One-time use      |
| Performance | Similar             | Similar               |
| Scope      | Current statement   | Current query        |

---

## 🎯 Interview One-Liner

> CTE is a temporary named result set defined with WITH clause, making complex queries more readable and allowing multiple references to the same subquery.

---

# 168. Recursive CTE example

## ✅ What is Recursive CTE?

**Recursive CTE** calls itself to process hierarchical or tree-like data structures.

---

## ✅ Employee Hierarchy Example

```sql
CREATE TABLE Employees (
    emp_id INT PRIMARY KEY,
    name VARCHAR(50),
    manager_id INT,
    FOREIGN KEY (manager_id) REFERENCES Employees(emp_id)
);

-- Sample data
INSERT INTO Employees VALUES
(1, 'CEO', NULL),
(2, 'VP Sales', 1),
(3, 'VP Engineering', 1),
(4, 'Sales Manager', 2),
(5, 'Engineer', 3);
```

---

## 🔹 Recursive CTE Query

```sql
WITH RECURSIVE EmployeeHierarchy AS (
    -- Anchor member: Top-level employees (no manager)
    SELECT emp_id, name, manager_id, 0 AS level, CAST(name AS VARCHAR(1000)) AS path
    FROM Employees
    WHERE manager_id IS NULL
    
    UNION ALL
    
    -- Recursive member: Employees under managers
    SELECT e.emp_id, e.name, e.manager_id, eh.level + 1, 
           CAST(eh.path + ' -> ' + e.name AS VARCHAR(1000))
    FROM Employees e
    INNER JOIN EmployeeHierarchy eh ON e.manager_id = eh.emp_id
)
SELECT emp_id, name, level, path
FROM EmployeeHierarchy
ORDER BY level, emp_id;
```

Result:
```
emp_id | name            | level | path
-------|-----------------|-------|-------------------
1      | CEO             | 0     | CEO
2      | VP Sales        | 1     | CEO -> VP Sales
3      | VP Engineering  | 1     | CEO -> VP Engineering
4      | Sales Manager   | 2     | CEO -> VP Sales -> Sales Manager
5      | Engineer        | 2     | CEO -> VP Engineering -> Engineer
```

---

## 🔹 Another Example: Fibonacci Numbers

```sql
WITH RECURSIVE Fibonacci AS (
    -- Base cases
    SELECT 1 AS n, 0 AS fib
    UNION ALL
    SELECT 2 AS n, 1 AS fib
    
    UNION ALL
    
    -- Recursive case
    SELECT f.n + 1, f.fib + f2.fib
    FROM Fibonacci f
    CROSS JOIN Fibonacci f2
    WHERE f.n = f2.n + 1 AND f.n < 10
)
SELECT n, fib FROM Fibonacci ORDER BY n;
```

---

## ✅ Key Components

1. **Anchor Member**: Base case (non-recursive)
2. **UNION ALL**: Combines results
3. **Recursive Member**: References the CTE itself
4. **Termination**: Must have stopping condition

---

## 🎯 Interview One-Liner

> Recursive CTE processes hierarchical data by repeatedly executing itself, with anchor member providing base case and recursive member building upon previous results.

---

# 169. What are Views? Materialized View?

## ✅ What is a View?

**View** is a virtual table based on a SQL query.

👉 Doesn't store data physically - computes data when accessed

---

## ✅ Creating a View

```sql
CREATE VIEW EmployeeSummary AS
SELECT department, COUNT(*) AS emp_count, AVG(salary) AS avg_salary
FROM Employees
GROUP BY department;
```

---

## ✅ Using a View

```sql
SELECT * FROM EmployeeSummary WHERE emp_count > 5;
```

---

## ✅ Types of Views

### 🔹 1. Simple View

Based on single table:

```sql
CREATE VIEW ActiveEmployees AS
SELECT * FROM Employees WHERE status = 'Active';
```

### 🔹 2. Complex View

Based on multiple tables:

```sql
CREATE VIEW EmployeeDetails AS
SELECT e.name, e.salary, d.dept_name
FROM Employees e
JOIN Departments d ON e.dept_id = d.dept_id;
```

---

## ✅ What is Materialized View?

**Materialized View** stores the query results physically.

👉 Data is pre-computed and stored on disk

---

## ✅ Creating Materialized View

```sql
-- PostgreSQL/MySQL syntax
CREATE MATERIALIZED VIEW SalesSummary AS
SELECT region, SUM(amount) AS total_sales
FROM Sales
GROUP BY region;
```

---

## ✅ Refreshing Materialized View

```sql
-- Refresh the stored data
REFRESH MATERIALIZED VIEW SalesSummary;
```

---

## 🔥 View vs Materialized View

| Feature          | View              | Materialized View    |
| ---------------- | ----------------- | -------------------- |
| Storage          | ❌ No storage     | ✅ Stores data       |
| Performance      | 🐢 Slower         | ⚡ Faster            |
| Data Freshness   | ✅ Always fresh   | ❌ May be stale      |
| Update Frequency | Automatic         | Manual refresh       |
| Use case         | Security, simplicity | Performance, complex calculations |

---

## 🎯 Interview One-Liner

> Views are virtual tables computed on-the-fly, while Materialized Views store pre-computed results for better performance but require manual refresh.

---

# 170. What is Index? Types of indexes?

## ✅ What is an Index?

**Index** is a database object that improves query performance by providing fast access to rows.

👉 Like a **book index** - helps find data quickly without scanning entire table

---

## ✅ How Index Works

**Without Index:**
```
Table scan: Check every row → Slow
```

**With Index:**
```
Index lookup → Direct row access → Fast
```

---

## ✅ Creating an Index

```sql
CREATE INDEX idx_employee_salary ON Employees(salary);
```

---

## ✅ Types of Indexes

### 🔹 1. Single-Column Index

Index on one column:

```sql
CREATE INDEX idx_name ON Employees(name);
```

### 🔹 2. Composite Index

Index on multiple columns:

```sql
CREATE INDEX idx_dept_salary ON Employees(department, salary);
```

### 🔹 3. Unique Index

Ensures unique values:

```sql
CREATE UNIQUE INDEX idx_unique_email ON Employees(email);
```

### 🔹 4. Clustered Index

Physically sorts table data (covered in next question)

### 🔹 5. Non-Clustered Index

Separate index structure

### 🔹 6. Full-Text Index

For text search:

```sql
CREATE FULLTEXT INDEX idx_content ON Articles(content);
```

### 🔹 7. Spatial Index

For geographic data

---

## ✅ Index on Primary Key

Primary keys automatically get unique clustered indexes:

```sql
CREATE TABLE Users (
    user_id INT PRIMARY KEY,  -- Auto-creates clustered index
    name VARCHAR(50)
);
```

---

## 🎯 Interview One-Liner

> Indexes speed up queries by providing fast data access. Types include single-column, composite, unique, clustered, non-clustered, full-text, and spatial indexes.

---

# 171. Clustered vs Non-clustered index

## ✅ Clustered Index

**Clustered index** determines the physical order of data in the table.

👉 Only **one per table** - defines how data is stored

---

## ✅ Visual Understanding

**Clustered Index:**
```
Table data is physically sorted by index key
┌─────────┬─────────┬─────────┐
│ ID (PK) │ Name    │ Salary  │
├─────────┼─────────┼─────────┤
│ 1       │ Alice   │ 50000   │
│ 2       │ Bob     │ 60000   │
│ 3       │ Charlie │ 70000   │
└─────────┴─────────┴─────────┘
Data stored in ID order
```

---

## ✅ Non-Clustered Index

**Non-Clustered index** is a separate structure pointing to data.

👉 Multiple allowed per table - like a phone book

---

## ✅ Visual Understanding

**Non-Clustered Index:**
```
Index Structure:          Table Data:
┌─────────┬─────────┐     ┌─────────┬─────────┬─────────┐
│ Salary  │ Row Ptr │     │ ID      │ Name    │ Salary  │
├─────────┼─────────┤     ├─────────┼─────────┼─────────┤
│ 50000   │ → Row1  │     │ 1       │ Alice   │ 50000   │
│ 60000   │ → Row2  │     │ 3       │ Charlie │ 70000   │
│ 70000   │ → Row3  │     │ 2       │ Bob     │ 60000   │
└─────────┴─────────┘     └─────────┴─────────┴─────────┘
Sorted by salary          Data in insertion order
```

---

## 🔥 Key Differences

| Feature          | Clustered Index     | Non-Clustered Index |
| ---------------- | ------------------- | -------------------|
| Physical Order   | ✅ Yes             | ❌ No              |
| Count per Table  | 1                  | Multiple           |
| Storage          | Table itself       |Separate structure  |
| Speed            | ⚡ Very Fast        | ⚡ Fast             |
| Size             | Larger             |Smaller             |
| Primary Key      | Usually clustered  |Can be non-clustered|

---

## ✅ Examples

**Clustered Index (automatic on PK):**
```sql
CREATE TABLE Users (
    user_id INT PRIMARY KEY,  -- Clustered index
    name VARCHAR(50)
);
```

**Non-Clustered Index:**
```sql
CREATE INDEX idx_salary ON Employees(salary);  -- Non-clustered
```

---

## 🎯 Interview One-Liner

> Clustered index determines physical data order (one per table), while non-clustered indexes are separate structures (multiple allowed) that point to data rows.

---

# 172. When to use indexes? When not to?

## ✅ When to Use Indexes

### 🔹 1. Frequent WHERE Clauses

```sql
SELECT * FROM Users WHERE email = 'john@example.com';
-- Index on email column
```

### 🔹 2. JOIN Conditions

```sql
SELECT * FROM Orders o
JOIN Users u ON o.user_id = u.user_id;
-- Index on user_id in both tables
```

### 🔹 3. ORDER BY Columns

```sql
SELECT * FROM Products ORDER BY price DESC;
-- Index on price
```

### 🔹 4. Large Tables

Tables with > 1000 rows benefit from indexes

### 🔹 5. Low Selectivity Columns

Columns with few distinct values (but not too few)

---

## ✅ When NOT to Use Indexes

### ❌ 1. Small Tables

< 1000 rows - full table scan is faster

### ❌ 2. Frequently Updated Columns

Each update requires index maintenance

### ❌ 3. Low Cardinality Columns

Columns with very few distinct values (e.g., gender: M/F)

### ❌ 4. Columns in WHERE with Functions

```sql
SELECT * FROM Users WHERE YEAR(created_date) = 2023;
-- Function prevents index usage
```

### ❌ 5. Rarely Queried Columns

If column is never used in WHERE/JOIN

---

## ✅ Index Best Practices

### ✔ Composite Indexes

```sql
-- Good: Index matches query pattern
CREATE INDEX idx_user_status_date ON Users(status, created_date);

SELECT * FROM Users WHERE status = 'active' AND created_date > '2023-01-01';
```

### ✔ Covering Indexes

```sql
-- Index includes all queried columns
CREATE INDEX idx_covering ON Orders(user_id, order_date, total_amount);

SELECT user_id, order_date, total_amount FROM Orders WHERE user_id = 123;
```

### ❌ Over-Indexing

Too many indexes slow down INSERT/UPDATE/DELETE

---

## 🎯 Interview One-Liner

> Use indexes for frequently queried columns, JOINs, and ORDER BY. Avoid on small tables, low-cardinality columns, and rarely updated tables.

---

# 173. What is Query Optimization?

## ✅ What is Query Optimization?

**Query Optimization** is the process of choosing the most efficient execution plan for a SQL query.

👉 Database engine analyzes different strategies and picks the best one

---

## ✅ Query Execution Steps

1. **Parse** → Check syntax
2. **Bind** → Check semantics
3. **Optimize** → Choose execution plan
4. **Execute** → Run the plan
5. **Fetch** → Return results

---

## ✅ Optimization Techniques

### 🔹 1. Index Selection

Choose best indexes for WHERE/JOIN conditions

### 🔹 2. Join Order

Decide order of table joins (smaller tables first)

### 🔹 3. Join Methods

- **Nested Loop**: Good for small datasets
- **Hash Join**: Good for large datasets
- **Merge Join**: Good for sorted data

### 🔹 4. Query Rewriting

Transform query to equivalent but faster form

---

## ✅ Example Optimization

**Original Query:**
```sql
SELECT * FROM Orders o
JOIN Customers c ON o.customer_id = c.id
WHERE o.amount > 1000 AND c.region = 'US';
```

**Optimized Plan:**
1. Use index on `c.region` to find US customers
2. Use index on `o.customer_id` for join
3. Apply `amount > 1000` filter

---

## ✅ Factors Affecting Optimization

- **Table Statistics**: Row counts, value distributions
- **Available Indexes**: Which indexes exist
- **System Resources**: Memory, CPU
- **Cost Model**: Estimated execution cost

---

## 🎯 Interview One-Liner

> Query optimization involves selecting the most efficient execution plan by analyzing indexes, join orders, and access methods to minimize query execution time.

---

# 174. EXPLAIN PLAN usage

## ✅ What is EXPLAIN PLAN?

**EXPLAIN PLAN** shows the execution plan chosen by the query optimizer.

👉 Helps understand **how** the database will execute your query

---

## ✅ Basic Usage

```sql
EXPLAIN PLAN FOR
SELECT * FROM Employees WHERE department = 'IT' AND salary > 50000;

SELECT * FROM TABLE(DBMS_XPLAN.DISPLAY());
```

---

## ✅ Sample Output

```
Plan hash value: 123456789

-----------------------------------------------------------------------------------
| Id  | Operation| Name      | Rows  | Bytes | Cost (%CPU)|  
---------------------------------------------------------------------------------
|   0 | SELECT STATEMENT|   |     5 |   200 |     3   (0)|
|*  1 |TABLE ACCESS BY INDEX ROWID| EMPLOYEES|5 |200| 3 (0)|
|*  2 |INDEX RANGE SCAN| IDX_DEPT_SAL |5 |       |2   (0)|
-----------------------------------------------------------------------------------

Predicate Information (identified by operation id):
---------------------------------------------------
   1 - filter("SALARY">50000)
   2 - access("DEPARTMENT"='IT')
```

---

## ✅ Key Columns Explained

| Column | Meaning |
| ------ | ------- |
| Operation | What the database is doing |
| Name | Table/index name |
| Rows | Estimated rows returned |
| Bytes | Estimated bytes |
| Cost | Relative cost (lower = better) |

---

## ✅ Common Operations

| Operation | Meaning |
| --------- | ------- |
| TABLE ACCESS FULL | Full table scan |
| INDEX RANGE SCAN | Index lookup |
| NESTED LOOPS | Join method |
| HASH JOIN | Join method |
| SORT ORDER BY | Sorting data |

---

## ✅ Reading the Plan

**Good Plan:**
```
INDEX RANGE SCAN → TABLE ACCESS BY INDEX ROWID
```

**Bad Plan:**
```
TABLE ACCESS FULL (no index used)
```

---

## ✅ Using EXPLAIN PLAN

### 🔹 Before Production

```sql
-- Check if query uses indexes
EXPLAIN PLAN FOR SELECT * FROM large_table WHERE column = 'value';
```

### 🔹 Performance Tuning

```sql
-- Compare different query approaches
EXPLAIN PLAN FOR SELECT * FROM table WHERE indexed_col = 1;
EXPLAIN PLAN FOR SELECT * FROM table WHERE non_indexed_col = 1;
```

---

## 🎯 Interview One-Liner

> EXPLAIN PLAN displays the execution plan showing operations, estimated costs, and access methods to help optimize query performance.

---

# 175. What are Stored Procedures?

## ✅ What is Stored Procedure?

**Stored Procedure** is a pre-compiled collection of SQL statements stored in the database.

👉 Like a **function** that you can call with parameters

---

## ✅ Creating a Stored Procedure

```sql
CREATE PROCEDURE GetEmployeeSalary (
    IN emp_id INT,
    OUT emp_salary DECIMAL(10,2)
)
BEGIN
    SELECT salary INTO emp_salary
    FROM Employees
    WHERE employee_id = emp_id;
END;
```

---

## ✅ Calling a Stored Procedure

```sql
-- Call the procedure
CALL GetEmployeeSalary(123, @salary);

-- Get the output
SELECT @salary;
```

---

## ✅ Benefits

### ✅ Performance
- Pre-compiled and cached
- Reduced network traffic

### ✅ Security
- Access control at procedure level
- Hide complex business logic

### ✅ Maintainability
- Centralized business logic
- Reusable code

### ✅ Consistency
- Same logic across applications

---

## ✅ Types of Parameters

| Type | Description | Example |
| ---- | ----------- | ------- |
| IN   | Input only  | `IN user_id INT` |
| OUT  | Output only | `OUT result INT` |
| INOUT| Both input and output | `INOUT counter INT` |

---

## ✅ Example: Complete Procedure

```sql
DELIMITER //

CREATE PROCEDURE ProcessSalaryIncrease (
    IN dept_name VARCHAR(50),
    IN increase_percent DECIMAL(5,2),
    OUT affected_count INT
)
BEGIN
    -- Update salaries
    UPDATE Employees 
    SET salary = salary * (1 + increase_percent / 100)
    WHERE department = dept_name;
    
    -- Get count of affected employees
    SELECT COUNT(*) INTO affected_count
    FROM Employees 
    WHERE department = dept_name;
END //

DELIMITER ;
```

---

## 🎯 Interview One-Liner

> Stored procedures are pre-compiled SQL code stored in the database that can be called with parameters, improving performance, security, and code reusability.

---

# 176. What are Triggers? Types of triggers?

## ✅ What is a Trigger?

**Trigger** is a special stored procedure that automatically executes when specific events occur in the database.

👉 "Automatically" means **no manual calling needed**

---

## ✅ Creating a Trigger

```sql
CREATE TRIGGER audit_salary_changes
AFTER UPDATE ON Employees
FOR EACH ROW
BEGIN
    INSERT INTO SalaryAudit (emp_id, old_salary, new_salary, change_date)
    VALUES (OLD.employee_id, OLD.salary, NEW.salary, NOW());
END;
```

---

## ✅ Trigger Components

| Component | Description    | Example                |
| --------- | -----------    | -------                |
| Timing    | WHEN it fires  | BEFORE, AFTER          |
| Event     | WHAT causes it | INSERT, UPDATE, DELETE |
| Scope     | WHICH rows     | FOR EACH ROW           |
| Body      | WHAT to do     | SQL statements         |

---

## ✅ Types of Triggers

### 🔹 1. Timing-Based

**BEFORE Triggers:**
- Execute before the triggering event
- Can modify data before it's saved
- Used for validation/modification

```sql
CREATE TRIGGER validate_salary
BEFORE INSERT ON Employees
FOR EACH ROW
BEGIN
    IF NEW.salary < 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Salary cannot be negative';
    END IF;
END;
```

**AFTER Triggers:**
- Execute after the triggering event
- Cannot modify the triggering data
- Used for auditing/logging

---

### 🔹 2. Event-Based

**INSERT Triggers:**
```sql
CREATE TRIGGER log_new_employee
AFTER INSERT ON Employees
FOR EACH ROW
BEGIN
    INSERT INTO AuditLog (action, emp_id) VALUES ('INSERT', NEW.employee_id);
END;
```

**UPDATE Triggers:**
```sql
CREATE TRIGGER track_salary_changes
AFTER UPDATE ON Employees
FOR EACH ROW
BEGIN
    IF OLD.salary != NEW.salary THEN
        INSERT INTO SalaryHistory (emp_id, old_salary, new_salary) 
        VALUES (NEW.employee_id, OLD.salary, NEW.salary);
    END IF;
END;
```

**DELETE Triggers:**
```sql
CREATE TRIGGER prevent_deletion
BEFORE DELETE ON Employees
FOR EACH ROW
BEGIN
    IF OLD.role = 'CEO' THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Cannot delete CEO';
    END IF;
END;
```

---

### 🔹 3. Row-Level vs Statement-Level

**FOR EACH ROW (Row-level):**
- Fires once per affected row
- Can access OLD and NEW values

**FOR EACH STATEMENT (Statement-level):**
- Fires once per statement
- Cannot access individual row values

---

## ✅ Special Variables

| Variable | Description | Available In |
| -------- | ----------- | ------------ |
| NEW      | New values  | INSERT/UPDATE |
| OLD      | Old values  | UPDATE/DELETE |
| NEW.column | Specific new value | INSERT/UPDATE |
| OLD.column | Specific old value | UPDATE/DELETE |

---

## 🎯 Interview One-Liner

> Triggers automatically execute SQL code in response to database events. Types include BEFORE/AFTER timing and INSERT/UPDATE/DELETE events, commonly used for auditing and validation.

---

# 177. What are Constraints? (NOT NULL, UNIQUE, CHECK, DEFAULT)

## ✅ What are Constraints?

**Constraints** are rules enforced on database columns to maintain data integrity.

👉 Automatically enforced by the database

---

## ✅ Types of Constraints

### 🔹 1. NOT NULL

**Column cannot contain NULL values**

```sql
CREATE TABLE Users (
    user_id INT PRIMARY KEY,
    email VARCHAR(100) NOT NULL,  -- Required field
    name VARCHAR(50) NOT NULL     -- Required field
);
```

---

### 🔹 2. UNIQUE

**All values in column must be unique**

```sql
CREATE TABLE Users (
    user_id INT PRIMARY KEY,
    email VARCHAR(100) UNIQUE,    -- No duplicate emails
    phone VARCHAR(20) UNIQUE      -- No duplicate phones
);
```

---

### 🔹 3. CHECK

**Values must satisfy a condition**

```sql
CREATE TABLE Products (
    product_id INT PRIMARY KEY,
    name VARCHAR(100),
    price DECIMAL(10,2) CHECK (price > 0),           -- Price must be positive
    stock_quantity INT CHECK (stock_quantity >= 0),  -- Cannot be negative
    category VARCHAR(50) CHECK (category IN ('Electronics', 'Books', 'Clothing'))
);
```

---

### 🔹 4. DEFAULT

**Provides default value when none specified**

```sql
CREATE TABLE Orders (
    order_id INT PRIMARY KEY,
    user_id INT,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- Auto-set current time
    status VARCHAR(20) DEFAULT 'Pending',            -- Default status
    total_amount DECIMAL(10,2) DEFAULT 0.00
);
```

---

### 🔹 5. PRIMARY KEY

**Unique identifier + NOT NULL**

```sql
CREATE TABLE Users (
    user_id INT PRIMARY KEY,  -- Unique + Not NULL
    name VARCHAR(50)
);
```

---

### 🔹 6. FOREIGN KEY

**References primary key in another table**

```sql
CREATE TABLE Orders (
    order_id INT PRIMARY KEY,
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES Users(user_id)  -- Must exist in Users
);
```

---

## ✅ Constraint Levels

### 🔹 Column Level

Applied to single column:

```sql
CREATE TABLE Users (
    id INT PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE
);
```

### 🔹 Table Level

Applied to multiple columns:

```sql
CREATE TABLE Users (
    id INT,
    email VARCHAR(100),
    phone VARCHAR(20),
    PRIMARY KEY (id),
    UNIQUE (email, phone)  -- Composite unique constraint
);
```

---

## ✅ Adding Constraints Later

```sql
-- Add NOT NULL (requires no NULL values first)
ALTER TABLE Users MODIFY email VARCHAR(100) NOT NULL;

-- Add CHECK constraint
ALTER TABLE Products ADD CONSTRAINT chk_price CHECK (price > 0);

-- Add FOREIGN KEY
ALTER TABLE Orders ADD CONSTRAINT fk_user 
FOREIGN KEY (user_id) REFERENCES Users(user_id);
```

---

## ✅ Dropping Constraints

```sql
-- Drop constraint by name
ALTER TABLE Users DROP CONSTRAINT chk_salary;

-- Drop PRIMARY KEY
ALTER TABLE Users DROP PRIMARY KEY;

-- Drop FOREIGN KEY
ALTER TABLE Orders DROP FOREIGN KEY fk_user;
```

---

## 🎯 Interview One-Liner

> Constraints enforce data integrity: NOT NULL prevents nulls, UNIQUE ensures uniqueness, CHECK validates conditions, DEFAULT provides fallback values, PRIMARY KEY uniquely identifies rows, FOREIGN KEY maintains referential integrity.

---

# 178. What is DDL vs DML in Database?

## ✅ DDL (Data Definition Language)

**DDL** commands define and manage database structure/schema.

👉 Changes database structure, not data

---

## ✅ DDL Commands

### 🔹 CREATE

Creates database objects:

```sql
CREATE TABLE Employees (
    id INT PRIMARY KEY,
    name VARCHAR(50),
    salary DECIMAL(10,2)
);

CREATE INDEX idx_salary ON Employees(salary);

CREATE DATABASE CompanyDB;
```

### 🔹 ALTER

Modifies existing objects:

```sql
ALTER TABLE Employees ADD COLUMN department VARCHAR(50);

ALTER TABLE Employees MODIFY COLUMN salary DECIMAL(12,2);

ALTER TABLE Employees DROP COLUMN department;
```

### 🔹 DROP

Deletes objects permanently:

```sql
DROP TABLE Employees;        -- Deletes table and data
DROP INDEX idx_salary;       -- Deletes index
DROP DATABASE CompanyDB;     -- Deletes entire database
```

### 🔹 TRUNCATE

Removes all data but keeps structure:

```sql
TRUNCATE TABLE Employees;    -- Faster than DELETE FROM
```

---

## ✅ DML (Data Manipulation Language)

**DML** commands manipulate data within existing tables.

👉 Changes data, not structure

---

## ✅ DML Commands

### 🔹 SELECT

Retrieves data:

```sql
SELECT name, salary FROM Employees WHERE department = 'IT';

SELECT COUNT(*) FROM Employees;

SELECT * FROM Employees ORDER BY salary DESC;
```

### 🔹 INSERT

Adds new data:

```sql
INSERT INTO Employees (name, salary) VALUES ('John', 50000);

INSERT INTO Employees VALUES (1, 'John', 50000);

INSERT INTO Employees (name, salary) 
SELECT name, salary FROM TempEmployees;
```

### 🔹 UPDATE

Modifies existing data:

```sql
UPDATE Employees SET salary = salary * 1.1 WHERE department = 'IT';

UPDATE Employees SET name = 'John Doe' WHERE id = 1;
```

### 🔹 DELETE

Removes data:

```sql
DELETE FROM Employees WHERE salary < 30000;

DELETE FROM Employees WHERE department = 'HR';
```

---

## 🔥 Key Differences
| Feature          | DDL                          | DML                           |
| ---------------- | ---------------------------- | ----------------------------  |
| Purpose          | Define/modify structure      | Manipulate data               |
| Commands         | CREATE, ALTER, DROP, TRUNCATE| SELECT, INSERT, UPDATE, DELETE|
| Effect on Data   | ❌ No direct effect          | ✅ Changes data               |
| Transaction      | ❌ Auto-committed            | ✅ Can be rolled back         |
| Performance      | 🐢 Slower (structure changes)| ⚡ Faster (data operations)    |
| Examples         | CREATE TABLE, ALTER COLUMN   | SELECT *, INSERT INTO         |

---

## ✅ Transaction Behavior

**DDL Commands:**
- Auto-committed (cannot rollback)
- Implicit commit before/after DDL

```sql
BEGIN;
UPDATE Employees SET salary = 60000 WHERE id = 1;
CREATE TABLE AuditLog (id INT);  -- Auto-commits previous UPDATE
ROLLBACK;  -- Cannot rollback the UPDATE!
```

**DML Commands:**
- Can be rolled back
- Part of transactions

```sql
BEGIN;
UPDATE Employees SET salary = 60000 WHERE id = 1;
INSERT INTO AuditLog VALUES (1, 'Salary updated');
COMMIT;  -- Both operations committed

-- Or ROLLBACK to undo both
```

---

## ✅ Real-World Examples

### 🔹 DDL Usage

```sql
-- Setting up database structure
CREATE DATABASE ECommerce;

USE ECommerce;

CREATE TABLE Users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Products (
    product_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    category VARCHAR(50)
);

-- Later modifications
ALTER TABLE Users ADD COLUMN phone VARCHAR(20);
ALTER TABLE Products ADD CONSTRAINT chk_price CHECK (price > 0);
```

### 🔹 DML Usage

```sql
-- Adding data
INSERT INTO Users (username, email) VALUES 
('john_doe', 'john@example.com'),
('jane_smith', 'jane@example.com');

INSERT INTO Products (name, price, category) VALUES 
('Laptop', 999.99, 'Electronics'),
('Book', 29.99, 'Education');

-- Querying data
SELECT * FROM Users WHERE created_at > '2023-01-01';

-- Updating data
UPDATE Products SET price = price * 0.9 WHERE category = 'Electronics';

-- Deleting data
DELETE FROM Users WHERE username = 'inactive_user';
```

---

## ✅ When to Use What

### 🔹 Use DDL when:

- Creating new tables/databases
- Changing table structure
- Adding/removing columns
- Creating indexes/constraints
- Database setup/migration

### 🔹 Use DML when:

- Inserting new records
- Updating existing data
- Deleting records
- Querying/reporting data
- Daily application operations

---

## 🎯 Interview One-Liner

> DDL defines and manages database structure (CREATE, ALTER, DROP), while DML manipulates data within tables (SELECT, INSERT, UPDATE, DELETE). DDL is auto-committed, DML can be rolled back.

## 🎯 Interview One-Liner

> DDL defines and manages database structure (CREATE, ALTER, DROP), while DML manipulates data within tables (SELECT, INSERT, UPDATE, DELETE). DDL is auto-committed, DML can be rolled back.

### **Transactions**
178. What is Transaction? COMMIT vs ROLLBACK
179. What is isolation level? Types?
180. What is deadlock in database?
181. What is locking mechanism?
182. Optimistic vs Pessimistic locking

---
# 178. What is Transaction? COMMIT vs ROLLBACK

## ✅ What is a Transaction?

**Transaction** is a logical unit of work that contains one or more SQL statements.

👉 Either **ALL statements succeed** or **ALL are rolled back** - no partial completion

---

## ✅ Transaction Properties (ACID)

### 🔹 A - Atomicity

**All or Nothing**

```sql
-- Transfer $100 from Account A to Account B
BEGIN TRANSACTION;

UPDATE AccountA SET balance = balance - 100;
UPDATE AccountB SET balance = balance + 100;

COMMIT;  -- Both succeed or both fail
```

---

### 🔹 C - Consistency

**Database remains in valid state**

```sql
-- Before: Total balance = $1500
-- After: Total balance = $1500 (consistent)
```

---

### 🔹 I - Isolation

**Transactions don't interfere with each other**

```sql
-- Transaction 1 sees original data or final result
-- Not intermediate states from Transaction 2
```

---

### 🔹 D - Durability

**Committed changes survive system failures**

```sql
COMMIT;  -- Data is permanently saved
```

---

## ✅ Transaction Control Commands

### 🔹 BEGIN / START TRANSACTION

Starts a transaction:

```sql
BEGIN TRANSACTION;
-- or
START TRANSACTION;
```

### 🔹 COMMIT

**Saves all changes permanently**

```sql
BEGIN;
UPDATE Employees SET salary = 60000 WHERE id = 1;
INSERT INTO AuditLog VALUES (1, 'Salary updated');
COMMIT;  -- Both changes are saved permanently
```

### 🔹 ROLLBACK

**Undoes all changes since transaction start**

```sql
BEGIN;
UPDATE Employees SET salary = 60000 WHERE id = 1;
INSERT INTO AuditLog VALUES (1, 'Salary updated');
ROLLBACK;  -- Both changes are undone
```

### 🔹 SAVEPOINT

**Creates a rollback point within transaction**

```sql
BEGIN;
UPDATE Employees SET salary = 60000 WHERE id = 1;
SAVEPOINT before_bonus;
UPDATE Employees SET bonus = 5000 WHERE id = 1;
ROLLBACK TO SAVEPOINT before_bonus;  -- Only bonus update is undone
COMMIT;  -- Salary update is saved
```

---

## ✅ Auto-Commit vs Manual Transactions

### 🔹 Auto-Commit (Default)

Each statement is a separate transaction:

```sql
UPDATE Employees SET salary = 60000 WHERE id = 1;  -- Auto-committed
INSERT INTO AuditLog VALUES (1, 'Updated');        -- Auto-committed
```

### 🔹 Manual Transactions

Group statements together:

```sql
SET AUTOCOMMIT = 0;  -- Disable auto-commit

BEGIN;
UPDATE Employees SET salary = 60000 WHERE id = 1;
INSERT INTO AuditLog VALUES (1, 'Updated');
COMMIT;  -- Both committed together
```

---

## ✅ Transaction States

```
Active → Partially Committed → Committed
   ↓
Failed → Aborted → Rolled Back
```

---

## ✅ Real-World Example: Bank Transfer

```sql
-- Safe money transfer
BEGIN TRANSACTION;

-- Step 1: Check sender balance
SELECT balance INTO @sender_balance FROM Accounts WHERE id = @sender_id;
IF @sender_balance < @amount THEN
    ROLLBACK;
    RETURN 'Insufficient funds';
END IF;

-- Step 2: Debit sender
UPDATE Accounts SET balance = balance - @amount WHERE id = @sender_id;

-- Step 3: Credit receiver
UPDATE Accounts SET balance = balance + @amount WHERE id = @receiver_id;

-- Step 4: Log transaction
INSERT INTO Transactions (sender_id, receiver_id, amount) 
VALUES (@sender_id, @receiver_id, @amount);

COMMIT;  -- All changes permanent
```

---

## 🎯 Interview One-Liner

> A transaction is a logical unit of work that ensures all operations succeed together or fail together. COMMIT saves changes permanently, ROLLBACK undoes all changes since transaction start.

---

# 179. What is isolation level? Types?

## ✅ What is Isolation Level?

**Isolation Level** controls how transaction integrity is visible to other concurrent transactions.

👉 Balances **concurrency** vs **data consistency**

---

## ✅ Transaction Isolation Problems

### 🔹 1. Dirty Read

**Reading uncommitted data**

```
Transaction 1: UPDATE balance = 200 WHERE id = 1;
Transaction 2: SELECT balance FROM Accounts WHERE id = 1;  -- Reads 200
Transaction 1: ROLLBACK;  -- Balance reverts to 100
-- Transaction 2 read "dirty" data that was never committed
```

### 🔹 2. Non-Repeatable Read

**Same query returns different results**

```
Transaction 1: SELECT balance FROM Accounts WHERE id = 1;  -- Returns 100
Transaction 2: UPDATE Accounts SET balance = 200 WHERE id = 1; COMMIT;
Transaction 1: SELECT balance FROM Accounts WHERE id = 1;  -- Returns 200
-- Same query, different result
```

### 🔹 3. Phantom Read

**New rows appear in subsequent queries**

```
Transaction 1: SELECT COUNT(*) FROM Accounts WHERE balance > 100;  -- Returns 5
Transaction 2: INSERT INTO Accounts VALUES (6, 150); COMMIT;
Transaction 1: SELECT COUNT(*) FROM Accounts WHERE balance > 100;  -- Returns 6
-- "Phantom" row appeared
```

---

## ✅ Isolation Levels (ANSI Standard)

### 🔹 1. READ UNCOMMITTED

**Lowest isolation level**

- ✅ Allows dirty reads
- ✅ Allows non-repeatable reads
- ✅ Allows phantom reads
- ⚡ Highest concurrency

```sql
SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;
```

**Use case:** Real-time dashboards where slight inaccuracies are acceptable

---

### 🔹 2. READ COMMITTED (Default in most DBs)

**Prevents dirty reads**

- ❌ No dirty reads
- ✅ Allows non-repeatable reads
- ✅ Allows phantom reads
- ⚖️ Balanced performance

```sql
SET TRANSACTION ISOLATION LEVEL READ COMMITTED;
```

**Use case:** Most business applications

---

### 🔹 3. REPEATABLE READ

**Prevents dirty reads + non-repeatable reads**

- ❌ No dirty reads
- ❌ No non-repeatable reads
- ✅ Allows phantom reads
- 🐢 Lower concurrency

```sql
SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;
```

**Use case:** Financial reports requiring consistent data

---

### 🔹 4. SERIALIZABLE

**Highest isolation level**

- ❌ No dirty reads
- ❌ No non-repeatable reads
- ❌ No phantom reads
- 🐢 Lowest concurrency (like single-threaded)

```sql
SET TRANSACTION ISOLATION LEVEL SERIALIZABLE;
```

**Use case:** Critical financial transactions

---

## ✅ Isolation Level Comparison

| Isolation Level | Dirty Read | Non-Repeatable Read | Phantom Read | Performance |
|----------------|------------|---------------------|--------------|-------------|
| READ UNCOMMITTED | ✅ Allowed | ✅ Allowed | ✅ Allowed | ⚡ Highest |
| READ COMMITTED | ❌ Prevented | ✅ Allowed | ✅ Allowed | ⚖️ Good |
| REPEATABLE READ | ❌ Prevented | ❌ Prevented | ✅ Allowed | 🐢 Lower |
| SERIALIZABLE | ❌ Prevented | ❌ Prevented | ❌ Prevented | 🐢 Lowest |

---

## ✅ Setting Isolation Level

### 🔹 Session Level

```sql
SET SESSION TRANSACTION ISOLATION LEVEL READ COMMITTED;
```

### 🔹 Transaction Level

```sql
BEGIN;
SET TRANSACTION ISOLATION LEVEL SERIALIZABLE;
-- transaction statements
COMMIT;
```

### 🔹 Spring Boot Example

```java
@Transactional(isolation = Isolation.READ_COMMITTED)
public void transferMoney() {
    // transaction code
}
```

---

## ✅ Real-World Scenarios

### 🔹 Banking (High Consistency)

```sql
-- Use SERIALIZABLE for fund transfers
SET TRANSACTION ISOLATION LEVEL SERIALIZABLE;
BEGIN;
-- Transfer logic
COMMIT;
```

### 🔹 Analytics (High Performance)

```sql
-- Use READ UNCOMMITTED for reports
SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;
SELECT SUM(amount) FROM Transactions;  -- Approximate values OK
```

---

## 🎯 Interview One-Liner

> Isolation levels control transaction visibility to prevent concurrency issues. READ UNCOMMITTED allows all anomalies, READ COMMITTED prevents dirty reads, REPEATABLE READ prevents non-repeatable reads, SERIALIZABLE prevents all anomalies.

---

# 180. What is deadlock in database?

## ✅ What is Deadlock?

**Deadlock** is a situation where two or more transactions are **waiting for each other to release locks**, creating a circular dependency.

👉 Neither transaction can proceed - system must intervene

---

## ✅ Deadlock Example

**Transaction 1:**
```sql
BEGIN;
UPDATE Accounts SET balance = balance - 100 WHERE id = 1;  -- Lock Account 1
-- Waits for Account 2 lock
UPDATE Accounts SET balance = balance + 100 WHERE id = 2;  -- Needs Account 2
```

**Transaction 2:**
```sql
BEGIN;
UPDATE Accounts SET balance = balance - 50 WHERE id = 2;   -- Lock Account 2
-- Waits for Account 1 lock
UPDATE Accounts SET balance = balance + 50 WHERE id = 1;   -- Needs Account 1
```

**Result:** Both transactions wait forever ⭕

---

## ✅ Deadlock Conditions (Coffman Conditions)

For deadlock to occur, ALL four conditions must be met:

### 🔹 1. Mutual Exclusion

Resources cannot be shared (locks are exclusive)

### 🔹 2. Hold and Wait

Transaction holds one resource while waiting for another

### 🔹 3. No Preemption

Resources cannot be forcibly taken from transactions

### 🔹 4. Circular Wait

Circular chain of transactions waiting for each other

---

## ✅ Deadlock Prevention Strategies

### 🔹 1. Lock Ordering (Most Effective)

**Always acquire locks in the same order**

```sql
-- Good: Always lock lower ID first
BEGIN;
UPDATE Accounts SET balance = balance - 100 WHERE id = LEAST(@from_id, @to_id);
UPDATE Accounts SET balance = balance + 100 WHERE id = GREATEST(@from_id, @to_id);
COMMIT;
```

### 🔹 2. Lock Timeout

**Set maximum wait time**

```sql
SET innodb_lock_wait_timeout = 10;  -- Wait max 10 seconds
```

### 🔹 3. Deadlock Detection

**Database automatically detects and resolves deadlocks**

```sql
-- MySQL: One transaction is rolled back
-- SQL Server: Deadlock victim chosen by priority
```

---

## ✅ Deadlock Resolution

### 🔹 Automatic Resolution

Most databases automatically:

1. **Detect** deadlock using wait-for graphs
2. **Choose victim** (usually shorter transaction)
3. **Rollback victim** transaction
4. **Allow other transaction** to proceed

### 🔹 Manual Resolution

```sql
-- Check for deadlocks
SHOW ENGINE INNODB STATUS;

-- Kill specific transaction
KILL 12345;  -- Process ID
```

---

## ✅ Deadlock Monitoring

### 🔹 MySQL

```sql
-- Check deadlock information
SHOW ENGINE INNODB STATUS;

-- Deadlock count
SHOW STATUS LIKE 'innodb_row_lock_waits';
```

### 🔹 SQL Server

```sql
-- Deadlock events
SELECT * FROM sys.dm_exec_requests WHERE blocking_session_id <> 0;
```

---

## ✅ Avoiding Deadlocks

### 🔹 Best Practices

1. **Keep transactions short**
2. **Access resources in consistent order**
3. **Use appropriate isolation levels**
4. **Avoid user interaction in transactions**
5. **Use timeouts**
6. **Index properly** to reduce lock duration

### 🔹 Code Example

```java
@Transactional
public void transferMoney(int fromId, int toId, BigDecimal amount) {
    // Always lock in order (smaller ID first)
    int firstId = Math.min(fromId, toId);
    int secondId = Math.max(fromId, toId);
    
    // Lock accounts in consistent order
    accountRepository.lockAccount(firstId);
    accountRepository.lockAccount(secondId);
    
    // Transfer logic
    // ...
}
```

---

## ✅ Deadlock vs Live Lock

| Feature | Deadlock | Live Lock |
|---------|----------|-----------|
| State | Permanent wait | Constant yielding |
| Resolution | External intervention | May resolve itself |
| Example | Circular waiting | Two processes keep yielding to each other |

---

## 🎯 Interview One-Liner

> Deadlock occurs when transactions wait for each other in a circular dependency. Prevention strategies include lock ordering, timeouts, and keeping transactions short. Databases automatically detect and resolve deadlocks by rolling back one transaction.

---

# 181. What is locking mechanism?

## ✅ What is Locking?

**Locking** is a mechanism that prevents multiple transactions from modifying the same data simultaneously.

👉 Ensures **data consistency** in concurrent environments

---

## ✅ Types of Locks

### 🔹 1. Based on Granularity

**Row-Level Locks:**
- Lock individual rows
- High concurrency
- Used by InnoDB (MySQL)

**Page-Level Locks:**
- Lock database pages
- Medium concurrency
- Used by SQL Server

**Table-Level Locks:**
- Lock entire tables
- Low concurrency
- Used by MyISAM (MySQL)

### 🔹 2. Based on Mode

**Shared Lock (S):**
- Multiple transactions can read
- No writes allowed while held
- Used for SELECT statements

**Exclusive Lock (X):**
- Only one transaction can access
- Blocks all other access
- Used for INSERT/UPDATE/DELETE

**Update Lock (U):**
- Like shared lock but can be upgraded to exclusive
- Prevents deadlock scenarios

---

## ✅ Lock Compatibility Matrix

| Lock Type | Shared (S) | Exclusive (X) | Update (U) |
|-----------|------------|---------------|------------|
| Shared (S) | ✅ Compatible | ❌ Conflict | ✅ Compatible |
| Exclusive (X) | ❌ Conflict | ❌ Conflict | ❌ Conflict |
| Update (U) | ✅ Compatible | ❌ Conflict | ❌ Conflict |

---

## ✅ Lock Acquisition

### 🔹 Automatic Locking

Database automatically applies appropriate locks:

```sql
-- SELECT applies shared lock
SELECT * FROM Users WHERE id = 1;

-- UPDATE applies exclusive lock
UPDATE Users SET name = 'John' WHERE id = 1;
```

### 🔹 Explicit Locking

Manual lock control:

```sql
-- Shared lock
SELECT * FROM Users WHERE id = 1 LOCK IN SHARE MODE;

-- Exclusive lock
SELECT * FROM Users WHERE id = 1 FOR UPDATE;
```

---

## ✅ Lock Duration

### 🔹 Short Duration Locks

- Acquired when needed
- Released immediately after use
- High concurrency

### 🔹 Long Duration Locks

- Held for entire transaction
- Lower concurrency but safer

```sql
BEGIN;
SELECT * FROM Accounts WHERE id = 1 FOR UPDATE;  -- Lock held until commit
-- Other operations
COMMIT;  -- Lock released
```

---

## ✅ Lock Escalation

**Converting multiple fine-grained locks to fewer coarse-grained locks**

```sql
-- MySQL: When > 50% of table rows are locked, escalate to table lock
-- SQL Server: Similar behavior for performance
```

---

## ✅ Lock Types in Practice

### 🔹 MySQL InnoDB

```sql
-- Row-level locking by default
UPDATE Users SET status = 'active' WHERE id = 1;  -- Locks only row 1
```

### 🔹 SQL Server

```sql
-- Can use different isolation levels
SET TRANSACTION ISOLATION LEVEL READ COMMITTED;
UPDATE Users SET status = 'active' WHERE id = 1;
```

---

## ✅ Lock Monitoring

### 🔹 Check Current Locks

```sql
-- MySQL
SHOW PROCESSLIST;
SHOW ENGINE INNODB STATUS;

-- SQL Server
SELECT * FROM sys.dm_exec_requests WHERE blocking_session_id <> 0;
```

### 🔹 Lock Wait Timeout

```sql
-- MySQL
SET innodb_lock_wait_timeout = 10;  -- Wait max 10 seconds

-- SQL Server
SET LOCK_TIMEOUT 10000;  -- 10 seconds
```

---

## ✅ Lock-Related Issues

### 🔹 Lock Contention

Too many transactions waiting for locks:

**Symptoms:**
- Slow performance
- Timeouts
- Deadlocks

**Solutions:**
- Shorter transactions
- Proper indexing
- Appropriate isolation levels

### 🔹 Lock Starvation

Some transactions never get locks:

**Solutions:**
- Fair locking policies
- Lock timeouts
- Priority queues

---

## 🎯 Interview One-Liner

> Locking prevents concurrent data modifications. Shared locks allow multiple reads, exclusive locks block all access. Row-level locks provide high concurrency, table-level locks provide low concurrency but are simpler.

---

# 182. Optimistic vs Pessimistic locking

## ✅ What is Optimistic Locking?

**Optimistic locking** assumes **conflicts are rare** and checks for conflicts only at commit time.

👉 No locks held during transaction - better concurrency

---

## ✅ Optimistic Locking Flow

```
1. Read data (no lock)
2. Modify data in application
3. Check if data changed by others
4. Commit if no conflicts, rollback if conflicts
```

---

## ✅ Optimistic Locking Implementation

### 🔹 Version Column Approach

```sql
CREATE TABLE Products (
    id INT PRIMARY KEY,
    name VARCHAR(100),
    price DECIMAL(10,2),
    version INT DEFAULT 0  -- Version column
);
```

**Update with version check:**
```sql
UPDATE Products 
SET price = 29.99, version = version + 1
WHERE id = 1 AND version = 2;  -- Check current version

-- If 0 rows affected → version mismatch → conflict
```

### 🔹 Timestamp Approach

```sql
CREATE TABLE Products (
    id INT PRIMARY KEY,
    name VARCHAR(100),
    price DECIMAL(10,2),
    last_modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

**Update with timestamp check:**
```sql
UPDATE Products 
SET price = 29.99, last_modified = NOW()
WHERE id = 1 AND last_modified = '2023-01-01 10:00:00';
```

---

## ✅ What is Pessimistic Locking?

**Pessimistic locking** assumes **conflicts are likely** and locks data immediately.

👉 Holds locks during entire transaction - prevents conflicts

---

## ✅ Pessimistic Locking Flow

```
1. Acquire lock on data
2. Read/modify data
3. Release lock on commit/rollback
```

---

## ✅ Pessimistic Locking Implementation

### 🔹 Database-Level Locking

```sql
BEGIN;
SELECT * FROM Products WHERE id = 1 FOR UPDATE;  -- Exclusive lock
-- Other transactions blocked
UPDATE Products SET price = 29.99 WHERE id = 1;
COMMIT;  -- Lock released
```

### 🔹 Application-Level Locking

```java
@Transactional
public void updateProduct(int productId, BigDecimal newPrice) {
    // Lock the record
    Product product = productRepository.findByIdForUpdate(productId);
    
    // Modify
    product.setPrice(newPrice);
    
    // Save (lock held until commit)
    productRepository.save(product);
}
```

---

## 🔥 Key Differences

| Feature | Optimistic Locking | Pessimistic Locking |
|---------|-------------------|-------------------|
| Assumption | Conflicts are rare | Conflicts are likely |
| Lock Duration | No locks during transaction | Locks held during transaction |
| Concurrency | ⚡ High | 🐢 Low |
| Performance | ⚡ Better for reads | 🐢 Better for writes |
| Conflict Handling | Check at commit time | Prevent conflicts |
| Deadlock Risk | ❌ Low | ✅ High |
| Network Traffic | ⚡ Low | 🐢 High (lock management) |

---

## ✅ When to Use What

### 🔹 Use Optimistic Locking when:

- **Read-heavy applications**
- **Short transactions**
- **Low conflict probability**
- **Web applications**
- **Disconnected scenarios**

```java
// Spring Boot example
@Version
private Long version;

@Transactional
public void updateProduct(Product product) {
    // Version automatically checked
    productRepository.save(product);
}
```

### 🔹 Use Pessimistic Locking when:

- **Write-heavy applications**
- **Long transactions**
- **High conflict probability**
- **Financial systems**
- **Critical data integrity**

```java
// JPA example
@Lock(LockModeType.PESSIMISTIC_WRITE)
public Product findByIdForUpdate(Long id);
```

---

## ✅ Conflict Resolution

### 🔹 Optimistic Locking

**Automatic retry on conflicts:**

```java
@Transactional
public void updateWithRetry(Product product, int maxRetries) {
    for (int i = 0; i < maxRetries; i++) {
        try {
            productRepository.save(product);
            return;  // Success
        } catch (OptimisticLockException e) {
            // Reload and retry
            product = productRepository.findById(product.getId()).orElseThrow();
        }
    }
    throw new RuntimeException("Max retries exceeded");
}
```

### 🔹 Pessimistic Locking

**Conflicts prevented by locks - no retry needed**

---

## ✅ Real-World Examples

### 🔹 Optimistic: E-commerce Product Updates

```sql
-- Multiple users can view product simultaneously
-- Conflicts detected only when saving
UPDATE Products SET stock = stock - 1, version = version + 1
WHERE id = 123 AND version = 5;
```

### 🔹 Pessimistic: Bank Account Transfers

```sql
-- Lock accounts to prevent concurrent transfers
BEGIN;
SELECT balance FROM Accounts WHERE id = 1 FOR UPDATE;
-- Transfer logic
UPDATE Accounts SET balance = balance - 100 WHERE id = 1;
UPDATE Accounts SET balance = balance + 100 WHERE id = 2;
COMMIT;
```

---

## 🎯 Interview One-Liner

> Optimistic locking assumes rare conflicts and checks at commit time (high concurrency), while pessimistic locking assumes frequent conflicts and locks immediately (prevents conflicts but lower concurrency).


### **Normalization**
183. What is Normalization? Why needed?
184. 1NF, 2NF, 3NF, BCNF
185. What is Denormalization?

---

# 183. What is Normalization? Why needed?

## ✅ What is Normalization?

**Normalization** is the process of organizing data in a database to reduce redundancy and improve data integrity.

👉 Transforms complex tables into simpler, well-structured tables

---

## ✅ Why Normalization is Needed?

### 🔹 1. Eliminate Data Redundancy

**Same data stored multiple times**

**Before Normalization:**
```sql
-- Unnormalized table with redundancy
CREATE TABLE StudentCourses (
    student_id INT,
    student_name VARCHAR(100),
    course_id INT,
    course_name VARCHAR(100),
    instructor_name VARCHAR(100),  -- Repeated for each course
    grade VARCHAR(2)
);

-- Data redundancy: instructor_name repeated for every student-course combination
INSERT INTO StudentCourses VALUES
(1, 'John', 101, 'Math', 'Dr. Smith', 'A'),
(1, 'John', 102, 'Physics', 'Dr. Smith', 'B'),  -- Dr. Smith repeated
(2, 'Jane', 101, 'Math', 'Dr. Smith', 'A');     -- Dr. Smith repeated again
```

**After Normalization:**
```sql
-- Normalized: Separate tables eliminate redundancy
CREATE TABLE Students (
    student_id INT PRIMARY KEY,
    student_name VARCHAR(100)
);

CREATE TABLE Courses (
    course_id INT PRIMARY KEY,
    course_name VARCHAR(100),
    instructor_name VARCHAR(100)  -- Stored only once
);

CREATE TABLE Enrollments (
    student_id INT,
    course_id INT,
    grade VARCHAR(2),
    FOREIGN KEY (student_id) REFERENCES Students(student_id),
    FOREIGN KEY (course_id) REFERENCES Courses(course_id)
);
```

---

### 🔹 2. Prevent Update Anomalies

**Inconsistent data when updating**

**Update Anomaly Example:**
```sql
-- If Dr. Smith changes name, must update multiple rows
UPDATE StudentCourses SET instructor_name = 'Dr. Smith Jr.' 
WHERE instructor_name = 'Dr. Smith';
-- Risk: Missing some rows leads to inconsistency
```

---

### 🔹 3. Prevent Insert Anomalies

**Cannot insert data without unrelated data**

**Insert Anomaly Example:**
```sql
-- Cannot add new course without a student enrolling
-- Cannot add instructor without course assignment
INSERT INTO StudentCourses (course_id, course_name, instructor_name) 
VALUES (103, 'Chemistry', 'Dr. Brown');
-- Fails because student_id is required but we don't have a student yet
```

---

### 🔹 4. Prevent Delete Anomalies

**Losing data when deleting unrelated data**

**Delete Anomaly Example:**
```sql
-- Deleting last student from Math course also deletes course info
DELETE FROM StudentCourses WHERE student_id = 1 AND course_id = 101;
-- Now Math course and Dr. Smith info is completely lost!
```

---

## ✅ Benefits of Normalization

### 🔹 Data Integrity

- **Consistency:** Same data appears only once
- **Accuracy:** Changes made in one place only
- **Reliability:** No conflicting information

### 🔹 Storage Efficiency

- **Reduced redundancy:** Less disk space used
- **Faster updates:** Fewer places to update
- **Better performance:** Smaller tables, faster queries

### 🔹 Maintainability

- **Easier changes:** Modify data in one location
- **Flexible design:** Easy to add new data types
- **Database evolution:** Support schema changes

### 🔹 Query Performance

- **Focused queries:** Each table serves specific purpose
- **Index effectiveness:** Better indexing strategies
- **Join optimization:** Smaller tables join faster

---

## ✅ Normalization Process

### 🔹 Step 1: Identify Entities

**Find main objects in your system**

```
Student, Course, Instructor, Enrollment, Department
```

### 🔹 Step 2: Identify Relationships

**How entities relate to each other**

```
- Student enrolls in Course (Many-to-Many)
- Course taught by Instructor (Many-to-One)
- Instructor belongs to Department (Many-to-One)
```

### 🔹 Step 3: Apply Normal Forms

**Progressively eliminate anomalies**

```
Unnormalized → 1NF → 2NF → 3NF → BCNF
```

### 🔹 Step 4: Create Tables

**Design tables with proper relationships**

```sql
-- Result of normalization process
CREATE TABLE Students (student_id PK, name, email, dept_id FK);
CREATE TABLE Courses (course_id PK, name, credits, dept_id FK);
CREATE TABLE Instructors (instructor_id PK, name, dept_id FK);
CREATE TABLE Departments (dept_id PK, name, location);
CREATE TABLE Enrollments (student_id FK, course_id FK, semester, grade);
```

---

## ✅ Normalization Levels

### 🔹 First Normal Form (1NF)

**Eliminate repeating groups**

### 🔹 Second Normal Form (2NF)

**Remove partial dependencies**

### 🔹 Third Normal Form (3NF)

**Remove transitive dependencies**

### 🔹 Boyce-Codd Normal Form (BCNF)

**Every determinant is a candidate key**

---

## ✅ When NOT to Normalize

### 🔹 Read-Heavy Systems

**OLAP, Data Warehouses, Reporting**

- Complex joins slow down queries
- Denormalization often better for performance

### 🔹 Simple Applications

**Small datasets, simple relationships**

- Over-normalization adds complexity
- Balance simplicity vs. normalization benefits

### 🔹 Real-Time Systems

**Performance critical applications**

- Extra joins add latency
- Consider denormalization for speed

---

## ✅ Normalization vs Performance

| Aspect | Normalized | Denormalized |
|--------|------------|--------------|
| **Integrity** | ✅ Excellent | ⚠️ Risk of inconsistency |
| **Storage** | ✅ Efficient | 🐢 More storage |
| **Updates** | ✅ Fast | 🐢 Slower (multiple places) |
| **Queries** | 🐢 Complex joins | ⚡ Simple queries |
| **Maintenance** | ✅ Easy | 🐢 Complex |

---

## 🎯 Interview One-Liner

> Normalization eliminates data redundancy and prevents update/insert/delete anomalies by organizing data into related tables. It ensures data integrity but may require complex joins for queries.

---

# 184. 1NF, 2NF, 3NF, BCNF

## ✅ Normal Forms Hierarchy

**Normalization** is a step-by-step process to eliminate data anomalies:

```
Unnormalized Data
       ↓
    1NF (Eliminate repeating groups)
       ↓
    2NF (Remove partial dependencies)
       ↓
    3NF (Remove transitive dependencies)
       ↓
   BCNF (Every determinant is candidate key)
```

---

## ✅ First Normal Form (1NF)

**A table is in 1NF if:**
- ✅ All columns contain atomic (indivisible) values
- ✅ No repeating groups or arrays
- ❌ No multi-valued attributes

### 🔹 1NF Violation Example

**Before 1NF:**
```sql
-- Violates 1NF: Multi-valued phone numbers
CREATE TABLE Students (
    student_id INT,
    name VARCHAR(100),
    phones VARCHAR(500)  -- "123-456-7890, 098-765-4321"
);
```

**After 1NF:**
```sql
-- 1NF Compliant: Separate table for phones
CREATE TABLE Students (
    student_id INT PRIMARY KEY,
    name VARCHAR(100)
);

CREATE TABLE StudentPhones (
    student_id INT,
    phone_type VARCHAR(20),  -- 'home', 'mobile', 'work'
    phone_number VARCHAR(20),
    FOREIGN KEY (student_id) REFERENCES Students(student_id)
);
```

### 🔹 Another 1NF Example

**Unnormalized Data:**
```
Student: John, Courses: Math, Physics, Chemistry
```

**1NF Tables:**
```
Students: (1, John)
Courses: (1, Math), (1, Physics), (1, Chemistry)
```

---

## ✅ Second Normal Form (2NF)

**A table is in 2NF if:**
- ✅ It is in 1NF
- ✅ All non-key attributes depend on the entire primary key
- ❌ No partial dependencies

**Partial Dependency:** Non-key column depends on part of composite primary key

### 🔹 2NF Violation Example

**Before 2NF:**
```sql
-- Composite PK: (student_id, course_id)
-- Partial dependency: course_name depends only on course_id
CREATE TABLE Enrollments (
    student_id INT,
    course_id INT,
    student_name VARCHAR(100),    -- Depends on student_id only
    course_name VARCHAR(100),     -- Depends on course_id only
    grade VARCHAR(2),
    PRIMARY KEY (student_id, course_id)
);
```

**After 2NF:**
```sql
-- Remove partial dependencies
CREATE TABLE Students (
    student_id INT PRIMARY KEY,
    student_name VARCHAR(100)
);

CREATE TABLE Courses (
    course_id INT PRIMARY KEY,
    course_name VARCHAR(100)
);

CREATE TABLE Enrollments (
    student_id INT,
    course_id INT,
    grade VARCHAR(2),
    PRIMARY KEY (student_id, course_id),
    FOREIGN KEY (student_id) REFERENCES Students(student_id),
    FOREIGN KEY (course_id) REFERENCES Courses(course_id)
);
```

---

## ✅ Third Normal Form (3NF)

**A table is in 3NF if:**
- ✅ It is in 2NF
- ✅ No transitive dependencies
- ❌ Non-key attributes don't depend on other non-key attributes

**Transitive Dependency:** A → B → C, where A determines C through B

### 🔹 3NF Violation Example

**Before 3NF:**
```sql
-- Transitive dependency: student_id → dept_id → dept_name
CREATE TABLE Students (
    student_id INT PRIMARY KEY,
    student_name VARCHAR(100),
    dept_id INT,
    dept_name VARCHAR(100),       -- Depends on dept_id, not student_id
    dept_location VARCHAR(100)    -- Depends on dept_id, not student_id
);
```

**After 3NF:**
```sql
-- Remove transitive dependencies
CREATE TABLE Departments (
    dept_id INT PRIMARY KEY,
    dept_name VARCHAR(100),
    dept_location VARCHAR(100)
);

CREATE TABLE Students (
    student_id INT PRIMARY KEY,
    student_name VARCHAR(100),
    dept_id INT,
    FOREIGN KEY (dept_id) REFERENCES Departments(dept_id)
);
```

---

## ✅ Boyce-Codd Normal Form (BCNF)

**A table is in BCNF if:**
- ✅ It is in 3NF
- ✅ Every determinant is a candidate key
- ❌ No non-trivial functional dependencies where determinant is not a candidate key

**BCNF is stricter than 3NF**

### 🔹 BCNF Violation Example

**Before BCNF:**
```sql
-- Functional dependency: instructor_id → course_id
-- But instructor_id is not a candidate key
CREATE TABLE CourseAssignments (
    course_id INT,
    instructor_id INT,
    semester VARCHAR(20),
    classroom VARCHAR(20),
    PRIMARY KEY (course_id, semester),
    UNIQUE (instructor_id, semester)  -- Instructor teaches one course per semester
);
```

**Problem:** One instructor per semester rule creates dependency where instructor_id determines course_id, but instructor_id is not a candidate key.

**After BCNF:**
```sql
-- Separate the dependency
CREATE TABLE Courses (
    course_id INT PRIMARY KEY,
    course_name VARCHAR(100)
);

CREATE TABLE Instructors (
    instructor_id INT PRIMARY KEY,
    instructor_name VARCHAR(100)
);

CREATE TABLE CourseOfferings (
    course_id INT,
    instructor_id INT,
    semester VARCHAR(20),
    classroom VARCHAR(20),
    PRIMARY KEY (course_id, semester),
    FOREIGN KEY (course_id) REFERENCES Courses(course_id),
    FOREIGN KEY (instructor_id) REFERENCES Instructors(instructor_id),
    UNIQUE (instructor_id, semester)  -- Still enforced
);
```

---

## ✅ Normal Forms Comparison

| Normal Form | Requirement | Eliminates |
|-------------|-------------|------------|
| **1NF** | Atomic values, no repeating groups | Repeating groups |
| **2NF** | 1NF + No partial dependencies | Partial dependencies |
| **3NF** | 2NF + No transitive dependencies | Transitive dependencies |
| **BCNF** | 3NF + Every determinant is candidate key | All non-trivial dependencies |

---

## ✅ Functional Dependencies

### 🔹 Types of Dependencies

**Functional Dependency (FD):** X → Y means X determines Y

```
student_id → student_name  (Trivial)
course_id → course_name    (Non-trivial)
student_id, course_id → grade  (Composite)
```

**Partial Dependency:** Part of key determines non-key attribute

```
(student_id, course_id) → grade  ✓ Full dependency
(student_id, course_id) → student_name  ❌ Partial (only student_id needed)
```

**Transitive Dependency:** A → B → C

```
student_id → dept_id → dept_name
student_id → dept_name (through dept_id)
```

---

## ✅ Step-by-Step Normalization Example

**Starting Data:**
```
Employees: emp_id, emp_name, dept_id, dept_name, project_id, project_name, hours
```

### 🔹 Step 1: 1NF
- ✅ All atomic values
- ✅ No repeating groups

### 🔹 Step 2: 2NF
**Identify partial dependencies:**
- emp_id → emp_name ❌ (partial)
- dept_id → dept_name ❌ (partial)
- project_id → project_name ❌ (partial)

**Create tables:**
```
Employees: (emp_id, emp_name, dept_id)
Departments: (dept_id, dept_name)
Projects: (project_id, project_name)
Assignments: (emp_id, project_id, hours)
```

### 🔹 Step 3: 3NF
**Check transitive dependencies:**
- emp_id → dept_id → dept_name ❌

**Final tables:**
```
Employees: (emp_id, emp_name, dept_id FK→Departments)
Departments: (dept_id, dept_name)
Projects: (project_id, project_name)
Assignments: (emp_id FK→Employees, project_id FK→Projects, hours)
```

---

## ✅ Practical Considerations

### 🔹 When to Stop at 3NF

- **Most applications:** 3NF is sufficient
- **BCNF:** Required only for complex business rules
- **Performance:** Higher normal forms may require more joins

### 🔹 Denormalization After Normalization

- **Read-heavy systems:** May denormalize for performance
- **OLAP systems:** Often use star schemas (denormalized)
- **Balance:** Integrity vs. Performance

---

## 🎯 Interview One-Liner

> 1NF eliminates repeating groups, 2NF removes partial dependencies, 3NF eliminates transitive dependencies, BCNF ensures every determinant is a candidate key. Higher normal forms reduce redundancy but increase join complexity.

---

# 185. What is Denormalization?

## ✅ What is Denormalization?

**Denormalization** is the process of intentionally introducing redundancy into a normalized database to improve read performance.

👉 **Trade data integrity** for **query performance**

---

## ✅ Why Denormalize?

### 🔹 Performance Issues with Normalized Databases

**Complex Joins Slow Down Queries:**

```sql
-- Normalized: Multiple joins needed
SELECT s.student_name, c.course_name, i.instructor_name, e.grade
FROM Students s
JOIN Enrollments e ON s.student_id = e.student_id
JOIN Courses c ON e.course_id = c.course_id
JOIN Instructors i ON c.instructor_id = i.instructor_id;
-- 4 tables, 3 joins = slow for large datasets
```

**Denormalized Alternative:**
```sql
-- Denormalized: Single table query
SELECT student_name, course_name, instructor_name, grade
FROM StudentEnrollments;  -- All data in one table
-- Much faster, no joins needed
```

---

## ✅ Types of Denormalization

### 🔹 1. Precomputed Values

**Store calculated results**

```sql
-- Instead of calculating total on every query
CREATE TABLE Orders (
    order_id INT PRIMARY KEY,
    customer_id INT,
    order_date DATE,
    -- Denormalized: Precomputed total
    total_amount DECIMAL(10,2),  -- Sum of all line items
    status VARCHAR(20)
);

-- Update total when line items change
UPDATE Orders SET total_amount = 
    (SELECT SUM(price * quantity) FROM OrderItems WHERE order_id = 1)
WHERE order_id = 1;
```

### 🔹 2. Duplicate Columns

**Copy data from related tables**

```sql
-- Denormalized enrollment table
CREATE TABLE Enrollments_Denorm (
    enrollment_id INT PRIMARY KEY,
    student_id INT,
    student_name VARCHAR(100),      -- Duplicated from Students
    course_id INT,
    course_name VARCHAR(100),       -- Duplicated from Courses
    instructor_name VARCHAR(100),   -- Duplicated from Instructors
    grade VARCHAR(2),
    enrollment_date DATE
);
```

### 🔹 3. Summary Tables

**Pre-aggregated data**

```sql
-- Monthly sales summary
CREATE TABLE MonthlySales (
    year INT,
    month INT,
    total_sales DECIMAL(15,2),
    total_orders INT,
    avg_order_value DECIMAL(10,2),
    PRIMARY KEY (year, month)
);

-- Updated via batch process or triggers
```

---

## ✅ When to Use Denormalization

### 🔹 Read-Heavy Workloads

**OLAP Systems, Reporting, Analytics**

- **Data Warehouses:** Complex queries on large datasets
- **Dashboards:** Real-time reporting requirements
- **Analytics:** Historical trend analysis

### 🔹 Performance Critical Applications

**High-Volume Read Operations**

```sql
-- E-commerce product catalog: Thousands of views per minute
SELECT product_name, price, category_name, brand_name, rating
FROM Products_Denorm
WHERE category = 'Electronics';
-- No joins needed for fast response
```

### 🔹 Distributed Systems

**Reduce Cross-Network Joins**

```sql
-- Microservices: Each service has its own denormalized data
-- Avoid expensive distributed joins
```

---

## ✅ Denormalization Strategies

### 🔹 1. Horizontal Partitioning

**Split table by rows**

```sql
-- Orders by year
CREATE TABLE Orders_2023 (order_id, customer_id, total, status);
CREATE TABLE Orders_2024 (order_id, customer_id, total, status);

-- Faster queries on recent data
SELECT * FROM Orders_2024 WHERE customer_id = 123;
```

### 🔹 2. Vertical Partitioning

**Split table by columns**

```sql
-- Frequently accessed columns
CREATE TABLE Products_Hot (
    product_id INT PRIMARY KEY,
    name VARCHAR(100),
    price DECIMAL(10,2),
    stock INT
);

-- Rarely accessed columns
CREATE TABLE Products_Cold (
    product_id INT PRIMARY KEY,
    description TEXT,
    specifications JSON,
    reviews TEXT
);
```

### 🔹 3. Star Schema (Data Warehouse)

**Central fact table with dimension tables**

```sql
-- Fact table (denormalized measures)
CREATE TABLE Sales_Fact (
    sale_id INT,
    customer_id INT,
    product_id INT,
    store_id INT,
    sale_date DATE,
    quantity INT,
    total_amount DECIMAL(10,2)
);

-- Dimension tables (normalized reference data)
CREATE TABLE Customers_Dim (customer_id, name, region, age_group);
CREATE TABLE Products_Dim (product_id, name, category, brand);
CREATE TABLE Stores_Dim (store_id, location, manager);
```

---

## ✅ Maintaining Denormalized Data

### 🔹 Update Strategies

**1. Application Logic:**
```java
@Transactional
public void updateProduct(Product product) {
    // Update normalized tables
    productRepository.save(product);
    
    // Update denormalized tables
    productDenormRepository.updateDenorm(product);
}
```

**2. Database Triggers:**
```sql
-- Auto-update denormalized table
CREATE TRIGGER update_product_denorm
AFTER UPDATE ON Products
FOR EACH ROW
BEGIN
    UPDATE Products_Denorm 
    SET name = NEW.name, price = NEW.price
    WHERE product_id = NEW.product_id;
END;
```

**3. Batch Updates:**
```sql
-- Nightly refresh of summary tables
UPDATE MonthlySales 
SET total_sales = (
    SELECT SUM(amount) FROM Orders 
    WHERE YEAR(order_date) = 2024 AND MONTH(order_date) = 1
)
WHERE year = 2024 AND month = 1;
```

---

## ✅ Risks and Drawbacks

### 🔹 Data Inconsistency

**Multiple copies can get out of sync**

```sql
-- Update product price in normalized table
UPDATE Products SET price = 29.99 WHERE id = 1;

-- Forget to update denormalized table
-- Now two different prices exist!
```

### 🔹 Update Complexity

**More places to update**

```sql
-- Cascade updates required
UPDATE Products SET category_name = 'Electronics_New'
WHERE category_name = 'Electronics';

UPDATE Products_Denorm SET category_name = 'Electronics_New'
WHERE category_name = 'Electronics';
```

### 🔹 Storage Overhead

**Duplicate data uses more space**

```sql
-- Normalized: Store category name once
-- Denormalized: Store category name with every product
-- 1000 products in same category = 1000 copies of category name
```

---

## ✅ Denormalization vs Normalization

| Aspect | Normalization | Denormalization |
|--------|---------------|-----------------|
| **Data Integrity** | ✅ Excellent | ⚠️ Risk of inconsistency |
| **Storage Efficiency** | ✅ Minimal redundancy | 🐢 More storage |
| **Query Performance** | 🐢 Complex joins | ⚡ Fast reads |
| **Update Performance** | ✅ Fast updates | 🐢 Slower updates |
| **Maintenance** | ✅ Simple | 🐢 Complex |
| **Best For** | OLTP, transactional | OLAP, analytical |

---

## ✅ Real-World Examples

### 🔹 E-commerce Product Catalog

```sql
-- Denormalized for fast searches
CREATE TABLE ProductCatalog (
    product_id INT,
    name VARCHAR(100),
    price DECIMAL(10,2),
    category_name VARCHAR(50),      -- Duplicated
    brand_name VARCHAR(50),         -- Duplicated
    avg_rating DECIMAL(3,2),        -- Precomputed
    total_reviews INT,              -- Precomputed
    stock_status VARCHAR(20)        -- Computed
);
```

### 🔹 Social Media Feed

```sql
-- Denormalized user posts
CREATE TABLE UserPosts (
    post_id INT,
    user_id INT,
    username VARCHAR(50),           -- Duplicated
    user_avatar VARCHAR(200),       -- Duplicated
    post_content TEXT,
    created_at TIMESTAMP,
    likes_count INT,                -- Precomputed
    comments_count INT              -- Precomputed
);
```

### 🔹 Analytics Dashboard

```sql
-- Pre-aggregated metrics
CREATE TABLE DashboardMetrics (
    date DATE,
    total_users INT,
    active_users INT,
    total_revenue DECIMAL(15,2),
    conversion_rate DECIMAL(5,2),
    top_product_name VARCHAR(100)   -- Denormalized
);
```

---

## ✅ Best Practices

### 🔹 1. Normalize First, Denormalize Later

**Start with normalized design, then optimize**

### 🔹 2. Identify Performance Bottlenecks

**Use query profiling to find slow operations**

```sql
-- Identify slow queries
EXPLAIN SELECT * FROM NormalizedTable 
JOIN OtherTable ON condition;

-- Consider denormalization if joins are the bottleneck
```

### 🔹 3. Automate Synchronization

**Use triggers, application code, or ETL processes**

### 🔹 4. Monitor and Maintain

**Regular checks for data consistency**

```sql
-- Data quality checks
SELECT COUNT(*) FROM Products p1
JOIN Products_Denorm p2 ON p1.id = p2.id
WHERE p1.price != p2.price;  -- Should be 0
```

### 🔹 5. Document Denormalizations

**Keep track of why and where denormalization was applied**

---

## 🎯 Interview One-Liner

> Denormalization intentionally adds redundancy to improve read performance by reducing joins, but increases storage and maintenance complexity while risking data inconsistency. Use for read-heavy systems where query speed outweighs data integrity concerns.


## **7. NoSQL DATABASE**

### **NoSQL Basics**
186. What is NoSQL? When to use it?
187. SQL vs NoSQL
188. CAP Theorem
189. BASE vs ACID
190. Types of NoSQL databases

### **MongoDB**
191. What is MongoDB? Features?
192. Document structure in MongoDB
193. BSON vs JSON
194. MongoDB CRUD operations
195. What is Aggregation Pipeline?
196. Indexing in MongoDB
197. Sharding in MongoDB
198. Replication in MongoDB

### **Other NoSQL**
199. What is Redis? Use cases?
200. What is Cassandra? When to use?
201. Key-Value vs Document vs Column-family stores

---

# 186. What is NoSQL? When to use it?

## ✅ What is NoSQL?

**NoSQL** (Not Only SQL) is a database paradigm that provides flexible schema design and horizontal scalability for handling large volumes of unstructured or semi-structured data.

👉 **Break from traditional relational databases** - designed for modern web-scale applications

---

## ✅ NoSQL Characteristics

### 🔹 Schema Flexibility

**Dynamic schema - no fixed table structure**

```javascript
// Traditional SQL: Fixed columns
CREATE TABLE Users (
    id INT,
    name VARCHAR(100),
    email VARCHAR(100),
    age INT
);

// NoSQL: Flexible documents
{
    "_id": "user123",
    "name": "John Doe",
    "email": "john@example.com",
    "age": 30,
    "preferences": {
        "theme": "dark",
        "notifications": true
    },
    "tags": ["developer", "mongodb"]
}
```

### 🔹 Horizontal Scalability

**Scale out by adding more servers**

```javascript
// Add more nodes to handle increased load
// Automatic data distribution across cluster
```

### 🔹 High Performance

**Optimized for specific data models**

- **Key-Value:** Fast lookups by key
- **Document:** Complex queries on structured data
- **Column-Family:** Efficient for analytical queries
- **Graph:** Fast relationship traversals

---

## ✅ When to Use NoSQL

### 🔹 Big Data Applications

**Massive data volumes**

- **Social media platforms:** Billions of posts, users, interactions
- **IoT systems:** Sensor data from millions of devices
- **Analytics platforms:** Processing large datasets

### 🔹 Unstructured/Semi-structured Data

**Data without fixed schema**

```javascript
// Product catalog with varying attributes
{
    "product_id": "P001",
    "name": "Laptop",
    "price": 999.99,
    "specs": {
        "cpu": "Intel i7",
        "ram": "16GB",
        "storage": "512GB SSD"
    },
    "reviews": [
        {"user": "john", "rating": 5, "comment": "Great!"},
        {"user": "jane", "rating": 4, "comment": "Good value"}
    ],
    "tags": ["electronics", "computers"]
}
```

### 🔹 High Write Loads

**Frequent data updates**

- **Real-time analytics:** Continuous data ingestion
- **Logging systems:** High-volume event logging
- **Gaming:** Player statistics, leaderboards

### 🔹 Agile Development

**Rapid schema changes**

- **Startups:** Requirements change frequently
- **Prototyping:** Quick iterations without schema migrations
- **Microservices:** Each service can evolve independently

### 🔹 Distributed Systems

**Geographically distributed data**

- **Global applications:** Data centers across continents
- **CDN integration:** Content delivery networks
- **Edge computing:** Data processing at network edge

---

## ✅ When NOT to Use NoSQL

### 🔹 Complex Relationships

**Heavy use of joins and transactions**

```sql
-- SQL excels at complex relationships
SELECT u.name, o.total, p.name
FROM users u
JOIN orders o ON u.id = o.user_id
JOIN products p ON o.product_id = p.id
WHERE o.status = 'completed';
```

### 🔹 ACID Transactions

**Strong consistency requirements**

- **Financial systems:** Banking, accounting
- **E-commerce:** Order processing with inventory
- **Critical business logic:** Where data accuracy is paramount

### 🔹 Structured Data with Fixed Schema

**Well-defined, unchanging data structure**

- **Enterprise ERP systems**
- **Legacy applications**
- **Regulatory compliance systems**

---

## ✅ NoSQL Use Cases by Type

### 🔹 Key-Value Stores

**Redis, DynamoDB**

- **Session management:** User sessions, shopping carts
- **Caching:** Frequently accessed data
- **Real-time counters:** Likes, views, votes

### 🔹 Document Databases

**MongoDB, CouchDB**

- **Content management:** Articles, blogs, CMS
- **User profiles:** Flexible user data
- **Product catalogs:** E-commerce products

### 🔹 Column-Family Stores

**Cassandra, HBase**

- **Time-series data:** IoT sensors, logs
- **Analytics:** Large-scale data analysis
- **Recommendation systems:** User behavior tracking

### 🔹 Graph Databases

**Neo4j, Amazon Neptune**

- **Social networks:** Friend connections
- **Recommendation engines:** Product relationships
- **Fraud detection:** Transaction pattern analysis

---

## ✅ Migration Strategy

### 🔹 Polyglot Persistence

**Use multiple database types for different needs**

```javascript
// Example architecture:
// - PostgreSQL: User accounts, orders (ACID)
// - MongoDB: Product catalog, reviews (flexible schema)
// - Redis: Session data, caching (high performance)
// - Cassandra: Analytics data (scalability)
```

### 🔹 Gradual Migration

**Start with NoSQL for new features**

```javascript
// Phase 1: New features use NoSQL
// Phase 2: Migrate high-traffic data
// Phase 3: Consider legacy data migration
```

---

## 🎯 Interview One-Liner

> NoSQL databases provide flexible schemas and horizontal scalability for handling large volumes of unstructured data, ideal for big data applications, real-time analytics, and agile development where traditional SQL constraints are limiting.

---

# 187. SQL vs NoSQL

## ✅ Fundamental Differences

**SQL** and **NoSQL** represent different approaches to data storage and retrieval, each optimized for specific use cases.

---

## ✅ Data Model

### 🔹 SQL (Relational)

**Structured data in tables with fixed schema**

```sql
-- Fixed schema with relationships
CREATE TABLE Users (
    user_id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    created_date DATE
);

CREATE TABLE Orders (
    order_id INT PRIMARY KEY,
    user_id INT,
    total DECIMAL(10,2),
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);
```

### 🔹 NoSQL (Non-Relational)

**Flexible data models without fixed schema**

```javascript
// MongoDB document
{
    "_id": ObjectId("507f1f77bcf86cd799439011"),
    "user_id": 123,
    "name": "John Doe",
    "email": "john@example.com",
    "orders": [
        {
            "order_id": 456,
            "total": 299.99,
            "items": ["laptop", "mouse"]
        }
    ],
    "preferences": {
        "theme": "dark",
        "language": "en"
    }
}
```

---

## ✅ Schema

| Aspect | SQL | NoSQL |
|--------|-----|-------|
| **Schema** | Fixed schema | Dynamic schema |
| **Changes** | ALTER TABLE (migration) | No migration needed |
| **Flexibility** | Rigid | Flexible |
| **Validation** | Strict constraints | Application-level |

---

## ✅ Query Language

### 🔹 SQL

**Structured Query Language (declarative)**

```sql
-- Complex queries with joins
SELECT u.name, COUNT(o.order_id) as order_count, SUM(o.total) as total_spent
FROM Users u
LEFT JOIN Orders o ON u.user_id = o.user_id
WHERE u.created_date > '2023-01-01'
GROUP BY u.user_id, u.name
HAVING SUM(o.total) > 100
ORDER BY total_spent DESC;
```

### 🔹 NoSQL

**API-based queries (imperative)**

```javascript
// MongoDB query
db.users.aggregate([
    {
        $match: { created_date: { $gt: new Date('2023-01-01') } }
    },
    {
        $lookup: {
            from: "orders",
            localField: "user_id",
            foreignField: "user_id",
            as: "user_orders"
        }
    },
    {
        $project: {
            name: 1,
            order_count: { $size: "$user_orders" },
            total_spent: { $sum: "$user_orders.total" }
        }
    },
    {
        $match: { total_spent: { $gt: 100 } }
    },
    {
        $sort: { total_spent: -1 }
    }
]);
```

---

## ✅ Scalability

### 🔹 SQL (Vertical Scaling)

**Scale up: Add more power to single server**

```sql
-- Single powerful server
-- Limited by hardware constraints
-- Expensive hardware upgrades
```

### 🔹 NoSQL (Horizontal Scaling)

**Scale out: Add more commodity servers**

```javascript
// Distributed across multiple nodes
// Automatic data partitioning
// Cost-effective scaling
```

---

## ✅ Consistency & Transactions

### 🔹 SQL (ACID)

**Strong consistency and transactions**

```sql
-- Bank transfer: All-or-nothing
BEGIN TRANSACTION;
UPDATE accounts SET balance = balance - 100 WHERE id = 'sender';
UPDATE accounts SET balance = balance + 100 WHERE id = 'receiver';
COMMIT;  -- Both succeed or both fail
```

### 🔹 NoSQL (BASE)

**Eventual consistency**

```javascript
// Eventual consistency: Updates propagate over time
// Immediate consistency not guaranteed
// Better performance and availability
```

---

## ✅ Performance

| Operation | SQL | NoSQL |
|-----------|-----|-------|
| **Complex Joins** | ✅ Excellent | 🐢 Limited |
| **Simple Lookups** | ✅ Good | ⚡ Excellent |
| **Bulk Inserts** | 🐢 Slower | ⚡ Fast |
| **Schema Changes** | 🐢 Requires migration | ⚡ No migration |
| **Large Scale** | 🐢 Vertical limits | ⚡ Horizontal scaling |

---

## ✅ Use Cases

### 🔹 Choose SQL when:

- **Data integrity is critical** (banking, finance)
- **Complex relationships** (joins, foreign keys)
- **Structured data** with fixed schema
- **ACID transactions** required
- **Complex queries** and reporting

### 🔹 Choose NoSQL when:

- **Big data** and high volume
- **Flexible schema** needed
- **High write loads**
- **Horizontal scalability** required
- **Real-time applications**

---

## ✅ Hybrid Approach

**Best of both worlds**

```javascript
// Use SQL for:
// - User accounts and authentication
// - Financial transactions
// - Complex business logic

// Use NoSQL for:
// - User-generated content
// - Analytics and logging
// - Session data and caching
// - Real-time features
```

---

## ✅ Migration Considerations

### 🔹 Data Migration

**Schema transformation challenges**

```javascript
// SQL to NoSQL migration
// Flatten relationships
// Handle data type differences
// Consider denormalization
```

### 🔹 Application Changes

**Query and code modifications**

```javascript
// Rewrite SQL queries to NoSQL operations
// Update ORM/ODM usage
// Handle eventual consistency
```

---

## 🎯 Interview One-Liner

> SQL databases use fixed schemas and ACID transactions for structured data with complex relationships, while NoSQL databases offer flexible schemas and horizontal scalability for handling large volumes of unstructured data with eventual consistency.

---

# 188. CAP Theorem

## ✅ What is CAP Theorem?

**CAP Theorem** states that in a distributed system, you can only guarantee **two out of three** properties simultaneously:

- **C**onsistency
- **A**vailability  
- **P**artition Tolerance

👉 **Impossible to achieve all three** in distributed systems

---

## ✅ The Three Properties

### 🔹 Consistency

**All nodes see the same data at the same time**

```javascript
// Consistent system:
// Write to Node A, immediately visible on Node B
// All replicas have identical data
```

**Example:** Bank balance must be consistent across all ATMs

### 🔹 Availability

**System remains operational despite failures**

```javascript
// Available system:
// Every request receives a response
// System continues to function during failures
```

**Example:** Website stays up even if some servers fail

### 🔹 Partition Tolerance

**System continues to function despite network failures**

```javascript
// Partition-tolerant system:
// Works even when network splits occur
// Nodes can operate independently
```

**Example:** System works during network outages between data centers

---

## ✅ CAP Trade-offs

### 🔹 CA (Consistency + Availability)

**No Partition Tolerance**

- **Traditional RDBMS:** MySQL, PostgreSQL
- **Single data center** systems
- **Fails during network partitions**

### 🔹 CP (Consistency + Partition Tolerance)

**Sacrifice Availability**

- **MongoDB, Redis:** Can become unavailable during partitions
- **Prioritize data correctness** over system availability
- **Banking systems**

### 🔹 AP (Availability + Partition Tolerance)

**Sacrifice Consistency**

- **Cassandra, DynamoDB:** Stay available during partitions
- **Eventual consistency**
- **Social media, e-commerce**

---

## ✅ Real-World Examples

### 🔹 CA Systems

**Traditional databases in single location**

```sql
-- MySQL cluster in one data center
-- Consistent and available within the center
-- But fails if network partition occurs
```

### 🔹 CP Systems

**MongoDB with strict consistency**

```javascript
// MongoDB with majority write concern
// Sacrifices availability during network issues
// Ensures data consistency
```

### 🔹 AP Systems

**Cassandra for high availability**

```javascript
// Cassandra during network partition
// All nodes remain available
// Data may be temporarily inconsistent
// Eventually becomes consistent
```

---

## ✅ CAP in Practice

### 🔹 Network Partitions Are Inevitable

**Distributed systems will experience partitions**

```javascript
// Real-world scenarios:
// - Network cable cut
// - Data center outage
// - Regional network failure
// - Server hardware failure
```

### 🔹 Choosing CAP Properties

**Based on business requirements**

```javascript
// Banking: CP (consistency over availability)
// Social media: AP (availability over immediate consistency)
// E-commerce: Depends on the operation
```

---

## ✅ CAP Extensions

### 🔹 PACELC Theorem

**If Partition (P), choose Availability (A) or Consistency (C); Else (E), choose Latency (L) or Consistency (C)**

```
During Partition → Choose A or C
No Partition → Choose L or C
```

### 🔹 Real-World Systems

| System | CAP Choice | Use Case |
|--------|------------|----------|
| **MongoDB** | CP (configurable) | General purpose |
| **Cassandra** | AP | High availability |
| **Redis** | CP/AP (configurable) | Caching, sessions |
| **DynamoDB** | AP | Global scale |

---

## ✅ CAP Theorem Misconceptions

### 🔹 Myth: "Choose 2 out of 3"

**Reality:** Partition tolerance is mandatory for distributed systems

```javascript
// In distributed systems, you MUST have P
// So you choose between CA, CP, or AP
```

### 🔹 Myth: "CAP means you can't have consistency"

**Reality:** You can have strong consistency, but may sacrifice availability

```javascript
// CP systems provide consistency
// Just not during partitions
```

---

## ✅ Implementing CAP Choices

### 🔹 Consistency (CP Systems)

```javascript
// MongoDB: Wait for majority acknowledgment
db.collection.insertOne(
    { name: "John" },
    { writeConcern: { w: "majority" } }
);
```

### 🔹 Availability (AP Systems)

```javascript
// Cassandra: Tune consistency levels
// Allow reads/writes with fewer replicas
// Accept eventual consistency
```

---

## 🎯 Interview One-Liner

> CAP theorem states that in distributed systems, you can only achieve two of three properties: Consistency (all nodes see same data), Availability (system stays operational), and Partition Tolerance (works despite network failures). Most NoSQL systems choose AP for high availability.

---

# 189. BASE vs ACID

## ✅ ACID Properties

**ACID** ensures reliable database transactions in traditional RDBMS.

### 🔹 Atomicity

**All-or-nothing transactions**

```sql
-- Bank transfer
BEGIN;
UPDATE accounts SET balance = balance - 100 WHERE id = 1;
UPDATE accounts SET balance = balance + 100 WHERE id = 2;
COMMIT;  -- Both succeed or both fail
```

### 🔹 Consistency

**Database remains in valid state**

```sql
-- Balance constraints maintained
-- Referential integrity preserved
```

### 🔹 Isolation

**Transactions don't interfere**

```sql
-- Concurrent transactions isolated
-- No dirty reads, lost updates
```

### 🔹 Durability

**Committed changes survive failures**

```sql
-- Data written to persistent storage
-- Survives system crashes
```

---

## ✅ BASE Properties

**BASE** is the consistency model for NoSQL databases, prioritizing availability over strict consistency.

### 🔹 Basically Available

**System remains operational despite failures**

```javascript
// System stays up during:
// - Network partitions
// - Server failures
// - High load periods
```

### 🔹 Soft State

**State may change over time**

```javascript
// Data can be stale
// Replicas may have different versions
// State is not guaranteed to be consistent
```

### 🔹 Eventually Consistent

**Data becomes consistent over time**

```javascript
// Updates propagate to all replicas
// May take time (seconds, minutes)
// Conflicts resolved automatically or manually
```

---

## ✅ ACID vs BASE Comparison

| Property | ACID | BASE |
|----------|------|------|
| **Consistency** | Strong consistency | Eventual consistency |
| **Availability** | May sacrifice availability | High availability |
| **Transactions** | Multi-statement transactions | Single document operations |
| **Performance** | Slower (locks, coordination) | Faster (optimistic updates) |
| **Scalability** | Vertical scaling | Horizontal scaling |
| **Use Cases** | Financial, critical systems | Big data, real-time apps |

---

## ✅ When to Use ACID

### 🔹 Financial Systems

**Banking, accounting, e-commerce payments**

```sql
-- Money transfers must be atomic
-- Account balances must be consistent
-- Transactions must be durable
```

### 🔹 Critical Business Logic

**Order processing, inventory management**

```sql
-- Order placement with inventory deduction
-- Must succeed or fail completely
```

### 🔹 Regulatory Compliance

**Systems requiring audit trails**

```sql
-- Healthcare records
-- Legal document management
```

---

## ✅ When to Use BASE

### 🔹 High-Volume Applications

**Social media, content platforms**

```javascript
// Facebook likes, Twitter tweets
// Eventual consistency acceptable
// High availability critical
```

### 🔹 Real-Time Systems

**Gaming, live analytics**

```javascript
// Player scores, leaderboards
// Real-time updates more important than perfect consistency
```

### 🔹 Big Data Analytics

**Data warehouses, log analysis**

```javascript
// Batch processing
// Approximate results acceptable
// Focus on throughput over precision
```

---

## ✅ Eventual Consistency Models

### 🔹 Causal Consistency

**Causally related operations consistent**

```javascript
// If A causes B, all nodes see A before B
// Unrelated operations may be inconsistent
```

### 🔹 Read-Your-Writes

**User sees their own writes immediately**

```javascript
// After posting, user sees post immediately
// Other users may see it later
```

### 🔹 Monotonic Reads

**Once seen, data never "goes back"**

```javascript
// If user sees version 2, never sees version 1 again
```

### 🔹 Monotonic Writes

**Writes applied in order**

```javascript
// User's writes applied sequentially
// No out-of-order writes
```

---

## ✅ Conflict Resolution in BASE

### 🔹 Last-Write-Wins (LWW)

**Most recent update wins**

```javascript
// Timestamp-based resolution
// Simple but may lose data
```

### 🔹 Application-Level Resolution

**Custom conflict resolution logic**

```javascript
// Merge strategies:
// - Union sets
// - Sum counters
// - Custom business logic
```

### 🔹 Version Vectors

**Track update history**

```javascript
// Complex conflict resolution
// Preserves all changes
// Application decides resolution
```

---

## ✅ Hybrid Approaches

### 🔹 ACID within BASE

**ACID transactions for critical operations**

```javascript
// MongoDB transactions
// ACID within document boundaries
// BASE across distributed operations
```

### 🔹 Tunable Consistency

**Adjust consistency per operation**

```javascript
// Cassandra consistency levels:
// - ONE: Fast but weak consistency
// - QUORUM: Balanced
// - ALL: Strong consistency
```

---

## ✅ Real-World Examples

### 🔹 ACID: Banking

```sql
-- Transfer money between accounts
-- Must be atomic and consistent
-- Cannot lose or duplicate money
```

### 🔹 BASE: Social Media

```javascript
// User posts a tweet
// Appears immediately for user (read-your-writes)
// Propagates to followers over time
// Like counts eventually consistent
```

### 🔹 Hybrid: E-commerce

```sql
// Product catalog: BASE (eventual consistency)
-- Product views, ratings
// Order processing: ACID (strong consistency)
-- Payment, inventory, shipping
```

---

## 🎯 Interview One-Liner

> ACID provides strong consistency and reliability for critical transactions, while BASE offers high availability and eventual consistency for scalable distributed systems. ACID prioritizes correctness, BASE prioritizes performance and availability.

---

# 190. Types of NoSQL databases

## ✅ Four Main Types of NoSQL Databases

NoSQL databases are categorized based on their data model and storage approach.

---

## ✅ 1. Key-Value Stores

**Simple key-value pairs for high-performance lookups**

### 🔹 Characteristics

- **Data Model:** Key → Value
- **Schema:** None
- **Query:** By key only
- **Performance:** Excellent for simple operations

### 🔹 Examples

**Redis:**
```bash
# Set key-value
SET user:123:name "John Doe"
SET user:123:email "john@example.com"

# Get value
GET user:123:name
# Returns: "John Doe"

# Complex operations
INCR page_views  # Atomic increment
EXPIRE session:abc 3600  # Auto-expire
```

**DynamoDB:**
```javascript
// Simple key-value
{
    "user_id": "123",
    "data": {
        "name": "John",
        "email": "john@example.com",
        "preferences": {...}
    }
}
```

### 🔹 Use Cases

- **Caching:** Session data, user preferences
- **Real-time counters:** Likes, views, votes
- **Configuration:** Feature flags, settings
- **Rate limiting:** API call limits

---

## ✅ 2. Document Stores

**JSON-like documents with flexible schema**

### 🔹 Characteristics

- **Data Model:** Collections of documents
- **Schema:** Flexible per document
- **Query:** Rich queries on document fields
- **Indexing:** Multiple indexes supported

### 🔹 Examples

**MongoDB:**
```javascript
// User document
{
    "_id": ObjectId("507f1f77bcf86cd799439011"),
    "user_id": 123,
    "name": "John Doe",
    "email": "john@example.com",
    "profile": {
        "age": 30,
        "interests": ["coding", "music"]
    },
    "orders": [
        {
            "order_id": 456,
            "total": 299.99,
            "items": ["laptop", "mouse"]
        }
    ]
}

// Query examples
db.users.find({ "profile.age": { $gte: 25 } })
db.users.find({ "orders.total": { $gt: 200 } })
```

### 🔹 Use Cases

- **Content management:** Blogs, CMS, articles
- **User profiles:** Flexible user data
- **Product catalogs:** E-commerce products
- **Analytics:** Event tracking, logs

---

## ✅ 3. Column-Family Stores

**Tables with rows and dynamic columns**

### 🔹 Characteristics

- **Data Model:** Rows with column families
- **Schema:** Flexible columns per row
- **Query:** Efficient for column-based queries
- **Scalability:** Excellent horizontal scaling

### 🔹 Examples

**Cassandra:**
```sql
-- Create table
CREATE TABLE user_events (
    user_id UUID,
    event_time TIMESTAMP,
    event_type TEXT,
    event_data MAP<TEXT, TEXT>,
    PRIMARY KEY (user_id, event_time)
);

// Insert data
INSERT INTO user_events (user_id, event_time, event_type, event_data)
VALUES (uuid(), '2024-01-01 10:00:00', 'login', {'ip': '192.168.1.1'});

// Query by user and time range
SELECT * FROM user_events 
WHERE user_id = ? 
AND event_time > ? AND event_time < ?;
```

### 🔹 Use Cases

- **Time-series data:** IoT sensors, logs
- **Analytics:** Large-scale data analysis
- **Recommendation systems:** User behavior tracking
- **Messaging:** Chat history, notifications

---

## ✅ 4. Graph Databases

**Nodes and relationships for connected data**

### 🔹 Characteristics

- **Data Model:** Nodes, relationships, properties
- **Schema:** Flexible graph structure
- **Query:** Graph traversal queries
- **Performance:** Fast relationship queries

### 🔹 Examples

**Neo4j (Cypher query language):**
```cypher
// Create nodes and relationships
CREATE (john:Person {name: "John", age: 30})
CREATE (jane:Person {name: "Jane", age: 28})
CREATE (john)-[:FRIENDS_WITH]->(jane)
CREATE (john)-[:WORKS_AT]->(company:Company {name: "TechCorp"})

// Query relationships
MATCH (person:Person)-[:FRIENDS_WITH]->(friend:Person)
WHERE person.name = "John"
RETURN friend.name

// Find connections
MATCH (john:Person {name: "John"})-[:FRIENDS_WITH*2]->(friend_of_friend)
RETURN friend_of_friend.name
```

### 🔹 Use Cases

- **Social networks:** Friend connections, recommendations
- **Fraud detection:** Transaction pattern analysis
- **Knowledge graphs:** Entity relationships
- **Route planning:** Transportation networks

---

## ✅ Comparison Table

| Type | Data Model | Query Style | Scalability | Use Cases | Examples |
|------|------------|-------------|-------------|-----------|----------|
| **Key-Value** | Key → Value | Key lookup | Excellent | Caching, sessions | Redis, DynamoDB |
| **Document** | JSON documents | Field queries | Good | Content, profiles | MongoDB, CouchDB |
| **Column-Family** | Rows/columns | Column scans | Excellent | Analytics, IoT | Cassandra, HBase |
| **Graph** | Nodes/edges | Graph traversal | Good | Relationships | Neo4j, Neptune |

---

## ✅ Choosing the Right Type

### 🔹 Data Structure

```javascript
// Simple key-value → Key-Value store
// Complex nested objects → Document store
// Time-series columns → Column-Family store
// Highly connected data → Graph database
```

### 🔹 Query Patterns

```javascript
// Primary key lookups → Key-Value
// Complex field queries → Document
// Range scans on columns → Column-Family
// Relationship traversals → Graph
```

### 🔹 Scalability Needs

```javascript
// Massive scale → Column-Family (Cassandra)
// Global distribution → Key-Value (DynamoDB)
// Complex relationships → Graph
// Flexible schemas → Document
```

---

## ✅ Multi-Model Databases

**Support multiple data models in one database**

### 🔹 Examples

**ArangoDB:** Document + Graph
**OrientDB:** Document + Graph + Key-Value
**Couchbase:** Document + Key-Value

```javascript
// One database, multiple models
// Documents for user data
// Graph for relationships
// Key-Value for sessions
```

---

## 🎯 Interview One-Liner

> NoSQL databases include Key-Value stores (Redis) for simple lookups, Document stores (MongoDB) for flexible JSON data, Column-Family stores (Cassandra) for scalable analytics, and Graph databases (Neo4j) for relationship-heavy data.

---

# 191. What is MongoDB? Features?

## ✅ What is MongoDB?

**MongoDB** is a popular open-source document-oriented NoSQL database designed for scalability, flexibility, and high performance.

👉 **Leading NoSQL database** - stores data as JSON-like documents

---

## ✅ Core Features

### 🔹 Document-Oriented Storage

**Data stored as BSON documents (binary JSON)**

```javascript
// User document example
{
    "_id": ObjectId("507f1f77bcf86cd799439011"),
    "username": "johndoe",
    "email": "john@example.com",
    "profile": {
        "firstName": "John",
        "lastName": "Doe",
        "age": 30
    },
    "preferences": {
        "theme": "dark",
        "notifications": true
    },
    "tags": ["developer", "mongodb"],
    "createdAt": ISODate("2024-01-01T00:00:00Z")
}
```

### 🔹 Flexible Schema

**No fixed schema - documents can have different fields**

```javascript
// Different user documents
{
    "_id": 1,
    "type": "premium",
    "name": "John",
    "subscription": {
        "plan": "gold",
        "expires": "2024-12-31"
    }
}

{
    "_id": 2,
    "type": "free",
    "name": "Jane",
    "trialEnds": "2024-02-01"
}
```

### 🔹 Rich Query Language

**Powerful queries with operators and aggregation**

```javascript
// Find users with complex criteria
db.users.find({
    "profile.age": { $gte: 25, $lte: 35 },
    "tags": { $in: ["developer", "designer"] },
    "createdAt": { $gte: new Date('2024-01-01') }
})

// Update with conditions
db.users.updateMany(
    { "type": "free" },
    { $set: { "trialExtended": true } }
)
```

---

## ✅ Advanced Features

### 🔹 Indexing

**Multiple index types for performance**

```javascript
// Single field index
db.users.createIndex({ "email": 1 })

// Compound index
db.users.createIndex({ "profile.age": 1, "profile.city": 1 })

// Text index for search
db.users.createIndex({ "name": "text", "bio": "text" })

// Geospatial index
db.users.createIndex({ "location": "2dsphere" })
```

### 🔹 Aggregation Framework

**Data processing pipelines**

```javascript
// Complex analytics
db.orders.aggregate([
    {
        $match: { "status": "completed" }
    },
    {
        $group: {
            _id: "$customer_id",
            totalSpent: { $sum: "$total" },
            orderCount: { $sum: 1 },
            avgOrderValue: { $avg: "$total" }
        }
    },
    {
        $sort: { "totalSpent": -1 }
    },
    {
        $limit: 10
    }
])
```

### 🔹 Replication

**Data redundancy and high availability**

```javascript
// Replica set configuration
// Primary + secondary nodes
// Automatic failover
// Read from secondaries for scaling
```

### 🔹 Sharding

**Horizontal scaling across multiple servers**

```javascript
// Shard key: distribute data
// Config servers: metadata
// Mongos routers: query routing
// Auto-balancing
```

---

## ✅ MongoDB Ecosystem

### 🔹 MongoDB Atlas

**Cloud-hosted MongoDB service**

```javascript
// Fully managed
// Global clusters
// Auto-scaling
// Built-in security
```

### 🔹 MongoDB Compass

**GUI for database management**

```javascript
// Visual query builder
// Schema analysis
// Performance monitoring
// Data import/export
```

### 🔹 Drivers and Libraries

**Official drivers for all languages**

```javascript
// Node.js
const { MongoClient } = require('mongodb');
const client = new MongoClient(uri);

// Java
MongoClient mongoClient = MongoClients.create(connectionString);

// Python
from pymongo import MongoClient
client = MongoClient()
```

---

## ✅ MongoDB Use Cases

### 🔹 Content Management Systems

**Flexible content structures**

```javascript
// Articles with varying metadata
{
    "title": "MongoDB Guide",
    "content": "...",
    "author": "John",
    "tags": ["nosql", "database"],
    "published": true,
    "seo": {
        "keywords": ["mongodb", "tutorial"],
        "description": "..."
    }
}
```

### 🔹 Real-Time Analytics

**Fast aggregations on large datasets**

```javascript
// User behavior tracking
{
    "user_id": 123,
    "event": "page_view",
    "page": "/products",
    "timestamp": ISODate(),
    "user_agent": "...",
    "ip": "192.168.1.1"
}
```

### 🔹 IoT Applications

**Sensor data with varying schemas**

```javascript
// Different device types
{
    "device_id": "sensor_001",
    "type": "temperature",
    "value": 23.5,
    "unit": "celsius",
    "location": "room_1",
    "battery": 85
}

{
    "device_id": "sensor_002", 
    "type": "motion",
    "detected": true,
    "sensitivity": "high",
    "last_motion": ISODate()
}
```

---

## ✅ MongoDB Limitations

### 🔹 ACID Transactions

**Limited to single document by default**

```javascript
// Multi-document transactions (4.0+)
const session = client.startSession();
session.startTransaction();

try {
    // Multiple operations
    await collection1.updateOne({...}, session);
    await collection2.insertOne({...}, session);
    await session.commitTransaction();
} catch (error) {
    await session.abortTransaction();
}
```

### 🔹 Joins

**No traditional SQL joins**

```javascript
// $lookup aggregation for joins
db.orders.aggregate([
    {
        $lookup: {
            from: "customers",
            localField: "customer_id",
            foreignField: "_id",
            as: "customer"
        }
    }
])
```

### 🔹 Data Consistency

**Eventual consistency in distributed setups**

---

## ✅ MongoDB vs Other Databases

| Feature | MongoDB | PostgreSQL | Cassandra |
|---------|---------|------------|-----------|
| **Data Model** | Document | Relational | Column-family |
| **Schema** | Flexible | Fixed | Flexible |
| **Scaling** | Good | Limited | Excellent |
| **Consistency** | Configurable | Strong | Eventual |
| **Query Language** | Rich | SQL | CQL |

---

## 🎯 Interview One-Liner

> MongoDB is a document-oriented NoSQL database that stores data as flexible JSON-like documents, offering rich queries, indexing, aggregation, and horizontal scaling through sharding and replication for modern applications.

---

# 192. Document structure in MongoDB

## ✅ MongoDB Document Structure

**Documents** are the basic unit of data in MongoDB, stored as **BSON** (Binary JSON) objects with flexible schemas.

---

## ✅ Document Components

### 🔹 Fields and Values

**Key-value pairs with various data types**

```javascript
{
    "_id": ObjectId("507f1f77bcf86cd799439011"),  // Unique identifier
    "name": "John Doe",                             // String
    "age": 30,                                       // Number
    "isActive": true,                                // Boolean
    "score": 95.5,                                   // Float
    "tags": ["developer", "mongodb"],                // Array
    "address": {                                     // Embedded document
        "street": "123 Main St",
        "city": "New York",
        "zip": "10001"
    },
    "createdAt": ISODate("2024-01-01T00:00:00Z"),    // Date
    "metadata": {                                    // Nested object
        "version": 1,
        "source": "web"
    }
}
```

### 🔹 Field Names

**Rules for field names:**

- **String type** (UTF-8)
- **No null characters** (\0)
- **No dollar sign** ($) at start (reserved)
- **No dot** (.) in field names (reserved for nested access)
- **Case sensitive**

---

## ✅ Data Types

### 🔹 Primitive Types

```javascript
{
    "string": "Hello World",
    "integer": 42,
    "double": 3.14159,
    "boolean": true,
    "null": null
}
```

### 🔹 Date and Time

```javascript
{
    "createdAt": ISODate("2024-01-01T10:30:00Z"),
    "timestamp": Timestamp(1640995200, 1),  // Internal MongoDB timestamp
    "dateOnly": new Date("2024-01-01")      // JavaScript Date object
}
```

### 🔹 Arrays

```javascript
{
    "tags": ["mongodb", "nosql", "database"],
    "scores": [85, 92, 78, 96],
    "coordinates": [40.7128, -74.0060],    // [longitude, latitude]
    "mixed": ["string", 42, true, null]
}
```

### 🔹 Embedded Documents

```javascript
{
    "user": {
        "name": "John",
        "email": "john@example.com"
    },
    "address": {
        "street": "123 Main St",
        "city": "New York",
        "coordinates": {
            "lat": 40.7128,
            "lng": -74.0060
        }
    }
}
```

### 🔹 Special Types

```javascript
{
    "_id": ObjectId("507f1f77bcf86cd799439011"),  // 12-byte identifier
    "binaryData": BinData(0, "SGVsbG8gV29ybGQ="),   // Binary data
    "regex": /pattern/i,                             // Regular expression
    "javascript": Code("function() { return true; }"), // JavaScript code
    "uuid": UUID("550e8400-e29b-41d4-a716-446655440000") // UUID
}
```

---

## ✅ Document Size Limits

### 🔹 16MB Document Limit

**Maximum BSON document size**

```javascript
// Large documents split into chunks
// GridFS for files > 16MB
// Design for efficient access patterns
```

### 🔹 Field Name Length

**Field names stored in each document**

```javascript
// Shorter field names save space
{
    "fn": "John",     // Instead of "firstName"
    "ln": "Doe",      // Instead of "lastName"
    "a": 30           // Instead of "age"
}
```

---

## ✅ Document Design Patterns

### 🔹 Embedded Documents

**One-to-One or One-to-Few relationships**

```javascript
// User with embedded profile
{
    "_id": ObjectId(),
    "username": "johndoe",
    "profile": {
        "firstName": "John",
        "lastName": "Doe",
        "bio": "Developer",
        "socialLinks": {
            "twitter": "@johndoe",
            "github": "johndoe"
        }
    }
}
```

### 🔹 References

**One-to-Many or Many-to-Many relationships**

```javascript
// User document
{
    "_id": ObjectId(),
    "username": "johndoe",
    "posts": [
        ObjectId("post1"),
        ObjectId("post2"),
        ObjectId("post3")
    ]
}

// Post document
{
    "_id": ObjectId("post1"),
    "title": "MongoDB Guide",
    "content": "...",
    "author_id": ObjectId()  // Reference to user
}
```

### 🔹 Arrays for Multi-Values

**Store multiple values in single field**

```javascript
{
    "product": "Laptop",
    "categories": ["electronics", "computers", "gaming"],
    "reviews": [
        {
            "user": "john",
            "rating": 5,
            "comment": "Great performance"
        },
        {
            "user": "jane", 
            "rating": 4,
            "comment": "Good value"
        }
    ]
}
```

---

## ✅ Schema Design Considerations

### 🔹 Read vs Write Patterns

**Design for common access patterns**

```javascript
// If you frequently need user + posts together
// Consider embedding posts in user document
// Trade-off: duplication vs. join performance
```

### 🔹 Data Consistency

**Embedded vs Referenced documents**

```javascript
// Embedded: Strong consistency, atomic updates
// Referenced: Flexible, normalized, requires joins
```

### 🔹 Document Growth

**Consider how documents will grow**

```javascript
// Avoid unbounded arrays
// Use references for growing relationships
// Plan for document size limits
```

---

## ✅ Document Validation

### 🔹 Schema Validation

**JSON Schema for document validation**

```javascript
// Collection validation rules
db.createCollection("users", {
    validator: {
        $jsonSchema: {
            bsonType: "object",
            required: ["name", "email"],
            properties: {
                name: {
                    bsonType: "string",
                    description: "Name is required and must be a string"
                },
                email: {
                    bsonType: "string",
                    pattern: "^.+@.+$",
                    description: "Email must be valid format"
                },
                age: {
                    bsonType: "int",
                    minimum: 0,
                    maximum: 120
                }
            }
        }
    }
})
```

---

## ✅ Best Practices

### 🔹 Field Naming Conventions

```javascript
// Consistent naming
{
    "firstName": "John",    // camelCase
    "lastName": "Doe",
    "emailAddress": "john@example.com",
    "phoneNumber": "555-0123"
}

// Or snake_case
{
    "first_name": "John",
    "last_name": "Doe", 
    "email_address": "john@example.com"
}
```

### 🔹 Avoid Reserved Words

```javascript
// Don't use MongoDB reserved words as field names
// Avoid: _id, $ prefixed fields, etc.
```

### 🔹 Index Field Order

**Consider index usage in queries**

```javascript
// Query pattern determines index design
db.collection.find({ "status": "active", "priority": "high" })

// Index should match query order
db.collection.createIndex({ "status": 1, "priority": 1 })
```

---

## 🎯 Interview One-Liner

> MongoDB documents are flexible JSON-like structures stored as BSON, containing fields with various data types including embedded documents and arrays, with a 16MB size limit and support for schema validation.

---

# 193. BSON vs JSON

## ✅ What is JSON?

**JSON (JavaScript Object Notation)** is a lightweight text-based data interchange format.

```json
{
    "name": "John Doe",
    "age": 30,
    "isActive": true,
    "tags": ["developer", "mongodb"],
    "address": {
        "street": "123 Main St",
        "city": "New York"
    }
}
```

---

## ✅ What is BSON?

**BSON (Binary JSON)** is MongoDB's binary representation of JSON-like documents.

👉 **Binary-encoded serialization** of JSON documents

---

## ✅ Key Differences

| Feature | JSON | BSON |
|---------|------|------|
| **Format** | Text-based | Binary |
| **Size** | Larger (verbose) | Smaller (compact) |
| **Parsing** | Slower | Faster |
| **Data Types** | Limited | Rich (dates, ObjectId, etc.) |
| **Traversal** | Requires parsing | Direct binary access |
| **Human Readable** | ✅ Yes | ❌ No |

---

## ✅ BSON Advantages

### 🔹 Compact Storage

**More efficient than JSON**

```javascript
// JSON representation
{"name": "John", "age": 30, "active": true}
// ~40 bytes

// BSON representation  
// Binary encoded, compressed
// ~30 bytes (25% smaller)
```

### 🔹 Rich Data Types

**Additional data types beyond JSON**

```javascript
// BSON-specific types
{
    "_id": ObjectId("507f1f77bcf86cd799439011"),  // 12-byte identifier
    "createdAt": ISODate("2024-01-01T00:00:00Z"),   // Date type
    "binaryData": BinData(0, "SGVsbG8="),           // Binary data
    "regex": /pattern/i,                            // Regular expression
    "timestamp": Timestamp(1640995200, 1)           // Internal timestamp
}
```

### 🔹 Fast Traversal

**Direct binary access without parsing**

```javascript
// JSON: Parse entire string to access field
const json = '{"name": "John", "age": 30}';
const obj = JSON.parse(json);
console.log(obj.age);  // Parse whole string first

// BSON: Direct binary access to field
// No parsing required, faster random access
```

### 🔹 Efficient Updates

**In-place updates possible**

```javascript
// BSON allows updating specific parts
// Without rewriting entire document
// Better performance for partial updates
```

---

## ✅ BSON Data Types

### 🔹 Standard JSON Types

```javascript
{
    "string": "Hello",
    "number": 42,
    "boolean": true,
    "null": null,
    "array": [1, 2, 3],
    "object": {"key": "value"}
}
```

### 🔹 BSON Extended Types

```javascript
{
    "objectId": ObjectId("507f1f77bcf86cd799439011"),
    "date": ISODate("2024-01-01T00:00:00Z"),
    "timestamp": Timestamp(1640995200, 1),
    "binary": BinData(0, "SGVsbG8="),
    "regex": /pattern/i,
    "javascript": Code("function() { return true; }"),
    "int32": NumberInt(42),
    "int64": NumberLong(9223372036854775807),
    "decimal": NumberDecimal("123.456")
}
```

---

## ✅ When JSON is Used

### 🔹 External Communication

**APIs, configuration files, data exchange**

```javascript
// REST API responses
{
    "status": "success",
    "data": {
        "user": {
            "id": "123",
            "name": "John"
        }
    }
}

// Configuration files
{
    "database": {
        "host": "localhost",
        "port": 27017
    }
}
```

### 🔹 Development Tools

**MongoDB Compass, debugging, logging**

```javascript
// MongoDB shell output
db.users.findOne()
// Returns JSON for readability
```

---

## ✅ When BSON is Used

### 🔹 Internal Storage

**MongoDB database files, indexes, network transfer**

```javascript
// Documents stored as BSON on disk
// Indexes use BSON for efficient storage
// Network communication between MongoDB nodes
```

### 🔹 Performance Critical Operations

**Queries, updates, aggregations**

```javascript
// Query execution on BSON is faster
// Index lookups use BSON comparison
// Aggregation pipelines process BSON directly
```

---

## ✅ Conversion Between JSON and BSON

### 🔹 JSON to BSON

**Serialization when storing data**

```javascript
// Application sends JSON
const userJson = '{"name": "John", "age": 30}';

// MongoDB driver converts to BSON
// Stores as binary BSON document
```

### 🔹 BSON to JSON

**Deserialization when retrieving data**

```javascript
// MongoDB returns BSON
// Driver converts to JSON for application
// Dates become ISO strings, ObjectIds become hex strings
```

---

## ✅ BSON Limitations

### 🔹 Not Human Readable

**Cannot directly read BSON files**

```bash
# Need tools to convert BSON to JSON
mongodump --db test --out /tmp/backup  # BSON files
mongoexport --db test --collection users --out users.json  # JSON export
```

### 🔹 Language Specific

**BSON libraries needed for each language**

```javascript
// Node.js
const bson = require('bson');
const doc = bson.deserialize(buffer);

// Python
import bson
doc = bson.loads(bson_data)
```

---

## ✅ Practical Examples

### 🔹 Storage Comparison

```javascript
// Same document in JSON vs BSON
const doc = {
    "_id": ObjectId("507f1f77bcf86cd799439011"),
    "name": "John Doe",
    "email": "john@example.com",
    "createdAt": new Date(),
    "tags": ["developer", "mongodb"],
    "metadata": {
        "version": 1,
        "source": "web"
    }
};

// JSON: ~250 bytes (text)
// BSON: ~200 bytes (binary) - 20% smaller
```

### 🔹 Performance Impact

```javascript
// Query performance
// BSON: Direct field access
// JSON: Parse entire string first

// Network transfer
// BSON: Compact binary format
// JSON: Verbose text format
```

---

## 🎯 Interview One-Liner

> BSON is MongoDB's binary JSON format that is more compact, faster to parse, and supports richer data types than JSON, while JSON is human-readable text format used for APIs and configuration.

---

# 194. MongoDB CRUD operations

## ✅ CRUD Operations in MongoDB

**CRUD** stands for Create, Read, Update, Delete - the four basic operations for managing data.

---

## ✅ Create Operations

### 🔹 Insert Single Document

```javascript
// Insert one document
db.users.insertOne({
    name: "John Doe",
    email: "john@example.com",
    age: 30,
    createdAt: new Date()
});

// Result: { "acknowledged": true, "insertedId": ObjectId("...") }
```

### 🔹 Insert Multiple Documents

```javascript
// Insert many documents
db.users.insertMany([
    {
        name: "Jane Smith",
        email: "jane@example.com",
        age: 28
    },
    {
        name: "Bob Johnson",
        email: "bob@example.com", 
        age: 35
    }
]);

// Result: { "acknowledged": true, "insertedIds": [...] }
```

### 🔹 Upsert Operation

**Insert or update if exists**

```javascript
// Insert or update based on filter
db.users.updateOne(
    { email: "john@example.com" },  // Filter
    { 
        $set: { name: "John Doe", age: 31 },
        $setOnInsert: { createdAt: new Date() }
    },
    { upsert: true }  // Create if doesn't exist
);
```

---

## ✅ Read Operations

### 🔹 Find Single Document

```javascript
// Find one document
const user = db.users.findOne({ email: "john@example.com" });

// Find by ID
const user = db.users.findOne({ _id: ObjectId("507f1f77bcf86cd799439011") });
```

### 🔹 Find Multiple Documents

```javascript
// Find all users
db.users.find();

// Find with conditions
db.users.find({ age: { $gte: 25 } });

// Find with multiple conditions
db.users.find({
    age: { $gte: 25, $lte: 35 },
    city: "New York"
});
```

### 🔹 Query Operators

```javascript
// Comparison operators
db.users.find({ age: { $gt: 25, $lt: 35 } });  // > 25 AND < 35
db.users.find({ age: { $in: [25, 30, 35] } }); // IN array

// Logical operators
db.users.find({ 
    $or: [
        { age: { $lt: 25 } },
        { age: { $gt: 35 } }
    ]
});

// Element operators
db.users.find({ tags: { $exists: true } });     // Field exists
db.users.find({ tags: { $size: 3 } });          // Array size
db.users.find({ tags: { $all: ["mongodb", "nosql"] } }); // Contains all
```

### 🔹 Projection

**Select specific fields**

```javascript
// Include only name and email
db.users.find({}, { name: 1, email: 1 });

// Exclude age field
db.users.find({}, { age: 0 });

// Include name, exclude _id
db.users.find({}, { name: 1, _id: 0 });
```

### 🔹 Sorting and Limiting

```javascript
// Sort by age ascending
db.users.find().sort({ age: 1 });

// Sort by age descending, then name ascending
db.users.find().sort({ age: -1, name: 1 });

// Limit results
db.users.find().limit(10);

// Skip and limit (pagination)
db.users.find().skip(20).limit(10);
```

---

## ✅ Update Operations

### 🔹 Update Single Document

```javascript
// Update one document
db.users.updateOne(
    { email: "john@example.com" },  // Filter
    { $set: { age: 31, city: "Boston" } }  // Update
);

// Result: { "acknowledged": true, "matchedCount": 1, "modifiedCount": 1 }
```

### 🔹 Update Multiple Documents

```javascript
// Update all matching documents
db.users.updateMany(
    { city: "New York" },
    { $set: { state: "NY" } }
);
```

### 🔹 Update Operators

```javascript
// $set - Set field value
db.users.updateOne({ _id: userId }, { $set: { status: "active" } });

// $unset - Remove field
db.users.updateOne({ _id: userId }, { $unset: { tempField: 1 } });

// $inc - Increment numeric value
db.users.updateOne({ _id: userId }, { $inc: { loginCount: 1 } });

// $push - Add to array
db.users.updateOne({ _id: userId }, { $push: { tags: "newbie" } });

// $pull - Remove from array
db.users.updateOne({ _id: userId }, { $pull: { tags: "inactive" } });

// $addToSet - Add to array if not exists
db.users.updateOne({ _id: userId }, { $addToSet: { tags: "premium" } });
```

### 🔹 Replace Document

```javascript
// Replace entire document (except _id)
db.users.replaceOne(
    { email: "john@example.com" },
    {
        name: "John Smith",
        email: "johnsmith@example.com",
        age: 32,
        department: "Engineering"
    }
);
```

---

## ✅ Delete Operations

### 🔹 Delete Single Document

```javascript
// Delete one document
db.users.deleteOne({ email: "inactive@example.com" });

// Result: { "acknowledged": true, "deletedCount": 1 }
```

### 🔹 Delete Multiple Documents

```javascript
// Delete all matching documents
db.users.deleteMany({ status: "inactive" });

// Delete all documents in collection
db.users.deleteMany({});
```

### 🔹 Drop Collection

```javascript
// Remove entire collection
db.users.drop();
```

---

## ✅ Advanced CRUD Patterns

### 🔹 Bulk Operations

```javascript
// Perform multiple operations atomically
const bulk = db.users.initializeBulkOp();

bulk.insert({ name: "Alice", email: "alice@example.com" });
bulk.find({ email: "john@example.com" }).updateOne({ $set: { age: 31 } });
bulk.find({ status: "inactive" }).delete();

bulk.execute();
```

### 🔹 Transactions (MongoDB 4.0+)

```javascript
// Multi-document transactions
const session = db.getMongo().startSession();

session.startTransaction();

try {
    // Multiple operations
    db.accounts.updateOne(
        { _id: fromAccount },
        { $inc: { balance: -amount } },
        { session }
    );
    
    db.accounts.updateOne(
        { _id: toAccount },
        { $inc: { balance: amount } },
        { session }
    );
    
    // Log transaction
    db.transactions.insertOne({
        from: fromAccount,
        to: toAccount,
        amount: amount,
        timestamp: new Date()
    }, { session });
    
    session.commitTransaction();
} catch (error) {
    session.abortTransaction();
} finally {
    session.endSession();
}
```

---

## ✅ Error Handling

### 🔹 Write Concern

**Control write acknowledgment**

```javascript
// Wait for majority acknowledgment
db.users.insertOne(
    { name: "John", email: "john@example.com" },
    { writeConcern: { w: "majority", wtimeout: 5000 } }
);
```

### 🔹 Error Types

```javascript
try {
    db.users.insertOne({ _id: existingId, name: "Duplicate" });
} catch (error) {
    if (error.code === 11000) {
        console.log("Duplicate key error");
    }
}
```

---

## ✅ Performance Considerations

### 🔹 Indexes for Queries

```javascript
// Create index for frequent queries
db.users.createIndex({ email: 1 });  // For email lookups
db.users.createIndex({ age: 1, city: 1 });  // For age+city queries
```

### 🔹 Query Optimization

```javascript
// Use explain() to analyze queries
db.users.find({ age: { $gt: 25 } }).explain("executionStats");

// Check index usage
db.users.find({ email: "john@example.com" }).explain();
```

---

## 🎯 Interview One-Liner

> MongoDB CRUD operations include insertOne/insertMany for creation, find/findOne with query operators for reading, updateOne/updateMany with $set/$inc operators for updates, and deleteOne/deleteMany for deletion, all supporting rich query conditions and bulk operations.

---

# 195. What is Aggregation Pipeline?

## ✅ What is Aggregation Pipeline?

**Aggregation Pipeline** is MongoDB's framework for data aggregation, transformation, and analysis using a sequence of stages.

👉 **Process documents through multiple stages** to produce aggregated results

---

## ✅ Pipeline Structure

**Series of stages that process documents**

```javascript
db.collection.aggregate([
    // Stage 1
    { $stage1: { ... } },
    
    // Stage 2  
    { $stage2: { ... } },
    
    // Stage 3
    { $stage3: { ... } }
])
```

---

## ✅ Key Stages

### 🔹 $match - Filter Documents

**Filter documents (like find())**

```javascript
db.orders.aggregate([
    {
        $match: {
            status: "completed",
            total: { $gte: 100 }
        }
    }
])
```

### 🔹 $group - Group Documents

**Group by fields and compute aggregates**

```javascript
db.orders.aggregate([
    {
        $group: {
            _id: "$customer_id",
            totalOrders: { $sum: 1 },
            totalSpent: { $sum: "$total" },
            avgOrderValue: { $avg: "$total" },
            maxOrder: { $max: "$total" },
            minOrder: { $min: "$total" }
        }
    }
])
```

### 🔹 $project - Reshape Documents

**Include/exclude/rename/compute fields**

```javascript
db.users.aggregate([
    {
        $project: {
            _id: 0,  // Exclude _id
            fullName: { $concat: ["$firstName", " ", "$lastName"] },
            age: 1,
            yearBorn: { $subtract: [new Date().getFullYear(), "$age"] }
        }
    }
])
```

### 🔹 $sort - Sort Documents

**Sort by fields**

```javascript
db.products.aggregate([
    { $sort: { price: -1, name: 1 } }  // Price desc, name asc
])
```

### 🔹 $limit - Limit Results

**Limit number of documents**

```javascript
db.products.aggregate([
    { $sort: { rating: -1 } },
    { $limit: 10 }  // Top 10 products
])
```

### 🔹 $skip - Skip Documents

**Skip documents (pagination)**

```javascript
db.products.aggregate([
    { $sort: { price: 1 } },
    { $skip: 20 },
    { $limit: 10 }  // Page 3 (21-30)
])
```

---

## ✅ Advanced Stages

### 🔹 $lookup - Join Collections

**Left outer join with another collection**

```javascript
db.orders.aggregate([
    {
        $lookup: {
            from: "customers",
            localField: "customer_id",
            foreignField: "_id",
            as: "customer"
        }
    },
    {
        $unwind: "$customer"  // Convert array to object
    }
])
```

### 🔹 $unwind - Deconstruct Arrays

**Create separate document for each array element**

```javascript
// Document: { tags: ["mongodb", "nosql", "database"] }
db.articles.aggregate([
    { $unwind: "$tags" }
])
// Results in 3 documents, one for each tag
```

### 🔹 $addFields - Add New Fields

**Add computed fields**

```javascript
db.products.aggregate([
    {
        $addFields: {
            discountedPrice: { $multiply: ["$price", 0.9] },
            isExpensive: { $gte: ["$price", 100] }
        }
    }
])
```

### 🔹 $facet - Multiple Pipelines

**Run multiple aggregation pipelines**

```javascript
db.products.aggregate([
    {
        $facet: {
            "priceRanges": [
                { $match: { price: { $lt: 50 } } },
                { $count: "cheap" }
            ],
            "categories": [
                { $group: { _id: "$category", count: { $sum: 1 } } }
            ]
        }
    }
])
```

---

## ✅ Expression Operators

### 🔹 Arithmetic Operators

```javascript
{
    $add: [10, 5],           // 15
    $subtract: [10, 5],       // 5
    $multiply: [10, 2],       // 20
    $divide: [10, 2],         // 5
    $mod: [10, 3]             // 1
}
```

### 🔹 Comparison Operators

```javascript
{
    $eq: ["$field", "value"],     // Equal
    $ne: ["$field", "value"],     // Not equal
    $gt: ["$field", 10],          // Greater than
    $gte: ["$field", 10],         // Greater or equal
    $lt: ["$field", 10],          // Less than
    $lte: ["$field", 10]          // Less or equal
}
```

### 🔹 String Operators

```javascript
{
    $concat: ["$firstName", " ", "$lastName"],  // Concatenate
    $substr: ["$name", 0, 3],                   // Substring
    $toLower: "$name",                           // Lowercase
    $toUpper: "$name"                            // Uppercase
}
```

### 🔹 Array Operators

```javascript
{
    $size: "$tags",                    // Array size
    $in: ["mongodb", "$tags"],         // Value in array
    $push: "$newTag",                  // Add to array
    $pull: "oldTag"                    // Remove from array
}
```

---

## ✅ Real-World Examples

### 🔹 E-commerce Analytics

```javascript
db.orders.aggregate([
    // Filter completed orders
    { $match: { status: "completed" } },
    
    // Join with customers
    {
        $lookup: {
            from: "customers",
            localField: "customer_id", 
            foreignField: "_id",
            as: "customer"
        }
    },
    
    // Group by customer
    {
        $group: {
            _id: "$customer_id",
            customerName: { $first: "$customer.name" },
            totalOrders: { $sum: 1 },
            totalSpent: { $sum: "$total" },
            avgOrderValue: { $avg: "$total" },
            lastOrderDate: { $max: "$order_date" }
        }
    },
    
    // Sort by total spent
    { $sort: { totalSpent: -1 } },
    
    // Top 10 customers
    { $limit: 10 }
])
```

### 🔹 Social Media Analytics

```javascript
db.posts.aggregate([
    // Unwind tags array
    { $unwind: "$tags" },
    
    // Group by tag
    {
        $group: {
            _id: "$tags",
            postCount: { $sum: 1 },
            totalLikes: { $sum: "$likes" },
            avgLikes: { $avg: "$likes" }
        }
    },
    
    // Sort by popularity
    { $sort: { postCount: -1 } },
    
    // Top 20 tags
    { $limit: 20 }
])
```

### 🔹 Time Series Analysis

```javascript
db.events.aggregate([
    // Add date fields
    {
        $addFields: {
            year: { $year: "$timestamp" },
            month: { $month: "$timestamp" },
            day: { $dayOfMonth: "$timestamp" }
        }
    },
    
    // Group by date
    {
        $group: {
            _id: {
                year: "$year",
                month: "$month", 
                day: "$day"
            },
            eventCount: { $sum: 1 },
            uniqueUsers: { $addToSet: "$user_id" }
        }
    },
    
    // Add computed fields
    {
        $addFields: {
            uniqueUserCount: { $size: "$uniqueUsers" },
            date: {
                $dateFromParts: {
                    year: "$_id.year",
                    month: "$_id.month",
                    day: "$_id.day"
                }
            }
        }
    },
    
    // Sort by date
    { $sort: { "_id.year": 1, "_id.month": 1, "_id.day": 1 } }
])
```

---

## ✅ Performance Optimization

### 🔹 Indexes

**Create indexes for aggregation stages**

```javascript
// Index for $match stage
db.orders.createIndex({ status: 1, total: 1 });

// Index for $lookup
db.orders.createIndex({ customer_id: 1 });
db.customers.createIndex({ _id: 1 });
```

### 🔹 Pipeline Optimization

**Order stages efficiently**

```javascript
// Good: Filter early
db.collection.aggregate([
    { $match: { status: "active" } },  // Filter first
    { $group: { ... } },               // Then group
    { $sort: { ... } }                 // Finally sort
])

// Bad: Sort before filter
db.collection.aggregate([
    { $sort: { ... } },                // Sorts all documents
    { $match: { status: "active" } }   // Then filters
])
```

### 🔹 Memory Limits

**16MB limit for pipeline results**

```javascript
// Use $limit to control output size
// Use $out to save to collection
db.collection.aggregate([
    // ... pipeline stages ...
    { $out: "aggregated_results" }  // Save to collection
])
```

---

## ✅ Aggregation vs Map-Reduce

| Feature | Aggregation Pipeline | Map-Reduce |
|---------|---------------------|------------|
| **Complexity** | Declarative, easier | Imperative, complex |
| **Performance** | Generally faster | Slower for large data |
| **Flexibility** | Rich operators | Custom JavaScript |
| **Real-time** | Yes | Batch processing |
| **Use Case** | Most aggregations | Complex custom logic |

---

## 🎯 Interview One-Liner

> MongoDB's aggregation pipeline processes documents through stages like $match (filter), $group (aggregate), $project (reshape), $sort (order), and $lookup (join), enabling complex data analysis and transformation with high performance.

---

# 196. Indexing in MongoDB

## ✅ What is Indexing in MongoDB?

**Indexing** is a data structure that improves query performance by allowing MongoDB to quickly locate documents.

👉 **Like book index** - find information faster without scanning entire book

---

## ✅ How Indexes Work

### 🔹 Without Index

**Collection scan - check every document**

```javascript
// Query: Find user by email
db.users.find({ email: "john@example.com" })

// Without index: Scans all documents
// Checks email field in each document
// Very slow for large collections
```

### 🔹 With Index

**Index scan - direct lookup**

```javascript
// With email index: Direct lookup in B-tree
// Much faster - O(log n) vs O(n)
```

---

## ✅ Index Types

### 🔹 Single Field Index

**Index on one field**

```javascript
// Create index on email field
db.users.createIndex({ email: 1 })  // 1 = ascending

// Query uses index
db.users.find({ email: "john@example.com" })
```

### 🔹 Compound Index

**Index on multiple fields**

```javascript
// Index on age and city
db.users.createIndex({ age: 1, city: 1 })

// Can use for:
// - Exact match: { age: 30, city: "NYC" }
// - Prefix match: { age: 30 } (age only)
// - Range: { age: { $gte: 25 }, city: "NYC" }
```

### 🔹 Multikey Index

**Index on array fields**

```javascript
// Document with tags array
{
    name: "John",
    tags: ["mongodb", "nosql", "developer"]
}

// Index on array field
db.users.createIndex({ tags: 1 })

// Can query array elements
db.users.find({ tags: "mongodb" })
```

### 🔹 Text Index

**Full-text search on string fields**

```javascript
// Create text index
db.articles.createIndex({ title: "text", content: "text" })

// Text search
db.articles.find({ $text: { $search: "mongodb tutorial" } })
```

### 🔹 Geospatial Index

**Index for location-based queries**

```javascript
// 2dsphere for Earth coordinates
db.places.createIndex({ location: "2dsphere" })

// Geo queries
db.places.find({
    location: {
        $near: {
            $geometry: { type: "Point", coordinates: [-74.0, 40.7] },
            $maxDistance: 1000
        }
    }
})
```

### 🔹 Hashed Index

**Hash-based index for sharding**

```javascript
// Hashed index on shard key
db.users.createIndex({ user_id: "hashed" })

// Even distribution across shards
```

---

## ✅ Index Properties

### 🔹 Unique Index

**Prevent duplicate values**

```javascript
// Unique index on email
db.users.createIndex({ email: 1 }, { unique: true })

// Error on duplicate insert
db.users.insertOne({ email: "john@example.com" })  // Duplicate error
```

### 🔹 Sparse Index

**Index only documents with the field**

```javascript
// Sparse index on optional field
db.users.createIndex({ phone: 1 }, { sparse: true })

// Doesn't index documents without phone field
// Saves space for optional fields
```

### 🔹 Partial Index

**Index documents matching a condition**

```javascript
// Index only active users
db.users.createIndex(
    { email: 1 },
    { partialFilterExpression: { status: "active" } }
)

// Smaller index, faster queries for active users
```

---

## ✅ Index Management

### 🔹 Create Index

```javascript
// Basic index
db.collection.createIndex({ field: 1 })

// With options
db.collection.createIndex(
    { field: 1 },
    {
        unique: true,
        sparse: true,
        name: "my_index"
    }
)
```

### 🔹 List Indexes

```javascript
// Show all indexes
db.collection.getIndexes()

// Example output:
[
    { "v": 2, "key": { "_id": 1 }, "name": "_id_" },
    { "v": 2, "key": { "email": 1 }, "name": "email_1" }
]
```

### 🔹 Drop Index

```javascript
// Drop by name
db.collection.dropIndex("email_1")

// Drop all indexes (except _id)
db.collection.dropIndexes()
```

### 🔹 Index Statistics

```javascript
// Index usage statistics
db.collection.stats()

// Index size information
db.collection.totalIndexSize()
```

---

## ✅ Index Best Practices

### 🔹 Index Selectivity

**Choose fields with high cardinality**

```javascript
// Good: High selectivity
db.users.createIndex({ email: 1 })     // Unique values
db.users.createIndex({ ssn: 1 })       // Unique values

// Bad: Low selectivity  
db.users.createIndex({ gender: 1 })    // Only "M" or "F"
db.users.createIndex({ active: 1 })    // Mostly true
```

### 🔹 Query Pattern Matching

**Create indexes matching query patterns**

```javascript
// Query pattern
db.users.find({ age: { $gte: 25 }, city: "NYC", status: "active" })

// Matching compound index
db.users.createIndex({ age: 1, city: 1, status: 1 })

// ESR (Equality, Sort, Range) rule:
// Equality fields first, then sort, then range
```

### 🔹 Index Size Consideration

**Balance performance vs storage**

```javascript
// Check index size
db.collection.stats().indexSizes

// Large indexes impact:
// - Write performance
// - Storage costs
// - Memory usage
```

### 🔹 Covered Queries

**Index contains all queried fields**

```javascript
// Index on name and age
db.users.createIndex({ name: 1, age: 1 })

// Covered query (no document access needed)
db.users.find({ name: "John" }, { name: 1, age: 1, _id: 0 })

// Uses only index - very fast
```

---

## ✅ Index Performance Analysis

### 🔹 Explain Query

**Analyze query execution**

```javascript
// Explain query execution
db.users.find({ age: { $gte: 25 } }).explain("executionStats")

// Output shows:
// - Index used
// - Documents examined
// - Execution time
// - Winning plan
```

### 🔹 Index Usage Statistics

```javascript
// Check index usage
db.serverStatus().metrics.queryExecutor

// Shows:
// - Total queries
// - Index hits/misses
// - Scanned objects
```

---

## ✅ Common Index Issues

### 🔹 Index Not Used

**Query doesn't match index pattern**

```javascript
// Index: { age: 1, city: 1 }
db.users.find({ city: "NYC" })  // Doesn't use index (city first)

// Fix: Create separate index or reorder
db.users.createIndex({ city: 1 })
```

### 🔹 Slow Range Queries

**Range queries on low-selectivity fields**

```javascript
// Slow: Range on low-cardinality field
db.users.find({ status: { $ne: "inactive" } })

// Better: Index on high-selectivity field
db.users.createIndex({ email: 1 })
```

### 🔹 Over-Indexing

**Too many indexes hurt performance**

```javascript
// Symptoms:
// - Slow writes
// - High memory usage
// - Large database size

// Solution: Remove unused indexes
db.collection.dropIndex("unused_index")
```

---

## ✅ Special Index Types

### 🔹 TTL Index

**Automatically delete documents after time**

```javascript
// Sessions expire after 1 hour
db.sessions.createIndex(
    { createdAt: 1 },
    { expireAfterSeconds: 3600 }
)

// Documents auto-deleted after 1 hour
```

### 🔹 Wildcard Index

**Index unknown field names**

```javascript
// Index all fields starting with "metadata."
db.collection.createIndex({ "metadata.$**": 1 })

// Useful for dynamic schemas
```

---

## 🎯 Interview One-Liner

> MongoDB indexes improve query performance using B-tree structures, supporting single-field, compound, text, geospatial, and unique indexes, with best practices focusing on selectivity, query patterns, and avoiding over-indexing.

---

# 197. Sharding in MongoDB

## ✅ What is Sharding?

**Sharding** is MongoDB's method of distributing data across multiple servers (shards) for horizontal scalability.

👉 **Split large datasets** across multiple machines

---

## ✅ Why Sharding?

### 🔹 Scale Beyond Single Server

**Handle massive data and traffic**

```javascript
// Single server limits:
// - Storage: Disk capacity
// - CPU: Processing power  
// - Memory: RAM constraints
// - Network: Bandwidth limits

// Sharding distributes load across multiple servers
```

### 🔹 High Availability

**No single point of failure**

```javascript
// If one shard fails, others continue
// Automatic failover within shards
```

### 🔹 Cost Effective Scaling

**Add commodity hardware**

```javascript
// Horizontal scaling (add more servers)
// vs Vertical scaling (upgrade single server)
```

---

## ✅ Sharding Architecture

### 🔹 Shard

**Individual MongoDB instance storing subset of data**

```javascript
// Each shard is a replica set
// Contains subset of total data
// Can be on different machines
```

### 🔹 Config Servers

**Store cluster metadata**

```javascript
// 3 config servers (replica set)
// Store:
// - Shard locations
// - Chunk distributions
// - Cluster configuration
```

### 🔹 Mongos Router

**Query router for applications**

```javascript
// Routes queries to appropriate shards
// Presents single interface to applications
// Can have multiple mongos instances
```

---

## ✅ Shard Key

**Field used to distribute data across shards**

### 🔹 Choosing Shard Key

**Critical for performance**

```javascript
// Good shard key: High cardinality, even distribution
db.collection.createIndex({ user_id: 1 })
sh.shardCollection("mydb.users", { user_id: 1 })

// Bad shard key: Low cardinality, uneven distribution
sh.shardCollection("mydb.logs", { log_level: 1 })  // Only "INFO", "WARN", "ERROR"
```

### 🔹 Shard Key Types

**Hashed vs Ranged**

```javascript
// Ranged sharding (default)
// Data distributed by value ranges
sh.shardCollection("mydb.collection", { field: 1 })

// Hashed sharding
// Data distributed by hash of field value
sh.shardCollection("mydb.collection", { field: "hashed" })
```

---

## ✅ Chunks

**Units of data distribution**

### 🔹 Chunk Structure

```javascript
// Each chunk contains:
// - Range of shard key values
// - Subset of documents
// - Default size: 64MB

// Example chunk:
{
    "_id": "collection-shard1-0000",
    "min": { "user_id": 1 },
    "max": { "user_id": 1000 }
}
```

### 🔹 Chunk Splitting

**Automatic chunk division**

```javascript
// When chunk exceeds size limit:
// - Split into two chunks
// - Redistribute across shards
// - Balancer moves chunks between shards
```

### 🔹 Chunk Migration

**Balancer moves chunks for even distribution**

```javascript
// Balancer:
// - Runs every few minutes
// - Identifies imbalance
// - Migrates chunks between shards
// - Minimal downtime during migration
```

---

## ✅ Sharding Strategies

### 🔹 Ranged Sharding

**Distribute by value ranges**

```javascript
// Good for:
// - Range queries
// - Sorted data
// - Incremental keys

// Example: User data by ID ranges
// Shard 1: user_id 1-1000
// Shard 2: user_id 1001-2000
// Shard 3: user_id 2001-3000
```

### 🔹 Hashed Sharding

**Distribute by hash value**

```javascript
// Good for:
// - Even distribution
// - Write-heavy workloads
// - No range queries

// Example: Hash user_id
// user_id 123 → hash to Shard A
// user_id 456 → hash to Shard B
```

### 🔹 Zone Sharding

**Pin data to specific shards**

```javascript
// Geographic data pinning
sh.addShardToZone("shard1", "US_East")
sh.addShardToZone("shard2", "US_West")

// Route queries to specific zones
sh.updateZoneKeyRange(
    "mydb.users",
    { country: "US", state: "NY" },
    { country: "US", state: "NY" },
    "US_East"
)
```

---

## ✅ Query Routing

### 🔹 Targeted Queries

**Query single shard**

```javascript
// Shard key in query
db.users.find({ user_id: 123 })

// Mongos routes to specific shard
// Fast: Only one shard queried
```

### 🔹 Scatter-Gather Queries

**Query all shards**

```javascript
// No shard key in query
db.users.find({ age: { $gte: 25 } })

// Mongos broadcasts to all shards
// Results merged and returned
// Slower: All shards involved
```

### 🔹 Scatter-Gather with Sort

**Complex queries**

```javascript
// Sort across shards
db.users.find().sort({ created_at: -1 }).limit(10)

// Each shard returns top 10
// Mongos merges and returns global top 10
```

---

## ✅ Sharding Administration

### 🔹 Enable Sharding

```javascript
// Start config servers
// Start mongos routers
// Add shards to cluster

// Enable sharding for database
sh.enableSharding("mydb")

// Shard collection
sh.shardCollection("mydb.collection", { shard_key: 1 })
```

### 🔹 Monitor Sharding

```javascript
// Check shard status
sh.status()

// View chunk distribution
db.collection.getShardDistribution()

// Check balancer status
sh.getBalancerState()
```

### 🔹 Manage Shards

```javascript
// Add new shard
sh.addShard("shard4/mongodb4:27017")

// Remove shard (drain chunks first)
sh.removeShard("shard3")
```

---

## ✅ Sharding Limitations

### 🔹 Unique Indexes

**Shard key must be prefix of unique indexes**

```javascript
// Shard key: { user_id: 1 }
// Unique index must be: { user_id: 1, email: 1 }
// Cannot have unique index on email alone
```

### 🔹 Transactions

**Limited cross-shard transactions**

```javascript
// MongoDB 4.0+: Multi-document transactions
// But performance impact on sharded clusters
// Best: Keep transactional data on same shard
```

### 🔹 Joins

**$lookup works but expensive**

```javascript
// Cross-shard joins require scatter-gather
// Better: Denormalize or co-locate related data
```

---

## ✅ Sharding Best Practices

### 🔹 Choose Right Shard Key

```javascript
// Cardinality: High (many unique values)
// Frequency: Evenly distributed writes
// Query patterns: Match common queries
// Monotonic: Avoid hotspotting
```

### 🔹 Pre-Split Chunks

**For bulk data loading**

```javascript
// Pre-create empty chunks
sh.splitAt("mydb.collection", { shard_key: "value1" })
sh.splitAt("mydb.collection", { shard_key: "value2" })

// Then load data
```

### 🔹 Monitor Performance

```javascript
// Watch for:
// - Uneven chunk distribution
// - Hot shards (high load)
// - Slow queries
// - Balancer performance
```

### 🔹 Backup Strategy

**Backup sharded clusters**

```javascript
// Use mongodump with --oplog
// Or MongoDB Atlas automated backups
// Coordinate across all shards
```

---

## ✅ Sharding vs Replication

| Feature | Sharding | Replication |
|---------|----------|-------------|
| **Purpose** | Scale out (horizontal) | High availability |
| **Data Distribution** | Split across servers | Full copy on each |
| **Read Scaling** | Yes | Yes |
| **Write Scaling** | Yes | No (single primary) |
| **Failure Impact** | Partial data loss | Full redundancy |
| **Complexity** | High | Medium |

---

## 🎯 Interview One-Liner

> MongoDB sharding distributes data across multiple servers using a shard key, enabling horizontal scalability with automatic chunk splitting and balancing, supporting ranged or hashed distribution for optimal query performance.

---

# 198. Replication in MongoDB

## ✅ What is Replication?

**Replication** is the process of synchronizing data across multiple MongoDB servers for high availability and data redundancy.

👉 **Multiple copies** of data across servers

---

## ✅ Why Replication?

### 🔹 High Availability

**System stays operational during failures**

```javascript
// If primary fails:
// - Automatic failover
// - Secondary becomes primary
// - Application continues working
```

### 🔹 Data Redundancy

**Multiple copies protect against data loss**

```javascript
// Hardware failure on one server
// Data still available on others
// Automatic recovery possible
```

### 🔹 Read Scaling

**Distribute read operations**

```javascript
// Read from secondaries
// Reduce load on primary
// Better performance
```

### 🔹 Disaster Recovery

**Geographic data distribution**

```javascript
// Data centers in different regions
// Survive regional disasters
// Faster local access
```

---

## ✅ Replica Set Architecture

### 🔹 Primary Node

**Handles all write operations**

```javascript
// Only one primary per replica set
// Receives all write operations
// Records operations in oplog
// Replicates changes to secondaries
```

### 🔹 Secondary Nodes

**Maintain copies of primary's data**

```javascript
// Apply operations from oplog
// Can serve read operations
// Participate in elections
// Provide data redundancy
```

### 🔹 Arbiter Node

**Participates in elections only**

```javascript
// No data storage
// Helps achieve majority in elections
// Used in sets with even number of nodes
```

---

## ✅ Replication Process

### 🔹 Oplog (Operation Log)

**Record of all operations**

```javascript
// Every operation on primary:
// - Executed on primary
// - Recorded in oplog
// - Replicated to secondaries

// Oplog entry example:
{
    "ts": Timestamp(1640995200, 1),
    "op": "i",  // insert operation
    "ns": "mydb.users",
    "o": { "_id": 1, "name": "John" }
}
```

### 🔹 Initial Sync

**New secondary catches up**

```javascript
// Process:
// 1. Copy all data files
// 2. Apply all oplog operations
// 3. Start normal replication
```

### 🔹 Steady State Replication

**Ongoing synchronization**

```javascript
// Secondaries:
// - Tail the primary's oplog
// - Apply operations in order
// - Maintain identical data
```

---

## ✅ Read Preferences

### 🔹 Primary (Default)

**Read from primary only**

```javascript
// Strong consistency
// Latest data
// May be slower under high load
db.collection.find().readPref("primary")
```

### 🔹 Primary Preferred

**Read from primary, secondary if unavailable**

```javascript
// Strong consistency when possible
// High availability
db.collection.find().readPref("primaryPreferred")
```

### 🔹 Secondary

**Read from secondary only**

```javascript
// Reduce load on primary
// May have stale data
db.collection.find().readPref("secondary")
```

### 🔹 Secondary Preferred

**Read from secondary, primary if none available**

```javascript
// Maximum read scaling
// Eventual consistency
db.collection.find().readPref("secondaryPreferred")
```

### 🔹 Nearest

**Read from closest node**

```javascript
// Geographic distribution
// Lowest latency
db.collection.find().readPref("nearest")
```

---

## ✅ Write Concerns

### 🔹 Acknowledgment Levels

**Control write durability**

```javascript
// w: 1 (default) - Primary acknowledgment
db.collection.insertOne(doc, { writeConcern: { w: 1 } })

// w: "majority" - Majority of nodes
db.collection.insertOne(doc, { writeConcern: { w: "majority" } })

// w: 0 - No acknowledgment (fire and forget)
db.collection.insertOne(doc, { writeConcern: { w: 0 } })
```

### 🔹 Journaling

**Write to journal before acknowledgment**

```javascript
// j: true - Wait for journal write
db.collection.insertOne(doc, { writeConcern: { w: 1, j: true } })
```

### 🔹 Timeout

**Maximum wait time**

```javascript
// wtimeout: 5000 - 5 second timeout
db.collection.insertOne(doc, {
    writeConcern: { w: "majority", wtimeout: 5000 }
})
```

---

## ✅ Elections and Failover

### 🔹 Election Process

**Choose new primary when current fails**

```javascript
// Election triggered by:
// - Primary failure
// - Network partition
// - Manual step down

// Election requirements:
// - Majority of voting members
// - Highest priority (if configured)
// - Most recent oplog
```

### 🔹 Election Algorithm

**Raft consensus protocol**

```javascript
// Phases:
// 1. Candidate requests votes
// 2. Nodes vote for candidate
// 3. Candidate becomes primary with majority
// 4. Heartbeats maintain leadership
```

### 🔹 Priority Settings

**Control which node becomes primary**

```javascript
// High priority for preferred primary
rs.reconfig({
    _id: "myReplSet",
    members: [
        { _id: 0, host: "primary:27017", priority: 2 },
        { _id: 1, host: "secondary1:27017", priority: 1 },
        { _id: 2, host: "secondary2:27017", priority: 0 }
    ]
})
```

---

## ✅ Replica Set Configuration

### 🔹 Initialize Replica Set

```javascript
// Start mongod instances
// Connect to one instance
rs.initiate({
    _id: "myReplSet",
    members: [
        { _id: 0, host: "localhost:27017" },
        { _id: 1, host: "localhost:27018" },
        { _id: 2, host: "localhost:27019" }
    ]
})
```

### 🔹 Add Members

```javascript
// Add secondary
rs.add("newhost:27017")

// Add arbiter
rs.addArb("arbiterhost:27017")
```

### 🔹 Remove Members

```javascript
// Remove member
rs.remove("oldhost:27017")
```

---

## ✅ Monitoring Replication

### 🔹 Replication Status

```javascript
// Check replica set status
rs.status()

// Shows:
// - Member states
// - Replication lag
// - Last heartbeat
// - Election information
```

### 🔹 Oplog Status

```javascript
// Check oplog size and usage
rs.printReplicationInfo()

// Shows:
// - Oplog size
// - Time range covered
// - Replication window
```

### 🔹 Replication Lag

**Delay between primary and secondary**

```javascript
// Monitor lag
db.serverStatus().repl

// Causes of lag:
// - High write load
// - Network issues
// - Secondary maintenance
// - Large operations
```

---

## ✅ Replication Best Practices

### 🔹 Replica Set Size

**Odd number of members**

```javascript
// 3 members: Can tolerate 1 failure
// 5 members: Can tolerate 2 failures
// Avoid even numbers (split votes)
```

### 🔹 Geographic Distribution

**Place members in different locations**

```javascript
// Data centers in different regions
// Disaster recovery
// Local read access
```

### 🔹 Oplog Sizing

**Ensure sufficient oplog**

```javascript
// Check oplog size
db.getReplicationInfo()

// Increase if needed
db.adminCommand({
    replSetResizeOplog: 1,
    size: 10240  // MB
})
```

### 🔹 Backup Strategy

**Backup from secondary**

```javascript
// Reduce load on primary
// Use hidden secondary for backups
rs.add({ _id: 3, host: "backup:27017", hidden: true })
```

---

## ✅ Replication Limitations

### 🔹 Write Performance

**All writes go to primary**

```javascript
// No write scaling
// Primary can become bottleneck
// Use sharding for write scaling
```

### 🔹 Cross-Data Center Latency

**Network delays affect replication**

```javascript
// High latency between data centers
// Increased replication lag
// Potential election issues
```

### 🔹 Schema Changes

**Careful with schema modifications**

```javascript
// Schema changes on primary
// Must be compatible with secondaries
// Rolling upgrades required
```

---

## ✅ Replication vs Sharding

| Feature | Replication | Sharding |
|---------|-------------|----------|
| **Purpose** | High availability | Horizontal scaling |
| **Data Distribution** | Full copies | Data subsets |
| **Read Scaling** | Yes | Yes |
| **Write Scaling** | No | Yes |
| **Complexity** | Medium | High |
| **Use Case** | Failover, backups | Massive datasets |

---

## 🎯 Interview One-Liner

> MongoDB replication creates multiple data copies across servers for high availability, with one primary handling writes and secondaries providing reads and automatic failover through elections using the oplog for synchronization.

---

# 199. What is Redis? Use cases?

## ✅ What is Redis?

**Redis** (Remote Dictionary Server) is an open-source, in-memory data structure store used as a database, cache, and message broker.

👉 **Blazingly fast** key-value store with rich data types

---

## ✅ Key Features

### 🔹 In-Memory Storage

**Data stored in RAM for ultra-fast access**

```bash
# Memory-first architecture
# Microsecond response times
# Optional disk persistence
```

### 🔹 Rich Data Types

**Beyond simple key-value pairs**

```bash
# Strings, Lists, Sets, Hashes, Sorted Sets
# Bitmaps, HyperLogLogs, Streams
```

### 🔹 Persistence Options

**Data durability choices**

```bash
# RDB: Point-in-time snapshots
# AOF: Append-only file logging
# Both: Maximum durability
```

### 🔹 High Availability

**Replication and clustering**

```bash
# Master-slave replication
# Redis Cluster for sharding
# Sentinel for monitoring/failover
```

---

## ✅ Redis Data Types

### 🔹 Strings

**Basic key-value pairs**

```bash
SET user:123:name "John Doe"
GET user:123:name
# "John Doe"

INCR page_views  # Atomic increment
EXPIRE session:abc 3600  # Auto-expire
```

### 🔹 Lists

**Ordered collections**

```bash
LPUSH recent_posts "post1" "post2" "post3"
LRANGE recent_posts 0 -1
# ["post3", "post2", "post1"]

RPOP recent_posts  # Remove from right
```

### 🔹 Sets

**Unordered unique collections**

```bash
SADD user:123:tags "developer" "mongodb" "redis"
SMEMBERS user:123:tags
# {"developer", "mongodb", "redis"}

SINTER user:123:tags user:456:tags  # Intersection
```

### 🔹 Hashes

**Field-value pairs within keys**

```bash
HSET user:123 name "John" email "john@example.com" age 30
HGET user:123 name
# "John"

HGETALL user:123
# {"name": "John", "email": "john@example.com", "age": "30"}
```

### 🔹 Sorted Sets

**Ordered unique elements with scores**

```bash
ZADD leaderboard 100 "player1" 85 "player2" 92 "player3"
ZRANGE leaderboard 0 -1 WITHSCORES
# ["player2", "85", "player3", "92", "player1", "100"]

ZRANK leaderboard "player1"  # Get rank
```

### 🔹 Other Types

```bash
# Bitmaps: Bit-level operations
SETBIT user:123:login_days 1 1  # Mark day 1 as logged in

# HyperLogLogs: Approximate unique counts
PFADD page_views:user123 "page1" "page2" "page1"
PFCOUNT page_views:user123  # Approximate unique pages

# Streams: Append-only logs
XADD events * sensor_id 123 temperature 23.5
```

---

## ✅ Redis Use Cases

### 🔹 Caching

**Accelerate application performance**

```bash
# Cache database queries
SET user:123:profile '{"name": "John", "email": "..."}'
EXPIRE user:123:profile 3600  # Expire in 1 hour

# Cache session data
SET session:abc:user_id 123
EXPIRE session:abc 1800
```

### 🔹 Session Storage

**Store user sessions**

```bash
# Session management
SET session:xyz:user_id 123
SET session:xyz:cart_items 5
EXPIRE session:xyz 3600

# Distributed sessions across servers
```

### 🔹 Real-time Analytics

**Fast counters and statistics**

```bash
# Page views
INCR page:home:views

# User actions
INCR user:123:likes_given

# Time-based counters
INCR daily:active:users:2024-01-01
```

### 🔹 Rate Limiting

**Control API usage**

```bash
# Sliding window rate limiting
INCR api:calls:user123
EXPIRE api:calls:user123 60  # Reset every minute

# Check limit
GET api:calls:user123  # Compare with threshold
```

### 🔹 Leaderboards

**Gaming and ranking systems**

```bash
# Game scores
ZADD game:scores 1500 "player1" 1420 "player2"

# Get top players
ZREVRANGE game:scores 0 9 WITHSCORES

# Player ranking
ZRANK game:scores "player1"
```

### 🔹 Message Queues

**Simple pub/sub messaging**

```bash
# Publish messages
PUBLISH notifications "User 123 logged in"

# Subscribe to channels
SUBSCRIBE notifications  # Receive messages
```

### 🔹 Geospatial Data

**Location-based operations**

```bash
# Store locations
GEOADD locations 13.361389 38.115556 "Palermo"

# Find nearby
GEORADIUS locations 15 37 200 km
```

### 🔹 Distributed Locks

**Prevent race conditions**

```bash
# Set lock with expiration
SET lock:resource:123 "locked" NX EX 30

# Release lock
DEL lock:resource:123
```

---

## ✅ Redis Architecture

### 🔹 Single Threaded

**Single-threaded execution model**

```bash
# Advantages:
# - No race conditions
# - Atomic operations
# - Simple concurrency model

# Disadvantages:
# - CPU-bound operations block server
# - Use pipelining for multiple commands
```

### 🔹 Persistence

**Data durability options**

```bash
# RDB Snapshots:
SAVE  # Synchronous save
BGSAVE  # Background save

# AOF (Append Only File):
APPENDONLY yes  # Log every write
```

### 🔹 Replication

**Master-slave architecture**

```bash
# Master accepts writes
# Slaves replicate data
# Read scaling with slaves
# Automatic failover with Sentinel
```

### 🔹 Clustering

**Distributed Redis**

```bash
# Automatic sharding
# 16384 hash slots
# Master-slave per shard
# Automatic failover
```

---

## ✅ Redis vs Other Databases

| Feature | Redis | MongoDB | PostgreSQL |
|---------|-------|---------|------------|
| **Performance** | ⚡ Microseconds | ⚡ Milliseconds | 🐌 Milliseconds |
| **Data Types** | Rich (10+ types) | Documents | Tables |
| **Persistence** | Optional | Yes | Yes |
| **Scaling** | Clustering | Sharding | Limited |
| **Use Case** | Cache, sessions | Documents | Relations |

---

## ✅ Redis Best Practices

### 🔹 Memory Management

```bash
# Monitor memory usage
INFO memory

# Set memory limits
CONFIG SET maxmemory 256mb
CONFIG SET maxmemory-policy allkeys-lru

# Use appropriate data types
# Avoid large strings
```

### 🔹 Connection Pooling

```javascript
// Reuse connections
const redis = require('redis');
const client = redis.createClient();

// Connection pooling in applications
```

### 🔹 Key Naming

```bash
# Consistent naming convention
user:123:profile
user:123:posts
session:abc:data

# Avoid long keys
```

### 🔹 Expiration Strategy

```bash
# Set TTL on temporary data
EXPIRE cache:key 3600

# Use volatile policies
maxmemory-policy volatile-ttl
```

---

## ✅ Redis Limitations

### 🔹 Memory Bound

**Data must fit in memory**

```bash
# Solutions:
# - Redis Cluster for large datasets
# - Redis on Flash (SSDs)
# - Data partitioning
```

### 🔹 Single Threaded

**CPU operations block server**

```bash
# Avoid expensive operations
# Use pipelining for multiple commands
# Offload computation to clients
```

### 🔹 No Advanced Queries

**Limited query capabilities**

```bash
# No joins, aggregations
# Simple key-based access
# Complex logic in application
```

---

## 🎯 Interview One-Liner

> Redis is an in-memory key-value store with rich data types (strings, lists, sets, hashes, sorted sets) used for caching, session storage, real-time analytics, rate limiting, and message queues, offering microsecond performance with optional persistence.

---

# 200. What is Cassandra? When to use?

## ✅ What is Cassandra?

**Apache Cassandra** is a highly scalable, distributed NoSQL database designed for handling large amounts of data across many commodity servers.

👉 **Built for massive scale** and high availability

---

## ✅ Key Features

### 🔹 Distributed Architecture

**Peer-to-peer, no single point of failure**

```sql
-- All nodes are equal
-- Automatic data distribution
-- No master-slave bottleneck
```

### 🔹 High Availability

**Designed for 99.9% uptime**

```sql
-- No downtime for maintenance
-- Survives node failures
-- Cross-data center replication
```

### 🔹 Linear Scalability

**Performance increases with nodes**

```sql
-- Add nodes without downtime
-- Automatic load balancing
-- Consistent performance
```

### 🔹 Column-Family Data Model

**Flexible column structure**

```sql
-- Tables with dynamic columns
-- Efficient for time-series data
-- Sparse data handling
```

---

## ✅ Cassandra Data Model

### 🔹 Keyspace

**Top-level container (like database)**

```sql
CREATE KEYSPACE ecommerce
WITH REPLICATION = {
    'class': 'SimpleStrategy',
    'replication_factor': 3
};
```

### 🔹 Table

**Contains rows and columns**

```sql
CREATE TABLE products (
    product_id UUID PRIMARY KEY,
    name TEXT,
    price DECIMAL,
    category TEXT,
    tags SET<TEXT>,
    reviews MAP<UUID, TEXT>
);
```

### 🔹 Partition Key

**Distributes data across nodes**

```sql
-- Primary key determines data location
CREATE TABLE user_events (
    user_id UUID,
    event_time TIMESTAMP,
    event_type TEXT,
    data TEXT,
    PRIMARY KEY (user_id, event_time)
);
-- user_id = partition key
-- event_time = clustering column
```

---

## ✅ Cassandra Query Language (CQL)

### 🔹 Basic CRUD Operations

```sql
-- Create
INSERT INTO users (id, name, email) 
VALUES (uuid(), 'John', 'john@example.com');

-- Read
SELECT * FROM users WHERE id = ?;

-- Update  
UPDATE users SET name = 'John Doe' WHERE id = ?;

-- Delete
DELETE FROM users WHERE id = ?;
```

### 🔹 Advanced Queries

```sql
-- Range queries on clustering columns
SELECT * FROM user_events 
WHERE user_id = ? 
AND event_time > ? AND event_time < ?;

-- Batch operations
BEGIN BATCH
    INSERT INTO orders (id, user_id, total) VALUES (?, ?, ?);
    UPDATE inventory SET stock = stock - ? WHERE product_id = ?;
APPLY BATCH;
```

---

## ✅ When to Use Cassandra

### 🔹 Time-Series Data

**IoT sensors, logs, events**

```sql
CREATE TABLE sensor_data (
    sensor_id UUID,
    timestamp TIMESTAMP,
    temperature DOUBLE,
    humidity DOUBLE,
    PRIMARY KEY (sensor_id, timestamp)
);

-- Efficient time-range queries
SELECT * FROM sensor_data 
WHERE sensor_id = ? 
AND timestamp > ? AND timestamp < ?;
```

### 🔹 High Write Throughput

**Applications with massive write loads**

```sql
-- Social media posts
-- User activity tracking
-- Analytics events
-- Write-heavy workloads
```

### 🔹 Global Scale Applications

**Worldwide user base**

```sql
-- Multi-data center deployment
-- Automatic replication
-- Local data access
-- Disaster recovery
```

### 🔹 Variable Schema Data

**Flexible data structures**

```sql
-- User profiles with custom fields
-- Product catalogs with varying attributes
-- Dynamic content management
```

---

## ✅ Cassandra Architecture

### 🔹 Ring Topology

**Nodes arranged in a ring**

```sql
-- Consistent hashing
-- Data distributed evenly
-- Virtual nodes for better distribution
```

### 🔹 Replication

**Configurable data copies**

```sql
-- Replication factor (RF)
-- RF = 3: 3 copies of each piece of data
-- Survives 2 node failures
```

### 🔹 Consistency Levels

**Tune consistency vs availability**

```sql
-- ONE: Fast, weak consistency
-- QUORUM: Balanced
-- ALL: Strong consistency, slow
```

### 🔹 Compaction

**Merge SSTable files**

```sql
-- Size-tiered compaction
-- Leveled compaction
-- Time-window compaction for time-series
```

---

## ✅ Cassandra vs Other Databases

| Feature | Cassandra | MongoDB | DynamoDB |
|---------|-----------|---------|----------|
| **Consistency** | Tunable | Configurable | Eventual |
| **Scaling** | Linear | Good | Excellent |
| **Data Model** | Column-family | Document | Key-value |
| **Query Language** | CQL | Rich queries | Limited |
| **Availability** | 99.9% | High | Very high |
| **Best For** | Time-series | Documents | Global scale |

---

## ✅ Cassandra Use Cases

### 🔹 IoT and Sensor Data

```sql
CREATE TABLE iot_readings (
    device_id UUID,
    time_bucket TEXT,
    timestamp TIMESTAMP,
    sensor_value DOUBLE,
    PRIMARY KEY ((device_id, time_bucket), timestamp)
);

-- Efficient queries by device and time
```

### 🔹 Messaging Systems

```sql
CREATE TABLE messages (
    channel_id UUID,
    message_id TIMEUUID,
    sender_id UUID,
    content TEXT,
    PRIMARY KEY (channel_id, message_id)
);

-- Time-ordered messages per channel
```

### 🔹 Recommendation Systems

```sql
CREATE TABLE user_interactions (
    user_id UUID,
    item_id UUID,
    interaction_type TEXT,
    timestamp TIMESTAMP,
    score DOUBLE,
    PRIMARY KEY (user_id, timestamp)
);

-- User behavior analysis
```

---

## ✅ Cassandra Best Practices

### 🔹 Data Modeling

```sql
-- Denormalize for query patterns
-- Use composite partition keys
-- Design for your query patterns first
```

### 🔹 Partition Key Design

```sql
-- Even data distribution
-- Avoid hot partitions
-- Use time bucketing for time-series
```

### 🔹 Consistency Tuning

```sql
-- Use appropriate consistency levels
-- LOCAL_QUORUM for multi-DC
-- ONE for high throughput
```

### 🔹 Monitoring

```sql
-- Monitor compaction
-- Watch for dropped mutations
-- Check repair status
-- Monitor latency
```

---

## ✅ Cassandra Limitations

### 🔹 Complex Queries

**Limited join and aggregation support**

```sql
-- No joins
-- Basic aggregations only
-- Complex logic in application
```

### 🔹 Learning Curve

**Steep learning curve**

```sql
-- Data modeling is different
-- Tuning requires expertise
-- Operations complexity
```

### 🔹 Storage Overhead

**Multiple copies of data**

```sql
-- Replication factor overhead
-- Tombstones for deletes
-- Compaction overhead
```

---

## 🎯 Interview One-Liner

> Cassandra is a highly scalable, distributed NoSQL database optimized for time-series data and high write throughput, offering tunable consistency, linear scalability, and 99.9% availability across multiple data centers.

---

# 201. Key-Value vs Document vs Column-family stores

## ✅ Database Store Types Comparison

NoSQL databases are categorized by their data models, each optimized for specific use cases and query patterns.

---

## ✅ Key-Value Stores

### 🔹 Data Model

**Simple key-value pairs**

```javascript
// Redis example
SET user:123:name "John Doe"
SET user:123:email "john@example.com"
GET user:123:name  // "John Doe"
```

### 🔹 Characteristics

- **Schema:** None
- **Query:** Key-based only
- **Performance:** Excellent for simple lookups
- **Scalability:** Highly scalable
- **Data Types:** Usually strings, some support complex types

### 🔹 Use Cases

- **Caching:** Session data, user preferences
- **Real-time counters:** Likes, views, votes
- **Configuration:** Feature flags, settings
- **Rate limiting:** API call limits

### 🔹 Examples

**Redis, DynamoDB, Riak**

### 🔹 Advantages

- ⚡ Ultra-fast lookups
- 🔄 Simple horizontal scaling
- 📦 Easy to use

### 🔹 Disadvantages

- 🔍 No complex queries
- 📊 No aggregations
- 🔗 No relationships

---

## ✅ Document Stores

### 🔹 Data Model

**JSON-like documents with nested structures**

```javascript
// MongoDB example
{
    "_id": ObjectId("507f1f77bcf86cd799439011"),
    "user_id": 123,
    "name": "John Doe",
    "profile": {
        "age": 30,
        "interests": ["coding", "music"]
    },
    "orders": [
        {
            "order_id": 456,
            "total": 299.99,
            "status": "completed"
        }
    ]
}
```

### 🔹 Characteristics

- **Schema:** Flexible per document
- **Query:** Rich field-based queries
- **Performance:** Good for complex queries
- **Scalability:** Good horizontal scaling
- **Data Types:** Rich (objects, arrays, dates, etc.)

### 🔹 Use Cases

- **Content management:** Blogs, CMS, articles
- **User profiles:** Flexible user data
- **Product catalogs:** E-commerce products
- **Analytics:** Event tracking, logs

### 🔹 Examples

**MongoDB, CouchDB, RavenDB**

### 🔹 Advantages

- 🔍 Rich query capabilities
- 📄 Flexible schemas
- 🏗️ Good for complex data
- 🔧 Developer-friendly

### 🔹 Disadvantages

- 🐌 Slower than key-value
- 💾 Higher memory usage
- 🔄 Less scalable than key-value

---

## ✅ Column-Family Stores

### 🔹 Data Model

**Tables with rows and dynamic columns**

```sql
// Cassandra example
CREATE TABLE user_events (
    user_id UUID,
    event_time TIMESTAMP,
    event_type TEXT,
    event_data MAP<TEXT, TEXT>,
    PRIMARY KEY (user_id, event_time)
);

INSERT INTO user_events (user_id, event_time, event_type, event_data)
VALUES (uuid(), '2024-01-01 10:00:00', 'login', {'ip': '192.168.1.1'});
```

### 🔹 Characteristics

- **Schema:** Flexible columns per row
- **Query:** Efficient for column-based queries
- **Performance:** Excellent for analytical queries
- **Scalability:** Excellent horizontal scaling
- **Data Types:** Column-oriented storage

### 🔹 Use Cases

- **Time-series data:** IoT sensors, logs
- **Analytics:** Large-scale data analysis
- **Recommendation systems:** User behavior tracking
- **Messaging:** Chat history, notifications

### 🔹 Examples

**Cassandra, HBase, ScyllaDB**

### 🔹 Advantages

- 📊 Excellent for analytics
- 🔄 Highly scalable
- ⏱️ Good for time-series
- 💪 High write throughput

### 🔹 Disadvantages

- 📈 Steep learning curve
- 🔍 Limited query flexibility
- 🏗️ Complex data modeling
- 🚫 No joins or complex queries

---

## ✅ Detailed Comparison

| Aspect | Key-Value | Document | Column-Family |
|--------|-----------|----------|---------------|
| **Data Structure** | Simple pairs | Complex documents | Column families |
| **Schema Flexibility** | ✅ Maximum | ✅ High | ✅ High |
| **Query Complexity** | 🔍 Simple | 🔍 Rich | 🔍 Moderate |
| **Performance** | ⚡ Highest | ⚡ Good | ⚡ Excellent (analytics) |
| **Scalability** | 🔄 Excellent | 🔄 Good | 🔄 Excellent |
| **Learning Curve** | 📚 Easy | 📚 Medium | 📚 Hard |
| **Use Case** | Caching, sessions | Content, profiles | Time-series, analytics |
| **Examples** | Redis, DynamoDB | MongoDB, CouchDB | Cassandra, HBase |

---

## ✅ Choosing the Right Store Type

### 🔹 For Simple Lookups

**Choose Key-Value**

```javascript
// User session storage
// Configuration management
// Real-time counters
// Rate limiting
```

### 🔹 For Complex Data with Queries

**Choose Document**

```javascript
// User profiles with nested data
// Product catalogs
// Content management
// Event logging with search
```

### 🔹 For Time-Series and Analytics

**Choose Column-Family**

```javascript
// IoT sensor data
// User activity logs
// Financial transactions
// Recommendation data
```

---

## ✅ Hybrid Approaches

### 🔹 Polyglot Persistence

**Use multiple store types**

```javascript
// Architecture example:
// - Redis: Sessions, caching
// - MongoDB: User data, content
// - Cassandra: Analytics, logs
// - PostgreSQL: Financial data
```

### 🔹 Multi-Model Databases

**Single database with multiple models**

```javascript
// ArangoDB: Document + Graph
// OrientDB: Document + Graph + Key-Value
// Couchbase: Document + Key-Value
```

---

## ✅ Performance Considerations

### 🔹 Read Patterns

```javascript
// Key-Value: Best for primary key lookups
// Document: Good for field-based queries
// Column-Family: Excellent for range scans
```

### 🔹 Write Patterns

```javascript
// Key-Value: High write throughput
// Document: Good write performance
// Column-Family: Excellent for bulk writes
```

### 🔹 Data Size

```javascript
// Key-Value: Small to medium data
// Document: Medium to large documents
// Column-Family: Massive datasets
```

---

## ✅ Migration Strategies

### 🔹 From Relational to NoSQL

```javascript
// Identify access patterns first
// Choose appropriate NoSQL type
// Denormalize for performance
// Plan data migration carefully
```

### 🔹 Between NoSQL Types

```javascript
// Export data from source
// Transform to target format
// Import with appropriate tools
// Update application code
```

---

## 🎯 Interview One-Liner

> Key-Value stores offer simple, ultra-fast lookups for caching and sessions; Document stores provide flexible schemas and rich queries for complex data; Column-Family stores excel at scalable analytics and time-series data with high write throughput.