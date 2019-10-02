package com.tenor.tsf.gs.exceptions;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
@Data
@Log4j2
public class DateExpection extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String msg;
	public DateExpection(String Msg) {
		super();
		// TODO Auto-generated constructor stub
		this.msg=Msg;
		log.error(this.msg,this);
	}
	
}
