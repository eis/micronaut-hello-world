<a href="https://github.com/eis/micronaut-hello-world/actions/workflows/github-actions-build.yml"
    title="Build Status">
    <img src="https://github.com/eis/micronaut-hello-world/actions/workflows/github-actions-build.yml/badge.svg">
</a>
## About this project

This project is the result of running `mn create-app hello-world --build maven` and
adding a controller.

You can use it without micronaut cli though.

Example of use:

```
mvn mn:run
curl localhost:8080
```

or old-style:
```
mvn clean package
java -jar target/hello-world-0.1.jar
curl localhost:8080
```
Running the tests:
```
mvn test
```

## Micronaut 3.9.3 Documentation

- [User Guide](https://docs.micronaut.io/3.9.3/guide/index.html)
- [API Reference](https://docs.micronaut.io/3.9.3/api/index.html)
- [Configuration Reference](https://docs.micronaut.io/3.9.3/guide/configurationreference.html)
- [Micronaut Guides](https://guides.micronaut.io/index.html)
---

## Feature http-client documentation

- [Micronaut HTTP Client documentation](https://docs.micronaut.io/latest/guide/index.html#httpClient)

