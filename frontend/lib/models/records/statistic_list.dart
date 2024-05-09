// 톨계

class StatisticList {
  String? status;
  String? message;
  statistic? data;

  StatisticList({this.status, this.message, this.data});

  StatisticList.fromJson(Map<String, dynamic> json) {
    status = json['status'];
    message = json['message'];
    data = json['data'] != null ? statistic.fromJson(json['data']) : null;
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

class statistic {
  double? time;
  double? dist;
  int? cal;
  int? pace;
  String? date;
  int? count;

  statistic({this.time, this.dist, this.cal, this.pace, this.date, this.count});

  statistic.fromJson(Map<String, dynamic> json) {
    time = json['time'];
    dist = json['dist'];
    cal = json['cal'];
    pace = json['pace'];
    date = json['date'];
    count = json['count'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['time'] = time;
    data['dist'] = dist;
    data['cal'] = cal;
    data['pace'] = pace;
    data['date'] = date;
    data['count'] = count;
    return data;
  }
}
