# Scala - Spring Boot - JDBC

This POC has:
- Scala 3.6.2
- Java 21
- Spring Boot 3.4.1
- SBT 1.10.2

### Observation 

1. Necessary that the API/Controller response body was type `class` (with getters and setter) **NOT** `case class`.
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

2. When using the pattern matcher and the dependency returns null the case `null => ?` , needs to be the first implementation
```scala
def findByNumber(number: String): Option[CardEntity] = {
    cardRepository.findByNumber(number) match {
      case null => None
      case card => Some(card)
    }
}
```

3. Enable spring tests
* add plugin into the `plugins.sbt`:
```
resolvers += Resolver.jcenterRepo

addSbtPlugin("com.github.sbt.junit" % "sbt-jupiter-interface" % "0.13.3")
```

* add dependency into the `build.sbt`:
```
"com.github.sbt.junit" % "jupiter-interface" % JupiterKeys.jupiterVersion.value % Test,
```