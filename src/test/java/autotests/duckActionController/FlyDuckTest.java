package autotests.duckActionController;

import autotests.DuckActions;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class FlyDuckTest extends TestNGCitrusSpringSupport {
    private final DuckActions action = new DuckActions();

    @Test(description = "Тест: полёт уточки с активными крыльями и существующим id")
    @CitrusTest
    public void flyActive(@Optional @CitrusResource TestCaseRunner runner) {
        action.createDuck(runner, "pink", "10", "puff", "quack", "ACTIVE");
        action.idDuck(runner);
        action.flyDuck(runner, "${duckId}");
        action.validateResponse(runner, "{\n" + "  \"message\": \"I am flying :)\"\n" + "}");
        action.deleteDuck(runner, "${duckId}");
    }

    @Test(description = "Тест: полёт уточки с неактивными крыльями и существующим id")
    @CitrusTest
    public void flyFixed(@Optional @CitrusResource TestCaseRunner runner) {
        action.createDuck(runner, "black", "10", "slime", "quack", "FIXED");
        action.idDuck(runner);
        action.flyDuck(runner, "${duckId}");
        action.validateResponse(runner, "{\n" + "  \"message\": \"I can not fly :C\"\n" + "}");
        action.deleteDuck(runner, "${duckId}");
    }

    @Test(description = "Тест: полёт уточки с неопределёнными крыльями и существующим id")
    @CitrusTest
    public void flyUndefined(@Optional @CitrusResource TestCaseRunner runner) {
        action.createDuck(runner, "black", "10", "slime", "quack", "UNDEFINED");
        action.idDuck(runner);
        action.flyDuck(runner, "${duckId}");
        action.validateResponse(runner, "{\n" + "  \"message\": \"Wings are not detected :(\"\n" + "}");
        action.deleteDuck(runner, "${duckId}");
    }
}
