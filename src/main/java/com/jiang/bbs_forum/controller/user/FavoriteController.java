package com.jiang.bbs_forum.controller.user;

import com.jiang.bbs_forum.common.Response;
import com.jiang.bbs_forum.dto.request.FavoriteRequest;
import com.jiang.bbs_forum.service.user.FavoriteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    /** 收藏帖子 */
    @PostMapping
    public Response<Void> favorite(@RequestAttribute("userId") int userId,
                                    @Valid @RequestBody FavoriteRequest request) {
        return favoriteService.favorite(userId, request.getPostId());
    }

    /** 取消收藏 */
    @DeleteMapping
    public Response<Void> unfavorite(@RequestAttribute("userId") int userId,
                                      @RequestParam int postId) {
        return favoriteService.unfavorite(userId, postId);
    }
}
