package com.kirilldikun.ishop.controller;

import com.kirilldikun.ishop.dto.UserProductsResponse;
import com.kirilldikun.ishop.service.UserProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-products")
@RequiredArgsConstructor
public class UserProductsController {

    private final UserProductsService userProductsService;

    @GetMapping("/{userId}")
    public ResponseEntity<Page<UserProductsResponse>> findAllWithUserInfo(
            @RequestParam(defaultValue = "") String query,
            @PathVariable Long userId, Pageable pageable) {
        return ResponseEntity.ok(userProductsService.findAllProductsWithUserInfo(userId, query, pageable));
    }

}
