package net.javaguides.springboot.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

//import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.model.Usuario;
import net.javaguides.springboot.repository.UsuarioRepository;

@RestController
public class UsuarioController {
	
	/*IC/CD ou CI - Injecao de dependencia*/
	@Autowired	
	private UsuarioRepository usuarioRepository;
	
 
    @RequestMapping(value = "/mostranome/{name}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String greetingText(@PathVariable String name) {
        return "Curso Spring Boot API: " + name + "!";
    }
    
    @RequestMapping(value = "/olamundo/{nome}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String retornaOlaMundo(@PathVariable String nome) {
    	Usuario usuario = new Usuario();
    	usuario.setNome(nome);
    	
    	usuarioRepository.save(usuario);
    	
    	return "Ola Mundo " + nome;
    }
    
    //Get Usuarios
  	@GetMapping("listatodos")
  	@ResponseBody /*Retorna os dados para  o corpo da resposta*/
  	public ResponseEntity<List<Usuario>> listUsuario(){
  		
  		List<Usuario> usuarios = usuarioRepository.findAll();
  		
  		return new ResponseEntity<List<Usuario>>(usuarios,HttpStatus.OK); /*Retorna a lista em Json*/
  	}
  	 	
  	
  	@PostMapping(value = "salvar")
	@ResponseBody 
	public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario) {
  		
  		Usuario user = usuarioRepository.save(usuario);
  		
  		return new ResponseEntity<Usuario>(user,HttpStatus.CREATED);
	}
  	

  	@DeleteMapping(value = "delete")
	@ResponseBody 
	public ResponseEntity<String> delete(@RequestParam Long idUser) {
  		
  		usuarioRepository.deleteById(idUser);
  		
  		return new ResponseEntity<String>("User deletado com sucesso",HttpStatus.OK);
	}
  	
  	@GetMapping(value = "buscaruserId")
	@ResponseBody 
	public ResponseEntity<Usuario> buscaruserId(@RequestParam(name = "idUser") Long idUser) {
  		
  		Usuario user = usuarioRepository.findById(idUser).get();
  		
  		return new ResponseEntity<Usuario>(user,HttpStatus.OK);
	}
    
  	@PutMapping(value = "atualizar")
	@ResponseBody 
	public ResponseEntity<?> atualizar(@RequestBody Usuario usuario) {
  		if (usuario.getId() == null) {
  			return new ResponseEntity<String>("Id nao foi informado para atualização",HttpStatus.OK);
		}
  		
  		Usuario user = usuarioRepository.saveAndFlush(usuario);
  		
  		return new ResponseEntity<Usuario>(user,HttpStatus.OK);
	}
  	
  	
	@GetMapping(value = "buscarPorNome")
	@ResponseBody 
	public ResponseEntity<List<Usuario>> buscarPorNome(@RequestParam(name = "name") String name) {
  		
  		List<Usuario> usuario = usuarioRepository.buscarPorNome(name.trim().toUpperCase());
  		
  		return new ResponseEntity<List<Usuario>>(usuario,HttpStatus.OK);
	}
}
