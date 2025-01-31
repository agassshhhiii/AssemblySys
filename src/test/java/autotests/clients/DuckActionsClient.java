package autotests.clients;

import autotests.EndpointConfig;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.context.ContextConfiguration;

import static com.consol.citrus.DefaultTestActionBuilder.action;
import static com.consol.citrus.actions.ExecuteSQLAction.Builder.sql;
import static com.consol.citrus.actions.ExecuteSQLQueryAction.Builder.query;
import static com.consol.citrus.container.FinallySequence.Builder.doFinally;
import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

@ContextConfiguration(classes = {EndpointConfig.class})
public class DuckActionsClient extends TestNGCitrusSpringSupport {

    @Autowired
    protected HttpClient DuckService;
    @Autowired
    protected SingleConnectionDataSource testDb;

    public void databaseUpdate(TestCaseRunner runner, String sql) {
        runner.$(sql(testDb).statement(sql));
    }

    @Step("Create duck via DB")
    public void createDuckViaDB(TestCaseRunner runner) {
                databaseUpdate(runner, "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                        "values (${duckId}, \'${color}\', ${height}, \'${material}\', \'${sound}\', \'${wings_state}\');");
    }

    @Step("Endpoint for create duck")
    public void createDuck(TestCaseRunner runner, Object body) {
        runner.$(http().client(DuckService)
                .send()
                .post("/api/duck/create")
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ObjectMappingPayloadBuilder(body, new ObjectMapper())));
    }

    @Step("Get id")
    public void getDuckId(TestCaseRunner runner) {
        runner.$(http().client(DuckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .extract(fromBody().expression("$.id", "duckId")));
    }

    @Step("Odd id")
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

    @Step("Even id")
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

    @Step("Endpoint for fly duck")
    public void flyDuck(TestCaseRunner runner, String id) {
        runner.$(http()
                .client(DuckService)
                .send()
                .get("/api/duck/action/fly")
                .queryParam("id", id));
    }

    @Step("Delete duck via DB")
    public void deleteDuckViaDB(TestCaseRunner runner) {
        runner.$(doFinally().actions(context ->
                databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=${duckId}")));
    }

    @Step("Endpoint for delete duck")
    public void deleteDuck(TestCaseRunner runner, String id) {
        runner.$(http()
                .client(DuckService)
                .send()
                .delete("/api/duck/delete")
                .queryParam("id", id));
    }

    @Step("Endpoint for update duck")
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

    @Step("Endpoint for quack duck")
    public void quackDuck(TestCaseRunner runner, String id, String repetitionCount, String soundCount) {
        runner.$(http()
                .client(DuckService)
                .send()
                .get("/api/duck/action/quack")
                .queryParam("id", id)
                .queryParam("repetitionCount", repetitionCount)
                .queryParam("soundCount", soundCount));
    }

    @Step("Endpoint for swim duck")
    public void swimDuck(TestCaseRunner runner, String id) {
        runner.$(http().client(DuckService)
                .send()
                .get("/api/duck/action/swim")
                .queryParam("id", id));
    }

    @Step("Endpoint for properties duck")
    public void propertiesDuck(TestCaseRunner runner, String id) {
        runner.$(http()
                .client(DuckService)
                .send()
                .get("/api/duck/action/properties")
                .queryParam("id", id));
    }

    @Step("Validate response for status OK")
    public void validateResponseOk(TestCaseRunner runner, String responseMessage) {
        runner.$(http().client(DuckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ClassPathResource(responseMessage)));
    }

    //сделала отдельный метод для swim, чтобы не менять все остальные тесты
    @Step("Validate response for any status")
    public void validateResponse(TestCaseRunner runner, String responseMessage, HttpStatus status) {
        runner.$(http().client(DuckService)
                .receive()
                .response(status)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ClassPathResource(responseMessage)));
    }

    @Step("Validate response for status OK and get id")
    public void validateCreateAndGetId(TestCaseRunner runner, String responseMessage) {
        runner.$(http().client(DuckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .extract(fromBody().expression("$.id", "duckId"))
                .body(new ClassPathResource(responseMessage)));
    }

    //метод для проверки ответа через string
    @Step("Validate response for status OK and via string")
    public void validateResponseViaStringOk(TestCaseRunner runner, String responseMessage) {
        runner.$(http().client(DuckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(responseMessage));
    }

    @Step("Validate response for status OK and via payloads")
    public void validateResponsesPayload(TestCaseRunner runner, Object expectedPayload) {
        runner.$(http().client(DuckService)
                .receive()
                .response(HttpStatus.OK)
                .message().type(MessageType.JSON)
                .body(new ObjectMappingPayloadBuilder(expectedPayload, new ObjectMapper())));
    }

    @Step("Validate duck in database")
    public void validateDuckInDatabase(TestCaseRunner runner, String id, String color, String height, String material, String sound, String wingsState) {
        runner.$(query(testDb)
                .statement("SELECT * FROM DUCK WHERE ID=" + id)
                .validate("COLOR",color)
                .validate("HEIGHT",height)
                .validate("MATERIAL",material)
                .validate("SOUND",sound)
                .validate("WINGS_STATE",wingsState));
    }
}
