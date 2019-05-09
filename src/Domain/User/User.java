package Domain.User;

public class User {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Role role;
    private int roleid;
    private int id;

    public User(String firstName, String lastName, String username, String password, int roleid, String roleName, int id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.id=id;
        role = new Role(roleid, roleName);
    }
    
     public User(String firstName, String lastName, String username, String password, int roleid) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.roleid = roleid;    
        
    }

    public int getRoleid() {
        return roleid;
    }
    
    @Override
    public String toString (){
        return this.firstName +" "+ this.lastName +" "+this.id;
    }

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
        System.out.println("check");
       return role.checkForPermission(permissionID); 
    }
    
    public int getRoleID(){
        return role.getRoleID();
    }
    
    public int getID(){
         return id;
    }
    
    public void setID(int id){
        this.id=id;
    }
}
