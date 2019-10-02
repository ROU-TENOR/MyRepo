package com.tenor.tsf.gs.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.After;
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
import com.tenor.tsf.gs.entity.Departement;

import lombok.extern.log4j.Log4j2;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Log4j2
public class DepartementControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;
	private Departement departement = new Departement();
	private Departement newDepartement = new Departement();

	@Before
	public void init() throws JsonProcessingException, Exception {
		departement.setLibelle("salle 1");
		MvcResult resultActions = mockMvc.perform(post("/departements").contentType("application/json")
				.content(objectMapper.writeValueAsString(departement))).andReturn();
		String content = resultActions.getResponse().getContentAsString();
		departement = objectMapper.readValue(content, Departement.class);
	}

	@After
	public void destroy() {

	}

	@Test
	public void testSaveDapart() throws JsonProcessingException, Exception {
		newDepartement.setLibelle("salle 2");
		log.info("my serialized object" + objectMapper.writeValueAsString(departement));
		mockMvc.perform(post("/departements").contentType("application/json")
				.content(objectMapper.writeValueAsString(newDepartement))).andExpect(status().isOk());
		// resultActions.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testSaveDapartEmptyField() throws JsonProcessingException, Exception {
		newDepartement.setLibelle("");
		log.info("my serialized object" + objectMapper.writeValueAsString(departement));
		mockMvc.perform(post("/departements").contentType("application/json")
				.content(objectMapper.writeValueAsString(newDepartement))).andExpect(status().isBadRequest());
		// resultActions.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testGetAllDapart() throws JsonProcessingException, Exception {

		MvcResult resultActions = mockMvc.perform(get("/departements").contentType("application/json"))
				.andExpect(status().isOk()).andReturn();
		String content = resultActions.getResponse().getContentAsString();
		List<Departement> listdepartement = objectMapper.readValue(content, new TypeReference<List<Departement>>() {
		});
		log.info(listdepartement);

	}

	/*
	 * @Test public void testEmptyList() throws JsonProcessingException, Exception {
	 * 
	 * MvcResult resultActions =
	 * mockMvc.perform(get("/departements").contentType("application/json"))
	 * .andExpect(status().isNotFound()).andReturn();
	 * 
	 * }
	 */
	@Test
	public void testDelete() throws JsonProcessingException, Exception {
		ResultActions resultActions = mockMvc.perform(delete("/departements").contentType("application/json")
				.content(objectMapper.writeValueAsString(departement))).andExpect(status().isOk());
		resultActions.andDo(MockMvcResultHandlers.print());

	}

	@Test
	public void testDeleteNotFound() throws JsonProcessingException, Exception {
		Departement departement = new Departement();
		departement.setId(30L);
		departement.setLibelle("mydsi");
		ResultActions resultActions = mockMvc.perform(delete("/departements").contentType("application/json")
				.content(objectMapper.writeValueAsString(departement))).andExpect(status().isNotFound());
		resultActions.andDo(MockMvcResultHandlers.print());

	}

}
