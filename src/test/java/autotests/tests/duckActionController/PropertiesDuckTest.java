package autotests.tests.duckActionController;

import autotests.clients.DuckActionsClient;
import autotests.payloads.PayloadsCreateDuck;
import autotests.payloads.WingState;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class PropertiesDuckTest extends DuckActionsClient {

    @Test(description = "Тест: уточка с нечётным id и материалом rubber показывает характеристики")
    @CitrusTest
    public void propertiesRubber(@Optional @CitrusResource TestCaseRunner runner) {
        PayloadsCreateDuck duck = new PayloadsCreateDuck()
                .color("pink")
                .height(10.0)
                .material("rubber")
                .sound("quack")
                .wingsState(WingState.ACTIVE);
        createDuck(runner, duck);
        getDuckId(runner);
        checkOddDuck(runner, duck);
        propertiesDuck(runner, "${duckId}");
        validateResponseOk(runner, "duckActionTest/PropertiesDuck/propertiesRubber.json");
        deleteDuck(runner, "${duckId}");
    }
    //в документации указывается, что просто должны вывестись характеристики, но в сваггере высота умножается на 100
    //поэтому ожидаемый результат не совпадает с фактическим

    @Test(description = "Тест: уточка с чётным id и материалом wood показывает характеристики")
    @CitrusTest
    public void propertiesWood(@Optional @CitrusResource TestCaseRunner runner) {
        PayloadsCreateDuck duck = new PayloadsCreateDuck()
                .color("pink")
                .height(10.0)
                .material("wood")
                .sound("quack")
                .wingsState(WingState.ACTIVE);
        createDuck(runner, duck);
        getDuckId(runner);
        checkEvenDuck(runner, duck);
        propertiesDuck(runner, "${duckId}");
        validateResponseOk(runner, "duckActionTest/PropertiesDuck/propertiesWood.json");
        deleteDuck(runner, "${duckId}");
    }
    //этот тест тоже будет падать, потому что вместо вывода характеристик сваггер выводит пустое тело
}
