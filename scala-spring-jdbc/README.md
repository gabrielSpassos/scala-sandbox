# Scala - Spring Boot - JDBC

This POC has:
- Scala 3.6.2
- Java 21
- Spring Boot 3.4.1
- SBT 1.10.2

### Requests

* Fetch all cards
```
curl --request GET \
  --url http://localhost:8080/v1/cards
```

* Fetch card by number
```
curl --request GET \
  --url http://localhost:8080/v1/cards/{{cardNumber}}
```

* Fetch bank by code
```
curl --request GET \
  --url http://localhost:8080/v1/banks/{{bankCode}}
```

### Swagger
- http://localhost:8080/swagger-ui/index.html

### Observation 

1. Necessary that the API/Controller response body was type `class` (with getters and setter) **NOT** `case class`, 
otherwise the spring will not be able to serialize the response, and the output would look like `{}` instead a proper JSON.
```scala
class CardResponse(
 var id: String,
 var brand: String,
 var institutionName: String,
 var number: String,
 var name: String,
 var expirationDate: String,
 var cvv: String
) {
  def this() = this(null, null, null, null, null, null, null)

  def this(cardDTO: CardDTO) = this(
    if (cardDTO.id != null) cardDTO.id.toString else null,
    cardDTO.brand,
    cardDTO.institutionName,
    cardDTO.number,
    cardDTO.name,
    if (cardDTO.expirationDate != null) cardDTO.expirationDate.toString else null,
    cardDTO.cvv
  )

  def getId: String = id
  def setId(id: String): Unit = {
    this.id = id
  }

  def getInstitutionName: String = institutionName
  def setInstitutionName(institutionName: String): Unit = {
    this.institutionName = institutionName
  }

  def getBrand: String = brand
  def setBrand(brand: String): Unit = {
    this.brand = brand
  }

  def getNumber: String = number
  def setNumber(number: String): Unit = {
    this.number = number
  }

  def getName: String = name
  def setName(name: String): Unit = {
    this.name = name
  }

  def getExpirationDate: String = expirationDate
  def setExpirationDate(expirationDate: String): Unit = {
    this.expirationDate = expirationDate
  }

  def getCvv: String = cvv
  def setCvv(cvv: String): Unit = {
    this.cvv = cvv
  }

}
```

2. As alternative is also possible to create your own **Java** class and use it as response.
```java
public class BankResponse {

    private String code;
    private String name;

    public BankResponse() {
        this.code = null;
        this.name = null;
    }

    public BankResponse(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
```

3. When using the pattern matcher and the dependency returns null the case `null => ???` , needs to be the first implementation, otherwise the `null` will be considered as a valid value.
```scala
def findByNumber(number: String): Option[CardEntity] = {
    cardRepository.findByNumber(number) match {
      case null => None
      case card => Some(card)
    }
}
```

4. Enable spring tests
* add plugin into the `plugins.sbt`:
```
resolvers += Resolver.jcenterRepo

addSbtPlugin("com.github.sbt.junit" % "sbt-jupiter-interface" % "0.13.3")
```

* add dependency into the `build.sbt`:
```
"com.github.sbt.junit" % "jupiter-interface" % JupiterKeys.jupiterVersion.value % Test,
```

* output:
```
[info] Passed: Total 5, Failed 0, Errors 0, Passed 5
[success] Total time: 4 s, completed Jan 24, 2025, 4:43:05AM
```