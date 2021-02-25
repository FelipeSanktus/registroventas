package avla.registroventas.security;


import avla.registroventas.repositorys.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("usuarioService")
public class UsuarioService implements UserDetailsService {


    @Autowired
    UserRepository usuarioRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        avla.registroventas.entitys.User user = usuarioRepository.findUsuarioByUsername(username);
        return this.userBulder(user.getUsername(),new BCryptPasswordEncoder().encode(user.getPassword()),user.getRol()+" "+user.getId());
    }

    public List<GrantedAuthority> buildgrante(String rol){
        List<GrantedAuthority> l = new ArrayList();
        if(rol.equals("ADMIN")){
            l.add(new SimpleGrantedAuthority("ADMIN"));
        }
        else if(rol.equals("USER")){
            l.add(new SimpleGrantedAuthority("USER"));
        }
        else if(rol.equals("SUPERUSER")){
            l.add(new SimpleGrantedAuthority("SUPERUSER"));
        }
        else{
            l.add(new SimpleGrantedAuthority("USER"));
        }
        return l;
    }

    public User userBulder(String username,String password,String... roles){
        boolean enabled = true;
        boolean acountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean acountNonLocked = true;
        List<GrantedAuthority> l = new ArrayList();
        for(String rol:roles){
            l.add(new SimpleGrantedAuthority(rol));
        }
        return new User(username,password,enabled,acountNonExpired,credentialsNonExpired,acountNonLocked,l);
    }
}
