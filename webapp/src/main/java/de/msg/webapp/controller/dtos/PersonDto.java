package de.msg.webapp.controller.dtos;

import lombok.*;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
public class PersonDto {

    @NotNull
    @Size(min=36, max =36)
    private String id;

    @NotNull
    @Size(min=2, max =30)
    private String vorname;

    @NotNull
    @Size(min=2, max =30)
    private String nachname;

}