package avla.registroventas.services;


import avla.registroventas.entitys.Product;
import avla.registroventas.entitys.ProductHistory;
import avla.registroventas.repositorys.ProductRepository;
import avla.registroventas.repositorys.UserProductHistoryRepository;
import avla.registroventas.repositorys.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@CrossOrigin(origins = "*")
@RestController
public class ProductHistoryService {



    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserProductHistoryRepository userProductHistoryRepository;


    @GetMapping("/user/{id}/history")
        public Collection<ProductHistory> getUserHistory(@PathVariable(value = "id") Long userId) {
        List<Product> userProducts = productRepository.findAllProductsByUserId(userId);
        Collection<ProductHistory> userHistory = new ArrayList<>();
        for (Product product:userProducts) {
            Collection<ProductHistory> list = userProductHistoryRepository.findAllByProduct(product);
            userHistory.addAll(list);
        }
        return userHistory;
    }
}
