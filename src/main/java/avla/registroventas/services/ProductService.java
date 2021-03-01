package avla.registroventas.services;

import avla.registroventas.entitys.Product;
import avla.registroventas.entitys.SoldResume;
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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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

    @GetMapping("/user/{userId}/product/{productId}")
    public Product getProductById(@PathVariable (value = "userId") Long userId,@PathVariable (value = "productId") Long productId){
        return productRepository.findProductByIdAndUserId(productId,userId);
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
                    int flag = 0;
                    if(!productEdited.getName().equals(product.getName())){
                        ProductHistory history = new ProductHistory(user,"Se ha cambiado el nombre del producto "+product.getId()+" de: '"+productEdited.getName()+"' a: '"+product.getName()+"' ");
                        productEdited.setName(product.getName());
                        userProductHistoryRepository.save(history);
                    }
                    if(productEdited.getPrice() != product.getPrice() && productEdited.getStatus() == 0){
                        ProductHistory history = new ProductHistory(user,"Se ha cambiado el precio del producto "+product.getId()+" de: '"+productEdited.getPrice()+"' a: '"+product.getPrice()+"' ");
                        productEdited.setPrice(product.getPrice());
                        userProductHistoryRepository.save(history);
                    }
                    if(!productEdited.getDescription().equals(product.getDescription())){
                        ProductHistory history = new ProductHistory(user,"Se ha cambiado la descripción del producto "+product.getId()+" de: '"+productEdited.getDescription()+"' a: '"+product.getDescription()+"' ");
                        productEdited.setDescription(product.getDescription());
                        userProductHistoryRepository.save(history);
                    }
                    if(productEdited.getStatus() != product.getStatus()){
                        if(product.getStatus() == 1){
                            ProductHistory history = new ProductHistory(user, Parametros.SOLD_RESOURCE+productEdited.getName());
                            userProductHistoryRepository.save(history);
                            flag = 1;
                        }
                        else if(product.getStatus() == 2){
                            ProductHistory history = new ProductHistory(user, Parametros.LOST_RESOURCE+productEdited.getName());
                            userProductHistoryRepository.save(history);
                            flag = 1;
                        }

                        productEdited.setStatus(product.getStatus());
                    }
                    if(flag == 1){
                        productEdited.setSaleDate();
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
    public SoldResume getAllSoldItemsByUserId(@PathVariable (value = "userid") Long userId) {
        List<Product> products =  productRepository.findAllByUserIdAndStatusOrderByPrice(userId, 1);
        SoldResume resume = new SoldResume(products);
        return resume;
    }

    @GetMapping("/user/{userid}/lost/items")
    public List<Product> getAllLostItemsByUserId(@PathVariable (value = "userid") Long userId) {
        return  productRepository.findAllByUserIdAndStatusOrderByPrice(userId, 2);
    }

    @GetMapping("/user/{userid}/sold/items/{date}")
    public SoldResume getAllByDateSale(@PathVariable (value = "userid") Long userId, @PathVariable(value = "date")  String filter) {
        if(filter.length() != 8){
            return null;
        }

        String newDate = filter.substring(0,4)+"/"+filter.substring(4,6)+"/"+filter.substring(6);
        List<Product> products =  productRepository.findAllBySaleDateAndUserId(newDate,userId);
        SoldResume soldResume = new SoldResume(products);
        return soldResume;
    }







}
