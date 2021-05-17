package com.example.srgplearningpoint_adminpanel;

public class TeacherData {
    private String name;
    private String email;
    private String contact;
    private String qualification;
    private String image;
    private String key;

    public TeacherData() {
    }

    public TeacherData(String name, String email, String contact, String qualification, String image, String key) {
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.qualification = qualification;
        this.image = image;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


}
