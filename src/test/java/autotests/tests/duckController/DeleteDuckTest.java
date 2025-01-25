package autotests.tests.duckController;

import autotests.clients.DuckActionsClient;
import autotests.payloads.PayloadsCreateDuck;
import autotests.payloads.WingState;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class DeleteDuckTest extends DuckActionsClient {

    @Test(description = "Тест: удаление уточки")
    @CitrusTest
    public void deleteDuck(@Optional @CitrusResource TestCaseRunner runner) {
        PayloadsCreateDuck duck = new PayloadsCreateDuck()
                .color("black")
                .height(10.0)
                .material("slime")
                .sound("quack")
                .wingsState(WingState.FIXED);
        createDuck(runner, duck);
        getDuckId(runner);
        deleteDuck(runner, "${duckId}");
        //проверка через string
        validateResponseOkDeleteDuck(runner);
    }
}
