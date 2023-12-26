package com.bilgeadam.hospital.service;

import com.bilgeadam.hospital.dto.EmailDetails;

public interface EmailService {
	void send(EmailDetails details);
}
