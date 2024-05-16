class SignupUser {
  String? accountId;
  String? password;
  String? nickName;
  String? birth;
  String? gender;
  int? height;
  int? weight;
  MyPet? myPet;

  SignupUser(
      {this.accountId,
      this.password,
      this.nickName,
      this.birth,
      this.gender,
      this.height,
      this.weight,
      this.myPet});

  SignupUser.fromJson(Map<String, dynamic> json) {
    accountId = json['accountId'];
    password = json['password'];
    nickName = json['nickName'];
    birth = json['birth'];
    gender = json['gender'];
    height = json['height'];
    weight = json['weight'];
    myPet = json['myPet'] != null ? MyPet.fromJson(json['myPet']) : null;
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['accountId'] = accountId;
    data['password'] = password;
    data['nickName'] = nickName;
    data['birth'] = birth;
    data['gender'] = gender;
    data['height'] = height;
    data['weight'] = weight;
    if (myPet != null) {
      data['myPet'] = myPet!.toJson();
    }
    return data;
  }
}

class MyPet {
  int? id;
  String? name;

  MyPet({this.id, this.name});

  MyPet.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    name = json['name'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['id'] = id;
    data['name'] = name;
    return data;
  }
}

class SignupOk {
  String? status;
  String? message;
  Data? data;

  SignupOk({this.status, this.message, this.data});

  SignupOk.fromJson(Map<String, dynamic> json) {
    status = json['status'];
    message = json['message'];
    data = json['data'] != null ? Data.fromJson(json['data']) : null;
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

class Data {
  int? memberId;
  String? nickName;
  MyPet? myPet;

  Data({this.memberId, this.nickName, this.myPet});

  Data.fromJson(Map<String, dynamic> json) {
    memberId = json['memberId'];
    nickName = json['nickName'];
    myPet = json['myPet'] != null ? MyPet.fromJson(json['myPet']) : null;
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['memberId'] = memberId;
    data['nickName'] = nickName;
    if (myPet != null) {
      data['myPet'] = myPet!.toJson();
    }
    return data;
  }
}
