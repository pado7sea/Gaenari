class SearchMateList {
  String? status;
  String? message;
  List<SearchMate>? data;

  SearchMateList({this.status, this.message, this.data});

  SearchMateList.fromJson(Map<String, dynamic> json) {
    status = json['status'];
    message = json['message'];
    if (json['data'] != null) {
      data = <SearchMate>[];
      json['data'].forEach((v) {
        data!.add(SearchMate.fromJson(v));
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

class SearchMate {
  int? mateId;
  int? memberId;
  String? nickName;
  String? state;
  String? call;
  int? petId;
  String? petName;
  String? petTier;

  SearchMate(
      {this.mateId,
      this.memberId,
      this.nickName,
      this.state,
      this.call,
      this.petId,
      this.petName,
      this.petTier});

  SearchMate.fromJson(Map<String, dynamic> json) {
    mateId = json['mateId'];
    memberId = json['memberId'];
    nickName = json['nickName'];
    state = json['state'];
    call = json['call'];
    petId = json['petId'];
    petName = json['petName'];
    petTier = json['petTier'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['mateId'] = mateId;
    data['memberId'] = memberId;
    data['nickName'] = nickName;
    data['state'] = state;
    data['call'] = call;
    data['petId'] = petId;
    data['petName'] = petName;
    data['petTier'] = petTier;
    return data;
  }
}
