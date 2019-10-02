package com.tenor.tsf.gs.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
@Data
@Log4j2
public class AllreadyReservedException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String msg;
	public AllreadyReservedException(String Msg) {
		this.msg=Msg;
		log.error(this.msg,this);
	}
}
