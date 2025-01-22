package autotests.duckActionController;

import autotests.DuckActions;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class SwimDuckTest extends TestNGCitrusSpringSupport {
    private final DuckActions action = new DuckActions();

    @Test(description = "Проверка того, что уточка поплыла")
    @CitrusTest
    public void swim(@Optional @CitrusResource TestCaseRunner runner) {
        action.createDuck(runner, "black", "10", "slime", "quack", "FIXED");
        action.idDuck(runner);
        action.swimDuck(runner, "${duckId}");
        action.validateResponse(runner, "{\n" + "  \"message\": \"Paws are not found ((((\"\n" + "}");
        action.deleteDuck(runner, "${duckId}");
    }
}
