class MonthlyRecordList {
  String? status;
  String? message;
  Monthly? data;

  MonthlyRecordList({this.status, this.message, this.data});

  MonthlyRecordList.fromJson(Map<String, dynamic> json) {
    status = json['status'];
    message = json['message'];
    data = json['data'] != null ? Monthly.fromJson(json['data']) : null;
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
        exerciseRecords!.add(ExerciseRecords.fromJson(v));
      });
    }
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['year'] = year;
    data['month'] = month;
    if (exerciseRecords != null) {
      data['exerciseRecords'] =
          exerciseRecords!.map((v) => v.toJson()).toList();
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
  String? exerciseType;
  String? programType;
  double? recordTime;
  double? recordDist;
  double? recordPace;
  double? recordCal;

  DailyRecords(
      {this.recordId,
      this.recordDate,
      this.exerciseType,
      this.programType,
      this.recordTime,
      this.recordDist,
      this.recordPace,
      this.recordCal});

  DailyRecords.fromJson(Map<String, dynamic> json) {
    recordId = json['recordId'];
    recordDate = json['recordDate'];
    exerciseType = json['exerciseType'];
    programType = json['programType'];
    recordTime = json['recordTime'];
    recordDist = json['recordDist'];
    recordPace = json['recordPace'];
    recordCal = json['recordCal'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['recordId'] = recordId;
    data['recordDate'] = recordDate;
    data['exerciseType'] = exerciseType;
    data['programType'] = programType;
    data['recordTime'] = recordTime;
    data['recordDist'] = recordDist;
    data['recordPace'] = recordPace;
    data['recordCal'] = recordCal;
    return data;
  }
}
