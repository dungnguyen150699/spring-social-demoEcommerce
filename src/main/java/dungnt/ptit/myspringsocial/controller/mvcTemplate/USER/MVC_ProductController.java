package dungnt.ptit.myspringsocial.controller.mvcTemplate.USER;

import dungnt.ptit.myspringsocial.pojo.model.Product;
import dungnt.ptit.myspringsocial.pojo.response.dto.ProductDTO;
import dungnt.ptit.myspringsocial.service.ProductService;
import dungnt.ptit.myspringsocial.ulti.DataUntil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/mvc/shop")
public class MVC_ProductController {
    @Autowired
    private ProductService productService;

    @RequestMapping(value = "")
    public String home(Model model,Pageable pageable){
        return showPage(model,null,null, pageable);
    }

    @RequestMapping(value = "/search-all-by-codition")
    public String showPage(Model model,@RequestParam(value = "name",required = false) String name,
                           @RequestParam(value = "type",required = false) String type, Pageable pageable){
        name = DataUntil.checkNullOrEmpty(name) ? null : name;
        type = DataUntil.checkNullOrEmpty(type) ? null : type;
        return productService.showPage(model, name, type, pageable);
    }

    @RequestMapping(value = "/addcart/{id}")
    public String addCart(Model model, @PathVariable(name="id") Long id, HttpSession session){
        return productService.addCart(model, id, session);
    }

}
