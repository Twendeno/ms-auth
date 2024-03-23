package com.twendeno.msauth.heritage;

import com.twendeno.msauth.business.BusinessRepository;
import com.twendeno.msauth.business.entity.Business;
import com.twendeno.msauth.heritage.dto.CreateHeritageDto;
import com.twendeno.msauth.heritage.dto.UpdateEmergencyHeritageDto;
import com.twendeno.msauth.heritage.dto.UpdateHeritageDto;
import com.twendeno.msauth.heritage.entity.Heritage;
import com.twendeno.msauth.heritage.model.BoxType;
import com.twendeno.msauth.heritage.model.CarType;
import com.twendeno.msauth.heritage.model.FuelType;
import com.twendeno.msauth.heritage.model.TransmissionType;
import com.twendeno.msauth.shared.Utils;
import com.twendeno.msauth.user.UserRepository;
import com.twendeno.msauth.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Service
public class HeritageService {

    private final HeritageRepository heritageRepository;
    private final BusinessRepository businessRepository;
    private final UserRepository userRepository;

    public Heritage createHeritage(CreateHeritageDto createHeritageDto) {
        // Get Business by name
        Business business = businessRepository.findByName(createHeritageDto.businessName()).orElseThrow(() -> new RuntimeException("Business not found"));

        // Get User by email
        User user = userRepository.findByEmail(createHeritageDto.userEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // generate reference
        String reference = Utils.generateSubscriptionReference();

        // Create Heritage
        Heritage heritage = Heritage.builder()
                .business(business)
                .lastUserUpdate(user.getEmail())
                .reference(reference)
                .matriculation(createHeritageDto.matriculation())
                .brand(createHeritageDto.brand())
                .model(createHeritageDto.model())
                .mileage(createHeritageDto.mileage())
                .build();
        return heritageRepository.save(heritage);
    }

    public Heritage updateHeritage(String uuid, UpdateHeritageDto updateHeritageDto) {
        Heritage heritage = heritageRepository.findById(UUID.fromString(uuid)).orElseThrow(() -> new RuntimeException("Heritage not found"));

        // Get User by email
        User user = userRepository.findByEmail(updateHeritageDto.userEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        heritage.setLastUserUpdate(user.getEmail());
        heritage.setMatriculation(updateHeritageDto.matriculation());

        heritage.setBuyDate(Utils.convertDateToInstant(updateHeritageDto.buyDate()));
        heritage.setFirstRegistration(Utils.convertDateToInstant(updateHeritageDto.firstRegistration()));

        heritage.setMileage(updateHeritageDto.mileage());
        heritage.setPrice(updateHeritageDto.price());
        heritage.setSellingPrice(updateHeritageDto.sellingPrice());
        heritage.setRentPrice(updateHeritageDto.rentPrice());
        heritage.setDeposit(updateHeritageDto.deposit());

        heritage.setLastTechnicalInspection(Utils.convertDateToInstant(updateHeritageDto.lastTechnicalInspection()));
        heritage.setNextTechnicalInspection(Utils.convertDateToInstant(updateHeritageDto.nextTechnicalInspection()));
        heritage.setLastMaintenance(Utils.convertDateToInstant(updateHeritageDto.lastMaintenance()));
        heritage.setNextMaintenance(Utils.convertDateToInstant(updateHeritageDto.nextMaintenance()));
        heritage.setLastOilChange(Utils.convertDateToInstant(updateHeritageDto.lastOilChange()));
        heritage.setNextOilChange(Utils.convertDateToInstant(updateHeritageDto.nextOilChange()));
        heritage.setLastInsurance(Utils.convertDateToInstant(updateHeritageDto.lastInsurance()));
        heritage.setNewInsurance(Utils.convertDateToInstant(updateHeritageDto.newInsurance()));

        heritage.setBrand(updateHeritageDto.brand());
        heritage.setModel(updateHeritageDto.model());


        heritage.setCarType(getCarType(updateHeritageDto.carType()));
        heritage.setFiscalPower(updateHeritageDto.fiscalPower());
        heritage.setFuelType(getFuelType(updateHeritageDto.fuelType()));
        heritage.setTransmission(getTransmissionType(updateHeritageDto.transmission()));
        heritage.setBoxType(getBoxType(updateHeritageDto.boxType()));

        heritage.setNumberOfDoors(updateHeritageDto.doors());
        heritage.setNumberOfSeats(updateHeritageDto.seats());
        heritage.setNumberOfGears(updateHeritageDto.gears());

        heritage.setLength(updateHeritageDto.length());
        heritage.setWidth(updateHeritageDto.width());
        heritage.setHeight(updateHeritageDto.height());

        return heritageRepository.save(heritage);
    }

    public Heritage updateEmergencyHeritage(String uuid, UpdateEmergencyHeritageDto emergencyHeritageDto) {
        Heritage heritage = heritageRepository.findById(UUID.fromString(uuid)).orElseThrow(() -> new RuntimeException("Heritage not found"));

        heritage.setAccident(emergencyHeritageDto.accident());
        heritage.setBreakdown(emergencyHeritageDto.breakdown());
        heritage.setMaintenance(emergencyHeritageDto.maintenance());
        heritage.setTechnicalInspection(emergencyHeritageDto.technicalInspection());
        heritage.setOilChange(emergencyHeritageDto.oilChange());

        return heritageRepository.save(heritage);
    }

    public void deleteHeritage(String uuid) {
        Heritage heritage = heritageRepository.findById(UUID.fromString(uuid)).orElseThrow(() -> new RuntimeException("Heritage not found"));
        heritageRepository.delete(heritage);
    }

    public List<Heritage> getAllHeritages() {
        return heritageRepository.findAll();
    }

    public Heritage getHeritage(String uuid) {
        return heritageRepository.findById(UUID.fromString(uuid)).orElseThrow(() -> new RuntimeException("Heritage not found"));
    }

    public Heritage getHeritageByReference(String reference) {
        return heritageRepository.findByReference(reference).orElseThrow(() -> new RuntimeException("Heritage not found"));
    }

    private CarType getCarType(String carType) {
        return switch (carType.toUpperCase()) {
            case "SUV" -> CarType.SUV;
            case "TRUCK" -> CarType.TRUCK;
            case "VAN" -> CarType.VAN;
            case "MINIVAN" -> CarType.MINIVAN;
            case "MONOSPACE" -> CarType.MONOSPACE;
            case "MINIBUS" -> CarType.MINIBUS;
            case "BERLINE" -> CarType.BERLINE;
            case "COUPE" -> CarType.COUPE;
            case "BREAK" -> CarType.BREAK;
            case "CABRIOLET" -> CarType.CABRIOLET;
            case "CONVERTIBLE" -> CarType.CONVERTIBLE;
            case "SPORTS_CAR" -> CarType.SPORTS_CAR;
            case "HYBRID" -> CarType.HYBRID;
            case "MINISPACE" -> CarType.MINISPACE;
            default -> CarType.OTHER;
        };
    }

    private FuelType getFuelType(String fuelType) {
        return switch (fuelType.toUpperCase()) {
            case "DIESEL" -> FuelType.DIESEL;
            case "GASOLINE" -> FuelType.GASOLINE;
            case "ELECTRIC" -> FuelType.ELECTRIC;
            case "HYBRID" -> FuelType.HYBRID;
            case "LPG" -> FuelType.LPG;
            case "CNG" -> FuelType.CNG;
            case "ETHANOL" -> FuelType.ETHANOL;
            case "BIOFUEL" -> FuelType.BIOFUEL;
            case "HYDROGEN" -> FuelType.HYDROGEN;
            default -> throw new IllegalStateException("Unexpected value: " + fuelType.toUpperCase());
        };
    }

    private TransmissionType getTransmissionType(String transmissionType) {
        return switch (transmissionType.toUpperCase()) {
            case "INTEGRAL" -> TransmissionType.INTEGRAL;
            case "PROPULSION" -> TransmissionType.PROPULSION;
            case "TRACTION" -> TransmissionType.TRACTION;
            default -> throw new IllegalStateException("Unexpected value: " + transmissionType.toUpperCase());
        };
    }

    private BoxType getBoxType(String boxType) {
        return switch (boxType.toUpperCase()) {
            case "AUTOMATIC" -> BoxType.AUTOMATIC;
            case "SEMI_AUTOMATIC" -> BoxType.SEMI_AUTOMATIC;
            default -> BoxType.MANUAL;
        };
    }


}
