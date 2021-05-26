package com.example.springbootwiththymleaf.repository;

import com.example.springbootwiththymleaf.model.Owner;
import com.example.springbootwiththymleaf.service.OwnerNotFoundException;

import java.util.List;

public interface IPetClinicService {
     List<Owner> findOwners();
     List<Owner> findOwners(String lastName);
     Owner findOwner(Long id) throws OwnerNotFoundException;
     void createOwner(Owner owner);
     void updateOwner(Owner owner);
     void deleteOwner(Long id);

}
