package service.adapter;

import io.qameta.allure.Step;
import runner.WebsocketClientEndpoint;
import service.BaseService;

public class AdapterService extends BaseService {

    public AdapterService(WebsocketClientEndpoint clientEndpoint){
        super(clientEndpoint);
    }

    @Step("Get user list")
    public AdapterService getDefaultOptions(int id){
        this.clientEndpoint.sendRequest(id, "Adapter.Line.getDefaultOptions");
        return this;
    }
}