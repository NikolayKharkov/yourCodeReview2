package ru.kh.chat.dto.messagesDto;

public class MessageIdDto {

    private long messageId;

    public MessageIdDto() {
    }

    public MessageIdDto(long messageId) {
        this.messageId = messageId;
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }


}
