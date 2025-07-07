package KritimBackend.KritimBackend.model;

public class SessionDTO {
    private String userId;
    private String userRole;

    public SessionDTO(String userId, String userRole) {
        this.userId = userId;
        this.userRole = userRole;
    }

    // Getters and setters
    public String getUserId() {
        return userId;
    }

    public String getUserRole() {
        return userRole;
    }
}
