package avla.registroventas.entitys;

public class UserInfo {
    private String token;
    private String username;
    private String authorities;
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserInfo(String token, String username, String rol, String id) {
        this.token = token;
        this.username = username;
        this.authorities = rol;
        this.id = Long.parseLong(id);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }
}
