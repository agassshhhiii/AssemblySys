package autotests.tests.duckController;

import autotests.clients.DuckActionsClient;
import autotests.payloads.CreateDucks;
import autotests.payloads.WingState;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class UpdateDuckTest extends DuckActionsClient {

    @Test(description = "Тест: изменение цвета и высоты уточки")
    @CitrusTest
    public void updateColorHeight(@Optional @CitrusResource TestCaseRunner runner) {
        CreateDucks duck = new CreateDucks()
                .color("black")
                .height(10.0)
                .material("slime")
                .sound("quack")
                .wingsState(WingState.FIXED);
        createDuck(runner, duck);
        getDuckId(runner);
        updateDuck(runner, "${duckId}", "pink", "5", "slime", "quack", "FIXED");
        validateResponseOk(runner, "duckActionTest/updateDuck/updateDuck.json");
        deleteDuck(runner, "${duckId}");
    }

    @Test(description = "Тест: изменение цвета и звука уточки")
    @CitrusTest
    public void updateColorSound(@Optional @CitrusResource TestCaseRunner runner) {
        CreateDucks duck = new CreateDucks()
                .color("black")
                .height(10.0)
                .material("slime")
                .sound("quack")
                .wingsState(WingState.FIXED);
        createDuck(runner, duck);
        getDuckId(runner);
        updateDuck(runner, "${duckId}", "pink", "10", "slime", "quack-quack", "FIXED");
        validateResponseOk(runner, "duckActionTest/updateDuck/updateDuck.json");
        deleteDuck(runner, "${duckId}");
    }
}
