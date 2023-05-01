package com.example.wohnungsuchen.services;

import com.example.wohnungsuchen.entities.Searchers;
import com.example.wohnungsuchen.repositories.SearchersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchersService {
    private final SearchersRepository searchersRepository;

    public void enableNotification(Long searcher_id) {
        if (searchersRepository.findById(searcher_id).isEmpty()) {
            throw new NullPointerException();
        }
        Searchers searcher = searchersRepository.findById(searcher_id).get();
        searcher.setNotifications(true);
        searchersRepository.save(searcher);
    }

    public void disableNotification(Long searcher_id) {
        if (searchersRepository.findById(searcher_id).isEmpty()) {
            throw new NullPointerException();
        }
        Searchers searcher = searchersRepository.findById(searcher_id).get();
        searcher.setNotifications(false);
        searchersRepository.save(searcher);
    }
}
