package com.dobe.locmns.startup;

import com.dobe.locmns.services.Impl.InitialisationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppStartupRunner implements CommandLineRunner {

    private final InitialisationServiceImpl initialisationService;
    @Override
    public void run(String... args) throws Exception {
        initialisationService.initRoleAndUser();

    }
}
