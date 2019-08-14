package com.igoryasko.justmusic.entity;

import lombok.*;

/**
 * Entity for database table tracks.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Track extends Entity {

   private static final long serialVersionUID = -4343808810105536731L;

   private long trackId;
   private String name;
   private String path;
   private Genre genre;
   private Singer singer;

   public Track(long trackId, String name, Genre genre, Singer singer) {
      this.trackId = trackId;
      this.name = name;
      this.genre = genre;
      this.singer = singer;
   }

   public Track(String name, String path, Genre genre, Singer singer) {
      this.name = name;
      this.path = path;
      this.genre = genre;
      this.singer = singer;
   }

}
