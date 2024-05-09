class ProgramList {
  String? status;
  String? message;
  List<ProgramItem>? data;

  ProgramList({this.status, this.message, this.data});

  ProgramList.fromJson(Map<String, dynamic> json) {
    status = json['status'];
    message = json['message'];
    if (json['data'] != null) {
      data = <ProgramItem>[];
      json['data'].forEach((v) {
        data!.add(ProgramItem.fromJson(v));
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

class ProgramItem {
  int? programId;
  String? programTitle;
  bool? isFavorite;
  int? usageCount;
  int? finishedCount;
  String? type;
  Program? program;

  ProgramItem(
      {this.programId,
      this.programTitle,
      this.isFavorite,
      this.usageCount,
      this.finishedCount,
      this.type,
      this.program});

  ProgramItem.fromJson(Map<String, dynamic> json) {
    programId = json['programId'];
    programTitle = json['programTitle'];
    isFavorite = json['isFavorite'];
    usageCount = json['usageCount'];
    finishedCount = json['finishedCount'];
    type = json['type'];
    program =
        json['program'] != null ? Program.fromJson(json['program']) : null;
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['programId'] = programId;
    data['programTitle'] = programTitle;
    data['isFavorite'] = isFavorite;
    data['usageCount'] = usageCount;
    data['finishedCount'] = finishedCount;
    data['type'] = type;
    if (program != null) {
      data['program'] = program!.toJson();
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
