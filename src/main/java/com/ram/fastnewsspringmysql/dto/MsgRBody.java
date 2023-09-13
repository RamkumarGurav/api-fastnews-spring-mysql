package com.ram.fastnewsspringmysql.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MsgRBody<T> {
    private String status;
    private T message;
}
