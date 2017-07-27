package com.test.fillForm;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
//import org.apache.pdfbox.pdmodel.font.PDTrueTypeFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

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

            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            //      PDFont font = PDTrueTypeFont.loadTTF(document, new File("SIMSUN.TTC"));
            PDFont font = PDType1Font.HELVETICA_BOLD;
            /**
             *  Function: new PDPageContentStream(document, page)
             *  Description: Create a new PDPage content stream.
             *  Parameters: document - The document the page is part of.
             *  Parameters: sourcePage - The page to write the contents to.
             *  Throws: IOException  - If there is an error writing to the page contents.
             */
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            /**
             *  Function: beginText()
             *  Description: Begin some text operations.
             *  Throws: IOException - If there is an error writing to the stream or if you attempt to nest beginText calls.
             */
            contentStream.beginText();
            /**
             *  Function: setFont(PDFont font, float fontSize)
             *  Description: Set the font and font size to draw text with.
             *  Parameters: font - The font to use.
             *  Parameters: fontSize - The font size to draw the text.
             *  Throws: IOException - If there is an error writing the font information.
             */
            contentStream.setFont(font, 14);
            /**
             *  Function: newLineAtOffset(float tx, float ty)
             *  Description: The Td operator. Move to the start of the next line, offset from the start of the current line by (tx, ty).
             *  Parameters: tx - The x translation.
             *  Parameters: ty - The y translation.
             *  Throws: IOException - If there is an error writing to the stream.
             *  Throws: IllegalStateException - If the method was not allowed to be called at this time.
             */
            contentStream.newLineAtOffset(100, 700);
            /**
             *  Function: showText(String text)
             *  Description: Shows the given text at the location specified by the current text matrix.
             *  Parameters: text - The Unicode text to show.
             *  Throws: IOException - If an io exception occurs.
             */
            contentStream.showText("name:yf l");
            contentStream.newLineAtOffset(50, 50);
            contentStream.showText("gender:female");
            /**
             *  Function: endText()
             *  Description: End some text operations.
             *  Throws: IOException - If there is an error writing to the stream or if you attempt to nest endText calls.
             *  Throws: IllegalStateException - If the method was not allowed to be called at this time.
             */
            contentStream.endText();
            /**
             *  Function: close()
             *  Description: Close the content stream. This must be called when you are done with this object.
             *  Throws: IOException - If the underlying stream has a problem being written to.
             */
            contentStream.close();


            document.save("resources/new/new.pdf");

            document.close();

        }catch (IOException e){
            System.out.println(e.toString());
        }

    }
}
