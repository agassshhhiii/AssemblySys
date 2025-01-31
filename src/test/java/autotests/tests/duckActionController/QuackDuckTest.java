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
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import java.util.Random;

@Epic("Tests for duckActionController")
@Feature("Endpoint /api/duck/action/quack")
public class QuackDuckTest extends DuckActionsClient {

    @Test(description = "Тест: уточка с чётным id издает корректный звук (quack)")
    @CitrusTest
    public void evenQuack(@Optional @CitrusResource TestCaseRunner runner) {
        CreateDucks duck = new CreateDucks()
                .color("pink")
                .height(10.0)
                .material("slime")
                .sound("quack")
                .wingsState(WingState.ACTIVE);
        createDuck(runner, duck);
        getDuckId(runner);
        checkEvenDuck(runner, duck);
        quackDuck(runner,"${duckId}","1","1");
        //validateResponseOk(runner, "duckActionTest/quackDuck/quackDuck.json");
        ResponseMessage response = new ResponseMessage()
                .sound("quack");
        validateResponsesPayload(runner, response, HttpStatus.OK);
        deleteDuck(runner, "${duckId}");
    }
    //падает тест, тк сваггер выдает moo

    @Test(description = "Тест: уточка с нечётным id издает корректный звук (quack)")
    @CitrusTest
    public void oddQuack(@Optional @CitrusResource TestCaseRunner runner) {
        CreateDucks duck = new CreateDucks()
                .color("pink")
                .height(10.0)
                .material("slime")
                .sound("quack")
                .wingsState(WingState.ACTIVE);
        createDuck(runner, duck);
        getDuckId(runner);
        checkOddDuck(runner, duck);
        quackDuck(runner,"${duckId}","1","1");
        validateResponse(runner, "duckActionTest/quackDuck/quackDuck.json", HttpStatus.OK);
        deleteDuck(runner, "${duckId}");
    }

    //через бд
    @Test(description = "Тест: уточка с нечётным id издает корректный звук (quack)")
    @CitrusTest
    public void oddQuackDB(@Optional @CitrusResource TestCaseRunner runner) {
        long randomDuckId;
        do {randomDuckId = Math.abs(new Random().nextLong());} while (randomDuckId % 2 == 0);
        runner.variable("duckId", Long.toString(randomDuckId));
        deleteDuckViaDB(runner);
        runner.variable("color", "pink");
        runner.variable("height", 10.0);
        runner.variable("material", "slime");
        runner.variable("sound", "quack");
        runner.variable("wings_state", "ACTIVE");
        createDuckViaDB(runner);
        quackDuck(runner,"${duckId}","1","1");
        validateResponse(runner, "duckActionTest/quackDuck/quackDuck.json", HttpStatus.OK);
    }

    @Test(description = "Тест: уточка с чётным id издает корректный звук (quack)")
    @CitrusTest
    public void evenQuackDB(@Optional @CitrusResource TestCaseRunner runner) {
        long randomDuckId;
        do {randomDuckId = Math.abs(new Random().nextLong());} while (randomDuckId % 2 != 0);
        runner.variable("duckId", Long.toString(randomDuckId));
        deleteDuckViaDB(runner);
        runner.variable("color", "pink");
        runner.variable("height", 10.0);
        runner.variable("material", "slime");
        runner.variable("sound", "quack");
        runner.variable("wings_state", "ACTIVE");
        createDuckViaDB(runner);
        quackDuck(runner,"${duckId}","1","1");
        validateResponse(runner, "duckActionTest/quackDuck/quackDuck.json", HttpStatus.OK);
    }
}
