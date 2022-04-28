package org.emoflon.gips.gipsl.examples.mdvne;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.gips.gipsl.examples.mdvne.api.gips.MdvneGipsAPI;

public class ExampleMdVNE {

	public static void main(final String[] args) {
		// Create new MdVNE Gips API and load a model
		final MdvneGipsAPI api = new MdvneGipsAPI();
		api.init(URI.createFileURI("model-in.xmi"));

		// Build the ILP problem (including updates)
		api.buildILPProblem(true);
		final ILPSolverOutput output = api.solveILPProblem();
		System.out.println("Solver status: " + output.status());
		System.out.println("Objective value: " + output.objectiveValue());

		api.getSrv2srv().applyNonZeroMappings();
		api.getSw2node().applyNonZeroMappings();
		api.getL2p().applyNonZeroMappings();
		api.getL2s().applyNonZeroMappings();

		try {
			api.saveResult("model-out.xmi");
		} catch (final IOException e) {
			e.printStackTrace();
		}

		System.out.println("Gipsl run finished.");
		System.exit(0);
	}

}
