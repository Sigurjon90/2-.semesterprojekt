package Domain.User;

public class User {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private final Role role;

    public User(String firstName, String lastName, String username, String password, int roleid, String roleName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        role = new Role(roleid, roleName);
    }

//    public int getRoleid() {
//        return roleid;
//    }
//
//    public void setRoleid(int roleid) {
//        this.roleid = roleid;
//    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public boolean checkForPermission(int permissionID){
       return role.checkForPermission(permissionID); 
    }
}
