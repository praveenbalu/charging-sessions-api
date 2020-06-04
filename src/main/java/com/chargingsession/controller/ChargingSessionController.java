package com.chargingsession.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chargingsession.model.ChargingSession;
import com.chargingsession.model.ChargingSessionRequest;
import com.chargingsession.model.ChargingSessionSummaryResponse;
import com.chargingsession.service.IChargingSessionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * RestController for ChargingSessions
 * @author Praveen
 * 
 */
@RestController
@Api("Charging Sessions API")
@RequestMapping("/chargingSessions")
public class ChargingSessionController {
	
	@Autowired
	private IChargingSessionService<ChargingSession> chargingSessionService;

	/**
	 * Submits a new charging session session
	 *
	 * @param request the request
	 * @return the response entity
	 */
	@PostMapping
	@ApiOperation(value = "New Session", notes = "Submits a new session for the charging session")
	public ResponseEntity<ChargingSession> newSession(@Valid @RequestBody ChargingSessionRequest request) {
		return ResponseEntity.ok(chargingSessionService.newSession(request.getStationId()));
	}

	/**
	 * Stops an existing charging session
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@PutMapping("/{id}")
	@ApiOperation(value = "Stop Session", notes = "Stops the existing session")
	public ResponseEntity<ChargingSession> stopSession(@NonNull @PathVariable(value = "id") String id) {
		return ResponseEntity.ok(chargingSessionService.stopSession(id));
	}

	/**
	 * Retrieve all charging sessions.
	 *
	 * @return the response entity
	 */
	@GetMapping()
	@ApiOperation(value = "Retrieve Sessions", notes = "Retrieve all the sessions")
	public ResponseEntity<List<ChargingSession>> retrieveAllSessions() {
		return ResponseEntity.ok(chargingSessionService.retrieveAllSessions());
	}

	/**
	 * Retrieve summary of charging sessions for last minute.
	 *
	 * @return the response entity
	 */
	@GetMapping("/summary")
	@ApiOperation(value = "Retrieve Summary", notes = "Retrieve the summary of sessions occured before a minute")
	public ResponseEntity<ChargingSessionSummaryResponse> retrieveSummary() {
		return ResponseEntity.ok(chargingSessionService.retrieveSummary());
	}
}
