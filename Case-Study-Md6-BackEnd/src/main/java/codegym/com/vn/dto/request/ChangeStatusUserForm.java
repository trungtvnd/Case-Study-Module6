package codegym.com.vn.dto.request;

public class ChangeStatusUserForm {
    private Integer statusUser;

    public ChangeStatusUserForm() {
    }

    public ChangeStatusUserForm(Integer statusUser) {
        this.statusUser = statusUser;
    }

    public Integer getStatusUser() {
        return statusUser;
    }

    public void setStatusUser(Integer statusUser) {
        this.statusUser = statusUser;
    }
}
