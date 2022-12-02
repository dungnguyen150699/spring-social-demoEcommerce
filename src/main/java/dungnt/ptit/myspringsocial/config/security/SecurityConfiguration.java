package dungnt.ptit.myspringsocial.config.security;

import dungnt.ptit.myspringsocial.config.oauth2.CustomOAuth2UserService;
import dungnt.ptit.myspringsocial.config.oauth2.OAuth2AuthenticationFailureHandler;
import dungnt.ptit.myspringsocial.config.oauth2.OAuth2AuthenticationSuccessHandler;
import dungnt.ptit.myspringsocial.config.security.exception.CustomAcessDeniedHandler;
import dungnt.ptit.myspringsocial.config.security.exception.CustomAuthencationEntryPoint;
import dungnt.ptit.myspringsocial.config.security.exception.CustomAuthenticationFailureHandler;
import dungnt.ptit.myspringsocial.config.security.filter.CustomAuthorFilter;
import dungnt.ptit.myspringsocial.config.security.jwt.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfiguration {
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;
    @Autowired
    private OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    @Autowired
    private OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

    public static final String[] WHITELIST = {
            "/auth/**", "/oauth2/**",
            "/","/mvc/login","/mvc/doLogin","/mvc/register","/mvc/doRegister","/mvc/index","/mvc",
            "/**/css/**","/**/js/**","/**/images/**","/**/static/**","/**/webfonts/**"
    };

    @Bean
    PasswordEncoder getBeanPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()
                .authorizeHttpRequests((auth) -> {
                    auth.antMatchers(WHITELIST).permitAll();
                    auth.antMatchers("/admin","/delete/**").hasAnyAuthority("ADMIN"); // check link for only admin -> link role user will not work
                    auth.antMatchers("/mvc/**").hasAnyAuthority("ADMIN","USER");
                    auth.anyRequest().authenticated();
                })
                .exceptionHandling()
                    .accessDeniedHandler(acessDeniedHandler()) // 403 User đăng nhập thành công nhưng đòi truy cập quyền khác User
                    .authenticationEntryPoint(authenticationEntryPoint()) // 401
                    .and();

        http.cors().configurationSource(corsConfigurationSource());
        // Set session management to stateless // Bỏ state_less nếu dùng mô hình mvc + thymeleaf :) vì nó ko dùng token
//        http
//                .sessionManagement()
//                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                    .and();
        // Login
        http
                .formLogin()
                    .loginPage("/mvc/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/mvc/doLogin",true)
                    .and()
                    .logout()
                        .logoutUrl("/mvc/logout")
                        .logoutSuccessUrl("/mvc/login")
                    .and()
//                    .disable()
                .oauth2Login()
                .authorizationEndpoint()
                    .baseUri("/oauth2/authorize")
//                    .authorizationRequestRepository(cookieAuthorizationRequestRepository())
                    .and()
                .redirectionEndpoint()
                    .baseUri("/oauth2/callback/*")
                    .and()
                .userInfoEndpoint()
                    .userService(customOAuth2UserService)
                    .and()
                .successHandler(oAuth2AuthenticationSuccessHandler)
                .failureHandler(oAuth2AuthenticationFailureHandler)
                .and();
        ;

        // Filter --> 1 cách thay thế @Autowired tránh cricle rất hay nhé
        http.addFilterBefore(new CustomAuthorFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);
        return http.httpBasic(withDefaults()).build(); // ** nhờ dòng này nó trả về SecurityFilterChain đó
        // function interface trả về chính thằng HttpSecurity
        // nếu ko thì nó trả ra thằng HTTPBasic relam gì ấy
    }

    // Cấu hình các phương thức lỗi
    // 1 Xử lý khi login thất bại (Form login của Spring Security)
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }
    // 2 Xử lý khi người dùng là user đăng nhập vào quyền của người khác vd admin 403
    @Bean
    public AccessDeniedHandler acessDeniedHandler() {
        return new CustomAcessDeniedHandler();
    }
    // 3 Xử lý khi người dùng chưa đăng nhập mà trỏ đến project cũng là 403
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        return new CustomAuthencationEntryPoint();
    }

    // Config Cors
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("https://example.com"));
        configuration.setAllowedMethods(Arrays.asList(HttpMethod.GET.name(),"POST","PUT","DELETE","OPTIONS"));
        configuration.addAllowedMethod(CorsConfiguration.ALL);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
