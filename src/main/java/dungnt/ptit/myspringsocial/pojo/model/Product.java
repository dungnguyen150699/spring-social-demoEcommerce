package dungnt.ptit.myspringsocial.pojo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Entity
@Table(name = "products")
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "name")
	private String name;
	
	@Column(name = "img")
	@Lob
//	@Type(type = "org.hibernate.type.ImageType") // Cái này dùng để nói với csdl type lưu vào csdl ntn hoặc lấy nó ra
	private Byte[] img;
	
	@Column(name = "price")
	private Double price;
	
	@Column(name = "count")
	private int count;
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	private Category category;

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

	public Byte[] toByteWrapper(byte [] bytePrimitive){
		Byte []byteWrapper = new Byte[bytePrimitive.length];
		for(int i=0 ; i<byteWrapper.length ; i++){
			byteWrapper[i] = bytePrimitive[i];
		}
		return byteWrapper;
	}
	
	public String toString() {
		return "id:" + this.id + "\n name: " + this.name  + "\n count: " + this.count + "\n price: " + this.price; 
	}
	
}