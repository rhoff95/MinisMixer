package org.rhoff95.vallejo;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class VallejoImporter {

    private static final Logger logger = LogManager.getLogger(VallejoImporter.class);

    private static File[] getResourceFolderFiles(String folder) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL url = loader.getResource(folder);
        String path = url.getPath();
        return new File(path).listFiles();
    }

    public static void main(String[] args)
        throws IOException, ParserConfigurationException, TransformerException {
        String folder = "org/rhoff95/vallejo";
        logger.info("Loading files from {}", folder);

        DocumentBuilderFactory dbFactory =
            DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.newDocument();

        Element rootElement = doc.createElement("paints");
        doc.appendChild(rootElement);

        for (File file : getResourceFolderFiles(folder)) {
            if (!file.getName().contains(".jpg")) {
                continue;
            }
            logger.debug("File: {}", file);

            var split = file.getName().split("-");

            StringBuilder colorName = new StringBuilder();
            String colorSku = "";

            for (int i = 2; i < split.length; i++) {
                String item = split[i];
                try {
                    Integer.parseInt(item);
                    colorSku = item;
                    break;
                } catch (NumberFormatException e) {
                    if (colorName.length() == 0) {
                        colorName.append(item);
                    } else {
                        colorName.append(" ").append(item);
                    }
                }
            }
            BufferedImage image = ImageIO.read(file);

            int colorPositionX, colorPositionY;
            if (file.getName().contains("420x420")) {
                colorPositionX = 100;
                colorPositionY = 240;
            } else {
                colorPositionX = 140;
                colorPositionY = 330;
            }

            int argb = image.getRGB(colorPositionX, colorPositionY);
            int b = (argb) & 0xFF;
            int g = (argb >> 8) & 0xFF;
            int r = (argb >> 16) & 0xFF;
            int a = (argb >> 24) & 0xFF;

            Element paint = doc.createElement("paint");
            rootElement.appendChild(paint);

            Attr nameAttribute = doc.createAttribute("name");
            nameAttribute.setValue(colorName.toString());
            paint.setAttributeNode(nameAttribute);

            Attr brandAttribute = doc.createAttribute("brand");
            brandAttribute.setValue("Vallejo");
            paint.setAttributeNode(brandAttribute);

//            Attr colorAttribute = doc.createAttribute("name");
//            nameAttribute.setValue(colorName.toString());
//            paint.setAttributeNode(nameAttribute);
            Element color = doc.createElement("color");
            paint.appendChild(color);

            Attr redAttribute = doc.createAttribute("r");
            redAttribute.setValue(String.valueOf(r));
            color.setAttributeNode(redAttribute);

            Attr blueAttribute = doc.createAttribute("g");
            blueAttribute.setValue(String.valueOf(g));
            color.setAttributeNode(blueAttribute);

            Attr greenAttribute = doc.createAttribute("b");
            greenAttribute.setValue(String.valueOf(b));
            color.setAttributeNode(greenAttribute);

            Attr alphaAttribute = doc.createAttribute("a");
            alphaAttribute.setValue(String.valueOf(a));
            color.setAttributeNode(alphaAttribute);
        }

        // write the content into xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(".\\vallejo.xml"));
        transformer.transform(source, result);


    }

}
