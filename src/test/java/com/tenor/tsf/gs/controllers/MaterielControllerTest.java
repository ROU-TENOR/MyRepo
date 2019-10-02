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
import com.tenor.tsf.gs.entity.Materiel;
import com.tenor.tsf.gs.entity.Salle;
import com.tenor.tsf.gs.service.MaterielService;
import com.tenor.tsf.gs.service.SalleService;

import lombok.extern.log4j.Log4j2;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Log4j2
public class MaterielControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private SalleService salleService;
	@Autowired
	private MaterielService materielService;

	@Autowired
	private ObjectMapper objectMapper;
	Salle salle = new Salle();
	private Materiel materiel = new Materiel();
	private Materiel newMateriel = new Materiel();

	@Before
	public void Init() throws Exception {
		salle.setId(1L);
		salle.setLibelle("dsi");
		salle = salleService.addSalle(salle);
		newMateriel.setLibelle("mat 2");
		newMateriel.setSalle(salle);
		ResultActions resultActions = mockMvc.perform(post("/materiels").contentType("application/json")
				.content(objectMapper.writeValueAsString(newMateriel))).andExpect(status().isOk());
		resultActions.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testSave() throws JsonProcessingException, Exception {
		salle.setId(1L);
		salle.setLibelle("dsi");
		salle = salleService.addSalle(salle);
		newMateriel.setLibelle("mat 2");
		newMateriel.setSalle(salle);
		ResultActions resultActions = mockMvc.perform(post("/materiels").contentType("application/json")
				.content(objectMapper.writeValueAsString(newMateriel))).andExpect(status().isOk());
		resultActions.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testSaveEmptyField() throws JsonProcessingException, Exception {
		newMateriel.setLibelle("mat 2");
		newMateriel.setSalle(null);
		mockMvc.perform(post("/materiels").contentType("application/json")
				.content(objectMapper.writeValueAsString(newMateriel))).andExpect(status().isBadRequest());
		// resultActions.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testGetAll() throws JsonProcessingException, Exception {

		materielService.saveMateriel(newMateriel);
		MvcResult resultActions = mockMvc.perform(get("/materiels").contentType("application/json"))
				.andExpect(status().isOk()).andReturn();
		String content = resultActions.getResponse().getContentAsString();
		List<Materiel> listMateriel = objectMapper.readValue(content, new TypeReference<List<Materiel>>() {
		});
		log.info(listMateriel);

	}

	/*
	 * @Test public void testEmptyList() throws JsonProcessingException, Exception {
	 * 
	 * MvcResult resultActions =
	 * mockMvc.perform(get("/materiels").contentType("application/json"))
	 * .andExpect(status().isNotFound()).andReturn();
	 * 
	 * }
	 */
	@Test
	public void testDelete() throws JsonProcessingException, Exception {
		salle.setId(1L);
		salle.setLibelle("dsi");
		salle = salleService.addSalle(salle);
		newMateriel.setLibelle("mat 2");
		newMateriel.setSalle(salle);
		newMateriel.setLibelle("HP");
		materielService.saveMateriel(newMateriel);
		ResultActions resultActions = mockMvc.perform(delete("/materiels").contentType("application/json")
				.content(objectMapper.writeValueAsString(newMateriel))).andExpect(status().isOk());
		resultActions.andDo(MockMvcResultHandlers.print());

	}

	@Test
	public void testDeleteNotFound() throws JsonProcessingException, Exception {
		materiel.setId(30L);
		materiel.setLibelle("mydsi");
		ResultActions resultActions = mockMvc.perform(
				delete("/materiels").contentType("application/json").content(objectMapper.writeValueAsString(materiel)))
				.andExpect(status().isNotFound());
		resultActions.andDo(MockMvcResultHandlers.print());

	}

}
