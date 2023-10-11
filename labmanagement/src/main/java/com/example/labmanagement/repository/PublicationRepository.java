package com.example.labmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.labmanagement.model.Publication;

public interface PublicationRepository extends JpaRepository<Publication, Long> {
    
}