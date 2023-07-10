package dungnt.ptit.myspringsocial.pojo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class Order implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "date_order")
	private Date dateOrder;
	
	@Column(name = "ship_method")
	private String ship_method;
	
	@Column(name = "apporved")
	private int approved = 0 ;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
	
	public double getTotalPrice() {
		double result = 0;
		for(OrderDetail od : this.orderDetails) {
			result += od.getCount() * od.getPrice();
		}
		return result;
	}
}
