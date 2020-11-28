package org.rhoff95.mixer.app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.config.constructionheuristic.ConstructionHeuristicPhaseConfig;
import org.optaplanner.core.config.constructionheuristic.ConstructionHeuristicType;
import org.optaplanner.core.config.heuristic.selector.common.SelectionOrder;
import org.optaplanner.core.config.heuristic.selector.move.generic.ChangeMoveSelectorConfig;
import org.optaplanner.core.config.localsearch.LocalSearchPhaseConfig;
import org.optaplanner.core.config.localsearch.decider.acceptor.LocalSearchAcceptorConfig;
import org.optaplanner.core.config.phase.PhaseConfig;
import org.optaplanner.core.config.score.director.ScoreDirectorFactoryConfig;
import org.optaplanner.core.config.solver.SolverConfig;
import org.optaplanner.core.config.solver.termination.TerminationConfig;
import org.optaplanner.examples.common.app.CommonApp;
import org.optaplanner.examples.common.swingui.SolutionPanel;
import org.optaplanner.persistence.common.api.domain.solution.SolutionFileIO;
import org.rhoff95.mixer.domain.MixerSolution;
import org.rhoff95.mixer.domain.PaintMix;
import org.rhoff95.mixer.persistence.MixerXmlSolutionFileIO;
import org.rhoff95.mixer.solver.score.MixerEasyScoreCalculator;
import org.rhoff95.mixer.sqingui.MixerPanel;

public class MixerApp extends CommonApp<MixerSolution> {

    public static final String SOLVER_CONFIG = "org/rhoff95/mixer/solver/mixerSolverConfig.xml";

    public static final String DATA_DIR_NAME = "mixer";

    public MixerApp() {
        super("Paint Mixer",
            "Mix paints to get as close as possible to the target color",
            SOLVER_CONFIG,
            DATA_DIR_NAME,
            MixerPanel.LOGO_PATH);
    }

    public static void main(String[] args) {
        prepareSwingEnvironment();
        new MixerApp().init();
    }

    @Override
    protected SolverFactory<MixerSolution> createSolverFactory() {
        return createSolverFactoryByXml();
    }

    /**
     * Normal way to create a {@link Solver}.
     *
     * @return never null
     */
    protected SolverFactory<MixerSolution> createSolverFactoryByXml() {
        return SolverFactory.createFromXmlResource(SOLVER_CONFIG);
    }

    /**
     * Unused alternative. A way to create a {@link Solver} without using XML.
     * <p>
     * It is recommended to use {@link #createSolverFactoryByXml()} instead.
     *
     * @return never null
     */
    protected SolverFactory<MixerSolution> createSolverFactoryByApi() {
        SolverConfig solverConfig = new SolverConfig();

        solverConfig.setSolutionClass(MixerSolution.class);
        solverConfig.setEntityClassList(Collections.singletonList(PaintMix.class));

        ScoreDirectorFactoryConfig scoreDirectorFactoryConfig = new ScoreDirectorFactoryConfig();
        scoreDirectorFactoryConfig.setEasyScoreCalculatorClass(MixerEasyScoreCalculator.class);
//        scoreDirectorFactoryConfig.setScoreDrlList(
//            Arrays.asList("org/optaplanner/examples/nqueens/solver/nQueensConstraints.drl"));
        solverConfig.setScoreDirectorFactoryConfig(scoreDirectorFactoryConfig);

        solverConfig.setTerminationConfig(new TerminationConfig().withSecondsSpentLimit(30L));
        List<PhaseConfig> phaseConfigList = new ArrayList<>();

        ConstructionHeuristicPhaseConfig constructionHeuristicPhaseConfig = new ConstructionHeuristicPhaseConfig();
        constructionHeuristicPhaseConfig.setConstructionHeuristicType(
//            ConstructionHeuristicType.FIRST_FIT_DECREASING);
            ConstructionHeuristicType.FIRST_FIT);
        phaseConfigList.add(constructionHeuristicPhaseConfig);

        LocalSearchPhaseConfig localSearchPhaseConfig = new LocalSearchPhaseConfig();
        ChangeMoveSelectorConfig changeMoveSelectorConfig = new ChangeMoveSelectorConfig();
        changeMoveSelectorConfig.setSelectionOrder(SelectionOrder.ORIGINAL);
        localSearchPhaseConfig.setMoveSelectorConfig(changeMoveSelectorConfig);
        localSearchPhaseConfig
            .setAcceptorConfig(new LocalSearchAcceptorConfig()
                .withEntityTabuSize(5)
            );
        phaseConfigList.add(localSearchPhaseConfig);

        solverConfig.setPhaseConfigList(phaseConfigList);
        return SolverFactory.create(solverConfig);
    }

    @Override
    protected SolutionPanel<MixerSolution> createSolutionPanel() {
        return new MixerPanel();
    }

    @Override
    public SolutionFileIO<MixerSolution> createSolutionFileIO() {
        return new MixerXmlSolutionFileIO();
    }
}
