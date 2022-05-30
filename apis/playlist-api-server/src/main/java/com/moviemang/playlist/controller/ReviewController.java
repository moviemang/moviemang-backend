package com.moviemang.playlist.controller;

import com.moviemang.coreutils.common.response.CommonResponse;
import com.moviemang.playlist.dto.MyReview;
import com.moviemang.playlist.service.ReviewService;
import com.moviemang.security.uitls.AuthenticationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequestMapping(path = "/review")
@RestController
public class ReviewController {

    private ReviewService reviewService;
    private AuthenticationUtil authenticationUtil;

    @Autowired
    public ReviewController(ReviewService reviewService, AuthenticationUtil authenticationUtil) {
        this.reviewService = reviewService;
        this.authenticationUtil = authenticationUtil;
    }

    @GetMapping(path = "/me")
    public CommonResponse myReview(HttpServletRequest httpServletRequest,
                                         @PageableDefault(page = 0, size = 20)Pageable pageRequest, MyReview.Request request){
        authenticationUtil.checkAuthenticationInfo(httpServletRequest, request);
        return reviewService.myReview(request, pageRequest);
    }

}
