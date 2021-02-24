package avla.registroventas.services;


import avla.registroventas.entitys.Product;
import avla.registroventas.entitys.ProductHistory;
import avla.registroventas.repositorys.ProductRepository;
import avla.registroventas.repositorys.UserProductHistoryRepository;
import avla.registroventas.repositorys.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
@RestController
public class ProductHistoryService {



    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserProductHistoryRepository userProductHistoryRepository;


    @GetMapping("/user/{id}/history")
    public List<ProductHistory> getUserHistory(@PathVariable(value = "id") Long userId, Pageable pageable) {
        return userProductHistoryRepository.findAllByUserId(userId,pageable);
    }

    @GetMapping("/user/{id}/history/all")
        public List<ProductHistory> getAllUserHistory(@PathVariable(value = "id") Long userId) {
        return userProductHistoryRepository.findAllByUserId(userId);
    }

}
