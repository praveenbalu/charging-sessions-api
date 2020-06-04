package com.chargingsession.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Data;

/**
 * Class for maintaining charging session entity 
 * @author Praveen
 *
 */

@Data
@Builder
@JsonInclude(Include.NON_NULL)
public class ChargingSession {
	
	/** Unique identifier for a charging session. */
	private UUID id;
	
	/** station identifier of the charging session. */
	private String stationId;
	
	/** starting time of the session */
	private LocalDateTime startedAt;
	
	/** stopping time of the session */
	private LocalDateTime stoppedAt;
	
	/** current status of the session */
	private Status status;
}
