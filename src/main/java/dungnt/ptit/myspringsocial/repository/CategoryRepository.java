package dungnt.ptit.myspringsocial.repository;

import dungnt.ptit.myspringsocial.pojo.model.Category;
import dungnt.ptit.myspringsocial.pojo.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

//@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{
    Category findByNameCategory(String type);
}
