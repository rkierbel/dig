package com.skilld.bff.insee;

import com.skilld.bff.messaging.SireneProducer;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class SireneSearchService {

    @Inject
    SireneProducer sireneProducer;

    void sireneSearch() {
        sireneProducer.sendSireneSearchEvent(SireneSearchEvent
                .builder()
                .build());
    }

}
