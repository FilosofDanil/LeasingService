package com.example.wohnungsuchen.controllers;

import com.example.wohnungsuchen.services.SearchersService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
