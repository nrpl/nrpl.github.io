Effective Java
==============
This document sums up the advices of Joshua Bloch's Book Effective Java.
## Chapter 1: Introduction



## Chapter 2: Creating and Destroying objects
### Item 1: Consider static factory methods instead of constructors
* static factory method returns instance of class
* advantages
  * factory methods are named, constructors not
  * construtors create new objects, factory methods can reuse objects
  * factory methods can return subtypes, construtors not
* disadvantages
  * classes without public/protected constructor can not be subclassed
  * factory methods are not distinguishable from other methods
  * not useful for many parameters (use builder, see Item 2)
* naming: valueOf(...), of(...), getInstance(), newInstance(), getType(), newType()

### Item 2: Consider builder when faced with many constructor parameters
* avoid using telescoping constructor pattern (many constructors which call each other successively)
* java beans may be in inconsistent state partway through its construction (setter)
* Builder Pattern combines readability of Beans and safety of telescoping constructor
* Builder Pattern simulates names optional parameters
* Builder Pattern is useful for classes that would have many constructor/factory-method parameters
* Usage:
  ```
  WeatherConditions weatherConditions = WeatherConditions.Builder().temperature(23).humidity(40).cloudBase(3000).build();
  ```
* Implementation:
  ```
  public class WeatherConditions {
    private final int temperature;
    private final int humidity;
    private final int cloudBase;
    ...

    public static class Builder() {
      private int temperature = 0;
      private int humidity = 0;
      private int cloudBase = 0;

      public Builder temperature(int temperature) {
          this.temperature = temperature;
          return this;
      }

      ...

      public WeatherConditions build() {
        return new WeatherConditions(this);
      }
    }

    private WeatherConditions(Builder builder) {
      this.temperature = builder.temperature;
      ...
    }
  }
  ```

### Item 3: Enforce the singleton property with a private constructor pr an enum type
* there are different approaches to implement singleton
* a single-element enum type is the best way!
  ```
  public enum MySingleton {
    INSTANCE;

    public void doSomething() {
      ...
    }
  }
  ```

### Item 4: Enforce noninstantiability with a private constructor
* utility classes group static methods and fields and should not be instantiated
* prevent instantiation with a private constructor
* to prevent instantiation from the class itself throw an Exception in the constructor
* do not use abstract classes for this purpose

### Item 5: Avoid creating unnecessary objects
* immutable objects should not be created using the constructor, instead the static factory should be used which reuses existent objects
* never use `new String("...")``
* consider moving local variables into fields which are initialized only once
* prefer primitives to boxed primitives and watch out for autoboxing

### Item 6: Eliminate obsolete object references
* null out obsolete references when managing the memory, e.g. shrinking arrays, working with caches, listeners and callbacks
* but nulling out references should be the exception

### Item 7: Avoid finalizers
* as a rule of thumb: avoid finalizers
* finalizers are not guaranteed to be executed promptly
* never do anything time-critical in a finalizer
  * do not close files in a finalizer
* never depend on a finalizer to update critical persistent state
* using finalizers causes severe performance penalties
* exceptions in finalizers do not print stacktraces, they just terminate silently
* do provide an explicit termination method and call this method after using the object
  * see close method of InputStream
*  use try-finally and call the close method in the finally block
* finalizers can be used as last resort to call the close method, but then they should log an warning to indicate this flaw

## Chapter 3: Methods Common to All Objects
### Item 8: Conform to the general contract when overriding equals
* value classes should override equals to test for logical equality
* enum classes and Singletons do not need to override equals
* do not override equals in these cases:
  * each instance of the class is inherently unique (e.g. Thread)
  * a "logical equality" test is not needed (e.g. provided by superclass) or wanted
  * you are certain, that the equals method will never be invoked (private or package-private classes),
  but then you should provide something like this:
  ```
  @Override public boolean equals(Object obj) {
    throw new AssertionError();
  }
  ```
* always override hashCode when you override equals  
* the contract of equals:
  * Reflexivity: An object must be equal to itself
  * Symmetry: a.equals(b) == b.equals(a)
  * Transitivity: a.equals(b), b.equals(c) then a.equals(c)
  * Consistency: a.equals(b) should always return the same result unless one or both objects are changed
  * Non-nullity: equals must return false on null and though must be null-safe
* do not write an equals method that depends on unreliable resources
* implement equals by following these steps:
  * use the == operator to check if the argument is a reference to this object
  * use the instanceof operator to check if the argument has the correct type
  * Cast the object to the correct type
  * check for each significant field that the corresponding field of the argument matches
    * use == for primitives excluding float and double
    * use Float.compare / Double.compare for float and double
    * use equals for object references
    * use Arrays.equals for Arrays
  * write unit tests to check the functionality

### Item 9: Always override hashCode when you override equals
* you must override hashCode() when you override equals
* equal objects must have equal hashCodes
* unequal objects should have unequal hash codes
* hashCode is used in hash-based collections to distribute values into buckets
* a good hashCode method should distribute all instances evenly across all possible hash values
* you must exclude fields from the hashCode computation that are not used in equals
* If performance is important consider caching the hash value
* do not omit significant values from the computation for performacne reasons, this may lead to collisions and bad performance (O^2) of hash-based collections
* state-of-the-art hash functions are a research topic and best left mathematicians

### Item 10: Always override toString
* a good toString method makes your class much more pleasant to use
* should return all of the interesting information of the object
* should be self-explanatory

### Item 11: Override clone judiciously
* consider not using or implementing the clone-method cause of its shortcomings
* immutable classes should not implement Cloneable
* classes designed for inheritance should not implement Cloneable
* interfaces should not extend Cloneable
* an alternative to clone is providing a copy constructor or copy factory
* a class that implements Cloneable is expected to provide a properly functioning public clone method which returns an instance of that class
* if you override the clone method in a nonfinal class, you should return an object obtained by invoking super.clone()
* be careful of you are cloning reference types: modifications in the clone must not affect the original
  * use clone() to clone arrays
* the clone architecture is incompatible with final fields referring to mutable objects
  * it may be necessary to remove the final keyword in order to make clone work
* be careful when cloning complex objects (deep copy)

### Item 12: Consider implementing Comparable
* if you are writing a value class with an obvious natural ordering you should implement Comparable
* writing a compareTo method is similar to writing an equals method
* compareTo should throw a ClassCastException if two objects of different classes are compared
* compareTo should return
  * -1 if this object is less than the specified
  * 0 if this object is equals than the specified
  * 1 if this object is greater than the specified
* for integral primitives use < and >
* for double and float use Double.compare / Float.compare
* if your object has multiple fields included in the comparison you have to start with the most significant
* compareTo should be consistent with equals
* sorted collections and the Utility classes Arrays and Collections rely on compareTo
* Example:
  ```
    public int compareTo(PhoneNumber phoneNumber) {
      if(areaCode < phoneNumber.areaCode)
        return -1;
      if(areaCode > phoneNumber.areaCode)
        return 1;
      if(lineNumber < phoneNumber.lineNumber)
        return -1;
      if(lineNumber > phoneNumber.lineNumber)
        return 1;
      return 0;
    }
  ```

<!-- ## Chapter 4: Classes and interfaces (KW 18) -->
<!-- ## Chapter 5 (KW 20) -->
<!-- ## Chapter 6 (KW 21) -->
<!-- ## Chapter 7 (KW 22) -->

## Chapter 8: General Programming
### Item 45: Minimize Scope of local Variables
* Declare it where it is first used
* Initialize variables sensibly on declaration
* prefer for loops to while loops
* keep methods small and focused

### Item 46: Prefer for-each loops to traditional for loops
* use for-each whenever possible
* implement iterable for your own types

### Item 47: Know and use the libraries
* use expert knowledge and experience in practice by using standard libraries
* don't reinvent the wheel

### Item 48: Avoid float and double if exact answers are required
* float and double are primarily designed for engineering and scientific needs
* they are NOT designed for monetary calculations
* use BigDecimal, int or long for monetary calculations

### Item 49: Prefer primitive types to boxed primitives
* applying the == operator on boxed primitives is almost always wrong
* auto-boxing and auto-unboxing cause performance penalties

### Item 50: Avoid Strings where other types are more appropriate
* Strings are designed to represent textual values
* Strings are poor substitutes for other value types
* Strings are poor substitutes for enum types

### Item 51: Beware the performance of String concatenation
* do not use the concatenation operator repeatedly
* n string concatenations requires time quadratic in n
* use StringBuilder or StringBuffer instead

### Item 52: Refer to object by their interfaces
* If appropriate interface types exist, then parameters, return values, variables and fields should all be declared using interface types

### Item 53: Prefer interfaces to reflection
* objects should not be accessed reflectively in normal programs at runtime
* create instances reflectively and access them normally via their interface or superclass

### Item 54: Use native methods judiciously
* it is rarely advisable to use native methods for improved performance

### Item 55: Optimize judiciously
* strive to write good programs rather than fast ones
* strive to avoid design decisions that limit performance
* consider the performance consequences of your API design decisions
* it is a very bad idea to warp an API to achieve good performance
* measure performance before and after each attempted optimization

### Item 56: Adhere to generally accepted naming conventions
* never violate the [Java Language Specification](http://docs.oracle.com/javase/specs/) naming conventions without a good reason
* package names should be domain names in reverse order
* class, interface and enum: noun or noun phrase, first letter of each word capitalized, e.g. UserRepository
* method and field names: verb or verb phrase, first letter of first word in lowercase, first letter of each subsequent word uppercase, e.g. calculateDeviation
* methods that return a boolen should start with 'is', e.g. isValidSelection
* constants: uppercase letters separated by underscore, e.g. MIN_VALUE

<!-- ## Chapter 9 (KW 23) -->
<!-- ## Chapter 10 (KW 24) -->
<!-- ## Chapter 11 (KW 25) -->
