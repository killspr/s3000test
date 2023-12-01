package runner;

public class Request {

    private int id;

    private final String jsonrpc = "2.0";



    public String method;

    private String[] params = new String[0];

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String[] getParams() {
        return params;
    }

    public void setParams(String[] params) {
        this.params = params;
    }

    public Request(int id, String method) {
        this.id = id;
        this.method = method;
    }

    public Request(int id, String method, String[] params) {
        this.id = id;
        this.method = method;
        this.params = params;
    }
}
