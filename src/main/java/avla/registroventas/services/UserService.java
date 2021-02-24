package avla.registroventas.services;

import avla.registroventas.entitys.User;
import avla.registroventas.errors.ResourceNotFoundException;
import avla.registroventas.repositorys.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.EditorKit;

@CrossOrigin(origins = "*")
@RestController

public class UserService {
    @Autowired
    UserRepository userRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/user/new")
    public User createProduct(@RequestBody User user) throws Exception {
        try{
            return userRepository.save(user);
        }catch (Exception e){
            throw new Exception(e);
        }
    }
}
