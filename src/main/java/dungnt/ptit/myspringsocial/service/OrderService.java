package dungnt.ptit.myspringsocial.service;

import dungnt.ptit.myspringsocial.pojo.model.Order;
import dungnt.ptit.myspringsocial.pojo.model.OrderDetail;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface OrderService {
    void save(Order o);
    Order findById(Long id);
    List<Order> findASC();
    List<Order> findDESC();

    List<Order> findAllByUserID(Long id);
}
