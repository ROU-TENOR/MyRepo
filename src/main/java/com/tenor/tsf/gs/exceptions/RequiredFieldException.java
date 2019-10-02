package com.tenor.tsf.gs.exceptions;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
@Data
@Log4j2
public class RequiredFieldException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	private String msg;
	
	public RequiredFieldException( String message) {
		this.msg=message;
		log.error(this.msg,this);
	}
}
