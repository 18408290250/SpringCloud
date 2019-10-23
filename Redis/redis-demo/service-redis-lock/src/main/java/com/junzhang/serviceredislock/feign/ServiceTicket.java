package com.junzhang.serviceredislock.feign;

import com.junzhang.serviceredislock.feign.fallback.ServiceTicketHystric;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value = "service-redis-cache-webflux",fallback = ServiceTicketHystric.class)
public interface ServiceTicket {
    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    String getTiketInfo();
}

