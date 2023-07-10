package dungnt.ptit.myspringsocial.service.impl;

import dungnt.ptit.myspringsocial.pojo.model.Category;
import dungnt.ptit.myspringsocial.pojo.response.dto.CategoryDTO;
import dungnt.ptit.myspringsocial.repository.CategoryRepository;
import dungnt.ptit.myspringsocial.service.CategoryService;
import dungnt.ptit.myspringsocial.ulti.mapper.CommonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<CategoryDTO> getAllCategory() {
        List<Category> listAll = categoryRepository.findAll();
        return CommonMapper.toList(listAll,CategoryDTO.class);
    }
}
