
package Domain.User;

import Persistence.UserManager;
import java.util.ArrayList;

public class Role {
    private int roleID;
    private String roleName;
    private ArrayList<Permission> permissions = new ArrayList<>();
    

    public Role(int roleID, String roleName) {
        this.roleID = roleID;
        this.roleName = roleName;
        permissions = UserManager.getPermissions(roleID);
    }
    
    public boolean checkForPermission(String name){
        for (Permission permission : permissions) {
            if(name.equals(permission.getPermissionName())){
                return true;
            }
        }
        return false;
    }
    
    public String getRoleInfo(){
        return roleName + ", " + roleID;
    }
    
    public int getRoleID(){
        return roleID;
    }
    
    public String getRoleName(){
        return roleName;
    }

}
