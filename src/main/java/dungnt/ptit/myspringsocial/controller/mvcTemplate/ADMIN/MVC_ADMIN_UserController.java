package dungnt.ptit.myspringsocial.controller.mvcTemplate.ADMIN;

import dungnt.ptit.myspringsocial.controller.api.BaseController;
import dungnt.ptit.myspringsocial.pojo.payload.userPayLoad.UserManagementSearch;
import dungnt.ptit.myspringsocial.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/mvc/admin")
public class MVC_ADMIN_UserController extends BaseController {
   @Autowired
   private UserService userService;

    @RequestMapping({"","/users"})
    public String userManagementView(Model model,
                                     @RequestParam(value = "id",required = false) Long id,
                                     @ModelAttribute(value = "searchUserManagement") UserManagementSearch userManagementSearch,
                                     Pageable pageable) {
        return userService.userManagementView(model,id,userManagementSearch.getEmail(),
                userManagementSearch.getType(),pageable);
    }

    @RequestMapping("/users/delete/{id}")
    public String userManagementDelete(Model model,@PathVariable("id") Long id){
        return userService.userManagementDelete(model, id);
    }

}
