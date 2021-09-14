package com.example.userservice;

import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class UserController {

	private final Map<Long, User> users = new HashMap<>();
	private final AtomicLong counter = new AtomicLong();

	@GetMapping("/users")
	public Collection<User> users(){
		return users.values();
	}

	@PostMapping("/users")
	public User create_user(@RequestBody User user){
		long new_id = counter.incrementAndGet();
		user.setId(new_id);
		users.put(new_id,user);

		return user;
	}

	@GetMapping("/users/{id}")
	public User specific_user (@PathVariable(value = "id") Long id){
		return users.get(id);
	}

	@DeleteMapping("/users/{id}")
	public String delete_user(@PathVariable(value = "id") Long id) {
		users.remove(id);
		return "L'utilisateur " + id + " a été supprimé";
	}

	@PutMapping("/users/{id}")
	public User modify_user (@PathVariable(value = "id") Long id, @RequestBody User user){
		user.setId(id);
		users.replace(id, user);
		return user;
	}


}
