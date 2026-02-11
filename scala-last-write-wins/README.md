# Last Write Wins

## Stack
- Scala 3.8.1
- Java 25
- Spring Boot 4.0.2
- SBT 1.11.7
- POSTGRES
- TestContainers

## Output

* Upsert depends on SQL constraint

```sql
CREATE TABLE IF NOT EXISTS items(
  id            TEXT PRIMARY KEY,
  external_id   TEXT NOT NULL UNIQUE,
  value         TEXT NOT NULL,
  updated_at    TIMESTAMP NOT NULL
);
```

* Scala code upsert uses sql
```sql
INSERT INTO items(external_id, value, updated_at)
VALUES (:externalId, :value, :ts)
ON CONFLICT(external_id)
DO UPDATE
SET value = EXCLUDED.value,
    updated_at = EXCLUDED.updated_at
WHERE items.updated_at <= EXCLUDED.updated_at
```

## Tests
```bash
[info] Passed: Total 3, Failed 0, Errors 0, Passed 3
[success] Total time: 11 s, completed 11 de fev. de 2026 08:16:36
```