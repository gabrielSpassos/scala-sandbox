# Text Server HTTP Server POC

> Goal to create HTTP server to be able to send and retrieve text like we have on https://dontpad.com/

### Usage
* http://localhost:8080/hello
* `curl http://localhost:8080/hello`
* ```
  curl --request POST \
  --url http://localhost:8080/orders \
  --header 'content-type: application/json' \
  --data '{
    "items": [{
      "id": 1,
      "name": "t-shirt"
    }]
  }'
  ```
* `curl --request GET --url http://localhost:8080/items/1`
* `curl --limit-rate 50b http://localhost:8080/numbers`
* `curl -X PUT "http://localhost:8080/auction?bid=22&user=Martin"`
* `curl http://localhost:8080/auction`
* `curl http://localhost:8080/auction`
* `curl http://localhost:8080`
* `curl http://localhost:8080/ping`
* `curl http://localhost:8080/crash`
* `curl http://localhost:8080/foo`

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
- [x] https://doc.akka.io/libraries/akka-http/current/introduction.html#marshalling
- [x] https://doc.akka.io/libraries/akka-http/current/introduction.html#streaming
- [x] https://doc.akka.io/libraries/akka-http/current/introduction.html#low-level-http-server-apis
- [x] https://doc.akka.io/libraries/akka-http/current/introduction.html#http-client-api

