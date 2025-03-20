package com.example.jooqtest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoWatchResponseDto {

    private String videoId;
    private Long userId;
    private Long timestamp;
    private boolean success;
    private String message;

}
