package com.example.springbootwiththymleaf.controller;

import com.example.springbootwiththymleaf.model.Owner;
import com.example.springbootwiththymleaf.service.OwnerNotFoundException;
import com.example.springbootwiththymleaf.service.PetClinicService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.LinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import  static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import java.net.URI;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/owner")
public class PetClinicRestController  {
    private final PetClinicService petClinicService;
@GetMapping(value = "/getOwners")
    public ResponseEntity<List<Owner>> getOwners() {
        try {
            List<Owner> owners = petClinicService.findOwners();
            return ResponseEntity.ok(owners);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
      @GetMapping(value = "/ownerLast")
       public ResponseEntity<List<Owner>> getOwnerByLastName( @RequestParam("ln") String lastName){
          try {
              List<Owner> owners = petClinicService.findOwners(lastName);
              return ResponseEntity.ok(owners);
          }catch (Exception e) {
              return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
          }
              /*


                 List<Owner> owners=petClinicService.findOwners(lastName);
          } catch (Exception e) {
              return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

          }
               */

    }
       //GET METHOD WITH USING ID
       @GetMapping(value = "/ownerId/{id}")
    public ResponseEntity<Owner> getOwnerById( @PathVariable("id") Long id){
        try{
            Owner owner=petClinicService.findOwner(id);
            return ResponseEntity.ok(owner);
        }catch (OwnerNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
  @GetMapping(value = "/owners")
    public ResponseEntity<Owner> getOwners(@RequestBody Owner owner ){
        try{

            Long id=owner.getId();
            URI location=ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{id}")
                    .buildAndExpand(owner).toUri();
            return  ResponseEntity.created(location).build();
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
@GetMapping(value = "/owner/{id}",produces = "application/json")
    public ResponseEntity<?>getOwnerAsHateoas(@PathVariable("id") Long id){
    try{
        Owner owner=petClinicService.findOwner(id);
      getOwnerAsHateoas(owner.getId());
        return ResponseEntity.ok(owner);
    }catch (OwnerNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }catch (Exception ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}

}
