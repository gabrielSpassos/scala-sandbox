import http from "k6/http";
import { sleep, check } from "k6";

export let options = {
  thresholds: {
    http_req_failed: ["rate<0.01"], // http errors should be less than 1%
    http_req_duration: ["p(99)<200"], // 95% of requests should be below 200ms
  },
  stages: [
    { duration: "10s", target: 50 }, // ramping 1 to 50 users in 10s
    { duration: "30s", target: 50 }, // sustain 50 for 30s
    { duration: "20s", target: 200 }, // ramp up to 200 users in 20s
    { duration: "1m", target: 200 }, // sustain 200 for 1 minute
    { duration: "20s", target: 500 }, // ramp up to 500 users in 20s
    { duration: "2m", target: 500 }, // sustain 500 (heavy load) for 2 minutes
    { duration: "30s", target: 0 }, // ramp down to 0 users in 30s
  ],
};

function randomBoard(minSize = 5, maxSize = 20, minVal = -10, maxVal = 10) {
  const rows = Math.floor(Math.random() * (maxSize - minSize + 1)) + minSize;
  const cols = Math.floor(Math.random() * (maxSize - minSize + 1)) + minSize;

  const board = Array.from({ length: rows }, () =>
    Array.from(
      { length: cols },
      () => Math.floor(Math.random() * (maxVal - minVal + 1)) + minVal
    )
  );

  return board;
}

export default function () {
  sleep(Math.random() * 10); // Random initial 0-10s delay

  let size = Math.floor(Math.random() * 10) + 1; // random size between 1 and 10
  let lowerBoundary = Math.floor(Math.random() * 6) + 1; // random size between 1 and 6
  let upperBoundary = Math.random() * (15 - 7) + 7; // random size between 7 and 15

  // Create a player
  let bitonicRes = http.post(
    "http://app:8080/v1/bitonic/sequences",
    JSON.stringify({
      size: size,
      lowerBoundary: lowerBoundary,
      upperBoundary: upperBoundary
    }),
    { headers: { "Content-Type": "application/json" } }
  );

  check(bitonicRes, {
    "bitonic sequence created": (r) => r.status === 200,
  });

}
