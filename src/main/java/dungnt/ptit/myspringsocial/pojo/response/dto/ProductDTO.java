package dungnt.ptit.myspringsocial.pojo.response.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Base64;
import java.util.List;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    private String name;

    private Byte[] img;

    private Double price;

    private int count;

    private List<OrderDetailDTO> orderDetails ;

    private CategoryDTO category;

    public String getImgBase64(){
        if(getImg() == null) return "";
        return Base64.getEncoder().encodeToString(toBytePrimitive(getImg()));
    }

    public byte[] toBytePrimitive(Byte[] byteWrapper){
        if(byteWrapper == null) return null;
        byte []bytePrimitive = new byte[byteWrapper.length];
        for(int i=0 ; i<bytePrimitive.length ; i++){
            bytePrimitive[i] = byteWrapper[i];
        }
        return bytePrimitive;
    }

    public String toString() {
        return "id:" + this.id + "\n name: " + this.name  + "\n count: " + this.count + "\n price: " + this.price;
    }

}
