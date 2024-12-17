package com.example.service.adoptions;

import com.example.service.adoptions.validation.Validation;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.annotation.Id;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;


@Controller
@ResponseBody
class DogAdoptionController {

    private final DogAdoptionService dogAdoptionService;

    DogAdoptionController(DogAdoptionService dogAdoptionService) {
        this.dogAdoptionService = dogAdoptionService;
    }

    @PostMapping("/dogs/{dogId}/adoptions")
    void adopt(@PathVariable int dogId, @RequestBody Map<String, String> owner) {
        this.dogAdoptionService.adopt(dogId, owner.get("name"));
    }
}

@Service
@Transactional
class DogAdoptionService {


    private final DogRepository dogRepository;
    private final ApplicationEventPublisher publisher;
    
    private final Validation validation;

    DogAdoptionService(DogRepository dogRepository, ApplicationEventPublisher publisher, Validation validation) {
        this.dogRepository = dogRepository;
        this.publisher = publisher;
        this.validation = validation;
    }

    void adopt(int dogId, String owner) {
        dogRepository.findById(dogId).ifPresent(dog -> {
            var newDog = dogRepository.save(new Dog(dog.id(), dog.name(),
                    dog.description(), owner));
            this.publisher.publishEvent(new DogAdoptionEvent(newDog.id()));
            System.out.println("adopted " + newDog + "!");
        });
    }
}

// look mom, no Lombok!!
record Dog(@Id int id, String name, String description, String owner) {
}

interface DogRepository extends ListCrudRepository<Dog, Integer> {
}