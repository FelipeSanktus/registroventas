package avla.registroventas.repositorys;

import avla.registroventas.entitys.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    Page<Product> findByUserId(Long userId, Pageable pageable);
    ArrayList<Product> findAllByUserId(Long userId);
    List<Product> findAllProductsByUserId(Long userId);
    List<Product> findAllByStatus(int stauts);

}
