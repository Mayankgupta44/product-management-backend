package com.example.restAPIs_practice.Controller;

import com.example.restAPIs_practice.DTO.UserDTO;
import com.example.restAPIs_practice.Entity.User;
import com.example.restAPIs_practice.Repository.UserRepository;
import com.example.restAPIs_practice.Service.ProductService;
import com.example.restAPIs_practice.Service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository){
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<UserDTO> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Integer id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

//    @PostMapping
//    public ResponseEntity<User> createUser(@RequestBody User user){
//        User savedUser = userRepository.save(user);
//        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
//    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Integer id,@Valid @RequestBody UserDTO dto){
        return  ResponseEntity.ok(userService.updateUser(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(dto));
    }

    @PutMapping("/create")
    public ResponseEntity<UserDTO> updateUserWithProduct(@Valid @RequestBody UserDTO dto){
        return ResponseEntity.ok(userService.updateUserWithProduct(dto));
    }


//    @GetMapping("/search/name")
//    public List<User> findByName(@RequestParam String name){
//        return userRepository.findByName(name);
//    }

//    @GetMapping("/search/age")
//    public List<User> findByAge(@RequestParam Integer age){
//        return userRepository.findByAge(age);
//    }

//    @GetMapping("/search/name/age")
//    public List<User> findByNameAndAge(@RequestParam String name, @RequestParam Integer age){
//        return userRepository.findByNameAndAge(name, age);
//    }
//
//    @GetMapping("/search/name/age/")
//    public List<User> findByNameOrAge(@RequestParam String name, @RequestParam Integer age){
//        return userRepository.findByNameOrAge(name, age);
//    }
//
//    @GetMapping("/search/age")
//    public List<User> findByAgeGreaterThan(@RequestParam Integer age){
//        return userRepository.findByAgeGreaterThan(age);
//    }
//
//    @GetMapping("/search/name/contains")
//    public List<User> findByNameContaining(@RequestParam(value = "contains") String name){
//        return userRepository.findByNameContaining(name);
//    }
}
