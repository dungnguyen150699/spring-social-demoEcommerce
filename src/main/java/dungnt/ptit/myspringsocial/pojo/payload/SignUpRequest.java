package dungnt.ptit.myspringsocial.pojo.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
    @NotBlank(message = "auth.signup.nameBlank")
    @NotNull(message = "auth.signup.nameBlank")
    private String name;

    @NotBlank(message = "auth.signup.emailBlank")
    @NotNull(message = "auth.signup.emailBlank")
    @Email(message = "user.email.invalid")
    private String email;

    @NotBlank(message = "auth.signup.passwordBlank")
    @NotNull(message = "auth.signup.passwordBlank")
    private String password;
}
