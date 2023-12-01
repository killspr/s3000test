package runner;

import io.qameta.allure.Muted;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public abstract class BaseTest {

    private WebsocketClientEndpoint clientEndPoint;
    private static String token;
    protected int id = 1;

    public void setId(int id) {
        this.id = id;
    }

    @Muted
    @BeforeMethod
    protected void beforeMethod() {
        try {
            ProjectUtils.log("beforeMethod: /***");
            String endpoint = "ws://" + ProjectUtils.getPropHost() + ":" + ProjectUtils.getPropPort() + "/wsapi/";

            // open websocket
            clientEndPoint = new WebsocketClientEndpoint(new URI(endpoint));

            //login and extract token
            token = ProjectUtils.login(clientEndPoint, ProjectUtils.getUserName(),ProjectUtils.getPassword(), id);

            System.out.println("end of beforeMethod***/");
        } catch (URISyntaxException ex) {
            System.err.println("URISyntaxException exception: " + ex.getMessage());
            closeSocket();
        }
    }

    @AfterMethod
    protected void afterMethod() {
        ProjectUtils.log("end of test");

        closeSocket();
        ProjectUtils.revertSnapshot("AfterDeploy");

        setId(1);

        System.out.println("***");
    }

    protected WebsocketClientEndpoint getClientEndPoint() {
        return clientEndPoint;
    }

    protected static String getToken() {
        return token;
    }

    protected void closeSocket(){
        if (clientEndPoint.getUserSession() != null){
            try {
                clientEndPoint.getUserSession().close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
