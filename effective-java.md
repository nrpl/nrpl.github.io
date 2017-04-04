Effective Java
==============
This document sums up the advices of Joshua Bloch's Book Effective Java.

Chapter 8: General Programming
------------------------------
Item 45: Minimize Scope of local Variables
* Declare it where it is first used
* Initialize variables sensibly on declaration
* prefer for loops to while loops
* keep methods small and focused

Item 46: Prefer for-each loops to traditional for loops
* use for-each whenever possible
* implement iterable for your own types

Item 47: Know and use the libraries
* use expert knowledge and experience in practice by using standard libraries
* don't reinvent the wheel

Item 48: Avoid floar and double if exact answers are required
* float and double are primarily designed for engineering and scientific needs
* they are NOT designed for monetary calculations
* use BigDecimal, int or long for monetary calculations

Item 49: Prefer primitive types to boxed primitives
* applying the == operator on boxed primitives is almost always wrong
* auto-boxing and auto-unboxing cause performance penalties

Item 50: Avoid Strings where other types are more appropriate
* Strings are designed to represent textual values
* Strings are poor substitutes for other value types
* Strings are poor substitutes for enum types

Item 51: Beware the performance of String concatenation
* do not use the concatenation operator repeatedly
* n string concatenations requires time quadratic in n
* use StringBuilder or StringBuffer instead

Item 52: Refer to object by their interfaces
* If appropriate interface types exist, then parameters, return values, variables and fields should all be declared using interface types

Item 53: Prefer interfaces to reflection
* objects should not be accessed reflectively in normal programs at runtime
* create instances reflectively and access them normally via their interface or superclass

Item 54: Use native methods judiciously
* it is rarely advisable to use native methods for improved performance

Item 55: Optimize judiciously
* strive to write good programs rather than fast ones
* strive to avoid design decisions that limit performance
* consider the performance consequences of your API design decisions
* it is a very bad idea to warp an API to achieve good performance
* measure performance before and after each attempted optimization

Item 56: Adhere to generally accepted naming conventions
* never violate the [Java Language Specification](http://docs.oracle.com/javase/specs/) naming conventions without a good reason
* package names should be domain names in reverse order
* class, interface and enum: noun or noun phrase, first letter of each word capitalized, e.g. UserRepository
* method and field names: verb or verb phrase, first letter of first word in lowercase, first letter of each subsequent word uppercase, e.g. calculateDeviation
* methods that return a boolen should start with 'is', e.g. isValidSelection
* constants: uppercase letters separated by underscore, e.g. MIN_VALUE
