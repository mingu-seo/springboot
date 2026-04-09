package com.example.chapter15.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {
    private Long idx;
    private String name;
    private String email;
    private String pwd;
    private LocalDateTime crdt;
}
