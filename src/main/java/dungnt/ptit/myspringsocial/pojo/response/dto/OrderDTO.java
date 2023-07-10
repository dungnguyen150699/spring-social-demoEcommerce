package dungnt.ptit.myspringsocial.pojo.response.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.sql.Date;
import java.util.List;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private Date dateOrder;

    private String ship_method;

    private int approved = 0 ;

    private UserDTO user;

    private List<OrderDetailDTO> orderDetails;
}
