package br.com.feltex.academicnet;

import com.sun.faces.config.ConfigureListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import javax.faces.webapp.FacesServlet;
import javax.servlet.Servlet;
import javax.servlet.ServletContainerInitializer;

@SpringBootApplication
public class AcademicnetApplication {

	public static void main(String[] args) {
		SpringApplication.run(AcademicnetApplication.class, args);
	}

	//Mapear arquivos .xhtml para .jr
	@Bean
	public ServletRegistrationBean faceServletRegistration(){
		ServletRegistrationBean registration = new ServletRegistrationBean<>(new FacesServlet(), "*.xhtml");
		registration.setLoadOnStartup(1);
		registration.addUrlMappings("*.jr");
		return registration;
	}

	//Força a configuração face e o tema do primefaces
	@Bean
	public ServletContextInitializer servletContextInitializer() {
		return servletContext -> {
			servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());
			servletContext.setInitParameter("primefaces.THEME", "redmond");
		};
	}

	//Configurar para registrar o Beans inicializados
	@Bean
	public ServletListenerRegistrationBean<ConfigureListener> jsfConfigureListener(){
		return new ServletListenerRegistrationBean<>(new ConfigureListener());
	}

	//Comunicação Rest
	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}

}

