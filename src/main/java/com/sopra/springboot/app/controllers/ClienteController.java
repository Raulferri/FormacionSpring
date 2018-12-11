package com.sopra.springboot.app.controllers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sopra.springboot.app.models.entity.Cliente;
import com.sopra.springboot.app.models.service.IClienteService;
import com.sopra.springboot.app.util.paginator.PageRender;

@Controller
@SessionAttributes("cliente")
public class ClienteController 
{
	
	@Autowired
	private IClienteService clienteService;
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	private final static String UPLOADS_FOLDER = "uploads";
	
	@GetMapping(value="/uploads/{filename:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String filename)
	{
		Path pathFoto = Paths.get(UPLOADS_FOLDER).resolve(filename).toAbsolutePath();
		log.info("pathFoto: " + pathFoto);
		Resource recurso = null;
		
		try
		{
			recurso = new UrlResource(pathFoto.toUri());
			
			if(!recurso.exists() || !recurso.isReadable()) 
			{
				throw new RuntimeException("Error: no se puede cargar la imagen: " + pathFoto.toString());
			}
		} 
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
		}
		
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() +"\"").body(recurso);
	}
	
	@GetMapping(value="/ver/{id}")
	public String ver(@PathVariable(value="id") Long id, Map<String, Object> model, RedirectAttributes flash)
	{
		Cliente cliente = clienteService.findOne(id);
		
		if(cliente == null)
		{
			flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
			
			return "redirect:/listar";
		}
		
		model.put("cliente", cliente);
		model.put("titulo", "Detalle cliente: " + cliente.getNombre());
		
		return "ver";
	}
	
	@RequestMapping(value="/listar", method=RequestMethod.GET)
	public String listar(@RequestParam(name="page", defaultValue="0") int page, Model model) 
	{
		Pageable pageRequest = new PageRequest(page, 5);
		//Pageable pageRequest = pageRequest.of(page) --> Spring 5
		Page<Cliente> clientes = clienteService.findAll(pageRequest);
		
		PageRender<Cliente> pageRender = new PageRender<>("/listar", clientes);
		
		model.addAttribute("titulo", "Listado de clientes");
		model.addAttribute("clientes", clientes);
		model.addAttribute("page", pageRender);
		
		return "listar";
	}
	
	@RequestMapping(value="/form")
	public String crear(Map<String, Object> model)
	{
		Cliente cliente = new Cliente();
		
		model.put("cliente", cliente);
		model.put("titulo", "Formulario de Cliente");
		
		return "form";
	}
	
	@RequestMapping(value="/form/{id}")
	public String editar(@PathVariable(value="id") Long id, RedirectAttributes flash, Map<String, Object> model)
	{
		Cliente cliente = null;
		
		if(id > 0)
		{
			cliente = clienteService.findOne(id);
			
			if(cliente == null)
			{
				flash.addFlashAttribute("error", "¡El ID del cliente no existe en la Base de Datos!");
				
				return "redirect:/listar";
			}
		}
		else
		{
			flash.addFlashAttribute("error", "¡El ID del Cliente no puede ser 0!");
			
			return "redirect:/listar";
		}
		
		model.put("cliente", cliente);
		model.put("titulo", "Editar Cliente");
		
		return "form";
	}
	
	@RequestMapping(value="/form", method=RequestMethod.POST)
	public String guardar(@Valid Cliente cliente, BindingResult result, Model model, @RequestParam("file") MultipartFile foto, RedirectAttributes flash, SessionStatus status) {
		
		if(result.hasErrors()) 
		{
			model.addAttribute("titulo", "Formulario de Cliente");
			return "form";
		}
		
		if(!foto.isEmpty()) 
		{
			if(cliente.getId() != null && cliente.getId() > 0 && cliente.getFoto() != null && cliente.getFoto().length() > 0)
			{
				Path rootPath = Paths.get(UPLOADS_FOLDER).resolve(cliente.getFoto()).toAbsolutePath();
				File archivo = rootPath.toFile();
				
				if(archivo.exists() && archivo.canRead())
					if(archivo.delete())
						flash.addFlashAttribute("info", "Foto" + cliente.getFoto() + " editada con exito!");
			}
			
//			APUNTANDO AL WORKSPACE
//			Path directorioRecursos = Paths.get("src/main/resources/static/uploads");
//			String rootPath = directorioRecursos.toFile().getAbsolutePath();
			
//			APUNTANDO A DIRECTORIO EXTERNO - OPCION SOLO USADO CON SPRING Y TOCANDO EL RESOURCE HANDLER EN LA CLASE MVC CONFIG
//			String rootPath = "C://temp//uploads";
			

//			APUNTANDO A DIRECTORIO EXTERNO - OPCION MAS COMUN USANDO UN UNIQUE FILENAME
			String uniqueFilename = UUID.randomUUID().toString() + "_" + foto.getOriginalFilename();
			//Path relativo al proyecto
			Path rootPath = Paths.get(UPLOADS_FOLDER).resolve(uniqueFilename);
			//Path absoluto
			Path rootAbsolutPath = rootPath.toAbsolutePath();
			//usamos la clase LogFactory para ver por consola nuestras rutas
			log.info("rootPath: " + rootPath);
			log.info("rootAbsolutPath: " + rootAbsolutPath);
			
			try 
			{
//				USANDO write Y PASANDO COMO bytes
//				byte[] bytes = foto.getBytes();
//				Path rutaCompleta = Paths.get(rootPath + "//" + foto.getOriginalFilename());
//				Files.write(rutaCompleta,  bytes);
				
//				MISMA OPERACION CON EL METODO COPY
				Files.copy(foto.getInputStream(), rootAbsolutPath);
				flash.addFlashAttribute("info", "Ha subido correctamente '" + uniqueFilename+ "'");
				
				cliente.setFoto(uniqueFilename);
				
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			
		}
		
		
		String mensajeFlash = (cliente.getId() !=null)? "Cliente editado con éxito!" : "Cliente creado con éxito!";
		
		clienteService.save(cliente);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/listar";
	}
	
	@RequestMapping(value="/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash)
	{
		if(id > 0)
		{
			Cliente cliente = clienteService.findOne(id);
			
			clienteService.delete(id);
			flash.addFlashAttribute("success", "¡Cliente eliminado con exito!");
			
			Path rootPath = Paths.get(UPLOADS_FOLDER).resolve(cliente.getFoto()).toAbsolutePath();
			File archivo = rootPath.toFile();
			
			if(archivo.exists() && archivo.canRead())
				if(archivo.delete())
					flash.addFlashAttribute("info", "Foto" + cliente.getFoto() + " eliminada con exito!");
		}
		
		return "redirect:/listar";
	}
}
