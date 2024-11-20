# Text Server HTTP Server POC

> Goal to create HTTP server to be able to send and retrieve text like we have on https://dontpad.com/

### Usage
* http://localhost:8080/hello
* `curl http://localhost:8080/hello`

### Tips

* Create project
```
sbt new scala/hello-world.g8
```

* Execute project
```
sbt
```

* Run project (inside the sbt console)
```sbt
~run
```
The `~` is to activate the hot reload

* Execute tests
```
sbt test
```

### Todo
- [x] https://doc.akka.io/libraries/akka-http/current/introduction.html#routing-dsl-for-http-servers
- [ ] https://doc.akka.io/libraries/akka-http/current/introduction.html#marshalling


