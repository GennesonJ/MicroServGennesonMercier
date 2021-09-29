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
	private final Map<Long, Set<String>> user_to_tokens = new HashMap<>();


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

	@DeleteMapping("/users/{id}")
	public String delete_user(@PathVariable(value = "id") Long id) {
		if(!users.containsKey(id))
			throw new NewUserNotFoundException(id);
		users.remove(id);
		return "L'utilisateur " + id + " a été supprimé";
	}

	@PutMapping("/users/{id}/{password}")
	public NewUser modify_password(@PathVariable(value = "id") Long id, @RequestBody NewUser user){
		if(!users.containsKey(id)) throw new NewUserNotFoundException(id);
		user.setId(id);
		users.replace(id, user);
		return user;
	}

//	@PostMapping("/users/{id}/token")
//	public Token connexion_user(@PathVariable(value = "id") Long id, @RequestBody String password){
//		if(users.containsKey(id))
//			throw new NewUserNotFoundException(id);
//		if(users.get(id).checkpassword(password))
//			throw new NewUserNotFoundException(id);
//
//		token = new Token();
//		return token;
//	}


}
