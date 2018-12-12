package com.sopra.springboot.app;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//import com.sopra.springboot.app.models.service.IUploadService;

//IMPLEMENTAMOS COMMAND LINE RUNNER PARA PEDIRLE QUE USE LOS DOS METODOS 
//QUE HEMOS CREADO EN IUPLOADSERVICE E IMPLEMENTADO EN UPLOADSERVICEIMPL
//Y PODER DE ESE MODO DESTRUIR LA CARPETA UPLOADS Y SU CONTENIDO Y VOLVERLA A CREAR AL LEVANTAR EL SERVIDOR,
//EN ESTE CASO NO ES UTIL, PERO PUEDE RESULTAR UTIL EN PROCESOS BATCH..., 
//O EN OPERACIONES INTERMEDIAS EN LAS QUE TRANSFORMAMOS O TRATAMOS ARCHIVOS
//QUE LUEGO NO SON EL DEFINITIVO Y DEBEN BORRARSE
@SpringBootApplication 
public class SpringBootJpaSopraApplication
{
//	@Autowired
//	IUploadService uploadService;
	
	public static void main(String[] args) 
	{
		SpringApplication.run(SpringBootJpaSopraApplication.class, args);
	}

//	@Override
//	public void run(String... arg0) throws Exception 
//	{
//		uploadService.deleteAll();
//		uploadService.init();
//	}
}
