package autotests.Create;

import autotests.DuckActions;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class CreateWoodDuckTest extends TestNGCitrusSpringSupport {
    private final DuckActions action = new DuckActions();

    @Test(description = "Создание утки с материалом wood")
    @CitrusTest
    public void successfulCreate(@Optional @CitrusResource TestCaseRunner runner) {
        action.createDuck(runner, "black", 0.15, "wood", "quack", "FIXED");
        runner.$(http().client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message());
        action.validateResponse(runner, "{\n"
                + "  \"id\": \"" + "${duckId}" + "\",\n"
                + "  \"color\": \"" + "black" + "\",\n"
                + "  \"height\": " + "0.15" + ",\n"
                + "  \"material\": \"" + "wood" + "\",\n"
                + "  \"sound\": \"" + "quack" + "\",\n"
                + "  \"wingsState\": \"" + "FIXED"
                + "\"\n" + "}");
    }

}
