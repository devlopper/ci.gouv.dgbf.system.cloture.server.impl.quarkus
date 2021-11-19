call ../../../mvnw clean package -f ../../../pom.xml
call docker build -f ../../../src/main/docker/Dockerfile.jvm -t mic-cloture-api ../../../