package dungnt.ptit.myspringsocial.pojo.response.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dungnt.ptit.myspringsocial.controller.response.enums.AuthProvider;
import dungnt.ptit.myspringsocial.pojo.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {
    private Long id;
    private String name;
    private String email;
    private String imageUrl;
    private Boolean emailVerified = false;
    private String password;
    private AuthProvider provider;
    private String providerId;
    private Set<Role> roles;

}
