package avla.registroventas.repositorys;

import avla.registroventas.entitys.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findUsuarioByUsername(String username);
    User findUsuarioByApellido(String apelldido);
    User findByUsername(String username);
}

