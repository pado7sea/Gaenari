// 아이템 리스트, 각각 몇개씩 보유중인지
class SetList {
  String? status;
  String? message;
  List<Set>? data;

  SetList({this.status, this.message, this.data});

  SetList.fromJson(Map<String, dynamic> json) {
    status = json['status'];
    message = json['message'];
    if (json['data'] != null) {
      data = <Set>[];
      json['data'].forEach((v) {
        data!.add(Set.fromJson(v));
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

class Set {
  int? setId;
  int? setCnt;

  Set({this.setId, this.setCnt});

  Set.fromJson(Map<String, dynamic> json) {
    setId = json['setId'];
    setCnt = json['setCnt'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['setId'] = setId;
    data['setCnt'] = setCnt;
    return data;
  }
}
