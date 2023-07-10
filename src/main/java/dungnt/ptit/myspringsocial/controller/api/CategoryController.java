package dungnt.ptit.myspringsocial.controller.api;

import dungnt.ptit.myspringsocial.controller.response.ResponseBodyDto;
import dungnt.ptit.myspringsocial.controller.response.enums.ResponseCode;
import dungnt.ptit.myspringsocial.controller.response.enums.impl.ResponseCodeEnum;
import dungnt.ptit.myspringsocial.pojo.response.dto.CategoryDTO;
import dungnt.ptit.myspringsocial.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController extends BaseController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/searchAll")
    public ResponseEntity searchAll(){
        List<CategoryDTO> data = categoryService.getAllCategory();
        long total = Long.parseLong(data.size() + "");
        ResponseBodyDto res = new ResponseBodyDto<CategoryDTO>(data, ResponseCodeEnum.R_200,total);
        return executionSuccess(res);
    }
}
