package com.chargingsession.model;

import lombok.Builder;
import lombok.Data;

/**
 * Class for creating a summary of 
 * charging sessions to return to the client
 * @author Praveen
 * 
 */

@Data
@Builder
public class ChargingSessionSummaryResponse {
	
	/** total number of sessions */
	private Long totalCount;
	
	/** number of sessions started */
	private Long startedCount;
	
	/** number of sessions stopped */
	private Long stoppedCount;
}
