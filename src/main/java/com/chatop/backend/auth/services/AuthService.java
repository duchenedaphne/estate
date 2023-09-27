package com.chatop.backend.auth.services;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;

import com.chatop.backend.auth.jpo.AuthResponse;
import com.chatop.backend.auth.jpo.LoginRequest;
import com.chatop.backend.auth.jpo.RegisterRequest;
import com.chatop.backend.models.User;
import com.chatop.backend.services.user.UserServiceImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    @Autowired
    private final UserServiceImpl userService;
    @Autowired
    private final JwtsService jwtsService;
    @Autowired
    private final JwteService jwteService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    public ResponseEntity<?> register(RegisterRequest registerRequest) {

        Matcher matcher = emailFormatChecker(registerRequest.getEmail());

        if (matcher.matches() == false)
            return new ResponseEntity<String>("Format email invalide.", HttpStatus.BAD_REQUEST);

        User existingUser;
        HttpStatus status;
        String jwtsToken;
        String jwteToken;
        AuthResponse authResponse = new AuthResponse();

        try {
            existingUser = userService.getUserByEmail(registerRequest.getEmail()); 

            if (existingUser != null) 
                return new ResponseEntity<String>("Cet email existe déjà, connectez-vous à votre compte.", HttpStatus.FORBIDDEN);  

        } catch (HttpStatusCodeException exception) { 

            status = exception.getStatusCode();
            return new ResponseEntity<String>("Impossible de vérifier l'email.", status);

        } catch (Exception e) { 
            e.printStackTrace(); 
        }            
        User newUser = new User();

        newUser.setName(registerRequest.getName());
        newUser.setEmail(registerRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword())); 
        newUser.setCreated_at(new Date());
        newUser.setUpdated_at(new Date());        

        try {
			userService.createUser(newUser);

            jwtsToken = jwtsService.generateToken(newUser);
            jwteToken = jwteService.encode(jwtsToken);
            authResponse.setToken(jwteToken);

		} catch (HttpStatusCodeException exception) {	

            status = exception.getStatusCode();
            return new ResponseEntity<String>("Impossible de créer le compte.", status);

		} catch (Exception e) { 
            e.printStackTrace(); 
        }

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                registerRequest.getEmail(), registerRequest.getPassword()
            )
        );
        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);
    }

    public ResponseEntity<?> login(LoginRequest loginRequest) {

        Matcher matcher = emailFormatChecker(loginRequest.getEmail());

        if (matcher.matches() == false)
            return new ResponseEntity<String>("Format email invalide.", HttpStatus.BAD_REQUEST);
            
        User userApp;
        HttpStatus status;
        String jwtsToken;
        String jwteToken;
        AuthResponse authResponse = new AuthResponse();

        try {
            userApp = userService.getUserByEmail(loginRequest.getEmail());

            if (userApp == null)
                return new ResponseEntity<String>("Email inconnu, veuillez créer un compte.", HttpStatus.NOT_FOUND);
                
            if (!passwordEncoder.matches(loginRequest.getPassword(), userApp.getPassword())) {                 
                return new ResponseEntity<String>("Mot de passe incorrect.", HttpStatus.UNAUTHORIZED);
            }         
            jwtsToken = jwtsService.generateToken(userApp);
            jwteToken = jwteService.encode(jwtsToken);
            authResponse.setToken(jwteToken);

        } catch (HttpStatusCodeException exception) {  

            status = exception.getStatusCode();
            return new ResponseEntity<String>("Échec de la récupération de l'utilisateur.", status);

        } catch (Exception e) { 
            e.printStackTrace(); 
        }    
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(), loginRequest.getPassword()
            )
        );
        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.OK);
    }

    public ResponseEntity<?> getUserApp(UserDetails userDetails) {

        User userApp = new User();
        HttpStatus status;
        
        try {
            userApp = userService.getUserByEmail(userDetails.getUsername()); 

        } catch (HttpStatusCodeException exception) {

            status = exception.getStatusCode();
            return new ResponseEntity<String>("Échec de la récupération de l'utilisateur.", status);

        } catch (Exception e) {
            e.printStackTrace();
        } 
        return new ResponseEntity<User>(userApp, HttpStatus.OK);
    }

    public Matcher emailFormatChecker(String email) {
        
        String regx = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(regx);
        Matcher matcher = pattern.matcher(email);

        return matcher;
    }    
}
