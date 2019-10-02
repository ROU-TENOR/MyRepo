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
import com.tenor.tsf.gs.entity.Departement;
import com.tenor.tsf.gs.entity.Utilisateur;

import lombok.extern.log4j.Log4j2;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Log4j2
public class UserControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;
	private Utilisateur user = new Utilisateur();
	private Departement departement = new Departement();

	@Before
	public void init() throws JsonProcessingException, Exception {
		departement.setId(1l);
		departement.setLibelle("DSI");
		user.setFirstName("user 1");
		user.setSecondName("user");
		user.setDepartement(departement);
		MvcResult resultActions = mockMvc.perform(
				post("/departements").contentType("application/json").content(objectMapper.writeValueAsString(user)))
				.andReturn();
		String content = resultActions.getResponse().getContentAsString();
		user = objectMapper.readValue(content, Utilisateur.class);
	}

	@Test
	public void testSave() throws JsonProcessingException, Exception {
		user.setFirstName("user 1");
		user.setSecondName("user");
		user.setDepartement(departement);
		mockMvc.perform(post("/users").contentType("application/json").content(objectMapper.writeValueAsString(user)))
				.andExpect(status().isOk());
		mockMvc.perform(post("/users").contentType("application/json").content(objectMapper.writeValueAsString(user)))
				.andExpect(status().isOk());
	}

	@Test
	public void testSaveEmptyField() throws JsonProcessingException, Exception {
		user.setFirstName("");
		mockMvc.perform(post("/users").contentType("application/json").content(objectMapper.writeValueAsString(user)))
				.andExpect(status().isBadRequest());
		// resultActions.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testGetAll() throws JsonProcessingException, Exception {

		MvcResult resultActions = mockMvc.perform(get("/users").contentType("application/json"))
				.andExpect(status().isOk()).andReturn();
		String content = resultActions.getResponse().getContentAsString();
		List<Utilisateur> listuser = objectMapper.readValue(content, new TypeReference<List<Utilisateur>>() {
		});
		log.info(listuser);

	}

	/*
	 * @Test public void testEmptyList() throws JsonProcessingException, Exception {
	 * 
	 * MvcResult resultActions =
	 * mockMvc.perform(get("/users").contentType("application/json"))
	 * .andExpect(status().isNotFound()).andReturn();
	 * 
	 * }
	 */
	@Test
	public void testDelete() throws JsonProcessingException, Exception {
		user.setId(1l);
		ResultActions resultActions = mockMvc
				.perform(
						delete("/users").contentType("application/json").content(objectMapper.writeValueAsString(user)))
				.andExpect(status().isOk());
		resultActions.andDo(MockMvcResultHandlers.print());

	}

	@Test
	public void testDeleteNotFound() throws JsonProcessingException, Exception {
		user=new Utilisateur();
		user.setId(30L);
		user.setFirstName("mydsi");
		user.setSecondName("mydsi");
		user.setDepartement(departement);
		ResultActions resultActions = mockMvc
				.perform(
						delete("/users").contentType("application/json").content(objectMapper.writeValueAsString(user)))
				.andExpect(status().isNotFound());
		resultActions.andDo(MockMvcResultHandlers.print());

	}

}
