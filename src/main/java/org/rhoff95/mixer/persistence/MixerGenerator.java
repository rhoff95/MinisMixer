package org.rhoff95.mixer.persistence;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.optaplanner.core.api.score.buildin.hardmediumsoftlong.HardMediumSoftLongScore;
import org.optaplanner.examples.common.app.CommonApp;
import org.optaplanner.examples.common.persistence.AbstractSolutionImporter;
import org.optaplanner.persistence.common.api.domain.solution.SolutionFileIO;
import org.optaplanner.persistence.xstream.impl.domain.solution.XStreamSolutionFileIO;
import org.rhoff95.mixer.app.MixerApp;
import org.rhoff95.mixer.constants.Vallejo;
import org.rhoff95.mixer.domain.MixerSolution;
import org.rhoff95.mixer.domain.Paint;
import org.rhoff95.mixer.domain.PaintMix;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class MixerGenerator {

    private static final Logger logger = LogManager.getLogger(MixerGenerator.class);
    protected final SolutionFileIO<MixerSolution> solutionFileIO;
    protected final File outputDir;

    private static final AtomicLong paintId = new AtomicLong();

    public MixerGenerator() {
        solutionFileIO = new XStreamSolutionFileIO<>(MixerSolution.class);
        outputDir = new File(CommonApp.determineDataDir(MixerApp.DATA_DIR_NAME), "unsolved");
    }

    public MixerGenerator(boolean withoutDao) {
        if (!withoutDao) {
            throw new IllegalArgumentException(
                "The parameter withoutDao (" + withoutDao + ") must be true.");
        }
        solutionFileIO = null;
        outputDir = null;
    }

    public static void main(String[] args)
        throws IOException, SAXException, ParserConfigurationException {
        MixerGenerator generator = new MixerGenerator();
        generator.writeMixerSolution(4);
        generator.writeMixerSolution(8);
        generator.writeMixerSolution(16);
        generator.writeMixerSolution(32);
        generator.writeMixerSolution(64);
        generator.writeMixerSolution(256);
        generator.writeMixerSetSolution("vallejo");
    }

    private void writeMixerSolution(int n) {
        String outputFileName = n + "mixer.xml";
        File outputFile = new File(outputDir, outputFileName);
        MixerSolution mixerSolution = createMixerSolution(n);
        solutionFileIO.write(mixerSolution, outputFile);
        logger.info("Saved: {}", outputFile);
    }

    private void writeMixerSetSolution(String name)
        throws IOException, SAXException, ParserConfigurationException {
        String outputFileName = name + ".xml";
        File outputFile = new File(outputDir, outputFileName);
        Collection<Paint> paints = readPaintsFromFile(name);
        MixerSolution mixerSolution = createMixerSolution(paints);
        solutionFileIO.write(mixerSolution, outputFile);
        logger.info("Saved: {}", outputFile);
    }

    private Collection<Paint> readPaintsFromFile(String fileName)
        throws ParserConfigurationException, IOException, SAXException {
        var file = new File(CommonApp.determineDataDir(MixerApp.DATA_DIR_NAME), "/colors/" + fileName + ".xml");

        List<Paint> paints = new ArrayList<>();

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//an instance of builder to parse the specified xml file
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(file);
        doc.getDocumentElement().normalize();
        logger.info("Root element: {}", doc.getDocumentElement().getNodeName());

        NodeList nodeList = doc.getElementsByTagName("paint");

        for (int itr = 0; itr < nodeList.getLength(); itr++)
        {
            Node paintNode = nodeList.item(itr);

            if (paintNode.getNodeType() == Node.ELEMENT_NODE)
            {
                Element paintElement = (Element) paintNode;
                String brand = paintElement.getAttribute("brand");
                String name = paintElement.getAttribute("name");

                Node colorNode = ((Element) paintNode).getElementsByTagName("color").item(0);
                NamedNodeMap colorAttributes = colorNode.getAttributes();
                int a = Integer.parseInt(colorAttributes.getNamedItem("a").getNodeValue());
                int r = Integer.parseInt(colorAttributes.getNamedItem("r").getNodeValue());
                int g = Integer.parseInt(colorAttributes.getNamedItem("g").getNodeValue());
                int b = Integer.parseInt(colorAttributes.getNamedItem("b").getNodeValue());

                Paint paint = new Paint(r , g, b, name);
                paints.add(paint);
            }
        }

        return paints;
    }

    public MixerSolution createMixerSolution(int n) {
        MixerSolution mixerSolution = new MixerSolution();
        mixerSolution.setId(0L);
        mixerSolution.setScore(HardMediumSoftLongScore.ZERO);
        mixerSolution.setTargetPaint(createTargetPaint());
        mixerSolution.setPaints(createPaints(n));
        mixerSolution.setPaintMixes(createPaintMixes(mixerSolution));
        BigInteger possibleSolutionSize =
            BigInteger.valueOf(255).pow(mixerSolution.getPaints().size());
        logger.info("MixerSolution {} has {} paints with a search space of {}.",
            n, mixerSolution.getPaints().size(),
            AbstractSolutionImporter.getFlooredPossibleSolutionSize(possibleSolutionSize));
        return mixerSolution;
    }

    public MixerSolution createMixerSolution(Collection<Paint> paints) {
        MixerSolution mixerSolution = new MixerSolution();
        mixerSolution.setId(0L);
        mixerSolution.setScore(HardMediumSoftLongScore.ZERO);
        mixerSolution.setTargetPaint(createTargetPaint());

        for (Paint paint : paints) {
            paint.setId(paintId.getAndIncrement());
        }
        mixerSolution.setPaints((List<Paint>) paints);
        mixerSolution.setPaintMixes(createPaintMixes(mixerSolution));
        BigInteger possibleSolutionSize =
            BigInteger.valueOf(255).pow(mixerSolution.getPaints().size());
        logger.info("MixerSolution {} has {} paints with a search space of {}.",
            paints.size(), mixerSolution.getPaints().size(),
            AbstractSolutionImporter.getFlooredPossibleSolutionSize(possibleSolutionSize));
        return mixerSolution;
    }

    private Paint createTargetPaint() {
        Paint targetPaint = Vallejo.OrangeFire;
        targetPaint.setId(paintId.getAndIncrement());

        return targetPaint;
    }

    private List<Paint> createPaints(int n) {
        List<Paint> paints = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Paint paint = new Paint(i, i, i);
            paint.setId(paintId.getAndIncrement());
            paints.add(paint);
        }
        return paints;
    }

    private List<PaintMix> createPaintMixes(MixerSolution mixerSolution) {
        List<PaintMix> paintMixes = new ArrayList<>();
        for (int i = 0; i < mixerSolution.getPaints().size(); i++) {
            Paint paint = mixerSolution.getPaints().get(i);
            PaintMix paintMix = new PaintMix(paint);
            paintMix.setId((long) i);
            paintMixes.add(paintMix);
        }
        return paintMixes;
    }
}
