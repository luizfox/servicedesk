package ee.eestie.servicedesk.repository;

import ee.eestie.servicedesk.entities.Ticket;
import org.springframework.data.repository.CrudRepository;

public interface TicketsRepository extends CrudRepository<Ticket, Long> {
}
