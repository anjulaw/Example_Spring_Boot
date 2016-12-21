package com.example.config;

import com.example.utils.WebConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import java.util.List;

/**
 * Created by anjulaw on 11/21/2016.
 */
@Configuration
@EnableWebMvc
@ComponentScan({ "com.example" })
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    Environment env;


    @Autowired
    @Qualifier("jstlViewResolver")
    private ViewResolver jstlViewResolver;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        String staticHtmlPath = env.getProperty(WebConstants.STATIC_WEB_HTML_RESOURCES);
        String staticCssPath  = env.getProperty(WebConstants.STATIC_WEB_CSS_JS_RESOURCES);


        if (staticHtmlPath != null) {
            registry.addResourceHandler("/**")
                    .addResourceLocations(WebConstants.FILE_PROTOCAL + staticHtmlPath)
                    .addResourceLocations(WebConstants.FILE_PROTOCAL + staticCssPath);
        }


    }

    @Bean
    @DependsOn({ "jstlViewResolver" })
    public ViewResolver viewResolver() {
        return jstlViewResolver;
    }

    @Bean(name = "jstlViewResolver")
    public ViewResolver jstlViewResolver() {
        UrlBasedViewResolver resolver = new UrlBasedViewResolver();
        resolver.setPrefix(""); // NOTE: no preffix here
        resolver.setViewClass(JstlView.class);
        resolver.setSuffix(".html"); // NOTE: no suffix here
        return resolver;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/index_OLD.html");
    }

    /**
     * Override default message to convert response to JSON data format.This will eliminate the usage of
     * produce attribute in order to convert responsebody to JSON format explicitly
     *
     * @param converters
     */
    @Override
    public void configureMessageConverters( List<HttpMessageConverter<?>> converters )
    {
        final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion( JsonInclude.Include.USE_DEFAULTS );
        converter.setObjectMapper( objectMapper );
        converters.add( converter );
        super.configureMessageConverters( converters );
    }


}



