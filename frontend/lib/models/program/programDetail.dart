import 'dart:convert';

ProgramDetail ProgramDetailFromJson(String str) =>
    ProgramDetail.fromJson(json.decode(str));

class ProgramDetail {
  String? status;
  String? message;
  Data? data;

  ProgramDetail({this.status, this.message, this.data});

  ProgramDetail.fromJson(Map<String, dynamic> json) {
    status = json['status'];
    message = json['message'];
    data = json['data'] != null ? new Data.fromJson(json['data']) : null;
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

class Data {
  int? programId;
  String? programTitle;
  bool? isFavorite;
  String? type;
  Program? program;
  int? usageCount;

  Data(
      {this.programId,
      this.programTitle,
      this.isFavorite,
      this.type,
      this.program,
      this.usageCount});

  Data.fromJson(Map<String, dynamic> json) {
    programId = json['programId'];
    programTitle = json['programTitle'];
    isFavorite = json['isFavorite'];
    type = json['type'];
    program =
        json['program'] != null ? new Program.fromJson(json['program']) : null;
    usageCount = json['usageCount'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['programId'] = this.programId;
    data['programTitle'] = this.programTitle;
    data['isFavorite'] = this.isFavorite;
    data['type'] = this.type;
    if (this.program != null) {
      data['program'] = this.program!.toJson();
    }
    data['usageCount'] = this.usageCount;
    return data;
  }
}

class Program {
  Null? targetValue;
  IntervalInfo? intervalInfo;

  Program({this.targetValue, this.intervalInfo});

  Program.fromJson(Map<String, dynamic> json) {
    targetValue = json['targetValue'];
    intervalInfo = json['intervalInfo'] != null
        ? new IntervalInfo.fromJson(json['intervalInfo'])
        : null;
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['targetValue'] = this.targetValue;
    if (this.intervalInfo != null) {
      data['intervalInfo'] = this.intervalInfo!.toJson();
    }
    return data;
  }
}

class IntervalInfo {
  int? duration;
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
        ranges!.add(new Ranges.fromJson(v));
      });
    }
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['duration'] = this.duration;
    data['setCount'] = this.setCount;
    data['rangeCount'] = this.rangeCount;
    if (this.ranges != null) {
      data['ranges'] = this.ranges!.map((v) => v.toJson()).toList();
    }
    return data;
  }
}

class Ranges {
  int? id;
  bool? isRunning;
  int? time;
  int? speed;

  Ranges({this.id, this.isRunning, this.time, this.speed});

  Ranges.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    isRunning = json['isRunning'];
    time = json['time'];
    speed = json['speed'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['id'] = this.id;
    data['isRunning'] = this.isRunning;
    data['time'] = this.time;
    data['speed'] = this.speed;
    return data;
  }
}
