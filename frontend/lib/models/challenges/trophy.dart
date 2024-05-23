// 업적조회

class Trophy {
  String? status;
  String? message;
  List<TrophyChallenge>? data;

  Trophy({this.status, this.message, this.data});

  Trophy.fromJson(Map<String, dynamic> json) {
    status = json['status'];
    message = json['message'];
    if (json['data'] != null) {
      data = <TrophyChallenge>[];
      json['data'].forEach((v) {
        data!.add(TrophyChallenge.fromJson(v));
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

class TrophyChallenge {
  int? challengeId;
  String? type;
  int? challengeValue;
  int? coin;
  bool? isAchieved;
  double? memberValue;
  int? obtainable;

  TrophyChallenge(
      {this.challengeId,
      this.type,
      this.challengeValue,
      this.coin,
      this.isAchieved,
      this.memberValue,
      this.obtainable});

  TrophyChallenge.fromJson(Map<String, dynamic> json) {
    challengeId = json['challengeId'];
    type = json['type'];
    challengeValue = json['challengeValue'];
    coin = json['coin'];
    isAchieved = json['isAchieved'];
    memberValue = json['memberValue'];
    obtainable = json['obtainable'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['challengeId'] = challengeId;
    data['type'] = type;
    data['challengeValue'] = challengeValue;
    data['coin'] = coin;
    data['isAchieved'] = isAchieved;
    data['memberValue'] = memberValue;
    data['obtainable'] = obtainable;
    return data;
  }
}
