package org.gold;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRGraphics2DExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleGraphics2DExporterOutput;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PaymentReceiptGeneration {

    public static void main(String[] args) {
        try {
            try (InputStream fontStream = Files.newInputStream(Paths.get("src/main/resources/29lt-bukra.ttf"))) {
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, fontStream));
            } catch (IOException | FontFormatException e) {
                e.printStackTrace();
            }

            String jrxmlFilePath = "src/main/resources/payment-txn-receipt.jrxml";

            InputStream jrxmlStream = Files.newInputStream(Paths.get(jrxmlFilePath));
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlStream);

            PaymentReceiptDTO paymentReceiptDTO = new PaymentReceiptDTO(
                    "26 Oct 2024, 13.01 PM",  // transactionTimestamp
                    "MQNI11102351091",        // txnRefNum
                    "5,000.00",                // amount
                    "PKR",                    // currency
                    "Ali Asad",               // debtorAccountTitle
                    "************1203",        // debtorAccountNumber
                    "Aisha Malik",            // creditorTitle
                    "PK***************8765",   // creditorIdentifier
                    "Meezan Bank Limited",    // creditorBankDisplayName
                    "IBFT - 1Link",                  // modeOfPayment
                    "Others",                 // purposeOfPayment
                    "Towards Friday lunch :)" // paymentNotes
            );

            JRBeanCollectionDataSource trxSuccessDetailData = new JRBeanCollectionDataSource(Collections.singletonList(paymentReceiptDTO));
            Map<String, Object> parameters = new HashMap<>();

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, trxSuccessDetailData);

            String base64Image = generateJasperReportAsBase64(jasperPrint);
            System.out.println(base64Image);

            displayBase64Image(base64Image);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("" + e);
        }
    }

    public static String generateJasperReportAsBase64(JasperPrint jasperPrint) throws JRException {
        int width = jasperPrint.getPageWidth();
        int height = jasperPrint.getPageHeight();

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D grx = bufferedImage.createGraphics();

        grx.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        grx.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        JRGraphics2DExporter exporter = new JRGraphics2DExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));

        SimpleGraphics2DExporterOutput output = new SimpleGraphics2DExporterOutput();
        output.setGraphics2D(grx);
        exporter.setExporterOutput(output);

        exporter.exportReport();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, "jpeg", byteArrayOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
            throw new JRException("Error while generating JPEG", e);
        }

        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        return Base64.getEncoder().encodeToString(imageBytes);
    }

    // Display Base64-encoded image in a JFrame
    public static void displayBase64Image(String base64Image) {
        byte[] imageBytes = Base64.getDecoder().decode(base64Image);

        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
            BufferedImage image = ImageIO.read(bis);

            JFrame frame = new JFrame("Base64 Image Viewer");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(new Dimension(image.getWidth(), image.getHeight()));

            JLabel label = new JLabel(new ImageIcon(image));
            frame.add(label);

            frame.pack();
            frame.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

