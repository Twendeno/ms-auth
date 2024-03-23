package com.twendeno.msauth.heritage;

import com.twendeno.msauth.heritage.dto.CreateHeritageDto;
import com.twendeno.msauth.heritage.dto.UpdateEmergencyHeritageDto;
import com.twendeno.msauth.heritage.dto.UpdateHeritageDto;
import com.twendeno.msauth.heritage.entity.Heritage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RequestMapping("heritage")
@RestController
public class HeritageController {
    private final HeritageService heritageService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Heritage createHeritage(@RequestBody CreateHeritageDto createHeritageDto) {
        return heritageService.createHeritage(createHeritageDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Heritage> getAllHeritages() {
        return heritageService.getAllHeritages();
    }

    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public Heritage getHeritage(@PathVariable("uuid") String uuid) {
        return heritageService.getHeritage(uuid);
    }

    @GetMapping("/{reference}")
    @ResponseStatus(HttpStatus.OK)
    public Heritage getHeritageByReference(@PathVariable("reference") String reference) {
        return heritageService.getHeritageByReference(reference);
    }

    @PutMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public Heritage updateHeritage(@PathVariable("uuid") String uuid, @RequestBody UpdateHeritageDto updateHeritageDto) {
        return heritageService.updateHeritage(uuid, updateHeritageDto);
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteHeritage(@PathVariable("uuid") String uuid) {
        heritageService.deleteHeritage(uuid);
    }

    @PutMapping("/{uuid}/emergency")
    @ResponseStatus(HttpStatus.OK)
    public Heritage updateEmergencyHeritage(@PathVariable("uuid") String uuid, @RequestBody UpdateEmergencyHeritageDto updateEmergencyHeritageDto) {
        return heritageService.updateEmergencyHeritage(uuid, updateEmergencyHeritageDto);
    }

}
