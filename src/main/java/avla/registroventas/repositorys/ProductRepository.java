package avla.registroventas.repositorys;

import avla.registroventas.entitys.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {

}
