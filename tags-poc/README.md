# Tags POC

## Stack
- Scala 3.7.3
- Java 25
- Spring Boot 4.0.0
- SBT 1.11.7
- POSTGRES

## APIs

#### V1

* Upsert Tag
```
curl -X PUT "http://localhost:8080/v1/tags/${ID}" \
  -H "Content-Type: application/json" \
  -H "Accept: application/json" \
  -d '{"isEnabled": true}'
  
200 OK
{
  "id": "${ID}",
  "isEnabled": true
}
```

* Get Tag by Id
```
curl -X GET "http://localhost:8080/v1/tags/${ID}" \
  -H "Accept: application/json"
  
200 OK
{
  "id": "${ID}",
  "isEnabled": ${isEnabled}
}
```

* Get Tags by Ids
```
curl -X GET "http://localhost:8080/v1/tags?ids=${ID1},${ID2}" \
  -H "Accept: application/json"
  
200 OK
[
  { "id": "${ID1}", "isEnabled": ${isEnabled} },
  { "id": "${ID2}", "isEnabled": ${isEnabled} }
]
```

* Delete Tag
```
curl -X DELETE "http://localhost:8080/v1/tags/${ID}" \
-H "Accept: application/json"

200 OK
{
  "id": "${ID}",
  "isEnabled": ${isEnabled}
}
```

#### V2

* Upsert Tag
```
curl -X PUT "http://localhost:8080/v2/tags/${ID}" \
  -H "Content-Type: application/json" \
  -H "Accept: application/json" \
  -d '{"isEnabled": true}'
  
200 OK
{
  "id": "${ID}",
  "enabledAt": "${someDateTime}",
  "disabledAt": null
}
```

* Get Tag by Id
```
curl -X GET "http://localhost:8080/v2/tags/${ID}" \
  -H "Accept: application/json"
  
200 OK
{
  "id": "${ID}",
  "enabledAt": "${someDateTime}",
  "disabledAt": null
}
```

* Get Tags by Ids
```
curl -X GET "http://localhost:8080/v2/tags?ids=${ID1},${ID2}" \
  -H "Accept: application/json"
  
200 OK
[
  { "id": "${ID1}", "enabledAt": "${someDateTime}", "disabledAt": null },
  { "id": "${ID2}", "enabledAt": null, "disabledAt": "${someDateTime}" }
]
```

* Delete Tag
```
curl -X DELETE "http://localhost:8080/v1/tags/${ID}" \
-H "Accept: application/json"

200 OK
{
  "id": "${ID}",
  "enabledAt": "${someDateTime}",
  "disabledAt": null
}
```

#### V3

* Upsert Tag
```
curl -X PUT "http://localhost:8080/v3/tags/${ID}/${ON|OFF}" \
  -H "Content-Type: application/json" \
  -H "Accept: application/json"

200 OK
{
  "id": "${ID}",
  "value": "ON"
}

```

* Get Tag by Id
```
curl -X GET "http://localhost:8080/v3/tags/${ID}" \
  -H "Accept: application/json"

200 OK
{
  "id": "${ID}",
  "value": "ON"
}
```

* Get Tags by Ids
```
curl -X GET "http://localhost:8080/v3/tags?ids=${ID1},${ID2}" \
  -H "Accept: application/json"

200 OK
[
  { "id": "${ID1}", "value": "ON" },
  { "id": "${ID2}", "value": "OFF" }
]
```

* Delete Tag
```
curl -X DELETE "http://localhost:8080/v3/tags/${ID}" \
-H "Accept: application/json"

200 OK
{
"id": "${ID}",
"value": "ON"
}
```

#### V4
* Upsert Tag
```
curl -X PUT "http://localhost:8080/v4/tags/${ID}" \
  -H "Content-Type: application/json" \
  -H "Accept: application/json" \
  -d '{"value":"${anyValue}"}'
  
200 OK
{
  "id": "${ID}",
  "value": "${anyValue}"
}
```

* Get Tag by Id
```
curl -X GET "http://localhost:8080/v4/tags/${ID}" \
  -H "Accept: application/json"

200 OK
{
  "id": "${ID}",
  "value": "${anyValue}"
}
```

* Get Tags by Ids
```
curl -X GET "http://localhost:8080/v4/tags?ids=${ID1},${ID2}" \
  -H "Accept: application/json"

200 OK
[
  { "id": "${ID1}", "value": "${anyValue}" },
  { "id": "${ID2}", "value": "${anyValue}" }
]
```

* Delete Tag
```
curl -X DELETE "http://localhost:8080/v4/tags/${ID}" \
-H "Accept: application/json"

200 OK
{
"id": "${ID}",
"value": "${anyValue}"
}
```

#### V5

* `$key` => `tagName:entityId` (e.g., `featureX:12345`)'

* Upsert Tag
```
curl -X PUT "http://localhost:8080/v5/tags" \
  -H "Content-Type: application/json" \
  -H "Accept: application/json" \
  -d '{"$key":"ON"}'
  
200 OK
{
  "tagName:entityId": {
    "value": "ON",
    "createdAt": "${someDateTime}",
    "updatedAt": "${someDateTime}"
  }
}
```

* Get Tags by Keys
```
curl -X GET "http://localhost:8080/v5/tags?keys=$key" \
  -H "Accept: application/json"

200 OK
{
  "$key": {
    "value": "ON",
    "createdAt": "${someDateTime}",
    "updatedAt": "${someDateTime}"
  }
}
```

* Delete Tag
```
curl -X DELETE "http://localhost:8080/v5/tags/$key" \
-H "Accept: application/json"

204 No Content
```

## Tests

```bash
[info] Passed: Total 42, Failed 0, Errors 0, Passed 42
[success] Total time: 37 s, completed 18 de dez. de 2025 10:52:16
```
