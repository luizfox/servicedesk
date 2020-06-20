package ee.eestie.servicedesk.controllers;

import ee.eestie.servicedesk.entities.Ticket;
import ee.eestie.servicedesk.repository.TicketsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ServiceDeskControllerTest {

  @Mock TicketsRepository ticketsRepository;
  @InjectMocks ServiceDeskController controller;
  private static final Ticket exampleTicket =
    Ticket.builder().title("title").email("some@email.ee").description("description").priority(1).build();
  private static final List<Ticket> listToReturn = new ArrayList<>();
  private static final long ID_EXISTING = 1L;
  private static final long ID_NON_EXISTING = 2L;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    listToReturn.add(exampleTicket);
    when(ticketsRepository.findById(1L)).thenReturn(Optional.of(exampleTicket));
    when(ticketsRepository.findById(2L)).thenReturn(Optional.empty());
  }

  @Test
  void addTicket() {
    controller.addTicket(
      exampleTicket.getTitle(), exampleTicket.getEmail(), exampleTicket.getDescription(), exampleTicket.getPriority());
    verify(ticketsRepository, times(1)).save(exampleTicket);
  }

  @Test
  void listTickets() {
    when(ticketsRepository.findAll()).thenReturn(listToReturn);
    assertEquals(exampleTicket, controller.listTickets().iterator().next());
  }

  @Test
  void getExistingTicket() {
    assertEquals(exampleTicket, controller.getTicket(ID_EXISTING) );
  }

  @Test
  void getNonExistingTicket() {
    assertThrows(RuntimeException.class, () -> controller.getTicket(ID_NON_EXISTING) );
  }

  @Test
  void updateNonExistingTicket() {
    assertThrows(RuntimeException.class, () -> controller.updateTicket(10L, any(), any(), any(), any()));
  }

  @Test
  void updateExistingTicket() {
    controller.updateTicket(ID_EXISTING, exampleTicket.getTitle(),
      exampleTicket.getEmail(), exampleTicket.getDescription(), exampleTicket.getPriority());
    verify(ticketsRepository, times(1)).save(exampleTicket);
  }

  @Test
  void closeTicket() {
    controller.closeTicket(ID_EXISTING);
    verify(ticketsRepository, times(1)).deleteById(ID_EXISTING);
  }
}