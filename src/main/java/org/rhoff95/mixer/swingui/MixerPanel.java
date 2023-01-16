package org.rhoff95.mixer.swingui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.optaplanner.examples.common.swingui.SolutionPanel;
import org.optaplanner.swing.impl.SwingUtils;
import org.rhoff95.mixer.domain.MixerSolution;
import org.rhoff95.mixer.domain.Paint;
import org.rhoff95.mixer.domain.PaintMix;
import org.rhoff95.mixer.realtime.DeletePaintRealtimeChange;

public class MixerPanel extends SolutionPanel<MixerSolution> {

    private static final Logger logger = LogManager.getLogger(MixerPanel.class);

    public static final String LOGO_PATH = "/org/rhoff95/mixer/swingui/mixerLogo.png";

    private final ImageIcon paintIcon;
    private final ImageIcon addPaintIcon;
    private final ImageIcon deletePaintIcon;
    private final ImageIcon paintMixIcon;
    private final ImageIcon paintTargetIcon;

    private JPanel paintMixesPanel;

    private PaintPanel targetPanel;
    private PaintPanel solutionMixturePanel;

    private Map<PaintMix, PaintMixPanel> paintMixToPanelMap;

    public MixerPanel() {
        paintIcon = new ImageIcon(getClass().getResource("paint.png"));
        addPaintIcon = new ImageIcon(getClass().getResource("addPaint.png"));
        deletePaintIcon = new ImageIcon(getClass().getResource("deletePaint.png"));
        paintMixIcon = new ImageIcon(getClass().getResource("paintMix.png"));
        paintTargetIcon = new ImageIcon(getClass().getResource("targetPaint.png"));

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);

        JPanel headerPanel = createHeaderPanel();
        JPanel paintsPanel = createPaintsPanel();

        layout.setHorizontalGroup(layout.createParallelGroup()
            .addComponent(headerPanel)
            .addComponent(paintsPanel)
        );
        layout.setVerticalGroup(layout.createSequentialGroup()
            .addComponent(headerPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE,
                GroupLayout.PREFERRED_SIZE)
            .addComponent(paintsPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE,
                GroupLayout.PREFERRED_SIZE)
        );
    }

    public ImageIcon getPaintIcon() {
        return paintIcon;
    }

    public ImageIcon getAddPaintIcon() {
        return addPaintIcon;
    }

    public ImageIcon getDeletePaintIcon() {
        return deletePaintIcon;
    }

    public ImageIcon getPaintMixIcon() {
        return paintMixIcon;
    }

    public ImageIcon getPaintTargetIcon() {
        return paintTargetIcon;
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new GridLayout(0, 5));
        JPanel addPanel = new JPanel(new GridLayout());
        JButton addComputerButton = SwingUtils.makeSmallButton(new JButton(getAddPaintIcon()));
        addComputerButton.setToolTipText("Add Paint");
        addComputerButton.addActionListener(e -> {
            // TODO
            PaintMix paintMix = new PaintMix();
            addPaintMix(paintMix);

        });
        addPanel.add(addComputerButton);

        JPanel cornerPanel = new JPanel(new BorderLayout());
        cornerPanel.add(addPanel, BorderLayout.EAST);
        headerPanel.add(cornerPanel);

        JLabel cpuPowerLabel = new JLabel("Paints");
        headerPanel.add(cpuPowerLabel);

        JLabel memoryLabel = new JLabel("Amount");
        headerPanel.add(memoryLabel);

        return headerPanel;
    }

    private JPanel createPaintsPanel() {
        paintMixesPanel = new JPanel(new GridLayout(0, 1));
        paintMixToPanelMap = new LinkedHashMap<>();
        return paintMixesPanel;
    }

    @Override
    public void resetPanel(MixerSolution mixerSolution) {
        for (PaintMixPanel paintMixPanel : paintMixToPanelMap.values()) {
            if (paintMixPanel.getPaintMix() != null) {
                paintMixesPanel.remove(paintMixPanel);
            }
        }
        paintMixToPanelMap.clear();
        paintMixesPanel.removeAll();

        targetPanel = new PaintPanel(this, mixerSolution.getTargetPaint(), getPaintTargetIcon());
        paintMixesPanel.add(targetPanel);

        var solutionMix = getSolutionPaint(mixerSolution);
        solutionMixturePanel = new PaintPanel(this, solutionMix, getPaintMixIcon());
        paintMixesPanel.add(solutionMixturePanel);

        updatePanel(mixerSolution);
    }

    @Override
    public void updatePanel(MixerSolution mixerSolution) {
        var solutionMix = getSolutionPaint(mixerSolution);
        solutionMixturePanel.setPaint(solutionMix);
        solutionMixturePanel.update();

        Set<PaintMix> deadPaintMixes = new LinkedHashSet<>(paintMixToPanelMap.keySet());
        deadPaintMixes.remove(null);

        var amountSum = mixerSolution.getPaintMixes().stream()
            .mapToInt(PaintMix::getAmount)
            .sum();

        mixerSolution.getPaintMixes().stream()
            .sorted((PaintMix pm1, PaintMix pm2) -> pm2.getAmount() - pm1.getAmount())
            .forEach(paintMix -> {
                deadPaintMixes.remove(paintMix);
                PaintMixPanel paintMixPanel = paintMixToPanelMap.get(paintMix);

                if (paintMixPanel == null) {
                    paintMixPanel = new PaintMixPanel(this, paintMix);
                    paintMixesPanel.add(paintMixPanel);
                    paintMixToPanelMap.put(paintMix, paintMixPanel);
                }
                paintMixPanel.setTotalAmount(amountSum);

                paintMixPanel.update();
            });

        for (PaintMix deadPaintMix : deadPaintMixes) {
            PaintMixPanel deadPaintMixPanel = paintMixToPanelMap.remove(deadPaintMix);
            paintMixesPanel.remove(deadPaintMixPanel);
        }

        // If computersPanel.add() or computersPanel.remove() was called, the component needs to be validated.
        paintMixesPanel.validate();
    }

    private Paint getSolutionPaint(MixerSolution mixerSolution) {
        var mixes = 0;
        var r = 0;
        var g = 0;
        var b = 0;

        var paintMixes = mixerSolution.getPaintMixes();

        for (PaintMix paintMix : paintMixes) {
            var paint = paintMix.getPaint();
            var amount = paintMix.getAmount();

            mixes += amount;

            r += amount * paint.getR();
            g += amount * paint.getG();
            b += amount * paint.getB();
        }

        var scaledR = 0f;
        var scaledG = 0f;
        var scaledB = 0f;

        if (mixes == 0) {
            return new Paint(0, 0, 0, "Empty Mixture");
        }

        scaledR = (float) r / (float) mixes;
        scaledG = (float) g / mixes;
        scaledB = (float) b / mixes;

        return new Paint(scaledR, scaledG, scaledB, "Mixture");
    }

    public void addPaintMix(final PaintMix paintMix) {
        logger.info("Scheduling addition of paint mix ({})", paintMix);
        //        doProblemFactChange(new AddPaintMixProblemFactChange(paintMix));
    }

    public void deletePaint(final Paint paint) {
        logger.info("Scheduling delete of paint ({})", paint);
        doProblemFactChange(new DeletePaintRealtimeChange(paint));
    }
}
