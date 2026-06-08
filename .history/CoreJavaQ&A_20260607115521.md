# 📝 Core Java Interview Prep Summary

Quick checklist of essential core Java topics to review:

- **OOP fundamentals**: classes, objects, inheritance, polymorphism, encapsulation, abstraction.
- **Interfaces and abstract classes**: differences, when to use, default methods.
- **Collections framework**: List, Set, Map interfaces; ArrayList, LinkedList, HashSet, TreeSet, HashMap, TreeMap; equals/hashCode contract; iterators; performance.
- **Exception handling**: checked vs unchecked, try-catch-finally, throw vs throws, custom exceptions.
- **Generics**: type safety, wildcards, bounded types.
- **Concurrency**: threads, synchronization, volatile, locks, executors, atomic classes.
- **I/O and NIO**: file handling, streams, readers/writers, channels, buffers.
- **JVM internals**: class loading, bytecode, JIT, memory model, GC.
- **Reflection and annotations**: dynamic class inspection, custom annotations.
- **Serialization**: object serialization, externalizable, transient.
- **Java 8+ features**: lambdas, streams, optional, modules, records.

Missing topics to consider: design patterns, effective Java best practices, security (sandbox), internationalization, JDBC basics, RMI, advanced JVM tuning, pattern matching (Java 17+), sealed classes.

Use this as your revision guide before diving into the detailed Q&A below.

1. # Explain the complete contract between: equals() and hashCode()
Tell me:
What are the rules?
Why do they exist?
What happens if we break them?
Where is it practically important?

# MyAns: 
The contract between the equals and hashCode equals and hashCode are both are implemented or both are not implemented. If you are implemented only equals, then the hashCode is the different and then when we are storing in a hashmap, maybe concatenation is happened or duplication of hashCode is happened. So, and if we are same opposite if we are doing like writing the hashCode and not writing the equals, then the retrieval we are getting a problem when we are retrieving the any hashmap data or any data. So, the contact between the equals and hashCode is if you are override a equal from the object class, you must be override the hashCode. If you override the hashCode, you must be override the equals as well. What happen if we break them? If you break them, it will break the contract and the logic is not happening in the right direction or we are not getting the correct output, the program going to fail in some cases. Critical imported inside the collections.

# CorrectAnswer:
**equals() Contract Rules**
     Reflexive → x.equals(x) must be true;
     Symmetric → x.equals(y) == y.equals(x);
     Transitive → if x=y and y=z then x=z;
     Consistent → multiple calls give same result;
     x.equals(null) must return false;
**hashCode() Contract Rules**
     If two objects are equal → they MUST have same hashCode
     If two objects have same hashCode → they may or may not be equal     
**Why Contract Exists?**
     Because hash-based collections use:
     hashCode() → find bucket
     equals() → find exact object

     If contract breaks:
     Retrieval fails
     Duplicate logical keys stored
     Map behaves incorrectly     
**Where Practically Important?**
     HashMap
     HashSet
     ConcurrentHashMap
     Caching systems
2. # If equals() logic changes AFTER inserting object into HashMap, what happens?
# example
     Person p = new Person("Ram");
     map.put(p, "Developer");
     p.name = "Shyam";
     map.get(p);
 # MyAns  
     So, as I saw as per a code, we create the person, we created the person object and assigned a name as a Ram, and they are inserted into a map, and after that, they are changed the name into the Shyam, and they try to retrieve into a map. Now, because the name is changed and we stored the name with the Ram, so when we are retrieving with the name of Shyam, so data is not stored in a map, so we are not getting any output. Okay? So that's what happened. Map.getB not found.   
# CorrectAnswer:
     Step 1:
      hashCode calculated using "Ram"
      Suppose bucket index = 5
      Object stored in bucket 5
     
     Now:
     p.name ="Shayam"

     Now hashCode changes (because equals & hashCode depend on name).
     Now when we do:
     map.get(p);
     
     Step 2:
     hashCode calculated using "Shyam"
     Suppose bucket index = 9
     HashMap looks in bucket 9
     But object is in bucket 5

     So it does not find it.
     Result:
     Returns null

     This Is Called

Mutable key problem in HashMap
     Golden rule:
     👉 Never use mutable objects as HashMap keys
     👉 Always make key fields immutable

This is why:
     String is immutable and Good key candidate

3. # Question String:
   # Where are String objects stored?
   # What is String Pool?
   # Difference between:
      String s1 = "Ram";
      String s2 = new String("Ram");
   # Why is String immutable?
   # What is advantage of immutability?

# MyAns: 
    A string object is stored in a heap memory, but if you are not creating with using the new keyword, it will also cache the string constant pool. Difference between the string S1 and S2, it is first check if the object is present in the string constant pool. If it is, it is return the reference. And if it is not checked the string constant pool, the string is present or not, it is always write in the heap memory and return the reference. What is a string immutable? A string immutable is that if you are assigned something like S1.com and you want it to change, you will not, you just create a new object, but you cannot overwrite the old one. What is the advantage of immutability? Advantage of immutability, it will guarantee that if you write anything or assign anything in a string, it will never change. It will always be the same. It will be helpful in a hash map as a key parameters. Yeah, yes, and multiple places where we are need immutability.
# Where Are String Objects Stored?
     All objects are stored in Heap
     String pool is a special area inside Heap (since Java 7)

# What is Spring pool
     

