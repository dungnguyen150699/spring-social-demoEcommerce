package dungnt.ptit.myspringsocial.controller.mvcTemplate.ADMIN;

import dungnt.ptit.myspringsocial.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static dungnt.ptit.myspringsocial.ulti.DataUntil.checkNullOrEmpty;

@Controller
@RequestMapping(value = "/mvc/admin/product")
public class MVC_ADMIN_ProductController {
    @Autowired
    private ProductService productService;

    @RequestMapping("")
    public String productManagementView(Model model,@RequestParam(name="name",required = false) String name,
                                        @RequestParam(name="type",required = false) String type, Pageable pageable) {
        name = checkNullOrEmpty(name) ? null : name;
        type = checkNullOrEmpty(type) ? null : type;
        return productService.showADMINPage(model,name,type,pageable);
    }

    @RequestMapping("/formInsertProduct")
    public String formProductManagementView(Model model) {
        return productService.showInsertForm(model);
    }

    @RequestMapping("/formUpdateProduct/{id}")
    public String formUpdateProductManagementView(Model model,@PathVariable(value = "id") Long id) {
        return productService.showUpdateForm(model,id);
    }

    @RequestMapping("/addProduct")
    public String addProduct(Model model,
                             @RequestParam(name="name") String name,
                             @RequestParam(name="type") String type,
                             @RequestParam(name="img") MultipartFile img,
                             @RequestParam(name="price") double price,
                             @RequestParam(name="count") int count) throws IOException {
        return productService.doInsert(model,type, name, img, price, count);
    }

    @RequestMapping("/update")
    public String update(@RequestParam(name="id") Long id,
                         @RequestParam(name="name") String name,
                         @RequestParam(name="type") String type,
                         @RequestParam(name="img") MultipartFile img,
                         @RequestParam(name="price") double price,
                         @RequestParam(name="count") int count
                         ) throws IOException {
        return productService.doUpdate(id,type, name, img, price, count);
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable(name="id") Long id){
        return productService.doDelete(id);
    }
}
