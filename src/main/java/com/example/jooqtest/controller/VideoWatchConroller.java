package com.example.jooqtest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.jooqtest.model.VideoWatchRequestDto;
import com.example.jooqtest.model.VideoWatchResponseDto;
import com.example.jooqtest.service.VideoWatchService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/videowatch")
public class VideoWatchConroller {

    private final VideoWatchService videoWatchService;

    @PostMapping("/update")
    public ResponseEntity<VideoWatchResponseDto> updateTime(@Valid @RequestBody VideoWatchRequestDto request) {
        try {
            videoWatchService.saveTime(request.getUserId(), request.getVideoId(), request.getTimestamp());
            
            VideoWatchResponseDto response = new VideoWatchResponseDto(
                request.getVideoId(),
                request.getUserId(),
                request.getTimestamp(),
                true,
                "Watch time updated successfully"
            );
            
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            VideoWatchResponseDto response = new VideoWatchResponseDto(
                request.getVideoId(),
                request.getUserId(),
                request.getTimestamp(),
                false,
                "Failed to update watch time: " + e.getMessage()
            );
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    @GetMapping("/last")
    public ResponseEntity<VideoWatchResponseDto> getTime(@RequestParam Long userId, @RequestParam String videoId) {
        try {
            Long timestamp = videoWatchService.getTime(userId, videoId);
            
            VideoWatchResponseDto response = new VideoWatchResponseDto(
                videoId,
                userId,
                timestamp,
                true,
                "Watch time retrieved successfully"
            );
            
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            VideoWatchResponseDto response = new VideoWatchResponseDto(
                videoId,
                userId,
                0L,
                false,
                "Failed to retrieve watch time: " + e.getMessage()
            );
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
}
