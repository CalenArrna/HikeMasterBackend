package org.backend.DTOs;

import org.backend.Model.HikeRoute;
import org.backend.Model.Message;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserProfileDTO {
    private String email;

    private String username;

    private String fullName;

    private List<Message> userMessageList = new ArrayList<>();

    private Set<HikeRoute> hikeRouteWishSet = new HashSet<>();

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    public List<Message> getUserMessageList() {
        return userMessageList;
    }

    public void setUserMessageList(List<Message> userMessageList) {
        this.userMessageList = userMessageList;
    }

    public Set<HikeRoute> getHikeRouteWishSet() {
        return hikeRouteWishSet;
    }

    public void setHikeRouteWishSet(Set<HikeRoute> hikeRouteWishSet) {
        this.hikeRouteWishSet = hikeRouteWishSet;
    }
}
