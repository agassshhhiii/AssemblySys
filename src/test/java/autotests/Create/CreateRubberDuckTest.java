package autotests.Create;

import autotests.DuckActions;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class CreateRubberDuckTest extends TestNGCitrusSpringSupport {
    private final DuckActions action = new DuckActions();

    @Test(description = "Создание утки с материалом rubber")
    @CitrusTest
    public void createRubberDuck(@Optional @CitrusResource TestCaseRunner runner) {
        runner.variable("color", "black");
        runner.variable("height", "15.2");
        runner.variable("material", "rubber");
        runner.variable("sound", "quack");
        runner.variable("wingsState", "FIXED");

        action.createDuck(runner, "${color}","${height}","${material}","${sound}","${wingsState}");
        runner.$(http().client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .extract(fromBody().expression("$.id", "duckId"))
                .body("{"
                        + "\"id\": ${duckId},"
                        + "\"color\": \"${color}\","
                        + "\"height\": ${height},"
                        + "\"material\": \"${material}\","
                        + "\"sound\": \"${sound}\","
                        + "\"wingsState\": \"${wingsState}\""
                        + "}"));
        action.deleteDuck(runner, "${duckId}");
    }
}
