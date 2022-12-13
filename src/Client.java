class Client {
    private int port;
    private String UserName;
    private String type;

    public Client(String type, int port, String name) {
        setType(type);
        setPort(port);
        setUserName(name);
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