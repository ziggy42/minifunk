# minifunk
[ ![Download](https://api.bintray.com/packages/ziggy42/minifunk/minifunk/images/download.svg) ](https://bintray.com/ziggy42/minifunk/minifunk/_latestVersion)   
   
This library targets `Java 6` and wraps a `T[]` or `List<T>` to add:
* `allMatch`
* `anyMatch`
* `count`
* `distinct`
* `filter`
* `findFirst`
* `flatMap`
* `forEach`
* `limit`
* `map`
* `max`
* `min`
* `noneMatch`
* `skip`
* `sorted`

## Example
```java
Stream
    .of("apple", "pear", "lemon")
    .filter(new Predicate<String>() {
        @Override
        public boolean test(String value) {
            return value.length() > 4;
        }
    })
    .forEach(new Consumer<String>() {
        @Override   
        public void accept(String value) {
            System.out.println(value);
        }
    });
```
## Usage
Add repository:
```groovy
repositories {
    maven {
        url  "http://dl.bintray.com/ziggy42/minifunk"
    }
    ...
}
```

Add dependency:
```groovy
dependencies {
    compile 'com.andreapivetta.minifunk:minifunk:0.0.1'
    ...
}
```
