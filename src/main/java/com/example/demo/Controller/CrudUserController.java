//package com.example.demo.Controller;
//
//import com.example.demo.entity.Role;
//import com.example.demo.entity.Utilisateurs;
//import com.example.demo.reponses.UserReponsse;
//import com.example.demo.repository.RoleRepository;
//import com.example.demo.repository.UtilisateurRepository;
//import com.example.demo.service.UtilisateurService;
//import com.example.demo.service.inter.IUtilisateurService;
//import com.example.demo.util.RoleEnum;
//import java.util.Arrays;
//import java.util.List;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@CrossOrigin
//@RequestMapping(value = "/crud_user")
//public class CrudUserController {
//
//        @Autowired
//	private RoleRepository roleRepository;
//    
//        @Autowired
//        private IUtilisateurService userService;
//        
//        @Autowired
//        private UtilisateurRepository urep;
//	
//	@GetMapping
//	public List<Utilisateurs> getUsers(){
//		return userService.getAllUsers();
//	}
//        /*public List<UserReponsse> getUsers(){
//		return urep.ToutesLesUsers();
//	}*/
//	
//	@GetMapping("/{matricule}")
//	public Utilisateurs getById(@PathVariable int matricule){
//		return userService.findBy(matricule);
//	}
//        
//        @GetMapping("/s/{username}")
//	public Utilisateurs getByUsername(@PathVariable String username){
//		return userService.search(username);
//	}
//	
//	@PostMapping("/user")
//	public void addUser(@RequestBody Utilisateurs user) {
//            Role role = roleRepository.findByNom(RoleEnum.ROLE_USER.getName());
//            user.setRole(Arrays.asList(role));
//            user.setEnable(true);
//		userService.addUser(user);
//	}
//	
//	@PostMapping("/admin")
//	public void addAdmin(@RequestBody Utilisateurs user) {
//            Role role = roleRepository.findByNom(RoleEnum.ROLE_ADMIN.getName());
//            user.setRole(Arrays.asList(role));
//            user.setEnable(true);
//		userService.addUser(user);
//	}
//	
//	@PostMapping("/super_admin")
//	public void addSuperAdmin(@RequestBody Utilisateurs user) {
//            Role role = roleRepository.findByNom(RoleEnum.ROLE_SUPER_ADMIN.getName());
//            user.setRole(Arrays.asList(role));
//            user.setEnable(true);
//		userService.addUser(user);
//	}
//	
//	@PutMapping
//	public void updateUser(@RequestBody Utilisateurs user) {
//		userService.updateUser(user);
//	}
//	
//	@DeleteMapping("/{id}")
//	public void deleteUser(@PathVariable Long id) {
//		userService.deleteUser(id);
//	}
//        
//        
//	
//	@PutMapping("/{matricule}")
//	public void desactivateUser(@PathVariable int matricule) {
//            Utilisateurs user = new Utilisateurs();
//            user = userService.findBy(matricule);
//            if(user.isEnable()){
//                user.setEnable(false);
//            }else{
//                user.setEnable(true);
//            }
//		userService.updateUser(user);
//	}
//}
