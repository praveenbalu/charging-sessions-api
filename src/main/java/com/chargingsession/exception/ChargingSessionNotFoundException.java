package com.chargingsession.exception;

/**
 * ChargingSessionNotFoundException returns when the given Charging session is not found.
 * @author Praveen
 * 
 */
public class ChargingSessionNotFoundException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3390559508099483174L;
	
	/**
	 * Instantiates a new ChargingSessionNotFoundException exception.
	 *
	 * @param message the message
	 */
	public ChargingSessionNotFoundException(String message) {
		super(message);
	}
}
