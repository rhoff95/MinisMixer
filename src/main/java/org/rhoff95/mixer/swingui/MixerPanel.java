package org.rhoff95.mixer.swingui;

import java.awt.GridLayout;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import org.optaplanner.examples.common.swingui.SolutionPanel;
import org.rhoff95.mixer.domain.MixerSolution;
import org.rhoff95.mixer.domain.Paint;
import org.rhoff95.mixer.domain.PaintMix;

public class MixerPanel extends SolutionPanel<MixerSolution> {

    public static final String LOGO_PATH = "/org/rhoff95/mixer/swingui/mixerLogo.png";

    private final ImageIcon cloudComputerIcon;
    private final ImageIcon addCloudComputerIcon;
    private final ImageIcon deleteCloudComputerIcon;
    private final ImageIcon cloudProcessIcon;
    private final ImageIcon addCloudProcessIcon;
    private final ImageIcon deleteCloudProcessIcon;

    private JPanel paintMixesPanel;

    //    private PaintMixPanel unassignedPanel;
    private Map<PaintMix, PaintMixPanel> paintMixToPanelMap;

    public MixerPanel() {
        cloudComputerIcon = new ImageIcon(getClass().getResource("cloudComputer.png"));
        addCloudComputerIcon = new ImageIcon(getClass().getResource("addCloudComputer.png"));
        deleteCloudComputerIcon = new ImageIcon(getClass().getResource("deleteCloudComputer.png"));
        cloudProcessIcon = new ImageIcon(getClass().getResource("cloudProcess.png"));
        addCloudProcessIcon = new ImageIcon(getClass().getResource("addCloudProcess.png"));
        deleteCloudProcessIcon = new ImageIcon(getClass().getResource("deleteCloudProcess.png"));

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);

//        JPanel headerPanel = createHeaderPanel();
        JPanel paintsPanel = createPaintsPanel();

        layout.setHorizontalGroup(layout.createParallelGroup()
//            .addComponent(headerPanel)
                .addComponent(paintsPanel)
        );
        layout.setVerticalGroup(layout.createSequentialGroup()
//            .addComponent(headerPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE,
//                GroupLayout.PREFERRED_SIZE)
                .addComponent(paintsPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE,
                    GroupLayout.PREFERRED_SIZE)
        );
    }

    public ImageIcon getCloudComputerIcon() {
        return cloudComputerIcon;
    }

    public ImageIcon getAddCloudComputerIcon() {
        return addCloudComputerIcon;
    }

    public ImageIcon getDeleteCloudComputerIcon() {
        return deleteCloudComputerIcon;
    }

    public ImageIcon getCloudProcessIcon() {
        return cloudProcessIcon;
    }

    public ImageIcon getAddCloudProcessIcon() {
        return addCloudProcessIcon;
    }

    public ImageIcon getDeleteCloudProcessIcon() {
        return deleteCloudProcessIcon;
    }

//    private JPanel createHeaderPanel() {
//        JPanel headerPanel = new JPanel(new GridLayout(0, 5));
//        JPanel addPanel = new JPanel(new GridLayout());
//        JButton addComputerButton = SwingUtils.makeSmallButton(new JButton(addCloudComputerIcon));
//        addComputerButton.setToolTipText("Add computer");
//        addComputerButton.addActionListener(e -> {
////            CloudComputer computer = new CloudComputer();
////            computer.setCpuPower(12);
////            computer.setMemory(32);
////            computer.setNetworkBandwidth(12);
////            computer.setCost(400 + 400 + 600);
////            addComputer(computer);
//        });
//        addPanel.add(addComputerButton);
//        JButton addProcessButton = SwingUtils.makeSmallButton(new JButton(addCloudProcessIcon));
//        addProcessButton.setToolTipText("Add process");
//        addProcessButton.addActionListener(e -> {
////            CloudProcess process = new CloudProcess();
////            process.setRequiredCpuPower(3);
////            process.setRequiredMemory(8);
////            process.setRequiredNetworkBandwidth(3);
////            addProcess(process);
//        });
//        addPanel.add(addProcessButton);
//        JPanel cornerPanel = new JPanel(new BorderLayout());
//        cornerPanel.add(addPanel, BorderLayout.EAST);
//        headerPanel.add(cornerPanel);
//        JLabel cpuPowerLabel = new JLabel("CPU power");
//        headerPanel.add(cpuPowerLabel);
//        JLabel memoryLabel = new JLabel("Memory");
//        headerPanel.add(memoryLabel);
//        JLabel networkBandwidthLabel = new JLabel("Network bandwidth");
//        headerPanel.add(networkBandwidthLabel);
//        JLabel costLabel = new JLabel("Cost");
//        headerPanel.add(costLabel);
//        return headerPanel;
//    }

    private JPanel createPaintsPanel() {
        paintMixesPanel = new JPanel(new GridLayout(0, 1));
        paintMixToPanelMap = new LinkedHashMap<>();
        return paintMixesPanel;
    }

    @Override
    public void resetPanel(MixerSolution mixerSolution) {
        if (targetPanel != null) {
            paintMixesPanel.remove(targetPanel);
        }
        if (solutionMixturePanel != null) {
            paintMixesPanel.remove(solutionMixturePanel);
        }

        for (PaintMixPanel paintMixPanel : paintMixToPanelMap.values()) {
            if (paintMixPanel.getPaintMix() != null) {
                paintMixesPanel.remove(paintMixPanel);
            }
        }
        paintMixToPanelMap.clear();
        paintMixesPanel.removeAll();
//        unassignedPanel = new PaintMixPanel(this, null);
//        paintMixesPanel.add(unassignedPanel);
//        paintMixToPanelMap.put(null, unassignedPanel);
        updatePanel(mixerSolution);
    }

    PaintPanel targetPanel;
    PaintPanel solutionMixturePanel;

    @Override
    public void updatePanel(MixerSolution mixerSolution) {
        if (targetPanel != null) {
            paintMixesPanel.remove(targetPanel);
        }
        if (solutionMixturePanel != null) {
            paintMixesPanel.remove(solutionMixturePanel);
        }
        targetPanel = new PaintPanel(this, mixerSolution.getTargetPaint());
        paintMixesPanel.add(targetPanel);

        var solutionMix = getSolutionPaint(mixerSolution);
        solutionMixturePanel = new PaintPanel(this, solutionMix);
        paintMixesPanel.add(solutionMixturePanel);

        Set<PaintMix> deadPaintMixes = new LinkedHashSet<>(paintMixToPanelMap.keySet());
        deadPaintMixes.remove(null);
        for (PaintMix computer : mixerSolution.getPaintMixes()) {
            deadPaintMixes.remove(computer);
            PaintMixPanel computerPanel = paintMixToPanelMap.get(computer);
            if (computerPanel == null) {
                computerPanel = new PaintMixPanel(this, computer);
                paintMixesPanel.add(computerPanel);
                paintMixToPanelMap.put(computer, computerPanel);
            }
            computerPanel.clearPaintMixes();
        }
//        unassignedPanel.clearPaintMixes();
        for (PaintMix deadComputer : deadPaintMixes) {
            PaintMixPanel deadComputerPanel = paintMixToPanelMap.remove(deadComputer);
            paintMixesPanel.remove(deadComputerPanel);
        }
        for (PaintMixPanel paintMixPanel : paintMixToPanelMap.values()) {
            paintMixPanel.update();
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
}
