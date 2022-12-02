package dungnt.ptit.myspringsocial.controller.mvcTemplate.ADMIN;

import dungnt.ptit.myspringsocial.pojo.model.Order;
import dungnt.ptit.myspringsocial.repository.OrderRepository;
import dungnt.ptit.myspringsocial.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/mvc/admin/order")
public class MVC_ADMIN_MOrderController {
	@Autowired
	private OrderService os;
	@Autowired
	private OrderRepository or;
//	Show
	@RequestMapping(value="")
	public String HandleOrder(Model model) {
		model.addAttribute("listOrder", or.findAll());
		return "ADMIN/order";
	}
	
	@RequestMapping(value="/orderDESC")
	public String orderAccepted(Model model) {
		model.addAttribute("listOrder", os.findDESC());
		return "ADMIN/order";
	}
	
	@RequestMapping(value="/orderASC")
	public String orderRejected(Model model) {
		model.addAttribute("listOrder", os.findASC());
		return "ADMIN/order";
	}
	
//	Handle
	@RequestMapping(value="/accept/{id}")
	public String orderAccept(@PathVariable(name="id") Long id) {
		System.out.println("accepting");
		Order o = os.findById(id);
		o.setApproved(1);
		os.save(o);
		return "redirect:/mvc/admin/order";
	}
	
	@RequestMapping(value="/reject/{id}")
	public String orderReject(@PathVariable(name="id") Long id) {
		System.out.println("rejecting");
		Order o = os.findById(id);
		o.setApproved(-1);
		os.save(o);
		return "redirect:/mvc/admin/order";
	}
}
