/**
 * DocumentFactory - Abstract base class that defines the Factory Method.
 *
 * Subclasses override createDocument() to instantiate the specific
 * document type they are responsible for.  The getDocument() template
 * method shows how callers interact with the factory without knowing
 * which concrete document is being created.
 */
public abstract class DocumentFactory {

    /**
     * Factory Method — subclasses must implement this to return
     * their specific Document type.
     *
     * @return a new Document instance
     */
    public abstract Document createDocument();

    /**
     * Template method: creates, opens, and returns a ready-to-use document.
     * Demonstrates how the factory method fits into a larger workflow.
     *
     * @return an opened Document instance
     */
    public Document getDocument() {
        Document doc = createDocument();
        System.out.println("[Factory] Created: " + doc.getType());
        doc.open();
        return doc;
    }
}
