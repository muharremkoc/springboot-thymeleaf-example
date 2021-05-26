package com.example.springbootwiththymleaf.repository;

import com.example.springbootwiththymleaf.model.Owner;

import java.util.List;

public interface IOwnerRepository {
    List<Owner> findAll();
    Owner findById(Long id);
    List<Owner> findByLastName(String lastName);
    void create(Owner owner);
    Owner  update(Owner owner);
    void  delete(Long id);
}
