package com.example.demo.controller;

import com.example.demo.domain.Anime;
import com.example.demo.requests.AnimePostRequestBody;
import com.example.demo.requests.AnimePutRequestBody;
import com.example.demo.service.AnimeService;
import com.example.demo.util.DateUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/animes")
@Log4j2
@RequiredArgsConstructor
public class AnimeController {
    @Autowired
    private DateUtil dateUtil;
    private final AnimeService animeService;

    //localhost:8080/animes
    @GetMapping
     public ResponseEntity<Page<Anime>> list(Pageable pageable){
        log.info(dateUtil.formatLocalDateTimeDarabaseStyle(LocalDateTime.now()));
        return new ResponseEntity<>(animeService.listAll(pageable), HttpStatus.OK);
     }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Anime> findById(@PathVariable("id") long id){
        return new ResponseEntity<>(animeService.findByIdOrThrowBadRequestException(id), HttpStatus.OK);
    }
    @GetMapping(path = "/find")
    public ResponseEntity<List<Anime>> findbyName(@RequestParam String name ){
        return ResponseEntity.ok(animeService.findByName(name));
    }

    @PostMapping
    public ResponseEntity<Anime> save(@RequestBody @Valid AnimePostRequestBody anime){
        return new ResponseEntity<>(animeService.save(anime), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        animeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Anime> replace(@RequestBody AnimePutRequestBody animePutRequestBody){
        animeService.replace(animePutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
