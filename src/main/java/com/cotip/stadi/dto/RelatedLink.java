package com.cotip.stadi.dto;

public class RelatedLink {

    private String text;
    private String url;

    public RelatedLink(String text, String url) {
        this.text = text;
        this.url = url;
    }

    public String getText() {
        return text;
    }

    public String getUrl() {
        return url;
    }
}
