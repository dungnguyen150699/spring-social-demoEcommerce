package dungnt.ptit.myspringsocial.controller.api;

import dungnt.ptit.myspringsocial.controller.response.ResponseBodyDto;
import dungnt.ptit.myspringsocial.ulti.i18n.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public abstract class BaseController {
    @Autowired
    private MessageService messageService;

    public ResponseEntity executionSuccess(ResponseBodyDto responseBodyDto){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        responseBodyDto.setMessage(messageService.getMessage("execution.handle.success"));
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(responseBodyDto);
    }

    public ResponseEntity executionCreateSuccess(ResponseBodyDto responseBodyDto){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        responseBodyDto.setMessage(messageService.getMessage("execution.handle.create.success"));
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(responseBodyDto);
    }

    public ResponseEntity executionDeleteSuccess(ResponseBodyDto responseBodyDto){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        responseBodyDto.setMessage(messageService.getMessage("execution.handle.create.success"));
        return ResponseEntity.status(HttpStatus.ACCEPTED).headers(headers).body(responseBodyDto);
    }


}
