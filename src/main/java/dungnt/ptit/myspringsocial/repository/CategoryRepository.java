package dungnt.ptit.myspringsocial.repository;

import dungnt.ptit.myspringsocial.pojo.model.Category;
import dungnt.ptit.myspringsocial.pojo.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

//@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{
    Category findByNameCategory(String type);
}
