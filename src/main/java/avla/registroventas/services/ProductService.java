package avla.registroventas.services;

import avla.registroventas.entitys.Product;
import avla.registroventas.entitys.User;
import avla.registroventas.entitys.ProductHistory;
import avla.registroventas.errors.ResourceNotFoundException;
import avla.registroventas.repositorys.ProductRepository;
import avla.registroventas.repositorys.UserProductHistoryRepository;
import avla.registroventas.repositorys.UserRepository;
import avla.registroventas.security.Parametros;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*")
@RestController
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserProductHistoryRepository userProductHistoryRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/{id}/products")
    public Page<Product> getAllProductsByUserId(@PathVariable (value = "id") Long userId, Pageable pageable) {
        return  productRepository.findByUserId(userId, pageable);
    }

    @GetMapping("user/resume/products/{status}")
    public List<Product> getAllProductsByUserId(@PathVariable (value = "status") int status) {
        return  productRepository.findAllByStatus(status);
    }


    @PostMapping("/user/{id}/products")
    public Product createProduct(@PathVariable (value = "id") Long userId, @RequestBody Product product) {
       try{
            User user = userRepository.findUserById(userId);
            if(user != null){
                product.setUser(user);
                product.setStatus(0);
                Product saveProduct = productRepository.save(product);
                ProductHistory history = new ProductHistory(product, Parametros.CREATE_RESOURCE+saveProduct.getId());
                userProductHistoryRepository.save(history);
                return product;
            }
            else{
                throw new ResourceNotFoundException("Resource not found");
            }
       }catch (ResourceNotFoundException e){
           throw new ResourceNotFoundException("Resource not found");
       }
    }
}
