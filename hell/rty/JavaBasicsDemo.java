import java.util.*; // Import List, ArrayList, Set, and Map

// Main class to demonstrate Java concepts
public class JavaBasicsDemo {

    public static void main(String[] args) {
        // 1. Demonstrating List and ArrayList
        System.out.println("--- List and ArrayList Example ---");
        List<String> list = new ArrayList<>(); // List interface, implemented by ArrayList
        list.add("Apple");
        list.add("Banana");
        list.add("Apple"); // Allows duplicates
        System.out.println("List: " + list);

        ArrayList<String> arrayList = new ArrayList<>(list); // Explicit ArrayList
        arrayList.add("Cherry");
        System.out.println("ArrayList (copied from List): " + arrayList);
        System.out.println("ArrayList contains 'Apple': " + arrayList.contains("Apple")); // Check if contains element
        arrayList.remove("Apple"); // Remove an element
        System.out.println("ArrayList after removing 'Apple': " + arrayList);

        // 2. Demonstrating Set
        System.out.println("--- Set Example ---");
        Set<String> set = new HashSet<>(); // Unordered, no duplicates
        set.add("Apple");
        set.add("Banana");
        set.add("Apple"); // Duplicate ignored
        System.out.println("Set: " + set);
        System.out.println("Set contains 'Apple': " + set.contains("Apple"));
        set.remove("Apple");
        System.out.println("Set after removing 'Apple': " + set);

        // 3. Demonstrating Map
        System.out.println("--- Map Example ---");
        Map<String, Integer> map = new HashMap<>(); // Key-value pairs
        map.put("Apple", 1);
        map.put("Banana", 2);
        map.put("Cherry", 5);
        map.put("Apple", 3); // Replaces previous value for key "Apple"
        System.out.println("Map: " + map);
        System.out.println("Value for 'Apple': " + map.get("Apple")); // Access value by key
        System.out.println("Value for 'Banana': " + map.get("Banana"));
        System.out.println("All Keys: " + map.keySet()); // Get all keys
        System.out.println("All Values: " + map.values()); // Get all values
        map.remove("Apple"); // Remove entry by key
        System.out.println("Map after removing 'Apple': " + map);

        // Access key by value
        System.out.println("--- Access Key by Value ---");
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue().equals(2)) {
                System.out.println("Key for value 2: " + entry.getKey());
            }
        }

        // 4. Demonstrating Loops
        System.out.println("--- Loops Example ---");

        // For loop
        for (int i = 0; i < arrayList.size(); i++) {
            System.out.println("For loop index " + i + ": " + arrayList.get(i));
        }

        // Enhanced for-loop (for-each)
        for (String item : arrayList) {
            System.out.println("Enhanced for-loop item: " + item);
        }

        // While loop
        int index = 0;
        while (index < arrayList.size()) {
            System.out.println("While loop index " + index + ": " + arrayList.get(index));
            index++;
        }

        // Do-while loop
        index = 0;
        do {
            System.out.println("Do-while loop index " + index + ": " + arrayList.get(index));
            index++;
        } while (index < arrayList.size());

        // Iterator loop
        Iterator<String> iterator = arrayList.iterator();
        while (iterator.hasNext()) {
            System.out.println("Iterator loop item: " + iterator.next());
        }

        // Stream loop (functional style, Java 8+)
        arrayList.stream().forEach(item -> System.out.println("Stream loop item: " + item));

        // 5. Inheritance Example
        System.out.println("--- Inheritance Example ---");
        Dog dog = new Dog("Buddy");
        dog.eat(); // Method inherited from Animal
        dog.bark(); // Method specific to Dog

        // 6. Interface Example
        System.out.println("--- Interface Example ---");
        Flyable bird = new Bird();
        bird.fly();

        // 7. Generics Example
        System.out.println("--- Generics Example ---");
        Box<Integer> intBox = new Box<>();
        intBox.set(123);
        System.out.println("Integer Box contains: " + intBox.get());

        Box<String> strBox = new Box<>();
        strBox.set("Hello Generics");
        System.out.println("String Box contains: " + strBox.get());

        // 8. Exception Handling Example
        System.out.println("--- Exception Handling Example ---");
        try {
            validateBook("J.K. Rowling", ""); // Invalid book (empty title)
        } catch (InvalidBookException e) {
            System.out.println("Invalid Book Detected: Author - " + e.getAuthor() + ", Title - " + e.getTitle());
        }

        // 9. Array Example
        System.out.println("--- Array Example ---");
        String[] fruits = {"Apple", "Banana", "Cherry"};
        System.out.println("Array: " + Arrays.toString(fruits)); // Print array
        System.out.println("Element at index 1: " + fruits[1]); // Access element by index
        fruits[1] = "Blueberry"; // Modify element
        System.out.println("Modified Array: " + Arrays.toString(fruits));
    }

    // Method to validate book details
    public static void validateBook(String author, String title) throws InvalidBookException {
        if (author == null || author.isEmpty() || title == null || title.isEmpty()) {
            throw new InvalidBookException(author, title);
        }
        System.out.println("Book is valid: " + author + " - " + title);
    }
}

// Base class for Inheritance example
class Animal {
    public void eat() {
        System.out.println("This animal eats food.");
    }
}

// Subclass of Animal
class Dog extends Animal {
    private String name;

    public Dog(String name) {
        this.name = name;
    }

    public void bark() {
        System.out.println(name + " says: Woof Woof!");
    }
}

// Interface example
interface Flyable {
    void fly();
}

// Class implementing Flyable
class Bird implements Flyable {
    @Override
    public void fly() {
        System.out.println("The bird is flying.");
    }
}

// Generics example
class Box<T> { // Generic class with type parameter T
    private T item;

    public void set(T item) {
        this.item = item;
    }

    public T get() {
        return item;
    }
}

// Custom exception example
@SuppressWarnings("serial")
class InvalidBookException extends Exception {
    private String author;
    private String title;

    public InvalidBookException(String author, String title) {
        this.author = author;
        this.title = title;
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
}
