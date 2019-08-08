package com.igoryasko.justmusic.entity;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Singer extends Entity{

    private long singerId;
    private String name;

    public Singer(String name) {
        this.name = name;
    }

}
