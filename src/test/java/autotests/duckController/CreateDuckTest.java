package autotests.duckController;

import autotests.DuckActions;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class CreateDuckTest extends TestNGCitrusSpringSupport {
    private final DuckActions action = new DuckActions();

    @Test(description = "Тест: создание уточки с материалом rubber")
    @CitrusTest
    public void createRubberDuck(@Optional @CitrusResource TestCaseRunner runner) {
        action.createRubberDuck(runner);
        action.createDuck(runner, "${color}","${height}","${material}","${sound}","${wingsState}");
        action.idCreateDuck(runner);
        action.deleteDuck(runner, "${duckId}");
    }

    @Test(description = "Тест: создание уточки с материалом wood")
    @CitrusTest
    public void createWoodDuck(@Optional @CitrusResource TestCaseRunner runner) {
        action.createWoodDuck(runner);
        action.createDuck(runner, "${color}","${height}","${material}","${sound}","${wingsState}");
        action.idCreateDuck(runner);
        action.deleteDuck(runner, "${duckId}");
    }
}
