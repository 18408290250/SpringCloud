package com.junzhang.serviceredislock.feign.fallback;

import com.junzhang.serviceredislock.feign.ServiceTicket;
import org.springframework.stereotype.Component;


@Component
public class ServiceTicketHystric implements ServiceTicket {
    @Override
    public String getTiketInfo() {
        return "抱歉，服务器繁忙";
    }
}