package dungnt.ptit.myspringsocial.controller.mvcTemplate.USER;

import dungnt.ptit.myspringsocial.pojo.model.Role;
import dungnt.ptit.myspringsocial.pojo.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

import static dungnt.ptit.myspringsocial.ulti.Constant.ROLE.TYPE.USER;

@Controller
@RequestMapping("/")
public class MVC_IndexController {
    @RequestMapping({"","mvc"})
    public String indexForm(HttpSession session){
        return "/SHOP/index.html";
    }
    @RequestMapping("/register")
    public String registerView(Model model){
        User user = new User();
        model.addAttribute(USER.code,user);
        return "RegisterForm";
    }
}
