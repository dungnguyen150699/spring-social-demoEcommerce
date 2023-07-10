package dungnt.ptit.myspringsocial.service;

import dungnt.ptit.myspringsocial.pojo.model.Category;
import dungnt.ptit.myspringsocial.pojo.response.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getAllCategory();
}
