package com.example.retsservice.fullstack;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.retsservice.fullstack.Todo.Todo;
import com.example.retsservice.fullstack.Todo.TodoJpaRepository;

@RestController
public class TodoJparesource {
	@Autowired
	private TodoJpaRepository todojparepository;
    
    @GetMapping("/jpa/users/{username}/todos")
	public List<Todo> getAllTodos(@PathVariable String username){
	 return todojparepository.findByUsername(username);
	}
    
    @GetMapping("/jpa/users/{username}/todos/{id}")
   	public Todo getTodo(@PathVariable String username,@PathVariable long id){
   	  return todojparepository.findById(id).get() ;
   	}
    
    
    @DeleteMapping("/jpa/users/{username}/todos/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable String username, @PathVariable long id){
     todojparepository.deleteById(id);
      return ResponseEntity.noContent().build();
    }
    @PutMapping("/jpa/users/{username}/todos/{id}")
    public ResponseEntity<Todo> updatetodo(@PathVariable String username , @PathVariable long id,@RequestBody Todo todo)
    {   
    	todo.setUsername(username);
    	Todo tod  = todojparepository.save(todo);
    	return new ResponseEntity<Todo>(tod,HttpStatus.OK);
    }
    
    @PostMapping("/jpa/users/{username}/todos")
    public ResponseEntity<Void> updatetodo(@PathVariable String username,@RequestBody Todo todo)
    {
    	todo.setUsername(username);
    	Todo tod  = todojparepository.save(todo);
    	 URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
    			.buildAndExpand(tod.getId()).toUri();
    	
    	return  ResponseEntity.created(uri).build();
    }
}
