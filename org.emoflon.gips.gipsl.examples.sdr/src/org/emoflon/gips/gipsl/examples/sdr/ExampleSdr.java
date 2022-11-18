package org.emoflon.gips.gipsl.examples.sdr;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.gips.gipsl.examples.sdr.api.gips.SdrGipsAPI;

public class ExampleSdr {

	public static void main(final String[] args) {
		final SdrGipsAPI api = new SdrGipsAPI();
		api.init(URI.createFileURI("../org.emoflon.gips.gipsl.examples.sdrmodel/instances/instance0.xmi"));
		
		api.buildILPProblem(true);
		final ILPSolverOutput output = api.solveILPProblem();
		System.out.println("Solver status: " + output.status());
		System.out.println("Objective value: " + output.objectiveValue());
		
		api.getB2t().applyNonZeroMappings();
		api.getF2i().applyNonZeroMappings();
		api.getF2t().applyNonZeroMappings();
		
		try {
			api.saveResult("./model-out.xmi");
		} catch (final IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("GIPSL run finished.");
		System.exit(0);
	}

}
