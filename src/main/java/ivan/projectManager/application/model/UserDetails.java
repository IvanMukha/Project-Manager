package ivan.projectManager.application.model;

public class UserDetails {
    private int userId;
    private String name;
    private String surname;
    private String phone;
    private String workPhone;
    private String workAddress;
    private String department;

    public int getUserId() {
        return userId;
    }

    public UserDetails setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserDetails setName(String name) {
        this.name = name;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public UserDetails setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public UserDetails setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public UserDetails setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
        return this;
    }

    public String getWorkAddress() {
        return workAddress;
    }

    public UserDetails setWorkAddress(String workAddress) {
        this.workAddress = workAddress;
        return this;
    }

    public String getDepartment() {
        return department;
    }

    public UserDetails setDepartment(String department) {
        this.department = department;
        return this;
    }
}
