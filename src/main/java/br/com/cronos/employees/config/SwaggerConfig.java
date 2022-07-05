package br.com.cronos.employees.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket cobrancaAPI() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build()
				.apiInfo(metainfo())
				.useDefaultResponseMessages(false)
				.globalResponseMessage(RequestMethod.GET, ResponseMessageForGET());
	}

	private ApiInfo metainfo() {
		return new ApiInfoBuilder()
				.title("App de controle de Posts")
				.description("Relacionamentos entre entidades: funcionários, departamentos e posts com hypermedia Hateoas")
				.version("1.0")
				.contact(new Contact("mauro lucio", "https://linkedin.com/in/mauro-lucio-pereira", "mauroslucios"))
				.license("Apache License Version 2.0")
				.licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
				.build();
	}
	
	private List<ResponseMessage> ResponseMessageForGET() {
		return new ArrayList<ResponseMessage>() {
			private static final long serialVersionUID = 1L;
			{
				add(new ResponseMessageBuilder()
						.code(500)
						.message("500 message")
						.responseModel(new ModelRef("Error"))
						.build());
				add(new ResponseMessageBuilder()
						.code(403)
						.message("Forbidden!")
						.build());
			}
		};
	}


}