package runner;

import com.google.gson.JsonElement;

public class Result extends Response {
    private JsonElement result;

    @Override
    public JsonElement getPayLoad() {
        return result;
    }
}
