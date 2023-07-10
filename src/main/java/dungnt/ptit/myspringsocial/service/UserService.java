package dungnt.ptit.myspringsocial.service;

import dungnt.ptit.myspringsocial.pojo.model.User;
import dungnt.ptit.myspringsocial.pojo.response.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpSession;

public interface UserService extends UserDetailsService {
    // -- USER
    String loginView(Model model, HttpSession session);
    String doLogin(HttpSession session,User user);
    String registerView(Model model);
    String doRegister(Model model, User user);
    // -- ADMIN
    String userManagementView(Model model, Long seePassWordId, String email,
                              String type, Pageable pageable);
    String userManagementDelete(Model model, Long idDelete);

    Page<UserDTO> getAllByCondition(String email, String type, Pageable pageable);

}
