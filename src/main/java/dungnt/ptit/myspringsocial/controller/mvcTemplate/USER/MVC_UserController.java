package dungnt.ptit.myspringsocial.controller.mvcTemplate.USER;

import dungnt.ptit.myspringsocial.config.oauth2.UserPrincipal;
import dungnt.ptit.myspringsocial.controller.api.BaseController;
import dungnt.ptit.myspringsocial.controller.response.enums.AuthProvider;
import dungnt.ptit.myspringsocial.pojo.model.Role;
import dungnt.ptit.myspringsocial.pojo.model.User;
import dungnt.ptit.myspringsocial.repository.UserRepository;
import dungnt.ptit.myspringsocial.service.UserService;
import dungnt.ptit.myspringsocial.ulti.error.NotFoundException;
import org.springframework.beans.NotReadablePropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.*;

import static dungnt.ptit.myspringsocial.ulti.Constant.ROLE.TYPE.*;
@Controller
@RequestMapping(value = "/mvc")
public class MVC_UserController extends BaseController {
   @Autowired
   private UserService userService;

    @RequestMapping("/login")
    public String loginView(Model model,HttpSession session) {
        return userService.loginView(model, session);
    }

    @RequestMapping("/doLogin")
    public String doLogin(HttpSession session,@ModelAttribute("user") User user){
       return userService.doLogin(session, user);
    }

    @RequestMapping("/register")
    public String registerView(Model model){
        return userService.registerView(model);
    }

    @RequestMapping(value = "/doRegister")
    public String doRegister(Model model, @ModelAttribute("user") User user) {
       return userService.doRegister(model, user);
    }
}
