/**
 * PdfDocumentFactory - Concrete factory that creates PdfDocument instances.
 */
public class PdfDocumentFactory extends DocumentFactory {

    @Override
    public Document createDocument() {
        return new PdfDocument();
    }
}
