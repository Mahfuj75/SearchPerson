package com.onenation.oneworld.mahfuj75.searchperson.objectclass;

/**
 * Created by mahfu on 12/13/2016.
 */

public class Comment {

    private String userName;
    private String uid;
    private String imageUrl;

    private String comment;

    private String commentDateDay;
    private String commentDateMonth;
    private String commentDateYear;
    private String commentDateHH;
    private String commentDateMM;
    private String commentDateAMPM;
    private String commentDate;
    private String commentTime;

    public Comment() {
    }

    public Comment(String userName, String uid, String imageUrl, String comment, String commentDateDay,
                   String commentDateMonth, String commentDateYear, String commentDateHH, String commentDateMM,
                   String commentDateAMPM, String commentDate, String commentTime) {
        this.userName = userName;
        this.uid = uid;
        this.imageUrl = imageUrl;
        this.comment = comment;
        this.commentDateDay = commentDateDay;
        this.commentDateMonth = commentDateMonth;
        this.commentDateYear = commentDateYear;
        this.commentDateHH = commentDateHH;
        this.commentDateMM = commentDateMM;
        this.commentDateAMPM = commentDateAMPM;
        this.commentDate = commentDate;
        this.commentTime = commentTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommentDateDay() {
        return commentDateDay;
    }

    public void setCommentDateDay(String commentDateDay) {
        this.commentDateDay = commentDateDay;
    }

    public String getCommentDateMonth() {
        return commentDateMonth;
    }

    public void setCommentDateMonth(String commentDateMonth) {
        this.commentDateMonth = commentDateMonth;
    }

    public String getCommentDateYear() {
        return commentDateYear;
    }

    public void setCommentDateYear(String commentDateYear) {
        this.commentDateYear = commentDateYear;
    }

    public String getCommentDateHH() {
        return commentDateHH;
    }

    public void setCommentDateHH(String commentDateHH) {
        this.commentDateHH = commentDateHH;
    }

    public String getCommentDateMM() {
        return commentDateMM;
    }

    public void setCommentDateMM(String commentDateMM) {
        this.commentDateMM = commentDateMM;
    }

    public String getCommentDateAMPM() {
        return commentDateAMPM;
    }

    public void setCommentDateAMPM(String commentDateAMPM) {
        this.commentDateAMPM = commentDateAMPM;
    }

    public String getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }
}
