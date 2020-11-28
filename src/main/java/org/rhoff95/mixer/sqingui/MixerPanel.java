package org.rhoff95.mixer.sqingui;

import java.awt.Color;
import javax.swing.BoxLayout;
import org.optaplanner.examples.common.swingui.SolutionPanel;
import org.rhoff95.mixer.domain.MixerSolution;

public class MixerPanel extends SolutionPanel<MixerSolution> {

    public static final String LOGO_PATH = "/org/rhoff95/mixer/swingui/mixerLogo.png";

    public MixerPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.BLACK);
    }

    @Override
    public void resetPanel(MixerSolution mixerSolution) {

    }
}
