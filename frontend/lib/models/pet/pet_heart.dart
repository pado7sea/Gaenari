// 애정도 증가시킬때 쓰는 바디

class PetHeart {
  int? id;
  int? affection;

  PetHeart({this.id, this.affection});

  PetHeart.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    affection = json['affection'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['id'] = id;
    data['affection'] = affection;
    return data;
  }
}
