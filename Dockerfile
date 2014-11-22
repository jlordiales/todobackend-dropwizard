FROM java:8
COPY . /usr/src/todobackend
EXPOSE 8080
WORKDIR /usr/src/todobackend
CMD ["./gradlew", "run"]