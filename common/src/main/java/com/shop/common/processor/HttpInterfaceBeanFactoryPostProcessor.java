package com.shop.common.processor;

import java.util.Objects;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.env.Environment;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;
import org.springframework.web.service.annotation.HttpExchange;

@Component
public class HttpInterfaceBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
	private static final String BASE_PACKAGE = "sungjeon";
	private static final String SUFFIX_CLIENT = "Client";

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		var environment = beanFactory.getBean(Environment.class);
		var componentProvider = new HttpClientCandidateComponentProvider(environment);

		componentProvider.findCandidateComponents(BASE_PACKAGE).stream()
			.filter(it -> StringUtils.hasText(it.getBeanClassName()))
			.forEach(it -> {
				var interfaceClass = findHttpInterfaceClass(it);

				var baseUrl = resolveBaseUrl(interfaceClass, environment);

				var restClientBuilder = RestClient.builder()
					.baseUrl(baseUrl)
					.messageConverters(converters -> {
						var stringConverter = beanFactory.getBean(StringHttpMessageConverter.class);
						converters.add(stringConverter);

						var jacksonConverter = beanFactory.getBean(MappingJackson2HttpMessageConverter.class);
						converters.add(jacksonConverter);
					}).build();

				beanFactory.registerSingleton(it.getBeanClassName(), restClientBuilder);
			});

	}

	private static class HttpClientCandidateComponentProvider extends ClassPathScanningCandidateComponentProvider {
		public HttpClientCandidateComponentProvider(Environment environment) {
			super(false, environment);
			addIncludeFilter(new AnnotationTypeFilter(HttpExchange.class));
		}
	}

	private Class<?> findHttpInterfaceClass(BeanDefinition beanDefinition) {
		try {
			return ClassUtils.forName(Objects.requireNonNull(beanDefinition.getBeanClassName()),
				this.getClass().getClassLoader());
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException(e);
		}
	}

	private String resolveBaseUrl(Class<?> interfaceClass, Environment environment) {
		var targetBeanName = interfaceClass.getSimpleName();
		var propertyPrefix = targetBeanName.endsWith(SUFFIX_CLIENT) ?
			targetBeanName.substring(0, targetBeanName.length() - SUFFIX_CLIENT.length()) : targetBeanName;
		var propertyName = "https://api.openai.com";

		return environment.getProperty(propertyName);
	}
}
