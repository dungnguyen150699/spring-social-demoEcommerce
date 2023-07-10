package dungnt.ptit.myspringsocial.pojo.response.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Set;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {
    private static final long serialVersionUID = 1L;

    private int id;

    private String name;

    private Set<UserDTO> users;

}
