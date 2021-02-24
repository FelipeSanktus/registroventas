package avla.registroventas.repositorys;


import avla.registroventas.entitys.Product;
import avla.registroventas.entitys.ProductHistory;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Collection;


public interface UserProductHistoryRepository extends JpaRepository<ProductHistory,Long> {
    ArrayList<ProductHistory> findAllByUserId(Long id,Pageable pageable);

}
