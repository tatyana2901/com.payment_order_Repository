package com.payment_order.mapper;

import com.payment_order.DTO.PaymentDTO;
import com.payment_order.Entity.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    Payment toEntity(PaymentDTO paymentDTO);

    PaymentDTO toPaymentDTO(Payment payment);

}
