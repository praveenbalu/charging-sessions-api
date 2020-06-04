package com.chargingsession.concurrency;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import com.chargingsession.model.ChargingSession;
import com.chargingsession.model.Status;
import com.chargingsession.service.IChargingSessionService;
import com.chargingsession.service.impl.ChargingSessionService;

@Execution(ExecutionMode.CONCURRENT)
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
public class ConcurrencyTest {
	
	static IChargingSessionService<ChargingSession> chargingSessionService;
	
	@BeforeAll
	public static void setUp() {
		chargingSessionService=new ChargingSessionService();
	} 
	
	@RepeatedTest(100)
	@Order(1)
	public void startStopSessions() {
		ChargingSession session=chargingSessionService
				.newSession(String.format("stationId-%s",new Random().nextInt(9)));
		chargingSessionService.stopSession(session.getId().toString());
	}
	
	@Order(2)
	@Test
	public void validateSessionsStatus() {
		long stoppedSessions=chargingSessionService.retrieveAllSessions()
			.stream().filter(session -> session.getStatus()==Status.FINISHED).count();
		assertThat(stoppedSessions).isEqualTo(100)
			.withFailMessage("100 Sessions supposed to be stopped");
	}
	
	@Order(3)
	@Test
	public void validateSessionsCount() {
		assertThat(chargingSessionService.retrieveAllSessions().size()).isEqualTo(100)
		.withFailMessage("100 Sessions supposed to be exists");
	}
}
