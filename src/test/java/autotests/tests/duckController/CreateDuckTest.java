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
@Feature("Endpoint /api/duck/create")
public class CreateDuckTest extends DuckActionsClient {

    @Test(description = "Тест: создание уточки с материалом rubber")
    @CitrusTest
    public void createRubberDuck(@Optional @CitrusResource TestCaseRunner runner) {
        CreateDucks duck = new CreateDucks()
                .color("black")
                .height(15.2)
                .material("rubber")
                .sound("quack")
                .wingsState(WingState.FIXED);
        createDuck(runner, duck);
        validateCreateAndGetId(runner, "duckActionTest/createDuck/createRubberDuck.json");
        deleteDuck(runner, "${duckId}");
    }

    @Test(description = "Тест: создание уточки с материалом wood")
    @CitrusTest
    public void createWoodDuck(@Optional @CitrusResource TestCaseRunner runner) {
        CreateDucks duck = new CreateDucks()
                .color("black")
                .height(15.2)
                .material("wood")
                .sound("quack")
                .wingsState(WingState.FIXED);
        createDuck(runner, duck);
        validateCreateAndGetId(runner, "duckActionTest/createDuck/createWoodDuck.json");
        deleteDuck(runner, "${duckId}");
    }

    //тесты через бд
    @Test(description = "Тест: создание уточки с материалом rubber")
    @CitrusTest
    public void createRubberDuckDB(@Optional @CitrusResource TestCaseRunner runner) {
        long randomDuckId = Math.abs(new Random().nextLong());
        runner.variable("duckId", Long.toString(randomDuckId));
        deleteDuckViaDB(runner);
        runner.variable("color", "black");
        runner.variable("height", 15.2);
        runner.variable("material", "rubber");
        runner.variable("sound", "quack");
        runner.variable("wings_state", "FIXED");
        createDuckViaDB(runner);
        validateDuckInDatabase(runner, "${duckId}", "black", "15.2", "rubber", "quack", "FIXED");
        //validateResponseOk(runner, "duckActionTest/createDuck/createRubberDuck.json");
    }

    @Test(description = "Тест: создание уточки с материалом wood")
    @CitrusTest
    public void createWoodDuckDB(@Optional @CitrusResource TestCaseRunner runner) {
        long randomDuckId = Math.abs(new Random().nextLong());
        runner.variable("duckId", Long.toString(randomDuckId));
        deleteDuckViaDB(runner);
        runner.variable("color", "black");
        runner.variable("height", 15.2);
        runner.variable("material", "wood");
        runner.variable("sound", "quack");
        runner.variable("wings_state", "FIXED");
        createDuckViaDB(runner);
        validateDuckInDatabase(runner, "${duckId}", "black", "15.2", "wood", "quack", "FIXED");
        //validateResponseOk(runner, "duckActionTest/createDuck/createWoodDuck.json");
    }
}
