package com.coopbank.onboarding.device.controller;

import com.coopbank.onboarding.device.dto.RegisterDeviceRequest;
import com.coopbank.onboarding.device.entity.Device;
import com.coopbank.onboarding.device.service.DeviceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;

    @PostMapping("/register")
    public Device registerDevice(

            @Valid
            @RequestBody
            RegisterDeviceRequest request

    ) {

        return deviceService
                .registerDevice(
                        request
                );
    }
}