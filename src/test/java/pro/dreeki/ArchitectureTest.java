package pro.dreeki;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = "pro.dreeki", importOptions = ImportOption.DoNotIncludeTests.class)
public class ArchitectureTest {
	private static final String DOMAIN_LAYER = "pro.dreeki.domain..";

	private static final String PRESENTATION_LAYER = "pro.dreeki.presentation..";
	private static final String PERSISTENCE_LAYER = "pro.dreeki.persistence..";
	private static final String INIT_LAYER = "pro.dreeki.init..";

	@ArchTest
	public static final ArchRule domainShouldNotDependOnAnyOtherLayerRule =
			noClasses().that().resideInAPackage(DOMAIN_LAYER)
					.should().dependOnClassesThat().resideInAnyPackage(
							PRESENTATION_LAYER,
							PERSISTENCE_LAYER,
							INIT_LAYER
					)
					.because("This conflicts with hexagonal architecture: Domain should not depend on other layers.");

	@ArchTest
	public static final ArchRule otherLayersShouldOnlyDependOnDomain =
			noClasses().that().resideInAnyPackage(
							PRESENTATION_LAYER,
							PERSISTENCE_LAYER,
							INIT_LAYER
					)
					.should().onlyDependOnClassesThat().resideInAPackage(DOMAIN_LAYER)
					.because("This conflicts with hexagonal architecture: Other layers should only depend on domain.");
}
