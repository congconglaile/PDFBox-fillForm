package com.test.fillForm;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;

public class FillFormField {

    public static void main(String[] args) {
        String pdfFileWithForm = "resources/before/FillFormField.pdf";

        /**
         *  Function: PDDocument.load(File file)
         *  Description: Parses a PDF. Load PDF to memory. Unrestricted main memory will be used for buffering PDF streams.
         *               pdfDocument is the in-memory representation of the PDF document.
         *              The #close() method must be called once the document is no longer needed.
         *  Parameters:File file
         *  Returns :loaded document.
         *  Throws: IOException - in case of a file reading or parsing error. InvalidPasswordException - If the file required a non-empty password.
         */
        try (PDDocument pdfDocument = PDDocument.load(new File(pdfFileWithForm)))
        {
            /**
             *  Function: getDocumentCatalog()
             *  Description: This will get the document CATALOG. This is guaranteed to not return null.
             *  Returns: The documents /Root dictionary
             */
            PDDocumentCatalog catalog = pdfDocument.getDocumentCatalog();

            /**
             *  Function: getAcroForm()
             *  Description: Get Get the documents AcroForm. This will return null if no AcroForm is part of the document.
             *  Returns: The document's AcroForm.
             */
            PDAcroForm acroForm = catalog.getAcroForm();

            // as there might not be an AcroForm entry a null check is necessary
            if (acroForm != null)
            {
                /**
                 *  Function: getField(String fullyQualifiedName)
                 *  Description: This will get a field by name, possibly using the cache if setCache is true.
                 *  Parameters: fullyQualifiedName - The name of the field to get.
                 *  Returns: The field with that name of null if one was not found.
                 */
                PDTextField field = (PDTextField) acroForm.getField( "name" );

                /**
                 *  Function: setValue(String value)
                 *  Description: Sets the plain text value of this field.
                 *  Parameters: value - Plain text
                 *  Throws: IOException - if the value could not be set
                 */
                field.setValue("name:Fang");

                // set the other field
                field = (PDTextField) acroForm.getField( "gender" );
                field.setValue("gender: female");
            }

            /**
             *  Function: save(File file)
             *  Description: Save the document to a file and close the filled out form.
             *  Parameters: file - The file to save as.
             *  Throws: IOException - if the output could not be written
             */
            pdfDocument.save("resources/after/FillFormField.pdf");

        }catch (IOException e){
            System.out.println(e.toString());
        }

    }
}
