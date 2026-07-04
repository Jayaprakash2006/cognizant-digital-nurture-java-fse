public class SearchAlgorithms {

    public static Product linearSearch(Product[] products, int targetId) {
        for (Product product : products) {
            if (product.getProductId() == targetId) {
                return product;
            }
        }
        return null;
    }

    public static Product binarySearch(Product[] sortedProducts, int targetId) {
        int low = 0;
        int high = sortedProducts.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            int midId = sortedProducts[mid].getProductId();

            if (midId == targetId) {
                return sortedProducts[mid];
            } else if (midId < targetId) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return null;
    }
}
