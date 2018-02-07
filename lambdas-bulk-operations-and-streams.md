# Lambdas, Functional Interfaces, Bulk Operations and Streams
Java 8 introduced Lambda Expressions, Functional Interfaces 
and Bulk Operations on Collections.
Bulk Opertions include Streams.
These features can be used to write more efficient code:
- they can be used to write more compact expressions
- they reduce boilerplate code
- parallelisation can be handled by the java framework

This document gives an overview of the concepts and demonstrates the features 
based on code samples.

## Lambdas
- lambda expressions 
- represent a function
- can replace class definition containing only one method
- can be passed to method as parameter (functional programming)
- a lambda expressions implements a functional interface

## Functional Interfaces
- implicitly exists before Java 8
- can be marked by @FunctionalInterface since Java 8
- is defined to be an interface with only one abstract method
- also known as Single Abstract Method Type (SAM)
- Examples:
    - Comparator
    - Runnable
    - ...

### Predicate
- java.util.function.Predicate<Type>
- boolean test(T)
- default methods:
    - and
    - negate
    - or
- can be used to check things like
    - age
    - null
- can be passed to bulk operation removeIf (see removeIf)
- Example:
  ```
  Predicate<String> isNull = str -> str == null;
  Predicate<String> isEmpty = String::isEmpty;

  isNull.test(null);    // true
  isNull.test("hello"); // false
  isEmpty.test("");     // true
  isEmpty.test("...");  // false
  ```

### UnaryOperator
- java.util.function.UnaryOperator<T>
- provides static method identity()
- extends Function<T,R> with its apply() method
- apply() maps object of type T to an object of the same type
- can be used to 
    - edit String objects
    - trim String objects
    - map null objects to default values
- can be passed to bulk operation replaceAll
- Example:
  ```
  List<String> names = new ArrayList<>(
      Arrays.asList(
          " foo", 
          "bar ", 
          "hans", 
          "Otto", 
          "and...", 
          "...so", 
          "on"
  ));
 
  UnaryOperator<String> transferDotsToSmileys =
      str -> str.contains("...") ? str.replace("...", ":-)") : str;

  names.replaceAll( transferDotsToSmileys );

  names.forEach(System.out::println);
  ```

## Bulk Operations
### forEach
### removeIf
- removeIf takes Predicate as parameter
  ```
  List<String> names = new LinkedList<>(Arrays.asList(
      "Hans",
      "Otto",
      "Anna",
      " ",
      ""
  ));

  System.out.println(names.size());               // 5
  list.removeIf(name -> name.trim().isEmpty());
  System.out.println(names.size());               // 3
  ```
- replaceAll takes UnaryOperator as parameter

### Streams
Streams represent a pipeline concept for manipulating data.
The pipeline consists of several steps.
The first step is to create a Stream based on a Datasource.
The Stream object can then be used to apply intermediate operations.
These operations can manipulate or filter the data.
A terminal operation is used to provide the result at the end of the pipeline.

The concept is illustrated below:
```
Datasource --> Stream --> operation1 --> operation2 --> operationN --> result
```

Example:
```
List<String> names = new ArrayList<>(
    Arrays.asList(
        "hans", 
        "otto", 
        "anna", 
        "anne"
    )
);

names.stream().filter( name -> name.isEmpty).collect(Collectors.toList());
```

#### Creating Streams
- from Collections: 
    - `myList.stream()`
    - `myList.parallelStream()`
- from Array: 
    - `Arrays.stream(myArray)`
    - `Arrays.stream(myArray).parallel()` 
- using Factory Methods for String or Integer
    - `Stream.of("foo", "bar")`
    - `Stream.of(1, 2, 3)`
- using Factory Methods for primitives
    - `IntStream.range(23, 42)`
    - `"foobar".chars()`

#### Intermediate Operations
Intermediate operations can be stateless or stateful.
Filtering is stateless, because the objects can be checked independently from each other.
Stateless operations can be parallelised.
Sorting is a stateful operation, because more than one object has to be considered.

Intermediate Operations are not executed on the fly.
They are used to prepare the pipeline and get executed by the terminal operation.

- filter() removes elemets which do not comply to the given Predicate
  ```
  List<Customers> = regularCustomers = customers.steam()
    .filter( c -> c.isRegularCustomer() )
    .collect( Collectors.toList());
  ```
- map() transforms elements from one type to another type
  ```
  List<String> namesOfRegularCusotmers = customers.stream()
      .filter(customer -> customer.isRegularCustomer())
      .map(customer -> customer.getName())
      .collect(Collectors.toList());
  ```
- flatMap()

- peek() can be used for debugging
  ```
  List<String> namesOfRegularCusotmers = customers.stream()
      .filter(customer -> customer.isRegularCustomer())
      .peek(System.out::println)
      .map(customer -> customer.getName())
      .peek(System.out::println)
      .collect(Collectors.toList());
  ```
- distinct() removes duplicated objects
  ```
  Stream<Integer> integerStream = Stream.of(1, 2, 3, 4, 5, 1, 4, 3, 6);
  integerStream.distinct().forEach(System.out::println);
  ```
- sorted() sorting with or without Comparator
  ```
  Stream<Integer> integerStream = Stream.of(1, 2, 3, 4, 5, 1, 4, 3, 6);
  integerStream.distinct().sorted().forEach(System.out::println);
  ```
- limit() limits the elements
  ```
  Stream<Integer> integerStream = Stream.of(1, 2, 3, 4, 5, 1, 4, 3, 6);
  integerStream
    .distinct()
    .sorted()
    .limit(5)
    .forEach(System.out::println);
  ```
- skip() skips n-elements, useful for paging
  ```
  Stream<Integer> integerStream = Stream.of(1, 2, 3, 4, 5, 1, 4, 3, 6);
  integerStream
    .distinct()
    .sorted()
    .limit(5)
    .skip(3)
    .forEach(System.out::println);
  ```
#### Terminal Operations
- forEach() iterate elements of Stream
- toArray() create Array from Stream
- collect() create Collection from Stream
- count(), sum(), min(), max(), average() perform calculations
- allMatch(), anyMatch(), noneMatch() do all, any, no elements match Predicate
- findFirst(), findAny() return first or any element, retunrs Optional
- reduce() summarise elements:
  ```
  List<String> strings = new ArrayList<>(Arrays.asList("hans", "otto", "anna"));
  Optional<String> reduce = strings.stream()
    .reduce((s1, s2) -> {
      return s1 + "," + s2;
    }
  );
  System.out.println(reduce.get().toString());  // hans,otto,anna
  ```
- joining(), groupingBy(), partitioningBy()

## References
- [Java 8 - Die Neuerungen, Michael Inden](https://www.amazon.de/dp/3864902908/)
