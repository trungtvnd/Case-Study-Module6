package codegym.com.vn.mailConfirm;



import org.springframework.context.ApplicationEvent;

public class OnSendRegistrationUserConfirmEvent extends ApplicationEvent {
    private String email;

    public OnSendRegistrationUserConfirmEvent(String email) {
        super(email);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}