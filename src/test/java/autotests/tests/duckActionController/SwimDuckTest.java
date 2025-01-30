package autotests.tests.duckActionController;

import autotests.clients.DuckActionsClient;
import autotests.payloads.CreateDucks;
import autotests.payloads.WingState;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

@Epic("Tests for duckActionController")
@Feature("Endpoint /api/duck/action/swim")
public class SwimDuckTest extends DuckActionsClient {

    @Test(description = "Тест: плавание уточки с существующим id")
    @CitrusTest
    public void existingIdSwim(@Optional @CitrusResource TestCaseRunner runner) {
        CreateDucks duck = new CreateDucks()
                .color("black")
                .height(10.0)
                .material("slime")
                .sound("quack")
                .wingsState(WingState.ACTIVE);
        createDuck(runner, duck);
        getDuckId(runner);
        swimDuck(runner, "${duckId}");
        validateResponse(runner, "duckActionTest/swimDuck/existingIdSwim.json", HttpStatus.OK);
        deleteDuck(runner, "${duckId}");
    }
    //падает тест, тк сваггер выдает 404 и другое тело ответа

    @Test(description = "Тест: плавание уточки с несуществующим id")
    @CitrusTest
    public void nonExistingIdSwim(@Optional @CitrusResource TestCaseRunner runner) {
        CreateDucks duck = new CreateDucks()
                .color("black")
                .height(10.0)
                .material("slime")
                .sound("quack")
                .wingsState(WingState.ACTIVE);
        createDuck(runner, duck);
        getDuckId(runner);
        deleteDuck(runner, "${duckId}");
        swimDuck(runner, "${duckId}");
        validateResponse(runner, "duckActionTest/swimDuck/nonExistingIdSwim.json", HttpStatus.NOT_FOUND);
        //если я задам большое число для id (10000), то при реальных условиях этот id мб создан как полноценная утка
        //и тогда тест будет работать не правильно, для этого теста я взяла ситуацию с удалённым id
    }
}
