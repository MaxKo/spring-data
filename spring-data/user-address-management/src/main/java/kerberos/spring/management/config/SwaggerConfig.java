package kerberos.spring.management.config;
/*
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.hateoas.client.JsonPathLinkDiscoverer;
import org.springframework.hateoas.client.LinkDiscoverers;
import org.springframework.hateoas.mediatype.hal.HalLinkDiscoverer;
import org.springframework.hateoas.server.LinkRelationProvider;
import org.springframework.hateoas.server.core.DelegatingLinkRelationProvider;
import org.springframework.hateoas.server.core.EvoInflectorLinkRelationProvider;

import org.springframework.plugin.core.SimplePluginRegistry;
import org.springframework.plugin.core.support.PluginRegistryFactoryBean;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.DocExpansion;
import springfox.documentation.swagger.web.ModelRendering;
import springfox.documentation.swagger.web.OperationsSorter;
import springfox.documentation.swagger.web.TagsSorter;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

// https://www.baeldung.com/swagger-2-documentation-for-spring-rest-api
// http://localhost:8080/swagger-ui.html

@Configuration
@EnableSwagger2
//@Import({SpringDataRestConfiguration.class})
public class SwaggerConfig {

	@Bean
	public LinkDiscoverers discoverers() {
	    List<JsonPathLinkDiscoverer> plugins = new ArrayList<>();
	    plugins.add(new HalLinkDiscoverer());
	    return new LinkDiscoverers(SimplePluginRegistry.create(plugins));
	}
	
	@Bean
	public LinkRelationProvider provider() {
	    return new EvoInflectorLinkRelationProvider();
	}
	
	
	@Bean
	@Primary
	public PluginRegistryFactoryBean<LinkRelationProvider, LinkRelationProvider.LookupContext>myPluginRegistryProvider(){
	
	    PluginRegistryFactoryBean<LinkRelationProvider, LinkRelationProvider.LookupContext> factory
	            = new PluginRegistryFactoryBean<>();
	
	    factory.setType(LinkRelationProvider.class);
	    Class<?> classes[] = new Class<?>[1];
	    classes[0] = DelegatingLinkRelationProvider.class;
	    factory.setExclusions(classes);
	
	    return factory;
	}
	
	
	@Bean
	public Docket api() {
	    return new Docket(DocumentationType.SWAGGER_2)
	            .select()
	            .apis(RequestHandlerSelectors.basePackage("kerberos.spring.management.controller"))
	            //.paths(PathSelectors.any())
	            .paths(PathSelectors.any())
	            //.paths(selector) Mapping("/")
	            .build()
	            .pathMapping("/api");
	            //.apiInfo(getApiInformation())
	            //.useDefaultResponseMessages(false)
	            //.globalResponseMessage(RequestMethod.GET, getCustomizedResponseMessages());
	}
	
	
	
	private ApiInfo getApiInformation() {
	    return new ApiInfo("Demo REST API",
	            "This is a Demo API created using Spring Boot",
	            "1.0",
	            "API Terms of Service URL",
	            new Contact("SAMPLE Coder", "www.sample.com", "coder.sample@gmail.com"),
	            "API License",
	            "API License URL",
	            Collections.emptyList()
	    );
	}
	
	private List<ResponseMessage> getCustomizedResponseMessages() {
	    List<ResponseMessage> responseMessages = new ArrayList<>();
	    //responseMessages.add(new ResponseMessageBuilder().code(500).message("Server has crashed!!").responseModel(new ModelRef("Error")).build());
	    //responseMessages.add(new ResponseMessageBuilder().code(403).message("You shall not pass!!").build());
	    return responseMessages;
	}
	
    @Bean
    UiConfiguration uiConfig() {
        return UiConfigurationBuilder.builder()
                .deepLinking(true)
                .displayOperationId(false)
                .defaultModelsExpandDepth(1)
                .defaultModelExpandDepth(1)
                .defaultModelRendering(ModelRendering.EXAMPLE)
                .displayRequestDuration(false)
                .docExpansion(DocExpansion.NONE)
                .filter(false)
                .maxDisplayedTags(null)
                .operationsSorter(OperationsSorter.ALPHA)
                .showExtensions(false)
                .tagsSorter(TagsSorter.ALPHA)
                .supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS)
                .validatorUrl(null)
                .build();
    }
    
}
*/
