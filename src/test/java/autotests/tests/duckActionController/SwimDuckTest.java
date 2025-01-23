package autotests.tests.duckActionController;

import autotests.clients.DuckActionsClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class SwimDuckTest extends DuckActionsClient {

    @Test(description = "Тест: плавание уточки с существующим id")
    @CitrusTest
    public void existingIdSwim(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "black", "10", "slime", "quack", "ACTIVE");
        getDuckId(runner);
        swimDuck(runner, "${duckId}");
        validateResponse(runner, "{\n" + "  \"message\": \"I'm swimming\"\n" + "}", HttpStatus.OK);
        deleteDuck(runner, "${duckId}");
    }
    //падает тест, тк сваггер выдает 404 и другое тело ответа

    @Test(description = "Тест: плавание уточки с несуществующим id")
    @CitrusTest
    public void nonExistingIdSwim(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "black", "10", "slime", "quack", "ACTIVE");
        getDuckId(runner);
        deleteDuck(runner, "${duckId}");
        swimDuck(runner, "${duckId}");
        validateResponse(runner, "{\n" + "  \"message\": \"Paws are not found ((((\"\n" + "}", HttpStatus.NOT_FOUND);
        //если я задам большое число для id (10000), то при реальных условиях этот id мб создан как полноценная утка
        //и тогда тест будет работать не правильно, для этого теста я взяла ситуацию с удалённым id
    }
}
