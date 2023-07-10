package dungnt.ptit.myspringsocial.pojo.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest implements Serializable {

    private static final long serialVersionUID = 7047170666528429177L;

    @NotBlank(message = "auth.login.emailBlank")
    @NotNull(message = "auth.login.emailBlank")
    @Email(message = "user.email.invalid")
    private String email;

    @NotBlank(message = "auth.login.passwordBlank")
    @NotNull(message = "auth.login.passwordBlank")
    private String password;
}
