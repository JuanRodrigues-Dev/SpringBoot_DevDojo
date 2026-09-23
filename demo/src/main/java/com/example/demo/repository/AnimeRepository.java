package com.example.demo.repository;

import com.example.demo.domain.Anime;

import java.util.List;

public interface AnimeRepository {
    List<Anime> listAll();
}
