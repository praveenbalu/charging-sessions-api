package com.chargingsession.model;

import org.springframework.lang.NonNull;

import lombok.Getter;
import lombok.Setter;

/**
 * Class for receiving the new charging request from the client
 * @author Praveen
 * 
 */

/**
 * Get stationId.
 *
 * @return the station id
 */
@Getter

/**
 * Set new stationId.
 *
 * @param stationId the new station id
 */
@Setter
public class ChargingSessionRequest {
	
	/** stationId for creating the charging session. */
	@NonNull
	private String stationId;
}
