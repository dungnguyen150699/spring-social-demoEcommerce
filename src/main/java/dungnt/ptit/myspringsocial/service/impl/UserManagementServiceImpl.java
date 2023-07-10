package dungnt.ptit.myspringsocial.service.impl;

import dungnt.ptit.myspringsocial.pojo.model.User;
import dungnt.ptit.myspringsocial.service.UserManagementService;
import dungnt.ptit.myspringsocial.ulti.AES_EncryptDecypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

import static dungnt.ptit.myspringsocial.ulti.Constant.ROLE.TYPE.USER;

@Service
public class UserManagementServiceImpl implements UserManagementService {
    @Autowired
    private AES_EncryptDecypt aes_encryptDecypt;

    @Override
    public String managementUserView(Model model, HttpSession session, Long userId) {
        session.removeAttribute(USER.code);
        model.addAttribute(USER.code,new User());
        return "LoginForm";
    }
}
