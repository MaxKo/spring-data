package kerberos.spring.management.config.swagger;
/*


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.stereotype.Component;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.contexts.ModelContext;
import springfox.documentation.spi.service.*;
import springfox.documentation.spi.service.contexts.*;
import springfox.documentation.spring.web.SpringGroupingStrategy;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spring.web.plugins.DocumentationPluginsManager;
import springfox.documentation.spring.web.scanners.ApiListingScanningContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
//import java.util.function.Function;
import java.util.function.Function;

import static java.util.Collections.singleton;
import static java.util.stream.Collectors.toList;

@Component
@Primary
public class DocumentationPluginsManagerReplaced extends DocumentationPluginsManager {

    @Autowired
    @Qualifier("documentationPluginRegistry")
    private PluginRegistry<DocumentationPlugin, DocumentationType> documentationPlugins;
    @Autowired
    @Qualifier("apiListingBuilderPluginRegistry")
    private PluginRegistry<ApiListingBuilderPlugin, DocumentationType> apiListingPlugins;
    @Autowired
    @Qualifier("parameterBuilderPluginRegistry")
    private PluginRegistry<ParameterBuilderPlugin, DocumentationType> parameterPlugins;
    @Autowired
    @Qualifier("expandedParameterBuilderPluginRegistry")
    private PluginRegistry<ExpandedParameterBuilderPlugin, DocumentationType> parameterExpanderPlugins;
    @Autowired
    @Qualifier("operationBuilderPluginRegistry")
    private PluginRegistry<OperationBuilderPlugin, DocumentationType> operationBuilderPlugins;
    @Autowired
    @Qualifier("resourceGroupingStrategyRegistry")
    private PluginRegistry<ResourceGroupingStrategy, DocumentationType> resourceGroupingStrategies;
    @Autowired
    @Qualifier("operationModelsProviderPluginRegistry")
    private PluginRegistry<OperationModelsProviderPlugin, DocumentationType> operationModelsProviders;
    @Autowired
    @Qualifier("defaultsProviderPluginRegistry")
    private PluginRegistry<DefaultsProviderPlugin, DocumentationType> defaultsProviders;
    @Autowired
    @Qualifier("pathDecoratorRegistry")
    private PluginRegistry<PathDecorator, DocumentationContext> pathDecorators;
    @Autowired
    @Qualifier("apiListingScannerPluginRegistry")
    private PluginRegistry<ApiListingScannerPlugin, DocumentationType> apiListingScanners;

    public Iterable<DocumentationPlugin> documentationPlugins() throws IllegalStateException {
        List<DocumentationPlugin> plugins = documentationPlugins.getPlugins();
        DuplicateGroupsDetector.ensureNoDuplicateGroups(plugins);
        if (plugins.isEmpty()) {
            return singleton(defaultDocumentationPlugin());
        }
        return plugins;
    }

    public Parameter parameter(ParameterContext parameterContext) {
        for (ParameterBuilderPlugin each : parameterPlugins.getPluginsFor(parameterContext.getDocumentationType())) {
            each.apply(parameterContext);
        }
        return parameterContext.parameterBuilder().build();
    }

    public Parameter expandParameter(ParameterExpansionContext context) {
        for (ExpandedParameterBuilderPlugin each : parameterExpanderPlugins.getPluginsFor(context.getDocumentationType())) {
            each.apply(context);
        }
        return context.getParameterBuilder().build();
    }

    public Operation operation(OperationContext operationContext) {
        for (OperationBuilderPlugin each : operationBuilderPlugins.getPluginsFor(operationContext.getDocumentationType())) {
            each.apply(operationContext);
        }
        return operationContext.operationBuilder().build();
    }


    public ApiListing apiListing(ApiListingContext context) {
        for (ApiListingBuilderPlugin each : apiListingPlugins.getPluginsFor(context.getDocumentationType())) {
            each.apply(context);
        }
        return context.apiListingBuilder().build();
    }

    public Set<ModelContext> modelContexts(RequestMappingContext context) {
        DocumentationType documentationType = context.getDocumentationContext().getDocumentationType();
        for (OperationModelsProviderPlugin each : operationModelsProviders.getPluginsFor(documentationType)) {
            each.apply(context);
        }
        return context.operationModelsBuilder().build();
    }

    public ResourceGroupingStrategy resourceGroupingStrategy(DocumentationType documentationType) {
        return resourceGroupingStrategies.getPluginOrDefaultFor(documentationType, new SpringGroupingStrategy());
    }

    private DocumentationPlugin defaultDocumentationPlugin() {
        return new Docket(DocumentationType.SWAGGER_2);
    }

    public DocumentationContextBuilder createContextBuilder(
            DocumentationType documentationType,
            DefaultsProviderPlugin defaultConfiguration) {
        return defaultsProviders.getPluginOrDefaultFor(documentationType, defaultConfiguration)
                .create(documentationType)
                .withResourceGroupingStrategy(resourceGroupingStrategy(documentationType));
    }

    public com.google.common.base.Function<String, String> decorator(final PathContext context) {
        return input -> {
            //Iterable<Function<String, String>> decorators = pathDecorators
        	Iterable<com.google.common.base.Function<String,String>> decorators = pathDecorators
            		.getPluginsFor(context.documentationContext())
            			.stream()
                    		.map(each -> each.decorator(context))
                    		.collect(toList());
            String decorated = input;
            for (com.google.common.base.Function<String, String> decorator : decorators) {
                decorated = decorator.apply(decorated);
            }
            return decorated;
        };
    }

    public Collection<ApiDescription> additionalListings(final ApiListingScanningContext context) {
        final DocumentationType documentationType = context.getDocumentationContext().getDocumentationType();
        List<ApiDescription> additional = new ArrayList<>();
        for (ApiListingScannerPlugin each : apiListingScanners.getPluginsFor(documentationType)) {
            additional.addAll(each.apply(context.getDocumentationContext()));
        }
        return additional;
    }
}

*/