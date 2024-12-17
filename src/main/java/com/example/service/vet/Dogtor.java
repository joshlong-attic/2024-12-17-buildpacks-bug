package com.example.service.vet;

import com.example.service.adoptions.DogAdoptionEvent;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Service;

@Service
class Dogtor {
    
    @ApplicationModuleListener
    void checkup(DogAdoptionEvent dogId) throws Exception {
        System.out.println("start: checking up dog " + dogId + ".");
        Thread.sleep(5000);
        System.out.println("stop: checking up dog " + dogId + ".");
    }
}
