package autotests.tests.duckController;

import autotests.clients.DuckActionsClient;
import autotests.payloads.PayloadsCreateDuck;
import autotests.payloads.WingState;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class CreateDuckTest extends DuckActionsClient {

    @Test(description = "Тест: создание уточки с материалом rubber")
    @CitrusTest
    public void createRubberDuck(@Optional @CitrusResource TestCaseRunner runner) {
        PayloadsCreateDuck duck = new PayloadsCreateDuck()
                .color("black")
                .height(15.2)
                .material("rubber")
                .sound("quack")
                .wingsState(WingState.FIXED);
        createDuck(runner, duck);
        //проверка через string
        validateCreateAndGetId(runner, "duckActionTest/createDuck/createRubberDuck.json");
        deleteDuck(runner, "${duckId}");
    }

    @Test(description = "Тест: создание уточки с материалом wood")
    @CitrusTest
    public void createWoodDuck(@Optional @CitrusResource TestCaseRunner runner) {
        PayloadsCreateDuck duck = new PayloadsCreateDuck()
                .color("black")
                .height(15.2)
                .material("wood")
                .sound("quack")
                .wingsState(WingState.FIXED);
        createDuck(runner, duck);
        //проверка через resources
        validateCreateAndGetId(runner, "duckActionTest/createDuck/createWoodDuck.json");
        deleteDuck(runner, "${duckId}");
    }
}
