package com.quinstagram.postService.entity;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


@Entity
@Table
public class Post {


    @Id
    @GeneratedValue(generator = "seq_gen_alias")
    @GenericGenerator(name="seq_gen_alias",strategy = "increment")
    private Long id;

    private String userId;

    private Long postedOn;

    private String description;

    private Boolean type; //true Image false Video
    @Column(length = 2056)
    private String url;

    @Column(length = 2056)
    private String sharableLink;

    private Integer numberOfLikes;

    private Integer numberOfDisLikes;
    private String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getPostedOn() {
        return postedOn;
    }

    public void setPostedOn(Long postedOn) {
        this.postedOn = postedOn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSharableLink() {
        return sharableLink;
    }

    public void setSharableLink(String sharableLink) {
        this.sharableLink = sharableLink;
    }

    public Integer getNumberOfLikes() {
        return numberOfLikes;
    }

    public void setNumberOfLikes(Integer numberOfLikes) {
        this.numberOfLikes = numberOfLikes;
    }

    public Integer getNumberOfDisLikes() {
        return numberOfDisLikes;
    }

    public void setNumberOfDisLikes(Integer numberOfDisLikes) {
        this.numberOfDisLikes = numberOfDisLikes;
    }
}
