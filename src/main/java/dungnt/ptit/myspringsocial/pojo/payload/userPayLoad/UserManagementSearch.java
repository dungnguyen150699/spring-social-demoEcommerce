package dungnt.ptit.myspringsocial.pojo.payload.userPayLoad;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class UserManagementSearch implements Serializable {
    private String email;
    private String type;
}
