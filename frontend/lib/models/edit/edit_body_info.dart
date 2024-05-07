class EditBodyInfo {
  int? height;
  int? weight;

  EditBodyInfo({this.height, this.weight});

  EditBodyInfo.fromJson(Map<String, dynamic> json) {
    height = json['height'];
    weight = json['weight'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['height'] = height;
    data['weight'] = weight;
    return data;
  }
}
