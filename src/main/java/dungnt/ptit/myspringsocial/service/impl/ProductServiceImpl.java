package dungnt.ptit.myspringsocial.service.impl;

import dungnt.ptit.myspringsocial.pojo.model.Category;
import dungnt.ptit.myspringsocial.pojo.model.OrderDetail;
import dungnt.ptit.myspringsocial.pojo.model.Product;
import dungnt.ptit.myspringsocial.pojo.response.dto.CategoryDTO;
import dungnt.ptit.myspringsocial.pojo.response.dto.ProductDTO;
import dungnt.ptit.myspringsocial.repository.CategoryRepository;
import dungnt.ptit.myspringsocial.repository.ProductRepository;
import dungnt.ptit.myspringsocial.service.ProductService;
import dungnt.ptit.myspringsocial.ulti.error.NotFoundException;
import dungnt.ptit.myspringsocial.ulti.mapper.CommonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Page<ProductDTO> searchAlByCondition(String name, String type, Pageable pageable) {
        Page<Product> page = productRepository.searchAllByCodition(name,type,pageable);
        return CommonMapper.toPage(page,ProductDTO.class,pageable);
    }

    @Override
    public String showPage(Model model, String name, String type, Pageable pageable) {
        Page<ProductDTO> productPage = searchAlByCondition(name,type,pageable);
        model.addAttribute("page",productPage);
        model.addAttribute("categoryList",CommonMapper.toList(categoryRepository.findAll(), CategoryDTO.class));
        model.addAttribute("type", "");
        return "SHOP/shop";
    }

    @Override
    public String addCart(Model model, Long id, HttpSession session) {
        Product pd = productRepository.findById(id).orElseThrow(() -> new NotFoundException("NotFound Product"));
        OrderDetail od = new OrderDetail();
        od.setProduct(pd);
        HashMap<Long, OrderDetail> listCart = (HashMap<Long, OrderDetail>) session.getAttribute("listCart");
        if (listCart == null) {
            listCart = new HashMap<Long, OrderDetail>();
            listCart.put(id, od);
            session.setAttribute("listCart", listCart);
        } else {
            listCart.put(id, od);
            session.setAttribute("listCart", listCart);
        }
        return "redirect:/mvc/shop";
    }

    @Override
    public String showADMINPage(Model model, String name, String type, Pageable pageable) {
        Page<ProductDTO> productPage = searchAlByCondition(name,type,pageable);
        model.addAttribute("page",productPage);
        model.addAttribute("categoryList",CommonMapper.toList(categoryRepository.findAll(), CategoryDTO.class));
        return "ADMIN/product";
    }

    @Override
    public String showInsertForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categoryList",CommonMapper.toList(categoryRepository.findAll(), CategoryDTO.class));
        model.addAttribute("mes", "insert");
        return "ADMIN/formProduct";
    }

    @Override
    public String showUpdateForm(Model model, Long id) {
        model.addAttribute("product", CommonMapper.map(productRepository.findById(id).orElse(null), ProductDTO.class));
        model.addAttribute("categoryList",CommonMapper.toList(categoryRepository.findAll(), CategoryDTO.class));
        model.addAttribute("mes", "update");
        return "ADMIN/formProduct";
    }

    @Override
    public String doInsert(Model model,String type,String name, MultipartFile img,
                            double price, int count
                           ) throws IOException {
        Product product = productRepository.findByName(name).orElse(null);
        Category category = categoryRepository.findByNameCategory(type);
        if(category == null) return "redirect:/mvc/admin/product/formInsertProduct?error=Category name dont exist!";
        if(product != null) return "redirect:/mvc/admin/product/formInsertProduct?error=Product name has exist!";

        product = new Product();
        product.setCategory(category);
        product.setName(name);
        product.setImg(product.toByteWrapper(img.getBytes()));
        product.setPrice(price);
        product.setCount(count);
        productRepository.save(product);
        return "redirect:/mvc/admin/product";

    }

    @Override
    public String doUpdate(Long id, String type, String name, MultipartFile img, double price, int count) throws IOException {
        Product product = productRepository.findById(id).orElse(null);
        Category category = categoryRepository.findByNameCategory(type);
        if(category == null) return "redirect:/mvc/admin/product/formUpdateProduct/"+id+"?error=Category notFound";
        if(product == null)  return "redirect:/mvc/admin/product/formUpdateProduct/"+id+"?error=Product notFound";

        product = new Product();
        product.setId(id);
        product.setCategory(category);
        product.setName(name);
        product.setImg(product.toByteWrapper(img.getBytes()));
        product.setPrice(price);
        product.setCount(count);
        productRepository.save(product);
        return "redirect:/mvc/admin/product";
    }

    @Override
    public String doDelete(Long id) {
        productRepository.deleteById(id);
        return "redirect:/mvc/admin/product";
    }

}
