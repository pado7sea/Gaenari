class DetailRecordList {
  String? status;
  String? message;
  RecordDetail? data;

  DetailRecordList({this.status, this.message, this.data});

  DetailRecordList.fromJson(Map<String, dynamic> json) {
    status = json['status'];
    message = json['message'];
    data = json['data'] != null ? RecordDetail.fromJson(json['data']) : null;
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = Map<String, dynamic>();
    data['status'] = status;
    data['message'] = message;
    if (this.data != null) {
      data['data'] = this.data!.toJson();
    }
    return data;
  }
}

class RecordDetail {
  int? exerciseId;
  String? date;
  String? exerciseType;
  String? programType;
  Program? program;
  Record? record;
  Paces? paces;
  Heartrates? heartrates;
  List<Trophies>? trophies;
  List<Missions>? missions;
  int? attainableCoin;
  int? attainableHeart;

  RecordDetail({
    this.exerciseId,
    this.date,
    this.exerciseType,
    this.programType,
    this.program,
    this.record,
    this.paces,
    this.heartrates,
    this.trophies,
    this.missions,
    this.attainableCoin,
    this.attainableHeart,
  });

  RecordDetail.fromJson(Map<String, dynamic> json) {
    exerciseId = json['exerciseId'];
    date = json['date'];
    exerciseType = json['exerciseType'];
    programType = json['programType'];
    program =
        json['program'] != null ? Program.fromJson(json['program']) : null;
    record = json['record'] != null ? Record.fromJson(json['record']) : null;
    paces = json['paces'] != null ? Paces.fromJson(json['paces']) : null;
    heartrates = json['heartrates'] != null
        ? Heartrates.fromJson(json['heartrates'])
        : null;
    if (json['trophies'] != null) {
      trophies = <Trophies>[];
      json['trophies'].forEach((v) {
        trophies!.add(Trophies.fromJson(v));
      });
    }
    if (json['missions'] != null) {
      missions = <Missions>[];
      json['missions'].forEach((v) {
        missions!.add(Missions.fromJson(v));
      });
    }
    attainableCoin = json['attainableCoin'];
    attainableHeart = json['attainableHeart'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = Map<String, dynamic>();
    data['exerciseId'] = exerciseId;
    data['date'] = date;
    data['exerciseType'] = exerciseType;
    data['programType'] = programType;
    if (program != null) {
      data['program'] = program!.toJson();
    }
    if (record != null) {
      data['record'] = record!.toJson();
    }
    if (paces != null) {
      data['paces'] = paces!.toJson();
    }
    if (heartrates != null) {
      data['heartrates'] = heartrates!.toJson();
    }
    if (trophies != null) {
      data['trophies'] = trophies!.map((v) => v.toJson()).toList();
    }
    if (missions != null) {
      data['missions'] = missions!.map((v) => v.toJson()).toList();
    }
    data['attainableCoin'] = attainableCoin;
    data['attainableHeart'] = attainableHeart;
    return data;
  }
}

class Program {
  int? programId;
  String? programTitle;
  double? targetValue; // double 타입으로 변경
  IntervalInfo? intervalInfo;

  Program(
      {this.programId, this.programTitle, this.targetValue, this.intervalInfo});

  Program.fromJson(Map<String, dynamic> json) {
    programId = json['programId'];
    programTitle = json['programTitle'];
    targetValue = json['targetValue']?.toDouble(); // double 타입으로 변환
    intervalInfo = json['intervalInfo'] != null
        ? IntervalInfo.fromJson(json['intervalInfo'])
        : null;
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['programId'] = this.programId;
    data['targetValue'] = this.targetValue;
    if (this.intervalInfo != null) {
      data['intervalInfo'] = this.intervalInfo!.toJson();
    }
    return data;
  }
}

class IntervalInfo {
  double? duration;
  int? setCount;
  int? rangeCount;
  List<Ranges>? ranges; // 리스트로 변경

  IntervalInfo({this.duration, this.setCount, this.rangeCount, this.ranges});

  IntervalInfo.fromJson(Map<String, dynamic> json) {
    duration = json['duration'];
    setCount = json['setCount'];
    rangeCount = json['rangeCount'];
    if (json['ranges'] != null) {
      ranges = List<Ranges>.from(
          json['ranges'].map((x) => Ranges.fromJson(x))); // 리스트로 변환하여 처리
    }
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['duration'] = this.duration;
    data['setCount'] = this.setCount;
    data['rangeCount'] = this.rangeCount;
    if (this.ranges != null) {
      data['ranges'] = this.ranges!.map((x) => x.toJson()).toList();
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
    time = json['time']?.toDouble();
    speed = json['speed']?.toDouble();
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = Map<String, dynamic>();
    data['id'] = id;
    data['isRunning'] = isRunning;
    data['time'] = time;
    data['speed'] = speed;
    return data;
  }
}

// class Record {
//   int? year;
//   int? month;
//   int? day;
//   List<DailyRecords>? dailyRecords;

//   Record({this.year, this.month, this.day, this.dailyRecords});

//   Record.fromJson(Map<String, dynamic> json) {
//     year = json['year'];
//     month = json['month'];
//     day = json['day'];
//     if (json['dailyRecords'] != null) {
//       dailyRecords = <DailyRecords>[];
//       json['dailyRecords'].forEach((v) {
//         dailyRecords!.add(DailyRecords.fromJson(v));
//       });
//     }
//   }

//   Map<String, dynamic> toJson() {
//     final Map<String, dynamic> data = Map<String, dynamic>();
//     data['year'] = year;
//     data['month'] = month;
//     data['day'] = day;
//     if (dailyRecords != null) {
//       data['dailyRecords'] = dailyRecords!.map((v) => v.toJson()).toList();
//     }
//     return data;
//   }
// }

class Record {
  double? distance;
  double? time;
  double? cal;

  Record({this.distance, this.time, this.cal});

  Record.fromJson(Map<String, dynamic> json) {
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

class DailyRecords {
  int? recordId;
  String? recordDate;
  int? recordTime;
  int? recordDist;
  int? recordPace;
  int? recordCal;

  DailyRecords({
    this.recordId,
    this.recordDate,
    this.recordTime,
    this.recordDist,
    this.recordPace,
    this.recordCal,
  });

  DailyRecords.fromJson(Map<String, dynamic> json) {
    recordId = json['recordId'];
    recordDate = json['recordDate'];
    recordTime = json['recordTime'];
    recordDist = json['recordDist'];
    recordPace = json['recordPace'];
    recordCal = json['recordCal'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = Map<String, dynamic>();
    data['recordId'] = recordId;
    data['recordDate'] = recordDate;
    data['recordTime'] = recordTime;
    data['recordDist'] = recordDist;
    data['recordPace'] = recordPace;
    data['recordCal'] = recordCal;
    return data;
  }
}

class Paces {
  double? average;
  List<int>? arr;

  Paces({this.average, this.arr});

  Paces.fromJson(Map<String, dynamic> json) {
    average = json['average']?.toDouble();
    arr = json['arr'].cast<int>();
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = Map<String, dynamic>();
    data['average'] = average;
    data['arr'] = arr;
    return data;
  }
}

class Heartrates {
  double? average;
  int? max;
  int? min;
  List<int>? arr;

  Heartrates({this.average, this.max, this.min, this.arr});

  Heartrates.fromJson(Map<String, dynamic> json) {
    average = json['average']?.toDouble();
    max = json['max'];
    min = json['min'];
    arr = json['arr'].cast<int>();
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = Map<String, dynamic>();
    data['average'] = average;
    data['max'] = max;
    data['min'] = min;
    data['arr'] = arr;
    return data;
  }
}

class Trophies {
  int? id;
  String? type;
  int? coin;

  Trophies({this.id, this.type, this.coin});

  Trophies.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    type = json['type'];
    coin = json['coin'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = Map<String, dynamic>();
    data['id'] = id;
    data['type'] = type;
    data['coin'] = coin;
    return data;
  }
}

class Missions {
  int? id;
  String? type;
  int? value;
  int? coin;
  int? heart;

  Missions({this.id, this.type, this.value, this.coin, this.heart});

  Missions.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    type = json['type'];
    value = json['value'];
    coin = json['coin'];
    heart = json['heart'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = Map<String, dynamic>();
    data['id'] = id;
    data['type'] = type;
    data['value'] = value;
    data['coin'] = coin;
    data['heart'] = heart;
    return data;
  }
}
