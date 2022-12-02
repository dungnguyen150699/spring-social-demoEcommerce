package dungnt.ptit.myspringsocial.ulti.error;

import dungnt.ptit.myspringsocial.controller.response.ResponseBodyDto;
import dungnt.ptit.myspringsocial.controller.response.enums.impl.ResponseCodeEnum;
import dungnt.ptit.myspringsocial.ulti.i18n.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//@RestControllerAdvice
@RestControllerAdvice
@Slf4j
public class ApiExceptionHandler {
    @Autowired
    private MessageService messageService;

    /**
     * Tất cả các Exception không được khai báo sẽ được xử lý tại đây
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity handleServerError(Exception ex) {
        log.error(ex.getMessage(),ex);
    //    --> Có thể dùng cái này thay thế
//        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
//        headers.add("Content-Type", "application/json; charset=utf-8");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseBodyDto res = new ResponseBodyDto()
                .setCode(ResponseCodeEnum.R_500)
                .setMessage(messageService.getMessage("R_500"));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).body(res);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity handleNotFoundException(Exception ex) {
        log.error(ex.getMessage(),ex);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseBodyDto res = new ResponseBodyDto()
                .setCode(ResponseCodeEnum.R_404)
                .setMessage(ex.getMessage().replaceAll("//s+"," "));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(headers).body(res);
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseEntity handleInternalAuthenticationServiceException(Exception ex) {
        log.error(ex.getMessage(),ex);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseBodyDto res = new ResponseBodyDto()
                .setCode(ResponseCodeEnum.R_401)
                .setMessage(ex.getMessage().replaceAll("//s+"," "));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).headers(headers).body(res);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity handleBadRequestException(Exception ex) {
        log.error(ex.getMessage(),ex);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseBodyDto res = new ResponseBodyDto()
                .setCode(ResponseCodeEnum.R_403)
                .setMessage(ex.getMessage().replaceAll("//s+"," "));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body(res);
    }
}
