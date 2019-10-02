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
import com.tenor.tsf.gs.entity.Reservation;
import com.tenor.tsf.gs.entity.Salle;
import com.tenor.tsf.gs.entity.Utilisateur;
import com.tenor.tsf.gs.service.DepartementService;
import com.tenor.tsf.gs.service.ReservationService;
import com.tenor.tsf.gs.service.SalleService;
import com.tenor.tsf.gs.service.UtilisateurService;

import lombok.extern.log4j.Log4j2;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Log4j2
public class ReservationControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	private LocalDate dateD = LocalDate.of(2021, 9, 12);
	private LocalDate dateF = LocalDate.of(2021, 9, 13);

	private Salle salle = new Salle();
	private Departement departement = new Departement();
	private Utilisateur user = new Utilisateur();

	private Reservation reservation = new Reservation();
	private Reservation newReservation = new Reservation();
	@Autowired
	private UtilisateurService utilisateurService;
	@Autowired
	private SalleService salleService;
	@Autowired
	private DepartementService departementService;
	@Autowired
	ReservationService resevSer;

	@Before
	public void init() throws JsonProcessingException, Exception {
		// initiale the databse
		departement.setLibelle("DSI");
		user.setFirstName("ahmed");
		user.setSecondName("ouahmane");
		user.setDepartement(departement);
		salle.setLibelle("salle 1");
		reservation.setSalle(salle);
		reservation.setUser(user);
		reservation.setDateDebut(LocalDate.of(2021, 9, 12));
		reservation.setDateFin(LocalDate.of(2021, 9, 13));
		departement = departementService.addDepartement(departement);
		user = utilisateurService.saveUtilisateur(user);
		salle = salleService.addSalle(salle);
	}

	@Test
	public void testSave() throws JsonProcessingException, Exception {

		departement = departementService.addDepartement(departement);
		user = utilisateurService.saveUtilisateur(user);
		salle = salleService.addSalle(salle);

		reservation.setSalle(salle);
		reservation.setUser(user);
		reservation.setDateDebut(LocalDate.of(2021, 9, 20));
		reservation.setDateFin(LocalDate.of(2021, 9, 21));
		mockMvc.perform(post("/reservations").contentType("Application/json")
				.content(objectMapper.writeValueAsString(reservation))).andExpect(status().isOk()).andDo(print());
		// resultActions.andDo(MockMvcResultHandlers.print());
		reservation.setDateDebut(LocalDate.of(2021, 9, 25));
		reservation.setDateFin(LocalDate.of(2021, 9, 26));
		mockMvc.perform(post("/reservations").contentType("Application/json")
				.content(objectMapper.writeValueAsString(reservation))).andExpect(status().isOk()).andDo(print());
	}

	@Test
	public void testSaveAlreadyReserved() throws JsonProcessingException, Exception {

		reservation.setDateDebut(LocalDate.of(2021, 9, 25));
		reservation.setDateFin(LocalDate.of(2021, 9, 26));
		mockMvc.perform(post("/reservations").contentType("Application/json")
				.content(objectMapper.writeValueAsString(reservation)));
		mockMvc.perform(post("/reservations").contentType("Application/json")
				.content(objectMapper.writeValueAsString(reservation))).andExpect(status().isBadRequest());
		// resultActions.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testSaveEmptyField() throws JsonProcessingException, Exception {
		newReservation.setSalle(null);
		mockMvc.perform(post("/reservations").contentType("application/json")
				.content(objectMapper.writeValueAsString(newReservation))).andExpect(status().isBadRequest())
				.andDo(print());
		;
	}

	@Test
	public void testGetAll() throws JsonProcessingException, Exception {

		MvcResult resultActions = mockMvc.perform(get("/reservations").contentType("application/json"))
				.andExpect(status().isOk()).andReturn();
		String content = resultActions.getResponse().getContentAsString();
		List<Reservation> listReservation = objectMapper.readValue(content, new TypeReference<List<Reservation>>() {
		});
		log.info(listReservation);

	}

	/*
	 * @Test public void testEmptyList() throws JsonProcessingException, Exception {
	 * 
	 * MvcResult resultActions =
	 * mockMvc.perform(get("/reservations").contentType("application/json"))
	 * .andExpect(status().isNotFound()).andReturn();
	 * 
	 * }
	 */
	@Test
	public void testDelete() throws JsonProcessingException, Exception {
		reservation.setId(1L);
		ResultActions resultActions = mockMvc.perform(delete("/reservations").contentType("application/json")
				.content(objectMapper.writeValueAsString(reservation))).andExpect(status().isOk());
		resultActions.andDo(MockMvcResultHandlers.print());

	}

	@Test
	public void testDeleteNotFound() throws JsonProcessingException, Exception {
		Reservation reservation = new Reservation();
		reservation.setId(30L);
		ResultActions resultActions = mockMvc.perform(delete("/reservations").contentType("application/json")
				.content(objectMapper.writeValueAsString(reservation))).andExpect(status().isNotFound());
		resultActions.andDo(MockMvcResultHandlers.print());

	}

}
