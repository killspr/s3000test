import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.Response;
import service.auth.AuthService;

public class UserTest extends BaseTest {

    @Feature("Operation with user's")
    @Severity(SeverityLevel.NORMAL)
    @Description("check list of default users")
    @Owner("Arthur Korzh")
    @Test
    public void testDefaultUserList() {
        Response defaultUserList = new AuthService(getClientEndPoint())
                .getUserList(++id)
                .getResponse();

        Assert.assertEquals(defaultUserList.getPayLoad().getAsString(), "admin", "Error: non-standard user-list");
        Assert.assertEquals(defaultUserList.getId(), id, "Error: mismatch id");
        Assert.assertEquals(defaultUserList.getJsonrpc(), "2.0", "Error: wrong json-rpc version");
    }

    @Feature("Operation with user's")
    @Severity(SeverityLevel.NORMAL)
    @Description("add new user")
    @Owner("Arthur Korzh")
    @Test
    public void testAddNewUser() {
        Response addNewUser = new AuthService(getClientEndPoint())
                .addNewUser(++id, new String[]{"operator", "qwaszx"})
                .getResponse();

        Assert.assertTrue(addNewUser.getPayLoad().getAsBoolean(), "Error: user not added");
    }

    @Feature("Operation with user's")
    @Severity(SeverityLevel.NORMAL)
    @Description("check list of changed users")
    @Owner("Arthur Korzh")
    @Test
    public void testChangedUserList() {
        Response defaultUserList = new AuthService(getClientEndPoint())
                .addNewUser(++id, new String[]{"operator", "qwaszx"})
                .getUserList(++id)
                .getResponse();

        Assert.assertEquals(defaultUserList.getPayLoad().getAsJsonArray().toString(), "[\"admin\",\"operator\"]", "Error: smth wrong");
        Assert.assertEquals(defaultUserList.getId(), id, "Error: mismatch id");
        Assert.assertEquals(defaultUserList.getJsonrpc(), "2.0", "Error: wrong json-rpc version");
    }

    @Feature("Operation with user's")
    @Severity(SeverityLevel.NORMAL)
    @Description("remove added user")
    @Owner("Arthur Korzh")
    @Test
    public void testRemoveUser() {
        Response removeUser = new AuthService(getClientEndPoint())
                .addNewUser(++id, new String[]{"operator", "qwaszx"})
                .removeUser(++id, new String[]{"operator"})
                .getResponse();

        Assert.assertTrue(removeUser.getPayLoad().getAsBoolean(), "Error: user not deleted");
    }

    @Feature("Operation with user's")
    @Severity(SeverityLevel.NORMAL)
    @Description("change password of existing user")
    @Owner("Arthur Korzh")
    @Test
    public void testChangePass() {
        Response changePass = new AuthService(getClientEndPoint())
                .addNewUser(++id, new String[]{"operator", "qwaszx"})
                .changePass(++id, new String[]{"operator", "qweasdzxc"})
                .getResponse();

        Assert.assertTrue(changePass.getPayLoad().getAsBoolean(), "Error: failed to change pass");
    }
}