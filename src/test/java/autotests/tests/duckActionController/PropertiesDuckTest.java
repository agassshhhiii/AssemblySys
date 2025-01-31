package autotests.tests.duckActionController;

import autotests.clients.DuckActionsClient;
import autotests.payloads.CreateDucks;
import autotests.payloads.ResponseMessage;
import autotests.payloads.WingState;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import java.util.Random;

@Epic("Tests for duckActionController")
@Feature("Endpoint /api/duck/action/properties")
public class PropertiesDuckTest extends DuckActionsClient {

    @Test(description = "Тест: уточка с нечётным id и материалом rubber показывает характеристики")
    @CitrusTest
    public void propertiesRubber(@Optional @CitrusResource TestCaseRunner runner) {
        CreateDucks duck = new CreateDucks()
                .color("pink")
                .height(10.0)
                .material("rubber")
                .sound("quack")
                .wingsState(WingState.ACTIVE);
        createDuck(runner, duck);
        getDuckId(runner);
        checkOddDuck(runner, duck);
        propertiesDuck(runner, "${duckId}");
        //validateResponseOk(runner, "duckActionTest/PropertiesDuck/propertiesRubber.json");
        ResponseMessage response = new ResponseMessage()
                .color("pink")
                .height(10.0)
                .material("rubber")
                .sound("quack")
                .wingsState("ACTIVE");
        validateResponsesPayload(runner, response);
        deleteDuck(runner, "${duckId}");
    }
    //в документации указывается, что просто должны вывестись характеристики, но в сваггере высота умножается на 100
    //поэтому ожидаемый результат не совпадает с фактическим

    @Test(description = "Тест: уточка с чётным id и материалом wood показывает характеристики")
    @CitrusTest
    public void propertiesWood(@Optional @CitrusResource TestCaseRunner runner) {
        CreateDucks duck = new CreateDucks()
                .color("pink")
                .height(10.0)
                .material("wood")
                .sound("quack")
                .wingsState(WingState.ACTIVE);
        createDuck(runner, duck);
        getDuckId(runner);
        checkEvenDuck(runner, duck);
        propertiesDuck(runner, "${duckId}");
        validateResponseOk(runner, "duckActionTest/PropertiesDuck/propertiesWood.json");
        deleteDuck(runner, "${duckId}");
    }
    //этот тест тоже будет падать, потому что вместо вывода характеристик сваггер выводит пустое тело

    //тесты через бд
    @Test(description = "Тест: уточка с чётным id и материалом wood показывает характеристики")
    @CitrusTest
    public void propertiesWoodDB(@Optional @CitrusResource TestCaseRunner runner) {
        long randomDuckId;
        do {randomDuckId = Math.abs(new Random().nextLong());} while (randomDuckId % 2 != 0);
        runner.variable("duckId", Long.toString(randomDuckId));
        deleteDuckViaDB(runner);
        runner.variable("color", "pink");
        runner.variable("height", 10.0);
        runner.variable("material", "wood");
        runner.variable("sound", "quack");
        runner.variable("wings_state", "ACTIVE");
        createDuckViaDB(runner);
        propertiesDuck(runner, "${duckId}");
        validateResponseOk(runner, "duckActionTest/PropertiesDuck/propertiesWood.json");
    }

    @Test(description = "Тест: уточка с нечётным id и материалом rubber показывает характеристики")
    @CitrusTest
    public void propertiesRubberDB(@Optional @CitrusResource TestCaseRunner runner) {
        long randomDuckId;
        do {randomDuckId = Math.abs(new Random().nextLong());} while (randomDuckId % 2 != 0);
        runner.variable("duckId", Long.toString(randomDuckId));
        deleteDuckViaDB(runner);
        runner.variable("color", "pink");
        runner.variable("height", 10.0);
        runner.variable("material", "rubber");
        runner.variable("sound", "quack");
        runner.variable("wings_state", "ACTIVE");
        createDuckViaDB(runner);
        propertiesDuck(runner, "${duckId}");
        validateResponseOk(runner, "duckActionTest/PropertiesDuck/propertiesRubber.json");
    }
}
