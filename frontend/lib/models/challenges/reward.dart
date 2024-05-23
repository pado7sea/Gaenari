class Reward {
  String? status;
  String? message;
  RewardItem? data;

  Reward({this.status, this.message, this.data});

  Reward.fromJson(Map<String, dynamic> json) {
    status = json['status'];
    message = json['message'];
    data = json['data'] != null ? RewardItem.fromJson(json['data']) : null;
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

class RewardItem {
  String? memberId;
  int? coin;
  int? heart;

  RewardItem({this.memberId, this.coin, this.heart});

  RewardItem.fromJson(Map<String, dynamic> json) {
    memberId = json['memberId'];
    coin = json['coin'];
    heart = json['heart'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['memberId'] = memberId;
    data['coin'] = coin;
    data['heart'] = heart;
    return data;
  }
}
