class programList {
  String? status;
  String? message;
  List<Data>? data;

  programList({this.status, this.message, this.data});

  programList.fromJson(Map<String, dynamic> json) {
    status = json['status'];
    message = json['message'];
    if (json['data'] != null) {
      data = <Data>[];
      json['data'].forEach((v) {
        data!.add(new Data.fromJson(v));
      });
    }
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['status'] = this.status;
    data['message'] = this.message;
    if (this.data != null) {
      data['data'] = this.data!.map((v) => v.toJson()).toList();
    }
    return data;
  }
}

class Data {
  List<Data>? data;

  Data({this.data});

  Data.fromJson(Map<String, dynamic> json) {
    if (json['data'] != null) {
      data = <Data>[];
      json['data'].forEach((v) {
        data!.add(new Data.fromJson(v));
      });
    }
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    if (this.data != null) {
      data['data'] = this.data!.map((v) => v.toJson()).toList();
    }
    return data;
  }
}

class Data2 {
  int? programId;
  String? programTitle;
  bool? isFavorite;
  int? usageCount;
  int? finishedCount;
  String? type;
  Program? program;

  Data2(
      {this.programId,
      this.programTitle,
      this.isFavorite,
      this.usageCount,
      this.finishedCount,
      this.type,
      this.program});

  Data2.fromJson(Map<String, dynamic> json) {
    programId = json['programId'];
    programTitle = json['programTitle'];
    isFavorite = json['isFavorite'];
    usageCount = json['usageCount'];
    finishedCount = json['finishedCount'];
    type = json['type'];
    program =
        json['program'] != null ? new Program.fromJson(json['program']) : null;
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['programId'] = this.programId;
    data['programTitle'] = this.programTitle;
    data['isFavorite'] = this.isFavorite;
    data['usageCount'] = this.usageCount;
    data['finishedCount'] = this.finishedCount;
    data['type'] = this.type;
    if (this.program != null) {
      data['program'] = this.program!.toJson();
    }
    return data;
  }
}

class Program {
  IntervalInfo? intervalInfo;
  int? targetValue;

  Program({this.intervalInfo, this.targetValue});

  Program.fromJson(Map<String, dynamic> json) {
    intervalInfo = json['intervalInfo'] != null
        ? new IntervalInfo.fromJson(json['intervalInfo'])
        : null;
    targetValue = json['targetValue'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    if (this.intervalInfo != null) {
      data['intervalInfo'] = this.intervalInfo!.toJson();
    }
    data['targetValue'] = this.targetValue;
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
