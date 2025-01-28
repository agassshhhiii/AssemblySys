package autotests.tests.duckActionController;

import autotests.clients.DuckActionsClient;
import autotests.payloads.CreateDucks;
import autotests.payloads.ResponseMessage;
import autotests.payloads.WingState;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

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
        validateResponsesPayload(runner, response);
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
        validateResponseOk(runner, "duckActionTest/quackDuck/quackDuck.json");
        deleteDuck(runner, "${duckId}");
    }
}
