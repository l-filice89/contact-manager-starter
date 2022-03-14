package com.programming.techie;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ContactManagerTest {

    private ContactManager contactManager;

    @BeforeAll
    public void setupAll() {
        System.out.println("Should print before all tests");
    }

    @BeforeEach
    public void setup() {
        contactManager = new ContactManager();
    }

    @Test
    @DisplayName("Shuold create contact")
    @Disabled
    public void shouldCreateContact() {
        contactManager.addContact("John", "Doe", "0123456789");
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1, contactManager.getAllContacts().size());
        Assertions.assertTrue(contactManager.getAllContacts().stream()
                .anyMatch(contact -> contact.getFirstName().equals("John")
                        && contact.getLastName().equals("Doe") && contact.getPhoneNumber().equals("0123456789")));
    }

    @Test
    @DisplayName("Should not create contact when First Name is null")
    public void shouldThrowRuntimeExceptionWhenFirstNameIsNull() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            contactManager.addContact(null, "Doe", "0123456789");
        });
    }

    @Test
    @DisplayName("Should not create contact when Last Name is null")
    public void shouldThrowRuntimeExceptionWhenLastNameIsNull() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            contactManager.addContact("John", null, "0123456789");
        });
    }

    @Test
    @DisplayName("Should not create contact when Phone Number is null")
    public void shouldThrowRuntimeExceptionWhenPhoneNumberIsNull() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            contactManager.addContact("John", "Doe", null);
        });
    }

    @Test
    @DisplayName("Shuold create contact only on MAC OS")
    @EnabledOnOs(value = OS.MAC, disabledReason = "Enabled only on MAC OS")
    public void shouldCreateContactOnlyOnMAC() {
        contactManager.addContact("John", "Doe", "0123456789");
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1, contactManager.getAllContacts().size());
        Assertions.assertTrue(contactManager.getAllContacts().stream()
                .anyMatch(contact -> contact.getFirstName().equals("John")
                        && contact.getLastName().equals("Doe") && contact.getPhoneNumber().equals("0123456789")));
    }

    @Test
    @DisplayName("Shuold create contact only on Windows OS")
    @EnabledOnOs(value = OS.WINDOWS, disabledReason = "Enabled only on Windows OS")
    public void shouldCreateContactOnlyOnWindows() {
        contactManager.addContact("John", "Doe", "0123456789");
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1, contactManager.getAllContacts().size());
        Assertions.assertTrue(contactManager.getAllContacts().stream()
                .anyMatch(contact -> contact.getFirstName().equals("John")
                        && contact.getLastName().equals("Doe") && contact.getPhoneNumber().equals("0123456789")));
    }

    @Test
    @DisplayName("Test contact creation on Developer Machine")
    public void shouldTestContactCreationOnDEV() {
        Assumptions.assumeTrue("DEV".equals(System.getProperty("ENV")));
        contactManager.addContact("John", "Doe", "0123456789");
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1, contactManager.getAllContacts().size());
        Assertions.assertTrue(contactManager.getAllContacts().stream()
                .anyMatch(contact -> contact.getFirstName().equals("John")
                        && contact.getLastName().equals("Doe") && contact.getPhoneNumber().equals("0123456789")));
    }

    @Nested
    class RepeatedNestedTest {

        @RepeatedTest(value = 5, name = "Repeating contact creation test {currentRepetition} of {totalRepetitions}")
        @DisplayName("Test contact creation 5 times")
        public void shouldTestContactCreationRepeatedly() {
            contactManager.addContact("John", "Doe", "0123456789");
            Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
            Assertions.assertEquals(1, contactManager.getAllContacts().size());
            Assertions.assertTrue(contactManager.getAllContacts().stream()
                    .anyMatch(contact -> contact.getFirstName().equals("John")
                            && contact.getLastName().equals("Doe") && contact.getPhoneNumber().equals("0123456789")));
        }

    }

    @Nested
    class ParametrizedNestedTest {

        @ParameterizedTest
        @ValueSource(strings = {"0123456789", "0123456789", "0123456789"})
        @DisplayName("Test contact creation using Values Source")
        public void shouldTestContactCreationUsingValuesSource(String phoneNumber) {
            contactManager.addContact("John", "Doe", phoneNumber);
            Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
            Assertions.assertEquals(1, contactManager.getAllContacts().size());
            Assertions.assertTrue(contactManager.getAllContacts().stream()
                    .anyMatch(contact -> contact.getFirstName().equals("John")
                            && contact.getLastName().equals("Doe") && contact.getPhoneNumber().equals("0123456789")));
        }

        @ParameterizedTest
        @CsvSource({"0123456789", "0123456789", "0123456789"})
        @DisplayName("CSV Source case - Phone number should match the required format")
        public void shouldTestPhoneNumberFormatUsingCSVSource(String phoneNumber) {
            contactManager.addContact("John", "Doe", phoneNumber);
            Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
            Assertions.assertEquals(1, contactManager.getAllContacts().size());
            Assertions.assertTrue(contactManager.getAllContacts().stream()
                    .anyMatch(contact -> contact.getFirstName().equals("John")
                            && contact.getLastName().equals("Doe") && contact.getPhoneNumber().equals("0123456789")));
        }

        @ParameterizedTest
        @CsvFileSource(resources = "/data.csv")
        @DisplayName("CSV File Source case - Phone number should match the required format")
        public void shouldTestPhoneNumberFormatUsingCSVFileSource(String phoneNumber) {
            contactManager.addContact("John", "Doe", phoneNumber);
            Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
            Assertions.assertEquals(1, contactManager.getAllContacts().size());
            Assertions.assertTrue(contactManager.getAllContacts().stream()
                    .anyMatch(contact -> contact.getFirstName().equals("John")
                            && contact.getLastName().equals("Doe") && contact.getPhoneNumber().equals("0123456789")));
        }
    }



    @ParameterizedTest
    @MethodSource("phoneNumberList")
    @DisplayName("Test contact creation using Method Source")
    public void shouldTestContactCreationUsingMethodSource(String phoneNumber) {
        contactManager.addContact("John", "Doe", phoneNumber);
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1, contactManager.getAllContacts().size());
        Assertions.assertTrue(contactManager.getAllContacts().stream()
                .anyMatch(contact -> contact.getFirstName().equals("John")
                        && contact.getLastName().equals("Doe") && contact.getPhoneNumber().equals("0123456789")));
    }


    private static List<String> phoneNumberList() {
        return Arrays.asList("0123456789", "0123456789", "0123456789");
    }



    @AfterEach
    public void tearDown() {
        System.out.println("Should execute after each test");
    }

    @AfterAll
    public void tearDownAll() {
        System.out.println("Should be executed at the end of the test");
    }
}