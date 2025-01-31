package autotests.tests.duckController;

import autotests.clients.DuckActionsClient;
import autotests.payloads.CreateDucks;
import autotests.payloads.WingState;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import java.util.Random;

@Epic("Tests for duckController")
@Feature("Endpoint /api/duck/update")
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
        //проверка на изменение через бд
        validateDuckInDatabase(runner, "${duckId}", "pink", "5.0", "slime", "quack", "FIXED");
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

    //через бд
    @Test(description = "Тест: изменение цвета и высоты уточки")
    @CitrusTest
    public void updateColorHeightDB(@Optional @CitrusResource TestCaseRunner runner) {
        long randomDuckId = Math.abs(new Random().nextLong());
        runner.variable("duckId", Long.toString(randomDuckId));
        deleteDuckViaDB(runner);
        runner.variable("color", "black");
        runner.variable("height", 10.0);
        runner.variable("material", "slime");
        runner.variable("sound", "quack");
        runner.variable("wings_state", "FIXED");
        createDuckViaDB(runner);
        updateDuck(runner, "${duckId}", "pink", "5", "slime", "quack", "FIXED");
        validateResponseOk(runner, "duckActionTest/updateDuck/updateDuck.json");
        validateDuckInDatabase(runner, "${duckId}", "pink", "5.0", "slime", "quack", "FIXED");
    }

    @Test(description = "Тест: изменение цвета и звука уточки")
    @CitrusTest
    public void updateColorSoundDB(@Optional @CitrusResource TestCaseRunner runner) {
        long randomDuckId = Math.abs(new Random().nextLong());
        runner.variable("duckId", Long.toString(randomDuckId));
        deleteDuckViaDB(runner);
        runner.variable("color", "black");
        runner.variable("height", 10.0);
        runner.variable("material", "slime");
        runner.variable("sound", "quack");
        runner.variable("wings_state", "FIXED");
        createDuckViaDB(runner);
        updateDuck(runner, "${duckId}", "pink", "10", "slime", "quack-quack", "FIXED");
        validateResponseOk(runner, "duckActionTest/updateDuck/updateDuck.json");
        validateDuckInDatabase(runner, "${duckId}", "pink", "10.0", "slime", "quack-quack", "FIXED");
    }
}
