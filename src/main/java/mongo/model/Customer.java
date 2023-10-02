package mongo.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@Document(collection = "customers")
public class Customer implements Entity {
    @Id
    private String id;
    private String username;
    private String name;
    private String address;
    private String email;
    private LocalDateTime birthdate;
}
