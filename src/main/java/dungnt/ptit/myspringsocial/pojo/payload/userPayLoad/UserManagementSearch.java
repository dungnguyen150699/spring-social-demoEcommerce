package dungnt.ptit.myspringsocial.pojo.payload.userPayLoad;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Component(value = "UserManagementSearch")
public class UserManagementSearch implements Serializable {
    private String email;
    private String type;

    @Required // Nói chung là cái này dùng ở phiên bản cũ config = xml
    public void setEmail(String email) {
        this.email = email;
    }
}
