package com.tenor.tsf.gs.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenor.tsf.gs.entity.Salle;
import com.tenor.tsf.gs.service.SalleService;

import lombok.extern.log4j.Log4j2;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Log4j2
public class SalleControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;
	private Salle salle = new Salle();
	@Autowired
	SalleService salleServ;
	@Before
	public void init() throws JsonProcessingException, Exception {
		salle.setLibelle("salle 1");
		mockMvc.perform(post("/salles").contentType("application/json").content(objectMapper.writeValueAsString(salle)))
				.andReturn();

	}

	@Test
	public void testSave() throws JsonProcessingException, Exception {
		salle.setLibelle("dsi");

		ResultActions resultActions = mockMvc
				.perform(
						post("/salles").contentType("application/json").content(objectMapper.writeValueAsString(salle)))
				.andExpect(status().isOk());
		resultActions.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testSaveEmptyField() throws JsonProcessingException, Exception {
		salle.setLibelle("");
		mockMvc.perform(post("/salles").contentType("application/json").content(objectMapper.writeValueAsString(salle)))
				.andExpect(status().isBadRequest());
		// resultActions.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testGetAll() throws JsonProcessingException, Exception {

		MvcResult resultActions = mockMvc.perform(get("/salles").contentType("application/json"))
				.andExpect(status().isOk()).andReturn();
		String content = resultActions.getResponse().getContentAsString();
		List<Salle> listsalle = objectMapper.readValue(content, new TypeReference<List<Salle>>() {
		});
		log.info(listsalle);

	}

	/*
	 * @Test public void testEmptyList() throws JsonProcessingException, Exception {
	 * 
	 * MvcResult resultActions =
	 * mockMvc.perform(get("/salles").contentType("application/json"))
	 * .andExpect(status().isNotFound()).andReturn();
	 * 
	 * }
	 */
	@Test
	public void testDelete() throws JsonProcessingException, Exception {
		salle=new Salle();
		
		salle.setLibelle("dsi");
		salle=salleServ.addSalle(salle);
		ResultActions resultActions = mockMvc.perform(
				delete("/salles").contentType("application/json").content(objectMapper.writeValueAsString(salle)))
				.andExpect(status().isOk());
		resultActions.andDo(MockMvcResultHandlers.print());

	}

	@Test
	public void testDeleteNotFound() throws JsonProcessingException, Exception {
		salle.setId(30L);
		salle.setLibelle("mydsi");
		ResultActions resultActions = mockMvc.perform(
				delete("/salles").contentType("application/json").content(objectMapper.writeValueAsString(salle)))
				.andExpect(status().isNotFound());
		resultActions.andDo(MockMvcResultHandlers.print());

	}

}
