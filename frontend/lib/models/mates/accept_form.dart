// 친구 수락, 거절할때 바디에 넣는 값임.

class AcceptForm {
  int? mateId;
  bool? isAccept;

  AcceptForm({this.mateId, this.isAccept});

  AcceptForm.fromJson(Map<String, dynamic> json) {
    mateId = json['mateId'];
    isAccept = json['isAccept'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['mateId'] = mateId;
    data['isAccept'] = isAccept;
    return data;
  }
}
