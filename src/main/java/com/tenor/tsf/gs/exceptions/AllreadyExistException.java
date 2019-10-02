package com.tenor.tsf.gs.exceptions;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
@Data
@Log4j2
public class AllreadyExistException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String msg;
	public AllreadyExistException(String Msg) {
		this.msg=Msg;
		log.error(this.msg,this);
	}
}
