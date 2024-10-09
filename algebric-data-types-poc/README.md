# Algebraic Data Types POC

### Content

- an **algebraic data type** is any data that uses the **Product or Sum pattern**.
  - product type pattern
    - model containing other data
    - often defined as a case class
    ```scala
    case class ChessPiece(color: Color, name: Name)
    ```

  - sum pattern
    - type that can assume different values
    - often defined with sealed traits
    ```scala
    sealed trait Color
    case object White extends Color
    case object Black extends Color
    
    sealed trait Name
    case object Pawn extends Name
    case object Rook extends Name
    case object Knight extends Name
    case object Bishop extends Name
    case object Queen extends Name
    case object King extends Name
    ```

### Stack
```
scala: 2.13.15
java: 17
sbt: 1.10.2
```

### Created project with

```shell
sbt new scala/hello-world.g8
```