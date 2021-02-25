package avla.registroventas.security;

import avla.registroventas.entitys.User;
import avla.registroventas.repositorys.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;


@CrossOrigin(origins = "*")
public class LoginFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    UserRepository userRepository;

    public LoginFilter(String url, AuthenticationManager authManager){
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        InputStream body = request.getInputStream();
        Usuario user = new ObjectMapper().readValue(body,Usuario.class);
        if (CorsUtils.isPreFlightRequest(request)) {
            response.setStatus(HttpServletResponse.SC_OK);
            return (Authentication) response;
        }else{

            return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword(), Collections.emptyList()));

        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String name = authResult.getAuthorities().toString();
        JwtUtil.addAuthentication(response,authResult.getName(),name);
    }


}

