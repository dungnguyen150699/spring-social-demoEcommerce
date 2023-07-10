package dungnt.ptit.myspringsocial.service.impl;

import dungnt.ptit.myspringsocial.config.oauth2.UserPrincipal;
import dungnt.ptit.myspringsocial.pojo.model.Order;
import dungnt.ptit.myspringsocial.pojo.model.OrderDetail;
import dungnt.ptit.myspringsocial.pojo.model.Product;
import dungnt.ptit.myspringsocial.pojo.model.User;
import dungnt.ptit.myspringsocial.repository.OrderDetailRepository;
import dungnt.ptit.myspringsocial.repository.OrderRepository;
import dungnt.ptit.myspringsocial.repository.ProductRepository;
import dungnt.ptit.myspringsocial.repository.UserRepository;
import dungnt.ptit.myspringsocial.service.CartService;
import dungnt.ptit.myspringsocial.service.OrderService;
import dungnt.ptit.myspringsocial.ulti.DataUntil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.HashMap;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private OrderService os;
    @Autowired
    private OrderRepository or;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository ur;

    @Override
    public String showPage(Model model, HttpSession session) {
        HashMap<Long, OrderDetail> orderDetail = (HashMap<Long, OrderDetail>) session.getAttribute("listCart");
        if (orderDetail == null) {
            orderDetail = new HashMap<Long, OrderDetail>();
            session.setAttribute("listCart", orderDetail);
            model.addAttribute("meg", "you havenot order!");
        }

        Order order = new Order();
        order.setOrderDetails(CarttoList(orderDetail));
        model.addAttribute("order", order);

        double price = 0;
        for (OrderDetail i : orderDetail.values()) {
            double priceitem = i.getCount() * i.getProduct().getPrice();
            price += priceitem;
        }
        model.addAttribute("totalPrice", price);
        return "SHOP/cart";
    }

    @Override
    public String updateCart(Model model, Order order, HttpSession session) {
        HashMap<Long, OrderDetail> orderDetail = (HashMap<Long, OrderDetail>) session.getAttribute("listCart");

        Order order1 = new Order();
        order1.setOrderDetails(CarttoList(orderDetail));
        double price = 0;
        int j = 0;
        for (OrderDetail i : order.getOrderDetails()) {
            double priceitem = i.getCount() * order1.getOrderDetails().get(j).getProduct().getPrice();
            orderDetail.get(order1.getOrderDetails().get(j).getProduct().getId()).setCount(i.getCount());
            order1.getOrderDetails().get(j).setCount(i.getCount());
            price += priceitem;
            j++;
        }
        session.setAttribute("listCart", orderDetail);
        session.setAttribute("totalPrice", price);
        model.addAttribute("order", order1);
        model.addAttribute("totalPrice", price);
        return "SHOP/cart";
    }

    @Override
    public String checkOutPage(Model model, HttpSession session) {
        String str = "";
        model.addAttribute("ship_method", str);
        UserPrincipal u = (UserPrincipal) session.getAttribute("userPrincipal");
        HashMap<Integer, OrderDetail> orderDetail = (HashMap<Integer, OrderDetail>) session.getAttribute("listCart");
        if (orderDetail.size() > 0) {
            if (DataUntil.checkNullOrEmpty(u)) {
                return "redirect:/login";
            } else {
                Order order = new Order();
                model.addAttribute("order", order);
                return "SHOP/checkout";
            }
        } else
            return "redirect:/mvc/cart";
    }

    public String approved_order(Model model,Order od, HttpSession session) {
        UserPrincipal userPrincipal = (UserPrincipal) session.getAttribute("userPrincipal");
        User u = ur.getById(userPrincipal.getId());

        HashMap<Long, OrderDetail> orderDetail = (HashMap<Long, OrderDetail>) session.getAttribute("listCart");
        Order order = new Order();
        order.setUser(u);
        order.setShip_method(od.getShip_method());
        order.setOrderDetails(CarttoList(orderDetail));

        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        order.setDateOrder(date);

        if (insertOrder2(order)) {
            return "SHOP/checkout_done";
        }
        return "redirect:/mvc/shop";
    }

    @Transactional
    public boolean insertOrder2(Order od) {
        boolean re = true;
        od.setId(LatstID_OrderDetail()+1);
        System.out.println(od.getId());
        for (OrderDetail i : od.getOrderDetails()) {
            i.setOrder(od);
            Product p = i.getProduct();
            p.setCount(i.getProduct().getCount() - i.getCount());
        }
        or.save(od);
        for (OrderDetail i : od.getOrderDetails()) {
            productRepository.save(i.getProduct());
        }
//			throw new RuntimeException();
        return re;
    }

    public int LatstID_OrderDetail() {
        int result;
        try{
            result = or.LatstID_OrderDetail();
        }
        catch(Exception e) {
            result = 0;
        }
        return result;
    }

}
