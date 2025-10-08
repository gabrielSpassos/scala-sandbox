#!/usr/bin/env bash

echo "🔧 Compiling the Scala project..."
sh compile.sh

echo "🚀 Running the JAR file..."
java -jar target/scala-3.6.2/scala-bitonic-poc.jar