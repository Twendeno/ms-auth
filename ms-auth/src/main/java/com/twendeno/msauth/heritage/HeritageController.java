package com.twendeno.msauth.heritage;

import com.twendeno.msauth.heritage.dto.CreateHeritageDto;
import com.twendeno.msauth.heritage.dto.UpdateEmergencyHeritageDto;
import com.twendeno.msauth.heritage.dto.UpdateHeritageDto;
import com.twendeno.msauth.heritage.entity.Heritage;
import com.twendeno.msauth.shared.model.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ApiResponse<Heritage>> createHeritage(@Valid @RequestBody CreateHeritageDto createHeritageDto) {
        return new ResponseEntity<>(heritageService.createHeritage(createHeritageDto), HttpStatus.CREATED);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<List<Heritage>>> getAllHeritages() {
        return ResponseEntity.ok(heritageService.getAllHeritages());
    }

    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<Heritage>> getHeritage(@PathVariable("uuid") String uuid) {
        return ResponseEntity.ok(heritageService.getHeritage(uuid));
    }

    @GetMapping("/{reference}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<Heritage>> getHeritageByReference(@PathVariable("reference") String reference) {
        return ResponseEntity.ok(heritageService.getHeritageByReference(reference));
    }

    @PutMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<Heritage>> updateHeritage(@PathVariable("uuid") String uuid, @Valid @RequestBody UpdateHeritageDto updateHeritageDto) {
        return ResponseEntity.ok(heritageService.updateHeritage(uuid, updateHeritageDto));
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<ApiResponse<String>> deleteHeritage(@PathVariable("uuid") String uuid) {
        return new ResponseEntity<>(heritageService.deleteHeritage(uuid), HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{uuid}/emergency")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<Heritage>> updateEmergencyHeritage(@PathVariable("uuid") String uuid, @Valid @RequestBody UpdateEmergencyHeritageDto updateEmergencyHeritageDto) {
        return ResponseEntity.ok(heritageService.updateEmergencyHeritage(uuid, updateEmergencyHeritageDto));
    }

}
