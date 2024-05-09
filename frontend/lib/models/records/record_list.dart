// 기록 모델클래스

class RecordList {
  String? status;
  String? message;
  List<Record>? data;

  RecordList({this.status, this.message, this.data});

  RecordList.fromJson(Map<String, dynamic> json) {
    status = json['status'];
    message = json['message'];
    if (json['data'] != null) {
      data = <Record>[];
      json['data'].forEach((v) {
        data!.add(Record.fromJson(v));
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

class Record {
  int? year;
  int? month;
  int? day;
  List<DailyRecords>? dailyRecords;

  Record({this.year, this.month, this.day, this.dailyRecords});

  Record.fromJson(Map<String, dynamic> json) {
    year = json['year'];
    month = json['month'];
    day = json['day'];
    if (json['dailyRecords'] != null) {
      dailyRecords = <DailyRecords>[];
      json['dailyRecords'].forEach((v) {
        dailyRecords!.add(DailyRecords.fromJson(v));
      });
    }
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['year'] = year;
    data['month'] = month;
    data['day'] = day;
    if (dailyRecords != null) {
      data['dailyRecords'] = dailyRecords!.map((v) => v.toJson()).toList();
    }
    return data;
  }
}

class DailyRecords {
  int? recordId;
  String? recordDate;
  int? recordTime;
  int? recordDist;
  int? recordPace;

  DailyRecords(
      {this.recordId,
      this.recordDate,
      this.recordTime,
      this.recordDist,
      this.recordPace});

  DailyRecords.fromJson(Map<String, dynamic> json) {
    recordId = json['recordId'];
    recordDate = json['recordDate'];
    recordTime = json['recordTime'];
    recordDist = json['recordDist'];
    recordPace = json['recordPace'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['recordId'] = recordId;
    data['recordDate'] = recordDate;
    data['recordTime'] = recordTime;
    data['recordDist'] = recordDist;
    data['recordPace'] = recordPace;
    return data;
  }
}
