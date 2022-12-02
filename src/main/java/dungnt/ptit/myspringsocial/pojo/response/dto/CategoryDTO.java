package dungnt.ptit.myspringsocial.pojo.response.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String nameCategory;

    private List<ProductDTO> listProduct ;
}
