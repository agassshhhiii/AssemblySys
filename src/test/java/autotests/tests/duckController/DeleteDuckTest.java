package autotests.tests.duckController;

import autotests.clients.DuckActionsClient;
import autotests.payloads.CreateDucks;
import autotests.payloads.WingState;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import java.util.Random;

@Epic("Tests for duckController")
@Feature("Endpoint /api/duck/delete")
public class DeleteDuckTest extends DuckActionsClient {

    @Test(description = "Тест: удаление уточки")
    @CitrusTest
    public void deleteDuck(@Optional @CitrusResource TestCaseRunner runner) {
        CreateDucks duck = new CreateDucks()
                .color("black")
                .height(10.0)
                .material("slime")
                .sound("quack")
                .wingsState(WingState.FIXED);
        createDuck(runner, duck);
        getDuckId(runner);
        deleteDuck(runner, "${duckId}");
        //проверка через string
        validateResponseViaStringOk(runner, "{\n" + "  \"message\": \"Duck is deleted\"\n" + "}");
    }

    //через бд
    @Test(description = "Тест: удаление уточки")
    @CitrusTest
    public void deleteDuckDB(@Optional @CitrusResource TestCaseRunner runner) {
        long randomDuckId = Math.abs(new Random().nextLong());
        runner.variable("duckId", Long.toString(randomDuckId));
        deleteDuckViaDB(runner);
        runner.variable("color", "black");
        runner.variable("height", 10.0);
        runner.variable("material", "slime");
        runner.variable("sound", "quack");
        runner.variable("wings_state", "FIXED");
        createDuckViaDB(runner);
        //deleteDuckViaDB(runner);
        //если я использую удаление через бд, то тест выдает ошибку Failed to get correlation key
        deleteDuck(runner,"${duckId}");
        validateResponseViaStringOk(runner, "{\n" + "  \"message\": \"Duck is deleted\"\n" + "}");
    }
}
