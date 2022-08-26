package pro.dreeki;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noMethods;

@AnalyzeClasses(packages = "be.gekeurd")
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
					.because("This conflicts with hexagonal architecture.");

	@ArchTest
	public static final ArchRule otherLayersShouldOnlyDependOnDomain =
			noClasses().that().resideInAnyPackage(
							PRESENTATION_LAYER,
							PERSISTENCE_LAYER,
							INIT_LAYER
					)
					.should().onlyDependOnClassesThat().resideInAPackage(DOMAIN_LAYER)
					.because("This conflicts with hexagonal architecture.");

	@ArchTest
	public static final ArchRule weShouldUseListOfRule =
			noClasses().should().callMethod(Arrays.class, "asList", Object[].class)
					.orShould().callMethod(Collections.class, "singletonList", Object.class)
					.because("List.of() is the better function for creating lists (cfr immutability).");

	@ArchTest
	public static final ArchRule junitDoesNotNeedPublicMethodsRule =
			noMethods().that().areDeclaredInClassesThat().haveSimpleNameEndingWith("Test")
					.should().bePublic()
					.because("junit 5 does not require public methods.");

	@ArchTest
	public static final ArchRule doNotUseJunit3StyleOfTests =
			noMethods().that().areAnnotatedWith(Test.class).and()
					.areDeclaredInClassesThat().haveSimpleNameEndingWith("Test")
					.should().haveNameStartingWith("test")
					.because("prefixing tests with 'test' does not have an added value.");

	@ArchTest
	public static final ArchRule noUseOfJunit5Assertions =
			noClasses()
					.should().accessClassesThat().haveFullyQualifiedName(org.junit.jupiter.api.Assertions.class.getName())
					.because("We consistently want to use assertj in our tests");
}
