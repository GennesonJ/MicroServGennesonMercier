package com.example.userservice;

import com.example.AuthService.NewUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class UserController {

	private final Map<Long, User> users = new HashMap<>();
	private final AtomicLong counter = new AtomicLong();

	@Value("${services.authentication}")
	private String auth_service_url;

	@GetMapping("/users")
	public Collection<User> users(){
		return users.values();
	}

	@PostMapping("/users")
	public User create_user(@RequestBody @Valid User user){
		long new_id = counter.incrementAndGet();
		user.setId(new_id);
		users.put(new_id,user);

		NewUser authUser = new NewUser (new_id, "password");
		RestTemplate restTemplate = new RestTemplate();
		Long check_id = restTemplate.postForObject(auth_service_url+"/users", authUser,Long.class);
		if(check_id!=new_id)
			throw new RuntimeException();
		return user;
	}

	@GetMapping(path = "/users/{id}/name")
	public String get_user_name(@PathVariable(value = "id")Long id){return users.get(id).getName();}


								@PostMapping(path = "/users/{id}/name")
	public String set_user_name(@RequestBody String name,
								@PathVariable(value = "id") Long id,
								@RequestHeader(value = "X-Token")String token){
		RestTemplate restTemplate=new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-Token", token);
		HttpEntity entity = new HttpEntity<String>("", headers);

		ResponseEntity<Long> response = restTemplate.exchange(auth_service_url+"/token", HttpMethod.GET,entity, Long.class);
		Long token_user = response.getBody();
		if(token_user!= id)
			throw new RuntimeException();
		users.get(id).setName(name);
		return name;
	}

	@GetMapping("/users/{id}")
	public User specific_user (@PathVariable(value = "id") Long id){
		if(!users.containsKey(id)) throw new UserNotFoundException(id);
		return users.get(id);
	}

	@DeleteMapping("/users/{id}")
	public String delete_user(@PathVariable(value = "id") Long id) {
		if(!users.containsKey(id)) throw new UserNotFoundException(id);
		users.remove(id);
		return "L'utilisateur " + id + " a été supprimé";
	}

	@PutMapping("/users/{id}")
	public User modify_user (@PathVariable(value = "id") Long id, @RequestBody User user){
		if(!users.containsKey(id)) throw new UserNotFoundException(id);
		user.setId(id);
		users.replace(id, user);
		return user;
	}


}
