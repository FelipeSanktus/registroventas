package avla.registroventas.repositorys;


import avla.registroventas.entitys.ProductHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProductHistoryRepository extends JpaRepository<ProductHistory,Long> {

}
