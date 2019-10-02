package com.tenor.tsf.gs.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
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
import com.tenor.tsf.gs.entity.Reclamation;
import com.tenor.tsf.gs.entity.Salle;
import com.tenor.tsf.gs.entity.Utilisateur;
import com.tenor.tsf.gs.service.DepartementService;
import com.tenor.tsf.gs.service.ReclamationService;
import com.tenor.tsf.gs.service.SalleService;
import com.tenor.tsf.gs.service.UtilisateurService;

import lombok.extern.log4j.Log4j2;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Log4j2
public class ReclamationControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	private LocalDate date = LocalDate.of(2021, 9, 12);

	private Salle salle = new Salle();
	private Departement departement = new Departement();
	private Utilisateur user = new Utilisateur();

	private Reclamation reclamation = new Reclamation();
	private Reclamation newReclamation = new Reclamation();
	@Autowired
	private UtilisateurService utilisateurService;
	@Autowired
	private SalleService salleService;
	@Autowired
	private DepartementService departementService;
	@Autowired
	ReclamationService reclamationService;

	@Before
	public void init() throws JsonProcessingException, Exception {
		// initiale the databse
		departement.setLibelle("DSI");
		user.setFirstName("ahmed");
		user.setSecondName("ouahmane");
		user.setDepartement(departement);
		salle.setLibelle("salle 1");
		reclamation.setSalle(salle);
		reclamation.setUser(user);
		reclamation.setDateRec(date);
		departement = departementService.addDepartement(departement);
		user = utilisateurService.saveUtilisateur(user);
		salle = salleService.addSalle(salle);
	}

	@Test
	public void testSave() throws JsonProcessingException, Exception {

		departement = departementService.addDepartement(departement);
		user = utilisateurService.saveUtilisateur(user);
		salle = salleService.addSalle(salle);

		reclamation.setSalle(salle);
		reclamation.setUser(user);
		reclamation.setDateRec(LocalDate.of(2021, 9, 14));
		mockMvc.perform(post("/reclamations").contentType("Application/json")
				.content(objectMapper.writeValueAsString(reclamation))).andExpect(status().isOk()).andDo(print());
		mockMvc.perform(post("/reclamations").contentType("Application/json")
				.content(objectMapper.writeValueAsString(reclamation))).andExpect(status().isOk()).andDo(print());
		// resultActions.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testSaveEmptyField() throws JsonProcessingException, Exception {
		newReclamation.setSalle(null);
		mockMvc.perform(post("/reclamations").contentType("application/json")
				.content(objectMapper.writeValueAsString(newReclamation))).andExpect(status().isBadRequest())
				.andDo(print());
		;
	}

	@Test
	public void testGetAll() throws JsonProcessingException, Exception {

		MvcResult resultActions = mockMvc.perform(get("/reclamations").contentType("application/json"))
				.andExpect(status().isOk()).andReturn();
		String content = resultActions.getResponse().getContentAsString();
		List<Reclamation> listReclamation = objectMapper.readValue(content, new TypeReference<List<Reclamation>>() {
		});
		log.info(listReclamation);

	}

	/*
	 * @Test public void testEmptyList() throws JsonProcessingException, Exception {
	 * 
	 * MvcResult resultActions =
	 * mockMvc.perform(get("/Reclamations").contentType("application/json"))
	 * .andExpect(status().isNotFound()).andReturn();
	 * 
	 * }
	 */
	@Test
	public void testDelete() throws JsonProcessingException, Exception {
		reclamation.setId(1L);
		mockMvc.perform(delete("/reclamations").contentType("application/json")
				.content(objectMapper.writeValueAsString(reclamation))).andExpect(status().isOk());

	}

	@Test
	public void testDeleteNotFound() throws JsonProcessingException, Exception {
		Reclamation Reclamation = new Reclamation();
		Reclamation.setId(30L);
		ResultActions resultActions = mockMvc.perform(delete("/Reclamations").contentType("application/json")
				.content(objectMapper.writeValueAsString(Reclamation))).andExpect(status().isNotFound());
		resultActions.andDo(MockMvcResultHandlers.print());

	}

}
