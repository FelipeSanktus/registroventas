package avla.registroventas.security;

import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import io.jsonwebtoken.Jwts;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;


public class JwtUtil {
    public static void addAuthentication(HttpServletResponse response, String name, String rol) {
        String token = Jwts.builder().setSubject(name).signWith(SignatureAlgorithm.HS512,"A@vla").compact();
        response.addHeader("Authorization","Bearer "+token);
    }


    public static Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if(token != null){
            String user = Jwts.parser().setSigningKey("A@vla").parseClaimsJws(token.replace("Bearer ","")).getBody().getSubject();
            return user != null?
                    new UsernamePasswordAuthenticationToken(user,null, Collections.emptyList()):null;
        }
        return null;
    }
}
