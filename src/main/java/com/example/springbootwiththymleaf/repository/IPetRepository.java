package com.example.springbootwiththymleaf.repository;

import com.example.springbootwiththymleaf.model.Owner;
import com.example.springbootwiththymleaf.model.Pet;

import java.util.List;

public interface IPetRepository {
    Pet findById(Long id);
    List<Pet> findByLastName(String lastName);
    void create(Pet pet);
    Pet  update(Pet pet);
    void delete(Pet pet);
    void  deleteOwnerId(Long ownerId);
}
