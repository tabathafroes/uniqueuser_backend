package com.fatec.backjava.resources;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.backjava.domain.Usuario;
import com.fatec.backjava.dto.DadosDTO;
import com.fatec.backjava.dto.DadosEntropicosUserDTO;
import com.fatec.backjava.dto.UsuarioDTO;
import com.fatec.backjava.dto.UsuarioDedupeDTO;
import com.fatec.backjava.dto.UsuarioKmeansDTO;
import com.fatec.backjava.dto.UsuarioUnicoDTO;
import com.fatec.backjava.services.DadosEntropicosUserService;
import com.fatec.backjava.services.UsuarioService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin()
@RestController
@Api(value="API REST UNIQUE USER DETECTOR")
@RequestMapping("usuarios")
public class UsuarioResource {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private DadosEntropicosUserService dadosEntropicosUserService;
	
	@ApiOperation(value="Realiza a busca de um usuário por seu ID")
	@GetMapping("/buscarPorId/{id}")
	public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) throws Exception {
		Usuario usuario = usuarioService.buscarPorId(id);
		return ResponseEntity.ok().body(usuario);
	}
	
	@ApiOperation(value="Realiza o cadastro de um usuario e salva seus dados entropicos")
	@PostMapping()
	@Transactional
	public ResponseEntity<DadosDTO> cadastrar(@RequestBody DadosDTO dadosDTO) throws Exception {
		UsuarioDTO usuarioDTO = dadosDTO.converterParaUsuario();
		Usuario usuario = usuarioService.inserir(usuarioDTO);
		DadosEntropicosUserDTO dadosEntropicosUserDTO = dadosDTO.converterParaDadosEntropicos(usuario);
		dadosEntropicosUserService.inserir(dadosEntropicosUserDTO, usuarioDTO);
		return ResponseEntity.ok(dadosDTO);
	}
	
	@ApiOperation(value="Realiza a busca de todos usuários")
	@GetMapping("/buscarTodos")
	public ResponseEntity<List<Usuario>> buscarTodos() {
		List<Usuario> usuarios = usuarioService.buscarTodos();
		return ResponseEntity.ok().body(usuarios);
	}
	
	@ApiOperation(value="Realiza a busca da lista de DTO de usuarios unicos")
	@GetMapping("/buscarUsuarioUnicoDTO")
	public ResponseEntity<List<UsuarioUnicoDTO>> buscarUsuarioUnicoDTO() {
		List<UsuarioUnicoDTO> listaUsuarioUnicoDTO = usuarioService.buscarUsuarioUnicoDTO();
		return ResponseEntity.ok().body(listaUsuarioUnicoDTO);
	}

	@ApiOperation(value="Realiza a busca da lista de DTO de usuarios agrupados pelo kmeans")
	@GetMapping("/buscarListaUsuarioKmeansDTO")
	public ResponseEntity<List<UsuarioKmeansDTO>> buscarListaUsuarioKmeansDTO() {
		List<UsuarioKmeansDTO> listaUsuarioKmeansDTO = usuarioService.buscarListaUsuarioKmeansDTO();
		return ResponseEntity.ok().body(listaUsuarioKmeansDTO);
	}
	
	@ApiOperation(value="Realiza a busca da lista de DTO de usuarios agrupados pelo dedupe")
	@GetMapping("/buscarListaUsuarioDedupeDTO")
	public ResponseEntity<List<UsuarioDedupeDTO>> buscarListaUsuarioDedupeDTO() {
		List<UsuarioDedupeDTO> listaUsuarioDedupeDTO = usuarioService.buscarListaUsuarioDedupeDTO();
		return ResponseEntity.ok().body(listaUsuarioDedupeDTO );
	}

}
