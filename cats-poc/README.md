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
- Working with type classes in Scala means we have to work with implicit values and implicit parameters.
Any definitions marked implicit in Scala must be placed inside an object or trait rather than at the top level.

- data types
  - transformer
  - nested either
  - compose
  - EitherT[F[_], L, R] = F[Either[L, R]]
    - F[_] = Monand -> type class
    - L = Left
    - R = Right

- Functors allow us to sequence computations ignoring some complication
  - Monand is a type of functors

- The name cats comes from Category theory

- Fibers
  - Fibers are the fundamental abstraction in Cats Effect
  - enables concurrent operations
    - concurrent = execute multiple tasks on same core, with context switch
    - Concurrency without Blocking:
      - suspend execution when face blocking operations, allowing other fiber to run
      - with this blocking management, we can optimize the resource utilization
  - lightweight threads
    - "green threads" or "coroutines"
  - The Cats Effect IO runtime implements fibers in roughly 150 bytes per fiber
  - Unlike system threads, fibers do not rely on the operating system's scheduler to switch between contexts. 
  Instead, they use a framework-level scheduler that controls when and how they are executed

## sbt project compiled with Scala 3

### Usage

This is a normal sbt project. You can compile code with `sbt compile`, run it with `sbt run`, and `sbt console` will start a Scala 3 REPL.

For more information on the sbt-dotty plugin, see the
[scala3-example-project](https://github.com/scala/scala3-example-project/blob/main/README.md).
