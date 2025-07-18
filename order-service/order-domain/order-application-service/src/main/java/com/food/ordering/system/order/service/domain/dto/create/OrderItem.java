package com.food.ordering.system.order.service.domain.dto.create;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class OrderItem {

  @NotNull
  private final UUID productId;

  @NotNull
  private final Integer quantity;

  @NotNull
  private final BigDecimal price;

  @NotNull
  private final BigDecimal subTotal;






}
