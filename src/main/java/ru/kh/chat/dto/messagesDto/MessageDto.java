package ru.kh.chat.dto.messagesDto;

import javax.validation.constraints.NotBlank;

public class MessageDto {

    private long chatId;

    private long author;

    @NotBlank(message = "Text can't be empty")
    private String text;

    public MessageDto() {
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public long getAuthor() {
        return author;
    }

    public void setAuthor(long author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
