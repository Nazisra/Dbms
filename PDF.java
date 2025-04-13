package Hotel.Management.System;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import javax.swing.*;
import java.awt.*;
import java.io.FileOutputStream;
import java.sql.*;

public class PDF {
    PDF(String customerID) {
        try {
            // Database Connection
            con conn = new con();
            String query = "SELECT * FROM customer WHERE number = '" + customerID + "'";
            ResultSet rs = conn.statement.executeQuery(query);

            if (rs.next()) {
                String name = rs.getString("name");
                String roomNumber = rs.getString("room");
                String checkIn = rs.getString("checkintime");
                String checkOut = new java.util.Date().toString();
                String bill = rs.getString("deposit");

                // Generate PDF
                generateBillPDF(customerID, name, roomNumber, checkIn, checkOut, bill);

                // Delete customer information and update room availability after generating the PDF
                deleteCustomerAndUpdateRoom(conn, customerID, roomNumber);

                JOptionPane.showMessageDialog(null, "PDF Generated Successfully and Customer Checked-Out!");
                new Reception();
            } else {
                JOptionPane.showMessageDialog(null, "Customer Not Found!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Function to Generate Bill PDF
    private void generateBillPDF(String id, String name, String room, String checkIn, String checkOut, String bill) {
        Document document = new Document();
        try {
            String filePath = "Customer_Bill_" + id + ".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            // Adding hotel details with modern design enhancements
            document.add(new Paragraph("******************************** Hotel N & R ******************************", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLUE)));
            document.add(Chunk.NEWLINE);

            // Customer Bill Title with spacing and alignment
            Paragraph title = new Paragraph("Customer Bill", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14));
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(Chunk.NEWLINE);

            // Using Paragraph for separator
            Paragraph separator = new Paragraph("=====================================", FontFactory.getFont(FontFactory.HELVETICA, 12));
            separator.setAlignment(Element.ALIGN_CENTER);
            document.add(separator);
            document.add(Chunk.NEWLINE);

            // Add customer details in a modern design format
            document.add(new Paragraph("Customer ID: " + id, FontFactory.getFont(FontFactory.HELVETICA, 12)));
            document.add(new Paragraph("Name: " + name, FontFactory.getFont(FontFactory.HELVETICA, 12)));
            document.add(new Paragraph("Room Number: " + room, FontFactory.getFont(FontFactory.HELVETICA, 12)));
            document.add(new Paragraph("Check-In Time: " + checkIn, FontFactory.getFont(FontFactory.HELVETICA, 12)));
            document.add(new Paragraph("Check-Out Time: " + checkOut, FontFactory.getFont(FontFactory.HELVETICA, 12)));
            document.add(new Paragraph("Total Bill: $" + bill, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.RED)));

            // More space before closing
            document.add(Chunk.NEWLINE);

            // Thank You Message with spacing
            Paragraph thankYouMessage = new Paragraph("Thank you for choosing our hotel!", FontFactory.getFont(FontFactory.HELVETICA, 12));
            thankYouMessage.setAlignment(Element.ALIGN_CENTER);
            document.add(thankYouMessage);

            // Closing the document
            document.close();

            // Open the PDF automatically
            Desktop.getDesktop().open(new java.io.File(filePath));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Function to Delete Customer and Update Room Availability
    private void deleteCustomerAndUpdateRoom(con conn, String customerID, String roomNumber) {
        try {
            // Delete the customer from the 'customer' table
            String deleteQuery = "DELETE FROM customer WHERE number = '" + customerID + "'";
            conn.statement.executeUpdate(deleteQuery);

            // Update the room availability in the 'room' table
            String updateQuery = "UPDATE room SET availability = 'Available' WHERE roomnumber = '" + roomNumber + "'";
            conn.statement.executeUpdate(updateQuery);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
