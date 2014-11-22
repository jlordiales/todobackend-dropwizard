FROM java:8
COPY . /usr/src/todobackend
WORKDIR /usr/src/todobackend
CMD ["./gradlew", "run"]