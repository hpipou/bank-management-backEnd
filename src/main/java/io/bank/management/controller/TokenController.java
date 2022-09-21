package io.bank.management.controller;

import io.bank.management.entity.User;
import io.bank.management.repository.UserRepository;
import io.bank.management.utilsVariables.Variables;
import org.springframework.web.bind.annotation.RestController;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class TokenController {

    private UserRepository userRepository;

    public TokenController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/refreshToken")
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String authToken= request.getHeader("Authorization");
        if(authToken!=null && authToken.startsWith("Bearer ")){

            try {
                String jwtRefreshToken=authToken.substring(7);
                Algorithm algorithm=Algorithm.HMAC256(Variables.SECRET);
                JWTVerifier jwtVerifier= JWT.require(algorithm).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(jwtRefreshToken);
                String username=decodedJWT.getSubject();
                User appUser=userRepository.findByEmail(username);
                String jwtAccessToken=JWT.create()
                        .withSubject(username)
                        .withIssuer(request.getRequestURL().toString())
                        .withExpiresAt(new Date(System.currentTimeMillis()+5*60*1000))
                        .withClaim("roles",appUser.getRoles().stream().map(r->r.getRoleName()).collect(Collectors.toList()))
                        .sign(algorithm);

                Map<String,String> token = new HashMap<>();
                token.put("AccessToken",jwtAccessToken);
                token.put("RefreshToken", jwtRefreshToken);
                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(),token);

            }catch (Exception e){
                response.setStatus(403);
                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(),"REFRESH TOKEN INVALIDE");
            }

        }else{
            response.setStatus(403);
            response.setContentType("application/json");
            new ObjectMapper().writeValue(response.getOutputStream(),"REFRESH TOKEN INTROUVABLE");
        }
    }
}
