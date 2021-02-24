package avla.registroventas.repositorys;

import avla.registroventas.entitys.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    Page<Product> findByUserId(Long userId, Pageable pageable);
    Product findProductById(Long id);
    List<Product> findAllByStatus(int stauts);
    List<Product> findAllByUserIdAndStatusOrderByPrice(Long userId,int status);


}
