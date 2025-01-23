package autotests.tests.duckActionController;

import autotests.clients.DuckActionsClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class FlyDuckTest extends DuckActionsClient {

    @Test(description = "Тест: полёт уточки с активными крыльями и существующим id")
    @CitrusTest
    public void flyActive(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "pink", "10", "puff", "quack", "ACTIVE");
        getDuckId(runner);
        flyDuck(runner, "${duckId}");
        validateResponseOk(runner, "{\n" + "  \"message\": \"I am flying\"\n" + "}");
        deleteDuck(runner, "${duckId}");
    }
    //падает тест, потому что в документации ожидаемый ответ другой

    @Test(description = "Тест: полёт уточки с неактивными крыльями и существующим id")
    @CitrusTest
    public void flyFixed(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "black", "10", "slime", "quack", "FIXED");
        getDuckId(runner);
        flyDuck(runner, "${duckId}");
        validateResponseOk(runner, "{\n" + "  \"message\": \"I can't fly\"\n" + "}");
        deleteDuck(runner, "${duckId}");
    }
    //падает тест, потому что в документации ожидаемый ответ другой

    @Test(description = "Тест: полёт уточки с неопределёнными крыльями и существующим id")
    @CitrusTest
    public void flyUndefined(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "black", "10", "slime", "quack", "UNDEFINED");
        getDuckId(runner);
        flyDuck(runner, "${duckId}");
        validateResponseOk(runner, "{\n" + "  \"message\": \"Wings are not detected :(\"\n" + "}");
        deleteDuck(runner, "${duckId}");
    }
    //насчет этого не уверена как поступить, в документации такого вообще не ожидается, а проверить надо, оставила ответ сваггера
}
