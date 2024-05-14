class MonthlyRecordList {
  String? status;
  String? message;
  Monthly? data;

  MonthlyRecordList({this.status, this.message, this.data});

  MonthlyRecordList.fromJson(Map<String, dynamic> json) {
    status = json['status'];
    message = json['message'];
    data = json['data'] != null ? new Monthly.fromJson(json['data']) : null;
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['status'] = this.status;
    data['message'] = this.message;
    if (this.data != null) {
      data['data'] = this.data!.toJson();
    }
    return data;
  }
}

class Monthly {
  int? year;
  int? month;
  List<ExerciseRecords>? exerciseRecords;

  Monthly({this.year, this.month, this.exerciseRecords});

  Monthly.fromJson(Map<String, dynamic> json) {
    year = json['year'];
    month = json['month'];
    if (json['exerciseRecords'] != null) {
      exerciseRecords = <ExerciseRecords>[];
      json['exerciseRecords'].forEach((v) {
        exerciseRecords!.add(new ExerciseRecords.fromJson(v));
      });
    }
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['year'] = this.year;
    data['month'] = this.month;
    if (this.exerciseRecords != null) {
      data['exerciseRecords'] =
          this.exerciseRecords!.map((v) => v.toJson()).toList();
    }
    return data;
  }
}

class ExerciseRecords {
  int? year;
  int? month;
  int? day;
  List<DailyRecords>? dailyRecords;

  ExerciseRecords({this.year, this.month, this.day, this.dailyRecords});

  ExerciseRecords.fromJson(Map<String, dynamic> json) {
    year = json['year'];
    month = json['month'];
    day = json['day'];
    if (json['dailyRecords'] != null) {
      dailyRecords = <DailyRecords>[];
      json['dailyRecords'].forEach((v) {
        dailyRecords!.add(new DailyRecords.fromJson(v));
      });
    }
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['year'] = this.year;
    data['month'] = this.month;
    data['day'] = this.day;
    if (this.dailyRecords != null) {
      data['dailyRecords'] = this.dailyRecords!.map((v) => v.toJson()).toList();
    }
    return data;
  }
}

class DailyRecords {
  int? recordId;
  String? recordDate;
  double? recordTime;
  double? recordDist;
  double? recordPace;
  double? recordCal;

  DailyRecords(
      {this.recordId,
      this.recordDate,
      this.recordTime,
      this.recordDist,
      this.recordPace,
      this.recordCal});

  DailyRecords.fromJson(Map<String, dynamic> json) {
    recordId = json['recordId'];
    recordDate = json['recordDate'];
    recordTime = json['recordTime'];
    recordDist = json['recordDist'];
    recordPace = json['recordPace'];
    recordCal = json['recordCal'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['recordId'] = this.recordId;
    data['recordDate'] = this.recordDate;
    data['recordTime'] = this.recordTime;
    data['recordDist'] = this.recordDist;
    data['recordPace'] = this.recordPace;
    data['recordCal'] = this.recordCal;
    return data;
  }
}
