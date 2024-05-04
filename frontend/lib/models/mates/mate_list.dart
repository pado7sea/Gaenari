class MateList {
  String? status;
  String? message;
  List<Data>? data;

  MateList({this.status, this.message, this.data});

  MateList.fromJson(Map<String, dynamic> json) {
    status = json['status'];
    message = json['message'];
    if (json['data'] != null) {
      data = <Data>[];
      json['data'].forEach((v) {
        data!.add(Data.fromJson(v));
      });
    }
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['status'] = status;
    data['message'] = message;
    if (this.data != null) {
      data['data'] = this.data!.map((v) => v.toJson()).toList();
    }
    return data;
  }
}

class Data {
  int? mateId;
  int? memberId;
  String? nickName;
  Mypet? mypet;

  Data({this.mateId, this.memberId, this.nickName, this.mypet});

  Data.fromJson(Map<String, dynamic> json) {
    mateId = json['mateId'];
    memberId = json['memberId'];
    nickName = json['nickName'];
    mypet = json['mypet'] != null ? Mypet.fromJson(json['mypet']) : null;
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['mateId'] = mateId;
    data['memberId'] = memberId;
    data['nickName'] = nickName;
    if (mypet != null) {
      data['mypet'] = mypet!.toJson();
    }
    return data;
  }
}

class Mypet {
  int? id;
  String? name;
  String? tier;

  Mypet({this.id, this.name, this.tier});

  Mypet.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    name = json['name'];
    tier = json['tier'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['id'] = id;
    data['name'] = name;
    data['tier'] = tier;
    return data;
  }
}
