package dungnt.ptit.myspringsocial.config.security;

import dungnt.ptit.myspringsocial.config.oauth2.UserPrincipal;
import dungnt.ptit.myspringsocial.pojo.model.Role;
import dungnt.ptit.myspringsocial.pojo.model.User;
import dungnt.ptit.myspringsocial.repository.UserRepository;
import dungnt.ptit.myspringsocial.ulti.AES_EncryptDecypt;
import dungnt.ptit.myspringsocial.ulti.i18n.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class CustomAuthenticationManager implements AuthenticationManager {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageService messageService;
    @Autowired
    private AES_EncryptDecypt encryptDecypt;
//    private final PasswordEncoder passwordEncoder;

//    public CustomAuthenticationManager(PasswordEncoder passwordEncoder){
//        this.passwordEncoder = passwordEncoder;
//    }

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        String username = auth.getName();
        String password = auth.getCredentials().toString();
        User user = userRepository.findByEmail(username).orElseThrow(() -> new InternalAuthenticationServiceException(messageService.getMessage("user.notfound.email") + ": " + username ));
        if(!password.equals(encryptDecypt.decryptUser(user.getPassword())))  throw new InternalAuthenticationServiceException(messageService.getMessage("user.invalid.password") + " for: " + username);

        // add Role Authority
        Set<Role> roles = user.getRoles();
        List<GrantedAuthority> grantedAuths = new ArrayList<>();
        roles.stream().forEach(r -> grantedAuths.add(new SimpleGrantedAuthority(r.getName()) ));

        // Bước này xem lại UserDetail (thật ra bước này thay thế UserDetail thôi)
        UserDetails authenticationToken = UserPrincipal.create(user);
//         chú ý bước này thiếu check hết hạn tài khoản và mấy thứ lấy từ db kia ra nhé( isEnabled, accountNonExpired,...)
        return new UsernamePasswordAuthenticationToken(authenticationToken,password, grantedAuths);
    }
}
