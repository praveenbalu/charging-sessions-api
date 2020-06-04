package com.chargingsession.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.chargingsession.controller.ChargingSessionController;
import com.chargingsession.model.ChargingSession;
import com.chargingsession.model.ChargingSessionSummaryResponse;
import com.chargingsession.service.IChargingSessionService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ChargingSessionController.class)
@Execution(ExecutionMode.SAME_THREAD)
public class ChargingSessionControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private IChargingSessionService<ChargingSession> chargingSessionService;
	
	@Test
	public void testSubmitSession() throws Exception{
		ChargingSession chargingSession=ChargingSession.builder().stationId("station-123").build();
		when(chargingSessionService.newSession(anyString())).thenReturn(chargingSession);
		mockMvc.perform(post("/chargingSessions")
					.content("{\"stationId\":\"station-123\"}")
					.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.stationId").value("station-123"));
	}
	
	@Test
	public void testSubmitInvalidSession() throws Exception{
		mockMvc.perform(post("/chargingSessions")
					.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testStopSession() throws Exception{
		ChargingSession chargingSession=ChargingSession.builder().stationId("station-123").build();
		when(chargingSessionService.stopSession(anyString())).thenReturn(chargingSession);
		mockMvc.perform(put("/chargingSessions/12345")
					.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.stationId").value("station-123"));
	}
	
	@Test
	public void testAllSessions() throws Exception{
		when(chargingSessionService.retrieveAllSessions()).thenReturn(Collections.emptyList());
		mockMvc.perform(get("/chargingSessions")
					.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}


	@Test
	public void testSummary() throws Exception{
		ChargingSessionSummaryResponse response=ChargingSessionSummaryResponse.builder()
				.totalCount(10L).startedCount(7L).stoppedCount(3L).build();
		when(chargingSessionService.retrieveSummary()).thenReturn(response);
		mockMvc.perform(get("/chargingSessions/summary")
					.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.totalCount").value(10))
				.andExpect(jsonPath("$.startedCount").value(7))
				.andExpect(jsonPath("$.stoppedCount").value(3));
	}}
