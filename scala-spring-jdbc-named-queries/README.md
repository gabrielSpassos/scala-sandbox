# Scala - Spring Boot - JDBC - Named Query - POC

## Stack
- Scala 3.7.1
- Java 21
- Spring Boot 3.5.3
- SBT 1.10.2
- GSON
- POSTGRES

## Output

* This both ways works for empty list as inputs:

```scala
  def findByInstitutionNameIn(institutionNames: util.List[String]): util.List[CardEntity] = {
    if (institutionNames == null || institutionNames.isEmpty) {
      util.Collections.emptyList[CardEntity]()
    } else {
      findByInstitutionNameInQuery(institutionNames)
    }
  }
  
  @Query("SELECT * FROM card WHERE institution_name in (:institutionNames) AND soft_deleted = false")
  private[repository] def findByInstitutionNameInQuery(institutionNames: util.List[String]): util.List[CardEntity]

  def findByBrandInAndSoftDeletedFalse(brands: util.List[String]): util.List[CardEntity]
```

## Tests
```
[info] Passed: Total 28, Failed 0, Errors 0, Passed 28
[success] Total time: 53 s, completed 7 de jul. de 2025 19:33:22
```