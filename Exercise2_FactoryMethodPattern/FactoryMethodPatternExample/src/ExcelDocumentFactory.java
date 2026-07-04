/**
 * ExcelDocumentFactory - Concrete factory that creates ExcelDocument instances.
 */
public class ExcelDocumentFactory extends DocumentFactory {

    @Override
    public Document createDocument() {
        return new ExcelDocument();
    }
}
