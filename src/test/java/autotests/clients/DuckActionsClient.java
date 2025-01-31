package autotests.clients;

import autotests.BaseTest;
import com.consol.citrus.TestCaseRunner;
import io.qameta.allure.Step;
import org.springframework.http.HttpStatus;

import static com.consol.citrus.DefaultTestActionBuilder.action;
import static com.consol.citrus.actions.ExecuteSQLAction.Builder.sql;
import static com.consol.citrus.container.FinallySequence.Builder.doFinally;
import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DuckActionsClient extends BaseTest {

    public void databaseUpdate(TestCaseRunner runner, String sql) {
        runner.$(sql(testDb).statement(sql));
    }

    @Step("Create duck via DB")
    public void createDuckViaDB(TestCaseRunner runner) {
                databaseUpdate(runner, "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                        "values (${duckId}, \'${color}\', ${height}, \'${material}\', \'${sound}\', \'${wings_state}\');");
    }

    @Step("Delete duck via DB")
    public void deleteDuckViaDB(TestCaseRunner runner) {
        runner.$(doFinally().actions(context ->
                databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=${duckId}")));
    }

    @Step("Endpoint for create duck")
    public void createDuck(TestCaseRunner runner, Object body) {
        sendPostRequest(runner, DuckService, "/api/duck/create", body);
    }

    @Step("Endpoint for delete duck")
    public void deleteDuck(TestCaseRunner runner, String id) {
        sendDeleteRequest(runner, DuckService, "/api/duck/delete", id);
    }

    @Step("Endpoint for update duck")
    public void updateDuck(TestCaseRunner runner, String id, String color, String height, String material, String sound, String wingsState){
        sendPutRequest(runner, DuckService, "/api/duck/update", id, color, height, material, sound, wingsState);
    }

    @Step("Endpoint for properties duck")
    public void propertiesDuck(TestCaseRunner runner, String id) {
        sendGetRequest(runner, DuckService, "/api/duck/action/properties", id);
    }

    @Step("Endpoint for fly duck")
    public void flyDuck(TestCaseRunner runner, String id) {
        sendGetRequest(runner, DuckService, "/api/duck/action/fly", id);
    }

    @Step("Endpoint for quack duck")
    public void quackDuck(TestCaseRunner runner, String id, String repetitionCount, String soundCount) {
        sendGetRequestQuack(runner, DuckService, "/api/duck/action/quack", id, repetitionCount, soundCount);
    }

    @Step("Endpoint for swim duck")
    public void swimDuck(TestCaseRunner runner, String id) {
        sendGetRequest(runner, DuckService, "/api/duck/action/swim", id);
    }

    @Step("Get id")
    public void getDuckId(TestCaseRunner runner) {
        runner.$(http().client(DuckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .extract(fromBody().expression("$.id", "duckId")));
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

    @Step("Validate response")
    public void validateResponse(TestCaseRunner runner, String responseMessage, HttpStatus status) {
        validateResponse(runner, DuckService, status, responseMessage);
    }

    @Step("Validate response")
    public void validateResponseString(TestCaseRunner runner, String responseMessage, HttpStatus status) {
        validateResponseViaString(runner, DuckService, status, responseMessage);
    }

    @Step("Validate response via payloads")
    public void validateResponsesPayload(TestCaseRunner runner, Object expectedPayload, HttpStatus status) {
        validateResponsePayloads(runner, DuckService, status, expectedPayload);
    }

    @Step("Validate duck in database")
    public void validateDuckInDatabase(TestCaseRunner runner, String id, String color, String height, String material, String sound, String wingsState) {
        validateResponseInDB(runner, id, color, height, material, sound, wingsState);
    }
}
