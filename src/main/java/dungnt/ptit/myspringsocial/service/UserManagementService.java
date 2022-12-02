package dungnt.ptit.myspringsocial.service;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

public interface UserManagementService {
    String managementUserView(Model model, HttpSession session,Long userId);
}
