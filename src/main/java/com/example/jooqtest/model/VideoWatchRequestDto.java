package com.example.jooqtest.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoWatchRequestDto {

    @NotBlank(message = "VideoId is required")
    private String videoId;
    
    @NotNull(message = "UserId is required")
    private Long userId;
    
    @NotNull(message = "Timestamp is required")
    private Long timestamp;

}
