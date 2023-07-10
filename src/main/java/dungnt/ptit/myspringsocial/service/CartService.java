package dungnt.ptit.myspringsocial.service;

import dungnt.ptit.myspringsocial.pojo.model.Order;
import dungnt.ptit.myspringsocial.pojo.model.OrderDetail;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface CartService {
    String showPage(Model model, HttpSession session);

    String updateCart(Model model, Order order, HttpSession session);

    String checkOutPage(Model model, HttpSession session);

    String approved_order(Model model,Order od, HttpSession session);

    default List<OrderDetail> CarttoList(HashMap<Long, OrderDetail> hm) {
        List<OrderDetail> list = new ArrayList<OrderDetail>(hm.values());
        list.stream().forEach(od->{
            od.setPrice(od.getProduct().getPrice());
        });
        return list;
    }
}
