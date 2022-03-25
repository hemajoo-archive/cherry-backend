/*
 * (C) Copyright Resse Christophe 2021 - All Rights Reserved
 * -----------------------------------------------------------------------------------------------
 * All information contained herein is, and remains the property of
 * Resse Christophe. and its suppliers, if any. The intellectual and technical
 * concepts contained herein are proprietary to Resse C. and its
 * suppliers and may be covered by U.S. and Foreign Patents, patents
 * in process, and are protected by trade secret or copyright law.
 *
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained from
 * Resse Christophe (christophe.resse@gmail.com).
 * -----------------------------------------------------------------------------------------------
 */
package com.hemajoo.commerce.cherry.backend.rest.controller.person;

import com.hemajoo.commerce.cherry.backend.commons.type.EntityType;
import com.hemajoo.commerce.cherry.backend.persistence.base.entity.EntityFactory;
import com.hemajoo.commerce.cherry.backend.persistence.base.entity.ServerEntity;
import com.hemajoo.commerce.cherry.backend.persistence.base.entity.ServiceFactoryPerson;
import com.hemajoo.commerce.cherry.backend.persistence.person.converter.EmailAddressConverter;
import com.hemajoo.commerce.cherry.backend.persistence.person.entity.ServerEmailAddressEntity;
import com.hemajoo.commerce.cherry.backend.persistence.person.randomizer.EmailAddressRandomizer;
import com.hemajoo.commerce.cherry.backend.persistence.person.service.EmailAddressServiceCore;
import com.hemajoo.commerce.cherry.backend.persistence.person.validation.constraint.ValidEmailAddressForCreation;
import com.hemajoo.commerce.cherry.backend.persistence.person.validation.constraint.ValidEmailAddressForUpdate;
import com.hemajoo.commerce.cherry.backend.persistence.person.validation.constraint.ValidEmailAddressId;
import com.hemajoo.commerce.cherry.backend.persistence.person.validation.engine.EmailAddressValidationEngine;
import com.hemajoo.commerce.cherry.backend.persistence.person.validation.validator.EmailAddressValidatorForUpdate;
import com.hemajoo.commerce.cherry.backend.shared.base.converter.GenericEntityConverter;
import com.hemajoo.commerce.cherry.backend.shared.base.entity.EntityException;
import com.hemajoo.commerce.cherry.backend.shared.person.address.email.ClientEmailAddress;
import com.hemajoo.commerce.cherry.backend.shared.person.address.email.EmailAddressException;
import com.hemajoo.commerce.cherry.backend.shared.person.address.email.SearchEmailAddress;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * REST controller providing service endpoints to manage the email addresses.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Tag(name = "Email Addresses")
@ComponentScan(basePackageClasses = { EmailAddressValidatorForUpdate.class, EmailAddressServiceCore.class })
//@GroupSequence( { EmailAddressController.class, BasicValidation.class, ExtendedValidation.class } )
@Validated
@RestController
@RequestMapping("/api/v1/person/email")
public class EmailAddressController
{
    /**
     * Server entity factory.
     */
    @Autowired
    private ServiceFactoryPerson servicePerson;

    /**
     * Email address converter.
     */
    @Autowired
    private EmailAddressConverter converterEmailAddress;

    /**
     * Email address validation engine.
     */
    @Autowired
    private EmailAddressValidationEngine validationEmailAddress;

    @Autowired
    private EntityFactory factory;

    /**
     * Service to count the number of email addresses.
     * @return Number of email addresses.
     */
    @Operation(summary = "Count email addresses")
    @GetMapping("/count")
    public long count()
    {
        return servicePerson.getEmailAddressService().count();
    }

    /**
     * Service to retrieve an email address.
     * @param id Email address identifier.
     * @return Email address matching the given identifier.
     */
    @Operation(summary = "Retrieve an email address")
    @GetMapping("/get/{id}")
    public ResponseEntity<ClientEmailAddress> get(
            @Parameter(description = "Email address identifier", required = true)
            @Valid @ValidEmailAddressId // Handles email id validation automatically, need both annotations!
            @NotNull
            @PathVariable String id)
    {
        ServerEmailAddressEntity serverEmailAddress = servicePerson.getEmailAddressService().findById(UUID.fromString(id));
        return ResponseEntity.ok(converterEmailAddress.fromServerToClient(serverEmailAddress));
    }

    /**
     * Service to add a new email address.
     * @param emailAddress Email address.
     * @return Newly created email address.
     * @throws EmailAddressException Thrown to indicate an error occurred while trying to create an email address.
     */
    @Operation(summary = "Create a new email address")
    @PostMapping("/create")
    public ResponseEntity<ClientEmailAddress> create(
            @Parameter(description = "Email address", required = true)
            @Valid @ValidEmailAddressForCreation @RequestBody ClientEmailAddress emailAddress) throws EmailAddressException
    {
        ServerEmailAddressEntity serverEmailAddress = converterEmailAddress.fromClientToServer(emailAddress);
        serverEmailAddress = servicePerson.getEmailAddressService().save(serverEmailAddress);

        return ResponseEntity.ok(converterEmailAddress.fromServerToClient(serverEmailAddress));
    }

    /**
     * Service to create a random email address.
     * @param parentId Entity identifier being the owner of the email address to create.
     * @param parentType Entity type being the owner of the email address to create.
     * @return Randomly generated email address.
     * @throws EmailAddressException Thrown to indicate an error occurred while trying to create a random email address.
     */
    @Operation(summary = "Create a new random email address")
    @PostMapping("/random")
    public ResponseEntity<ClientEmailAddress> random(
            @Parameter(description = "Parent entity type", name = "parentType", required = true)
            @NotNull @RequestParam EntityType parentType,
            @Parameter(description = "Entity identifier (UUID) being the owner of the new random email address", name = "parentId", required = true)
            /*@Valid @ValidPersonId*/ @NotNull @RequestParam String parentId) throws EntityException
    {
        ServerEmailAddressEntity serverEmail = EmailAddressRandomizer.generateServerEntity(false);

        ServerEntity parent = (ServerEntity) factory.from(parentType, UUID.fromString(parentId));
        serverEmail.setParent(parent);
        serverEmail = servicePerson.getEmailAddressService().save(serverEmail);

        return ResponseEntity.ok(converterEmailAddress.fromServerToClient(serverEmail));
    }

    /**
     * Service to update an email address.
     * @param email Email address to update.
     * @return Updated email address.
     * @throws EmailAddressException Thrown to indicate an error occurred while trying to update an email address.
     */
    @Operation(summary = "Update an email address"/*, notes = "Update an email address given the new values."*/)
    @PutMapping("/update")
    //@Transactional
    public ResponseEntity<ClientEmailAddress> update(
            @NotNull @Valid @ValidEmailAddressForUpdate @RequestBody ClientEmailAddress email) throws EmailAddressException
    {
        try
        {
            //emailAddressRuleEngine.validateEmailAddressId(email);
            validationEmailAddress.validateEmailForUpdate(email);

            ServerEmailAddressEntity source = converterEmailAddress.fromClientToServer(email);
            ServerEmailAddressEntity updated = servicePerson.getEmailAddressService().update(source);
            ClientEmailAddress client = converterEmailAddress.fromServerToClient(updated);

            return ResponseEntity.ok(client);
        }
        catch (EntityException e)
        {
            throw new EmailAddressException(e);
        }
    }

    /**
     * Service to delete an email address given its identifier.
     * @param id Email address identifier.
     * @return Confirmation message.
     */
    @Operation(summary = "Delete an email address")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(
            @Parameter(description = "Email address identifier (UUID)", required = true)
            @NotNull @Valid @ValidEmailAddressId @PathVariable String id)
    {
        servicePerson.getEmailAddressService().deleteById(UUID.fromString(id));

        return ResponseEntity.ok(String.format("Email address id: '%s' has been deleted successfully!", id));
    }

    /**
     * Service to search for email addresses given some criteria.
     * @param search Email address specification object.
     * @return List of matching email addresses.
     * @throws EmailAddressException Thrown to indicate an error occurred while trying to search for email addresses.
     */
    @Operation(summary = "Search for email addresses", description = "Search for email addresses matching the given predicates. Fill only the fields to be taken into account.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "No email address found matching the given criteria"),
            @ApiResponse(responseCode = "400", description = "Missing or invalid request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")})
    @PatchMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE) // PATCH method Because a GET method cannot have a request body!
    public ResponseEntity<List<ClientEmailAddress>> search(final @RequestBody @NotNull SearchEmailAddress search) throws EmailAddressException
    {
        EmailAddressValidationEngine.isSearchValid(search);

        List<ClientEmailAddress> clients = servicePerson.getEmailAddressService().search(search)
                .stream()
                .map(element -> converterEmailAddress.fromServerToClient(element))
                .collect(Collectors.toList());

        return ResponseEntity.ok(clients);
    }

    /**
     * Service to query for email addresses identifiers matching some criteria.
     * @param search Email address specification criteria.
     * @return List of matching email address identifiers.
     * @throws EmailAddressException Thrown to indicate an error occurred while trying to query for email addresses.
     */
    @Operation(summary = "Query email addresses", description = "Returns a list of email addresses matching the given criteria.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful query"),
            @ApiResponse(responseCode = "404", description = "No email address found matching the given criteria"),
            @ApiResponse(responseCode = "400", description = "Missing or invalid request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")})
    @GetMapping("/query")
    public ResponseEntity<List<String>> query(final @NotNull SearchEmailAddress search) throws EmailAddressException
    {
        EmailAddressValidationEngine.isSearchValid(search);

        List<ClientEmailAddress> clients = servicePerson.getEmailAddressService().search(search)
                .stream()
                .map(element -> converterEmailAddress.fromServerToClient(element))
                .collect(Collectors.toList());

        return ResponseEntity.ok(GenericEntityConverter.toIdList(clients));
    }
}
