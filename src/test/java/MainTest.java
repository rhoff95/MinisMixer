import constants.Vallejo;
import domain.MixtureSolution;
import java.util.Arrays;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;

public class MainTest {

    private static final Logger logger = LogManager.getLogger(MainTest.class);

    @Test
    public void testPaintAdd() {
        SolverFactory<MixtureSolution> solverFactory = SolverFactory.createFromXmlResource(
            "paintMixingSolverConfig.xml");
        Solver<MixtureSolution> solver = solverFactory.buildSolver();

        MixtureSolution unsolvedPaintMixture = new MixtureSolution();

        unsolvedPaintMixture.setPaints(Arrays.asList(Vallejo.BloodyRed, Vallejo.MoonYellow));
        unsolvedPaintMixture.setTargetPaint(Vallejo.OrangeFire);

        MixtureSolution solution = solver.solve(unsolvedPaintMixture);

        logger.info("Solving complete: {}", solution);
    }
}
