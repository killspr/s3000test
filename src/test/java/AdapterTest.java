import com.google.gson.JsonObject;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.Response;
import service.adapter.AdapterService;

public class AdapterTest extends BaseTest {
    @Feature("Operation's with Default info")
    @Severity(SeverityLevel.NORMAL)
    @Description("get Default Line Options")
    @Owner("Arthur Korzh")
    @Test
    public void testGetDefaultOptions(){
        Response defaultLineOptions = new AdapterService(getClientEndPoint())
                .getDefaultOptions(++id)
                .getResponse();

        JsonObject responseResult = defaultLineOptions.getPayLoad().getAsJsonObject();

        Assert.assertEquals(defaultLineOptions.getId(), id, "Error: mismatch id");
        Assert.assertTrue(responseResult.get("enableCrypto").getAsBoolean(), "Error: crypto is off");
        Assert.assertFalse(responseResult.get("enableLogging").getAsBoolean(), "Error: logging is on");
        Assert.assertFalse(responseResult.get("enableRelay").getAsBoolean(), "Error: relay is on");
        Assert.assertEquals(responseResult.get("noAnswerCountAllow").getAsInt(), 3, "wrong default noAnswerCountAllow");
    }
}
