package de.msg.webapp.services.inner;

import de.msg.webapp.repositories.PersonenRepository;
import de.msg.webapp.services.PersonenService;
import de.msg.webapp.services.PersonenServiceException;
import de.msg.webapp.services.mapper.PersonMapper;
import de.msg.webapp.services.models.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PersonenServiceImplTest {

    @InjectMocks private PersonenServiceImpl objectUnderTest;
    @Mock private PersonenRepository repoMock;
    @Mock private PersonMapper mapperMock;
    @Mock private List<String> blacklistMock;

    @Nested
    @DisplayName("Tests für Methode speichern")
    class TestFuerSpeichern {

        @Test
        void parameterNull_throwsPersonenServiceException() throws PersonenServiceException {
            PersonenServiceException ex = assertThrows(PersonenServiceException.class,()->objectUnderTest.speichern(null));
            assertEquals("Person darf nicht null sein.", ex.getMessage());
        }

        @Test
        void vornameNull_throwsPersonenServiceException() throws PersonenServiceException {
            Person failPerson = Person.builder().id("SomeId").vorname(null).nachname("Doe").build();
            PersonenServiceException ex = assertThrows(PersonenServiceException.class,()->objectUnderTest.speichern(failPerson));
            assertEquals("Vorname zu kurz.", ex.getMessage());
        }

        @Test
        void vornamezukurz_throwsPersonenServiceException() throws PersonenServiceException {
            Person failPerson = Person.builder().id("SomeId").vorname("J").nachname("Doe").build();
            PersonenServiceException ex = assertThrows(PersonenServiceException.class,()->objectUnderTest.speichern(failPerson));
            assertEquals("Vorname zu kurz.", ex.getMessage());
        }

        @Test
        void ausschlussVorname_throwsPersonenServiceException() throws PersonenServiceException {
            Person validPerson = Person.builder().id("SomeId").vorname("John").nachname("Doe").build();

            Mockito.when(blacklistMock.contains(Mockito.anyString())).thenReturn(true);

            PersonenServiceException ex = assertThrows(PersonenServiceException.class,()->objectUnderTest.speichern(validPerson));
            assertEquals("Antipath", ex.getMessage());
        }
    }
}