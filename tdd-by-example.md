# Test Driven Development by example

- the rythm of tdd:
  1. Write a failing test
  2. Run all tests, to see the new one failing
  3. Write the minimum code necessary to make the test passing
  4. Run all the tests and succeed
  5. Refactor to remove duplication

- in short:
  1. Write a test 
  2. Make it run 
  3. Make it right

- even shorter:
  1. Red
  2. Green
  3. Refactor

- the goal is clean code that works
	- TDD: first make code work, then make it clean
	- architecture-driven development: first make clean design, than make it work
- quickly getting the tests pass dominates everything!
- failure is progress
- the goal is not to find the right answer/solution to the problem, but to pass the test as simply as possible
- the smallest possible change that causes a passing test can be to return a static value (Fake it)
- after that you have to introduce more tests to force the implementation of business logic instead of returning static values (Triangulation)
- this workflow helps to focus, but at the beginning it feels just not right
- you should use a list of todos, to keep an eye on all the things which are not quite okay at the moment, but do not cause a failing test
- if a clean, simple solution is obvious and quick, then implement it,
  but if it will take you some time, then add it to the todo list
- TDD conflicts with good  (upfront) engineering, because you do dirty tricks to make the tests green as quick as possible,
  but there is a refactoring step and your todo list to make sure that these dirty tricks are replaced by production ready code.
- Refactoring is an essential part of the TDD process ensure clean code.
- tests are executed often during the TDD cycle, they have to fast
- it is okay to commit any number of sins to achieve a green test, but these sins have to be removed during the refactoring step, or they have to be added to the todo list
- good design at good times, first focus on solving the problem, then focus on good engineering principles
- with TDD you will end up with about the same number of lines of test code
  as model code
