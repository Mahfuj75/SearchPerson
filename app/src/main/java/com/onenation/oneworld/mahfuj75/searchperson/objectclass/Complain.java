package com.onenation.oneworld.mahfuj75.searchperson.objectclass;

/**
 * Created by mahfu on 1/28/2017.
 */

public class Complain {

    private String complainSubject;
    private String complainSubSubject;
    private String gander;
    private String incidentDate;
    private String incidentDateMonth;
    private String incidentDateYear;
    private String incidentDescription;
    private String incidentSpotDescription;
    private String incidentSpotDistrict;
    private String incidentSpotDivision;
    private String incidentSpotSubLocation;
    private String incidentSpotType;
    private String incidentTime;
    private String name;
    private String nationalID;
    private String phoneNumber;
    private String presentAddress;
    private String postDate;
    private String postTime;

    public Complain() {
    }

    public Complain(String complainSubject,
                    String complainSubSubject, String gander, String incidentDate,
                    String incidentDateMonth, String incidentDateYear, String incidentDescription,
                    String incidentSpotDescription, String incidentSpotDistrict, String incidentSpotDivision,
                    String incidentSpotSubLocation, String incidentSpotType, String incidentTime, String name,
                    String nationalID, String phoneNumber, String presentAddress, String postDate,
                    String postTime) {
        this.complainSubject = complainSubject;
        this.complainSubSubject = complainSubSubject;
        this.gander = gander;
        this.incidentDate = incidentDate;
        this.incidentDateMonth = incidentDateMonth;
        this.incidentDateYear = incidentDateYear;
        this.incidentDescription = incidentDescription;
        this.incidentSpotDescription = incidentSpotDescription;
        this.incidentSpotDistrict = incidentSpotDistrict;
        this.incidentSpotDivision = incidentSpotDivision;
        this.incidentSpotSubLocation = incidentSpotSubLocation;
        this.incidentSpotType = incidentSpotType;
        this.incidentTime = incidentTime;
        this.name = name;
        this.nationalID = nationalID;
        this.phoneNumber = phoneNumber;
        this.presentAddress = presentAddress;
        this.postDate = postDate;
        this.postTime = postTime;
    }

    public String getComplainSubSubject() {
        return complainSubSubject;
    }

    public void setComplainSubSubject(String complainSubSubject) {
        this.complainSubSubject = complainSubSubject;
    }

    public String getComplainSubject() {
        return complainSubject;
    }

    public void setComplainSubject(String complainSubject) {
        this.complainSubject = complainSubject;
    }

    public String getGander() {
        return gander;
    }

    public void setGander(String gander) {
        this.gander = gander;
    }

    public String getIncidentDate() {
        return incidentDate;
    }

    public void setIncidentDate(String incidentDate) {
        this.incidentDate = incidentDate;
    }

    public String getIncidentDateMonth() {
        return incidentDateMonth;
    }

    public void setIncidentDateMonth(String incidentDateMonth) {
        this.incidentDateMonth = incidentDateMonth;
    }

    public String getIncidentDateYear() {
        return incidentDateYear;
    }

    public void setIncidentDateYear(String incidentDateYear) {
        this.incidentDateYear = incidentDateYear;
    }

    public String getIncidentDescription() {
        return incidentDescription;
    }

    public void setIncidentDescription(String incidentDescription) {
        this.incidentDescription = incidentDescription;
    }

    public String getIncidentSpotDescription() {
        return incidentSpotDescription;
    }

    public void setIncidentSpotDescription(String incidentSpotDescription) {
        this.incidentSpotDescription = incidentSpotDescription;
    }

    public String getIncidentSpotDistrict() {
        return incidentSpotDistrict;
    }

    public void setIncidentSpotDistrict(String incidentSpotDistrict) {
        this.incidentSpotDistrict = incidentSpotDistrict;
    }

    public String getIncidentSpotDivision() {
        return incidentSpotDivision;
    }

    public void setIncidentSpotDivision(String incidentSpotDivision) {
        this.incidentSpotDivision = incidentSpotDivision;
    }

    public String getIncidentSpotSubLocation() {
        return incidentSpotSubLocation;
    }

    public void setIncidentSpotSubLocation(String incidentSpotSubLocation) {
        this.incidentSpotSubLocation = incidentSpotSubLocation;
    }

    public String getIncidentSpotType() {
        return incidentSpotType;
    }

    public void setIncidentSpotType(String incidentSpotType) {
        this.incidentSpotType = incidentSpotType;
    }

    public String getIncidentTime() {
        return incidentTime;
    }

    public void setIncidentTime(String incidentTime) {
        this.incidentTime = incidentTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationalID() {
        return nationalID;
    }

    public void setNationalID(String nationalID) {
        this.nationalID = nationalID;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPresentAddress() {
        return presentAddress;
    }

    public void setPresentAddress(String presentAddress) {
        this.presentAddress = presentAddress;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }
}
