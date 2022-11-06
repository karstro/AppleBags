# Installation instructions and stuff
./mvnw.cmd spring-boot:build-image
docker run -d -p 8080:8080 applebags
or
docker run -d -p 8080:8080 applebags:0.0.1-SNAPSHOT