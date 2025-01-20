package autotests.Quack;

import autotests.DuckActions;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import org.testng.Assert;

import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;
import static com.consol.citrus.DefaultTestActionBuilder.action;

public class EvenIdQuackTest extends TestNGCitrusSpringSupport {
    private final DuckActions action = new DuckActions();

    @Test(description = "Проверка того, что утка с чётным id издает корректный звук (moo)", invocationCount = 2)
    @CitrusTest
    public void successfulEvenQuack(@Optional @CitrusResource TestCaseRunner runner) {
        action.createDuck(runner, "odd", "10", "slime", "quack", "UNDEFINED");
        runner.$(http().client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .extract(fromBody().expression("$.id", "duckId")));

        runner.$(action(context -> {
        String duckId = context.getVariable("duckId");
        //Проверка на четность ID
        if (Integer.parseInt(duckId) % 2 != 0) {
            action.deleteDuck(runner, "${duckId}");
            Assert.fail("ID утки нечетный: " + duckId);
        }}));

        action.quackDuck(runner,"${duckId}","1","1");
        action.validateResponse(runner, "{\n" + "  \"sound\": \"moo\"\n" + "}");
        action.deleteDuck(runner, "${duckId}");
    }
}
