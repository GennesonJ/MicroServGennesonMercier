package com.example.AuthService;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
	public Long create_user(@RequestBody @Valid NewUser user){
		//vérifier si le Id est déjà utilisé
		if(users.containsKey(user.getId()))
			throw new NewUserNotValidException(user.getId());
		user.setId(user.getId());
		users.put(user.getId(),user);
		return user.getId();
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
	public void delete_user (@PathVariable(value = "id") Long id, String token){
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


	@PostMapping("/users/{id}/password")
	public long modify_password(@PathVariable(value = "id") Long id, @RequestHeader(value = "X-Token") String token, @RequestBody String newPassword){

		if (!tokens.containsKey(token))
			throw new TokenNotValidException(token);
		Token t = tokens.get(token);
		if (t.getUserId()!=id)
			throw new TokenNotValidException(token);
		if(!users.containsKey(id))
			throw new NewUserNotFoundException(id);
		NewUser user = users.get(id);
		user.setPassword(newPassword);
		return id;
	}


	@PostMapping("/users/{id}/token")
	public String connexion_user(@PathVariable(value = "id") Long id, @RequestBody String password) throws PasswordIncorrectExeption {
		if(!users.containsKey(id))
			throw new NewUserNotFoundException(id);
		if(!users.get(id).checkpassword(password))
			throw new PasswordIncorrectExeption(id);

		Token token = new Token(id);
		this.tokens.put(token.getToken(), token);

		return token.getToken();
	}

	@DeleteMapping("/users/{id}/token")
	public void deconnexion_user(@PathVariable(value = "id") Long id, @RequestHeader(value = "X-Token") String token ){
		if (!users.containsKey(id))
			throw new NewUserNotFoundException(id);
		if (!tokens.containsKey(token))
			throw new TokenNotValidException(token);
		Token t = tokens.get(token);
		if (t.getUserId() != id)
			throw new TokenNotValidException(token);
		if (!t.isValid()){
			throw new TokenNotValidException(token);
		}
		this.tokens.remove(token);
	}
}
