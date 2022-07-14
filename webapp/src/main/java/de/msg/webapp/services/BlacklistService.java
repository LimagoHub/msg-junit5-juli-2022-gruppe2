package de.msg.webapp.services;

import de.msg.webapp.services.models.Person;

public interface BlacklistService {

    boolean check(Person p);
}
