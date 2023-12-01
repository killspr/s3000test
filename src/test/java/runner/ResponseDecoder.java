package runner;

import com.google.gson.Gson;
import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class ResponseDecoder implements Decoder.Text<Response> {
    @Override
    public Response decode(String s) {
        Gson gson = new Gson();
        if (s.contains("result")) {
            ProjectUtils.log("got result: " + s);
            return gson.fromJson(s, Result.class);
        } else if (s.contains("error")) {
            ProjectUtils.log("got error: " + s);

            return gson.fromJson(s, Error.class);
        }
        ProjectUtils.log("got event: " + s);
        return null;
    }

    @Override
    public boolean willDecode(String s) {
        return (s != null);
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
