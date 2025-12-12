import http from "k6/http";
import { check, sleep } from "k6";
import { Trend } from "k6/metrics";

export const options = {
  vus: 1,
  duration: "10s",
};

const webRequestDuration = new Trend("web_request_duration", true);
const reactiveRequestDuration = new Trend("reactive_request_duration", true);

function requestNumber(reqIdx) {
  const MAX_ITER = 10000000000;
  const REQ_PER_ITER = 2;
  return ((__VU - 1) * MAX_ITER + __ITER) * REQ_PER_ITER + reqIdx + 1;
}

export default function () {
  //
  // Request 1 -> WEB
  //
  const x1 = requestNumber(0);
  const webUrl = `http://localhost:8086/v1/messages?key=web-key-${x1}&message=test-${x1}`;

  const webRes = http.post(webUrl, null, {
    tags: { endpoint: "web", request_number: `${x1}` },
  });

  if (webRes) {
    webRequestDuration.add(webRes.timings.duration);
    check(webRes, { "web status 200": (r) => r.status === 200 });
  }

  //
  // Request 2 -> REACTIVE
  //
  const x2 = requestNumber(1);
  const reactiveUrl = `http://localhost:8085/v1/messages?key=reactive-key-${x2}&message=test-${x2}`;

  const reactiveRes = http.post(reactiveUrl, null, {
    tags: { endpoint: "reactive", request_number: `${x2}` },
  });

  if (reactiveRes) {
    reactiveRequestDuration.add(reactiveRes.timings.duration);
    check(reactiveRes, { "reactive status 200": (r) => r.status === 200 });
  }

  sleep(1);
}
