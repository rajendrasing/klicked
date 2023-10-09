package com.dating.klicked.Model.RequestRepo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SendMessageBody {

    @SerializedName("chatId")
    @Expose
    public String chatId;
    @SerializedName("outgoingMessages")
    @Expose
    public List<OutgoingMessage> outgoingMessages = null;


    public SendMessageBody() {
    }


    public SendMessageBody(String chatId, List<OutgoingMessage> outgoingMessages) {
        super();
        this.chatId = chatId;
        this.outgoingMessages = outgoingMessages;
    }

    public static class OutgoingMessage {

        @SerializedName("outgoingMessage")
        @Expose
        public String outgoingMessage;
        @SerializedName("userId")
        @Expose
        public String userId;

        @SerializedName("online")
        @Expose
        public Boolean online;

        public OutgoingMessage() {
        }


        public OutgoingMessage(String outgoingMessage, String userId, Boolean online) {
            super();
            this.outgoingMessage = outgoingMessage;
            this.userId = userId;
            this.online = online;
        }

        @Override
        public String toString() {
            return "OutgoingMessage{" +
                    "outgoingMessage='" + outgoingMessage + '\'' +
                    ", userId='" + userId + '\'' +
                    ", online=" + online +
                    '}';
        }
    }

}