package com.example.labmanagement.service;

import com.example.labmanagement.model.Publication;
import com.example.labmanagement.repository.PublicationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PublicationService {

    private final PublicationRepository publicationRepository;

    public PublicationService(PublicationRepository publicationRepository) {
        this.publicationRepository = publicationRepository;
    }

    // Save a publication
    public Publication savePublication(Publication publication) {
        return publicationRepository.save(publication);
    }

    // Get all publications
    public List<Publication> getAllPublications() {
        return publicationRepository.findAll();
    }

    // Get a publication by ID
    public Publication getPublicationById(Long id) {
        Optional<Publication> optionalPublication = publicationRepository.findById(id);
        return optionalPublication.orElse(null);
    }

    // Delete a publication by ID
    public void deletePublicationById(Long id) {
        publicationRepository.deleteById(id);
    }
}
