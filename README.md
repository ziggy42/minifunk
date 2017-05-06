# minifunk
This library targets `Java 6` and wraps a `T[]` or `List<T>` to add many methods such as:
* `forEach`
* `map`
* `filter`
* `reduce`
* ...

## Example
```java
Enumerable
    .from(Arrays.asList("apple", "pear", "lemon"))
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
