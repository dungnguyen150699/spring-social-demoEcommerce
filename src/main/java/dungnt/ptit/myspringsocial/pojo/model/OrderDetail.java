package dungnt.ptit.myspringsocial.pojo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "orderdetail")
@Getter
@Setter
@NoArgsConstructor
public class OrderDetail implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	private Order order;
	
	@Column(name = "count")
	private int count=0;
	
	@Column(name = "price")
	private Double price;
	
	public String toString() {
		return "id: " + this.id  + "\n productId: " + this.product.getId()  +"\n orderId:" + this.order.getId() + "\n count: " + this.count + "\n price: " + this.price; 
	}
}