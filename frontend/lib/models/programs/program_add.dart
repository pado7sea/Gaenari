class ProgramAdd {
  String? programTitle;
  String? programType;
  double? programTargetValue;
  Interval? interval;

  ProgramAdd(
      {this.programTitle,
      this.programType,
      this.programTargetValue,
      this.interval});

  ProgramAdd.fromJson(Map<String, dynamic> json) {
    programTitle = json['programTitle'];
    programType = json['programType'];
    programTargetValue = json['programTargetValue'];
    interval =
        json['interval'] != null ? Interval.fromJson(json['interval']) : null;
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['programTitle'] = programTitle;
    data['programType'] = programType;
    data['programTargetValue'] = programTargetValue;
    if (interval != null) {
      data['interval'] = interval!.toJson();
    }
    return data;
  }
}

class Interval {
  double? duration;
  int? setCount;
  int? rangeCount;
  List<Ranges>? ranges;

  Interval({this.duration, this.setCount, this.rangeCount, this.ranges});

  Interval.fromJson(Map<String, dynamic> json) {
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
  bool? isRunning;
  double? time;
  double? speed;

  Ranges({this.isRunning, this.time, this.speed});

  Ranges.fromJson(Map<String, dynamic> json) {
    isRunning = json['isRunning'];
    time = json['time'];
    speed = json['speed'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['isRunning'] = isRunning;
    data['time'] = time;
    data['speed'] = speed;
    return data;
  }
}
