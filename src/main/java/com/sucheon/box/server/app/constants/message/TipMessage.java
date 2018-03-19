package com.sucheon.box.server.app.constants.message;

public enum TipMessage {
    SUCCESS(1, "操作成功!");


    private String message;
    private int errorCode;

    TipMessage(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
