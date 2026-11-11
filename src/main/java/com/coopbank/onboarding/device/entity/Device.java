package com.coopbank.onboarding.device.entity;

import com.coopbank.onboarding.customer.entity.Customer;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "devices")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String deviceId;

    private String deviceName;

    private String ipAddress;

    @Enumerated(EnumType.STRING)
    private DeviceStatus status;

    private LocalDateTime registeredAt;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}