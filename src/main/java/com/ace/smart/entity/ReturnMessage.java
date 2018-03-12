package com.ace.smart.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
@JsonSerialize(using = ToStringSerializer.class)
public class ReturnMessage {

    private String name;

}
