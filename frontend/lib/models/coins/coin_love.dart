class CoinLove {
  bool? isIncreased;
  String? coinTitle;
  int? coinAmount;

  CoinLove({this.isIncreased, this.coinTitle, this.coinAmount});

  CoinLove.fromJson(Map<String, dynamic> json) {
    isIncreased = json['isIncreased'];
    coinTitle = json['coinTitle'];
    coinAmount = json['coinAmount'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['isIncreased'] = isIncreased;
    data['coinTitle'] = coinTitle;
    data['coinAmount'] = coinAmount;
    return data;
  }
}

class CoinRes {
  String? status;
  String? message;
  Null data;

  CoinRes({this.status, this.message, this.data});

  CoinRes.fromJson(Map<String, dynamic> json) {
    status = json['status'];
    message = json['message'];
    data = json['data'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['status'] = status;
    data['message'] = message;
    data['data'] = this.data;
    return data;
  }
}
