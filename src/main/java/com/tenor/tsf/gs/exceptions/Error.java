package com.tenor.tsf.gs.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Error {
	
	private String msg;
	private HttpStatus httpStatus;
	private LocalDateTime dateTime;
//	public Error(String msg, HttpStatus httpStatus,LocalDateTime dateTime) {
//		super();
//		this.msg = msg;
//		this.httpStatus = httpStatus;
//		this.dateTime=dateTime;
//	}
	

}
