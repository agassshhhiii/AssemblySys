package autotests.duckActionController;

import autotests.DuckActions;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class SwimDuckTest extends TestNGCitrusSpringSupport {
    private final DuckActions action = new DuckActions();

    @Test(description = "Тест: плавание уточки с существующим id")
    @CitrusTest
    public void existingIdSwim(@Optional @CitrusResource TestCaseRunner runner) {
        action.createDuck(runner, "black", "10", "slime", "quack", "ACTIVE");
        action.getDuckId(runner);
        action.swimDuck(runner, "${duckId}");
        action.validateResponseForSwim(runner, "{\n" + "  \"message\": \"Paws are not found ((((\"\n" + "}", HttpStatus.NOT_FOUND);
        action.deleteDuck(runner, "${duckId}");
    }

    @Test(description = "Тест: плавание уточки с несуществующим id")
    @CitrusTest
    public void nonExistingIdSwim(@Optional @CitrusResource TestCaseRunner runner) {
        action.createDuck(runner, "black", "10", "slime", "quack", "ACTIVE");
        action.getDuckId(runner);
        action.swimDuck(runner, "${duckId}");
        action.deleteDuck(runner, "${duckId}");
        action.validateResponseForSwim(runner, "{\n" + "  \"message\": \"Duck is deleted\"\n" + "}", HttpStatus.OK);
        //в сваггере, при удалённом id, так же должен выводиться ответ об отсутствии лап, но автотест ожидает "Duck is deleted"
        //а если я задам большое число для id (10000), то при реальных условиях этот id мб создан как полноценная утка
        //и тогда тест будет работать не правильно, для этого теста я взяла ситуацию с удалённым id
    }
}
