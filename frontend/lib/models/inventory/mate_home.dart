// 친구집 방문
class MateHome {
  String? status;
  String? message;
  Home? data;

  MateHome({this.status, this.message, this.data});

  MateHome.fromJson(Map<String, dynamic> json) {
    status = json['status'];
    message = json['message'];
    data = json['data'] != null ? Home.fromJson(json['data']) : null;
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['status'] = status;
    data['message'] = message;
    if (this.data != null) {
      data['data'] = this.data!.toJson();
    }
    return data;
  }
}

class Home {
  Pet? pet;
  List<Items>? items;

  Home({this.pet, this.items});

  Home.fromJson(Map<String, dynamic> json) {
    pet = json['pet'] != null ? Pet.fromJson(json['pet']) : null;
    if (json['items'] != null) {
      items = <Items>[];
      json['items'].forEach((v) {
        items!.add(Items.fromJson(v));
      });
    }
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    if (pet != null) {
      data['pet'] = pet!.toJson();
    }
    if (items != null) {
      data['items'] = items!.map((v) => v.toJson()).toList();
    }
    return data;
  }
}

class Pet {
  int? id;
  String? name;
  int? affection;
  String? tier;

  Pet({this.id, this.name, this.affection, this.tier});

  Pet.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    name = json['name'];
    affection = json['affection'];
    tier = json['tier'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['id'] = id;
    data['name'] = name;
    data['affection'] = affection;
    data['tier'] = tier;
    return data;
  }
}

class Items {
  int? id;
  String? category;
  bool? isEquip;
  bool? isHave;

  Items({this.id, this.category, this.isEquip, this.isHave});

  Items.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    category = json['category'];
    isEquip = json['isEquip'];
    isHave = json['isHave'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['id'] = id;
    data['category'] = category;
    data['isEquip'] = isEquip;
    data['isHave'] = isHave;
    return data;
  }
}
