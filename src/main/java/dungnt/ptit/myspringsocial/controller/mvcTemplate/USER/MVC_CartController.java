package dungnt.ptit.myspringsocial.controller.mvcTemplate.USER;

import dungnt.ptit.myspringsocial.pojo.model.Order;
import dungnt.ptit.myspringsocial.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.HashMap;

@Controller
@RequestMapping("/mvc/cart")
public class MVC_CartController {
    @Autowired
    private CartService cartService;

    @RequestMapping("")
    public String showOrderPage(Model model, HttpSession session) {
        return cartService.showPage(model, session);
    }

    @RequestMapping("/updatecart")
    public String updateorder(Model model, @ModelAttribute("order") Order order, HttpSession session) {
        return cartService.updateCart(model, order, session);
    }

    @RequestMapping("/checkout")
    public String checkout(Model model, HttpSession session) {
        return cartService.checkOutPage(model, session);
    }


    @RequestMapping("/approved_order")
    public String approved_order(Model model, @ModelAttribute("order") Order od, HttpSession session) {
       return cartService.approved_order(model,od,session);
    }

}
