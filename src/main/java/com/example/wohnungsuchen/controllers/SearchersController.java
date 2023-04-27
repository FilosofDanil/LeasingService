package com.example.wohnungsuchen.controllers;

import com.example.wohnungsuchen.services.SearchersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/searchers")
@RequiredArgsConstructor
public class SearchersController {
    private final SearchersService searchersService;

    @PreAuthorize("hasAuthority('SEARCHER')")
    @PatchMapping("/enable/{searcher_id}")
    public void enableNotifications(@PathVariable Long searcher_id) {
        searchersService.enableNotification(searcher_id);
    }

    @PreAuthorize("hasAuthority('SEARCHER')")
    @PatchMapping("/disable/{searcher_id}")
    public void disableNotifications(@PathVariable Long searcher_id) {
        searchersService.disableNotification(searcher_id);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<String> onMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }
}
