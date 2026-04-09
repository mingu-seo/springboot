package com.example.chapter13.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductCreateRequest {

    @NotBlank(message = "상품명은 필수입니다.")
    private String name;

    @NotNull(message = "가격은 필수입니다.")
    @Min(value = 1000, message = "가격은 최소 1000원 이상입니다.")
    private Integer price;

    @NotNull(message = "재고는 필수입니다.")
    @Min(value = 0, message = "재고는 0 이상이어야 합니다.")
    private Integer stock;

    @Size(max = 100, message = "설명은 최대 100자까지 가능합니다.")
    private String description;
}