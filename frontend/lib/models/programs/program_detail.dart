class ProgramDetail {
  String? status;
  String? message;
  Data? data;

  ProgramDetail({this.status, this.message, this.data});

  ProgramDetail.fromJson(Map<String, dynamic> json) {
    status = json['status'];
    message = json['message'];
    data = json['data'] != null ? Data.fromJson(json['data']) : null;
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
        json['program'] != null ? Program.fromJson(json['program']) : null;
    usageCount = json['usageCount'];
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
    data['usageCount'] = usageCount;
    return data;
  }
}

class Program {
  String? targetValue;
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
    final Map<String, dynamic> data = <String, dynamic>{};
    data['id'] = id;
    data['isRunning'] = isRunning;
    data['time'] = time;
    data['speed'] = speed;
    return data;
  }
}
