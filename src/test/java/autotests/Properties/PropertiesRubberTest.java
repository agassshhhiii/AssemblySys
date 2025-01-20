package autotests.Properties;

import autotests.DuckActions;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.springframework.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.DefaultTestActionBuilder.action;
import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class PropertiesRubberTest extends TestNGCitrusSpringSupport {
    private final DuckActions action = new DuckActions();

    @Test(description = "Проверка того, что утка с нечётным id и материалом rubber показывает характеристики", invocationCount = 2)
    @CitrusTest
    public void successfulPropertiesRubber(@Optional @CitrusResource TestCaseRunner runner) {
        runner.variable("color", "black");
        runner.variable("height", "15.0");
        runner.variable("material", "rubber");
        runner.variable("sound", "quack");
        runner.variable("wingsState", "FIXED");

        action.createDuck(runner, "${color}","${height}","${material}","${sound}","${wingsState}");
        runner.$(http().client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .extract(fromBody().expression("$.id", "duckId")));

        runner.$(action(context -> {
            String duckId = context.getVariable("duckId");
            //Проверка на четность ID
            if (Integer.parseInt(duckId) % 2 == 0) {
                action.deleteDuck(runner, "${duckId}");
                Assert.fail("ID утки четный: " + duckId);
            }}));
        action.propertiesDuck(runner, "${duckId}");
        runner.$(http().client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .body("{"
                        + "\"color\": \"${color}\","
                        + "\"height\": \"${height}\","
                        + "\"material\": \"${material}\","
                        + "\"sound\": \"${sound}\","
                        + "\"wingsState\": \"${wingsState}\""
                        + "}"));
        action.deleteDuck(runner, "${duckId}");
    }
}
