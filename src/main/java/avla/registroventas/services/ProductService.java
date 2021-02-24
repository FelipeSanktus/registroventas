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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/user/{id}/products/all")
    public List<Product> getAllByUserId(@PathVariable (value = "id") Long userId) {
        return  productRepository.findAllByUserId(userId);
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
                ProductHistory history = new ProductHistory(user, Parametros.CREATE_RESOURCE+saveProduct.getName());
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

    @PutMapping("/user/{userid}/products/{id}")
    public Product updateProduct(@RequestBody Product product, @PathVariable Long userid,@PathVariable Long id) {
        User user = userRepository.findUserById(userid);
        return productRepository.findById(id)
                .map(productEdited -> {
                    productEdited.setStatus(product.getStatus());
                    if(productEdited.getStatus() == 1){
                        ProductHistory history = new ProductHistory(user, Parametros.SOLD_RESOURCE+productEdited.getName());
                        userProductHistoryRepository.save(history);
                    }
                    else if(productEdited.getStatus() == 2){
                        ProductHistory history = new ProductHistory(user, Parametros.LOST_RESOURCE+productEdited.getName());
                        userProductHistoryRepository.save(history);
                    }
                    return productRepository.save(productEdited);
                })
                .orElseThrow(() -> new ResourceNotFoundException("ProductId " + id + " not found"));
    }

    @DeleteMapping(value = "/user/{userId}/products/{id}")
    public ResponseEntity<Long> deletePost(@PathVariable Long id, @PathVariable Long userId) {
        User user = userRepository.findUserById(userId);
        if(user != null){
            Product deleteProduct = productRepository.findProductById(id);
            if(deleteProduct != null){
                ProductHistory history = new ProductHistory(user,Parametros.DELETE_RESOURCE+deleteProduct.getName());
                userProductHistoryRepository.save(history);
                productRepository.delete(deleteProduct);
                return new ResponseEntity<>(id, HttpStatus.OK);
            }
            else{
                throw new ResourceNotFoundException("Product id: "+id+ "Not Found");
            }
        }
        else{
            throw new ResourceNotFoundException("User id: "+userId+ "Not Found");
        }
    }

    @GetMapping("/user/{userid}/sold/items")
    public List<Product> getAllSoldItemsByUserId(@PathVariable (value = "userid") Long userId) {
        return  productRepository.findAllByUserIdAndStatusOrderByPrice(userId, 1);
    }

    @GetMapping("/user/{userid}/lost/items")
    public List<Product> getAllLostItemsByUserId(@PathVariable (value = "userid") Long userId) {
        return  productRepository.findAllByUserIdAndStatusOrderByPrice(userId, 2);
    }



}
