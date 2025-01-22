package autotests.duckController;

import autotests.DuckActions;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class UpdateDuckTest extends TestNGCitrusSpringSupport {
    private final DuckActions action = new DuckActions();

    @Test(description = "Тест: изменение цвета и высоты уточки")
    @CitrusTest
    public void updateColorHeight(@Optional @CitrusResource TestCaseRunner runner) {
        action.createDuck(runner, "black", "10", "slime", "quack", "FIXED");
        action.idDuck(runner);
        action.updateDuck(runner, "${duckId}", "pink", "5", "slime", "quack", "FIXED");
        action.validateResponse(runner, "{\n" + "  \"message\": \"Duck with id = ${duckId} is updated\"\n" + "}");
        action.deleteDuck(runner, "${duckId}");
    }

    @Test(description = "Тест: изменение цвета и звука уточки")
    @CitrusTest
    public void updateColorSound(@Optional @CitrusResource TestCaseRunner runner) {
        action.createDuck(runner, "black", "10", "slime", "quack", "FIXED");
        action.idDuck(runner);
        action.updateDuck(runner, "${duckId}", "pink", "10", "slime", "quack-quack", "FIXED");
        action.validateResponse(runner, "{\n" + "  \"message\": \"Duck with id = ${duckId} is updated\"\n" + "}");
        action.deleteDuck(runner, "${duckId}");
    }
}
