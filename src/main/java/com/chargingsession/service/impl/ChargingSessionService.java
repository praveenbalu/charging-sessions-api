package com.chargingsession.service.impl;

import static java.time.LocalDateTime.now;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.chargingsession.exception.ChargingSessionNotFoundException;
import com.chargingsession.model.ChargingSession;
import com.chargingsession.model.ChargingSessionSummaryResponse;
import com.chargingsession.model.Status;
import com.chargingsession.service.IChargingSessionService;

import lombok.extern.log4j.Log4j2;

/**
 * Implementation of {@link IChargingSessionService}
 * @author Praveen
 * 
 */
@Service

/** The Constant log from lombok. */
@Log4j2
public class ChargingSessionService implements IChargingSessionService<ChargingSession> {

	/** Map of charging sessions with its unique id as key */
	private final Map<UUID, ChargingSession> chargingSessions;

	/**
	 * Instantiates a new charging session service.
	 */
	public ChargingSessionService() {
		//Concurrent HashMap for thread safety
		chargingSessions = new ConcurrentHashMap<>();
	}

	/* (non-Javadoc)
	 * @see com.chargingsession.service.IChargingSessionService#newSession(java.lang.String)
	 */
	@Override
	public ChargingSession newSession(String stationId) {
		UUID id=UUID.randomUUID();
		ChargingSession chargingSession=ChargingSession.builder()
				.id(id).stationId(stationId).startedAt(now()).status(Status.IN_PROGRESS)
				.build();
		chargingSessions.put(id, chargingSession);
		log.info("New session created :: {}",chargingSession);
		return chargingSession;
	}

	/* (non-Javadoc)
	 * @see com.chargingsession.service.IChargingSessionService#stopSession(java.lang.String)
	 */
	@Override
	public ChargingSession stopSession(String id) {
		UUID uuid=UUID.fromString(id);
		ChargingSession chargingSession=chargingSessions.get(uuid);
		if(null==chargingSession)
			throw new ChargingSessionNotFoundException(
					String.format("Cannot find sessions with given id -> '%s'",id));
		chargingSession.setStoppedAt(now());
		chargingSession.setStatus(Status.FINISHED);
		log.info("Stopped Session :: {}",chargingSession);
		return chargingSession;
	}

	/* (non-Javadoc)
	 * @see com.chargingsession.service.IChargingSessionService#retrieveAllSessions()
	 */
	@Override
	public List<ChargingSession> retrieveAllSessions() {
		return chargingSessions.values().stream().collect(Collectors.toList());
	}

	/* (non-Javadoc)
	 * @see com.chargingsession.service.IChargingSessionService#retrieveSummary()
	 */
	@Override
	public ChargingSessionSummaryResponse retrieveSummary() {
		LocalDateTime timeBeforeMin=now().minusMinutes(1);
		
		Map<Status, Long> countMap=chargingSessions.values().stream()
			.filter(chargingSession -> 
			 chargingSession.getStartedAt().isAfter(timeBeforeMin) || 
			 (chargingSession.getStoppedAt()!=null && chargingSession.getStoppedAt().isAfter(timeBeforeMin)))
			.collect(Collectors.groupingBy(ChargingSession::getStatus, Collectors.counting()));
		
		Long startedCount=countMap.getOrDefault(Status.IN_PROGRESS,0L);
		Long stoppedCount=countMap.getOrDefault(Status.FINISHED,0L);
		
		ChargingSessionSummaryResponse chargingSessionSummaryResponse=
				ChargingSessionSummaryResponse.builder()
				.startedCount(startedCount)
				.stoppedCount(stoppedCount)
				.totalCount(startedCount+stoppedCount).build();
		log.info("Session Summary :: {}",chargingSessionSummaryResponse);
		
		return chargingSessionSummaryResponse;
	}

}
