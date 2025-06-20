# Logger with TypeSystem POC

> Create sample logger with TypeSystem to log the "layer" of the application.

### Layers

1. Controller
2. Service
3. Client
4. Unknown

### Tests

```bash
[source=SampleController] [layer=CONTROLLER] Start to get ids
[source=SampleService] [layer=SERVICE] Calling repo to get ids
[source=SampleRepository] [layer=UNKNOWN] Fetching ids from repository

[source=SampleController] [layer=CONTROLLER] Starting to get data
[source=SampleService] [layer=SERVICE] Starting fetchData from http://example.com/data
[source=HttpClient] [layer=CLIENT] Sending GET request to http://example.com/data
[source=SampleService] [layer=SERVICE] Received response: Response from GET request from http://example.com/data
[source=SampleController] [layer=CONTROLLER] Received data: Response from GET request from http://example.com/data
```