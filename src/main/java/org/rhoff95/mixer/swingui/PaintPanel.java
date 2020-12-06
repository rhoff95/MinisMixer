package org.rhoff95.mixer.swingui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.rhoff95.mixer.domain.Paint;
import org.rhoff95.mixer.domain.PaintMix;

public class PaintPanel extends JPanel {
    private final MixerPanel mixerPanel;
    private Paint paint;
    private List<PaintMix> paintMixList = new ArrayList<>();

    private JLabel computerLabel;
    private JTextField cpuPowerField;
//    private JTextField memoryField;
//    private JTextField networkBandwidthField;
//    private JTextField costField;

//    private JLabel numberOfProcessesLabel;
    //    private CloudBar cpuPowerBar;
//    private CloudBar memoryBar;
//    private CloudBar networkBandwidthBar;
//    private JButton detailsButton;

    public PaintPanel(MixerPanel mixerPanel, Paint paint) {
        super(new GridLayout(0, 5));
        this.mixerPanel = mixerPanel;
        this.paint = paint;
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(1, 2, 1, 2),
                BorderFactory.createLineBorder(Color.BLACK)),
            BorderFactory.createEmptyBorder(1, 1, 1, 1)));
        createTotalsUI();
//        createBarsUI();
    }

    public Paint getPaint() {
        return paint;
    }

    public String getPaintLabel() {
        return paint == null ? "Unassigned" : paint.getLabel();
    }

    public Color getPaintMixColor() {
        if(paint == null) {
            return Color.WHITE;
        }
        return new Color(paint.getR() / 255f, paint.getG() / 255f, paint.getB() / 255f);
    }

    private void createTotalsUI() {
        JPanel labelAndDeletePanel = new JPanel(new BorderLayout(5, 0));
//        if (paintMix != null) {
//            labelAndDeletePanel.add(new JLabel(mixerPanel.getCloudComputerIcon()), BorderLayout.WEST);
//        }
        computerLabel = new JLabel(getPaintLabel());
        computerLabel.setEnabled(false);
        labelAndDeletePanel.add(computerLabel, BorderLayout.CENTER);
//        if (paintMix != null) {
//            JPanel deletePanel = new JPanel(new BorderLayout());
//            JButton deleteButton = SwingUtils.makeSmallButton(new JButton(mixerPanel.getDeleteCloudComputerIcon()));
//            deleteButton.setToolTipText("Delete");
//            deleteButton.addActionListener(e -> cloudBalancingPanel.deleteComputer(computer));
//            deletePanel.add(deleteButton, BorderLayout.NORTH);
//            labelAndDeletePanel.add(deletePanel, BorderLayout.EAST);
//        }
        add(labelAndDeletePanel);
//        cpuPowerField = new JTextField("Amount: " + getPaintMixAmount());
//        cpuPowerField.setEditable(false);
//        cpuPowerField.setEnabled(false);
//        add(cpuPowerField);
//        memoryField = new JTextField("0 GB / " + "getComputerMemory()" + " GB");
//        memoryField.setEditable(false);
//        memoryField.setEnabled(false);
//        add(memoryField);
//        networkBandwidthField = new JTextField("0 GB / " + "getComputerNetworkBandwidth()" + " GB");
//        networkBandwidthField.setEditable(false);
//        networkBandwidthField.setEnabled(false);
//        add(networkBandwidthField);
//        costField = new JTextField("getComputerCost()" + " $");
//        costField.setEditable(false);
//        costField.setEnabled(false);
//        add(costField);
        JButton button = new JButton();
        Color paintColor = getPaintMixColor();
        button.setBackground(paintColor);
        button.setText(paintColor.toString());
        add(button);
    }

    public void clearPaintMixes() {
        paintMixList.clear();
    }

    public void update() {

    }
}
