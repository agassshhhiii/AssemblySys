package autotests.tests.duckController;

import autotests.clients.DuckActionsClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class UpdateDuckTest extends DuckActionsClient {

    @Test(description = "Тест: изменение цвета и высоты уточки")
    @CitrusTest
    public void updateColorHeight(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "black", "10", "slime", "quack", "FIXED");
        getDuckId(runner);
        updateDuck(runner, "${duckId}", "pink", "5", "slime", "quack", "FIXED");
        validateResponse(runner, "{\n" + "  \"message\": \"Duck with id = ${duckId} is updated\"\n" + "}");
        deleteDuck(runner, "${duckId}");
    }

    @Test(description = "Тест: изменение цвета и звука уточки")
    @CitrusTest
    public void updateColorSound(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "black", "10", "slime", "quack", "FIXED");
        getDuckId(runner);
        updateDuck(runner, "${duckId}", "pink", "10", "slime", "quack-quack", "FIXED");
        validateResponse(runner, "{\n" + "  \"message\": \"Duck with id = ${duckId} is updated\"\n" + "}");
        deleteDuck(runner, "${duckId}");
    }
}
