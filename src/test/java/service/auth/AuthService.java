package service.auth;

import io.qameta.allure.Step;
import runner.WebsocketClientEndpoint;
import service.BaseService;

public class AuthService extends BaseService {

    public AuthService(WebsocketClientEndpoint clientEndpoint){
        super(clientEndpoint);
    }

    @Step("Get user list")
    public AuthService getUserList(int id){
        this.clientEndpoint.sendRequest(id, "Auth.User.list");
        return this;
    }

    @Step("Add new user with creds: [{creds}]")
    public AuthService addNewUser(int id, String[] creds){
        this.clientEndpoint.sendRequest(id, "Auth.User.add", creds);
        return this;
    }

    @Step("Remove user with creds: [{creds}]")
    public AuthService removeUser(int id, String[] creds){
        this.clientEndpoint.sendRequest(id, "Auth.User.remove", creds);
        return this;
    }

    @Step("Change password of user[0], to [1]: [{creds}]")
    public AuthService changePass(int id, String[] creds){
        this.clientEndpoint.sendRequest(id, "Auth.User.Password.reset", creds);
        return this;
    }
}
