class WeeklyStatisticList {
  String? status;
  String? message;
  List<Weekly>? data;

  WeeklyStatisticList({this.status, this.message, this.data});

  WeeklyStatisticList.fromJson(Map<String, dynamic> json) {
    status = json['status'];
    message = json['message'];
    if (json['data'] != null) {
      data = <Weekly>[];
      json['data'].forEach((v) {
        data!.add(Weekly.fromJson(v));
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

class Weekly {
  String? date;
  DailyStatistic? dailyStatistic;

  Weekly({this.date, this.dailyStatistic});

  Weekly.fromJson(Map<String, dynamic> json) {
    date = json['date'];
    dailyStatistic = json['dailyStatistic'] != null
        ? DailyStatistic.fromJson(json['dailyStatistic'])
        : null;
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['date'] = date;
    if (dailyStatistic != null) {
      data['dailyStatistic'] = dailyStatistic!.toJson();
    }
    return data;
  }
}

class DailyStatistic {
  double? time;
  double? dist;
  double? cal;
  double? pace;
  double? heart;

  DailyStatistic({this.time, this.dist, this.cal, this.pace, this.heart});

  DailyStatistic.fromJson(Map<String, dynamic> json) {
    time = json['time'];
    dist = json['dist'];
    cal = json['cal'];
    pace = json['pace'];
    heart = json['heart'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['time'] = time;
    data['dist'] = dist;
    data['cal'] = cal;
    data['pace'] = pace;
    data['heart'] = heart;
    return data;
  }
}
