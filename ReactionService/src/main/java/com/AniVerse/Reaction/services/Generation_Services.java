package com.AniVerse.Reaction.services;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class Generation_Services {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public String Generation_uuid() {
		String uuid= UUID.randomUUID().toString();
		
		return uuid;
	}
	

}
