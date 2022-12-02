package dungnt.ptit.myspringsocial.controller.api;

import dungnt.ptit.myspringsocial.config.oauth2.UserPrincipal;
import dungnt.ptit.myspringsocial.controller.response.ResponseBodyDto;
import dungnt.ptit.myspringsocial.controller.response.enums.impl.ResponseCodeEnum;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @GetMapping("/infor")
    public ResponseEntity infor(@AuthenticationPrincipal UserPrincipal infor){
        ResponseBodyDto responseBodyDto = new ResponseBodyDto<>()
                .setCode(ResponseCodeEnum.R_200)
                .setItem(infor);
        return executionSuccess(responseBodyDto);
    }
}
