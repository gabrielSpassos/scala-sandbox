# Scala - Bitonic POC

- Implement: https://www.geeksforgeeks.org/generate-bitonic-sequence-of-length-n-from-integers-in-a-given-range/
- Red Team: Scala 3x
- Do:
    - [x] Implementation
    - [x] Unit tests
    - [ ] Performance Test / Benchmarks
    - [ ] Proper Documentation
    - [x] Expose Solution via REST API
    - [x] Store Results into a Database (Redis with Docker)

## Requirements

- Given integers n, l and r, the task is to generate a Bitonic Sequence 
(a sequence that must be strictly increasing at first and then strictly decreasing) 
of length n from the integers in the range [l, r] such that the first element is the maximum.
If it is not possible to create such a sequence, then return [-1].
- Note: n > 2.