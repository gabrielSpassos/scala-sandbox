# Scala Bazel POC

> https://virtuslab.com/blog/scala/introduction-to-bazel-for-scala-developers/
> https://github.com/tanishiking/bazel-tutorial-scala/tree/main/01_scala_tutorial

### Usage

```shell
bazel --host_jvm_args '-Djavax.net.ssl.trustStore=/Users/gpassos/.sdkman/candidates/java/17.0.12-amzn/lib/security/cacerts' build //src/main/scala/lib:greeting
```
