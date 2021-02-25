package avla.registroventas.security;

import avla.registroventas.entitys.User;
import avla.registroventas.entitys.UserInfo;
import avla.registroventas.repositorys.UserRepository;
import com.google.gson.Gson;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import io.jsonwebtoken.Jwts;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;


public class JwtUtil {

    @Autowired
    private static UserRepository userRepository;


    public static void addAuthentication(HttpServletResponse response, String authResultName, String name) throws IOException {
        String token = Jwts.builder()
                .setSubject(name).signWith(SignatureAlgorithm.HS512,"A@vla").compact();
        response.addHeader("Authorization","Bearer "+token);
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String[] moreInfo = name.split(" ");
        UserInfo userInfo = new UserInfo(token,authResultName,moreInfo[0]+"]",moreInfo[1].substring(0, moreInfo[1].length()-1));
        Gson gson = new Gson();
        String jsonUser = gson.toJson(userInfo);
        out.print(jsonUser);
        out.flush();

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
