# Tags POC

## Stack
- Scala 3.7.3
- Java 25
- Spring Boot 3.5.7
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

## Tests

```bash
[info] Passed: Total 35, Failed 0, Errors 0, Passed 35
[success] Total time: 25 s, completed 12 de nov. de 2025 09:49:18
```
