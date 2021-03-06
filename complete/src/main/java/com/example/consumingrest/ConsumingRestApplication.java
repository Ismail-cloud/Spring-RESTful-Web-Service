package com.example.consumingrest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;


@SpringBootApplication
public class ConsumingRestApplication {

	private static final Logger log = LoggerFactory.getLogger(ConsumingRestApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ConsumingRestApplication.class, args);
	}
    @Bean
    public SimpleClientHttpRequestFactory getRequestFactory (){
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("http://proxyweb.mnh.fr", 8080));
        SimpleClientHttpRequestFactory httpRequestFactory = new SimpleClientHttpRequestFactory();
        httpRequestFactory.setProxy(proxy);
        return httpRequestFactory;
    }

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder, SimpleClientHttpRequestFactory httpClientFactory) {

		return builder.requestFactory(httpClientFactory).build();
	}
	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {
			Quote quote = restTemplate.getForObject(
					"https://gturnquist-quoters.cfapps.io/api/random", Quote.class);
			log.info(quote.toString());
		};
	}
}
