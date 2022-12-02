package dungnt.ptit.myspringsocial.controller.api;

import dungnt.ptit.myspringsocial.config.oauth2.UserPrincipal;
import dungnt.ptit.myspringsocial.config.security.jwt.TokenProvider;
import dungnt.ptit.myspringsocial.controller.response.ResponseBodyDto;
import dungnt.ptit.myspringsocial.controller.response.enums.AuthProvider;
import dungnt.ptit.myspringsocial.controller.response.enums.impl.ResponseCodeEnum;
import dungnt.ptit.myspringsocial.pojo.model.User;
import dungnt.ptit.myspringsocial.pojo.payload.LoginRequest;
import dungnt.ptit.myspringsocial.pojo.payload.SignUpRequest;
import dungnt.ptit.myspringsocial.pojo.response.LoginResponse;
import dungnt.ptit.myspringsocial.repository.UserRepository;
import dungnt.ptit.myspringsocial.ulti.error.BadRequestException;
import dungnt.ptit.myspringsocial.ulti.i18n.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@Controller
@RequestMapping("/auth")
public class AuthController extends BaseController{
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MessageService messageService;

    @PostMapping("/login")
    public @ResponseBody ResponseEntity authenticateUser(@Valid @RequestBody LoginRequest request, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BadRequestException(messageService.getMessage(bindingResult.getFieldError().getDefaultMessage()));
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.createToken(authentication);

        ResponseBodyDto responseBodyDto = new ResponseBodyDto<>(new LoginResponse(token), ResponseCodeEnum.R_200);
        return executionSuccess(responseBodyDto);
    }

    @PostMapping("/signup")
    public @ResponseBody ResponseEntity registerUser(@Valid @RequestBody SignUpRequest signUpRequest, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BadRequestException(messageService.getMessage(bindingResult.getFieldError().getDefaultMessage()));
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new BadRequestException(messageService.getMessage("user.email.existed"));
        }

        // Creating user's account
        User user = new User();
        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(signUpRequest.getPassword());
        user.setProvider(AuthProvider.local);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User result = userRepository.save(user);
        ResponseBodyDto responseBodyDto = new ResponseBodyDto<>(result,ResponseCodeEnum.R_201);
        return executionCreateSuccess(responseBodyDto);
    }

}
