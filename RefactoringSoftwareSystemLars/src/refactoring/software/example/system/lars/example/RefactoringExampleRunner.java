package refactoring.software.example.system.lars.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.eclipse.emf.common.util.URI;
import org.emoflon.gips.core.ilp.ILPSolverOutput;

import RefactoringSoftwareSystemLars.api.gips.RefactoringSoftwareSystemLarsGipsAPI;

public class RefactoringExampleRunner {

	public static void main(final String[] args) {
		final RefactoringSoftwareSystemLarsGipsAPI gipsApi = new RefactoringSoftwareSystemLarsGipsAPI();
		final String projectFolder = System.getProperty("user.dir");
		final String instancesFolder = projectFolder + "/instances";
		final String scenarioName = "TestSystem1";
		final String file = projectFolder + "/../../TGG-3.0-Prototype/Refactoring/resources/" + scenarioName + ".xmi";
		final URI uri = URI.createFileURI(file);
		gipsApi.init(uri);

		gipsApi.buildILPProblem(true);
		final ILPSolverOutput output = gipsApi.solveILPProblem();
		if (output.solutionCount() == 0) {
			throw new InternalError("No solution found!");
		}
		System.out.println("=> Objective value: " + output.objectiveValue());

		System.out.println("Embeddings: ");
		gipsApi.getEmbed().getMappings().forEach((k, v) -> {
			if (v.getValue() == 1) {
				System.out.println("  " + v.getMatch().getC().getName() + " -> " + v.getMatch().getSnew().getName());
			}
		});

		gipsApi.getEmbed().applyNonZeroMappings();

		final String outputFile = instancesFolder + "/" + scenarioName + "_solved.xmi";
		try {
			Files.createDirectories(Paths.get(instancesFolder));
			gipsApi.saveResult(outputFile);
		} catch (final IOException e) {
			e.printStackTrace();
		}

//		gipsApi.terminate();
		java.lang.System.exit(0);
	}

}
