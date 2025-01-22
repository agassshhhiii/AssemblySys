package autotests.duckActionController;

import autotests.DuckActions;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class QuackDuckTest extends TestNGCitrusSpringSupport {
    private final DuckActions action = new DuckActions();

    @Test(description = "Тест: уточка с чётным id издает корректный звук (moo)")
    @CitrusTest
    public void evenQuack(@Optional @CitrusResource TestCaseRunner runner) {
        action.createDuck(runner, "pink", "10", "slime", "quack", "UNDEFINED");
        action.idDuck(runner);
        action.checkEvenDuck(runner, "${duckId}");
        action.quackDuck(runner,"${duckId}","1","1");
        action.validateResponse(runner, "{\n" + "  \"sound\": \"moo\"\n" + "}");
        action.deleteDuck(runner, "${duckId}");
    }

    @Test(description = "Тест: уточка с нечётным id издает корректный звук (quack)")
    @CitrusTest
    public void oddQuack(@Optional @CitrusResource TestCaseRunner runner) {
        action.createDuck(runner, "pink", "10", "slime", "quack", "UNDEFINED");
        action.idDuck(runner);
        action.checkOddDuck(runner, "${duckId}");
        action.quackDuck(runner,"${duckId}","1","1");
        action.validateResponse(runner, "{\n" + "  \"sound\": \"quack\"\n" + "}");
        action.deleteDuck(runner, "${duckId}");
    }
}
