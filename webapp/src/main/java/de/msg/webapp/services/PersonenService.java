package de.msg.webapp.services;

import de.msg.webapp.services.models.Person;

import java.util.Optional;

public interface PersonenService {

    boolean speichern(Person person) throws PersonenServiceException;
    Optional<Person> findeNachId(String id) throws PersonenServiceException;
    boolean loesche(String id) throws PersonenServiceException;
    default boolean loesche(Person p)  throws PersonenServiceException{
        return loesche(p.getId());
    }

    Iterable<Person> findeAlle() throws PersonenServiceException;

}
