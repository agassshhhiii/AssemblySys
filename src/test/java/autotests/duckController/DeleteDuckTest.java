package autotests.duckController;

import autotests.DuckActions;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class DeleteDuckTest extends TestNGCitrusSpringSupport {
    private final DuckActions action = new DuckActions();

    @Test(description = "Тест: удаление уточки")
    @CitrusTest
    public void deleteDuck(@Optional @CitrusResource TestCaseRunner runner) {
        action.createDuck(runner, "black", "10", "slime", "quack", "FIXED");
        action.getDuckId(runner);
        action.deleteDuck(runner, "${duckId}");
        action.validateResponse(runner, "{\n" + "  \"message\": \"Duck is deleted\"\n" + "}");
    }
}
