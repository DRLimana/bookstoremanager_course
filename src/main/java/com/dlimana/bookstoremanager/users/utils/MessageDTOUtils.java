package com.dlimana.bookstoremanager.users.utils;

import com.dlimana.bookstoremanager.users.dto.MessageDTO;
import com.dlimana.bookstoremanager.users.entity.User;

public class MessageDTOUtils {

    public static MessageDTO creationMessage(User createdUser) {
        return returnMessage(createdUser, "created");
    }

    public static MessageDTO updateMessage(User updatedUser) {
        return returnMessage(updatedUser, "updated");
    }

    public static MessageDTO returnMessage(User updatedUser, String action) {
        String createdUserName = updatedUser.getUsername();
        Long createdId = updatedUser.getId();
        String createdUserMessage = String.format("User %s with ID %s successfully %s", createdUserName, createdId, action);
        return MessageDTO.builder()
                .message(createdUserMessage)
                .build();
    }
}
