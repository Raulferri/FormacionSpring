package com.sopra.springboot.app;

//import java.nio.file.Paths;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter
{
//	private final Logger log = LoggerFactory.getLogger(getClass());
	
//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) 
//	{
//		super.addResourceHandlers(registry);
//		
////		USANDO EL METODO COPY DEFINIMOS DE ESTA MANERA EL GUARDADO EN EL PROYECTO 
//		String resourcePath = Paths.get("uploads").toAbsolutePath().toUri().toString();
//		
//		log.info(resourcePath);
//		
//		registry.addResourceHandler("/uploads/**").addResourceLocations(resourcePath);
////												  .addResourceLocations("file:/C:/TEMP/uploads/";) ESTO SERIA IGUAL QUE EL DE ARRIBA
//	}
}
