package ee.eestie.servicedesk.entities;

import lombok.*;

import javax.persistence.*;

@Data
@ToString
@Builder
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  Long id;

  String title;
  String email;
  String description;
  Integer priority;
}
