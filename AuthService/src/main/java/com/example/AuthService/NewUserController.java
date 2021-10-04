package com.example.AuthService;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.http.HttpHeaders;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class NewUserController {

	private final Map<Long, NewUser> users = new HashMap<>();
	private final Map<String,Token> tokens = new HashMap<>();
	//private final Map<Long, Set<String>> user_to_tokens = new HashMap<>();


	@PostMapping("/users")
	public NewUser create_user(@RequestBody @Valid NewUser user){
		//vérifier si le Id est déjà utilisé
		if(users.containsKey(user.getId()))
			throw new NewUserNotValidException(user.getId());
		user.setId(user.getId());
		users.put(user.getId(),user);
		return user;
	}

	@GetMapping("/users/{id}")
	public long specific_user (@PathVariable(value = "id") Long id){
		if(!users.containsKey(id))
			throw new NewUserNotFoundException(id);
		return id;
	}

	public void delete_token (Token token) {
		String tokenString = token.getToken();
		//long userId = token.getUserId();
		this.tokens.remove(tokenString);
		//this.user_to_tokens.get(userId).remove(tokenString);
	}

	@DeleteMapping("/users/{id}")
	public void delete_user (@PathVariable(value = "id") Long id, Token token){
		if(!tokens.containsKey(token))
			throw new TokenNotValidException(token);
		Token t = tokens.get(token);
		if(!t.isValid()){
			this.delete_token(t);
			throw new TokenNotValidException(token);
		}
//		if(t.getUserId() != id)
//			throw new NewUserNotFoundException(id);
//		users.remove(id);
//		Set<String> userTokens = user_to_token.get(id);
//		for(String tkn : userTokens)
//			this.tokens.remove(tkn);
	}


	@PostMapping("/users/{id}/{password}")
	public NewUser modify_password(@PathVariable(value = "id") Long id, @RequestBody NewUser user){

		return user;
	}


	@PostMapping("/users/{id}/token")
	public String connexion_user(@PathVariable(value = "id") Long id, @RequestBody String password){
		if(users.containsKey(id))
			throw new NewUserNotFoundException(id);
		if(users.get(id).checkpassword(password))
			throw new NewUserNotFoundException(id);

		return "";
	}

	@DeleteMapping("/users/{id}/token")
	public String deconnexion_user(@PathVariable(value = "id") Long id, @RequestBody String password){
		if(users.containsKey(id))
			throw new NewUserNotFoundException(id);
		if(users.get(id).checkpassword(password))
			throw new NewUserNotFoundException(id);

		return "";
	}

}
