package avla.registroventas.services;

import avla.registroventas.entitys.User;
import avla.registroventas.errors.ResourceNotFoundException;
import avla.registroventas.repositorys.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.EditorKit;

@CrossOrigin(origins = "*")
@RestController

public class UserService {
    @Autowired
    UserRepository userRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/user/new")
    public User createUser(@RequestBody User user) throws Exception {
        try{
            return userRepository.save(user);
        }catch (Exception e){
            throw new Exception(e);
        }
    }



}
