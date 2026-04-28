package br.com.krono.exerciciossb.controllers;

import br.com.krono.exerciciossb.infra.security.TokenService;
import br.com.krono.exerciciossb.model.DTO.AuthenticationDTO;
import br.com.krono.exerciciossb.model.DTO.LoginResponseDTO;
import br.com.krono.exerciciossb.model.DTO.RegisterDTO;
import br.com.krono.exerciciossb.model.entity.User;
import br.com.krono.exerciciossb.model.repositories.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private UserRepository userRepository; //Interface para acessar os dados dos usuários no banco de dados.

    @Autowired
    private TokenService tokenService; //Serviço para gerar e validar tokens de autenticação.

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private AuthenticationManager authenticationManager; //Gerencia o processo de autenticação, verificando as credenciais do usuário.

    @Operation(summary = "Realiza o acesso (Login)", description = "Verifica as credenciais do usuário e retorna um Token JWT. Este token" +
            " deve ser usado para acessar as rotas protegidas (como cadastro de produtos).")
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password()); //Objeto padrão do Spring Security para carregar dados de login.
        var auth = authenticationManager.authenticate(usernamePassword); //Autentica o usuário usando o AuthenticationManager. Se as credenciais forem válidas, retorna um objeto de autenticação.

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));

    }

    @Operation(summary = "Cria uma nova conta", description = "Cadastra um novo" +
            " usuário no sistema com login, senha criptografada e nível de acesso (ADMIN ou USER).")
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data){
        if (userRepository.findUserByLogin(data.login()) != null){ //Verifica se um usuário com o mesmo login já existe no banco de dados.
            return ResponseEntity.badRequest().build(); //Retorna uma resposta HTTP 400 Bad Request se o login já estiver em uso.
        }
        String encryptePassword = new BCryptPasswordEncoder().encode(data.password()); //Criptografa a senha usando BCryptPasswordEncoder.
        User newUser = new User(data.login(), encryptePassword, data.role()); //Cria um novo objeto User com o login, a senha criptografada e o papel do usuário.
        return ResponseEntity.ok(userRepository.save(newUser)); //Salva o novo usuário no banco de dados e retorna uma resposta HTTP 200 OK com os detalhes do usuário criado.
    }

}
