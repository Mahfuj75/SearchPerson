package com.onenation.oneworld.mahfuj75.searchperson.objectclass;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mahfu on 12/8/2016.
 */

@IgnoreExtraProperties
public class Post {

    public String postDate;
    public String postTime;

    public String uid;
    public String userName;
    public String userPhoneNumber;
    public String userEmail;

    public String missingPersonName;
    public String relativeName;
    public String age;

    public String missingDateDay;
    public String missingDateMonth;
    public String missingDateYear;
    public String missingDate;

    public String heightFoot;
    public String heightInch;
    public String height;
    public String district;
    public String subDistrict;
    public String description;

    public String imageUrl;


    public int starCount = 0;
    public Map<String, Boolean> stars = new HashMap<>();

    public Post() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Post(String postDate, String postTime, String uid, String userName,
                String userPhoneNumber, String userEmail, String missingPersonName,
                String relativeName, String age, String missingDateDay, String missingDateMonth,
                String missingDateYear, String missingDate, String heightFoot, String heightInch,
                String height, String district, String subDistrict, String description,String imageUrl) {

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
        this.imageUrl =imageUrl;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();

        result.put("postDate", postDate);
        result.put("postTime", postTime);

        result.put("uid", uid);
        result.put("userName", userName);
        result.put("userPhoneNumber", userPhoneNumber);
        result.put("userEmail", userEmail);

        result.put("missingPersonName", missingPersonName);
        result.put("relativeName", relativeName);
        result.put("age", age);
        result.put("imageUrl", imageUrl);

        result.put("missingDateDay", missingDateDay);
        result.put("missingDateMonth", missingDateMonth);
        result.put("missingDateYear", missingDateYear);
        result.put("missingDate", missingDate);

        result.put("heightFoot", heightFoot);
        result.put("heightInch", heightInch);
        result.put("height", height);

        result.put("district", district);
        result.put("subDistrict", subDistrict);
        result.put("heightInch", description);

        result.put("starCount", starCount);
        result.put("stars", stars);


        return result;
    }

}