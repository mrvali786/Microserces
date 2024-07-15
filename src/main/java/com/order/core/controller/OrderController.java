package com.order.core.controller;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.order.core.feign.Payment;
import com.order.core.feign.PaymentClient;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private PaymentClient paymentClient;//

	@Autowired
	private WebClient.Builder webClientInstance;

	@Autowired
	private RestClient.Builder restClient;

	@Autowired
	private RestTemplate restTemplate;//

	

	@GetMapping("/feignOrder")
	public String feignOrder() {
		String response = paymentClient.getPayment();
		System.out.println("Feign Client");
		System.out.println(response);
		return response;
	}
	
	@GetMapping("/feignOrderSave")
	public String feignOrderSave() {
		Payment payment =new Payment();
		payment.setId(102);
		payment.setName("Indian Rupee");
		String response = paymentClient.save(payment);
		System.out.println("Feign Client");
		System.out.println(response);
		return response;
	}

	@GetMapping("/webfluxOrder")
	public String webfluxOrder() {

		String response = webClientInstance.build().get().uri(this.uri())
				.exchangeToMono(res -> res.bodyToMono(String.class)).block();
		System.out.println("Web Client");
		System.out.println(response);
		return response;

	}

	@GetMapping("/restTemplateOrder")
	public String restTemplateOrder() {

		ResponseEntity<String> responseEntity = restTemplate.exchange(this.uri(), HttpMethod.GET, null, String.class);

		String response = responseEntity.getBody();
		System.out.println("Rest Template");
		System.out.println(response);
		return response;
	}

	@GetMapping("/restClientOrder")
	public String restClientOrder() {

		String response = restClient.build().get().uri(this.uri()).retrieve().body(String.class);

		System.out.println("Rest Client");
		System.out.println(response);
		
		return response;
	}
	
	private URI uri() {
		URI paymentURI = null;
		try {
			paymentURI = new URI("HTTP", "PaymentService", "/payment/message", null);
		} catch (URISyntaxException e) {
		}

		return paymentURI;

	}

}
