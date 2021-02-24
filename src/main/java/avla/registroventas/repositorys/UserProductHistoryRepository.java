package avla.registroventas.repositorys;


import avla.registroventas.entitys.Product;
import avla.registroventas.entitys.ProductHistory;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;


public interface UserProductHistoryRepository extends JpaRepository<ProductHistory,Long> {
    List<ProductHistory> findAllByUserId(Long id,Pageable pageable);
    List<ProductHistory> findAllByUserId(Long id);

}
