class ProgramDetail {
  String? status;
  String? message;
  Detail? data;

  ProgramDetail({this.status, this.message, this.data});

  ProgramDetail.fromJson(Map<String, dynamic> json) {
    status = json['status'];
    message = json['message'];
    data = json['data'] != null ? Detail.fromJson(json['data']) : null;
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

class Detail {
  int? programId;
  String? programTitle;
  bool? isFavorite;
  String? type;
  Program? program;
  TotalRecord? totalRecord;
  int? usageCount;
  int? finishedCount;
  List<UsageLog>? usageLog;

  Detail(
      {this.programId,
      this.programTitle,
      this.isFavorite,
      this.type,
      this.program,
      this.totalRecord,
      this.usageCount,
      this.finishedCount,
      this.usageLog});

  Detail.fromJson(Map<String, dynamic> json) {
    programId = json['programId'];
    programTitle = json['programTitle'];
    isFavorite = json['isFavorite'];
    type = json['type'];
    program =
        json['program'] != null ? Program.fromJson(json['program']) : null;
    totalRecord = json['totalRecord'] != null
        ? TotalRecord.fromJson(json['totalRecord'])
        : null;
    usageCount = json['usageCount'];
    finishedCount = json['finishedCount'];
    if (json['usageLog'] != null) {
      usageLog = <UsageLog>[];
      json['usageLog'].forEach((v) {
        usageLog!.add(UsageLog.fromJson(v));
      });
    }
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['programId'] = programId;
    data['programTitle'] = programTitle;
    data['isFavorite'] = isFavorite;
    data['type'] = type;
    if (program != null) {
      data['program'] = program!.toJson();
    }
    if (totalRecord != null) {
      data['totalRecord'] = totalRecord!.toJson();
    }
    data['usageCount'] = usageCount;
    data['finishedCount'] = finishedCount;
    if (usageLog != null) {
      data['usageLog'] = usageLog!.map((v) => v.toJson()).toList();
    }
    return data;
  }
}

class Program {
  double? targetValue;
  IntervalInfo? intervalInfo;

  Program({this.targetValue, this.intervalInfo});

  Program.fromJson(Map<String, dynamic> json) {
    targetValue = json['targetValue'];
    intervalInfo = json['intervalInfo'] != null
        ? IntervalInfo.fromJson(json['intervalInfo'])
        : null;
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['targetValue'] = targetValue;
    if (intervalInfo != null) {
      data['intervalInfo'] = intervalInfo!.toJson();
    }
    return data;
  }
}

class IntervalInfo {
  double? duration;
  int? setCount;
  int? rangeCount;
  List<Ranges>? ranges;

  IntervalInfo({this.duration, this.setCount, this.rangeCount, this.ranges});

  IntervalInfo.fromJson(Map<String, dynamic> json) {
    duration = json['duration'];
    setCount = json['setCount'];
    rangeCount = json['rangeCount'];
    if (json['ranges'] != null) {
      ranges = <Ranges>[];
      json['ranges'].forEach((v) {
        ranges!.add(Ranges.fromJson(v));
      });
    }
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['duration'] = duration;
    data['setCount'] = setCount;
    data['rangeCount'] = rangeCount;
    if (ranges != null) {
      data['ranges'] = ranges!.map((v) => v.toJson()).toList();
    }
    return data;
  }
}

class Ranges {
  int? id;
  bool? isRunning;
  double? time;
  double? speed;

  Ranges({this.id, this.isRunning, this.time, this.speed});

  Ranges.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    isRunning = json['isRunning'];
    time = json['time'];
    speed = json['speed'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['id'] = id;
    data['isRunning'] = isRunning;
    data['time'] = time;
    data['speed'] = speed;
    return data;
  }
}

class TotalRecord {
  double? distance;
  double? time;
  double? cal;

  TotalRecord({this.distance, this.time, this.cal});

  TotalRecord.fromJson(Map<String, dynamic> json) {
    distance = json['distance'];
    time = json['time'];
    cal = json['cal'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['distance'] = distance;
    data['time'] = time;
    data['cal'] = cal;
    return data;
  }
}

class UsageLog {
  int? recordId;
  double? distance;
  double? averagePace;
  double? time;
  String? date;
  double? cal;
  bool? isFinished;

  UsageLog(
      {this.recordId,
      this.distance,
      this.averagePace,
      this.time,
      this.date,
      this.cal,
      this.isFinished});

  UsageLog.fromJson(Map<String, dynamic> json) {
    recordId = json['recordId'];
    distance = json['distance'];
    averagePace = json['averagePace'];
    time = json['time'];
    date = json['date'];
    cal = json['cal'];
    isFinished = json['isFinished'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['recordId'] = recordId;
    data['distance'] = distance;
    data['averagePace'] = averagePace;
    data['time'] = time;
    data['date'] = date;
    data['cal'] = cal;
    data['isFinished'] = isFinished;
    return data;
  }
}
