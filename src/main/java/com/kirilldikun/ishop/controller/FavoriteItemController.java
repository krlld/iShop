package com.kirilldikun.ishop.controller;

import com.kirilldikun.ishop.dto.FavoriteItemDTO;
import com.kirilldikun.ishop.dto.FavoriteItemResponse;
import com.kirilldikun.ishop.service.FavoriteItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/favorite-item")
@RequiredArgsConstructor
public class FavoriteItemController {

    private final FavoriteItemService favoriteItemService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<FavoriteItemResponse>> findAllByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(favoriteItemService.findAllByUserId(userId));
    }

    @PatchMapping
    public ResponseEntity<Void> changeFavoriteStatus(@Valid @RequestBody FavoriteItemDTO favoriteItemDTO) {
        favoriteItemService.changeFavoriteStatus(favoriteItemDTO);
        return ResponseEntity.ok().build();
    }
}
