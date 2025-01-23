package autotests.tests.duckActionController;

import autotests.clients.DuckActionsClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class QuackDuckTest extends DuckActionsClient {

    @Test(description = "Тест: уточка с чётным id издает корректный звук (quack)")
    @CitrusTest
    public void evenQuack(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "pink", "10", "slime", "quack", "ACTIVE");
        getDuckId(runner);
        checkEvenDuck(runner);
        quackDuck(runner,"${duckId}","1","1");
        validateResponseOk(runner, "{\n" + "  \"sound\": \"quack\"\n" + "}");
        deleteDuck(runner, "${duckId}");
    }
    //падает тест, тк сваггер выдает moo

    @Test(description = "Тест: уточка с нечётным id издает корректный звук (quack)")
    @CitrusTest
    public void oddQuack(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "pink", "10", "slime", "quack", "ACTIVE");
        getDuckId(runner);
        checkOddDuck(runner);
        quackDuck(runner,"${duckId}","1","1");
        validateResponseOk(runner, "{\n" + "  \"sound\": \"quack\"\n" + "}");
        deleteDuck(runner, "${duckId}");
    }
}
