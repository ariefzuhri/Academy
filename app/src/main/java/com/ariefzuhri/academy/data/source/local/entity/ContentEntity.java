package com.ariefzuhri.academy.data.source.local.entity;

import androidx.room.ColumnInfo;

// Tidak ada tabel karena informasi di sini sudah digabung bersama tabel moduleEntities

public class ContentEntity {
    @ColumnInfo(name = "content")
    private String mContent;

    public ContentEntity(String content) {
        this.mContent = content;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String mContent) {
        this.mContent = mContent;
    }
}
