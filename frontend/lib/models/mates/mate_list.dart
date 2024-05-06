class MateList {
  String? status;
  String? message;
  List<Mate>? data;

  MateList({this.status, this.message, this.data});

  MateList.fromJson(Map<String, dynamic> json) {
    status = json['status'];
    message = json['message'];
    if (json['data'] != null) {
      data = <Mate>[];
      json['data'].forEach((v) {
        data!.add(Mate.fromJson(v));
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

class Mate {
  int? mateId;
  int? memberId;
  String? nickName;
  int? petId;
  String? petName;
  String? petTier;

  Mate(
      {this.mateId,
      this.memberId,
      this.nickName,
      this.petId,
      this.petName,
      this.petTier});

  Mate.fromJson(Map<String, dynamic> json) {
    mateId = json['mateId'];
    memberId = json['memberId'];
    nickName = json['nickName'];
    petId = json['petId'];
    petName = json['petName'];
    petTier = json['petTier'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['mateId'] = mateId;
    data['memberId'] = memberId;
    data['nickName'] = nickName;
    data['petId'] = petId;
    data['petName'] = petName;
    data['petTier'] = petTier;
    return data;
  }
}
