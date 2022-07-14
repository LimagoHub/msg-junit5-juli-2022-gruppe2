package de.msg.webapp.services.models;


import lombok.*;
import org.springframework.stereotype.Service;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    @Setter(AccessLevel.PRIVATE)
    private String id;

    @Setter(AccessLevel.PRIVATE)
    private String vorname;

    @Setter(AccessLevel.PRIVATE)
    private String nachname;

}
