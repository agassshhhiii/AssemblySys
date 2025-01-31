package autotests.tests.duckController;

import autotests.clients.DuckActionsClient;
import autotests.payloads.CreateDucks;
import autotests.payloads.WingState;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.springframework.http.HttpStatus;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import java.util.Random;

@Epic("Tests for duckController")
@Feature("Endpoint /api/duck/create")
public class CreateDuckTest extends DuckActionsClient {

    CreateDucks duck1 = new CreateDucks()
            .color("black")
            .height(15.2)
            .material("rubber")
            .sound("quack")
            .wingsState(WingState.FIXED);
    CreateDucks duck2 = new CreateDucks()
            .color("black")
            .height(15.2)
            .material("wood")
            .sound("quack")
            .wingsState(WingState.FIXED);
    CreateDucks duck3 = new CreateDucks()
            .color("pink")
            .height(15.2)
            .material("rubber")
            .sound("quack")
            .wingsState(WingState.ACTIVE);
    CreateDucks duck4 = new CreateDucks()
            .color("pink")
            .height(15.2)
            .material("wood")
            .sound("quack")
            .wingsState(WingState.ACTIVE);
    CreateDucks duck5 = new CreateDucks()
            .color("green")
            .height(1.2)
            .material("slime")
            .sound("quack")
            .wingsState(WingState.FIXED);
    @Test(dataProvider = "duckList")
    @CitrusTest
    @CitrusParameters({"payload","response","runner"})
    public void createDucks(Object payload, String response, @Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, payload);
        validateResponse(runner, response, HttpStatus.OK);
    }
    @DataProvider(name="duckList")
    public Object[][] DuckProvider() {
            return new Object[][] {
                    {duck1, "duckActionTest/createDuck/createRubberDuck.json", null},
                    {duck2, "duckActionTest/createDuck/createWoodDuck.json", null},
                    {duck3, "duckActionTest/createDuck/createDuck3.json", null},
                    {duck4, "duckActionTest/createDuck/createDuck4.json",null},
                    {duck5, "duckActionTest/createDuck/createDuck5.json",null}
            };
    }

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
        getDuckId(runner);
        validateDuckInDatabase(runner, "${duckId}", "black", "15.2", "rubber", "quack", "FIXED");
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
        getDuckId(runner);
        validateDuckInDatabase(runner, "${duckId}", "black", "15.2", "wood", "quack", "FIXED");
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
    }
}
