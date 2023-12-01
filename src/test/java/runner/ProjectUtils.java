package runner;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Properties;


public final class ProjectUtils {

    private static final String PREFIX_PROP = "local.";
    private static final String PROP_HOST = PREFIX_PROP + "host";
    private static final String PROP_PORT = PREFIX_PROP + "port";
    private static final String PROP_ADMIN_USERNAME = PREFIX_PROP + "admin.username";
    private static final String PROP_ADMIN_PAS = PREFIX_PROP + "admin.password";
    private static Properties properties;

    private static void initProperties() {
        if (properties == null) {
            properties = new Properties();
            try {
                InputStream inputStream = ProjectUtils.class.getClassLoader().getResourceAsStream("local.properties");
                if (inputStream == null) {
                    System.out.println("ERROR: The \u001B[31mlocal.properties\u001B[0m file not found in src/test/resources/ directory.");
                    System.out.println("You need to create it from local.properties.TEMPLATE file.");
                    System.exit(1);
                }
                properties.load(inputStream);
            } catch (IOException ignore) {
            }
        }
    }

    static {
        log("Start of initialization /***");
        log("initProperties");
        initProperties();
        log("getSoftwareVer");
        getSoftwareVer();
        log("deployService");
        deployService();
        log("createSnapshoot");
        createSnapshot();
        log("End of initialization ***/");
    }

    private static String passhash(String salt, String str_pass) {
        String str_salt= salt.replace("-", "+").replace("_", "/");
        byte[] b_salt = Base64.getDecoder().decode(str_salt);

        byte[] b_pass = str_pass.getBytes();

        byte[] b_pass_hash = new byte [b_salt.length+b_pass.length];
        int count = 0;
        for(int i = 0; i < b_salt.length; i++) {
            b_pass_hash[i] = b_salt[i];
            count++;
        }

        for (byte bPass : b_pass) {
            b_pass_hash[count++] = bPass;
        }
        MessageDigest md = null;

        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            System.err.println("NoSuchAlgorithmException exception: " + e.getMessage());
        }

        byte[] hash = new byte[0];
        if (md != null) {
            hash = md.digest(b_pass_hash);
        }
        String pass_hash_1 = Base64.getEncoder().encodeToString(hash);

        return pass_hash_1.replace("+", "-").replace("/", "_");
    }

    public static void log(String str){
        System.out.println(str);
    }

    public static String login(WebsocketClientEndpoint clientEndpoint, String user, String password, int id) {
        //start Session
        clientEndpoint.sendRequest(id, "Auth.Session.start");

        //extract salt from response
        String salt = clientEndpoint.getResponse().getPayLoad().getAsString();

        //login session
        String[] params = {user, passhash(salt, password)};
        clientEndpoint.sendRequest(++id, "Auth.Session.login", params);

        return String.valueOf(clientEndpoint.getResponse().getPayLoad().getAsJsonObject().get("token"));
    }

    public static String getPropHost() {
        return properties.getProperty(PROP_HOST);
    }

    public static String getPropPort() {
        return properties.getProperty(PROP_PORT);
    }

    static String getUserName() {
        return properties.getProperty(PROP_ADMIN_USERNAME);
    }

    static String getPassword() {
        return properties.getProperty(PROP_ADMIN_PAS);
    }

    public static void deployService() {
        //revert snapshot to beforeTest
        revertSnapshot("beforeTests3");

        try {
            //execute script
            log("useScript: connect to vm, download last container, run it, accept license");
            useScript("exp.sh");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void createSnapshot(){
        String[] arg2 = {"--url", "https://192.168.202.4/sdk", "--username", "korz@vsphere.local", "--password", "123Qwerty!", "--vmname", "Astra_Smolensk_1.7_Корж", "--operation", "create", "--snapshotname", "AfterDeploy", "--description", "beforeFirstTest"};
        VmSnapshootManager.main(arg2);
    }

    public static void revertSnapshot(String snapshotName){
        String[] arg2 = {"--url", "https://192.168.202.4/sdk", "--username", "korz@vsphere.local", "--password", "123Qwerty!", "--vmname", "Astra_Smolensk_1.7_Корж", "--operation", "revert", "--snapshotname", snapshotName};
        VmSnapshootManager.main(arg2);
    }

    public static void deleteSnapshot(String snapshotName){
        String[] arg2 = {"--url", "https://192.168.202.4/sdk", "--username", "korz@vsphere.local", "--password", "123Qwerty!", "--vmname", "Astra_Smolensk_1.7_Корж", "--operation", "remove", "--snapshotname", snapshotName, "--removechild", "0"};
        VmSnapshootManager.main(arg2);
    }

    public static void useScript(String script) throws IOException, InterruptedException {
//        String scriptPath = "/home/vmware/IdeaProjects/s3000test/src/test/resources/" + script;
        String scriptPathCI = "/var/lib/jenkins/workspace/1/src/test/resources/" + script;

        Process process = Runtime.getRuntime().exec(scriptPathCI);
        process.waitFor();

        int exitValue = process.exitValue();
        if (exitValue == 0) {
            System.out.println("Script executed successfully");
        } else {
            System.out.println("Script failed with exit value: " + exitValue);
        }
    }

    public static void getSoftwareVer(){
        try {
            useScript("softwareVersion.sh");
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}