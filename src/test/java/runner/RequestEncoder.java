package runner;

import com.google.gson.Gson;
import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;

public class RequestEncoder implements Encoder.Text<Request> {
    @Override
    public String encode(Request request) {
        Gson gson = new Gson();
        return gson.toJson(request) + "\n";
    }

    @Override
    public void init(EndpointConfig config) {
        Text.super.init(config);
    }

    @Override
    public void destroy() {
        Text.super.destroy();
    }
}
