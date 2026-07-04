/**
 * Document - Common interface for all document types.
 *
 * Every concrete document must implement these lifecycle methods
 * so the rest of the application can treat all documents uniformly.
 */
public interface Document {

    /** Opens the document. */
    void open();

    /** Saves the document. */
    void save();

    /** Closes the document. */
    void close();

    /** Returns a human-readable description of the document type. */
    String getType();
}
