package dungnt.ptit.myspringsocial.config.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import dungnt.ptit.myspringsocial.config.oauth2.UserPrincipal;
import dungnt.ptit.myspringsocial.config.security.exception.CustomAuthencationEntryPoint;
import dungnt.ptit.myspringsocial.config.security.jwt.TokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
public class CustomAuthorFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;

    public CustomAuthorFilter(TokenProvider tokenProvider){
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {;
        if (request.getServletPath().equals("/auth/login") || request.getServletPath().equals("/mvc/login") ||
        request.getServletPath().equals("/mvc/register")) {
            // Nếu user đang cố login ko làm gì cả tiếp tục gọi filter tiếp theo
            // ( ko chỉ có 1 loại bộ lọc nhé --> cái này là bộ lọc trước reponse)
            // Mặc định nếu ko login thành công thì Security sẽ bắn ra authentication rồi
            filterChain.doFilter(request, response);
        } else {
            if(!request.getServletPath().startsWith("/mvc")){
                // token request api
                String authriztionHeader = request.getHeader(AUTHORIZATION);
                if (authriztionHeader != null && authriztionHeader.startsWith("Bearer ")) { // Nếu thế cái Filter bên kia thừa mẹ rồi
                    authriztionHeader = authriztionHeader.substring("Bearer ".length());
                    if(!tokenProvider.validateToken(authriztionHeader)) {
                        ObjectMapper objectMapper = new ObjectMapper();
                        Map map = new HashMap<String, String>();
                        response.setStatus(HttpStatus.UNAUTHORIZED.value());
                        response.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                        response.getOutputStream()
                                .println(objectMapper.writeValueAsString(map));
                    }else {
                        try {
                            UserPrincipal userPrincipal = tokenProvider.getUserFromToken(authriztionHeader);
                            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
                            SecurityContextHolder.getContext().setAuthentication(authenticationToken); // Cái này cho pass nhờ vào token
                            filterChain.doFilter(request, response);
                        } catch (Exception exception) {
                            log.error("Error loggin in: {}", exception.getMessage());
                            //                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                            ObjectMapper objectMapper = new ObjectMapper();
                            Map map = new HashMap<String, String>();
                            map.put("error", exception.getMessage());
                            response.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                            response.getOutputStream()
                                    .println(objectMapper.writeValueAsString(map));
                        }
                    }
                } else {
                    filterChain.doFilter(request, response);
                }
            }else filterChain.doFilter(request,response);
        }
    }
}