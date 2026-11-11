package com.coopbank.onboarding.device.service;

import com.coopbank.onboarding.customer.entity.Customer;
import com.coopbank.onboarding.customer.repository.CustomerRepository;
import com.coopbank.onboarding.device.dto.RegisterDeviceRequest;
import com.coopbank.onboarding.device.entity.Device;
import com.coopbank.onboarding.device.entity.DeviceStatus;
import com.coopbank.onboarding.device.repository.DeviceRepository;
import com.coopbank.onboarding.exception.ResourceNotFoundException;
import com.coopbank.onboarding.exception.VerificationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;

    private final CustomerRepository customerRepository;

    public Device registerDevice(
            RegisterDeviceRequest request
    ) {

        deviceRepository
                .findByDeviceId(
                        request.deviceId()
                )
                .ifPresent(
                        device -> {
                            throw new VerificationException(
                                    "Device already registered"
                            );
                        }
                );

        Customer customer =
                customerRepository
                        .findById(
                                request.customerId()
                        )
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Customer not found"
                                )
                        );

        Device device =
                Device.builder()
                        .deviceId(
                                request.deviceId()
                        )
                        .deviceName(
                                request.deviceName()
                        )
                        .ipAddress(
                                request.ipAddress()
                        )
                        .status(
                                DeviceStatus.TRUSTED
                        )
                        .registeredAt(
                                LocalDateTime.now()
                        )
                        .customer(
                                customer
                        )
                        .build();

        return deviceRepository.save(
                device
        );
    }
}