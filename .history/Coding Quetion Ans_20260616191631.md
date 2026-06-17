## **8. PROBLEM SOLVING / CODING QUESTIONS**

### **Arrays & Strings**
202. Reverse a string
203. Check if string is palindrome
204. Find duplicate elements in array
205. Find second largest element in array
206. Two Sum problem
207. Anagram check
208. Remove duplicates from sorted array
209. Rotate array
210. Find missing number in array (1 to N)
211. Merge two sorted arrays

## ✅ 202. Reverse a string

**String reversal can be implemented using multiple approaches including iterative, recursive, and built-in methods, with different time and space complexities.**

### 🔹 Iterative Approach

**Using two pointers to swap characters**

```java
public class StringReversal {
    
    public static String reverseIterative(String str) {
        if (str == null || str.length() <= 1) {
            return str;
        }
        
        char[] chars = str.toCharArray();
        int left = 0;
        int right = chars.length - 1;
        
        while (left < right) {
            // Swap characters
            char temp = chars[left];
            chars[left] = chars[right];
            chars[right] = temp;
            
            left++;
            right--;
        }
        
        return new String(chars);
    }
    
    // Test
    public static void main(String[] args) {
        System.out.println(reverseIterative("hello")); // "olleh"
        System.out.println(reverseIterative("world")); // "dlrow"
    }
}
```

### 🔹 Recursive Approach

**Using recursion to reverse string**

```java
public class StringReversalRecursive {
    
    public static String reverseRecursive(String str) {
        if (str == null || str.length() <= 1) {
            return str;
        }
        
        // Take first character and append to reversed rest
        return reverseRecursive(str.substring(1)) + str.charAt(0);
    }
    
    // More efficient recursive approach
    public static String reverseRecursiveEfficient(String str) {
        return reverseHelper(str, 0, str.length() - 1);
    }
    
    private static String reverseHelper(String str, int start, int end) {
        if (start >= end) {
            return str;
        }
        
        // Swap characters
        char[] chars = str.toCharArray();
        char temp = chars[start];
        chars[start] = chars[end];
        chars[end] = temp;
        
        return reverseHelper(new String(chars), start + 1, end - 1);
    }
    
    public static void main(String[] args) {
        System.out.println(reverseRecursive("hello")); // "olleh"
        System.out.println(reverseRecursiveEfficient("world")); // "dlrow"
    }
}
```

### 🔹 Using StringBuilder

**Built-in method for reversal**

```java
public class StringReversalBuilder {
    
    public static String reverseUsingBuilder(String str) {
        if (str == null) {
            return null;
        }
        
        return new StringBuilder(str).reverse().toString();
    }
    
    // Using streams (Java 8+)
    public static String reverseUsingStreams(String str) {
        if (str == null) {
            return null;
        }
        
        return str.chars()
                .mapToObj(c -> (char) c)
                .reduce("", (s, c) -> c + s, (s1, s2) -> s2 + s1);
    }
    
    public static void main(String[] args) {
        System.out.println(reverseUsingBuilder("hello")); // "olleh"
        System.out.println(reverseUsingStreams("world")); // "dlrow"
    }
}
```

### 🔹 Best Practices

```java
// Use StringBuilder.reverse() for simple reversals (most efficient)
// Use iterative approach for large strings to avoid recursion stack overflow
// Handle null and empty strings properly
// Consider Unicode characters (surrogate pairs) for international text
// Use char[] for mutable operations to avoid creating multiple String objects
```

### 🔹 Time and Space Complexity

```java
// Iterative approach: O(n) time, O(n) space (due to char array)
// Recursive approach: O(n) time, O(n) space (recursion stack)
// StringBuilder approach: O(n) time, O(n) space
// Where n is the length of the string
```

---

## 🎯 Interview One-Liner

> String reversal: iterative two-pointer swap (O(n) time/space), recursive approach, or StringBuilder.reverse(); handle edge cases like null/empty strings.

---

## ✅ 203. Check if string is palindrome

**A palindrome string reads the same forwards and backwards. Palindrome checking can be implemented with different approaches considering case sensitivity and special characters.**

### 🔹 Two Pointer Approach

**Most efficient palindrome check**

```java
public class PalindromeChecker {
    
    public static boolean isPalindrome(String str) {
        if (str == null) {
            return false;
        }
        
        // Remove spaces and convert to lowercase for case-insensitive check
        String cleanStr = str.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        
        int left = 0;
        int right = cleanStr.length() - 1;
        
        while (left < right) {
            if (cleanStr.charAt(left) != cleanStr.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        
        return true;
    }
    
    // Case-sensitive version
    public static boolean isPalindromeCaseSensitive(String str) {
        if (str == null) {
            return false;
        }
        
        int left = 0;
        int right = str.length() - 1;
        
        while (left < right) {
            if (str.charAt(left) != str.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        
        return true;
    }
    
    public static void main(String[] args) {
        System.out.println(isPalindrome("A man, a plan, a canal: Panama")); // true
        System.out.println(isPalindrome("race a car")); // false
        System.out.println(isPalindromeCaseSensitive("Racecar")); // false
    }
}
```

### 🔹 Using StringBuilder

**Simple reversal comparison**

```java
public class PalindromeCheckerSimple {
    
    public static boolean isPalindrome(String str) {
        if (str == null) {
            return false;
        }
        
        String cleanStr = str.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        String reversed = new StringBuilder(cleanStr).reverse().toString();
        
        return cleanStr.equals(reversed);
    }
    
    public static void main(String[] args) {
        System.out.println(isPalindrome("A man, a plan, a canal: Panama")); // true
    }
}
```

### 🔹 Recursive Approach

**Recursive palindrome check**

```java
public class PalindromeCheckerRecursive {
    
    public static boolean isPalindrome(String str) {
        if (str == null) {
            return false;
        }
        
        String cleanStr = str.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        return isPalindromeHelper(cleanStr, 0, cleanStr.length() - 1);
    }
    
    private static boolean isPalindromeHelper(String str, int left, int right) {
        if (left >= right) {
            return true;
        }
        
        if (str.charAt(left) != str.charAt(right)) {
            return false;
        }
        
        return isPalindromeHelper(str, left + 1, right - 1);
    }
    
    public static void main(String[] args) {
        System.out.println(isPalindrome("radar")); // true
    }
}
```

### 🔹 Best Practices

```java
// Clean input by removing special characters and spaces for practical palindromes
// Handle case sensitivity based on requirements
// Use two-pointer approach for O(n) time and O(1) extra space
// Consider Unicode characters and surrogate pairs
// Handle null and empty strings appropriately
```

### 🔹 Edge Cases

```java
// Test cases
System.out.println(isPalindrome(null)); // false
System.out.println(isPalindrome("")); // true
System.out.println(isPalindrome("a")); // true
System.out.println(isPalindrome("aa")); // true
System.out.println(isPalindrome("aba")); // true
System.out.println(isPalindrome("Aba")); // true (case-insensitive)
```

---

## 🎯 Interview One-Liner

> Palindrome check: two-pointer approach comparing characters from ends; clean input (remove spaces/punctuation), handle case sensitivity; O(n) time, O(1) space.

---

## ✅ 204. Find duplicate elements in array

**Finding duplicates in an array can be done using various data structures with different time and space complexities, each suitable for different scenarios.**

### 🔹 Using HashSet

**Most efficient approach for finding duplicates**

```java
import java.util.*;

public class FindDuplicates {
    
    public static List<Integer> findDuplicates(int[] arr) {
        List<Integer> duplicates = new ArrayList<>();
        Set<Integer> seen = new HashSet<>();
        
        for (int num : arr) {
            if (!seen.add(num)) {
                // num is already in set, it's a duplicate
                if (!duplicates.contains(num)) {
                    duplicates.add(num);
                }
            }
        }
        
        return duplicates;
    }
    
    // Find all occurrences of duplicates
    public static Map<Integer, Integer> findDuplicateCounts(int[] arr) {
        Map<Integer, Integer> countMap = new HashMap<>();
        
        for (int num : arr) {
            countMap.put(num, countMap.getOrDefault(num, 0) + 1);
        }
        
        // Filter only duplicates
        Map<Integer, Integer> duplicates = new HashMap<>();
        for (Map.Entry<Integer, Integer> entry : countMap.entrySet()) {
            if (entry.getValue() > 1) {
                duplicates.put(entry.getKey(), entry.getValue());
            }
        }
        
        return duplicates;
    }
    
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 2, 5, 3, 6, 3};
        System.out.println("Duplicates: " + findDuplicates(arr)); // [2, 3]
        System.out.println("Duplicate counts: " + findDuplicateCounts(arr)); // {2=2, 3=3}
    }
}
```

### 🔹 Using Sorting

**Sort and find adjacent duplicates**

```java
import java.util.*;

public class FindDuplicatesSorting {
    
    public static List<Integer> findDuplicates(int[] arr) {
        Arrays.sort(arr);
        List<Integer> duplicates = new ArrayList<>();
        
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] == arr[i - 1] && (duplicates.isEmpty() || 
            duplicates.get(duplicates.size() - 1) != arr[i])) 
            {
                duplicates.add(arr[i]);
            }
        }
        
        return duplicates;
    }
    
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 2, 5, 3, 6, 3};
        System.out.println("Duplicates: " + findDuplicates(arr)); // [2, 3]
    }
}
```

### 🔹 Using Frequency Array

**For arrays with known range**

```java
import java.util.*;

public class FindDuplicatesFrequency {
    
    public static List<Integer> findDuplicates(int[] arr, int maxValue) {
        List<Integer> duplicates = new ArrayList<>();
        int[] frequency = new int[maxValue + 1];
        
        // Count frequencies
        for (int num : arr) {
            frequency[num]++;
        }
        
        // Find duplicates
        for (int i = 0; i < frequency.length; i++) {
            if (frequency[i] > 1) {
                duplicates.add(i);
            }
        }
        
        return duplicates;
    }
    
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 2, 5, 3, 6, 3};
        System.out.println("Duplicates: " + findDuplicates(arr, 10)); // [2, 3]
    }
}
```

### 🔹 Best Practices

```java
// Use HashSet for O(n) time and O(n) space when order doesn't matter
// Use sorting when you need sorted duplicates or can't use extra space
// Use frequency array when array values are in a known small range
// Handle negative numbers and large ranges appropriately
// Consider using streams for functional approach
```

### 🔹 Time and Space Complexity

```java
// HashSet approach: O(n) time, O(n) space
// Sorting approach: O(n log n) time, O(1) extra space (modifies array)
// Frequency array: O(n) time, O(max_value) space
```

---

## 🎯 Interview One-Liner

> Find duplicates: HashSet (O(n) time/space), sorting (O(n log n) time), frequency array (for bounded ranges); track seen elements or count frequencies.

---

## ✅ 205. Find second largest element in array

**Finding the second largest element requires careful handling of edge cases like duplicates, negative numbers, and arrays with less than 2 elements.**

### 🔹 Efficient Single Pass

**Find both largest and second largest in one pass**

```java
public class SecondLargest {
    
    public static Integer findSecondLargest(int[] arr) {
        if (arr == null || arr.length < 2) {
            return null; // Not enough elements
        }
        
        int largest = Integer.MIN_VALUE;
        int secondLargest = Integer.MIN_VALUE;
        
        for (int num : arr) {
            if (num > largest) {
                secondLargest = largest;
                largest = num;
            } else if (num > secondLargest && num != largest) {
                secondLargest = num;
            }
        }
        
        // Check if second largest was found
        return secondLargest == Integer.MIN_VALUE ? null : secondLargest;
    }
    
    public static void main(String[] args) {
        int[] arr1 = {12, 35, 1, 10, 34, 1};
        System.out.println("Second largest: " + findSecondLargest(arr1)); // 34
        
        int[] arr2 = {10, 10, 10};
        System.out.println("Second largest: " + findSecondLargest(arr2)); // null
        
        int[] arr3 = {5};
        System.out.println("Second largest: " + findSecondLargest(arr3)); // null
    }
}
```

### 🔹 Using Sorting

**Sort and find second element**

```java
import java.util.*;

public class SecondLargestSorting {
    
    public static Integer findSecondLargest(int[] arr) {
        if (arr == null || arr.length < 2) {
            return null;
        }
        
        Arrays.sort(arr);
        
        // Find the largest element from the end
        int n = arr.length;
        int largest = arr[n - 1];
        
        // Find the second largest (skip duplicates of largest)
        for (int i = n - 2; i >= 0; i--) {
            if (arr[i] != largest) {
                return arr[i];
            }
        }
        
        return null; // All elements are same
    }
    
    public static void main(String[] args) {
        int[] arr = {12, 35, 1, 10, 34, 1};
        System.out.println("Second largest: " + findSecondLargest(arr)); // 34
    }
}
```

### 🔹 Using Java Streams

**Functional approach**

```java
import java.util.*;

public class SecondLargestStreams {
    
    public static OptionalInt findSecondLargest(int[] arr) {
        if (arr == null || arr.length < 2) {
            return OptionalInt.empty();
        }
        
        return Arrays.stream(arr)
                .distinct() // Remove duplicates
                .boxed()
                .sorted(Comparator.reverseOrder())
                .skip(1) // Skip the largest
                .findFirst()
                .map(Integer::intValue);
    }
    
    public static void main(String[] args) {
        int[] arr = {12, 35, 1, 10, 34, 1};
        OptionalInt result = findSecondLargest(arr);
        System.out.println("Second largest: " + (result.isPresent() ? result.getAsInt() : "Not found"));
    }
}
```

### 🔹 Best Practices

```java
// Handle edge cases: null array, single element, all duplicates
// Use single pass approach for efficiency (O(n) time)
// Consider using Integer.MIN_VALUE for initialization
// Handle negative numbers properly
// Return null or Optional for cases where second largest doesn't exist
```

### 🔹 Edge Cases

```java
// Test cases
int[] arr1 = {5, 5, 5}; // null - all same
int[] arr2 = {1}; // null - single element
int[] arr3 = {1, 2}; // 1
int[] arr4 = {3, 1, 2}; // 2
int[] arr5 = {-1, -5, -3, -2}; // -2
int[] arr6 = {Integer.MAX_VALUE, Integer.MIN_VALUE}; // Integer.MIN_VALUE
```

---

## 🎯 Interview One-Liner

> Second largest: single pass tracking largest and second largest (O(n) time); handle duplicates, edge cases; return null if doesn't exist.

---

## ✅ 206. Two Sum problem

**The Two Sum problem finds two numbers in an array that add up to a target sum, with optimal solutions using hash maps for O(n) time complexity.**

### 🔹 Brute Force Approach

**Check all pairs - O(n²) time**

```java
import java.util.*;

public class TwoSumBruteForce {
    
    public static int[] twoSum(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{}; // No solution found
    }
    
    public static void main(String[] args) {
        int[] nums = {2, 7, 11, 15};
        int target = 9;
        int[] result = twoSum(nums, target);
        System.out.println("Indices: [" + result[0] + ", " + result[1] + "]"); // [0, 1]
    }
}
```

### 🔹 Optimal HashMap Approach

**One-pass hash map solution**

```java
import java.util.*;

public class TwoSumOptimal {
    
    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> numToIndex = new HashMap<>();
        
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            
            if (numToIndex.containsKey(complement)) {
                return new int[]{numToIndex.get(complement), i};
            }
            
            numToIndex.put(nums[i], i);
        }
        
        return new int[]{}; // No solution found
    }
    
    public static void main(String[] args) {
        int[] nums = {2, 7, 11, 15};
        int target = 9;
        int[] result = twoSum(nums, target);
        System.out.println("Indices: [" + result[0] + ", " + result[1] + "]"); // [0, 1]
    }
}
```

### 🔹 Two Pointer Approach (Sorted Array)

**For sorted arrays**

```java
import java.util.*;

public class TwoSumTwoPointers {
    
    public static int[] twoSum(int[] nums, int target) {
        // Sort the array with indices
        int[][] numsWithIndices = new int[nums.length][2];
        for (int i = 0; i < nums.length; i++) {
            numsWithIndices[i] = new int[]{nums[i], i};
        }
        
        Arrays.sort(numsWithIndices, Comparator.comparingInt(a -> a[0]));
        
        int left = 0;
        int right = nums.length - 1;
        
        while (left < right) {
            int sum = numsWithIndices[left][0] + numsWithIndices[right][0];
            
            if (sum == target) {
                return new int[]{numsWithIndices[left][1], numsWithIndices[right][1]};
            } else if (sum < target) {
                left++;
            } else {
                right--;
            }
        }
        
        return new int[]{}; // No solution found
    }
    
    public static void main(String[] args) {
        int[] nums = {2, 7, 11, 15};
        int target = 9;
        int[] result = twoSum(nums, target);
        System.out.println("Indices: [" + result[0] + ", " + result[1] + "]"); // [0, 1]
    }
}
```

### 🔹 Best Practices

```java
// Use HashMap for O(n) time and O(n) space (most efficient for unsorted arrays)
// Use two pointers for O(n log n) time if array can be sorted
// Handle edge cases: no solution, duplicate numbers, negative numbers
// Return indices in any order unless specified
// Consider if multiple pairs exist (return any valid pair)
```

### 🔹 Time and Space Complexity

```java
// Brute force: O(n²) time, O(1) space
// HashMap: O(n) time, O(n) space
// Two pointers: O(n log n) time (due to sorting), O(1) extra space
```

---

## 🎯 Interview One-Liner

> Two Sum: HashMap stores complement (target - current), check if exists; O(n) time/space; handles unsorted arrays efficiently.

---

## ✅ 207. Anagram check

**Anagram check determines if two strings contain the same characters with the same frequencies, with multiple approaches offering different trade-offs.**

### 🔹 Sorting Approach

**Sort both strings and compare**

```java
import java.util.*;

public class AnagramCheck {
    
    public static boolean isAnagram(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() != s2.length()) {
            return false;
        }
        
        char[] arr1 = s1.toLowerCase().toCharArray();
        char[] arr2 = s2.toLowerCase().toCharArray();
        
        Arrays.sort(arr1);
        Arrays.sort(arr2);
        
        return Arrays.equals(arr1, arr2);
    }
    
    public static void main(String[] args) {
        System.out.println(isAnagram("listen", "silent")); // true
        System.out.println(isAnagram("hello", "world")); // false
        System.out.println(isAnagram("Listen", "Silent")); // true (case-insensitive)
    }
}
```

### 🔹 Frequency Count Approach

**Count character frequencies**

```java
import java.util.*;

public class AnagramCheckFrequency {
    
    public static boolean isAnagram(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() != s2.length()) {
            return false;
        }
        
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();
        
        int[] count = new int[26]; // Assuming only lowercase letters
        
        for (char c : s1.toCharArray()) {
            count[c - 'a']++;
        }
        
        for (char c : s2.toCharArray()) {
            count[c - 'a']--;
        }
        
        for (int i : count) {
            if (i != 0) {
                return false;
            }
        }
        
        return true;
    }
    
    // Unicode-safe version
    public static boolean isAnagramUnicode(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() != s2.length()) {
            return false;
        }
        
        Map<Character, Integer> countMap = new HashMap<>();
        
        for (char c : s1.toCharArray()) {
            countMap.put(c, countMap.getOrDefault(c, 0) + 1);
        }
        
        for (char c : s2.toCharArray()) {
            countMap.put(c, countMap.getOrDefault(c, 0) - 1);
        }
        
        for (int count : countMap.values()) {
            if (count != 0) {
                return false;
            }
        }
        
        return true;
    }
    
    public static void main(String[] args) {
        System.out.println(isAnagram("listen", "silent")); // true
        System.out.println(isAnagramUnicode("résumé", "émesur")); // true
    }
}
```

### 🔹 Best Practices

```java
// Handle case sensitivity based on requirements
// Check length first for early exit
// Use frequency count for O(n) time complexity
// Handle Unicode characters properly
// Consider whitespace and punctuation handling
```

### 🔹 Edge Cases

```java
// Test cases
System.out.println(isAnagram(null, "test")); // false
System.out.println(isAnagram("test", null)); // false
System.out.println(isAnagram("test", "tset")); // true
System.out.println(isAnagram("Test", "test")); // true (case-insensitive)
System.out.println(isAnagram("aabb", "abab")); // true
System.out.println(isAnagram("hello", "helo")); // false (different lengths)
```

---

## 🎯 Interview One-Liner

> Anagram check: sort both strings and compare, or use frequency count array/map; O(n log n) vs O(n) time; handle case sensitivity and Unicode.

---

## ✅ 208. Remove duplicates from sorted array

**Removing duplicates from a sorted array requires in-place modification to maintain sorted order, with optimal solutions achieving O(n) time complexity.**

### 🔹 Two Pointer Approach

**Efficient in-place removal**

```java
public class RemoveDuplicates {
    
    public static int removeDuplicates(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        int i = 0; // Slow pointer for unique elements
        
        for (int j = 1; j < nums.length; j++) {
            if (nums[j] != nums[i]) {
                i++;
                nums[i] = nums[j];
            }
        }
        
        return i + 1; // Length of array with unique elements
    }
    
    public static void main(String[] args) {
        int[] nums = {1, 1, 2, 2, 3, 4, 4, 5};
        int length = removeDuplicates(nums);
        
        System.out.println("New length: " + length);
        System.out.print("Array: ");
        for (int i = 0; i < length; i++) {
            System.out.print(nums[i] + " ");
        }
        // Output: New length: 5, Array: 1 2 3 4 5
    }
}
```

### 🔹 Using HashSet (Unsorted Result)

**For unsorted arrays or when order doesn't matter**

```java
import java.util.*;

public class RemoveDuplicatesUnsorted {
    
    public static int[] removeDuplicates(int[] nums) {
        Set<Integer> uniqueSet = new LinkedHashSet<>();
        
        for (int num : nums) {
            uniqueSet.add(num);
        }
        
        return uniqueSet.stream().mapToInt(Integer::intValue).toArray();
    }
    
    public static void main(String[] args) {
        int[] nums = {4, 2, 2, 1, 3, 4, 1};
        int[] result = removeDuplicates(nums);
        System.out.println(Arrays.toString(result)); // [4, 2, 1, 3]
    }
}
```

### 🔹 Best Practices

```java
// Use two pointers for sorted arrays (O(n) time, O(1) space)
// Return the length of unique elements, not the array
// Handle edge cases: empty array, single element, all duplicates
// For unsorted arrays, consider using HashSet
// Preserve relative order when possible
```

### 🔹 Edge Cases

```java
// Test cases
int[] arr1 = {}; // length 0
int[] arr2 = {1}; // length 1
int[] arr3 = {1, 1, 1}; // length 1
int[] arr4 = {1, 2, 3}; // length 3
int[] arr5 = {1, 1, 2, 2, 3, 3, 3}; // length 3
```

---

## 🎯 Interview One-Liner

> Remove duplicates from sorted array: two pointers, slow/fast approach; move unique elements forward; O(n) time, O(1) space; return new length.

---

## ✅ 209. Rotate array

**Array rotation shifts elements by k positions, with efficient solutions handling large k values and negative rotations.**

### 🔹 Using Extra Space

**Simple rotation with temporary array**

```java
import java.util.*;

public class RotateArray {
    
    public static void rotate(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k == 0) {
            return;
        }
        
        int n = nums.length;
        k = k % n; // Handle k > n
        
        int[] temp = new int[n];
        
        // Copy last k elements to front
        for (int i = 0; i < k; i++) {
            temp[i] = nums[n - k + i];
        }
        
        // Copy first n-k elements after k
        for (int i = 0; i < n - k; i++) {
            temp[k + i] = nums[i];
        }
        
        // Copy back to original array
        System.arraycopy(temp, 0, nums, 0, n);
    }
    
    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4, 5, 6, 7};
        rotate(nums, 3);
        System.out.println(Arrays.toString(nums)); // [5, 6, 7, 1, 2, 3, 4]
    }
}
```

### 🔹 In-Place Rotation

**Reverse approach - most efficient**

```java
public class RotateArrayInPlace {
    
    public static void rotate(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k == 0) {
            return;
        }
        
        int n = nums.length;
        k = k % n;
        
        // Reverse entire array
        reverse(nums, 0, n - 1);
        
        // Reverse first k elements
        reverse(nums, 0, k - 1);
        
        // Reverse remaining elements
        reverse(nums, k, n - 1);
    }
    
    private static void reverse(int[] nums, int start, int end) {
        while (start < end) {
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start++;
            end--;
        }
    }
    
    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4, 5, 6, 7};
        rotate(nums, 3);
        System.out.println(Arrays.toString(nums)); // [5, 6, 7, 1, 2, 3, 4]
    }
}
```

### 🔹 Cyclic Replacement

**Rotate elements one by one**

```java
public class RotateArrayCyclic {
    
    public static void rotate(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k == 0) {
            return;
        }
        
        int n = nums.length;
        k = k % n;
        
        int count = 0;
        for (int start = 0; count < n; start++) {
            int current = start;
            int prev = nums[start];
            
            do {
                int next = (current + k) % n;
                int temp = nums[next];
                nums[next] = prev;
                prev = temp;
                current = next;
                count++;
            } while (start != current);
        }
    }
    
    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4, 5, 6, 7};
        rotate(nums, 3);
        System.out.println(Arrays.toString(nums)); // [5, 6, 7, 1, 2, 3, 4]
    }
}
```

### 🔹 Best Practices

```java
// Handle k > n by using k % n
// Use reverse approach for O(n) time and O(1) space
// Handle edge cases: empty array, k = 0, negative k
// Consider direction of rotation (left vs right)
```

### 🔹 Time and Space Complexity

```java
// Extra space: O(n) time, O(n) space
// Reverse approach: O(n) time, O(1) space
// Cyclic replacement: O(n) time, O(1) space
```

---

## 🎯 Interview One-Liner

> Array rotation: reverse entire array, then reverse first k and remaining parts; O(n) time, O(1) space; handle k > n with modulo.

---

## ✅ 210. Find missing number in array (1 to N)

**Finding the missing number in an array containing numbers from 1 to N can be solved using mathematical formulas or bitwise operations.**

### 🔹 Mathematical Approach (Sum Formula)

**Calculate expected sum minus actual sum**

```java
public class MissingNumber {
    
    public static int findMissingNumber(int[] nums, int n) {
        // Expected sum of numbers from 1 to n
        int expectedSum = n * (n + 1) / 2;
        
        // Actual sum of array elements
        int actualSum = 0;
        for (int num : nums) {
            actualSum += num;
        }
        
        return expectedSum - actualSum;
    }
    
    public static void main(String[] args) {
        int[] nums = {1, 2, 4, 5, 6}; // Missing 3, n = 6
        System.out.println("Missing number: " + findMissingNumber(nums, 6)); // 3
    }
}
```

### 🔹 XOR Approach

**Using XOR properties for efficiency**

```java
public class MissingNumberXOR {
    
    public static int findMissingNumber(int[] nums, int n) {
        int xor1 = 0;
        int xor2 = 0;
        
        // XOR all numbers from 1 to n
        for (int i = 1; i <= n; i++) {
            xor1 ^= i;
        }
        
        // XOR all numbers in array
        for (int num : nums) {
            xor2 ^= num;
        }
        
        return xor1 ^ xor2;
    }
    
    public static void main(String[] args) {
        int[] nums = {1, 2, 4, 5, 6}; // Missing 3, n = 6
        System.out.println("Missing number: " + findMissingNumber(nums, 6)); // 3
    }
}
```

### 🔹 Best Practices

```java
// Use XOR approach to avoid integer overflow with large n
// Handle edge cases: missing first/last number, n = 1
// Consider using mathematical formula for simplicity
// XOR approach works for any range, not just 1 to n
```

### 🔹 Edge Cases

```java
// Test cases
int[] arr1 = {1}; // n = 2, missing 2
int[] arr2 = {2}; // n = 2, missing 1
int[] arr3 = {1, 2, 3, 4}; // n = 5, missing 5
int[] arr4 = {2, 3, 4, 5}; // n = 5, missing 1
```

---

## 🎯 Interview One-Liner

> Missing number (1 to N): XOR all numbers 1 to N with array elements; result is missing number; O(n) time, handles overflow better than sum.

---

## ✅ 211. Merge two sorted arrays

**Merging two sorted arrays can be done in-place or with extra space, with different approaches suitable for different constraints.**

### 🔹 Using Extra Space

**Simple merge with temporary array**

```java
import java.util.*;

public class MergeSortedArrays {
    
    public static int[] merge(int[] arr1, int[] arr2) {
        int[] result = new int[arr1.length + arr2.length];
        
        int i = 0, j = 0, k = 0;
        
        // Merge while both arrays have elements
        while (i < arr1.length && j < arr2.length) {
            if (arr1[i] <= arr2[j]) {
                result[k++] = arr1[i++];
            } else {
                result[k++] = arr2[j++];
            }
        }
        
        // Copy remaining elements
        while (i < arr1.length) {
            result[k++] = arr1[i++];
        }
        
        while (j < arr2.length) {
            result[k++] = arr2[j++];
        }
        
        return result;
    }
    
    public static void main(String[] args) {
        int[] arr1 = {1, 3, 5, 7};
        int[] arr2 = {2, 4, 6, 8};
        int[] merged = merge(arr1, arr2);
        System.out.println(Arrays.toString(merged)); // [1, 2, 3, 4, 5, 6, 7, 8]
    }
}
```

### 🔹 In-Place Merge (Gap Method)

**Merge second array into first (assuming first has enough space)**

```java
public class MergeInPlace {
    
    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = m - 1; // Last element of nums1
        int j = n - 1; // Last element of nums2
        int k = m + n - 1; // Last position of merged array
        
        // Merge from the end
        while (i >= 0 && j >= 0) {
            if (nums1[i] > nums2[j]) {
                nums1[k--] = nums1[i--];
            } else {
                nums1[k--] = nums2[j--];
            }
        }
        
        // Copy remaining elements from nums2
        while (j >= 0) {
            nums1[k--] = nums2[j--];
        }
        
        // nums1 is already in place for remaining elements
    }
    
    public static void main(String[] args) {
        int[] nums1 = {1, 2, 3, 0, 0, 0}; // nums1 has extra space
        int m = 3;
        int[] nums2 = {2, 5, 6};
        int n = 3;
        
        merge(nums1, m, nums2, n);
        System.out.println(Arrays.toString(nums1)); // [1, 2, 2, 3, 5, 6]
    }
}
```

### 🔹 Best Practices

```java
// Use two pointers for efficient merging
// Handle edge cases: empty arrays, one array empty
// Consider in-place merging when space is constrained
// Maintain stability (equal elements keep relative order)
```

### 🔹 Time and Space Complexity

```java
// Extra space approach: O(m + n) time and space
// In-place merge: O(m + n) time, O(1) extra space
```

---

## 🎯 Interview One-Liner

> Merge sorted arrays: two pointers from start/end, compare and merge; O(m+n) time; in-place possible if first array has space.

---

### **Collections**
212. Count frequency of elements (using HashMap)
213. Find first non-repeating character
214. Group anagrams together
215. Sort HashMap by values
216. Find top K frequent elements

## ✅ 212. Count frequency of elements (using HashMap)

**Counting element frequencies using HashMap provides efficient O(n) time complexity for frequency analysis in collections.**

### 🔹 Basic Frequency Count

**Using HashMap to count occurrences**

```java
import java.util.*;

public class FrequencyCount {
    
    public static Map<Integer, Integer> countFrequency(int[] arr) {
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        
        for (int num : arr) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }
        
        return frequencyMap;
    }
    
    public static Map<String, Integer> countFrequency(String[] arr) {
        Map<String, Integer> frequencyMap = new HashMap<>();
        
        for (String str : arr) {
            frequencyMap.put(str, frequencyMap.getOrDefault(str, 0) + 1);
        }
        
        return frequencyMap;
    }
    
    public static void main(String[] args) {
        int[] nums = {1, 2, 2, 3, 3, 3, 4};
        Map<Integer, Integer> freq = countFrequency(nums);
        System.out.println("Frequency: " + freq); // {1=1, 2=2, 3=3, 4=1}
        
        String[] words = {"apple", "banana", "apple", "cherry", "banana", "apple"};
        Map<String, Integer> wordFreq = countFrequency(words);
        System.out.println("Word frequency: " + wordFreq); // {banana=2, apple=3, cherry=1}
    }
}
```

### 🔹 Using Java 8 Streams

**Functional approach with streams**

```java
import java.util.*;
import java.util.stream.Collectors;

public class FrequencyCountStreams {
    
    public static Map<Integer, Long> countFrequencyStreams(int[] arr) {
        return Arrays.stream(arr)
                .boxed()
                .collect(Collectors.groupingBy(
                    num -> num,
                    Collectors.counting()
                ));
    }
    
    public static Map<String, Long> countFrequencyStreams(String[] arr) {
        return Arrays.stream(arr)
                .collect(Collectors.groupingBy(
                    str -> str,
                    Collectors.counting()
                ));
    }
    
    public static void main(String[] args) {
        int[] nums = {1, 2, 2, 3, 3, 3, 4};
        Map<Integer, Long> freq = countFrequencyStreams(nums);
        System.out.println("Frequency: " + freq); // {1=1, 2=2, 3=3, 4=1}
    }
}
```

### 🔹 Best Practices

```java
// Use HashMap for O(n) time complexity
// Use getOrDefault() to avoid null checks
// Consider using streams for functional programming
// Handle null values appropriately
// Use appropriate map types (HashMap, TreeMap, LinkedHashMap)
```

### 🔹 Time and Space Complexity

```java
// HashMap approach: O(n) time, O(n) space
// Streams approach: O(n) time, O(n) space
```

---

## 🎯 Interview One-Liner

> Frequency count: HashMap with getOrDefault(key, 0) + 1; O(n) time/space; use streams for functional approach.

---

## ✅ 213. Find first non-repeating character

**Finding the first non-repeating character requires tracking character frequencies and their first occurrence positions.**

### 🔹 Using HashMap and Queue

**Track frequency and order of appearance**

```java
import java.util.*;

public class FirstNonRepeating {
    
    public static Character findFirstNonRepeating(String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        
        Map<Character, Integer> frequency = new HashMap<>();
        Queue<Character> queue = new LinkedList<>();
        
        for (char c : str.toCharArray()) {
            frequency.put(c, frequency.getOrDefault(c, 0) + 1);
            
            if (frequency.get(c) == 1) {
                queue.offer(c);
            }
        }
        
        // Find first character with frequency 1
        while (!queue.isEmpty()) {
            char c = queue.poll();
            if (frequency.get(c) == 1) {
                return c;
            }
        }
        
        return null; // No non-repeating character
    }
    
    public static void main(String[] args) {
        System.out.println(findFirstNonRepeating("leetcode")); // 'l'
        System.out.println(findFirstNonRepeating("loveleetcode")); // 'v'
        System.out.println(findFirstNonRepeating("aabbcc")); // null
    }
}
```

### 🔹 Two Pass Approach

**Count frequencies first, then find first non-repeating**

```java
import java.util.*;

public class FirstNonRepeatingTwoPass {
    
    public static Character findFirstNonRepeating(String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        
        Map<Character, Integer> frequency = new HashMap<>();
        
        // First pass: count frequencies
        for (char c : str.toCharArray()) {
            frequency.put(c, frequency.getOrDefault(c, 0) + 1);
        }
        
        // Second pass: find first character with frequency 1
        for (char c : str.toCharArray()) {
            if (frequency.get(c) == 1) {
                return c;
            }
        }
        
        return null;
    }
    
    public static void main(String[] args) {
        System.out.println(findFirstNonRepeating("leetcode")); // 'l'
    }
}
```

### 🔹 Best Practices

```java
// Use HashMap for frequency counting
// Consider order of appearance for "first" requirement
// Handle case sensitivity based on requirements
// Return null or special character for no result
```

### 🔹 Time and Space Complexity

```java
// Two pass approach: O(n) time, O(1) space (limited character set)
// Queue approach: O(n) time, O(n) space
```

---

## 🎯 Interview One-Liner

> First non-repeating character: two passes - count frequencies, then scan for first with count 1; O(n) time, O(1) space for ASCII.

---

## ✅ 214. Group anagrams together

**Grouping anagrams involves categorizing strings that contain the same characters with the same frequencies.**

### 🔹 Sorting Approach

**Sort characters and use as key**

```java
import java.util.*;

public class GroupAnagrams {
    
    public static List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> anagramGroups = new HashMap<>();
        
        for (String str : strs) {
            // Sort characters to create key
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            String key = new String(chars);
            
            // Add to group
            anagramGroups.computeIfAbsent(key, k -> new ArrayList<>()).add(str);
        }
        
        return new ArrayList<>(anagramGroups.values());
    }
    
    public static void main(String[] args) {
        String[] strs = {"eat", "tea", "tan", "ate", "nat", "bat"};
        List<List<String>> result = groupAnagrams(strs);
        System.out.println(result);
        // [[eat, tea, ate], [tan, nat], [bat]]
    }
}
```

### 🔹 Frequency Count Approach

**Use character frequency as key**

```java
import java.util.*;

public class GroupAnagramsFrequency {
    
    public static List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> anagramGroups = new HashMap<>();
        
        for (String str : strs) {
            // Create frequency key
            String key = getFrequencyKey(str);
            
            // Add to group
            anagramGroups.computeIfAbsent(key, k -> new ArrayList<>()).add(str);
        }
        
        return new ArrayList<>(anagramGroups.values());
    }
    
    private static String getFrequencyKey(String str) {
        int[] count = new int[26];
        for (char c : str.toCharArray()) {
            count[c - 'a']++;
        }
        
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < 26; i++) {
            if (count[i] > 0) {
                key.append((char)('a' + i)).append(count[i]);
            }
        }
        
        return key.toString();
    }
    
    public static void main(String[] args) {
        String[] strs = {"eat", "tea", "tan", "ate", "nat", "bat"};
        List<List<String>> result = groupAnagrams(strs);
        System.out.println(result);
    }
}
```

### 🔹 Best Practices

```java
// Use sorted string as key for simplicity
// Use frequency count for better performance with long strings
// Handle case sensitivity and Unicode characters
// Return groups in any order unless specified
```

### 🔹 Time and Space Complexity

```java
// Sorting approach: O(n * k log k) time, O(n * k) space
// Frequency approach: O(n * k) time, O(n * k) space
// Where n is number of strings, k is average string length
```

---

## 🎯 Interview One-Liner

> Group anagrams: sort each string as key, group in HashMap; O(n k log k) time; frequency count alternative for better performance.

---

## ✅ 215. Sort HashMap by values

**Sorting a HashMap by values requires converting to a different data structure since HashMap doesn't guarantee order.**

### 🔹 Using TreeMap

**Sort by values using TreeMap**

```java
import java.util.*;

public class SortMapByValues {
    
    public static Map<String, Integer> sortByValues(Map<String, Integer> map) {
        // Create a list of entries
        List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
        
        // Sort the list by values
        list.sort(Map.Entry.comparingByValue());
        
        // Create a LinkedHashMap to preserve insertion order
        Map<String, Integer> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        
        return sortedMap;
    }
    
    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        map.put("apple", 3);
        map.put("banana", 1);
        map.put("cherry", 2);
        
        Map<String, Integer> sorted = sortByValues(map);
        System.out.println(sorted); // {banana=1, cherry=2, apple=3}
    }
}
```

### 🔹 Using Java 8 Streams

**Functional approach with streams**

```java
import java.util.*;
import java.util.stream.Collectors;

public class SortMapByValuesStreams {
    
    public static Map<String, Integer> sortByValues(Map<String, Integer> map) {
        return map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    Map.Entry::getValue,
                    (e1, e2) -> e1,
                    LinkedHashMap::new
                ));
    }
    
    // Sort in descending order
    public static Map<String, Integer> sortByValuesDescending(Map<String, Integer> map) {
        return map.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    Map.Entry::getValue,
                    (e1, e2) -> e1,
                    LinkedHashMap::new
                ));
    }
    
    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        map.put("apple", 3);
        map.put("banana", 1);
        map.put("cherry", 2);
        
        System.out.println(sortByValues(map)); // {banana=1, cherry=2, apple=3}
        System.out.println(sortByValuesDescending(map)); // {apple=3, cherry=2, banana=1}
    }
}
```

### 🔹 Best Practices

```java
// Use LinkedHashMap to preserve sorted order
// Handle duplicate values appropriately
// Use streams for functional programming
// Consider custom comparators for complex sorting
```

### 🔹 Time and Space Complexity

```java
// Sorting approach: O(n log n) time, O(n) space
// Where n is the number of entries in the map
```

---

## 🎯 Interview One-Liner

> Sort HashMap by values: convert to list, sort by values, collect to LinkedHashMap; O(n log n) time; use streams for functional approach.

---

## ✅ 216. Find top K frequent elements

**Finding top K frequent elements requires counting frequencies and then selecting the most frequent ones.**

### 🔹 Using HashMap and PriorityQueue

**Count frequencies, then use min-heap**

```java
import java.util.*;

public class TopKFrequent {
    
    public static List<Integer> topKFrequent(int[] nums, int k) {
        // Count frequencies
        Map<Integer, Integer> frequency = new HashMap<>();
        for (int num : nums) {
            frequency.put(num, frequency.getOrDefault(num, 0) + 1);
        }
        
        // Use min-heap to keep top k elements
        PriorityQueue<Map.Entry<Integer, Integer>> minHeap = 
            new PriorityQueue<>((a, b) -> a.getValue() - b.getValue());
        
        for (Map.Entry<Integer, Integer> entry : frequency.entrySet()) {
            minHeap.offer(entry);
            if (minHeap.size() > k) {
                minHeap.poll(); // Remove least frequent
            }
        }
        
        // Extract elements from heap
        List<Integer> result = new ArrayList<>();
        while (!minHeap.isEmpty()) {
            result.add(minHeap.poll().getKey());
        }
        
        // Reverse to get descending order
        Collections.reverse(result);
        return result;
    }
    
    public static void main(String[] args) {
        int[] nums = {1, 1, 1, 2, 2, 3};
        List<Integer> result = topKFrequent(nums, 2);
        System.out.println(result); // [1, 2]
    }
}
```

### 🔹 Using Bucket Sort

**More efficient for large frequency ranges**

```java
import java.util.*;

public class TopKFrequentBucket {
    
    public static List<Integer> topKFrequent(int[] nums, int k) {
        // Count frequencies
        Map<Integer, Integer> frequency = new HashMap<>();
        for (int num : nums) {
            frequency.put(num, frequency.getOrDefault(num, 0) + 1);
        }
        
        // Create buckets based on frequency
        List<Integer>[] buckets = new ArrayList[nums.length + 1];
        for (int i = 0; i <= nums.length; i++) {
            buckets[i] = new ArrayList<>();
        }
        
        for (Map.Entry<Integer, Integer> entry : frequency.entrySet()) {
            buckets[entry.getValue()].add(entry.getKey());
        }
        
        // Collect top k from highest frequency buckets
        List<Integer> result = new ArrayList<>();
        for (int i = buckets.length - 1; i >= 0 && result.size() < k; i--) {
            if (!buckets[i].isEmpty()) {
                result.addAll(buckets[i]);
            }
        }
        
        return result.subList(0, k);
    }
    
    public static void main(String[] args) {
        int[] nums = {1, 1, 1, 2, 2, 3};
        List<Integer> result = topKFrequent(nums, 2);
        System.out.println(result); // [1, 2]
    }
}
```

### 🔹 Best Practices

```java
// Use bucket sort for better performance when frequencies vary widely
// Use PriorityQueue for general cases
// Handle edge cases: k = 0, k > number of unique elements
// Consider time vs space trade-offs
```

### 🔹 Time and Space Complexity

```java
// Heap approach: O(n log k) time, O(n) space
// Bucket sort: O(n) time, O(n) space
```

---

## 🎯 Interview One-Liner

> Top K frequent: count frequencies with HashMap, use min-heap of size k; O(n log k) time; bucket sort alternative for O(n) time.

---

### **Streams**
217. Filter and collect elements
218. Map and transform data
219. Reduce and aggregate values
220. Find first/last element
221. Group and partition data
222. Parallel streams
223. Stream performance tips

## ✅ 217. Filter and collect elements

**Java Streams provide powerful filtering capabilities with lazy evaluation and fluent API design.**

### 🔹 Basic Filtering

**Filter elements based on conditions**

```java
import java.util.*;
import java.util.stream.Collectors;

public class StreamFiltering {
    
    public static List<Integer> filterEvenNumbers(List<Integer> numbers) {
        return numbers.stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList());
    }
    
    public static List<String> filterLongStrings(List<String> strings, int minLength) {
        return strings.stream()
                .filter(s -> s.length() >= minLength)
                .collect(Collectors.toList());
    }
    
    public static List<Employee> filterBySalary(List<Employee> employees, double minSalary) {
        return employees.stream()
                .filter(emp -> emp.getSalary() >= minSalary)
                .collect(Collectors.toList());
    }
    
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        System.out.println(filterEvenNumbers(numbers)); // [2, 4, 6, 8, 10]
        
        List<String> words = Arrays.asList("cat", "elephant", "dog", "hippopotamus");
        System.out.println(filterLongStrings(words, 4)); // [elephant, hippopotamus]
    }
}

class Employee {
    private String name;
    private double salary;
    
    // Constructor, getters, setters
    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }
    
    public double getSalary() { return salary; }
    public String getName() { return name; }
}
```

### 🔹 Multiple Conditions

**Combine multiple filter conditions**

```java
import java.util.*;
import java.util.stream.Collectors;

public class AdvancedFiltering {
    
    public static List<Integer> filterRange(List<Integer> numbers, int min, int max) {
        return numbers.stream()
                .filter(n -> n >= min && n <= max)
                .collect(Collectors.toList());
    }
    
    public static List<String> filterByPattern(List<String> strings, String pattern) {
        return strings.stream()
                .filter(s -> s.toLowerCase().contains(pattern.toLowerCase()))
                .collect(Collectors.toList());
    }
    
    public static List<Employee> filterEmployees(List<Employee> employees) {
        return employees.stream()
                .filter(emp -> emp.getSalary() > 50000)
                .filter(emp -> emp.getName().startsWith("A"))
                .collect(Collectors.toList());
    }
    
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 5, 10, 15, 20, 25);
        System.out.println(filterRange(numbers, 10, 20)); // [10, 15, 20]
    }
}
```

### 🔹 Best Practices

```java
// Use filter() for conditional selection
// Chain multiple filters for complex conditions
// Use collect() to materialize results
// Consider parallel streams for large datasets
// Use distinct() to remove duplicates
```

### 🔹 Time and Space Complexity

```java
// Filtering: O(n) time, O(n) space for result collection
// Lazy evaluation: operations performed only when terminal operation called
```

---

## 🎯 Interview One-Liner

> Stream filtering: .filter(predicate).collect(Collectors.toList()); lazy evaluation; chain multiple filters; O(n) time complexity.

---

## ✅ 218. Map and transform data

**Map operations transform each element in a stream to produce a new stream of transformed elements.**

### 🔹 Basic Mapping

**Transform elements using map()**

```java
import java.util.*;
import java.util.stream.Collectors;

public class StreamMapping {
    
    public static List<Integer> squareNumbers(List<Integer> numbers) {
        return numbers.stream()
                .map(n -> n * n)
                .collect(Collectors.toList());
    }
    
    public static List<String> convertToUpperCase(List<String> strings) {
        return strings.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
    }
    
    public static List<String> getEmployeeNames(List<Employee> employees) {
        return employees.stream()
                .map(Employee::getName)
                .collect(Collectors.toList());
    }
    
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        System.out.println(squareNumbers(numbers)); // [1, 4, 9, 16, 25]
        
        List<String> words = Arrays.asList("hello", "world");
        System.out.println(convertToUpperCase(words)); // [HELLO, WORLD]
    }
}
```

### 🔹 Complex Transformations

**Map to different types and complex operations**

```java
import java.util.*;
import java.util.stream.Collectors;

public class ComplexMapping {
    
    public static List<Double> calculateSalaries(List<Employee> employees) {
        return employees.stream()
                .map(emp -> emp.getSalary() * 1.1) // 10% bonus
                .collect(Collectors.toList());
    }
    
    public static List<String> formatEmployeeInfo(List<Employee> employees) {
        return employees.stream()
                .map(emp -> String.format("%s: $%.2f", emp.getName(), emp.getSalary()))
                .collect(Collectors.toList());
    }
    
    public static List<Integer> getStringLengths(List<String> strings) {
        return strings.stream()
                .map(String::length)
                .collect(Collectors.toList());
    }
    
    public static void main(String[] args) {
        List<Employee> employees = Arrays.asList(
            new Employee("Alice", 50000),
            new Employee("Bob", 60000)
        );
        
        System.out.println(calculateSalaries(employees)); // [55000.0, 66000.0]
        System.out.println(formatEmployeeInfo(employees)); 
        // [Alice: $50000.00, Bob: $60000.00]
    }
}
```

### 🔹 Best Practices

```java
// Use map() for one-to-one transformations
// Use flatMap() for one-to-many transformations
// Method references improve readability
// Consider performance impact of complex mappings
```

### 🔹 Time and Space Complexity

```java
// Mapping: O(n) time, O(n) space
// Each element processed exactly once
```

---

## 🎯 Interview One-Liner

> Stream mapping: .map(transformer).collect(); one-to-one transformation; use flatMap for one-to-many; O(n) time complexity.

---

## ✅ 219. Reduce and aggregate values

**Reduce operations combine stream elements into a single result using associative accumulation functions.**

### 🔹 Basic Reduction

**Sum, min, max operations**

```java
import java.util.*;
import java.util.stream.Collectors;

public class StreamReduction {
    
    public static int sumNumbers(List<Integer> numbers) {
        return numbers.stream()
                .reduce(0, Integer::sum);
    }
    
    public static Optional<Integer> findMax(List<Integer> numbers) {
        return numbers.stream()
                .reduce(Integer::max);
    }
    
    public static String concatenateStrings(List<String> strings) {
        return strings.stream()
                .reduce("", (a, b) -> a + b);
    }
    
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        System.out.println(sumNumbers(numbers)); // 15
        
        Optional<Integer> max = findMax(numbers);
        max.ifPresent(System.out::println); // 5
        
        List<String> words = Arrays.asList("Hello", " ", "World");
        System.out.println(concatenateStrings(words)); // Hello World
    }
}
```

### 🔹 Advanced Reduction

**Custom reduction with collectors**

```java
import java.util.*;
import java.util.stream.Collectors;

public class AdvancedReduction {
    
    public static double calculateAverage(List<Integer> numbers) {
        return numbers.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);
    }
    
    public static Map<String, Double> calculateDepartmentSalaries(List<Employee> employees) {
        return employees.stream()
                .collect(Collectors.groupingBy(
                    emp -> emp.getDepartment(),
                    Collectors.summingDouble(Employee::getSalary)
                ));
    }
    
    public static String joinWithDelimiter(List<String> strings, String delimiter) {
        return strings.stream()
                .collect(Collectors.joining(delimiter));
    }
    
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        System.out.println(calculateAverage(numbers)); // 3.0
        
        List<String> words = Arrays.asList("Java", "Streams", "API");
        System.out.println(joinWithDelimiter(words, " - ")); // Java - Streams - API
    }
}

// Updated Employee class with department
class Employee {
    private String name;
    private double salary;
    private String department;
    
    public Employee(String name, double salary, String department) {
        this.name = name;
        this.salary = salary;
        this.department = department;
    }
    
    public double getSalary() { return salary; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
}
```

### 🔹 Best Practices

```java
// Use reduce() for custom accumulation
// Use built-in collectors for common operations
// Handle empty streams with Optional
// Consider parallel reduction for large datasets
```

### 🔹 Time and Space Complexity

```java
// Reduction: O(n) time, O(1) space for simple reductions
// Grouping: O(n) time, O(n) space for result storage
```

---

## 🎯 Interview One-Liner

> Stream reduction: .reduce(identity, accumulator) for custom aggregation; use Collectors for built-in operations; handle Optional results.

---

## ✅ 220. Find first/last element

**Finding first or last elements in streams with optional results and short-circuiting behavior.**

### 🔹 Find Operations

**findFirst() and findLast() methods**

```java
import java.util.*;
import java.util.stream.Collectors;

public class FindOperations {
    
    public static Optional<Integer> findFirstEven(List<Integer> numbers) {
        return numbers.stream()
                .filter(n -> n % 2 == 0)
                .findFirst();
    }
    
    public static Optional<String> findLongestString(List<String> strings) {
        return strings.stream()
                .max(Comparator.comparingInt(String::length));
    }
    
    public static Optional<Employee> findHighestPaid(List<Employee> employees) {
        return employees.stream()
                .max(Comparator.comparingDouble(Employee::getSalary));
    }
    
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 3, 5, 2, 4, 6);
        Optional<Integer> firstEven = findFirstEven(numbers);
        firstEven.ifPresent(n -> System.out.println("First even: " + n)); // 2
        
        List<String> words = Arrays.asList("cat", "elephant", "dog");
        Optional<String> longest = findLongestString(words);
        longest.ifPresent(s -> System.out.println("Longest: " + s)); // elephant
    }
}
```

### 🔹 Find with Conditions

**Complex find operations with predicates**

```java
import java.util.*;
import java.util.stream.Collectors;

public class ConditionalFind {
    
    public static Optional<Employee> findEmployeeByName(List<Employee> employees, String name) {
        return employees.stream()
                .filter(emp -> emp.getName().equalsIgnoreCase(name))
                .findFirst();
    }
    
    public static Optional<Integer> findFirstDivisibleBy(List<Integer> numbers, int divisor) {
        return numbers.stream()
                .filter(n -> n % divisor == 0)
                .findFirst();
    }
    
    public static Optional<String> findFirstStartingWith(List<String> strings, String prefix) {
        return strings.stream()
                .filter(s -> s.startsWith(prefix))
                .findFirst();
    }
    
    public static void main(String[] args) {
        List<Employee> employees = Arrays.asList(
            new Employee("Alice", 50000, "IT"),
            new Employee("Bob", 60000, "HR")
        );
        
        Optional<Employee> alice = findEmployeeByName(employees, "Alice");
        alice.ifPresent(emp -> System.out.println("Found: " + emp.getName()));
    }
}
```

### 🔹 Best Practices

```java
// Use findFirst() for predictable ordering
// Use findAny() for parallel streams
// Always handle Optional results
// Use orElse() for default values
```

### 🔹 Time and Space Complexity

```java
// Find operations: O(1) to O(n) time, short-circuiting
// Depends on position of element found
```

---

## 🎯 Interview One-Liner

> Find operations: .findFirst() for ordered streams, .findAny() for parallel; returns Optional; short-circuiting behavior.

---

## ✅ 221. Group and partition data

**Grouping and partitioning operations organize stream elements into collections based on criteria.**

### 🔹 Grouping

**Group elements by key**

```java
import java.util.*;
import java.util.stream.Collectors;

public class GroupingOperations {
    
    public static Map<String, List<Employee>> groupByDepartment(List<Employee> employees) {
        return employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment));
    }
    
    public static Map<Integer, List<Integer>> groupByLength(List<String> strings) {
        return strings.stream()
                .collect(Collectors.groupingBy(String::length));
    }
    
    public static Map<Character, List<String>> groupByFirstLetter(List<String> strings) {
        return strings.stream()
                .collect(Collectors.groupingBy(s -> s.charAt(0)));
    }
    
    public static void main(String[] args) {
        List<Employee> employees = Arrays.asList(
            new Employee("Alice", 50000, "IT"),
            new Employee("Bob", 60000, "HR"),
            new Employee("Charlie", 55000, "IT")
        );
        
        Map<String, List<Employee>> byDept = groupByDepartment(employees);
        System.out.println(byDept);
        // {IT=[Alice, Charlie], HR=[Bob]}
    }
}
```

### 🔹 Partitioning

**Split into two groups based on predicate**

```java
import java.util.*;
import java.util.stream.Collectors;

public class PartitioningOperations {
    
    public static Map<Boolean, List<Employee>> partitionBySalary(List<Employee> employees, double threshold) {
        return employees.stream()
                .collect(Collectors.partitioningBy(emp -> emp.getSalary() > threshold));
    }
    
    public static Map<Boolean, List<String>> partitionByLength(List<String> strings, int length) {
        return strings.stream()
                .collect(Collectors.partitioningBy(s -> s.length() > length));
    }
    
    public static Map<Boolean, List<Integer>> partitionEvenOdd(List<Integer> numbers) {
        return numbers.stream()
                .collect(Collectors.partitioningBy(n -> n % 2 == 0));
    }
    
    public static void main(String[] args) {
        List<Employee> employees = Arrays.asList(
            new Employee("Alice", 50000, "IT"),
            new Employee("Bob", 60000, "HR")
        );
        
        Map<Boolean, List<Employee>> partitioned = partitionBySalary(employees, 55000);
        System.out.println("High salary: " + partitioned.get(true));
        System.out.println("Low salary: " + partitioned.get(false));
    }
}
```

### 🔹 Best Practices

```java
// Use groupingBy() for multiple categories
// Use partitioningBy() for binary classification
// Combine with downstream collectors for aggregation
// Consider performance with large datasets
```

### 🔹 Time and Space Complexity

```java
// Grouping/Partitioning: O(n) time, O(n) space
// Creates result collections for each group
```

---

## 🎯 Interview One-Liner

> Group/partition: Collectors.groupingBy() for categories, .partitioningBy() for binary split; O(n) time/space complexity.

---

## ✅ 222. Parallel streams

**Parallel streams leverage multiple cores for improved performance on large datasets with CPU-intensive operations.**

### 🔹 Basic Parallel Processing

**Convert sequential to parallel streams**

```java
import java.util.*;
import java.util.stream.Collectors;

public class ParallelStreams {
    
    public static List<Integer> processNumbers(List<Integer> numbers) {
        return numbers.parallelStream()
                .map(n -> n * n) // CPU-intensive operation
                .filter(n -> n > 100)
                .collect(Collectors.toList());
    }
    
    public static double calculateTotalSalary(List<Employee> employees) {
        return employees.parallelStream()
                .mapToDouble(Employee::getSalary)
                .sum();
    }
    
    public static List<String> processStrings(List<String> strings) {
        return strings.parallelStream()
                .map(String::toUpperCase)
                .sorted()
                .collect(Collectors.toList());
    }
    
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Integer> result = processNumbers(numbers);
        System.out.println(result); // [121, 144, 169, 196, 225, 256, 289, 324, 361, 400]
    }
}
```

### 🔹 When to Use Parallel Streams

**Performance considerations**

```java
import java.util.*;
import java.util.stream.Collectors;

public class ParallelStreamPerformance {
    
    // Good for parallel: CPU-intensive operations
    public static List<Double> calculateComplexValues(List<Integer> numbers) {
        return numbers.parallelStream()
                .map(n -> Math.sqrt(n) * Math.sin(n) * Math.cos(n))
                .collect(Collectors.toList());
    }
    
    // Bad for parallel: I/O operations
    public static List<String> readFiles(List<String> filePaths) {
        return filePaths.parallelStream()
                .map(path -> readFile(path)) // I/O operation
                .collect(Collectors.toList());
    }
    
    // Good for parallel: Large datasets
    public static long countLargeDataset(List<Integer> numbers) {
        return numbers.parallelStream()
                .filter(n -> n > 1000000)
                .count();
    }
    
    private static String readFile(String path) {
        // Simulate file reading
        return "content of " + path;
    }
    
    public static void main(String[] args) {
        List<Integer> largeNumbers = new ArrayList<>();
        for (int i = 0; i < 1000000; i++) {
            largeNumbers.add(i);
        }
        
        long count = countLargeDataset(largeNumbers);
        System.out.println("Count: " + count);
    }
}
```

### 🔹 Best Practices

```java
// Use parallelStream() for CPU-intensive operations
// Avoid for I/O operations or small datasets
// Be aware of thread-safety issues
// Use sequential() to switch back to sequential
```

### 🔹 Time and Space Complexity

```java
// Parallel streams: Potentially O(n/cores) time
// Depends on available processors and workload distribution
```

---

## 🎯 Interview One-Liner

> Parallel streams: .parallelStream() for CPU-intensive ops on large data; avoid for I/O; thread-safe operations required.

---

## ✅ 223. Stream performance tips

**Optimizing stream performance requires understanding lazy evaluation, short-circuiting, and appropriate data structures.**

### 🔹 Performance Best Practices

**Efficient stream operations**

```java
import java.util.*;
import java.util.stream.Collectors;

public class StreamPerformance {
    
    // Use limit() for early termination
    public static List<Integer> getFirstNEvenNumbers(List<Integer> numbers, int n) {
        return numbers.stream()
                .filter(x -> x % 2 == 0)
                .limit(n)
                .collect(Collectors.toList());
    }
    
    // Use findFirst() instead of max() when possible
    public static Optional<Integer> findFirstMatch(List<Integer> numbers, int target) {
        return numbers.stream()
                .filter(n -> n == target)
                .findFirst(); // Short-circuits
    }
    
    // Avoid unnecessary boxing/unboxing
    public static int sumUsingPrimitive(List<Integer> numbers) {
        return numbers.stream()
                .mapToInt(Integer::intValue)
                .sum(); // More efficient than .map().reduce()
    }
    
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        
        List<Integer> firstThreeEven = getFirstNEvenNumbers(numbers, 3);
        System.out.println(firstThreeEven); // [2, 4, 6]
        
        int sum = sumUsingPrimitive(numbers);
        System.out.println("Sum: " + sum); // 55
    }
}
```

### 🔹 Common Performance Pitfalls

**Avoid these anti-patterns**

```java
import java.util.*;
import java.util.stream.Collectors;

public class StreamAntiPatterns {
    
    // Bad: Multiple traversals
    public static void badMultipleTraversals(List<Integer> numbers) {
        long count = numbers.stream().count();
        long sum = numbers.stream().mapToInt(Integer::intValue).sum();
        // Two separate traversals - inefficient
    }
    
    // Good: Single traversal
    public static void goodSingleTraversal(List<Integer> numbers) {
        IntSummaryStatistics stats = numbers.stream()
                .mapToInt(Integer::intValue)
                .summaryStatistics();
        
        long count = stats.getCount();
        long sum = stats.getSum();
    }
    
    // Bad: Unnecessary sorting
    public static boolean badUnnecessarySort(List<Integer> numbers, int target) {
        return numbers.stream()
                .sorted() // Unnecessary for existence check
                .anyMatch(n -> n == target);
    }
    
    // Good: No sorting needed
    public static boolean goodNoSort(List<Integer> numbers, int target) {
        return numbers.stream()
                .anyMatch(n -> n == target);
    }
    
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        
        // Demonstrate single traversal
        IntSummaryStatistics stats = numbers.stream()
                .mapToInt(Integer::intValue)
                .summaryStatistics();
        
        System.out.println("Count: " + stats.getCount() + ", Sum: " + stats.getSum());
    }
}
```

### 🔹 Best Practices

```java
// Use primitive streams (IntStream, LongStream) to avoid boxing
// Use limit() and findFirst() for short-circuiting
// Combine operations in single traversal when possible
// Avoid sorting unless necessary
// Consider parallel streams for CPU-intensive operations
```

### 🔹 Time and Space Complexity

```java
// Optimized streams: O(n) time with short-circuiting
// Primitive streams: Better performance than boxed streams
// Single traversal: More efficient than multiple passes
```

---

## 🎯 Interview One-Liner

> Stream performance: use primitive streams, limit()/findFirst() for short-circuiting, single traversal over multiple; avoid unnecessary sorting.

---

### **Recursion**
224. Fibonacci series
225. Factorial of number
226. Power of number (x^n)
227. Print all permutations of string

## ✅ 224. Fibonacci series

**Fibonacci series generates each number as the sum of the two preceding ones, starting from 0 and 1.**

### 🔹 Recursive Approach

**Basic recursive implementation**

```java
public class Fibonacci {
    
    public static int fibonacci(int n) {
        if (n <= 1) {
            return n;
        }
        return fibonacci(n - 1) + fibonacci(n - 2);
    }
    
    public static void printFibonacciSeries(int n) {
        System.out.print("Fibonacci series: ");
        for (int i = 0; i < n; i++) {
            System.out.print(fibonacci(i) + " ");
        }
        System.out.println();
    }
    
    public static void main(String[] args) {
        printFibonacciSeries(10); // 0 1 1 2 3 5 8 13 21 34
        System.out.println("10th Fibonacci number: " + fibonacci(10)); // 55
    }
}
```

### 🔹 Optimized Recursive with Memoization

**Avoid redundant calculations using memoization**

```java
import java.util.*;

public class FibonacciMemoization {
    
    public static int fibonacci(int n, Map<Integer, Integer> memo) {
        if (n <= 1) {
            return n;
        }
        
        if (memo.containsKey(n)) {
            return memo.get(n);
        }
        
        int result = fibonacci(n - 1, memo) + fibonacci(n - 2, memo);
        memo.put(n, result);
        return result;
    }
    
    public static int fibonacci(int n) {
        return fibonacci(n, new HashMap<>());
    }
    
    public static void main(String[] args) {
        System.out.println("10th Fibonacci number: " + fibonacci(10)); // 55
    }
}
```

### 🔹 Iterative Approach

**More efficient iterative solution**

```java
public class FibonacciIterative {
    
    public static int fibonacci(int n) {
        if (n <= 1) {
            return n;
        }
        
        int a = 0, b = 1;
        for (int i = 2; i <= n; i++) {
            int temp = a + b;
            a = b;
            b = temp;
        }
        return b;
    }
    
    public static void printFibonacciSeries(int n) {
        int a = 0, b = 1;
        System.out.print("Fibonacci series: " + a + " " + b + " ");
        
        for (int i = 2; i < n; i++) {
            int temp = a + b;
            System.out.print(temp + " ");
            a = b;
            b = temp;
        }
        System.out.println();
    }
    
    public static void main(String[] args) {
        printFibonacciSeries(10);
        System.out.println("10th Fibonacci number: " + fibonacci(10));
    }
}
```

### 🔹 Best Practices

```java
// Use iterative approach for better performance
// Implement memoization for recursive solutions
// Handle base cases properly (n = 0, n = 1)
// Consider BigInteger for large Fibonacci numbers
```

### 🔹 Time and Space Complexity

```java
// Recursive: O(2^n) time, O(n) space (call stack)
// Memoized: O(n) time, O(n) space
// Iterative: O(n) time, O(1) space
```

---

## 🎯 Interview One-Liner

> Fibonacci: iterative O(n) time/O(1) space preferred; recursive with memoization O(n) time/O(n) space; avoid naive recursion due to exponential time.

---

## ✅ 225. Factorial of number

**Factorial of a non-negative integer n is the product of all positive integers less than or equal to n.**

### 🔹 Recursive Approach

**Basic recursive implementation**

```java
public class Factorial {
    
    public static long factorial(int n) {
        if (n == 0 || n == 1) {
            return 1;
        }
        return n * factorial(n - 1);
    }
    
    public static void main(String[] args) {
        System.out.println("Factorial of 5: " + factorial(5)); // 120
        System.out.println("Factorial of 0: " + factorial(0)); // 1
        System.out.println("Factorial of 1: " + factorial(1)); // 1
    }
}
```

### 🔹 Iterative Approach

**More efficient iterative solution**

```java
public class FactorialIterative {
    
    public static long factorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Factorial not defined for negative numbers");
        }
        
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }
    
    public static void main(String[] args) {
        System.out.println("Factorial of 5: " + factorial(5)); // 120
        System.out.println("Factorial of 10: " + factorial(10)); // 3628800
    }
}
```

### 🔹 Tail Recursive Approach

**Optimized recursive with tail call**

```java
public class FactorialTailRecursive {
    
    public static long factorial(int n) {
        return factorialHelper(n, 1);
    }
    
    private static long factorialHelper(int n, long accumulator) {
        if (n == 0 || n == 1) {
            return accumulator;
        }
        return factorialHelper(n - 1, n * accumulator);
    }
    
    public static void main(String[] args) {
        System.out.println("Factorial of 5: " + factorial(5)); // 120
    }
}
```

### 🔹 Best Practices

```java
// Use iterative approach to avoid stack overflow
// Handle edge cases: n = 0, n = 1, negative numbers
// Use long for larger factorials, BigInteger for very large numbers
// Consider tail recursion for optimization
```

### 🔹 Time and Space Complexity

```java
// Recursive: O(n) time, O(n) space (call stack)
// Iterative: O(n) time, O(1) space
// Tail recursive: O(n) time, O(n) space (may be optimized by JVM)
```

---

## 🎯 Interview One-Liner

> Factorial: iterative O(n) time/O(1) space preferred; recursive O(n) time/O(n) space; handle n=0,1; use long/BigInteger for large values.

---

## ✅ 226. Power of number (x^n)

**Computing x raised to the power n efficiently using exponentiation by squaring.**

### 🔹 Recursive Approach

**Basic recursive power calculation**

```java
public class Power {
    
    public static double power(double x, int n) {
        if (n == 0) {
            return 1;
        }
        if (n < 0) {
            return 1 / power(x, -n);
        }
        
        double half = power(x, n / 2);
        if (n % 2 == 0) {
            return half * half;
        } else {
            return half * half * x;
        }
    }
    
    public static void main(String[] args) {
        System.out.println("2^10 = " + power(2, 10)); // 1024.0
        System.out.println("3^4 = " + power(3, 4)); // 81.0
        System.out.println("2^-3 = " + power(2, -3)); // 0.125
    }
}
```

### 🔹 Iterative Approach

**Efficient iterative implementation**

```java
public class PowerIterative {
    
    public static double power(double x, int n) {
        if (n == 0) {
            return 1;
        }
        
        long absN = Math.abs((long) n);
        double result = 1.0;
        double current = x;
        
        while (absN > 0) {
            if (absN % 2 == 1) {
                result *= current;
            }
            current *= current;
            absN /= 2;
        }
        
        return n < 0 ? 1 / result : result;
    }
    
    public static void main(String[] args) {
        System.out.println("2^10 = " + power(2, 10)); // 1024.0
        System.out.println("3^4 = " + power(3, 4)); // 81.0
    }
}
```

### 🔹 Built-in Math.pow()

**Using Java's built-in method**

```java
public class PowerBuiltIn {
    
    public static double power(double x, int n) {
        return Math.pow(x, n);
    }
    
    public static void main(String[] args) {
        System.out.println("2^10 = " + power(2, 10)); // 1024.0
        System.out.println("2.5^3 = " + power(2.5, 3)); // 15.625
    }
}
```

### 🔹 Best Practices

```java
// Use exponentiation by squaring for efficiency
// Handle negative exponents by reciprocation
// Consider integer overflow for large exponents
// Use Math.pow() for floating-point calculations
```

### 🔹 Time and Space Complexity

```java
// Recursive/Iterative: O(log n) time, O(log n)/O(1) space
// Math.pow(): O(1) time (native implementation)
```

---

## 🎯 Interview One-Liner

> Power x^n: exponentiation by squaring O(log n) time; handle negative n with reciprocation; iterative preferred to avoid stack overflow.

---

## ✅ 227. Print all permutations of string

**Generating all possible arrangements of characters in a string using backtracking.**

### 🔹 Recursive Backtracking Approach

**Generate all permutations using recursion**

```java
import java.util.*;

public class StringPermutations {
    
    public static void printPermutations(String str) {
        printPermutationsHelper(str.toCharArray(), 0);
    }
    
    private static void printPermutationsHelper(char[] arr, int index) {
        if (index == arr.length - 1) {
            System.out.println(String.valueOf(arr));
            return;
        }
        
        for (int i = index; i < arr.length; i++) {
            swap(arr, index, i);
            printPermutationsHelper(arr, index + 1);
            swap(arr, index, i); // backtrack
        }
    }
    
    private static void swap(char[] arr, int i, int j) {
        char temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    
    public static void main(String[] args) {
        System.out.println("Permutations of 'ABC':");
        printPermutations("ABC");
    }
}
```

### 🔹 Using List to Store Permutations

**Collect all permutations in a list**

```java
import java.util.*;

public class StringPermutationsList {
    
    public static List<String> generatePermutations(String str) {
        List<String> result = new ArrayList<>();
        generatePermutationsHelper(str.toCharArray(), 0, result);
        return result;
    }
    
    private static void generatePermutationsHelper(char[] arr, int index, List<String> result) {
        if (index == arr.length - 1) {
            result.add(String.valueOf(arr));
            return;
        }
        
        Set<Character> used = new HashSet<>();
        for (int i = index; i < arr.length; i++) {
            if (used.add(arr[i])) { // Skip duplicates
                swap(arr, index, i);
                generatePermutationsHelper(arr, index + 1, result);
                swap(arr, index, i); // backtrack
            }
        }
    }
    
    private static void swap(char[] arr, int i, int j) {
        char temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    
    public static void main(String[] args) {
        List<String> permutations = generatePermutations("AAB");
        System.out.println("Permutations of 'AAB': " + permutations);
        System.out.println("Total permutations: " + permutations.size());
    }
}
```

### 🔹 Iterative Approach

**Generate permutations iteratively**

```java
import java.util.*;

public class StringPermutationsIterative {
    
    public static List<String> generatePermutations(String str) {
        List<String> result = new ArrayList<>();
        if (str == null || str.isEmpty()) {
            result.add("");
            return result;
        }
        
        char first = str.charAt(0);
        String remaining = str.substring(1);
        List<String> perms = generatePermutations(remaining);
        
        for (String perm : perms) {
            for (int i = 0; i <= perm.length(); i++) {
                String newPerm = perm.substring(0, i) + first + perm.substring(i);
                result.add(newPerm);
            }
        }
        
        return result;
    }
    
    public static void main(String[] args) {
        List<String> permutations = generatePermutations("ABC");
        System.out.println("Permutations: " + permutations);
    }
}
```

### 🔹 Best Practices

```java
// Use backtracking for efficiency
// Handle duplicates by tracking used characters
// Consider iterative approach for small strings
// Be aware of factorial time complexity
```

### 🔹 Time and Space Complexity

```java
// Backtracking: O(n!) time, O(n) space (recursion stack)
// Iterative: O(n!) time, O(n!) space (result storage)
// Where n is string length
```

---

## 🎯 Interview One-Liner

> String permutations: backtracking with swap O(n!) time; handle duplicates with Set; iterative approach alternative; factorial complexity.

---

### **Data Structures**
228. Implement Stack using Queue
229. Implement Queue using Stack
230. Reverse a LinkedList
231. Detect cycle in LinkedList
232. Binary Tree traversal (Inorder, Preorder, Postorder)
233. Check if Binary Tree is BST
234. Level order traversal

## ✅ 228. Implement Stack using Queue

**Implementing stack operations (push, pop, peek, isEmpty) using queue data structure.**

### 🔹 Using Single Queue

**Implement stack using one queue**

```java
import java.util.*;

public class StackUsingQueue {
    private Queue<Integer> queue;
    
    public StackUsingQueue() {
        queue = new LinkedList<>();
    }
    
    // Push operation - O(n) time
    public void push(int x) {
        queue.add(x);
        // Rotate queue to make newly added element at front
        for (int i = 0; i < queue.size() - 1; i++) {
            queue.add(queue.remove());
        }
    }
    
    // Pop operation - O(1) time
    public int pop() {
        if (isEmpty()) {
            throw new RuntimeException("Stack is empty");
        }
        return queue.remove();
    }
    
    // Peek operation - O(1) time
    public int peek() {
        if (isEmpty()) {
            throw new RuntimeException("Stack is empty");
        }
        return queue.peek();
    }
    
    public boolean isEmpty() {
        return queue.isEmpty();
    }
    
    public int size() {
        return queue.size();
    }
    
    public static void main(String[] args) {
        StackUsingQueue stack = new StackUsingQueue();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        
        System.out.println("Top element: " + stack.peek()); // 3
        System.out.println("Pop: " + stack.pop()); // 3
        System.out.println("Pop: " + stack.pop()); // 2
        System.out.println("Is empty: " + stack.isEmpty()); // false
    }
}
```

### 🔹 Using Two Queues

**More efficient implementation using two queues**

```java
import java.util.*;

public class StackUsingTwoQueues {
    private Queue<Integer> q1;
    private Queue<Integer> q2;
    
    public StackUsingTwoQueues() {
        q1 = new LinkedList<>();
        q2 = new LinkedList<>();
    }
    
    // Push operation - O(1) amortized
    public void push(int x) {
        q1.add(x);
    }
    
    // Pop operation - O(n) time
    public int pop() {
        if (isEmpty()) {
            throw new RuntimeException("Stack is empty");
        }
        
        // Move all elements except last from q1 to q2
        while (q1.size() > 1) {
            q2.add(q1.remove());
        }
        
        int result = q1.remove();
        
        // Swap q1 and q2
        Queue<Integer> temp = q1;
        q1 = q2;
        q2 = temp;
        
        return result;
    }
    
    public int peek() {
        if (isEmpty()) {
            throw new RuntimeException("Stack is empty");
        }
        
        while (q1.size() > 1) {
            q2.add(q1.remove());
        }
        
        int result = q1.peek();
        q2.add(q1.remove());
        
        Queue<Integer> temp = q1;
        q1 = q2;
        q2 = temp;
        
        return result;
    }
    
    public boolean isEmpty() {
        return q1.isEmpty();
    }
    
    public static void main(String[] args) {
        StackUsingTwoQueues stack = new StackUsingTwoQueues();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        
        System.out.println("Top element: " + stack.peek()); // 3
        System.out.println("Pop: " + stack.pop()); // 3
    }
}
```

### 🔹 Best Practices

```java
// Use two queues for better amortized performance
// Single queue approach is simpler but has O(n) push
// Handle empty stack cases properly
// Consider space vs time trade-offs
```

### 🔹 Time and Space Complexity

```java
// Single queue: Push O(n), Pop O(1)
// Two queues: Push O(1), Pop O(n)
// Space: O(n) for both approaches
```

---

## 🎯 Interview One-Liner

> Stack using queue: two queues better - push O(1), pop O(n); single queue simpler but push O(n); use LinkedList as queue implementation.

---

## ✅ 229. Implement Queue using Stack

**Implementing queue operations (enqueue, dequeue, peek, isEmpty) using stack data structure.**

### 🔹 Using Two Stacks

**Efficient implementation using two stacks**

```java
import java.util.*;

public class QueueUsingStack {
    private Stack<Integer> s1; // for enqueue
    private Stack<Integer> s2; // for dequeue
    
    public QueueUsingStack() {
        s1 = new Stack<>();
        s2 = new Stack<>();
    }
    
    // Enqueue operation - O(1) amortized
    public void enqueue(int x) {
        s1.push(x);
    }
    
    // Dequeue operation - O(1) amortized
    public int dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Queue is empty");
        }
        
        if (s2.isEmpty()) {
            // Transfer all elements from s1 to s2
            while (!s1.isEmpty()) {
                s2.push(s1.pop());
            }
        }
        
        return s2.pop();
    }
    
    public int peek() {
        if (isEmpty()) {
            throw new RuntimeException("Queue is empty");
        }
        
        if (s2.isEmpty()) {
            while (!s1.isEmpty()) {
                s2.push(s1.pop());
            }
        }
        
        return s2.peek();
    }
    
    public boolean isEmpty() {
        return s1.isEmpty() && s2.isEmpty();
    }
    
    public int size() {
        return s1.size() + s2.size();
    }
    
    public static void main(String[] args) {
        QueueUsingStack queue = new QueueUsingStack();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        
        System.out.println("Front element: " + queue.peek()); // 1
        System.out.println("Dequeue: " + queue.dequeue()); // 1
        System.out.println("Dequeue: " + queue.dequeue()); // 2
        System.out.println("Is empty: " + queue.isEmpty()); // false
    }
}
```

### 🔹 Using Single Stack with Recursion

**Recursive approach (not recommended for large queues)**

```java
import java.util.*;

public class QueueUsingSingleStack {
    private Stack<Integer> stack;
    
    public QueueUsingSingleStack() {
        stack = new Stack<>();
    }
    
    public void enqueue(int x) {
        stack.push(x);
    }
    
    public int dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Queue is empty");
        }
        
        // Recursive function to remove bottom element
        return removeBottomElement();
    }
    
    private int removeBottomElement() {
        int top = stack.pop();
        
        if (stack.isEmpty()) {
            return top; // This is the bottom element
        }
        
        int bottom = removeBottomElement();
        stack.push(top); // Push back the elements
        
        return bottom;
    }
    
    public boolean isEmpty() {
        return stack.isEmpty();
    }
    
    public static void main(String[] args) {
        QueueUsingSingleStack queue = new QueueUsingSingleStack();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        
        System.out.println("Dequeue: " + queue.dequeue()); // 1
        System.out.println("Dequeue: " + queue.dequeue()); // 2
    }
}
```

### 🔹 Best Practices

```java
// Use two stacks for efficient implementation
// Single stack with recursion works but risky for large queues
// Handle empty queue cases properly
// Consider stack overflow with recursive approach
```

### 🔹 Time and Space Complexity

```java
// Two stacks: Enqueue O(1), Dequeue O(1) amortized
// Single stack recursive: Both operations O(n)
// Space: O(n) for both approaches
```

---

## 🎯 Interview One-Liner

> Queue using stack: two stacks efficient - enqueue O(1), dequeue O(1) amortized; transfer elements when needed; single stack recursive works but O(n).

---

## ✅ 230. Reverse a LinkedList

**Reversing a singly linked list by changing the direction of pointers.**

### 🔹 Iterative Approach

**Three-pointer technique**

```java
class ListNode {
    int val;
    ListNode next;
    ListNode(int val) { this.val = val; }
}

public class ReverseLinkedList {
    
    public static ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode current = head;
        
        while (current != null) {
            ListNode nextTemp = current.next; // Store next
            current.next = prev; // Reverse pointer
            prev = current; // Move prev forward
            current = nextTemp; // Move current forward
        }
        
        return prev; // New head
    }
    
    public static void printList(ListNode head) {
        ListNode current = head;
        while (current != null) {
            System.out.print(current.val + " -> ");
            current = current.next;
        }
        System.out.println("null");
    }
    
    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        
        System.out.println("Original list:");
        printList(head);
        
        ListNode reversed = reverseList(head);
        System.out.println("Reversed list:");
        printList(reversed);
    }
}
```

### 🔹 Recursive Approach

**Recursive reversal**

```java
public class ReverseLinkedListRecursive {
    
    public static ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        
        ListNode reversedHead = reverseList(head.next);
        head.next.next = head; // Make next node point back
        head.next = null; // Set current next to null
        
        return reversedHead;
    }
    
    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        
        System.out.println("Original: 1 -> 2 -> 3");
        ListNode reversed = reverseList(head);
        System.out.println("Reversed: 3 -> 2 -> 1");
    }
}
```

### 🔹 Using Stack

**Stack-based reversal**

```java
import java.util.*;

public class ReverseLinkedListStack {
    
    public static ListNode reverseList(ListNode head) {
        if (head == null) {
            return null;
        }
        
        Stack<ListNode> stack = new Stack<>();
        ListNode current = head;
        
        // Push all nodes to stack
        while (current != null) {
            stack.push(current);
            current = current.next;
        }
        
        // Pop from stack and rebuild list
        ListNode newHead = stack.pop();
        current = newHead;
        
        while (!stack.isEmpty()) {
            current.next = stack.pop();
            current = current.next;
        }
        
        current.next = null; // Set last node's next to null
        return newHead;
    }
    
    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        
        ListNode reversed = reverseList(head);
        // Result: 3 -> 2 -> 1
    }
}
```

### 🔹 Best Practices

```java
// Use iterative approach for better performance
// Handle edge cases: empty list, single node
// Be careful with null pointer exceptions
// Consider both recursive and iterative solutions
```

### 🔹 Time and Space Complexity

```java
// Iterative: O(n) time, O(1) space
// Recursive: O(n) time, O(n) space (call stack)
// Stack-based: O(n) time, O(n) space
```

---

## 🎯 Interview One-Liner

> Reverse LinkedList: iterative three-pointer O(n) time/O(1) space preferred; recursive O(n) time/O(n) space; careful with pointer manipulation.

---

## ✅ 231. Detect cycle in LinkedList

**Detecting whether a linked list contains a cycle using Floyd's cycle detection algorithm.**

### 🔹 Floyd's Cycle Detection (Tortoise and Hare)

**Two-pointer approach**

```java
public class CycleDetection {
    
    public static boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        
        ListNode slow = head;
        ListNode fast = head.next;
        
        while (fast != null && fast.next != null) {
            if (slow == fast) {
                return true; // Cycle detected
            }
            
            slow = slow.next;
            fast = fast.next.next;
        }
        
        return false; // No cycle
    }
    
    // Find cycle start node
    public static ListNode detectCycle(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }
        
        ListNode slow = head;
        ListNode fast = head;
        
        // Phase 1: Detect cycle
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            
            if (slow == fast) {
                break; // Cycle found
            }
        }
        
        if (fast == null || fast.next == null) {
            return null; // No cycle
        }
        
        // Phase 2: Find cycle start
        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        
        return slow; // Cycle start node
    }
    
    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = head.next; // Cycle: 3 -> 2
        
        System.out.println("Has cycle: " + hasCycle(head)); // true
        System.out.println("Cycle starts at: " + detectCycle(head).val); // 2
    }
}
```

### 🔹 Using HashSet

**Hash-based approach**

```java
import java.util.*;

public class CycleDetectionHash {
    
    public static boolean hasCycle(ListNode head) {
        Set<ListNode> visited = new HashSet<>();
        
        ListNode current = head;
        while (current != null) {
            if (visited.contains(current)) {
                return true; // Cycle detected
            }
            
            visited.add(current);
            current = current.next;
        }
        
        return false;
    }
    
    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = head.next;
        
        System.out.println("Has cycle: " + hasCycle(head)); // true
    }
}
```

### 🔹 Best Practices

```java
// Use Floyd's algorithm for O(1) space solution
// HashSet approach simpler but uses O(n) space
// Handle edge cases: empty list, single node, no cycle
// Can also find cycle start node with Floyd's algorithm
```

### 🔹 Time and Space Complexity

```java
// Floyd's algorithm: O(n) time, O(1) space
// HashSet approach: O(n) time, O(n) space
```

---

## 🎯 Interview One-Liner

> Cycle detection: Floyd's tortoise-hare O(n) time/O(1) space preferred; HashSet O(n) time/O(n) space simpler; can find cycle start node.

---

## ✅ 232. Binary Tree traversal (Inorder, Preorder, Postorder)

**Tree traversal algorithms visit each node exactly once in different orders.**

### 🔹 Tree Node Definition

```java
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int val) { this.val = val; }
}
```

### 🔹 Inorder Traversal (Left, Root, Right)

**Recursive and iterative approaches**

```java
import java.util.*;

public class TreeTraversals {
    
    // Recursive Inorder
    public static void inorderRecursive(TreeNode root) {
        if (root == null) return;
        
        inorderRecursive(root.left);
        System.out.print(root.val + " ");
        inorderRecursive(root.right);
    }
    
    // Iterative Inorder
    public static void inorderIterative(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode current = root;
        
        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            
            current = stack.pop();
            System.out.print(current.val + " ");
            current = current.right;
        }
    }
    
    // Recursive Preorder (Root, Left, Right)
    public static void preorderRecursive(TreeNode root) {
        if (root == null) return;
        
        System.out.print(root.val + " ");
        preorderRecursive(root.left);
        preorderRecursive(root.right);
    }
    
    // Recursive Postorder (Left, Right, Root)
    public static void postorderRecursive(TreeNode root) {
        if (root == null) return;
        
        postorderRecursive(root.left);
        postorderRecursive(root.right);
        System.out.print(root.val + " ");
    }
    
    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        
        System.out.print("Inorder: ");
        inorderRecursive(root); // 4 2 5 1 3
        System.out.println();
        
        System.out.print("Preorder: ");
        preorderRecursive(root); // 1 2 4 5 3
        System.out.println();
        
        System.out.print("Postorder: ");
        postorderRecursive(root); // 4 5 2 3 1
        System.out.println();
    }
}
```

### 🔹 Iterative Preorder and Postorder

**Stack-based iterative traversals**

```java
import java.util.*;

public class IterativeTraversals {
    
    public static void preorderIterative(TreeNode root) {
        if (root == null) return;
        
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        
        while (!stack.isEmpty()) {
            TreeNode current = stack.pop();
            System.out.print(current.val + " ");
            
            // Push right first, then left (so left is processed first)
            if (current.right != null) stack.push(current.right);
            if (current.left != null) stack.push(current.left);
        }
    }
    
    public static void postorderIterative(TreeNode root) {
        if (root == null) return;
        
        Stack<TreeNode> stack1 = new Stack<>();
        Stack<TreeNode> stack2 = new Stack<>();
        
        stack1.push(root);
        
        while (!stack1.isEmpty()) {
            TreeNode current = stack1.pop();
            stack2.push(current);
            
            if (current.left != null) stack1.push(current.left);
            if (current.right != null) stack1.push(current.right);
        }
        
        while (!stack2.isEmpty()) {
            System.out.print(stack2.pop().val + " ");
        }
    }
    
    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        
        System.out.print("Iterative Preorder: ");
        preorderIterative(root); // 1 2 4 5 3
        System.out.println();
        
        System.out.print("Iterative Postorder: ");
        postorderIterative(root); // 4 5 2 3 1
        System.out.println();
    }
}
```

### 🔹 Best Practices

```java
// Use recursive for simplicity, iterative for large trees
// Inorder gives sorted order for BST
// Preorder useful for copying trees
// Postorder useful for deletion
```

### 🔹 Time and Space Complexity

```java
// All traversals: O(n) time, O(h) space (h = height)
// Recursive: O(h) call stack space
// Iterative: O(h) stack space
```

---

## 🎯 Interview One-Liner

> Tree traversal: inorder left-root-right, preorder root-left-right, postorder left-right-root; recursive simple, iterative for large trees; O(n) time.

---

## ✅ 233. Check if Binary Tree is BST

**Validating whether a binary tree is a Binary Search Tree by checking BST property.**

### 🔹 Recursive Approach with Bounds

**Check BST property recursively**

```java
public class ValidateBST {
    
    public static boolean isValidBST(TreeNode root) {
        return isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }
    
    private static boolean isValidBST(TreeNode node, long min, long max) {
        if (node == null) {
            return true;
        }
        
        if (node.val <= min || node.val >= max) {
            return false;
        }
        
        return isValidBST(node.left, min, node.val) && 
               isValidBST(node.right, node.val, max);
    }
    
    public static void main(String[] args) {
        // Valid BST
        TreeNode root1 = new TreeNode(2);
        root1.left = new TreeNode(1);
        root1.right = new TreeNode(3);
        System.out.println("Is valid BST: " + isValidBST(root1)); // true
        
        // Invalid BST
        TreeNode root2 = new TreeNode(5);
        root2.left = new TreeNode(1);
        root2.right = new TreeNode(4);
        root2.right.left = new TreeNode(3);
        root2.right.right = new TreeNode(6);
        System.out.println("Is valid BST: " + isValidBST(root2)); // false
    }
}
```

### 🔹 Inorder Traversal Approach

**BST property: inorder traversal should be sorted**

```java
import java.util.*;

public class ValidateBSTInorder {
    
    private static Integer prev = null;
    
    public static boolean isValidBST(TreeNode root) {
        prev = null;
        return inorderCheck(root);
    }
    
    private static boolean inorderCheck(TreeNode node) {
        if (node == null) {
            return true;
        }
        
        // Check left subtree
        if (!inorderCheck(node.left)) {
            return false;
        }
        
        // Check current node
        if (prev != null && node.val <= prev) {
            return false;
        }
        prev = node.val;
        
        // Check right subtree
        return inorderCheck(node.right);
    }
    
    public static void main(String[] args) {
        TreeNode root = new TreeNode(2);
        root.left = new TreeNode(1);
        root.right = new TreeNode(3);
        
        System.out.println("Is valid BST: " + isValidBST(root)); // true
    }
}
```

### 🔹 Best Practices

```java
// Use bounds checking for efficiency
// Inorder approach works but modifies state
// Handle integer overflow with long bounds
// Consider duplicate values based on problem requirements
```

### 🔹 Time and Space Complexity

```java
// Both approaches: O(n) time, O(h) space
// Bounds checking: cleaner, no state modification
// Inorder: uses class variable for state
```

---

## 🎯 Interview One-Liner

> Validate BST: recursive with bounds O(n) time/O(h) space preferred; inorder traversal alternative; check left < root < right property.

---

## ✅ 234. Level order traversal

**Breadth-first traversal visiting nodes level by level using a queue.**

### 🔹 Using Queue

**Iterative level order traversal**

```java
import java.util.*;

public class LevelOrderTraversal {
    
    public static void levelOrder(TreeNode root) {
        if (root == null) {
            return;
        }
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            
            for (int i = 0; i < levelSize; i++) {
                TreeNode current = queue.poll();
                System.out.print(current.val + " ");
                
                if (current.left != null) {
                    queue.add(current.left);
                }
                if (current.right != null) {
                    queue.add(current.right);
                }
            }
            System.out.println(); // New line for each level
        }
    }
    
    public static List<List<Integer>> levelOrderList(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> currentLevel = new ArrayList<>();
            
            for (int i = 0; i < levelSize; i++) {
                TreeNode current = queue.poll();
                currentLevel.add(current.val);
                
                if (current.left != null) {
                    queue.add(current.left);
                }
                if (current.right != null) {
                    queue.add(current.right);
                }
            }
            result.add(currentLevel);
        }
        
        return result;
    }
    
    public static void main(String[] args) {
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);
        
        System.out.println("Level order traversal:");
        levelOrder(root);
        /*
        Output:
        3
        9 20
        15 7
        */
        
        List<List<Integer>> levels = levelOrderList(root);
        System.out.println("Levels: " + levels);
    }
}
```

### 🔹 Recursive Approach

**Recursive level order (less efficient)**

```java
import java.util.*;

public class LevelOrderRecursive {
    
    public static void levelOrderRecursive(TreeNode root) {
        int height = getHeight(root);
        for (int i = 1; i <= height; i++) {
            printLevel(root, i);
            System.out.println();
        }
    }
    
    private static void printLevel(TreeNode node, int level) {
        if (node == null) {
            return;
        }
        
        if (level == 1) {
            System.out.print(node.val + " ");
        } else {
            printLevel(node.left, level - 1);
            printLevel(node.right, level - 1);
        }
    }
    
    private static int getHeight(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }
    
    public static void main(String[] args) {
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        
        levelOrderRecursive(root);
    }
}
```

### 🔹 Best Practices

```java
// Use iterative queue approach for efficiency
// Process level by level using queue size
// Useful for finding shortest path in unweighted graphs
// Can be modified for zigzag level order
```

### 🔹 Time and Space Complexity

```java
// Iterative: O(n) time, O(w) space (w = max width)
// Recursive: O(n) time, O(h) space plus multiple traversals
```

---

## 🎯 Interview One-Liner

> Level order traversal: queue-based BFS O(n) time/O(w) space; process level-by-level using queue size; breadth-first search approach.

---

### **String Manipulation**
235. Longest substring without repeating characters
236. Valid parentheses check
237. String compression (aabbbcc → a2b3c2)
238. Count words in string
239. Reverse words in sentence

## ✅ 235. Longest substring without repeating characters

**Find the longest substring containing unique characters using sliding window and HashMap for last seen positions.**

### 🔹 Sliding Window with Last Index Map

```java
import java.util.*;

public class LongestUniqueSubstring {
    public static int lengthOfLongestUniqueSubstr(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        
        Map<Character, Integer> lastIndex = new HashMap<>();
        int maxLen = 0;
        int start = 0;
        
        for (int end = 0; end < s.length(); end++) {
            char ch = s.charAt(end);
            if (lastIndex.containsKey(ch) && lastIndex.get(ch) >= start) {
                start = lastIndex.get(ch) + 1;
            }
            lastIndex.put(ch, end);
            maxLen = Math.max(maxLen, end - start + 1);
        }
        
        return maxLen;
    }
    
    public static void main(String[] args) {
        System.out.println(lengthOfLongestUniqueSubstr("abcabcbb")); // 3
        System.out.println(lengthOfLongestUniqueSubstr("bbbbb")); // 1
        System.out.println(lengthOfLongestUniqueSubstr("pwwkew")); // 3
    }
}
```

### 🔹 Best Practices

```java
// Use sliding window to keep current unique substring range
// Use HashMap for last seen indices
// Move start pointer only when duplicate is inside window
// Handle Unicode if needed using a larger map or int[] for ASCII
```

### 🔹 Time and Space Complexity

```java
// Time: O(n)
// Space: O(min(n, m)) where m is character set size
```

---

## 🎯 Interview One-Liner

> Longest substring without repeating chars: sliding window + last-index map; update start on duplicate; O(n) time.

---

## ✅ 236. Valid parentheses check

**Validate balanced parentheses, brackets and braces using a stack.**

### 🔹 Stack-Based Validation

```java
import java.util.*;

public class ValidParentheses {
    public static boolean isValid(String s) {
        if (s == null || s.length() % 2 != 0) {
            return false;
        }
        
        Map<Character, Character> pairs = new HashMap<>();
        pairs.put(')', '(');
        pairs.put('}', '{');
        pairs.put(']', '[');
        
        Deque<Character> stack = new ArrayDeque<>();
        for (char ch : s.toCharArray()) {
            if (pairs.containsValue(ch)) {
                stack.push(ch);
            } else if (pairs.containsKey(ch)) {
                if (stack.isEmpty() || stack.pop() != pairs.get(ch)) {
                    return false;
                }
            } else {
                return false; // invalid character
            }
        }
        
        return stack.isEmpty();
    }
    
    public static void main(String[] args) {
        System.out.println(isValid("()[]{}")); // true
        System.out.println(isValid("(]")); // false
        System.out.println(isValid("([{}])")); // true
    }
}
```

### 🔹 Best Practices

```java
// Push opening chars and match closing chars against stack top
// Reject invalid characters and odd-length strings early
// Use ArrayDeque instead of Stack for better performance
// Always check stack emptiness after processing
```

### 🔹 Time and Space Complexity

```java
// Time: O(n)
// Space: O(n)
```

---

## 🎯 Interview One-Liner

> Valid parentheses: push opens, pop on matching close; use stack/ArrayDeque; balanced if stack empty at end.

---

## ✅ 237. String compression (aabbbcc → a2b3c2)

**Compress strings by collapsing consecutive repeating characters and preserving order.**

### 🔹 Run-Length Encoding

```java
public class StringCompression {
    public static String compress(String s) {
        if (s == null || s.isEmpty()) {
            return s;
        }
        
        StringBuilder sb = new StringBuilder();
        int count = 1;
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == s.charAt(i - 1)) {
                count++;
            } else {
                sb.append(s.charAt(i - 1)).append(count);
                count = 1;
            }
        }
        sb.append(s.charAt(s.length() - 1)).append(count);
        
        return sb.toString();
    }
    
    public static void main(String[] args) {
        System.out.println(compress("aabbbcc")); // a2b3c2
        System.out.println(compress("abc")); // a1b1c1
    }
}
```

### 🔹 Best Practices

```java
// Use StringBuilder to avoid string concatenation overhead
// Handle empty and null strings
// Only compress consecutive repeats, preserve characters order
// Return original if compression requirement differs
```

### 🔹 Time and Space Complexity

```java
// Time: O(n)
// Space: O(n)
```

---

## 🎯 Interview One-Liner

> String compression: run-length encode consecutive duplicates with StringBuilder; O(n) time.

---

## ✅ 238. Count words in string

**Count words by splitting on whitespace while trimming leading and trailing spaces.**

### 🔹 Split on Whitespace

```java
public class WordCount {
    public static int countWords(String s) {
        if (s == null || s.trim().isEmpty()) {
            return 0;
        }
        
        String[] words = s.trim().split("\\s+");
        return words.length;
    }
    
    public static void main(String[] args) {
        System.out.println(countWords("Hello world")); // 2
        System.out.println(countWords("  Java  is   fun ")); // 3
        System.out.println(countWords("")); // 0
    }
}
```

### 🔹 Manual Scan Approach

```java
public class WordCountManual {
    public static int countWords(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        
        int count = 0;
        boolean inWord = false;
        for (char ch : s.toCharArray()) {
            if (Character.isWhitespace(ch)) {
                inWord = false;
            } else if (!inWord) {
                inWord = true;
                count++;
            }
        }
        return count;
    }
}
```

### 🔹 Best Practices

```java
// Use trim() and split("\\s+") for readability
// Manual scan is more precise for mixed whitespace
// Handle null and empty input safely
// Avoid counting spaces as words
```

### 🔹 Time and Space Complexity

```java
// Time: O(n)
// Space: O(n) for split, O(1) for manual scan
```

---

## 🎯 Interview One-Liner

> Word count: trim and split on whitespace, or scan manually with a boolean word flag; O(n) time.

---

## ✅ 239. Reverse words in sentence

**Reverse the order of words while preserving letters within each word.**

### 🔹 Split and Reverse

```java
import java.util.*;

public class ReverseWords {
    public static String reverseWords(String s) {
        if (s == null || s.trim().isEmpty()) {
            return "";
        }
        
        String[] words = s.trim().split("\\s+");
        Collections.reverse(Arrays.asList(words));
        return String.join(" ", words);
    }
    
    public static void main(String[] args) {
        System.out.println(reverseWords("Hello world")); // world Hello
        System.out.println(reverseWords("  Java is fun  ")); // fun is Java
    }
}
```

### 🔹 In-Place Character Array

```java
public class ReverseWordsInPlace {
    public static String reverseWords(String s) {
        if (s == null || s.trim().isEmpty()) {
            return "";
        }
        
        char[] chars = s.trim().toCharArray();
        reverse(chars, 0, chars.length - 1);
        
        int start = 0;
        for (int end = 0; end <= chars.length; end++) {
            if (end == chars.length || chars[end] == ' ') {
                reverse(chars, start, end - 1);
                start = end + 1;
            }
        }
        
        return new String(chars);
    }
    
    private static void reverse(char[] chars, int left, int right) {
        while (left < right) {
            char tmp = chars[left];
            chars[left++] = chars[right];
            chars[right--] = tmp;
        }
    }
}
```

### 🔹 Best Practices

```java
// Use split("\\s+") to normalize whitespace
// Preserve word contents and reverse only word order
// Trim input to remove leading/trailing spaces
// Use String.join for clean output
```

### 🔹 Time and Space Complexity

```java
// Time: O(n)
// Space: O(n)
```

---

## 🎯 Interview One-Liner

> Reverse words: split on whitespace, reverse the word list, join with spaces; O(n) time.

---

### **Number Problems**
240. Check if number is prime
241. Find GCD of two numbers
242. Armstrong number check
243. Print prime numbers up to N
244. Sum of digits

## ✅ 240. Check if number is prime

**Determine prime numbers efficiently using trial division up to sqrt(n).**

### 🔹 Prime Check

```java
public class PrimeCheck {
    public static boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }
        if (n <= 3) {
            return true;
        }
        if (n % 2 == 0 || n % 3 == 0) {
            return false;
        }
        
        for (int i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) {
                return false;
            }
        }
        return true;
    }
    
    public static void main(String[] args) {
        System.out.println(isPrime(2)); // true
        System.out.println(isPrime(15)); // false
        System.out.println(isPrime(17)); // true
    }
}
```

### 🔹 Best Practices

```java
// Handle n <= 1 as non-prime
// Check divisibility by 2 and 3 first
// Use 6k ± 1 optimization for remaining factors
// Avoid checking all numbers up to n
```

### 🔹 Time and Space Complexity

```java
// Time: O(sqrt(n))
// Space: O(1)
```

---

## 🎯 Interview One-Liner

> Prime check: trial divide up to sqrt(n) with 6k ± 1 optimization; O(sqrt(n)) time.

---

## ✅ 241. Find GCD of two numbers

**Compute greatest common divisor using Euclid's algorithm.**

### 🔹 Euclidean Algorithm

```java
public class GCD {
    public static int gcd(int a, int b) {
        if (a == 0) {
            return Math.abs(b);
        }
        if (b == 0) {
            return Math.abs(a);
        }
        
        a = Math.abs(a);
        b = Math.abs(b);
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
    
    public static void main(String[] args) {
        System.out.println(gcd(54, 24)); // 6
        System.out.println(gcd(17, 13)); // 1
    }
}
```

### 🔹 Best Practices

```java
// Use absolute values to handle negatives
// Return the non-zero value when one input is zero
// Use iterative Euclid for robust performance
```

### 🔹 Time and Space Complexity

```java
// Time: O(log min(a, b))
// Space: O(1)
```

---

## 🎯 Interview One-Liner

> GCD: Euclid's algorithm with modulo until remainder zero; O(log min(a,b)) time.

---

## ✅ 242. Armstrong number check

**Verify Armstrong number by comparing the sum of each digit raised to the power of digit count with the original number.**

### 🔹 Armstrong Check

```java
public class ArmstrongNumber {
    public static boolean isArmstrong(int n) {
        if (n < 0) {
            return false;
        }
        
        int original = n;
        int digits = String.valueOf(n).length();
        int sum = 0;
        
        while (n > 0) {
            int digit = n % 10;
            sum += Math.pow(digit, digits);
            n /= 10;
        }
        
        return sum == original;
    }
    
    public static void main(String[] args) {
        System.out.println(isArmstrong(153)); // true
        System.out.println(isArmstrong(9474)); // true
        System.out.println(isArmstrong(123)); // false
    }
}
```

### 🔹 Best Practices

```java
// Handle negative numbers as false
// Compute digit count once before the loop
// Compare the computed sum to the original number
```

### 🔹 Time and Space Complexity

```java
// Time: O(d) where d is digit count
// Space: O(1)
```

---

## 🎯 Interview One-Liner

> Armstrong number: sum each digit^digitCount and compare to original; O(d) time.

---

## ✅ 243. Print prime numbers up to N

**Generate primes up to N efficiently using the Sieve of Eratosthenes.**

### 🔹 Sieve of Eratosthenes

```java
import java.util.*;

public class SievePrimes {
    public static List<Integer> primesUpTo(int n) {
        List<Integer> primes = new ArrayList<>();
        if (n < 2) {
            return primes;
        }
        
        boolean[] isPrime = new boolean[n + 1];
        Arrays.fill(isPrime, true);
        isPrime[0] = false;
        isPrime[1] = false;
        
        for (int i = 2; i * i <= n; i++) {
            if (isPrime[i]) {
                for (int j = i * i; j <= n; j += i) {
                    isPrime[j] = false;
                }
            }
        }
        
        for (int i = 2; i <= n; i++) {
            if (isPrime[i]) {
                primes.add(i);
            }
        }
        return primes;
    }
    
    public static void main(String[] args) {
        System.out.println(primesUpTo(30)); // [2, 3, 5, 7, 11, 13, 17, 19, 23, 29]
    }
}
```

### 🔹 Best Practices

```java
// Use boolean array and mark multiples starting at i^2
// Avoid repeated checks for even numbers by handling 2 separately
// Use sieve for large N instead of repeated primality tests
```

### 🔹 Time and Space Complexity

```java
// Time: O(n log log n)
// Space: O(n)
```

---

## 🎯 Interview One-Liner

> Prime generation up to N: use Sieve of Eratosthenes, mark multiples from i^2; O(n log log n) time.

---

## ✅ 244. Sum of digits

**Calculate the sum of digits of a number using repeated division and modulo.**

### 🔹 Digit Sum Calculation

```java
public class SumOfDigits {
    public static int sumDigits(int n) {
        int sum = 0;
        int num = Math.abs(n);
        while (num > 0) {
            sum += num % 10;
            num /= 10;
        }
        return sum;
    }
    
    public static void main(String[] args) {
        System.out.println(sumDigits(12345)); // 15
        System.out.println(sumDigits(-98)); // 17
    }
}
```

### 🔹 Best Practices

```java
// Use Math.abs to handle negative numbers
// Avoid string conversion if performance matters
// Use modulo and division in a loop
```

### 🔹 Time and Space Complexity

```java
// Time: O(d)
// Space: O(1)
```

---

## 🎯 Interview One-Liner

> Sum of digits: repeatedly mod 10 and divide by 10; use abs for negatives; O(d) time.

---

### **Pattern Programs**
245. Print pyramid pattern
246. Print diamond pattern
247. Number patterns

## ✅ 245. Print pyramid pattern

**Print a centered pyramid pattern of stars using nested loops.**

### 🔹 Pyramid Pattern

```java
public class PyramidPattern {
    public static void printPyramid(int rows) {
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= rows - i; j++) {
                System.out.print(" ");
            }
            for (int k = 1; k <= 2 * i - 1; k++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }
    
    public static void main(String[] args) {
        printPyramid(5);
    }
}
```

### 🔹 Best Practices

```java
// Use nested loops for spaces and stars
// Print rows from 1 to n for increasing width
// Use 2*i-1 stars for centered pyramid shape
```

### 🔹 Time and Space Complexity

```java
// Time: O(n^2)
// Space: O(1)
```

---

## 🎯 Interview One-Liner

> Pyramid pattern: nested loops, print spaces then 2*i-1 stars per row; O(n^2) time.

---

## ✅ 246. Print diamond pattern

**Create a diamond shape by combining a top pyramid and a bottom inverted pyramid.**

### 🔹 Diamond Pattern

```java
public class DiamondPattern {
    public static void printDiamond(int rows) {
        // Top half
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= rows - i; j++) {
                System.out.print(" ");
            }
            for (int k = 1; k <= 2 * i - 1; k++) {
                System.out.print("*");
            }
            System.out.println();
        }
        // Bottom half
        for (int i = rows - 1; i >= 1; i--) {
            for (int j = 1; j <= rows - i; j++) {
                System.out.print(" ");
            }
            for (int k = 1; k <= 2 * i - 1; k++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }
    
    public static void main(String[] args) {
        printDiamond(5);
    }
}
```

### 🔹 Best Practices

```java
// Build top and bottom halves separately
// Use spaces to center each row
// Avoid duplicating the middle row twice
```

### 🔹 Time and Space Complexity

```java
// Time: O(n^2)
// Space: O(1)
```

---

## 🎯 Interview One-Liner

> Diamond pattern: top and bottom halves, center rows with spaces, avoid double middle row; O(n^2) time.

---

## ✅ 247. Number patterns

**Print common number patterns such as Floyd's triangle, right-angle numbers, or mirrored sequences.**

### 🔹 Floyd's Triangle

```java
public class FloydTriangle {
    public static void printFloydTriangle(int rows) {
        int num = 1;
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= i; j++) {
                System.out.print(num++ + " ");
            }
            System.out.println();
        }
    }
    
    public static void main(String[] args) {
        printFloydTriangle(5);
    }
}
```

### 🔹 Right-Angle Number Pattern

```java
public class NumberTriangle {
    public static void printNumberTriangle(int rows) {
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= i; j++) {
                System.out.print(j + " ");
            }
            System.out.println();
        }
    }
    
    public static void main(String[] args) {
        printNumberTriangle(5);
    }
}
```

### 🔹 Best Practices

```java
// Use nested loops for pattern rows and columns
// Choose counters carefully for numbers vs stars
// Print newline after each row
```

### 🔹 Time and Space Complexity

```java
// Time: O(n^2)
// Space: O(1)
```

---

## 🎯 Interview One-Liner

> Number patterns: nested loops generate rows and values; Floyd's triangle uses increasing counter, right-angle prints 1..i; O(n^2) time.

---