
Effective Java
==============
This document sums up the advices of Joshua Bloch's Book Effective Java.
## Chapter 1: Introduction


## Chapter 2: Creating and Destroying objects
### Item 1: Consider static factory methods instead of constructors
* static factory method returns instance of class
* advantages
  * factory methods are named, constructors not
  * constructors create new objects, factory methods can reuse objects
  * factory methods can return subtypes, constructors not
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

### Item 3: Enforce the singleton property with a private constructor or an enum type
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

## Chapter 4: Classes and interfaces
### Item 13: Minimize the accessibility of classes and members
* internal data and implementatio details should be hidden and only be accessible through an well defined API
* instance fields should never be public
* public static final fields should only hold references to immutable classes
* a class should never have a public static final array field
  * make it private and provide an access method which returns unmodifiable lists or cloned array
* make each class or member as inaccessible as possible
  * private -> package private ---> protected -> public
  * proteced and public define the public API, try to avoid them

### Item 14: In public classes use accessor methods not public fields
* if a class is package-private or private it is okay to use public fields
* if a class is accessible outside its package:
  * do not use public fields for mutable attributes
  * provide accessor methods (getter)
  * provide mutator methods (setter) for mutable attributes

### Item 15: Minimize mutability
* you should always make small value classes immutable
* classes should be immutable unless there is a very good reason to make them mutable
  * do not provide mutators/setters unless needed
  * make every field final and private unless there is a compelling reason to make it not final/private
  * prevent subclassing, e.g. by making it final
  * ensure exclusive access to mutable objects (defensive copies in constructors/accessors)
* if a class cannot be made immutable, limit its mutability as much as possible
  * only provide mutators/setters on necessary fields
* immutable objects are thread-safe and can be shared, they make great building blocks for other objects
* their only disadvantage is performance, due to object creation

### Item 16: Favor composition over inheritance
* do not use implementation inheritance over package-boundaries
* subclass depends on implementation details of superclass, therefore they have evolve in tandem
* use composition-and-forwarding instead of inheritance
  * forwarding class implements interface, holds an instance of and delegates to a concrete implementation for reusing its functionality
  * wrapper class extends forwarding class to add extra functionality
* see Decorator pattern
* only use implementation inheritance if there is a real is-a relationship
  * a stack is not a vector, but can be implemented using a vector
* using inheritance you use the same API as the superclass
* using composition you design your own API

### Item 17 Design and document for inheritance or else prohibit it
* designing a class for inheritance places substantial limitations on the class, but there are situations where it is cleary the right thing to do
* a class must document its self-use of overridable (non-final public/protected) methods
* each method that calls overridable methods should document the internal usage of these methods (special documentation)

  ``This implementation ...``
* this leads to the need of documenting implementation details which should be otherwise unspecified
* design for inheritance provides hooks into the internal workings of a class in the form of protected methods (and fields)
* the only way to test a class designed for inheritance is to write subclasses before you release it
* you should expose as few protected members as possible, each one is a commitment to an implementation detail
* too few protected members can make the class unusable for inheritance
* constructors must not invoke overridable methods
* by default classes are open for inheritance, but most of the time they should not be:
  * make classes final to prevent inheritance
  * use package-private/private constructors and use static factories to allow internal subclassing

### Item 18 Prefer interfaces to abstract classes
* existing classes can be easily retrofitted to implement a new interface
* interfaces enable safe, powerful functionality enhancements
* interfaces are ideal for defining mixins
* interfaces allow the construction of nonhierarchical type frameworks
* by combining abstract classes and interfaces you can provide a default implementation
* once an interface is released and widely implemented, it is almost impossible to change
* it is far more easier to evolve an abstract class

### Item 19 Use interfaces only to define types
* using interfaces solely to define constants is an anti pattern
  * ojects can be refered by the interface type, which is not realy specifing any behaviour and should be avoided
  * use utility classes (item 4) are enums (item 30) instead

### Item 20 Prefer class hierarchies to tagged classes
* do not use tagged classes (type field and if/else or switch statements to check of which type an object is)
* tagged classes are verbose, error-prone and inefficient
* a tagged class is just a pallid imitation of a class hierarchy

### Item 21 Use function objects to represent strategies
* see Comparator which is a function object for sorting elements
* Comparator is an Interface which can be implemented

### Item 22 Favor static member classes over non-static
* a nested class is defined within another class
* nested classes should only be used, if they only are used be the enclosing class
* there are 4 different types of nested classes:
  * static member classes
    * class declared within another class and marked as static
    * used as public helper class which is used in combination with the enclosing class
    * Example: Calculator.Operation.PLUS (Operation is a static member class of Calculator)
  * nonstatic member classes
    * nonstatic member classes always exist in combination with an instance of the enclosing class
    * used to implement Adapter (an instance of the outter class can be viewed as an instance of some unrelated class)
  * anonymous classes
    * class without a name
    * not a member of the enclosing class
    * declared an instantiated on the fly at the point of declaration
    * should not be longer than 10 lines
    * can be declared at any point where an expression can be placed
  * local classes
    * can be declared anywhere a local variable can be declared
* if you declare a member class that does not require access to an enclosing instance, always put the static modifier in its declaration


## Chapter 5: Generics
* Generics since Java 1.5 (09/2004)
* Compiler adds casts automatically at compile time
* generic types = classes/interfaces with type parameters, e.g. List<E>
* generic type information is only used at compile time, it is erased at runtime

### Item 23: Don't use raw types in new code
* if you use raw types, you lose all the safety and benefits of generics
* raw types are still supported to provide migration compatibility
* you lose type safety if you use raw types like List, but not if you use a parameterized type like List<Object>
* use generics with unbounded wildcard type List<?> if you don't know which type is used
* you must use raw types in class literals, e.g. String[].class but not List<String>.class
* you must use raw types with instanceof operator
* Set<Object> is a parameterized type representing a set that can contain objects of any type
* Set<?> is a wildcard type representing a set that can contain only objects of some unknown type
* Set is a raw type which is not using the benefits of the generic type system

### Item 24: Eliminate unchecked warnings
* unchecked type warnings are common when using generics
* eliminate every unchecked warning that you can
* if you can't eliminate a warning, you have to prove that the code is typesafe. then you can suppress the warning with @SupressWarnings("unchecked")
* always use the SupressWarnings annotation on the smallest scope possible
* every time you use an SupressWarnings annotation, add a comment describing why it is safe

### Item 25: Prefer lists to arrays
* adding wrong types to arrays fails at runtime, adding wrong types to generic types fails at compile time
* arrays and generics use fundamentally different concepts and do not mix well
  * arrays are covariant and reified
  * generics are invariant and erased
* varargs and generic types can lead to problems (compile-time errors and warnings)
  * replace arrays with lists

### Item 26: Favor generic types
* use generics for your own type implementations (e.g. your own Stack implementation) to prevent type casts in client code

### Item 27: Favor generic methods
* use generics for your own method declarations to prevent type casts in client code

### Item 28: Use bounded wildcards to increase API flexibility
* using wildcard types in your API makes it far more flexible
* if you are providing a library which is widely used, wildcard types should be considered mandatory

### Item 29: Consider typesafe heterogeneous containers
* the collections API is restricted to a fixed number of type parameters
* you can get around this restriction by placing the type parameter on the key rather than the container

## Chapter 6: Enums and Annotations
* Enums and Annotation Types are supported since Java 1.5

### Item 30: Use Enums instead of int constants
* enums are intended to replace int constants
* simple enums are just a list of named constants:
  ```
  public enum Activities { RUN, BIKE, SWIM }
  ```
* enums are classes that export one instance for each enumeration constant via a public static final field
* enums in java are more powerful than in other languages, they can implement behavior (rich enums)
* enums provide compile-time type-safety
* enums have an automatically generated valueOf(String) method which transfers the constants name to an enum instance
* if you provide a custom toString-method, consider implementing a fromString method
* use enums every time you need a fixed set of constants or you know all the possible values at compile time
* consider the enum-strategy-patterns if multiple enums share common behaviors

### Item 31: Use instance fields instead of ordinals
* never derive a value associated with an enum from its ordinal, use instance fields instead
  ```
  public enum Activities {
    RUN("run.png"), BIKE("bike.png"), SWIM("swim.png");
    private final String image;
    public Activities(String image) {this.image = image;}
    public String getImage() { return this.image; }
  }
  ```

<!-- ## Chapter 7 (MI 22, 181-208) -->


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

<!-- ## Chapter 9 (DO 23, 241-258) -->
<!-- ## Chapter 10 (FR 24, 259-288) -->
<!-- ## Chapter 11 (FR 25, 289-314) -->
