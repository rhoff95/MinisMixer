package org.rhoff95.mixer.persistence;

import org.optaplanner.persistence.xstream.impl.domain.solution.XStreamSolutionFileIO;
import org.rhoff95.mixer.domain.MixerSolution;

public class MixerXmlSolutionFileIO extends XStreamSolutionFileIO<MixerSolution> {

    public MixerXmlSolutionFileIO() {
        super(MixerSolution.class);
    }
}
