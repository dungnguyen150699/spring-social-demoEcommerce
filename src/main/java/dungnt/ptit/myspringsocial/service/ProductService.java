package dungnt.ptit.myspringsocial.service;

import dungnt.ptit.myspringsocial.pojo.model.Product;
import dungnt.ptit.myspringsocial.pojo.response.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;

public interface ProductService {
    Page<ProductDTO> searchAlByCondition(String name, String type, Pageable pageable);
    String showPage(Model model, String name, String type, Pageable pageable);
    String addCart(Model model, Long id, HttpSession session);

    // -- ADMIN
    String showADMINPage(Model model,String name, String type, Pageable pageable);
    String showInsertForm(Model model);
    String showUpdateForm(Model model, Long id);
    String doInsert(Model model, String type , String name, MultipartFile img, double price, int count) throws IOException;
    String doUpdate(Long id,String type ,String name, MultipartFile img,double price, int count) throws IOException;
    String doDelete(Long id);
}
