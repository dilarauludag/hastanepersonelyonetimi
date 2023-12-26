package com.bilgeadam.hospital.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.bilgeadam.hospital.dto.EmailDetails;

@Service("hotmail")
//@Primary
public class EmailUsingHotmail implements EmailService {

	@Override
	public void send(EmailDetails details) {
		// TODO Auto-generated method stub
		
	}
	

}
