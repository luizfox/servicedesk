package ee.eestie.servicedesk.controllers;

import ee.eestie.servicedesk.entities.Ticket;
import ee.eestie.servicedesk.repository.TicketsRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ticket")
public class ServiceDeskController {

  private static final String UNABLE_TO_FIND = "Unable to locate the requested ticket";
  @Autowired private final TicketsRepository ticketsRepository;

  public ServiceDeskController(TicketsRepository ticketsRepository) {
    this.ticketsRepository = ticketsRepository;
  }

  @GetMapping("/add")
  void addTicket(String title, String email, String description, Integer priority) {
    Ticket ticket = Ticket.builder().title(title).email(email).description(description).priority(priority).build();
    ticketsRepository.save(ticket);
  }

  @GetMapping("/list")
  Iterable<Ticket> listTickets(){
    return ticketsRepository.findAll();
  }

  @GetMapping("/get")
  Ticket getTicket(@NonNull Long id){
    return ticketsRepository.findById(id)
      .orElseThrow(() -> {throw new RuntimeException(UNABLE_TO_FIND);});
  }

  @GetMapping("/update")
  Ticket updateTicket(@NonNull Long id, String title, String email, String description, Integer priority) {
    Ticket ticket = ticketsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(UNABLE_TO_FIND));
    ticket.setTitle(title);
    ticket.setEmail(email);
    ticket.setDescription(description);
    ticket.setPriority(priority);
    return ticketsRepository.save(ticket);
  }

  @GetMapping("/close")
  void closeTicket(Long id){
    ticketsRepository.deleteById(id);
  }
}
