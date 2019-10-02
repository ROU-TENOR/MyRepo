package com.tenor.tsf.gs.exceptions;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
@Data
@Log4j2
public class NotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String msg;
	public NotFoundException(String Msg) {
		super();
		this.msg=Msg;
		log.error(this.msg, this);
	}

}
