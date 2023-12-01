import com.google.gson.JsonObject;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectUtils;
import runner.Response;
import service.BaseService;

public class FirstTest extends BaseTest {

    @Feature("Operation's with Default info")
    @Severity(SeverityLevel.NORMAL)
    @Description("get Device info")
    @Owner("Arthur Korzh")
    @Test
    public void testDeviceInfoGet() {
        Response deviceInfo = new BaseService(getClientEndPoint())
                .getDeviceInfo(++id)
                .getResponse();

        JsonObject responseResult = deviceInfo.getPayLoad().getAsJsonObject();

        Assert.assertEquals(responseResult.get("hardware_type").getAsString(), "ARM-S3000", "Error: not ARM-S3000");
        Assert.assertEquals(deviceInfo.getId(), id, "Error: mismatch id");
    }

    @AfterSuite
    protected void afterSuit() {
        ProjectUtils.log("after suit inside FirstTest");
        ProjectUtils.deleteSnapshot("AfterDeploy");
    }
}