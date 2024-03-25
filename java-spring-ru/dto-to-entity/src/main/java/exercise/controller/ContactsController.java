package exercise.controller;

import exercise.dto.ContactCreateDTO;
import exercise.dto.ContactDTO;
import exercise.model.Contact;
import exercise.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contacts")
public class ContactsController {

    @Autowired
    private ContactRepository contactRepository;

    /*
    Создайте в контроллере обработчик, который будет обрабатывать POST-запросы на адрес /contacts.
    Обработчик должен принимать объект класса ContactCreateDTO и возвращать объект класса ContactDTO.
     */
    // BEGIN

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContactDTO create(@RequestBody ContactCreateDTO contactCreateDTO) {

        Contact contact = toEntity(contactCreateDTO);
        contactRepository.save(contact);
        ContactDTO contactDTO = toContactDTO(contact);

        return contactDTO;
    }

    private ContactDTO toContactDTO(Contact contact) {
        var dto = new ContactDTO();
        dto.setId(contact.getId());
        dto.setFirstName(contact.getFirstName());
        dto.setLastName(contact.getLastName());
        dto.setPhone(contact.getPhone());
        dto.setCreatedAt(contact.getCreatedAt());
        dto.setUpdatedAt(contact.getUpdatedAt());
        return dto;
    }

    private Contact toEntity(ContactCreateDTO contactCreateDTO) {
        var contact = new Contact();
        contact.setPhone(contactCreateDTO.getPhone());
        contact.setFirstName(contactCreateDTO.getFirstName());
        contact.setLastName(contactCreateDTO.getLastName());
        return contact;
    }
    // END
}