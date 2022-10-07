package ru.kh.chat.dto.messagesDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class MessageDto {

    @NotNull(message = "Chat id can't be null")
    private Long chatId;

    @NotNull(message = "Author id can't be null")
    private Long author;

    @NotBlank(message = "Text can't be empty")
    private String text;

    public MessageDto() {
    }

    public MessageDto(Long chatId, Long author, String text) {
        this.chatId = chatId;
        this.author = author;
        this.text = text;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public long getAuthor() {
        return author;
    }

    public void setAuthor(Long author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
