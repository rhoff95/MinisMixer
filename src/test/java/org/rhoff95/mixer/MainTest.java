package org.rhoff95.mixer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.optaplanner.examples.app.OptaPlannerExamplesApp;
import org.optaplanner.examples.common.app.CommonApp;

public class MainTest {

    private static final Logger logger = LogManager.getLogger(MainTest.class);

    @Test
    public void testPaintAdd() {
//        SolverFactory<MixtureSolution> solverFactory = SolverFactory.createFromXmlResource(
//            "mixerSolverConfig.xml");
//        Solver<MixtureSolution> solver = solverFactory.buildSolver();
//
//        List<Paint> paintList = Arrays.asList(Vallejo.BloodyRed, Vallejo.MoonYellow);
//        Paint targetPaint = Vallejo.OrangeFire;
//
//        MixtureSolution unsolvedPaintMixture = new MixtureSolution(paintList);
//        unsolvedPaintMixture.setTargetPaint(targetPaint);
//
//        MixtureSolution solution = solver.solve(unsolvedPaintMixture);
//
//        logger.info("Solving complete: {}", solution);
        CommonApp.prepareSwingEnvironment();
        OptaPlannerExamplesApp optaPlannerExamplesApp = new OptaPlannerExamplesApp();
        optaPlannerExamplesApp.pack();
        optaPlannerExamplesApp.setLocationRelativeTo(null);
        optaPlannerExamplesApp.setVisible(true);
        optaPlannerExamplesApp.setVisible(true);
    }
}
