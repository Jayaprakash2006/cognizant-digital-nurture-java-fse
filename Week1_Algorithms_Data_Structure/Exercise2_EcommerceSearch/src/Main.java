public class Main {

    public static void main(String[] args) {
        Product[] products = {
            new Product(5, "Headphones", "Electronics"),
            new Product(2, "Running Shoes", "Footwear"),
            new Product(8, "Coffee Maker", "Appliances"),
            new Product(1, "Laptop", "Electronics"),
            new Product(3, "Desk Lamp", "Home"),
            new Product(7, "Yoga Mat", "Sports"),
            new Product(4, "Backpack", "Accessories"),
            new Product(6, "Water Bottle", "Sports")
        };

        System.out.println("=== Linear Search ===");
        int linearTarget = 7;
        Product linearResult = SearchAlgorithms.linearSearch(products, linearTarget);
        System.out.println("Searching for productId " + linearTarget + ":");
        System.out.println(linearResult != null ? "Found: " + linearResult : "Not found");

        Product[] sortedProducts = {
            new Product(1, "Laptop", "Electronics"),
            new Product(2, "Running Shoes", "Footwear"),
            new Product(3, "Desk Lamp", "Home"),
            new Product(4, "Backpack", "Accessories"),
            new Product(5, "Headphones", "Electronics"),
            new Product(6, "Water Bottle", "Sports"),
            new Product(7, "Yoga Mat", "Sports"),
            new Product(8, "Coffee Maker", "Appliances")
        };

        System.out.println("\n=== Binary Search ===");
        int binaryTarget = 7;
        Product binaryResult = SearchAlgorithms.binarySearch(sortedProducts, binaryTarget);
        System.out.println("Searching for productId " + binaryTarget + ":");
        System.out.println(binaryResult != null ? "Found: " + binaryResult : "Not found");

        System.out.println("\n=== Search for Non-existent Product ===");
        Product missing = SearchAlgorithms.binarySearch(sortedProducts, 99);
        System.out.println("Searching for productId 99:");
        System.out.println(missing != null ? "Found: " + missing : "Not found");

        System.out.println("\n=== Time Complexity Analysis ===");
        System.out.println("Linear Search  - Best: O(1) | Average: O(n) | Worst: O(n)");
        System.out.println("Binary Search  - Best: O(1) | Average: O(log n) | Worst: O(log n)");
        System.out.println("\nConclusion: Binary search is more suitable for large, sorted product catalogs.");
        System.out.println("For unsorted or frequently changing data, linear search may be used.");
    }
}
