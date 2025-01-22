package autotests.duckActionController;

import autotests.DuckActions;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class PropertiesDuckTest extends TestNGCitrusSpringSupport {
    private final DuckActions action = new DuckActions();

    @Test(description = "Тест: уточка с нечётным id и материалом rubber показывает характеристики")
    @CitrusTest
    public void propertiesRubber(@Optional @CitrusResource TestCaseRunner runner) {
        action.createDuck(runner, "pink", "10", "rubber", "quack", "UNDEFINED");
        action.idDuck(runner);
        action.checkOddDuck(runner, "${duckId}");
        action.propertiesDuck(runner, "${duckId}");
        action.validateResponse(runner, "{\n"
                + "  \"color\": \"pink\",\n"
                + "  \"height\": 1000.0,\n"
                + "  \"material\": \"rubber\",\n"
                + "  \"sound\": \"quack\",\n"
                + "  \"wingsState\": \"UNDEFINED\"\n" + "}");
        action.deleteDuck(runner, "${duckId}");
    }

    @Test(description = "Тест: уточка с чётным id и материалом wood показывает характеристики")
    @CitrusTest
    public void propertiesWood(@Optional @CitrusResource TestCaseRunner runner) {
        action.createDuck(runner, "pink", "10", "wood", "quack", "UNDEFINED");
        action.idDuck(runner);
        action.checkEvenDuck(runner, "${duckId}");
        action.propertiesDuck(runner, "${duckId}");
        action.validateResponse(runner, "{\n" + "}");
        action.deleteDuck(runner, "${duckId}");
    }
}
