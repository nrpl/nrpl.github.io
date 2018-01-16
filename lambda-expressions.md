# Quick Introduction to Lambda Expressions
Lambdas, also known as Closures, are part of Java since Java 8.
They enable functional programming in Java.
By using Lambdas it is possible to avoid boilerplate code and simplify parallel processing.

In Java everything is defined inside a Class.
But if you just have one method in a class, there is a lot of boilerplate code.
Lambdas represent a block of code without additional wrapper code.
To define a lambda expression the following syntax is used:
```
(parameter list) -> { expression }
```

The following Lambda Expressions calculates the sum of two numbers:
```
(int x, int y) -> { return x+y; }
```
x and y are input parameters and the code between the curly braces is the logic.

You could implement a method representing the same logic:
```
int sumOf(int y, int x) { return x+y; }
```

The special feature with lambdas is, that you can assign a lambda expression to a variable:
```
Consumer<String> consumer = (String name) -> { System.out.println(name); };
```
The type of the variable needs to be a Functional Interface.
Consumer is a Functional Interface provided by Java.
See [Documentation](https://docs.oracle.com/javase/8/docs/api/java/util/function/Consumer.html) for details.

But what is an Functional Interface?
Functional Interfaces are Interfaces with exactly one abstract method.
They are also known as Single Abstract Method Type.

Remember: Each method in an interface is by default public and abstract.
But Interfaces can define static methods and since Java 8 interfaces can define default methods as well.

Since Java 8 Functional Interfaces can be annotated with `@FunctionalInterface`.
The annotation is optional.
The compiler automatically identifies interfaces with only one abstract method as Functional Interfaces.

Examples for Functional Interfaces are:
- Runnable with its run() method
- Comparator with its compare() method
- ActionListener
- EventHandler
- FileFilter
- FileNameFilter
Before Java8 you had to define a class which implements the Functional Interface.
This procedure results in boilerplate code as shown below:
```
button.addActionListener( new ActionListener() {
    @Override
    public void actionPerformed(final ActionEvent e) {
      System.out.println("Button clicked");
    }
});
```

With lambdas you can implement the same behaviour with less code
```
button.addActionListener( (e) -> { System.out.println("Button clicked"); })
```

A lambda expression stored in a variable can be passed to a method as parameter:
```
List<String> elements = Arrays.asList("foo", "bar");
Consumer<String> printElement = (element) -> { System.out.println(element); };
elements.forEach(printElement);
```

You can also pass it directly to a method:
```
List<String> elements = Arrays.asList("foo", "bar");
elements.forEach((element) -> { System.out.println(element); });
```

The crucial point it, that Lambda expressions can only be defined, if they implement a Functional Interface.
Lambdas make functional programming in Java possible and thus offer an elegant way to write more concise and readable code.
