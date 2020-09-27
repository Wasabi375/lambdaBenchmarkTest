# Lambda/FunctionRef benchmark comparison

compares performance of 
```
longSequence.map { it.inv() }
longSequence.map(Long::inv)
```

to run use 
```
mvn clean package
java -jar target/benchmark.jar
````