package com.chargingsession.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import com.chargingsession.exception.ChargingSessionNotFoundException;
import com.chargingsession.model.ChargingSession;
import com.chargingsession.model.ChargingSessionSummaryResponse;
import com.chargingsession.model.Status;
import com.chargingsession.service.IChargingSessionService;
import com.chargingsession.service.impl.ChargingSessionService;

@Execution(ExecutionMode.SAME_THREAD)
public class ChargingSessionServiceTest {
	
	private IChargingSessionService<ChargingSession> chargingSessionService;
	private ChargingSession chargingSession;
	
	@BeforeEach
	public void initialize() {
		chargingSessionService=new ChargingSessionService();
	}
	
	@Test
	public void testNewSession() {
		chargingSession=chargingSessionService.newSession("station-123");
		assertThat(chargingSessionService.retrieveAllSessions())
			.contains(chargingSession)
			.withFailMessage("Session with station-id 'station-123' is not available");
	}
	
	@Test
	public void testStopValidSession() {
		chargingSession=chargingSessionService.newSession("station-123");
		chargingSession=chargingSessionService.stopSession(chargingSession.getId().toString());
		assertThat(chargingSession.getStatus())
			.isEqualTo(Status.FINISHED)
			.withFailMessage("Session with station-id 'station-123' should be in finished status");
	}
	
	@Test
	public void testStopInvalidSession() {
		ChargingSessionNotFoundException ex=assertThrows(ChargingSessionNotFoundException.class,
				() -> chargingSessionService.stopSession("123e4567-e89b-12d3-a456-426655440000"),
				"Expected Exception while trying to stop session");
		assertThat(ex.getMessage()).isEqualTo("Cannot find sessions with given id -> '123e4567-e89b-12d3-a456-426655440000'")
			.withFailMessage("Expected Exception message");
	}
	
	@Test
	public void testRetrieveAllSessions() {
		chargingSessionService.newSession("station-122");
		chargingSessionService.newSession("station-124");
		chargingSessionService.newSession("station-125");
		chargingSessionService.newSession("station-126");
		assertThat(chargingSessionService.retrieveAllSessions())
			.hasSize(4)
			.withFailMessage("Totally 4 charging stations should exists");
	}
	
	@Test
	public void testSummary() {
		chargingSessionService.newSession("station-122");
		chargingSessionService.newSession("station-124");
		chargingSessionService.newSession("station-125");
		chargingSessionService.newSession("station-126");
		ChargingSessionSummaryResponse response=chargingSessionService.retrieveSummary();
		
		assertThat(response.getStartedCount())
			.isEqualTo(4)
			.withFailMessage("Totally 4 charging stations should be in progress");
		assertThat(response.getTotalCount())
			.isEqualTo(4)
			.withFailMessage("Totally 4 charging stations should be available");
		assertThat(response.getStoppedCount())
			.isEqualTo(0)
			.withFailMessage("Totally 0 charging stations should be stopped");
	}
	
	
	
}
