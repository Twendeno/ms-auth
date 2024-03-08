package com.twendeno.msauth.licence;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LicenceController {

    @GetMapping("/licence")
    public void licences() {
        System.out.println("Licence listed");
    }
}
