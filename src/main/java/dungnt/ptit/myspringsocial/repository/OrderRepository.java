package dungnt.ptit.myspringsocial.repository;

import dungnt.ptit.myspringsocial.pojo.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
	
	@Query(value = "select max(id) from orders", nativeQuery=true)
	int LatstID_OrderDetail();
	
	@Query(value = "select * from orders where id = ?1", nativeQuery=true)
	Order findByID(Long id);
	
	@Query(value = "select * from orders where user_id = ?1", nativeQuery=true)
	List<Order> findAllByUserID(Long id);
	
	@Query(value = "select * from orders ORDER BY apporved DESC;", nativeQuery=true)
	List<Order> findDESC();
	
	@Query(value = "select * from orders ORDER BY apporved ASC;", nativeQuery=true)
	List<Order> findASC();
}
