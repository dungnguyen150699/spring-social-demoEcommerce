package dungnt.ptit.myspringsocial.repository;

import dungnt.ptit.myspringsocial.pojo.model.Product;
import dungnt.ptit.myspringsocial.pojo.response.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "SELECT p.* FROM products p INNER JOIN category c ON p.category_id = c.id\n" +
            "WHERE (?1 IS NULL OR p.name like %?1%) AND (?2 is null or c.name = ?2 )",
            countQuery = "SELECT count(1) FROM products p INNER JOIN category c ON p.category_id = c.id\n" +
                    "WHERE (?1 IS NULL OR p.name like %?1%) AND (?2 is null or c.name = ?2 )",nativeQuery = true)
    Page<Product> searchAllByCodition(String name, String type, Pageable pageable);

    Optional<Product> findByName(String name);
}
