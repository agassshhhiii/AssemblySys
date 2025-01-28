package autotests.clients;

import autotests.EndpointConfig;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;

import static com.consol.citrus.DefaultTestActionBuilder.action;
import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

@ContextConfiguration(classes = {EndpointConfig.class})
public class DuckActionsClient extends TestNGCitrusSpringSupport {

    @Autowired
    protected HttpClient DuckService;

    public void createDuck(TestCaseRunner runner, Object body) {
        runner.$(http().client(DuckService)
                .send()
                .post("/api/duck/create")
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ObjectMappingPayloadBuilder(body, new ObjectMapper())));
    }

    public void getDuckId(TestCaseRunner runner) {
        runner.$(http().client(DuckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .extract(fromBody().expression("$.id", "duckId")));
    }

    public void checkOddDuck(TestCaseRunner runner, Object body) {
        runner.$(action(context -> {
            String duckId = context.getVariable("duckId");
            while (Integer.parseInt(duckId) % 2 == 0) {
                deleteDuck(runner, "${duckId}");
                createDuck(runner, body);
                getDuckId(runner);
                duckId = context.getVariable("duckId");
            }
        }));
    }

    public void checkEvenDuck(TestCaseRunner runner, Object body) {
        runner.$(action(context -> {
            String duckId = context.getVariable("duckId");
            while (Integer.parseInt(duckId) % 2 != 0) {
                deleteDuck(runner, "${duckId}");
                createDuck(runner, body);
                getDuckId(runner);
                duckId = context.getVariable("duckId");
            }
        }));
    }

    public void flyDuck(TestCaseRunner runner, String id) {
        runner.$(http()
                .client(DuckService)
                .send()
                .get("/api/duck/action/fly")
                .queryParam("id", id));
    }

    public void deleteDuck(TestCaseRunner runner, String id) {
        runner.$(http()
                .client(DuckService)
                .send()
                .delete("/api/duck/delete")
                .queryParam("id", id));
    }

    public void updateDuck(TestCaseRunner runner, String id, String color, String height, String material, String sound, String wingsState) {
        runner.$(http()
                .client(DuckService)
                .send()
                .put("/api/duck/update")
                .queryParam("id", id)
                .queryParam("color", color)
                .queryParam("height", height)
                .queryParam("material", material)
                .queryParam("sound", sound)
                .queryParam("wingsState", wingsState));
    }

    public void quackDuck(TestCaseRunner runner, String id, String repetitionCount, String soundCount) {
        runner.$(http()
                .client(DuckService)
                .send()
                .get("/api/duck/action/quack")
                .queryParam("id", id)
                .queryParam("repetitionCount", repetitionCount)
                .queryParam("soundCount", soundCount));
    }

    public void swimDuck(TestCaseRunner runner, String id) {
        runner.$(http().client(DuckService)
                .send()
                .get("/api/duck/action/swim")
                .queryParam("id", id));
    }

    public void propertiesDuck(TestCaseRunner runner, String id) {
        runner.$(http()
                .client(DuckService)
                .send()
                .get("/api/duck/action/properties")
                .queryParam("id", id));
    }

    public void validateResponseOk(TestCaseRunner runner, String responseMessage) {
        runner.$(http().client(DuckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ClassPathResource(responseMessage)));
    }

    //сделала отдельный метод для swim, чтобы не менять все остальные тесты
    public void validateResponse(TestCaseRunner runner, String responseMessage, HttpStatus status) {
        runner.$(http().client(DuckService)
                .receive()
                .response(status)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ClassPathResource(responseMessage)));
    }

    public void validateCreateAndGetId(TestCaseRunner runner, String responseMessage) {
        runner.$(http().client(DuckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .extract(fromBody().expression("$.id", "duckId"))
                .body(new ClassPathResource(responseMessage)));
    }

    //метод для проверки ответа через string в DeleteDuck
    public void validateResponseOkDeleteDuck(TestCaseRunner runner, String responseMessage) {
        runner.$(http().client(DuckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(responseMessage));
    }

    //исходя из примера в лекции с созданием утки создала файл в payload
    public void validateResponsesPayload(TestCaseRunner runner, Object expectedPayload) {
        runner.$(http().client(DuckService)
                .receive()
                .response(HttpStatus.OK)
                .message().type(MessageType.JSON)
                .body(new ObjectMappingPayloadBuilder(expectedPayload, new ObjectMapper())));
    }
}
