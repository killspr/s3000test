package service;

import io.qameta.allure.Step;
import runner.Response;
import runner.WebsocketClientEndpoint;

public class BaseService {
    protected final WebsocketClientEndpoint clientEndpoint;

    public BaseService(WebsocketClientEndpoint clientEndpoint) {
        this.clientEndpoint = clientEndpoint;
    }

    public Response getResponse(){
        return clientEndpoint.getResponse();
    }

    @Step("Get device info")
    public BaseService getDeviceInfo(int id){
        this.clientEndpoint.sendRequest(id, "DeviceInfo.get");
        return this;
    }
}