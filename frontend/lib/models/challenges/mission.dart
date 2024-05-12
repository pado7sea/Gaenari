// 미션조회

class Mission {
  String? status;
  String? message;
  List<Challenge>? data;

  Mission({this.status, this.message, this.data});

  Mission.fromJson(Map<String, dynamic> json) {
    status = json['status'];
    message = json['message'];
    if (json['data'] != null) {
      data = <Challenge>[];
      json['data'].forEach((v) {
        data!.add(Challenge.fromJson(v));
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

class Challenge {
  int? challengeId;
  String? type;
  int? challengeValue;
  int? coin;
  int? heart;
  int? count;
  int? obtainable;

  Challenge(
      {this.challengeId,
      this.type,
      this.challengeValue,
      this.coin,
      this.heart,
      this.count,
      this.obtainable});

  Challenge.fromJson(Map<String, dynamic> json) {
    challengeId = json['challengeId'];
    type = json['type'];
    challengeValue = json['challengeValue'];
    coin = json['coin'];
    heart = json['heart'];
    count = json['count'];
    obtainable = json['obtainable'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['challengeId'] = challengeId;
    data['type'] = type;
    data['challengeValue'] = challengeValue;
    data['coin'] = coin;
    data['heart'] = heart;
    data['count'] = count;
    data['obtainable'] = obtainable;
    return data;
  }
}
