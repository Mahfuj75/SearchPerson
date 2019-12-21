package com.onenation.oneworld.mahfuj75.searchperson.objectclass;

/**
 * Created by mahfu on 10/3/2016.
 */

public class MissingPerson {

    private String postDate;
    private String postTime;

    private String uid;
    private String userName;
    private String userPhoneNumber;
    private String userEmail;

    private String missingPersonName;
    private String relativeName;
    private String age;

    private String missingDateDay;
    private String missingDateMonth;
    private String missingDateYear;
    private String missingDate;

    private String heightFoot;
    private String heightInch;
    private String height;
    private String district;
    private String location;
    private String subDistrict;
    private String description;
    private String lostFound;

    public String imageUrl;

    public MissingPerson() {
    }

    public MissingPerson(String postDate, String postTime, String uid, String userName,
                         String userPhoneNumber, String userEmail, String missingPersonName,
                         String relativeName, String age, String missingDateDay, String missingDateMonth,
                         String missingDateYear, String missingDate, String heightFoot, String heightInch,
                         String height, String district, String subDistrict, String description, String imageUrl,
                         String location, String lostFound) {

        this.postDate = postDate;
        this.postTime = postTime;
        this.uid = uid;
        this.userName = userName;
        this.userPhoneNumber = userPhoneNumber;
        this.userEmail = userEmail;
        this.missingPersonName = missingPersonName;
        this.relativeName = relativeName;
        this.age = age;
        this.missingDateDay = missingDateDay;
        this.missingDateMonth = missingDateMonth;
        this.missingDateYear = missingDateYear;
        this.missingDate = missingDate;
        this.heightFoot = heightFoot;
        this.heightInch = heightInch;
        this.height = height;
        this.district = district;
        this.subDistrict = subDistrict;
        this.description = description;
        this.imageUrl = imageUrl;
        this.lostFound = lostFound;
        this.location = location;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getMissingPersonName() {
        return missingPersonName;
    }

    public void setMissingPersonName(String missingPersonName) {
        this.missingPersonName = missingPersonName;
    }

    public String getRelativeName() {
        return relativeName;
    }

    public void setRelativeName(String relativeName) {
        this.relativeName = relativeName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getMissingDateDay() {
        return missingDateDay;
    }

    public void setMissingDateDay(String missingDateDay) {
        this.missingDateDay = missingDateDay;
    }

    public String getMissingDateMonth() {
        return missingDateMonth;
    }

    public void setMissingDateMonth(String missingDateMonth) {
        this.missingDateMonth = missingDateMonth;
    }

    public String getMissingDateYear() {
        return missingDateYear;
    }

    public void setMissingDateYear(String missingDateYear) {
        this.missingDateYear = missingDateYear;
    }

    public String getMissingDate() {
        return missingDate;
    }

    public void setMissingDate(String missingDate) {
        this.missingDate = missingDate;
    }

    public String getHeightFoot() {
        return heightFoot;
    }

    public void setHeightFoot(String heightFoot) {
        this.heightFoot = heightFoot;
    }

    public String getHeightInch() {
        return heightInch;
    }

    public void setHeightInch(String heightInch) {
        this.heightInch = heightInch;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getSubDistrict() {
        return subDistrict;
    }

    public void setSubDistrict(String subDistrict) {
        this.subDistrict = subDistrict;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLostFound() {
        return lostFound;
    }

    public void setLostFound(String lostFound) {
        this.lostFound = lostFound;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
