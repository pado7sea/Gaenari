class MonthlyCoinRecord {
  String? status;
  String? message;
  CoinRecord? data;

  MonthlyCoinRecord({this.status, this.message, this.data});

  MonthlyCoinRecord.fromJson(Map<String, dynamic> json) {
    status = json['status'];
    message = json['message'];
    data = json['data'] != null ? CoinRecord.fromJson(json['data']) : null;
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

class CoinRecord {
  int? year;
  int? month;
  List<MemberCoinRecordList>? memberCoinRecordList;

  CoinRecord({this.year, this.month, this.memberCoinRecordList});

  CoinRecord.fromJson(Map<String, dynamic> json) {
    year = json['year'];
    month = json['month'];
    if (json['memberCoinRecordList'] != null) {
      memberCoinRecordList = <MemberCoinRecordList>[];
      json['memberCoinRecordList'].forEach((v) {
        memberCoinRecordList!.add(MemberCoinRecordList.fromJson(v));
      });
    }
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['year'] = year;
    data['month'] = month;
    if (memberCoinRecordList != null) {
      data['memberCoinRecordList'] =
          memberCoinRecordList!.map((v) => v.toJson()).toList();
    }
    return data;
  }
}

class MemberCoinRecordList {
  int? day;
  bool? isIncreased;
  String? coinTitle;
  int? coinAmount;
  int? balance;

  MemberCoinRecordList(
      {this.day,
      this.isIncreased,
      this.coinTitle,
      this.coinAmount,
      this.balance});

  MemberCoinRecordList.fromJson(Map<String, dynamic> json) {
    day = json['day'];
    isIncreased = json['isIncreased'];
    coinTitle = json['coinTitle'];
    coinAmount = json['coinAmount'];
    balance = json['balance'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['day'] = day;
    data['isIncreased'] = isIncreased;
    data['coinTitle'] = coinTitle;
    data['coinAmount'] = coinAmount;
    data['balance'] = balance;
    return data;
  }
}
