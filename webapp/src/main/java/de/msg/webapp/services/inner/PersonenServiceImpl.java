package de.msg.webapp.services.inner;

import de.msg.webapp.repositories.PersonenRepository;
import de.msg.webapp.services.PersonenService;
import de.msg.webapp.services.PersonenServiceException;
import de.msg.webapp.services.mapper.PersonMapper;
import de.msg.webapp.services.models.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PersonenServiceImpl implements PersonenService {

    private final PersonenRepository repo;
    private final PersonMapper mapper;

    private final List<String> blacklist;

    /*
        person == null -> PSE
        vorname == null -> PSE
        vorname < 2 -> PSE
        nachname == null -> PSE
        nachname < 2 -> PSE

        vorname Attila -> PSE

        Exception in underlying service -> PSE

        Happy day save -> false update-> true
     */
    @Override
    public boolean speichern(Person person) throws PersonenServiceException {
        try {
            return speichernImpl(person);
        } catch (RuntimeException e) {
            throw new PersonenServiceException("Fehler beim Speichern",e);
        }
    }

    private boolean speichernImpl(Person person) throws PersonenServiceException {
        checkPerson(person);
        boolean result = repo.existsById(person.getId());
        repo.save(mapper.convert(person));
        return result;
    }

    private void checkPerson(Person person) throws PersonenServiceException {
        validatePerson(person);
        businessCheck(person);
    }

    private void businessCheck(Person person) throws PersonenServiceException {
        if(blacklist.contains(person.getVorname()))
            throw new PersonenServiceException("Antipath");
    }

    private void validatePerson(Person person) throws PersonenServiceException {
        if(person == null)
            throw new PersonenServiceException("Person darf nicht null sein.");
        if(person.getVorname() == null || person.getVorname().length() < 2)
            throw new PersonenServiceException("Vorname zu kurz.");
        if(person.getNachname() == null || person.getNachname().length() < 2)
            throw new PersonenServiceException("Nachname zu kurz.");
    }

    @Override
    public Optional<Person> findeNachId(String id) throws PersonenServiceException {
        try {
            return repo.findById(id).map(mapper::convert);
        } catch (RuntimeException e) {
            throw new PersonenServiceException(e);
        }
    }

    @Override
    public boolean loesche(String id) throws PersonenServiceException {
        try {
            if(repo.existsById(id)) {
                repo.deleteById(id);
                return true;
            }
            return false;
        } catch (RuntimeException e) {
            throw new PersonenServiceException(e);
        }
    }

    @Override
    public Iterable<Person> findeAlle() throws PersonenServiceException {
        try {
            return mapper.convert(repo.findAll());
        } catch (RuntimeException e) {
            throw new PersonenServiceException(e);
        }
    }
}
