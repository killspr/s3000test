package runner;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;

import jakarta.websocket.*;

@SuppressWarnings("BusyWait")
@ClientEndpoint(encoders = RequestEncoder.class, decoders = ResponseDecoder.class)
public class WebsocketClientEndpoint {
    Session userSession = null;
    public Response response;

    public WebsocketClientEndpoint(URI endpointURI) {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            Thread.sleep(500);
            container.connectToServer(this, endpointURI);
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

//    Callback hook for Connection open events.
    @OnOpen
    public void onOpen(Session userSession) {
        ProjectUtils.log("opening websocket");

        this.userSession = userSession;
    }

//    Callback hook for Connection close events.
    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        ProjectUtils.log("closing websocket; Reason:" + reason);
        this.userSession = null;
    }

//    Callback hook for Message Events. This method will be invoked when a server send a response.
    @OnMessage
    public void onMessage(Response response) {
        if (response != null) {
            this.response = response;
        }
    }

    public WebsocketClientEndpoint sendRequest(int id, String method) {
        ProjectUtils.log("send obj w/o params:  id: " + id + ", method: " + method);
        try {
            //send msg
            this.userSession.getBasicRemote().sendObject(new Request(id, method));

            //wait for response
            int count = 0;
            do {
                Thread.sleep(500);
                if (++count >= 6){
                    throw new RuntimeException("no response");
                }
            } while (getResponse() == null);

        } catch (EncodeException ex) {
            System.err.println("EncodeException exception: " + ex.getMessage());
        } catch (IOException ex) {
            System.err.println("IOException exception: " + ex.getMessage());
        } catch (InterruptedException ex) {
            System.err.println("InterruptedException exception: " + ex.getMessage());
        }
        return this;
    }

    public WebsocketClientEndpoint sendRequest(int id, String method, String[] params){
        ProjectUtils.log("send obj with params:  id: " + id + ", method: " + method + ", params:" + Arrays.toString(params));
        try {
            //send msg
            this.userSession.getBasicRemote().sendObject(new Request(id, method, params));

            //wait for response
            int count = 0;
            do {
                Thread.sleep(500);
                if (++count >= 6){
                    throw new RuntimeException("no response");
                }
            } while (getResponse() == null);

        } catch (EncodeException ex) {
            System.err.println("EncodeException exception: " + ex.getMessage());
        } catch (IOException ex) {
            System.err.println("IOException exception: " + ex.getMessage());
        } catch (InterruptedException ex) {
            System.err.println("InterruptedException exception: " + ex.getMessage());
        }
        return this;
    }

    public Response getResponse(){
        return response;
    }

    public Session getUserSession() {
        return userSession;
    }
}