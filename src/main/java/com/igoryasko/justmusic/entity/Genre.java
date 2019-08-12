package com.igoryasko.justmusic.entity;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Genre extends Entity {

    private static final long serialVersionUID = 7016717353667632362L;

    private long genreId;
    private String name;

    public Genre(String title) {
        this.name = title;
    }
}
