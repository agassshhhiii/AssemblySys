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

    //создание и удаление утки через бд
    @Test(description = "Тест: полёт уточки с неопределёнными крыльями и существующим id")
    @CitrusTest
    public void flyUndefinedDB(@Optional @CitrusResource TestCaseRunner runner) {
        runner.variable("duckId", "1234567");
        databaseUpdate(runner,  "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                "values (${duckId}, 'pink', 10.0, 'slime', 'quack','UNDEFINED');");
        flyDuck(runner, "${duckId}");
        validateResponseOk(runner, "duckActionTest/flyDuck/flyUndefined.json");
        deleteDuckViaDB(runner);
    }
}
