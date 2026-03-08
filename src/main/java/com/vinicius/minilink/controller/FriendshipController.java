package com.vinicius.minilink.controller;

import com.vinicius.minilink.model.Friendship;
import com.vinicius.minilink.service.FriendshipService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friends")
public class FriendshipController {

    private final FriendshipService service;

    public FriendshipController(FriendshipService service) {
        this.service = service;
    }

    @PostMapping("/request")
    public Friendship sendRequest(@RequestParam Long requesterId, @RequestParam Long addresseeId) {
        return service.sendRequest(requesterId, addresseeId);
    }

    @PutMapping("/{friendshipId}/accept")
    public Friendship acceptRequest(@PathVariable Long friendshipId) {
        return service.acceptRequest(friendshipId);
    }

    @GetMapping("/{userId}")
    public List<Friendship> listFriends(@PathVariable Long userId) {
        return service.listAcceptedFriends(userId);
    }
}