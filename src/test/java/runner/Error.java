package runner;

import com.google.gson.JsonElement;

public class Error extends Response{
    private JsonElement error;
    @Override
    public JsonElement getPayLoad() {
        return error;
    }
}
