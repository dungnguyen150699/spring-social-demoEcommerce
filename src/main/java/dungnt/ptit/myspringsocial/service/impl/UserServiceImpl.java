package dungnt.ptit.myspringsocial.service.impl;

import dungnt.ptit.myspringsocial.config.oauth2.UserPrincipal;
import dungnt.ptit.myspringsocial.controller.response.enums.AuthProvider;
import dungnt.ptit.myspringsocial.pojo.model.Role;
import dungnt.ptit.myspringsocial.pojo.model.User;
import dungnt.ptit.myspringsocial.pojo.response.dto.UserDTO;
import dungnt.ptit.myspringsocial.repository.UserRepository;
import dungnt.ptit.myspringsocial.service.UserService;
import dungnt.ptit.myspringsocial.ulti.AES_EncryptDecypt;
import dungnt.ptit.myspringsocial.ulti.DataUntil;
import dungnt.ptit.myspringsocial.ulti.error.BadRequestException;
import dungnt.ptit.myspringsocial.ulti.mapper.CommonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.thymeleaf.util.DateUtils;

import javax.servlet.http.HttpSession;

import java.util.*;
import java.util.stream.Collectors;

import static dungnt.ptit.myspringsocial.ulti.Constant.ROLE.TYPE.ADMIN;
import static dungnt.ptit.myspringsocial.ulti.Constant.ROLE.TYPE.USER;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
    @Autowired
    private AES_EncryptDecypt encryptDecypt;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with email : " + email)
                );

        return UserPrincipal.create(user);
    }

    @Override
    public String loginView(Model model, HttpSession session) {
        session.removeAttribute(USER.code);
        model.addAttribute(USER.code,new User());
        return "LoginForm";
    }

    @Override
    public String doLogin(HttpSession session, User user) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("name", userRepository.findByEmail(user.getEmail()).get().getName());
            userPrincipal.setAttributes(attributes);
            session.setAttribute("userPrincipal", userPrincipal);
            List<GrantedAuthority> authoritys = (List<GrantedAuthority>) userPrincipal.getAuthorities();
            if (authoritys.stream().anyMatch(a -> ADMIN.name().equals(a.getAuthority())))
                return "redirect:/mvc/admin";
            else return "redirect:/mvc";
        }catch (Exception exception){
            log.error(exception.getMessage(),exception);
            return "redirect:/mvc/login?error="+ exception.getMessage();
        }
    }

    @Override
    public String registerView(Model model) {
        User user = new User();
        model.addAttribute(USER.code,user);
        return "RegisterForm";
    }

    @Override
    public String doRegister(Model model, User user) {
        User u = userRepository.findByEmail(user.getEmail()).orElse(null);
        if (u != null) {
            String str = "User has exist";
            model.addAttribute("meg", str);
            return "RegisterForm";
        } else {
            String str = "Congratulations on your successful registration --> You will have admin Role";
            Set<Role> roles = new HashSet();
            Role r = new Role();
            r.setId(1);
            r.setName("ADMIN");
            roles.add(r);
//			-----------
            user.setRoles(roles);
            user.setPassword(encryptDecypt.encryptUser(user.getPassword()));
            user.setProvider(AuthProvider.local);
            userRepository.save(user);
            model.addAttribute("meg", str);
            return "RegisterForm";
        }
    }

    @Override
    public String userManagementView(Model model, Long seePassWordId, String email,
                                     String type, Pageable pageable) {
        Page<UserDTO> pageResult = getAllByCondition(email,type,pageable);
        List<UserDTO> listDTO = pageResult.getContent().stream().map(item -> {
            if(item.getId().equals(seePassWordId)){
                item.setPassword(encryptDecypt.decryptUser(item.getPassword()));
                return item;
            }else return item;
        }).collect(Collectors.toList());
        model.addAttribute("page",new PageImpl<UserDTO>(listDTO,pageable, pageResult.getTotalElements()));
        return "ADMIN/index";
    }

    @Override
    public String userManagementDelete(Model model, Long idDelete) {
        User user = userRepository.findById(idDelete).orElseThrow(() -> new BadRequestException("user.id.notfound"));
        userRepository.deleteById(idDelete);
        return "redirect:/mvc/admin";
    }

    @Override
    public Page<UserDTO> getAllByCondition(String email, String type, Pageable pageable) {
        email = DataUntil.checkNullOrEmpty(email) ? null : email;
        type = DataUntil.checkNullOrEmpty(type) ? null : type;

        Page<User> page = userRepository.getAllByCondition(email, type, pageable);
        return CommonMapper.toPage(page,UserDTO.class,pageable);
    }
}
