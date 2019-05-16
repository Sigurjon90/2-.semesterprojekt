
package Domain.User;

public class Permission {
    
    private String name;
    private int permissionID;

    public Permission(String name, int permissionID) {
        this.name = name;
        this.permissionID = permissionID;
    }
    
    public int getPermissionID(){
        return permissionID;
    }
    
    public String getPermissionName(){
        return name;
    }
    
    
    
}
