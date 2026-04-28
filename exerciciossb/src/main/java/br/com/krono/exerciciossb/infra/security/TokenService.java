package br.com.krono.exerciciossb.infra.security;

import br.com.krono.exerciciossb.model.entity.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static com.mysql.cj.protocol.ExportControlled.sign;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;

     public String generateToken(User user){
         try {
             Algorithm algorithm = Algorithm.HMAC256(secret);
             String token = JWT.create()
                     .withIssuer("exerciciossb")
                     .withSubject(user.getLogin())
                     .withExpiresAt(getExpirationTime()) // Token válido por 1 hora
                     .sign(algorithm);
             return token;
         }catch (JWTCreationException e ){
                throw new RuntimeException("Erro ao gerar token JWT", e);
         }
     }

     public String validationToken(String token){
         try {
             Algorithm algorithm = Algorithm.HMAC256(secret);
             return JWT.require(algorithm)
                     .withIssuer("exerciciossb")
                     .build()
                     .verify(token) //Verifica a validade do token, incluindo a assinatura e o emissor.
                     .getSubject();
         }catch (JWTVerificationException exception){
             return null;
         }
     }


     private Instant getExpirationTime() {
         return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
     }
}
