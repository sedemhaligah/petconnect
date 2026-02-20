#!/bin/bash
set -e

echo "Step 1: Cleaning and packaging the application (skipping tests)..."
mvn clean package -DskipTests

echo "Step 2: Generating JavaDoc..."
mvn javadoc:javadoc

echo "Step 3: Running integration tests in Docker..."
docker-compose -f docker-compose.test.yml up --build --abort-on-container-exit --exit-code-from petconnect-test

echo "Step 7: Building Docker image for the app..."
docker build -t myapp:latest .

echo "Local build pipeline completed successfully!"
