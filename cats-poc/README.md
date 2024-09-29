# Scala Cats

- https://github.com/typelevel/cats
- https://typelevel.org/cats/
- https://www.baeldung.com/scala/cats-intro

A Good presentation:
Finishes on time (5 min)
Has code
Has Trade-offs Pros and Cons
Has images
Explain how it works
Explain the architecture
Compare with similar solutions

## About

- a Scala library that provides abstractions supporting a typeful, functional programming style
- tooling for building asynchronous and concurrent applications.
- Type Class (https://www.baeldung.com/scala/type-classes) it allows extend libs with new functionality, without traditional inheritance.
  In Scala Cats, components of type classes can be specified as:
  - Type class
  - Instances of type class
  - Interface objects
  - Interface syntax
- Working with type classes in Scala means we have to work with implicit values and implicit parameters. Any definitions marked implicit in Scala must be placed inside an object or trait rather than at the top level.

- The name cats comes from Category theory
  - 
## sbt project compiled with Scala 3

### Usage

This is a normal sbt project. You can compile code with `sbt compile`, run it with `sbt run`, and `sbt console` will start a Scala 3 REPL.

For more information on the sbt-dotty plugin, see the
[scala3-example-project](https://github.com/scala/scala3-example-project/blob/main/README.md).
