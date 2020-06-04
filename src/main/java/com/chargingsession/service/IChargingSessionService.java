package com.chargingsession.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.chargingsession.model.ChargingSession;
import com.chargingsession.model.ChargingSessionSummaryResponse;

/**
 * The Interface IChargingSessionService.
 * Generic structure of ChargingSessions service
 *
 * @param <T> the generic type
 * 
 * @author Praveen
 * 
 */
@Service
public interface IChargingSessionService<T extends ChargingSession> {
	
	/**
	 * Submits a new charging session with the given station id.
	 *
	 * @param the stationId
	 * @return the new charging session
	 */
	public T newSession(String stationId);
	
	/**
	 * Stops the mentioned charging session and updates its status
	 *
	 * @param id the unique identifier
	 * @return the updated charging session
	 */
	public T stopSession(String id);
	
	/**
	 * Retrieve all charging sessions
	 *
	 * @return list of all charging sessions
	 */
	public List<T> retrieveAllSessions();
	
	/**
	 * Retrieve summary about charging sessions
	 * which has been submitted/stopped for the 
	 * last minute
	 * 
	 * @return the Charging Sessions Summary {@link ChargingSessionSummaryResponse}
	 */
	public ChargingSessionSummaryResponse retrieveSummary();
}
