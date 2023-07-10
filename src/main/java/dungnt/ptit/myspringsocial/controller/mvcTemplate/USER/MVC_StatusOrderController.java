package dungnt.ptit.myspringsocial.controller.mvcTemplate.USER;

import dungnt.ptit.myspringsocial.config.oauth2.UserPrincipal;
import dungnt.ptit.myspringsocial.pojo.model.Order;
import dungnt.ptit.myspringsocial.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping(value="/mvc/order")
public class MVC_StatusOrderController {
	@Autowired
	private OrderService os;
	
	@RequestMapping(value="")
	public String HandleOrder(Model model,HttpSession session) {
		UserPrincipal u = (UserPrincipal) session.getAttribute("userPrincipal");
		List<Order> orders =  os.findAllByUserID(u.getId());
		model.addAttribute("listOrder",orders);
		System.out.println(orders.size() == 0 ? "Approval null" : "Approval: " + os.findAllByUserID(u.getId()).get(0).getApproved());
		return "SHOP/statusorder";
	}
	
	@RequestMapping(value="/orderDESC")
	public String orderAccepted(Model model) {
		model.addAttribute("listOrder", os.findDESC());
		return "/mvc/order";
	}
	
	@RequestMapping(value="/orderASC")
	public String orderRejected(Model model) {
		model.addAttribute("listOrder", os.findASC());
		return "/mvc/order";
	}
	
	@RequestMapping(value="/reject/{id}")
	public String orderReject(@PathVariable(name="id") Long id) {
		System.out.println("rejecting");
		Order o = os.findById(id);
		o.setApproved(-1);
		os.save(o);
		return "redirect:/mvc/order";
	}
}
