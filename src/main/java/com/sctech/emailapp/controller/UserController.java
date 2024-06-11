package com.sctech.emailapp.controller;

import com.sctech.emailapp.dto.CommonResposeDto;
import com.sctech.emailapp.dto.RegisterUserDto;
import com.sctech.emailapp.model.User;
import com.sctech.emailapp.service.UserService;
import com.sctech.emailapp.util.EmailResponseBuilder;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/user")
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private EmailResponseBuilder emailResponseBuilder;

    @PostMapping("/signup")
    public ResponseEntity<CommonResposeDto> register(@RequestBody @Valid RegisterUserDto registerUserDto) {
        CommonResposeDto commonResposeDto = new CommonResposeDto();

        User user = userService.signup(registerUserDto);
        ArrayList<User> arrayList = new ArrayList<>();
        arrayList.add(user);
        commonResposeDto = emailResponseBuilder.commonResponse("success",arrayList);
        return ResponseEntity.ok(commonResposeDto);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CommonResposeDto> authenticatedUser(@PathVariable("userId") String userId) {
        User user = userService.getDetail(userId);

        ArrayList<User> arrayList = new ArrayList<>();
        arrayList.add(user);
        CommonResposeDto commonResposeDto = emailResponseBuilder.commonResponse("success",arrayList);
        return ResponseEntity.ok(commonResposeDto);
    }

    @GetMapping
    public ResponseEntity<CommonResposeDto> allUsers() {
        List <User> users = userService.getAll();
        CommonResposeDto commonResposeDto = emailResponseBuilder.commonResponse("success",users);
        return ResponseEntity.ok(commonResposeDto);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<CommonResposeDto> update(@PathVariable("userId") String userId, @RequestBody @Valid RegisterUserDto registerUserDto) {
        User user = userService.update(userId, registerUserDto);
        ArrayList<User> arrayList = new ArrayList<>();
        arrayList.add(user);
        CommonResposeDto commonResposeDto = emailResponseBuilder.commonResponse("success",arrayList);
        return ResponseEntity.ok(commonResposeDto);
    }


}
