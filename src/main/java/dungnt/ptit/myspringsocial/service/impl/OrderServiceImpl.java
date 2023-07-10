package dungnt.ptit.myspringsocial.service.impl;

import dungnt.ptit.myspringsocial.pojo.model.Order;
import dungnt.ptit.myspringsocial.pojo.model.OrderDetail;
import dungnt.ptit.myspringsocial.repository.OrderRepository;
import dungnt.ptit.myspringsocial.repository.ProductRepository;
import dungnt.ptit.myspringsocial.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private ProductRepository repo;
    @Autowired
    private OrderRepository or;

    @Override
    public void save(Order o) {
        or.save(o);
    }

    @Override
    public Order findById(Long id) {
        Order o = or.findByID(id);
        return o;
    }

    @Override
    public List<Order> findASC() {
        return or.findASC();
    }

    @Override
    public List<Order> findDESC() {
        return or.findDESC();
    }

    @Override
    public List<Order> findAllByUserID(Long id) {
        return or.findAllByUserID(id);
    }
}
