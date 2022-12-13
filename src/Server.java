public class Server {
    private int port;
    private String UserName;
    private String type;

    public Server(String type, int port, String name) {
        setUserName(name);
        setType(type);
        setPort(port);
    }
    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
