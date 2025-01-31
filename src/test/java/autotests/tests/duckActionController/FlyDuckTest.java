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
@Feature("Endpoint /api/duck/action/fly")
public class FlyDuckTest extends DuckActionsClient {

    @Test(description = "Тест: полёт уточки с активными крыльями и существующим id")
    @CitrusTest
    public void flyActive(@Optional @CitrusResource TestCaseRunner runner) {
        CreateDucks duck = new CreateDucks()
                .color("pink")
                .height(10.0)
                .material("puff")
                .sound("quack")
                .wingsState(WingState.ACTIVE);
        createDuck(runner, duck);
        getDuckId(runner);
        flyDuck(runner, "${duckId}");
        validateResponseOk(runner, "duckActionTest/flyDuck/flyActive.json");
        deleteDuck(runner, "${duckId}");
    }
    //падает тест, потому что в документации ожидаемый ответ другой

    @Test(description = "Тест: полёт уточки с неактивными крыльями и существующим id")
    @CitrusTest
    public void flyFixed(@Optional @CitrusResource TestCaseRunner runner) {
        CreateDucks duck = new CreateDucks()
                .color("black")
                .height(10.0)
                .material("slime")
                .sound("quack")
                .wingsState(WingState.FIXED);
        createDuck(runner, duck);
        getDuckId(runner);
        flyDuck(runner, "${duckId}");
        //проверка через Payload
        ResponseMessage response = new ResponseMessage()
                .message("I can't fly");
        validateResponsesPayload(runner, response);
        deleteDuck(runner, "${duckId}");
    }
    //падает тест, потому что в документации ожидаемый ответ другой

    @Test(description = "Тест: полёт уточки с неопределёнными крыльями и существующим id")
    @CitrusTest
    public void flyUndefined(@Optional @CitrusResource TestCaseRunner runner) {
        CreateDucks duck = new CreateDucks()
                .color("black")
                .height(10.0)
                .material("slime")
                .sound("quack")
                .wingsState(WingState.UNDEFINED);
        createDuck(runner, duck);
        getDuckId(runner);
        flyDuck(runner, "${duckId}");
        validateResponseOk(runner, "duckActionTest/flyDuck/flyUndefined.json");
        deleteDuck(runner, "${duckId}");
    }
    //насчет этого не уверена как поступить, в документации такого вообще не ожидается, а проверить надо, оставила ответ сваггера

    //тесты через бд
    @Test(description = "Тест: полёт уточки с активными крыльями и существующим id")
    @CitrusTest
    public void flyActiveDB(@Optional @CitrusResource TestCaseRunner runner) {
        long randomDuckId = Math.abs(new Random().nextLong());
        runner.variable("duckId", Long.toString(randomDuckId));
        deleteDuckViaDB(runner);
        runner.variable("color", "pink");
        runner.variable("height", 10.0);
        runner.variable("material", "puff");
        runner.variable("sound", "quack");
        runner.variable("wings_state", "ACTIVE");
        createDuckViaDB(runner);
        flyDuck(runner, "${duckId}");
        validateResponseOk(runner, "duckActionTest/flyDuck/flyActive.json");
    }

    @Test(description = "Тест: полёт уточки с неактивными крыльями и существующим id")
    @CitrusTest
    public void flyFixedDB(@Optional @CitrusResource TestCaseRunner runner) {
        long randomDuckId = Math.abs(new Random().nextLong());
        runner.variable("duckId", Long.toString(randomDuckId));
        deleteDuckViaDB(runner);
        runner.variable("color", "black");
        runner.variable("height", 10.0);
        runner.variable("material", "slime");
        runner.variable("sound", "quack");
        runner.variable("wings_state", "FIXED");
        createDuckViaDB(runner);
        flyDuck(runner, "${duckId}");
        //проверка через Payload
        ResponseMessage response = new ResponseMessage()
                .message("I can't fly");
        validateResponsesPayload(runner, response);
    }

    @Test(description = "Тест: полёт уточки с неопределёнными крыльями и существующим id")
    @CitrusTest
    public void flyUndefinedDB(@Optional @CitrusResource TestCaseRunner runner) {
        long randomDuckId = Math.abs(new Random().nextLong());
        runner.variable("duckId", Long.toString(randomDuckId));
        deleteDuckViaDB(runner);
        runner.variable("color", "pink");
        runner.variable("height", 10.0);
        runner.variable("material", "slime");
        runner.variable("sound", "quack");
        runner.variable("wings_state", "UNDEFINED");
        createDuckViaDB(runner);
        flyDuck(runner, "${duckId}");
        validateResponseOk(runner, "duckActionTest/flyDuck/flyUndefined.json");
    }
}
