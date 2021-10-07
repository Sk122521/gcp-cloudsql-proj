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

@RestController
public class Todoresource {
    @Autowired
	private TodoHardcodedService todoservice;
	
    @GetMapping("/users/{username}/todos")
	public List<Todo> getAllTodos(@PathVariable String username){
	  return todoservice.findAll();	
	}
    
    @DeleteMapping("users/{username}/todos/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable String username, @PathVariable long id){
    Todo todo = todoservice.deleteById(id);
    if(todo != null) {
    	return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
    }
    @PutMapping("/users/{username}/todos/{id}")
    public ResponseEntity<Todo> updatetodo(@PathVariable String username , @PathVariable long id,@RequestBody Todo todo)
    {
    	Todo tod  = todoservice.save(todo);
    	return new ResponseEntity<Todo>(tod,HttpStatus.OK);
    }
    
    @PostMapping("/users/{username}/todos")
    public ResponseEntity<Void> updatetodo(@PathVariable String username,@RequestBody Todo todo)
    {
    	Todo tod  = todoservice.save(todo);
    	 URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
    			.buildAndExpand(tod.getId()).toUri();
    	
    	return  ResponseEntity.created(uri).build();
    }
}
