package org.rhoff95.mixer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.rhoff95.mixer.constants.Vallejo;
import org.rhoff95.mixer.domain.MixerSolution;
import org.rhoff95.mixer.domain.Paint;
import org.rhoff95.mixer.domain.PaintMix;

public class SolveTest {

    private static final Logger logger = LogManager.getLogger(SolveTest.class);

    @Test
    public void testPaintAdd() {
        SolverFactory<MixerSolution> solverFactory = SolverFactory.createFromXmlResource(
            "org/rhoff95/mixer/solver/mixerSolverConfig.xml");
        Solver<MixerSolution> solver = solverFactory.buildSolver();

        List<Paint> originalPaints = Arrays.asList(Vallejo.BloodyRed, Vallejo.MoonYellow);

        List<Paint> paintList = new ArrayList<>();
        List<PaintMix> paintMixes = new ArrayList<>();

        for(int i = 0; i < originalPaints.size(); i++) {
            Paint p = new Paint(originalPaints.get(i));
            p.setId((long) i);
            paintList.add(p);

            PaintMix pm = new PaintMix(p);
            pm.setId((long) i);
            paintMixes.add(pm);
        }

        Paint targetPaint = new Paint(Vallejo.OrangeFire);
        targetPaint.setId((long) originalPaints.size() );

        MixerSolution unsolvedPaintMixture = new MixerSolution(0L);
        unsolvedPaintMixture.setPaints(paintList);
        unsolvedPaintMixture.setPaintMixes(paintMixes);
        unsolvedPaintMixture.setTargetPaint(targetPaint);

        MixerSolution solution = solver.solve(unsolvedPaintMixture);

        logger.info("Solving complete: {}", solution);
    }
}
