/**
 * DocumentFactoryTest - Demonstrates and tests the Factory Method Pattern.
 *
 * Each section of the test:
 *  1. Creates a specific factory without knowing the concrete Document class.
 *  2. Calls the factory to produce a Document.
 *  3. Exercises all Document lifecycle methods.
 *  4. Verifies the correct concrete type was produced.
 */
public class DocumentFactoryTest {

    public static void main(String[] args) {
        System.out.println("=== Factory Method Pattern Test ===\n");

        // Array of all factories — the client only knows DocumentFactory
        DocumentFactory[] factories = {
            new WordDocumentFactory(),
            new PdfDocumentFactory(),
            new ExcelDocumentFactory()
        };

        String[] expectedTypes = {
            "Word Document (.docx)",
            "PDF Document (.pdf)",
            "Excel Document (.xlsx)"
        };

        boolean allPassed = true;

        for (int i = 0; i < factories.length; i++) {
            System.out.println("--- Test " + (i + 1) + ": " + expectedTypes[i] + " ---");

            // Use the template method to get a ready-to-use document
            Document doc = factories[i].getDocument();

            // Exercise save and close
            doc.save();
            doc.close();

            // Verify the correct type was returned
            boolean pass = expectedTypes[i].equals(doc.getType());
            System.out.println("Type check: " + (pass ? "PASS" : "FAIL")
                    + " (expected: " + expectedTypes[i]
                    + ", got: " + doc.getType() + ")");

            if (!pass) allPassed = false;

            System.out.println();
        }

        // --- Polymorphism demonstration ---
        System.out.println("--- Polymorphism Check ---");
        System.out.println("All documents are handled through the Document interface.");
        DocumentFactory factory = new PdfDocumentFactory();   // only interface type known
        Document anyDoc = factory.createDocument();
        anyDoc.open();
        anyDoc.save();
        anyDoc.close();
        System.out.println("PASS - Client code never referenced PdfDocument directly.\n");

        System.out.println("=== Overall Result: " + (allPassed ? "ALL TESTS PASSED" : "SOME TESTS FAILED") + " ===");
    }
}
