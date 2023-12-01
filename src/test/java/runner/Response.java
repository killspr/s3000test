package runner;

import com.google.gson.JsonElement;

public abstract class Response {

    private String jsonrpc;
    private int id;
    public abstract JsonElement getPayLoad();
    public int getId() {
        return id;
    }
    public String getJsonrpc() {
        return jsonrpc;
    }

}
