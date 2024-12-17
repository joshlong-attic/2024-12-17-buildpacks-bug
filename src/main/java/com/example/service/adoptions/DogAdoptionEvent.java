package com.example.service.adoptions;

import org.springframework.modulith.events.Externalized;

@Externalized("destination::routing-key")
public record DogAdoptionEvent(int dogId) {
}
