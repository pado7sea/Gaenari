package com.example.gaenari.dto.response;

public class AuthResponseDto {

    private String AccessToken;
    private Long memberId;
    private String email;
    private String nickname;
    private String birthday;
    private String gender;
    private int height;
    private int weight;
    private int coin;
    private MyPetDto myPetDto;

    class MyPetDto {
        private Long id;
        private String name;
        private int affection;
        private String tier;
        private String changeTime;
    }

    public AuthResponseDto() {
    }

    public String getAccessToken() {
        return AccessToken;
    }

    public void setAccessToken(String accessToken) {
        AccessToken = accessToken;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public void setMyPetDto(MyPetDto myPetDto) {
        this.myPetDto = myPetDto;
    }

    public Long getPetId() { return myPetDto.id; }

    public String getPetName() { return myPetDto.name; }

    public void setPetName(String petName) { this.myPetDto.name = petName; }

    @Override
    public String toString() {
        return "AuthResponseDto{" +
                "AccessToken='" + AccessToken + '\'' +
                ", memberId=" + memberId +
                ", email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
                ", birthday='" + birthday + '\'' +
                ", gender='" + gender + '\'' +
                ", height=" + height +
                ", weight=" + weight +
                ", coin=" + coin +
                ", myPetDto=" + myPetDto +
                '}';
    }
}
