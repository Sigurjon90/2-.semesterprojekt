package Domain.User;

import Domain.CalendarModule.Calendar;
import Domain.DiaryModule.Diary;

public class User {

    private String firstName;
    private String lastName;
    private String username;

    private String password;
    private Role role;
    private Diary residentDiary;

    private int roleid;
    private int id;

    //constructor til oprettelse af case og beboer
    public User(String firstName, String lastName, String username) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;

        this.password = "initial";
        this.roleid = 4;

        role = new Role(4, "resident");
    }

    public User(String firstName, String lastName, String username, String password, String roleName, int id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.residentDiary = new Diary();
        this.username = username;
        this.password = password;
        this.id = id;

        role = new Role(roleName);

    }

    public User(String firstName, String lastName, String username, String password, int roleid, String roleName, int id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.residentDiary = new Diary();

        this.username = username;
        this.password = password;
        this.roleid = roleid;
        this.id = id;
        role = new Role(roleid, roleName);
    }

    public User(String firstName, String lastName, String username, String password, int roleid) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.residentDiary = new Diary();

        this.username = username;
        this.password = password;
        this.roleid = roleid;

    }

    public Diary getResidentDiary() {
        return residentDiary;
    }

    public void setResidentDiary(Diary residentDiary) {
        this.residentDiary = residentDiary;
    }

    public int getRoleid() {
        return roleid;
    }

    @Override
    public String toString() {
        return this.role.getRoleName() + " | " + this.firstName + " " + this.lastName + " " + this.id;
    }

    public String getFullName() {
        return firstName + lastName;
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

    public boolean checkForPermission(String name) {
        System.out.println("check");
        return role.checkForPermission(name);
    }

    public int getRoleID() {
        return role.getRoleID();
    }

    public String getRoleName() {
        return role.getRoleName();
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }
}
