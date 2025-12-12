# Kafka Producers Performance POC

## Default Spring Webflux vs Default Spring Web

### Results

### Detailed Results

```
scenarios: (100.00%) 1 scenario, 1 max VUs, 40s max duration (incl. graceful stop):
    * default: 1 looping VUs for 10s (gracefulStop: 30s)

█ TOTAL RESULTS 

checks_total.......: 20      1.934766/s
checks_succeeded...: 100.00% 20 out of 20
checks_failed......: 0.00%   0 out of 20

✓ web status 200
✓ reactive status 200

CUSTOM
reactive_request_duration......: avg=15.93ms min=13.37ms med=16.55ms max=18.1ms  p(90)=17.39ms p(95)=17.75ms
web_request_duration...........: avg=15.75ms min=12.67ms med=16.13ms max=17.37ms p(90)=16.86ms p(95)=17.11ms
```

```
scenarios: (100.00%) 1 scenario, 10 max VUs, 1m0s max duration (incl. graceful stop):
    * default: 10 looping VUs for 30s (gracefulStop: 30s)

█ TOTAL RESULTS 

checks_total.......: 600     19.364681/s
checks_succeeded...: 100.00% 600 out of 600
checks_failed......: 0.00%   0 out of 600

✓ web status 200
✓ reactive status 200

CUSTOM
reactive_request_duration......: avg=15.86ms min=7.85ms med=15.75ms max=27.51ms p(90)=20.99ms p(95)=22.63ms
web_request_duration...........: avg=15.29ms min=6.7ms  med=14.73ms max=36.92ms p(90)=19.99ms p(95)=22.31ms
```

```
scenarios: (100.00%) 1 scenario, 100 max VUs, 1m0s max duration (incl. graceful stop):
    * default: 100 looping VUs for 30s (gracefulStop: 30s)

█ TOTAL RESULTS 

checks_total.......: 5946    191.72561/s
checks_succeeded...: 100.00% 5946 out of 5946
checks_failed......: 0.00%   0 out of 5946

✓ web status 200
✓ reactive status 200

CUSTOM
reactive_request_duration......: avg=14.36ms min=3.41ms med=10.29ms max=123.93ms p(90)=23.31ms p(95)=34.23ms
web_request_duration...........: avg=15.66ms min=2.81ms med=10.17ms max=148.96ms p(90)=26.08ms p(95)=39.7ms 
```

```
scenarios: (100.00%) 1 scenario, 200 max VUs, 1m0s max duration (incl. graceful stop):
    * default: 200 looping VUs for 30s (gracefulStop: 30s)


█ TOTAL RESULTS 

checks_total.......: 11942   384.934208/s
checks_succeeded...: 100.00% 11942 out of 11942
checks_failed......: 0.00%   0 out of 11942

✓ web status 200
✓ reactive status 200

CUSTOM
reactive_request_duration......: avg=13.52ms min=2.79ms med=9.83ms  max=102.46ms p(90)=24.26ms p(95)=34.29ms
web_request_duration...........: avg=15.71ms min=2.38ms med=10.52ms max=149.05ms p(90)=27.83ms p(95)=42.02ms
```

```
scenarios: (100.00%) 1 scenario, 500 max VUs, 1m0s max duration (incl. graceful stop):
    * default: 500 looping VUs for 30s (gracefulStop: 30s)


█ TOTAL RESULTS 

checks_total.......: 29418   948.902031/s
checks_succeeded...: 100.00% 29418 out of 29418
checks_failed......: 0.00%   0 out of 29418

✓ web status 200
✓ reactive status 200

CUSTOM
reactive_request_duration......: avg=14.86ms min=2.01ms med=9.34ms  max=151.77ms p(90)=29.9ms  p(95)=52.57ms
web_request_duration...........: avg=18.31ms min=1.92ms med=10.65ms max=181.44ms p(90)=40.7ms  p(95)=62.91ms
```

```
scenarios: (100.00%) 1 scenario, 1000 max VUs, 1m0s max duration (incl. graceful stop):
    * default: 1000 looping VUs for 30s (gracefulStop: 30s)

█ TOTAL RESULTS 

checks_total.......: 58272   1879.02975/s
checks_succeeded...: 100.00% 58272 out of 58272
checks_failed......: 0.00%   0 out of 58272

✓ web status 200
✓ reactive status 200

CUSTOM
reactive_request_duration......: avg=15.84ms min=1.67ms med=8.43ms max=341.12ms p(90)=29.78ms p(95)=59.09ms 
web_request_duration...........: avg=21.62ms min=1.57ms med=9.5ms  max=361.2ms  p(90)=40.39ms p(95)=105.14ms
```

```
scenarios: (100.00%) 1 scenario, 2000 max VUs, 1m0s max duration (incl. graceful stop):
    * default: 2000 looping VUs for 30s (gracefulStop: 30s)


█ TOTAL RESULTS 

checks_total.......: 109814  3544.983257/s
checks_succeeded...: 100.00% 109814 out of 109814
checks_failed......: 0.00%   0 out of 109814

✓ web status 200
✓ reactive status 200

CUSTOM
reactive_request_duration......: avg=29.11ms min=1.36ms med=10.41ms max=568.36ms p(90)=73.22ms p(95)=121.19ms
web_request_duration...........: avg=43.33ms min=1.32ms med=13.47ms max=707.65ms p(90)=95.65ms p(95)=210.17ms
```

### Usage 

```shell
k6 run --vus 10 --duration 30s src/test.js
```