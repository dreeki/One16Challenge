package pro.dreeki;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noMethods;

@AnalyzeClasses(packages = "pro.dreeki")
public class CodeStyleTest {

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
