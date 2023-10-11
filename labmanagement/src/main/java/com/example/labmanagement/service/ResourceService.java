package com.example.labmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.labmanagement.model.Resource;
import com.example.labmanagement.repository.ResourceRepository;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ResourceService {
    @Autowired
    private ResourceRepository resourceRepository;

    public List<Resource> getAllResources() {
        return resourceRepository.findAll();
    }

    @Transactional
    public void saveResource(Resource resource) {
        resource.setAvailability(true);
        resourceRepository.save(resource);
    }

    @Transactional
    public void deleteResource(Long id) {
        resourceRepository.deleteById(id);
    }

    @Transactional
    public void bookResource(Long id) {
        Optional<Resource> optionalResource = resourceRepository.findById(id);

        if (optionalResource.isPresent()) {
            Resource resource = optionalResource.get();
            resource.setAvailability(false);
            resourceRepository.save(resource);
        } 
    }

    @Transactional
    public void unBookResource(Long id) {
        Optional<Resource> optionalResource = resourceRepository.findById(id);

        if (optionalResource.isPresent()) {
            Resource resource = optionalResource.get();
            resource.setAvailability(true);
            resourceRepository.save(resource);
        } 
    }

    public Optional<Resource> findById(Long id) {
        return resourceRepository.findById(id);
    }
}
