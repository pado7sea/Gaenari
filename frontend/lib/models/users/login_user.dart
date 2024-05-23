class LoginUser {
  String? status;
  String? message;
  LoginInfo? data;

  LoginUser({this.status, this.message, this.data});

  LoginUser.fromJson(Map<String, dynamic> json) {
    status = json['status'];
    message = json['message'];
    data = json['data'] != null ? LoginInfo.fromJson(json['data']) : null;
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['status'] = status;
    data['message'] = message;
    if (this.data != null) {
      data['data'] = this.data!.toJson();
    }
    return data;
  }
}

class LoginInfo {
  int? memberId;
  String? accountId;
  String? nickname;
  String? birthday;
  String? gender;
  int? height;
  int? weight;
  int? coin;
  MyPetDto? myPetDto;

  LoginInfo(
      {this.memberId,
      this.accountId,
      this.nickname,
      this.birthday,
      this.gender,
      this.height,
      this.weight,
      this.coin,
      this.myPetDto});

  LoginInfo.fromJson(Map<String, dynamic> json) {
    memberId = json['memberId'];
    accountId = json['accountId'];
    nickname = json['nickname'];
    birthday = json['birthday'];
    gender = json['gender'];
    height = json['height'];
    weight = json['weight'];
    coin = json['coin'];
    myPetDto =
        json['myPetDto'] != null ? MyPetDto.fromJson(json['myPetDto']) : null;
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['memberId'] = memberId;
    data['accountId'] = accountId;
    data['nickname'] = nickname;
    data['birthday'] = birthday;
    data['gender'] = gender;
    data['height'] = height;
    data['weight'] = weight;
    data['coin'] = coin;
    if (myPetDto != null) {
      data['myPetDto'] = myPetDto!.toJson();
    }
    return data;
  }
}

class MyPetDto {
  int? id;
  String? name;
  int? affection;
  String? tier;
  String? changeTime;

  MyPetDto({this.id, this.name, this.affection, this.tier, this.changeTime});

  MyPetDto.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    name = json['name'];
    affection = json['affection'];
    tier = json['tier'];
    changeTime = json['changeTime'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['id'] = id;
    data['name'] = name;
    data['affection'] = affection;
    data['tier'] = tier;
    data['changeTime'] = changeTime;
    return data;
  }
}
