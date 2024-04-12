package com.skilld.bff.insee;

import com.skilld.bff.messaging.SirenProducer;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class SirenSearchService {

    @Inject
    SirenProducer sirenProducer;

    void sirenSearch() {
        sirenProducer.sendSirenSearchEvent(SirenSearchEvent.builder().build());
    }

}
