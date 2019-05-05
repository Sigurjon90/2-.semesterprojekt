
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
    
    public boolean checkForPermission(int permissionID){
        for (Permission permission : permissions) {
            if(permissionID==permission.getPermissionID()){
                return true;
            }
        }
        return false;
    }

}
