// 반려견 입양할때 여기에 맞춰 적기

class PetAdopt {
  int? id;
  String? name;

  PetAdopt({this.id, this.name});

  PetAdopt.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    name = json['name'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['id'] = id;
    data['name'] = name;
    return data;
  }
}
