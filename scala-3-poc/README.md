## sbt project compiled with Scala 3

### Usage

This is a normal sbt project. You can compile code with `sbt compile`, run it with `sbt run`, and `sbt console` will start a Scala 3 REPL.

For more information on the sbt-dotty plugin, see the
[scala3-example-project](https://github.com/scala/scala3-example-project/blob/main/README.md).

* Create project
```shell
sbt new scala/scala3.g8
```

* Run SBT console
```shell
sbt
```

* Run Application with re-run for each file save
```shell
~run
```

* Run Unit test
```shell
sbt test
```