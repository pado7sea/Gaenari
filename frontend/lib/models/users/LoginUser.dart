class LoginUser {
  String? status;
  String? message;
  Data? data;

  LoginUser({this.status, this.message, this.data});

  LoginUser.fromJson(Map<String, dynamic> json) {
    status = json['status'];
    message = json['message'];
    data = json['data'] != null ? new Data.fromJson(json['data']) : null;
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['status'] = this.status;
    data['message'] = this.message;
    if (this.data != null) {
      data['data'] = this.data!.toJson();
    }
    return data;
  }
}

class Data {
  int? memberId;
  String? email;
  String? nickname;
  String? birthday;
  String? gender;
  int? height;
  int? weight;
  int? coin;
  String? lastTime;
  List<MyPetDto>? myPetDto;

  Data(
      {this.memberId,
      this.email,
      this.nickname,
      this.birthday,
      this.gender,
      this.height,
      this.weight,
      this.coin,
      this.lastTime,
      this.myPetDto});

  Data.fromJson(Map<String, dynamic> json) {
    memberId = json['memberId'];
    email = json['email'];
    nickname = json['nickname'];
    birthday = json['birthday'];
    gender = json['gender'];
    height = json['height'];
    weight = json['weight'];
    coin = json['coin'];
    lastTime = json['lastTime'];
    if (json['myPetDto'] != null) {
      myPetDto = <MyPetDto>[];
      json['myPetDto'].forEach((v) {
        myPetDto!.add(new MyPetDto.fromJson(v));
      });
    }
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['memberId'] = this.memberId;
    data['email'] = this.email;
    data['nickname'] = this.nickname;
    data['birthday'] = this.birthday;
    data['gender'] = this.gender;
    data['height'] = this.height;
    data['weight'] = this.weight;
    data['coin'] = this.coin;
    data['lastTime'] = this.lastTime;
    if (this.myPetDto != null) {
      data['myPetDto'] = this.myPetDto!.map((v) => v.toJson()).toList();
    }
    return data;
  }
}

class MyPetDto {
  int? id;
  String? name;

  MyPetDto({this.id, this.name});

  MyPetDto.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    name = json['name'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['id'] = this.id;
    data['name'] = this.name;
    return data;
  }
}
