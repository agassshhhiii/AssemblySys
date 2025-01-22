package autotests;

import com.consol.citrus.TestCaseRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static com.consol.citrus.DefaultTestActionBuilder.action;
import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DuckActions {

    public void createDuck(TestCaseRunner runner, String color, String height, String material, String sound, String wingsState) {
        runner.$(http().client("http://localhost:2222")
                .send()
                .post("/api/duck/create")
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("{\n" + "  \"color\": \"" + color + "\",\n"
                        + "  \"height\": " + height + ",\n"
                        + "  \"material\": \"" + material + "\",\n"
                        + "  \"sound\": \"" + sound + "\",\n"
                        + "  \"wingsState\": \"" + wingsState
                        + "\"\n" + "}"));
    }
    public void getDuckId(TestCaseRunner runner) {
        runner.$(http().client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .extract(fromBody().expression("$.id", "duckId")));
    }
    public void getAndCheckIdDuck(TestCaseRunner runner) {
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
    }
    public void checkOddDuck(TestCaseRunner runner, String id) {
        runner.$(action(context -> {
            String duckId = context.getVariable("duckId");
            while (Integer.parseInt(duckId) % 2 == 0) {
                deleteDuck(runner, "${duckId}");
                createDuck(runner, "pink", "10", "rubber", "quack", "UNDEFINED");
                getDuckId(runner);
                duckId = context.getVariable("duckId");
            }
        }));
    }
    public void checkEvenDuck(TestCaseRunner runner, String id) {
        runner.$(action(context -> {
            String duckId = context.getVariable("duckId");
            while (Integer.parseInt(duckId) % 2 != 0) {
                deleteDuck(runner, "${duckId}");
                createDuck(runner, "pink", "10", "wood", "quack", "UNDEFINED");
                getDuckId(runner);
                duckId = context.getVariable("duckId");
            }
        }));
    }
    public void flyDuck(TestCaseRunner runner, String id) {
        runner.$(http()
                .client("http://localhost:2222")
                .send()
                .get("/api/duck/action/fly")
                .queryParam("id", id));
    }
    public void deleteDuck(TestCaseRunner runner, String id) {
        runner.$(http()
                .client("http://localhost:2222")
                .send()
                .delete("/api/duck/delete")
                .queryParam("id", id));
    }
    public void updateDuck(TestCaseRunner runner, String id, String color, String height, String material, String sound, String wingsState) {
        runner.$(http()
                .client("http://localhost:2222")
                .send()
                .put("/api/duck/update")
                .queryParam("id", id)
                .queryParam("color",color)
                .queryParam("height",height)
                .queryParam("material",material)
                .queryParam("sound",sound)
                .queryParam("wingsState",wingsState));
    }
    public void quackDuck(TestCaseRunner runner, String id, String repetitionCount, String soundCount) {
        runner.$(http()
                .client("http://localhost:2222")
                .send()
                .get("/api/duck/action/quack")
                .queryParam("id", id)
                .queryParam("repetitionCount",repetitionCount)
                .queryParam("soundCount",soundCount));
    }
    public void swimDuck(TestCaseRunner runner, String id) {
        runner.$(http().client("http://localhost:2222")
                .send()
                .get("/api/duck/action/swim")
                .queryParam("id", id));
    }
    public void propertiesDuck(TestCaseRunner runner, String id) {
        runner.$(http()
                .client("http://localhost:2222")
                .send()
                .get("/api/duck/action/properties")
                .queryParam("id", id));
    }
    public void validateResponse(TestCaseRunner runner, String responseMessage) {
        runner.$(http().client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE).
                body(responseMessage));
    }
    //сделала отдельный метод, чтобы не менять все остальные тесты
    public void validateResponseForSwim(TestCaseRunner runner, String responseMessage,HttpStatus status) {
        runner.$(http().client("http://localhost:2222")
                .receive()
                .response(status)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE).
                body(responseMessage));
    }
}
