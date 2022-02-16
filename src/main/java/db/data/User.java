package db.data;

public class User {
    private String username;
    private String id;

    public void setUsername (String username) {
        this.username = username;
    }
    public void setId (String id) {this.id = id;}
    public  String getUsername () {
        return username;
    }
    public  String getId () {return id;}
}
