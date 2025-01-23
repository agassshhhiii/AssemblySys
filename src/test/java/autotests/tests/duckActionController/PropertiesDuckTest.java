package autotests.tests.duckActionController;

import autotests.clients.DuckActionsClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class PropertiesDuckTest extends DuckActionsClient {

    @Test(description = "Тест: уточка с нечётным id и материалом rubber показывает характеристики")
    @CitrusTest
    public void propertiesRubber(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "pink", "10", "rubber", "quack", "ACTIVE");
        getDuckId(runner);
        checkOddDuck(runner);
        propertiesDuck(runner, "${duckId}");
        validateResponseOk(runner, "{\n"
                + "  \"color\": \"pink\",\n"
                + "  \"height\": 10.0,\n"
                + "  \"material\": \"rubber\",\n"
                + "  \"sound\": \"quack\",\n"
                + "  \"wingsState\": \"ACTIVE\"\n" + "}");
        deleteDuck(runner, "${duckId}");
    }
    //в документации указывается, что просто должны вывестись характеристики, но в сваггере высота умножается на 100
    //поэтому ожидаемый результат не совпадает с фактическим

    @Test(description = "Тест: уточка с чётным id и материалом wood показывает характеристики")
    @CitrusTest
    public void propertiesWood(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "pink", "10", "wood", "quack", "ACTIVE");
        getDuckId(runner);
        checkEvenDuck(runner);
        propertiesDuck(runner, "${duckId}");
        validateResponseOk(runner, "{\n"
                + "  \"color\": \"pink\",\n"
                + "  \"height\": 10.0,\n"
                + "  \"material\": \"wood\",\n"
                + "  \"sound\": \"quack\",\n"
                + "  \"wingsState\": \"ACTIVE\"\n" + "}");
        deleteDuck(runner, "${duckId}");
    }
    //этот тест тоже будет падать, потому что вместо вывода характеристик сваггер выводит пустое тело
}
