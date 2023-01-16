package org.rhoff95.mixer.swingui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import org.optaplanner.swing.impl.SwingUtils;
import org.rhoff95.mixer.domain.Paint;
import org.rhoff95.mixer.domain.PaintMix;

public class PaintMixPanel extends JPanel {

    private final MixerPanel mixerPanel;
    private PaintMix paintMix;
    private int totalAmount;

    private JProgressBar progressBar;
    private JLabel computerLabel;
    private JTextField amountField;

    public PaintMixPanel(MixerPanel mixerPanel, PaintMix paintMix) {
        super(new GridLayout(0, 3));
        this.mixerPanel = mixerPanel;
        this.paintMix = paintMix;
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(1, 2, 1, 2),
                BorderFactory.createLineBorder(Color.BLACK)),
            BorderFactory.createEmptyBorder(1, 1, 1, 1)));
        createTotalsUI();
    }

    public PaintMix getPaintMix() {
        return paintMix;
    }

    public String getPaintMixLabel() {
        return paintMix == null ? "Unassigned" : paintMix.getPaint().getLabel();
    }

    private String getPaintColorLabel() {
        if (paintMix == null) {
            return "Unassigned";
        }
        Paint paint = paintMix.getPaint();
        return String.format("R=%.0f, G=%.0f, B=%.0f", paint.getR(), paint.getG(), paint.getB());
    }

    public int getPaintMixAmount() {
        return paintMix == null ? 0 : paintMix.getAmount();
    }

    public Color getPaintMixColor() {
        if (paintMix == null) {
            return Color.WHITE;
        }
        Paint paint = paintMix.getPaint();
        return new Color(paint.getR() / 255f, paint.getG() / 255f, paint.getB() / 255f);
    }

    public void setTotalAmount(int sum) {
        totalAmount = sum;
    }

    private void createTotalsUI() {
        JPanel labelAndDeletePanel = new JPanel(new BorderLayout(5, 0));
        if (paintMix != null) {
            labelAndDeletePanel.add(new JLabel(mixerPanel.getPaintIcon()), BorderLayout.WEST);
        }
        computerLabel = new JLabel(getPaintMixLabel());
        computerLabel.setEnabled(false);
        labelAndDeletePanel.add(computerLabel, BorderLayout.CENTER);
        if (paintMix != null) {
            JPanel deletePanel = new JPanel(new BorderLayout());
            JButton deleteButton = SwingUtils
                .makeSmallButton(new JButton(mixerPanel.getDeletePaintIcon()));
            deleteButton.setToolTipText("Delete");
            deleteButton.addActionListener(e -> mixerPanel.deletePaint(paintMix.getPaint()));
            deletePanel.add(deleteButton, BorderLayout.NORTH);
            labelAndDeletePanel.add(deletePanel, BorderLayout.EAST);
        }
        add(labelAndDeletePanel);

        JButton colorButton = new JButton();
        Color paintColor = getPaintMixColor();
        colorButton.setBackground(paintColor);
        colorButton.setText(getPaintColorLabel());
        add(colorButton);

        progressBar = new JProgressBar(0, 100);
        progressBar.setValue((int) ((getPaintMixAmount() / (float) totalAmount)));
        progressBar.setStringPainted(true);
        add(progressBar);
    }

    public void update() {
        var value = 100 * (getPaintMixAmount() / (float) totalAmount);
        progressBar.setValue((int) value);
    }
}
