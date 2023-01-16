package org.rhoff95.mixer.swingui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.rhoff95.mixer.domain.Paint;
import org.rhoff95.mixer.domain.PaintMix;

public class PaintPanel extends JPanel {

    private final MixerPanel mixerPanel;
    private Paint paint;
    private final ImageIcon icon;

//    private List<PaintMix> paintMixList = new ArrayList<>();

    private JLabel computerLabel;
    private JButton button;

    public PaintPanel(MixerPanel mixerPanel, Paint paint, ImageIcon icon) {
        super(new GridLayout(0, 3));
        this.mixerPanel = mixerPanel;
        this.paint = paint;
        this.icon = icon;
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(1, 2, 1, 2),
                BorderFactory.createLineBorder(Color.BLACK)),
            BorderFactory.createEmptyBorder(1, 1, 1, 1)));
        createTotalsUI();
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public String getPaintLabel() {
        return paint == null ? "Unassigned" : paint.getLabel();
    }

    public String getPaintColorLabel() {
        return paint == null
            ? "Unassigned"
            : String.format("R=%.0f, G=%.0f, B=%.0f", paint.getR(), paint.getG(), paint.getB());
    }

    public Color getPaintMixColor() {
        if (paint == null) {
            return Color.WHITE;
        }
        return new Color(paint.getR() / 255f, paint.getG() / 255f, paint.getB() / 255f);
    }

    private void createTotalsUI() {
        JPanel labelAndDeletePanel = new JPanel(new BorderLayout(5, 0));
        if (paint != null) {
            labelAndDeletePanel.add(new JLabel(icon), BorderLayout.WEST);
        }
        computerLabel = new JLabel(getPaintLabel());
        computerLabel.setEnabled(false);
        labelAndDeletePanel.add(computerLabel, BorderLayout.CENTER);
        add(labelAndDeletePanel);

        button = new JButton();
        Color paintColor = getPaintMixColor();
        button.setBackground(paintColor);
        button.setText(getPaintColorLabel());
        add(button);
    }

    public void update() {
        Color paintColor = getPaintMixColor();
        button.setBackground(paintColor);
        button.setText(getPaintColorLabel());
    }
}
