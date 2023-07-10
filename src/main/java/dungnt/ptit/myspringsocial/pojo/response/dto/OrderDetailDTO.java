package dungnt.ptit.myspringsocial.pojo.response.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private ProductDTO product;

    private OrderDTO order;

    private int count;

    private Double price;
}
