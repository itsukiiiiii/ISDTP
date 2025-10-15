package com.example.demo.app;

import com.example.demo.common.ApiResponse;
import com.example.demo.app.vo.AppVO;
import com.example.demo.app.entity.AppEntity;
import com.example.demo.app.mapper.AppMapper;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/apps")
public class AppController {
    private final AppMapper appMapper;

    public AppController(AppMapper appMapper) {
        this.appMapper = appMapper;
    }

    @GetMapping
    // ================== MODIFICATION START ==================
    // Step 1: Add @RequestParam to accept 'sortBy' and 'order' from the URL
    // Example URL: /api/apps?sortBy=rating&order=desc
    public ApiResponse<List<AppVO>> list(
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false, defaultValue = "desc") String order) {

        // Step 2: Add a security check to prevent SQL injection.
        // Only allow sorting by 'rating' or 'downloads'.
        if (sortBy != null && !sortBy.equals("rating") && !sortBy.equals("downloads")) {
            // If an invalid field is passed, set it to null to ignore sorting.
            sortBy = null;
        }

        // Step 3: Call a new, more flexible mapper method instead of the old one.
        List<AppEntity> entities = appMapper.findAll(sortBy, order);
    // =================== MODIFICATION END ===================

        // The rest of the code for converting to VO remains the same.
        List<AppVO> out = new ArrayList<>();
        for (AppEntity e : entities) {
            AppVO vo = AppVO.builder()
                    .id(e.getId())
                    .name(e.getName())
                    .description(e.getDescription())
                    .fullDescription(e.getFullDescription())
                    .avatar(e.getAvatar())
                    .category(e.getCategory())
                    .price(e.getPrice())
                    .rating(e.getRating())
                    .downloads(e.getDownloads())
                    .reviews(e.getReviews())
                    .author(e.getAuthor())
                    .publishedAt(e.getPublishedAt())
                    .build();
            out.add(vo);
        }
        return ApiResponse.ok(out);
    }

    // This method for searching by category does not need to be changed.
    @GetMapping("/category/{category}")
    public ApiResponse<List<AppVO>> listByCategory(@PathVariable String category) {
        List<AppEntity> entities = appMapper.findByCategory(category);
        List<AppVO> out = new ArrayList<>();
        for (AppEntity e : entities) {
            AppVO vo = AppVO.builder()
                    .id(e.getId())
                    .name(e.getName())
                    .description(e.getDescription())
                    .fullDescription(e.getFullDescription())
                    .avatar(e.getAvatar())
                    .category(e.getCategory())
                    .price(e.getPrice())
                    .rating(e.getRating())
                    .downloads(e.getDownloads())
                    .reviews(e.getReviews())
                    .author(e.getAuthor())
                    .publishedAt(e.getPublishedAt())
                    .build();
            out.add(vo);
        }
        return ApiResponse.ok(out);
    }
}