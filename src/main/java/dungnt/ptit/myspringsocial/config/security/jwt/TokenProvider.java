package dungnt.ptit.myspringsocial.config.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import dungnt.ptit.myspringsocial.config.oauth2.UserPrincipal;
import dungnt.ptit.myspringsocial.config.security.AppProperties;
import dungnt.ptit.myspringsocial.pojo.model.User;
import dungnt.ptit.myspringsocial.repository.UserRepository;
import dungnt.ptit.myspringsocial.ulti.error.NotFoundException;
import dungnt.ptit.myspringsocial.ulti.i18n.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TokenProvider {
    private static final Logger logger = LoggerFactory.getLogger(TokenProvider.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageService messageService;
    private final AppProperties appProperties;
    private final Algorithm algorithm;

    // Từ spring 4.3 nếu tiêm 1 @Config có thể bỏ qua từ khóa @Autowired
    public TokenProvider(AppProperties appProperties) {
        this.appProperties = appProperties;
        this.algorithm = Algorithm.HMAC256(appProperties.getAuth().getTokenSecret());
    }

    public String createToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Date expiryDate = new Date(System.currentTimeMillis() + appProperties.getAuth().getTokenExpirationMsec());
        List<String> authorities = authorities = userPrincipal.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
        String accessToken = JWT.create()
                .withSubject(userPrincipal.getEmail())
                .withExpiresAt(expiryDate)
                .withClaim("role",authorities)
                .sign(algorithm);
        return accessToken;
    }

    public UserPrincipal getUserFromToken(String token) {
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        String email = decodedJWT.getSubject();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException(messageService.getMessage("user.email.notFound")));
        return UserPrincipal.create(user);
    }

    public boolean validateToken(String authToken) {
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT ;
        Date dateExpire;
        try {
            decodedJWT = verifier.verify(authToken);
            dateExpire = decodedJWT.getExpiresAt();
            if(new Date().before(dateExpire)) return true;
        } catch (Exception exception){
            logger.error(exception.getMessage(),exception);
        }
        return false;
    }
}
