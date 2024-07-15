package com.order.core.feign;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("PaymentService")
@LoadBalancerClient
public interface PaymentClient {
	
	@GetMapping("/payment/message")
	public String getPayment();
	
	@PostMapping("/payment/save")
	public String save(@RequestBody Payment payment);

}
